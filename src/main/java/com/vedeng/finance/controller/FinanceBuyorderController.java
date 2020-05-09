package com.vedeng.finance.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.goods.service.VgoodsService;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.PayApplyDetail;
import com.vedeng.finance.service.BankBillService;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.TagService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderSupplier;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.TraderCustomerService;
import com.vedeng.trader.service.TraderSupplierService;

/**
 * <b>Description:</b><br>
 * 采购订单控制器
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.model <br>
 *       <b>ClassName:</b> BuyorderController <br>
 *       <b>Date:</b> 2017年7月11日 上午9:07:20
 */
@Controller
@RequestMapping("/finance/buyorder")
public class FinanceBuyorderController extends BaseController {

	@Resource
	private BuyorderService buyorderService;
	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;
	@Resource
	private OrgService orgService;
	@Resource
	private UserService userService;
	@Resource
	private TraderSupplierService traderSupplierService;
	@Resource
	private TraderCustomerService traderCustomerService;
	@Resource
	private TagService tagService;
	
	@Autowired
	@Qualifier("payApplyService")
	private PayApplyService payApplyService;
	
	@Autowired
	@Qualifier("capitalBillService")
	private CapitalBillService capitalBillService;

	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;//自动注入invoiceService	
	
	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;
	
	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	@Autowired
	@Qualifier("bankBillService")
	private BankBillService bankBillService;

	@Autowired
	private VgoodsService vGoodsService;

	/**
	 * <b>Description:</b><br> 编辑采购订单 --普发或直发页面
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月25日 下午5:24:24
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value="/editBuyorderPage")
	public ModelAndView editBuyorderPage(HttpServletRequest request,Buyorder buyorder,String uri){
		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder,user);
		mav.addObject("buyorderVo", bv);
		if(bv.getTraderId()!=null && bv.getTraderId()!=0){
			TraderContactVo traderContactVo=new TraderContactVo();
			traderContactVo.setTraderId(bv.getTraderId());
			traderContactVo.setTraderType(ErpConst.TWO);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
			List<TraderAddressVo> taList = (List<TraderAddressVo>)map.get("address");
			mav.addObject("contactList", list);
			mav.addObject("tarderAddressList", taList);
		}
		//普发收货地址
		if(bv.getDeliveryDirect()==0){
			ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
			paramsConfigValue.setCompanyId(user.getCompanyId());
			paramsConfigValue.setParamsConfigId(ErpConst.TWO);
			mav.addObject("addressList", buyorderService.getAddressVoList(paramsConfigValue));
		}
		//付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);

		//收票种类
		List<SysOptionDefinition> receiptTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("receiptTypes", receiptTypes);

		//运费说明
		List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(SysOptionConstant.ID_469);
		mav.addObject("freightDescriptions", freightDescriptions);

		//物流公司
		List<Logistics> logisticsList = getLogisticsList(user.getCompanyId());
		mav.addObject("logisticsList", logisticsList);
		if(uri==null || "".equals(uri)){
			uri = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/order/buyorder/addBuyorderPage.do";
		}
		mav.addObject("uri",uri);
		mav.setViewName("order/buyorder/add_buyorder_pf");
		return mav;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 查询采购订单分页信息
	 * 
	 * @param request
	 * @param buyorderVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月11日 上午9:46:49
	 */
	@ResponseBody
	@RequestMapping(value = "getBuyorderList")
	public ModelAndView indexBuy(HttpServletRequest request, BuyorderVo buyorderVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("finance/buyorder/index");
		// 产品部门--选择条件(所有的采购部门)
		List<Organization> productOrgList = orgService.getOrgListByPositType(SysOptionConstant.ID_311,user.getCompanyId());
		mav.addObject("productOrgList", productOrgList);
		
		// 产品负责人
		List<User> productUserList = userService.getUserListByPositType(SysOptionConstant.ID_311,user.getCompanyId());
		mav.addObject("productUserList", productUserList);
		//收票种类
		List<SysOptionDefinition> receiptTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("receiptTypes", receiptTypes);

		// 通过父部门的id查询所有子部门的id集合

		// 查询所有职位类型为311的员工
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_311);//采购
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mav.addObject("userList", userList);
		List<Integer> userIds = new ArrayList<>();
		
		if (buyorderVo.getProUserId() != null && buyorderVo.getProUserId() != 0) {
			userIds.add(buyorderVo.getProUserId());
			buyorderVo.setUserIds(userIds);
		}
		
		if (buyorderVo.getProOrgtId() != null && buyorderVo.getProOrgtId() != 0) {
			buyorderVo.setOrgId(buyorderVo.getProOrgtId());
		}
		
		Page page = getPageTag(request, pageNo, pageSize);
		
		//获取当前日期
		Date date = new Date();
		String nowDate = DateUtil.DatePreMonth(date, 0, null);
		mav.addObject("nowDate", nowDate);
		//获取当月第一天日期
		String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
		mav.addObject("pre1MonthDate", pre1MonthDate);
		
