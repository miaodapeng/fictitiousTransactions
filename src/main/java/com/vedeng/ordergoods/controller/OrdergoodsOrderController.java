package com.vedeng.ordergoods.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.common.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import com.vedeng.common.money.OrderMoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.model.SaleorderData;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.CommunicateRecord;

/**
 * <b>Description:</b><br> 订货订单
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.ordergoods.controller
 * <br><b>ClassName:</b> OrdergoodsOrderController
 * <br><b>Date:</b> 2018年1月5日 下午1:14:48
 */
@Controller
@RequestMapping("/ordergoods/order")
public class OrdergoodsOrderController extends BaseController {

	// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 afterSalesOrderService. start
	@Autowired
	@Qualifier("afterSalesOrderService")
	private AfterSalesService afterSalesOrderService;
	// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 afterSalesOrderService. end

	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;
	
	@Autowired
	@Qualifier("capitalBillService")
	private CapitalBillService capitalBillService;
	
	/**
	 * <b>Description:</b><br> 订货订单列表
	 * @param request
	 * @param saleorder
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月5日 下午1:15:22
	 */
	@ResponseBody
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, Saleorder saleorder, HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		saleorder.setOrderType(3);
		Page page = getPageTag(request, pageNo, pageSize);

		// Map<String, Object> map;

		if (null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != "") {
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00",
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59",
					"yyyy-MM-dd HH:mm:ss")); 
		}

		// 客户性质
		List<SysOptionDefinition> customerNatures = getSysOptionDefinitionList(464);

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		// 获取销售部门
		List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310,user.getCompanyId());
		mv.addObject("orgList", orgList);


		saleorder.setCompanyId(user.getCompanyId());
		mv.addObject("loginUser",user);
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList", userList);// 操作人员
		saleorder.setSaleUserList(userList);

		/*
		if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
			// 销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = userService.getTraderIdListByUserId(saleorder.getOptUserId(), 1);// 1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		} else if (null != user.getPositType() && user.getPositType() == 310) {// 销售
			// 销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = userService.getTraderIdListByUserList(userList, ErpConst.ONE.toString());
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}*/
		
		saleorder.setOptType("orderIndex");
		Map<String, Object> map = saleorderService.getSaleorderListPage(request, saleorder, page,0);

		List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");

		CommunicateRecord cr = null;
		List<Integer> saleorderIdList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			// 销售人员
			user = userService.getUserById(list.get(i).getUserId());
			list.get(i).setSalesName(user == null ? "" : user.getUsername());
			// 归属销售
			user = orgService.getTraderUserAndOrgByTraderId(list.get(i).getTraderId(), 1);// 1客户，2供应商
			list.get(i).setOptUserName(user == null ? "" : user.getUsername());
			list.get(i).setSalesDeptName(user == null ? "" : user.getOrgName());
			// 销售部门
