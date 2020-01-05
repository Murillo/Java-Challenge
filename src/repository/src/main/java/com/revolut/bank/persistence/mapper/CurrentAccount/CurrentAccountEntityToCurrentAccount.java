package com.revolut.bank.persistence.mapper.CurrentAccount;

import com.revolut.bank.currentaccount.business.CurrentAccount;
import com.revolut.bank.currentaccount.business.Customer;
import com.revolut.bank.persistence.entity.CurrentAccountEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CurrentAccountEntityToCurrentAccount {

    private CurrentAccountEntityToCurrentAccount() {}

    public static Optional<CurrentAccount> map(CurrentAccountEntity currentAccountEntity){
        if (Objects.isNull(currentAccountEntity))
            return Optional.empty();

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setVersion(currentAccountEntity.getVersion());
        currentAccount.setAmount(currentAccountEntity.getAmount());
        currentAccount.setCode(currentAccountEntity.getId());
        currentAccount.setActive(currentAccountEntity.getActive());
        currentAccount.setCustomer(new Customer(currentAccountEntity.getCustomerEntity().getId()));
        return Optional.of(currentAccount);
    }

    public static List<CurrentAccount> mapToCollection(List<CurrentAccountEntity> currentAccountEntity){
        return currentAccountEntity.stream()
                .map(CurrentAccountEntityToCurrentAccount::map)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
