package com.vedeng.trader.model;

import java.io.Serializable;

import com.vedeng.system.model.SysOptionDefinition;

public class TraderCustomerAttribute implements Serializable{
    private Integer traderCustomerAttributeId;

    private Integer traderCustomerId;

    private Integer attributeCategoryId;

    private Integer attributeId;

    private String attributeOther;

    private String subCategoryIds;
    
    private SysOptionDefinition sysOptionDefinition;

    public TraderCustomerAttribute() {
		super();
	}
    
    public Integer getTraderCustomerAttributeId() {
        return traderCustomerAttributeId;
    }

    public void setTraderCustomerAttributeId(Integer traderCustomerAttributeId) {
        this.traderCustomerAttributeId = traderCustomerAttributeId;
    }

    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
    }

    public Integer getAttributeCategoryId() {
        return attributeCategoryId;
    }

    public void setAttributeCategoryId(Integer attributeCategoryId) {
        this.attributeCategoryId = attributeCategoryId;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeOther() {
        return attributeOther;
    }

    public void setAttributeOther(String attributeOther) {
        this.attributeOther = attributeOther == null ? null : attributeOther.trim();
    }

    public String getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(String subCategoryIds) {
        this.subCategoryIds = subCategoryIds == null ? null : subCategoryIds.trim();
    }

	public SysOptionDefinition getSysOptionDefinition() {
		return sysOptionDefinition;
	}

	public void setSysOptionDefinition(SysOptionDefinition sysOptionDefinition) {
		this.sysOptionDefinition = sysOptionDefinition;
	}
}