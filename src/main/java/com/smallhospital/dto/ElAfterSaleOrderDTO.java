package com.smallhospital.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 小医院系统售后请求传输类
 */
public class ElAfterSaleOrderDTO {

    private String returnId;

    private String returnNumber;

    private String hospitalId;

    private String remark;

    @JsonProperty(value = "THDMX")
    private List<THDMX> THDMX;

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public List<THDMX> getTHDMX() {
        return THDMX;
    }

    public void setTHDMX(List<THDMX> THDMX) {
        this.THDMX = THDMX;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ElAfterSaleOrderDTO{" +
                "returnId='" + returnId + '\'' +
                ", returnNumber='" + returnNumber + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", remark='" + remark + '\'' +
                ", THDMX=" + THDMX +
                '}';
    }
}
