package com.vedeng.goods.dao;


import com.vedeng.goods.model.CategoryAttrValueMapping;
import com.vedeng.goods.model.vo.CategoryAttrValueMappingVo;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("categoryAttrValueMappingMapper")
public interface CategoryAttrValueMappingMapper {
    int deleteByPrimaryKey(Integer categoryAttrValueMappingId);

    int insert(CategoryAttrValueMapping record);

    int insertSelective(CategoryAttrValueMapping record);

    CategoryAttrValueMapping selectByPrimaryKey(Integer categoryAttrValueMappingId);

    int updateByPrimaryKeySelective(CategoryAttrValueMapping record);

    int updateByPrimaryKey(CategoryAttrValueMapping record);

    /**
     * @description 查询三级分类下关联的属性列表
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    List<CategoryAttrValueMappingVo> getCategoryAttrValueMappingVoList(Map<String,Object> map);

    /**
     * @description 删除属性关联信息
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    Integer deleteCategoryAttrValueMappingByCategoryIds(Map<String,Object> map);

    /**
     * @description 批量插入属性关联信息
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    Integer insertCategoryAttrValueMappingBatch(List<CategoryAttrValueMapping> list);

    /**
     * @description 查询三级分类下关联的属性列表
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    List<CategoryAttrValueMapping> getCategoryAttrValueMappingList(Map<String,Object> map);
}