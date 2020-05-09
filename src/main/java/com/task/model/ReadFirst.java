package com.task.model;

/**
 * @param $
 * @author bill
 * @description excel数据
 * @date $
 */
public class ReadFirst {


    private String registrationNumber;
    private Integer manageCategoryLevel;
    private String productCompanyChineseName;

    private Long issuingDate;
    private String issuingDateStr;

    private Long effectiveDate;
    private String effectiveDateStr;

    private String productionAddress;
    private String productCompanyAddress;

    private String productChineseName;
    private String productEnglishName;
    private String approvalDepartment;
    private String model;

    private String registeredAgent;
    private String registeredAgentAddress;
    private String trademark;
    private String zipCode;
    private String proPerfStruAndComp;
    private String productUseRange;
    private String otherContents;

    private String remarks;
    private String productStandards;
    private String expectedUsage;
    private String mainProPerfStruAndComp;
    private String changeDateStr;
    private Long changeDate;
    private String changeContents;
    private Integer goodsType;
    private Integer standardCategoryType;
    private Integer newStandardCategoryId;
    private Integer oldStandardCategoryId;

    public Integer getEffectiveDayUnit() {
        return effectiveDayUnit;
    }

    public void setEffectiveDayUnit(Integer effectiveDayUnit) {
        this.effectiveDayUnit = effectiveDayUnit;
    }

    /**
     * 存储条件1
     */
    private Integer conditionOne;


    private Integer productCompanyId;

    private Integer firstEngageId;
    private Integer registrationNumberId;

    private Integer brandType;


    private Integer effectiveDays;
    private Integer status;
    private Integer isDeleted;
    private Integer creator;
    private Integer updater;
    private String comments;
    private Integer productCategoryId;//旧国标
    private String  productCategoryName;
    private String  productionOrCountry;
    private String  aftersaleServiceOrg;
    private boolean isRelease;

    private Integer effectiveDayUnit;

    public String getSortDays() {
        return sortDays;
    }

    public void setSortDays(String sortDays) {
        this.sortDays = sortDays;
    }

    private String sortDays;

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductionOrCountry() {
        return productionOrCountry;
    }

    public void setProductionOrCountry(String productionOrCountry) {
        this.productionOrCountry = productionOrCountry;
    }

    public String getAftersaleServiceOrg() {
        return aftersaleServiceOrg;
    }

    public void setAftersaleServiceOrg(String aftersaleServiceOrg) {
        this.aftersaleServiceOrg = aftersaleServiceOrg;
    }

    public boolean isRelease() {
        return isRelease;
    }

    public void setRelease(boolean release) {
        isRelease = release;
    }

    public String getStorageCondAndEffectiveDate() {
        return storageCondAndEffectiveDate;
    }

    public void setStorageCondAndEffectiveDate(String storageCondAndEffectiveDate) {
        this.storageCondAndEffectiveDate = storageCondAndEffectiveDate;
    }

    private String     storageCondAndEffectiveDate;


    public Integer getProductCompanyId() {
        return productCompanyId;
    }

    public void setProductCompanyId(Integer productCompanyId) {
        this.productCompanyId = productCompanyId;
    }

