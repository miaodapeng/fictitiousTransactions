package com.vedeng.system.model;

import java.io.Serializable;

public class QCellCore implements Serializable{
    private Integer qcellcoreId;

    private String phone;

    private String province;

    private String city;

    private String operatpr;

    private String code;

    private String zip;

    public Integer getQcellcoreId() {
        return qcellcoreId;
    }

    public void setQcellcoreId(Integer qcellcoreId) {
        this.qcellcoreId = qcellcoreId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getOperatpr() {
        return operatpr;
    }

    public void setOperatpr(String operatpr) {
        this.operatpr = operatpr == null ? null : operatpr.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip == null ? null : zip.trim();
    }
}