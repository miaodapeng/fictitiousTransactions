package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderInfoTyc implements Serializable{
    private Integer traderInfoTycId;

    private Long syncTime;

    private Long updateTime;

    private Long fromTime;

    private Long toTime;

    private Integer type;

    private Integer categoryScore;

    private Long id;

    private String regNumber;

    private Integer percentileScore;

    private String phoneNumber;

    private String regCapital;

    private String regInstitute;

    private String name;

    private String regLocation;

    private String industry;

    private Long approvedTime;

    private String orgApprovedInstitute;

    private String businessScope;

    private String orgNumber;

    private Long estiblishTime;

    private String regStatus;

    private String legalPersonName;

    private Long legalPersonId;

    private String actualCapital;

    private String websiteList;

    private Integer flag;

    private String correctCompanyId;

    private String companyOrgType;

    private String base;

    private Long updateTimes;

    private String creditCode;

    private Long companyId;

    private String historyNames;

    private Integer companyType;

    private String sourceFlag;

    private String taxNumber;

    private String logo;

    private String jsonData;
    
    private Integer codeType=0;
    
    public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public Integer getTraderInfoTycId() {
        return traderInfoTycId;
    }

    public void setTraderInfoTycId(Integer traderInfoTycId) {
        this.traderInfoTycId = traderInfoTycId;
    }

    public Long getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Long syncTime) {
        this.syncTime = syncTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getFromTime() {
        return fromTime;
    }

    public void setFromTime(Long fromTime) {
        this.fromTime = fromTime;
    }

    public Long getToTime() {
        return toTime;
    }

    public void setToTime(Long toTime) {
        this.toTime = toTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategoryScore() {
        return categoryScore;
    }

    public void setCategoryScore(Integer categoryScore) {
        this.categoryScore = categoryScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber == null ? null : regNumber.trim();
    }

    public Integer getPercentileScore() {
        return percentileScore;
    }

    public void setPercentileScore(Integer percentileScore) {
        this.percentileScore = percentileScore;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital == null ? null : regCapital.trim();
    }

    public String getRegInstitute() {
        return regInstitute;
    }

    public void setRegInstitute(String regInstitute) {
        this.regInstitute = regInstitute == null ? null : regInstitute.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRegLocation() {
        return regLocation;
    }

    public void setRegLocation(String regLocation) {
        this.regLocation = regLocation == null ? null : regLocation.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public Long getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Long approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getOrgApprovedInstitute() {
        return orgApprovedInstitute;
    }

    public void setOrgApprovedInstitute(String orgApprovedInstitute) {
        this.orgApprovedInstitute = orgApprovedInstitute == null ? null : orgApprovedInstitute.trim();
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber == null ? null : orgNumber.trim();
    }

    public Long getEstiblishTime() {
        return estiblishTime;
    }

    public void setEstiblishTime(Long estiblishTime) {
        this.estiblishTime = estiblishTime;
    }

    public String getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus == null ? null : regStatus.trim();
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }

    public Long getLegalPersonId() {
        return legalPersonId;
    }

    public void setLegalPersonId(Long legalPersonId) {
        this.legalPersonId = legalPersonId;
    }

    public String getActualCapital() {
        return actualCapital;
    }

    public void setActualCapital(String actualCapital) {
        this.actualCapital = actualCapital == null ? null : actualCapital.trim();
    }

    public String getWebsiteList() {
        return websiteList;
    }

    public void setWebsiteList(String websiteList) {
        this.websiteList = websiteList == null ? null : websiteList.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCorrectCompanyId() {
        return correctCompanyId;
    }

    public void setCorrectCompanyId(String correctCompanyId) {
        this.correctCompanyId = correctCompanyId == null ? null : correctCompanyId.trim();
    }

    public String getCompanyOrgType() {
        return companyOrgType;
    }

    public void setCompanyOrgType(String companyOrgType) {
        this.companyOrgType = companyOrgType == null ? null : companyOrgType.trim();
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base == null ? null : base.trim();
    }

    public Long getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(Long updateTimes) {
        this.updateTimes = updateTimes;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getHistoryNames() {
        return historyNames;
    }

    public void setHistoryNames(String historyNames) {
        this.historyNames = historyNames == null ? null : historyNames.replaceAll("\\(","（").replaceAll("\\)","）").trim();
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag == null ? null : sourceFlag.trim();
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData == null ? null : jsonData.trim();
    }
}