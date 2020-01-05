package com.revolut.bank.currentaccount.factories;

import com.revolut.bank.currentaccount.business.CurrentAccount;
import com.revolut.bank.currentaccount.business.Customer;
import com.revolut.bank.factory.FactoryAbstract;

public class CurrentAccountToCreateFunction extends FactoryAbstract {

    public static CurrentAccount create(Customer customer) {
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAmount(0D);
        currentAccount.setCustomer(customer);
        currentAccount.setActive(true);
        return currentAccount;
    }

}
