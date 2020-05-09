package com.vedeng.finance.model;

public class BatchBillInfo {
    private CapitalBill capitalBill;
    private Integer saleOrderId;

    public CapitalBill getCapitalBill() {
        return capitalBill;
    }

    public void setCapitalBill(CapitalBill capitalBill) {
        this.capitalBill = capitalBill;
    }

    public Integer getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(Integer saleOrderId) {
        this.saleOrderId = saleOrderId;
    }
}
