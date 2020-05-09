package com.vedeng.common.util;

import com.vedeng.common.constant.goods.GoodsConstants;

public class GoodsNoDict {
	public static String getSpuNo(Integer spuId) {
		StringBuilder sb = new StringBuilder();
		sb.append(GoodsConstants.SPU_NO_PRE);

		sb.append( spuId);
		return sb.toString();
	}

	public static String getSkuNo(Integer spuId) {
		StringBuilder sb = new StringBuilder();
		sb.append(GoodsConstants.SKU_NO_PRE);

		 sb.append(spuId);
		return sb.toString();
	}
}
