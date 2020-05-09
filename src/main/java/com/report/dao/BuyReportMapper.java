package com.report.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsSafeStock;
import com.vedeng.trader.model.vo.TraderSupplierVo;

@Named("buyReportMapper")
public interface BuyReportMapper {

	/**
	 * <b>Description:</b><br> 获取分类归属
	 * @param categoryId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 上午11:22:38
	 */
	List<User> getUserByCategory(@Param("categoryIds")List<Integer> categoryIds, @Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 根据产品ID查询安全库存信息
	 * @param goodsIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 上午11:25:55
	 */
	List<GoodsSafeStock> getGoodsSafeStock(@Param("goodsIds")List<Integer> goodsIds, @Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 获取产品采购价
	 * @param goodsIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 上午11:27:21
	 */
	List<GoodsChannelPrice> getPurchasePrice(@Param("goodsIds")List<Integer> goodsIds);

	/**
	 * <b>Description:</b><br> 供应商归属
	 * @param traderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午2:04:00
	 */
	List<TraderSupplierVo> getSupplierOwnerData(@Param("traderIds")List<Integer> traderIds);

	/**
	 * <b>Description:</b><br> 供应商沟通数据
	 * @param traderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午2:04:23
	 */
	List<TraderSupplierVo> getSupplierCommunicateData(@Param("traderIds")List<Integer> traderIds);
	
	/**
	 * <b>Description:</b><br> 根据人员查客户
	 * @param userIds
	 * @param one
	 * @param two
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 上午11:19:37
	 */
	public List<Integer> getTraderIdListByUserList(@Param("userIds")List<Integer> userIds, @Param("traderType")Integer traderType);

	/**
	 * <b>Description:</b><br> 通过沟通记录查询客户
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午3:10:55
	 */
	List<Integer> getTraderIdsByCommunicateIds(@Param("list")List<Integer> list);

}