		String start = request.getParameter("searchBegintimeStr");
		String end = request.getParameter("searchEndtimeStr");
		if (start != null && !"".equals(start)) {
			buyorderVo.setSearchBegintime(DateUtil.convertLong(start, DateUtil.DATE_FORMAT));
		} else {
			buyorderVo.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if ("".equals(start)) {
			buyorderVo.setSearchBegintime(null);
		}
		
		if (end != null && !"".equals(end)) {
			buyorderVo.setSearchEndtime(DateUtil.convertLong(end + " 23:59:59", DateUtil.TIME_FORMAT));
		} else {
			buyorderVo.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if ("".equals(end)) {
			buyorderVo.setSearchEndtime(null);
		}
		
		buyorderVo.setValidStatus(1);//查看生效订单
		buyorderVo.setSearchDateType(2);//搜索生效时间
		
		buyorderVo.setIsBuyorderTotal(1);
		buyorderVo.setCompanyId(user.getCompanyId());
		Map<String, Object> map = buyorderService.getBuyorderVoPage(buyorderVo, page);
		if(map!=null){
			mav.addObject("list", map.get("list"));
			List<Buyorder> list = (List<Buyorder>)map.get("list");
			
			/*
			List<Integer> buyorderIdList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				buyorderIdList.add(list.get(i).getBuyorderId());
			}
			//根据采购订单款---换成Jerry写的方法
			List<BuyorderData> capitalBillList = capitalBillService.getCapitalListByBuyorderId(buyorderIdList);
			mav.addObject("capitalBillList",capitalBillList);*/
		}

		mav.addObject("list", map.get("list"));
		
		buyorderVo.setProOrgtId(buyorderVo.getProOrgtId());
		mav.addObject("buyorderVo", map.get("buyorderVo"));
		mav.addObject("page", map.get("page"));
		return mav;
	}
	/**
	 * <b>Description:</b><br>
	 * 根据公司的id以及部门id查询所属员工
	 * 
	 * @param parentId
	 * @param companyId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月13日 上午11:47:48
	 */
	private List<Integer> getUserIdList(Integer parentId, HttpServletRequest request) {
		List<Integer> userIds = new ArrayList<>();
		if (parentId != null && parentId != 0) {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			List<Integer> orgIds = new ArrayList<>();
			List<Organization> list = orgService.getOrgList(parentId, user.getCompanyId(), false);
			if (list != null && list.size() > 0) {
				for (Organization org : list) {
					orgIds.add(org.getOrgId());
				}
			}
			if (orgIds.size() > 0) {
				List<User> uList = userService.getUserListByOrgIds(orgIds, user.getCompanyId());
				if (uList != null && uList.size() > 0) {
					for (User us : uList) {
						userIds.add(us.getUserId());
					}
				}
			}
		}
		return userIds;
	}
	/**
	 * <b>Description:</b><br> 查看采购订单详情-普发
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月19日 上午10:03:21
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "viewBuyorder")
	public ModelAndView viewBuyorder(HttpServletRequest request,Buyorder buyorder,@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,String uri){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv =  buyorderService.getBuyorderVoDetailNew(buyorder,user);
		ModelAndView mav = new ModelAndView();
//		if(bv.getValidStatus()==1){
		mav.setViewName("finance/buyorder/view_buyorder_pf");
//		}else{
//			mav.setViewName("order/buyorder/view_buyorder_sx");
//		}
		mav.addObject("buyorderVo", bv);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(bv.getBuyorderGoodsVoList())){
			List<Integer> skuIds = new ArrayList<>();
			bv.getBuyorderGoodsVoList().stream().forEach(buyOrderGoods -> {
				skuIds.add(buyOrderGoods.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		if(uri==null || "".equals(uri)){
			uri = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/finance/buyorder/viewBuyorder.do";
		}
		mav.addObject("uri",uri);
		CommunicateRecord communicateRecord = new CommunicateRecord();
		communicateRecord.setBuyorderId(bv.getBuyorderId());
		List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
		mav.addObject("communicateList", crList);
		//付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);
		
		//发票类型
		List<SysOptionDefinition> invoiceTypes=getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("invoiceTypes", invoiceTypes);
		//获取发票信息
		List<Invoice> buyInvoiceList = invoiceService.getInvoiceInfoByRelatedId(bv.getBuyorderId(),SysOptionConstant.ID_503);
		mav.addObject("buyInvoiceList", buyInvoiceList);
		
		//获取付款申请列表
		PayApply payApply = new PayApply();
		payApply.setCompanyId(user.getCompanyId());
		payApply.setPayType(517);//采购付款申请
		payApply.setRelatedId(bv.getBuyorderId());
		List<PayApply> payApplyList = payApplyService.getPayApplyList(payApply);
		mav.addObject("payApplyList", payApplyList);
		// 判断是否有正在审核中的付款申请
		Integer isPayApplySh = 0;
		Integer payApplyId = 0;
		for (int i = 0; i < payApplyList.size(); i++) {
			if (payApplyList.get(i).getValidStatus() == 0 || payApplyList.get(i).getValidStatus() == 2) {
				if (payApplyList.get(i).getValidStatus() == 0) {
					isPayApplySh = 1;
				}
				//payApplyId = payApplyList.get(i).getPayApplyId();
				break;
			}
		}
		if (!payApplyList.isEmpty() && payApplyId == 0) {
			payApplyId = payApplyList.get(0).getPayApplyId();
		}
		mav.addObject("payApplyId", payApplyId);
		mav.addObject("isPayApplySh", isPayApplySh);
		//获取交易信息数据
		CapitalBill capitalBill = new CapitalBill();
		capitalBill.setOperationType("finance_buy_detail");
		CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		capitalBillDetail.setOrderType(ErpConst.TWO);//采购订单类型
		capitalBillDetail.setOrderNo(bv.getSaleorderNo());
		capitalBillDetail.setRelatedId(bv.getBuyorderId());
		capitalBill.setCapitalBillDetail(capitalBillDetail);
		List<CapitalBill> capitalBillList = capitalBillService.getCapitalBillList(capitalBill);
		mav.addObject("capitalBillList", capitalBillList);
		
		//资金流水交易方式
		List<SysOptionDefinition> traderModes=getSysOptionDefinitionList(519);
		mav.addObject("traderModes", traderModes);
		
		//资金流水业务类型
		mav.addObject("bussinessTypeList", getSysOptionDefinitionList(524));
		
		//供应商等级
		mav.addObject("gradeList", getSysOptionDefinitionList(7));
		
		if(bv.getTraderId() != null && bv.getTraderId().intValue() > 0){
			//获取供应商基本信息
			TraderSupplier traderSupplier = new TraderSupplier();
			traderSupplier.setTraderId(bv.getTraderId());
			TraderSupplierVo traderBaseInfo = traderSupplierService.getTraderSupplierBaseInfo(traderSupplier);
			mav.addObject("traderBaseInfo", traderBaseInfo);
		}
		
		Map<String, Object> historicInfoPay=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ payApplyId);
	    	Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
	    	mav.addObject("taskInfoPay", taskInfoPay);
	    	mav.addObject("startUserPay", historicInfoPay.get("startUser"));
		// 最后审核状态
	    	mav.addObject("endStatusPay",historicInfoPay.get("endStatus"));
	    	mav.addObject("historicActivityInstancePay", historicInfoPay.get("historicActivityInstance"));
	    	mav.addObject("commentMapPay", historicInfoPay.get("commentMap"));
	    	mav.addObject("candidateUserMapPay", historicInfoPay.get("candidateUserMap"));
	    	
	    	String verifyUsersPay = null;
	    	if(null!=taskInfoPay){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoPay);
	    	    verifyUsersPay = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mav.addObject("verifyUsersPay", verifyUsersPay);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 订单新增沟通记录-普发
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午10:17:31
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "addCommunicatePagePf")
	public ModelAndView addCommunicatePage(Buyorder buyorder, TraderSupplier traderSupplier,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("order/buyorder/add_communicate_pf");
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(traderSupplier.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.TWO);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);
		// 客户标签
		Tag tag = new Tag();
		tag.setTagType(SysOptionConstant.ID_32);
		tag.setIsRecommend(ErpConst.ONE);
		tag.setCompanyId(user.getCompanyId());

		Integer pageNo = 1;
		Integer pageSize = 10;

		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

		mav.addObject("traderSupplier", traderSupplier);
		mav.addObject("buyorder", buyorder);
		mav.addObject("contactList", contactList);

		// 沟通方式
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);

		mav.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mav.addObject("page", (Page) tagMap.get("page"));
		return mav;
	}
	
	
	/**
	 * <b>Description:</b><br> 申请付款页面
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年8月23日 下午4:17:12
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "applyPayment")
	public ModelAndView applyPayment(HttpServletRequest request,Buyorder buyorder) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv =  buyorderService.getBuyorderVoDetail(buyorder,user);
		
		mv.addObject("buyorderVo", bv);
		mv.setViewName("order/buyorder/apply_payment");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存申请付款
	 * @param request
	 * @param payApply
	 * @param priceArr
	 * @param numArr
	 * @param totalAmountArr
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年8月30日 下午6:12:54
	 */
	@ResponseBody
	@RequestMapping(value="saveApplyPayment")
	public ResultInfo<?> saveApplyPayment(HttpServletRequest request,PayApply payApply,
							@RequestParam(required = false, value="priceArr") String priceArr,
							@RequestParam(required = false, value="numArr") String numArr,
							@RequestParam(required = false, value="totalAmountArr") String totalAmountArr){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			payApply.setCreator(user.getUserId());
			payApply.setAddTime(DateUtil.sysTimeMillis());
			payApply.setUpdater(user.getUserId());
			payApply.setModTime(DateUtil.sysTimeMillis());
			payApply.setCompanyId(user.getCompanyId());
		}
		payApply.setPayType(517);//采购
		List<Double> priceList = JSON.parseArray(request.getParameter("priceArr").toString(),Double.class);
		List<Double> numList = JSON.parseArray(request.getParameter("numArr").toString(),Double.class);
		List<Double> totalAmountList = JSON.parseArray(request.getParameter("totalAmountArr").toString(),Double.class);
		
		List<Integer> buyorderGoodsIdList = JSON.parseArray(request.getParameter("buyorderGoodsIdArr").toString(),Integer.class);
		
		List<PayApplyDetail> detailList = new ArrayList<>();
		for(int i=0;i<buyorderGoodsIdList.size();i++){
			PayApplyDetail payApplyDetail = new PayApplyDetail();
			payApplyDetail.setDetailgoodsId(buyorderGoodsIdList.get(i));
			payApplyDetail.setPrice(new BigDecimal(priceList.get(i)));
			payApplyDetail.setNum(new BigDecimal(numList.get(i)));
			payApplyDetail.setTotalAmount(new BigDecimal(totalAmountList.get(i)));
			detailList.add(payApplyDetail);
		}
		payApply.setDetailList(detailList);
		
		return buyorderService.saveApplyPayment(payApply);
	}
	
