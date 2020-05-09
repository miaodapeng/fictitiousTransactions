package com.vedeng.aftersales.model.vo;

import com.vedeng.aftersales.model.AfterSalesCost;

public class AfterSalesCostVo extends AfterSalesCost{

	private String userName;
	
	private String costTypeName;
	
	private String lastModTime;
	
	public String getLastModTime() {
		return lastModTime;
	}
	public void setLastModTime(String lastModTime) {
		this.lastModTime = lastModTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCostTypeName() {
		return costTypeName;
	}
	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}
	
}
