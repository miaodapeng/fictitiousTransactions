package com.vedeng.reportSaleorder.model;

import java.math.BigDecimal;

public class ReportSaleorder {
    /** 订单ID  SALEORDER_ID **/
    private Integer saleorderId;

    /** 订单号  SALEORDER_NO **/
    private String saleorderNo;

    /** 订单类型0销售订单2备货订单3订货订单5耗材商城订单  ORDER_TYPE **/
    private Integer orderType;

    /** 订单状态：0待确认（默认）、1进行中、2已完结、3已关闭  ORDER_STATUS **/
    private Integer orderStatus;

    /** 订单金额  ORDER_AMOUNT **/
    private BigDecimal orderAmount;

    /** 退款金额  REFUND_AMOUNT **/
    private BigDecimal refundAmount;

    /** 采购金额  PURCHASE_AMOUNT **/
    private BigDecimal purchaseAmount;

    /** 含有账期支付 0无 1有  IS_ACCOUNT_PERIOD **/
    private Integer isAccountPeriod;

    /** 付款状态 0未付款 1部分付款 2全部付款  PAYMENT_STATUS **/
    private Integer paymentStatus;

    /** 客户ID  CUSTOMER_ID **/
    private Integer customerId;

    /** 客户名称  CUSTOMER_NAME **/
    private String customerName;

    /** 客户等级ID  CUSTOMER_LEVEL_ID **/
    private Integer customerLevelId;

    /** 客户等级  CUSTOMER_LEVEL **/
    private String customerLevel;

    /** 客户性质ID  CUSTOMER_NATURE_ID **/
    private Integer customerNatureId;

    /** 客户性质  CUSTOMER_NATURE **/
    private String customerNature;

    /** 客户所属地区省ID  CUSTOMER_PROVINCE_ID **/
    private Integer customerProvinceId;

    /** 客户所属地区市ID  CUSTOMER_CITY_ID **/
    private Integer customerCityId;

    /** 客户所属地区省名称  CUSTOMER_PROVINCE **/
    private String customerProvince;

    /** 客户所属地区市名称  CUSTOMER_CITY **/
    private String customerCity;

    /** 创建人ID  CREATOR_ID **/
    private Integer creatorId;

    /** 创建人名称  CREATOR_NAME **/
    private String creatorName;

    /** 创建人所属部门ID  CREATOR_DEPT_ID **/
    private Integer creatorDeptId;

    /** 创建人所属部门ID  CREATOR_PARENT_DEPT_ID **/
    private Integer creatorParentDeptId;

    /** 创建人所属部门名称  CREATOR_DEPT_NAME **/
    private String creatorDeptName;

    /** 创建人所属上级部门名称  CREATOR_PARENT_DEPT_NAME **/
    private String creatorParentDeptName;

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

    /**   订单ID  SALEORDER_ID   **/
    public Integer getSaleorderId() {
        return saleorderId;
    }

    /**   订单ID  SALEORDER_ID   **/
    public void setSaleorderId(Integer saleorderId) {
        this.saleorderId = saleorderId;
    }

    /**   订单号  SALEORDER_NO   **/
    public String getSaleorderNo() {
        return saleorderNo;
    }

    /**   订单号  SALEORDER_NO   **/
    public void setSaleorderNo(String saleorderNo) {
        this.saleorderNo = saleorderNo == null ? null : saleorderNo.trim();
    }

    /**   订单类型0销售订单2备货订单3订货订单5耗材商城订单  ORDER_TYPE   **/
    public Integer getOrderType() {
        return orderType;
    }

    /**   订单类型0销售订单2备货订单3订货订单5耗材商城订单  ORDER_TYPE   **/
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**   订单状态：0待确认（默认）、1进行中、2已完结、3已关闭  ORDER_STATUS   **/
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**   订单状态：0待确认（默认）、1进行中、2已完结、3已关闭  ORDER_STATUS   **/
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**   订单金额  ORDER_AMOUNT   **/
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**   订单金额  ORDER_AMOUNT   **/
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**   退款金额  REFUND_AMOUNT   **/
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**   退款金额  REFUND_AMOUNT   **/
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**   采购金额  PURCHASE_AMOUNT   **/
    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    /**   采购金额  PURCHASE_AMOUNT   **/
    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    /**   含有账期支付 0无 1有  IS_ACCOUNT_PERIOD   **/
    public Integer getIsAccountPeriod() {
        return isAccountPeriod;
    }

    /**   含有账期支付 0无 1有  IS_ACCOUNT_PERIOD   **/
    public void setIsAccountPeriod(Integer isAccountPeriod) {
        this.isAccountPeriod = isAccountPeriod;
    }

    /**   付款状态 0未付款 1部分付款 2全部付款  PAYMENT_STATUS   **/
    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    /**   付款状态 0未付款 1部分付款 2全部付款  PAYMENT_STATUS   **/
    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**   客户ID  CUSTOMER_ID   **/
    public Integer getCustomerId() {
        return customerId;
    }

