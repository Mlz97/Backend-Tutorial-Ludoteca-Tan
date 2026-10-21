package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.common.exception.LoanValidationException;
import com.ccsw.tutorial.customer.CustomerRepository;
import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.game.GameRepository;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId()));
        LoanSpecification customerSpec = new LoanSpecification(new SearchCriteria("customer.id", ":", dto.getCustomerId()));
        LoanSpecification startDateSpect = new LoanSpecification(new SearchCriteria("startDate", "<=", dto.getDate()));
        LoanSpecification endDateSpec = new LoanSpecification(new SearchCriteria("endDate", ">=", dto.getDate()));

        Specification<Loan> spec = Specification.where(gameSpec).and(customerSpec).and(startDateSpect).and(endDateSpec);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) throws LoanValidationException {

        Loan loan;

        Long daysBetweenDates = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate());

        int maxDaysLoan = 14;

        List<Loan> loanByGame = loanRepository.findByGameId(dto.getGame().getId());

        List<Loan> loanByCustomer = loanRepository.findByCustomerId(dto.getCustomer().getId());

        //fecha fin > fecha inicio
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new LoanValidationException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        //prestamo < 14 días
        if (daysBetweenDates > maxDaysLoan) {
            throw new LoanValidationException("El préstamo no puede superar los 14 días");
        }

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
        }

        //1 cliente no puede tener mas de 2 prestamos en mismo dia
        for (LocalDate day = dto.getStartDate(); !day.isAfter(dto.getEndDate()); day = day.plusDays(1)) {

            int loanSum = 0;

            for (Loan existingLoan : loanByCustomer) {
                if (id != null && existingLoan.getId().equals(dto.getId())) {
                    continue;
                }
                boolean activeOnDay = !day.isBefore(existingLoan.getStartDate()) && !day.isAfter(existingLoan.getEndDate());
                if (activeOnDay) {
                    loanSum++;
                }
            }
            if (loanSum >= 2) {
                throw new LoanValidationException("El cliente no puede tener más de 2 préstamos simultáneos");
            }
        }

        //mismo juego NO prestado a distintos clientes en mismo dia
        for (Loan existingLoan : loanByGame) {

            if (id != null && existingLoan.getId().equals(dto.getId())) {
                continue;
            }

            boolean overlap = !dto.getEndDate().isBefore(existingLoan.getStartDate()) && !dto.getStartDate().isAfter(existingLoan.getEndDate());

            if (overlap) {
                throw new LoanValidationException("El juego ya está prestado en las fechas seleccionadas");
            }
        }

        BeanUtils.copyProperties(dto, loan, "id", "customer", "game");

        Customer customer = customerRepository.findById(dto.getCustomer().getId()).orElseThrow();

        Game game = gameRepository.findById(dto.getGame().getId()).orElseThrow();

        loan.setCustomer(customer);
        loan.setGame(game);

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.loanRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }
}
