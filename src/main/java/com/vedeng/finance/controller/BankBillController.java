package com.vedeng.finance.controller;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.annotation.MethodLock;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.finance.model.*;
import com.vedeng.logistics.service.WarehouseStockService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.service.BankBillService;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br>
 * 付款申请管理
 *
 * @author leo.yang
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.finance.controller <br>
 *       <b>ClassName:</b> PayApplyController <br>
 *       <b>Date:</b> 2017年9月8日 下午2:47:24
 */
@Controller
@RequestMapping("/finance/bankbill")
public class BankBillController extends BaseController {

	@Autowired
	@Qualifier("bankBillService")
	private BankBillService bankBillService;// 自动注入payApplyService

	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;

	@Autowired
	@Qualifier("capitalBillService")
	private CapitalBillService capitalBillService;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("payApplyService")
	PayApplyService payApplyService;

	@Autowired
	private WarehouseStockService warehouseStockService;

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Autowired
	private RedisUtils redisUtils;
	/**
	 *
	 * <b>Description:</b><br>
	 * 建行银行流水列表
	 *
	 * @param request
	 * @param bankBill
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @throws ParseException
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年9月15日 下午5:07:18
	 */
	@ResponseBody
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, BankBill bankBill, HttpSession session,
							  @RequestParam(required = false, defaultValue = "1") Integer pageNo,
							  @RequestParam(required = false) Integer pageSize,
							  @RequestParam(required = false, value = "beginTime") String searchBeginTime,
							  @RequestParam(required = false, value = "endTime") String searchEndTime) throws ParseException {
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);

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

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());

		Map<String, Object> map;
		//建设银行
		bankBill.setBankTag(1);
		try {
			map = bankBillService.getBankBillListPage(bankBill, page);
			mv.addObject("list", map.get("list"));
			mv.addObject("page", map.get("page"));
			mv.addObject("getAmount", map.get("getAmount"));
			mv.addObject("payAmount", map.get("payAmount"));
			mv.addObject("orderNum", map.get("orderNum"));
			mv.addObject("orderAmount", map.get("orderAmount"));
			mv.addObject("matchAmount", map.get("matchAmount"));
		} catch (Exception e) {
			logger.error("bank bill:", e);
		}

		mv.addObject("beginTime", searchBeginTime);
		mv.addObject("endTime", searchEndTime);
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		mv.addObject("nowDate", nowDate);

		mv.setViewName("finance/bankBill/index");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 订单结款列表
	 *
	 * @param request
	 * @param bankBill
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @throws ParseException
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年9月15日 下午5:07:34
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "bankBillMatchList")
	public ModelAndView bankBillMatchList(HttpServletRequest request, BankBill bankBill, HttpSession session,
										  @RequestParam(required = false, defaultValue = "1") Integer pageNo,
										  @RequestParam(required = false) Integer pageSize,
										  @RequestParam(required = false, value = "beginTime") String searchBeginTime,
										  @RequestParam(required = false, value = "endTime") String searchEndTime) throws ParseException {
		ModelAndView mv = new ModelAndView();
		//默认100条
		if(pageSize == null){
			pageSize = 100;
		}
		Page page = getPageTag(request, pageNo, pageSize);
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

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());
		bankBill.setBankTag(1);
		Map<String, Object> map;
		try {
			map = bankBillService.getBankBillMatchListPage(bankBill, page);
			// 补充订单剩余账期额度数据
			List<Integer> saleorderIdList = new ArrayList<>();
			List<BankBill> bankBillInfo = (List<BankBill>) map.get("list");
//	    if (null != bankBillInfo && !bankBillInfo.isEmpty()) {
//		for (BankBill b : bankBillInfo) {
//		    List<CapitalBillDetail> capitalBillDetailList = b.getCapitalBillDetailList();
//		    if (null != capitalBillDetailList && !capitalBillDetailList.isEmpty()) {
//			for (CapitalBillDetail c : capitalBillDetailList) {
//			    Saleorder saleorderInfo = c.getSaleorder();
//			    if (null != saleorderInfo.getSaleorderId()) {
//				saleorderIdList.add(saleorderInfo.getSaleorderId());
//			    }
//			}
//		    }
//		}
//	    }
//	    if (null != saleorderIdList && !saleorderIdList.isEmpty()) {
//		List<SaleorderData> saleorderDataList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);
//		if (null != bankBillInfo && !bankBillInfo.isEmpty()) {
//		    for (BankBill b : bankBillInfo) {
//			List<CapitalBillDetail> capitalBillDetailList = b.getCapitalBillDetailList();
//			if (null != capitalBillDetailList && !capitalBillDetailList.isEmpty()) {
//			    for (CapitalBillDetail c : capitalBillDetailList) {
//				Saleorder saleorderInfo = c.getSaleorder();
//				if (null != saleorderDataList && !saleorderDataList.isEmpty()) {
//				    for (SaleorderData sd : saleorderDataList) {
//					if (saleorderInfo.getSaleorderId().equals(sd.getSaleorderId())) {
//					    saleorderInfo.setResidueAmount(sd.getPaymentAmount());
//					}
//				    }
//				}
//			    }
//			}
//		    }
//		}
//	    }

			mv.addObject("list", bankBillInfo);
			mv.addObject("page", map.get("page"));

			mv.addObject("getAmount", map.get("getAmount"));
			mv.addObject("payAmount", map.get("payAmount"));
			mv.addObject("orderNum", map.get("orderNum"));
			mv.addObject("orderAmount", map.get("orderAmount"));
			mv.addObject("matchAmount", map.get("matchAmount"));
		} catch (Exception e) {
			logger.error("bankBillMatchList:", e);
		}
		mv.addObject("beginTime", searchBeginTime);
		mv.addObject("endTime", searchEndTime);
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		mv.addObject("nowDate", nowDate);
		mv.setViewName("finance/bankBill/list_bankBillMatch");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 修改银行流水
	 *
	 * @param bankBill
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年9月26日 下午3:12:40
	 */
	@ResponseBody
	@RequestMapping(value = "/editBankBill")
	public ResultInfo editBankBill(BankBill bankBill) throws Exception {
		ResultInfo result = new ResultInfo();
		result = bankBillService.editBankBill(bankBill);
		return result;
	}

	/**
	 *
	 * <b>Description:</b><br>忽略匹配
	 *
	 * @param session
	 * @param fileDeliveryId
	 * @param type 1.结款 2.付款
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年9月26日 下午4:25:30
	 */
	@ResponseBody
	@RequestMapping(value = "/addIgnore")
	public ModelAndView addIgnore(HttpSession session, Integer bankBillId,Integer type) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("bankBillId", bankBillId);

		if(type == 1){
			//匹配项目
			List<SysOptionDefinition> macthObjectList = getSysOptionDefinitionList(840);
			mv.addObject("macthObjectList", macthObjectList);
		}else if(type == 2){
			//匹配项目
			List<SysOptionDefinition> macthObjectList = getSysOptionDefinitionList(856);
			mv.addObject("macthObjectList", macthObjectList);
		}

		mv.setViewName("finance/bankBill/add_ignore");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 银企直连结款，添加资金流水（只针对销售订单）
	 *
	 * @param session
	 * @param capitalBill
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年9月27日 下午4:15:03
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/addCapitalBill")
	public ResultInfo addCapitalBill(HttpSession session, CapitalBill capitalBill, Integer saleorderId,
									 BigDecimal receivedAmount) {
		// 获取订单信息
		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(saleorderId);
		Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);

		//add by Tomcat.Hui 20190820 如果订单状态位待确认，则返回
		if(null != saleorderInfo){
			if(saleorderInfo.getStatus().equals(4)&&!saleorderInfo.getOrderType().equals(1)){
				return new ResultInfo<>(-1,"该订单待用户确认，请联系销售处理");
			}

			if (saleorderInfo.getStatus().equals(0) || saleorderInfo.getStatus().equals(3)) {
				return new ResultInfo(-1, "订单处于待确认或已关闭状态,无法结款");
			}

		}
		//add by Tomcat.Hui 20190820 如果订单状态位待确认，则返回 end

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		// 归属销售
		User belongUser = new User();
		if(saleorderInfo.getTraderId() != null ){
			belongUser = userService.getUserByTraderId(saleorderInfo.getTraderId(), 1);// 1客户，2供应商
			if(belongUser != null && belongUser.getUserId() != null){
				belongUser = userService.getUserById(belongUser.getUserId());
			}
		}
		Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
		// 获取银行流水信息
		BankBill bankBill = bankBillService.getBankBillById(capitalBill.getBankBillId());
		// 获取订单已付款金额
