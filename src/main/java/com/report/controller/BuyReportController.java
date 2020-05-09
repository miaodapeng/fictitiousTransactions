package com.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.report.service.BuyReportService;
import com.report.service.CommonReportService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;

/**
 * <b>Description:</b><br> 采购业务数据
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.com.controller
 * <br><b>ClassName:</b> BuyReportController
 * <br><b>Date:</b> 2017年11月30日 上午10:39:40
 */
@Controller
@RequestMapping("/report/buy")
public class BuyReportController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(BuyReportController.class);

	@Autowired
	@Qualifier("buyReportService")
	private BuyReportService buyReportService;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	
	/**
	 * <b>Description:</b><br> 导出备货管理列表
	 * @param request
	 * @param response
	 * @param goods
	 * @param session
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月27日 下午3:18:39
	 */
	@RequestMapping(value = "/exportbhmanagelist", method = RequestMethod.GET)
	public void exportBHManageList(HttpServletRequest request, HttpServletResponse response, GoodsVo goodsVo,HttpSession session) {
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			goodsVo.setCompanyId(user.getCompanyId());
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			List<GoodsVo> list = buyReportService.exportBHManageList(goodsVo,request,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/备货计划管理.jrxml", list, "备货计划管理.xls");
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出供应商列表
	 * @param request
	 * @param response
	 * @param goodsVo
	 * @param session
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月4日 上午11:16:36
	 */
	@RequestMapping(value = "/exportsupplierlist", method = RequestMethod.GET)
	public void exportSupplierList(HttpServletRequest request, HttpServletResponse response, TraderSupplierVo traderSupplierVo,HttpSession session){
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			traderSupplierVo.setCompanyId(user.getCompanyId());
			
			// 查询所有职位类型为311的员工
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_311);//采购
			List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);
			List<Integer> traderList = new ArrayList<>();
			
			//归属销售查询客户
			List<Integer> userIds = new ArrayList<>();
			if(ObjectUtils.notEmpty(traderSupplierVo.getHomePurchasing())){
				userIds.add(traderSupplierVo.getHomePurchasing());
				traderList = buyReportService.getTraderIdListByUserList(userIds,ErpConst.TWO);
			}else{
				if(null != userList && userList.size() > 0){
					for(User u : userList){
						userIds.add(u.getUserId());
					}
					traderList = buyReportService.getTraderIdListByUserList(userIds,ErpConst.TWO);
				}
			}
			if(traderList!=null && traderList.size()>0){
				traderSupplierVo.setTraderList(traderList);
			}else{
				traderList.add(-1);
				traderSupplierVo.setTraderList(traderList);
			}
			traderSupplierVo.setTraderList(traderList);
			
			//时间
			String queryStartTime=request.getParameter("queryStartTime");
			if(queryStartTime!=null&&!"".equals(queryStartTime)){
				traderSupplierVo.setStartTime(DateUtil.convertLong(queryStartTime, "yyyy-MM-dd"));
			}
			String queryEndTime=request.getParameter("queryEndTime");
			if(queryEndTime!=null&&!"".equals(queryEndTime)){
				traderSupplierVo.setEndTime(DateUtil.convertLong(queryEndTime+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			
			//地区 
			if (ObjectUtils.notEmpty(traderSupplierVo.getZone())) {
				traderSupplierVo.setAreaId(traderSupplierVo.getZone());
			} else if (ObjectUtils.notEmpty(traderSupplierVo.getProvince()) && ObjectUtils.notEmpty(traderSupplierVo.getCity()) && ObjectUtils.isEmpty(traderSupplierVo.getZone())) {
				traderSupplierVo.setAreaId(traderSupplierVo.getCity());
			} else if (ObjectUtils.notEmpty(traderSupplierVo.getProvince()) && ObjectUtils.isEmpty(traderSupplierVo.getCity()) && ObjectUtils.isEmpty(traderSupplierVo.getZone())) {
				traderSupplierVo.setAreaId(traderSupplierVo.getProvince());
			} else {
				traderSupplierVo.setAreaId(null);
			}
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			List<TraderSupplierVo> list = buyReportService.exportSupplierList(traderSupplierVo,request,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/供应商列表.jrxml", list, "供应商列表.xls");
		} catch (Exception e) {
			logger.error("exportsupplierlist:", e);
		}
	}
	
	
	/**
	 * <b>Description:</b><br> 导出采购售后列表
	 * @param request
	 * @param response
	 * @param session
	 * @param afterSalesVo
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月8日 上午10:24:02
	 */
	@RequestMapping(value = "/exportbuyorderaftersaleslist", method = RequestMethod.GET)
	public void exportBuyorderAfterSalesList(HttpServletRequest request, HttpServletResponse response,HttpSession session,AfterSalesVo afterSalesVo){
		try {
			
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			afterSalesVo.setCompanyId(user.getCompanyId());
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			
			List<AfterSalesVo> list = buyReportService.exportBuyorderAfterSalesList(afterSalesVo,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/采购售后列表.jrxml", list, "采购售后列表.xls");
		}catch (Exception e) {
			logger.error("exportbuyorderaftersaleslist:", e);
		}
	}
}
