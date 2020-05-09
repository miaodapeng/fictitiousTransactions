package com.vedeng.ordergoods.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.ordergoods.model.OrdergoodsLaunch;
import com.vedeng.ordergoods.model.vo.OrdergoodsLaunchVo;

public interface OrdergoodsLaunchMapper {
    int insert(OrdergoodsLaunch ordergoodsLaunch);

    OrdergoodsLaunchVo selectByPrimaryKey(Integer ordergoodsLaunchId);

    int updateByPrimaryKeySelective(OrdergoodsLaunch ordergoodsLaunch);

	/**
	 * <b>Description:</b><br> 获取投放列表
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月27日 下午4:25:25
	 */
	List<OrdergoodsLaunchVo> getOrdergoodsLaunchListPage(Map<String, Object> map);
}