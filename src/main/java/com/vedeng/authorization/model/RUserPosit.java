package com.vedeng.authorization.model;

import java.io.Serializable;

/**
 * <b>Description:</b><br> 员工职位bean
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.model
 * <br><b>ClassName:</b> RUserPosit
 * <br><b>Date:</b> 2017年4月25日 上午11:07:58
 */
public class RUserPosit implements Serializable{
    private Integer userPositId;

    private Integer userId;

    private Integer positionId;

    public Integer getUserPositId() {
        return userPositId;
    }

    public void setUserPositId(Integer userPositId) {
        this.userPositId = userPositId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
}