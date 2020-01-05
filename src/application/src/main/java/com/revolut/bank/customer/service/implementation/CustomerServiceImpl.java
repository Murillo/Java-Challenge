package com.revolut.bank.customer.service.implementation;

import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.customer.dao.CustomerDao;
import com.revolut.bank.customer.dto.CustomerDto;
import com.revolut.bank.customer.factories.CustomerToCreateFunction;
import com.revolut.bank.customer.form.CustomerForm;
import com.revolut.bank.customer.mapper.CustomerToCustomerDto;
import com.revolut.bank.customer.service.CustomerService;
import com.revolut.bank.exception.BusinessRuleException;
import com.revolut.bank.exception.RecordNotFoundException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND = "CUSTOMER_NOT_FOUND";
    private static final String CUSTOMER_IS_NOT_VALID = "CUSTOMER_IS_NOT_VALID";

    private final CustomerDao customerDao;

    @Inject
    public CustomerServiceImpl(CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    @Override
    public List<CustomerDto> getAll() {
        return CustomerToCustomerDto.mapCollection(customerDao.getAll());
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        Optional<Customer> customer = customerDao.getById(id);
        if (!customer.isPresent()) {
            throw new RecordNotFoundException(CUSTOMER_NOT_FOUND);
        }
        return CustomerToCustomerDto.map(customer.get());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(Long id, CustomerForm customerForm) {
        Optional<Customer> optionalCustomer = customerDao.getById(id);
        if (!optionalCustomer.isPresent())
            throw new RecordNotFoundException(CUSTOMER_NOT_FOUND);

        Customer customer = optionalCustomer.get();
        customer.setName(customerForm.getName());
        customer.setMiddleName(customerForm.getMiddleName());
        customer.setLastName(customerForm.getLastName());
        customer.getContact().setEmail(customerForm.getEmail());
        customer.getContact().setPhone(customerForm.getPhone());
        if (!customer.isValid())
            throw new BusinessRuleException(CUSTOMER_IS_NOT_VALID);

        customerDao.save(customer);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(CustomerForm customerForm) {
        Customer customer = CustomerToCreateFunction.create(customerForm);
        if (!customer.isValid())
            throw new BusinessRuleException(CUSTOMER_IS_NOT_VALID);

        customerDao.save(CustomerToCreateFunction.create(customerForm));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Long id) {
        Optional<Customer> customer = customerDao.getById(id);
        if (!customer.isPresent()) {
            throw new RecordNotFoundException(CUSTOMER_NOT_FOUND);
        }
        customerDao.delete(customer.get());
    }
}
