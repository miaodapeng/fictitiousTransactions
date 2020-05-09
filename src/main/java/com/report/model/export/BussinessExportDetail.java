package com.report.model.export;

import java.math.BigDecimal;

public class BussinessExportDetail {
	
	private Integer companyId;
	
	private Integer bussinessChanceId;
	
	private Integer customerNature,areaId,orgId,traderId,status,creator;
	
	private String bussinessChanceNo,traderName,traderContactName,mobile,telephone,otherContact,comments,content,areaIds;
	
	private Integer quoteorderId,quoteCreator,quoteNum;
	
	private String quoteorderNo,goodsCategory,goodsBrand,goodsName,quoteValidTime;
	
	private Integer saleorderId,saleCreator,saleNum,saleStatus;
	
	private String saleorderNo,saleAddTimeStr,saleValidTimeStr,firstPayTimeStr,lastTimeEndStr,satisfyDeliveryTimeStr,statusCommentsStr;
	
	private BigDecimal quoteTotalAmount,saleTotalAmount;
	
	private String statusComments,closedComments,firstViewTimeStr,assignTimeStr,responseMinute;
	
	private String addTimeStr,receiveTimeStr,typeStr,sourceStr,communicationStr,paymentTypeStr,modTimeStr,goodsCategoryStr,quoteValidTimeStr;
	
	private Long startTime,endTime;

	private String creatorNm,quoteCreatorNm,saleCreatorNm,optUserNm,orgNm,provinceStr,cityStr,areaStr;
	
	
	
	public String getQuoteValidTimeStr() {
		return quoteValidTimeStr;
	}

	public void setQuoteValidTimeStr(String quoteValidTimeStr) {
		this.quoteValidTimeStr = quoteValidTimeStr;
	}

	public String getGoodsCategoryStr() {
		return goodsCategoryStr;
	}

	public void setGoodsCategoryStr(String goodsCategoryStr) {
		this.goodsCategoryStr = goodsCategoryStr;
	}

	public String getStatusCommentsStr() {
		return statusCommentsStr;
	}

	public void setStatusCommentsStr(String statusCommentsStr) {
		this.statusCommentsStr = statusCommentsStr;
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

	public String getAreaStr() {
		return areaStr;
	}

	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getModTimeStr() {
		return modTimeStr;
	}

	public void setModTimeStr(String modTimeStr) {
		this.modTimeStr = modTimeStr;
	}

	public BigDecimal getSaleTotalAmount() {
		return saleTotalAmount;
	}

	public void setSaleTotalAmount(BigDecimal saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}

	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}

	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getOptUserNm() {
		return optUserNm;
	}

	public void setOptUserNm(String optUserNm) {
		this.optUserNm = optUserNm;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getQuoteCreatorNm() {
		return quoteCreatorNm;
	}

	public void setQuoteCreatorNm(String quoteCreatorNm) {
		this.quoteCreatorNm = quoteCreatorNm;
	}

	public String getSaleCreatorNm() {
		return saleCreatorNm;
	}

	public void setSaleCreatorNm(String saleCreatorNm) {
		this.saleCreatorNm = saleCreatorNm;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getBussinessChanceId() {
		return bussinessChanceId;
	}

	public void setBussinessChanceId(Integer bussinessChanceId) {
		this.bussinessChanceId = bussinessChanceId;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getTraderContactName() {
		return traderContactName;
	}

	public void setTraderContactName(String traderContactName) {
		this.traderContactName = traderContactName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOtherContact() {
		return otherContact;
	}

	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuoteorderId() {
		return quoteorderId;
	}

	public void setQuoteorderId(Integer quoteorderId) {
		this.quoteorderId = quoteorderId;
	}

	public Integer getQuoteCreator() {
		return quoteCreator;
	}

	public void setQuoteCreator(Integer quoteCreator) {
		this.quoteCreator = quoteCreator;
	}

	public Integer getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}

	public String getQuoteorderNo() {
		return quoteorderNo;
	}

	public void setQuoteorderNo(String quoteorderNo) {
		this.quoteorderNo = quoteorderNo;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getGoodsBrand() {
		return goodsBrand;
	}

	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getQuoteValidTime() {
		return quoteValidTime;
	}

	public void setQuoteValidTime(String quoteValidTime) {
		this.quoteValidTime = quoteValidTime;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getSaleCreator() {
		return saleCreator;
	}

	public void setSaleCreator(Integer saleCreator) {
		this.saleCreator = saleCreator;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public String getSaleAddTimeStr() {
		return saleAddTimeStr;
	}

	public void setSaleAddTimeStr(String saleAddTimeStr) {
		this.saleAddTimeStr = saleAddTimeStr;
	}

	public String getSaleValidTimeStr() {
		return saleValidTimeStr;
	}

	public void setSaleValidTimeStr(String saleValidTimeStr) {
		this.saleValidTimeStr = saleValidTimeStr;
	}

	public String getFirstPayTimeStr() {
		return firstPayTimeStr;
	}

	public void setFirstPayTimeStr(String firstPayTimeStr) {
		this.firstPayTimeStr = firstPayTimeStr;
	}

	public String getLastTimeEndStr() {
		return lastTimeEndStr;
	}

	public void setLastTimeEndStr(String lastTimeEndStr) {
		this.lastTimeEndStr = lastTimeEndStr;
	}

	public String getSatisfyDeliveryTimeStr() {
		return satisfyDeliveryTimeStr;
	}

	public void setSatisfyDeliveryTimeStr(String satisfyDeliveryTimeStr) {
		this.satisfyDeliveryTimeStr = satisfyDeliveryTimeStr;
	}

	public BigDecimal getQuoteTotalAmount() {
		return quoteTotalAmount;
	}

	public void setQuoteTotalAmount(BigDecimal quoteTotalAmount) {
		this.quoteTotalAmount = quoteTotalAmount;
	}

	public String getStatusComments() {
		return statusComments;
	}

	public void setStatusComments(String statusComments) {
		this.statusComments = statusComments;
	}

	public String getClosedComments() {
		return closedComments;
	}

	public void setClosedComments(String closedComments) {
		this.closedComments = closedComments;
	}

	public String getFirstViewTimeStr() {
		return firstViewTimeStr;
	}

	public void setFirstViewTimeStr(String firstViewTimeStr) {
		this.firstViewTimeStr = firstViewTimeStr;
	}

	public String getAssignTimeStr() {
		return assignTimeStr;
	}

	public void setAssignTimeStr(String assignTimeStr) {
		this.assignTimeStr = assignTimeStr;
	}

	public String getResponseMinute() {
		return responseMinute;
	}

	public void setResponseMinute(String responseMinute) {
		this.responseMinute = responseMinute;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getReceiveTimeStr() {
		return receiveTimeStr;
	}

	public void setReceiveTimeStr(String receiveTimeStr) {
		this.receiveTimeStr = receiveTimeStr;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public String getCommunicationStr() {
		return communicationStr;
	}

	public void setCommunicationStr(String communicationStr) {
		this.communicationStr = communicationStr;
	}

}
