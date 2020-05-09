package com.smallhospital.model;

import java.io.Serializable;
import java.util.Date;

/**
 * T_EL_CONTRACT
 * @author 
 */
public class ElContract implements Serializable {

    private Integer elContractId;

    /**
     * 合同号
     */
    private String contractNumber;

    /**
     * 对应接口的hospitalId 
     */
    private Integer traderId;

    /**
     * 签订日期
     */
    private Long signDate;

    /**
     * 合同开始时间
     */
    private Long contractValidityDateStart;

    /**
     * 合同结束时间
     */
    private Long contractValidityDateEnd;

    /**
     * 合同图片
     */
    private String contractPic;

    /**
     * 备注
     */
    private String remark;

    private Long addTime;

    private Long updateTime;

    private Integer creator;

    private Integer updator;

    /**
     * 合同状态，是否有效，意向合同status为0
     */
    private Integer status;

    private Integer effctiveStatus;

    private Integer productSynStatus;

    private Integer contractSynStatus;

    private Integer confirmStatus;

    private Integer owner;

    /**
     * 审核状态0-未审核 1.审核通过 2.审核不通过
     */
    private Integer auditStatus;

    /**
     * 审核人
     */
    private Integer auditer;

    /**
     * 审核时间
     */
    private Long auditTime;

    /**
     * 审核备注
     */
    private String auditDesc;

    private static final long serialVersionUID = 1L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getElContractId() {
        return elContractId;
    }

    public void setElContractId(Integer elContractId) {
        this.elContractId = elContractId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Long getSignDate() {
        return signDate;
    }

    public void setSignDate(Long signDate) {
        this.signDate = signDate;
    }

    public Long getContractValidityDateStart() {
        return contractValidityDateStart;
    }

    public void setContractValidityDateStart(Long contractValidityDateStart) {
        this.contractValidityDateStart = contractValidityDateStart;
    }

    public Long getContractValidityDateEnd() {
        return contractValidityDateEnd;
    }

    public void setContractValidityDateEnd(Long contractValidityDateEnd) {
        this.contractValidityDateEnd = contractValidityDateEnd;
    }

    public String getContractPic() {
        return contractPic;
    }

    public void setContractPic(String contractPic) {
        this.contractPic = contractPic;
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

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getEffctiveStatus() {
        return effctiveStatus;
    }

    public void setEffctiveStatus(Integer effctiveStatus) {
        this.effctiveStatus = effctiveStatus;
    }

    public Integer getProductSynStatus() {
        return productSynStatus;
    }

    public void setProductSynStatus(Integer productSynStatus) {
        this.productSynStatus = productSynStatus;
    }

    public Integer getContractSynStatus() {
        return contractSynStatus;
    }

    public void setContractSynStatus(Integer contractSynStatus) {
        this.contractSynStatus = contractSynStatus;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditer() {
        return auditer;
    }

    public void setAuditer(Integer auditer) {
        this.auditer = auditer;
    }

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }
}