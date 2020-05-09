package com.vedeng.ordergoods.model;

import java.io.Serializable;

public class OrdergoodsStoreAccount implements Serializable{
    private Integer ordergoodsStoreAccountId;

    private Integer webAccountId;
    
    private String uuid;

    private Integer ordergoodsStoreId;

    private Integer traderContactId;

    private Integer traderAddressId;

    private Integer isEnable;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getOrdergoodsStoreAccountId() {
        return ordergoodsStoreAccountId;
    }

    public void setOrdergoodsStoreAccountId(Integer ordergoodsStoreAccountId) {
        this.ordergoodsStoreAccountId = ordergoodsStoreAccountId;
    }

    public Integer getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Integer webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Integer getOrdergoodsStoreId() {
        return ordergoodsStoreId;
    }

    public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
        this.ordergoodsStoreId = ordergoodsStoreId;
    }

    public Integer getTraderContactId() {
        return traderContactId;
    }

    public void setTraderContactId(Integer traderContactId) {
        this.traderContactId = traderContactId;
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

	public Integer getTraderAddressId() {
		return traderAddressId;
	}

	public void setTraderAddressId(Integer traderAddressId) {
		this.traderAddressId = traderAddressId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}