	/**
	 * <b>Description:</b><br>
	 * 编辑沟通记录
	 * 
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午1:31:13
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editcommunicate")
	public ModelAndView editCommunicate(CommunicateRecord communicateRecord, TraderSupplier traderSupplier,
			Buyorder buyorder, HttpServletRequest request, HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("order/buyorder/edit_communicate");
		CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
		communicate.setTraderSupplierId(communicateRecord.getTraderSupplierId());
		communicate.setTraderId(communicateRecord.getTraderId());
		communicate.setBuyorderId(communicateRecord.getBuyorderId());
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(communicateRecord.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.TWO);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

		// 沟通方式
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mv.addObject("communicateList", communicateList);

		// 客户标签
		Tag tag = new Tag();
		tag.setTagType(SysOptionConstant.ID_32);
		tag.setIsRecommend(ErpConst.ONE);
		tag.setCompanyId(user.getCompanyId());

		Integer pageNo = 1;
		Integer pageSize = 10;

		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

		mv.addObject("communicateRecord", communicate);

		mv.addObject("contactList", contactList);

		mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mv.addObject("page", (Page) tagMap.get("page"));
		mv.addObject("method", "communicaterecord");
		mv.addObject("traderSupplier", traderSupplier);
		mv.addObject("buyorder", buyorder);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 新增沟通
	 * 
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:36:53
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/saveaddcommunicate")
	public ResultInfo saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			HttpSession session) throws Exception {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_247);// 采购订单
		communicateRecord.setRelatedId(communicateRecord.getBuyorderId());
		if (null != communicateRecord.getCommunicateRecordId() && communicateRecord.getCommunicateRecordId() > 0) {
			record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
		} else {
			record = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
		}
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getBuyorderId());
		} else {
			return new ResultInfo(1, "操作失败！");
		}

	}
	
	
	/**
	 * <b>Description:</b><br> 付款申请通过初始页面（新增交易记录）
	 * @param request
	 * @param id
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年10月16日 上午11:04:06
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/initPayApplyPass")
	public ModelAndView initPayApplyPass(HttpServletRequest request,Integer id,String taskId){
		//获取付款申请主表信息（根据ID）
		PayApply payApplyInfo = payApplyService.getPayApplyInfo(id);
		// 获取付款交易方式
		List<SysOptionDefinition> payTypeName = getSysOptionDefinitionList(SysOptionConstant.ID_640);
		ModelAndView mav=new ModelAndView("finance/buyorder/pay_apply_pass");
		mav.addObject("payTypeName",payTypeName);
		mav.addObject("id", id);
		mav.addObject("taskId", taskId);
		mav.addObject("payApplyInfo", payApplyInfo);
		
		mav.addObject("bussinessTypeList", getSysOptionDefinitionList(524));
		mav.addObject("traderModeList", getSysOptionDefinitionList(519));
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 付款申请不通过初始页面
	 * @param request
	 * @param id
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月8日 上午9:34:57
	 */
	@ResponseBody
	@RequestMapping(value="/initPayApplyNoPass")
	public ModelAndView initPayApplyNoPass(HttpServletRequest request,Integer id){
		ModelAndView mav=new ModelAndView("finance/buyorder/pay_apply_no_pass");
		mav.addObject("id", id);
		return mav;
	}
	
