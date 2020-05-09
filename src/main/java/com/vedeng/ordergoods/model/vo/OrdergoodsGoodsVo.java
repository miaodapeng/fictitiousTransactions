package com.vedeng.ordergoods.model.vo;

import java.util.List;

import com.vedeng.ordergoods.model.OrdergoodsGoods;

public class OrdergoodsGoodsVo extends OrdergoodsGoods {
    private String sku;

    private String goodsName;

    private String model;

    private String materialCode;

    private String brandName;
    
    private Integer isOnSale;
    
    private Integer isDiscard;
    
    private Integer companyId;
    
    private Integer sort;
    
    private List<Integer> goodsIds;
    
    private Integer ordergoodsCategoryId;
    
    private Integer rOrdergoodsGoodsJCategoryId;
    
    private String searchContent;
    
    private Integer isValid;

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

	public Integer getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Integer isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Integer getIsDiscard() {
		return isDiscard;
	}

	public void setIsDiscard(Integer isDiscard) {
		this.isDiscard = isDiscard;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Integer> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<Integer> goodsIds) {
		this.goodsIds = goodsIds;
	}

	public Integer getOrdergoodsCategoryId() {
		return ordergoodsCategoryId;
	}

	public void setOrdergoodsCategoryId(Integer ordergoodsCategoryId) {
		this.ordergoodsCategoryId = ordergoodsCategoryId;
	}

	public Integer getrOrdergoodsGoodsJCategoryId() {
		return rOrdergoodsGoodsJCategoryId;
	}

	public void setrOrdergoodsGoodsJCategoryId(Integer rOrdergoodsGoodsJCategoryId) {
		this.rOrdergoodsGoodsJCategoryId = rOrdergoodsGoodsJCategoryId;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
    
    
}
