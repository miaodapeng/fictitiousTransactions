package com.vedeng.ordergoods.model.vo;

import java.util.List;

import com.vedeng.ordergoods.model.OrdergoodsGoodsAccount;

public class OrdergoodsGoodsAccountVo extends OrdergoodsGoodsAccount{
	private String sku;

    private String goodsName;

    private String model;
    
    private String brandName;

    private List<Integer> goodsIds;
    
    private Integer companyId;
    
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<Integer> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<Integer> goodsIds) {
		this.goodsIds = goodsIds;
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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
    
    
}
