package com.vedeng.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.order.model.BussinessChance;


public interface BussinessChanceMapper {
    int deleteByPrimaryKey(Integer bussinessChanceId);

    int insert(BussinessChance record);

    int insertSelective(BussinessChance record);

    BussinessChance selectByPrimaryKey(Integer bussinessChanceId);

    int updateByPrimaryKeySelective(BussinessChance record);

    int updateByPrimaryKey(BussinessChance record);
    
}