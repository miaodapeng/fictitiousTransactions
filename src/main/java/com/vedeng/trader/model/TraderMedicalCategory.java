package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderMedicalCategory implements Serializable{
    private Integer traderMedicalCategoryId;

    private Integer traderId;

    private Integer traderType;

    private Integer medicalCategoryId;

    private Integer medicalCategoryLevel;

    public Integer getTraderMedicalCategoryId() {
        return traderMedicalCategoryId;
    }

    public void setTraderMedicalCategoryId(Integer traderMedicalCategoryId) {
        this.traderMedicalCategoryId = traderMedicalCategoryId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getTraderType() {
        return traderType;
    }

    public void setTraderType(Integer traderType) {
        this.traderType = traderType;
    }

    public Integer getMedicalCategoryId() {
        return medicalCategoryId;
    }

    public void setMedicalCategoryId(Integer medicalCategoryId) {
        this.medicalCategoryId = medicalCategoryId;
    }

    public Integer getMedicalCategoryLevel() {
        return medicalCategoryLevel;
    }

    public void setMedicalCategoryLevel(Integer medicalCategoryLevel) {
        this.medicalCategoryLevel = medicalCategoryLevel;
    }
}