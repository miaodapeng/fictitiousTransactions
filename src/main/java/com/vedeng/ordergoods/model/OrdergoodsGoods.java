package com.vedeng.ordergoods.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrdergoodsGoods implements Serializable{
    private Integer ordergoodsGoodsId;

    private Integer ordergoodsStoreId;

    private Integer goodsId;

    private Integer saleAreaId;

    private String supplyCompany;

    private String unit;

    private BigDecimal costPrice;

    private BigDecimal marketPrice;

    private BigDecimal retailPrice;

    private BigDecimal agencyPrice1;

    private BigDecimal agencyPrice2;

    private String spec;

    private String comments;

    private String used;

    private String testMethod;

    private Long addTime;

    private Integer creator;
    
    private Integer transportRequirements;

    public Integer getOrdergoodsGoodsId() {
        return ordergoodsGoodsId;
    }

    public void setOrdergoodsGoodsId(Integer ordergoodsGoodsId) {
        this.ordergoodsGoodsId = ordergoodsGoodsId;
    }

    public Integer getOrdergoodsStoreId() {
        return ordergoodsStoreId;
    }

    public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
        this.ordergoodsStoreId = ordergoodsStoreId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSaleAreaId() {
        return saleAreaId;
    }

    public void setSaleAreaId(Integer saleAreaId) {
        this.saleAreaId = saleAreaId;
    }

    public String getSupplyCompany() {
        return supplyCompany;
    }

    public void setSupplyCompany(String supplyCompany) {
        this.supplyCompany = supplyCompany == null ? null : supplyCompany.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getAgencyPrice1() {
        return agencyPrice1;
    }

    public void setAgencyPrice1(BigDecimal agencyPrice1) {
        this.agencyPrice1 = agencyPrice1;
    }

    public BigDecimal getAgencyPrice2() {
        return agencyPrice2;
    }

    public void setAgencyPrice2(BigDecimal agencyPrice2) {
        this.agencyPrice2 = agencyPrice2;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used == null ? null : used.trim();
    }

    public String getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(String testMethod) {
        this.testMethod = testMethod == null ? null : testMethod.trim();
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

	public Integer getTransportRequirements() {
		return transportRequirements;
	}

	public void setTransportRequirements(Integer transportRequirements) {
		this.transportRequirements = transportRequirements;
	}
}