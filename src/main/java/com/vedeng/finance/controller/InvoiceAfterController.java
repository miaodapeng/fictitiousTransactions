package com.vedeng.finance.controller;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.trader.model.TraderCustomer;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesInvoice;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesInvoiceVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.DbRestInterfaceConstant;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceAfter;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.InvoiceDetail;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.service.InvoiceAfterService;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;

/**
 * <b>Description:</b><br> 财务-售后模块
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.finance.controller
 * <br><b>ClassName:</b> InvoiceAfterController
 * <br><b>Date:</b> 2017年10月17日 下午4:19:18
 */
@Controller
@RequestMapping("/finance/after")
public class InvoiceAfterController extends BaseController{

	@Autowired
	@Qualifier("invoiceAfterService")
	private InvoiceAfterService invoiceAfterService;//自动注入invoiceAfterService
	
	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;//自动注入invoiceService
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;
	
	@Autowired
	@Qualifier("payApplyService")
	private PayApplyService payApplyService;
	
	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;
	
	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;

	@Resource
	private AfterSalesService afterSalesOrderService;
	
	/**
	 * <b>Description:</b><br> 查询财务模块售后列表
	 * @param request
	 * @param invoiceAfter
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月17日 下午4:58:25
	 */
	@ResponseBody
	@RequestMapping(value="getFinanceAfterListPage")
	public ModelAndView getFinanceAfterListPage(HttpServletRequest request,InvoiceAfter invoiceAfter,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request,pageNo,pageSize);
		List<Integer> typeList = new ArrayList<>();List<SysOptionDefinition> sysList = new ArrayList<>();
		typeList.add(539);//539销售退货
		sysList.add(getSysOptionDefinition(539));
		typeList.add(540);//540销售换货
		sysList.add(getSysOptionDefinition(540));
		typeList.add(541);//541销售安调
		sysList.add(getSysOptionDefinition(541));
		typeList.add(584);//584销售维修
		sysList.add(getSysOptionDefinition(584));
		typeList.add(542);//542销售退票
		sysList.add(getSysOptionDefinition(542));
		typeList.add(543);//543销售退款
		sysList.add(getSysOptionDefinition(543));
		typeList.add(546);//546采购退货
		sysList.add(getSysOptionDefinition(546));
		typeList.add(547);//547采购换货
		sysList.add(getSysOptionDefinition(547));
		typeList.add(550);//550第三方安调
		sysList.add(getSysOptionDefinition(550));
		typeList.add(585);//585第三方维修
		sysList.add(getSysOptionDefinition(585));
		typeList.add(551);//551第三方退款
		sysList.add(getSysOptionDefinition(551));
		invoiceAfter.setAfterTypeList(typeList);
		mv.addObject("sysList", sysList);
		
