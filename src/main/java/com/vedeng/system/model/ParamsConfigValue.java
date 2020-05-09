package com.vedeng.system.model;

import java.io.Serializable;

public class ParamsConfigValue implements Serializable{
    private Integer paramsConfigValueId;

    private Integer paramsConfigId;

    private Integer companyId;

    private String paramsValue;

    public Integer getParamsConfigValueId() {
        return paramsConfigValueId;
    }

    public void setParamsConfigValueId(Integer paramsConfigValueId) {
        this.paramsConfigValueId = paramsConfigValueId;
    }

    public Integer getParamsConfigId() {
        return paramsConfigId;
    }

    public void setParamsConfigId(Integer paramsConfigId) {
        this.paramsConfigId = paramsConfigId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getParamsValue() {
        return paramsValue;
    }

    public void setParamsValue(String paramsValue) {
        this.paramsValue = paramsValue == null ? null : paramsValue.trim();
    }
}