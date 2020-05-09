package com.vedeng.goods.model.vo;

import com.vedeng.goods.model.SetMeal;

import java.util.List;

public class SetMealVo extends SetMeal {

    /**创建开始时间  ADD_BEGIN_TIME**/
    private String addBeginTime;

    /**创建结束时间  ADD_END_TIME**/
    private String addEndTime;

    /**更新开始时间  MOD_BEGIN_TIME**/
    private String modBeginTime;

    /**更新结束时间  MOD_END_TIME**/
    private String modEndTime;

    /**排序类型  1-更新时间：由近到远 2-更新时间：由远到近 3-创建时间：由近到远 4-创建时间：由远到近 ORDER_TYPE **/
    private Integer orderType;

    /**套餐详情列表**/
    private List<SetMealSkuMappingVo> setMealSkuMappingVoList;

    public String getAddBeginTime() {
        return addBeginTime;
    }

    public void setAddBeginTime(String addBeginTime) {
        this.addBeginTime = addBeginTime;
    }

    public String getAddEndTime() {
        return addEndTime;
    }

    public void setAddEndTime(String addEndTime) {
        this.addEndTime = addEndTime;
    }

    public String getModBeginTime() {
        return modBeginTime;
    }

    public void setModBeginTime(String modBeginTime) {
        this.modBeginTime = modBeginTime;
    }

    public String getModEndTime() {
        return modEndTime;
    }

    public void setModEndTime(String modEndTime) {
        this.modEndTime = modEndTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<SetMealSkuMappingVo> getSetMealSkuMappingVoList() {
        return setMealSkuMappingVoList;
    }

    public void setSetMealSkuMappingVoList(List<SetMealSkuMappingVo> setMealSkuMappingVoList) {
        this.setMealSkuMappingVoList = setMealSkuMappingVoList;
    }
}
