package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.BaseCategory;

import java.util.List;

public class BaseCategoryVo extends BaseCategory {

    /**核心商品数量  CORE_PRODUCT_NUM **/
    private Integer coreProductNum;

    /**临时商品数量  TEMPORARY_PRODUCT_NUM **/
    private Integer temporaryProductNum;

    /**其他商品数量  OTHER_PRODUCT_NUM **/
    private Integer otherProductNum;

    /**商品分类拼接后的名称 CATEGORY_JOIN_NAME**/
    private String categoryJoinName;

    /**商品分类属性配置 ATTRIBUTE_JOIN_NAME**/
    private String attributeJoinName;

    /**商品一级分类列表**/
    private List<BaseCategoryVo> firstCategoryList;

    /**商品二级分类列表**/
    private List<BaseCategoryVo> secondCategoryList;

    /**商品三级分类列表**/
    private List<BaseCategoryVo> thirdCategoryList;

    /**商品分类引用的属性列表**/
    private List<BaseAttributeVo> attributeVoList;

    /**商品分类引用的属性值列表**/
    private List<BaseAttributeValueVo> attributeValueVoList;

    /**商品一级分类名称 FIRST_LEVEL_CATEGORY_NAME**/
    private String firstLevelCategoryName;

    /**商品二级分类名称  SECOND_LEVEL_CATEGORY_NAME**/
    private String secondLevelCategoryName;

    /**三级分类下的属性关联表**/
    private List<CategoryAttrValueMappingVo> categoryAttrValueMappingVoList;

    /**是否需要修改商品状态 **/
    private Integer isEditGoods;

    private String[] baseAttributeId;

    private String[] baseAttributeValueIds;

    public Integer getCoreProductNum() {
       return coreProductNum;
    }

    public void setCoreProductNum(Integer coreProductNum) {
        this.coreProductNum = coreProductNum;
    }

    public Integer getTemporaryProductNum() {
        return temporaryProductNum;
    }

    public void setTemporaryProductNum(Integer temporaryProductNum) {
        this.temporaryProductNum = temporaryProductNum;
    }

    public Integer getOtherProductNum() {
        return otherProductNum;
    }

    public void setOtherProductNum(Integer otherProductNum) {
        this.otherProductNum = otherProductNum;
    }

    public String getCategoryJoinName() {
        return categoryJoinName;
    }

    public void setCategoryJoinName(String categoryJoinName) {
        this.categoryJoinName = categoryJoinName == null ? null : categoryJoinName.trim();
    }

    public String getAttributeJoinName() {
        return attributeJoinName;
    }

    public void setAttributeJoinName(String attributeJoinName) {
        this.attributeJoinName = attributeJoinName == null ? null : attributeJoinName.trim();
    }

    public List<BaseCategoryVo> getFirstCategoryList() {
        return firstCategoryList;
    }

    public void setFirstCategoryList(List<BaseCategoryVo> firstCategoryList) {
        this.firstCategoryList = firstCategoryList;
    }

    public List<BaseCategoryVo> getSecondCategoryList() {
        return secondCategoryList;
    }

    public void setSecondCategoryList(List<BaseCategoryVo> secondCategoryList) {
        this.secondCategoryList = secondCategoryList;
    }

    public List<BaseCategoryVo> getThirdCategoryList() {
        return thirdCategoryList;
    }

    public void setThirdCategoryList(List<BaseCategoryVo> thirdCategoryList) {
        this.thirdCategoryList = thirdCategoryList;
    }

    public List<BaseAttributeVo> getAttributeVoList() {
        return attributeVoList;
    }

    public void setAttributeVoList(List<BaseAttributeVo> attributeVoList) {
        this.attributeVoList = attributeVoList;
    }

    public List<BaseAttributeValueVo> getAttributeValueVoList() {
        return attributeValueVoList;
    }

    public void setAttributeValueVoList(List<BaseAttributeValueVo> attributeValueVoList) {
        this.attributeValueVoList = attributeValueVoList;
    }

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public List<CategoryAttrValueMappingVo> getCategoryAttrValueMappingVoList() {
        return categoryAttrValueMappingVoList;
    }

    public void setCategoryAttrValueMappingVoList(List<CategoryAttrValueMappingVo> categoryAttrValueMappingVoList) {
        this.categoryAttrValueMappingVoList = categoryAttrValueMappingVoList;
    }

    public String[] getBaseAttributeId() {
        return baseAttributeId;
    }

    public void setBaseAttributeId(String[] baseAttributeId) {
        this.baseAttributeId = baseAttributeId;
    }

    public String[] getBaseAttributeValueIds() {
        return baseAttributeValueIds;
    }

    public void setBaseAttributeValueIds(String[] baseAttributeValueIds) {
        this.baseAttributeValueIds = baseAttributeValueIds;
    }

    public Integer getIsEditGoods() {
        return isEditGoods;
    }

    public void setIsEditGoods(Integer isEditGoods) {
        this.isEditGoods = isEditGoods;
    }
}
