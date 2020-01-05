package com.revolut.bank.persistence.mapper.CurrentAccount;

import com.revolut.bank.currentaccount.business.CurrentAccount;
import com.revolut.bank.persistence.entity.CurrentAccountEntity;
import com.revolut.bank.persistence.entity.CustomerEntity;

import java.util.Objects;

public class CurrentAccountToCurrentAccountEntity {

    private CurrentAccountToCurrentAccountEntity() {}

    public static CurrentAccountEntity map(CurrentAccount currentAccount, CustomerEntity customerEntity){
        CurrentAccountEntity currentAccountEntity = new CurrentAccountEntity();
        if (Objects.nonNull(currentAccount.getCode())) {
            currentAccountEntity.setId(currentAccount.getCode());
            currentAccountEntity.setVersion(currentAccount.getVersion());
        }
        currentAccountEntity.setAmount(currentAccount.getAmount());
        currentAccountEntity.setCustomerEntity(customerEntity);
        currentAccountEntity.setActive(currentAccount.isActive());
        return currentAccountEntity;
    }

}
