package com.report.service;

import java.util.List;

import com.report.model.export.CapitalBillExport;
import com.report.model.export.CapitalBillExportDetail;
import com.report.model.export.InvoiceExportDetail;
import com.report.model.export.SaleOrderExportCw;
import com.report.model.export.SaleOrderExportDetail;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.AccountPeriod;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.Saleorder;

/**
 * <b>Description:</b><br> 财务数据Service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.service.impl
 * <br><b>ClassName:</b> FinanceReportService
 * <br><b>Date:</b> 2017年11月30日 上午10:42:22
 */
public interface FinanceReportService extends BaseService {

	/**
	 * <b>Description:</b><br> 客户账期使用记录导出
	 * @param ap
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月26日 下午2:00:55
	 */
	List<AccountPeriod> exportCustomerAccount(AccountPeriod ap, Page page);

	/**
	 * <b>Description:</b><br> 供应商账期使用记录导出
	 * @param ap
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月26日 下午4:27:42
	 */
	List<AccountPeriod> exportSupplierAccount(AccountPeriod ap, Page page);

	/**
	 * <b>Description:</b><br> 导出收票记录
	 * @param invoice
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月26日 下午4:58:21
	 */
	List<Invoice> exportIncomeInvoiceList(Invoice invoice, Page page);

	/**
	 * <b>Description:</b><br> 导出收票详情记录
	 * @param invoice
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月27日 上午11:39:13
	 */
	List<InvoiceExportDetail> exportIncomeInvoiceDetailList(Invoice invoice, Page page);

	/**
	 * <b>Description:</b><br> 导出开票列表
	 * @param invoice
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月28日 下午3:30:59
	 */
	List<Invoice> exportOpenInvoiceList(Invoice invoice, Page page);

	/**
	 * <b>Description:</b><br> 导出提前开票申请列表
	 * @param invoiceApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月29日 下午3:17:14
	 */
	List<InvoiceApply> exportInvoiceAdvanceApplyList(InvoiceApply invoiceApply, Page page);

	/**
	 * <b>Description:</b><br> 导出开票申请记录
	 * @param invoiceApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月3日 上午11:08:52
	 */
	List<InvoiceApply> exportInvoiceApplyList(InvoiceApply invoiceApply, Page page);

	/**
	 * <b>Description:</b><br> 导出资金流水列表
	 * @param request
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月3日 下午2:46:18
	 */
	List<CapitalBill> exportCapitalBillList(CapitalBill capitalBill, Page page);

	/**
	 * <b>Description:</b><br> 导出银行流水记录
	 * @param bankBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月3日 下午4:44:19
	 */
	List<BankBill> exportBankBillList(BankBill bankBill, Page page);

	/**
	 * <b>Description:</b><br> 导出收款记录
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月5日 下午5:04:24
	 */
	List<CapitalBillExport> exportReceiveCapitalBillList(CapitalBill capitalBill, Page page);

	/**
	 * <b>Description:</b><br> 导出收款明细记录
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月9日 上午9:54:07
	 */
	List<CapitalBillExportDetail> exportReceiveCapitalDetailList(CapitalBill capitalBill, Page page);

	/**
	 * <b>Description:</b><br> 导出付款记录
	 * @param capitalBill
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月11日 下午3:21:31
	 */
	List<CapitalBill> exportPayCapitalBillList(CapitalBill capitalBill, Page page);

	/**
	 * <b>Description:</b><br> 导出付款申请记录
	 * @param payApply
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月11日 下午5:49:52
	 */
	List<PayApply> exportPayApplyList(PayApply payApply, Page page);

	/**
	 * <b>Description:</b><br> 账期申请记录导出（客户-供应商）
	 * @param tapa
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月12日 下午4:30:47
	 */
	List<TraderAccountPeriodApply> exportAccountPeriodList(TraderAccountPeriodApply tapa, Page page);

	/**
	 * <b>Description:</b><br> 导出财务销售订单列表
	 * @param saleorder
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月17日 下午2:38:53
	 */
	List<SaleOrderExportCw> exportFinanceSaleOrderList(Saleorder saleorder, Page page);

	/**
	 * <b>Description:</b><br> 导出财务销售订单明细记录
	 * @param saleorder
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月17日 下午4:04:23
	 */
	List<SaleOrderExportDetail> exportFinanceSaleOrderDetailList(Saleorder saleorder, Page page);

	/**
	 * <b>Description:</b><br> 导出开票明细
	 * @param invoice
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月18日 下午2:54:02
	 */
	/*List<InvoiceExportDetail> exportOpenInvoiceDetailList(Invoice invoice, Page page);*/

}
