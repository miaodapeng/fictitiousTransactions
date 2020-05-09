package com.smallhospital.dto;

/**
 * @Author: Daniel
 * @Description: 小医院商品基本分类传输类
 * @Date created in 2019/11/21 2:10 下午
 */
public class ElBaseCategoryDTO {

    /**
     * 类别id
     */
    private Integer baseCategoryId;

    /**
     * 类别名称
     */
    private String baseCategoryName;

    /**
     * 类别所属第几层
     */
    private Integer baseCategoryLevel;

    /**
     * 类别的父类节点id，如果当前分类节点为根节点，那parentId为0
     */
    private Integer parentId;

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

    public Integer getBaseCategoryLevel() {
        return baseCategoryLevel;
    }

    public void setBaseCategoryLevel(Integer baseCategoryLevel) {
        this.baseCategoryLevel = baseCategoryLevel;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ElBaseCategoryDTO{" +
                "baseCategoryId=" + baseCategoryId +
                ", baseCategoryName='" + baseCategoryName + '\'' +
                ", baseCategoryLevel=" + baseCategoryLevel +
                ", parentId=" + parentId +
                '}';
    }
}
