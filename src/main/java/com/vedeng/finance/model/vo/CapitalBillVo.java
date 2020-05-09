package com.vedeng.finance.model.vo;

import java.math.BigDecimal;

import com.vedeng.finance.model.CapitalBill;

public class CapitalBillVo extends CapitalBill {
	private Integer bussinessType;
	
	private Integer orderType;
	
	private String orderNo;
	
	private Integer relatedId;
	
	private BigDecimal paymentAmount;//订单已付款金额 含账期

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	
}
