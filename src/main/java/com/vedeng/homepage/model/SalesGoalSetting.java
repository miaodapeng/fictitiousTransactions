package com.vedeng.homepage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SalesGoalSetting implements Serializable{
    private Integer salesGoalSettingId;

    private Integer companyId;

    private Date year;

    private Integer month;

    private BigDecimal goal;

    private Integer orgId;

    private Integer userId;

    private Long addTime;

    private Integer creator;

    private Long modTime;

    private Integer updater;

    public Integer getSalesGoalSettingId() {
        return salesGoalSettingId;
    }

    public void setSalesGoalSettingId(Integer salesGoalSettingId) {
        this.salesGoalSettingId = salesGoalSettingId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Long getModTime() {
        return modTime;
    }

    public void setModTime(Long modTime) {
        this.modTime = modTime;
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }
}