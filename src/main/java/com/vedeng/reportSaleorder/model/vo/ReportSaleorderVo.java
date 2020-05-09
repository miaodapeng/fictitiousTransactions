package com.vedeng.reportSaleorder.model.vo;

import com.vedeng.reportSaleorder.model.ReportSaleorder;

public class ReportSaleorderVo extends ReportSaleorder {

    /** 订单销售区域ID  SALES_AREA_ID **/
    private Integer salesAreaId;

    /** 订单生效时间 VALID_TIME **/
    private Long validTime;

    /** 订单可发货时间 SATISFY_DELIVERY_TIME **/
    private Long SatisfyDeliveryTime;

    /** 客户所属区域 CUSTOMER_VALID_PROVINCE_ID **/
    private Integer CustomerValidProvinceId;

    /** 订单首次付款时间 TRADER_TIME **/
    private Long traderTime;

    /** 客户所属地区 区ID CUSTOMER_REGION_ID **/
    private Integer customerRegionId;

    /** 客户所属地区 区名 CUSTOMER_REGION**/
    private String customerRegion;

    public Integer getSalesAreaId() {
        return salesAreaId;
    }

    public void setSalesAreaId(Integer salesAreaId) {
        this.salesAreaId = salesAreaId;
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
    }

    public Long getSatisfyDeliveryTime() {
        return SatisfyDeliveryTime;
    }

    public void setSatisfyDeliveryTime(Long satisfyDeliveryTime) {
        SatisfyDeliveryTime = satisfyDeliveryTime;
    }

    public Integer getCustomerValidProvinceId() {
        return CustomerValidProvinceId;
    }

    public void setCustomerValidProvinceId(Integer customerValidProvinceId) {
        CustomerValidProvinceId = customerValidProvinceId;
    }

    public Long getTraderTime() {
        return traderTime;
    }

    public void setTraderTime(Long traderTime) {
        this.traderTime = traderTime;
    }

    public Integer getCustomerRegionId() {
        return customerRegionId;
    }

    public void setCustomerRegionId(Integer customerRegionId) {
        this.customerRegionId = customerRegionId;
    }

    public String getCustomerRegion() {
        return customerRegion;
    }

    public void setCustomerRegion(String customerRegion) {
        this.customerRegion = customerRegion;
    }
}