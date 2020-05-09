package com.report.model.export;

import java.math.BigDecimal;

public class SaleOrderExportDetail {
	
	private Integer companyId,saleorderId;
	
	private String saleorderNo,customerTraderName;
	
	private Integer traderId,customerNature,deliveryDirect,invoiceType,invoiceStatus,orderDeliveryStatus,paymentStatus;
	
	private String validTimeStr,takeTraderArea,invoiceTypeStr,rate,firstExpressTimeStr;
	
	private BigDecimal totalAmount,salePrice,buyPrice,expressAmount,goodsInvoiceAmount,lastAmount;
	
	private Integer goodsId,saleNum,buyNum;
	
	private String goodsName,sku,brandName,model,materialCode,unitName;
	
	private Integer goodsDeliveryStatus,arrivalStatus,expressNum,goodsInvoiceNum;
	
	private String insideComments,buyorderNo,lastPayTimeStr,supplyTraderName,arrivalTimeStr;

	private BigDecimal paymentAmount,accountPeriodAmount;
	
	private String optUserNm,orgNm;
	
	
	
	public String getFirstExpressTimeStr() {
		return firstExpressTimeStr;
	}

	public void setFirstExpressTimeStr(String firstExpressTimeStr) {
		this.firstExpressTimeStr = firstExpressTimeStr;
	}

	public String getCustomerTraderName() {
		return customerTraderName;
	}

	public void setCustomerTraderName(String customerTraderName) {
		this.customerTraderName = customerTraderName;
	}

	public String getOptUserNm() {
		return optUserNm;
	}

	public void setOptUserNm(String optUserNm) {
		this.optUserNm = optUserNm;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getAccountPeriodAmount() {
		return accountPeriodAmount;
	}

	public void setAccountPeriodAmount(BigDecimal accountPeriodAmount) {
		this.accountPeriodAmount = accountPeriodAmount;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getDeliveryDirect() {
		return deliveryDirect;
	}

	public void setDeliveryDirect(Integer deliveryDirect) {
		this.deliveryDirect = deliveryDirect;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Integer getOrderDeliveryStatus() {
		return orderDeliveryStatus;
	}

	public void setOrderDeliveryStatus(Integer orderDeliveryStatus) {
		this.orderDeliveryStatus = orderDeliveryStatus;
	}

	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getValidTimeStr() {
		return validTimeStr;
	}

	public void setValidTimeStr(String validTimeStr) {
		this.validTimeStr = validTimeStr;
	}

	public String getTakeTraderArea() {
		return takeTraderArea;
	}

	public void setTakeTraderArea(String takeTraderArea) {
		this.takeTraderArea = takeTraderArea;
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

	public BigDecimal getExpressAmount() {
		return expressAmount;
	}

	public void setExpressAmount(BigDecimal expressAmount) {
		this.expressAmount = expressAmount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public BigDecimal getGoodsInvoiceAmount() {
		return goodsInvoiceAmount;
	}

	public void setGoodsInvoiceAmount(BigDecimal goodsInvoiceAmount) {
		this.goodsInvoiceAmount = goodsInvoiceAmount;
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

	public Integer getGoodsDeliveryStatus() {
		return goodsDeliveryStatus;
	}

	public void setGoodsDeliveryStatus(Integer goodsDeliveryStatus) {
		this.goodsDeliveryStatus = goodsDeliveryStatus;
	}

	public Integer getArrivalStatus() {
		return arrivalStatus;
	}

	public void setArrivalStatus(Integer arrivalStatus) {
		this.arrivalStatus = arrivalStatus;
	}

	public Integer getExpressNum() {
		return expressNum;
	}

	public void setExpressNum(Integer expressNum) {
		this.expressNum = expressNum;
	}

	public Integer getGoodsInvoiceNum() {
		return goodsInvoiceNum;
	}

	public void setGoodsInvoiceNum(Integer goodsInvoiceNum) {
		this.goodsInvoiceNum = goodsInvoiceNum;
	}

	public String getInsideComments() {
		return insideComments;
	}

	public void setInsideComments(String insideComments) {
		this.insideComments = insideComments;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getLastPayTimeStr() {
		return lastPayTimeStr;
	}

	public void setLastPayTimeStr(String lastPayTimeStr) {
		this.lastPayTimeStr = lastPayTimeStr;
	}

	public BigDecimal getLastAmount() {
		return lastAmount;
	}

	public void setLastAmount(BigDecimal lastAmount) {
		this.lastAmount = lastAmount;
	}

	public String getSupplyTraderName() {
		return supplyTraderName;
	}

	public void setSupplyTraderName(String supplyTraderName) {
		this.supplyTraderName = supplyTraderName;
	}

	public String getArrivalTimeStr() {
		return arrivalTimeStr;
	}

	public void setArrivalTimeStr(String arrivalTimeStr) {
		this.arrivalTimeStr = arrivalTimeStr;
	}
	
}
