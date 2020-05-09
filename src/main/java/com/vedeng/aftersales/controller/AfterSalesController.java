package com.vedeng.aftersales.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.collect.Maps;
import com.vedeng.authorization.dao.RoleMapper;
import com.vedeng.authorization.model.*;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import com.vedeng.common.constant.stock.StockOperateTypeConst;
import com.vedeng.aftersales.dao.AfterSalesMapper;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.logistics.service.*;
import com.smallhospital.service.ElSaleOrderService;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.system.service.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesCost;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.AfterSalesInvoice;
import com.vedeng.aftersales.model.AfterSalesRecord;
import com.vedeng.aftersales.model.AfterSalesTrader;
import com.vedeng.aftersales.model.vo.AfterSalesCostVo;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesInstallstionVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.call.service.CallService;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.InvoiceApply;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.vo.PayApplyVo;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.LendOut;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehousePicking;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.TraderFinance;
import com.vedeng.trader.model.TraderSupplier;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.model.vo.TraderVo;
import com.vedeng.trader.service.TraderCustomerService;
import com.vedeng.trader.service.TraderSupplierService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br> 售后订单控制器
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.aftersales.controller
 * <br><b>ClassName:</b> AfterSalesOrderController
 * <br><b>Date:</b> 2017年10月9日 上午11:01:13
 */
@Controller
@RequestMapping("/aftersales/order")
public class AfterSalesController extends BaseController {
	@Resource
	private AfterSalesService afterSalesOrderService;
	@Resource
	private UserService userService;
	@Resource
	private TraderCustomerService traderCustomerService;
	@Resource
	private TagService tagService;
	
	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired
	@Qualifier("callService")
	private CallService callService;
	
	@Autowired // 自动装载
	@Qualifier("historyService")
	private HistoryService historyService;
	
	@Autowired
    @Qualifier("warehouseGoodsOperateLogService")
    private WarehouseGoodsOperateLogService warehouseGoodsOperateLogService;
	
	@Autowired
    @Qualifier("expressService")
    private ExpressService expressService;
	
	@Autowired
	@Qualifier("warehousePickService")
	private WarehousePickService warehousePickService;
	
	@Autowired
	@Qualifier("warehouseOutService")
	private WarehouseOutService warehouseOutService;
	    
	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;
	
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;
	
	@Resource
	private FtpUtilService ftpUtilService;
	
	@Resource
	private SaleorderService saleorderService;
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	
	@Autowired
	@Qualifier("payApplyService")
	private PayApplyService payApplyService;
	
	@Resource
	private TraderSupplierService traderSupplierService;
	
	@Autowired
	@Qualifier("invoiceService")
	private InvoiceService invoiceService;//自动注入invoiceService

	@Autowired
	@Qualifier("warehouseInService")
	private WarehouseInService warehouseInService;

	@Autowired
	private WarehouseStockService warehouseStockService;

	@Autowired
	private VgoodsService vGoodsService;


    @Autowired
    private BaseService baseService;

    @Autowired
    @Qualifier("orgService")
    protected OrgService orgService;

    @Autowired
	private PositService positService;


	/**
	 * <b>Description:</b><br>查询售后分页信息
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月9日 上午11:14:24
	 */
	@RequestMapping("/getAfterSalesPage")
	@ResponseBody
	public ModelAndView getAfterSalesPage(HttpServletRequest request, AfterSalesVo afterSalesVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize){
		User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav =new ModelAndView("/aftersales/order/index");
		Page page = getPageTag(request, pageNo, pageSize);
		// 售后人员
		List<User> serviceUserList = userService.getUserListByPositType(SysOptionConstant.ID_312,sessUser.getCompanyId());
		mav.addObject("serviceUserList", serviceUserList);

		//归属销售人员
        mav.addObject("loginUser", sessUser);
        Integer position=0;
        if (null!=sessUser.getPositType()){
			position=sessUser.getPositType();
		}
		mav.addObject("position",position);
/*		List<Position> positionList=positService.getPositionByUserId(sessUser.getUserId());
		if(positionList!=null&&positionList.size()>0){
			position=positionList.get(0).getType();
		}*/
		List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, sessUser.getCompanyId());
        mav.addObject("orgList", orgList);
		 if(position.equals(310)) {
			 // 获取当前销售用户下级职位用户
			 List<Integer> positionType = new ArrayList<>();
			 positionType.add(SysOptionConstant.ID_310);
			 List<User> userList = userService.getMyUserList(sessUser, positionType, false);
			 if(afterSalesVo.getOptUserId()==null) {
				 List<Integer> userIds = new ArrayList<>();
				 if (userList != null && userList.size() > 0) {
					 for (User user : userList) {
						 userIds.add(user.getUserId());
					 }
				 }
				 afterSalesVo.setUserIdList(userIds);
			 }
			 mav.addObject("userList", userList);// 操作人员
		 }else {
			 // 查询所有职位类型为310的员工
			 List<User> userList = userService.getActiveUserByPositType(SysOptionConstant.ID_310, sessUser.getCompanyId());
			 mav.addObject("userList", userList);// 操作人员
			 // 获取销售部
		 }
		//业务类型
		List<SysOptionDefinition> sysList1 = getSysOptionDefinitionList(535);//售后
		//List<SysOptionDefinition> sysList2 = getSysOptionDefinitionList(536);//采购
		List<SysOptionDefinition> sysList3 = getSysOptionDefinitionList(537);//第三方
		List<SysOptionDefinition> sysList = new ArrayList<>();
		sysList.addAll(sysList1);
		sysList.addAll(sysList3);
		mav.addObject("sysList", sysList);

