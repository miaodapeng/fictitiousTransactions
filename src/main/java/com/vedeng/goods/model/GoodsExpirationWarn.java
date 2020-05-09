package com.vedeng.goods.model;

import java.io.Serializable;

public class GoodsExpirationWarn implements Serializable{
    private Integer goodsExpirationWarnId;

    private Integer companyId;

    private Integer goodsId;

    private Integer day;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getGoodsExpirationWarnId() {
        return goodsExpirationWarnId;
    }

    public void setGoodsExpirationWarnId(Integer goodsExpirationWarnId) {
        this.goodsExpirationWarnId = goodsExpirationWarnId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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