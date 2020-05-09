package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.model.CategoryAttribute;
import com.vedeng.goods.model.CategoryAttributeValue;

/**
 * <b>Description:</b><br> 属性管理接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.service
 * <br><b>ClassName:</b> CategoryAttributeService
 * <br><b>Date:</b> 2017年5月17日 下午2:51:27
 */
public interface CategoryAttributeService {
	
	/**
	 * <b>Description:</b><br> 查询产品分类属性列表（分页）
	 * @param categoryAttribute
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:51:09
	 */
	Map<String,Object> getCateAttributeListPage(CategoryAttribute categoryAttribute,Page page);
	
	/**
	 * <b>Description:</b><br> 查詢属性信息
	 * @param categoryAttributeId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:51:14
	 */
	CategoryAttribute getCateAttributeByKey(CategoryAttribute categoryAttribute);
	
	
	/**
	 * <b>Description:</b><br> 保存属性信息
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:51:31
	 */
	ResultInfo<?> saveCateAttribute(CategoryAttribute categoryAttribute);
	
	
	/**
	 * <b>Description:</b><br> 编辑属性信息保存
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:51:35
	 */
	ResultInfo<?> editCateAttribute(CategoryAttribute categoryAttribute);
	
	
	/**
	 * <b>Description:</b><br> 删除属性信息
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:51:37
	 */
	ResultInfo<?> delCateAttribute(CategoryAttribute categoryAttribute);
	
	/**
	 * <b>Description:</b><br> 属性值添加、编辑保存
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月19日 下午1:56:54
	 */
	ResultInfo<?> updateCateAttributeValue(CategoryAttributeValue categoryAttributeValue);
	
	/**
	 * <b>Description:</b><br> 获取属性对应值
	 * @param categoryAttributeValue
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月19日 下午3:02:49
	 */
	List<CategoryAttributeValue> getCateAttrValueByKey(CategoryAttributeValue categoryAttributeValue);

	/**
	 * <b>Description:</b><br> 根据产品分类ID获取属性列表
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月22日 下午7:58:16
	 */
	List<CategoryAttribute> getAttributeByCategoryId(CategoryAttribute categoryAttribute);

}
