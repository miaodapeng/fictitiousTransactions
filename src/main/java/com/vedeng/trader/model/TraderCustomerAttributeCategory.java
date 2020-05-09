package com.vedeng.trader.model;

import java.io.Serializable;
import java.util.List;

import com.vedeng.system.model.SysOptionDefinition;


public class TraderCustomerAttributeCategory implements Serializable{
    private Integer attributeCategoryId;

    private Integer traderCustomerCategoryId;

    private Integer parentId;

    private Integer scope;

    private String categoryName;

    private Integer inputType;

    private Integer isSelected;

    private Integer sort;
    
    List<SysOptionDefinition> sysOptionDefinitions;

    public List<SysOptionDefinition> getSysOptionDefinitions() {
		return sysOptionDefinitions;
	}

	public void setSysOptionDefinitions(List<SysOptionDefinition> sysOptionDefinitions) {
		this.sysOptionDefinitions = sysOptionDefinitions;
	}

    public Integer getAttributeCategoryId() {
        return attributeCategoryId;
    }

    public void setAttributeCategoryId(Integer attributeCategoryId) {
        this.attributeCategoryId = attributeCategoryId;
    }

    public Integer getTraderCustomerCategoryId() {
        return traderCustomerCategoryId;
    }

    public void setTraderCustomerCategoryId(Integer traderCustomerCategoryId) {
        this.traderCustomerCategoryId = traderCustomerCategoryId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getInputType() {
        return inputType;
    }

    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}