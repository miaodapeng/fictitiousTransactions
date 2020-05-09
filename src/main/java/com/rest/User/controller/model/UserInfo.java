package com.rest.User.controller.model;

import java.io.Serializable;

/**
 * 用户基本信息
 * @author wayne.liu
 * @description 
 * @date 2019/6/20 14:32
 */
public class UserInfo implements Serializable {


    private static final long serialVersionUID = -5749268110413317288L;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
