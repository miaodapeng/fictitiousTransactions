package com.vedeng.aftersales.model.vo;

import com.vedeng.aftersales.model.AfterSalesRecord;

public class AfterSalesRecordVo extends AfterSalesRecord {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String optName;// 操作人

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

}
