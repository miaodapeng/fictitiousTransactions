package com.vedeng.aftersales.model;

import java.io.Serializable;

public class AfterSalesTrader implements Serializable {
    private Integer afterSalesTraderId;

    private Integer afterSalesId;

    private Integer traderId;
    
    private Integer realTraderType;

    private Integer traderType;

    private String traderName;

    private String comments;

    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getRealTraderType() {
		return realTraderType;
	}

	public void setRealTraderType(Integer realTraderType) {
		this.realTraderType = realTraderType;
	}

	public Integer getAfterSalesTraderId() {
        return afterSalesTraderId;
    }

    public void setAfterSalesTraderId(Integer afterSalesTraderId) {
        this.afterSalesTraderId = afterSalesTraderId;
    }

    public Integer getAfterSalesId() {
        return afterSalesId;
    }

    public void setAfterSalesId(Integer afterSalesId) {
        this.afterSalesId = afterSalesId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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