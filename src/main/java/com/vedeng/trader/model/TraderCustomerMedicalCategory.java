package com.vedeng.trader.model;

import java.io.Serializable;

public class TraderCustomerMedicalCategory implements Serializable{
    private Integer traderCustomerMedicalCategoryId;

    private Integer traderCustomerId;

    private Integer medicalCategoryId;

    private Integer medicalCategoryLevel;

    public Integer getTraderCustomerMedicalCategoryId() {
        return traderCustomerMedicalCategoryId;
    }

    public void setTraderCustomerMedicalCategoryId(Integer traderCustomerMedicalCategoryId) {
        this.traderCustomerMedicalCategoryId = traderCustomerMedicalCategoryId;
    }

    public Integer getTraderCustomerId() {
        return traderCustomerId;
    }

    public void setTraderCustomerId(Integer traderCustomerId) {
        this.traderCustomerId = traderCustomerId;
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