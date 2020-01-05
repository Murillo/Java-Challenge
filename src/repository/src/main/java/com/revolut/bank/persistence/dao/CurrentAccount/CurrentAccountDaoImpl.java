package com.revolut.bank.persistence.dao.CurrentAccount;

import com.revolut.bank.currentaccount.business.CurrentAccount;
import com.revolut.bank.currentaccount.dao.CurrentAccountDao;
import com.revolut.bank.persistence.entity.CurrentAccountEntity;
import com.revolut.bank.persistence.entity.CustomerEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.revolut.bank.persistence.mapper.CurrentAccount.CurrentAccountEntityToCurrentAccount.map;
import static com.revolut.bank.persistence.mapper.CurrentAccount.CurrentAccountEntityToCurrentAccount.mapToCollection;
import static com.revolut.bank.persistence.mapper.CurrentAccount.CurrentAccountToCurrentAccountEntity.map;

@Stateless
public class CurrentAccountDaoImpl implements CurrentAccountDao {

    @PersistenceContext(unitName = "bank-system")
    private EntityManager entityManager;

    @Override
    public List<CurrentAccount> getAll() {
        return mapToCollection(entityManager.createQuery("SELECT c from CurrentAccountEntity as c").getResultList());
    }

    @Override
    public Optional<CurrentAccount> getById(Long id) {
        return map(entityManager.find(CurrentAccountEntity.class, id));
    }


    @Override
    public void update(CurrentAccount currentAccount) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, currentAccount.getCustomer().getCode());
        CurrentAccountEntity currentAccountEntity = map(currentAccount, customerEntity);
        entityManager.merge(currentAccountEntity);
    }

    @Override
    public void create(CurrentAccount currentAccount) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, currentAccount.getCustomer().getCode());
        entityManager.persist(map(currentAccount, customerEntity));
    }

}
