package com.report.model.export;

import java.math.BigDecimal;

public class WareHouseLogExport {

	private Integer warehouseGoodsOperateLogId;
	
	private Integer orderId,num,creator,checkStatus,checkStatusUser,invoiceStatus;
	
	private String addTimeStr,orderNo,sku,goodsName,brandName,model,materialCode,unitName,sendTraderName,barcode,barcodeFactory;
	
	private String expirationDateStr,batchNumber,storageAddress,invoiceNo,invoiceTypeStr,invoiceAddTimeStr,comments,sequence;
	
	private BigDecimal price,invoiceAmount;
	
	private Integer colorType,isEnable;
	
	private String creatorNm,checkStatusUserNm,invoiceColorTypeStr;

	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getInvoiceColorTypeStr() {
		return invoiceColorTypeStr;
	}

	public void setInvoiceColorTypeStr(String invoiceColorTypeStr) {
		this.invoiceColorTypeStr = invoiceColorTypeStr;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreatorNm() {
		return creatorNm;
	}

	public void setCreatorNm(String creatorNm) {
		this.creatorNm = creatorNm;
	}

	public String getCheckStatusUserNm() {
		return checkStatusUserNm;
	}

	public void setCheckStatusUserNm(String checkStatusUserNm) {
		this.checkStatusUserNm = checkStatusUserNm;
	}

	public Integer getWarehouseGoodsOperateLogId() {
		return warehouseGoodsOperateLogId;
	}

	public void setWarehouseGoodsOperateLogId(Integer warehouseGoodsOperateLogId) {
		this.warehouseGoodsOperateLogId = warehouseGoodsOperateLogId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getCheckStatusUser() {
		return checkStatusUser;
	}

	public void setCheckStatusUser(Integer checkStatusUser) {
		this.checkStatusUser = checkStatusUser;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getAddTimeStr() {
		return addTimeStr;
	}

	public void setAddTimeStr(String addTimeStr) {
		this.addTimeStr = addTimeStr;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSendTraderName() {
		return sendTraderName;
	}

	public void setSendTraderName(String sendTraderName) {
		this.sendTraderName = sendTraderName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeFactory() {
		return barcodeFactory;
	}

	public void setBarcodeFactory(String barcodeFactory) {
		this.barcodeFactory = barcodeFactory;
	}

	public String getExpirationDateStr() {
		return expirationDateStr;
	}

	public void setExpirationDateStr(String expirationDateStr) {
		this.expirationDateStr = expirationDateStr;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getStorageAddress() {
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceTypeStr() {
		return invoiceTypeStr;
	}

	public void setInvoiceTypeStr(String invoiceTypeStr) {
		this.invoiceTypeStr = invoiceTypeStr;
	}

	public String getInvoiceAddTimeStr() {
		return invoiceAddTimeStr;
	}

	public void setInvoiceAddTimeStr(String invoiceAddTimeStr) {
		this.invoiceAddTimeStr = invoiceAddTimeStr;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Integer getColorType() {
		return colorType;
	}

	public void setColorType(Integer colorType) {
		this.colorType = colorType;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	
}
