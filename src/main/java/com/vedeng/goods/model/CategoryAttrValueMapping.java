package com.vedeng.goods.model;

import java.util.Date;

public class CategoryAttrValueMapping {

    /** 主键id  CATEGORY_ATTR_VALUE_MAPPING_ID **/
    private Integer categoryAttrValueMappingId;

    /** 分类id  BASE_CATEGORY_ID **/
    private Integer baseCategoryId;

    /** 属性id  BASE_ATTRIBUTE_ID **/
    private Integer baseAttributeId;

    /** 属性值id  BASE_ATTRIBUTE_VALUE_ID **/
    private Integer baseAttributeValueId;

    /** 0 未删除 1.删除  IS_DELETED **/
    private Integer isDeleted;

    /** 创建人ID  CREATOR **/
    private Integer creator;

    /** 更新人ID  UPDATER **/
    private Integer updater;

    /** 更新时间  MOD_TIME **/
    private Date modTime;

    /** 添加时间  ADD_TIME **/
    private Date addTime;

    /**   主键id  CATEGORY_ATTR_VALUE_MAPPING_ID   **/
    public Integer getCategoryAttrValueMappingId() {
        return categoryAttrValueMappingId;
    }

    /**   主键id  CATEGORY_ATTR_VALUE_MAPPING_ID   **/
    public void setCategoryAttrValueMappingId(Integer categoryAttrValueMappingId) {
        this.categoryAttrValueMappingId = categoryAttrValueMappingId;
    }

    /**   分类id  BASE_CATEGORY_ID   **/
    public Integer getBaseCategoryId() {
        return baseCategoryId;
    }

    /**   分类id  BASE_CATEGORY_ID   **/
    public void setBaseCategoryId(Integer baseCategoryId) {
        this.baseCategoryId = baseCategoryId;
    }

    /**   属性id  BASE_ATTRIBUTE_ID   **/
    public Integer getBaseAttributeId() {
        return baseAttributeId;
    }

    /**   属性id  BASE_ATTRIBUTE_ID   **/
    public void setBaseAttributeId(Integer baseAttributeId) {
        this.baseAttributeId = baseAttributeId;
    }

    /**   属性值id  BASE_ATTRIBUTE_VALUE_ID   **/
    public Integer getBaseAttributeValueId() {
        return baseAttributeValueId;
    }

    /**   属性值id  BASE_ATTRIBUTE_VALUE_ID   **/
    public void setBaseAttributeValueId(Integer baseAttributeValueId) {
        this.baseAttributeValueId = baseAttributeValueId;
    }

    /**   0 未删除 1.删除  IS_DELETED   **/
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**   0 未删除 1.删除  IS_DELETED   **/
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

    /**   更新时间  MOD_TIME   **/
    public Date getModTime() {
        return modTime;
    }

    /**   更新时间  MOD_TIME   **/
    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    /**   添加时间  ADD_TIME   **/
    public Date getAddTime() {
        return addTime;
    }

    /**   添加时间  ADD_TIME   **/
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}