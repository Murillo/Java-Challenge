package com.revolut.bank.currentaccount.service.implementation;

import com.revolut.bank.currentaccount.business.CurrentAccount;
import com.revolut.bank.currentaccount.business.Customer;
import com.revolut.bank.currentaccount.dao.CurrentAccountDao;
import com.revolut.bank.currentaccount.dao.CustomerCurrentAccountDao;
import com.revolut.bank.currentaccount.dto.BalanceDto;
import com.revolut.bank.currentaccount.factories.CurrentAccountToCreateFunction;
import com.revolut.bank.currentaccount.form.CreateCurrentAccountForm;
import com.revolut.bank.currentaccount.form.TransferBetweenAccountForm;
import com.revolut.bank.currentaccount.service.CurrentAccountService;
import com.revolut.bank.exception.BusinessRuleException;
import com.revolut.bank.exception.RecordNotFoundException;

import javax.ejb.*;
import javax.inject.Inject;

@Stateless
public class CurrentAccountServiceImpl implements CurrentAccountService {

    private static final Integer MINIMUM_AMOUNT_CURRENT_ACCOUNT = 0;
    private static final String CUSTOMER_NOT_FOUND_TO_CREATE_ACCOUNT_MESSAGE = "CUSTOMER_NOT_FOUND_TO_CREATE_ACCOUNT";
    private static final String CURRENT_ACCOUNT_NOT_FOUND_MESSAGE = "CURRENT_ACCOUNT_NOT_FOUND";
    private static final String FROM_CURRENT_ACCOUNT_NOT_FOUND_MESSAGE = "FROM_CURRENT_ACCOUNT_NOT_FOUND";
    private static final String TO_CURRENT_ACCOUNT_NOT_FOUND_MESSAGE = "TO_CURRENT_ACCOUNT_NOT_FOUND";
    private static final String INVALID_OPERATION_DUE_TO_SOME_ACCOUNT_INVALID_MESSAGE = "INVALID_OPERATION_DUE_TO_SOME_ACCOUNT_INVALID";
    private static final String MINIMUM_AMOUNT_CURRENT_ACCOUNT_RULE_MESSAGE = "MINIMUM_AMOUNT_CURRENT_ACCOUNT_RULE";

    private final CurrentAccountDao currentAccountDao;
    private final CustomerCurrentAccountDao customerCurrentAccountDao;

    @Inject
    public CurrentAccountServiceImpl(CurrentAccountDao currentAccountDao, CustomerCurrentAccountDao customerCurrentAccountDao){
        this.currentAccountDao = currentAccountDao;
        this.customerCurrentAccountDao = customerCurrentAccountDao;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(CreateCurrentAccountForm currentAccountForm) {
        Customer customer = customerCurrentAccountDao
                .getById(currentAccountForm.getCustomerCode())
                .orElseThrow(() -> new RecordNotFoundException(CUSTOMER_NOT_FOUND_TO_CREATE_ACCOUNT_MESSAGE));

        CurrentAccount currentAccount = CurrentAccountToCreateFunction.create(customer);
        currentAccountDao.create(currentAccount);
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void withdraw(Long accountCode, Double value) {
        CurrentAccount currentAccount = currentAccountDao
                .getById(accountCode)
                .orElseThrow(() -> new RecordNotFoundException(CURRENT_ACCOUNT_NOT_FOUND_MESSAGE));

        currentAccount.setAmount(currentAccount.getAmount() - value);
        if (!currentAccount.isValid())
            throw new BusinessRuleException(MINIMUM_AMOUNT_CURRENT_ACCOUNT_RULE_MESSAGE);

        currentAccountDao.update(currentAccount);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deposit(Long accountCode, Double value) {
        CurrentAccount currentAccount = currentAccountDao
                .getById(accountCode)
                .orElseThrow(() -> new RecordNotFoundException(CURRENT_ACCOUNT_NOT_FOUND_MESSAGE));

        currentAccount.setAmount(currentAccount.getAmount() + value);
        currentAccountDao.update(currentAccount);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transfer(Long accountCode, TransferBetweenAccountForm transferBetweenAccountForm) {
        CurrentAccount fromCurrentAccount = currentAccountDao
                .getById(accountCode)
                .orElseThrow(() -> new RecordNotFoundException(FROM_CURRENT_ACCOUNT_NOT_FOUND_MESSAGE));

        CurrentAccount toCurrentAccount = currentAccountDao
                .getById(transferBetweenAccountForm.getToAccountCode())
                .orElseThrow(() -> new RecordNotFoundException(TO_CURRENT_ACCOUNT_NOT_FOUND_MESSAGE));

        if (!(fromCurrentAccount.isValid() || toCurrentAccount.isValid()))
            throw new BusinessRuleException(INVALID_OPERATION_DUE_TO_SOME_ACCOUNT_INVALID_MESSAGE);

        double fromNewAmount = fromCurrentAccount.getAmount() - transferBetweenAccountForm.getValue();
        if (fromNewAmount < MINIMUM_AMOUNT_CURRENT_ACCOUNT)
            throw new BusinessRuleException(MINIMUM_AMOUNT_CURRENT_ACCOUNT_RULE_MESSAGE);

        double toNewAmount = toCurrentAccount.getAmount() + transferBetweenAccountForm.getValue();
        toCurrentAccount.setAmount(toNewAmount);
        fromCurrentAccount.setAmount(fromNewAmount);
        currentAccountDao.update(fromCurrentAccount);
        currentAccountDao.update(toCurrentAccount);
    }

    @Override
    public BalanceDto balance(Long accountCode) {
        CurrentAccount currentAccount = currentAccountDao
                .getById(accountCode)
                .orElseThrow(() -> new RecordNotFoundException(CURRENT_ACCOUNT_NOT_FOUND_MESSAGE));
        return BalanceDto.of(currentAccount.getAmount());
    }
}
