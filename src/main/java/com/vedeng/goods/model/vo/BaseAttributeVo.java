package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.BaseAttribute;

import java.util.List;

public class BaseAttributeVo extends BaseAttribute {

    /**
     * 排序方式
     */
    private Integer timeSort;

    /**
     * 属性值列表
     */
    private List<BaseAttributeValueVo> attrValue;

    /**
     * 已引用的分类数量
     */
    private Integer categoryNum;

    /**
     * 是否已经被引用
     */
    private Integer isUserd;

    /**
     * 被引用分类Id
     */
    private Integer baseCagegoryId;

    /** 属性值Id拼接字符串，以@符号相隔**/
    private String baseAttributeValueIds;


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

    /**
     * spu属性关联id
     */
    private Integer spuAttrId;

    public Integer getSpuAttrId() {
        return spuAttrId;
    }

    public void setSpuAttrId(Integer spuAttrId) {
        this.spuAttrId = spuAttrId;
    }

    public Integer getTimeSort() { return timeSort; }

    public void setTimeSort(Integer timeSort) {
        this.timeSort = timeSort;
    }

    public List<BaseAttributeValueVo> getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(List<BaseAttributeValueVo> attrValue) {
        this.attrValue = attrValue;
    }

    public Integer getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(Integer categoryNum) {
        this.categoryNum = categoryNum;
    }

    public Integer getIsUserd() {
        return isUserd;
    }

    public void setIsUserd(Integer isUserd) {
        this.isUserd = isUserd;
    }

    public Integer getBaseCagegoryId() {
        return baseCagegoryId;
    }

    public void setBaseCagegoryId(Integer baseCagegoryId) {
        this.baseCagegoryId = baseCagegoryId;
    }

    public String getBaseAttributeValueIds() {
        return baseAttributeValueIds;
    }

    public void setBaseAttributeValueIds(String baseAttributeValueIds) {
        this.baseAttributeValueIds = baseAttributeValueIds;
    }
}