		List<Integer> serviceUserIdList = new ArrayList<>();
		if(afterSalesVo != null && afterSalesVo.getServiceUserId() != null){
			serviceUserIdList.add(afterSalesVo.getServiceUserId());
			afterSalesVo.setServiceUserIdList(serviceUserIdList);
		}else{
			for (User user : serviceUserList) {
				serviceUserIdList.add(user.getUserId());
			}
			afterSalesVo.setServiceUserIdList(serviceUserIdList);
		}
		String search = request.getParameter("search");
		String nowDate = "";
		String pre1MonthDate = "";
		//获取当前日期
		Date date = new Date();
		nowDate = DateUtil.DatePreMonth(date, 0, null);
		//获取往前推一个月
		pre1MonthDate = DateUtil.getDayOfMonth(-1);
		if(search == null || "".equals(search)){
			mav.addObject("nowDate", nowDate);
			mav.addObject("pre1MonthDate", pre1MonthDate);
		}
		if(null != request.getParameter("starttime")){
			afterSalesVo.setSearchStartTime(DateUtil.convertLong(afterSalesVo.getStarttime(), "yyyy-MM-dd"));
		} else {
			afterSalesVo.setTimeType(1);
			afterSalesVo.setSearchStartTime(DateUtil.convertLong(pre1MonthDate, "yyyy-MM-dd"));
		}
		if(null != request.getParameter("endtime") && request.getParameter("endtime") != ""){
			afterSalesVo.setSearchEndTime(DateUtil.convertLong(afterSalesVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		} else {
			afterSalesVo.setSearchEndTime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		afterSalesVo.setCompanyId(sessUser.getCompanyId());

		Map<String, Object> map = afterSalesOrderService.getAfterSalesPage(afterSalesVo, page,serviceUserList);
		if(map!=null){
			mav.addObject("list", map.get("list"));
			mav.addObject("page", map.get("page"));
		}
		if(afterSalesVo.getOptUserId()==null){
		    afterSalesVo.setOptUserId(-1);
        }
		if(afterSalesVo.getOrgId()==null){
		    afterSalesVo.setOrgId(-1);
        }
		mav.addObject("afterSalesVo", afterSalesVo);
		return mav;
	}
	
	/**
	* @Title: searchAfterOrder
	* @Description: TODO 搜索售后单
	* @return ModelAndView    返回类型
	* @author strange
	* @throws
	* @date 2019年8月22日
	*/
	@ResponseBody
	@RequestMapping("/searchAfterOrder")
	public ModelAndView searchAfterOrder(HttpServletRequest request, AfterSalesVo afterSalesVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize,Integer lendOut) {
		User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv =new ModelAndView("/aftersales/order/search_afterorder_list");
		Page page = getPageTag(request, pageNo, pageSize);
		afterSalesVo.setCompanyId(sessUser.getCompanyId());
		if(StringUtils.isNotBlank(afterSalesVo.getAfterSalesNo())) {
			//外借出库页筛选销售单换货
			afterSalesVo.setType(540);
			afterSalesVo.setGoodsType(0);
			afterSalesVo.setAtferSalesStatus(1);
			Map<String, Object> map = afterSalesOrderService.searchAfterOrder(afterSalesVo, page);
			if(map!=null){
				mv.addObject("list", map.get("list"));
				mv.addObject("page", map.get("page"));
			}
		}
		mv.addObject("afterSalesVo", afterSalesVo);
		mv.addObject("lendOut", lendOut);
		return mv;
	}
	 /**
		 * <b>Description:</b><br> 采购售后列表
		 * @param request
		 * @param afterSalesVo
		 * @param pageNo
		 * @param pageSize
		 * @return
		 * @Note
		 * <b>Author:</b> Jerry
		 * <br><b>Date:</b> 2017年10月9日 下午5:26:11
		 */
		@RequestMapping("/buyorderaftersaleslist")
		@ResponseBody
		public ModelAndView buyorderAfterSalesList(HttpServletRequest request, AfterSalesVo afterSalesVo,
				@RequestParam(required = false, defaultValue = "1") Integer pageNo,
				@RequestParam(required = false) Integer pageSize){
			ModelAndView mav =new ModelAndView("/aftersales/order/list_buyorderaftersales");
			Page page = getPageTag(request, pageNo, pageSize);
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			afterSalesVo.setCompanyId(sessUser.getCompanyId());
			//业务类型
			List<SysOptionDefinition> sysList = getSysOptionDefinitionList(536);

			// 时间处理
			if (null != afterSalesVo.getStarttime() && afterSalesVo.getStarttime() != "") {
				afterSalesVo.setSearchStartTime(DateUtil.convertLong(afterSalesVo.getStarttime(), "yyyy-MM-dd"));
			}
			if (null != afterSalesVo.getEndtime() && afterSalesVo.getEndtime() != "") {
				afterSalesVo.setSearchEndTime(DateUtil.convertLong(afterSalesVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}

			Map<String, Object> map = afterSalesOrderService.getbuyorderAfterSalesList(afterSalesVo, page);
			if(map!=null){
				mav.addObject("list", map.get("list"));
				mav.addObject("page", map.get("page"));
			}
			mav.addObject("sysList", sysList);
			mav.addObject("afterSalesVo", afterSalesVo);

			return mav;
		}
	/**
	 * <b>Description:</b><br> 查看售后订单详情
	 * @param request
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月25日 下午4:24:34
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping("/viewAfterSalesDetail")
	public ModelAndView viewAfterSalesDetail(HttpServletRequest request, AfterSalesVo afterSales){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSales.setCompanyId(user.getCompanyId());
		ModelAndView mav = new ModelAndView();
		AfterSalesVo afterSalesVo= afterSalesOrderService.getAfterSalesVoDetail(afterSales);
        if(afterSalesVo.getRealRefundAmount().compareTo(BigDecimal.ZERO)>0){
            mav.addObject("flag",1);
        }else{
            mav.addObject("flag",2);
        }

		//售后单生效后，销售人员不显示"关闭订单"按钮；设置afterSalesVo.setCloseStatus(2)来实现隐藏"关闭订单"按钮
		if(afterSalesVo.getValidStatus() > 0 && null!=user.getOrgId()){
			if(SysOptionConstant.ID_535.equals(afterSalesVo.getSubjectType())&& 10!=user.getOrgId()){//销售
				afterSalesVo.setCloseStatus(2);
			}
		}
		//查询关闭原因
		afterSalesVo.setAfterSalesStatusResonName((getSysOptionDefinition(afterSalesVo.getAfterSalesStatusReson()).getComments()));
		PayApply payApply=new PayApply();
		payApply.setRelatedId(afterSalesVo.getAfterSalesId());
		payApply.setPayType(ErpConst.AFTERSALES_ORDER_TYPE);//售后类型
        PayApply payApply1=payApplyService.getPayApplyMaxRecord(payApply);
		//mav.addObject("curr_user", user);
		mav.addObject("afterSalesVo", afterSalesVo);
        mav.addObject("payApply", payApply1);

		String sku = "";
		if(afterSalesVo.getAfterSalesGoodsList().size() > 0){
			for (int i = 0; i < afterSalesVo.getAfterSalesGoodsList().size(); i++) {
				if(i == afterSalesVo.getAfterSalesGoodsList().size() - 1){
					sku += afterSalesVo.getAfterSalesGoodsList().get(i).getSku();
				}else{
					sku += afterSalesVo.getAfterSalesGoodsList().get(i).getSku() + ",";
				}
			}
		}
		if(null != sku && !("".equals(sku))){
			mav.addObject("sku", sku);
		}

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(afterSalesVo.getAfterSalesGoodsList())){
			List<Integer> skuIds = new ArrayList<>();
			afterSalesVo.getAfterSalesGoodsList().stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		
//		查询订单的费用类型以及费用列表
		AfterSalesCost afterSalesCost = new AfterSalesCost();
		afterSalesCost.setAfterSalesId(afterSalesVo.getAfterSalesId());
		List<AfterSalesCostVo> costList = afterSalesOrderService.getAfterSalesCostListById(afterSalesCost);
		for(AfterSalesCostVo cost:costList){
			cost.setCostTypeName(getSysOptionDefinition(cost.getType()).getComments());
			cost.setLastModTime(DateUtil.convertString(cost.getModTime(),""));
		}
		mav.addObject("costList",costList);

		if(afterSalesVo.getType() != 544 && afterSalesVo.getType() != 545 && afterSalesVo.getType() != 552 && afterSalesVo.getType() != 553){
			CommunicateRecord communicateRecord = new CommunicateRecord();
			communicateRecord.setAfterSalesId(afterSalesVo.getAfterSalesId());
			List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
			//遍历结果集循环获取客户名称
			for(CommunicateRecord cr:crList){
				AfterSalesTrader afterSalesTrader = new AfterSalesTrader(); 
				afterSalesTrader.setAfterSalesTraderId(cr.getAfterSalesTraderId());
				AfterSalesTrader ast = afterSalesOrderService.getAfterSalesTrader(afterSalesTrader);
				if (ast != null) {
					cr.setAfterSalesTraderName(ast.getTraderName());
				}
			}
			mav.addObject("communicateList", crList);
		}
		if(afterSalesVo.getSubjectType() == 535){
			if(afterSalesVo.getType()==539 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_th");
			}else if(afterSalesVo.getType()==539 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_th_shz");
			}else if(afterSalesVo.getType()==539 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/saleorder/view_afterSales_th_shwc");
			}else if(afterSalesVo.getType()==540 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_hh");
			}else if(afterSalesVo.getType()==540 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_hh_shz");
			}else if(afterSalesVo.getType()==540 && afterSalesVo.getStatus() == 2){
//				//关联外借单
				List<LendOut> lendoutList = warehouseOutService.getLendOutInfoList(afterSalesVo);
				if(CollectionUtils.isNotEmpty(lendoutList)) {
					mav.addObject("lendoutFlag", 1);//存在进行中的外接单不允许关闭
				}
				mav.setViewName("order/saleorder/view_afterSales_hh_shwc");
			}else if(afterSalesVo.getType()==541 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_at");
			}else if(afterSalesVo.getType()==541 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_at_shz");
			}else if(afterSalesVo.getType()==541 && afterSalesVo.getStatus() == 2){
				if(afterSalesVo.getAfterPayApplyList() != null && afterSalesVo.getAfterPayApplyList().size() > 0 
						&& afterSalesVo.getAfterPayApplyList().get(0).getValidStatus() == 0){
					mav.addObject("payStatus", 0);//最新付款申请为待审核状态
				}else{
					mav.addObject("payStatus", 1);
				}
				mav.setViewName("order/saleorder/view_afterSales_at_shwc");
			}else if(afterSalesVo.getType()==584 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_wx");
			}else if(afterSalesVo.getType()==584 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_wx_shz");
			}else if(afterSalesVo.getType()==584 && afterSalesVo.getStatus() == 2){
				if(afterSalesVo.getAfterPayApplyList() != null && afterSalesVo.getAfterPayApplyList().size() > 0 
						&& afterSalesVo.getAfterPayApplyList().get(0).getValidStatus() == 0){
					mav.addObject("payStatus", 0);//最新付款申请为待审核状态
				}else{
					mav.addObject("payStatus", 1);
				}
				mav.setViewName("order/saleorder/view_afterSales_wx_shwc");
			}else if(afterSalesVo.getType()==542 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_tp");
			}else if(afterSalesVo.getType()==542 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_tp_shz");
			}else if(afterSalesVo.getType()==542 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/saleorder/view_afterSales_tp_shwc");
			}else if(afterSalesVo.getType()==543 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_tk");
			}else if(afterSalesVo.getType()==543 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_tk_shz");
			}else if(afterSalesVo.getType()==543 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/saleorder/view_afterSales_tk_shwc");
			}else if(afterSalesVo.getType()==544 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_jz");
			}else if(afterSalesVo.getType()==544 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_jz_shz");
			}else if(afterSalesVo.getType()==544 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/saleorder/view_afterSales_jz_shwc");
			}else if(afterSalesVo.getType()==545 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/saleorder/view_afterSales_qt");
			}else if(afterSalesVo.getType()==545 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/saleorder/view_afterSales_qt_shz");
			}else if(afterSalesVo.getType()==545 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/saleorder/view_afterSales_qt_shwc");
			} 
		}else if(afterSalesVo.getSubjectType() == 536){
			if(afterSalesVo.getType()==546 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/buyorder/view_afterSales_th");
			}else if(afterSalesVo.getType()==546 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/buyorder/view_afterSales_th_shz");
			}else if(afterSalesVo.getType()==546 && afterSalesVo.getStatus() == 2){
				if(afterSalesVo.getAfterPayApplyList() != null && afterSalesVo.getAfterPayApplyList().size() > 0 
						&& afterSalesVo.getAfterPayApplyList().get(0).getValidStatus() == 0){
					mav.addObject("payStatus", 0);//最新付款申请为待审核状态
				}else{
					mav.addObject("payStatus", 1);
				}
				mav.setViewName("order/buyorder/view_afterSales_th_shwc");
			}else if(afterSalesVo.getType()==547 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/buyorder/view_afterSales_hh");
			}else if(afterSalesVo.getType()==547 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/buyorder/view_afterSales_hh_shz");
			}else if(afterSalesVo.getType()==547 && afterSalesVo.getStatus() == 2){
				if(afterSalesVo.getAfterPayApplyList() != null && afterSalesVo.getAfterPayApplyList().size() > 0 
						&& afterSalesVo.getAfterPayApplyList().get(0).getValidStatus() == 0){
					mav.addObject("payStatus", 0);//最新付款申请为待审核状态
				}else{
					mav.addObject("payStatus", 1);
				}
				mav.setViewName("order/buyorder/view_afterSales_hh_shwc");
			}else if(afterSalesVo.getType()==548 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/buyorder/view_afterSales_tp");
			}else if(afterSalesVo.getType()==548 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/buyorder/view_afterSales_tp_shz");
			}else if(afterSalesVo.getType()==548 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/buyorder/view_afterSales_tp_shwc");
			}else if(afterSalesVo.getType()==549 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("order/buyorder/view_afterSales_tk");
			}else if(afterSalesVo.getType()==549 && afterSalesVo.getStatus() == 1){
				mav.setViewName("order/buyorder/view_afterSales_tk_shz");
			}else if(afterSalesVo.getType()==549 && afterSalesVo.getStatus() == 2){
				mav.setViewName("order/buyorder/view_afterSales_tk_shwc");
			}
		}else if(afterSalesVo.getSubjectType() == 537){//第三方
			if(afterSalesVo.getType()==550 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("aftersales/order/view_afterSales_at");
			}else if(afterSalesVo.getType()==550 && afterSalesVo.getStatus() == 1){
				mav.setViewName("aftersales/order/view_afterSales_at_shz");
			}else if(afterSalesVo.getType()==550 && afterSalesVo.getStatus() == 2){
				if(afterSalesVo.getAfterPayApplyList() != null && afterSalesVo.getAfterPayApplyList().size() > 0 
						&& afterSalesVo.getAfterPayApplyList().get(0).getValidStatus() == 0){
					mav.addObject("payStatus", 0);//最新付款申请为待审核状态
				}else{
					mav.addObject("payStatus", 1);
				}
				mav.setViewName("aftersales/order/view_afterSales_at_shwc");
			}else if(afterSalesVo.getType()==585 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("aftersales/order/view_afterSales_wx");
			}else if(afterSalesVo.getType()==585 && afterSalesVo.getStatus() == 1){
				mav.setViewName("aftersales/order/view_afterSales_wx_shz");
			}else if(afterSalesVo.getType()==585 && afterSalesVo.getStatus() == 2){
				if(afterSalesVo.getAfterPayApplyList() != null && afterSalesVo.getAfterPayApplyList().size() > 0 
						&& afterSalesVo.getAfterPayApplyList().get(0).getValidStatus() == 0){
					mav.addObject("payStatus", 0);//最新付款申请为待审核状态
				}else{
					mav.addObject("payStatus", 1);
				}
				mav.setViewName("aftersales/order/view_afterSales_wx_shwc");
			}else if(afterSalesVo.getType()==551 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("aftersales/order/view_afterSales_tk");
			}else if(afterSalesVo.getType()==551 && afterSalesVo.getStatus() == 1){
				mav.setViewName("aftersales/order/view_afterSales_tk_shz");
			}else if(afterSalesVo.getType()==551 && afterSalesVo.getStatus() == 2){
				mav.setViewName("aftersales/order/view_afterSales_tk_shwc");
			}else if(afterSalesVo.getType()==552 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("aftersales/order/view_afterSales_jz");
			}else if(afterSalesVo.getType()==552 && afterSalesVo.getStatus() == 1){
				mav.setViewName("aftersales/order/view_afterSales_jz_shz");
			}else if(afterSalesVo.getType()==552 && afterSalesVo.getStatus() == 2){
				mav.setViewName("aftersales/order/view_afterSales_jz_shwc");
			}else if(afterSalesVo.getType()==553 && (afterSalesVo.getStatus() == 0||afterSalesVo.getStatus() == 3)){
				mav.setViewName("aftersales/order/view_afterSales_qt");
			}else if(afterSalesVo.getType()==553 && afterSalesVo.getStatus() == 1){
				mav.setViewName("aftersales/order/view_afterSales_qt_shz");
			}else if(afterSalesVo.getType()==553 && afterSalesVo.getStatus() == 2){
				mav.setViewName("aftersales/order/view_afterSales_qt_shwc");
			}
		}
		// 判断是否有正在审核中的付款申请
		Integer isPayApplySh = 0;
		Integer payApplyId = 0;
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
		}
		mav.addObject("isPayApplySh", isPayApplySh);

	    	Map<String, Object> historicInfoPay=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ payApplyId);
	    	Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
        historicInfoPay.get("historicActivityInstance");
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
	    	
		
		Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "afterSalesVerify_"+ afterSalesVo.getAfterSalesId());
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		mav.addObject("taskInfo", historicInfo.get("taskInfo"));
		mav.addObject("startUser", historicInfo.get("startUser"));
		mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mav.addObject("endStatus",historicInfo.get("endStatus"));
		mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mav.addObject("commentMap", historicInfo.get("commentMap"));
		String verifyUsers = null;
	    	if(null!=taskInfo){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfo);
	    	    verifyUsers = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mav.addObject("verifyUsers", verifyUsers);
	    	
	    	
	    	//售后单完结审核信息
	    	Map<String, Object> historicInfoOver=actionProcdefService.getHistoric(processEngine, "overAfterSalesVerify_"+ afterSales.getAfterSalesId());
	    	Task taskInfoOver = (Task) historicInfoOver.get("taskInfo");
	    	mav.addObject("taskInfoOver", taskInfoOver);
	    	mav.addObject("startUserOver", historicInfoOver.get("startUser"));
		// 最后审核状态
	    	mav.addObject("endStatusOver",historicInfoOver.get("endStatus"));
	    	mav.addObject("historicActivityInstanceOver", historicInfoOver.get("historicActivityInstance"));
	    	mav.addObject("commentMapOver", historicInfoOver.get("commentMap"));
	    	mav.addObject("candidateUserMapOver", historicInfoOver.get("candidateUserMap"));
	    	String verifyUsersOver = null;
	    	if(null!=taskInfoOver){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoOver);
	    	    verifyUsersOver = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mav.addObject("verifyUsersOver", verifyUsersOver);
	    	
	    	
	    	//开票申请审核信息
	    	InvoiceApply invoiceApplyInfo= invoiceService.getInvoiceApplyByRelatedId(afterSalesVo.getAfterSalesId(),SysOptionConstant.ID_504,afterSalesVo.getCompanyId());
	    	mav.addObject("invoiceApply", invoiceApplyInfo);
	    	if(invoiceApplyInfo != null) {
	    		Map<String, Object> historicInfoInvoice=actionProcdefService.getHistoric(processEngine, "invoiceVerify_"+ invoiceApplyInfo.getInvoiceApplyId());
	    		mav.addObject("taskInfoInvoice", historicInfoInvoice.get("taskInfo"));
	    		mav.addObject("startUserInvoice", historicInfoInvoice.get("startUser"));
	    		mav.addObject("candidateUserMapInvoice", historicInfoInvoice.get("candidateUserMap"));
	    		// 最后审核状态
	    		mav.addObject("endStatusInvoice",historicInfoInvoice.get("endStatus"));
	    		mav.addObject("historicActivityInstanceInvoice", historicInfoInvoice.get("historicActivityInstance"));
	    		mav.addObject("commentMapInvoice", historicInfoInvoice.get("commentMap"));
	    		
	    		Task taskInfoInvoice = (Task) historicInfoInvoice.get("taskInfo");
	    		//当前审核人
	    		String verifyUsersInvoice = null;
	    		if(null!=taskInfoInvoice){
	    			Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfoInvoice);
	    			verifyUsersInvoice = (String) taskInfoVariables.get("verifyUsers");
	    		}
	    		mav.addObject("verifyUsersInvoice", verifyUsersInvoice);	
	    	}

		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 售后订单新增沟通记录
	 *
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午10:17:31
	 */
	@SuppressWarnings("unchecked")
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "addCommunicatePage")
	public ModelAndView addCommunicatePage(AfterSales afterSales, TraderVo trader,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("aftersales/order/add_communicate");
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(trader.getTraderId());
		traderContact.setIsEnable(1);
		traderContact.setTraderType(trader.getTraderType());
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

		mav.addObject("trader", trader);
		mav.addObject("afterSales", afterSales);
		mav.addObject("contactList", contactList);

		CommunicateRecord communicate = new CommunicateRecord();
		communicate.setBegintime(DateUtil.sysTimeMillis());
		communicate.setEndtime(DateUtil.sysTimeMillis()+2*60*1000);
		mav.addObject("communicateRecord", communicate);

		// 沟通方式
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);

		/*** 2018-08-08 新增沟通记录页面增加录音Id ***/
		//获取售后对象列表
		List<AfterSalesTrader> afterSalesTraders = afterSalesOrderService.getAfterSalesTraderList(Integer.valueOf(request.getParameter("afterSalesId")));
		mav.addObject("afterSalesTraders",afterSalesTraders);
		//获取录音Id
		mav.addObject("communicateRecordId",request.getParameter("communicateRecordId"));
		/*** END ***/

		mav.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mav.addObject("page", (Page) tagMap.get("page"));
		mav.addObject("traderType",trader.getTraderType());
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑沟通记录
	 *
	 * @param communicateRecord
	 * @param request
	 *
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午1:31:13
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editcommunicate")
	public ModelAndView editCommunicate(CommunicateRecord communicateRecord, TraderVo trader,
			HttpServletRequest request, HttpSession session) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("aftersales/order/edit_communicate");
		CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
		//communicate.setTraderCustomerId(communicateRecord.getTraderCustomerId());
		communicate.setTraderId(communicateRecord.getTraderId());
		communicate.setAfterSalesId(communicateRecord.getAfterSalesId());
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(communicateRecord.getTraderId());
		traderContact.setIsEnable(1);
		traderContact.setTraderType(trader.getTraderType());
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

		/*** 2018-08-08 新增沟通记录页面增加录音Id ***/
		//获取售后对象列表
		List<AfterSalesTrader> afterSalesTraders = afterSalesOrderService.getAfterSalesTraderList(Integer.valueOf(request.getParameter("afterSalesId")));
		mv.addObject("afterSalesTraders",afterSalesTraders);
		//获取录音Id
		mv.addObject("relateCommunicateRecordId",request.getParameter("relateCommunicateRecordId"));
		/*** END ***/

		mv.addObject("flag",request.getParameter("flag"));//获取订单审核状态
		mv.addObject("orderFlag",request.getParameter("orderFlag"));//获取订单状态
		mv.addObject("communicateRecord", communicate);

		mv.addObject("contactList", contactList);

		mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mv.addObject("page", (Page) tagMap.get("page"));
		mv.addObject("method", "communicaterecord");
		mv.addObject("trader", trader);
		mv.addObject("traderContactId",communicateRecord.getTraderContactId());
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(communicate)));
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增沟通
	 *
	 * @param communicateRecord
	 * @param request
	 *
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:36:53
	 */
	@SuppressWarnings("all")
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveaddcommunicate")
	@SystemControllerLog(operationType = "add",desc = "保存新增售后沟通记录")
	public ResultInfo saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			HttpSession session) throws Exception {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_248);// 售后订单
		communicateRecord.setRelatedId(communicateRecord.getAfterSalesId());
		record = traderSupplierService.saveAddCommunicate(communicateRecord, request, session);
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getAfterSalesId());
		} else {
			return new ResultInfo(1, "操作失败！");
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑沟通记录
	 *
	 * @param communicateRecord
	 * @param request
	 *
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:36:53
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/saveeditcommunicate")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑沟通记录")
	public ResultInfo saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,String beforeParams,
			HttpSession session) throws Exception {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_248);// 售后订单
		communicateRecord.setRelatedId(communicateRecord.getAfterSalesId());
		record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getAfterSalesId());
		} else {
			return new ResultInfo(1, "操作失败！");
		}

	}
	/**
	 *
	 * <b>Description:</b><br> 销售退换货入库
	 * @param request
	 * @param afterSalesVo
	 * @param pageNo
	 * @param pageSize
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月17日 下午2:52:59
	 */
	@ResponseBody
	@RequestMapping(value="/getStorageAftersales")
	public ModelAndView getStorageAftersales(HttpServletRequest request,AfterSalesVo afterSalesVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize,HttpSession session,
			@RequestParam(required = false, value="_startTime") String _startTime,
			@RequestParam(required = false, value="_endTime") String _endTime,
			@RequestParam(required = false, value="searchDateType") String searchDateType) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();

		/*if(StringUtils.isBlank(searchDateType)){//新打开
			_startTime = DateUtil.getFirstDayOfMonth(0);//当月第一天
			_endTime = DateUtil.getNowDate("yyyy-MM-dd");//当前日期
		}else{
			mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
			mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
		}*/
		mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
		mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
		mv.addObject("searchDateType", searchDateType);
		if(_startTime!=null && !"".equals(_startTime)){
			afterSalesVo.setSearchStartTime(DateUtil.convertLong(_startTime+" 00:00:00",""));
		}
		if(_endTime!=null && !"".equals(_endTime)){
			afterSalesVo.setSearchEndTime(DateUtil.convertLong(_endTime+ " 23:59:59",""));
		}
		mv.addObject("_startTime", _startTime);mv.addObject("_endTime", _endTime);
		afterSalesVo.setCompanyId(session_user.getCompanyId());
		afterSalesVo.setIsIn(1);
		afterSalesVo.setBusinessType(1);
		Map<String, Object> map = afterSalesOrderService.getStorageAftersalesPage(afterSalesVo, page);
		List<AfterSalesVo> list = (List<AfterSalesVo>)map.get("list");
		//售后订单下的产品信息
		String Ids = "";
		for (AfterSalesVo asv : list) {
			/*asv.setBusinessType(1);
			asv.setIsIn(1);
			asv.setIsNormal(1);
			asv.setCompanyId(session_user.getCompanyId());
			List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
			asv.setAfterSalesGoodsList(asvList);*/
			Ids += asv.getAfterSalesId() + "_";
		}
		mv.addObject("Ids", Ids);
		mv.addObject("afterSalesVo",afterSalesVo);
		mv.addObject("afterSalesList",list);

        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
        if(!CollectionUtils.isEmpty(list)){
            List<Integer> skuIds = new ArrayList<>();
            list.stream().forEach(afterSaleItem -> {
                if(!CollectionUtils.isEmpty(afterSaleItem.getAfterSalesGoodsList())){
                    afterSaleItem.getAfterSalesGoodsList().stream().forEach(afterSalesGoodsItem -> {
                        skuIds.add(afterSalesGoodsItem.getGoodsId());
                    });
                }
            });
            List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
            Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
            mv.addObject("newSkuInfosMap", newSkuInfosMap);
        }
        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("aftersales/storageAftersales/index_storageAftersales");
		return mv;
	}
	/**
	 *
	 * <b>Description:</b><br> 销售退换货入库的商品
	 * @param request
	 * @param afterSalesVo
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2018年4月28日 下午3:45:48
	 */
	@ResponseBody
	@RequestMapping(value = "/queryShGoodsInList")
	public ResultInfo<List<AfterSalesGoodsVo>> queryShGoodsInList(HttpServletRequest request, AfterSalesVo afterSalesVo,
			HttpSession session) {
		User curr_user = (User) session.getAttribute(ErpConst.CURR_USER);
		afterSalesVo.setBusinessType(1);
		afterSalesVo.setIsIn(1);
		afterSalesVo.setIsNormal(1);
		afterSalesVo.setCompanyId(curr_user.getCompanyId());
		ResultInfo<List<AfterSalesGoodsVo>> resultInfo = new ResultInfo<List<AfterSalesGoodsVo>>();
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(afterSalesVo,session);
		if (asvList.size() > 0) {

			List<Integer> skuIds = new ArrayList<>();
			asvList.stream().forEach(afterSalesGoodsVo -> {
				skuIds.add(afterSalesGoodsVo.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));

			asvList.stream().forEach(afterSalesGoodsVo -> {
				afterSalesGoodsVo.setGoodsName(newSkuInfosMap.get(afterSalesGoodsVo.getSku()).get("SHOW_NAME").toString());
				afterSalesGoodsVo.setSku(newSkuInfosMap.get(afterSalesGoodsVo.getSku()).get("SKU_NO").toString());
				afterSalesGoodsVo.setBrandName(newSkuInfosMap.get(afterSalesGoodsVo.getSku()).get("BRAND_NAME").toString());
				afterSalesGoodsVo.setModel(newSkuInfosMap.get(afterSalesGoodsVo.getSku()).get("MODEL").toString());
				afterSalesGoodsVo.setMaterialCode(newSkuInfosMap.get(afterSalesGoodsVo.getSku()).get("MATERIAL_CODE").toString());
				afterSalesGoodsVo.setUnitName(newSkuInfosMap.get(afterSalesGoodsVo.getSku()).get("UNIT_NAME").toString());
			});

			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(asvList);
			resultInfo.setParam(afterSalesVo.getAfterSalesId());
		} else {
			resultInfo.setCode(-1);
			resultInfo.setMessage("操作失败");
			resultInfo.setData(null);
		}
		return resultInfo;
	}
	/**
	 *
	 * <b>Description:</b><br> 采购换货入库
	 * @param request
	 * @param afterSalesVo
	 * @param pageNo
	 * @param pageSize
	 *
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年11月15日 上午9:43:19
	 */
	@ResponseBody
	@RequestMapping(value="/getChangeAftersales")
	public ModelAndView getChangeAftersales(HttpServletRequest request,AfterSalesVo afterSalesVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize,HttpSession session,
			@RequestParam(required = false, value="_startTime") String _startTime,
			@RequestParam(required = false, value="_endTime") String _endTime,
			@RequestParam(required = false, value="searchDateType") String searchDateType) {
		User session_user =(User)session.getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		ModelAndView mv = new ModelAndView();

		if(StringUtils.isBlank(searchDateType)){//新打开
			_startTime = DateUtil.getFirstDayOfMonth(0);//当月第一天
			_endTime = DateUtil.getNowDate("yyyy-MM-dd");//当前日期
	}else{
			mv.addObject("de_startTime", DateUtil.getFirstDayOfMonth(0));
			mv.addObject("de_endTime", DateUtil.getNowDate("yyyy-MM-dd"));
		}
		mv.addObject("searchDateType", searchDateType);
		if(_startTime!=null && !"".equals(_startTime)){
			afterSalesVo.setSearchStartTime(DateUtil.convertLong(_startTime+" 00:00:00",""));
		}
		if(_endTime!=null && !"".equals(_endTime)){
			afterSalesVo.setSearchEndTime(DateUtil.convertLong(_endTime+ " 23:59:59",""));
		}
		mv.addObject("_startTime", _startTime);mv.addObject("_endTime", _endTime);
		afterSalesVo.setCompanyId(session_user.getCompanyId());
		afterSalesVo.setIsIn(1);
		afterSalesVo.setBusinessType(2);
		Map<String, Object> map = afterSalesOrderService.getStorageAftersalesPage(afterSalesVo, page);
		List<AfterSalesVo> list = (List<AfterSalesVo>)map.get("list");
		//售后订单下的产品信息
		for (AfterSalesVo asv : list) {
			asv.setBusinessType(2);
			asv.setIsIn(1);
			List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
			asv.setAfterSalesGoodsList(asvList);
		}
		mv.addObject("afterSalesVo",afterSalesVo);
		mv.addObject("afterSalesList",list);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(list)){
			List<Integer> skuIds = new ArrayList<>();
			list.stream().forEach(afterSaleItem -> {
				if(!CollectionUtils.isEmpty(afterSaleItem.getAfterSalesGoodsList())){
					afterSaleItem.getAfterSalesGoodsList().stream().forEach(afterSaleGood -> {
						skuIds.add(afterSaleGood.getGoodsId());
					});
				}
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("aftersales/storageAftersales/index_changAftersales");
		return mv;
	}
	/**
	 *
	 * <b>Description:</b><br> 销售退换货详情
	 *
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月18日 下午1:40:21
	 */
	@ResponseBody
	@RequestMapping(value = "/returnGoodsDetail")
	public ModelAndView detailJump(HttpSession session,AfterSales afterSales) {
		User curr_user = (User)session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		//售后订单下的产品信息
		asv.setCompanyId(curr_user.getCompanyId());
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsIn(1);
		asv.setIsNormal(1);

		Set<Integer> skuIds = new HashSet<>();

		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		asv.setAfterSalesGoodsList(asvList);
		if(CollectionUtils.isNotEmpty(asvList)){
			Iterator<AfterSalesGoodsVo> iterator = asvList.iterator();
			while(iterator.hasNext()){
				AfterSalesGoodsVo next = iterator.next();
				//需入库数量小于0则排除
				if(next.getRknum() != null && next.getRknum() < 0){
					iterator.remove();
				}
			}

			asvList.stream().forEach(afterSaleGoods->{
				skuIds.add(afterSaleGoods.getGoodsId());
			});
		}
		User user = userService.getUserById(asv.getCreator());
		asv.setCreatorName(user == null?"":user.getUsername());
		mv.addObject("afterSalesVo", asv);
		mv.addObject("companyId", curr_user.getCompanyId());
		WarehouseGoodsOperateLog wlg = new WarehouseGoodsOperateLog();
		if(asv.getType()== 540){//销售换货
			wlg.setOperateType(3);
			wlg.setYwType(SysOptionConstant.ID_540);
		}else if(asv.getType()== 539){//销售退货
			wlg.setOperateType(5);
			wlg.setYwType(SysOptionConstant.ID_539);
		}else if(asv.getType()== 547){//采购换货
			wlg.setOperateType(8);
			wlg.setYwType(SysOptionConstant.ID_547);
		}
		wlg.setIsEnable(1);
		wlg.setCompanyId(curr_user.getCompanyId());
		wlg.setAfterSalesId(asv.getAfterSalesId());
		try {
		    // 入库记录
		    List<WarehouseGoodsOperateLog> wlog = warehouseGoodsOperateLogService.getWGOlog(wlg);
		    if (null != wlg) {
				Integer wNum = 0;
				List<String> timeArray = new ArrayList<>();
				for (WarehouseGoodsOperateLog w : wlog) {
				    wNum += w.getNum();
				    timeArray.add(DateUtil.convertString(w.getAddTime(), "yyyy-MM-dd"));
				}
				HashSet<String> tArray = new HashSet<String>(timeArray);
				mv.addObject("wNum", wNum);
				mv.addObject("timeArrayrk", tArray);
		    }
		    mv.addObject("wlog", wlog);

			//入库记录
			wlog.stream().forEach(warehouseGoodsOperateLog->{
				skuIds.add(warehouseGoodsOperateLog.getGoodsId());
			});


		} catch (Exception e) {
		    logger.error("returnGoodsDetail 1:", e);
		}
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_582);

		List<Integer> relatedIds = new ArrayList<Integer>();
		for (AfterSalesGoodsVo afterSalesGoodsVo :asvList) {
		    relatedIds.add(afterSalesGoodsVo.getAfterSalesGoodsId());
		}
		if (relatedIds != null && relatedIds.size() > 0) {
			express.setRelatedIds(relatedIds);
			try {
			    List<Express> expressList = expressService.getExpressList(express);
			    mv.addObject("expressList", expressList);
			} catch (Exception e) {
				logger.error("returnGoodsDetail 2:", e);
			}
		}
		//换货
		if(afterSales.getType()==540 || afterSales.getType()==547){
			//拣货记录
			Saleorder saleorder = new Saleorder();
			saleorder.setBussinessId(asv.getAfterSalesId());
			if(asv.getType()==540){
				saleorder.setBussinessType(4);
			}else if(asv.getType()==547){
				saleorder.setBussinessType(7);
			}
			List<WarehousePicking> warehousePickList = warehousePickService.getPickDetil(saleorder);
			List<String> timeArray = new ArrayList<>();
			String pickFlag = "0";
			if (null != warehousePickList){
				for (WarehousePicking wp : warehousePickList) {
					if(wp.getCnt()==0){
					  timeArray.add(DateUtil.convertString(wp.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
					  pickFlag = "1";
					}
				}
				HashSet<String> tArray = new HashSet<String>(timeArray);
				mv.addObject("timeArray", tArray);
		    }
			Map<Integer,User> userTempMap= Maps.newHashMap();//基本上都是一个人。临时缓存一下。
			for (WarehousePicking warehousePicking : warehousePickList) {
				User u =null;
				if(userTempMap.containsKey(warehousePicking.getCreator())){
					u=userTempMap.get(warehousePicking.getCreator());
				}else{
					u = userService.getUserById(warehousePicking.getCreator());
					if(u!=null){
						userTempMap.put(warehousePicking.getCreator(),u);
					}
				}
				warehousePicking.setOperator(u==null?"":u.getUsername());
			}


			//拣货记录
			warehousePickList.stream().forEach(warehousePicking->{
				skuIds.add(warehousePicking.getGoodsId());
			});

			//出库记录清单
			List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
			List<String> timeArrayWl = new ArrayList<>();
			if (null != warehouseOutList){
				for (WarehouseGoodsOperateLog wl : warehouseOutList) {
					User u =null;
					if(userTempMap.containsKey(wl.getCreator())){
						u=userTempMap.get(wl.getCreator());
					}else{
						u = userService.getUserById(wl.getCreator());
						if(u!=null){
							userTempMap.put(wl.getCreator(),u);
						}
					}
					wl.setOpName(u==null?"":u.getUsername());
					timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
				}
				HashSet<String> wArray = new HashSet<String>(timeArrayWl);
				mv.addObject("timeArrayWl", wArray);


				//出库记录
				warehouseOutList.stream().forEach(warehouseGoodsOperateLog->{
					skuIds.add(warehouseGoodsOperateLog.getGoodsId());
				});
		    }
			mv.addObject("afterSales",afterSales);
			mv.addObject("warehouseOutList",warehouseOutList);
			mv.addObject("warehousePickList",warehousePickList);
			mv.setViewName("aftersales/storageAftersales/view_changeGoodsin");
		}else{
			mv.addObject("afterSales",afterSales);
			mv.setViewName("aftersales/storageAftersales/view_returnGoodsin");
		}

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(skuIds)){
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(new ArrayList<>(skuIds));
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		return mv;
	}
	/**
	 *
	 * <b>Description:</b><br> 新增售后快递
	 *
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月23日 下午1:50:07
	 */
	@FormToken(save=true)
	@ResponseBody
    @RequestMapping(value = "/addExpress")
    public ModelAndView addExpress(HttpSession session, AfterSales afterSales) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
		//售后订单下的产品信息
		asv.setBusinessType(afterSales.getBusinessType());
		asv.setIsIn(1);
		asv.setIsNormal(1);
		List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
		asv.setAfterSalesGoodsList(asvList);
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_582);
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		List<Integer> relatedIds = new ArrayList<Integer>();
		for (AfterSalesGoodsVo asgv : asvList) {
			relatedIds.add(asgv.getAfterSalesGoodsId());
			map.put(asgv.getAfterSalesGoodsId(), 0);
		}
		String nowTime = DateUtil.convertString(DateUtil.sysTimeMillis(), DateUtil.DATE_FORMAT);
		mv.addObject("nowTime", nowTime);
		express.setRelatedIds(relatedIds);
		try {
		    List<Express> expressList = expressService.getExpressList(express);
		    if (null != expressList) {
			for (Express e : expressList) {
			    if (null != e.getExpressDetail()) {
				// 循环计算每件产品发货数量
				for (ExpressDetail ed : e.getExpressDetail()) {
				    Integer num = 0;
				    num = (Integer) map.get(ed.getRelatedId());
				    num = num + ed.getNum();
				    map.put(ed.getRelatedId(), num);
				}
			    }
			}
		    }
		    mv.addObject("expressNumMap", map);
		    mv.addObject("expressList", expressList);
		} catch (Exception e) {
		    logger.error("addExpress:", e);
		}
		// 获取物流公司列表
		List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
		mv.addObject("logisticsList", logisticsList);
		mv.addObject("afterSales", asv);
		if(afterSales.getFlag()==1){
			mv.setViewName("aftersales/storageAftersales/add_returnExpress");
		}else{
			mv.setViewName("aftersales/storageAftersales/add_changeExpress");
		}
		return mv;
    }
	/**
	 *
	 * <b>Description:</b><br> 编辑快递
	 *
	 * @param afterSales
	 * @param express
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月23日 下午3:33:11
	 */
    @ResponseBody
    @RequestMapping(value = "/editExpress")
    public ModelAndView editExpress(HttpSession session,AfterSales afterSales, Express express) {
	ModelAndView mv = new ModelAndView();
	// 获取session中user信息
	List<Integer> relatedIds = new ArrayList<Integer>();
	User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
	AfterSalesVo asv  = afterSalesOrderService.getAfterSalesVoListById(afterSales);
	//售后订单下的产品信息
	asv.setBusinessType(afterSales.getBusinessType());
	asv.setIsIn(1);
	asv.setIsNormal(1);
	asv.setBusinessType(afterSales.getBusinessType());
	List<AfterSalesGoodsVo> asvList = afterSalesOrderService.getAfterSalesGoodsVoList(asv,session);
	asv.setAfterSalesGoodsList(asvList);
	// 物流信息
	Map<Integer, Object> map = new HashMap<Integer, Object>();
	Map<Integer, Object> oldmap = new HashMap<Integer, Object>();
	Express oldExpress = new Express();
	express.setBusinessType(SysOptionConstant.ID_582);
	oldExpress.setBusinessType(SysOptionConstant.ID_582);
	for (AfterSalesGoodsVo asgv : asvList) {
		relatedIds.add(asgv.getAfterSalesGoodsId());
		map.put(asgv.getAfterSalesGoodsId(), 0);
		oldmap.put(asgv.getAfterSalesGoodsId(), 0);
	}
	oldExpress.setRelatedIds(relatedIds);
	try {
	    List<Express> expressList = expressService.getExpressList(express);
	    List<Express> oldExpressList = expressService.getExpressList(oldExpress);
	    if (null != oldExpressList) {
		for (Express e : oldExpressList) {
		    if (null != e.getExpressDetail()) {
			// 循环计算每件产品发货数量
			for (ExpressDetail ed : e.getExpressDetail()) {
			    Integer num = 0;
			    num = (Integer) oldmap.get(ed.getRelatedId());
			    num = num + ed.getNum();
			    oldmap.put(ed.getRelatedId(), num);
			}
		    }
		}
	    }
	    if (null != expressList.get(0)) {
		if (null != expressList.get(0).getExpressDetail()) {
		    // 循环计算每件产品发货数量
		    for (ExpressDetail ed : expressList.get(0).getExpressDetail()) {
			Integer num = 0;
			num = (Integer) map.get(ed.getRelatedId());
			num = num + ed.getNum();
			map.put(ed.getRelatedId(), num);
		    }
		}
	    }
	    mv.addObject("allExpressNumMap", oldmap);
	    mv.addObject("expressNumMap", map);
	    mv.addObject("expressList", expressList.get(0));
	    mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(expressList.get(0))));
	} catch (Exception e) {
	    logger.error("editExpress:", e);
	}
	// 获取物流公司列表
	List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
	mv.addObject("logisticsList", logisticsList);
	mv.addObject("afterSalesVo", asv);
	if(afterSales.getFlag()==1){
		mv.setViewName("aftersales/storageAftersales/edit_returnGoodsExpress");
	}else{
		mv.setViewName("aftersales/storageAftersales/edit_changeGoodsExpress");
	}
	return mv;
    }

    /**
	 * <b>Description:</b><br> 新增售后过程
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:30:44
	 */
    @FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/addAfterSalesRecordPage")
	public ModelAndView addAfterSalesRecordPage(HttpServletRequest request,AfterSalesRecord afterSalesRecord){
		ModelAndView mav = new ModelAndView("aftersales/order/add_afterSales_record");
		mav.addObject("afterSalesRecord", afterSalesRecord);
		return mav;
	}

	/**
	 * <b>Description:</b><br> 保存新增售后过程
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午1:14:09
	 */
    @FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveAddAfterSalesRecord")
	@SystemControllerLog(operationType = "add",desc = "保存新增售后过程")
	public ResultInfo<?> saveAddAfterSalesRecord(HttpServletRequest request,AfterSalesRecord afterSalesRecord){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo res = afterSalesOrderService.saveAfterSalesRecord(afterSalesRecord, user);
		if(res == null){
			return new ResultInfo<>();
		}else{
		    return res;
		}
	}

	/**
	 * <b>Description:</b><br> 编辑售后过程
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:30:44
	 */
	@ResponseBody
	@RequestMapping(value="/editAfterSalesRecordPage")
	public ModelAndView editAfterSalesRecordPage(HttpServletRequest request,AfterSalesRecord afterSalesRecord) throws IOException{
		ModelAndView mav = new ModelAndView("aftersales/order/edit_afterSales_record");
		afterSalesRecord.setContent(null);
		//afterSalesRecord.setContent(URLDecoder.decode(URLDecoder.decode(afterSalesRecord.getContent(), "UTF-8"), "UTF-8")) ;
		afterSalesRecord = afterSalesOrderService.getAfterSalesRecord(afterSalesRecord);
		mav.addObject("afterSalesRecord", afterSalesRecord);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSalesRecord)));
		return mav;
	}

	/**
	 * <b>Description:</b><br> 保存更新售后过程
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午1:14:09
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditAfterSalesRecord")
	@SystemControllerLog(operationType = "edit",desc = "保存更新售后过程")
	public ResultInfo<?> saveEditAfterSalesRecord(HttpServletRequest request,AfterSalesRecord afterSalesRecord){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo res = afterSalesOrderService.saveAfterSalesRecord(afterSalesRecord, user);
		if(res == null){
			return new ResultInfo<>();
		}
		return res;
	}

	/**
	 * <b>Description:</b><br> 编辑售后申请
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:30:44
	 */
	@ResponseBody
	@RequestMapping(value="/editAfterSalesApplyPage")
	public ModelAndView editAfterSalesApplyPage(HttpServletRequest request,AfterSalesVo afterSalesVo) throws IOException{
		ModelAndView mav = new ModelAndView("aftersales/order/edit_afterSales_apply");
		/*afterSalesVo.setPayee(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getPayee(), "UTF-8"), "UTF-8"));
		afterSalesVo.setBank(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getBank(), "UTF-8"), "UTF-8"));
		afterSalesVo.setBankAccount(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getBankAccount(), "UTF-8"), "UTF-8"));
		afterSalesVo.setBankCode(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getBankCode(), "UTF-8"), "UTF-8"));*/
		mav.addObject("afterSales", afterSalesVo);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSalesVo)));

		return mav;
	}


	/**
	 * 跳转售后申请页面
	 * @return
	 * b>Author:</b> addis
	 */
	@ResponseBody
	@RequestMapping(value="/editAfterSalesDetail",produces ={"application/json;charset=UTF-8"})
	public ModelAndView editAfterSalesDetail(HttpServletRequest request,AfterSalesVo afterSalesVo) throws UnsupportedEncodingException {
	ModelAndView modelAndView =new ModelAndView("aftersales/order/edit_aftersale_detail");
        /*afterSalesVo.setPayee(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getPayee(), "UTF-8"), "UTF-8"));
        afterSalesVo.setBank(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getBank(), "UTF-8"), "UTF-8"));
        afterSalesVo.setBankAccount(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getBankAccount(), "UTF-8"), "UTF-8"));
        afterSalesVo.setBankCode(URLDecoder.decode(URLDecoder.decode(afterSalesVo.getBankCode(), "UTF-8"), "UTF-8"));*/
		modelAndView.addObject("afterSales", afterSalesVo);
		modelAndView.addObject("traderModeList", getSysOptionDefinitionList(519));
		return modelAndView;

	}


	/**
	 * <b>Description:</b><br> 保存售后申请
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:30:44
	 */
	@ResponseBody
	@RequestMapping(value="/saveAfterSalesApply")
	@SystemControllerLog(operationType = "edit",desc = "保存售后申请")
	public ResultInfo<?> saveAfterSalesApply(HttpServletRequest request,AfterSalesDetail afterSalesDetail,String beforeParams) throws IOException{
		ResultInfo<?> res = new ResultInfo<>();
		//查找售后订单的退款状态
		AfterSalesDetail afterSalesDetail2 = afterSalesOrderService.getAfterSalesDetailByAfterSaleDetailId(afterSalesDetail.getAfterSalesDetailId());


		// 查找售后订单的售后类型
		// VDERP-2411：只有在销售订单退货和销售订单退款时才做“余额大于退款金额的限定”
		AfterSales afterSales = afterSalesOrderService.getAfterSalesById(afterSalesDetail2.getAfterSalesId());
		if (afterSales != null){
			if (SysOptionConstant.ID_539.equals(afterSales.getType()) || SysOptionConstant.ID_543.equals(afterSales.getType())){
				//查询客户的账户余额
				BigDecimal amount = traderCustomerService.getTraderCustomerId(afterSalesDetail2.getTraderId()).getAmount();
				if( afterSalesDetail2.getRefund() != null && afterSalesDetail2.getRefund().equals(1)
						&& afterSalesDetail2.getRefundAmountStatus() != null && afterSalesDetail2.getRefundAmountStatus().equals(3)){
					BigDecimal 	refundAmount = afterSalesDetail2.getRefundAmount();
					if(refundAmount == null){
						refundAmount = BigDecimal.ZERO;
					}
					if(amount == null){
						amount = BigDecimal.ZERO;
					}
					if(amount.compareTo(refundAmount) < 0){
						res.setMessage("退款金额大于账户余额，无法修改款项退还方式");
						return res;
					}
				}
			}
		}

		 res = afterSalesOrderService.saveEditInstallstion(afterSalesDetail);
		if(res == null){
			return new ResultInfo<>();
		}
		return res;
	}

	/**
	 * <b>Description:</b><br> 编辑退票信息
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 上午11:30:44
	 */
	@ResponseBody
	@RequestMapping(value="/editAfterSalesInvoicePage")
	public ModelAndView editAfterSalesInvoicePage(HttpServletRequest request,AfterSalesInvoice afterSalesInvoice) throws IOException{
		ModelAndView mav = new ModelAndView("order/saleorder/edit_refundTicket");
		mav.addObject("afterSalesInvoice", afterSalesInvoice);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSalesInvoice)));
		return mav;
	}

	/**
	 * <b>Description:</b><br> 保存编辑的退票信息
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午1:14:09
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditRefundTicket")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑的退票信息")
	public ResultInfo<?> saveEditRefundTicket(HttpServletRequest request,AfterSalesInvoice afterSalesInvoice,String beforeParams){
		ResultInfo res = afterSalesOrderService.saveEditRefundTicket(afterSalesInvoice);
		if(res == null){
			return new ResultInfo<>();
		}
		return res;
	}

	/**
	 * <b>Description:</b><br> 新增产品与工程师信息页面
	 * @param request
	 * @param afterSalesInstallstion
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午2:55:14
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/addEngineerPage")
	public ModelAndView addEngineerPage(HttpServletRequest request,AfterSalesVo afterSales){
		ModelAndView mav = new ModelAndView();
		if(afterSales.getSubjectType() != 537){
			List<AfterSalesGoodsVo> list = afterSalesOrderService.getAfterSalesInstallstionVoByParam(afterSales);
			mav.addObject("list", list);
			mav.setViewName("order/saleorder/add_afterSales_engineer");
		}else{
			mav.setViewName("aftersales/order/add_afterSales_engineer");
		}
		AfterSalesInstallstionVo afterSalesInstallstionVo = new AfterSalesInstallstionVo();
		Calendar c = Calendar.getInstance();
		mav.addObject("end", c.getTimeInMillis());
		c.add(Calendar.DAY_OF_MONTH, 1);
		mav.addObject("start", c.getTimeInMillis());
		afterSalesInstallstionVo.setAfterSalesId(afterSales.getAfterSalesId());
		mav.addObject("afterSalesInstallstionVo", afterSalesInstallstionVo);
		return mav;
	}

	/**
	 * <b>Description:</b><br> 编辑产品与工程师信息页面
	 * @param request
	 * @param afterSalesInstallstion
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午2:55:14
	 */
	@ResponseBody
	@RequestMapping(value="/editEngineerPage")
	public ModelAndView editEngineerPage(HttpServletRequest request,AfterSalesInstallstionVo afterSalesInstallstionVo) throws IOException{
		ModelAndView mav = new ModelAndView();
		if(afterSalesInstallstionVo.getSubjectType() != null && afterSalesInstallstionVo.getSubjectType() != 537){
			mav.setViewName("order/saleorder/edit_afterSales_engineer");
		}else{
			mav.setViewName("aftersales/order/edit_afterSales_engineer");
		}
		Integer areaId = afterSalesInstallstionVo.getAreaId();
		afterSalesInstallstionVo = afterSalesOrderService.getAfterSalesInstallstionVo(afterSalesInstallstionVo);
		Calendar c = Calendar.getInstance();
		mav.addObject("end", c.getTimeInMillis());
		c.add(Calendar.DAY_OF_MONTH, 1);
		mav.addObject("start", c.getTimeInMillis());
		afterSalesInstallstionVo.setAreaId(areaId);
		mav.addObject("afterSalesInstallstionVo", afterSalesInstallstionVo);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSalesInstallstionVo)));
		return mav;
	}

	/**
	 * <b>Description:</b><br> 查询工程师的分页信息
	 * @param request
	 * @param engineer
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月27日 下午1:52:24
	 */
	@ResponseBody
	@RequestMapping(value="/getEngineerPage")
	public ModelAndView getEngineerPage(HttpServletRequest request,AfterSalesVo afterSales,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) throws UnsupportedEncodingException{
		ModelAndView mav = new ModelAndView("order/saleorder/engineer_page");
		afterSales.setName(URLDecoder.decode(URLDecoder.decode(afterSales.getSearchName(), "UTF-8"), "UTF-8")) ;
		afterSales.setSearchName(URLDecoder.decode(URLDecoder.decode(afterSales.getSearchName(), "UTF-8"), "UTF-8"));
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,pageSize);
		afterSales.setCompanyId(user.getCompanyId());
		Map<String, Object> map = afterSalesOrderService.getEngineerPage(afterSales, page);
		if(!map.isEmpty()){
			if(map.containsKey("list")){
				List<EngineerVo> list = (List<EngineerVo>) map.get("list");
				mav.addObject("list", list);
			}
			if(map.containsKey("page")){
				page = (Page) map.get("page");
				mav.addObject("page", page);
			}
		}
		mav.addObject("afterSales", afterSales);
		return mav;
	}

	/**
	 * <b>Description:</b><br> 查询客户的分页信息
	 * @param request
	 * @param engineer
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月27日 下午1:52:24
	 */
	@ResponseBody
	@RequestMapping(value="/getCustomerPage")
	public ModelAndView getCustomerPage(HttpServletRequest request,String searchName,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) throws UnsupportedEncodingException{
		ModelAndView mav = new ModelAndView("aftersales/order/search_customer");
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request,pageNo,10);
		TraderCustomerVo tcv = new TraderCustomerVo();
		tcv.setCompanyId(user.getCompanyId());
		tcv.setName(URLDecoder.decode(searchName, "UTF-8"));
		Map<String, Object> map = traderCustomerService.getTraderCustomerVoPage(tcv, page,null);
		@SuppressWarnings("unchecked")
		List<TraderCustomerVo> list = (List<TraderCustomerVo>) map.get("list");
		page = (Page) map.get("page");
		mav.addObject("list", list);
		mav.addObject("page", page);
		mav.addObject("searchName", URLDecoder.decode(searchName, "UTF-8"));
		return mav;
	}

	/**
	 * <b>Description:</b><br> 根据traderid和tradertype查询联系人和财务信息
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月3日 上午11:38:25
	 */
	@ResponseBody
	@RequestMapping(value="/getCustomerContactAndFinace")
	public ResultInfo<?> getCustomerContactAndFinace(HttpServletRequest request,TraderVo trader){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		trader.setCompanyId(user.getCompanyId());
		Map<String, Object> map = afterSalesOrderService.getCustomerContactAndFinace(trader);
		if(!map.isEmpty()){
			ResultInfo res =new ResultInfo<>(0, "查询成功");
			if(map.containsKey("contact")){
				net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(map.get("contact"));
				List<TraderContact> list = (List<TraderContact>) JSONArray.toCollection(json, TraderContact.class);
				res.setListData(list);
			}
			if(map.containsKey("finace")){
				JSONObject json = JSONObject.fromObject(map.get("finace"));
				TraderFinanceVo traderFinanceVo = (TraderFinanceVo) JSONObject.toBean(json, TraderFinanceVo.class);
				res.setData(traderFinanceVo);
			}
			return res;
		}else{
			return new ResultInfo<>();
		}
	}

	/**
	 * <b>Description:</b><br> 保存新增售后产品和工程师的关系
	 * @param request
	 * @param afterSalesInstallstionVo
	 * @param afterSaleNums
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月27日 下午5:54:04
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveAddAfterSalesEngineer")
	@SystemControllerLog(operationType = "add",desc = "保存新增售后产品和工程师的关系")
	public ModelAndView saveAddAfterSalesEngineer(HttpServletRequest request,AfterSalesInstallstionVo afterSalesInstallstionVo,
			@RequestParam(required = false, value="afterSaleNums") String [] afterSaleNums,String start){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		afterSalesInstallstionVo.setAfterSalesNums(afterSaleNums);
		afterSalesInstallstionVo.setServiceTime(DateUtil.convertLong(start, "yyyy-MM-dd"));
		ResultInfo<?> res = afterSalesOrderService.saveAfterSalesEngineer(afterSalesInstallstionVo,user);
		if(res == null || res.getCode() == -1){
			mav.addObject("message", res != null?res.getMessage():"");
			return fail(mav);
		}else{
			mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
			if(afterSalesInstallstionVo.getType() == 541 || afterSalesInstallstionVo.getType() == 584){
				mav.addObject("url","./viewAfterSalesDetail.do?afterSalesId="+afterSalesInstallstionVo.getAfterSalesId()+"&traderType=1");
			}else{
				mav.addObject("url","./viewAfterSalesDetail.do?afterSalesId="+afterSalesInstallstionVo.getAfterSalesId());
			}
			return success(mav);
		}
	}

	/**
	 * <b>Description:</b><br> 保存编辑售后产品和工程师的关系
	 * @param request
	 * @param afterSalesInstallstionVo
	 * @param afterSaleNums
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月27日 下午5:54:04
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditAfterSalesEngineer")
	public ModelAndView saveEditAfterSalesEngineer(HttpServletRequest request,AfterSalesInstallstionVo afterSalesInstallstionVo,
			@RequestParam(required = false, value="afterSaleNums") String [] afterSaleNums,String start,String beforeParams){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		afterSalesInstallstionVo.setAfterSalesNums(afterSaleNums);
		afterSalesInstallstionVo.setServiceTime(DateUtil.convertLong(start, "yyyy-MM-dd"));
		ResultInfo<?> res = afterSalesOrderService.saveAfterSalesEngineer(afterSalesInstallstionVo,user);
		if(res == null || res.getCode() == -1){
			return fail(mav);
		}else{
			mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
			if(afterSalesInstallstionVo.getType() == 541 || afterSalesInstallstionVo.getType() == 584){
				mav.addObject("url","./viewAfterSalesDetail.do?afterSalesId="+afterSalesInstallstionVo.getAfterSalesId()+"&traderType=1");
			}else{
				mav.addObject("url","./viewAfterSalesDetail.do?afterSalesId="+afterSalesInstallstionVo.getAfterSalesId());
			}

			return success(mav);
		}
	}

	/**
	 * <b>Description:</b><br> 编辑售后服务费
	 * @param request
	 * @param afterSalesInstallstion
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月24日 下午2:55:14
	 */
	@ResponseBody
	@RequestMapping(value="/editInstallstionPage")
	public ModelAndView editInstallstionPage(HttpServletRequest request,AfterSalesDetail afterSalesDetail,String flag) throws IOException{
		ModelAndView mav = new ModelAndView("order/saleorder/edit_afterSales_installation");
		if(ObjectUtils.notEmpty(flag) && !"bth".equals(flag)){
			AfterSalesDetailVo afterSalesDetailVo = afterSalesOrderService.getAfterSalesDetailVoByParam(afterSalesDetail);
			mav.addObject("afterSalesDetail", afterSalesDetailVo);
			mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSalesDetailVo)));
		}else{
			mav.addObject("afterSalesDetail", afterSalesDetail);
			mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSalesDetail)));
		}
		mav.addObject("flag", flag);
		return mav;
	}

	/**
	 * <b>Description:</b><br> 保存编辑的安调信息
	 * @param request
	 * @param afterSalesDetail
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月25日 上午8:46:23
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditInstallstion")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑的售后服务费")
	public ResultInfo<?> saveEditInstallstion(HttpServletRequest request,AfterSalesDetail afterSalesDetail,
				String beforeParams,String invoiceTraderContactIds,String invoiceTraderAddressIds){
		if(afterSalesDetail.getIsSendInvoice() == null){
			afterSalesDetail.setIsSendInvoice(1);
		}
		if(ObjectUtils.notEmpty(invoiceTraderAddressIds)){
			String [] addresses = invoiceTraderAddressIds.split("\\|");
			if(addresses.length > 0 && ObjectUtils.notEmpty(addresses[0])){
				afterSalesDetail.setInvoiceTraderAddressId(Integer.valueOf(addresses[0]));
			}
			if(addresses.length > 1 && ObjectUtils.notEmpty(addresses[1])){
				afterSalesDetail.setInvoiceTraderArea(addresses[1]);
			}
			if(addresses.length > 2 && ObjectUtils.notEmpty(addresses[2])){
				afterSalesDetail.setInvoiceTraderAddress(addresses[2]);
			}
		}
		if(ObjectUtils.notEmpty(invoiceTraderContactIds)){
			String [] contacts = invoiceTraderContactIds.split("\\|");
			if(contacts.length > 0 && ObjectUtils.notEmpty(contacts[0])){
				afterSalesDetail.setInvoiceTraderContactId(Integer.valueOf(contacts[0]));
			}
			if(contacts.length > 1 && ObjectUtils.notEmpty(contacts[1])){
				afterSalesDetail.setInvoiceTraderContactName(contacts[1]);
			}
			if(contacts.length > 2){
				if(ObjectUtils.notEmpty(contacts[2])){
					afterSalesDetail.setInvoiceTraderContactTelephone(contacts[2]);
				}else{
					afterSalesDetail.setInvoiceTraderContactTelephone("");
				}

			}
			if(contacts.length > 3 && ObjectUtils.notEmpty(contacts[3])){
				afterSalesDetail.setInvoiceTraderContactMobile(contacts[3]);
			}
		}
		ResultInfo<?> res = afterSalesOrderService.saveEditInstallstion(afterSalesDetail);

		if(res == null){
			return new ResultInfo<>();
		}
		return res;
	}

    /**
	 * <b>Description:</b><br> 跳转到新增第三方售后页面
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月10日 上午10:48:10
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/addAfterSalesPage")
	public ModelAndView addAfterSalesPage(HttpServletRequest request,String flag){
		ModelAndView mav =new ModelAndView();
		if("qt".equals(flag)){
			mav.setViewName("aftersales/order/add_afterSales_qt");
			return mav;
		}
		mav.addObject("domain", domain);
		if("at".equals(flag)){//安调
			List<Region> provinceList = regionService.getRegionByParentId(1);
			mav.addObject("provinceList", provinceList);
			mav.setViewName("aftersales/order/add_afterSales_at");
		}else if("wx".equals(flag)){
			List<Region> provinceList = regionService.getRegionByParentId(1);
			mav.addObject("provinceList", provinceList);
			mav.setViewName("aftersales/order/add_afterSales_wx");
		}else if("tk".equals(flag)){
			mav.setViewName("aftersales/order/add_afterSales_tk");
		}else if("jz".equals(flag)){
			mav.setViewName("aftersales/order/add_afterSales_jz");
		}
		return mav;
	}

	/**
	 * <b>Description:</b><br> 保存新增售后
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午1:27:56
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveAddAfterSales")
	@SystemControllerLog(operationType = "add",desc = "保存新增售后")
	public ModelAndView saveAddAfterSales(HttpServletRequest request,AfterSalesVo afterSalesVo,
			@RequestParam(required = false, value="fileName") String [] fileName,
			@RequestParam(required = false, value="fileUri") String [] fileUri){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSalesVo.setAttachName(fileName);
		afterSalesVo.setAttachUri(fileUri);
		afterSalesVo.setSubjectType(537);//第三方
		afterSalesVo.setAtferSalesStatus(0);
		afterSalesVo.setServiceUserId(user.getUserId());
		afterSalesVo.setStatus(0);
		afterSalesVo.setValidStatus(0);
		afterSalesVo.setDomain(domain);
		afterSalesVo.setCompanyId(user.getCompanyId());
		if(afterSalesVo.getType() == 551){
			afterSalesVo.setRefundAmountStatus(1);
		}
		ModelAndView mav = new ModelAndView();
		ResultInfo<?> res = afterSalesOrderService.saveAddAfterSales(afterSalesVo, user);
		//mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		//mav.addObject("url","./getAfterSalesPage.do");
		if(res.getCode()==0){
			mav.addObject("url", "./viewAfterSalesDetail.do?afterSalesId=" + res.getData());
			return success(mav);
		}else{
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br> 跳转到第三方售后编辑页
	 * @param request
	 * @param afterSales
	 * @return
	 * @throws IOException
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月20日 上午9:00:02
	 */
	@ResponseBody
	@RequestMapping(value="/editAfterSalesPage")
	public ModelAndView editAfterSalesPage(HttpServletRequest request,AfterSalesVo afterSales) throws IOException{
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		if(afterSales == null){
			return pageNotFound();
		}
		afterSales.setCompanyId(user.getCompanyId());
		afterSales = afterSalesOrderService.getAfterSalesVoDetail(afterSales);
		mav.addObject("afterSales", afterSales);
		if(afterSales.getType() ==553){//售后其他
			mav.setViewName("aftersales/order/edit_afterSales_qt");
			return mav;
		}
		mav.addObject("domain", domain);

		if(afterSales.getType() ==550 || afterSales.getType() ==585){
			Integer areaId = afterSales.getAreaId();
			if(ObjectUtils.notEmpty(areaId)){
				Region region = regionService.getRegionByRegionId(areaId);
				if(region.getRegionType()==1){//省级
					mav.addObject("province", areaId);
				}else if(region.getRegionType()==2){
					List<Region> cityList = regionService.getRegionByParentId(region.getParentId());
					mav.addObject("cityList", cityList);
					mav.addObject("city", areaId);
					mav.addObject("province", region.getParentId());
				}else if(region.getRegionType() == 3){
					List<Region> zoneList = regionService.getRegionByParentId(region.getParentId());
					mav.addObject("zoneList", zoneList);
					mav.addObject("zone", areaId);
					Region cyregion = regionService.getRegionByRegionId(zoneList.get(0).getParentId());
					List<Region> cityList = regionService.getRegionByParentId(cyregion.getParentId());
					mav.addObject("cityList", cityList);
					mav.addObject("city", region.getParentId());
					mav.addObject("province", cityList.get(0).getParentId());
				}
				List<Region> provinceList = regionService.getRegionByParentId(1);
				mav.addObject("provinceList", provinceList);
			}
			if(afterSales.getType() ==550){
				mav.setViewName("aftersales/order/edit_afterSales_at");
			}else{
				mav.setViewName("aftersales/order/edit_afterSales_wx");
			}
		}else if(afterSales.getType() ==551){
			mav.setViewName("aftersales/order/edit_afterSales_tk");
		}else if(afterSales.getType() ==552){
			mav.setViewName("aftersales/order/edit_afterSales_jz");
		}
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(afterSales)));
		return mav;
	}

	/**
	 * <b>Description:</b><br> 保存编辑售后
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月11日 下午1:27:56
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditAfterSales")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑售后")
	public ModelAndView saveEditAfterSales(HttpServletRequest request,AfterSalesVo afterSalesVo,String beforeParams,
			@RequestParam(required = false, value="fileName") String [] fileName,
			@RequestParam(required = false, value="fileUri") String [] fileUri){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);

		// add by franlin.wu for [公司ID] at 2018-12-19 begin
		if(null != user)
		{
			// 公司ID
			afterSalesVo.setCompanyId(user.getCompanyId());
		}
		// add by franlin.wu for [公司ID] at 2018-12-19 end

		afterSalesVo.setAttachName(fileName);
		afterSalesVo.setAttachUri(fileUri);
		afterSalesVo.setDomain(domain);
		afterSalesVo.setTraderType(1);
		afterSalesVo.setSubjectType(537);//第三方
		if(afterSalesVo.getType() == 551){
			afterSalesVo.setRefundAmountStatus(1);
		}
		ModelAndView mav = new ModelAndView();
		ResultInfo<?> res = afterSalesOrderService.saveEditAfterSales(afterSalesVo, user);
//		mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
//		mav.addObject("url","./getAfterSalesPage.do");
		if(res.getCode()==0){
			mav.addObject("url", "./viewAfterSalesDetail.do?afterSalesId=" + res.getData());
			return success(mav);
		}else{
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br> 退票材料页面
	 * @param request
	 *
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@ResponseBody
	@RequestMapping(value = "/ticketReturnInit")
	public ModelAndView ticketReturnInit(HttpServletRequest request, Integer afterSalesId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("afterSalesId", afterSalesId);
		mv.setViewName("aftersales/order/ticket_return");
		return mv;
	}

	/**
	 * <b>Description:</b><br> 订单合同回传初始化
	 * @param request
	 *
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnInit")
	public ModelAndView contractReturnInit(HttpServletRequest request, Integer afterSalesId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("afterSalesId", afterSalesId);
		mv.setViewName("aftersales/order/contract_return");
		return mv;
	}

	/**
	 * <b>Description:</b><br> 订单合同回传文件上传
	 * @param request
	 * @param response
	 * @param lwfile
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:47:39
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnUpload")
	public FileInfo contractReturnUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			String path = "/upload/aftersales";
			long size = lwfile.getSize();
			if(size > 2*1024*1024){
				return new FileInfo(-1,"图片大小应为2MB以内");
			}
			return ftpUtilService.uploadFile(lwfile, path,request,"");
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}

	/**
	 * <b>Description:</b><br> 订单合同回传保存
	 * @param request
	 * @param attachment
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:55:15
	 */
	@ResponseBody
	@RequestMapping(value="/contractReturnSave")
	@SystemControllerLog(operationType = "add",desc = "订单合同回传保存")
	public ResultInfo<?> contractReturnSave(HttpServletRequest request,Attachment attachment){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(attachment != null && (attachment.getUri().contains("jpg") || attachment.getUri().contains("png")
				|| attachment.getUri().contains("gif") || attachment.getUri().contains("bmp"))){
			attachment.setAttachmentType(SysOptionConstant.ID_460);
		}else{
			attachment.setAttachmentType(SysOptionConstant.ID_461);
		}
		if(user!=null){
			attachment.setCreator(user.getUserId());
			attachment.setAddTime(DateUtil.sysTimeMillis());
		}
		return saleorderService.saveSaleorderAttachment(attachment);
	}

	/**
	 * <b>Description:</b><br> 订单合同回传删除
	 * @param request
	 * @param attachment
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:58:22
	 */
	@ResponseBody
	@RequestMapping(value="/contractReturnDel")
	@SystemControllerLog(operationType = "edit",desc = "订单合同回传删除")
	public ResultInfo<?> contractReturnDel(HttpServletRequest request,Attachment attachment){
		return saleorderService.delSaleorderAttachment(attachment);
	}

	/**
	 * <b>Description:</b><br> 审核通过
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月19日 下午4:14:13
	 */
	@ResponseBody
	@RequestMapping(value="/editPassAudit")
	@SystemControllerLog(operationType = "add",desc = "售后订单审核通过")
	public ResultInfo<?> editPassAudit(HttpServletRequest request,AfterSalesVo afterSales){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		//afterSales.setTraderType(2);
		afterSales.setValidStatus(1);//已生效
		afterSales.setValidTime(DateUtil.sysTimeMillis());
		afterSales.setStatus(2);//审核通过
		afterSales.setAtferSalesStatus(1);//进行中
		afterSales.setCompanyId(user.getCompanyId());
		afterSales.setPayer(user.getCompanyName());
		afterSales.setModTime(DateUtil.sysTimeMillis());
		afterSales.setUpdater(user.getUserId());
		ResultInfo<?> res = afterSalesOrderService.editApplyAudit(afterSales);
		if(res == null){
			return new ResultInfo<>();
		}
		return res;
	}

	/**
	 * <b>Description:</b><br> 确认完成
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月19日 下午4:14:13
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/editConfirmComplete")
	@SystemControllerLog(operationType = "edit",desc = "售后订单确认完成")
	public ResultInfo<?> editConfirmComplete(HttpServletRequest request,AfterSalesVo afterSales){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		//销售订单确认完成和第三方售后确认完成需要审核流程
		if (afterSales.getSubjectType().equals(535) || afterSales.getSubjectType().equals(537)) {
		    	AfterSalesVo afterSalesVo = new AfterSalesVo();
			afterSalesVo.setAfterSalesId(afterSales.getAfterSalesId());
			afterSalesVo.setCompanyId(user.getCompanyId());
			afterSalesVo.setTraderId(afterSales.getTraderId());
			//如果是销售订单售后
			if(afterSales.getType() != null && (afterSales.getType().equals(539) || afterSales.getType().equals(540) || afterSales.getType().equals(541) || afterSales.getType().equals(542) || afterSales.getType().equals(543) || afterSales.getType().equals(544) || afterSales.getType().equals(545) || afterSales.getType().equals(584)))
			{
			    //客户
			    afterSalesVo.setTraderType(1);

			}else if(afterSales.getType() != null && (afterSales.getType().equals(546) || afterSales.getType().equals(547) || afterSales.getType().equals(548) || afterSales.getType().equals(549)))
			{
			    //供应商
			    afterSalesVo.setTraderType(2);
			}
			AfterSalesVo afterSalesInfo = afterSalesOrderService.getAfterSalesVoDetail(afterSalesVo);
			//将完成原因，完成备注，完成人员存在数据库
			afterSales.setAfterSalesStatusUser(user.getUserId());//设置完结人员Id
			ResultInfo<?> res = afterSalesOrderService.updateAfterSalesById(afterSales);

			afterSales.setAfterSalesNo(afterSalesInfo.getAfterSalesNo());
			afterSales.setAtferSalesStatus(2);//已完结
			afterSales.setCompanyId(user.getCompanyId());
			afterSales.setModTime(DateUtil.sysTimeMillis());
			afterSales.setUpdater(user.getUserId());
			afterSales.setAfterSalesStatusUser(user.getUserId());//设置完结人员Id
			//对售后单的流程判断金额进行计算
	        	// 销售售后
	        	if (afterSalesInfo.getSubjectType().equals(535)) {
	        	    // 销售订单退货
	        	    if (afterSalesInfo.getType().equals(539)) {
		        		if (afterSalesInfo.getRefundAmount() != null) {
		        		    afterSales.setOrderTotalAmount(afterSalesInfo.getRefundAmount());
		        		} else {
		        		    afterSales.setOrderTotalAmount(BigDecimal.ZERO);
		        		}
	        		// 销售订单换货
	        	    } else if (afterSalesInfo.getType().equals(540)) {

	        	    	//关联外借单
	    				List<LendOut>  lendoutList = warehouseOutService.getLendOutInfoList(afterSalesVo);
	    				if(CollectionUtils.isNotEmpty(lendoutList)) {
							//存在进行中的外接单不允许关闭
							return new ResultInfo(-1, "存在未完成的外借单，无法操作" );
						}

		        		// 售后单对应的商品信息
		        		if (afterSalesInfo.getAfterSalesGoodsList() != null) {
		        		    BigDecimal totalAmount = BigDecimal.ZERO;
		        		    for (AfterSalesGoodsVo asg : afterSalesInfo.getAfterSalesGoodsList()) {
			        			if (asg.getSaleorderNum() != null && asg.getSaleorderPrice() != null) {
			        			    BigDecimal sn = new BigDecimal(asg.getSaleorderNum());
			        			    totalAmount = totalAmount.add(sn.multiply(asg.getSaleorderPrice()));
			        			}
		        		    }
		        		    afterSales.setOrderTotalAmount(totalAmount);
		        		} else {
		        		    afterSales.setOrderTotalAmount(BigDecimal.ZERO);
		        		}
	        		// 销售订单退款
	        	    } else if (afterSalesInfo.getType().equals(543)) {
	        		if (afterSalesInfo.getRefundAmount() != null) {
	        		    afterSales.setOrderTotalAmount(afterSalesInfo.getRefundAmount());
	        		} else {
	        		    afterSales.setOrderTotalAmount(BigDecimal.ZERO);
	        		}
	        		// 销售订单维修
	        	    } else if (afterSalesInfo.getType().equals(584)) {
	        		if (afterSalesInfo.getServiceAmount() != null) {
	        		    afterSales.setOrderTotalAmount(afterSalesInfo.getServiceAmount());
	        		} else {
	        		    afterSales.setOrderTotalAmount(BigDecimal.ZERO);
	        		}
	        	    } else {
	        		// 其他
	        		afterSales.setOrderTotalAmount(BigDecimal.ZERO);
	        	    }
	        	    // 第三方售后
	        	}else if (afterSalesInfo.getSubjectType().equals(537)) {
	        	    // 第三方退款
	        	    if (afterSalesInfo.getType().equals(551)) {
	        		if (afterSalesInfo.getRefundAmount() != null) {
	        		    afterSales.setOrderTotalAmount(afterSalesInfo.getRefundAmount());
	        		} else {
	        		    afterSales.setOrderTotalAmount(BigDecimal.ZERO);
	        		}
	        		// 第三方维修
	        	    } else if (afterSalesInfo.getType().equals(585)) {
	        		if (afterSalesInfo.getServiceAmount() != null) {
	        		    afterSales.setOrderTotalAmount(afterSalesInfo.getServiceAmount());
	        		} else {
	        		    afterSales.setOrderTotalAmount(BigDecimal.ZERO);
	        		}
	        		// 其他
	        	    } else {
	        		afterSales.setOrderTotalAmount(BigDecimal.ZERO);
	        	    }
	        	}

	        	try {
	        	    // 售后单完成
	        	    afterSales.setVerifiesType(1);
	        	    Map<String, Object> variableMap = new HashMap<String, Object>();
	        	    // 开始生成流程(如果没有taskId表示新流程需要生成)
	        	    variableMap.put("afterSalesVo", afterSales);
	        	    variableMap.put("afterSales", afterSales);
	        	    variableMap.put("currentAssinee", user.getUsername());
	        	    variableMap.put("processDefinitionKey", "overAfterSalesVerify");
	        	    variableMap.put("businessKey", "overAfterSalesVerify_" + afterSalesInfo.getAfterSalesId());
	        	    variableMap.put("relateTableKey", afterSalesInfo.getAfterSalesId());
	        	    variableMap.put("relateTable", "T_AFTER_SALES");
	        	    actionProcdefService.createProcessInstance(request, "overAfterSalesVerify",
	        		    "overAfterSalesVerify_" + afterSalesInfo.getAfterSalesId(), variableMap);
	        	    // 默认申请人通过
	        	    // 根据BusinessKey获取生成的审核实例
	        	    Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
	        		    "overAfterSalesVerify_" + afterSalesInfo.getAfterSalesId());
	        	    if (historicInfo.get("endStatus") != "审核完成") {
		        		Task taskInfo = (Task) historicInfo.get("taskInfo");
		        		String taskId = taskInfo.getId();
		        		Authentication.setAuthenticatedUserId(user.getUsername());
		        		Map<String, Object> variables = new HashMap<String, Object>();
		        		// 默认审批通过
		        		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
		        			user.getUsername(), variables);
		        		// 如果未结束添加审核对应主表的审核状态
		        		if (!complementStatus.getData().equals("endEvent")) {
		        		    verifiesRecordService.saveVerifiesInfo(taskId, 0);
		        		}
//		        		// 销售订单退货,产品解锁
//		        	    if (afterSalesInfo.getType().equals(539) || afterSalesInfo.getType().equals(540)) {
//		        	    	afterSalesOrderService.getNoLockSaleorderGoodsVo(afterSales);
//		        	    }
	        	    }
	        	    return new ResultInfo(0, "操作成功");
	        	} catch (Exception e) {
	        		logger.error("editConfirmComplete:", e);
	        	    return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
	        	}
		}else{
		    //采购单售后不走审核流程
		    	afterSales.setAtferSalesStatus(2);//已完结
			afterSales.setCompanyId(user.getCompanyId());
			afterSales.setModTime(DateUtil.sysTimeMillis());
			afterSales.setUpdater(user.getUserId());
			afterSales.setAfterSalesStatusUser(user.getUserId());//设置完结人员Id
			ResultInfo<?> res = afterSalesOrderService.editApplyAudit(afterSales);
			if(res == null){
				return new ResultInfo<>();
			}else if(res.getCode().equals(0)){
				afterSalesOrderService.updateAfterOrderDataUpdateTime(afterSales.getAfterSalesId(),null, OrderDataUpdateConstant.AFTER_ORDER_END);
			}
			return res;
		}


	}

	/**
	 * <b>Description:</b><br> 申请付款页面
	 * @param request
	 *
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/applyPayPage")
	public ModelAndView applyPayPage(HttpServletRequest request, AfterSalesVo afterSales) {
		ModelAndView mv = new ModelAndView();
		afterSales = afterSalesOrderService.getAfterSalesApplyPay(afterSales);
		mv.addObject("afterSales", afterSales);
		if(afterSales.getTraderSubject() == 535 || afterSales.getTraderSubject() == 537){
			mv.setViewName("aftersales/order/apply_pay_at");
		}else{
			// 获取银行帐号列表
			TraderFinance tf = new TraderFinance();
			tf.setTraderId(afterSales.getTraderId());
			tf.setTraderType(ErpConst.TWO);
			List<TraderFinance> traderFinance = traderCustomerService.getTraderCustomerFinanceList(tf);
			mv.addObject("traderFinance", traderFinance);

			// 获取对应供应商主信息
			TraderSupplier traderSupplier = new TraderSupplier();
			traderSupplier.setTraderId(afterSales.getTraderId());
			TraderSupplierVo supplierInfo = traderSupplierService.getSupplierInfoByTraderSupplier(traderSupplier);
			mv.addObject("supplierInfo", supplierInfo);

			mv.setViewName("aftersales/order/apply_pay_buyorder");
		}

		return mv;
	}

	/**
	 * <b>Description:</b><br> 销售执行退款前验证财务是否已确认退票
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年9月4日 下午2:34:46
	 */
	@ResponseBody
	@RequestMapping(value = "/isAllReturnTicket")
	public ResultInfo<?> isAllReturnTicket(HttpServletRequest request, AfterSalesVo afterSales) {
		ResultInfo<?> res = afterSalesOrderService.isAllReturnTicket(afterSales);
		return res;
	}

	/**
	 * <b>Description:</b><br> 执行退款运算操作
	 * @param request
	 *
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@ResponseBody
	@FormToken(remove=true)
	@RequestMapping(value = "/executeRefundOperation")
	@SystemControllerLog(operationType = "add",desc = "执行退款运算操作")
	public ResultInfo<?> executeRefundOperation(HttpServletRequest request, AfterSalesVo afterSales) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if(afterSales.getType() == 539 || afterSales.getType() == 543){
			afterSales.setPayer(user.getCompanyName());
		}
		ResultInfo<?> res = afterSalesOrderService.executeRefundOperation(afterSales, user);
		return res;
	}

	/**
	 * <b>Description:</b><br> 保存销售实退金额大于0的申请付款/采购实退金额小于0的申请付款
	 * @param request
	 *
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveRealRefundAmountApplyPay")
	@SystemControllerLog(operationType = "add",desc = "保存销售实退金额大于0的申请付款/采购实退金额小于0的申请付款")
	public ResultInfo<?> saveRealRefundAmountApplyPay(HttpServletRequest request, PayApplyVo payApplyVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> res = afterSalesOrderService.saveRefundApplyPay(payApplyVo, user);
		if(res.getCode() == 0){
		    payApplyVo.setPayApplyId((Integer) res.getData());
		}else{
		    return new ResultInfo<>();
		}
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			AfterSalesVo afterSalesVo= new AfterSalesVo();
			afterSalesVo.setAfterSalesId(payApplyVo.getRelatedId());
			AfterSalesVo afterSalesInfo= afterSalesOrderService.getAfterSalesVoListById(afterSalesVo);

			PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApplyVo.getPayApplyId());
			payApplyInfo.setOrderNo(afterSalesInfo.getAfterSalesNo());
			payApplyInfo.setPayApplyId(payApplyVo.getPayApplyId());
			variableMap.put("payApply", payApplyInfo);
			variableMap.put("currentAssinee", user.getUsername());
			variableMap.put("processDefinitionKey", "paymentVerify");
			variableMap.put("businessKey", "paymentVerify_" + payApplyInfo.getPayApplyId());
			variableMap.put("relateTableKey", payApplyInfo.getPayApplyId());
			variableMap.put("relateTable", "T_PAY_APPLY");
			variableMap.put("orgId", user.getOrgId());
			// 流程条件标识
			variableMap.put("activitiType", "PaymentVerify");
			actionProcdefService.createProcessInstance(request, "paymentVerify",
					"paymentVerify_" + payApplyInfo.getPayApplyId(), variableMap);
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"paymentVerify_" + payApplyInfo.getPayApplyId());
			if (historicInfo.get("endStatus") != "审核完成") {
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				String taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 设置审核完成监听器回写参数
				variables.put("tableName", "T_PAY_APPLY");
				variables.put("id", "PAY_APPLY_ID");
				variables.put("idValue", payApplyInfo.getPayApplyId());
				variables.put("key", "VALID_STATUS");
				variables.put("value", 1);
				// 回写数据的表在db中
				variables.put("db", 2);
				// 默认审批通过
				ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
						user.getUsername(), variables);
				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}
			}
			return res;
		} catch (Exception e) {
			logger.error("saveRealRefundAmountApplyPay:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}

	/**
	 * <b>Description:</b><br> 新增保存申请付款
	 * @param request
	 *
	 *
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/saveApplyPay")
	@SystemControllerLog(operationType = "add",desc = "新增保存申请付款")
	public ModelAndView saveApplyPay(HttpServletRequest request, PayApplyVo payApplyVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		String traderFinance = request.getParameter("traderFinance");
		if(ObjectUtils.notEmpty(traderFinance)){
			String [] str = traderFinance.split(",");
			if(str.length >= 1 && ObjectUtils.notEmpty(str[0])){
				payApplyVo.setBank(str[0]);
			}
			if(str.length >= 2 && ObjectUtils.notEmpty(str[1])){
				payApplyVo.setBankAccount(str[1]);
			}
			if(str.length >= 3 && ObjectUtils.notEmpty(str[2])){
				payApplyVo.setBankCode(str[2]);
			}
			if(str.length >= 5 && ObjectUtils.notEmpty(str[4])){
				payApplyVo.setTraderFinanceId(Integer.valueOf(str[4]));
			}
		}

		mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		if(payApplyVo.getTraderType() == null ||"".equals(payApplyVo.getTraderType())){//第三方申请付款后跳转页面
			mav.addObject("url","./viewAfterSalesDetail.do?afterSalesId="+payApplyVo.getRelatedId());
		}else{//销售售后安调维修申请付款后跳转页面
			mav.addObject("url","./viewAfterSalesDetail.do?afterSalesId="+payApplyVo.getRelatedId()+"&traderType="+payApplyVo.getTraderType());
		}
		ResultInfo<?> res = afterSalesOrderService.saveApplyPay(payApplyVo, user);
		AfterSalesVo afterSalesVo= new AfterSalesVo();
		afterSalesVo.setAfterSalesId(payApplyVo.getRelatedId());
		AfterSalesVo afterSalesInfo= afterSalesOrderService.getAfterSalesVoListById(afterSalesVo);
		try {
		    //付款申请ID
		    Integer payApplyId= (Integer) res.getData();
		    PayApply payApplyInfo= payApplyService.getPayApplyInfo(payApplyId);
			if(afterSalesInfo != null){
				payApplyInfo.setOrderNo(afterSalesInfo.getAfterSalesNo());
			}
	    		Map<String, Object> variableMap = new HashMap<String, Object>();
	    		//开始生成流程(如果没有taskId表示新流程需要生成)
	    		    variableMap.put("payApply", payApplyInfo);
	    		    variableMap.put("currentAssinee", user.getUsername());
	    		    variableMap.put("processDefinitionKey","paymentVerify");
	    		    variableMap.put("businessKey","paymentVerify_" + payApplyInfo.getPayApplyId());
	    		    variableMap.put("relateTableKey",payApplyInfo.getPayApplyId());
	    		    variableMap.put("relateTable", "T_PAY_APPLY");
	    		    variableMap.put("orgId", user.getOrgId());
	    		    //流程条件标识
	    		    variableMap.put("activitiType", "PaymentVerify");
	    		    actionProcdefService.createProcessInstance(request,"paymentVerify","paymentVerify_" + payApplyInfo.getPayApplyId(),variableMap);
	    		    //默认申请人通过
	    		    //根据BusinessKey获取生成的审核实例
	    		    Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ payApplyInfo.getPayApplyId());
	    		if(historicInfo.get("endStatus") != "审核完成"){
	            		Task taskInfo = (Task) historicInfo.get("taskInfo");
	            		String taskId = taskInfo.getId();
	            		Authentication.setAuthenticatedUserId(user.getUsername());
	            		Map<String, Object> variables = new HashMap<String, Object>();
	            		//设置审核完成监听器回写参数
	            		variables.put("tableName", "T_PAY_APPLY");
	            		variables.put("id", "PAY_APPLY_ID");
	            		variables.put("idValue", payApplyInfo.getPayApplyId());
	            		variables.put("key", "VALID_STATUS");
	            		variables.put("value", 1);
	            		//回写数据的表在db中
	            		variables.put("db", 2);
	            		//默认审批通过
	            		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,"",user.getUsername(),variables);
	    			//如果未结束添加审核对应主表的审核状态
	        		if(!complementStatus.getData().equals("endEvent")){
	        		    verifiesRecordService.saveVerifiesInfo(taskId,0);
	        		}
	    		}
			} catch (Exception e) {
				logger.error("saveApplyPay:", e);
			    	mav.addObject("message", "付款申请流程出错");
				return fail(mav);
			}
		if(res.getCode()==0){
			return success(mav);
		}else{
			return fail(mav);
		}
	}


	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session, HttpServletRequest request, String taskId, Boolean pass,Integer type,Integer saleorderId,Integer afterSalesId) {
		ModelAndView mv = new ModelAndView();
		if(null != request.getParameter("sku") && !("".equals(request.getParameter("sku")))){
			mv.addObject("sku", request.getParameter("sku"));
		}
		if(null != request.getParameter("orderId") && !("".equals(request.getParameter("orderId")))){
			mv.addObject("orderId", request.getParameter("orderId"));
		}
		mv.addObject("taskId", taskId);
		mv.addObject("saleorderId", saleorderId);
		mv.addObject("afterSalesId", afterSalesId);
		mv.addObject("pass", pass);
		mv.addObject("type", type);
		mv.setViewName("aftersales/order/complement");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b><br>
	 * 订单审核操作
	 *
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementAfterSaleTask")
	@SystemControllerLog(operationType = "add",desc = "订单审核操作")
	public ResultInfo<?> complementAfterSaleTask(HttpServletRequest request, String taskId, String comment, Boolean pass,Integer saleorderId,Integer afterSalesId,
			HttpSession session) {
	    	// 获取session中user信息
	 	User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		//审批操作
		try {
		    	if(!pass){
			    //如果审核不通过
		    	    TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量
		    	    taskService.setVariable(taskId,"value", 3);
		    	    String tableName= (String) taskService.getVariable(taskId, "tableName");
		    	    String id=(String) taskService.getVariable(taskId, "id");
		    	    Integer idValue=(Integer) taskService.getVariable(taskId, "idValue");
		    	    String key= (String) taskService.getVariable(taskId, "key");
		    	    if(tableName != null && id != null && idValue != null && key != null){
		    		actionProcdefService.updateInfo(tableName, id, idValue, key, 3, 2);
		    	    }
		    	    verifiesRecordService.saveVerifiesInfo(taskId,2);
			}
		    ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,comment,user.getUsername(),variables);
			  //如果审核没结束添加审核对应主表的审核状态
			    if(!complementStatus.getData().equals("endEvent")){
				Integer status = 0;
				if(pass){
				    //如果审核通过
				     status = 0;
				}else{
				    //如果审核不通过
				    status = 2;
				}
				verifiesRecordService.saveVerifiesInfo(taskId,status);

			    }

//				// 销售订单退货,产品解锁
//				if (afterSalesInfo.getType().equals(539) || afterSalesInfo.getType().equals(540)) {
//	    	    	afterSalesOrderService.getNoLockSaleorderGoodsVo(afterSales);
//	    	    }
			    if(null != request.getParameter("sku") && !("".equals(request.getParameter("sku"))) && null != request.getParameter("orderId") && !("".equals(request.getParameter("orderId")))){
			    	AfterSalesVo afterSalesInfo = new AfterSalesVo();
			    	afterSalesInfo.setSku(request.getParameter("sku"));
			    	afterSalesInfo.setOrderId(Integer.valueOf(request.getParameter("orderId")));
			    	afterSalesOrderService.getNoLockSaleorderGoodsVo(afterSalesInfo);

				    Saleorder saleorder = new Saleorder();
				    saleorder.setSaleorderId(Integer.valueOf(request.getParameter("orderId")));
				    saleorder.setCompanyId(user.getCompanyId());
				    saleorderService.synchronousOrderStatus(request,saleorder);
				}
			if (saleorderId!=null||(null != request.getParameter("orderId") && !("".equals(request.getParameter("orderId"))))) {
					Saleorder saleorder = new Saleorder();
					if(saleorderId!=null) {
						saleorder.setSaleorderId(saleorderId);
					}else {
						saleorder.setSaleorderId(Integer.valueOf(request.getParameter("orderId")));
					}
					if(complementStatus.getData().equals("endEvent")&&pass){

						// add by Tomcat.Hui 2020/3/6 1:57 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分  售后产生的付款状态变更需要通知mjx. start
						afterSalesOrderService.notifyStatusToMjx(saleorder.getSaleorderId(),afterSalesId);
						// add by Tomcat.Hui 2020/3/6 1:57 下午 .Desc: VDERP-2057 BD订单流程优化-ERP部分  售后产生的付款状态变更需要通知mjx. end

						if(afterSalesId!=null){
							AfterSales afterSales = new AfterSales();
							afterSales.setAfterSalesId(afterSalesId);
							afterSales.setBusinessType(1);
							AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoListById(afterSales);
							if (afterSalesVo.getType().equals(539)) {
								saleorder.setOperateType(StockOperateTypeConst.AFTERORDER_BACK_FINSH);
							}
						}
					}
					//更新售后单updateDataTime
					warehouseStockService.updateAfterOrderDataUpdateTime(afterSalesId,null,OrderDataUpdateConstant.AFTER_ORDER_END);
					//调用库存服务
					int i = warehouseStockService.updateOccupyStockService(saleorder, 0);
				}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("complementAfterSaleTask:"+afterSalesId, e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
		
		

	}
	
	/**
	 * <b>Description:</b><br> 跳转到发送派单通知页面
	 * @param request
	 * @param afterSalesInstallstionVo
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月29日 上午11:43:58
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/toSendInstallstionSmsPage")
	public ModelAndView toSendInstallstionSmsPage(HttpServletRequest request,AfterSalesInstallstionVo afterSalesInstallstionVo) throws UnsupportedEncodingException{
		ModelAndView mav = new ModelAndView("/aftersales/order/add_afterSales_sms");
		afterSalesInstallstionVo.setName(URLDecoder.decode(afterSalesInstallstionVo.getName(), "UTF-8"));
		afterSalesInstallstionVo.setTypeName(URLDecoder.decode(afterSalesInstallstionVo.getTypeName(), "UTF-8"));
		mav.addObject("asiv", afterSalesInstallstionVo);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 派单通知
	 * @param request
	 * @param afterSalesInstallstionVo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月29日 上午11:43:58
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/sendinstallstionsms")
	@SystemControllerLog(operationType = "edit",desc = "派单通知")
	public ResultInfo sendInstallstionSms(HttpServletRequest request,AfterSalesInstallstionVo afterSalesInstallstionVo){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			if(null == afterSalesInstallstionVo || afterSalesInstallstionVo.getAfterSalesInstallstionId() == null){
				return resultInfo;
			}
			resultInfo = afterSalesOrderService.sendInstallstionSms(afterSalesInstallstionVo,user);
		}catch (Exception e) {
			logger.error("sendinstallstionsms:", e);
		}
		
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 开票申请
	 * @param request
	 * 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 上午11:20:24
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="openInvoiceApply")
	public ModelAndView openInvoiceApply(HttpServletRequest request,InvoiceApply invoiceApply){
		ModelAndView mv = new ModelAndView();
		mv.addObject("invoiceApply", invoiceApply);
		mv.setViewName("aftersales/order/aftersale_invoice_apply");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存开票申请
	 * @param request
	 * 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 上午11:20:24
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="saveOpenInvoiceApply")
	@SystemControllerLog(operationType = "add",desc = "保存开票申请")
	public ResultInfo<?> saveOpenInvoiceApply(HttpServletRequest request,InvoiceApply invoiceApply){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			invoiceApply.setCreator(user.getUserId());
			invoiceApply.setAddTime(DateUtil.gainNowDate());
			invoiceApply.setCompanyId(user.getCompanyId());
			invoiceApply.setUpdater(user.getUserId());
			invoiceApply.setModTime(DateUtil.gainNowDate());
			invoiceApply.setYyValidStatus(1);
		}
		ResultInfo<?>  res = afterSalesOrderService.saveOpenInvoceApply(invoiceApply);
		if(user.getCompanyId() == 1) {
			return res;
		}else {
			if(res.getCode() == 0) {
				AfterSales afterSales = new AfterSales();
				afterSales.setAfterSalesId(invoiceApply.getRelatedId());
				AfterSalesVo afterSalesVo= afterSalesOrderService.getAfterSalesVoListById(afterSales);
				InvoiceApply invoiceApplyInfo= invoiceService.getInvoiceApplyByRelatedId(invoiceApply.getRelatedId(),SysOptionConstant.ID_504,invoiceApply.getCompanyId());
				if(afterSalesVo != null) {
					invoiceApplyInfo.setAfterSalesNo(afterSalesVo.getAfterSalesNo());
				}
				try {
					Map<String, Object> variableMap = new HashMap<String, Object>();
					// 开始生成流程(如果没有taskId表示新流程需要生成)
					variableMap.put("invoiceApply", invoiceApplyInfo);
					variableMap.put("currentAssinee", user.getUsername());
					variableMap.put("processDefinitionKey", "invoiceVerify");
					variableMap.put("businessKey", "invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId());
					variableMap.put("relateTableKey", invoiceApplyInfo.getInvoiceApplyId());
					variableMap.put("relateTable", "T_INVOICE_APPLY");
					
					actionProcdefService.createProcessInstance(request, "invoiceVerify",
							"invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId(), variableMap);
					// 默认申请人通过
					// 根据BusinessKey获取生成的审核实例
					Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
							"invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId());
					if (historicInfo.get("endStatus") != "审核完成") {
						Task taskInfo = (Task) historicInfo.get("taskInfo");
						String taskId = taskInfo.getId();
						Authentication.setAuthenticatedUserId(user.getUsername());
						Map<String, Object> variables = new HashMap<String, Object>();
						// 设置审核完成监听器回写参数
						variables.put("tableName", "T_INVOICE_APPLY");
						variables.put("id", "INVOICE_APPLY_ID");
						variables.put("idValue", invoiceApplyInfo.getInvoiceApplyId());
						variables.put("key", "VALID_STATUS");
						variables.put("value", 1);
						// 回写数据的表在db中
						variables.put("db", 2);
						// 默认审批通过
						ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
								user.getUsername(), variables);
						// 如果未结束添加审核对应主表的审核状态
						if (!complementStatus.getData().equals("endEvent")) {
							verifiesRecordService.saveVerifiesInfo(taskId, 0);
						}
					}
					return res;
				} catch (Exception e) {
					logger.error("saveOpenInvoiceApply:", e);
					return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
				}
			}else {
				return res;
			}
		}
	}
	
	/**
	 * <b>Description:</b><br> 售后直发产品确认全部收货
	 * @param request
	 * 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月28日 上午11:20:24
	 */
	@ResponseBody
	@RequestMapping(value="updateAfterSalesGoodsArrival")
	@SystemControllerLog(operationType = "update",desc = "售后直发产品确认全部收货")
	public ResultInfo<?> updateAfterSalesGoodsArrival(HttpServletRequest request,AfterSalesGoods afterSalesGoods){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesGoods.setArrivalTime(DateUtil.sysTimeMillis());
		afterSalesGoods.setArrivalStatus(2);//到货状态
		afterSalesGoods.setArrivalUserId(user.getUserId());
		afterSalesGoods.setDeliveryNum(afterSalesGoods.getArrivalNum());
		afterSalesGoods.setDeliveryStatus(2);//发货状态
		afterSalesGoods.setDeliveryTime(afterSalesGoods.getArrivalTime());
		return afterSalesOrderService.updateAfterSalesGoodsArrival(afterSalesGoods);
	}
	
	/**
	 * 
	 * <b>Description:</b>售后新增售后费用类型查询费用类型列表<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月23日 18:55
	 */
