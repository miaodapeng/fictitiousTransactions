package com.report.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.model.export.QuoteExportDetail;
import com.report.service.CommonReportService;
import com.report.service.SaleReportService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.trader.model.vo.TraderCustomerVo;

/**
 * <b>Description:</b><br> 销售业务数据导出
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.com.controller
 * <br><b>ClassName:</b> SaleReportController
 * <br><b>Date:</b> 2017年11月30日 上午10:40:13
 */
@Controller
@RequestMapping("/report/sale")
public class SaleReportController extends BaseController {
	@Autowired
	@Qualifier("saleReportService")
	private SaleReportService saleReportService;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	
	/**
	 * <b>Description:</b><br> 导出客户列表
	 * @param request
	 * @param response
	 * @param session
	 * @param traderCustomerVo
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月28日 下午4:42:32
	 */
	@RequestMapping(value = "/exportcustomerlist", method = RequestMethod.GET)
	public void exportCustomerList(HttpServletRequest request, HttpServletResponse response,HttpSession session,TraderCustomerVo traderCustomerVo){
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			traderCustomerVo.setCompanyId(user.getCompanyId());
			
			// 查询所有职位类型为310的员工
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);//销售
			List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);
			
			List<Integer> traderList = new ArrayList<>();
			
			//归属销售查询客户
			List<Integer> userIds = new ArrayList<>();
			if(ObjectUtils.notEmpty(traderCustomerVo.getHomePurchasing())){
				userIds.add(traderCustomerVo.getHomePurchasing());
				traderList = commonReportService.getTraderIdListByUserList(userIds,ErpConst.ONE);
			}else{
				if(null != userList && userList.size() > 0){
					for(User u : userList){
						userIds.add(u.getUserId());
					}
					traderList = commonReportService.getTraderIdListByUserList(userIds,ErpConst.ONE);
				}
			}
			if(traderList!=null && traderList.size()>0){
				traderCustomerVo.setTraderList(traderList);
			}else{
				traderList.add(-1);
				traderCustomerVo.setTraderList(traderList);
			}
