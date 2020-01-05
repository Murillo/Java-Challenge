package com.revolut.bank.customer.business;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

    private Customer create(long code, String name, String middleName, String lastName, String email){
        Customer customer = new Customer();
        customer.setCode(code);
        customer.setName(name);
        customer.setLastName(lastName);
        if (Objects.nonNull(middleName)) {
            customer.setMiddleName(middleName);
        }
        if (Objects.nonNull(email)) {
            Contact contact = new Contact();
            contact.setEmail(email);
            customer.setContact(contact);
        }
        return customer;
    }

    @Test
    public void shouldCustomerBeSaved() {
        Customer currentAccount = create(1L, "Murillo", "Carlos", "Grubler", "test@test.com");
        assertTrue(currentAccount.isValid());
    }

    @Test
    public void shouldCustomerBeSaved_whenMiddleNameIsNull() {
        Customer currentAccount = create(1L, "Murillo", null, "Grubler", "test@test.com");
        assertTrue(currentAccount.isValid());
    }

    @Test
    public void shouldCustomerBeNotSaved_whenEmailHasSpecialChar() {
        Customer currentAccount = create(1L, "Murillo", null, "Grubler", "tes!t@test.com");
        assertFalse(currentAccount.isValid());
    }

    @Test
    public void shouldCustomerBeNotSaved_whenEmailEmailIsNotCompleted() {
        Customer currentAccount = create(1L, "Murillo", null, "Grubler", "testes@test");
        assertFalse(currentAccount.isValid());
    }

    @Test
    public void shouldCustomerBeNotSaved_whenEmailIsNull() {
        Customer currentAccount = create(1L, "Murillo", null, "Grubler", null);
        assertFalse(currentAccount.isValid());
    }

    @Test
    public void shouldCustomerBeNotSaved_whenNameIsNull() {
        Customer currentAccount = create(1L, null, null, "Grubler", "test@test.com");
        assertFalse(currentAccount.isValid());
    }

    @Test
    public void shouldCustomerBeNotSaved_whenLastNameIsNull() {
        Customer currentAccount = create(1L, "Murillo", null, null, "test@test.com");
        assertFalse(currentAccount.isValid());
    }

}
