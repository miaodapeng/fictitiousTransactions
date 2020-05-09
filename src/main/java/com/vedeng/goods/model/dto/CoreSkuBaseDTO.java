package com.vedeng.goods.model.dto;

import java.util.Date;

/**
 * 加字段需慎重
 */
public class CoreSkuBaseDTO {
	private Integer skuId;
	private Integer spuId;
	private String skuNo;
	private String skuName;

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	private String showName;
	private String skuBelong;
	private Integer hasBackupMachine;
	private String isStockup;

	private Integer checkStatus;// sku审核状态

	private Date modTime;

	private String wikiHref;

	private Integer operateInfoId;

	private String model;

	public Integer getAssignmentManagerId() {
		return assignmentManagerId;
	}

	public void setAssignmentManagerId(Integer assignmentManagerId) {
		this.assignmentManagerId = assignmentManagerId;
	}

	private Integer assignmentManagerId;


	public Integer getAssignmentAssistantId() {
		return assignmentAssistantId;
	}

	public void setAssignmentAssistantId(Integer assignmentAssistantId) {
		this.assignmentAssistantId = assignmentAssistantId;
	}

	private Integer assignmentAssistantId;


	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	private String spec;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuBelong() {
		return skuBelong;
	}

	public void setSkuBelong(String skuBelong) {
		this.skuBelong = skuBelong;
	}

	public Integer getHasBackupMachine() {
		return hasBackupMachine;
	}

	public void setHasBackupMachine(Integer hasBackupMachine) {
		this.hasBackupMachine = hasBackupMachine;
	}

	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public String getWikiHref() {
		return wikiHref;
	}

	public void setWikiHref(String wikiHref) {
		this.wikiHref = wikiHref;
	}

	public Integer getOperateInfoId() {
		return operateInfoId;
	}

	public void setOperateInfoId(Integer operateInfoId) {
		this.operateInfoId = operateInfoId;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getSpuId() {
		return spuId;
	}

	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
	}

	public String getIsStockup() {
		return isStockup;
	}

	public void setIsStockup(String isStockup) {
		this.isStockup = isStockup;
	}
}
