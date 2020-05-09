package com.vedeng.logistics.model;

import java.io.Serializable;

public class WarehouseOrderAssign implements Serializable{
    private Integer warehouseOrderAssignId;

    private Integer companyId;

    private Integer orderType;

    private Integer isEnable;

    private Integer orderId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getWarehouseOrderAssignId() {
        return warehouseOrderAssignId;
    }

    public void setWarehouseOrderAssignId(Integer warehouseOrderAssignId) {
        this.warehouseOrderAssignId = warehouseOrderAssignId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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