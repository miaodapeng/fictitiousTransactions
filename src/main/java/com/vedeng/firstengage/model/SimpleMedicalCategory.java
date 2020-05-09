package com.vedeng.firstengage.model;

public class SimpleMedicalCategory {

    private Integer spuId;

    private String skuNo;
    /**
     * 管理分類
     */
    private Integer manageCategory;
    /**
     * 旧分类
     */
    private Integer oldMedicalCategory;
    /**
     * 新分类
     */
    private Integer newMedicalCategory;

    /**
     * 医疗器械
     */
    private Integer instrumentType;

    public Integer getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(Integer instrument) {
        instrumentType = instrument;
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

    public Integer getManageCategory() {
        return manageCategory;
    }

    public void setManageCategory(Integer manageCategory) {
        this.manageCategory = manageCategory;
    }

    public Integer getOldMedicalCategory() {
        return oldMedicalCategory;
    }

    public void setOldMedicalCategory(Integer oldMedicalCategory) {
        this.oldMedicalCategory = oldMedicalCategory;
    }

    public Integer getNewMedicalCategory() {
        return newMedicalCategory;
    }

    public void setNewMedicalCategory(Integer newMedicalCategory) {
        this.newMedicalCategory = newMedicalCategory;
    }
}
