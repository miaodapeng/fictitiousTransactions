package com.smallhospital.dto;

import java.math.BigDecimal;

public class ELAccountPeriodDto {

    //当前额度
    private BigDecimal periodAmount;

    //已用额度
    private BigDecimal usedAmount;

    //剩余额度
    private BigDecimal leftAmount;

    public BigDecimal getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(BigDecimal periodAmount) {
        this.periodAmount = periodAmount;
    }

    public BigDecimal getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(BigDecimal usedAmount) {
        this.usedAmount = usedAmount;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }
}
