package com.vedeng.ordergoods.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.ordergoods.model.vo.OrdergoodsStoreAccountVo;

public interface OrdergoodsStoreAccountMapper {
    int insert(OrdergoodsStoreAccount ordergoodsStoreAccount);

    OrdergoodsStoreAccountVo selectByPrimaryKey(Integer ordergoodsStoreAccountId);

    int updateByPrimaryKey(OrdergoodsStoreAccount ordergoodsStoreAccount);

	/**
	 * <b>Description:</b><br> 店铺账号
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月29日 下午2:17:43
	 */
	List<OrdergoodsStoreAccountVo> getOrdergoodsAccountListPage(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 查询所有联系人ID
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月29日 下午2:40:22
	 */
	List<Integer> getTraderCotactIds(@Param("ordergoodsStoreId")Integer ordergoodsStoreId);

	/**
	 * <b>Description:</b><br> 获取账号
	 * @param ordergoodsStoreAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月8日 下午5:49:43
	 */
	OrdergoodsStoreAccount getOrdergoodsStoreAccount(OrdergoodsStoreAccount ordergoodsStoreAccount);
	
	/**
	 * 
	 * <b>Description:</b><br> 根据客户联系人ID集合获取网站账号信息
	 * @param traderContactIds
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月23日 下午12:57:19
	 */
	List<OrdergoodsStoreAccountVo> getOrdergoodsStoreAccountByTraderContactIds(@Param("traderContactIds")List<Integer> traderContactIds);
}