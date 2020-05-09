package com.vedeng.logistics.model;

import java.io.Serializable;

public class CompanyDeliveryAddress implements Serializable{
    private Integer companyDeliveryAddressId;

    private Integer companyId;

    private Integer areaId;

    private String areaIds;

    private Integer isEnable;

    private String address;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getCompanyDeliveryAddressId() {
        return companyDeliveryAddressId;
    }

    public void setCompanyDeliveryAddressId(Integer companyDeliveryAddressId) {
        this.companyDeliveryAddressId = companyDeliveryAddressId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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