package com.vedeng.goods.model;

import java.util.Date;

public class BaseAttribute {

    /**
     * 主键
     */
    private Integer baseAttributeId;

    /**
     * 属性名称
     */
    private String baseAttributeName;


    /**
     * 是否删除 0否 1是
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 创建人ID
     */
    private Integer creator;

    /**
     * 更新时间
     */
    private Date modTime;

    /**
     * 更新人ID
     */
    private Integer updater;

    /**
     * 是否有单位 0:否 1:是
     */
    private Integer isUnit;



    public Date getModTime() {
        return modTime;
    }

    public void setModTime(Date modTime) {
        this.modTime = modTime;
    }

    public Integer getBaseAttributeId() {
        return baseAttributeId;
    }

    public void setBaseAttributeId(Integer baseAttributeId) {
        this.baseAttributeId = baseAttributeId;
    }

    public String getBaseAttributeName() {
        return baseAttributeName;
    }

    public void setBaseAttributeName(String baseAttributeName) {
        this.baseAttributeName = baseAttributeName == null ? null : baseAttributeName.trim();
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public Integer getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(Integer isUnit) {
        this.isUnit = isUnit;
    }
}