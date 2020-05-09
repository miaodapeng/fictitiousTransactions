package com.smallhospital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * T_EL_SKU
 * @author 
 */
public class ElSku implements Serializable {
    private Integer elSkuId;

    /**
     * V_CORE_SKU SKU_ID
     */
    private Integer skuId;

    /**
     * 添加时间
     */
    private Long addTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 添加人
     */
    private Integer creator;

    /**
     * 修改人
     */
    private Integer updator;

    private static final long serialVersionUID = 1L;

    public Integer getElSkuId() {
        return elSkuId;
    }

    public void setElSkuId(Integer elSkuId) {
        this.elSkuId = elSkuId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }
}