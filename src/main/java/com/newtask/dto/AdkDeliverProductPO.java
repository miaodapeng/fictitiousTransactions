package com.newtask.dto;

import java.math.BigDecimal;

public class AdkDeliverProductPO {
	private String goodsCode = "";
	private String goodsName = "";
	private String unitName = "";
	private BigDecimal num = new BigDecimal(0);
	private String beiUnit = "";
	private BigDecimal beiNum = new BigDecimal(0);
	private String batchNumber = "";
	private Integer baoZhiqi = 0;
	private String expirationDate = "";
	private String manufacturer = "";
	private String productionLicense = "";
	private String licenseNumber = "";
	private String registrationNumber = "";
	private String recordNumber = "";
	private String storageRequirements = "";

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public String getBeiUnit() {
		return beiUnit;
	}

	public void setBeiUnit(String beiUnit) {
		this.beiUnit = beiUnit;
	}

	public BigDecimal getBeiNum() {
		return beiNum;
	}

	public void setBeiNum(BigDecimal beiNum) {
		this.beiNum = beiNum;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Integer getBaoZhiqi() {
		return baoZhiqi;
	}

	public void setBaoZhiqi(Integer baoZhiqi) {
		this.baoZhiqi = baoZhiqi;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProductionLicense() {
		return productionLicense;
	}

	public void setProductionLicense(String productionLicense) {
		this.productionLicense = productionLicense;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getStorageRequirements() {
		return storageRequirements;
	}

	public void setStorageRequirements(String storageRequirements) {
		this.storageRequirements = storageRequirements;
	}

}
