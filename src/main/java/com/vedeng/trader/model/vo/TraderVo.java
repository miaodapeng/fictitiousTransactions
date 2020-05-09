package com.vedeng.trader.model.vo;

import java.util.List;

import com.vedeng.trader.model.Trader;

public class TraderVo extends Trader {
	
	private Integer traderType;
	
	private Integer verifyStatus;
	
	private String area;
	
	private String owner;
	
	private List<Integer> traderCustomerIds;
	
	private List<Integer> traderSupplierIds;
	

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<Integer> getTraderCustomerIds() {
		return traderCustomerIds;
	}

	public void setTraderCustomerIds(List<Integer> traderCustomerIds) {
		this.traderCustomerIds = traderCustomerIds;
	}

	public List<Integer> getTraderSupplierIds() {
		return traderSupplierIds;
	}

	public void setTraderSupplierIds(List<Integer> traderSupplierIds) {
		this.traderSupplierIds = traderSupplierIds;
	}


}
