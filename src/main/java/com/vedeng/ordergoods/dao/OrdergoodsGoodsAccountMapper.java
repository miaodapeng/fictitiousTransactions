package com.vedeng.ordergoods.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.ordergoods.model.OrdergoodsGoodsAccount;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsAccountVo;

public interface OrdergoodsGoodsAccountMapper {
    int deleteByPrimaryKey(Integer ordergoodsGoodsAccountId);

    int insert(OrdergoodsGoodsAccount ordergoodsGoodsAccount);

    OrdergoodsGoodsAccount selectByPrimaryKey(Integer ordergoodsGoodsAccountId);

    int update(OrdergoodsGoodsAccount ordergoodsGoodsAccount);

	/**
	 * <b>Description:</b><br> 分页获取账号产品
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 下午12:57:28
	 */
	List<OrdergoodsGoodsAccountVo> getAccountGoodsListPage(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 获取产品
	 * @param goodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年5月18日 下午1:12:31
	 */
	OrdergoodsGoodsAccountVo getOrdergoodsAccountGoods(OrdergoodsGoodsAccountVo goodsVo);
	
	/**
	 * 
	 * <b>Description:</b><br> 根据产品Id集合获取产品价格信息集合
	 * @param ordergoodsGoodsAccountVo
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月27日 上午11:17:20
	 */
	List<OrdergoodsGoodsAccountVo> getOrdergoodsAccountGoodsByGoodsList(OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo);
}