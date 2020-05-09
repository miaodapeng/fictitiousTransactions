package com.vedeng.ordergoods.dao;

import java.util.List;

import com.vedeng.ordergoods.model.OrdergoodsStore;

public interface OrdergoodsStoreMapper {

    int insert(OrdergoodsStore ordergoodsStore);

    OrdergoodsStore selectByPrimaryKey(Integer ordergoodsStoreId);

    int updateByPrimaryKey(OrdergoodsStore ordergoodsStore);

	/**
	 * <b>Description:</b><br> 根据公司ID获取店铺列表
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午9:16:31
	 */
	List<OrdergoodsStore> getStore(Integer companyId);
	
	/**
	 * <b>Description:</b><br> 根据公司名称,公司ID获取店铺列表
	 * @param ordergoodsStore
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 上午9:17:05
	 */
	OrdergoodsStore getStoreByName(OrdergoodsStore ordergoodsStore);
}