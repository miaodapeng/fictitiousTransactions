package com.vedeng.goods.model;

import java.io.Serializable;

public class RBrandJCategory implements Serializable{
    private Integer rBrandJCategoryId;

    private Integer brandId;

    private Integer categoryId;

    public Integer getrBrandJCategoryId() {
        return rBrandJCategoryId;
    }

    public void setrBrandJCategoryId(Integer rBrandJCategoryId) {
        this.rBrandJCategoryId = rBrandJCategoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}