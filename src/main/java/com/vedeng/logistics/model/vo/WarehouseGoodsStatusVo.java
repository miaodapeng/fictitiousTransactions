package com.vedeng.logistics.model.vo;

import com.vedeng.logistics.model.WarehouseGoodsStatus;

public class WarehouseGoodsStatusVo extends WarehouseGoodsStatus {
	public String wareHouseName;//仓库名称
	
	public Integer goodsNum;//商品数量

	public String getWareHouseName() {
		return wareHouseName;
	}

	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

}
