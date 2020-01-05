package com.revolut.bank.customer.business;

import java.util.Objects;
import java.util.function.Predicate;

public class Customer {
    private Long code;
    private String name;
    private String middleName;
    private String lastName;
    private Contact contact;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Boolean isValid(){
        return ((Predicate<Customer>)(field -> Objects.nonNull(field.getName())))
                .and(field -> Objects.nonNull(field.getLastName()))
                .and(field -> Objects.nonNull(field.getContact()))
                .and(field -> getContact().isValidEmail())
                .test(this);
    }
}
