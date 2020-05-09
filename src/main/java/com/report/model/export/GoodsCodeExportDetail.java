package com.report.model.export;

import java.math.BigDecimal;

public class GoodsCodeExportDetail {

	private String code,addtTimeStr,goodsStatusStr;
	
	private Integer companyId,inWarehouseLogId,buyValidStatus,buyArrivalStatus,barcodeId;
	
	private Integer outWarehouseLogId,saleDeliveryStatus,saleArrivalStatus;
	
	private Integer goodsId,categoryId;
	
	private String sku,goodsName,brandName,model,categoryName,unitName;
	
	private Integer buyorderId,buyUserId,buyOrgId,buyPaymentType,storageLocationId;
	
	private String buyorderNo,buyValidTimeStr,supplierTraderName,buyFirstPayTimeStr,buyLastTimeEndStr,inCheckStatusTimeStr,buyPaymentTypeStr,storageLocationName;
	
	private BigDecimal buyPrice,salePrice;
	
	private Integer saleorderId,saleDeliveryDirect,saleUserId,saleOrgId,customerNature,customerType,customerTraderId,isNewCustomer;
	
	private String saleorderNo,outRecheckStatusTimeStr,saleArrivalTimeStr,saleTraderName,customerTagName;
	
	private Integer invoiceStatus,haveInstallation;
	
	private String customerLevelStr,takeTraderName,takeTraderArea,invoiceTypeStr,invoiceTimeStr,goodsSn;
	
	private String buyUserNm,optUserNm,optUserOrgNm,buyOrgNm,saleUserNm,saleOrgNm,traderUserNm,traderUserOrgNm;
	
	private Long startTime,endTime;

	

	public String getGoodsStatusStr() {
		return goodsStatusStr;
	}

	public void setGoodsStatusStr(String goodsStatusStr) {
		this.goodsStatusStr = goodsStatusStr;
	}

	public Integer getHaveInstallation() {
		return haveInstallation;
	}

	public void setHaveInstallation(Integer haveInstallation) {
		this.haveInstallation = haveInstallation;
	}

	public String getTraderUserOrgNm() {
		return traderUserOrgNm;
	}

	public void setTraderUserOrgNm(String traderUserOrgNm) {
		this.traderUserOrgNm = traderUserOrgNm;
	}

	public String getTraderUserNm() {
		return traderUserNm;
	}

	public void setTraderUserNm(String traderUserNm) {
		this.traderUserNm = traderUserNm;
	}

