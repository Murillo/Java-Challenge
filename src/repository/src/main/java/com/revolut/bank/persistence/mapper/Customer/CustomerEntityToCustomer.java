package com.revolut.bank.persistence.mapper.Customer;

import com.revolut.bank.customer.business.Contact;
import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.persistence.entity.CustomerEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerEntityToCustomer {

    private CustomerEntityToCustomer() {}

    public static Optional<Customer> mapToCustomer(CustomerEntity customerEntity){
        if (Objects.isNull(customerEntity))
            return Optional.empty();

        Contact contact = new Contact();
        contact.setEmail(customerEntity.getEmail());
        contact.setPhone(customerEntity.getPhone());

        Customer customer = new Customer();
        customer.setCode(customerEntity.getId());
        customer.setName(customerEntity.getName());
        customer.setMiddleName(customerEntity.getMiddleName());
        customer.setLastName(customerEntity.getLastName());
        customer.setContact(contact);
        return Optional.of(customer);
    }

    public static List<Customer> mapCollectionToCustomer(List<CustomerEntity> customerEntityList){
        return customerEntityList.stream()
                .map(CustomerEntityToCustomer::mapToCustomer)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
