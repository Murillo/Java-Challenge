package com.revolut.bank.customer.service;

import com.revolut.bank.customer.business.Contact;
import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.customer.dao.CustomerDao;
import com.revolut.bank.customer.form.CustomerForm;
import com.revolut.bank.customer.service.implementation.CustomerServiceImpl;
import com.revolut.bank.exception.BusinessRuleException;
import com.revolut.bank.exception.RecordNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Objects;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    private static final Long CUSTOMER_CODE = 1L;

    CustomerServiceImpl customerService;

    @Mock
    CustomerDao customerDao;

    private Customer getCustomer(Long code){
        Contact contact = new Contact();
        contact.setPhone("9923123");
        contact.setEmail("test@test.com");

        Customer customer = new Customer();
        if (Objects.nonNull(code))
            customer.setCode(code);
        customer.setName("Test");
        customer.setMiddleName("Test");
        customer.setLastName("Test");
        customer.setContact(contact);
        return customer;
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerDao);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotGetCustomer_WhenCustomerIsNotFound(){
        when(customerDao.getById(CUSTOMER_CODE)).thenReturn(empty());
        customerService.getCustomer(CUSTOMER_CODE);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotCreateCustomer_WhenNameIsInvalid(){
        CustomerForm customerForm = new CustomerForm();
        customerForm.setName(null);
        customerForm.setLastName("Test");
        customerForm.setEmail("test@test.oom");
        customerService.create(customerForm);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotCreateCustomer_WhenLastNameIsInvalid(){
        CustomerForm customerForm = new CustomerForm();
        customerForm.setName("Murillo");
        customerForm.setLastName(null);
        customerForm.setEmail("test@test.oom");
        customerService.create(customerForm);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotCreateCustomer_WhenEmailIsInvalid(){
        CustomerForm customerForm = new CustomerForm();
        customerForm.setName("Murillo");
        customerForm.setLastName("Grubler");
        customerForm.setEmail("test@test");
        customerService.create(customerForm);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldNotDeleteCustomer_WhenCustomerIsNotFound(){
        when(customerDao.getById(CUSTOMER_CODE)).thenReturn(empty());
        customerService.delete(CUSTOMER_CODE);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotUpdateCustomer_WhenEmailIsInvalid(){
        when(customerDao.getById(CUSTOMER_CODE)).thenReturn(of(getCustomer(CUSTOMER_CODE)));
        CustomerForm customerForm = new CustomerForm();
        customerForm.setName("Murillo");
        customerForm.setLastName("Grubler");
        customerForm.setEmail("test");
        customerService.update(CUSTOMER_CODE, customerForm);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotUpdateCustomer_WhenNameIsInvalid(){
        when(customerDao.getById(CUSTOMER_CODE)).thenReturn(of(getCustomer(CUSTOMER_CODE)));
        CustomerForm customerForm = new CustomerForm();
        customerForm.setName(null);
        customerForm.setLastName("Grubler");
        customerForm.setEmail("test@test.com");
        customerService.update(CUSTOMER_CODE, customerForm);
    }

    @Test(expected = BusinessRuleException.class)
    public void shouldNotUpdateCustomer_WhenLastNameIsInvalid(){
        when(customerDao.getById(CUSTOMER_CODE)).thenReturn(of(getCustomer(CUSTOMER_CODE)));
        CustomerForm customerForm = new CustomerForm();
        customerForm.setName("Murillo");
        customerForm.setLastName(null);
        customerForm.setEmail("test@test.com");
        customerService.update(CUSTOMER_CODE, customerForm);
    }

}
