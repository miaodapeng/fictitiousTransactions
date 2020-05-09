package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderCustomerBussinessArea implements Serializable{
    private Integer traderCustomerBussinessAreaId;

    private Integer traderCustomerId;

    private Integer areaId;

    private String areaIds;
    
    private String areaStr;

    public Integer getTraderCustomerBussinessAreaId() {
        return traderCustomerBussinessAreaId;
    }

    public void setTraderCustomerBussinessAreaId(Integer traderCustomerBussinessAreaId) {
        this.traderCustomerBussinessAreaId = traderCustomerBussinessAreaId;
    }

    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds == null ? null : areaIds.trim();
    }

	public String getAreaStr() {
		return areaStr;
	}

	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}
}