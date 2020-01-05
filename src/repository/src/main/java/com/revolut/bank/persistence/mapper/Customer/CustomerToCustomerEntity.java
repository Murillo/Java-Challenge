package com.revolut.bank.persistence.mapper.Customer;

import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.persistence.entity.CustomerEntity;

import java.util.Objects;

public class CustomerToCustomerEntity {

    private CustomerToCustomerEntity() {}

    public static CustomerEntity map(Customer customer){
        CustomerEntity customerEntity = new CustomerEntity();
        if (Objects.nonNull(customer.getCode()))
            customerEntity.setId(customer.getCode());
        customerEntity.setName(customer.getName());
        customerEntity.setMiddleName(customer.getMiddleName());
        customerEntity.setLastName(customer.getLastName());
        customerEntity.setEmail(customer.getContact().getEmail());
        customerEntity.setPhone(customer.getContact().getPhone());
        return customerEntity;
    }
}
