package com.revolut.bank.currentaccount.service;

import com.revolut.bank.currentaccount.dto.BalanceDto;
import com.revolut.bank.currentaccount.form.CreateCurrentAccountForm;
import com.revolut.bank.currentaccount.form.TransferBetweenAccountForm;

import javax.ejb.Local;

@Local
public interface CurrentAccountService {
    void create(CreateCurrentAccountForm currentAccountForm);
    void withdraw(Long codeAccount, Double value);
    void deposit(Long codeAccount, Double value);
    void transfer(Long codeAccount,TransferBetweenAccountForm transferBetweenAccountForm);
    BalanceDto balance(Long accountCode);
}
