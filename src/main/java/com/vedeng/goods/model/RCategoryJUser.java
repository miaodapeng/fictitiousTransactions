package com.vedeng.goods.model;

import java.io.Serializable;

public class RCategoryJUser implements Serializable{
    private Integer rCategoryJUserId;

    private Integer categoryId;

    private Integer userId;
    
    private String username;

    public Integer getrCategoryJUserId() {
        return rCategoryJUserId;
    }

    public void setrCategoryJUserId(Integer rCategoryJUserId) {
        this.rCategoryJUserId = rCategoryJUserId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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