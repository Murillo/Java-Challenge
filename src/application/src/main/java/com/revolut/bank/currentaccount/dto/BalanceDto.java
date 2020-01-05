package com.revolut.bank.currentaccount.dto;

import java.time.LocalDateTime;

public final class BalanceDto {
    private Double amount;
    private LocalDateTime analysisTime;

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getAnalysisTime() {
        return analysisTime;
    }

    private BalanceDto(Double amount){
        this.amount = amount;
        this.analysisTime = LocalDateTime.now();
    }

    public static BalanceDto of(Double amount){
        return new BalanceDto(amount);
    }
}
