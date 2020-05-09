package com.vedeng.ordergoods.model;

import java.io.Serializable;

public class ROrdergoodsLaunchGoodsJCategory implements Serializable{
    private Integer rOrdergoodsLaunchGoodsJCategoryId;
    
    private Integer ordergoodsStoreId;

    private Integer goodsId;

    private String ordergoodsCategoryIds;

    public Integer getrOrdergoodsLaunchGoodsJCategoryId() {
        return rOrdergoodsLaunchGoodsJCategoryId;
    }

    public void setrOrdergoodsLaunchGoodsJCategoryId(Integer rOrdergoodsLaunchGoodsJCategoryId) {
        this.rOrdergoodsLaunchGoodsJCategoryId = rOrdergoodsLaunchGoodsJCategoryId;
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
        this.ordergoodsCategoryIds = ordergoodsCategoryIds == null ? null : ordergoodsCategoryIds.trim();
    }

	public Integer getOrdergoodsStoreId() {
		return ordergoodsStoreId;
	}

	public void setOrdergoodsStoreId(Integer ordergoodsStoreId) {
		this.ordergoodsStoreId = ordergoodsStoreId;
	}
}