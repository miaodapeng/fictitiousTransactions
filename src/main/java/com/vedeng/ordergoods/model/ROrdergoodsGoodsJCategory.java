package com.vedeng.ordergoods.model;

import java.io.Serializable;

public class ROrdergoodsGoodsJCategory implements Serializable{
    private Integer rOrdergoodsGoodsJCategoryId;

    private Integer ordergoodsGoodsId;

    private Integer ordergoodsCategoryId;

    private Integer sort;
    
    private Integer goodsId;

    public Integer getrOrdergoodsGoodsJCategoryId() {
        return rOrdergoodsGoodsJCategoryId;
    }

    public void setrOrdergoodsGoodsJCategoryId(Integer rOrdergoodsGoodsJCategoryId) {
        this.rOrdergoodsGoodsJCategoryId = rOrdergoodsGoodsJCategoryId;
    }

    public Integer getOrdergoodsGoodsId() {
        return ordergoodsGoodsId;
    }

    public void setOrdergoodsGoodsId(Integer ordergoodsGoodsId) {
        this.ordergoodsGoodsId = ordergoodsGoodsId;
    }

    public Integer getOrdergoodsCategoryId() {
        return ordergoodsCategoryId;
    }

    public void setOrdergoodsCategoryId(Integer ordergoodsCategoryId) {
        this.ordergoodsCategoryId = ordergoodsCategoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
}