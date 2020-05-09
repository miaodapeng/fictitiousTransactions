package com.vedeng.ordergoods.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.ordergoods.model.OrdergoodsLaunch;

public class OrdergoodsLaunchVo extends OrdergoodsLaunch {
	private Integer ordergoodsStoreId;
	
	private String traderName;
	
	private String sku;

    private String goodsName;

    private String model;

    private String materialCode;

    private String brandName;
    
    private Long startTime;

    private Long endTime;
    
    private BigDecimal goalAmount;
    
    private BigDecimal haveAmountPre;
    
    private List<Integer> goodsIds;
    
    private List<Integer> traderCustomerIds;
    
    private Integer goodsId;

    private String ordergoodsCategoryIds;
    
    private String categoryNames;
    
    private BigDecimal monthAmount;
    
    private List<BigDecimal> monthAmountList;
    
	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getGoalAmount() {
		return goalAmount;
	}

	public void setGoalAmount(BigDecimal goalAmount) {
		this.goalAmount = goalAmount;
	}

	public BigDecimal getHaveAmountPre() {
		return haveAmountPre;
	}

	public void setHaveAmountPre(BigDecimal haveAmountPre) {
		this.haveAmountPre = haveAmountPre;
	}

	public Integer getOrdergoodsStoreId() {
		return ordergoodsStoreId;
	}

	public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
		this.ordergoodsStoreId = ordergoodsStoreId;
	}

	public List<Integer> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<Integer> goodsIds) {
		this.goodsIds = goodsIds;
	}

	public List<Integer> getTraderCustomerIds() {
		return traderCustomerIds;
	}

	public void setTraderCustomerIds(List<Integer> traderCustomerIds) {
		this.traderCustomerIds = traderCustomerIds;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getOrdergoodsCategoryIds() {
		return ordergoodsCategoryIds;
	}

	public void setOrdergoodsCategoryIds(String ordergoodsCategoryIds) {
		this.ordergoodsCategoryIds = ordergoodsCategoryIds;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public BigDecimal getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}

	public List<BigDecimal> getMonthAmountList() {
		return monthAmountList;
	}

	public void setMonthAmountList(List<BigDecimal> monthAmountList) {
		this.monthAmountList = monthAmountList;
	}
}