	public String getStorageLocationName() {
		return storageLocationName;
	}

	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}

	public String getAddtTimeStr() {
		return addtTimeStr;
	}

	public void setAddtTimeStr(String addtTimeStr) {
		this.addtTimeStr = addtTimeStr;
	}

	public String getSaleOrgNm() {
		return saleOrgNm;
	}

	public void setSaleOrgNm(String saleOrgNm) {
		this.saleOrgNm = saleOrgNm;
	}

	public String getSaleUserNm() {
		return saleUserNm;
	}

	public void setSaleUserNm(String saleUserNm) {
		this.saleUserNm = saleUserNm;
	}

	public String getBuyOrgNm() {
		return buyOrgNm;
	}

	public void setBuyOrgNm(String buyOrgNm) {
		this.buyOrgNm = buyOrgNm;
	}

	public String getOptUserOrgNm() {
		return optUserOrgNm;
	}

	public void setOptUserOrgNm(String optUserOrgNm) {
		this.optUserOrgNm = optUserOrgNm;
	}

	public String getOptUserNm() {
		return optUserNm;
	}

	public void setOptUserNm(String optUserNm) {
		this.optUserNm = optUserNm;
	}

	public String getBuyUserNm() {
		return buyUserNm;
	}

	public void setBuyUserNm(String buyUserNm) {
		this.buyUserNm = buyUserNm;
	}

	public Integer getIsNewCustomer() {
		return isNewCustomer;
	}

	public void setIsNewCustomer(Integer isNewCustomer) {
		this.isNewCustomer = isNewCustomer;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getInWarehouseLogId() {
		return inWarehouseLogId;
	}

	public void setInWarehouseLogId(Integer inWarehouseLogId) {
		this.inWarehouseLogId = inWarehouseLogId;
	}

	public Integer getBuyValidStatus() {
		return buyValidStatus;
	}

	public void setBuyValidStatus(Integer buyValidStatus) {
		this.buyValidStatus = buyValidStatus;
	}

	public Integer getBuyArrivalStatus() {
		return buyArrivalStatus;
	}

	public void setBuyArrivalStatus(Integer buyArrivalStatus) {
		this.buyArrivalStatus = buyArrivalStatus;
	}

	public Integer getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(Integer barcodeId) {
		this.barcodeId = barcodeId;
	}

	public Integer getOutWarehouseLogId() {
		return outWarehouseLogId;
	}

	public void setOutWarehouseLogId(Integer outWarehouseLogId) {
		this.outWarehouseLogId = outWarehouseLogId;
	}

	public Integer getSaleDeliveryStatus() {
		return saleDeliveryStatus;
	}

	public void setSaleDeliveryStatus(Integer saleDeliveryStatus) {
		this.saleDeliveryStatus = saleDeliveryStatus;
	}

	public Integer getSaleArrivalStatus() {
		return saleArrivalStatus;
	}

	public void setSaleArrivalStatus(Integer saleArrivalStatus) {
		this.saleArrivalStatus = saleArrivalStatus;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getBuyorderId() {
		return buyorderId;
	}

	public void setBuyorderId(Integer buyorderId) {
		this.buyorderId = buyorderId;
	}

	public Integer getBuyUserId() {
		return buyUserId;
	}

	public void setBuyUserId(Integer buyUserId) {
		this.buyUserId = buyUserId;
	}

	public Integer getBuyOrgId() {
		return buyOrgId;
	}

	public void setBuyOrgId(Integer buyOrgId) {
		this.buyOrgId = buyOrgId;
	}

	public Integer getBuyPaymentType() {
		return buyPaymentType;
	}

	public void setBuyPaymentType(Integer buyPaymentType) {
		this.buyPaymentType = buyPaymentType;
	}

	public Integer getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(Integer storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	public String getBuyorderNo() {
		return buyorderNo;
	}

	public void setBuyorderNo(String buyorderNo) {
		this.buyorderNo = buyorderNo;
	}

	public String getBuyValidTimeStr() {
		return buyValidTimeStr;
	}

	public void setBuyValidTimeStr(String buyValidTimeStr) {
		this.buyValidTimeStr = buyValidTimeStr;
	}

	public String getSupplierTraderName() {
		return supplierTraderName;
	}

	public void setSupplierTraderName(String supplierTraderName) {
		this.supplierTraderName = supplierTraderName;
	}

	public String getBuyFirstPayTimeStr() {
		return buyFirstPayTimeStr;
	}

	public void setBuyFirstPayTimeStr(String buyFirstPayTimeStr) {
		this.buyFirstPayTimeStr = buyFirstPayTimeStr;
	}

	public String getBuyLastTimeEndStr() {
		return buyLastTimeEndStr;
	}

	public void setBuyLastTimeEndStr(String buyLastTimeEndStr) {
		this.buyLastTimeEndStr = buyLastTimeEndStr;
	}

	public String getInCheckStatusTimeStr() {
		return inCheckStatusTimeStr;
	}

	public void setInCheckStatusTimeStr(String inCheckStatusTimeStr) {
		this.inCheckStatusTimeStr = inCheckStatusTimeStr;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getSaleorderId() {
		return saleorderId;
	}

	public void setSaleorderId(Integer saleorderId) {
		this.saleorderId = saleorderId;
	}

	public Integer getSaleDeliveryDirect() {
		return saleDeliveryDirect;
	}

	public void setSaleDeliveryDirect(Integer saleDeliveryDirect) {
		this.saleDeliveryDirect = saleDeliveryDirect;
	}

	public Integer getSaleUserId() {
		return saleUserId;
	}

	public void setSaleUserId(Integer saleUserId) {
		this.saleUserId = saleUserId;
	}

	public Integer getSaleOrgId() {
		return saleOrgId;
	}

	public void setSaleOrgId(Integer saleOrgId) {
		this.saleOrgId = saleOrgId;
	}

	public Integer getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(Integer customerNature) {
		this.customerNature = customerNature;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Integer getCustomerTraderId() {
		return customerTraderId;
	}

	public void setCustomerTraderId(Integer customerTraderId) {
		this.customerTraderId = customerTraderId;
	}

	public String getSaleorderNo() {
		return saleorderNo;
	}

	public void setSaleorderNo(String saleorderNo) {
		this.saleorderNo = saleorderNo;
	}

	public String getOutRecheckStatusTimeStr() {
		return outRecheckStatusTimeStr;
	}

	public void setOutRecheckStatusTimeStr(String outRecheckStatusTimeStr) {
		this.outRecheckStatusTimeStr = outRecheckStatusTimeStr;
	}

	public String getSaleArrivalTimeStr() {
		return saleArrivalTimeStr;
	}

	public void setSaleArrivalTimeStr(String saleArrivalTimeStr) {
		this.saleArrivalTimeStr = saleArrivalTimeStr;
	}

	public String getSaleTraderName() {
		return saleTraderName;
	}

	public void setSaleTraderName(String saleTraderName) {
		this.saleTraderName = saleTraderName;
	}

	public String getCustomerTagName() {
		return customerTagName;
	}

	public void setCustomerTagName(String customerTagName) {
		this.customerTagName = customerTagName;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getCustomerLevelStr() {
		return customerLevelStr;
	}

	public void setCustomerLevelStr(String customerLevelStr) {
		this.customerLevelStr = customerLevelStr;
	}

	public String getTakeTraderName() {
		return takeTraderName;
	}

	public void setTakeTraderName(String takeTraderName) {
		this.takeTraderName = takeTraderName==null ? null :takeTraderName.replaceAll("\\(","（").replaceAll("\\)","）").trim();
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

	public String getInvoiceTimeStr() {
		return invoiceTimeStr;
	}

	public void setInvoiceTimeStr(String invoiceTimeStr) {
		this.invoiceTimeStr = invoiceTimeStr;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getBuyPaymentTypeStr() {
		return buyPaymentTypeStr;
	}

	public void setBuyPaymentTypeStr(String buyPaymentTypeStr) {
		this.buyPaymentTypeStr = buyPaymentTypeStr;
	}
	
}
