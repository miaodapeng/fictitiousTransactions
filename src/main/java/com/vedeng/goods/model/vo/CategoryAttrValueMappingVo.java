package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.CategoryAttrValueMapping;

public class CategoryAttrValueMappingVo extends CategoryAttrValueMapping {

    /**引用的分类名称 BASE_CATEGORY_NAME**/
    private String baseCategoryName;

    /**引用的属性名称 BASE_ATTRIBUTE_NAME**/
    private String baseAttributeName;

    /**引用的属性值  BASE_ATTR_VALUE**/
    private String baseAttrValue;

    public String getBaseCategoryName() {
        return baseCategoryName;
    }

    public void setBaseCategoryName(String baseCategoryName) {
        this.baseCategoryName = baseCategoryName;
    }

    public String getBaseAttributeName() {
        return baseAttributeName;
    }

    public void setBaseAttributeName(String baseAttributeName) {
        this.baseAttributeName = baseAttributeName;
    }

    public String getBaseAttrValue() {
        return baseAttrValue;
    }

    public void setBaseAttrValue(String baseAttrValue) {
        this.baseAttrValue = baseAttrValue;
    }
}
