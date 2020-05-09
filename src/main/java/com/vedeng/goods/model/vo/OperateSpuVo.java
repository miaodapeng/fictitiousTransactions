package com.vedeng.goods.model.vo;

import java.util.Date;

/**
 * @Description:
 * @Auther: Duke.li
 * @Date: 2019/7/15 17:41
 */
public class OperateSpuVo {

    private Integer spuId;

    private String spuNo,spuName;

    private String brandName;

    private Integer spuType,spuLevel,checkStatus,brandId;

    private Integer categoryId,manageCategoryLevel;

    private String categoryNameErp;

    private String addTime;

    private Integer creator,status;

    /**
     * 注册证号
     */
    private String registerNumber;

    private OperateSkuVo operateSku;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public OperateSkuVo getOperateSku() {
        return operateSku;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getManageCategoryLevel() {
        return manageCategoryLevel;
    }

    public void setManageCategoryLevel(Integer manageCategoryLevel) {
        this.manageCategoryLevel = manageCategoryLevel;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNameErp() {
        return categoryNameErp;
    }

    public void setCategoryNameErp(String categoryNameErp) {
        this.categoryNameErp = categoryNameErp;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setOperateSku(OperateSkuVo operateSku) {
        this.operateSku = operateSku;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getSpuNo() {
        return spuNo;
    }

    public void setSpuNo(String spuNo) {
        this.spuNo = spuNo;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public Integer getSpuType() {
        return spuType;
    }

    public void setSpuType(Integer spuType) {
        this.spuType = spuType;
    }

    public Integer getSpuLevel() {
        return spuLevel;
    }

    public void setSpuLevel(Integer spuLevel) {
        this.spuLevel = spuLevel;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }


    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }
}