//			traderCustomerVo.setTraderList(traderList);
			
			//地区 
			if (ObjectUtils.notEmpty(traderCustomerVo.getZone())) {
				traderCustomerVo.setAreaId(traderCustomerVo.getZone());
			} else if (ObjectUtils.notEmpty(traderCustomerVo.getProvince()) && ObjectUtils.notEmpty(traderCustomerVo.getCity()) && ObjectUtils.isEmpty(traderCustomerVo.getZone())) {
				traderCustomerVo.setAreaId(traderCustomerVo.getCity());
			} else if (ObjectUtils.notEmpty(traderCustomerVo.getProvince()) && ObjectUtils.isEmpty(traderCustomerVo.getCity()) && ObjectUtils.isEmpty(traderCustomerVo.getZone())) {
				traderCustomerVo.setAreaId(traderCustomerVo.getProvince());
			} else {
				traderCustomerVo.setAreaId(null);
			}
			//时间
			if(ObjectUtils.notEmpty(traderCustomerVo.getQueryStartTime())){
				traderCustomerVo.setStartTime(DateUtil.convertLong(traderCustomerVo.getQueryStartTime(), "yyyy-MM-dd"));
			}
			if(ObjectUtils.notEmpty(traderCustomerVo.getQueryEndTime())){
				traderCustomerVo.setEndTime(DateUtil.convertLong(traderCustomerVo.getQueryEndTime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			List<TraderCustomerVo> list = saleReportService.exportCustomerList(traderCustomerVo,request,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/客户列表.jrxml", list, "客户列表.xls");
		} catch (Exception e) {
			logger.error("exportcustomerlist:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出报价列表
	 * @param request
	 * @param response
	 * @param session
	 * @param quote
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午4:36:55
	 */
	@RequestMapping(value = "/exportQuoteList", method = RequestMethod.GET)
	public void exportQuoteList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value="beginTime") String beginTime,
			@RequestParam(required = false, value="endTime") String endTime,
			HttpSession session,Quoteorder quote){
		
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_3);
		
		try {
			if(StringUtils.isNotBlank(beginTime)){
				quote.setBeginDate(DateUtil.convertLong(beginTime + " 00:00:00",""));
			}
			if(StringUtils.isNotBlank(endTime)){
				quote.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
			}
			//查询沟通记录
			if(quote.getTimeType()!=null && quote.getTimeType()==2){
				if(quote.getBeginDate()!=null || quote.getEndDate()!=null){//若都为空，则查询全部报价列表，不需要查询沟通记录
					//根据时间获取沟通记录中外键ID
					quote.setKeyIds(commonReportService.getCommunicateRecordByDate(quote.getBeginDate(),quote.getEndDate(), SysOptionConstant.ID_244 + "," + SysOptionConstant.ID_245));
				}
			}
			
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			if(user != null){
				quote.setCompanyId(user.getCompanyId());
			}
			//获取当前销售用户下级职位用户
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> userList = commonReportService.getMyUserList(session, positionType, false);
			
			if(quote.getOptUserId()!=null){
				//销售人员所属客户（即当前报价单操作人员）
				List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>(){{add(quote.getOptUserId());}}, ErpConst.ONE);//1客户，2供应商
				if(traderIdList == null || traderIdList.isEmpty()){//销售人员无客户，默认不出数据
					traderIdList.add(-1);
				}
				quote.setTraderIdList(traderIdList);
			}else if(user.getPositType()!=null && user.getPositType().intValue()==SysOptionConstant.ID_310){//销售
				List<Integer> userIdList = new ArrayList<>();
				for(int i=0;i<userList.size();i++){
					userIdList.add(userList.get(i).getUserId());
				}
				//销售人员所属客户（即当前报价单操作人员）
				List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(userIdList, ErpConst.ONE);//1客户，2供应商
				if(traderIdList == null || traderIdList.isEmpty()){//销售人员无客户，默认不出数据
					traderIdList.add(-1);
				}
				quote.setTraderIdList(traderIdList);
			}
			
			List<Quoteorder> list = saleReportService.exportQuoteList(quote,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/报价列表.jrxml", list, "报价列表.xls");
		} catch (Exception e) {
			logger.error("exportQuoteList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出报价明细
	 * @param request
	 * @param response
	 * @param beginTime
	 * @param endTime
	 * @param session
	 * @param quote
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月11日 上午9:04:23
	 */
	@RequestMapping(value = "/exportQuoteDetailList", method = RequestMethod.GET)
	public void exportQuoteDetailList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "beginTime") String beginTime,
			@RequestParam(required = false, value = "endTime") String endTime, HttpSession session, Quoteorder quote) {
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_3);

		try {
			if (StringUtils.isNotBlank(beginTime)) {
				quote.setBeginDate(DateUtil.convertLong(beginTime + " 00:00:00", ""));
			}
			if (StringUtils.isNotBlank(endTime)) {
				quote.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
			}
			// 查询沟通记录
			if (quote.getTimeType() != null && quote.getTimeType() == 2) {
				if (quote.getBeginDate() != null || quote.getEndDate() != null) {// 若都为空，则查询全部报价列表，不需要查询沟通记录
					// 根据时间获取沟通记录中外键ID
					quote.setKeyIds(commonReportService.getCommunicateRecordByDate(quote.getBeginDate(), quote.getEndDate(), SysOptionConstant.ID_244 + "," + SysOptionConstant.ID_245));
				}
			}

			User user = (User) session.getAttribute(Consts.SESSION_USER);
			if (user != null) {
				quote.setCompanyId(user.getCompanyId());
			}
			// 获取当前销售用户下级职位用户
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> userList = commonReportService.getMyUserList(session, positionType, false);

			if (quote.getOptUserId() != null) {
				// 销售人员所属客户（即当前报价单操作人员）
				List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(new ArrayList<Integer>() {{add(quote.getOptUserId());}}, ErpConst.ONE);// 1客户，2供应商
				if (traderIdList == null || traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
					traderIdList.add(-1);
				}
				quote.setTraderIdList(traderIdList);
			} else if (user.getPositType() != null && user.getPositType().intValue() == SysOptionConstant.ID_310) {// 销售
				List<Integer> userIdList = new ArrayList<>();
				for (int i = 0; i < userList.size(); i++) {
					userIdList.add(userList.get(i).getUserId());
				}
				// 销售人员所属客户（即当前报价单操作人员）
				List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(userIdList, ErpConst.ONE);// 1客户，2供应商
				if (traderIdList == null || traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
					traderIdList.add(-1);
				}
				quote.setTraderIdList(traderIdList);
			}

			List<QuoteExportDetail> list = saleReportService.exportQuoteDetailList(quote, page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/报价明细.jrxml", list, "报价明细列表.xls");
		} catch (Exception e) {
			logger.error("exportQuoteDetailList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出销售订单列表
	 * @param request
	 * @param response
	 * @param saleorder
	 * @param pageNo
	 * @param pageSize
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 上午10:57:09
	 */
	@ResponseBody
	@RequestMapping(value = "exportSaleOrderList")
	public void exportSaleOrderList(HttpServletRequest request, HttpServletResponse response, Saleorder saleorder, 
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		saleorder.setOrderType(0);
		if (null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != "") {
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		saleorder.setCompanyId(user.getCompanyId());
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);
		saleorder.setSaleUserList(userList);
		List<Integer> userIdList = new ArrayList<>();
		if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
			// 销售人员所属客户（即当前订单操作人员）
			userIdList.add(saleorder.getOptUserId());
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(userIdList, ErpConst.ONE);// 1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		} else if (null != user.getPositType() && user.getPositType() == 310) {// 销售
			for(int i=0;i<userList.size();i++){
				userIdList.add(userList.get(i).getUserId());
			}
			// 销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(userIdList, ErpConst.ONE);
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_3);
		List<Saleorder> list = saleReportService.exportSaleOrderList(saleorder,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/销售订单列表.jrxml", list, "销售订单列表.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出销售订单明细列表
	 * @param request
	 * @param response
	 * @param saleorder
	 * @param pageNo
	 * @param pageSize
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 下午5:55:14
	 */
	@ResponseBody
	@RequestMapping(value = "exportSaleOrderDetailList")
	public void exportSaleOrderDetailList(HttpServletRequest request, HttpServletResponse response, Saleorder saleorder, 
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		saleorder.setOrderType(0);
		if (null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != "") {
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		saleorder.setCompanyId(user.getCompanyId());
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);
		saleorder.setSaleUserList(userList);
		List<Integer> userIdList = new ArrayList<>();
		if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
			// 销售人员所属客户（即当前订单操作人员）
			userIdList.add(saleorder.getOptUserId());
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(userIdList, ErpConst.ONE);// 1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		} else if (null != user.getPositType() && user.getPositType() == 310) {// 销售
			for(int i=0;i<userList.size();i++){
				userIdList.add(userList.get(i).getUserId());
			}
			// 销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = commonReportService.getTraderIdListByUserList(userIdList, ErpConst.ONE);
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
		List<SaleorderGoods> list = saleReportService.exportSaleOrderDetailList(saleorder,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/销售订单详情列表.jrxml", list, "销售订单详情列表.xls");
	}
}
