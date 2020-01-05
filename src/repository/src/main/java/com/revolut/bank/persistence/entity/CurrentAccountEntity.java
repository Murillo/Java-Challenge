package com.revolut.bank.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CURRENT_ACCOUNT")
public class CurrentAccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="account_sequence")
    @Column(name = "ID")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_CODE")
    private CustomerEntity customerEntity;

    @Column(name = "AMOUNT", nullable = false, columnDefinition = "decimal(13,2) default 0")
    private Double amount;

    @Column(name = "ACTIVE", nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    @Version
    @Column(name = "VERSION", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Integer version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
