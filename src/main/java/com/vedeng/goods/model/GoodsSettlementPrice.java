package com.vedeng.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsSettlementPrice implements Serializable{
	
    private Integer goodsSettlementPriceId;

    private Integer companyId;

    private Integer goodsId;

    private BigDecimal settlementPrice;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;
    
    //-----------------------------------------------------------------------
    
    private String sku;//订货号
    
    private String goodsLevel;//产品级别
    
    private String username;//创建者用户名
    
    
    
    public String getSku() {
    	return sku;
    }
    
    public void setSku(String sku) {
    	this.sku = sku;
    }
    
    public String getGoodsLevel() {
    	return goodsLevel;
    }
    
    public void setGoodsLevel(String goodsLevel) {
    	this.goodsLevel = goodsLevel;
    }
    //-----------------------------------------------------------------------

	public Integer getGoodsSettlementPriceId() {
        return goodsSettlementPriceId;
    }

    public void setGoodsSettlementPriceId(Integer goodsSettlementPriceId) {
        this.goodsSettlementPriceId = goodsSettlementPriceId;
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

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}