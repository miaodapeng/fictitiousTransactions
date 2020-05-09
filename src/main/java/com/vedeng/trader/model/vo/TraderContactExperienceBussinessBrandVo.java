package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.TraderContactExperienceBussinessBrand;

public class TraderContactExperienceBussinessBrandVo extends TraderContactExperienceBussinessBrand {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private String brandName;//品牌名称

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