	/**
	 * 
	 * <b>Description:</b><br> 付款申请不通过
	 * @param request
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月8日 上午10:15:08
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/payApplyNoPass")
	@SystemControllerLog(operationType = "add",desc = "付款申请不通过")
	public ResultInfo payApplyNoPass(HttpServletRequest request, PayApply payApply){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		payApply.setValidStatus(2);
		payApply.setValidTime(DateUtil.sysTimeMillis());
		payApply.setUpdater(user.getUserId());
		payApply.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> result = payApplyService.payApplyNoPass(payApply);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 付款申请通过
	 * @param request
	 * @param payApply
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年9月8日 上午11:04:23
	 */
	@FormToken(remove=true)
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/payApplyPass")
	@SystemControllerLog(operationType = "add",desc = "付款申请通过")
	public ResultInfo payApplyPass(HttpServletRequest request, PayApply payApply,String taskId,Integer paymentType){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);


		if(paymentType != null && paymentType == 641){
		    //如果付款是641南京银行银企直连支付
		    //获取付款申请主表信息（根据ID）
		    PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
		    payApplyInfo.setComments(payApply.getComments());
		    ResultInfo<?> res = bankBillService.addBankPayApply(payApplyInfo);
		    if(res.getCode().equals(-1)){
			return res;
		    }
		}
		payApply.setValidStatus(1);
		payApply.setValidTime(DateUtil.sysTimeMillis());
		payApply.setUpdater(user.getUserId());
		payApply.setModTime(DateUtil.sysTimeMillis());
		payApply.setComments(null);
		ResultInfo<?> result = payApplyService.payApplyPass(payApply);
		try {
		
		//获取付款申请主表信息（根据ID）
		PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
		int i=payApplyService.getPayStatusBill(ErpConst.BUY_ORDER_TYPE,payApply.getPayApplyId());
		if(i<0){
			return new ResultInfo(1, "流水已经生成" );
		}
		//获取付款申请详情列表信息（根据ID）
//		List<PayApplyDetail> payApplyDetailList = payApplyService.getPayApplyDetailList(payApply.getPayApplyId());
		//获取当前用户公司信息
		Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
		
		//根据采购订单ID获取订单号
		Buyorder buyorder = new Buyorder();
		buyorder.setBuyorderId(payApplyInfo.getRelatedId());
		BuyorderVo bv =  buyorderService.getBuyorderVoDetail(buyorder,user);
		 // 归属销售
		User belongUser = new User();
    	    	if(bv.getTraderId() != null ){
    		belongUser = userService.getUserByTraderId(bv.getTraderId(), 2);// 1客户，2供应商
        		if(belongUser != null && belongUser.getUserId() != null){
        		    belongUser = userService.getUserById(belongUser.getUserId());
        		}
    	    	}
		
		//添加资金流水记录
		CapitalBill capitalBill = new CapitalBill();
		if(user!=null){
			capitalBill.setCreator(user.getUserId());
			capitalBill.setAddTime(DateUtil.sysTimeMillis());
			capitalBill.setCompanyId(user.getCompanyId());
		}
		capitalBill.setPaymentType(paymentType);
		capitalBill.setCurrencyUnitId(1);
		capitalBill.setTraderTime(DateUtil.sysTimeMillis());
		capitalBill.setTraderType(payApplyInfo.getTraderMode() == 528 ? 5 : 2);
		capitalBill.setTraderSubject(payApplyInfo.getTraderSubject());
		capitalBill.setTraderMode(payApplyInfo.getTraderMode());
		capitalBill.setAmount(payApplyInfo.getAmount());
		capitalBill.setPayee(payApplyInfo.getTraderName());
		capitalBill.setPayeeBankAccount(payApplyInfo.getBankAccount());
		capitalBill.setPayer(companyInfo.getCompanyName());
		//capitalBill.setPayerBankAccount();
		capitalBill.setComments(request.getParameter("comments"));//资金流水交易备注
		
		
		List<CapitalBillDetail> capitalBillDetails = new ArrayList<>(); 
		CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
		capitalBillDetail2.setAmount(payApplyInfo.getAmount());
		capitalBillDetail2.setBussinessType(525);//订单付款
		capitalBillDetail2.setOrderType(2);//采购订单
		capitalBillDetail2.setRelatedId(payApplyInfo.getRelatedId());
		capitalBillDetail2.setOrderNo(bv.getBuyorderNo());
		capitalBillDetail2.setTraderId(bv.getTraderId());
		capitalBillDetail2.setTraderType(2);
		capitalBillDetail2.setUserId(bv.getUserId());
		 if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
		     capitalBillDetail2.setOrgName(belongUser.getOrgName());
		     capitalBillDetail2.setOrgId(belongUser.getOrgId());
		 }

