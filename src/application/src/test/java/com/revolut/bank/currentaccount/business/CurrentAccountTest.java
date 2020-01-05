package com.revolut.bank.currentaccount.business;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CurrentAccountTest {

    private CurrentAccount create(Long amount, boolean isActive, Long codeCustomer){
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAmount(amount);
        if (Objects.nonNull(codeCustomer))
            currentAccount.setCustomer(new Customer(codeCustomer));
        currentAccount.setActive(isActive);
        return currentAccount;
    }

    @Test
    public void shouldCurrentAccountBeSaved_WhenAmountIsZero() {
        CurrentAccount currentAccount = create(0L, true, 1L);
        assertTrue(currentAccount.isValid());
    }

    @Test
    public void shouldCurrentAccountBeNotSaved_WhenAmountIsLessThanZero() {
        CurrentAccount currentAccount = create(-1L, true, 1L);
        assertFalse(currentAccount.isValid());
    }

    @Test
    public void shouldCurrentAccountBeNotSaved_WhenActiveIsFalse() {
        CurrentAccount currentAccount = create(10L, false, 1L);
        assertFalse(currentAccount.isValid());
    }

    @Test
    public void shouldCurrentAccountBeNotSaved_WhenCustomerIsNull() {
        CurrentAccount currentAccount = create(10L, false, null);
        assertFalse(currentAccount.isValid());
    }

}
