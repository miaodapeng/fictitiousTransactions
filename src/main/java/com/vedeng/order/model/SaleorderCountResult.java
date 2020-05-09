package com.vedeng.order.model;

import java.math.BigDecimal;

public class SaleorderCountResult {
    //下单天数
    private int orderDaysCount;
    //下单总额
    private BigDecimal orderDaysMoneySum;

    public int getOrderDaysCount() {
        return orderDaysCount;
    }

    public void setOrderDaysCount(int orderDaysCount) {
        this.orderDaysCount = orderDaysCount;
    }

    public BigDecimal getOrderDaysMoneySum() {
        return orderDaysMoneySum;
    }

    public void setOrderDaysMoneySum(BigDecimal orderDaysMoneySum) {
        this.orderDaysMoneySum = orderDaysMoneySum;
    }
}
