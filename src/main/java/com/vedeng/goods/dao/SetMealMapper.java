package com.vedeng.goods.dao;

import com.vedeng.goods.model.SetMeal;

import javax.inject.Named;

@Named("setMealMapper")
public interface SetMealMapper {
    int deleteByPrimaryKey(Integer setMealId);

    int insert(SetMeal record);

    int insertSelective(SetMeal record);

    SetMeal selectByPrimaryKey(Integer setMealId);

    int updateByPrimaryKeySelective(SetMeal record);

    int updateByPrimaryKey(SetMeal record);
}