		invoiceAfter.setCompanyId(user.getCompanyId());
		Map<String,Object> map = invoiceAfterService.getFinanceAfterListPage(invoiceAfter, page);
		if(map!=null){
			mv.addObject("page", map.get("page"));
			mv.addObject("invoiceAfterList", (List<InvoiceAfter>)map.get("invoiceAfterList"));
			mv.addObject("userList", (List<User>)map.get("userList"));
			
			// 售后人员
			List<User> serviceUserList = userService.getUserListByPositType(SysOptionConstant.ID_312,user.getCompanyId());
			//采购人员--采购售后（售后人员是采购负责人）
			List<User> list = userService.getUserListByPositType(SysOptionConstant.ID_311,user.getCompanyId());
			if(list != null && serviceUserList != null){
				serviceUserList.addAll(list);
			}
			mv.addObject("serviceUserList", serviceUserList);
		}
		mv.addObject("invoiceAfter", invoiceAfter);
		mv.setViewName("finance/after/finance_after_list");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 售后订单详情
	 * @param request
	 * @param invoiceAfter
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月19日 下午4:15:22
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="getFinanceAfterSaleDetail")
	public ModelAndView getFinanceAfterSaleDetail(HttpServletRequest request,AfterSalesVo afterSales,HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		mv.addObject("curr_user", user);
		mv.addObject("companyId", user.getCompanyId());
		AfterSalesVo afterSalesVo = null;
		// 判断是否有正在审核中的付款申请
		Integer isPayApplySh = 0;
		Integer payApplyId = 0;
		try {
			if(user!=null){
				afterSales.setCompanyId(user.getCompanyId());
				afterSales.setTraderType(afterSales.getSubjectType());
				//获取售后订单基本信息-售后申请-所属订单
				afterSalesVo = invoiceAfterService.getFinanceAfterSaleDetail(afterSales,session);
				afterSalesVo.seteFlag(afterSales.geteFlag());//入口--财务开票申请列表进入
				mv.addObject("afterSalesVo", afterSalesVo);
				if(afterSalesVo != null && afterSalesVo.getAfterCapitalBillList() != null && afterSalesVo.getAfterCapitalBillList().size()>0){
					//资金流水交易方式
					List<SysOptionDefinition> traderModes=getSysOptionDefinitionList(519);
					mv.addObject("traderModes", traderModes);
					
					//资金流水交易方式
					List<SysOptionDefinition> bussinessTypes=getSysOptionDefinitionList(524);
					mv.addObject("bussinessTypes", bussinessTypes);
				}
				CommunicateRecord communicateRecord = new CommunicateRecord();
				communicateRecord.setAfterSalesId(afterSalesVo.getAfterSalesId());
				List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
				mv.addObject("communicateList", crList);
				//发票类型
				List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
				mv.addObject("invoiceTypeList", invoiceTypeList);
				/*if(afterSalesVo.getSubjectType().intValue() == 536){//采购
				}else{//
					List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
					mv.addObject("invoiceTypeList", invoiceTypeList);
				}*/
				
				if(afterSalesVo.getAfterPayApplyList() != null){
				    for (int i = 0; i < afterSalesVo.getAfterPayApplyList().size(); i++) {
					if (afterSalesVo.getAfterPayApplyList().get(i).getValidStatus() == 0 || afterSalesVo.getAfterPayApplyList().get(i).getValidStatus() == 2) {
					    if (afterSalesVo.getAfterPayApplyList().get(i).getValidStatus() == 0) {
						isPayApplySh = 1;
					    }
					   // payApplyId = afterSalesVo.getAfterPayApplyList().get(i).getPayApplyId();
					    break;
					}
				    }
				    if (!afterSalesVo.getAfterPayApplyList().isEmpty() && payApplyId == 0) {
					payApplyId = afterSalesVo.getAfterPayApplyList().get(0).getPayApplyId();
				    }
				    mv.addObject("isPayApplySh", isPayApplySh);
				    mv.addObject("payApplyId", payApplyId);
				}
			}
		} catch (Exception e) {
			logger.error("getFinanceAfterSaleDetail:", e);
		}
		if(afterSalesVo == null){
			return null;
		}else if(afterSalesVo.getType()==539){//销售订单退货
			mv.setViewName("finance/after/finance_after_sale_th");
		}else if(afterSalesVo.getType()==540){//销售换货
			mv.setViewName("finance/after/finance_after_sale_hh");
		}else if(afterSalesVo.getType()==541 || afterSalesVo.getType()==584){//541销售安调--584销售维修
			if(afterSalesVo.getType()==541){
				mv.addObject("shjsp", "销售安调-售后");
			}else{
				mv.addObject("shjsp", "销售维修-售后");
			}
			mv.setViewName("finance/after/finance_after_sale_at");
		}else if(afterSalesVo.getType()==542){//销售退票
			mv.setViewName("finance/after/finance_after_sale_tp");
		}else if(afterSalesVo.getType()==543){//销售退款
			mv.setViewName("finance/after/finance_after_sale_tk");
		}else if(afterSalesVo.getType()==546){//采购退货
			mv.setViewName("finance/after/finance_after_buy_th");
		}else if(afterSalesVo.getType()==547){//采购换货
			mv.setViewName("finance/after/finance_after_buy_hh");
		}else if(afterSalesVo.getType()==550 || afterSalesVo.getType()==585){//550第三方安调--585第三方维修
			mv.setViewName("finance/after/finance_after_other_at");
		}else if(afterSalesVo.getType()==551){//第三方退款
			mv.setViewName("finance/after/finance_after_other_tk");
		}
		
		Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "afterSalesVerify_"+ afterSalesVo.getAfterSalesId());
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		mv.addObject("taskInfo", historicInfo.get("taskInfo"));
		mv.addObject("startUser", historicInfo.get("startUser"));
		mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mv.addObject("endStatus",historicInfo.get("endStatus"));
		mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mv.addObject("commentMap", historicInfo.get("commentMap"));
	    	String verifyUsers = null;
	    	if(null!=taskInfo){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfo);
	    	    verifyUsers = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mv.addObject("verifyUsers", verifyUsers);
	    	
	    	Map<String, Object> historicInfoPay=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ payApplyId);
	    	Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
	    	mv.addObject("taskInfoPay", taskInfoPay);
	    	mv.addObject("startUserPay", historicInfoPay.get("startUser"));
		// 最后审核状态
	    	mv.addObject("endStatusPay",historicInfoPay.get("endStatus"));
	    	mv.addObject("historicActivityInstancePay", historicInfoPay.get("historicActivityInstance"));
	    	mv.addObject("commentMapPay", historicInfoPay.get("commentMap"));
	    	mv.addObject("candidateUserMapPay", historicInfoPay.get("candidateUserMap"));
	    	
	    	String verifyUsersPay = null;
	    	if(null!=taskInfoPay){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoPay);
	    	    verifyUsersPay = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mv.addObject("verifyUsersPay", verifyUsersPay);
	    	
	    	//开票申请审核信息
	    	InvoiceApply invoiceApplyInfo= invoiceService.getInvoiceApplyByRelatedId(afterSalesVo.getAfterSalesId(),SysOptionConstant.ID_504,afterSalesVo.getCompanyId());
	    	mv.addObject("invoiceApply", invoiceApplyInfo);
	    	if(invoiceApplyInfo != null) {
	    		Map<String, Object> historicInfoInvoice=actionProcdefService.getHistoric(processEngine, "invoiceVerify_"+ invoiceApplyInfo.getInvoiceApplyId());
	    		mv.addObject("taskInfoInvoice", historicInfoInvoice.get("taskInfo"));
	    		mv.addObject("startUserInvoice", historicInfoInvoice.get("startUser"));
	    		mv.addObject("candidateUserMapInvoice", historicInfoInvoice.get("candidateUserMap"));
	    		// 最后审核状态
	    		mv.addObject("endStatusInvoice",historicInfoInvoice.get("endStatus"));
	    		mv.addObject("historicActivityInstanceInvoice", historicInfoInvoice.get("historicActivityInstance"));
	    		mv.addObject("commentMapInvoice", historicInfoInvoice.get("commentMap"));
	    		
	    		Task taskInfoInvoice = (Task) historicInfoInvoice.get("taskInfo");
	    		//当前审核人
	    		String verifyUsersInvoice = null;
	    		if(null!=taskInfoInvoice){
	    			Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoInvoice);
	    			verifyUsersInvoice = (String) taskInfoVariables.get("verifyUsers");
	    		}
	    		mv.addObject("verifyUsersInvoice", verifyUsersInvoice);	
	    	}
		
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 售后订单中添加交易记录
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月24日 上午9:02:25
	 */
	@ResponseBody
	@RequestMapping(value="addAfterCapitalBill")
	@SystemControllerLog(operationType = "add",desc = "售后订单中添加交易记录")
	public ModelAndView addAfterCapitalBill(HttpServletRequest request,AfterSalesDetailVo afterDetailVo,@RequestParam(value="billType") Integer billType){
		ModelAndView mv = new ModelAndView();
		AfterSalesDetailVo afterSalesDetailVo = invoiceAfterService.getAfterCapitalBillInfo(afterDetailVo);
		mv.addObject("billType", billType);//1收款，2退款
		//获取订单基本信息
		if(afterSalesDetailVo != null && afterSalesDetailVo.getSubjectType() != null){
			if(billType.intValue() == 1){
				TraderCustomerVo customerInfo = traderCustomerService.getTraderCustomerInfo(afterSalesDetailVo.getTraderId());
				mv.addObject("customerInfo", customerInfo);
			}
			mv.setViewName("finance/after/add_after_capital_bill");
			/*if(afterSalesDetailVo.getSubjectType().intValue() == SysOptionConstant.ID_535){//销售
			}else if(afterSalesDetailVo.getSubjectType().intValue() == SysOptionConstant.ID_536){//采购
				
			}else if(afterSalesDetailVo.getSubjectType().intValue() == SysOptionConstant.ID_537){//第三方
				
			}*/
		}
		mv.addObject("afterSalesDetailVo", afterSalesDetailVo);
		//交易方式
		List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(SysOptionConstant.ID_519);
		mv.addObject("traderModeList", traderModeList);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 财务售后收款
	 * @param request
	 * @param afterDetailVo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月3日 下午1:55:30
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="addFinanceAfterCapital")
	public ModelAndView addFinanceAfterCapital(HttpServletRequest request,AfterSalesDetailVo afterDetailVo,@RequestParam(required = false,value="billType") Integer billType,@RequestParam(required = false,value="payApplyId") Integer payApplyId,@RequestParam(required = false,value="taskId") String taskId
			,@RequestParam(required = false,value="pageType") Integer pageType){
		ModelAndView mv = new ModelAndView();
		AfterSalesDetailVo afterSalesDetailVo = invoiceAfterService.getAfterCapitalBillInfo(afterDetailVo);
		// 获取付款交易方式
		List<SysOptionDefinition> payTypeName = getSysOptionDefinitionList(SysOptionConstant.ID_640);
		mv.addObject("payTypeName",payTypeName);
		mv.addObject("pageType",pageType);
		//获取订单基本信息
		if(afterSalesDetailVo != null && afterSalesDetailVo.getSubjectType() != null){
			if(afterSalesDetailVo.getSubjectType().intValue() == SysOptionConstant.ID_535){//销售
				if(afterSalesDetailVo.getAfterType().intValue()  == 539){//539销售订单退货
					if(billType.intValue() == 1){//收款
						if(afterSalesDetailVo.getTraderId() != null){
							//账户余额
							/*TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(afterSalesDetailVo.getTraderId());
							mv.addObject("amount", customer==null?0:customer.getAmount());*/
							Saleorder so = new Saleorder();
							so.setSaleorderId(afterSalesDetailVo.getOrderId());
							Saleorder saleorder = saleorderService.getBaseSaleorderInfo(so);
							mv.addObject("paymentComments", saleorder==null?"":saleorder.getPaymentComments());
						}
						mv.setViewName("finance/after/add_after_capital_sale_sk");//收款
					}else if(billType.intValue() == 2){//退款
						mv.setViewName("finance/after/add_after_capital_sale_tk");//退款
					}
				}else if(afterSalesDetailVo.getAfterType().intValue()  == 540){//540销售订单换货     
					if(afterSalesDetailVo.getTraderId() != null){
						//账户余额
						TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(afterSalesDetailVo.getTraderId());
						mv.addObject("amount", customer==null?0:customer.getAmount());
					}
					mv.setViewName("finance/after/add_after_capital_sale_sk");//收款
				}else if(afterSalesDetailVo.getAfterType().intValue() == 541 || afterSalesDetailVo.getAfterType().intValue() == 584){//541销售订单安调--584销售订单维修
					if(billType.intValue() == 1){//收款
						//账户余额
						/*TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(afterSalesDetailVo.getTraderId());
						mv.addObject("amount", customer==null?0:customer.getAmount());*/
						mv.setViewName("finance/after/add_after_capital_sale_sk");//收款
					}else if(billType.intValue() == 2){//付款
						//获取付款信息
						PayApply payApply = payApplyService.getPayApplyInfo(afterDetailVo.getPayApplyId());
						mv.addObject("payApply", payApply);
						mv.setViewName("finance/after/add_after_capital_pay");//付款
					}
				}else if(afterSalesDetailVo.getAfterType().intValue() == 543){//销售订单退款
					mv.setViewName("finance/after/add_after_capital_sale_tk");//退款
				}
			}else if(afterSalesDetailVo.getSubjectType().intValue() == SysOptionConstant.ID_536){//采购
				if(afterSalesDetailVo.getAfterType().intValue() == 546){//采购订单退货
					if(billType != null && billType.intValue() == 2){//付款
						//获取付款信息
						PayApply payApply = payApplyService.getPayApplyInfo(afterDetailVo.getPayApplyId());
						mv.addObject("payApply", payApply);
						mv.setViewName("finance/after/add_after_capital_pay");//付款
					}else{
						mv.setViewName("finance/after/add_after_capital_buy_in");//收款:我们收入
					}
				}else if(afterSalesDetailVo.getAfterType().intValue() == 547){//采购订单换货
					//获取付款信息
					PayApply payApply = payApplyService.getPayApplyInfo(afterDetailVo.getPayApplyId());
					mv.addObject("payApply", payApply);
					mv.setViewName("finance/after/add_after_capital_pay");//付款:我们付退换货手续费
				}
			}else if(afterSalesDetailVo.getSubjectType().intValue() == SysOptionConstant.ID_537){//第三方
				if(afterSalesDetailVo.getAfterType().intValue() == 550 || afterSalesDetailVo.getAfterType().intValue() == 585){//550第三方安调--585第三方维修
					if(billType != null && billType.intValue() == 2){//付款
						//获取付款信息
						PayApply payApply = payApplyService.getPayApplyInfo(afterDetailVo.getPayApplyId());
						mv.addObject("payApply", payApply);
						mv.setViewName("finance/after/add_after_capital_pay");//订单付款
					}else{
						mv.setViewName("finance/after/add_after_capital_other_in");//订单收款
					}
				}else if(afterSalesDetailVo.getAfterType().intValue() == 551){//第三方退款
					mv.setViewName("finance/after/add_after_capital_other_out");//退款
				}
			}
		}
		mv.addObject("afterSalesDetailVo", afterSalesDetailVo);
		//付款申请ID
		mv.addObject("payApplyId", payApplyId);
		//流程节点ID
		mv.addObject("taskId", taskId);
		//交易方式
		List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(SysOptionConstant.ID_519);
		mv.addObject("traderModeList", traderModeList);
		//业务类型
		List<SysOptionDefinition> bussinessTypeList = getSysOptionDefinitionList(524);
		mv.addObject("bussinessTypeList", bussinessTypeList);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 财务售后退款（销售退款-第三方退款）
	 * @param request
	 * @param afterDetailVo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月3日 下午6:05:07
	 */
	/*@ResponseBody
	@RequestMapping(value="addAfterCapitalTk")
	public ModelAndView addAfterCapitalTk(HttpServletRequest request,AfterSalesDetailVo afterDetailVo){
		ModelAndView mv = new ModelAndView();
		AfterSalesDetailVo afterSalesDetailVo = invoiceAfterService.getAfterCapitalBillInfo(afterDetailVo);
		//获取订单基本信息
		if(afterSalesDetailVo != null && afterSalesDetailVo.getSubjectType() != null){
			mv.setViewName("finance/after/add_after_capital_tk");
			//交易方式
			List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(SysOptionConstant.ID_519);
			mv.addObject("traderModeList", traderModeList);
			//业务类型
			List<SysOptionDefinition> bussinessTypeList = getSysOptionDefinitionList(524);
			mv.addObject("bussinessTypeList", bussinessTypeList);
		}
		mv.addObject("afterSalesDetailVo", afterSalesDetailVo);
		return mv;
	}*/
	
	
	/**
	 * <b>Description:</b><br> 确认退票操作初始化
	 * @param request
	 * @param afterSalesInvoiceVo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月24日 下午6:13:00
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="getAfterReturnInvoiceInfo")
	public ModelAndView getAfterReturnInvoiceInfo(HttpServletRequest request,AfterSalesInvoiceVo afterInvoiceVo){
		ModelAndView mv = new ModelAndView();
		//根据售后单号查询需要退票的发票信息
		AfterSalesInvoiceVo  afterInvoice= invoiceAfterService.getAfterReturnInvoiceInfo(afterInvoiceVo);
		if(afterInvoice!=null){
			//发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			/*if(afterInvoice.getSubjectType().intValue() == SysOptionConstant.ID_535){
				invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			}else if(afterInvoice.getSubjectType().intValue() == SysOptionConstant.ID_536){
				//发票类型
				invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			}*/
			mv.addObject("invoiceTypeList", invoiceTypeList);
			mv.addObject("afterInvoice", afterInvoice);
		}
		if(afterInvoice != null && afterInvoice.getAfterType() != null && afterInvoice.getAfterType().equals(542) //销售退票
				&& afterInvoice.getCurrentMonthInvoice() != null && afterInvoice.getCurrentMonthInvoice().equals(0)){//非当月发票
			mv.setViewName("finance/after/after_return_invoice_tp");
		}else{
			mv.setViewName("finance/after/after_return_invoice");
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 售后-退货退票保存
	 * @param request
	 * @param invoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月26日 下午3:46:57
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="saveAfterReturnInvoice")
	@SystemControllerLog(operationType = "add",desc = "售后-退货退票保存")
	public ModelAndView saveAfterReturnInvoice(HttpServletRequest request,Invoice invoice
			,@RequestParam(required = false, value="currentMonthInvoice") Integer currentMonthInvoice
			,@RequestParam(required = false, value="detailGoodsIdArr") String detailGoodsIdArr
			,@RequestParam(required = false, value="invoiceAmountArr") String invoiceAmountArr
			,@RequestParam(required = false, value="invoicePriceArr") String invoicePriceArr
			,@RequestParam(required = false, value="invoiceNumArr") String invoiceNumArr){
		ModelAndView mv = new ModelAndView();
		
		try {
			if(currentMonthInvoice.intValue() == 0){
				List<Integer> detailGoodsIdList = JSON.parseArray("["+detailGoodsIdArr+"]",Integer.class);
				List<BigDecimal> invoiceNumStr = JSON.parseArray("["+invoiceNumArr+"]",BigDecimal.class);
				List<BigDecimal> invoiceAmountStr = null;
				if(StringUtils.isNotBlank(invoiceAmountArr) && invoiceAmountArr.length() > 0){
					invoiceAmountStr = JSON.parseArray("["+invoiceAmountArr+"]",BigDecimal.class);
				}
				List<BigDecimal> invoicePriceStr = JSON.parseArray("["+invoicePriceArr+"]",BigDecimal.class);
				//根据税率计算出开票单价
				List<BigDecimal> invoicePriceList = new ArrayList<>();//开票单价
				List<BigDecimal> invoiceTotleAmountList = new ArrayList<>();//开票总额
				BigDecimal totleAmount = new BigDecimal(0);
				if(invoice != null && invoice.getRatio()!=null){
					for(int i=0;i<invoicePriceStr.size();i++){
						if(invoiceAmountStr != null && invoiceAmountStr.size() == invoicePriceStr.size()){
							invoiceTotleAmountList.add(invoiceAmountStr.get(i).setScale(2,BigDecimal.ROUND_HALF_UP));//开票单价*数量
						}else{
							invoiceTotleAmountList.add(invoicePriceStr.get(i).multiply(invoiceNumStr.get(i)).setScale(2,BigDecimal.ROUND_HALF_UP));//开票单价*(红字是退货)数量
						}
						invoicePriceList.add(invoicePriceStr.get(i).divide((invoice.getRatio().add(new BigDecimal(1))),10,BigDecimal.ROUND_HALF_UP).setScale(8,BigDecimal.ROUND_HALF_UP));//单价/(税率+1)
						totleAmount = totleAmount.add(invoiceTotleAmountList.get(i));
					}
					invoice.setAmount(totleAmount);
				}
				invoice.setDetailGoodsIdList(detailGoodsIdList);
				invoice.setInvoiceNumList(invoiceNumStr);
				invoice.setInvoicePriceList(invoicePriceList);
				invoice.setInvoiceTotleAmountList(invoiceTotleAmountList);
			}
			if(invoice.getType() == null){
				invoice.setType(SysOptionConstant.ID_505);//销售退票--退票记录跟着订单走
			}
			//保存售后退票发票信息
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			if(user!=null){
				invoice.setAddTime(DateUtil.gainNowDate());
				invoice.setCompanyId(user.getCompanyId());
				invoice.setCreator(user.getUserId());
			}
			ResultInfo<?> result = invoiceAfterService.saveAfterReturnInvoice(invoice);
			if(result.getCode()==0){
				mv.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
//				mv.addObject("url","./getAfterReturnInvoiceInfo.do?afterSalesId="+invoice.getRelatedId());
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveAfterReturnInvoice:", e);
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 财务-售后-安调-付款审核不通过原因页面初始化
	 * @param request
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月30日 下午5:49:02
	 */
	@ResponseBody
	@RequestMapping(value="afterSalePayAppleAuditReason")
	public ModelAndView afterSalePayAppleAuditReason(HttpServletRequest request,PayApply payApply){
		ModelAndView mv = new ModelAndView();
		mv.addObject("payApply", payApply);
		mv.setViewName("finance/after/payapply_audit_reason");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存财务-售后-安调-付款申请审核结果
	 * @param request
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月30日 下午6:00:21
	 */
	@ResponseBody
	@RequestMapping(value="editAfterPayApplyAudit")
	public ResultInfo<?> editAfterPayApplyAudit(HttpServletRequest request,PayApply payApply){
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			if(user != null){
				payApply.setUpdater(user.getUserId());
				payApply.setModTime(DateUtil.gainNowDate());
				payApply.setValidTime(DateUtil.gainNowDate());
			}
			if(payApply.getValidStatus().intValue() == 1){//通过
				return payApplyService.payApplyPass(payApply);
			}else{
				return payApplyService.payApplyNoPass(payApply);
			}
		} catch (Exception e) {
			logger.error("editAfterPayApplyAudit:", e);
			return new ResultInfo<>();
		}
	}
	
	/**
	 * <b>Description:</b><br> 财务-售后-安调-新增发票 
	 * @param request
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月30日 下午6:22:39
	 */
	@ResponseBody
	@RequestMapping(value="addAfterInvoiceAt")
	public ModelAndView addAfterInvoiceAt(HttpServletRequest request,AfterSalesGoodsVo afterSalesGoodsVo){
		ModelAndView mv = new ModelAndView();
		//获取售后安调开具发票信息
		afterSalesGoodsVo = invoiceAfterService.getAfterOpenInvoiceInfoAt(afterSalesGoodsVo);
		if(afterSalesGoodsVo!=null){
			mv.addObject("afterSalesGoodsVo", afterSalesGoodsVo);
			//发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			mv.addObject("invoiceTypeList", invoiceTypeList);
		}
		mv.setViewName("finance/after/add_after_invoice_at");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存-财务-售后-安调-开票信息（服务费）
	 * @param request
	 * @param invoice
	 * @param invoiceDetail
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月31日 下午2:03:45
	 */
	@ResponseBody
	@RequestMapping(value="saveAfterOpenInvoiceAt")
	@SystemControllerLog(operationType = "add",desc = "保存财务-售后-安调-开票信息")
	public ModelAndView saveAfterOpenInvoiceAt(HttpServletRequest request,Invoice invoice,InvoiceDetail invoiceDetail){
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			if(user != null){
				invoice.setCompanyId(user.getCompanyId());
				invoice.setCreator(user.getUserId());
				invoice.setAddTime(DateUtil.gainNowDate());
				invoice.setUpdater(user.getUserId());
				invoice.setModTime(DateUtil.gainNowDate());
			}
			//一个售后订单只允许有一个安调服务
			invoice.setDetailGoodsIdList(Arrays.asList(invoiceDetail.getDetailgoodsId()));
			invoice.setInvoiceNumList(Arrays.asList(invoiceDetail.getNum()));
			invoice.setInvoicePriceList(Arrays.asList(invoiceDetail.getPrice()));
			invoice.setInvoiceTotleAmountList(Arrays.asList(invoiceDetail.getTotalAmount()));
			
			invoice.setType(SysOptionConstant.ID_504);//售后
			invoice.setValidStatus(1);//售后开票，默认通过
			invoice.setTag(1);//开票
			ResultInfo<?> result = invoiceAfterService.saveAfterOpenInvoiceAt(invoice);
			if(result.getCode()==0){
				mv.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveAfterOpenInvoiceAt:", e);
			return fail(mv);
		}
	}
	
	
	/**
	 * <b>Description:</b><br> 财务-售后-退票-新增发票 
	 * @param request
	 * @param afterSalesGoodsVo
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月1日 上午9:15:56
	 */
	@ResponseBody
	@RequestMapping(value="addAfterInvoiceTp")
	public ModelAndView addAfterInvoiceTp(HttpServletRequest request,Invoice invoice){
		ModelAndView mv = new ModelAndView();
		invoice.setType(SysOptionConstant.ID_505);//销售开票
		//获取售后退票开具发票信息
		invoice = invoiceAfterService.getAfterOpenInvoiceInfoTp(invoice);
		if(invoice!=null){
			mv.addObject("invoice", invoice);
			//发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			mv.addObject("invoiceTypeList", invoiceTypeList);
		}
		mv.setViewName("finance/after/add_after_invoice_tp");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 售后退票-重新开票保存
	 * @param request
	 * @param invoice
	 * @param detailGoodsIdArr
	 * @param invoiceNumIdArr
	 * @param invoicePriceIdArr
	 * @param invoiceTotleAmountArr
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月1日 下午4:48:27
	 */
	@ResponseBody
	@RequestMapping(value="saveAfterOpenInvoiceTp")
	@SystemControllerLog(operationType = "add",desc = "保存财务-售后退票后重新开票")
	public ModelAndView saveAfterOpenInvoiceTp(HttpServletRequest request,Invoice invoice
			,@RequestParam(required = false, value="detailGoodsIdArr") String detailGoodsIdArr
			,@RequestParam(required = false, value="invoiceNumIdArr") String invoiceNumIdArr
			,@RequestParam(required = false, value="invoicePriceIdArr") String invoicePriceIdArr
			,@RequestParam(required = false, value="invoiceTotleAmountArr") String invoiceTotleAmountArr){
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			if(user != null){
				invoice.setCreator(user.getUserId());
				invoice.setCompanyId(user.getCompanyId());
				invoice.setAddTime(DateUtil.gainNowDate());
			}
			
			List<Integer> detailGoodsIdList = JSON.parseArray("["+detailGoodsIdArr+"]",Integer.class);
			List<BigDecimal> invoiceNumList = JSON.parseArray("["+invoiceNumIdArr+"]",BigDecimal.class);
			List<BigDecimal> invoicePriceList = JSON.parseArray("["+invoicePriceIdArr+"]",BigDecimal.class);
			List<BigDecimal> invoiceTotleAmountList = JSON.parseArray("["+invoiceTotleAmountArr+"]",BigDecimal.class);
			
			invoice.setDetailGoodsIdList(detailGoodsIdList);
			invoice.setInvoiceNumList(invoiceNumList);
			invoice.setInvoicePriceList(invoicePriceList);
			invoice.setInvoiceTotleAmountList(invoiceTotleAmountList);
			
			invoice.setType(SysOptionConstant.ID_504);//售后
			invoice.setTag(1);//开票
			ResultInfo<?> result = invoiceAfterService.saveAfterOpenInvoiceTp(invoice);
			if(result.getCode()==0){
				mv.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
				return success(mv);
			}else{
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveAfterOpenInvoiceTp:", e);
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 编辑售后发票记录信息 --方法现在不用了
	 * @param request
	 * @param afterSalesInvoice
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月6日 下午2:37:08
	 */
	@ResponseBody
	@RequestMapping(value="editAfterInvoice")
	public ResultInfo<?> editAfterInvoice(HttpServletRequest request,AfterSalesInvoice afterSalesInvoice){
		try {
			return invoiceAfterService.editAfterInvoice(afterSalesInvoice);
		} catch (Exception e) {
			logger.error("editAfterInvoice:", e);
			return new ResultInfo<>();
		}
	}
	
	/**
	 * <b>Description:</b><br> 销售售后订单退货--确认退款到余额操作
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月21日 上午9:24:35
	 */
	@ResponseBody
	@RequestMapping(value="afterThRefundAmountBalance")
	@SystemControllerLog(operationType = "edit",desc = "销售售后订单退货--确认退款到余额操作")
	public ResultInfo<?> afterThRefundAmountBalance(AfterSales afterSales){
		try {
			return invoiceAfterService.afterThRefundAmountBalance(afterSales);
		} catch (Exception e) {
			logger.error("afterThRefundAmountBalance:", e);
			return new ResultInfo<>();
		}
	}
	
	/**
	 * 
	 * <b>Description:</b><br> 售后付款申请审核弹层
	 * @param session
	 * @param taskId
	 * @param pass
	 * @param type
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 201a8年1月31日 下午6:15:17
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session, String taskId, Boolean pass,Integer type) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.addObject("type", type);
		mv.setViewName("finance/after/complement");
		return mv;
	}
	
	/**
	 * 
	 * <b>Description:</b><br> 售后付款申请审核操作
	 * @param request
	 * @param taskId
	 * @param comment
	 * @param pass
	 * @param buyorderId
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年1月31日 下午6:22:49
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	@SystemControllerLog(operationType = "edit", desc = "采购单审核操作")
	public ResultInfo<?> complementTask(HttpServletRequest request, String taskId, String comment, Boolean pass,
			Integer buyorderId, HttpSession session) {
		// 获取session中user信息
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		// 审批操作
		try {
			// 如果审核没结束添加审核对应主表的审核状态
			Integer status = 0;
			TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
			String id = (String) taskService.getVariable(taskId, "id");
			Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
			String key = (String) taskService.getVariable(taskId, "key");
			String tableName = (String) taskService.getVariable(taskId, "tableName");
			// 使用任务id,获取任务对象，获取流程实例id
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String taskName = task.getName();

			PayApply payApply = payApplyService.getPayApplyInfo(idValue);
			if (payApply == null){
				return new ResultInfo(-1,"退款支付申请不存在");
			}

			if (pass) {
				// 如果审核通过
				status = 0;

				if(tableName.equals("T_PAY_APPLY") && taskName.equals("财务制单")){

					//根据付款申请查询关联表，如果是售后，并且是售后类型是539,543；
					//VDERP-2193 如果是售后退货、退款生成的支付申请，那么在制单之前增加限制：退款金额不能大于账户余额
					TraderCustomer traderCustomer = null;
					AfterSales afterSales = afterSalesOrderService.getAfterSalesById(payApply.getRelatedId());
					if (SysOptionConstant.ID_539.equals(afterSales.getType()) || SysOptionConstant.ID_543.equals(afterSales.getType())){
						//获取售后支付申请客户的余额信息
						traderCustomer = traderCustomerService.getTraderByPayApply(idValue);
						if (payApply.getAmount().compareTo(traderCustomer.getAmount()) > 0){
							return new ResultInfo<>(-1,"退款金额大于账户余额，无法退款");
						}
					}


					//制单
					actionProcdefService.updateInfo(tableName, id, idValue, "IS_BILL", 1, 2);

					////VDERP-2193 如果是售后退货、退款生成的支付申请，那么制单成功则扣减余额
					if (traderCustomer != null){
						traderCustomerService.updateTraderAmount(traderCustomer.getTraderId(),payApply.getAmount().multiply(new BigDecimal(-1)));
						logger.info("售后订单：{}在财务制单环节，余额扣减金额：{}",payApply.getRelatedId(),payApply.getAmount());
					}

				}
			} else {
				// 如果审核不通过
				status = 2;
				// 回写数据的表在db中
				variables.put("db", 2);
				if(tableName != null && id != null && idValue != null && key != null){
				    actionProcdefService.updateInfo(tableName, id, idValue, key, 2, 2);
				}
				verifiesRecordService.saveVerifiesInfo(taskId, status);


				// 流程 paymentVerify:3:1792504 的财务审核节点，点击不通过，即售后退款财务审核不通过；
				// VDERP-2193 将售后退款流程中制单环节提前扣减余额的款项补加回来

				if ("T_PAY_APPLY".equals(tableName) && "财务审核".equals(taskName)){

					if (payApply.getPayType().equals(SysOptionConstant.ID_518)){

						AfterSales afterSales = afterSalesOrderService.getAfterSalesById(payApply.getRelatedId());
						if (SysOptionConstant.ID_539.equals(afterSales.getType()) || SysOptionConstant.ID_543.equals(afterSales.getType())){

							Optional.ofNullable(traderCustomerService.getTraderByPayApply(idValue))
									.ifPresent(traderCustomer -> {
										traderCustomerService.updateTraderAmount(traderCustomer.getTraderId(),payApply.getAmount());
										logger.info("售后订单：{}在财务审核环节，审核不通过，余额增加金额：{}",payApply.getRelatedId(),payApply.getAmount());
									});
						}

					}

				}

			}
			ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment,
					user.getUsername(), variables);
			// 如果未结束添加审核对应主表的审核状态
			if (!complementStatus.getData().equals("endEvent")) {
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}
		
				return new ResultInfo(0, "操作成功");
			
		} catch (Exception e) {
			logger.error("invoice after complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}
	
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/paymentVerify")
	public ModelAndView paymentVerify(HttpSession session, Integer payApplyId) {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> historicInfoPay=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ payApplyId);
	    	Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
	    	mv.addObject("taskInfoPay", taskInfoPay);
	    	mv.addObject("startUserPay", historicInfoPay.get("startUser"));
		// 最后审核状态
	    	mv.addObject("endStatusPay",historicInfoPay.get("endStatus"));
	    	mv.addObject("historicActivityInstancePay", historicInfoPay.get("historicActivityInstance"));
	    	mv.addObject("commentMapPay", historicInfoPay.get("commentMap"));
	    	mv.addObject("candidateUserMapPay", historicInfoPay.get("candidateUserMap"));
	    	
	    	String verifyUsersPay = null;
	    	if(null!=taskInfoPay){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoPay);
	    	    verifyUsersPay = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mv.addObject("verifyUsersPay", verifyUsersPay);
		mv.setViewName("finance/invoice/paymentVerify");
		return mv;
	}
	
	/**
	 * 
	 * <b>Description: 耗材线上售后退款</b><br> 
	 * @param request
	 * @param vo
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2018年12月19日 下午2:00:27 </b>
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="executeOnlineRefund")
	public ResultInfo<?> executeOnlineRefund(HttpServletRequest request, AfterSalesVo vo)
	{
		// 正常响应
		ResultInfo<?> result = new ResultInfo<>(0, "成功");
		
		// 获取session中user信息
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// 非空
		if(null != user)
		{
			vo.setCompanyId(user.getCompanyId());
		}
		// 请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("version", "v1");
		// 返回响应结果
		TypeReference<ResultInfo<?>> resultType = new TypeReference<ResultInfo<?>>() {};
		// 售后线上退款接口
		result = HttpRestClientUtil.post(restDbUrl + DbRestInterfaceConstant.DB_REST_FINANCE_OPERATE_AFTER_SALE_ONLINE_REFUND, resultType, headers, vo);
		
		return result;
	}
}
