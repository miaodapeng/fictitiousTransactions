package com.smallhospital.dto;

import org.springframework.web.bind.annotation.RequestParam;

public class ELLogisticQueryDto {

    private String logisticsNumber;

    private Integer hospitalId;

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }
}
