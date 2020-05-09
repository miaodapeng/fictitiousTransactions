package com.vedeng.goods.model;

import java.io.Serializable;

public class RBrandJTraderSupplier implements Serializable{
    private Integer rBrandJTraderSupplier;

    private Integer brandId;

    private Integer traderSupplierId;

    public Integer getrBrandJTraderSupplier() {
        return rBrandJTraderSupplier;
    }

    public void setrBrandJTraderSupplier(Integer rBrandJTraderSupplier) {
        this.rBrandJTraderSupplier = rBrandJTraderSupplier;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getTraderSupplierId() {
        return traderSupplierId;
    }

    public void setTraderSupplierId(Integer traderSupplierId) {
        this.traderSupplierId = traderSupplierId;
    }
}