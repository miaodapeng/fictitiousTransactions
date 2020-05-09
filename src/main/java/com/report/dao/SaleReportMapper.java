package com.report.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.vo.TraderCustomerVo;

@Named("saleReportMapper")
public interface SaleReportMapper {

	/**
	 * <b>Description:</b><br> 客户沟通信息
	 * @param traderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月2日 上午9:32:05
	 */
	List<TraderCustomerVo> getCustomerCommunicateData(@Param("traderIds")List<Integer> traderIds);

	/**
	 * <b>Description:</b><br> 客户归属人信息
	 * @param traderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月2日 上午9:59:13
	 */
	List<TraderCustomerVo> getCustomerOwnerData(@Param("traderIds")List<Integer> traderIds);

	/**
	 * <b>Description:</b><br> 根据userid获取dbcenter的trader表的主键
	 * @param rTraderJUser
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月2日 上午11:48:49
	 */
	List<Integer> getRTraderJUserListByUserId(RTraderJUser rTraderJUser);

	/**
	 * <b>Description:</b><br> 通过沟通记录查询客户
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月2日 下午3:37:06
	 */
	List<Integer> getTraderIdsByCommunicateIds(@Param("list")List<Integer> list);

}
