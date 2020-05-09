package com.report.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.report.model.export.BuyExportDetail;
import com.report.model.export.GoodsExport;
import com.report.service.CommonReportService;
import com.report.service.SupplyReportService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.order.model.vo.BuyorderVo;

/**
 * <b>Description:</b><br> 产品相关数据导出
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.controller
 * <br><b>ClassName:</b> GoodsReportController
 * <br><b>Date:</b> 2017年12月4日 上午9:33:10
 */
@Controller
@RequestMapping("/report/supply")
public class SupplyReportController extends BaseController{
	
	@Autowired
	@Qualifier("supplyReportService")
	private SupplyReportService supplyReportService;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;

	/**
	 * <b>Description:</b><br> 导出产品分类列表管理
	 * @param request
	 * @param response
	 * @param session
	 * @param category
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月4日 上午9:50:06
	 */
	@RequestMapping(value = "/exportCategoryList", method = RequestMethod.GET)
	public void exportCategoryList(HttpServletRequest request, HttpServletResponse response,HttpSession session,Category category) {
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			category.setCompanyId(user.getCompanyId());
			List<Category> list = supplyReportService.exportCategoryList(category);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/产品分类.jrxml", list, "产品分类.xls");
		} catch (Exception e) {
			logger.error("exportCategoryList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出产品品牌列表
	 * @param request
	 * @param response
	 * @param session
	 * @param category
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月4日 下午4:18:58
	 */
	@RequestMapping(value = "/exportBrandList", method = RequestMethod.GET)
	public void exportBrandList(HttpServletRequest request, HttpServletResponse response,HttpSession session,Brand brand) {
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			brand.setCompanyId(user.getCompanyId());
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT);
			List<Brand> list = supplyReportService.exportBrandList(brand,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/产品品牌.jrxml", list, "产品品牌.xls");
		} catch (Exception e) {
			logger.error("exportBrandList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出产品列表
	 * @param request
	 * @param response
	 * @param session
	 * @param goods
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午1:41:35
	 */
	@RequestMapping(value = "/exportGoodsList", method = RequestMethod.GET)
	public void exportGoodsList(HttpServletRequest request, HttpServletResponse response,HttpSession session,GoodsExport goods) {
		try {
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			if(user!=null){
				goods.setCompanyId(user.getCompanyId());
			}
			if(StringUtils.isNotBlank(request.getParameter("searchBegintimeStr"))){
				goods.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(StringUtils.isNotBlank(request.getParameter("searchEndtimeStr"))){
				goods.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (null != goods.getGoodsUserId() && goods.getGoodsUserId() != -1) {
				List<Integer> categoryIdList = commonReportService.getCategoryIdListByUserId(goods.getGoodsUserId());
				if (categoryIdList == null || categoryIdList.isEmpty()) {
					categoryIdList.add(-1);
				}
				goods.setCategoryIdList(categoryIdList);
			}
			
			Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_3);
			List<GoodsExport> list = supplyReportService.exportGoodsList(goods,page);
			IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/产品列表.jrxml", list, "产品列表.xls");
		} catch (Exception e) {
			logger.error("exportGoodsList:", e);
		}
	}
	
	/**
	 * <b>Description:</b><br> 导出供应链-采购订单列表
	 * @param request
	 * @param response
	 * @param buyorderVo
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月19日 下午4:40:08
	 */
	@RequestMapping(value = "/exportBuyOrderList", method = RequestMethod.GET)
	public void exportBuyOrderList(HttpServletRequest request, HttpServletResponse response,BuyorderVo buyorderVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorderVo.setCompanyId(user.getCompanyId());

		// 通过父部门的id查询所有子部门的id集合

		// 查询所有职位类型为311的员工
		/*List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_311);//采购
		List<User> userList = commonReportService.getMyUserList(request.getSession(), positionType, false);*/
		
		List<Integer> userIds = new ArrayList<>();
		//根据公司的id以及部门id查询所属员工
		userIds = commonReportService.getDeptUserIdList(buyorderVo.getProOrgtId(), request);
		if (buyorderVo.getProUserId() != null && buyorderVo.getProUserId() != 0) {
			userIds.add(buyorderVo.getProUserId());
		}
		if (userIds.size() > 0) {
			buyorderVo.setUserIds(userIds);
		}
		String start = request.getParameter("searchBegintimeStr");
		String end = request.getParameter("searchEndtimeStr");
		if (start != null && !"".equals(start)) {
			buyorderVo.setSearchBegintime(DateUtil.convertLong(start, DateUtil.DATE_FORMAT));
		}
		if (end != null && !"".equals(end)) {
			buyorderVo.setSearchEndtime(DateUtil.convertLong(end + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
		List<BuyExportDetail> list = supplyReportService.exportBuyOrderList(buyorderVo,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/采购订单列表.jrxml", list, "采购订单列表.xls");
	}
	
	/**
	 * <b>Description:</b><br> 导出采购订单明细
	 * @param request
	 * @param response
	 * @param buyorderVo
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月5日 下午10:15:52
	 */
	@RequestMapping(value = "/exportBuyOrderDetailList", method = RequestMethod.GET)
	public void exportBuyOrderDetailList(HttpServletRequest request, HttpServletResponse response,BuyorderVo buyorderVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorderVo.setCompanyId(user.getCompanyId());

		List<Integer> userIds = new ArrayList<>();
		//根据公司的id以及部门id查询所属员工
		userIds = commonReportService.getDeptUserIdList(buyorderVo.getProOrgtId(), request);
		if (buyorderVo.getProUserId() != null && buyorderVo.getProUserId() != 0) {
			userIds.add(buyorderVo.getProUserId());
		}
		if (userIds.size() > 0) {
			buyorderVo.setUserIds(userIds);
		}
		String start = request.getParameter("searchBegintimeStr");
		String end = request.getParameter("searchEndtimeStr");
		if (start != null && !"".equals(start)) {
			buyorderVo.setSearchBegintime(DateUtil.convertLong(start, DateUtil.DATE_FORMAT));
		}
		if (end != null && !"".equals(end)) {
			buyorderVo.setSearchEndtime(DateUtil.convertLong(end + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		
		Page page = getPageTag(request, 1, ErpConst.EXPORT_DATA_LIMIT_2);
		List<BuyExportDetail> list = supplyReportService.exportBuyOrderDetailList(buyorderVo,page);
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/采购订单明细.jrxml", list, "采购订单明细.xls");
	}
	
}
