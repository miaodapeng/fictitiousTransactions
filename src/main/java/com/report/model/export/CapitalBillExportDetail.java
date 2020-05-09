package com.report.model.export;

import java.math.BigDecimal;

public class CapitalBillExportDetail {

	private Integer capitalBillId;
	
	private BigDecimal amount;
	
	private Long traderTime;
	
	private String payer,comments;
	
	private Integer creator,traderType,traderSubject,traderMode;
	
	private String traderTimeStr,traderModeStr;
	
	private Integer capitalBillDetailId,relatedId;
	
	private String orderNo,bussinessTypeStr;
	
	private Integer bussinessType,orgId,traderId,userId;
	
	private Integer saleorderId,invoiceType,paymentStatus,deliveryStatus,arrivalStatus,deliveryType,deliveryDirect,saleComments;
	
	private String traderName,invoiceTypeStr;
	
	private BigDecimal totalAmount;
	
	private Integer saleorderGoodsId,goodsId,saleNum,buyNum,expressNum;
	
	private BigDecimal salePrice,buyPrice,expressPrice;
	
	private String goodsName,sku,brandName,model,materialCode,unitName,buyOrderNo;

	private String creatorName,optUserName,salesDeptName;
	
	
	
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

	public Integer getCapitalBillId() {
		return capitalBillId;
	}

	public void setCapitalBillId(Integer capitalBillId) {
		this.capitalBillId = capitalBillId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getTraderTime() {
		return traderTime;
	}

	public void setTraderTime(Long traderTime) {
		this.traderTime = traderTime;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer == null ? null : payer.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getTraderType() {
		return traderType;
	}

	public void setTraderType(Integer traderType) {
		this.traderType = traderType;
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

	public String getTraderTimeStr() {
		return traderTimeStr;
	}

	public void setTraderTimeStr(String traderTimeStr) {
		this.traderTimeStr = traderTimeStr;
	}

	public String getTraderModeStr() {
		return traderModeStr;
	}

	public void setTraderModeStr(String traderModeStr) {
		this.traderModeStr = traderModeStr;
	}

	public Integer getCapitalBillDetailId() {
		return capitalBillDetailId;
	}

	public void setCapitalBillDetailId(Integer capitalBillDetailId) {
		this.capitalBillDetailId = capitalBillDetailId;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBussinessTypeStr() {
		return bussinessTypeStr;
	}

	public void setBussinessTypeStr(String bussinessTypeStr) {
		this.bussinessTypeStr = bussinessTypeStr;
	}

	public Integer getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(Integer bussinessType) {
		this.bussinessType = bussinessType;
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

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public Integer getSaleComments() {
		return saleComments;
	}

	public void setSaleComments(Integer saleComments) {
		this.saleComments = saleComments;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName==null ? null :traderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getSaleorderGoodsId() {
		return saleorderGoodsId;
	}

	public void setSaleorderGoodsId(Integer saleorderGoodsId) {
		this.saleorderGoodsId = saleorderGoodsId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(Integer expressNum) {
		this.expressNum = expressNum;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(BigDecimal expressPrice) {
		this.expressPrice = expressPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getBuyOrderNo() {
		return buyOrderNo;
	}

	public void setBuyOrderNo(String buyOrderNo) {
		this.buyOrderNo = buyOrderNo;
	}
	
}
