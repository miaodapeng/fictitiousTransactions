package com.vedeng.firstengage.model;

import com.vedeng.common.controller.BaseCommand;
import com.vedeng.goods.model.Goods;

import java.util.List;
import java.util.Map;

public class FirstEngage extends BaseCommand {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.FIRST_ENGAGE_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer firstEngageId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.REGISTRATION_NUMBER_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer registrationNumberId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.GOODS_TYPE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer goodsType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.BRAND_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer brandType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.STANDARD_CATEGORY_TYPE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer standardCategoryType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.NEW_STANDARD_CATEGORY_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer newStandardCategoryId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.OLD_STANDARD_CATEGORY_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer oldStandardCategoryId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.EFFECTIVE_DATE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer effectiveDays;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.STATUS
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.IS_DELETED
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer isDeleted;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.ADD_TIME
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Long addTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.CREATOR
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer creator;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.MOD_TIME
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Long modTime;
	
	/**
	 * 更新时间
	 */
	private String modTimeStr;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.UPDATER
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private Integer updater;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column T_FIRST_ENGAGE.COMMENTS
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	private String comments;

	/**
	 * 是否
	 */
	private Integer checkAgain;

	
	/**
	 * 注册证号
	 */
	private String registrationNumber;
	
	/**
	 * 生产企业中文名
	 */
	private String productCompanyChineseName;
	
	/**
	 * 新国标分类名称
	 */
	private String newStandardCategoryName;
	
	/**
	 * 旧国标分类名称
	 */
	private String oldStandardCategoryName;
	
	/**
	 * 注册证有效期
	 */
	private Long registrationEffectiveDate;

	/**
	 * 注册证有效期str类型
	 */
	private String registrationEffectiveDateStr;
	
	/**
	 * 过期处理状态
	 */
	private Integer dealStatus;
	
	/**
	 * 商品信息
	 */
	private List<Goods> goodsList;
	
	/**
	 * 注册证信息
	 */
	private RegistrationNumber registration;
	
	/**
	 * 存储条件
	 */
	private List<FirstEngageStorageCondition> storageCondition;
	
	/**
	 * 有效期开始时间
	 */
	private String effectStartDate;
	
	/**
	 * 有效期结束时间 
	 * 
	 */
	private String effectEndDate;
	
	/**
	 * 搜索框
	 */
	private String searchInfo;
	
	/**
	 * 搜索状态
	 */
	private Integer searchStatus;

	/**
	 * 关键词
	 */
	private String keyWords;
	
	/**
	 * 存储条件1
	 */
	private Integer conditionOne;

	/**
	 * 存储温度
	 * @date: 2019/6/21 17:08
	 */
	private String temperature;

	/**
	 * 产品有效期限单位
	 */
	private Integer effectiveDayUnit;

	/**
	 * 产品有效期，排序用
	 */
	private Integer effectiveDaysSort;
	
	/**
	 * 时间筛选项
	 */
	private Integer timeSort;

	/**
	 * 时间的排序
	 */
	private Integer sortDays;

	private String manageCategoryLevelShow;

	private List<Map<String, Object>> zczMapList;
	private List<Map<String, Object>> yzMapList;

	private List<Map<String, Object>> smsMapList;
	private List<Map<String, Object>> wsMapList;
	private List<Map<String, Object>> scMapList;
	private List<Map<String, Object>> sbMapList;
	private List<Map<String, Object>> djbMapList;
	private List<Map<String, Object>> cpMapList;


	/**
	 * 注册证过期状态
	 */
	private Integer isOverDate;

	/**
	 * 审核不通过原因
	 */
	private String reason;

	public Integer getSortDays() {
		return sortDays;
	}

	public void setSortDays(Integer sortDays) {
		this.sortDays = sortDays;
	}

	public Integer getCheckAgain() {
		return checkAgain;
	}