		capitalBillDetails.add(capitalBillDetail2);
		capitalBill.setCapitalBillDetails(capitalBillDetails);
		
		CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		capitalBillDetail.setAmount(payApplyInfo.getAmount());
		capitalBillDetail.setBussinessType(525);//订单付款
		capitalBillDetail.setOrderType(2);//采购订单
		capitalBillDetail.setRelatedId(payApplyInfo.getRelatedId());
		capitalBillDetail.setOrderNo(bv.getBuyorderNo());
		capitalBillDetail.setTraderId(bv.getTraderId());
		capitalBillDetail.setTraderType(2);
		capitalBillDetail.setUserId(bv.getUserId());
		 if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
		     capitalBillDetail.setOrgName(belongUser.getOrgName());
		     capitalBillDetail.setOrgId(belongUser.getOrgId());
		 }
		capitalBill.setCapitalBillDetail(capitalBillDetail);
		
		capitalBillService.saveAddCapitalBill(capitalBill);

		payApplyService.updatePayStatusBill(ErpConst.BUY_ORDER_TYPE,1,payApply.getPayApplyId());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", true);
        		//审批操作
        		try {
        		Integer status = 0;
        		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,request.getParameter("comments"),user.getUsername(),variables);
        		 //如果未结束添加审核对应主表的审核状态
    	    	    	if(!complementStatus.getData().equals("endEvent")){
    	    	    	    verifiesRecordService.saveVerifiesInfo(taskId,status);
    	    	    	}
		    	//采购单付款申请不通过解锁
		    	actionProcdefService.updateInfo("T_BUYORDER", "BUYORDER_ID", payApplyInfo.getRelatedId(), "LOCKED_STATUS", 0, 2);
        		} catch (Exception e) {
        			logger.error("payApplyPass 1:", e);
        		    return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        		}
    		} catch (Exception e) {
				logger.error("payApplyPass 2:", e);
    		}
		return result;
	}
	
	
	
	/**
	 * 
	 * <b>Description:</b><br>银行流水付款匹配 
	 * @param request
	 * @param payApply
	 * @param taskId
	 * @param bankBillId
	 * @param paymentType
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年6月29日 上午11:47:44
	 */
	@FormToken(remove=true)
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/payApplyPassByBankBill")
	@SystemControllerLog(operationType = "add",desc = "银行流水付款匹配 ")
	public ResultInfo payApplyPassByBankBill(HttpServletRequest request, PayApply payApply,String taskId,Integer bankBillId,String tranFlow,Integer paymentType){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		
		payApply.setValidStatus(1);
		payApply.setValidTime(DateUtil.sysTimeMillis());
		payApply.setUpdater(user.getUserId());
		payApply.setModTime(DateUtil.sysTimeMillis());
		payApply.setComments(null);
		ResultInfo<?> result = payApplyService.payApplyPass(payApply);
		try {
		
		//获取付款申请主表信息（根据ID）
		PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
			int i=	payApplyService.getPayStatusBill(ErpConst.BUY_ORDER_TYPE,payApply.getPayApplyId());
			if(i<0){
				return new ResultInfo(1, "流水已经生成" );
			}
		Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
		
		//根据采购订单ID获取订单号
		Buyorder buyorder = new Buyorder();
		buyorder.setBuyorderId(payApplyInfo.getRelatedId());
		buyorder.setCompanyId(user.getCompanyId());
		BuyorderVo bv =  buyorderService.getBuyOrderPrintInfo(buyorder);
		 // 归属销售
		User belongUser = new User();
    	    	if(bv.getTraderId() != null ){
    		belongUser = userService.getUserByTraderId(bv.getTraderId(), 2);// 1客户，2供应商
        		if(belongUser != null && belongUser.getUserId() != null){
        		    belongUser = userService.getUserById(belongUser.getUserId());
        		}
    	    	}
		
		//添加资金流水记录
		CapitalBill capitalBill = new CapitalBill();
		if(user!=null){
			capitalBill.setCreator(user.getUserId());
			capitalBill.setAddTime(DateUtil.sysTimeMillis());
			capitalBill.setCompanyId(user.getCompanyId());
		}
		capitalBill.setBankBillId(bankBillId);
		capitalBill.setTranFlow(tranFlow);
		capitalBill.setPaymentType(paymentType);
		capitalBill.setCurrencyUnitId(1);
		capitalBill.setTraderTime(DateUtil.sysTimeMillis());
		capitalBill.setTraderType(payApplyInfo.getTraderMode() == 528 ? 5 : 2);
		capitalBill.setTraderSubject(payApplyInfo.getTraderSubject());
		capitalBill.setTraderMode(payApplyInfo.getTraderMode());
		capitalBill.setAmount(payApplyInfo.getAmount());
		capitalBill.setPayee(payApplyInfo.getTraderName());
		capitalBill.setPayeeBankAccount(payApplyInfo.getBankAccount());
		capitalBill.setPayer(companyInfo.getCompanyName());
		//capitalBill.setPayerBankAccount();
		capitalBill.setComments(request.getParameter("comments"));//资金流水交易备注
		
		
		List<CapitalBillDetail> capitalBillDetails = new ArrayList<>(); 
		CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
		capitalBillDetail2.setAmount(payApplyInfo.getAmount());
		capitalBillDetail2.setBussinessType(525);//订单付款
		capitalBillDetail2.setOrderType(2);//采购订单
		capitalBillDetail2.setRelatedId(payApplyInfo.getRelatedId());
		capitalBillDetail2.setOrderNo(bv.getBuyorderNo());
		capitalBillDetail2.setTraderId(bv.getTraderId());
		capitalBillDetail2.setTraderType(2);
		capitalBillDetail2.setUserId(bv.getUserId());
		 if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
		     capitalBillDetail2.setOrgName(belongUser.getOrgName());
		     capitalBillDetail2.setOrgId(belongUser.getOrgId());
		 }

		capitalBillDetails.add(capitalBillDetail2);
		capitalBill.setCapitalBillDetails(capitalBillDetails);
		
		CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		capitalBillDetail.setAmount(payApplyInfo.getAmount());
		capitalBillDetail.setBussinessType(525);//订单付款
		capitalBillDetail.setOrderType(2);//采购订单
		capitalBillDetail.setRelatedId(payApplyInfo.getRelatedId());
		capitalBillDetail.setOrderNo(bv.getBuyorderNo());
		capitalBillDetail.setTraderId(bv.getTraderId());
		capitalBillDetail.setTraderType(2);
		capitalBillDetail.setUserId(bv.getUserId());
		 if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
		     capitalBillDetail.setOrgName(belongUser.getOrgName());
		     capitalBillDetail.setOrgId(belongUser.getOrgId());
		 }
		capitalBill.setCapitalBillDetail(capitalBillDetail);
		
		capitalBillService.saveAddCapitalBill(capitalBill);
		payApplyService.updatePayStatusBill(ErpConst.BUY_ORDER_TYPE,1,payApply.getPayApplyId());
		//修改订单结款匹配项目
		BankBill bankBillInfo = new BankBill();
		bankBillInfo.setBankBillId(bankBillId);
		//付货款
		bankBillInfo.setMatchedObject(857);
		bankBillService.editBankBill(bankBillInfo);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", true);
        		//审批操作
        		try {
        		Integer status = 0;
        		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,request.getParameter("comments"),user.getUsername(),variables);
        		 //如果未结束添加审核对应主表的审核状态
    	    	    	if(!complementStatus.getData().equals("endEvent")){
    	    	    	    verifiesRecordService.saveVerifiesInfo(taskId,status);
    	    	    	}
		    	//采购单付款申请不通过解锁
		    	actionProcdefService.updateInfo("T_BUYORDER", "BUYORDER_ID", payApplyInfo.getRelatedId(), "LOCKED_STATUS", 0, 2);
    	    	    	//更新采购单updataTime
    	    	    	capitalBillService.updateBuyOrderDataUpdateTime(payApplyInfo.getRelatedId(),null, OrderDataUpdateConstant.BUY_ORDER_PAY);
        		} catch (Exception e) {
        			logger.error("payApplyPassByBankBill 1:", e);
        		    return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        		}
    		} catch (Exception e) {
				logger.error("payApplyPassByBankBill 2:", e);
    		}
		return result;
	}
	
	/**
	 * 没有formToken
	 * <p>Title: payApplyPassByBankBill</p>  
	 * <p>Description: </p>  
	 * @param request
	 * @param payApply
	 * @param taskId
	 * @param bankBillId
	 * @param tranFlow
	 * @param paymentType
	 * @return  
	 * @author Bill
	 * @date 2019年3月14日
	 */
	/*@FormToken(remove = true)*/
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/payApplyPassByBankBillNoFormToken")
	@SystemControllerLog(operationType = "add",desc = "银行流水付款匹配 ")
	public ResultInfo payApplyPassByBankBillNoFormToken(HttpServletRequest request, PayApply payApply,String taskId,Integer bankBillId,String tranFlow,Integer paymentType){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		
		payApply.setValidStatus(1);
		payApply.setValidTime(DateUtil.sysTimeMillis());
		payApply.setUpdater(user.getUserId());
		payApply.setModTime(DateUtil.sysTimeMillis());
		payApply.setComments(null);
		ResultInfo<?> result = payApplyService.payApplyPass(payApply);
		try {
		
		//获取付款申请主表信息（根据ID）
		PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApply.getPayApplyId());
			int i=payApplyService.getPayStatusBill(ErpConst.BUY_ORDER_TYPE,payApply.getPayApplyId());
			if(i<0){
				return new ResultInfo(1, "流水已经生成" );
			}
		Company companyInfo = companyService.getCompanyByCompangId(user.getCompanyId());
		
		//根据采购订单ID获取订单号
		Buyorder buyorder = new Buyorder();
		buyorder.setBuyorderId(payApplyInfo.getRelatedId());
		buyorder.setCompanyId(user.getCompanyId());
		BuyorderVo bv =  buyorderService.getBuyOrderPrintInfo(buyorder);
		 // 归属销售
		User belongUser = new User();
    	    	if(bv.getTraderId() != null ){
    		belongUser = userService.getUserByTraderId(bv.getTraderId(), 2);// 1客户，2供应商
        		if(belongUser != null && belongUser.getUserId() != null){
        		    belongUser = userService.getUserById(belongUser.getUserId());
        		}
    	    	}
		
		//添加资金流水记录
		CapitalBill capitalBill = new CapitalBill();
		if(user!=null){
			capitalBill.setCreator(user.getUserId());
			capitalBill.setAddTime(DateUtil.sysTimeMillis());
			capitalBill.setCompanyId(user.getCompanyId());
		}
		capitalBill.setBankBillId(bankBillId);
		capitalBill.setTranFlow(tranFlow);
		capitalBill.setPaymentType(paymentType);
		capitalBill.setCurrencyUnitId(1);
		capitalBill.setTraderTime(DateUtil.sysTimeMillis());
		capitalBill.setTraderType(payApplyInfo.getTraderMode() == 528 ? 5 : 2);
		capitalBill.setTraderSubject(payApplyInfo.getTraderSubject());
		capitalBill.setTraderMode(payApplyInfo.getTraderMode());
		capitalBill.setAmount(payApplyInfo.getAmount());
		capitalBill.setPayee(payApplyInfo.getTraderName());
		capitalBill.setPayeeBankAccount(payApplyInfo.getBankAccount());
		capitalBill.setPayer(companyInfo.getCompanyName());
		//capitalBill.setPayerBankAccount();
		capitalBill.setComments(request.getParameter("comments"));//资金流水交易备注
		
		
		List<CapitalBillDetail> capitalBillDetails = new ArrayList<>(); 
		CapitalBillDetail capitalBillDetail2 = new CapitalBillDetail();
		capitalBillDetail2.setAmount(payApplyInfo.getAmount());
		capitalBillDetail2.setBussinessType(525);//订单付款
		capitalBillDetail2.setOrderType(2);//采购订单
		capitalBillDetail2.setRelatedId(payApplyInfo.getRelatedId());
		capitalBillDetail2.setOrderNo(bv.getBuyorderNo());
		capitalBillDetail2.setTraderId(bv.getTraderId());
		capitalBillDetail2.setTraderType(2);
		capitalBillDetail2.setUserId(bv.getUserId());
		 if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
		     capitalBillDetail2.setOrgName(belongUser.getOrgName());
		     capitalBillDetail2.setOrgId(belongUser.getOrgId());
		 }

		capitalBillDetails.add(capitalBillDetail2);
		capitalBill.setCapitalBillDetails(capitalBillDetails);
		
		CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		capitalBillDetail.setAmount(payApplyInfo.getAmount());
		capitalBillDetail.setBussinessType(525);//订单付款
		capitalBillDetail.setOrderType(2);//采购订单
		capitalBillDetail.setRelatedId(payApplyInfo.getRelatedId());
		capitalBillDetail.setOrderNo(bv.getBuyorderNo());
		capitalBillDetail.setTraderId(bv.getTraderId());
		capitalBillDetail.setTraderType(2);
		capitalBillDetail.setUserId(bv.getUserId());
		 if(belongUser != null && belongUser.getOrgName() != null && belongUser.getOrgId() != null){
		     capitalBillDetail.setOrgName(belongUser.getOrgName());
		     capitalBillDetail.setOrgId(belongUser.getOrgId());
		 }
		capitalBill.setCapitalBillDetail(capitalBillDetail);
		
		capitalBillService.saveAddCapitalBill(capitalBill);
		payApplyService.updatePayStatusBill(ErpConst.BUY_ORDER_TYPE,1,payApply.getPayApplyId());
		//修改订单结款匹配项目
		BankBill bankBillInfo = new BankBill();
		bankBillInfo.setBankBillId(bankBillId);
		//付货款
		bankBillInfo.setMatchedObject(857);
		bankBillService.editBankBill(bankBillInfo);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", true);
        		//审批操作
        		try {
        		Integer status = 0;
        		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,request.getParameter("comments"),user.getUsername(),variables);
        		 //如果未结束添加审核对应主表的审核状态
    	    	    	if(!complementStatus.getData().equals("endEvent")){
    	    	    	    verifiesRecordService.saveVerifiesInfo(taskId,status);
    	    	    	}
		    	//采购单付款申请不通过解锁
		    	actionProcdefService.updateInfo("T_BUYORDER", "BUYORDER_ID", payApplyInfo.getRelatedId(), "LOCKED_STATUS", 0, 2);
        		} catch (Exception e) {
					logger.error("payApplyPassByBankBillNoFormToken 1:", e);
        		    return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        		}
    		} catch (Exception e) {
    			logger.error("payApplyPassByBankBillNoFormToken 2:", e);
    		}
		return result;
	}



}