//	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/addCostType")
	public ModelAndView addCostType(HttpServletRequest request,AfterSalesRecord afterSalesRecord){

		int costType = 0;
		if(request.getParameter("costType")!=null){
			costType = Integer.valueOf(request.getParameter("costType"));
		}
		ModelAndView mav = new ModelAndView("aftersales/order/add_afterSales_th_cost");
//		List<CostType> costTypes = afterSalesOrderService.getCostTypeById(costType);
		List<SysOptionDefinition> costTypes = new ArrayList<>();
		if(costType == 718){
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_718);			
		}else if (costType == 719) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_719);
		}else if (costType == 720) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_720);
		}else if (costType == 721) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_721);
		}else if (costType == 722) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_722);
		}else if (costType == 723) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_723);
		}else {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_724);
		}
		mav.addObject("costTypes",costTypes);
		mav.addObject("afterSalesRecord", afterSalesRecord);
		return mav;
	}
	 
	/**
	 * 
	 * <b>Description:</b>查询订单的费用类型以及费用列表<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 09:36
	 */
	@ResponseBody
	@RequestMapping(value="/getAfterSalesCostListById")
	public ModelAndView getAfterSalesCostListById(HttpServletRequest request,AfterSalesCost afterSalesCost){
		ModelAndView mav = new ModelAndView("aftersales/order/view_afterSales_th_shwc");
		List<AfterSalesCostVo> costList = afterSalesOrderService.getAfterSalesCostListById(afterSalesCost);
		for(AfterSalesCostVo cost:costList){
			cost.setCostTypeName(getSysOptionDefinition(cost.getAfterSalesCostId()).getComments());
		}
		mav.addObject("costList",costList);
		return mav;
	}
	
	/**
	 * 
	 * <b>Description:</b>售后增加费用类型<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 16:08
	 */
	@ResponseBody
	@RequestMapping(value="/addAfterSalesCost")
	public ResultInfo addAfterSalesCost(HttpServletRequest request,AfterSalesCost afterSalesCost){

		Date date = new Date();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesCost.setAddTime(date.getTime());
		afterSalesCost.setModTime(date.getTime());
		afterSalesCost.setCreator(user.getUserId());
		afterSalesCost.setUpdater(user.getUserId());
		afterSalesCost.setIsEnable(1);
		ResultInfo<?> resultInfo =  afterSalesOrderService.addAfterSalesCost(afterSalesCost);
		return resultInfo;
	}
	
	/**
	 * 
	 * <b>Description:</b>售后编辑费用类型回显<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 16:11
	 */
	@ResponseBody
	@RequestMapping(value="/getCostTypeById")
	public ModelAndView getCostTypeById(HttpServletRequest request,AfterSalesCost afterSalesCost){
		
		int costType = 0;
		if(request.getParameter("costType")!=null){
			costType = Integer.valueOf(request.getParameter("costType"));
		}
		ModelAndView mav = new ModelAndView("aftersales/order/edit_afterSales_costType");
		List<SysOptionDefinition> costTypes = new ArrayList<>();
		if(costType == 718){
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_718);			
		}else if (costType == 719) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_719);
		}else if (costType == 720) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_720);
		}else if (costType == 721) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_721);
		}else if (costType == 722) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_722);
		}else if (costType == 723) {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_723);
		}else {
			costTypes = getSysOptionDefinitionList(SysOptionConstant.ID_724);
		}
		mav.addObject("costTypes",costTypes);
		
		AfterSalesCostVo costVo = afterSalesOrderService.getAfterSalesCostBycostId(afterSalesCost);
		costVo.setAfterSalesCostId(afterSalesCost.getAfterSalesCostId());
		mav.addObject("costVo",costVo);
		mav.addObject("AfterSalesCost", afterSalesCost);
		return mav;
	}
	
	/**
	 * 
	 * <b>Description:</b>售后编辑费用类型<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 16:55
	 */
	@ResponseBody
	@RequestMapping(value="/updateAfterSalesCost")
	public ResultInfo updateAfterSalesCost(HttpServletRequest request,AfterSalesCost afterSalesCost){

		Date date = new Date();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesCost.setModTime(date.getTime());
		afterSalesCost.setUpdater(user.getUserId());
		afterSalesCost.setIsEnable(1);
		ResultInfo<?> resultInfo =  afterSalesOrderService.updateAfterSalesCostById(afterSalesCost);
		return resultInfo;
	}
	
	/**
	 * 
	 * <b>Description:</b>售后删除费用类型<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 16:55
	 */
	@ResponseBody
	@RequestMapping(value="/delectAfterSalesCost")
	public ResultInfo delectAfterSalesCost(HttpServletRequest request,AfterSalesCost afterSalesCost){

		Date date = new Date();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesCost.setModTime(date.getTime());
		afterSalesCost.setUpdater(user.getUserId());
		afterSalesCost.setIsEnable(1);
		ResultInfo<?> resultInfo =  afterSalesOrderService.deleteAfterSalesCostById(afterSalesCost);
		return resultInfo;
	}
	

	/**
	 * 
	 * <b>Description: 售后选择订单关闭原因</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月31日 10:02
	 */
	@ResponseBody
	@RequestMapping(value="/causeOfAfterSales")
	public ModelAndView causeOfAfterSales(HttpServletRequest request,AfterSalesRecord afterSalesRecord){

		int afterSalesType = 0;
		if(request.getParameter("afterSalesType")!=null){
			afterSalesType = Integer.valueOf(request.getParameter("afterSalesType"));
		}
		
		ModelAndView mav = new ModelAndView("aftersales/order/cause_afterSales");
		List<SysOptionDefinition> afterSalesTypes = new ArrayList<>();
		if(afterSalesType == 756){
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_756);			
		}else if (afterSalesType == 757) {
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_757);
		}else if (afterSalesType == 758) {
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_758);
		}else if (afterSalesType == 759) {
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_759);
		}else{
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_760);
		}
		mav.addObject("afterSalesTypes",afterSalesTypes);
		mav.addObject("type",request.getParameter("type"));
