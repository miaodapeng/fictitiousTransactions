package com.smallhospital.dto;

public class IntentionContractVo {

    private Integer hospitalId;

    private Integer[] productId;

    public Integer[] getProductId() {
        return productId;
    }

    public void setProductId(Integer[] productId) {
        this.productId = productId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
}
