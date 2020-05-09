package com.vedeng.goods.model;

import java.io.Serializable;
import java.util.Date;

public class SetMealSkuMapping implements Serializable {
    /**   SKU_SET_MEAL_MAPPING_ID **/
    private Integer skuSetMealMappingId;

    /** 套餐ID  SET_MEAL_ID **/
    private Integer setMealId;

    /** 商品ID  SKU_ID **/
    private Integer skuId;

    /** 商品标记 1-主推 2-备选 3-配套  SKU_SIGN **/
    private Integer skuSign;

    /** 科室ID拼接字符串，以英文逗号隔开  DEPARTMENT_IDS **/
    private String departmentIds;

    /** 数量  QUANTITY **/
    private Integer quantity;

    /** 用途  PURPOSE **/
    private String purpose;

    /** 备注  REMARK **/
    private String remark;

    /** 是否删除 0否 1是  IS_DELETED **/
    private Integer isDeleted;

    /** 创建人ID  CREATOR **/
    private Integer creator;

    /** 更新人ID  UPDATER **/
    private Integer updater;

    /** 创建时间  ADD_TIME **/
    private Date addTime;

    /** 更新时间  MOD_TIME **/
    private Date modTime;

    /**     SKU_SET_MEAL_MAPPING_ID   **/
    public Integer getSkuSetMealMappingId() {
        return skuSetMealMappingId;
    }

    /**     SKU_SET_MEAL_MAPPING_ID   **/
    public void setSkuSetMealMappingId(Integer skuSetMealMappingId) {
        this.skuSetMealMappingId = skuSetMealMappingId;
    }

    /**   套餐ID  SET_MEAL_ID   **/
    public Integer getSetMealId() {
        return setMealId;
    }

    /**   套餐ID  SET_MEAL_ID   **/
    public void setSetMealId(Integer setMealId) {
        this.setMealId = setMealId;
    }

    /**   商品ID  SKU_ID   **/
    public Integer getSkuId() {
        return skuId;
    }

    /**   商品ID  SKU_ID   **/
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    /**   商品标记 1-主推 2-备选 3-配套  SKU_SIGN   **/
    public Integer getSkuSign() {
        return skuSign;
    }

    /**   商品标记 1-主推 2-备选 3-配套  SKU_SIGN   **/
    public void setSkuSign(Integer skuSign) {
        this.skuSign = skuSign;
    }

    /**   科室ID拼接字符串，以英文逗号隔开  DEPARTMENT_IDS   **/
    public String getDepartmentIds() {
        return departmentIds;
    }

    /**   科室ID拼接字符串，以英文逗号隔开  DEPARTMENT_IDS   **/
    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds == null ? null : departmentIds.trim();
    }

    /**   数量  QUANTITY   **/
    public Integer getQuantity() {
        return quantity;
    }

    /**   数量  QUANTITY   **/
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**   用途  PURPOSE   **/
    public String getPurpose() {
        return purpose;
    }

    /**   用途  PURPOSE   **/
    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    /**   备注  REMARK   **/
    public String getRemark() {
        return remark;
    }

    /**   备注  REMARK   **/
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**   是否删除 0否 1是  IS_DELETED   **/
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**   是否删除 0否 1是  IS_DELETED   **/
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**   创建人ID  CREATOR   **/
    public Integer getCreator() {
        return creator;
    }

    /**   创建人ID  CREATOR   **/
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**   更新人ID  UPDATER   **/
    public Integer getUpdater() {
        return updater;
    }

    /**   更新人ID  UPDATER   **/
    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    /**   创建时间  ADD_TIME   **/
    public Date getAddTime() {
        return addTime;
    }

    /**   创建时间  ADD_TIME   **/
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**   更新时间  MOD_TIME   **/
    public Date getModTime() {
        return modTime;
    }

    /**   更新时间  MOD_TIME   **/
    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }
}