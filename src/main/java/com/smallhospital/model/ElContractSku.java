package com.smallhospital.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * T_EL_CONTRACT_SKU
 * @author 
 */
public class ElContractSku implements Serializable {
    /**
     * contractListId 合同清单项ID
     */
    private Integer elContractSkuId;

    private Integer contractId;

    /**
     * productId  产品ID  V_CORE_SKU
     */
    private Integer skuId;

    /**
     * 合同价格
     */
    private BigDecimal contractPrice;

    /**
     * 备注
     */
    private String remark;

    private Long addTime;

    private Long updateTime;

    private Integer creator;

    private Integer updator;

    private static final long serialVersionUID = 1L;

    public Integer getElContractSkuId() {
        return elContractSkuId;
    }

    public void setElContractSkuId(Integer elContractSkuId) {
        this.elContractSkuId = elContractSkuId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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