package com.vedeng.ordergoods.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.ordergoods.model.OrdergoodsGoods;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;

public interface OrdergoodsGoodsMapper {
    int deleteByPrimaryKey(Integer ordergoodsGoodsId);

    int insert(OrdergoodsGoods ordergoodsGoods);

    OrdergoodsGoods selectByPrimaryKey(Integer ordergoodsGoodsId);

    int update(OrdergoodsGoods ordergoodsGoods);

	/**
	 * <b>Description:</b><br> 分页获取产品
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午3:39:16
	 */
	List<OrdergoodsGoodsVo> getGoodsListPage(Map<String, Object> map);
	
	OrdergoodsGoodsVo getOrdergoodsGoods(OrdergoodsGoodsVo ordergoodsGoodsVo);
}