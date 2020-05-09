package com.report.model.export;

import java.math.BigDecimal;

public class CapitalBillExport {
	
	private Integer capitalBillId,capitalBillDetailId;
	
	private String capitalBillNo,orderNo,traderName,payer,payee,bussinessTypeStr,orderAddTimeStr;
	
	private Integer relatedId,orgId,traderId,userId,traderSubject,traderMode,orderType;
	
	private BigDecimal totalAmount,amount,OrderPaymentTotalAmount,buyAmount,lackAccountPeriodAmount;
	
	private String traderTimeStr,deliveryTimeStr,arrivalTimeStr,traderModeStr,comments,afterSalesNo;
	
	private Integer creator,deliveryStatus,arrivalStatus,businessUserId,areaId;

	private String creatorName,optUserName,salesDeptName,userName,businessUserName,provinceStr,cityStr,capitalTradeUserNm;
	
	
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getCapitalTradeUserNm() {
		return capitalTradeUserNm;
	}

	public void setCapitalTradeUserNm(String capitalTradeUserNm) {
		this.capitalTradeUserNm = capitalTradeUserNm;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee == null ? null : payee.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getAfterSalesNo() {
		return afterSalesNo;
	}

	public void setAfterSalesNo(String afterSalesNo) {
		this.afterSalesNo = afterSalesNo;
	}

	public String getProvinceStr() {
		return provinceStr;
	}

	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getOrderAddTimeStr() {
		return orderAddTimeStr;
	}

	public void setOrderAddTimeStr(String orderAddTimeStr) {
		this.orderAddTimeStr = orderAddTimeStr;
	}

	public BigDecimal getLackAccountPeriodAmount() {
		return lackAccountPeriodAmount;
	}

	public void setLackAccountPeriodAmount(BigDecimal lackAccountPeriodAmount) {
		this.lackAccountPeriodAmount = lackAccountPeriodAmount;
	}

	public String getBussinessTypeStr() {
		return bussinessTypeStr;
	}

	public void setBussinessTypeStr(String bussinessTypeStr) {
		this.bussinessTypeStr = bussinessTypeStr;
	}

	public String getCapitalBillNo() {
		return capitalBillNo;
	}

	public void setCapitalBillNo(String capitalBillNo) {
		this.capitalBillNo = capitalBillNo;
	}

	public String getBusinessUserName() {
		return businessUserName;
	}

	public void setBusinessUserName(String businessUserName) {
		this.businessUserName = businessUserName;
	}

	public Integer getBusinessUserId() {
		return businessUserId;
	}

	public void setBusinessUserId(Integer businessUserId) {
		this.businessUserId = businessUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getOptUserName() {
		return optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Integer getCapitalBillId() {
		return capitalBillId;
	}

	public void setCapitalBillId(Integer capitalBillId) {
		this.capitalBillId = capitalBillId;
	}

	public Integer getCapitalBillDetailId() {
		return capitalBillDetailId;
	}

	public void setCapitalBillDetailId(Integer capitalBillDetailId) {
		this.capitalBillDetailId = capitalBillDetailId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer == null ? null : payer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTraderSubject() {
		return traderSubject;
	}

	public void setTraderSubject(Integer traderSubject) {
		this.traderSubject = traderSubject;
	}

	public Integer getTraderMode() {
		return traderMode;
	}

	public void setTraderMode(Integer traderMode) {
		this.traderMode = traderMode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getOrderPaymentTotalAmount() {
		return OrderPaymentTotalAmount;
	}

	public void setOrderPaymentTotalAmount(BigDecimal orderPaymentTotalAmount) {
		OrderPaymentTotalAmount = orderPaymentTotalAmount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTraderTimeStr() {
		return traderTimeStr;
	}

	public void setTraderTimeStr(String traderTimeStr) {
		this.traderTimeStr = traderTimeStr;
	}

	public String getDeliveryTimeStr() {
		return deliveryTimeStr;
	}

	public void setDeliveryTimeStr(String deliveryTimeStr) {
		this.deliveryTimeStr = deliveryTimeStr;
	}

	public String getArrivalTimeStr() {
		return arrivalTimeStr;
	}

	public void setArrivalTimeStr(String arrivalTimeStr) {
		this.arrivalTimeStr = arrivalTimeStr;
	}

	public String getTraderModeStr() {
		return traderModeStr;
	}

	public void setTraderModeStr(String traderModeStr) {
		this.traderModeStr = traderModeStr;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

}
