package com.vedeng.rabbitmqlogs.model;

import java.util.Date;

public class RabbitmqLogs {
    /** mq主键  RABBITMQ_LOGS_ID **/
    private Integer rabbitmqLogsId;

    /** 订单号  ORDER_NO **/
    private String orderNo;

    /** 错误信息  ERROR_MESSAGE **/
    private String errorMessage;

    /** 重试次数  NUMBER_RETRIES **/
    private Integer numberRetries;

    /** 添加时间  ADD_TIME **/
    private Date addTime;

    /** 修改时间  UPDATE_TIME **/
    private Date updateTime;

    /** 入参信息  ORDER_INFORMATION **/
    private String orderInformation;

    /**   mq主键  RABBITMQ_LOGS_ID   **/
    public Integer getRabbitmqLogsId() {
        return rabbitmqLogsId;
    }

    /**   mq主键  RABBITMQ_LOGS_ID   **/
    public void setRabbitmqLogsId(Integer rabbitmqLogsId) {
        this.rabbitmqLogsId = rabbitmqLogsId;
    }

    /**   订单号  ORDER_NO   **/
    public String getOrderNo() {
        return orderNo;
    }

    /**   订单号  ORDER_NO   **/
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**   错误信息  ERROR_MESSAGE   **/
    public String getErrorMessage() {
        return errorMessage;
    }

    /**   错误信息  ERROR_MESSAGE   **/
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage == null ? null : errorMessage.trim();
    }

    /**   重试次数  NUMBER_RETRIES   **/
    public Integer getNumberRetries() {
        return numberRetries;
    }

    /**   重试次数  NUMBER_RETRIES   **/
    public void setNumberRetries(Integer numberRetries) {
        this.numberRetries = numberRetries;
    }

    /**   添加时间  ADD_TIME   **/
    public Date getAddTime() {
        return addTime;
    }

    /**   添加时间  ADD_TIME   **/
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**   修改时间  UPDATE_TIME   **/
    public Date getUpdateTime() {
        return updateTime;
    }

    /**   修改时间  UPDATE_TIME   **/
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**   入参信息  ORDER_INFORMATION   **/
    public String getOrderInformation() {
        return orderInformation;
    }

    /**   入参信息  ORDER_INFORMATION   **/
    public void setOrderInformation(String orderInformation) {
        this.orderInformation = orderInformation == null ? null : orderInformation.trim();
    }
}