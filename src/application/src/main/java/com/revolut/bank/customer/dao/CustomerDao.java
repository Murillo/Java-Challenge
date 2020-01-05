package com.revolut.bank.customer.dao;

import com.revolut.bank.customer.business.Customer;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface CustomerDao {
    List<Customer> getAll();
    Optional<Customer> getById(Long id);
    void save(Customer customer);
    void delete(Customer customer);
}
