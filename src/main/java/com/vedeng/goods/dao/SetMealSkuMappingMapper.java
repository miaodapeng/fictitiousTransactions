package com.vedeng.goods.dao;

import com.vedeng.goods.model.SetMealSkuMapping;

import javax.inject.Named;

@Named("setMealSkuMappingMapper")
public interface SetMealSkuMappingMapper {
    int deleteByPrimaryKey(Integer skuSetMealMappingId);

    int insert(SetMealSkuMapping record);

    int insertSelective(SetMealSkuMapping record);

    SetMealSkuMapping selectByPrimaryKey(Integer skuSetMealMappingId);

    int updateByPrimaryKeySelective(SetMealSkuMapping record);

    int updateByPrimaryKey(SetMealSkuMapping record);
}