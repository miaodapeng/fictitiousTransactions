package com.vedeng.goods.service;

import com.vedeng.order.model.GoodsData;
import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Description:</b><br> 产品数据
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> dbcenter
 * <br><b>PackageName:</b> com.vedeng.service.common
 * <br><b>ClassName:</b> GoodsDataService
 * <br><b>Date:</b> 2017年9月4日 上午10:30:54
 */
public interface GoodsDataService {
	/**
	 * <b>Description:</b><br> 产品库存总数（目前库存中的总数量）
	 * @param goodsId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月4日 下午2:19:24
	 */
	public Integer getGoodsStockNum(Integer goodsId, Integer companyId);
/*	*//**
	 * 根据对象列表查询批量查询产品库存总数（目前库存中的总数量）
	 * <b>Description:</b><br>
	 * @param goodsId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2018年7月3日 下午2:42:38
	 *//*
	public Integer getGoodsStockNumByList(Integer goodsId,Integer companyId);*/
	/**
	 * <b>Description:</b><br> 批量查询产品库存总数（目前库存中的总数量）
	 * @param goodsIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月24日 下午2:21:10
	 */
	public List<GoodsData> getGoodsStockNumList(List<Integer> goodsIds, Integer companyId);

	/**
	 * <b>Description:</b><br> 产品占用数量（订单需求量中，（预付款>=需付款）的订单所需要的商品数量(减去退货的产品数量)，不包含直发和已发货商品,不含备货订单）
	 * @param goodsId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月4日 下午2:20:25
	 */
	public Integer getGoodsOccupyNum(Integer goodsId, Integer companyId);

	/**
	 * <b>Description:</b><br> 批量查询产品占用数量（订单需求量中，（预付款>=需付款）的订单所需要的商品数量，不包含直发和已发货商品,不含备货订单）
	 * @param goodsIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月24日 下午3:02:42
	 */
	public List<GoodsData> getGoodsOccupyNumList(List<Integer> goodsId, Integer companyId);


	public Integer getGoodsId(String sku);


}
