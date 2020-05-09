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

import com.report.model.export.AfterExportDetail;
import com.report.model.export.BussinessChanceExport;
import com.report.model.export.BussinessExportDetail;
import com.report.model.export.GoodsCodeExportDetail;
import com.report.service.CommonReportService;
import com.report.service.ServiceReprotService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;

/**
 * <b>Description:</b><br> 客服数据导出
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.com.controller
 * <br><b>ClassName:</b> ServiceReprotController
 * <br><b>Date:</b> 2017年11月30日 上午10:40:28
 */
@Controller
@RequestMapping("/report/service")
public class ServiceReprotController extends BaseController {
	@Autowired
	@Qualifier("serviceReprotService")
	private ServiceReprotService serviceReprotService;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	
	/**
	 * <b>Description:</b><br> 导出工程师列表
	 * @param request
	 * @param response
	 * @param session
	 * @param engineerVo
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午3:54:08
	 */
	@RequestMapping(value = "/exportengineerlist", method = RequestMethod.GET)
	public void exportEngineerList(HttpServletRequest request, HttpServletResponse response,HttpSession session,EngineerVo engineerVo){
		try {
			
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			engineerVo.setCompanyId(user.getCompanyId());
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			
			List<EngineerVo> list = serviceReprotService.exportEngineerList(engineerVo,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/工程师列表.jrxml", list, "工程师列表.xls");
		}catch (Exception e) {
			logger.error("exportengineerlist:", e);
		}
	}
	
	
	
	/**
	 * <b>Description:</b><br> 导出录保卡列表
	 * @param request
	 * @param response
	 * @param session
	 * @param engineerVo
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 下午4:02:11
	 */
	@RequestMapping(value = "/exportgoodswarrantylist", method = RequestMethod.GET)
	public void exportGoodsWarrantyList(HttpServletRequest request, HttpServletResponse response,HttpSession session,SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo){
		try {
			
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			saleorderGoodsWarrantyVo.setCompanyId(user.getCompanyId());
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			
			List<SaleorderGoodsWarrantyVo> list = serviceReprotService.exportGoodsWarrantyList(saleorderGoodsWarrantyVo,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/录保卡明细列表.jrxml", list, "录保卡明细列表.xls");
		}catch (Exception e) {
			logger.error("exportgoodswarrantylist:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出商机列表
	 * @param request
	 * @param response
	 * @param session
	 * @param bussinessChanceVo
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月6日 上午9:27:32
	 */
	@RequestMapping(value = "/exportbussinesschancelist", method = RequestMethod.GET)
	public void exportBussinessChanceList(HttpServletRequest request, HttpServletResponse response,HttpSession session,BussinessChanceVo bussinessChanceVo){
		try {
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			bussinessChanceVo.setCompanyId(user.getCompanyId());
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);//销售
			positionType.add(SysOptionConstant.ID_312);//售后
			List<User> userList = new ArrayList<>();
			List<User> myUserList = commonReportService.getMyUserList(session, positionType, false);
			if(myUserList != null){
				for(User u : myUserList){
					userList.add(u);
				}
			}
			// 查询用户集合
			List<Integer> userIds = new ArrayList<>();
			if (null == bussinessChanceVo.getUserId() || bussinessChanceVo.getUserId() <= 0) {
				
				for(User u : userList){
					userIds.add(u.getUserId());
				}
				
				if (userIds.size() <= 0) {//名下无用户 
					userIds.add(-1);
				}
				bussinessChanceVo.setCreatorId(userIds);
			}else{
				userIds.add(bussinessChanceVo.getUserId());
				List<Integer> traderIdList = new ArrayList<>();
				if (userIds.size() > 0) {
					bussinessChanceVo.setUserIds(userIds);
					traderIdList = commonReportService.getTraderIdListByUserList(userIds, ErpConst.ONE);
				}else{//名下无用户 
					userIds.add(-1);
					bussinessChanceVo.setUserIds(userIds);
				}
				
				if(traderIdList != null && traderIdList.size()>0){
					bussinessChanceVo.setTraderIds(traderIdList);
				}
			}
			
			// 时间处理
			if (null != bussinessChanceVo.getStarttime() && bussinessChanceVo.getStarttime() != "") {
				bussinessChanceVo.setStarttimeLong(DateUtil.convertLong(bussinessChanceVo.getStarttime(), "yyyy-MM-dd"));
			}
			if (null != bussinessChanceVo.getEndtime() && bussinessChanceVo.getEndtime() != "") {
				bussinessChanceVo.setEndtimeLong(DateUtil.convertLong(bussinessChanceVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			
			// 地区处理
			if (null != bussinessChanceVo.getZone() && bussinessChanceVo.getZone() > 0) {
				bussinessChanceVo.setAreaId(bussinessChanceVo.getZone());
			} else if (null != bussinessChanceVo.getCity() && bussinessChanceVo.getCity() > 0) {
				bussinessChanceVo.setAreaId(bussinessChanceVo.getCity());
			} else if (null != bussinessChanceVo.getProvince() && bussinessChanceVo.getProvince() > 0) {
				bussinessChanceVo.setAreaId(bussinessChanceVo.getProvince());
			}
			List<BussinessChanceExport> list = serviceReprotService.exportBussinessChanceList(bussinessChanceVo,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/商机列表.jrxml", list, "商机列表.xls");
		}catch (Exception e) {
			logger.error("exportbussinesschancelist:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出售后列表
	 * @param request
	 * @param response
	 * @param session
	 * @param saleorderGoodsWarrantyVo
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 下午5:37:39
	 */
	@RequestMapping(value = "/exportaftersaleslist", method = RequestMethod.GET)
	public void exportAfterSalesList(HttpServletRequest request, HttpServletResponse response,HttpSession session,AfterSalesVo afterSalesVo){
		try {
			
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			afterSalesVo.setCompanyId(user.getCompanyId());
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			
			List<AfterSalesVo> list = serviceReprotService.exportAfterSalesList(afterSalesVo,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/售后列表.jrxml", list, "售后列表.xls");
		}catch (Exception e) {
			logger.error("exportaftersaleslist:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出售后售后商品明细表
	 * @param request
	 * @param response
	 * @param afterSalesVo
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月22日 上午9:34:05
	 */
	@RequestMapping(value = "/exportAfterGoodsDetailList", method = RequestMethod.GET)
	public void exportAfterGoodsDetailList(HttpServletRequest request, HttpServletResponse response,AfterExportDetail afterExportDetail
			,@RequestParam(required = false, value="startDate") String startDate,
			@RequestParam(required = false, value="endDate") String endDate){
		try {
			
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			afterExportDetail.setCompanyId(user.getCompanyId());
			
			if(StringUtils.isNoneBlank(startDate)){
				afterExportDetail.setStartTime(DateUtil.convertLong(startDate + " 00:00:00",""));
			}
			if(StringUtils.isNoneBlank(endDate)){
				afterExportDetail.setEndTime(DateUtil.convertLong(endDate + " 23:59:59",""));
			}
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			
			List<AfterExportDetail> list = serviceReprotService.exportAfterGoodsDetailList(afterExportDetail,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/售后商品明细.jrxml", list, "售后商品明细.xls");
		}catch (Exception e) {
			logger.error("exportAfterGoodsDetailList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出商机明细
	 * @param request
	 * @param response
	 * @param afterExportDetail
	 * @param startTime
	 * @param endTime
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月22日 下午1:50:04
	 */
	@RequestMapping(value = "/exportBussinessDetailList", method = RequestMethod.GET)
	public void exportBussinessDetailList(HttpServletRequest request, HttpServletResponse response,BussinessExportDetail bussinessExportDetail
			,@RequestParam(required = false, value="startDate") String startDate,
			@RequestParam(required = false, value="endDate") String endDate){
		try {
			
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			bussinessExportDetail.setCompanyId(user.getCompanyId());
			
			if(StringUtils.isNoneBlank(startDate)){
				bussinessExportDetail.setStartTime(DateUtil.convertLong(startDate + " 00:00:00",""));
			}
			if(StringUtils.isNoneBlank(endDate)){
				bussinessExportDetail.setEndTime(DateUtil.convertLong(endDate + " 23:59:59",""));
			}
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_3);
			
			List<BussinessExportDetail> list = serviceReprotService.exportBussinessDetailList(bussinessExportDetail,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/商机明细.jrxml", list, "商机明细.xls");
		}catch (Exception e) {
			logger.error("exportBussinessDetailList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出商品识别码维度明细表
	 * @param request
	 * @param response
	 * @param bussinessExportDetail
	 * @param startDate
	 * @param endDate
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月1日 上午10:35:11
	 */
	@RequestMapping(value = "/exportGoodsCodeDetailList", method = RequestMethod.GET)
	public void exportGoodsCodeDetailList(HttpServletRequest request, HttpServletResponse response,GoodsCodeExportDetail gced
			,@RequestParam(required = false, value="startDate") String startDate,
			@RequestParam(required = false, value="endDate") String endDate){
		try {
			
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			gced.setCompanyId(user.getCompanyId());
			
			if(StringUtils.isNoneBlank(startDate)){
				gced.setStartTime(DateUtil.convertLong(startDate + " 00:00:00",""));
			}
			if(StringUtils.isNoneBlank(endDate)){
				gced.setEndTime(DateUtil.convertLong(endDate + " 23:59:59",""));
			}
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
			
			List<GoodsCodeExportDetail> list = serviceReprotService.exportGoodsCodeDetailList(gced,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/商品识别码维度明细.jrxml", list, "商品识别码维度明细.xls");
		}catch (Exception e) {
			logger.error("exportGoodsCodeDetailList:", e);
		}
	}
}
