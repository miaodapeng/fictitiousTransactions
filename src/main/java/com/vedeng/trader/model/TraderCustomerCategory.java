package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderCustomerCategory implements Serializable{
    private Integer traderCustomerCategoryId;

    private Integer parentId;

    private String customerCategoryName;

    private Integer sort;

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

    public String getCustomerCategoryName() {
        return customerCategoryName;
    }

    public void setCustomerCategoryName(String customerCategoryName) {
        this.customerCategoryName = customerCategoryName == null ? null : customerCategoryName.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}