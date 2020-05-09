package com.vedeng.goods.model;

import java.util.Date;

public class SetMeal {
    /**   SET_MEAL_ID **/
    private Integer setMealId;

    /** 属性名称  SET_MEAL_NAME **/
    private String setMealName;

    /** 套餐类型 1-商品配套信息  2-根据科室  3-根据疾病  4-其他  SET_MEAL_TYPE **/
    private Integer setMealType;

    /** 套餐说明  SET_MEAL_DESCRIPT **/
    private String setMealDescript;

    /** 是否删除 0否 1是  IS_DELETED **/
    private Integer isDeleted;

    /** 删除原因  DELETED_REASON **/
    private String deletedReason;

    /** 创建人ID  CREATOR **/
    private Integer creator;

    /** 更新人ID  UPDATER **/
    private Integer updater;

    /** 创建时间  ADD_TIME **/
    private Date addTime;

    /** 更新时间  MOD_TIME **/
    private Date modTime;

    /**     SET_MEAL_ID   **/
    public Integer getSetMealId() {
        return setMealId;
    }

    /**     SET_MEAL_ID   **/
    public void setSetMealId(Integer setMealId) {
        this.setMealId = setMealId;
    }

    /**   属性名称  SET_MEAL_NAME   **/
    public String getSetMealName() {
        return setMealName;
    }

    /**   属性名称  SET_MEAL_NAME   **/
    public void setSetMealName(String setMealName) {
        this.setMealName = setMealName == null ? null : setMealName.trim();
    }

    /**   套餐类型 1-商品配套信息  2-根据科室  3-根据疾病  4-其他  SET_MEAL_TYPE   **/
    public Integer getSetMealType() {
        return setMealType;
    }

    /**   套餐类型 1-商品配套信息  2-根据科室  3-根据疾病  4-其他  SET_MEAL_TYPE   **/
    public void setSetMealType(Integer setMealType) {
        this.setMealType = setMealType;
    }

    /**   套餐说明  SET_MEAL_DESCRIPT   **/
    public String getSetMealDescript() {
        return setMealDescript;
    }

    /**   套餐说明  SET_MEAL_DESCRIPT   **/
    public void setSetMealDescript(String setMealDescript) {
        this.setMealDescript = setMealDescript == null ? null : setMealDescript.trim();
    }

    /**   是否删除 0否 1是  IS_DELETED   **/
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**   是否删除 0否 1是  IS_DELETED   **/
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**   删除原因  DELETED_REASON   **/
    public String getDeletedReason() {
        return deletedReason;
    }

    /**   删除原因  DELETED_REASON   **/
    public void setDeletedReason(String deletedReason) {
        this.deletedReason = deletedReason == null ? null : deletedReason.trim();
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