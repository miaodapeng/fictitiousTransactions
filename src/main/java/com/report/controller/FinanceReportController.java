package com.report.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.report.model.export.CapitalBillExport;
import com.report.model.export.CapitalBillExportDetail;
import com.report.model.export.InvoiceExportDetail;
import com.report.model.export.SaleOrderExportCw;
import com.report.model.export.SaleOrderExportDetail;
import com.report.service.CommonReportService;
import com.report.service.FinanceReportService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.AccountPeriod;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.Saleorder;

/**
 * <b>Description:</b><br> 财务数据导出
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.com.controller
 * <br><b>ClassName:</b> FinanceReportController
 * <br><b>Date:</b> 2017年11月30日 上午10:34:49
 */
@Controller
@RequestMapping("/report/finance")
public class FinanceReportController extends BaseController {
	
	@Autowired
	@Qualifier("financeReportService")
	private FinanceReportService financeReportService;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	
	/**
	 * <b>Description:</b><br> 客户账期使用记录导出
	 * @param request
	 * @param response
	 * @param session
	 * @param startTime
	 * @param endTime
	 * @param ap
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月26日 下午1:59:55
	 */
	@RequestMapping(value = "/exportCustomerAccount", method = RequestMethod.GET)
	public void exportCustomerAccount(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime,AccountPeriod ap){
		User user = (User)session.getAttribute(Consts.SESSION_USER);
		ap.setCompanyId(user.getCompanyId());
		
		if(StringUtils.isNoneBlank(startTime)){
			ap.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNoneBlank(endTime)){
			ap.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		if (ap.getTraderUserId() != null) {
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(ap.getTraderUserId());}}, ErpConst.TWO);// 1客户，2供应商
			if (traderIdList != null && !traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
				ap.setTraderIdList(traderIdList);
			}
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<AccountPeriod> list = financeReportService.exportCustomerAccount(ap,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/客户账期使用记录.jrxml", list, "客户账期使用记录.xls");
	}
	
	
	/**
	 * <b>Description:</b><br> 供应商账期使用记录导出
	 * @param request
	 * @param response
	 * @param session
	 * @param startTime
	 * @param endTime
	 * @param ap
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月26日 下午1:59:55
	 */
	@RequestMapping(value = "/exportSupplierAccount", method = RequestMethod.GET)
	public void exportSupplierAccount(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime,AccountPeriod ap){
		User user = (User)session.getAttribute(Consts.SESSION_USER);
		ap.setCompanyId(user.getCompanyId());
		
		if (StringUtils.isNoneBlank(startTime)) {
			ap.setStartDate(DateUtil.convertLong(startTime + " 00:00:00", ""));
		}
		if (StringUtils.isNoneBlank(endTime)) {
			ap.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
		}

		if (ap.getTraderUserId() != null) {
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(ap.getTraderUserId());}}, ErpConst.TWO);// 1客户，2供应商
			if (traderIdList != null && !traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
				ap.setTraderIdList(traderIdList);
			}
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<AccountPeriod> list = financeReportService.exportSupplierAccount(ap,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/供应商账期使用记录.jrxml", list, "供应商账期使用记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出收票记录
	 * @param request
	 * @param response
	 * @param invoice
	 * @param startTime
	 * @param endTime
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月26日 下午4:57:54
	 */
	@RequestMapping(value = "/exportIncomeInvoiceList", method = RequestMethod.GET)
	public void exportIncomeInvoiceList(HttpServletRequest request, HttpServletResponse response,Invoice invoice,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user != null){
			invoice.setCompanyId(user.getCompanyId());
		}
		if(StringUtils.isNotBlank(startTime)){
			invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNotBlank(endTime)){
			invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		if(invoice.getDateType() == null){
			invoice.setDateType(1);//默认申请日期
		}
		invoice.setType(SysOptionConstant.ID_503);
		invoice.setValidStatus(1);//审核通过
		invoice.setCompanyId(user.getCompanyId());
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<Invoice> list = financeReportService.exportIncomeInvoiceList(invoice,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/收票记录.jrxml", list, "收票记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出收票详情记录
	 * @param request
	 * @param response
	 * @param invoice
	 * @param startTime
	 * @param endTime
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月27日 上午11:20:11
	 */
	@RequestMapping(value = "/exportIncomeInvoiceDetailList", method = RequestMethod.GET)
	public void exportIncomeInvoiceDetailList(HttpServletRequest request, HttpServletResponse response,Invoice invoice,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user != null){
			invoice.setCompanyId(user.getCompanyId());
		}
		if(StringUtils.isNotBlank(startTime)){
			invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNotBlank(endTime)){
			invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		if(invoice.getDateType() == null){
			invoice.setDateType(1);//默认申请日期
		}
		invoice.setType(SysOptionConstant.ID_503);
		invoice.setValidStatus(1);//审核通过
		invoice.setCompanyId(user.getCompanyId());
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<InvoiceExportDetail> list = financeReportService.exportIncomeInvoiceDetailList(invoice,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/收票详细记录.jrxml", list, "收票详细记录.xlsx");
	}
	
	/**
	 * <b>Description:</b><br> 导出开票列表
	 * @param request
	 * @param response
	 * @param invoice
	 * @param startTime
	 * @param endTime
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月28日 下午3:27:50
	 */
	@RequestMapping(value = "/exportOpenInvoiceList", method = RequestMethod.GET)
	public void exportOpenInvoiceList(HttpServletRequest request, HttpServletResponse response,Invoice invoice,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user != null){
			invoice.setCompanyId(user.getCompanyId());
		}
		if(invoice.getDateType() == null){//日期查询类型
			invoice.setDateType(1);
		}
		if(StringUtils.isNotBlank(startTime)){
			invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNotBlank(endTime)){
			invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		
		invoice.setType(SysOptionConstant.ID_505);//销售开票
		invoice.setCompanyId(user.getCompanyId());
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<Invoice> list = financeReportService.exportOpenInvoiceList(invoice,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/开票列表.jrxml", list, "开票列表.xlsx");
	}
	
	/**
	 * <b>Description:</b><br> 导出开票明细记录
	 * @param request
	 * @param response
	 * @param invoice
	 * @param startTime
	 * @param endTime
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月18日 下午2:53:07
	 */
	/*@RequestMapping(value = "/exportOpenInvoiceDetailList", method = RequestMethod.GET)
	public void exportOpenInvoiceDetailList(HttpServletRequest request, HttpServletResponse response,Invoice invoice,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user != null){
			invoice.setCompanyId(user.getCompanyId());
		}
		if(invoice.getDateType() == null){//日期查询类型
			invoice.setDateType(1);
		}
		if(StringUtils.isNotBlank(startTime)){
			invoice.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNotBlank(endTime)){
			invoice.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		
		invoice.setType(SysOptionConstant.ID_505);//销售开票
		invoice.setCompanyId(user.getCompanyId());
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<InvoiceExportDetail> list = financeReportService.exportOpenInvoiceDetailList(invoice,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/开票明细.jrxml", list, "开票明细.xls");
	}*/

	/**
	 * <b>Description:</b><br> 导出提前开票申请列表
	 * @param request
	 * @param response
	 * @param invoiceApply
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月29日 下午3:17:05
	 */
	@RequestMapping(value = "/exportInvoiceAdvanceApplyList", method = RequestMethod.GET)
	public void exportInvoiceAdvanceApplyList(HttpServletRequest request, HttpServletResponse response,InvoiceApply invoiceApply,
			@RequestParam(required = false, value="startTime")String startTime,@RequestParam(required = false, value="endTime")String endTime){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		invoiceApply.setCompanyId(user.getCompanyId());
		if(invoiceApply.getDateType() == null){//日期查询类型
			invoiceApply.setDateType(1);
		}
		if(StringUtils.isNotBlank(startTime)){
			invoiceApply.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNotBlank(endTime)){
			invoiceApply.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		if(invoiceApply.getTraderUserId()!=null){
			//销售人员所属客户
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(invoiceApply.getTraderUserId());}}, 1);//1客户，2供应商
			if(traderIdList!=null && !traderIdList.isEmpty()){//销售人员无客户，默认不出数据
				invoiceApply.setTraderIdList(traderIdList);
			}
		}
		//初始化默认选择审核中
		if(invoiceApply.getAdvanceValidStatus() == null){
			invoiceApply.setAdvanceValidStatus(0);//默认审核中
		}else if(invoiceApply.getAdvanceValidStatus() == -1){
			invoiceApply.setAdvanceValidStatus(null);//全部
		}
		invoiceApply.setType(SysOptionConstant.ID_505);//销售开票
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<InvoiceApply> list = financeReportService.exportInvoiceAdvanceApplyList(invoiceApply,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/提前开票列表.jrxml", list, "提前开票列表.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出开票申请记录
	 * @param request
	 * @param invoiceApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月3日 上午10:41:15
	 */
	@RequestMapping(value = "/exportInvoiceApplyList", method = RequestMethod.GET)
	public void exportInvoiceApplyList(HttpServletRequest request, HttpServletResponse response,InvoiceApply invoiceApply){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user != null){
			invoiceApply.setCompanyId(user.getCompanyId());
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		if(invoiceApply.getTraderUserId()!=null){
			//销售人员所属客户
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(invoiceApply.getTraderUserId());}}, 1);//1客户，2供应商
			if(traderIdList == null || traderIdList.isEmpty()){//销售人员无客户，默认不出数据
				traderIdList.add(-1);
			}
			invoiceApply.setTraderIdList(traderIdList);
		}
		if(invoiceApply.getValidStatus() == null){
			invoiceApply.setValidStatus(0);//默认审核中
		}
		invoiceApply.setType(SysOptionConstant.ID_505);//销售开票
		List<InvoiceApply> list = financeReportService.exportInvoiceApplyList(invoiceApply,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/开票申请列表.jrxml", list, "开票申请列表.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出资金流水列表
	 * @param request
	 * @param response
	 * @param invoiceApply
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月3日 下午2:08:48
	 */
	@RequestMapping(value = "/exportCapitalBillList", method = RequestMethod.GET)
	public void exportCapitalBillList(HttpServletRequest request, HttpServletResponse response,CapitalBill capitalBill){
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		//获取前一个月日期
		String pre1MonthDate = DateUtil.DatePreMonth(date, -1, null);
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			capitalBill.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			capitalBill.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if ("".equals(request.getParameter("searchBegintimeStr"))) {
			capitalBill.setSearchBegintime(null);
		}
		if ("".equals(request.getParameter("searchEndtimeStr"))) {
			capitalBill.setSearchEndtime(null);
		}
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		
		capitalBill.setIsCapitalBillTotal(1);//查询收入总额,支出总额
		capitalBill.setCompanyId(user.getCompanyId());
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<CapitalBill> list = financeReportService.exportCapitalBillList(capitalBill,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/资金流水记录.jrxml", list, "资金流水记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出银行流水记录
	 * @param request
	 * @param response
	 * @param capitalBill
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月3日 下午4:40:46
	 */
	@RequestMapping(value = "/exportBankBillList", method = RequestMethod.GET)
	public void exportBankBillList(HttpServletRequest request, HttpServletResponse response,BankBill bankBill,
			@RequestParam(required = false, value = "beginTime") String searchBeginTime,
		    @RequestParam(required = false, value = "endTime") String searchEndTime){
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		// 如果有搜索时间就按照搜索时间，如果第一次进来设置默认时间为当前时间前一天
		if (null != searchEndTime && searchEndTime != "") {
		    bankBill.setSearchEndtime(searchEndTime);
		} else {
		    searchEndTime = DateUtil.convertString(DateUtil.sysTimeMillis(), DateUtil.DATE_FORMAT);
		    bankBill.setSearchEndtime(searchEndTime);
		}

		if (null != searchBeginTime && searchBeginTime != "") {
		    bankBill.setSearchBegintime(searchBeginTime);
		} else {
		    searchBeginTime = DateUtil.convertString(DateUtil.sysTimeMillis(), DateUtil.DATE_FORMAT);
		    bankBill.setSearchBegintime(searchBeginTime);
		}
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if(null != user){
			bankBill.setCompanyId(user.getCompanyId());
		}
		List<BankBill> list = financeReportService.exportBankBillList(bankBill,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/bankbill.jrxml", list, "银行流水记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出收款记录
	 * @param request
	 * @param response
	 * @param capitalBill
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月5日 下午5:00:40
	 */
	@RequestMapping(value = "/exportReceiveCapitalBillList", method = RequestMethod.GET)
	public void exportReceiveCapitalBillList(HttpServletRequest request, HttpServletResponse response,CapitalBill capitalBill){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(null != user){
			capitalBill.setCompanyId(user.getCompanyId());
		}
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		//获取当月第一天日期
		String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			capitalBill.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			capitalBill.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if ("".equals(request.getParameter("searchBegintimeStr"))) {
			capitalBill.setSearchBegintime(null);
		}
		if ("".equals(request.getParameter("searchEndtimeStr"))) {
			capitalBill.setSearchEndtime(null);
		}
		if(null != capitalBill.getOptUserId() && capitalBill.getOptUserId()!=-1){
			//销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(capitalBill.getOptUserId());}}, 1);//1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			capitalBill.setTraderIdList(traderIdList);
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
//		capitalBill.setIsCollectionRecordTotal(1);//查询记录总计及总金额
		List<CapitalBillExport> list = financeReportService.exportReceiveCapitalBillList(capitalBill,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/收款记录.jrxml", list, "收款记录.xls");
		
	}
	

	/**
	 * <b>Description:</b><br> 导出收款明细记录
	 * @param request
	 * @param response
	 * @param capitalBill
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月9日 上午9:53:56
	 */
	@RequestMapping(value = "/exportReceiveCapitalDetailList", method = RequestMethod.GET)
	public void exportReceiveCapitalDetailList(HttpServletRequest request, HttpServletResponse response,CapitalBill capitalBill){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(null != user){
			capitalBill.setCompanyId(user.getCompanyId());
		}
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		//获取当月第一天日期
		String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			capitalBill.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			capitalBill.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if ("".equals(request.getParameter("searchBegintimeStr"))) {
			capitalBill.setSearchBegintime(null);
		}
		if ("".equals(request.getParameter("searchEndtimeStr"))) {
			capitalBill.setSearchEndtime(null);
		}
		if(null != capitalBill.getOptUserId() && capitalBill.getOptUserId()!=-1){
			//销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(capitalBill.getOptUserId());}}, 1);//1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			capitalBill.setTraderIdList(traderIdList);
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		List<CapitalBillExportDetail> list = financeReportService.exportReceiveCapitalDetailList(capitalBill,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/收款明细记录.jrxml", list, "收款明细记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出付款记录
	 * @param request
	 * @param response
	 * @param capitalBill
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月11日 下午3:19:08
	 */
	@RequestMapping(value = "/exportPayCapitalBillList", method = RequestMethod.GET)
	public void exportPayCapitalBillList(HttpServletRequest request, HttpServletResponse response,CapitalBill capitalBill){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		//获取当月第一天日期
		String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
		
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			capitalBill.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			capitalBill.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} else {
			capitalBill.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if ("".equals(request.getParameter("searchBegintimeStr"))) {
			capitalBill.setSearchBegintime(null);
		}
		if ("".equals(request.getParameter("searchEndtimeStr"))) {
			capitalBill.setSearchEndtime(null);
		}
		
//		capitalBill.setIsPaymentRecordTotal(1);//查询订单已付款总额，本次付款总额
		capitalBill.setCompanyId(user.getCompanyId());
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		
		List<CapitalBill> list = financeReportService.exportPayCapitalBillList(capitalBill,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/付款记录.jrxml", list, "付款记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出付款申请记录
	 * @param request
	 * @param response
	 * @param payApply
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月11日 下午5:49:26
	 */
	@RequestMapping(value = "/exportPayApplyList", method = RequestMethod.GET)
	public void exportPayApplyList(HttpServletRequest request, HttpServletResponse response,PayApply payApply){
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		//获取当月第一天日期
		String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
		
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			payApply.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		} else {
			payApply.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			payApply.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} else {
			payApply.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		payApply.setCompanyId(user.getCompanyId());
		List<PayApply> list = financeReportService.exportPayApplyList(payApply,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/付款申请记录.jrxml", list, "付款申请记录.xls");
	}
	
	/**
	 * <b>Description:</b><br> 账期申请记录导出（客户-供应商）
	 * @param request
	 * @param response
	 * @param tapa
	 * @param startTime
	 * @param endTime
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月12日 下午4:30:11
	 */
	@RequestMapping(value = "/exportAccountPeriodList", method = RequestMethod.GET)
	public void exportAccountPeriodList(HttpServletRequest request, HttpServletResponse response,TraderAccountPeriodApply tapa,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime){
		if(StringUtils.isNoneBlank(startTime)){
			tapa.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
		}
		if(StringUtils.isNoneBlank(endTime)){
			tapa.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
		}
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		tapa.setCompanyId(user.getCompanyId());
		
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
		
		List<TraderAccountPeriodApply> list = financeReportService.exportAccountPeriodList(tapa,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/帐期申请记录.jrxml", list, "帐期申请记录.xls");
	}
	
	
	/**
	 * <b>Description:</b><br> 导出财务销售订单列表
	 * @param request
	 * @param response
	 * @param saleorder
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月17日 下午2:29:07
	 */
	@RequestMapping(value = "/exportFinanceSaleOrderList", method = RequestMethod.GET)
	public void exportFinanceSaleOrderList(HttpServletRequest request, HttpServletResponse response,Saleorder saleorder){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		saleorder.setOrderType(0);
		saleorder.setCompanyId(user.getCompanyId());
		
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		//获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);
		saleorder.setSaleUserList(userList);
		
		List<Integer> traderIdList = new ArrayList<>();
		//归属销售查询客户
		List<Integer> userIds = new ArrayList<>();
		if(null != saleorder.getOptUserId() && saleorder.getOptUserId()!=-1){
			//销售人员所属客户（即当前订单操作人员）
			userIds.add(saleorder.getOptUserId());
			traderIdList = commonReportService.getTraderIdListByUserList(userIds, ErpConst.ONE);// 1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}else if(null != user.getPositType() && user.getPositType()==310){//销售
			//销售人员所属客户（即当前订单操作人员）
			if(null != userList && userList.size() > 0){
				for(User u : userList){
					userIds.add(u.getUserId());
				}
				traderIdList = commonReportService.getTraderIdListByUserList(userIds, ErpConst.ONE);// 1客户，2供应商
			}
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}
		
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
		List<SaleOrderExportCw> list= financeReportService.exportFinanceSaleOrderList(saleorder,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/财务销售订单列表.jrxml", list, "财务销售订单列表.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出财务销售订单明细记录
	 * @param request
	 * @param response
	 * @param saleorder
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月17日 下午4:02:44
	 */
	@RequestMapping(value = "/exportFinanceSaleOrderDetailList", method = RequestMethod.GET)
	public void exportFinanceSaleOrderDetailList(HttpServletRequest request, HttpServletResponse response,Saleorder saleorder){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		saleorder.setOrderType(0);
		saleorder.setCompanyId(user.getCompanyId());
		
		if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		//获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);
		saleorder.setSaleUserList(userList);
		
		List<Integer> traderIdList = new ArrayList<>();
		//归属销售查询客户
		List<Integer> userIds = new ArrayList<>();
		if(null != saleorder.getOptUserId() && saleorder.getOptUserId()!=-1){
			//销售人员所属客户（即当前订单操作人员）
			userIds.add(saleorder.getOptUserId());
			traderIdList = commonReportService.getTraderIdListByUserList(userIds, ErpConst.ONE);// 1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}else if(null != user.getPositType() && user.getPositType()==310){//销售
			//销售人员所属客户（即当前订单操作人员）
			if(null != userList && userList.size() > 0){
				for(User u : userList){
					userIds.add(u.getUserId());
				}
				traderIdList = commonReportService.getTraderIdListByUserList(userIds, ErpConst.ONE);// 1客户，2供应商
			}
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}
		
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
		List<SaleOrderExportDetail> list= financeReportService.exportFinanceSaleOrderDetailList(saleorder,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/财务销售订单明细记录.jrxml", list, "财务销售订单明细记录.xls");
	}
	
}
