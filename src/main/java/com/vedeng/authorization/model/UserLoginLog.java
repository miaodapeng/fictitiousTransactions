package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 员工登录登出日志bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> UserLoginLog
 * <br><b>Date:</b> 2017年4月25日 上午11:07:16
 */
public class UserLoginLog implements Serializable{
    private Integer userLoginLogId;

    private Integer userId;

    private String username;

    private String ip;

    private Integer accessType;

    private Long accessTime;

    private String agentInfo;

    public Integer getUserLoginLogId() {
        return userLoginLogId;
    }

    public void setUserLoginLogId(Integer userLoginLogId) {
        this.userLoginLogId = userLoginLogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public Long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public String getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(String agentInfo) {
        this.agentInfo = agentInfo == null ? null : agentInfo.trim();
    }
}