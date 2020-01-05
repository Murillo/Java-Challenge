package com.revolut.bank.currentaccount.business;

import java.util.Objects;
import java.util.function.Predicate;

public class CurrentAccount {

    private long code;
    private Customer customer;
    private double amount;
    private boolean active;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isValid(){
        return ((Predicate<CurrentAccount>)(field -> Objects.nonNull(getCustomer())))
                .and(field -> field.getAmount() >= 0)
                .and(CurrentAccount::isActive)
                .test(this);
    }
}
