package com.vedeng.goods.model.vo;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: Duke.li
 * @Date: 2019/7/15 17:41
 */
public class OperateSkuVo {

    private List<String> platfromIdList;

    private List<Attachment> attachmentList;

    private Integer skuId,spuId;

    private String skuNo;

    private String skuName,showName,skuSubtitle;

    private Integer checkStatus;

    private String model,spec,materialCode,baseUnitName;

    private Integer status;

    private Integer isNew;

    private Integer isHot;

    private Integer isSeven;

    private Integer isOnSale;

    private Integer isOpShow;

    private String opDetails;

    private Integer creator;

    private String addTime;

    public String getBaseUnitName() {
        return baseUnitName;
    }

    public void setBaseUnitName(String baseUnitName) {
        this.baseUnitName = baseUnitName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsSeven() {
        return isSeven;
    }

    public void setIsSeven(Integer isSeven) {
        this.isSeven = isSeven;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getIsOpShow() {
        return isOpShow;
    }

    public void setIsOpShow(Integer isOpShow) {
        this.isOpShow = isOpShow;
    }

    public List<String> getPlatfromIdList() {
        return platfromIdList;
    }

    public void setPlatfromIdList(List<String> platfromIdList) {
        this.platfromIdList = platfromIdList;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getSkuSubtitle() {
        return skuSubtitle;
    }

    public void setSkuSubtitle(String skuSubtitle) {
        this.skuSubtitle = skuSubtitle;
    }

    public String getOpDetails() {
        return opDetails;
    }

    public void setOpDetails(String opDetails) {
        this.opDetails = opDetails;
    }
}
