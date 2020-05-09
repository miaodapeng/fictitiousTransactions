package com.vedeng.trader.model;

import java.io.Serializable;

import com.vedeng.goods.model.Brand;

public class TraderSupplierSupplyBrand implements Serializable{
    private Integer traderSupplierSupplyBrandId;

    private Integer traderSupplierId;

    private Integer brandId;
    
    private Brand brand;

    public Integer getTraderSupplierSupplyBrandId() {
        return traderSupplierSupplyBrandId;
    }

    public void setTraderSupplierSupplyBrandId(Integer traderSupplierSupplyBrandId) {
        this.traderSupplierSupplyBrandId = traderSupplierSupplyBrandId;
    }

    public Integer getTraderSupplierId() {
        return traderSupplierId;
    }

    public void setTraderSupplierId(Integer traderSupplierId) {
        this.traderSupplierId = traderSupplierId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}