    /**   客户ID  CUSTOMER_ID   **/
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**   客户名称  CUSTOMER_NAME   **/
    public String getCustomerName() {
        return customerName;
    }

    /**   客户名称  CUSTOMER_NAME   **/
    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    /**   客户等级ID  CUSTOMER_LEVEL_ID   **/
    public Integer getCustomerLevelId() {
        return customerLevelId;
    }

    /**   客户等级ID  CUSTOMER_LEVEL_ID   **/
    public void setCustomerLevelId(Integer customerLevelId) {
        this.customerLevelId = customerLevelId;
    }

    /**   客户等级  CUSTOMER_LEVEL   **/
    public String getCustomerLevel() {
        return customerLevel;
    }

    /**   客户等级  CUSTOMER_LEVEL   **/
    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel == null ? null : customerLevel.trim();
    }

    /**   客户性质ID  CUSTOMER_NATURE_ID   **/
    public Integer getCustomerNatureId() {
        return customerNatureId;
    }

    /**   客户性质ID  CUSTOMER_NATURE_ID   **/
    public void setCustomerNatureId(Integer customerNatureId) {
        this.customerNatureId = customerNatureId;
    }

    /**   客户性质  CUSTOMER_NATURE   **/
    public String getCustomerNature() {
        return customerNature;
    }

    /**   客户性质  CUSTOMER_NATURE   **/
    public void setCustomerNature(String customerNature) {
        this.customerNature = customerNature == null ? null : customerNature.trim();
    }

    /**   客户所属地区省ID  CUSTOMER_PROVINCE_ID   **/
    public Integer getCustomerProvinceId() {
        return customerProvinceId;
    }

    /**   客户所属地区省ID  CUSTOMER_PROVINCE_ID   **/
    public void setCustomerProvinceId(Integer customerProvinceId) {
        this.customerProvinceId = customerProvinceId;
    }

    /**   客户所属地区市ID  CUSTOMER_CITY_ID   **/
    public Integer getCustomerCityId() {
        return customerCityId;
    }

    /**   客户所属地区市ID  CUSTOMER_CITY_ID   **/
    public void setCustomerCityId(Integer customerCityId) {
        this.customerCityId = customerCityId;
    }

    /**   客户所属地区省名称  CUSTOMER_PROVINCE   **/
    public String getCustomerProvince() {
        return customerProvince;
    }

    /**   客户所属地区省名称  CUSTOMER_PROVINCE   **/
    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince == null ? null : customerProvince.trim();
    }

    /**   客户所属地区市名称  CUSTOMER_CITY   **/
    public String getCustomerCity() {
        return customerCity;
    }

    /**   客户所属地区市名称  CUSTOMER_CITY   **/
    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity == null ? null : customerCity.trim();
    }

    /**   创建人ID  CREATOR_ID   **/
    public Integer getCreatorId() {
        return creatorId;
    }

    /**   创建人ID  CREATOR_ID   **/
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**   创建人名称  CREATOR_NAME   **/
    public String getCreatorName() {
        return creatorName;
    }

    /**   创建人名称  CREATOR_NAME   **/
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    /**   创建人所属部门ID  CREATOR_DEPT_ID   **/
    public Integer getCreatorDeptId() {
        return creatorDeptId;
    }

    /**   创建人所属部门ID  CREATOR_DEPT_ID   **/
    public void setCreatorDeptId(Integer creatorDeptId) {
        this.creatorDeptId = creatorDeptId;
    }

    /**   创建人所属部门ID  CREATOR_PARENT_DEPT_ID   **/
    public Integer getCreatorParentDeptId() {
        return creatorParentDeptId;
    }

    /**   创建人所属部门ID  CREATOR_PARENT_DEPT_ID   **/
    public void setCreatorParentDeptId(Integer creatorParentDeptId) {
        this.creatorParentDeptId = creatorParentDeptId;
    }

    /**   创建人所属部门名称  CREATOR_DEPT_NAME   **/
    public String getCreatorDeptName() {
        return creatorDeptName;
    }

    /**   创建人所属部门名称  CREATOR_DEPT_NAME   **/
    public void setCreatorDeptName(String creatorDeptName) {
        this.creatorDeptName = creatorDeptName == null ? null : creatorDeptName.trim();
    }

    /**   创建人所属上级部门名称  CREATOR_PARENT_DEPT_NAME   **/
    public String getCreatorParentDeptName() {
        return creatorParentDeptName;
    }

    /**   创建人所属上级部门名称  CREATOR_PARENT_DEPT_NAME   **/
    public void setCreatorParentDeptName(String creatorParentDeptName) {
        this.creatorParentDeptName = creatorParentDeptName == null ? null : creatorParentDeptName.trim();
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