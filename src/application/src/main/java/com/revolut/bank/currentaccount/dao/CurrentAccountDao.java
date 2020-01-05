package com.revolut.bank.currentaccount.dao;


import com.revolut.bank.currentaccount.business.CurrentAccount;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface CurrentAccountDao {
    List<CurrentAccount> getAll();
    Optional<CurrentAccount> getById(Long id);
    void update(CurrentAccount currentAccount);
    void create(CurrentAccount currentAccount);
}
