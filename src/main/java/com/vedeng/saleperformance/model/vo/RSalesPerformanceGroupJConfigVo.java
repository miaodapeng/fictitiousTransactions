package com.vedeng.saleperformance.model.vo;

import com.vedeng.saleperformance.model.RSalesPerformanceGroupJConfig;

public class RSalesPerformanceGroupJConfigVo extends RSalesPerformanceGroupJConfig {
	private String item;

	private Integer companyId;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	
}