    public Long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Long changeDate) {
        this.changeDate = changeDate;
    }

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getManageCategoryLevel() {
        return manageCategoryLevel;
    }

    public void setManageCategoryLevel(Integer manageCategoryLevel) {
        this.manageCategoryLevel = manageCategoryLevel;
    }

    public String getProductCompanyChineseName() {
        return productCompanyChineseName;
    }

    public void setProductCompanyChineseName(String productCompanyChineseName) {
        this.productCompanyChineseName = productCompanyChineseName;
    }

    public Long getIssuingDate() {
        return issuingDate;
    }

    public void setIssuingDate(Long issuingDate) {
        this.issuingDate = issuingDate;
    }

    public String getIssuingDateStr() {
        return issuingDateStr;
    }

    public void setIssuingDateStr(String issuingDateStr) {
        this.issuingDateStr = issuingDateStr;
    }

    public Long getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Long effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDateStr() {
        return effectiveDateStr;
    }

    public void setEffectiveDateStr(String effectiveDateStr) {
        this.effectiveDateStr = effectiveDateStr;
    }

    public String getProductionAddress() {
        return productionAddress;
    }

    public void setProductionAddress(String productionAddress) {
        this.productionAddress = productionAddress;
    }

    public String getProductCompanyAddress() {
        return productCompanyAddress;
    }

    public void setProductCompanyAddress(String productCompanyAddress) {
        this.productCompanyAddress = productCompanyAddress;
    }

    public String getProductChineseName() {
        return productChineseName;
    }

    public void setProductChineseName(String productChineseName) {
        this.productChineseName = productChineseName;
    }

    public String getProductEnglishName() {
        return productEnglishName;
    }

    public void setProductEnglishName(String productEnglishName) {
        this.productEnglishName = productEnglishName;
    }

    public String getApprovalDepartment() {
        return approvalDepartment;
    }

    public void setApprovalDepartment(String approvalDepartment) {
        this.approvalDepartment = approvalDepartment;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegisteredAgent() {
        return registeredAgent;
    }

    public void setRegisteredAgent(String registeredAgent) {
        this.registeredAgent = registeredAgent;
    }

    public String getRegisteredAgentAddress() {
        return registeredAgentAddress;
    }

    public void setRegisteredAgentAddress(String registeredAgentAddress) {
        this.registeredAgentAddress = registeredAgentAddress;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProPerfStruAndComp() {
        return proPerfStruAndComp;
    }

    public void setProPerfStruAndComp(String proPerfStruAndComp) {
        this.proPerfStruAndComp = proPerfStruAndComp;
    }

    public String getProductUseRange() {
        return productUseRange;
    }

    public void setProductUseRange(String productUseRange) {
        this.productUseRange = productUseRange;
    }

    public String getOtherContents() {
        return otherContents;
    }

    public void setOtherContents(String otherContents) {
        this.otherContents = otherContents;
    }

    public String getProductStandards() {
        return productStandards;
    }

    public void setProductStandards(String productStandards) {
        this.productStandards = productStandards;
    }

    public String getExpectedUsage() {
        return expectedUsage;
    }

    public void setExpectedUsage(String expectedUsage) {
        this.expectedUsage = expectedUsage;
    }

    public String getMainProPerfStruAndComp() {
        return mainProPerfStruAndComp;
    }

    public void setMainProPerfStruAndComp(String mainProPerfStruAndComp) {
        this.mainProPerfStruAndComp = mainProPerfStruAndComp;
    }

    public String getChangeDateStr() {
        return changeDateStr;
    }

    public void setChangeDateStr(String changeDateStr) {
        this.changeDateStr = changeDateStr;
    }

    public String getChangeContents() {
        return changeContents;
    }

    public void setChangeContents(String changeContents) {
        this.changeContents = changeContents;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getStandardCategoryType() {
        return standardCategoryType;
    }

    public void setStandardCategoryType(Integer standardCategoryType) {
        this.standardCategoryType = standardCategoryType;
    }

    public Integer getNewStandardCategoryId() {
        return newStandardCategoryId;
    }

    public void setNewStandardCategoryId(Integer newStandardCategoryId) {
        this.newStandardCategoryId = newStandardCategoryId;
    }

    public Integer getOldStandardCategoryId() {
        return oldStandardCategoryId;
    }

    public void setOldStandardCategoryId(Integer oldStandardCategoryId) {
        this.oldStandardCategoryId = oldStandardCategoryId;
    }

    public Integer getConditionOne() {
        return conditionOne;
    }

    public void setConditionOne(Integer conditionOne) {
        this.conditionOne = conditionOne;
    }

    public Integer getFirstEngageId() {
        return firstEngageId;
    }

    public void setFirstEngageId(Integer firstEngageId) {
        this.firstEngageId = firstEngageId;
    }

    public Integer getRegistrationNumberId() {
        return registrationNumberId;
    }

    public void setRegistrationNumberId(Integer registrationNumberId) {
        this.registrationNumberId = registrationNumberId;
    }

    public Integer getBrandType() {
        return brandType;
    }

    public void setBrandType(Integer brandType) {
        this.brandType = brandType;
    }

    public Integer getEffectiveDays() {
        return effectiveDays;
    }

    public void setEffectiveDays(Integer effectiveDays) {
        this.effectiveDays = effectiveDays;
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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
}
