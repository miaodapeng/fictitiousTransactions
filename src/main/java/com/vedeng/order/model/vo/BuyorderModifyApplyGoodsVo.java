package com.vedeng.order.model.vo;

import com.vedeng.order.model.BuyorderModifyApplyGoods;

public class BuyorderModifyApplyGoodsVo extends BuyorderModifyApplyGoods {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String [] oldInsideCommentsArray;
	
	private String [] insideCommentsArray;

	public String[] getOldInsideCommentsArray() {
		return oldInsideCommentsArray;
	}

	public void setOldInsideCommentsArray(String[] oldInsideCommentsArray) {
		this.oldInsideCommentsArray = oldInsideCommentsArray;
	}

	public String[] getInsideCommentsArray() {
		return insideCommentsArray;
	}

	public void setInsideCommentsArray(String[] insideCommentsArray) {
		this.insideCommentsArray = insideCommentsArray;
	}

}
