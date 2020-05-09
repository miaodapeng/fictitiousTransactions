package com.vedeng.trader.model.vo;

import java.io.Serializable;

import com.vedeng.trader.model.TraderAddress;

public class TraderAddressVo implements Serializable{

	private TraderAddress traderAddress;
	
	private String area;//对应中文省市区

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public TraderAddress getTraderAddress() {
		return traderAddress;
	}

	public void setTraderAddress(TraderAddress traderAddress) {
		this.traderAddress = traderAddress;
	}
	
}
