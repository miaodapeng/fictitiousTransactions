package com.vedeng.goods.service;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.model.BaseAttribute;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;

import java.util.List;
import java.util.Map;

/**
 * @description 属性信息
 * @author bill
 * @param
 * @date 2019/5/8
 */
public interface BaseAttributeService {

	/**
	 * @description 查询属性基本信息
	 * @author bill
	 * @param
	 * @date 2019/5/8
	 */
	BaseAttributeVo getBaseAttributeByParam(Map<String, Object> param);

	/**
	 * @description 保存属性信息
	 * @author bill
	 * @param
	 * @date 2019/5/8
	 */
	ResultInfo saveAttribute(BaseAttributeVo baseAttributeVo);

	/**
	 * @description 属性列表
	 * @author bill
	 * @param
	 * @date 2019/5/9
	 */
	List<BaseAttributeVo> getBaseAttributeInfoListPage(BaseAttributeVo baseAttributeVo, Page page);

	/**
	 * @description 单位信息
	 * @author bill
	 * @param
	 * @date 2019/5/13
	 */
	List<Map<String, Object>> getUnitInfoMap(Map<String, Object> paramMap);

	/**
	 * @description 删除
	 * @author bill
	 * @param
	 * @date 2019/5/15
	 */
    ResultInfo delAttribute(Map<String, Object> paramMap);

    /**
     * @description 获取所有属性和属性值
     * @author bill
     * @param
     * @date 2019/5/17
     */
    List<BaseAttributeVo> getAllAttributeByParam();

	/**
	 * @description 获取分类引用的属性列表
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
    List<BaseAttributeVo> getAttributeListByCategoryId(List<BaseCategoryVo> list);

	/**
	 * 处理属性与属性值的归属
	 * @return
	 */
    List<BaseAttributeVo> doAttrAndValue(List<BaseAttributeVo> list , List<BaseAttributeValueVo> baseAttributeValueVoList);

	/**
	 * 根据ID查询属性
	 * @param baseAttributeVoId
	 * @return
	 */
	BaseAttribute selectByPrimaryKey(Integer baseAttributeVoId);
}
