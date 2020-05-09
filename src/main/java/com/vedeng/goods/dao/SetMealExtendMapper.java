package com.vedeng.goods.dao;

import com.vedeng.goods.model.SetMeal;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("setMealExtendMapper")
public interface SetMealExtendMapper {

    /**
     *  获取套餐列表
     * @param map
     * @Author Cooper.xu
     * @return
     */
    List<SetMeal> getSetMealListPage(Map<String,Object> map);

    /**
     *  删除套餐信息
     * @param map
     * @Author Cooper.xu
     * @return
     */
    Integer deleteSetMeal(Map<String,Object> map);

    /**
     *  查询套餐信息
     * @param list
     * @Author Cooper.xu
     * @return
     */
    List<SetMeal> getSetMealByIds(List<Integer> list);

    /**
     *  根据名称查询套餐信息，精确查找
     * @param setMealName
     * @param setMealId
     * @Author Cooper.xu
     * @return
     */
    SetMeal getSetMealByName(@Param("setMealName") String setMealName,@Param("setMealId") Integer setMealId);
}