//	Saleorder s = capitalBillService.getSaleorderCapitalById(saleorderId);
		// 资金流水赋值
		if (user != null) {
			capitalBill.setCreator(user.getUserId());
			capitalBill.setAddTime(DateUtil.sysTimeMillis());
			capitalBill.setCompanyId(user.getCompanyId());
		}
		// 根据借款金额判断是付预付款，还是付账期
		BigDecimal totalAmount = saleorderInfo.getTotalAmount();

		ResultInfo<?> result = new ResultInfo<>();
		// 订单总额 ！= 订单已付款金额+剩余账期还款金额
		if (totalAmount != receivedAmount) {
			// 剩余预付款金额 订单预付款金额-(订单已付款金额+剩余账期还款金额)
			BigDecimal residue = saleorderInfo.getPrepaidAmount().subtract(receivedAmount);
			// 如果剩余预付款金额大于订单借款金额
			// 交易方式银行
			capitalBill.setTraderMode(521);
			capitalBill.setTranFlow(bankBill.getTranFlow());
			capitalBill.setCurrencyUnitId(1);
			capitalBill.setTraderTime(DateUtil.sysTimeMillis());
			capitalBill.setTraderType(1);
			capitalBill.setPayerBankAccount(bankBill.getAccno2());
			capitalBill.setPayerBankName(bankBill.getCadbankNm());
			capitalBill.setPayer(bankBill.getAccName1());
			capitalBill.setPayee(companyInfo==null?"":companyInfo.getCompanyName());

			List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
			CapitalBillDetail capitalBillDetail = new CapitalBillDetail();

			capitalBillDetail.setOrderType(1);
			capitalBillDetail.setOrderNo(saleorderInfo.getSaleorderNo());
			capitalBillDetail.setRelatedId(saleorderInfo.getSaleorderId());
			capitalBillDetail.setTraderType(1);
			capitalBillDetail.setTraderId(saleorderInfo.getTraderId());
			capitalBillDetail.setUserId(saleorderInfo.getUserId());
			capitalBillDetail.setBussinessType(526);//交易类型订单收款
			capitalBillDetail.setAmount(capitalBill.getAmount());
			if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
				capitalBillDetail.setOrgName(belongUser.getOrgName());
				capitalBillDetail.setOrgId(belongUser.getOrgId());
			}
			// 订单收款
			capitalBillDetails.add(capitalBillDetail);
			capitalBill.setCapitalBillDetails(capitalBillDetails);

			CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
			capitalBillDetailInfo.setOrderType(1);
			capitalBillDetailInfo.setOrderNo(saleorderInfo.getSaleorderNo());
			capitalBillDetailInfo.setRelatedId(saleorderInfo.getSaleorderId());
			capitalBillDetailInfo.setTraderType(1);
			capitalBillDetailInfo.setTraderId(saleorderInfo.getTraderId());
			capitalBillDetailInfo.setUserId(saleorderInfo.getUserId());
			capitalBillDetailInfo.setBussinessType(526);//交易类型订单收款
			capitalBillDetailInfo.setAmount(capitalBill.getAmount());
			if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
				capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
				capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
			}
	    //VDERP-1327  订单结款自动生成流水记录
			String payer = capitalBill.getPayer();
			capitalBill.setCapitalBillDetail(capitalBillDetailInfo);
			result = capitalBillService.saveAddCapitalBill(capitalBill);
			//VDERP-1327  订单结款自动生成流水记录
			if(!StringUtils.isEmpty(payer)&&result.getCode()==0) {
				if(payer.equals(ErpConst.TAOBAO)||payer.equals(ErpConst.WEIXIN)) {
					ResultInfo resultInfo = capitalBillService.saveSecondCapitalBill(saleorderInfo,capitalBill);
				}
			}
			//VDERP-1327  订单结款自动生成流水记录
		}

		if(user.getCompanyId() == 1 && result.getCode() == 0){
			vedengSoapService.orderSyncWeb(saleorderId);
		}
		//调用库存服务
		if(result.getCode()==0){
			warehouseStockService.updateOccupyStockService(saleorderInfo, 0);
			//VDERP-2263   订单售后采购改动通知
			warehouseStockService.updateSaleOrderDataUpdateTime(saleorderId,null, OrderDataUpdateConstant.SALE_ORDER_PAY);
		}
		return result;


	}
	@FormToken(save=true)
	@RequestMapping(value = "/getManualMatchInfo")
	public ModelAndView getManualMatchInfo(HttpServletRequest request, Integer bankBillId, String search,
										   @RequestParam(required = false, defaultValue = "1") Integer pageNo,
										   @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		Page page = getPageTag(request, pageNo, pageSize);
		// 获取银行流水信息
		BankBill bankBill = bankBillService.getBankBillById(bankBillId);
		Map<String, Object> map;
		if (null != search) {
			// 搜索订单
			Saleorder saleorder = new Saleorder();
			saleorder.setSearch(search);
			saleorder.setValidStatus(1);
			saleorder.setStatus(-2);
			map = saleorderService.getSaleorderListPage(request, saleorder, page,0);
			List<Saleorder> saleorderList = (List<Saleorder>) map.get("saleorderList");
			if(null != saleorderList){
				for(Saleorder s:saleorderList){

					Saleorder saleorderInfo = capitalBillService.getSaleorderCapitalById(s.getSaleorderId());
					// 获取订单已付款金额
					if(null != saleorderInfo && null != saleorderInfo.getReceivedAmount()){
						s.setReceivedAmount(saleorderInfo.getReceivedAmount());
					}
					if(null != saleorderInfo && null != saleorderInfo.getRealAmount()){
						s.setRealAmount(saleorderInfo.getRealAmount());
					}
				}
			}
			// 如果有查到订单的话
			if (null != saleorderList && !saleorderList.isEmpty()) {
				List<Integer> saleorderIdList = new ArrayList<>();
				for (Saleorder s : saleorderList) {
					saleorderIdList.add(s.getSaleorderId());
				}
				//List<SaleorderData> saleorderDataList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);
			}
			mv.addObject("search",search);
			mv.addObject("saleorderList", saleorderList);
			mv.addObject("page", map.get("page"));
		}

		mv.addObject("bankBill", bankBill);
		mv.setViewName("finance/bankBill/list_manualMatch");
		return mv;
	}

	/**
	 * 银行流水发送至金蝶
	 * <b>Description:</b><br>
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Bill
	 * <br><b>Date:</b> 2018年5月28日 下午7:12:42
	 */
	@ResponseBody
	@RequestMapping(value = "/sendbankbilllist")
	public synchronized ResultInfo sendBankBillList(HttpServletRequest request, HttpSession session, BankBill bankBill) {
		logger.info("银行流水发送至金蝶....start");
		String isExecuting=	redisUtils.get(dbType+"sendbankbilllist-lock");
		if(StringUtils.isEmpty(isExecuting)){
			String time=System.nanoTime()+"";
			redisUtils.set(dbType+"sendbankbilllist-lock",time,43200);
			try{
				Page page = getPageTag(request, 1, 1000);
				User user = (User) session.getAttribute(Consts.SESSION_USER);
				bankBill.setCompanyId(user.getCompanyId());
				bankBill.setUserIdNow(user.getUserId());
				ResultInfo resultInfo=bankBillService.sendBankBillList(bankBill, page, session);
				return resultInfo;
			} catch (Exception e) {
				logger.error("sendbankbilllist:", e);
			}finally {
				logger.info("银行流水发送至金蝶....end 已执行");
				redisUtils.del(dbType+"sendbankbilllist-lock");
			}
		}else{
			logger.info("银行流水发送至金蝶....end 重复操作未执行");
		}
		return new ResultInfo();
	}

	/**
	 *
	 * <b>Description:</b><br> 南京银行流水列表
	 * @param request
	 * @param bankBill
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @throws ParseException
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2018年7月5日 下午1:43:43
	 */
	@ResponseBody
	@RequestMapping(value = "njindex")
	public ModelAndView njindex(HttpServletRequest request, BankBill bankBill, HttpSession session,
								@RequestParam(required = false, defaultValue = "1") Integer pageNo,
								@RequestParam(required = false) Integer pageSize,
								@RequestParam(required = false, value = "beginTime") String searchBeginTime,
								@RequestParam(required = false, value = "endTime") String searchEndTime) throws ParseException {
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
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

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());

		Map<String, Object> map;
		//南京银行
		bankBill.setBankTag(2);
		try {
			map = bankBillService.getBankBillListPage(bankBill, page);
			//匹配项目
			List<SysOptionDefinition> macthObjectList = getSysOptionDefinitionList(856);
			mv.addObject("macthObjectList", macthObjectList);
			mv.addObject("list", map.get("list"));
			mv.addObject("page", map.get("page"));
			mv.addObject("getAmount", map.get("getAmount"));
			mv.addObject("payAmount", map.get("payAmount"));
			mv.addObject("orderNum", map.get("orderNum"));
			mv.addObject("orderAmount", map.get("orderAmount"));
			mv.addObject("matchAmount", map.get("matchAmount"));
		} catch (Exception e) {
			logger.error("njindex:", e);
		}

		mv.addObject("beginTime", searchBeginTime);
		mv.addObject("endTime", searchEndTime);
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		mv.addObject("nowDate", nowDate);
		mv.setViewName("finance/bankBill/njindex");
		return mv;

	}

	/**
	 *
	 * <b>Description:</b><br> 订单付款匹配列表
	 * @param request
	 * @param bankBill
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @throws ParseException
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月6日 上午9:46:08
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "bankBillPayMatchList")
	public ModelAndView bankBillPayMatchList(HttpServletRequest request, BankBill bankBill, HttpSession session,
											 @RequestParam(required = false, defaultValue = "1") Integer pageNo,
											 @RequestParam(required = false) Integer pageSize,
											 @RequestParam(required = false, value = "beginTime") String searchBeginTime,
											 @RequestParam(required = false, value = "endTime") String searchEndTime) throws ParseException {
		ModelAndView mv = new ModelAndView();
		//默认100条
		if(pageSize == null){
			pageSize = 100;
		}
		Page page = getPageTag(request, pageNo, pageSize);
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

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());
		//南京银行流水
		if(bankBill.getBankTag() == null){
			bankBill.setBankTag(2);
		}
		Map<String, Object> map;
		try {
			map = bankBillService.getBankBillPayMatchListPage(bankBill, page);
			// 补充订单剩余账期额度数据
			List<Integer> saleorderIdList = new ArrayList<>();
			List<BankBill> bankBillInfo = (List<BankBill>) map.get("list");
			if(bankBillInfo!=null){
				// 获取订单审核信息
				TaskService taskService = processEngine.getTaskService(); // 任务相关service
				for (int i = 0; i < bankBillInfo.size(); i++) {
					if(bankBillInfo.get(i).getCapitalBillDetailList() != null){
						for(int j = 0; j <bankBillInfo.get(i).getCapitalBillDetailList().size(); j++){
							// 获取当前活动节点
							Task taskInfoPay = taskService.createTaskQuery().processInstanceBusinessKey("paymentVerify_"+ bankBillInfo.get(i).getCapitalBillDetailList().get(j).getPayApply().getPayApplyId())
									.singleResult();
							bankBillInfo.get(i).getCapitalBillDetailList().get(j).getPayApply().setTaskInfoPayId(taskInfoPay.getId());
						}
					}
				}
			}

			mv.addObject("list", bankBillInfo);
			mv.addObject("page", map.get("page"));

			mv.addObject("getAmount", map.get("getAmount"));
			mv.addObject("payAmount", map.get("payAmount"));
			mv.addObject("orderNum", map.get("orderNum"));
			mv.addObject("orderAmount", map.get("orderAmount"));
			mv.addObject("matchAmount", map.get("matchAmount"));
		} catch (Exception e) {
			logger.error("bankBillPayMatchList:", e);
		}
		mv.addObject("beginTime", searchBeginTime);
		mv.addObject("endTime", searchEndTime);
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		mv.addObject("nowDate", nowDate);
		mv.setViewName("finance/bankBill/list_bankBillPayMatch");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b><br> 订单付款手动匹配
	 * @param request
	 * @param bankBillId
	 * @param search
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月23日 上午10:16:16
	 */
	@FormToken(save=true)
	@RequestMapping(value = "/getManualMatchPayInfo")
	public ModelAndView getManualMatchPayInfo(HttpServletRequest request, Integer bankBillId, String search,
											  @RequestParam(required = false, defaultValue = "1") Integer pageNo,
											  @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		// 获取银行流水信息
		BankBill bankBill = bankBillService.getBankBillById(bankBillId);
		Map<String, Object> map;
		if (null != search) {
			// 搜索订单
			PayApply payApply = new PayApply();
			payApply.setSearch(search.trim());
			payApply.setValidStatus(0);
			payApply.setIsBill(1);
			map = payApplyService.getPayApplyListPage(request,payApply,page);
			List<PayApply> payApplyList = (List<PayApply>) map.get("payApplyList");
			if(payApplyList!=null){
				// 获取订单审核信息
				TaskService taskService = processEngine.getTaskService(); // 任务相关service
				for (int i = 0; i < payApplyList.size(); i++) {
					if(payApplyList.get(i).getPayApplyId() != null){
						// 获取当前活动节点
						Task taskInfoPay = taskService.createTaskQuery().processInstanceBusinessKey("paymentVerify_"+ payApplyList.get(i).getPayApplyId())
								.singleResult();
						payApplyList.get(i).setTaskInfoPayId(taskInfoPay.getId());
					}
				}
			}
			mv.addObject("search",search);
			mv.addObject("payApplyList", payApplyList);
			mv.addObject("page", map.get("page"));
		}

		mv.addObject("bankBill", bankBill);
		mv.setViewName("finance/bankBill/list_manualMatchPay");
		return mv;
	}


	/**
	 * 发送付款记录到金蝶
	 * <b>Description:</b><br>
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> Bill
	 * <br><b>Date:</b> 2018年8月3日 上午11:23:33
	 */
	@ResponseBody
	@RequestMapping(value = "/sendpaybilltokindlee")
	public ResultInfo sendPayBillToKindlee(HttpServletRequest request, HttpSession session, BankBill bankBill) throws Exception {
		Page page = getPageTag(request, 1, 1000);
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());
		bankBill.setUserIdNow(user.getUserId());
		ResultInfo resultInfo = bankBillService.sendPayBillToKindlee(bankBill, page, session);

		return resultInfo;
	}


	/**
	 *
	 * <b>Description:</b><br> 中国银行流水列表
	 * @param request
	 * @param bankBill
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @throws ParseException
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年9月18日 下午1:43:43
	 */
	@ResponseBody
	@RequestMapping(value = "chinaIndex")
	public ModelAndView chinaIndex(HttpServletRequest request, BankBill bankBill, HttpSession session,
								   @RequestParam(required = false, defaultValue = "1") Integer pageNo,
								   @RequestParam(required = false) Integer pageSize,
								   @RequestParam(required = false, value = "beginTime") String searchBeginTime,
								   @RequestParam(required = false, value = "endTime") String searchEndTime) throws ParseException {
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
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

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());

		Map<String, Object> map;
		//中国银行
		bankBill.setBankTag(3);
		try {
			map = bankBillService.getBankBillListPage(bankBill, page);
			//匹配项目
			List<SysOptionDefinition> macthObjectList = getSysOptionDefinitionList(856);
			mv.addObject("macthObjectList", macthObjectList);
			mv.addObject("list", map.get("list"));
			mv.addObject("page", map.get("page"));
			mv.addObject("getAmount", map.get("getAmount"));
			mv.addObject("payAmount", map.get("payAmount"));
			mv.addObject("orderNum", map.get("orderNum"));
			mv.addObject("orderAmount", map.get("orderAmount"));
			mv.addObject("matchAmount", map.get("matchAmount"));
		} catch (Exception e) {
			logger.error("chinaIndex:", e);
		}

		mv.addObject("beginTime", searchBeginTime);
		mv.addObject("endTime", searchEndTime);
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		mv.addObject("nowDate", nowDate);
		mv.setViewName("finance/bankBill/chinaIndex");
		return mv;
	}


	/**
	 * 中国银行流水推送金蝶
	 * <b>Description:</b>
	 * @param request
	 * @param session
	 * @param bankBill
	 * @return
	 * @throws Exception ResultInfo
	 * @Note
	 * <b>Author：</b> bill.bo
	 * <b>Date:</b> 2018年10月18日 下午2:45:16
	 */
	@ResponseBody
	@RequestMapping(value = "/sendchpaybilltokindlee")
	public ResultInfo<T> sendChainaBankBillToKindlee(HttpServletRequest request, HttpSession session, BankBill bankBill) throws Exception {
		Page page = getPageTag(request, 1, 1000);
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		bankBill.setCompanyId(user.getCompanyId());
		bankBill.setUserIdNow(user.getUserId());
		ResultInfo<T> resultInfo = bankBillService.sendChPayBillToKindlee(bankBill, page, session);

		return resultInfo;
	}

	/**
	 * 批量结款导表格
	 * @param request
	 * @author rock
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bankBillBatchInit")
	public ModelAndView batchUpdateGoodsInit(HttpServletRequest request,Integer bankBillId,String bankAccName) {

		ModelAndView mv = new ModelAndView();
		mv.addObject("bankBillId",bankBillId);
		mv.addObject("bankAccName",bankAccName);
		mv.setViewName("finance/bankBill/batchBankBill");
		return mv;
	}

	/**
	 * 批量結款
	 * @param request
	 * @param lwfile
	 * @author rock
	 * @return
	 */
	@MethodLock(className = CapitalBill.class ,field = "bankBillId",time = 1800000)
	@ResponseBody
	@RequestMapping("bankBillBatch")
	@SystemControllerLog(operationType = "import", desc = "导入批量结款数据")
	public ResultInfo<?> saveUplodeTaxCategoryNo(HttpServletRequest request,CapitalBill capitalBill,String bankAccName,
												 @RequestParam("lwfile") MultipartFile lwfile){
		ResultInfo<?> resultInfo = new ResultInfo<>();
		String bankAccName1=null;
		try {
			bankAccName1 = URLDecoder.decode(bankAccName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("字符串转换：",e);
		}


		//2019.12.9

		/*System.out.println(capitalBill.getBankBillId());*/
		// 获取银行流水信息
		BankBill bankBill = bankBillService.getBankBillById(capitalBill.getBankBillId());
		//获取剩余款额
		/*double finalamt=bankBill.getAmt().doubleValue()-bankBill.getMatchedAmount().doubleValue();*/
		BigDecimal finalamt=bankBill.getAmt().subtract(bankBill.getMatchedAmount());

		/*double billCount=0;*/
		BigDecimal billCount=BigDecimal.valueOf(0.00);

		InputStream inputStream=null;
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
			if (fileInfo.getCode() == 0) {
				/*List<Goods> list = new ArrayList<>();*/
				// 获取excel路径
				inputStream=new FileInputStream(new File(fileInfo.getFilePath()));
				Workbook workbook = WorkbookFactory.create(inputStream);
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;
				int endRowNum = sheet.getLastRowNum();// 结束行
				Row rowBEcell = sheet.getRow(sheet.getFirstRowNum());//获取第一行的单元个数
				int startCellNum = rowBEcell.getFirstCellNum();// 起始列
				int endCellNum = rowBEcell.getLastCellNum() - 1;//结束列
				//创建调用接口的集合
				List<BatchBillInfo> useAddCapitalBill=new ArrayList<>();
				BatchBillInfo batchBillInfo=null;
				CapitalBill capitalBillBatch=null;
				Integer saleOrderIdBatch=null;

				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					//创建接口需要的条件
					batchBillInfo=new BatchBillInfo();
					capitalBillBatch=new CapitalBill();
					capitalBillBatch.setBankBillId(capitalBill.getBankBillId());
					saleOrderIdBatch=0;
					/*int startCellNum = row.getFirstCellNum();// 起始列
					int endCellNum = row.getLastCellNum() - 1;// 结束列*/
					// 获取excel的值
					//此处本来是cellNum = startCellNum，改为cellNum = 0
					for (int cellNum = 0; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);

						if (cellNum == 0) {// 第一列数据cellNum==startCellNum
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() == 3) {
								resultInfo.setMessage("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
								throw new Exception("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
							} else {
								//传纯数字直接报不存在订单
								if (cell.getCellType()==0){
									resultInfo.setMessage("行" + (rowNum + 1) + "中订单不存在或不满足条件！");
									throw new Exception("行" + (rowNum + 1) + "中订单不存在或不满足条件！");
								}else {
									//判断订单状态
									String saleorderNo = cell.getStringCellValue();
									Saleorder getsaleId = saleorderService.getsaleorderId(saleorderNo);
									if (getsaleId == null) {
										resultInfo.setMessage("行" + (rowNum + 1) + "中订单不存在或不满足条件！");
										throw new Exception("行" + (rowNum + 1) + "中订单不存在或不满足条件！");
									}
									// 获取订单信息
									Saleorder saleorder = new Saleorder();
									saleorder.setSaleorderId(getsaleId.getSaleorderId());
									Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
									Integer validStatus = saleorderInfo.getValidStatus();
									Integer status = saleorderInfo.getStatus();
									if (saleorderInfo == null || validStatus == 0 || status == 3) {
										resultInfo.setMessage("行" + (rowNum + 1) + "中订单不存在或不满足条件！");
										throw new Exception("行" + (rowNum + 1) + "中订单不存在或不满足条件！");
									}
									saleOrderIdBatch = getsaleId.getSaleorderId();
								}
							}
						}

						if (cellNum == 1) {// 第二列数据cellNum==(startCellNum+1)
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白

							if (cell == null || cell.getCellType() == 3) {
								resultInfo.setMessage("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
								throw new Exception("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
							}
							if (cell.getCellType() != 0){
								resultInfo.setMessage("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
								throw new Exception("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
							}
							//2019.12.9
							/*double b=cell.getNumericCellValue();*/
							BigDecimal bigDecimal=BigDecimal.valueOf(cell.getNumericCellValue());
							bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
							capitalBillBatch.setAmount(bigDecimal);
							billCount=bigDecimal.add(billCount);
						}
						//第三列数据
						if (cellNum == 2) {// 第三列数据cellNum==(startCellNum+2)
							// 若excel中无内容的话，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType()==3) {
								resultInfo.setMessage("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
								throw new Exception("行" + (rowNum + 1) + "中数据错误或缺失，请返回修改！");
							}
							if (cell.getCellType()==0){
								cell.getNumericCellValue();
								DecimalFormat decimalFormat = new DecimalFormat("#########################################################.##############################");

								capitalBillBatch.setPayer(decimalFormat.format(cell.getNumericCellValue()));
							}else {
								capitalBillBatch.setPayer(cell.getStringCellValue());
							}
						}
						//第四列数据
						if (cellNum == 3) {
							if (cell==null || cell.getCellType()==3)
							{
							}else{
								if (cell.getCellType()==0){

									DecimalFormat decimalFormat = new DecimalFormat("#########################################################.##############################");
									capitalBillBatch.setComments(decimalFormat.format(cell.getNumericCellValue()));
								}else {
									capitalBillBatch.setComments(cell.getStringCellValue());
								}
							}
						}
						capitalBillBatch.setTraderSubject(1);
					}
					batchBillInfo.setCapitalBill(capitalBillBatch);
					batchBillInfo.setSaleOrderId(saleOrderIdBatch);
					useAddCapitalBill.add(batchBillInfo);
				}
				if (billCount.compareTo(finalamt)==1){
					resultInfo.setMessage("本次拟结款金额不得大于剩余结款金额！");
					throw new Exception("本次拟结款金额不得大于剩余结款金额！");
				}
				//调用接口
				for (BatchBillInfo batch:useAddCapitalBill) {
					this.addCapitalBillBatch(user,batch.getCapitalBill(),batch.getSaleOrderId(),bankAccName1);
				}
			}
		} catch (Exception e) {
			logger.error("bankBillBatch:", e);
			return resultInfo;
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("关闭批量结款流出现异常:", e);
			}
		}
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	//原先的结款接口
	public ResultInfo addCapitalBillBatch(User user, CapitalBill capitalBill, Integer saleorderId,String bankAccName) {
		// 获取订单信息
		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(saleorderId);
		Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);

		//add by Tomcat.Hui 20190820 如果订单状态位待确认，则返回
		//应该不需要
		/*if(null != saleorderInfo){
			if(saleorderInfo.getStatus().equals(4)&&!saleorderInfo.getOrderType().equals(1)){
				return new ResultInfo<>(-1,"该订单待用户确认，请联系销售处理");
			}
		}*/
		//add by Tomcat.Hui 20190820 如果订单状态位待确认，则返回 end

		/*User user = (User) session.getAttribute(Consts.SESSION_USER);*/
		// 归属销售
		User belongUser = new User();
		if(saleorderInfo.getTraderId() != null ){
			belongUser = userService.getUserByTraderId(saleorderInfo.getTraderId(), 1);// 1客户，2供应商
			if(belongUser != null && belongUser.getUserId() != null){
				belongUser = userService.getUserById(belongUser.getUserId());
			}
		}
		Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
		// 获取银行流水信息
		BankBill bankBill = bankBillService.getBankBillById(capitalBill.getBankBillId());
		// 获取订单已付款金额
//	Saleorder s = capitalBillService.getSaleorderCapitalById(saleorderId);
		// 资金流水赋值
		if (user != null) {
			capitalBill.setCreator(user.getUserId());
			capitalBill.setAddTime(DateUtil.sysTimeMillis());
			capitalBill.setCompanyId(user.getCompanyId());
		}
		// 根据借款金额判断是付预付款，还是付账期
		BigDecimal totalAmount = saleorderInfo.getTotalAmount();

		ResultInfo<?> result = new ResultInfo<>();
		// 订单总额 ！= 订单已付款金额+剩余账期还款金额
		/*if (totalAmount != receivedAmount) {*/
		// 剩余预付款金额 订单预付款金额-(订单已付款金额+剩余账期还款金额)
		/*BigDecimal residue = saleorderInfo.getPrepaidAmount().subtract(receivedAmount);*/
		// 如果剩余预付款金额大于订单借款金额
		// 交易方式银行
		capitalBill.setTraderMode(521);
		capitalBill.setTranFlow(bankBill.getTranFlow());
		capitalBill.setCurrencyUnitId(1);
		capitalBill.setTraderTime(DateUtil.sysTimeMillis());
		capitalBill.setTraderType(1);
		capitalBill.setPayerBankAccount(bankBill.getAccno2());
		capitalBill.setPayerBankName(bankBill.getCadbankNm());
		/*capitalBill.setPayer(bankBill.getAccName1());*/
		capitalBill.setPayee(companyInfo==null?"":companyInfo.getCompanyName());

		List<CapitalBillDetail> capitalBillDetails = new ArrayList<>();
		CapitalBillDetail capitalBillDetail = new CapitalBillDetail();

		capitalBillDetail.setOrderType(1);
		capitalBillDetail.setOrderNo(saleorderInfo.getSaleorderNo());
		capitalBillDetail.setRelatedId(saleorderInfo.getSaleorderId());
		capitalBillDetail.setTraderType(1);
		capitalBillDetail.setTraderId(saleorderInfo.getTraderId());
		capitalBillDetail.setUserId(saleorderInfo.getUserId());
		capitalBillDetail.setBussinessType(526);//交易类型订单收款
		capitalBillDetail.setAmount(capitalBill.getAmount());
		if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
			capitalBillDetail.setOrgName(belongUser.getOrgName());
			capitalBillDetail.setOrgId(belongUser.getOrgId());
		}
		// 订单收款
		capitalBillDetails.add(capitalBillDetail);
		capitalBill.setCapitalBillDetails(capitalBillDetails);

		CapitalBillDetail capitalBillDetailInfo = new CapitalBillDetail();
		capitalBillDetailInfo.setOrderType(1);
		capitalBillDetailInfo.setOrderNo(saleorderInfo.getSaleorderNo());
		capitalBillDetailInfo.setRelatedId(saleorderInfo.getSaleorderId());
		capitalBillDetailInfo.setTraderType(1);
		capitalBillDetailInfo.setTraderId(saleorderInfo.getTraderId());
		capitalBillDetailInfo.setUserId(saleorderInfo.getUserId());
		capitalBillDetailInfo.setBussinessType(526);//交易类型订单收款
		capitalBillDetailInfo.setAmount(capitalBill.getAmount());
		if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
			capitalBillDetailInfo.setOrgName(belongUser.getOrgName());
			capitalBillDetailInfo.setOrgId(belongUser.getOrgId());
		}
//	    //VDERP-1327  订单结款自动生成流水记录
		/*String payer = capitalBill.getPayer();*/
		String payer=bankAccName;
//	    if(!StringUtils.isEmpty(payer)) {
//	    	if(payer.equals(ErpConst.TAOBAO)||payer.equals(ErpConst.WEIXIN)) {
//	    		capitalBillDetailInfo.setBussinessType(526);
//	    		capitalBill.setBussinessType(526);
//	    		}
//	    	}
//	  //VDERP-1327  订单结款自动生成流水记录
		capitalBill.setCapitalBillDetail(capitalBillDetailInfo);
		result = capitalBillService.saveAddCapitalBill(capitalBill);
		//VDERP-1327  订单结款自动生成流水记录
		if(!StringUtils.isEmpty(payer)&&result.getCode()==0) {
			if(payer.equals(ErpConst.TAOBAO)||payer.equals(ErpConst.WEIXIN)) {
				//对私方式交易为支付宝或微信
				capitalBill.setPayer(payer);
				ResultInfo resultInfo = capitalBillService.saveSecondCapitalBill(saleorderInfo,capitalBill);
			}
		}
		//VDERP-1327  订单结款自动生成流水记录
		/*}*/
		//调用库存服务
		int i = warehouseStockService.updateOccupyStockService(saleorder, 0);
		if(user.getCompanyId() == 1 && result.getCode() == 0){
			vedengSoapService.orderSyncWeb(saleorderId);
		}
		if(result.getCode()==0){
			warehouseStockService.updateOccupyStockService(saleorderInfo, 0);
		}
		return result;


	}


}
