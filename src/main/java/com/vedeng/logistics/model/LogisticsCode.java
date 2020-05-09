package com.vedeng.logistics.model;

public class LogisticsCode {
    private Integer logisticsCodeId;

    private String name;

    private String code;

    public Integer getLogisticsCodeId() {
        return logisticsCodeId;
    }

    public void setLogisticsCodeId(Integer logisticsCodeId) {
        this.logisticsCodeId = logisticsCodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}