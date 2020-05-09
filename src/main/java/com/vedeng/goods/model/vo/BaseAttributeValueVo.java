package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.BaseAttributeValue;

public class BaseAttributeValueVo extends BaseAttributeValue {

    /**
     * 属性值关联分类的数量
     */
    private Integer valueCategoryNum;

    /**关联的属性名称**/
    private String baseAttributeName;

    /**关联的分类Id**/
    private Integer baseCategoryId;

    /**关联的分类名称**/
    private String baseCategoryName;

    /**关联表Id**/
    private Integer categoryAttrValueMappingId;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

    public Integer getValueCategoryNum() {
        return valueCategoryNum;
    }

    public void setValueCategoryNum(Integer valueCategoryNum) {
        this.valueCategoryNum = valueCategoryNum;
    }

    public String getBaseAttributeName() {
        return baseAttributeName;
    }

    public void setBaseAttributeName(String baseAttributeName) {
        this.baseAttributeName = baseAttributeName;
    }

    public Integer getBaseCategoryId() {
        return baseCategoryId;
    }

    public void setBaseCategoryId(Integer baseCategoryId) {
        this.baseCategoryId = baseCategoryId;
    }

    public String getBaseCategoryName() {
        return baseCategoryName;
    }

    public void setBaseCategoryName(String baseCategoryName) {
        this.baseCategoryName = baseCategoryName;
    }

    public Integer getCategoryAttrValueMappingId() {
        return categoryAttrValueMappingId;
    }

    public void setCategoryAttrValueMappingId(Integer categoryAttrValueMappingId) {
        this.categoryAttrValueMappingId = categoryAttrValueMappingId;
    }
}
