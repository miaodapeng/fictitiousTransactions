package com.report.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.report.model.export.QuoteExportDetail;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.trader.model.vo.TraderCustomerVo;

/**
 * <b>Description:</b><br> 销售业务数据Service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.service.impl
 * <br><b>ClassName:</b> SaleReportService
 * <br><b>Date:</b> 2017年11月30日 上午10:43:22
 */
public interface SaleReportService extends BaseService {
	/**
	 * <b>Description:</b><br> 导出客户列表
	 * @param traderCustomerVo
	 * @param request
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月28日 下午4:46:52
	 */
	List<TraderCustomerVo> exportCustomerList(TraderCustomerVo traderCustomerVo, HttpServletRequest request, Page page);
	
	/**
	 * <b>Description:</b><br> 导出报价列表
	 * @param quote
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午5:42:40
	 */
	List<Quoteorder> exportQuoteList(Quoteorder quote, Page page);

	/**
	 * <b>Description:</b><br> 导出报价明细列表
	 * @param quote
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月11日 上午9:07:00
	 */
	List<QuoteExportDetail> exportQuoteDetailList(Quoteorder quote, Page page);

	/**
	 * <b>Description:</b><br> 导出销售订单列表
	 * @param saleorder
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 上午11:00:41
	 */
	List<Saleorder> exportSaleOrderList(Saleorder saleorder, Page page);

	/**
	 * <b>Description:</b><br> 导出销售订单明细列表
	 * @param saleorder
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 下午5:55:58
	 */
	List<SaleorderGoods> exportSaleOrderDetailList(Saleorder saleorder, Page page);
}
