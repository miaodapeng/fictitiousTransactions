package com.vedeng.ordergoods.model;

import java.io.Serializable;

public class OrdergoodsStore implements Serializable{
    private Integer ordergoodsStoreId;

    private Integer companyId;

    private String name;

    private Integer isEnable;

    private String description;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getOrdergoodsStoreId() {
        return ordergoodsStoreId;
    }

    public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
        this.ordergoodsStoreId = ordergoodsStoreId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}