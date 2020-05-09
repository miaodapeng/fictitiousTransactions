package com.task.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SearchModel;
import com.vedeng.common.page.Page;

public interface SalesPerformanceTaskService {

	/**
	 * <b>Description:</b><br> 更新客户业绩
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午2:59:33
	 */
	ResultInfo updateSalesPerformanceTrader(SearchModel searchModel,Page page);
	
	/**
	 * <b>Description:</b><br> 分页获取客户业绩
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午3:46:32
	 */
	Map<String, Object> getSalesPerformanceTraderListPage(SearchModel searchModel,Page page);

	/**
	 * <b>Description:</b><br> 更新话务业绩
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午2:59:35
	 */
	ResultInfo updateSalesPerformanceComm(SearchModel searchModel,Page page);
	
	/**
	 * <b>Description:</b><br> 分页获取话务业绩
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午3:46:48
	 */
	Map<String, Object> getSalesPerformanceCommListPage(SearchModel searchModel,Page page);

	/**
	 * <b>Description:</b><br> 更新转化率
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午2:59:37
	 */
	ResultInfo updateSalesPerformanceRate(SearchModel searchModel,Page page);
	
	/**
	 * <b>Description:</b><br> 分页获取转化率
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月4日 下午3:46:55
	 */
	Map<String, Object> getSalesPerformanceRateListPage(SearchModel searchModel,Page page);

	/**
	 * <b>Description:</b><br> 业绩明细
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月4日 下午5:44:58
	 */
	ResultInfo updateSaleorders(SearchModel searchModel, Page page);

	/**
	 * <b>Description:</b><br> 品牌
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月4日 下午5:45:31
	 */
	ResultInfo updateSaleBrand(SearchModel searchModel, Page page);

	/**
	 * <b>Description:</b><br> 呼叫中心通话信息补充（通话时长、录音地址)
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月8日 上午9:24:57
	 */
	ResultInfo callRecordInfoSync(SearchModel searchModel, Page page) throws Exception;
	
	/**
	 * <b>Description:</b><br> 分页获取本月呼叫记录
	 * @param searchModel
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年6月8日 上午9:27:35
	 */
	Map<String, Object> getCallRecordInfoSyncListPage(SearchModel searchModel,Page page) throws Exception;


	ResultInfo generateSaleorderData(SearchModel searchModel);

	ResultInfo generateSaleorderBdCount(SearchModel searchModel);

	ResultInfo generateSaleorderBdSort(SearchModel searchModel);
}
