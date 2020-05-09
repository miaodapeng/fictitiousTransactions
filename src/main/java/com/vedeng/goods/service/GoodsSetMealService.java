package com.vedeng.goods.service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.page.Page;
import com.vedeng.goods.model.SetMeal;
import com.vedeng.goods.model.vo.SetMealSkuMappingVo;
import com.vedeng.goods.model.vo.SetMealVo;

import java.util.List;

public interface GoodsSetMealService {


    /**
     *  获取套餐列表
     * @param setMealVo
     * @param page
     * @Author Cooper.xu
     * @return
     */
    List<SetMeal> getSetMealListPage(SetMealVo setMealVo, Page page);

    /**
     *  根据主键获取套餐信息
     * @param setMealId
     * @Author Cooper.xu
     * @return
     */
    SetMeal getSetMealById(Integer setMealId);

    /**
     *  根据套餐Id获取商品关联信息列表
     * @param setMealSkuMappingVo
     * @Author Cooper.xu
     * @return
     */
    List<SetMealSkuMappingVo> getSetMealSkuMappingVoList(SetMealSkuMappingVo setMealSkuMappingVo);

    /**
     *  保存套餐信息
     * @param setMealVo
     * @Author Cooper.xu
     * @return
     */
    Integer saveSetMeal(SetMealVo setMealVo, User user);

    /**
     *  删除套餐信息
     * @param setMealIds
     * @Author Cooper.xu
     * @return
     */
    Integer deleteSetMeal(String setMealIds,String deletedReason, User user);

    /**
     *  查询套餐信息
     * @param setMealIds
     * @Author Cooper.xu
     * @return
     */
    List<SetMeal> getSetMealByIds(String setMealIds);

    /**
     *  根据套餐绑定的商品ID获取该商品所有的科室信息
     * @param setMealSkuMappingVoList
     * @Author Cooper.xu
     * @return
     */
    void getDepartmentByskuIds(List<SetMealSkuMappingVo> setMealSkuMappingVoList);

    /**
     *  根据名称查询套餐信息，精确查找
     * @param setMealName
     * @Author Cooper.xu
     * @return
     */
    SetMeal getSetMealByName(String setMealName, Integer setMealId);
}
