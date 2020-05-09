package com.vedeng.trader.model.vo;

import com.vedeng.trader.model.WebAccount;

public class WebAccountVo extends WebAccount {
	private Integer relateStatus;
	
	private Integer assignStatus;
	
	private String relateComapnyName;
	
	private String maybeSaler;
	
	private String owner;
	
	private Integer traderCustomerId;

	private Long startJoinDate,endJoinDate;

	private Long startMemberDate,endMemberDate;

	private Integer userTraderId;

	public Long getStartMemberDate() {
		return startMemberDate;
	}

	public void setStartMemberDate(Long startMemberDate) {
		this.startMemberDate = startMemberDate;
	}

	public Long getEndMemberDate() {
		return endMemberDate;
	}

	public void setEndMemberDate(Long endMemberDate) {
		this.endMemberDate = endMemberDate;
	}

	public TraderCustomerVo getTraderCustomerVo() {
		return traderCustomerVo;
	}

	public void setTraderCustomerVo(TraderCustomerVo traderCustomerVo) {
		this.traderCustomerVo = traderCustomerVo;
	}

	private TraderCustomerVo traderCustomerVo;


	public Long getStartJoinDate() {
		return startJoinDate;
	}

	public void setStartJoinDate(Long startJoinDate) {
		this.startJoinDate = startJoinDate;
	}

	public Long getEndJoinDate() {
		return endJoinDate;
	}

	public void setEndJoinDate(Long endJoinDate) {
		this.endJoinDate = endJoinDate;
	}

	public Integer getRelateStatus() {
		return relateStatus;
	}

	public void setRelateStatus(Integer relateStatus) {
		this.relateStatus = relateStatus;
	}

	public Integer getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(Integer assignStatus) {
		this.assignStatus = assignStatus;
	}

	public String getRelateComapnyName() {
		return relateComapnyName;
	}

	public void setRelateComapnyName(String relateComapnyName) {
		this.relateComapnyName = relateComapnyName;
	}

	public String getMaybeSaler() {
		return maybeSaler;
	}

	public void setMaybeSaler(String maybeSaler) {
		this.maybeSaler = maybeSaler;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getTraderCustomerId() {
		return traderCustomerId;
	}

	public void setTraderCustomerId(Integer traderCustomerId) {
		this.traderCustomerId = traderCustomerId;
	}

	public Integer getUserTraderId() {
		return userTraderId;
	}

	public void setUserTraderId(Integer userTraderId) {
		this.userTraderId = userTraderId;
	}
}