//						list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));
			// 沟通记录次数(参数使用List，多个参数，使方法能复用)
			cr = new CommunicateRecord();
			if (list.get(i).getSaleorderId() != null) {
				cr.setSaleorderId(list.get(i).getSaleorderId());
			}
			if (list.get(i).getQuoteorderId() != null) {
				cr.setQuoteorderId(list.get(i).getQuoteorderId());
			}
			if (list.get(i).getBussinessChanceId() != null) {
				cr.setBussinessChanceId(list.get(i).getBussinessChanceId());
			}
			// 沟通类型为商机和报价和销售订单
			int num = saleorderService.getSaleorderCommunicateRecordCount(cr);
			list.get(i).setCommunicateNum(num);

			list.get(i).setCustomerNatureStr(getSysOptionDefinition(list.get(i).getCustomerNature()).getTitle());
			// 获取订单合同回传&订单送货单回传列表
			List<Attachment> saleorderAttachmentList = saleorderService
					.getSaleorderAttachmentList(list.get(i).getSaleorderId());
			// mv.addObject("saleorderAttachmentList", saleorderAttachmentList);
			for (Attachment attachment : saleorderAttachmentList) {
				if (attachment.getAttachmentFunction() == 492)
					list.get(i).setIsContractReturn(1);
				if (attachment.getAttachmentFunction() == 493)
					list.get(i).setIsDeliveryOrderReturn(1);
			}
			saleorderIdList.add(list.get(i).getSaleorderId());
			
			//审核人
			if(null != list.get(i).getVerifyUsername()){
			    List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));  
			    list.get(i).setVerifyUsernameList(verifyUsernameList);
			}
			
			//可否开票判断
			Saleorder invoiceSaleorder = new Saleorder();
			invoiceSaleorder.setSaleorderId(list.get(i).getSaleorderId());
			invoiceSaleorder.setOptType("orderIndex");
			invoiceSaleorder = saleorderService.getBaseSaleorderInfo(invoiceSaleorder);
			list.get(i).setIsOpenInvoice(invoiceSaleorder.getIsOpenInvoice());
			Map<String, BigDecimal> moneyData=saleorderService.getSaleorderDataInfo(list.get(i).getSaleorderId());
			if(moneyData!=null){
				list.get(i).setPaymentAmount(OrderMoneyUtil.getPaymentAmount(moneyData));
				list.get(i).setRealAmount(moneyData.get("realAmount"));
			}
		}
		// 根据销售订单ID查询账期付款总额-订单未还账期款---换成Jerry写的方法
		List<SaleorderData> capitalBillList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);

		// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. start

		if (CollectionUtils.isNotEmpty(capitalBillList)) {
			capitalBillList.stream().forEach(order -> {
				try {
					if (null != order.getSaleorderId()) {
						BigDecimal periodAmount = saleorderService.getPeriodAmount(order.getSaleorderId());
						BigDecimal lackAccountPeriodAmount = saleorderService.getLackAccountPeriodAmount(order.getSaleorderId());
						BigDecimal refundBalanceAmount = afterSalesOrderService.getRefundBalanceAmountBySaleorderId(order.getSaleorderId());
						order.setPeriodAmount(periodAmount == null ? BigDecimal.ZERO : periodAmount);
						order.setLackAccountPeriodAmount(lackAccountPeriodAmount == null ? BigDecimal.ZERO : lackAccountPeriodAmount);
						order.setRefundBalanceAmount(refundBalanceAmount == null ? BigDecimal.ZERO : refundBalanceAmount);
					}
				} catch (Exception e) {
					log.info("订单实际金额运算出现异常: {}", e);
				}
			});
		}
		// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. end


		mv.addObject("capitalBillList", capitalBillList);

		mv.addObject("saleorderList", list);
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("customerNatures", customerNatures);
		mv.addObject("saleorder", saleorder);
		mv.addObject("from", "ordergoods");
		mv.setViewName("order/saleorder/index");
		return mv;
	}
	@ResponseBody
	@RequestMapping(value = "indexOnline")
	public ModelAndView indexOnline(HttpServletRequest request, Saleorder saleorder, HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		saleorder.setOrderType(3);
		Page page = getPageTag(request, pageNo, pageSize);

		// Map<String, Object> map;

		if (null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != "") {
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00",
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59",
					"yyyy-MM-dd HH:mm:ss"));
		}

		// 客户性质
		List<SysOptionDefinition> customerNatures = getSysOptionDefinitionList(464);

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		// 获取销售部门
		List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310,user.getCompanyId());
		mv.addObject("orgList", orgList);


		saleorder.setCompanyId(user.getCompanyId());
		mv.addObject("loginUser",user);
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList", userList);// 操作人员
		saleorder.setSaleUserList(userList);

		/*
		if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
			// 销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = userService.getTraderIdListByUserId(saleorder.getOptUserId(), 1);// 1客户，2供应商
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		} else if (null != user.getPositType() && user.getPositType() == 310) {// 销售
			// 销售人员所属客户（即当前订单操作人员）
			List<Integer> traderIdList = userService.getTraderIdListByUserList(userList, ErpConst.ONE.toString());
			if (traderIdList == null || traderIdList.isEmpty()) {
				traderIdList.add(-1);
			}
			saleorder.setTraderIdList(traderIdList);
		}*/
		
		saleorder.setOptType("orderIndex");
		Map<String, Object> map = saleorderService.getSaleorderListPage(request, saleorder, page,1);

		List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");

		CommunicateRecord cr = null;
		List<Integer> saleorderIdList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			// 销售人员
			user = userService.getUserById(list.get(i).getUserId());
			list.get(i).setSalesName(user == null ? "" : user.getUsername());
			// 归属销售
			user = orgService.getTraderUserAndOrgByTraderId(list.get(i).getTraderId(), 1);// 1客户，2供应商
			list.get(i).setOptUserName(user == null ? "" : user.getUsername());
			list.get(i).setSalesDeptName(user == null ? "" : user.getOrgName());
			// 销售部门
//						list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));
			// 沟通记录次数(参数使用List，多个参数，使方法能复用)
			cr = new CommunicateRecord();
			if (list.get(i).getSaleorderId() != null) {
				cr.setSaleorderId(list.get(i).getSaleorderId());
			}
			if (list.get(i).getQuoteorderId() != null) {
				cr.setQuoteorderId(list.get(i).getQuoteorderId());
			}
			if (list.get(i).getBussinessChanceId() != null) {
				cr.setBussinessChanceId(list.get(i).getBussinessChanceId());
			}
			// 沟通类型为商机和报价和销售订单
			int num = saleorderService.getSaleorderCommunicateRecordCount(cr);
			list.get(i).setCommunicateNum(num);

			list.get(i).setCustomerNatureStr(getSysOptionDefinition(list.get(i).getCustomerNature()).getTitle());
			// 获取订单合同回传&订单送货单回传列表
			List<Attachment> saleorderAttachmentList = saleorderService
					.getSaleorderAttachmentList(list.get(i).getSaleorderId());
			// mv.addObject("saleorderAttachmentList", saleorderAttachmentList);
			for (Attachment attachment : saleorderAttachmentList) {
				if (attachment.getAttachmentFunction() == 492)
					list.get(i).setIsContractReturn(1);
				if (attachment.getAttachmentFunction() == 493)
					list.get(i).setIsDeliveryOrderReturn(1);
			}
			saleorderIdList.add(list.get(i).getSaleorderId());
			
			//审核人
			if(null != list.get(i).getVerifyUsername()){
			    List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));  
			    list.get(i).setVerifyUsernameList(verifyUsernameList);
			}
			
			//可否开票判断
			Saleorder invoiceSaleorder = new Saleorder();
			invoiceSaleorder.setSaleorderId(list.get(i).getSaleorderId());
			invoiceSaleorder.setOptType("orderIndex");
			invoiceSaleorder = saleorderService.getBaseSaleorderInfo(invoiceSaleorder);
			list.get(i).setIsOpenInvoice(invoiceSaleorder.getIsOpenInvoice());
		}
		// 根据销售订单ID查询账期付款总额-订单未还账期款---换成Jerry写的方法
		List<SaleorderData> capitalBillList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);
		mv.addObject("capitalBillList", capitalBillList);

		mv.addObject("saleorderList", list);
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("customerNatures", customerNatures);
		mv.addObject("saleorder", saleorder);
		mv.addObject("from", "ordergoods");
		mv.setViewName("order/saleorder/index");
		return mv;
	}
}





