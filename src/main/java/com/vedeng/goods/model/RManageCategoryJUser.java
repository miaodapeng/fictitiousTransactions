package com.vedeng.goods.model;

import java.io.Serializable;

public class RManageCategoryJUser implements Serializable{
    private Integer rManageCategoryJUserId;

    private Integer manageCategory;

    private Integer userId;
    
    private String username;

    public Integer getrManageCategoryJUserId() {
        return rManageCategoryJUserId;
    }

    public void setrManageCategoryJUserId(Integer rManageCategoryJUserId) {
        this.rManageCategoryJUserId = rManageCategoryJUserId;
    }

    public Integer getManageCategory() {
        return manageCategory;
    }

    public void setManageCategory(Integer manageCategory) {
        this.manageCategory = manageCategory;
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
		this.username = username;
	}

}