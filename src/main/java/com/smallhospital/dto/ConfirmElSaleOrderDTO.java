package com.smallhospital.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 小医院确认收货传输类
 */
public class ConfirmElSaleOrderDTO {

    /**
     * 物流单号id,express_id
     */
    private String supplyId;

    /**
     * 1：确认收货
     */
    private Integer confirm;

    private String reason;

    @JsonProperty(value = "GHDMX")
    private List<GHDMX>  GHDMX;

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getConfirm() {
        return confirm;
    }

    public void setConfirm(Integer confirm) {
        this.confirm = confirm;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<com.smallhospital.dto.GHDMX> getGHDMX() {
        return GHDMX;
    }

    public void setGHDMX(List<com.smallhospital.dto.GHDMX> GHDMX) {
        this.GHDMX = GHDMX;
    }

    @Override
    public String toString() {
        return "ConfirmElSaleOrderDTO{" +
                "supplyId='" + supplyId + '\'' +
                ", confirm=" + confirm +
                ", reason='" + reason + '\'' +
                ", GHDMX=" + GHDMX +
                '}';
    }
}