//		mav.addObject("sku",request.getParameter("sku"));
		mav.addObject("subjectType",request.getParameter("subjectType"));
		mav.addObject("orderId",request.getParameter("orderId"));
		mav.addObject("traderId",request.getParameter("traderId"));
		mav.addObject("formToken",request.getParameter("formToken"));
		mav.addObject("afterSalesRecord", afterSalesRecord);
		return mav;
	}
	
	/**
	 * 
	 * <b>Description:售后选择订单完成原因</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月31日 16:50
	 */
	@ResponseBody
	@RequestMapping(value="/saleorderComplete")
	public ModelAndView saleorderComplete(HttpServletRequest request,AfterSalesRecord afterSalesRecord){

		int afterSalesType = 0;
		if(request.getParameter("afterSalesType")!=null){
			afterSalesType = Integer.valueOf(request.getParameter("afterSalesType"));
		}
		ModelAndView mav = new ModelAndView("aftersales/order/saleorderComplete");
		List<SysOptionDefinition> afterSalesTypes = new ArrayList<>();
		if(afterSalesType == 756){
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_756);			
		}else if (afterSalesType == 757) {
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_757);
		}else if (afterSalesType == 758) {
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_758);
		}else if (afterSalesType == 759) {
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_759);
		}else{
			afterSalesTypes = getSysOptionDefinitionList(SysOptionConstant.ID_760);
		}
		if(null != request.getParameter("sku") && !("".equals(request.getParameter("sku")))){
			mav.addObject("sku", request.getParameter("sku"));
		}
		mav.addObject("afterSalesTypes",afterSalesTypes);
		mav.addObject("type",request.getParameter("type"));
		mav.addObject("subjectType",request.getParameter("subjectType"));
		mav.addObject("orderId",request.getParameter("orderId"));
		mav.addObject("traderId",request.getParameter("traderId"));
		mav.addObject("formToken",request.getParameter("formToken"));
		mav.addObject("afterSalesRecord", afterSalesRecord);
		return mav;
	}

	/**
	 * <b>Description:</b><br> 搜索售后对象页面
	 * @param request
	 
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:16:59
	 */
	@ResponseBody
	@RequestMapping(value="/searchAfterTraderPage")
	public ModelAndView searchAfterTraderPage(HttpServletRequest request,AfterSalesTrader afterSalesTrader){
		//User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView("aftersales/order/search_aftersale_trader");
		mav.addObject("ast", afterSalesTrader);
		return mav;
	}

	/**
	 * <b>Description:</b><br> 新增售后对象
	 * @param request
	 
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:16:59
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="/addAfterTraderPage")
	public ModelAndView addAfterTraderPage(HttpServletRequest request,AfterSalesTrader afterSalesTrader){
		//User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView("aftersales/order/add_aftersale_trader");
		if(afterSalesTrader.getRealTraderType() == 1){
			TraderCustomer traderCustomer = new TraderCustomer();
			traderCustomer.setTraderId(afterSalesTrader.getTraderId());
			TraderCustomerVo traderCustomerVo = traderCustomerService.getTraderCustomerVo(traderCustomer);
			afterSalesTrader.setTraderName(traderCustomerVo.getName());
		}else{
			TraderSupplier traderSupplier = new TraderSupplier();
			traderSupplier.setTraderId(afterSalesTrader.getTraderId());
			TraderSupplierVo sup = traderSupplierService.getTraderSupplierInfo(traderSupplier);
			afterSalesTrader.setTraderName(sup.getTraderSupplierName());
		}
		mav.addObject("ast", afterSalesTrader);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增售后对象
	 * @param request
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:26:10
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveAddAfterTrader")
	@SystemControllerLog(operationType = "add",desc = "保存新增售后对象")
	public ResultInfo<?> saveAddAfterTrader(HttpServletRequest request,AfterSalesTrader afterSalesTrader){
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesTrader.setIsEnable(1);
		afterSalesTrader.setAddTime(DateUtil.sysTimeMillis());
		afterSalesTrader.setCreator(user.getUserId());
		afterSalesTrader.setModTime(DateUtil.sysTimeMillis());
		afterSalesTrader.setUpdater(user.getUserId());
		ResultInfo<?> resultInfo =  afterSalesOrderService.saveOrUpdateAfterTradder(afterSalesTrader);
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 编辑售后对象
	 * @param request
	 
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:16:59
	 */
	@ResponseBody
	@RequestMapping(value="/editAfterTraderPage")
	public ModelAndView editAfterTraderPage(HttpServletRequest request,AfterSalesTrader afterSalesTrader){
		//User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView("aftersales/order/edit_aftersale_trader");
		afterSalesTrader = afterSalesOrderService.getAfterSalesTrader(afterSalesTrader);
		mav.addObject("ast", afterSalesTrader);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑售后对象
	 * @param request
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:26:10
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditAfterTrader")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑售后对象")
	public ResultInfo<?> saveEditAfterTrader(HttpServletRequest request,AfterSalesTrader afterSalesTrader){
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesTrader.setModTime(DateUtil.sysTimeMillis());
		afterSalesTrader.setUpdater(user.getUserId());
		ResultInfo<?> resultInfo =  afterSalesOrderService.saveOrUpdateAfterTradder(afterSalesTrader);
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 删除售后对象
	 * @param request
	 * @param afterSalesTrader
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:26:10
	 */
	@ResponseBody
	@RequestMapping(value="/delAfterTrader")
	@SystemControllerLog(operationType = "edit",desc = "删除售后对象")
	public ResultInfo<?> delAfterTrader(HttpServletRequest request,AfterSalesTrader afterSalesTrader){
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		afterSalesTrader.setIsEnable(0);
		afterSalesTrader.setModTime(DateUtil.sysTimeMillis());
		afterSalesTrader.setUpdater(user.getUserId());
		ResultInfo<?> resultInfo =  afterSalesOrderService.saveOrUpdateAfterTradder(afterSalesTrader);
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 搜索售后对象
	 * @param request
	 
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月31日 下午1:16:59
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getAfterTraderListPage")
	public ModelAndView getAfterTraderListPage(HttpServletRequest request,AfterSalesTrader afterSalesTrader,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize){
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView("aftersales/order/search_aftersale_trader");
		Page page = getPageTag(request,pageNo,10);
		if(afterSalesTrader.getRealTraderType() == 1){
			TraderCustomerVo tcv = new TraderCustomerVo();
			tcv.setCompanyId(user.getCompanyId());
			tcv.setName(afterSalesTrader.getTraderName());
			Map<String, Object> map = traderCustomerService.getTraderCustomerVoPage(tcv, page,null);
			List<TraderCustomerVo> list = (List<TraderCustomerVo>) map.get("list");
			page = (Page) map.get("page");
			mav.addObject("cuslist", list);
			mav.addObject("page", page);
		}else{
			TraderSupplierVo traderSupplierVo = new TraderSupplierVo();
			traderSupplierVo.setCompanyId(user.getCompanyId());
			traderSupplierVo.setTraderSupplierName(afterSalesTrader.getTraderName());
			traderSupplierVo.setIsEnable(1);
			traderSupplierVo.setRequestType("cg");//采购搜索供应商用，其他地方可以不用
			// 查询所有职位类型为311的员工
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_311);// 采购
			List<User> userList = userService.getMyUserList(user, positionType, false);
			Map<String, Object> map = this.traderSupplierService.getSupplierByName(traderSupplierVo, page, userList);
			List<TraderSupplierVo> list = null;
			if (map != null) {
				list = (List<TraderSupplierVo>) map.get("list");
				page = (Page) map.get("page");
			}
			mav.addObject("suplist", list);
			mav.addObject("page", page);
		}
		mav.addObject("ast", afterSalesTrader);
		return mav;
	}
	
	/**
	 * <b>Description: 获取当前售后人员的通话记录列表（最近15天内）</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月08日 14:45
	 */
	@ResponseBody
	@RequestMapping(value = "/getCommunicateRecordList")
	public ModelAndView getRecord(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		
		ModelAndView mav = new ModelAndView("aftersales/order/afterSalesCallRecordList");
		CommunicateRecord communicateRecord = new CommunicateRecord();//调用方法待用参数
		List<Integer> userIds = new ArrayList<>();
		userIds.add(((User) request.getSession().getAttribute(Consts.SESSION_USER)).getUserId());
		communicateRecord.setUserIds(userIds);//设置要查的userId
		/** 设置要查询的时间范围 (最近15天内的) **/
		Date today = new Date();
		communicateRecord.setEndtime(today.getTime());//设置结束时间
		Long preDate = DateUtil.getDateBefore(today, 15);//获取15天前的时间
		communicateRecord.setBegintime(preDate);//设置开始时间（15天前）
		/** --END-- **/
		Page page = getPageTag(request, pageNo, pageSize);
		List<CommunicateRecord> recordList = callService.queryRecordlistPage(communicateRecord, page, session);
		mav.addObject("afterSalesId", request.getParameter("afterSalesId"));
		mav.addObject("traderId", request.getParameter("traderId"));
		mav.addObject("traderType", request.getParameter("traderType"));
		mav.addObject("page", page);
		mav.addObject("recordList", recordList);
		return mav;
	}
	
	/**
	 * 
	 * <b>Description: 获取录音的URI</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月10日 09:57
	 */
	@ResponseBody
	@RequestMapping(value="/getRecordCoidURI")
	public ResultInfo<?> getRecordCoidURI(HttpServletRequest request){
		int communicateRecordId = Integer.valueOf(request.getParameter("recordId"));
		return callService.getRecordCoidURIByCommunicateRecordId(communicateRecordId);
	}
	
	/**
	 * 
	 * <b>Description:售后订单新增沟通记录（修改自@RequestMapping(value = "addCommunicatePage")方法）</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月21日 08:52
	 */
	@SuppressWarnings("unchecked")
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "addNewCommunicatePage")
	public ModelAndView addNewCommunicatePage(AfterSales afterSales, TraderVo trader,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("aftersales/order/add_new_communicate");
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(trader.getTraderId());
		traderContact.setIsEnable(1);
		traderContact.setTraderType(trader.getTraderType());
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

		mav.addObject("trader", trader);
		mav.addObject("afterSales", afterSales);
		mav.addObject("contactList", contactList);
		
		CommunicateRecord communicate = new CommunicateRecord();
		communicate.setBegintime(DateUtil.sysTimeMillis());
		communicate.setEndtime(DateUtil.sysTimeMillis()+2*60*1000);
		mav.addObject("communicateRecord", communicate);

		// 沟通方式
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);
		
		/*** 2018-08-08 新增沟通记录页面增加录音Id ***/
		//获取售后对象列表
		List<AfterSalesTrader> afterSalesTraders = afterSalesOrderService.getAfterSalesTraderList(Integer.valueOf(request.getParameter("afterSalesId")));
		mav.addObject("afterSalesTraders",afterSalesTraders);
		//获取录音Id
		mav.addObject("communicateRecordId",request.getParameter("communicateRecordId"));
		/*** END ***/
		
		mav.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mav.addObject("page", (Page) tagMap.get("page"));
		mav.addObject("traderType",trader.getTraderType());
		return mav;
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "getConnectList")
	public ResultInfo<?> getConnectList(HttpServletRequest request) {
		if (Integer.valueOf(request.getParameter("traderId")) == 0) {
			ResultInfo resultInfo = new ResultInfo(-1,"请选择联系人");
			return resultInfo;
		}
		TraderContactVo traderContactVo=new TraderContactVo();
		traderContactVo.setTraderId(Integer.valueOf(request.getParameter("traderId")));
		traderContactVo.setTraderType(ErpConst.ONE);//客户
		traderContactVo.setIsEnable(ErpConst.ONE);
		Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
		String tastr = (String) map.get("contact");
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
		List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
		if (list == null || list.isEmpty()) {
			ResultInfo resultInfo = new ResultInfo(-1,"查询结果为空");
			return resultInfo;
		}
		ResultInfo resultInfo = new ResultInfo(0,"查询成功",list);
		return resultInfo;
	}


}
