package com.vedeng.goods.model;

import java.io.Serializable;

public class GoodsSafeStock implements Serializable{
    private Integer goodsSafeStockId;

    private Integer companyId;

    private Integer goodsId;

    private Integer num;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    private String sku;

    public Integer getGoodsSafeStockId() {
        return goodsSafeStockId;
    }

    public void setGoodsSafeStockId(Integer goodsSafeStockId) {
        this.goodsSafeStockId = goodsSafeStockId;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}