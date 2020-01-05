package com.revolut.bank.persistence.mapper.CurrentAccount;

import com.revolut.bank.currentaccount.business.Customer;
import com.revolut.bank.persistence.entity.CustomerEntity;

import java.util.Objects;
import java.util.Optional;

public class CustomerEntityToCustomer {

    private CustomerEntityToCustomer() {}

    public static Optional<Customer> map(CustomerEntity customerEntity){
        if (Objects.isNull(customerEntity))
            return Optional.empty();

        return Optional.of(new Customer(customerEntity.getId()));
    }
}
