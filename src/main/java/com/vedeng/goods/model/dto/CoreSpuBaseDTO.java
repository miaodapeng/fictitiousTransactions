package com.vedeng.goods.model.dto;

import java.util.Date;
/**
 * 加字段需慎重
 */
public class CoreSpuBaseDTO {

	private String spuNo;
	private Integer spuId;
	private String spuShowName;
	private String spuType;// 商品类型

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	private Integer brandId;

	private Integer spuLevel;
	/**
	 * 注册证号
	 *
	 */
	private String registrationNumber;

	private Integer checkStatus;
	private Date modTime;
	private String wikiHref;
	private Integer categoryId;
	private Integer operateInfoId;
	
	private String registrationIcon;
	
	public String getRegistrationIcon() {
		return registrationIcon;
	}

	public void setRegistrationIcon(String registrationIcon) {
		this.registrationIcon = registrationIcon;
	}

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

	public Integer getFirstEngageStatus() {
		return firstEngageStatus;
	}

	public void setFirstEngageStatus(Integer firstEngageStatus) {
		this.firstEngageStatus = firstEngageStatus;
	}

	private Integer firstEngageStatus;

	public Integer getFirstEngageId() {
		return firstEngageId;
	}

	public void setFirstEngageId(Integer firstEngageId) {
		this.firstEngageId = firstEngageId;
	}

	private Integer firstEngageId;


	public String getSpuNo() {
		return spuNo;
	}

	public void setSpuNo(String spuNo) {
		this.spuNo = spuNo;
	}

	public Integer getSpuId() {
		return spuId;
	}

	public void setSpuId(Integer spuId) {
		this.spuId = spuId;
	}

	public String getSpuShowName() {
		return spuShowName;
	}

	public void setSpuShowName(String spuShowName) {
		this.spuShowName = spuShowName;
	}

	public String getSpuType() {
		return spuType;
	}

	public void setSpuType(String spuType) {
		this.spuType = spuType;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getWikiHref() {
		return wikiHref;
	}

	public void setWikiHref(String wikiHref) {
		this.wikiHref = wikiHref;
	}

	public Integer getSpuLevel() {
		return spuLevel;
	}

	public void setSpuLevel(Integer spuLevel) {
		this.spuLevel = spuLevel;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public Integer getOperateInfoId() {
		return operateInfoId;
	}

	public void setOperateInfoId(Integer operateInfoId) {
		this.operateInfoId = operateInfoId;
	}

}
