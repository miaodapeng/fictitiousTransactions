package com.smallhospital.dto;

import java.util.List;

public class ELSkuDto {

    private Integer skuId;

    private Integer hospitalId;

    private String brand;

    private String spec;

    private String unit;

    private String productName;

    private String baseCategoryId;

    private Integer pageNo;

    private Integer pageSize = 10;

    private List<Integer> validSkuIds;

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<Integer> getValidSkuIds() {
        return validSkuIds;
    }

    public void setValidSkuIds(List<Integer> validSkuIds) {
        this.validSkuIds = validSkuIds;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBaseCategoryId() {
        return baseCategoryId;
    }

    public void setBaseCategoryId(String baseCategoryId) {
        this.baseCategoryId = baseCategoryId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
