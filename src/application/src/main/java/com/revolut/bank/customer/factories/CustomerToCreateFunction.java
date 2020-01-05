package com.revolut.bank.customer.factories;

import com.revolut.bank.customer.business.Contact;
import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.customer.form.CustomerForm;
import com.revolut.bank.factory.FactoryAbstract;

public  class CustomerToCreateFunction extends FactoryAbstract {

    public static Customer create(CustomerForm customerForm) {
        Contact contact = new Contact();
        contact.setEmail(customerForm.getEmail());
        contact.setPhone(customerForm.getPhone());

        Customer customer = new Customer();
        customer.setName(customerForm.getName());
        customer.setMiddleName(customerForm.getMiddleName());
        customer.setLastName(customerForm.getLastName());
        customer.setContact(contact);
        return customer;
    }
}
