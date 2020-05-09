package com.report.model.export;

import java.math.BigDecimal;

public class QuoteExportDetail {

	private Integer companyId,quoteorderId,bussinessChanceId;
	
	private String quoteorderNo,bussinessChanceNo,traderName;
	
	private Integer traderId,customerType,customerNature,isNewCustomer,isPolicymaker;
	
	private String area,traderContactName,customerLevel,telephone,mobile,address;
	
	private Integer purchasingType,paymentTerm,purchasingTime;
	
	private String projectProgress,additionalClause,comments,saleorderNo;
	
	private Integer orgId,validStatus,followOrderStatus,verifyStatus;
	
	private BigDecimal totalAmount;
	
	private String addTimeStr,verifyTimeStr,submitVerifyTimeStr,followOrderTimeStr;
	
	private Integer quoteorderGoodsId;
	
	private Integer goodsId,quoteNum,deliveryDirect,haveInstallation,reportStatus;
	
	private String sku,goodsName,brandName,model,materialCode,deliveryCycle,goodsComments,insideComments;
	
	private BigDecimal quotePrice,goodsTotalAmount;
	
	private Integer communicateNum;
	
	private String optUserName,salesDeptName,purchasingTimeStr;

	
	
	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Integer getCommunicateNum() {
		return communicateNum;
	}

	public void setCommunicateNum(Integer communicateNum) {
		this.communicateNum = communicateNum;
	}

	public String getPurchasingTimeStr() {
		return purchasingTimeStr;
	}

	public void setPurchasingTimeStr(String purchasingTimeStr) {
		this.purchasingTimeStr = purchasingTimeStr;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getQuoteorderId() {
		return quoteorderId;
	}

	public void setQuoteorderId(Integer quoteorderId) {
		this.quoteorderId = quoteorderId;
	}

	public Integer getBussinessChanceId() {
		return bussinessChanceId;
	}

	public void setBussinessChanceId(Integer bussinessChanceId) {
		this.bussinessChanceId = bussinessChanceId;
	}

	public String getQuoteorderNo() {
		return quoteorderNo;
	}

	public void setQuoteorderNo(String quoteorderNo) {
		this.quoteorderNo = quoteorderNo;
	}

	public String getBussinessChanceNo() {
		return bussinessChanceNo;
	}

	public void setBussinessChanceNo(String bussinessChanceNo) {
		this.bussinessChanceNo = bussinessChanceNo;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getIsNewCustomer() {
		return isNewCustomer;
	}

	public void setIsNewCustomer(Integer isNewCustomer) {
		this.isNewCustomer = isNewCustomer;
	}

	public Integer getIsPolicymaker() {
		return isPolicymaker;
	}

	public void setIsPolicymaker(Integer isPolicymaker) {
		this.isPolicymaker = isPolicymaker;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTraderContactName() {
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName) {
		this.traderContactName = traderContactName;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPurchasingType() {
		return purchasingType;
	}

	public void setPurchasingType(Integer purchasingType) {
		this.purchasingType = purchasingType;
	}

	public Integer getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(Integer paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public Integer getPurchasingTime() {
		return purchasingTime;
	}

	public void setPurchasingTime(Integer purchasingTime) {
		this.purchasingTime = purchasingTime;
	}

	public String getProjectProgress() {
		return projectProgress;
	}

	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}

	public String getAdditionalClause() {
		return additionalClause;
	}

	public void setAdditionalClause(String additionalClause) {
		this.additionalClause = additionalClause;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public Integer getFollowOrderStatus() {
		return followOrderStatus;
	}

	public void setFollowOrderStatus(Integer followOrderStatus) {
		this.followOrderStatus = followOrderStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getVerifyTimeStr() {
		return verifyTimeStr;
	}

	public void setVerifyTimeStr(String verifyTimeStr) {
		this.verifyTimeStr = verifyTimeStr;
	}

	public String getSubmitVerifyTimeStr() {
		return submitVerifyTimeStr;
	}

	public void setSubmitVerifyTimeStr(String submitVerifyTimeStr) {
		this.submitVerifyTimeStr = submitVerifyTimeStr;
	}

	public String getFollowOrderTimeStr() {
		return followOrderTimeStr;
	}

	public void setFollowOrderTimeStr(String followOrderTimeStr) {
		this.followOrderTimeStr = followOrderTimeStr;
	}

	public Integer getQuoteorderGoodsId() {
		return quoteorderGoodsId;
	}

	public void setQuoteorderGoodsId(Integer quoteorderGoodsId) {
		this.quoteorderGoodsId = quoteorderGoodsId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public Integer getHaveInstallation() {
		return haveInstallation;
	}

	public void setHaveInstallation(Integer haveInstallation) {
		this.haveInstallation = haveInstallation;
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getDeliveryCycle() {
		return deliveryCycle;
	}

	public void setDeliveryCycle(String deliveryCycle) {
		this.deliveryCycle = deliveryCycle;
	}

	public String getGoodsComments() {
		return goodsComments;
	}

	public void setGoodsComments(String goodsComments) {
		this.goodsComments = goodsComments;
	}

	public String getInsideComments() {
		return insideComments;
	}

	public void setInsideComments(String insideComments) {
		this.insideComments = insideComments;
	}

	public BigDecimal getQuotePrice() {
		return quotePrice;
	}

	public void setQuotePrice(BigDecimal quotePrice) {
		this.quotePrice = quotePrice;
	}

	public BigDecimal getGoodsTotalAmount() {
		return goodsTotalAmount;
	}

	public void setGoodsTotalAmount(BigDecimal goodsTotalAmount) {
		this.goodsTotalAmount = goodsTotalAmount;
	}
	
}
