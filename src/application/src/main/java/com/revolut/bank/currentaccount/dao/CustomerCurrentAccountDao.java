package com.revolut.bank.currentaccount.dao;

import com.revolut.bank.currentaccount.business.Customer;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface CustomerCurrentAccountDao {
    Optional<Customer> getById(Long id);
}
