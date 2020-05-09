package com.vedeng.ordergoods.dao;

import java.util.List;

import com.vedeng.ordergoods.model.OrdergoodsLaunch;
import com.vedeng.ordergoods.model.OrdergoodsLaunchGoal;

public interface OrdergoodsLaunchGoalMapper {
    int deleteByOrdergoodsLaunchId(Integer ordergoodsLaunchId);

    int insert(OrdergoodsLaunchGoal ordergoodsLaunchGoal);

    OrdergoodsLaunchGoal selectByPrimaryKey(Integer ordergoodsLaunchGoalId);

	List<OrdergoodsLaunchGoal> getOrdergoodsLaunchGoal(OrdergoodsLaunch ordergoodsLaunch);

}