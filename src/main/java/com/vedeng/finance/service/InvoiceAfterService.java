package com.vedeng.finance.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesInvoice;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesInvoiceVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceAfter;

public interface InvoiceAfterService extends BaseService{

	/**
	 * <b>Description:</b><br> 查询财务模块售后列表
	 * @param invoiceAfter
	 * @param page 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月17日 下午4:58:20
	 */
	Map<String,Object> getFinanceAfterListPage(InvoiceAfter invoiceAfter, Page page);

	/**
	 * <b>Description:</b><br> 获取售后订单基本信息-售后申请-所属订单
	 * @param session 
	 * @param invoiceAfter
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月19日 下午4:19:52
	 */
	AfterSalesVo getFinanceAfterSaleDetail(AfterSalesVo afterSalesVo, HttpSession session);

	/**
	 * <b>Description:</b><br> 根据售后ID获取交易对象信息
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月24日 上午10:11:34
	 */
	AfterSalesDetailVo getAfterCapitalBillInfo(AfterSalesDetailVo afterSalesDetailVo);

	/**
	 * <b>Description:</b><br> 根据售后单号查询需要退票的发票信息
	 * @param afterInvoiceVo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月25日 上午9:31:11
	 */
	AfterSalesInvoiceVo getAfterReturnInvoiceInfo(AfterSalesInvoiceVo afterInvoiceVo);

	/**
	 * <b>Description:</b><br> 保存售后退票发票信息
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月26日 下午4:46:10
	 */
	ResultInfo<?> saveAfterReturnInvoice(Invoice invoice);

	/**
	 * <b>Description:</b><br> 获取售后安调开具发票信息
	 * @param afterSalesInstallstionVo
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月31日 上午9:24:37
	 */
	AfterSalesGoodsVo getAfterOpenInvoiceInfoAt(AfterSalesGoodsVo afterSalesGoodsVo);

	/**
	 * <b>Description:</b><br> 保存-财务-售后-安调-开票信息
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月31日 下午2:49:57
	 */
	ResultInfo<?> saveAfterOpenInvoiceAt(Invoice invoice);

	/**
	 * <b>Description:</b><br> 获取售后退票开具发票信息
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月1日 上午9:16:59
	 */
	Invoice getAfterOpenInvoiceInfoTp(Invoice invoice);

	/**
	 * <b>Description:</b><br> 售后退票-重新开票保存
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月1日 下午4:45:13
	 */
	ResultInfo<?> saveAfterOpenInvoiceTp(Invoice invoice);

	/**
	 * <b>Description:</b><br> 编辑售后发票记录信息 
	 * @param afterSalesInvoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月6日 下午2:38:07
	 */
	ResultInfo<?> editAfterInvoice(AfterSalesInvoice afterSalesInvoice);

	/**
	 * <b>Description:</b><br> 销售售后订单退货--确认退款到余额操作
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月21日 上午9:25:27
	 */
	ResultInfo<?> afterThRefundAmountBalance(AfterSales afterSales);
}
