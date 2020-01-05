package com.revolut.bank.persistence.dao.CurrentAccount;

import com.revolut.bank.currentaccount.dao.CustomerCurrentAccountDao;
import com.revolut.bank.currentaccount.business.Customer;
import com.revolut.bank.persistence.entity.CustomerEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static com.revolut.bank.persistence.mapper.CurrentAccount.CustomerEntityToCustomer.map;

@Stateless
public class CustomerCurrentAccountDaoImpl implements CustomerCurrentAccountDao {

    @PersistenceContext(unitName = "bank-system")
    private EntityManager entityManager;

    @Override
    public Optional<Customer> getById(Long id) {
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, id);
        return map(customerEntity);
    }
}
