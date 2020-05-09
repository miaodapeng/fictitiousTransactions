package com.vedeng.goods.service;

import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;

import java.util.List;

public interface BaseAttributeValueService {

    /**
     * 查询属性值列表
     * @author cooper
     * @param baseAttributeValueVo
     * @param list
     * @return
     */
    List<BaseAttributeValueVo> getBaseAttributeValueVoList(BaseAttributeValueVo baseAttributeValueVo,List<BaseAttributeVo> list);

    /**
     * 根据属性ID查询属性值列表
     * @author cooper
     * @param baseAttributeValueVo
     * @return
     */
    List<BaseAttributeValueVo> getBaseAttributeValueVoListByAttrId(BaseAttributeValueVo baseAttributeValueVo);

    /**
     * 根据分类ID查询分类关联的属性值列表
     * @author cooper
     * @param list
     * @return
     */
    List<BaseAttributeValueVo> getAttrValueListByCategoryId(List<BaseCategoryVo> list);

    /**
     * 查询属性值列表,正叙
     * @author cooper
     * @return
     */
    List<BaseAttributeValueVo> getBaseAttributeValueVoListASC(BaseAttributeValueVo baseAttributeValueVo,List<BaseAttributeVo> list);
}
