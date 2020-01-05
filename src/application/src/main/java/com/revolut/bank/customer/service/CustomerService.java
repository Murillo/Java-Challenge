package com.revolut.bank.customer.service;

import com.revolut.bank.customer.dto.CustomerDto;
import com.revolut.bank.customer.form.CustomerForm;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerService {
    List<CustomerDto> getAll();
    CustomerDto getCustomer(Long id);
    void update(Long id, CustomerForm customerForm);
    void create(CustomerForm customerForm);
    void delete(Long id);
}
