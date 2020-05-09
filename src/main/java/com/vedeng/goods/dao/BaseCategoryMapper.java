package com.vedeng.goods.dao;


import com.vedeng.goods.model.BaseCategory;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("baseCategoryMapper")
public interface BaseCategoryMapper {
    int deleteByPrimaryKey(Integer baseCategoryId);

    int insert(BaseCategory record);

    int insertSelective(BaseCategory record);

    BaseCategoryVo selectByPrimaryKey(Integer baseCategoryId);

    int updateByPrimaryKeySelective(BaseCategory record);

    int updateByPrimaryKey(BaseCategory record);

    /**
     * @description 据属性Id获取已应用的商品分类列表
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    List<BaseCategoryVo> getBaseCategoryListPage(Map<String,Object> map);

    /**
     * @description 获取商品分类列表
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    List<BaseCategoryVo> getFirstCategoryListPage(Map<String,Object> map);

    /**
     * @description 获取商品分类列表
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    List<BaseCategoryVo> getSecondCategoryList(Map<String,Object> map);

    /**
     * @description 获取商品分类列表
     * @author cooper
     * @param
     * @date 2019/5/23
     */
    List<BaseCategoryVo> getThirdCategoryList(Map<String,Object> map);

    /**
     * @description 根据条件获取下级分类
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    List<BaseCategoryVo> getCategoryListByIds(Map<String,Object> map);

    /**
     * @description 根据三级分类ID获取该三级分类的信息
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    List<BaseCategoryVo> getthirdCategoryListById(BaseCategoryVo baseCategoryVo);

    /**
     * @description 删除一级商品分类
     * @author cooper
     * @param map
     * @return
     * @date 2019/5/24
     */
    Integer deleteCategory(Map<String,Object> map);

    /**
     * @description 验证分类是否重复
     * @author cooper
     * @param baseCategoryVo
     * @return
     * @date 2019/5/24
     */
    Integer checkRepeatCategory(BaseCategoryVo baseCategoryVo);

    /**
     * @description 根据三级分类的ID获取一级分类>二级分类>三级分类
     * @param thirdCategoryId
     * @author cooper
     * @date 2019/5/24
     * @return
     */
    String getOrganizedCategoryNameById(Integer thirdCategoryId);

    /**
     * @description 根据关键词获取商品关联的分类ID列表
     * @param keyWords
     * @author cooper
     * @date 2019/5/24
     * @return
     */
    List<Integer> getCategoryIdByKeyWords(String keyWords);

    /**
     * @description 根据关键词和分类ID获取分类列表
     * @param keyWords
     * @author cooper
     * @date 2019/5/24
     * @return
     */
    List<BaseCategoryVo> getCategoryListByKeyWords(@Param("keyWords") String keyWords , @Param("list") List<Integer> list);

    List<BaseCategoryVo> getSecondCategoryListForNameQuery(Map<String, Object> map);

    List<BaseCategoryVo> getThirdCategoryListForNameQuery(Map<String, Object> map);

}