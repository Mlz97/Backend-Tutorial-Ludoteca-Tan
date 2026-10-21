package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.common.exception.CustomerAlreadyExistsException;
import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import com.ccsw.tutorial.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer get(Long id) {
        return this.customerRepository.findById(id).orElse(null);
    }

    //devolver lista customer
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) this.customerRepository.findAll();
    }

    //crear/editar customer
    @Override
    public void save(Long id, CustomerDto dto) {
        Customer customer;

        if (id == null) {
            if (customerRepository.existsByName(dto.getName())) {
                throw new CustomerAlreadyExistsException("Ese nombre ya existe");
            }
            customer = new Customer();
        } else {
            customer = this.get(id);
        }

        customer.setName((dto.getName()));

        this.customerRepository.save(customer);

    }

    @Override
    public void delete(Long id) throws Exception {

        if (loanRepository.existsByCustomerId(id)) {
            throw new Exception("Customer has associated loan");
        }

        if (this.get(id) == null) {
            throw new Exception("No existe");
        }

        this.customerRepository.deleteById(id);
    }

}
