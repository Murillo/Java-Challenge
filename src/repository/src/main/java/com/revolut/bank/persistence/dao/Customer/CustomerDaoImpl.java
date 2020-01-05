package com.revolut.bank.persistence.dao.Customer;

import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.customer.dao.CustomerDao;
import com.revolut.bank.persistence.entity.CustomerEntity;
import com.revolut.bank.persistence.mapper.Customer.CustomerToCustomerEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.revolut.bank.persistence.mapper.Customer.CustomerEntityToCustomer.mapCollectionToCustomer;
import static com.revolut.bank.persistence.mapper.Customer.CustomerEntityToCustomer.mapToCustomer;

@Stateless
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext(unitName = "bank-system")
    private EntityManager entityManager;

    @Override
    public List<Customer> getAll() {
        return mapCollectionToCustomer(entityManager.createQuery("SELECT c from CustomerEntity as c").getResultList());
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return mapToCustomer(entityManager.find(CustomerEntity.class, id));
    }

    @Override
    public void save(Customer customer) {
        if (Objects.nonNull(customer.getCode())){
            entityManager.merge(CustomerToCustomerEntity.map(customer));
        }else{
            entityManager.persist(CustomerToCustomerEntity.map(customer));
        }
    }

    @Override
    public void delete(Customer customer) {
        CustomerEntity customerEntity = CustomerToCustomerEntity.map(customer);
        if (!entityManager.contains(customerEntity)) {
            customerEntity = entityManager.merge(customerEntity);
        }
        entityManager.remove(customerEntity);
    }
}
