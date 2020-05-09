package com.vedeng.trader.model;

import java.io.Serializable;

import com.vedeng.goods.model.Brand;

public class TraderCustomerAgentBrand implements Serializable{
    private Integer traderCustomerAgentBrandId;

    private Integer traderCustomerId;

    private Integer brandId;
    
    private Brand brand;

    public Integer getTraderCustomerAgentBrandId() {
        return traderCustomerAgentBrandId;
    }

    public void setTraderCustomerAgentBrandId(Integer traderCustomerAgentBrandId) {
        this.traderCustomerAgentBrandId = traderCustomerAgentBrandId;
    }

    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
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