package com.smallhospital.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vedeng.common.util.JsonMapper;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * T_EL_TRADER
 * @author 
 */
public class ElTrader implements Serializable {
    /**
     * 流水
     */
    private Integer elTraderId;

    /**
     * T_TRADER   TRADER_ID
     */
    private Integer traderId;

    private Integer owner;

    private String  contactName;

    private String  contactNumber1;

    private String  contactNumber2;

    private String  address;

    private String  email;

    private Long addTime;

    private Long updateTime;

    private Integer creator;

    private Integer updator;

    private static final long serialVersionUID = 1L;

    public Integer getElTraderId() {
        return elTraderId;
    }

    public void setElTraderId(Integer elTraderId) {
        this.elTraderId = elTraderId;
    }

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber1() {
        return contactNumber1;
    }

    public void setContactNumber1(String contactNumber1) {
        this.contactNumber1 = contactNumber1;
    }

    public String getContactNumber2() {
        return contactNumber2;
    }

    public void setContactNumber2(String contactNumber2) {
        this.contactNumber2 = contactNumber2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}