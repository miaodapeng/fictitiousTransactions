package com.report.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;

/**
 * <b>Description:</b><br> 采购业务Service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.service.impl
 * <br><b>ClassName:</b> BuyReportService
 * <br><b>Date:</b> 2017年11月30日 上午10:41:50
 */
public interface BuyReportService extends BaseService {

	/**
	 * <b>Description:</b><br> 导出备货管理列表
	 * @param goodsVo
	 * @param request
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 上午11:16:07
	 */
	List<GoodsVo> exportBHManageList(GoodsVo goodsVo, HttpServletRequest request, Page page);
	
	/**
	 * <b>Description:</b><br> 获取备货 计划产品列表
	 * @param goodsVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 上午11:17:30
	 */
	Map<String, Object> getBHManageList(GoodsVo goodsVo, Page page);

	/**
	 * <b>Description:</b><br> 导出供应商列表
	 * @param traderSupplierVo
	 * @param request
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 上午11:19:55
	 */
	List<TraderSupplierVo> exportSupplierList(TraderSupplierVo traderSupplierVo, HttpServletRequest request, Page page);

	/**
	 * <b>Description:</b><br> 获取供应商列表
	 * @param traderSupplierVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 上午11:20:54
	 */
	Map<String, Object> getSupplierList(TraderSupplierVo traderSupplierVo, Page page);
	
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
	public List<Integer> getTraderIdListByUserList(List<Integer> userIds, Integer traderType);

	/**
	 * <b>Description:</b><br> 导出采购售后列表
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月8日 上午10:24:58
	 */
	List<AfterSalesVo> exportBuyorderAfterSalesList(AfterSalesVo afterSalesVo, Page page);
	
	/**
	 * <b>Description:</b><br> 获取采购售后列表
	 * @param afterSalesVo
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月8日 上午10:25:28
	 */
	Map<String, Object> getBuyorderAfterSalesList(AfterSalesVo afterSalesVo, Page page);

}
