package com.vedeng.system.dao;

import com.vedeng.system.model.ConditionFunction;

public interface ConditionFunctionMapper {
    int deleteByPrimaryKey(Integer conditionFunctionId);

    int insert(ConditionFunction record);

    int insertSelective(ConditionFunction record);

    ConditionFunction selectByPrimaryKey(Integer conditionFunctionId);

    int updateByPrimaryKeySelective(ConditionFunction record);

    int updateByPrimaryKey(ConditionFunction record);
}