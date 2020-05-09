package com.vedeng.ordergoods.model;

import java.io.Serializable;

public class OrdergoodsLaunch implements Serializable{
    private Integer ordergoodsLaunchId;

    private Integer rOrdergoodsLaunchGoodsJCategoryId;

    private Integer traderCustomerId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getOrdergoodsLaunchId() {
        return ordergoodsLaunchId;
    }

    public void setOrdergoodsLaunchId(Integer ordergoodsLaunchId) {
        this.ordergoodsLaunchId = ordergoodsLaunchId;
    }

    public Integer getrOrdergoodsLaunchGoodsJCategoryId() {
        return rOrdergoodsLaunchGoodsJCategoryId;
    }

    public void setrOrdergoodsLaunchGoodsJCategoryId(Integer rOrdergoodsLaunchGoodsJCategoryId) {
        this.rOrdergoodsLaunchGoodsJCategoryId = rOrdergoodsLaunchGoodsJCategoryId;
    }

    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
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