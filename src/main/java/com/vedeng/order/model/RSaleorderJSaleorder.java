package com.vedeng.order.model;

import java.io.Serializable;

public class RSaleorderJSaleorder implements Serializable{
    private Integer rSaleorderJSaleorderId;

    private Integer mainSaleorderId;

    private Integer saleorderId;

    public Integer getrSaleorderJSaleorderId() {
        return rSaleorderJSaleorderId;
    }

    public void setrSaleorderJSaleorderId(Integer rSaleorderJSaleorderId) {
        this.rSaleorderJSaleorderId = rSaleorderJSaleorderId;
    }

    public Integer getMainSaleorderId() {
        return mainSaleorderId;
    }

    public void setMainSaleorderId(Integer mainSaleorderId) {
        this.mainSaleorderId = mainSaleorderId;
    }

    public Integer getSaleorderId() {
        return saleorderId;
    }

    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }
}