	public void setCheckAgain(Integer checkAgain) {
		this.checkAgain = checkAgain;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getManageCategoryLevelShow() {
		return manageCategoryLevelShow;
	}

	public void setManageCategoryLevelShow(String manageCategoryLevelShow) {
		this.manageCategoryLevelShow = manageCategoryLevelShow;
	}

    public Integer getEffectiveDaysSort() {
        return effectiveDaysSort;
    }

    public void setEffectiveDaysSort(Integer effectiveDaysSort) {
        this.effectiveDaysSort = effectiveDaysSort;
    }

    public List<Map<String, Object>> getYzMapList() {
		return yzMapList;
	}

	public void setYzMapList(List<Map<String, Object>> yzMapList) {
		this.yzMapList = yzMapList;
	}

	public Integer getIsOverDate() {
		return isOverDate;
	}

	public void setIsOverDate(Integer isOverDate) {
		this.isOverDate = isOverDate;
	}

	public List<Map<String, Object>> getWsMapList() {

        return wsMapList;
    }

    public void setWsMapList(List<Map<String, Object>> wsMapList) {
        this.wsMapList = wsMapList;
    }

    public List<Map<String, Object>> getScMapList() {
        return scMapList;
    }

    public void setScMapList(List<Map<String, Object>> scMapList) {
        this.scMapList = scMapList;
    }

    public List<Map<String, Object>> getSbMapList() {
        return sbMapList;
    }

    public void setSbMapList(List<Map<String, Object>> sbMapList) {
        this.sbMapList = sbMapList;
    }

    public List<Map<String, Object>> getDjbMapList() {
        return djbMapList;
    }

    public void setDjbMapList(List<Map<String, Object>> djbMapList) {
        this.djbMapList = djbMapList;
    }

    public List<Map<String, Object>> getCpMapList() {
        return cpMapList;
    }

    public void setCpMapList(List<Map<String, Object>> cpMapList) {
        this.cpMapList = cpMapList;
    }

    public List<Map<String, Object>> getSmsMapList() {
        return smsMapList;
    }

    public void setSmsMapList(List<Map<String, Object>> smsMapList) {
        this.smsMapList = smsMapList;
    }

    public List<Map<String, Object>> getZczMapList() {
		return zczMapList;
	}

	public void setZczMapList(List<Map<String, Object>> zczMapList) {
		this.zczMapList = zczMapList;
	}

	public Integer getConditionOne() {
		return conditionOne;
	}

	public void setConditionOne(Integer conditionOne) {
		this.conditionOne = conditionOne;
	}

	public Integer getEffectiveDayUnit() {
		return effectiveDayUnit;
	}

	public void setEffectiveDayUnit(Integer effectiveDayUnit) {
		this.effectiveDayUnit = effectiveDayUnit;
	}

	public Integer getTimeSort() {
		return timeSort;
	}

	public void setTimeSort(Integer timeSort) {
		this.timeSort = timeSort;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Integer getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(Integer searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

	public String getEffectStartDate() {
		return effectStartDate;
	}

	public void setEffectStartDate(String effectStartDate) {
		this.effectStartDate = effectStartDate;
	}

	public String getEffectEndDate() {
		return effectEndDate;
	}

	public void setEffectEndDate(String effectEndDate) {
		this.effectEndDate = effectEndDate;
	}

	public String getModTimeStr() {
		return modTimeStr;
	}

	public void setModTimeStr(String modTimeStr) {
		this.modTimeStr = modTimeStr;
	}

	public List<FirstEngageStorageCondition> getStorageCondition() {
		return storageCondition;
	}

	public void setStorageCondition(List<FirstEngageStorageCondition> storageCondition) {
		this.storageCondition = storageCondition;
	}

	public RegistrationNumber getRegistration() {
		return registration;
	}

	public void setRegistration(RegistrationNumber registration) {
		this.registration = registration;
	}

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getProductCompanyChineseName() {
		return productCompanyChineseName;
	}

	public void setProductCompanyChineseName(String productCompanyChineseName) {
		this.productCompanyChineseName = productCompanyChineseName;
	}

	public String getNewStandardCategoryName() {
		return newStandardCategoryName;
	}

	public void setNewStandardCategoryName(String newStandardCategoryName) {
		this.newStandardCategoryName = newStandardCategoryName;
	}

	public String getOldStandardCategoryName() {
		return oldStandardCategoryName;
	}

	public void setOldStandardCategoryName(String oldStandardCategoryName) {
		this.oldStandardCategoryName = oldStandardCategoryName;
	}

	public Long getRegistrationEffectiveDate() {
		return registrationEffectiveDate;
	}

	public void setRegistrationEffectiveDate(Long registrationEffectiveDate) {
		this.registrationEffectiveDate = registrationEffectiveDate;
	}

	public String getRegistrationEffectiveDateStr() {
		return registrationEffectiveDateStr;
	}

	public void setRegistrationEffectiveDateStr(String registrationEffectiveDateStr) {
		this.registrationEffectiveDateStr = registrationEffectiveDateStr;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.FIRST_ENGAGE_ID
	 * @return  the value of T_FIRST_ENGAGE.FIRST_ENGAGE_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getFirstEngageId() {
		return firstEngageId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.FIRST_ENGAGE_ID
	 * @param firstEngageId  the value for T_FIRST_ENGAGE.FIRST_ENGAGE_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setFirstEngageId(Integer firstEngageId) {
		this.firstEngageId = firstEngageId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.REGISTRATION_NUMBER_ID
	 * @return  the value of T_FIRST_ENGAGE.REGISTRATION_NUMBER_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getRegistrationNumberId() {
		return registrationNumberId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.REGISTRATION_NUMBER_ID
	 * @param registrationNumberId  the value for T_FIRST_ENGAGE.REGISTRATION_NUMBER_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setRegistrationNumberId(Integer registrationNumberId) {
		this.registrationNumberId = registrationNumberId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.GOODS_TYPE
	 * @return  the value of T_FIRST_ENGAGE.GOODS_TYPE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getGoodsType() {
		return goodsType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.GOODS_TYPE
	 * @param goodsType  the value for T_FIRST_ENGAGE.GOODS_TYPE
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	

	public Integer getBrandType() {
		return brandType;
	}

	public void setBrandType(Integer brandType) {
		this.brandType = brandType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.NEW_STANDARD_CATEGORY_ID
	 * @return  the value of T_FIRST_ENGAGE.NEW_STANDARD_CATEGORY_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getNewStandardCategoryId() {
		return newStandardCategoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.NEW_STANDARD_CATEGORY_ID
	 * @param newStandardCategoryId  the value for T_FIRST_ENGAGE.NEW_STANDARD_CATEGORY_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setNewStandardCategoryId(Integer newStandardCategoryId) {
		this.newStandardCategoryId = newStandardCategoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.OLD_STANDARD_CATEGORY_ID
	 * @return  the value of T_FIRST_ENGAGE.OLD_STANDARD_CATEGORY_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getOldStandardCategoryId() {
		return oldStandardCategoryId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.OLD_STANDARD_CATEGORY_ID
	 * @param oldStandardCategoryId  the value for T_FIRST_ENGAGE.OLD_STANDARD_CATEGORY_ID
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setOldStandardCategoryId(Integer oldStandardCategoryId) {
		this.oldStandardCategoryId = oldStandardCategoryId;
	}


	public Integer getEffectiveDays() {
		return effectiveDays;
	}

	public void setEffectiveDays(Integer effectiveDays) {
		this.effectiveDays = effectiveDays;
	}


	public Integer getStandardCategoryType() {
		return standardCategoryType;
	}

	public void setStandardCategoryType(Integer standardCategoryType) {
		this.standardCategoryType = standardCategoryType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.ADD_TIME
	 * @return  the value of T_FIRST_ENGAGE.ADD_TIME
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Long getAddTime() {
		return addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.ADD_TIME
	 * @param addTime  the value for T_FIRST_ENGAGE.ADD_TIME
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.CREATOR
	 * @return  the value of T_FIRST_ENGAGE.CREATOR
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getCreator() {
		return creator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.CREATOR
	 * @param creator  the value for T_FIRST_ENGAGE.CREATOR
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.MOD_TIME
	 * @return  the value of T_FIRST_ENGAGE.MOD_TIME
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Long getModTime() {
		return modTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.MOD_TIME
	 * @param modTime  the value for T_FIRST_ENGAGE.MOD_TIME
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setModTime(Long modTime) {
		this.modTime = modTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.UPDATER
	 * @return  the value of T_FIRST_ENGAGE.UPDATER
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public Integer getUpdater() {
		return updater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.UPDATER
	 * @param updater  the value for T_FIRST_ENGAGE.UPDATER
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column T_FIRST_ENGAGE.COMMENTS
	 * @return  the value of T_FIRST_ENGAGE.COMMENTS
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column T_FIRST_ENGAGE.COMMENTS
	 * @param comments  the value for T_FIRST_ENGAGE.COMMENTS
	 * @mbg.generated  Wed Mar 20 11:35:21 CST 2019
	 */
	public void setComments(String comments) {
		this.comments = comments == null ? null : comments.trim();
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
}