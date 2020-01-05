package com.revolut.bank.currentaccount.business;

public class Customer {
    private Long code;

    public Customer(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }
}
