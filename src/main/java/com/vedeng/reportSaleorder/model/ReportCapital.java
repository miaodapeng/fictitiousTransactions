package com.vedeng.reportSaleorder.model;

import java.math.BigDecimal;

public class ReportCapital {
    /** 订单ID  CAPITAL_ID **/
    private Integer capitalId;

    /** 订单号  SALEORDER_NO **/
    private String saleorderNo;

    /** 到款金额  CAPITAL_AMOUNT **/
    private BigDecimal capitalAmount;

    /** 订单所属人ID  VALID_USER_ID **/
    private Integer validUserId;

    /** 订单所属人名称  VALID_USER_NAME **/
    private String validUserName;

    /** 订单所属人所属部门ID  VALID_DEPT_ID **/
    private Integer validDeptId;

    /** 订单所属人所属部门ID  VALID_PARENT_DEPT_ID **/
    private Integer validParentDeptId;

    /** 订单所属人所属部门名称  VALID_DEPT_NAME **/
    private String validDeptName;

    /** 订单所属人所属上级部门名称  VALID_PARENT_DEPT_NAME **/
    private String validParentDeptName;

    /** 日期年 yyyy  DATA_DATE_YEAR **/
    private String dataDateYear;

    /** 日期月 mm  DATA_DATE_MONTH **/
    private String dataDateMonth;

    /** 日期年月 yyyy/mm  DATA_DATE_YM **/
    private String dataDateYm;

    /** 日期 yyyy/mm/dd  DATA_DATE **/
    private String dataDate;

    /**   订单ID  CAPITAL_ID   **/
    public Integer getCapitalId() {
        return capitalId;
    }

    /**   订单ID  CAPITAL_ID   **/
    public void setCapitalId(Integer capitalId) {
        this.capitalId = capitalId;
    }

    /**   订单号  SALEORDER_NO   **/
    public String getSaleorderNo() {
        return saleorderNo;
    }

    /**   订单号  SALEORDER_NO   **/
    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo == null ? null : saleorderNo.trim();
    }

    /**   到款金额  CAPITAL_AMOUNT   **/
    public BigDecimal getCapitalAmount() {
        return capitalAmount;
    }

    /**   到款金额  CAPITAL_AMOUNT   **/
    public void setCapitalAmount(BigDecimal capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    /**   订单所属人ID  VALID_USER_ID   **/
    public Integer getValidUserId() {
        return validUserId;
    }

    /**   订单所属人ID  VALID_USER_ID   **/
    public void setValidUserId(Integer validUserId) {
        this.validUserId = validUserId;
    }

    /**   订单所属人名称  VALID_USER_NAME   **/
    public String getValidUserName() {
        return validUserName;
    }

    /**   订单所属人名称  VALID_USER_NAME   **/
    public void setValidUserName(String validUserName) {
        this.validUserName = validUserName == null ? null : validUserName.trim();
    }

    /**   订单所属人所属部门ID  VALID_DEPT_ID   **/
    public Integer getValidDeptId() {
        return validDeptId;
    }

    /**   订单所属人所属部门ID  VALID_DEPT_ID   **/
    public void setValidDeptId(Integer validDeptId) {
        this.validDeptId = validDeptId;
    }

    /**   订单所属人所属部门ID  VALID_PARENT_DEPT_ID   **/
    public Integer getValidParentDeptId() {
        return validParentDeptId;
    }

    /**   订单所属人所属部门ID  VALID_PARENT_DEPT_ID   **/
    public void setValidParentDeptId(Integer validParentDeptId) {
        this.validParentDeptId = validParentDeptId;
    }

    /**   订单所属人所属部门名称  VALID_DEPT_NAME   **/
    public String getValidDeptName() {
        return validDeptName;
    }

    /**   订单所属人所属部门名称  VALID_DEPT_NAME   **/
    public void setValidDeptName(String validDeptName) {
        this.validDeptName = validDeptName == null ? null : validDeptName.trim();
    }

    /**   订单所属人所属上级部门名称  VALID_PARENT_DEPT_NAME   **/
    public String getValidParentDeptName() {
        return validParentDeptName;
    }

    /**   订单所属人所属上级部门名称  VALID_PARENT_DEPT_NAME   **/
    public void setValidParentDeptName(String validParentDeptName) {
        this.validParentDeptName = validParentDeptName == null ? null : validParentDeptName.trim();
    }

    /**   日期年 yyyy  DATA_DATE_YEAR   **/
    public String getDataDateYear() {
        return dataDateYear;
    }

    /**   日期年 yyyy  DATA_DATE_YEAR   **/
    public void setDataDateYear(String dataDateYear) {
        this.dataDateYear = dataDateYear == null ? null : dataDateYear.trim();
    }

    /**   日期月 mm  DATA_DATE_MONTH   **/
    public String getDataDateMonth() {
        return dataDateMonth;
    }

    /**   日期月 mm  DATA_DATE_MONTH   **/
    public void setDataDateMonth(String dataDateMonth) {
        this.dataDateMonth = dataDateMonth == null ? null : dataDateMonth.trim();
    }

    /**   日期年月 yyyy/mm  DATA_DATE_YM   **/
    public String getDataDateYm() {
        return dataDateYm;
    }

    /**   日期年月 yyyy/mm  DATA_DATE_YM   **/
    public void setDataDateYm(String dataDateYm) {
        this.dataDateYm = dataDateYm == null ? null : dataDateYm.trim();
    }

    /**   日期 yyyy/mm/dd  DATA_DATE   **/
    public String getDataDate() {
        return dataDate;
    }

    /**   日期 yyyy/mm/dd  DATA_DATE   **/
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }
}