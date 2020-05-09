package com.vedeng.goods.dao;

import com.vedeng.goods.model.vo.SetMealSkuMappingVo;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("setMealSkuMappingExtendMapper")
public interface SetMealSkuMappingExtendMapper {

    /**
     *  根据套餐Id获取商品关联信息列表
     * @param setMealSkuMappingVo
     * @Author Cooper.xu
     * @return
     */
    List<SetMealSkuMappingVo> getSetMealSkuMappingVoList(SetMealSkuMappingVo setMealSkuMappingVo);

    /**
     *  批量获取科室名称列表
     * @param list
     * @Author Cooper.xu
     * @return
     */
    List<SetMealSkuMappingVo> getSetMealSkuDepartmentVoList(List<SetMealSkuMappingVo> list);

    /**
     *  根据套餐Id删除套餐详情列表
     * @param map
     * @Author Cooper.xu
     * @return
     */
    Integer deleteSetMealSkuMapping(Map<String,Object> map);

    /**
     *  批量插入套餐详情列表
     * @param list
     * @Author Cooper.xu
     * @return
     */
    Integer insertBatch(List<SetMealSkuMappingVo> list);

    /**
     *  根据套餐绑定的商品ID获取该商品所有的科室信息(不管商品是否删除或其他状态)
     * @param list
     * @Author Cooper.xu
     * @return
     */
    List<Map<String, Object>> getDepartmentByskuIds(List<SetMealSkuMappingVo> list);
}