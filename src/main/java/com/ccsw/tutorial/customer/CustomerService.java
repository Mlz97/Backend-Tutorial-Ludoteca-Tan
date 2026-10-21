package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;

import java.util.List;

public interface CustomerService {

    Customer get(Long id);

    //recuperar los customers
    List<Customer> findAll();

    //crear/actualizar customers
    void save(Long id, CustomerDto dto);

    //Eliminar customer
    void delete(Long id) throws Exception;

}
