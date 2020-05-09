package com.vedeng.goods.service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.model.BaseAttribute;
import com.vedeng.goods.model.CategoryAttrValueMapping;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import com.vedeng.goods.model.vo.CategoryAttrValueMappingVo;

import java.util.List;

/**
 * @description 分类
 * @author bill
 * @param
 * @date 2019/5/9
 */
public interface BaseCategoryService {


	/**
	 * @description 查询分类信息
	 * @author cooper
	 * @param
	 * @date 2019/5/24
	 */
	BaseCategoryVo getBaseCategoryByParam(Integer baseCategoryId);

	/**
	 * @description 保存分类信息
	 * @author cooper
	 * @param
	 * @date 2019/5/24
	 */
    ResultInfo saveBaseCategory(BaseCategoryVo baseCategoryVo);

	/**
	 * @description 获取商品分类列表
	 * @author cooper
	 * @param
	 * @date 2019/5/23
	 */
	List<BaseCategoryVo> getCategoryListPage(BaseCategoryVo baseCategoryVo, Page page);

	/**
	 * @description 获取属性信息
	 * @author bill
	 * @param
	 * @date 2019/5/17
	 */
	List<BaseAttribute> getAttributeInfo(Integer baseCategoryId);

	/**
	 * @description 根据属性Id获取已应用的商品分类列表
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	List<BaseCategoryVo> getBaseCategoryListPageByAttr(Integer attrId, Page page);

	/**
	 * @description 根据条件获取下级分类
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	List<BaseCategoryVo> getCategoryListByIds(List<BaseCategoryVo> list,Integer level);

	/**
	 * @description 根据三级分类ID获取该三级分类的信息
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	List<BaseCategoryVo> getthirdCategoryListById(BaseCategoryVo baseCategoryVo);

	/**
	 * @description 根据分类Id删除分类以及所有关联的分类及属性
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	Integer deleteCategory(List<BaseCategoryVo> firstCategoryVoList, List<BaseCategoryVo> secondCategoryVoList,
		 List<BaseCategoryVo> thirdBasegoryVoList, User user);

	/**
	 * @description 查询三级分类下关联的属性列表
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	List<CategoryAttrValueMappingVo> getCategoryAttrValueMappingVoList(List<BaseCategoryVo> list);

	/**
	 * @description 属性及属性值处理,将属性值的Id以@拼接后放入到对应的属性中
	 * @param list
	 * @param baseAttributeValueVoList
	 * @author cooper
	 * @date 2019/5/24
	 * @return
	 */
	List<BaseAttributeVo> doAttrAndValueToString(List<BaseAttributeVo> list , List<BaseAttributeValueVo> baseAttributeValueVoList);

	/**
	 * @description 属性及属性值处理,处理成JSON格式
	 * @param list
	 * @param baseAttributeValueVoList
	 * @author cooper
	 * @date 2019/5/24
	 * @return
	 */
	String doAttrAndValueToJson(List<BaseAttributeVo> list , List<BaseAttributeValueVo> baseAttributeValueVoList);

	/**
	 * @description 属性及属性值处理,处理成List
	 * @param baseCategoryVo
	 * @author cooper
	 * @date 2019/5/24
	 * @return
	 */
	List<BaseAttributeVo> doAttrAndValueToList(BaseCategoryVo baseCategoryVo);
	/**
	 * @description 验证分类字段是否合法
	 * @param baseCategoryVo
	 * @author cooper
	 * @date 2019/5/24
	 * @return
	 */
	ResultInfo checkCategoryField(BaseCategoryVo baseCategoryVo);

	/**
	 * @description 根据三级分类的ID获取一级分类>二级分类>三级分类
	 * @param thirdCategoryId
	 * @author cooper
	 * @date 2019/5/24
	 * @return
	 */
	String getOrganizedCategoryNameById(Integer thirdCategoryId);

    /**
     * @description 查询三级分类下关联的属性以及属性值列表
     * @author cooper
     * @param
     * @date 2019/5/22
     */
    List<CategoryAttrValueMapping> getCategoryAttrValueMappingVoList(Integer thirdCategoryId);

	/**
	 * @description 根据三级分类的ID获取一级分类>二级分类>三级分类
	 * @param keyWords
	 * @author cooper
	 * @date 2019/5/24
	 * @return
	 */
	List<BaseCategoryVo> getCategoryListByKeyWords(String keyWords);
}
