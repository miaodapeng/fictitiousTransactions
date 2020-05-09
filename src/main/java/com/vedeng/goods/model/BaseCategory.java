package com.vedeng.goods.model;

import java.util.Date;

public class BaseCategory {
    /**   BASE_CATEGORY_ID **/
    private Integer baseCategoryId;

    /** 分类名称  BASE_CATEGORY_NAME **/
    private String baseCategoryName;

    /** 分类别名  BASE_CATEGORY_NICKNAME **/
    private String baseCategoryNickname;

    /** 分类级别（1开始依次递增）  BASE_CATEGORY_LEVEL **/
    private Integer baseCategoryLevel;

    /** 分类类型：1:医疗器械；2:非医疗器械  BASE_CATEGORY_TYPE **/
    private Integer baseCategoryType;

    /** 品名举例  BASE_CATEGORY_EXAMPLE_PRODUCT **/
    private String baseCategoryExampleProduct;

    /** 描述  BASE_CATEGORY_DESCRIBE **/
    private String baseCategoryDescribe;

    /** 预期用途  BASE_CATEGORY_INTENDED_USE **/
    private String baseCategoryIntendedUse;

    /** 是否删除 0否 1是  IS_DELETED **/
    private Integer isDeleted;

    /** 父级id  PARENT_ID **/
    private Integer parentId;

    /** 当前节点之前的所有节点，ID拼接以英文逗号分割  TREENODES **/
    private String treenodes;

    /** 创建人ID  CREATOR **/
    private Integer creator;

    /** 更新人ID  UPDATER **/
    private Integer updater;

    /** 更新时间  MOD_TIME **/
    private Date modTime;

    /** 添加时间  ADD_TIME **/
    private Date addTime;

    /**     BASE_CATEGORY_ID   **/
    public Integer getBaseCategoryId() {
        return baseCategoryId;
    }

    /**     BASE_CATEGORY_ID   **/
    public void setBaseCategoryId(Integer baseCategoryId) {
        this.baseCategoryId = baseCategoryId;
    }

    /**   分类名称  BASE_CATEGORY_NAME   **/
    public String getBaseCategoryName() {
        return baseCategoryName;
    }

    /**   分类名称  BASE_CATEGORY_NAME   **/
    public void setBaseCategoryName(String baseCategoryName) {
        this.baseCategoryName = baseCategoryName == null ? null : baseCategoryName.trim();
    }

    /**   分类别名  BASE_CATEGORY_NICKNAME   **/
    public String getBaseCategoryNickname() {
        return baseCategoryNickname;
    }

    /**   分类别名  BASE_CATEGORY_NICKNAME   **/
    public void setBaseCategoryNickname(String baseCategoryNickname) {
        this.baseCategoryNickname = baseCategoryNickname == null ? null : baseCategoryNickname.trim();
    }

    /**   分类级别（1开始依次递增）  BASE_CATEGORY_LEVEL   **/
    public Integer getBaseCategoryLevel() {
        return baseCategoryLevel;
    }

    /**   分类级别（1开始依次递增）  BASE_CATEGORY_LEVEL   **/
    public void setBaseCategoryLevel(Integer baseCategoryLevel) {
        this.baseCategoryLevel = baseCategoryLevel;
    }

    /**   分类类型：1:医疗器械；2:非医疗器械  BASE_CATEGORY_TYPE   **/
    public Integer getBaseCategoryType() {
        return baseCategoryType;
    }

    /**   分类类型：1:医疗器械；2:非医疗器械  BASE_CATEGORY_TYPE   **/
    public void setBaseCategoryType(Integer baseCategoryType) {
        this.baseCategoryType = baseCategoryType;
    }

    /**   品名举例  BASE_CATEGORY_EXAMPLE_GOODS   **/
    public String getBaseCategoryExampleProduct() {
        return baseCategoryExampleProduct;
    }

    /**   品名举例  BASE_CATEGORY_EXAMPLE_GOODS   **/
    public void setBaseCategoryExampleProduct(String baseCategoryExampleProduct) {
        this.baseCategoryExampleProduct = baseCategoryExampleProduct == null ? null : baseCategoryExampleProduct.trim();
    }

    /**   描述  BASE_CATEGORY_DESCRIBE   **/
    public String getBaseCategoryDescribe() {
        return baseCategoryDescribe;
    }

    /**   描述  BASE_CATEGORY_DESCRIBE   **/
    public void setBaseCategoryDescribe(String baseCategoryDescribe) {
        this.baseCategoryDescribe = baseCategoryDescribe == null ? null : baseCategoryDescribe.trim();
    }

    /**   预期用途  BASE_CATEGORY_INTENDED_USE   **/
    public String getBaseCategoryIntendedUse() {
        return baseCategoryIntendedUse;
    }

    /**   预期用途  BASE_CATEGORY_INTENDED_USE   **/
    public void setBaseCategoryIntendedUse(String baseCategoryIntendedUse) {
        this.baseCategoryIntendedUse = baseCategoryIntendedUse == null ? null : baseCategoryIntendedUse.trim();
    }

    /**   是否删除 0否 1是  IS_DELETED   **/
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**   是否删除 0否 1是  IS_DELETED   **/
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**   父级id  PARENT_ID   **/
    public Integer getParentId() {
        return parentId;
    }

    /**   父级id  PARENT_ID   **/
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**   当前节点之前的所有节点，ID拼接以英文逗号分割  TREENODES   **/
    public String getTreenodes() {
        return treenodes;
    }

    /**   当前节点之前的所有节点，ID拼接以英文逗号分割  TREENODES   **/
    public void setTreenodes(String treenodes) {
        this.treenodes = treenodes == null ? null : treenodes.trim();
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