package com.vedeng.ordergoods.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrdergoodsLaunchGoal implements Serializable{
    private Integer ordergoodsLaunchGoalId;

    private Integer ordergoodsLaunchId;

    private Long startTime;

    private Long endTime;

    private BigDecimal goalAmount;

    public Integer getOrdergoodsLaunchGoalId() {
        return ordergoodsLaunchGoalId;
    }

    public void setOrdergoodsLaunchGoalId(Integer ordergoodsLaunchGoalId) {
        this.ordergoodsLaunchGoalId = ordergoodsLaunchGoalId;
    }

    public Integer getOrdergoodsLaunchId() {
        return ordergoodsLaunchId;
    }

    public void setOrdergoodsLaunchId(Integer ordergoodsLaunchId) {
        this.ordergoodsLaunchId = ordergoodsLaunchId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }
}