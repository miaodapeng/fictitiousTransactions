package com.vedeng.ordergoods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.ordergoods.model.OrdergoodsCategory;

public interface OrdergoodsCategoryMapper {
    int insert(OrdergoodsCategory ordergoodsCategory);

    OrdergoodsCategory selectByPrimaryKey(Integer ordergoodsCategoryId);

    int updateByPrimaryKey(OrdergoodsCategory ordergoodsCategory);

	/**
	 * <b>Description:</b><br> 获取分类
	 * @param iparentId
	 * @param ordergoodsStoreId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 上午10:49:22
	 */
	List<OrdergoodsCategory> getOrdergoodsCategory(@Param("parentId")Integer parentId, @Param("ordergoodsStoreId")Integer ordergoodsStoreId);
	
	/**
	 * <b>Description:</b><br> 获取分类信息
	 * @param ordergoodsCategory
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月25日 下午2:02:48
	 */
	OrdergoodsCategory getOrdergoodsCategoryByCate(OrdergoodsCategory ordergoodsCategory);

	/**
	 * <b>Description:</b><br> 获取一级分类
	 * @param ordergoodsStoreId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月26日 下午3:11:15
	 */
	List<OrdergoodsCategory> getRootOrdergoodsCategory(@Param("ordergoodsStoreId")Integer ordergoodsStoreId);

	String getCategoryNamesByIds(@Param("categoryIds")List<Integer> categoryIds);
}