package com.vedeng.common.constant.goods;

public enum GoodsLevelEnum {
	TEMP(2), CORE(1), OTHER(0);
	private int goodsLevel;

	private GoodsLevelEnum(int goodsLevel) {
		this.goodsLevel = goodsLevel;
	}

	public int goodsLevel() {
		return goodsLevel;
	}
}
