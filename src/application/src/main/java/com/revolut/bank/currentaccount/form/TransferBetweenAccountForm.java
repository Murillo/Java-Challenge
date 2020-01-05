package com.revolut.bank.currentaccount.form;

public class TransferBetweenAccountForm {

    private Long toAccountCode;
    private Double value;

    public Long getToAccountCode() {
        return toAccountCode;
    }

    public void setToAccountCode(Long toAccountCode) {
        this.toAccountCode = toAccountCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
