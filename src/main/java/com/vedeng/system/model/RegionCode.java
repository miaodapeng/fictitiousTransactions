package com.vedeng.system.model;

import java.io.Serializable;

public class RegionCode implements Serializable{
    private Integer regionCodeId;

    private String code;

    private String provinceName;

    private String cityName;

    private String zoneName;

    private String streetName;

    public Integer getRegionCodeId() {
        return regionCodeId;
    }

    public void setRegionCodeId(Integer regionCodeId) {
        this.regionCodeId = regionCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName == null ? null : zoneName.trim();
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName == null ? null : streetName.trim();
    }
}