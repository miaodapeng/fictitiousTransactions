package com.vedeng.order.controller;

import com.alibaba.fastjson.JSON;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import com.common.constants.Contant;
import com.vedeng.authorization.model.*;
import com.vedeng.common.constant.*;
import com.vedeng.common.money.OrderMoneyUtil;
import com.vedeng.common.annotation.AntiOrderRepeat;
import com.vedeng.goods.model.*;
import com.vedeng.goods.service.*;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.service.*;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.stock.api.stock.dto.ActionStockDto;
import com.vedeng.system.dao.VerifiesInfoMapper;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.trader.model.*;
import com.vedeng.trader.model.vo.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.aftersales.service.WebAccountService;
import com.vedeng.authorization.dao.UserDetailMapper;
import com.vedeng.authorization.model.*;
import com.vedeng.common.annotation.AntiOrderRepeat;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.http.NewHttpClientUtils;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.money.OrderMoneyUtil;
import com.vedeng.common.page.Page;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.*;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.*;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.finance.service.InvoiceService;
import com.vedeng.finance.service.TraderAccountPeriodApplyService;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.goods.model.*;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.goods.service.*;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.model.*;
import com.vedeng.logistics.service.*;
import com.vedeng.order.dao.SaleorderMapper;
import com.vedeng.order.model.*;
import com.vedeng.order.model.vo.OrderData;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.ordergoods.service.OrdergoodsService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.model.vo.AdVo;
import com.vedeng.system.service.*;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.*;
import com.vedeng.trader.model.vo.*;
import com.vedeng.trader.service.TraderCustomerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Description:</b><br>
 * 订单管理功能
 *
 * @author leo.yang
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.controller <br>
 *       <b>ClassName:</b> SaleorderController <br>
 *       <b>Date:</b> 2017年6月27日 上午10:54:47
 */
@Controller
@RequestMapping("/order/saleorder")
public class SaleorderController extends BaseController {


	/**
	 * 记录日志
	 */
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SaleorderController.class);

	@Autowired // 自动装载
	protected ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired // 自动装载
	@Qualifier("historyService")
	protected HistoryService historyService;

	@Autowired
	@Qualifier("actionProcdefService")
	protected ActionProcdefService actionProcdefService;

	@Autowired
	@Qualifier("saleorderService")
	protected SaleorderService saleorderService;

	@Autowired
	@Qualifier("expressService")
	protected ExpressService expressService;

	@Autowired
	@Qualifier("quoteService")
	protected QuoteService quoteService;

	@Autowired
	@Qualifier("orgService")
	protected OrgService orgService;

	@Autowired
	@Qualifier("userService")
	protected UserService userService;

	@Autowired
	@Qualifier("traderCustomerService")
	protected TraderCustomerService traderCustomerService;// 客户-交易者

	@Autowired
	@Qualifier("goodsService")
	protected GoodsService goodsService;

	@Autowired
	private VgoodsService vGoodsService;

	@Autowired
	@Qualifier("regionService")
	protected RegionService regionService;

	@Autowired
	@Qualifier("tagService")
	protected TagService tagService;

	@Autowired
	@Qualifier("logisticsService")
	protected LogisticsService logisticsService;

	@Autowired
	@Qualifier("ftpUtilService")
	protected FtpUtilService ftpUtilService;

	@Autowired
	@Qualifier("invoiceService")
	protected InvoiceService invoiceService;// 自动注入invoiceService

	@Resource
	protected AfterSalesService afterSalesOrderService;

	@Autowired
	@Qualifier("capitalBillService")
	protected CapitalBillService capitalBillService;

	@Autowired
	@Qualifier("accountPeriodService")
	protected TraderAccountPeriodApplyService accountPeriodService;

	@Autowired
	@Qualifier("warehouseOutService")
	protected WarehouseOutService warehouseOutService;

	@Autowired
	@Qualifier("verifiesRecordService")
	protected VerifiesRecordService verifiesRecordService;

	@Autowired
	@Qualifier("userDetailMapper")
	protected UserDetailMapper userDetailMapper;

	@Autowired
	@Qualifier("goodsChannelPriceService")
	protected GoodsChannelPriceService goodsChannelPriceService;

	@Autowired
	@Qualifier("goodsSettlementPriceService")
	protected GoodsSettlementPriceService goodsSettlementPriceService;

	@Autowired
	@Qualifier("vedengSoapService")
	protected VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("companyService")
	protected CompanyService companyService;

	@Autowired
	@Qualifier("categoryService")
	protected CategoryService categoryService;

	@Resource
	protected CategoryAttributeService categoryAttributeService;

	@Resource
	protected ReadService readService;

	@Resource
	protected AdService adService;

	@Autowired
	@Qualifier("roleService")
	protected RoleService roleService;

	@Autowired
	@Qualifier("paramsConfigValueService")
	protected ParamsConfigValueService paramsConfigValueService;

	@Autowired
	@Qualifier("hcSaleorderService")
	protected HcSaleorderService hcSaleorderService;

	@Value("${api_url}")
	protected String apiUrl;

	@Autowired
	@Qualifier("warehouseInService")
	private WarehouseInService warehouseInService;

	@Autowired
	@Qualifier("webAccountService")
	private WebAccountService webAccountService;


	@Autowired
	@Qualifier("ordergoodsService")
	private OrdergoodsService ordergoodsService;

	@Autowired
	@Qualifier("goodsMapper")
	private  GoodsMapper  goodsMapper;

	@Autowired
	@Qualifier("warehouseStockService")
    protected WarehouseStockService warehouseStockService;

	@Autowired
	@Qualifier("saleorderMapper")
	private SaleorderMapper saleorderMapper;

	@Autowired
	private SaleorderGoodsMapper saleorderGoodsMapper;

	@Autowired
	@Qualifier("webAccountMapper")
	protected WebAccountMapper webAccountMapper;

	@Value("${hc.order.ownerids}")
	private String ownerIds;


    @Autowired
	VerifiesInfoMapper verifiesInfoMapper;
	@ResponseBody
	@RequestMapping(value = "/syncNewTraderName")
	public ResultInfo syncNewTraderName(HttpServletRequest request,Saleorder saleorder){
		if(saleorder==null
				||ObjectUtils.isEmpty(saleorder.getSaleorderId())
				||ObjectUtils.isEmpty(saleorder.getTraderId())){
			return new ResultInfo(-1,"请求参数信息不正确，无法同步");
		}
		Trader trader=traderCustomerService.getBaseTraderByTraderId(saleorder.getTraderId());
		if(trader==null||StringUtil.isBlank(trader.getTraderName())){
			return new ResultInfo(-1,"该客户不存在，无法同步");
		}
		String traderName=trader.getTraderName();
		Saleorder saleorderInfo=new Saleorder();
		saleorderInfo.setSaleorderId(saleorder.getSaleorderId());
		saleorderInfo.setTraderName(traderName);
		saleorderInfo.setInvoiceTraderName(traderName);
		saleorderInfo.setTakeTraderName(traderName);
		saleorderService.saveEditSaleorderInfo(saleorderInfo);
		return new ResultInfo(0,"同步名称成功");
	}
	/**
	 * <b>Description:</b><br>
	 * 订单列表
	 *
	 * @param request
	 * @param saleorder
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年6月27日 下午4:02:35
	 */
	@ResponseBody
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, Saleorder saleorder, HttpSession session,
							  TraderCustomer traderCustomer, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
							  @RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		//获取订单来源
		Integer orderType = saleorder.getOrderType();
		if(orderType==null) {
			saleorder.setOrderType(-1);
		}
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
		List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
		mv.addObject("orgList", orgList);

		saleorder.setCompanyId(user.getCompanyId());
		saleorder.setUserId(user.getUserId());
		mv.addObject("loginUser", user);
		Integer position = 0;
		if (user.getPositions() != null) {
			position = user.getPositions().get(0).getType();
		}
		mv.addObject("position", position);
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList", userList);// 操作人员

		List<User> saleUserList = new ArrayList<>();
		if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
			User saleUser = new User();
			saleUser.setUserId(saleorder.getOptUserId());
			saleUserList.add(saleUser);
			saleorder.setSaleUserList(saleUserList);
		} else if (null != user.getPositType() && user.getPositType() == 310) {// 销售
			saleorder.setSaleUserList(userList);
		}
		if(CollectionUtils.isEmpty(saleorder.getSaleUserList()) ){
			if(CollectionUtils.isNotEmpty(user.getPositions())){
				for(Position positiont:user.getPositions()){
					if(SysOptionConstant.ID_310.equals(positiont.getPositionId())){
						saleorder.setSaleUserList(userList);
						break;
					}
				}
			}
		}
		saleorder.setOptType("orderIndex");
		// 运营对合同审核的人员进入列表页后，默认加载合同待审核的列表，且生效时间>=2018.01.01
		if (399 == user.getUserId()) {
			if (saleorder.getContractStatus() == null) {
				saleorder.setContractStatus(3);
			}
			if ((null == request.getParameter("searchBegintimeStr") || request.getParameter("searchBegintimeStr") == "")
					&& saleorder.getSearchDateType() == null) {
				saleorder.setSearchBegintime(DateUtil.convertLong("2018-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				saleorder.setSearchDateType(2);
			}
		}

		//查询当前操作员角色是否是 产品专员,产品主管,产品总监中的一个
		List<Integer> roleIdLists = Arrays.asList(new Integer[]{7,8,9});
		List<Integer> userRoleIdLists = user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toList());

		userRoleIdLists.retainAll(roleIdLists);

		if(CollectionUtils.isNotEmpty(userRoleIdLists)){
			mv.addObject("hasProductRole", true);
			//所有的分配人
			mv.addObject("assUser", userService.selectAllAssignUser());

			//如果前台没有传值，默认就是当前登录人
			saleorder.setProductBelongUserId(saleorder.getProductBelongUserId()!= null ? saleorder.getProductBelongUserId() :user.getUserId());

		}else{
			mv.addObject("hasProductRole", false);
		}


		if(saleorder.getIsReferenceCostPrice() == null && position == 311){
			//默认选中待填写
			saleorder.setIsReferenceCostPrice(0);
		}

		Map<String, Object> map = saleorderService.getSaleorderListPage(request, saleorder, page,0);//0为销售订单,1为线上订单

		List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");


		// SysOptionDefinition sod = null;
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
			// list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));
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
			// 审核人
			if (null != list.get(i).getVerifyUsername()) {
				List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));
				list.get(i).setVerifyUsernameList(verifyUsernameList);
			}
			// 未添加产品成本人员
			if (null != list.get(i).getCostUserIds()) {
				List<String> costUserList = Arrays.asList(list.get(i).getCostUserIds().split(","));
				list.get(i).setCostUserIdsList(costUserList);
			}

			// 可否开票判断
			Saleorder invoiceSaleorder = new Saleorder();
			invoiceSaleorder.setSaleorderId(list.get(i).getSaleorderId());
			invoiceSaleorder.setOptType("orderIndex");
			invoiceSaleorder = saleorderService.getBaseSaleorderInfo(invoiceSaleorder);
			list.get(i).setIsOpenInvoice(invoiceSaleorder.getIsOpenInvoice());

			Map<String,BigDecimal> moneyData=saleorderService.getSaleorderDataInfo(list.get(i).getSaleorderId());
			if(moneyData!=null){
				if(moneyData.get("realAmount")!=null){
					list.get(i).setRealAmount(moneyData.get("realAmount"));

				}

				if(moneyData.get("paymentAmount")!=null){
					list.get(i).setPaymentAmount(OrderMoneyUtil.getPaymentAmount(moneyData));
				}
			}
		}
		// 根据销售订单ID查询账期付款总额-订单未还账期款---换成Jerry写的方法
		List<SaleorderData> capitalBillList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);


		// add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. start
        if(CollectionUtils.isNotEmpty(capitalBillList)){
            capitalBillList.stream().forEach(order -> {
                try{
                    if (null != order.getSaleorderId()){
                        BigDecimal periodAmount = saleorderService.getPeriodAmount(order.getSaleorderId());
                        BigDecimal lackAccountPeriodAmount = saleorderService.getLackAccountPeriodAmount(order.getSaleorderId());
                        BigDecimal refundBalanceAmount = afterSalesOrderService.getRefundBalanceAmountBySaleorderId(order.getSaleorderId());
                        order.setPeriodAmount(periodAmount == null ? BigDecimal.ZERO : periodAmount);
                        order.setLackAccountPeriodAmount(lackAccountPeriodAmount == null ? BigDecimal.ZERO : lackAccountPeriodAmount);
                        order.setRefundBalanceAmount(refundBalanceAmount == null ? BigDecimal.ZERO : refundBalanceAmount);
                    }
                }catch (Exception e){
                    log.info("订单实际金额运算出现异常: {}",e);
                }
            });
        }

        mv.addObject("capitalBillList", capitalBillList);
        // add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 每笔订单做实际金额运算. end


		// 客户信息里面的交易记录
		if (null != saleorder.getTraderId() && saleorder.getTraderId() > 0) {
			mv.addObject("method", "saleorder");
			mv.addObject("traderId", saleorder.getTraderId());
		}

		mv.addObject("saleorderList", list);

		if(!CollectionUtils.isEmpty(list) &&  (saleorder.getIsReferenceCostPrice() == null || saleorder.getIsReferenceCostPrice() == 0 || saleorder.getIsReferenceCostPrice() == -1)){


			User currUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);

			Set<Integer> userids = new HashSet<>();

			for(Saleorder saleorderItem : list){

				if(saleorderItem.getPaymentStatus() != 2 || saleorderItem.getStatus() == 3){
					continue;
				}

				List<SaleorderGoods> saleorderGoods = this.saleorderGoodsMapper.getSaleorderGoodsBySaleorderId(saleorderItem.getSaleorderId());

				if(CollectionUtils.isEmpty(saleorderGoods)){
					continue;
				}

				userids.clear();

				saleorderGoods.stream().forEach(saleOrderGood->{
					if(saleOrderGood.getIsDelete() == 0 && saleOrderGood.getReferenceCostPrice().compareTo(BigDecimal.ZERO) == 0){
						CoreSpuGenerate spuGenerate = saleorderService.getSpuBase(saleOrderGood.getGoodsId());
						if(spuGenerate != null){
							userids.add(spuGenerate.getAssignmentAssistantId());
							userids.add(spuGenerate.getAssignmentManagerId());
						}
					}
				});

				if(userids.contains(currUser.getUserId())){
					saleorderItem.setLoginUserBelongToProductManager(1);
				}
			}

		}

		mv.addObject("salerorderIndex", 1);//订单列表标签标识
		mv.addObject("page", map.get("page"));
		mv.addObject("total_amount", new BigDecimal(map.get("total_amount").toString()));
		mv.addObject("customerNatures", customerNatures);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/index");
		return mv;
	}

	/**
	 * @Title: indexOnline
	 * @Description: TODO(线上订单列表)
	 * @param @param request
	 * @param @param saleorder
	 * @param @param session
	 * @param @param traderCustomer
	 * @param @param pageNo
	 * @param @param pageSize
	 * @param @return    参数
	 * @return ModelAndView    返回类型
	 * @author strange
	 * @throws
	 * @date 2019年8月5日
	 */
	@ResponseBody
	@RequestMapping(value = "indexOnline")
	public ModelAndView indexOnline(HttpServletRequest request, Saleorder saleorder, HttpSession session,
									TraderCustomer traderCustomer, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
									@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		//获取订单来源
		Integer orderType = saleorder.getOrderType();
		if(orderType==null) {
			saleorder.setOrderType(-1);
		}
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
		List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310, user.getCompanyId());
		mv.addObject("orgList", orgList);

		saleorder.setCompanyId(user.getCompanyId());
		saleorder.setUserId(user.getUserId());
		mv.addObject("loginUser", user);
		Integer position = 0;
		if (user.getPositions() != null) {
			position = user.getPositions().get(0).getType();
		}
		mv.addObject("position", position);
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList", userList);// 操作人员

		List<User> saleUserList = new ArrayList<>();
		if (null != saleorder.getOptUserId() && saleorder.getOptUserId() != -1) {
			User saleUser = new User();
			saleUser.setUserId(saleorder.getOptUserId());
			saleUserList.add(saleUser);
			saleorder.setSaleUserList(saleUserList);
		} else if (null != user.getPositType() && user.getPositType() == 310) {// 销售
			saleorder.setSaleUserList(userList);
		}

		saleorder.setOptType("orderIndex");
		// 运营对合同审核的人员进入列表页后，默认加载合同待审核的列表，且生效时间>=2018.01.01
		if (399 == user.getUserId()) {
			if (saleorder.getContractStatus() == null) {
				saleorder.setContractStatus(3);
			}
			if ((null == request.getParameter("searchBegintimeStr") || request.getParameter("searchBegintimeStr") == "")
					&& saleorder.getSearchDateType() == null) {
				saleorder.setSearchBegintime(DateUtil.convertLong("2018-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				saleorder.setSearchDateType(2);
			}
		}
		Map<String, Object> map = saleorderService.getSaleorderListPage(request, saleorder, page,1);//1为线上订单,0为销售订单
		mv.addObject("salerorderOnline", 1);
		List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");

		// SysOptionDefinition sod = null;
		CommunicateRecord cr = null;
		List<Integer> saleorderIdList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			// 销售人员
			user = userService.getUserById(list.get(i).getUserId());
			list.get(i).setSalesName(user == null ? "" : user.getUsername());
			// 归属销售
			list.get(i).setOptUserName(user == null ? "" : user.getUsername());
			if(list.get(i).getTraderId()!=null && list.get(i).getTraderId()>0) {
				user = orgService.getTraderUserAndOrgByTraderId(list.get(i).getTraderId(), 1);// 1客户，2供应商
				list.get(i).setSalesDeptName(user == null ? "" : user.getOrgName());
				list.get(i).setOptUserName(user == null ? "" : user.getUsername());
			}else {
				Organization org = orgService.getOrgNameByUserId(list.get(i).getUserId());
				list.get(i).setSalesDeptName(org == null ? "" : org.getOrgName());
			}
			// 销售部门
			// list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));
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
			// 审核人
			if (null != list.get(i).getVerifyUsername()) {
				List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));
				list.get(i).setVerifyUsernameList(verifyUsernameList);
			}
			// 未添加产品成本人员
			if (null != list.get(i).getCostUserIds()) {
				List<String> costUserList = Arrays.asList(list.get(i).getCostUserIds().split(","));
				list.get(i).setCostUserIdsList(costUserList);
			}

			// 可否开票判断
			Saleorder invoiceSaleorder = new Saleorder();
			invoiceSaleorder.setSaleorderId(list.get(i).getSaleorderId());
			invoiceSaleorder.setOptType("orderIndex");
			invoiceSaleorder = saleorderService.getBaseSaleorderInfo(invoiceSaleorder);
			list.get(i).setIsOpenInvoice(invoiceSaleorder.getIsOpenInvoice());
//			//BD订单总价
//			if(list.get(i).getOnlineFlag()) {
//				list.get(i).setTotalAmount(null);
//			}
		}
		// 根据销售订单ID查询账期付款总额-订单未还账期款---换成Jerry写的方法
		List<SaleorderData> capitalBillList = capitalBillService.getCapitalListBySaleorderId(saleorderIdList);
		mv.addObject("capitalBillList", capitalBillList);

		// 客户信息里面的交易记录
		if (null != saleorder.getTraderId() && saleorder.getTraderId() > 0) {
			mv.addObject("method", "saleorder");
			mv.addObject("traderId", saleorder.getTraderId());
		}

		mv.addObject("saleorderList", list);
		mv.addObject("page", map.get("page"));
		mv.addObject("total_amount", new BigDecimal(map.get("total_amount").toString()));
		mv.addObject("customerNatures", customerNatures);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 订货系统订单详情查看---标签一：订单及基本信息
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2017年7月5日 下午1:33:18
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "viewNew")
	public ModelAndView viewNew(HttpServletRequest request, Saleorder saleorder) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("curr_user", curr_user);
		// Integer saleorderId = saleorder.getSaleorderId();
		List<SaleorderGoods> saleorderGoodsList = null;
		try {
			// 获取订单基本信息
			saleorder.setOptType("orderDetail");
			saleorder = saleorderService.getBaseSaleorderInfoNew(saleorder);
			saleorder.setCompanyId(curr_user.getCompanyId());
			TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
			mv.addObject("customer", customer);
			User user = null;
			// 销售人员名称
			user = userService.getUserById(saleorder.getUserId());
			saleorder.setSalesName(user == null ? "" : user.getUsername());
			// 创建人员
			user = userService.getUserById(saleorder.getCreator());
			saleorder.setCreatorName(user == null ? "" : user.getUsername());

			// 归属销售
			user = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
			saleorder.setOptUserName(user == null ? "" : user.getUsername());

			// 销售部门（若一个多个部门，默认取第一个部门）
			User userOrg = orgService.getTraderUserAndOrgByTraderId(saleorder.getTraderId(), 1);
			saleorder.setSalesDeptName(userOrg == null ? "" : userOrg.getOrgName());

			// 获取订单客户信息
			saleorder.setCustomerTypeStr(getSysOptionDefinition(saleorder.getCustomerType()).getTitle());
			// 客户性质
			saleorder.setCustomerNatureStr(getSysOptionDefinition(saleorder.getCustomerNature()).getTitle());
			// 终端类型
			saleorder.setTerminalTraderTypeStr(getSysOptionDefinition(saleorder.getTerminalTraderType()).getTitle());
			Map<String, Object> map = accountPeriodService.getTraderAptitudeInfo(saleorder.getTraderId());
			// 发票类型
			List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
			mv.addObject("invoiceTypes", invoiceTypes);

			if (map != null) {
				TraderCertificateVo bus = null;
				// 营业执照信息
				if (map.containsKey("business")) {
					JSONObject json = JSONObject.fromObject(map.get("business"));
					bus = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
					mv.addObject("business", bus);
				}
				// 税务登记信息
				TraderCertificateVo tax = null;
				if (map.containsKey("tax")) {
					JSONObject json = JSONObject.fromObject(map.get("tax"));
					tax = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
					mv.addObject("tax", tax);
				}
				// 组织机构信息
				TraderCertificateVo orga = null;
				if (map.containsKey("orga")) {
					JSONObject json = JSONObject.fromObject(map.get("orga"));
					orga = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
					mv.addObject("orga", orga);
				}
				// 客户
				mv.addObject("customerProperty", saleorder.getCustomerNature());
				if (saleorder.getCustomerNature().intValue() == 465) {// 分销
					// 二类医疗资质
					List<TraderCertificateVo> twoMedicalList = null;
					if (map.containsKey("twoMedical")) {
						// JSONObject json =
						// JSONObject.fromObject(map.get("twoMedical"));
						twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
						mv.addObject("twoMedicalList", twoMedicalList);
					}
					// 二类医疗资质详情
					List<TraderMedicalCategoryVo> list = null;
					if (map.containsKey("medicalCertificate")) {
						JSONArray jsonArray = JSONArray.fromObject(map.get("medicalCertificate"));
						list = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray,
								TraderMedicalCategoryVo.class);
						mv.addObject("medicalCertificates", list);
					}
					// 三类医疗资质
					TraderCertificateVo threeMedical = null;
					if (map.containsKey("threeMedical")) {
						JSONObject json = JSONObject.fromObject(map.get("threeMedical"));
						threeMedical = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("threeMedical", threeMedical);
					}
				} else {// 终端
					// 医疗机构执业许可证
					List<TraderCertificateVo> practiceList = null;
					if (map.containsKey("practice")) {
						// JSONObject
						// json=JSONObject.fromObject(map.get("practice"));
						practiceList = (List<TraderCertificateVo>) map.get("practice");
						mv.addObject("practiceList", practiceList);
					}
				}
			}
			// 付款方式列表
			List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
			mv.addObject("paymentTermList", paymentTermList);
			// 获取订单产品信息
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorder.getSaleorderId());
			sale.setTraderId(saleorder.getTraderId());
			sale.setCompanyId(curr_user.getCompanyId());
			saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
			// 所有产品的手填成本总价
			mv.addObject("totalReferenceCostPrice", sale.getTotalAmount());
			// 产品结算价
			saleorderGoodsList = goodsSettlementPriceService
					.getGoodsSettlePriceBySaleorderGoodsList(curr_user.getCompanyId(), saleorderGoodsList);
			// 计算核价信息
			saleorderGoodsList = goodsChannelPriceService.getSaleChannelPriceList(saleorder.getSalesAreaId(),
					saleorder.getCustomerNature(), customer.getOwnership(), saleorderGoodsList);
			// 根据分类获取产品负责人
			if (saleorderGoodsList != null && saleorderGoodsList.size() > 0) {
				List<Integer> categoryIdList = new ArrayList<>();
				for (int i = 0; i < saleorderGoodsList.size(); i++) {
					categoryIdList.add(saleorderGoodsList.get(i).getGoods().getCategoryId());
				}

				List<SaleorderGoods> my_saleGoodsList = new ArrayList<>();
				List<SaleorderGoods> they_saleGoodsList = new ArrayList<>();

				categoryIdList = new ArrayList<Integer>(new HashSet<Integer>(categoryIdList));
				List<User> categoryUserList = quoteService.getGoodsCategoryUserList(categoryIdList,
						sale.getCompanyId());
				if (categoryUserList != null && categoryUserList.size() > 0) {
					// user =
					// (User)request.getSession().getAttribute(Consts.SESSION_USER);
					mv.addObject("loginUserId", curr_user.getUserId() + ";");

					// 默认报价咨询人
					User contsultantUser = paramsConfigValueService.getQuoteConsultant(user.getCompanyId(), 107);
					if (contsultantUser == null) {
						contsultantUser = new User();
					}
					// mv.addObject("contsultantUserId",
					// contsultantUser.getUserId()+";");
					for (int i = 0; i < saleorderGoodsList.size(); i++) {
						for (int j = 0; j < categoryUserList.size(); j++) {
							if (categoryUserList.get(j).getCategoryId()
									.equals(saleorderGoodsList.get(i).getGoods().getCategoryId())) {
								saleorderGoodsList.get(i)
										.setGoodsUserIdStr((saleorderGoodsList.get(i).getGoodsUserIdStr() == null ? ";"
												: saleorderGoodsList.get(i).getGoodsUserIdStr())
												+ categoryUserList.get(j).getUserId() + ";");
							}
						}
						// 归属当前登录用户的商品排列在前
						if (saleorderGoodsList.get(i).getGoodsUserIdStr() != null && saleorderGoodsList.get(i)
								.getGoodsUserIdStr().contains(curr_user.getUserId() + ";")) {
							my_saleGoodsList.add(saleorderGoodsList.get(i));
						} else if (user.getUserId().equals(contsultantUser.getUserId())) {// 当前登录的用户是报价咨询默认人
							my_saleGoodsList.add(saleorderGoodsList.get(i));
						} else {
							they_saleGoodsList.add(saleorderGoodsList.get(i));
						}
					}
					my_saleGoodsList.addAll(they_saleGoodsList);
					saleorderGoodsList.clear();
					saleorderGoodsList.addAll(my_saleGoodsList);
				}
			}
			// 发货方式
			List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
			mv.addObject("deliveryTypes", deliveryTypes);
			// 获取物流公司列表
			List<Logistics> logisticsList = getLogisticsList(curr_user.getCompanyId());
			mv.addObject("logisticsList", logisticsList);
			// 运费说明
			List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
			mv.addObject("freightDescriptions", freightDescriptions);

		} catch (Exception e) {
			logger.error("sale order viewNew:", e);
		}
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/saleOrder_view_new");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 订货系统订单详情交易及物流信息---标签二：交易及物流信息
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2017年7月5日 下午1:33:18
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "getSaleAndExpress")
	public ModelAndView getSaleAndExpress(HttpServletRequest request, Saleorder saleorder) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("curr_user", curr_user);
		mv.addObject("companyId", curr_user.getCompanyId());
		// 开票申请
		List<InvoiceApply> saleInvoiceApplyList = invoiceService.getSaleInvoiceApplyList(saleorder.getSaleorderId(),
				SysOptionConstant.ID_505, -1);
		mv.addObject("saleInvoiceApplyList", saleInvoiceApplyList);
		// 根据客户ID查询客户信息
		TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
		mv.addObject("customer", customer);
		// 获取发票信息
		List<Invoice> saleInvoiceList = invoiceService.getInvoiceInfoByRelatedId(saleorder.getSaleorderId(),
				SysOptionConstant.ID_505);
		mv.addObject("saleInvoiceList", saleInvoiceList);
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorder.getSaleorderId());
		sale.setTraderId(saleorder.getTraderId());
		sale.setCompanyId(curr_user.getCompanyId());
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsByIdNew(sale);
		// 物流信息
		if (saleorderGoodsList.size() > 0) {
			Express express = new Express();
			express.setBusinessType(SysOptionConstant.ID_496);
			express.setCompanyId(curr_user.getCompanyId());
			List<Integer> relatedIds = new ArrayList<Integer>();
			for (SaleorderGoods sg : saleorderGoodsList) {
				relatedIds.add(sg.getSaleorderGoodsId());
			}
			express.setRelatedIds(relatedIds);
			try {
				List<Express> expressList = expressService.getExpressList(express);
				mv.addObject("expressList", expressList);
			} catch (Exception e) {
				logger.error("getSaleAndExpress:", e);
			}
		}

		// 出库记录
		saleorder.setBussinessType(2);
		// 出库记录清单
		List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
		List<String> timeArrayWl = new ArrayList<>();
		if (null != warehouseOutList) {
			for (WarehouseGoodsOperateLog wl : warehouseOutList) {
				User u = userService.getUserById(wl.getCreator());
				wl.setOpName(u.getUsername());
				timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
			}
			HashSet<String> wArray = new HashSet<String>(timeArrayWl);
			mv.addObject("timeArrayWl", wArray);
		}
		mv.addObject("warehouseOutList", warehouseOutList);
		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);
		// 获取交易信息数据
		CapitalBill capitalBill = new CapitalBill();
		capitalBill.setOperationType("finance_sale_detail");
		CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
		capitalBillDetail.setOrderType(ErpConst.ONE);// 销售订单类型
		capitalBillDetail.setOrderNo(saleorder.getSaleorderNo());
		capitalBillDetail.setRelatedId(saleorder.getSaleorderId());
		capitalBill.setCapitalBillDetail(capitalBillDetail);
		List<CapitalBill> capitalBillList = capitalBillService.getCapitalBillList(capitalBill);
		mv.addObject("capitalBillList", capitalBillList);
		// 资金流水交易方式
		List<SysOptionDefinition> traderModes = getSysOptionDefinitionList(519);
		mv.addObject("traderModes", traderModes);

		// 资金流水业务类型
		List<SysOptionDefinition> bussinessTypes = getSysOptionDefinitionList(524);
		mv.addObject("bussinessTypes", bussinessTypes);
		// 开票申请审核信息
		InvoiceApply invoiceApplyInfo = invoiceService.getInvoiceApplyByRelatedId(saleorder.getSaleorderId(),
				SysOptionConstant.ID_505, saleorder.getCompanyId());
		mv.addObject("invoiceApply", invoiceApplyInfo);
		if (invoiceApplyInfo != null && curr_user.getCompanyId() != 1) {
			Map<String, Object> historicInfoInvoice = actionProcdefService.getHistoric(processEngine,
					"invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId());
			mv.addObject("historicActivityInstanceInvoice", historicInfoInvoice.get("historicActivityInstance"));
			mv.addObject("commentMapInvoice", historicInfoInvoice.get("commentMap"));
			mv.addObject("startUserInvoice", historicInfoInvoice.get("startUser"));
			Task taskInfoInvoice = (Task) historicInfoInvoice.get("taskInfo");
			// 当前审核人
			String verifyUsersInvoice = null;
			if (null != taskInfoInvoice) {
				Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfoInvoice);
				verifyUsersInvoice = (String) taskInfoVariables.get("verifyUsers");
			}
			mv.addObject("verifyUsersInvoice", verifyUsersInvoice);
		}
		Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorder.getSaleorderId());
		mv.setViewName("order/saleorder/saleAndExpress");
		mv.addObject("saleorderDataInfo", saleorderDataInfo);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorder", saleorder);
		return mv;
	}

	/**
	 * 获取沟通及审核信息 <b>Description:</b><br>
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月25日 下午6:08:59
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "getContactAndCheckInfo")
	public ModelAndView getContactAndCheckInfo(HttpServletRequest request, Saleorder saleorder) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("curr_user", curr_user);
		Page page = Page.newBuilder(null, null, null);
		CommunicateRecord cr = new CommunicateRecord();
		if (saleorder.getQuoteorderId() != null && saleorder.getQuoteorderId() != 0) {
			cr.setQuoteorderId(saleorder.getQuoteorderId());
		}
		if (saleorder.getBussinessChanceId() != null && saleorder.getBussinessChanceId() != 0) {
			cr.setBussinessChanceId(saleorder.getBussinessChanceId());
		}
		cr.setSaleorderId(saleorder.getSaleorderId());
		cr.setCompanyId(curr_user.getCompanyId());
		List<CommunicateRecord> communicateList = traderCustomerService.getCommunicateRecordListPage(cr, page);
		if (!communicateList.isEmpty()) {
			// 沟通内容
			mv.addObject("communicateList", communicateList);
			mv.addObject("page", page);
		}
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorder.getSaleorderId());
		sale.setTraderId(saleorder.getTraderId());
		sale.setCompanyId(curr_user.getCompanyId());
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsByIdNew(sale);
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"saleorderVerify_" + saleorder.getSaleorderId());
		mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mv.addObject("taskInfo", historicInfo.get("taskInfo"));
		mv.addObject("startUser", historicInfo.get("startUser"));
		mv.addObject("endStatus", historicInfo.get("endStatus"));
		mv.addObject("commentMap", historicInfo.get("commentMap"));
		mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		// 当前审核人
		String verifyUsers = null;
		List<String> verifyUserList = new ArrayList<>();
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
			String verifyUser = (String) taskInfoVariables.get("verifyUserList");
			if (null != verifyUser) {
				verifyUserList = Arrays.asList(verifyUser.split(","));
			}
		}
		List<String> verifyUsersList = new ArrayList<>();
		if (verifyUsers != null && !verifyUserList.isEmpty()) {
			verifyUsersList = Arrays.asList(verifyUsers.split(","));
		}
		// 订单修改申请列表（不分页）
		SaleorderModifyApply saleorderModifyApply = new SaleorderModifyApply();
		saleorderModifyApply.setSaleorderId(saleorder.getSaleorderId());
		List<SaleorderModifyApply> saleorderModifyApplyList = saleorderService
				.getSaleorderModifyApplyList(saleorderModifyApply);
		Map<String, Object> historicInfoEarly = actionProcdefService.getHistoric(processEngine,
				"earlyBuyorderVerify_" + saleorder.getSaleorderId());
		Task taskInfoEarly = (Task) historicInfoEarly.get("taskInfo");
		// 当前审核人
		String verifyUsersEarly = null;
		List<String> verifyUserListEarly = new ArrayList<>();
		if (null != taskInfoEarly) {
			Map<String, Object> taskInfoVariablesEarly = actionProcdefService.getVariablesMap(taskInfoEarly);
			verifyUsersEarly = (String) taskInfoVariablesEarly.get("verifyUsers");
			String verifyUserEarly = (String) taskInfoVariablesEarly.get("verifyUserList");
			if (null != verifyUserEarly) {
				verifyUserListEarly = Arrays.asList(verifyUserEarly.split(","));
			}
		}
		List<String> verifyUsersListEarly = new ArrayList<>();
		if (verifyUsersEarly != null && !verifyUserListEarly.isEmpty()) {
			verifyUsersListEarly = Arrays.asList(verifyUsersEarly.split(","));
		}
		mv.addObject("commentMapEarly", historicInfoEarly.get("commentMap"));
		mv.addObject("verifyUsersEarly", verifyUsersEarly);
		mv.addObject("verifyUserListEarly", verifyUserListEarly);
		mv.addObject("verifyUsersListEarly", verifyUsersListEarly);
		mv.addObject("taskInfoEarly", historicInfoEarly.get("taskInfo"));
		mv.addObject("startUserEarly", historicInfoEarly.get("startUser"));
		mv.addObject("endStatusEarly", historicInfoEarly.get("endStatus"));
		mv.addObject("historicActivityInstanceEarly", historicInfoEarly.get("historicActivityInstance"));
		mv.addObject("saleorderModifyApplyList", saleorderModifyApplyList);
		mv.addObject("verifyUsers", verifyUsers);
		mv.addObject("verifyUserList", verifyUserList);
		mv.addObject("verifyUsersList", verifyUsersList);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/contactAndCheck");
		return mv;
	}

	/**
	 * 获取合同及售后信息 <b>Description:</b><br>
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月25日 下午6:08:59
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "getAttachmentAndInstance")
	public ModelAndView getAttachmentAndInstance(HttpServletRequest request, Saleorder saleorder) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("curr_user", curr_user);
		mv.addObject("saleorder", saleorder);
		// 获取订单合同回传&订单送货单回传列表
		List<Attachment> saleorderAttachmentList = saleorderService
				.getSaleorderAttachmentList(saleorder.getSaleorderId());
		mv.addObject("saleorderAttachmentList", saleorderAttachmentList);
		// 录保卡
		List<SaleorderGoodsWarrantyVo> goodsWarrantys = saleorderService
				.getSaleorderGoodsWarrantys(saleorder.getSaleorderId());
		mv.addObject("goodsWarrantys", goodsWarrantys);
		// 售后订单列表
		AfterSalesVo as = new AfterSalesVo();
		as.setOrderId(saleorder.getSaleorderId());
		as.setSubjectType(535);
		List<AfterSalesVo> asList = afterSalesOrderService.getAfterSalesVoListByOrderId(as);
		mv.addObject("asList", asList);
		mv.setViewName("order/saleorder/attachmentAndInstance");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单查看
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月5日 下午1:33:18
	 */
	@ResponseBody
	@RequestMapping(value = "view")
	@FormToken(save = true)
	public ModelAndView view(HttpServletRequest request, Saleorder saleorder) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		//是否是销售
		Boolean saleFlag = userService.getSaleFlagUserId(curr_user.getUserId());
		if(saleFlag){
			mv.addObject("roleStatus", 1);
		}
		mv.addObject("curr_user", curr_user);
		Integer saleorderId = saleorder.getSaleorderId();
		// 获取订单基本信息
		saleorder.setOptType("orderDetail");
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
		//------------------分批开票--------------------------------

		//若订单状态为已完成，但开票状态为“未开票”或“部分开票”，仍然显示“申请修改”的按钮，只有收票信息模块可修改，其他内容不可修改。
		Integer status = saleorder.getStatus();
		Integer invoiceStatus = saleorder.getInvoiceStatus();
		if(status.equals(2)&&invoiceStatus!=2){
			mv.addObject("invoiceModifyflag",1);
		}
		//------------------分批开票--------------------------------

        //VDERP-1374
        BigDecimal amount =new BigDecimal(0.00);
        BigDecimal totalAmount = saleorder.getTotalAmount();
        if(totalAmount!=null&&totalAmount.compareTo(amount)==0) {

            if(saleorder.getStatus().equals(1)&&saleorder.getPaymentStatus().equals(2)) {
                mv.addObject("amountFlag",1);
            }
        }
        //VDERP-1374

		Map<String,BigDecimal> moneyData=saleorderService.getSaleorderDataInfo(saleorderId);
		if(moneyData!=null&&moneyData.get("realAmount")!=null){
			mv.addObject("realAmount",moneyData.get("realAmount"));
		}
		try {
			//注册用户id
			Integer erpAccountId = webAccountService.getWebAccountIdByMobile(saleorder.getCreateMobile());
			mv.addObject("erpAccountId",erpAccountId);
			saleorder.setCompanyId(curr_user.getCompanyId());
			// 根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
			mv.addObject("customer", customer);
			//获取产品信息
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorderId);
			List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
			mv.addObject("saleorderGoodsList", saleorderGoodsList);
			//----------------------------------------报价咨询信息--------------------------------
			Quoteorder quoteInfo = quoteService.getQuoteInfoByKey(saleorder.getQuoteorderId());
			mv.addObject("quoteInfo", quoteInfo);
			//查询报价咨询记录
			List<QuoteorderConsult> consultList = quoteService.getQuoteConsultList(saleorder.getQuoteorderId());
			if(!consultList.isEmpty()){
				mv.addObject("consultList", consultList);
				List<Integer> userIds = new ArrayList<Integer>();
				for(QuoteorderConsult qc:consultList){
					userIds.add(qc.getCreator());
				}
				List<User> userList = userService.getUserByUserIds(userIds);
				mv.addObject("userList", userList);
			}
			//----------------------------------------报价咨询信息--------------------------------
			// 产品结算价
			saleorderGoodsList = goodsSettlementPriceService
					.getGoodsSettlePriceBySaleorderGoodsList(curr_user.getCompanyId(), saleorderGoodsList);
			// 计算核价信息
			if(customer!=null) {
				saleorderGoodsList = goodsChannelPriceService.getSaleChannelPriceList(saleorder.getSalesAreaId(),
						saleorder.getCustomerNature(), customer.getOwnership(), saleorderGoodsList);
			}


			//添加产品的成本价
			goodsChannelPriceService.setGoodsReferenceCostPrice(saleorderGoodsList);

			mv.addObject("saleorderGoodsList", saleorderGoodsList);

			List<SaleorderGoods> my_saleGoodsList = new ArrayList<>();
			List<SaleorderGoods> they_saleGoodsList = new ArrayList<>();

			mv.addObject("loginUserId", curr_user.getUserId() + ";");

			// 默认报价咨询人
			User contsultantUser = paramsConfigValueService.getQuoteConsultant(curr_user.getCompanyId(), 107);
			if (contsultantUser == null) {
				contsultantUser = new User();
			}
			mv.addObject("contsultantUserId", contsultantUser.getUserId() + ";");
			List<Integer> goodsIds = new ArrayList<>();
			for (int i = 0; i < saleorderGoodsList.size(); i++) {
				goodsIds.clear();
				goodsIds.add(saleorderGoodsList.get(i).getGoodsId());
				saleorderGoodsList.get(i).setGoodsUserIdStr("");
				List<Integer> manageUserIds = goodsMapper.getAssignUserIdsByGoods(goodsIds);
				if (CollectionUtils.isNotEmpty(manageUserIds)) {
					for (Integer uid : manageUserIds) {
						saleorderGoodsList.get(i).setGoodsUserIdStr(saleorderGoodsList.get(i).getGoodsUserIdStr() + uid + ";");
					}
				}
						// 归属当前登录用户的商品排列在前
				if (saleorderGoodsList.get(i).getGoodsUserIdStr() != null && saleorderGoodsList.get(i)
						.getGoodsUserIdStr().contains(curr_user.getUserId() + ";")) {
					my_saleGoodsList.add(saleorderGoodsList.get(i));
				} else if (curr_user.getUserId().equals(contsultantUser.getUserId())) {// 当前登录的用户是报价咨询默认人
					my_saleGoodsList.add(saleorderGoodsList.get(i));
				} else {
					they_saleGoodsList.add(saleorderGoodsList.get(i));
				}
			}
			my_saleGoodsList.addAll(they_saleGoodsList);
			saleorderGoodsList.clear();
			saleorderGoodsList.addAll(my_saleGoodsList);


			// add by Tomcat.Hui 2019/11/28 15:40 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start
			//已开票数量 已申请数量
			Map<Integer , Map<String, Object>> taxNumsMap = invoiceService.getInvoiceNums(saleorder);
			if (null != taxNumsMap) {
				saleorderGoodsList.stream().forEach(g -> {
					g.setAppliedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_NUM").toString()));
					g.setInvoicedNum(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_NUM").toString()));
					g.setInvoicedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("INVOICE_AMOUNT").toString()));
					g.setAppliedAmount(new BigDecimal(taxNumsMap.get(g.getSaleorderGoodsId()).get("APPLY_AMOUNT").toString()));
				});
			}

			saleorder = saleorderService.updateisOpenInvoice(saleorder,saleorderGoodsList);//更改申请开票按钮显示逻辑
			// add by Tomcat.Hui 2019/11/28 15:40 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end

			List<ExpressDetail> expresseList = expressService.getSEGoodsNum(saleorderGoodsList.stream().map(SaleorderGoods::getSaleorderGoodsId).collect(Collectors.toList()));
			mv.addObject("expresseList", expresseList);

			mv.addObject("saleorderGoodsList", saleorderGoodsList);

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			List<Integer> skuIds = new ArrayList<>();
			saleorderGoodsList.stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

			if(saleorder.getTraderId()!=null) {
				User user = null;
				// 销售人员名称
				user = userService.getUserById(saleorder.getUserId());
				saleorder.setSalesName(user == null ? "" : user.getUsername());
				// 创建人员
				user = userService.getUserById(saleorder.getCreator());
				saleorder.setCreatorName(user == null ? "" : user.getUsername());

				if(sale.getTraderId()!=null && saleorder.getTraderId()>0) {
					// 归属销售
					user = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
					saleorder.setOptUserName(user == null ? "" : user.getUsername());
					// 销售部门（若一个多个部门，默认取第一个部门）
					User userOrg = orgService.getTraderUserAndOrgByTraderId(saleorder.getTraderId(), 1);
					saleorder.setSalesDeptName(userOrg == null ? "" : userOrg.getOrgName());
				}else {
					user = userService.getUserById(saleorder.getUserId());
					saleorder.setOptUserName(user == null ? "" : user.getUsername());
					Organization org = orgService.getOrgNameByUserId(saleorder.getUserId());
					saleorder.setSalesDeptName(org == null ? "" : org.getOrgName());
				}


				// 获取订单客户信息
				saleorder.setCustomerTypeStr(getSysOptionDefinition(saleorder.getCustomerType()).getTitle());
				// 客户性质
				saleorder.setCustomerNatureStr(getSysOptionDefinition(saleorder.getCustomerNature()).getTitle());
				// 客户资质信息
				// 客户资质信息
				// 资质信息


				Map<String, Object> map = accountPeriodService.getTraderAptitudeInfo(saleorder.getTraderId());
				if (map != null) {
					TraderCertificateVo bus = null;
					// 营业执照信息
					if (map.containsKey("business")) {
						JSONObject json = JSONObject.fromObject(map.get("business"));
						bus = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("business", bus);
					}
					// 税务登记信息
					TraderCertificateVo tax = null;
					if (map.containsKey("tax")) {
						JSONObject json = JSONObject.fromObject(map.get("tax"));
						tax = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("tax", tax);
					}
					// 组织机构信息
					TraderCertificateVo orga = null;
					if (map.containsKey("orga")) {
						JSONObject json = JSONObject.fromObject(map.get("orga"));
						orga = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("orga", orga);
					}
					// 客户
					mv.addObject("customerProperty", saleorder.getCustomerNature());
					if (saleorder.getCustomerNature().intValue() == 465) {// 分销
						// 二类医疗资质
						List<TraderCertificateVo> twoMedicalList = null;
						if (map.containsKey("twoMedical")) {
							// JSONObject json =
							// JSONObject.fromObject(map.get("twoMedical"));
							twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
							mv.addObject("twoMedicalList", twoMedicalList);
						}
						// 二类医疗资质详情
						List<TraderMedicalCategoryVo> list = null;
						if (map.containsKey("medicalCertificate")) {
							JSONArray jsonArray = JSONArray.fromObject(map.get("medicalCertificate"));
							list = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray,
									TraderMedicalCategoryVo.class);
							mv.addObject("medicalCertificates", list);
						}
						// 三类医疗资质
						TraderCertificateVo threeMedical = null;
						if (map.containsKey("threeMedical")) {
							JSONObject json = JSONObject.fromObject(map.get("threeMedical"));
							threeMedical = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
							mv.addObject("threeMedical", threeMedical);
						}
					} else {// 终端
						// 医疗机构执业许可证
						List<TraderCertificateVo> practiceList = null;
						if (map.containsKey("practice")) {
							// JSONObject
							// json=JSONObject.fromObject(map.get("practice"));
							practiceList = (List<TraderCertificateVo>) map.get("practice");
							mv.addObject("practiceList", practiceList);
						}
					}
				}
			}else {
				User user = null;
				// 销售人员名称
				user = userService.getUserById(saleorder.getUserId());
				saleorder.setSalesName(user == null ? "" : user.getUsername());
				// 归属销售
				saleorder.setOptUserName(user == null ? "" : user.getUsername());
				// 创建人员
				user = userService.getUserById(saleorder.getCreator());
				saleorder.setCreatorName(user == null ? "" : user.getUsername());
				// 销售部门
				Organization org = orgService.getOrgNameByUserId(saleorder.getUserId());
				saleorder.setSalesDeptName(org == null ? "" : org.getOrgName());
			}
			Page page = Page.newBuilder(null, null, null);

			// 获取订单收货信息

			// 获取订单收票信息

			// 获取订单终端信息
			// 开票申请
			List<InvoiceApply> saleInvoiceApplyList = invoiceService.getSaleInvoiceApplyList(saleorder.getSaleorderId(),
					SysOptionConstant.ID_505, -1);
			mv.addObject("saleInvoiceApplyList", saleInvoiceApplyList);

			// 获取发票信息
			List<Invoice> saleInvoiceList = invoiceService.getInvoiceInfoByRelatedId(saleorder.getSaleorderId(),
					SysOptionConstant.ID_505);
			mv.addObject("saleInvoiceList", saleInvoiceList);
			// 终端类型
			saleorder.setTerminalTraderTypeStr(getSysOptionDefinition(saleorder.getTerminalTraderType()).getTitle());
//			mv.addObject("customer", customer);
            // modify by franlin.wu for[当customer为null 时] at 2018-11-27 begin
            if (null == customer && saleorder.getCustomerNature()!=null&& saleorder.getCustomerNature().equals(466)) {
                // 根据客户ID查询客户信息
                TraderCustomerVo customer2 = traderCustomerService.getCustomerInfo(saleorder.getTraderId());
                // 客户类型
				saleorder.setTerminalTraderName(customer2.getTraderName());
				// 客户性质
				saleorder.setTerminalTraderTypeStr(customer2.getCustomerTypeStr());
			}
			// 获取订单产品信息,以及所有产品的手填产品成本总和totalAmount
			sale.setTraderId(saleorder.getTraderId());
			sale.setCompanyId(curr_user.getCompanyId());
			sale.setReqType(1);
			saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
			// 所有产品的手填成本总价
			mv.addObject("totalReferenceCostPrice", sale.getFiveTotalAmount());
			// 物流信息
			if (saleorderGoodsList != null && saleorderGoodsList.size() > 0) {
				Express express = new Express();
				express.setBusinessType(SysOptionConstant.ID_496);
				express.setCompanyId(curr_user.getCompanyId());
				List<Integer> relatedIds = new ArrayList<Integer>();
				for (SaleorderGoods sg : saleorderGoodsList) {
					relatedIds.add(sg.getSaleorderGoodsId());
				}
				express.setRelatedIds(relatedIds);

				try {
					List<Express> expressList = expressService.getExpressListNew(express);
					mv.addObject("expressList", expressList);

                    //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
                    if(!CollectionUtils.isEmpty(expressList)){

                        List<Integer> expressGoodsId = new ArrayList<>();

                        expressList.stream().forEach(expressItem->{
                            if(!CollectionUtils.isEmpty(expressItem.getExpressDetail())){
                                expressItem.getExpressDetail().stream().forEach(expressDetail -> {
                                    expressGoodsId.add( expressDetail.getGoodsId());
                                });
                            }
                        });

                        if(CollectionUtils.isNotEmpty(expressGoodsId)){
                            List<CoreSpuGenerate> spuLists = this.vGoodsService.findSpuNamesBySpuIds(expressGoodsId);
                            Map<Integer,String> spuMap = spuLists.stream().collect(Collectors.toMap(k->k.getSpuId(), v -> v.getSpuName(), (k, v) -> v));
                            mv.addObject("spuMap", spuMap);
                        }
                    }
                    //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}




			// 出库记录
			saleorder.setBussinessType(2);
			// 出库记录清单
			List<WarehouseGoodsOperateLog> warehouseOutList = warehouseOutService.getOutDetil(saleorder);
			List<String> timeArrayWl = new ArrayList<>();
			if (null != warehouseOutList) {
				for (WarehouseGoodsOperateLog wl : warehouseOutList) {
					User u = userService.getUserById(wl.getCreator());
					wl.setOpName(u.getUsername());
					timeArrayWl.add(DateUtil.convertString(wl.getAddTime(), "YYYY-MM-dd HH:mm:ss"));
				}
				HashSet<String> wArray = new HashSet<String>(timeArrayWl);
				mv.addObject("timeArrayWl", wArray);
			}
			mv.addObject("warehouseOutList", warehouseOutList);
			// 获取订单其他信息

			// 获取订单沟通记录

			// 获取订单操作日志

			// 付款方式列表
			List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
			mv.addObject("paymentTermList", paymentTermList);

			// 发货方式
			List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
			mv.addObject("deliveryTypes", deliveryTypes);

			// 获取物流公司列表
			List<Logistics> logisticsList = getLogisticsList(curr_user.getCompanyId());
			mv.addObject("logisticsList", logisticsList);

			// 运费说明
			List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
			mv.addObject("freightDescriptions", freightDescriptions);
			// 发票类型
			List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
			mv.addObject("invoiceTypes", invoiceTypes);
			// 沟通类型为商机和报价和销售订单
			CommunicateRecord cr = new CommunicateRecord();
			if (saleorder.getQuoteorderId() != null && saleorder.getQuoteorderId() != 0) {
				cr.setQuoteorderId(saleorder.getQuoteorderId());
			}
			if (saleorder.getBussinessChanceId() != null && saleorder.getBussinessChanceId() != 0) {
				cr.setBussinessChanceId(saleorder.getBussinessChanceId());
			}
			cr.setSaleorderId(saleorder.getSaleorderId());
			cr.setCompanyId(curr_user.getCompanyId());
			List<CommunicateRecord> communicateList = traderCustomerService.getCommunicateRecordListPage(cr, page);
			if (!communicateList.isEmpty()) {
				// 沟通内容
				mv.addObject("communicateList", communicateList);
				mv.addObject("page", page);
			}

			// 获取订单合同回传&订单送货单回传列表
			List<Attachment> saleorderAttachmentList = saleorderService.getSaleorderAttachmentList(saleorderId);
			mv.addObject("saleorderAttachmentList", saleorderAttachmentList);

			// 售后订单列表
			AfterSalesVo as = new AfterSalesVo();
			as.setOrderId(saleorderId);
			as.setSubjectType(535);
			List<AfterSalesVo> asList = afterSalesOrderService.getAfterSalesVoListByOrderId(as);
			// if (asList != null && asList.size() > 0) {
			// mv.addObject("addAfterSales", 1);
			// for (AfterSalesVo asv : asList) {
			// if((asv.getType() == 539 || asv.getType() == 540 || asv.getType()
			// == 543) && (asv.getAtferSalesStatus()
			// == 0 || asv.getAtferSalesStatus() == 1)){
			// mv.addObject("addAfterSales", 0);
			// break;
			// }
			// }
			// } else {
			// mv.addObject("addAfterSales", 1);
			// }
			mv.addObject("asList", asList);
			// 获取交易信息数据
			CapitalBill capitalBill = new CapitalBill();
			capitalBill.setOperationType("finance_sale_detail");
			CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
			capitalBillDetail.setOrderType(ErpConst.ONE);// 销售订单类型
			capitalBillDetail.setOrderNo(saleorder.getSaleorderNo());
			capitalBillDetail.setRelatedId(saleorderId);
			capitalBill.setCapitalBillDetail(capitalBillDetail);
			List<CapitalBill> capitalBillList = capitalBillService.getCapitalBillList(capitalBill);
			mv.addObject("capitalBillList", capitalBillList);
			// 资金流水交易方式
			List<SysOptionDefinition> traderModes = getSysOptionDefinitionList(519);
			mv.addObject("traderModes", traderModes);

			// 资金流水业务类型
			List<SysOptionDefinition> bussinessTypes = getSysOptionDefinitionList(524);
			mv.addObject("bussinessTypes", bussinessTypes);
			// 订单修改申请列表（不分页）
			SaleorderModifyApply saleorderModifyApply = new SaleorderModifyApply();
			saleorderModifyApply.setSaleorderId(saleorderId);
			List<SaleorderModifyApply> saleorderModifyApplyList = saleorderService
					.getSaleorderModifyApplyList(saleorderModifyApply);
			mv.addObject("saleorderModifyApplyList", saleorderModifyApplyList);

			// 录保卡
			List<SaleorderGoodsWarrantyVo> goodsWarrantys = saleorderService.getSaleorderGoodsWarrantys(saleorderId);
			mv.addObject("goodsWarrantys", goodsWarrantys);
			// 查询当前订单的一些状态
			if(saleorder.getTraderId()!=null) {
				User userInfo = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
				saleorder.setOptUserName(userInfo == null ? "" : userInfo.getUsername());
			}
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"saleorderVerify_" + saleorderId);
			mv.addObject("taskInfo", historicInfo.get("taskInfo"));
			mv.addObject("startUser", historicInfo.get("startUser"));
			// 最后审核状态
			mv.addObject("endStatus", historicInfo.get("endStatus"));
			mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
			mv.addObject("commentMap", historicInfo.get("commentMap"));
			mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
			Task taskInfo = (Task) historicInfo.get("taskInfo");
			// 当前审核人
			String verifyUsers = null;
			List<String> verifyUserList = new ArrayList<>();
			if (null != taskInfo) {
				Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
				verifyUsers = (String) taskInfoVariables.get("verifyUsers");
				String verifyUser = (String) taskInfoVariables.get("verifyUserList");
				if (null != verifyUser) {
					verifyUserList = Arrays.asList(verifyUser.split(","));
				}
			}
			List<String> verifyUsersList = new ArrayList<>();
			if (verifyUsers != null && !verifyUserList.isEmpty()) {
				verifyUsersList = Arrays.asList(verifyUsers.split(","));
			}
			mv.addObject("verifyUsers", verifyUsers);
			mv.addObject("verifyUserList", verifyUserList);
			mv.addObject("verifyUsersList", verifyUsersList);
			// 提前采购审核信息
			Map<String, Object> historicInfoEarly = actionProcdefService.getHistoric(processEngine,
					"earlyBuyorderVerify_" + saleorderId);
			mv.addObject("taskInfoEarly", historicInfoEarly.get("taskInfo"));
			mv.addObject("startUserEarly", historicInfoEarly.get("startUser"));
			mv.addObject("endStatusEarly", historicInfoEarly.get("endStatus"));
			mv.addObject("historicActivityInstanceEarly", historicInfoEarly.get("historicActivityInstance"));
			mv.addObject("commentMapEarly", historicInfoEarly.get("commentMap"));
			mv.addObject("candidateUserMapEarly", historicInfoEarly.get("candidateUserMap"));
			Task taskInfoEarly = (Task) historicInfoEarly.get("taskInfo");
			// 当前审核人
			String verifyUsersEarly = null;
			List<String> verifyUserListEarly = new ArrayList<>();
			if (null != taskInfoEarly) {
				Map<String, Object> taskInfoVariablesEarly = actionProcdefService.getVariablesMap(taskInfoEarly);
				verifyUsersEarly = (String) taskInfoVariablesEarly.get("verifyUsers");
				String verifyUserEarly = (String) taskInfoVariablesEarly.get("verifyUserList");
				if (null != verifyUserEarly) {
					verifyUserListEarly = Arrays.asList(verifyUserEarly.split(","));
				}
			}
			List<String> verifyUsersListEarly = new ArrayList<>();
			if (verifyUsersEarly != null && !verifyUserListEarly.isEmpty()) {
				verifyUsersListEarly = Arrays.asList(verifyUsersEarly.split(","));
			}
			mv.addObject("verifyUsersEarly", verifyUsersEarly);
			mv.addObject("verifyUserListEarly", verifyUserListEarly);
			mv.addObject("verifyUsersListEarly", verifyUsersListEarly);

			// 合同回传审核信息
			Map<String, Object> historicInfoContractReturn = actionProcdefService.getHistoric(processEngine,
					"contractReturnVerify_" + saleorderId);
			Task taskInfoContractReturn = (Task) historicInfoContractReturn.get("taskInfo");
			mv.addObject("taskInfoContractReturn", taskInfoContractReturn);
			mv.addObject("startUserContractReturn", historicInfoContractReturn.get("startUser"));
			// 最后审核状态
			mv.addObject("endStatusContractReturn", historicInfoContractReturn.get("endStatus"));
			mv.addObject("historicActivityInstanceContractReturn",
					historicInfoContractReturn.get("historicActivityInstance"));
			mv.addObject("commentMapContractReturn", historicInfoContractReturn.get("commentMap"));
			mv.addObject("candidateUserMapContractReturn", historicInfoContractReturn.get("candidateUserMap"));
			String verifyUsersContractReturn = null;
			if (null != taskInfoContractReturn) {
				Map<String, Object> taskInfoVariablesContractReturn = actionProcdefService
						.getVariablesMap(taskInfoContractReturn);
				verifyUsersContractReturn = (String) taskInfoVariablesContractReturn.get("verifyUsers");
			}
			mv.addObject("verifyUsersContractReturn", verifyUsersContractReturn);

			// 开票申请审核信息
			InvoiceApply invoiceApplyInfo = invoiceService.getInvoiceApplyByRelatedId(saleorderId,
					SysOptionConstant.ID_505, saleorder.getCompanyId());
			mv.addObject("invoiceApply", invoiceApplyInfo);
			if (invoiceApplyInfo != null && curr_user.getCompanyId() != 1) {
				Map<String, Object> historicInfoInvoice = actionProcdefService.getHistoric(processEngine,
						"invoiceVerify_" + invoiceApplyInfo.getInvoiceApplyId());
				mv.addObject("taskInfoInvoice", historicInfoInvoice.get("taskInfo"));
				mv.addObject("startUserInvoice", historicInfoInvoice.get("startUser"));
				mv.addObject("candidateUserMapInvoice", historicInfoInvoice.get("candidateUserMap"));
				// 最后审核状态
				mv.addObject("endStatusInvoice", historicInfoInvoice.get("endStatus"));
				mv.addObject("historicActivityInstanceInvoice", historicInfoInvoice.get("historicActivityInstance"));
				mv.addObject("commentMapInvoice", historicInfoInvoice.get("commentMap"));

				Task taskInfoInvoice = (Task) historicInfoInvoice.get("taskInfo");
				// 当前审核人
				String verifyUsersInvoice = null;
				if (null != taskInfoInvoice) {
					Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfoInvoice);
					verifyUsersInvoice = (String) taskInfoVariables.get("verifyUsers");
				}
				mv.addObject("verifyUsersInvoice", verifyUsersInvoice);
			}
			/*
			 * //获取订单对应客户的余额信息 TraderCustomer traderCustomerInfo =
			 * traderCustomerService.getTraderCustomerInfo(saleorder.getTraderId ());
			 * mv.addObject("traderCustomerInfo", traderCustomerInfo);
			 */
			// 获取交易信息（订单实际金额，客户已付款金额）
			Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
			mv.addObject("saleorderDataInfo", saleorderDataInfo);
		} catch (Exception e) {
			logger.error("sale order view:", e);
		}
		//以开票数量和待审核开票数量
//		Map<String, Object> invoiceNumMap =invoiceService.getInvoiceNum(saleorderId);

		// 计算是否是当月
		String nextMonthFirst = null;
		// 跨月表示（0表示未跨月 1表示跨月）
		Integer isCrossMonth = 0;
		try {
			//VDERP-1377
//			Long time = saleorder.getValidTime();//原来的规则是按照生效时间的当月
			Long time = saleorder.getPaymentTime();//现在的规则是按照全部付款的时间当月
			// 如果有生效时间
			if (time != null && time != 0) {
				nextMonthFirst = DateUtil
						.getNextMonthFirst(DateUtil.convertString(time, "yyyy-MM-dd"));
				String nowTimeDate = DateUtil.getNowDate("yyyy-MM-dd");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// 下个月1号
				Date nmf = sdf.parse(nextMonthFirst);
				// 当前时间
				Date ntd = sdf.parse(nowTimeDate);
				// 当前时间小于 跨月时间
				if (!ntd.before(nmf)) {
					if(saleorder.getPaymentStatus().equals(2)) {//全部付款
					isCrossMonth = 1;
					}
				}
			}
			//VDERP-1377
		} catch (ParseException e) {
			logger.error("sale order view parse error:", e);
		}
		mv.addObject("isCrossMonth", isCrossMonth);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/view");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * ajax补充订单详情中数据
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年2月9日 下午4:16:45
	 */
	@ResponseBody
	@RequestMapping(value = "getsaleordergoodsextrainfo")
	public ResultInfo<?> getSaleordergetSaleorderGoodsExtraInfoGoodsExtraInfo(HttpServletRequest request, Saleorder saleorder) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if (null != curr_user) {
			saleorder.setCompanyId(curr_user.getCompanyId());
		}
		// 获取订单产品信息
		ResultInfo<?> saleorderGoodsExtraInfo = saleorderService.getSaleorderGoodsExtraInfo(saleorder);
		return saleorderGoodsExtraInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单编辑页面 ---- 于2018年11月14日重构
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 上午9:17:26
	 */
	/*
	 * @ResponseBody
	 *
	 * @RequestMapping(value = "edit") public ModelAndView edit(HttpServletRequest
	 * request, Saleorder saleorder) throws IOException { User curr_user = (User)
	 * request.getSession().getAttribute(ErpConst.CURR_USER); ModelAndView mv = new
	 * ModelAndView(); Integer saleorderId = saleorder.getSaleorderId(); // 获取订单基本信息
	 * saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
	 * saleorder.setCompanyId(curr_user.getCompanyId()); // 根据客户ID查询客户信息
	 * TraderCustomerVo customer =
	 * traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
	 * mv.addObject("customer", customer); User user = null; // 销售人员名称 user =
	 * userService.getUserById(saleorder.getUserId()); saleorder.setSalesName(user
	 * == null ? "" : user.getUsername()); // 创建人员 user =
	 * userService.getUserById(saleorder.getCreator());
	 * saleorder.setCreatorName(user == null ? "" : user.getUsername()); // 归属销售
	 * user = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
	 * saleorder.setOptUserName(user == null ? "" : user.getUsername());
	 *
	 * // 销售部门（若一个多个部门，默认取第一个部门） Organization org =
	 * orgService.getOrgNameByUserId(saleorder.getUserId());
	 * saleorder.setSalesDeptName(org == null ? "" : org.getOrgName());
	 *
	 * // SysOptionDefinition sod = null; // 客户类型
	 * saleorder.setCustomerTypeStr(getSysOptionDefinition(saleorder.
	 * getCustomerType()).getTitle()); // 客户性质
	 * saleorder.setCustomerNatureStr(getSysOptionDefinition(saleorder.
	 * getCustomerNature()).getTitle()); // 终端类型
	 *
	 * if (saleorder.getCustomerNature().intValue() == 465) {// 分销 // 省级地区
	 * List<Region> provinceList = regionService.getRegionByParentId(1);
	 * mv.addObject("provinceList", provinceList); if (saleorder.getSalesAreaId() !=
	 * null) { // 地区 List<Region> regionList = (List<Region>)
	 * regionService.getRegion(saleorder.getSalesAreaId(), 1); if (regionList !=
	 * null && (!regionList.isEmpty())) { for (Region r : regionList) { switch
	 * (r.getRegionType()) { case 1 : List<Region> cityList =
	 * regionService.getRegionByParentId(r.getRegionId());
	 * mv.addObject("provinceRegion", r); mv.addObject("cityList", cityList); break;
	 * case 2 : List<Region> zoneList =
	 * regionService.getRegionByParentId(r.getRegionId());
	 * mv.addObject("cityRegion", r); mv.addObject("zoneList", zoneList); break;
	 * case 3 : mv.addObject("zoneRegion", r); break; default :
	 * mv.addObject("countryRegion", r); break; } } }
	 *
	 * } }
	 *
	 * saleorder.setTerminalTraderTypeStr(getSysOptionDefinition(saleorder.
	 * getTerminalTraderType()).getTitle()); // 获取物流公司列表 List<Logistics>
	 * logisticsList = getLogisticsList(curr_user.getCompanyId());
	 * mv.addObject("logisticsList", logisticsList);
	 *
	 * // 客户信息 联系人&联系地址 if (saleorder.getTraderId() != null &&
	 * saleorder.getTraderId().intValue() > 0) { TraderContactVo traderContactVo =
	 * new TraderContactVo(); traderContactVo.setTraderId(saleorder.getTraderId());
	 * traderContactVo.setIsEnable(1); traderContactVo.setTraderType(ErpConst.ONE);
	 * Map<String, Object> map =
	 * traderCustomerService.getTraderContactVoList(traderContactVo); String tastr =
	 * (String) map.get("contact"); net.sf.json.JSONArray json =
	 * net.sf.json.JSONArray.fromObject(tastr); List<TraderContactVo>
	 * traderContactList = (List<TraderContactVo>) json.toCollection(json,
	 * TraderContactVo.class); List<TraderAddressVo> traderAddressList =
	 * (List<TraderAddressVo>) map.get("address"); mv.addObject("traderContactList",
	 * traderContactList); mv.addObject("traderAddressList", traderAddressList);
	 *
	 * TraderCustomerVo traderCustomerInfo =
	 * traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
	 * mv.addObject("traderCustomerInfo", traderCustomerInfo); }
	 *
	 * // 收货信息 联系人&联系地址 if (saleorder.getTakeTraderId() != null &&
	 * saleorder.getTakeTraderId().intValue() > 0) { TraderContactVo
	 * takeTraderContactVo = new TraderContactVo();
	 * takeTraderContactVo.setTraderId(saleorder.getTakeTraderId());
	 * takeTraderContactVo.setIsEnable(1);
	 * takeTraderContactVo.setTraderType(ErpConst.ONE); Map<String, Object> takeMap
	 * = traderCustomerService.getTraderContactVoList(takeTraderContactVo); String
	 * takeTastr = (String) takeMap.get("contact"); net.sf.json.JSONArray takeJson =
	 * net.sf.json.JSONArray.fromObject(takeTastr); List<TraderContactVo>
	 * takeTraderContactList = (List<TraderContactVo>)
	 * takeJson.toCollection(takeJson, TraderContactVo.class); List<TraderAddressVo>
	 * takeTraderAddressList = (List<TraderAddressVo>) takeMap.get("address");
	 * mv.addObject("takeTraderContactList", takeTraderContactList);
	 * mv.addObject("takeTraderAddressList", takeTraderAddressList);
	 *
	 * TraderCustomerVo takeTraderCustomerInfo =
	 * traderCustomerService.getCustomerBussinessInfo(saleorder.getTakeTraderId( ));
	 * mv.addObject("takeTraderCustomerInfo", takeTraderCustomerInfo); }
	 *
	 * // 收票信息 联系人&联系地址 if (saleorder.getInvoiceTraderId() != null &&
	 * saleorder.getInvoiceTraderId().intValue() > 0) { TraderContactVo
	 * invoiceTraderContactVo = new TraderContactVo();
	 * invoiceTraderContactVo.setTraderId(saleorder.getInvoiceTraderId());
	 * invoiceTraderContactVo.setIsEnable(1);
	 * invoiceTraderContactVo.setTraderType(ErpConst.ONE); Map<String, Object>
	 * invoiceMap =
	 * traderCustomerService.getTraderContactVoList(invoiceTraderContactVo); String
	 * invoiceTastr = (String) invoiceMap.get("contact"); net.sf.json.JSONArray
	 * invoiceJson = net.sf.json.JSONArray.fromObject(invoiceTastr);
	 * List<TraderContactVo> invoiceTraderContactList = (List<TraderContactVo>)
	 * invoiceJson.toCollection(invoiceJson, TraderContactVo.class);
	 * List<TraderAddressVo> invoiceTraderAddressList = (List<TraderAddressVo>)
	 * invoiceMap.get("address"); mv.addObject("invoiceTraderContactList",
	 * invoiceTraderContactList); mv.addObject("invoiceTraderAddressList",
	 * invoiceTraderAddressList);
	 *
	 * TraderCustomerVo invoiceTraderCustomerInfo =
	 * traderCustomerService.getCustomerBussinessInfo(saleorder.
	 * getInvoiceTraderId()); mv.addObject("invoiceTraderCustomerInfo",
	 * invoiceTraderCustomerInfo); }
	 *
	 * // 发货方式 List<SysOptionDefinition> deliveryTypes =
	 * getSysOptionDefinitionList(480); mv.addObject("deliveryTypes",
	 * deliveryTypes);
	 *
	 * // 运费说明 List<SysOptionDefinition> freightDescriptions =
	 * getSysOptionDefinitionList(469); mv.addObject("freightDescriptions",
	 * freightDescriptions);
	 *
	 * // 发票类型 List<SysOptionDefinition> invoiceTypes =
	 * getSysOptionDefinitionList(428); mv.addObject("invoiceTypes", invoiceTypes);
	 *
	 * // 物流公司列表 // List<Logistics> LogisticsList =
	 * saleorderService.getLogisticsList();
	 *
	 * // 付款方式列表 List<SysOptionDefinition> paymentTermList =
	 * getSysOptionDefinitionList(SysOptionConstant.ID_418);
	 * mv.addObject("paymentTermList", paymentTermList);
	 *
	 * // 获取订单产品信息 Saleorder sale = new Saleorder();
	 * sale.setSaleorderId(saleorderId); sale.setTraderId(saleorder.getTraderId());
	 * sale.setCompanyId(saleorder.getCompanyId()); List<SaleorderGoods>
	 * saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale); // 产品结算价
	 * saleorderGoodsList =
	 * goodsSettlementPriceService.getGoodsSettlePriceBySaleorderGoodsList(user.
	 * getCompanyId(), saleorderGoodsList); // 计算核价信息 saleorderGoodsList =
	 * goodsChannelPriceService.getSaleChannelPriceList(saleorder.getSalesAreaId (),
	 * saleorder.getCustomerNature(), customer.getOwnership(), saleorderGoodsList);
	 * mv.addObject("saleorderGoodsList", saleorderGoodsList);
	 *
	 * mv.addObject("saleorder", saleorder); mv.addObject("beforeParams",
	 * saveBeforeParamToRedis(JsonUtils.translateToJson(saleorder)));
	 * mv.setViewName("order/saleorder/edit"); return mv; }
	 */
	/**
	 * <b>Description:</b><br>
	 * 订单编辑页面 ---- 于2018年11月14日重构（待测试完成，没有问题后，此处代码删除）
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke.li <br>
	 *       <b>Date:</b> 2018年11月14日 下午19:17:26
	 */

	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "edit")
	public ModelAndView edit(HttpServletRequest request, Saleorder saleorder) throws IOException {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// add by franlin.wu for[取公司ID等信息] at 2018-11-27 begin
		// 公司ID
		Integer companyId = null;
		// 当前用户非空
		if (null != curr_user) {
			// 获取公司Id
			companyId = curr_user.getCompanyId();
		}
		// add by franlin.wu for[取公司ID等信息] at 2018-11-27 end
		ModelAndView mv = new ModelAndView();
		Integer saleorderId = saleorder.getSaleorderId();
		// 获取订单基本信息
		saleorder = saleorderService.getSaleOrderInfo(saleorder);

		// add by franlin.wu for[耗材商城订单编辑] at 2018.10.31 begin
		// 订单类型
		Integer orderType = saleorder.getOrderType();
		if (null != saleorder) {
			mv.addObject("orderType", saleorder.getOrderType());
		}
		// 耗材订单类型
		if (ErpConst.FIVE.equals(orderType)) {
			LOG.info("ownerIds::::"+ownerIds);
			// 订单归属list
			// SysOptionDefinition ownerUserIds = getSysOptionDefinition(SysOptionConstant.ID_906);
			//if (null != ownerUserIds) {
				// 数字字典配置订单归属userId多个以,分割
				String userIdStr =ownerIds;
				// 非空
				if (StringUtils.isNotBlank(userIdStr)) {
					try {
						// 将userIdStr分割成String[]，在转换为List<String>在转换为List<Integer>
						List<Integer> userIdList = Arrays.asList(userIdStr.split(",")).stream().map(Integer::parseInt)
								.collect(Collectors.toList());
						// 非空
						if (CollectionUtils.isNotEmpty(userIdList)) {
							// 设置订单归属ownerUserList
							mv.addObject("ownerUserList", userService.getUserListByUserIdList(userIdList));
						}
					} catch (Exception e) {
						LOG.error("订单归属的数字字典userId配置错误，数据格式错误或数据转换异常", e);
					}

			    }
		}
		// add by franlin.wu for[耗材商城订单编辑] at 2018.10.31 end

		saleorder.setCompanyId(companyId);
		// 根据客户ID查询客户信息
		TraderCustomerVo customer = traderCustomerService.getCustomerInfo(saleorder.getTraderId());
		mv.addObject("customer", customer);
		// modify by franlin.wu for[当customer为null 时] at 2018-11-27 begin
		if (null != customer) {
			// 客户类型
			saleorder.setCustomerTypeStr(customer.getCustomerTypeStr());
			// 客户性质
			saleorder.setCustomerNatureStr(customer.getCustomerNatureStr());
		}
		try {
			User user = null;
			// 销售人员名称
			user = userService.getUserById(saleorder.getUserId());
			saleorder.setSalesName(user == null ? "" : user.getUsername());
			// 创建人员
			user = userService.getUserById(saleorder.getCreator());
			saleorder.setCreatorName(user == null ? "" : user.getUsername());
			// 归属销售
			user = userService.getUserByTraderId(saleorder.getTraderId(), 1);// 1客户，2供应商
			saleorder.setOptUserName(user == null ? "" : user.getUsername());
			// 销售部门（若一个多个部门，默认取第一个部门）
			Organization org = orgService.getOrgNameByUserId(saleorder.getUserId());
			//判断是否为科研购及其子部门
            mv.addObject("isScientificDept",orgService.getKYGOrgFlagByTraderId(saleorder));
			saleorder.setSalesDeptName(org == null ? "" : org.getOrgName());
		} catch (Exception e) {
			LOG.error("请先关联归属销售", e);
		}

		// 耗材订单`
		if (ErpConst.FIVE.equals(orderType) || saleorder.getCustomerNature().intValue() == 465) {// 分销
			// 省级地区
			List<Region> provinceList = regionService.getRegionByParentId(1);
			mv.addObject("provinceList", provinceList);
			if (saleorder.getSalesAreaId() != null) {
				// 地区
				List<Region> regionList = (List<Region>) regionService.getRegion(saleorder.getSalesAreaId(), 1);
				if (regionList != null && (!regionList.isEmpty())) {
					for (Region r : regionList) {
						switch (r.getRegionType()) {
							case 1:
								List<Region> cityList = regionService.getRegionByParentId(r.getRegionId());
								mv.addObject("provinceRegion", r);
								mv.addObject("cityList", cityList);
								break;
							case 2:
								List<Region> zoneList = regionService.getRegionByParentId(r.getRegionId());
								mv.addObject("cityRegion", r);
								mv.addObject("zoneList", zoneList);
								break;
							case 3:
								mv.addObject("zoneRegion", r);
								break;
							default:
								mv.addObject("countryRegion", r);
								break;
						}
					}
				}

			}
		}

		// add by franlin.wu for[耗材商城的订单管理] at 2018-11-28 begin
		if (ErpConst.FIVE.equals(orderType) && null != saleorder) {
			// 收货地区最小级ID
			Integer traderAreaId = saleorder.getTraderAreaId();
			// 存在
			if (null != traderAreaId && !ErpConst.ZERO.equals(traderAreaId)) {
				// 根据地区最小Id查询省市区ID
				String traderAddressIdStr = regionService.getRegionIdStringByMinRegionId(traderAreaId);
				if (null != traderAddressIdStr) {
					String[] traderAddressIdArr = traderAddressIdStr.split(",");
					// 长度为3
					if (null != traderAddressIdArr && traderAddressIdArr.length == 3) {
						// 省地区id
						String provinceId = traderAddressIdArr[0];
						mv.addObject("traderAddressIdProvince", provinceId);
						// 市地区Id
						String cityId = traderAddressIdArr[1];
						mv.addObject("traderAddressIdCity", cityId);
						List<Region> traderAddressIdCityList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(provinceId));
						mv.addObject("traderAddressIdCityList", traderAddressIdCityList);
						// 区
						mv.addObject("traderAddressIdZone", traderAddressIdArr[2]);
						List<Region> traderAddressZoneList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(cityId));
						mv.addObject("traderAddressZoneList", traderAddressZoneList);
					}
				}
			}
			// 收货地区最小级ID
			Integer takeTraderAreaId = saleorder.getTakeTraderAreaId();
			// 存在
			if (null != takeTraderAreaId && !ErpConst.ZERO.equals(takeTraderAreaId)) {
				// 根据地区最小Id查询省市区ID
				String takeTraderAddressIdStr = regionService.getRegionIdStringByMinRegionId(takeTraderAreaId);
				if (null != takeTraderAddressIdStr) {
					String[] takeTraderAddressIdArr = takeTraderAddressIdStr.split(",");
					// 长度为3
					if (null != takeTraderAddressIdArr && takeTraderAddressIdArr.length == 3) {
						// 省地区id
						String provinceId = takeTraderAddressIdArr[0];
						mv.addObject("takeTraderAddressIdProvince", provinceId);
						// 市地区Id
						String cityId = takeTraderAddressIdArr[1];
						mv.addObject("takeTraderAddressIdCity", cityId);
						List<Region> takeTraderAddressCityList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(provinceId));
						mv.addObject("takeTraderAddressCityList", takeTraderAddressCityList);
						// 区
						mv.addObject("takeTraderAddressIdZone", takeTraderAddressIdArr[2]);
						List<Region> takeTraderAddressZoneList = StringUtil.isBlank(cityId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(cityId));
						mv.addObject("takeTraderAddressZoneList", takeTraderAddressZoneList);
					}
				}
			}
			// 收票地区最小级ID
			Integer invoiceTraderAreaId = saleorder.getInvoiceTraderAreaId();
			// 存在
			if (null != invoiceTraderAreaId && !ErpConst.ZERO.equals(invoiceTraderAreaId)) {
				// 根据地区最小Id查询省市区ID
				String invoiceTraderAddressIdStr = regionService.getRegionIdStringByMinRegionId(invoiceTraderAreaId);
				if (null != invoiceTraderAddressIdStr) {
					String[] invoiceTraderAddressIdArr = invoiceTraderAddressIdStr.split(",");
					// 长度为3
					if (null != invoiceTraderAddressIdArr && invoiceTraderAddressIdArr.length == 3) {
						// 省地区ID
						String provinceId = invoiceTraderAddressIdArr[0];
						mv.addObject("invoiceTraderAddressIdProvince", provinceId);
						// 市地区Id
						String citiyId = invoiceTraderAddressIdArr[1];
						mv.addObject("invoiceTraderAddressIdCity", citiyId);
						List<Region> invoiceCityList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(provinceId));
						mv.addObject("invoiceCityList", invoiceCityList);
						// 区地区Id
						mv.addObject("invoiceTraderAddressIdZone", invoiceTraderAddressIdArr[2]);
						List<Region> invoiceZoneList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(citiyId));
						mv.addObject("invoiceZoneList", invoiceZoneList);
					}
				}
			}
		}
		// add by franlin.wu for[耗材商城的订单管理] at 2018-11-28 end
		saleorder.setTerminalTraderTypeStr(getSysOptionDefinition(saleorder.getTerminalTraderType()).getTitle());
		// 获取物流公司列表
		List<Logistics> logisticsList = getLogisticsList(curr_user.getCompanyId());
		mv.addObject("logisticsList", logisticsList);

		if(saleorder.getOwnerUserId() != null){
			User ownerUser = this.userService.getUserById(saleorder.getOwnerUserId());
			if(ownerUser != null){
				saleorder.setOwnerUserName(ownerUser.getUsername());
			}
		}


		// 非耗材订单类型 && 客户信息 联系人&联系地址
		if (!ErpConst.FIVE.equals(orderType) && saleorder.getTraderId() != null
				&& saleorder.getTraderId().intValue() > 0) {
			TraderContactVo traderContactVo = new TraderContactVo();
			traderContactVo.setTraderId(saleorder.getTraderId());
			traderContactVo.setIsEnable(1);
			traderContactVo.setTraderType(ErpConst.ONE);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			List<TraderContactVo> traderContactList = (List<TraderContactVo>) json.toCollection(json,
					TraderContactVo.class);
			List<TraderAddressVo> traderAddressList = (List<TraderAddressVo>) map.get("address");
			mv.addObject("traderContactList", traderContactList);
			mv.addObject("traderAddressList", traderAddressList);
		}

		// 非耗材订单类型 && 收货信息 联系人&联系地址
		if (!ErpConst.FIVE.equals(orderType) && saleorder.getTakeTraderId() != null
				&& saleorder.getTakeTraderId().intValue() > 0) {
			TraderContactVo takeTraderContactVo = new TraderContactVo();
			takeTraderContactVo.setTraderId(saleorder.getTakeTraderId());
			takeTraderContactVo.setIsEnable(1);
			takeTraderContactVo.setTraderType(ErpConst.ONE);
			Map<String, Object> takeMap = traderCustomerService.getTraderContactVoList(takeTraderContactVo);
			String takeTastr = (String) takeMap.get("contact");
			net.sf.json.JSONArray takeJson = net.sf.json.JSONArray.fromObject(takeTastr);
			List<TraderContactVo> takeTraderContactList = (List<TraderContactVo>) takeJson.toCollection(takeJson,
					TraderContactVo.class);
			List<TraderAddressVo> takeTraderAddressList = (List<TraderAddressVo>) takeMap.get("address");

			// BD订单地址未同步-需要此操作
			if(orderType==1) {
				boolean userFlag = true,addressFlag = true;
				for(int x=0;x<takeTraderContactList.size();x++){
					if(takeTraderContactList.get(x).getTraderContactId().equals(saleorder.getTakeTraderContactId())){
						userFlag = false;
					}
				}
				if(userFlag){
					takeTraderContactVo.setTraderContactId(saleorder.getTakeTraderContactId());
					takeTraderContactVo.setName(saleorder.getTakeTraderContactName());
					takeTraderContactVo.setMobile(saleorder.getTakeTraderContactMobile());
					takeTraderContactVo.setTelephone(saleorder.getTakeTraderContactTelephone());
//					takeTraderContactVo.setTraderContactId(saleorder.getTraderContactId());
					takeTraderContactList.add(takeTraderContactVo);
				}

				for(int x=0;x<takeTraderAddressList.size();x++){
					if(takeTraderAddressList.get(x).getTraderAddress().getTraderAddressId().equals(saleorder.getTakeTraderAddressId())){
						addressFlag = false;
					}
				}
				if(addressFlag){
					TraderAddressVo e = new TraderAddressVo();
					TraderAddress address = new TraderAddress();
					address.setAddress(saleorder.getTakeTraderAddress());
					address.setTraderAddressId(saleorder.getTakeTraderAddressId());
					e.setTraderAddress(address);
					e.setArea(saleorder.getTakeTraderArea());
					takeTraderAddressList.add(e);
				}
			}
			mv.addObject("takeTraderAddressList", takeTraderAddressList);
			mv.addObject("takeTraderContactList", takeTraderContactList);


			TraderCustomerVo takeTraderCustomerInfo = traderCustomerService
					.getCustomerInfo(saleorder.getTakeTraderId());
			mv.addObject("takeTraderCustomerInfo", takeTraderCustomerInfo);
		}else {
			Saleorder saleTrader = saleorderService.getsaleorderbySaleorderId(saleorderId);
			List<TraderContactVo> takeTraderContactList = new ArrayList<TraderContactVo>();
			List<TraderAddressVo> takeTraderAddressList = new ArrayList<TraderAddressVo>();
			TraderContactVo traderContactVo = new TraderContactVo();
			traderContactVo.setName(saleTrader.getTakeTraderContactName());
			traderContactVo.setMobile(saleTrader.getTakeTraderContactMobile());
//			saleTrader.getTakeTraderName();//收货公司名称
			traderContactVo.setTelephone(saleTrader.getTakeTraderContactTelephone());
			takeTraderContactList.add(traderContactVo);
			TraderAddressVo traderAddressVo = new TraderAddressVo();
			TraderAddress traderAddress = new TraderAddress();
			traderAddress.setAddress(saleTrader.getTakeTraderAddress());
			traderAddressVo.setTraderAddress(traderAddress);
			traderAddressVo.setArea(saleTrader.getTakeTraderArea());
			takeTraderAddressList.add(traderAddressVo);
//			mv.addObject("saleTrader", saleTrader);
			mv.addObject("takeTraderContactList", takeTraderContactList);
			mv.addObject("takeTraderAddressList", takeTraderAddressList);
		}

		// 非耗材订单类型 && 收票信息 联系人&联系地址
		if (!ErpConst.FIVE.equals(orderType) && saleorder.getInvoiceTraderId() != null
				&& saleorder.getInvoiceTraderId().intValue() > 0) {
			TraderContactVo invoiceTraderContactVo = new TraderContactVo();
			invoiceTraderContactVo.setTraderId(saleorder.getInvoiceTraderId());
			invoiceTraderContactVo.setIsEnable(1);
			invoiceTraderContactVo.setTraderType(ErpConst.ONE);
			Map<String, Object> invoiceMap = traderCustomerService.getTraderContactVoList(invoiceTraderContactVo);
			String invoiceTastr = (String) invoiceMap.get("contact");
			net.sf.json.JSONArray invoiceJson = net.sf.json.JSONArray.fromObject(invoiceTastr);
			List<TraderContactVo> invoiceTraderContactList = (List<TraderContactVo>) invoiceJson
					.toCollection(invoiceJson, TraderContactVo.class);
			List<TraderAddressVo> invoiceTraderAddressList = (List<TraderAddressVo>) invoiceMap.get("address");
			mv.addObject("invoiceTraderContactList", invoiceTraderContactList);
			mv.addObject("invoiceTraderAddressList", invoiceTraderAddressList);

			TraderCustomerVo invoiceTraderCustomerInfo = traderCustomerService
					.getCustomerInfo(saleorder.getInvoiceTraderId());
			mv.addObject("invoiceTraderCustomerInfo", invoiceTraderCustomerInfo);
		}

		// 发货方式
		List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
		mv.addObject("deliveryTypes", deliveryTypes);

		// 运费说明
		List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
		mv.addObject("freightDescriptions", freightDescriptions);

		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);

		// 物流公司列表
		// List<Logistics> LogisticsList =
		// saleorderService.getLogisticsList();

		// 付款方式列表
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		// Bert 20190118修改付款付款方式(付款方式只提供两种选择：先款后货，预付100%和先款后货，预付0%) 任务号码:DSDEV-1975
		if (null != saleorder) {
			if (saleorder.getSaleorderNo().startsWith("HC")) {
				List<SysOptionDefinition> sysOptionDefinitionList = null;
				Optional<List<SysOptionDefinition>> optionDefinitionList = Optional.ofNullable(paymentTermList);
				if (optionDefinitionList.isPresent() && !optionDefinitionList.get().isEmpty()) {
					sysOptionDefinitionList = optionDefinitionList.get().stream().filter(
							x -> (x.getSysOptionDefinitionId().equals(419) || x.getSysOptionDefinitionId().equals(423)))
							.collect(Collectors.toList());
				}
				mv.addObject("paymentTermList", sysOptionDefinitionList);
			} else {
				mv.addObject("paymentTermList", paymentTermList);
			}

		}

		// 获取订单产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorderId);
		sale.setTraderId(saleorder.getTraderId());
		sale.setCompanyId(saleorder.getCompanyId());
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleOrderGoodsList(sale);

		for(int i=0;i<saleorderGoodsList.size();i++){
			saleorderGoodsList.get(i).getGoods().setUserList(saleorderService.getUserByCategory(saleorderGoodsList.get(i).getGoods().getCategoryId(), saleorderGoodsList.get(i).getGoods().getCompanyId()));
		}

		// 产品结算价
		saleorderGoodsList = goodsSettlementPriceService.getGoodsSettlePriceBySaleorderGoodsList(companyId, saleorderGoodsList);
		// modify by franlin.wu for[避免空指针] at 2018-11-27 begin
		if (null != customer) {
			// 计算核价信息
			saleorderGoodsList = goodsChannelPriceService.getSaleChannelPriceList(saleorder.getSalesAreaId(),
					saleorder.getCustomerNature(), customer.getOwnership(), saleorderGoodsList);
		}
		// modify by franlin.wu for[避免空指针] at 2018-11-27 end
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		if (null != saleorder) {
			if (saleorder.getPaymentType() == 0) {
				saleorder.setPaymentType(419);
			}
		}

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(saleorderGoodsList)){
			List<Integer> skuIds = new ArrayList<>();
			saleorderGoodsList.stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mv.addObject("saleorder", saleorder);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(saleorder)));
		mv.setViewName("order/saleorder/edit_saleorder");

		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 根据客户ID获取联系人列表&地址列表
	 *
	 * @param request
	 * @param traderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月14日 下午1:20:36
	 */
	@ResponseBody
	@RequestMapping(value = "/getCustomerContactAndAddress")
	public ResultInfo<?> getCustomerContactAndAddress(HttpServletRequest request, Integer traderId) {
		TraderContactVo traderContactVo = new TraderContactVo();
		traderContactVo.setTraderId(traderId);
		traderContactVo.setTraderType(ErpConst.ONE);
		Map<String, Object> invoiceMap = traderCustomerService.getTraderContactVoList(traderContactVo);
		String invoiceTastr = (String) invoiceMap.get("contact");
		net.sf.json.JSONArray Json = net.sf.json.JSONArray.fromObject(invoiceTastr);
		List<TraderContactVo> contactList = (List<TraderContactVo>) Json.toCollection(Json, TraderContactVo.class);
		List<TraderAddressVo> addressList = (List<TraderAddressVo>) invoiceMap.get("address");
		Map<String, Object> map = new HashMap<>();
		map.put("contactList", contactList);
		map.put("addressList", addressList);
		ResultInfo<?> result = new ResultInfo<>();
		result.setCode(0);
		result.setMessage("操作成功");
		result.setParam(map);
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑的订单信息
	 *
	 * @param request
	 * @param session
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 上午10:49:06
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveeditsaleorderinfo")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑的订单信息")
	public ModelAndView saveEditSaleorderInfo(HttpServletRequest request, HttpSession session, Saleorder saleorder,
											  String beforeParams) {
		// 订单类型
		Integer orderType = saleorder.getOrderType();
		ModelAndView mv = new ModelAndView();
		try {
			if (saleorder.getPaymentType() != null && saleorder.getPaymentType().equals(419)) {// 419
				// :
				// 100%预付--其他付款计划：均设置默认值
				BigDecimal bd = new BigDecimal(0.00);
				saleorder.setAccountPeriodAmount(bd);// 账期支付金额
				saleorder.setPeriodDay(0);// 账期天数
				saleorder.setLogisticsCollection(0);// 物流代收0否 1是
				saleorder.setRetainageAmount(bd);// 尾款
				saleorder.setRetainageAmountMonth(0);// 尾款期限(月)
			}
			saleorder.setHaveAccountPeriod((saleorder.getAccountPeriodAmount() != null
					&& saleorder.getAccountPeriodAmount().doubleValue() != 0.00) ? 1 : 0);// 是否含有账期支付
			saleorder = saleorderService.saveEditSaleorderInfo(saleorder, request, session);
			saleorder = saleorderMapper.getSaleOrderById(saleorder.getSaleorderId());
			if(orderType==1) {
				Quoteorder quoteorder = new Quoteorder();
				quoteorder.setAccountPeriodAmount(saleorder.getAccountPeriodAmount());
				quoteorder.setPeriodDay(saleorder.getPeriodDay());
				quoteorder.setLogisticsCollection(saleorder.getLogisticsCollection());
				quoteorder.setRetainageAmount(saleorder.getRealAmount());
				quoteorder.setRetainageAmountMonth(saleorder.getRetainageAmountMonth());
//				quoteorder.setPaymentTermStr(saleorder.getPaymentComments());
				quoteorder.setInvoiceType(saleorder.getInvoiceType());
				quoteorder.setPaymentType(saleorder.getPaymentType());
				quoteorder.setQuoteorderId(saleorder.getQuoteorderId());
				quoteorder.setPrepaidAmount(saleorder.getPrepaidAmount());
				quoteService.updateQuote(quoteorder);
			}
			if (null != saleorder) {
				// add by franlin.wu for[耗材商城订单] at 2018-11-01 begin
				if (null == orderType || orderType != OrderConstant.ORDER_TYPE_HC) {
					mv.addObject("url", "./view.do?saleorderId=" + saleorder.getSaleorderId());
				} else {
					mv.addObject("url", "./hcOrderDetailsPage.do?saleorderId=" + saleorder.getSaleorderId());
				}
				// add by franlin.wu for[耗材商城订单] at 2018-11-01 end
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveeditsaleorderinfo:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 订单产品添加页面
	 *
	 * @param request
	 * @param searchContent
	 * @param saleorderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 上午11:49:49
	 */
	@RequestMapping(value = "/addSaleorderGoods")
	public ModelAndView addSaleorderGoods(HttpServletRequest request,
										  @RequestParam(value = "searchContent", required = false) String searchContent,
										  @RequestParam(value = "saleorderId", defaultValue = "0") Integer saleorderId, HttpSession session,
										  @RequestParam(required = false, defaultValue = "1") Integer pageNo,
										  @RequestParam(required = false, defaultValue = "10") Integer pageSize,Integer lendOut) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNoneBlank(searchContent)) {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			Page page = getPageTag(request, pageNo, pageSize);
			Goods goods = new Goods();
			goods.setCompanyId(user.getCompanyId());
			goods.setSearchContent(searchContent);
			Map<String, Object> map = new  HashMap<String, Object>();
			if(lendOut!=null && lendOut==1) {//外借单搜索
				map = goodsService.getlistGoodsStockPage(request, goods, page);
				mv.addObject("goodsList", map.get("goodsList"));
				//				mv.addObject("saleorderId", 0);

			}else {
				map = goodsService.queryGoodsListPage(goods, page, session);
				mv.addObject("goodsList", map.get("list"));
			}
			mv.addObject("page", map.get("page"));
			mv.addObject("searchContent", searchContent);
		}
		mv.addObject("saleorderId", saleorderId);
		//外借单搜索
		mv.addObject("lendOut", lendOut);
		mv.setViewName("order/saleorder/add_saleorder_goods");
		return mv;

	}

	/**
	 * <b>Description:</b><br>
	 * 保存订单产品信息
	 *
	 * @param request
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午1:15:50
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSaleorderGoods")
	@SystemControllerLog(operationType = "add", desc = "保存订单产品信息")
	public ResultInfo<?> saveSaleorderGoods(HttpServletRequest request, SaleorderGoods saleorderGoods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if(saleorderGoods.getDeliveryDirect()==null) {
			saleorderGoods.setDeliveryDirect(0);
		}
		setUserMsg(saleorderGoods, user, DateUtil.sysTimeMillis());
		ResultInfo<?> resultInfo = saleorderService.saveSaleorderGoods(saleorderGoods);

		/**
		 *如果订单总金额不小于199，运费的金额需要减免
		 */
		Saleorder oldSaleorder = new Saleorder();
		oldSaleorder.setSaleorderId(saleorderGoods.getSaleorderId());;
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoods(oldSaleorder);

		BigDecimal totalPrice = new BigDecimal(0);
		BigDecimal carriage = new BigDecimal(0);

		if (CollectionUtils.isNotEmpty(saleorderGoodsList)){
			for (SaleorderGoods goods : saleorderGoodsList) {
				if (127063 == goods.getGoodsId()){
					carriage = goods.getPrice();
				} else {
					totalPrice = totalPrice.add(goods.getPrice().multiply(new BigDecimal(goods.getNum())));
				}
			}
		}
		//判断运费的实际金额
		if (totalPrice.compareTo(new BigDecimal(199)) == -1){
			carriage = new BigDecimal(8);
		} else {
			carriage = new BigDecimal(0);
		}
		//修改运费
		if (CollectionUtils.isNotEmpty(saleorderGoodsList)){
            for (SaleorderGoods goods : saleorderGoodsList) {
                if (127063 == goods.getGoodsId()){
                    goods.setPrice(carriage);
                    goods.setGoodsId(null);
                    setUserMsg(goods,user,DateUtil.sysTimeMillis());
                    saleorderService.saveSaleorderGoods(goods);
                }
            }
        }
		return resultInfo;
	}

	private void setUserMsg(SaleorderGoods saleorderGoods, User user, long l) {
		if (user != null) {
			saleorderGoods.setCreator(user.getUserId());
			saleorderGoods.setAddTime(l);

			saleorderGoods.setUpdater(user.getUserId());
			saleorderGoods.setModTime(l);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 订单产品编辑页面
	 *
	 * @param request
	 * @param saleorderGoods
	 * @param orderType
	 *            订单类型[默认-1什么订单类型都不是]
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午2:24:24
	 */
	@ResponseBody
	@RequestMapping(value = "/editSaleorderGoods")
	public ModelAndView editSaleorderGoods(HttpServletRequest request, SaleorderGoods saleorderGoods,
										   @RequestParam(required = false, defaultValue = "-1") Integer orderType) {
		ModelAndView mv = new ModelAndView();
		Integer saleorderGoodsId = saleorderGoods.getSaleorderGoodsId();
		// 根据订单产品ID获取对应产品信息
		saleorderGoods = saleorderService.getSaleorderGoodsInfoById(saleorderGoodsId);
		// 根据订单商品ID货区对应的订单信息
        Saleorder saleorderTmp = new Saleorder();
        saleorderTmp.setSaleorderId(saleorderGoods.getSaleorderId());
        Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorderTmp);
        mv.addObject("saleorder", saleorderInfo);
        mv.addObject("saleorderGoods", saleorderGoods);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		Map<String,Object> newSkuInfo = vGoodsService.skuTip(saleorderGoods.getGoodsId());
		mv.addObject("newSkuInfo", newSkuInfo);
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		// add by franlin.wu for[新增订单类型] at 2018-11-25 begin
		mv.addObject("orderType", orderType);
		// add by franlin.wu for[新增订单类型] at 2018-11-25 end
		mv.setViewName("order/saleorder/edit_saleorder_goods");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 删除订单产品
	 *
	 * @param request
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午5:10:35
	 */
	@ResponseBody
	@RequestMapping(value = "/delSaleorderGoodsById")
	@SystemControllerLog(operationType = "delete", desc = "删除订单产品")
	public ResultInfo<?> delSaleorderGoodsById(HttpServletRequest request, SaleorderGoods saleorderGoods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			saleorderGoods.setUpdater(user.getUserId());
			saleorderGoods.setModTime(DateUtil.sysTimeMillis());
		}
		return saleorderService.delSaleorderGoodsById(saleorderGoods);
	}

	/**
	 * <b>Description:</b><br>
	 * 关闭订单
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月7日 上午9:40:25
	 */
	@ResponseBody
	@RequestMapping(value = "/closeSaleorder")
	public ResultInfo<?> closeSaleorder(HttpServletRequest request, Saleorder saleorder) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			saleorder.setUpdater(user.getUserId());
			saleorder.setModTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> resultInfo ;
		resultInfo = saleorderService.closeSaleorder(saleorder);
		Saleorder order = saleorderService.getsaleorderbySaleorderId(saleorder.getSaleorderId());
		if(order.getOrderType()==1 && resultInfo.getCode().equals(0)) {
		resultInfo = saleorderService.closeBDSaleorder(order);
		}
		if (resultInfo != null && resultInfo.getCode().equals(0) && user.getCompanyId().equals(1)) {
			vedengSoapService.orderSync(saleorder.getSaleorderId());
			Integer saleorderId = saleorder.getSaleorderId();
			new Thread() {
				@Override
				public void run() {
					// 如果订单是耗材商城的订单，需要同步订单状态到耗材商城
					Saleorder saleorder = new Saleorder();
					saleorder.setSaleorderId(saleorderId);
					saleorder = saleorderService.getSaleOrderInfo(saleorder);
					if (saleorder.getOrderType() == 5) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderNo", saleorder.getSaleorderNo());
						map.put("orderStatus", 3);
						// 同步订单状态到耗材商城
						hcSaleorderService.putOrderStatustoHC(map);
						// 如果该订单已存在付款,且订单未生效，调用退款接口在线退款（三期再做）
						if ((saleorder.getPaymentStatus() == 1 || saleorder.getPaymentStatus() == 2)
								&& saleorder.getValidStatus() == 0) {

						}
					}
				}
			}.start();
		}
		//更新库存服务占用数量
		saleorder.setOperateType(0);
		int i = warehouseStockService.updateOccupyStockService(saleorder, 0);
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单生效
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月27日 下午2:49:25
	 */
	@ResponseBody
	@RequestMapping(value = "/validSaleorder")
	public ResultInfo<?> validSaleorder(HttpServletRequest request, Saleorder saleorder) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			saleorder.setUpdater(user.getUserId());
			saleorder.setModTime(DateUtil.sysTimeMillis());
			saleorder.setCompanyName(user.getCompanyName());
		}
		saleorder.setValidTime(DateUtil.sysTimeMillis());
		return saleorderService.validSaleorder(saleorder);
	}

	/**
	 * <b>Description:</b><br>
	 * 撤销订单生效
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月27日 下午2:50:22
	 */
	@ResponseBody
	@RequestMapping(value = "/noValidSaleorder")
	public ResultInfo<?> noValidSaleorder(HttpServletRequest request, Saleorder saleorder) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			saleorder.setUpdater(user.getUserId());
			saleorder.setModTime(DateUtil.sysTimeMillis());
		}
		return saleorderService.noValidSaleorder(saleorder);
	}

	/**
	 * <b>Description:</b><br>
	 * 添加客户联系人
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月7日 上午11:48:41
	 */
	// @FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addContact")
	public ModelAndView addContact(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String traderId = request.getParameter("traderId");
		String traderCustomerId = request.getParameter("traderCustomerId");
		String indexId = request.getParameter("indexId");
		mv.addObject("traderId", traderId);
		mv.addObject("traderCustomerId", traderCustomerId);
		mv.addObject("indexId", indexId);
		mv.setViewName("order/saleorder/add_contact");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 添加客户地址
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月7日 上午11:49:32
	 */
	// @FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addAddress")
	public ModelAndView addAddress(HttpServletRequest request, Integer traderId) {
		ModelAndView mv = new ModelAndView();
		// 省级地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		String indexId = request.getParameter("indexId");
		mv.addObject("traderId", traderId);
		mv.addObject("provinceList", provinceList);
		mv.addObject("indexId", indexId);
		mv.setViewName("order/saleorder/add_address");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增沟通记录页面
	 *
	 * @param saleorder
	 * @param traderCustomer
	 * @param request
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月17日 上午11:38:49
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addComrecord")
	public ModelAndView addComrecord(Saleorder saleorder, TraderCustomer traderCustomer, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("order/saleorder/add_communicate");
		if(null!=traderCustomer&&traderCustomer.getTraderId()!=null) {
			TraderContact traderContact = new TraderContact();
			// 联系人
			traderContact.setTraderId(traderCustomer.getTraderId());
			traderContact.setIsEnable(ErpConst.ONE);
			traderContact.setTraderType(ErpConst.ONE);
			List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);
			mav.addObject("contactList", contactList);

			TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
			if (null != customer) {
				traderCustomer.setTraderCustomerId(customer.getTraderCustomerId());
			}
		}
		// 客户标签
		Tag tag = new Tag();
		tag.setTagType(SysOptionConstant.ID_32);
		tag.setIsRecommend(ErpConst.ONE);
		tag.setCompanyId(user.getCompanyId());

		Integer pageNo = 1;
		Integer pageSize = 10;

		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

		mav.addObject("saleorder", saleorder);


		// 默认沟通时间
		Date now = new Date();
		String startTime = DateUtil.DateToString(now, "yyyy-MM-dd HH:mm:ss");
		Date endDate = new Date(now.getTime() + 120000);// 当前时间添加2分钟
		String endTime = DateUtil.DateToString(endDate, "yyyy-MM-dd HH:mm:ss");
		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);

		// 沟通目的
		// if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST
		// + SysOptionConstant.ID_24)) {
		// String strJson =
		// JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
		// SysOptionConstant.ID_24);
		// JSONArray jsonArray = JSONArray.fromObject(strJson);
		// List<SysOptionDefinition> communicateGoalList =
		// (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
		// SysOptionDefinition.class);
		// mav.addObject("communicateGoalList", communicateGoalList);
		// }
		List<SysOptionDefinition> communicateGoalList = getSysOptionDefinitionList(SysOptionConstant.ID_24);
		mav.addObject("communicateGoalList", communicateGoalList);
		// 沟通方式
		// if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST
		// + SysOptionConstant.ID_23)) {
		// String strJson =
		// JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
		// SysOptionConstant.ID_23);
		// JSONArray jsonArray = JSONArray.fromObject(strJson);
		// List<SysOptionDefinition> communicateList =
		// (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
		// SysOptionDefinition.class);
		// mav.addObject("communicateList", communicateList);
		// }
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);
		mav.addObject("tagList", tagMap.get("list"));
		mav.addObject("page", tagMap.get("page"));

		return mav;
	}
    /**
    * @Description: 跳转添加联系人页面
    * @Param:
    * @return:
    * @Author: addis
    * @Date: 2019/10/24
    */
    @RequestMapping(value = "/addtradercontact")
    public  String addTraderContact(){
    	ModelAndView mv = new ModelAndView("/order/saleorder/add_contactName");
    	return "/order/saleorder/add_contactName";
	}
	/**
	 * <b>Description:</b><br>
	 * 保存订单沟通记录
	 *
	 * @param request
	 * @param communicateRecord
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月17日 上午11:42:32
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveCommunicate")
	@SystemControllerLog(operationType = "add", desc = "保存订单沟通记录")
	public ResultInfo<?> saveCommunicate(HttpServletRequest request, CommunicateRecord communicateRecord,
										 HttpSession session) {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_246);// 销售订单
		communicateRecord.setRelatedId(communicateRecord.getSaleorderId());
		try {
			if (null != communicateRecord.getCommunicateRecordId() && communicateRecord.getCommunicateRecordId() > 0) {
				record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
			} else {
				record = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
			}
		} catch (Exception e) {
			logger.error("sale order saveCommunicate:", e);
		}
		if (record) {
			// 修改订单主表信息(有沟通记录0无 1有)
			Saleorder saleorder = new Saleorder();
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			if (user != null) {
				saleorder.setUpdater(user.getUserId());
				saleorder.setModTime(DateUtil.sysTimeMillis());
			}
			saleorder.setHaveCommunicate(1);
			saleorder.setSaleorderId(communicateRecord.getSaleorderId());
			return saleorderService.editSaleorderHaveCommunicate(saleorder);
		} else {
			return new ResultInfo<>(1, "操作失败！");
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑订单沟通记录页面
	 *
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月17日 下午2:23:53
	 */
	@ResponseBody
	@RequestMapping(value = "/editCommunicate")
	public ModelAndView editCommunicate(CommunicateRecord communicateRecord, Saleorder saleorder,
										HttpServletRequest request, HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("order/saleorder/edit_communicate");
		CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
		// communicate.setTraderCustomerId(communicateRecord.getTraderCustomerId());
		communicate.setTraderId(communicateRecord.getTraderId());

		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(communicateRecord.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.ONE);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

		// 沟通目的
		// if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST
		// + SysOptionConstant.ID_24)) {
		// String strJson =
		// JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
		// SysOptionConstant.ID_24);
		// JSONArray jsonArray = JSONArray.fromObject(strJson);
		// List<SysOptionDefinition> communicateGoalList =
		// (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
		// SysOptionDefinition.class);
		// mav.addObject("communicateGoalList", communicateGoalList);
		// }
		List<SysOptionDefinition> communicateGoalList = getSysOptionDefinitionList(SysOptionConstant.ID_24);
		mv.addObject("communicateGoalList", communicateGoalList);
		// 沟通方式
		// if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST
		// + SysOptionConstant.ID_23)) {
		// String strJson =
		// JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
		// SysOptionConstant.ID_23);
		// JSONArray jsonArray = JSONArray.fromObject(strJson);
		// List<SysOptionDefinition> communicateList =
		// (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
		// SysOptionDefinition.class);
		// mav.addObject("communicateList", communicateList);
		// }
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mv.addObject("communicateList", communicateList);

		// 客户标签
		Tag tag = new Tag();
		tag.setTagType(SysOptionConstant.ID_32);
		tag.setIsRecommend(ErpConst.ONE);
		tag.setCompanyId(user.getCompanyId());

		Page page = getPageTag(request, 1, 10);
		Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

		mv.addObject("communicateRecord", communicate);

		mv.addObject("contactList", contactList);

		mv.addObject("tagList", tagMap.get("list"));
		mv.addObject("page", tagMap.get("page"));
		// mv.addObject("method", "communicaterecord");
		mv.addObject("saleorder", saleorder);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 报价转为订单
	 *
	 * @param request
	 * @param session
	 * @param quoteorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月19日 下午3:44:01
	 */
	@ResponseBody
	@RequestMapping(value = "/quoteordertosaleorder")
	@SystemControllerLog(operationType = "add", desc = "报价转为订单")
	public ModelAndView quoteorderToSaleorder(HttpServletRequest request, HttpSession session, Integer quoteorderId) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		ModelAndView mv = new ModelAndView();
		try {
			Saleorder saleorder = saleorderService.quoteorderToSaleorder(quoteorderId, user);
			if (null != saleorder) {
				// 通知web
				if (user.getCompanyId().equals(1)) {
					vedengSoapService.quoteoderToOrderSync(quoteorderId);
				}
				return new ModelAndView("redirect:/order/saleorder/edit.do?saleorderId=" + saleorder.getSaleorderId());
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("quoteordertosaleorder:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 订单合同回传初始化
	 *
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午2:19:47
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/contractReturnInit")
	public ModelAndView contractReturnInit(HttpServletRequest request, HttpSession session, Integer saleorderId) {
		// User user = (User)
		// request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		mv.addObject("saleorderId", saleorderId);
		mv.setViewName("order/saleorder/contract_return");
		return mv;
	}

	/**
	 *
	 * <b>Description:</b>销售订单合同回传列表
	 *
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月7日 下午3:00:33
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/contractReturnList")
	public ModelAndView contractReturnList(HttpServletRequest request, HttpSession session, Integer saleorderId) {
		ModelAndView mv = new ModelAndView();

		// 获取订单合同回传&订单送货单回传列表
		List<Attachment> saleorderAttachmentList = saleorderService.getSaleorderAttachmentList(saleorderId);

		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(saleorderId);
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

		List<Attachment> tempList = new ArrayList<Attachment>();
		for (Attachment attach : saleorderAttachmentList) {
			if (attach.getAttachmentFunction() == 492) {
				tempList.add(attach);
			}
		}

		mv.addObject("saleorderAttachmentList", tempList);
		mv.addObject("saleorder", saleorder);

		mv.setViewName("homepage/sale/sale_engineer_page_contract_list");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/contractReturnListNoFormtoken")
	public ModelAndView contractReturnListNoFormtoken(HttpServletRequest request, HttpSession session,
													  Integer saleorderId) {
		ModelAndView mv = new ModelAndView();

		// 获取订单合同回传&订单送货单回传列表
		List<Attachment> saleorderAttachmentList = saleorderService.getSaleorderAttachmentList(saleorderId);

		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(saleorderId);
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

		List<Attachment> tempList = new ArrayList<Attachment>();
		for (Attachment attach : saleorderAttachmentList) {
			if (attach.getAttachmentFunction() == 492) {
				tempList.add(attach);
			}
		}

		mv.addObject("saleorderAttachmentList", tempList);
		mv.addObject("saleorder", saleorder);

		mv.setViewName("homepage/sale/sale_engineer_page_contract_list");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单合同回传文件上传
	 *
	 * @param request
	 * @param response
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午2:47:39
	 */
	@ResponseBody
	@RequestMapping(value = "/contractReturnUpload")
	public FileInfo contractReturnUpload(HttpServletRequest request, HttpServletResponse response,
										 @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			String path = "/upload/saleorder";
			long size = lwfile.getSize();
			if (size > 2 * 1024 * 1024) {
				return new FileInfo(-1, "图片大小应为2MB以内");
			}
			return ftpUtilService.uploadFile(lwfile, path, request, "");
		} else {
			return new FileInfo(-1, "登录用户不能为空");
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 订单合同回传保存
	 *
	 * @param request
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午2:55:15
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/contractReturnSave")
	public ResultInfo<?> contractReturnSave(HttpServletRequest request, Attachment attachment) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		if (attachment != null && (attachment.getUri().contains("jpg") || attachment.getUri().contains("png")
				|| attachment.getUri().contains("gif") || attachment.getUri().contains("bmp"))) {
			attachment.setAttachmentType(SysOptionConstant.ID_460);
		} else {
			attachment.setAttachmentType(SysOptionConstant.ID_461);
		}
		attachment.setAttachmentFunction(SysOptionConstant.ID_492);
		if (user != null) {
			attachment.setCreator(user.getUserId());
			attachment.setAddTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> saveSaleorderAttachment = saleorderService.saveSaleorderAttachment(attachment);

		// 附件同步
		if (saveSaleorderAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			Attachment att = (Attachment) saveSaleorderAttachment.getData();
			if (null != att) {
				vedengSoapService.saleorderAttachmentSyncWeb(att.getAttachmentId(), false);
			}
		}
		return saveSaleorderAttachment;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单合同回传删除
	 *
	 * @param request
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午2:58:22
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/contractReturnDel")
	@SystemControllerLog(operationType = "delete", desc = "订单合同回传删除")
	public ResultInfo<?> contractReturnDel(HttpServletRequest request, Attachment attachment) {
		ResultInfo<?> delSaleorderAttachment = saleorderService.delSaleorderAttachment(attachment);
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		// 附件同步
		if (delSaleorderAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			vedengSoapService.saleorderAttachmentSyncWeb(attachment.getAttachmentId(), true);
		}
		return delSaleorderAttachment;
	}

	@ResponseBody
	@RequestMapping(value = "/contractReturnDelNotoken")
	@SystemControllerLog(operationType = "delete", desc = "订单合同回传删除")
	public ResultInfo<?> contractReturnDelNotoken(HttpServletRequest request, Attachment attachment) {
		ResultInfo<?> delSaleorderAttachment = saleorderService.delSaleorderAttachment(attachment);
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		// 附件同步
		if (delSaleorderAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			vedengSoapService.saleorderAttachmentSyncWeb(attachment.getAttachmentId(), true);
		}
		return delSaleorderAttachment;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单送货单页面初始化
	 *
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午5:26:07
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/deliveryOrderReturnInit")
	public ModelAndView deliveryOrderReturnInit(HttpServletRequest request, HttpSession session, Integer saleorderId) {
		// User user = (User)
		// request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		mv.addObject("saleorderId", saleorderId);
		mv.setViewName("order/saleorder/delivery_order_return");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单送货单回传文件上传
	 *
	 * @param request
	 * @param response
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午5:27:33
	 */
	@ResponseBody
	@RequestMapping(value = "/deliveryOrderReturnUpload")
	public FileInfo deliveryOrderReturnUpload(HttpServletRequest request, HttpServletResponse response,
											  @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		FileInfo fileInfo = null;
		if (user != null) {
			String path = "/upload/saleorder";
			long size = lwfile.getSize();

			String fileName = lwfile.getOriginalFilename();
			// 文件后缀名称
			String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

			if (size > 10 * 1024 * 1024) {
				return new FileInfo(-1, "图片大小应为10MB以内");
			} else {
				if (size < 2 * 1024 * 1024 || prefix.toUpperCase().equals("PDF")) {
					fileInfo = ftpUtilService.uploadFile(lwfile, path, request, "");
				} else {
					/************* 根据文件上的大小进行压缩 ***********/
					Map<String, Integer> map = new HashMap<String, Integer>();

					// TODO 待定是否存在压缩
					// map.put("size", 160);
					map.put("quality", 70);
					fileInfo = ftpUtilService.compressImg(lwfile, request, "", path, map);
					/************* 根据文件上的大小进行压缩 ***********/
				}
			}
			return fileInfo;
		} else {
			return new FileInfo(-1, "登录用户不能为空");
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 订单送货单回传保存
	 *
	 * @param request
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午5:28:37
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/deliveryOrderReturnSave")
	public ResultInfo<?> deliveryOrderReturnSave(HttpServletRequest request, Attachment attachment) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		attachment.setAttachmentType(SysOptionConstant.ID_460);
		attachment.setAttachmentFunction(SysOptionConstant.ID_493);
		if (user != null) {
			attachment.setCreator(user.getUserId());
			attachment.setAddTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> saveSaleorderAttachment = saleorderService.saveSaleorderAttachment(attachment);
		// 附件同步
		if (saveSaleorderAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			Attachment att = (Attachment) saveSaleorderAttachment.getData();
			if (null != att) {
				vedengSoapService.saleorderAttachmentSyncWeb(att.getAttachmentId(), false);
			}
		}
		return saveSaleorderAttachment;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单送货单回传删除
	 *
	 * @param request
	 * @param attachment
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月24日 下午5:29:29
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/deliveryOrderReturnDel")
	@SystemControllerLog(operationType = "delete", desc = "订单送货单回传删除")
	public ResultInfo<?> deliveryOrderReturnDel(HttpServletRequest request, Attachment attachment) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ResultInfo<?> delSaleorderAttachment = saleorderService.delSaleorderAttachment(attachment);
		// 附件同步
		if (delSaleorderAttachment.getCode().equals(0) && user.getCompanyId().equals(1)) {
			vedengSoapService.saleorderAttachmentSyncWeb(attachment.getAttachmentId(), true);
		}
		return delSaleorderAttachment;
	}

	/**
	 * <b>Description:</b><br>
	 * 报价转订单预判断
	 *
	 * @param request
	 * @param quoteorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月31日 下午2:10:55
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/preQuoteorderToSaleorder")
	public ResultInfo<?> preQuoteorderToSaleorder(HttpServletRequest request, Integer quoteorderId) {
		if(saleorderService.isExistQuoteorderId(quoteorderId)>0){
			return  new ResultInfo<>(-1,"该报价已经存在订单");
		}
		return saleorderService.preQuoteorderToSaleorder(quoteorderId);
	}

	/**
	 * <b>Description:</b><br>
	 * 备货列表
	 *
	 * @param request
	 * @param saleorder
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月2日 下午1:27:42
	 */
	@ResponseBody
	@RequestMapping(value = "bhIndex")
	public ModelAndView bhIndex(HttpServletRequest request, Saleorder saleorder, HttpSession session,
								@RequestParam(required = false, defaultValue = "1") Integer pageNo,
								@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		User sessionUser = (User) session.getAttribute(Consts.SESSION_USER);
		Page page = getPageTag(request, pageNo, pageSize);
		mv.addObject("loginUser", sessionUser);
		if (null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != "") {
			saleorder.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00",
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != "") {
			saleorder.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59",
					"yyyy-MM-dd HH:mm:ss"));
		}

		// 申请人（采购人员）
		List<User> buyerList = userService.getUserByPositType(SysOptionConstant.ID_311, sessionUser.getCompanyId());

		saleorder.setOrderType(2);
		saleorder.setCompanyId(sessionUser.getCompanyId());
		Map<String, Object> map = saleorderService.getSaleorderListPage(request, saleorder, page,0);//0为销售订单,1为线上订单

		List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");

		for (int i = 0; i < list.size(); i++) {
			// 申请人
			User user = userService.getUserById(list.get(i).getCreator());
			VerifiesInfo verifiesInfo=new VerifiesInfo();
			verifiesInfo.setRelateTable("T_SALEORDER");
			verifiesInfo.setRelateTableKey(list.get(i).getSaleorderId());
			verifiesInfo.setVerifiesType(620);//备货
			VerifiesInfo verifiesInfo1=verifiesInfoMapper.getVerifiesInfoByParam(verifiesInfo);
			if(verifiesInfo1==null){
				list.get(i).setBhVerifyStatus(null);
			}else{
				list.get(i).setBhVerifyStatus(verifiesInfo1.getStatus());
			}
			list.get(i).setSalesName(user == null ? "" : user.getUsername());
			// 部门（若一个多个部门，默认取第一个部门）
			Organization org = orgService.getOrgNameByUserId(list.get(i).getCreator());
			list.get(i).setSalesDeptName(org == null ? "" : org.getOrgName());

			// 审核人
			if (null != list.get(i).getVerifyUsername()) {
				List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));
				list.get(i).setVerifyUsernameList(verifyUsernameList);
			}

		}

		mv.addObject("buyerList", buyerList);
		mv.addObject("saleorderList", list);
		mv.addObject("page", map.get("page"));
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/bh_index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增备货
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月2日 下午6:57:24
	 */
	@ResponseBody
	@RequestMapping(value = "addBhSaleorder")
	public ModelAndView addBhSaleorder(HttpServletRequest request) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();

		mv.addObject("username", curr_user.getUsername());
		mv.setViewName("order/saleorder/bh_add_saleorder");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增的备货订单
	 *
	 * @param request
	 * @param session
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月3日 上午11:28:38
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAddBhSaleorder")
	@SystemControllerLog(operationType = "add", desc = "保存新增的备货订单")
	public ModelAndView saveAddBhSaleorder(HttpServletRequest request, HttpSession session, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		try {
			saleorder = saleorderService.saveAddBhSaleorder(saleorder, request, session);
			if (null != saleorder) {
				mv.addObject("url", "./editBhSaleorder.do?saleorderId=" + saleorder.getSaleorderId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveAddBhSaleorder:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看备货详情
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年11月6日 下午2:20:18
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "viewBhSaleorder")
	public ModelAndView viewBhSaleorder(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		mv.addObject("curr_user", curr_user);
		Integer saleorderId = saleorder.getSaleorderId();
		// 获取订单基本信息
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
		// 根据客户ID查询客户信息
		TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
		mv.addObject("customer", customer);
		User user = null;
		// 申请人/创建者名称
		user = userService.getUserById(saleorder.getCreator());
		saleorder.setCreatorName(user == null ? "" : user.getUsername());

		// 部门（若一个多个部门，默认取第一个部门）
		Organization org = orgService.getOrgNameByUserId(saleorder.getCreator());
		saleorder.setSalesDeptName(org == null ? "" : org.getOrgName());

		// 获取备货订单产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorderId);
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
		for (SaleorderGoods sg : saleorderGoodsList) {
			sg.getGoodsId();
		}
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"bhSaleorderVerify_" + saleorderId);
		mv.addObject("taskInfo", historicInfo.get("taskInfo"));
		mv.addObject("startUser", historicInfo.get("startUser"));
		// 最后审核状态
		mv.addObject("endStatus", historicInfo.get("endStatus"));
		mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mv.addObject("commentMap", historicInfo.get("commentMap"));
		mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));

		mv.addObject("saleorderGoodsList", saleorderGoodsList);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(saleorderGoodsList)){
			List<Integer> skuIds = new ArrayList<>();
			saleorderGoodsList.stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


		mv.addObject("saleorder", saleorder);
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		// 当前审核人
		String verifyUsers = null;
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
		}
		mv.addObject("verifyUsers", verifyUsers);
		mv.setViewName("order/saleorder/bh_view_saleorder");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑备货订单页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月3日 下午3:30:59
	 */
	@ResponseBody
	@RequestMapping(value = "editBhSaleorder")
	public ModelAndView editBhSaleorder(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		Integer saleorderId = saleorder.getSaleorderId();
		// 获取订单基本信息
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

		// 根据客户ID查询客户信息
		TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorder.getTraderId());
		mv.addObject("customer", customer);
		User user = null;
		// 申请人/创建者名称
		user = userService.getUserById(saleorder.getCreator());
		saleorder.setCreatorName(user == null ? "" : user.getUsername());

		// 销售部门（若一个多个部门，默认取第一个部门）
		Organization org = orgService.getOrgNameByUserId(saleorder.getCreator());
		saleorder.setSalesDeptName(org == null ? "" : org.getOrgName());

		// 获取备货订单产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorderId);
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(saleorderGoodsList)){
			List<Integer> skuIds = new ArrayList<>();
			saleorderGoodsList.stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/bh_edit_saleorder");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 添加备货产品页面
	 *
	 * @param request
	 * @param searchContent
	 * @param saleorderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月3日 下午5:12:57
	 */
	@RequestMapping(value = "/addBhSaleorderGoods")
	public ModelAndView addBhSaleorderGoods(HttpServletRequest request,
											@RequestParam(value = "searchContent", required = false) String searchContent,
											@RequestParam(value = "saleorderId") Integer saleorderId, HttpSession session,
											@RequestParam(required = false, defaultValue = "1") Integer pageNo,
											@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNoneBlank(searchContent)) {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			Page page = getPageTag(request, pageNo, pageSize);
			Goods goods = new Goods();
			goods.setCompanyId(user.getCompanyId());
			goods.setSearchContent(searchContent);
			Map<String, Object> map = goodsService.queryGoodsListPage(goods, page, session);
			mv.addObject("goodsList", map.get("list"));
			mv.addObject("page", map.get("page"));
			mv.addObject("searchContent", searchContent);
		}
		mv.addObject("saleorderId", saleorderId);
		mv.setViewName("order/saleorder/bh_add_saleorder_goods");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 备货产品批量添加页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月11日 上午10:57:55
	 */
	@ResponseBody
	@RequestMapping(value = "/batchAddBhSaleorderGoods")
	public ModelAndView batchAddBhSaleorderGoods(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/batch_add_bh_saleorder_goods");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存批量添加产品信息
	 *
	 * @param request
	 * @param saleorder
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月14日 上午10:34:49
	 */
	@ResponseBody
	@RequestMapping(value = "/batchSaveBhSaleorderGoods")
	public ResultInfo<?> batchSaveBhSaleorderGoods(HttpServletRequest request, Saleorder saleorder,
												   @RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			if (fileInfo.getCode() == 0) {// 上传成功

				if (user != null) {
					saleorder.setCompanyId(user.getCompanyId());
					saleorder.setUpdater(user.getUserId());
					saleorder.setModTime(DateUtil.gainNowDate());
				}
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行

				int startCellNum = 0;// 默认从第一列开始（防止第一列为空）
				int endCellNum = sheet.getRow(0).getLastCellNum() - 1;
				List<SaleorderGoods> list = new ArrayList<>();// 保存Excel中读取的数据
				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);

					/*
					 * if (startRowNum == 1) { endCellNum = row.getLastCellNum() - 1;// 结束列 }
					 */
					// 获取excel的值
					SaleorderGoods sg = new SaleorderGoods();
					if (user != null) {
						sg.setCompanyId(user.getCompanyId());

						sg.setCreator(user.getUserId());
						sg.setAddTime(DateUtil.gainNowDate());

						sg.setUpdater(user.getUserId());
						sg.setModTime(DateUtil.gainNowDate());
					}
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						try {
							if (cell == null || "".equals(cell)) {// cell==null单元格空白（无内容，默认空）
								// if(cellNum==0){//第一列
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
								// }
								// continue;
							}
							Integer type = cell.getCellType();
							String str = null;
							Double pice = null;
							Integer num = null;
							BigDecimal bd = null;
							switch (cellNum) {
								case 0:// SKU（唯一识别，不为空）
									str = cell.getStringCellValue();
									if (str == null || "".equals(str)) {
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									}
									switch (type) {
										case Cell.CELL_TYPE_STRING:// 字符类型
											if (!str.matches("[A-Za-z0-9]+")) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
											}
											break;
										default:
											resultInfo.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
									}
									for (int x = 0; x < list.size(); x++) {
										if (str.equals(list.get(x).getSku())) {
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：SKU在此Excel表格中存在重复，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：SKU在此Excel表格中存在重复，请验证！");
										}
									}
									sg.setSku(str);
									sg.setLineNum(rowNum);
									break;
								case 1:// 备货价
									pice = cell.getNumericCellValue();
									if (pice == null) {
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									}
									BigDecimal priceDecimal=new BigDecimal(pice);
									switch (type) {
										case Cell.CELL_TYPE_NUMERIC:// 数字类型
//
											if(!(StringUtil.doubleToString(pice)).matches("[0-9]{1,14}\\.{0,1}[0-9]{0,2}")||priceDecimal.compareTo(OrderConstant.AMOUNT_LIMIT)==1){
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
											}
											break;
										default:
											resultInfo
													.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
									}
//									bd = new BigDecimal(pice);
									sg.setPrice(priceDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
									sg.setLineNum(rowNum);

									break;
								case 2:// 数量
									num = (int) cell.getNumericCellValue();
									if (num == null || "".equals(num)) {
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
									}
									if (!(num + "").matches("^[1-9]+[0-9]*$")) {
										resultInfo
												.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
										throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
									}
									sg.setNum(Integer.valueOf(num));
									sg.setLineNum(rowNum);
									break;
								default:
									resultInfo.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
							}
						} catch (Exception e) {
							logger.error("batchSaveBhSaleorderGoods 1:", e);
							if ("操作失败".equals(resultInfo.getMessage())) {
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列读取错误，请验证！");
							}
							return resultInfo;
							// throw new Exception("第:" + (rowNum + 1) + "行，第:"
							// + (cellNum + 1) + "列读取错误，请验证！");
						}
					}
					try {
						if (sg.getPrice().multiply(new BigDecimal(sg.getNum())).compareTo(OrderConstant.AMOUNT_LIMIT) == 1) {
							resultInfo.setMessage("第:" + (rowNum + 1) + "行,单个商品总额超过三亿,请验证!");
							throw new Exception("第:" + (rowNum + 1) + "行,单个商品总额超过三亿,请验证!");
						}
					}catch (Exception ex){
						return resultInfo;
					}
					sg.setSaleorderId(saleorder.getSaleorderId());
					list.add(sg);
				}
				if (list != null && !list.isEmpty()) {
					// 批量保存
					resultInfo = saleorderService.batchSaveBhSaleorderGoods(list, saleorder);
				} else {
					resultInfo.setMessage("第:2行，第:1列读取数据异常，请验证！");
				}
			}
		} catch (Exception e) {
			logger.error("batchSaveBhSaleorderGoods 2:", e);
			return new ResultInfo<>(-1, "Excel数据读取异常");
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑备货信息页面
	 *
	 * @param request
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月4日 下午4:07:03
	 */
	@ResponseBody
	@RequestMapping(value = "/editBhSaleorderGoods")
	public ModelAndView editBhSaleorderGoods(HttpServletRequest request, SaleorderGoods saleorderGoods) {
		ModelAndView mv = new ModelAndView();
		Integer saleorderGoodsId = saleorderGoods.getSaleorderGoodsId();
		// 根据订单产品ID获取对应产品信息
		saleorderGoods = saleorderService.getSaleorderGoodsInfoById(saleorderGoodsId);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		Map<String,Object> newSkuInfo = vGoodsService.skuTip(saleorderGoods.getGoodsId());
		mv.addObject("newSkuInfo", newSkuInfo);
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mv.addObject("saleorderGoods", saleorderGoods);
		mv.setViewName("order/saleorder/bh_edit_saleorder_goods");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑备货基本信息页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月4日 下午4:58:40
	 */
	@ResponseBody
	@RequestMapping(value = "editBhSaleorderBaseInfo")
	public ModelAndView editBhSaleorderBaseInfo(HttpServletRequest request, Saleorder saleorder) throws IOException {
		ModelAndView mv = new ModelAndView();
		// 获取订单基本信息
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

		mv.addObject("saleorder", saleorder);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(saleorder)));
		mv.setViewName("order/saleorder/bh_edit_saleorder_base_info");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑备货订单信息
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月4日 下午5:39:13
	 */
	@ResponseBody
	@RequestMapping(value = "/saveEditBhSaleorder")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑备货订单信息")
	public ResultInfo<?> saveEditBhSaleorder(HttpServletRequest request, Saleorder saleorder, String beforeParams) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			saleorder.setUpdater(user.getUserId());
			saleorder.setModTime(DateUtil.sysTimeMillis());
		}
		return saleorderService.saveEditBhSaleorder(saleorder);
	}

	/**
	 * <b>Description:</b><br>
	 * 通过销售订单NO获取订单基本表信息
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年8月8日 下午1:25:35
	 */
	@ResponseBody
	@RequestMapping(value = "/getsaleorderbysaleorderno")
	public ResultInfo getSaleorderBySaleorderNo(HttpServletRequest request, Saleorder saleorder) {
		ResultInfo resultInfo = new ResultInfo<>();
		if (saleorder.getSaleorderNo() != null) {
			Saleorder info = saleorderService.getSaleorderBySaleorderNo(saleorder);

			if (info != null) {
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setData(info);
			}
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到新增售后退货页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月10日 上午10:48:10
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addAfterSalesPage")
	public ModelAndView addAfterSalesPage(HttpServletRequest request, Saleorder saleorder) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		saleorder.setCompanyId(user.getCompanyId());
		ModelAndView mav = new ModelAndView();
		SaleorderVo sv = saleorderService.getSaleorderGoodsVoList(saleorder);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(sv.getSgvList())){

			List<Integer> skuIds = new ArrayList<>();

			sv.getSgvList().stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});

			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);

			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


		mav.addObject("saleorder", sv);
		if ("qt".equals(saleorder.getFlag())) {
			mav.setViewName("order/saleorder/add_afterSales_qt");
			return mav;
		}

		// 获取退货原因
		List<SysOptionDefinition> sysList = getSysOptionDefinitionList(535);
		mav.addObject("sysList", sysList);
		mav.addObject("domain", domain);
		if ("th".equals(sv.getFlag())) {// 退货
			mav.setViewName("order/saleorder/add_afterSales_th");
		} else if ("hh".equals(sv.getFlag())) {
			mav.setViewName("order/saleorder/add_afterSales_hh");
		} else if ("at".equals(sv.getFlag()) || "wx".equals(sv.getFlag())) {
			Integer areaId = sv.getAreaId();
			if (ObjectUtils.notEmpty(areaId)) {
				Region region = regionService.getRegionByRegionId(areaId);
				if (region.getRegionType() == 1) {// 省级

					mav.addObject("province", areaId);
				} else if (region.getRegionType() == 2) {
					List<Region> cityList = regionService.getRegionByParentId(region.getParentId());
					mav.addObject("cityList", cityList);
					mav.addObject("city", areaId);

					mav.addObject("province", region.getParentId());

				} else if (region.getRegionType() == 3) {
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
			if ("at".equals(sv.getFlag())) {
				mav.setViewName("order/saleorder/add_afterSales_at");
			} else {
				mav.setViewName("order/saleorder/add_afterSales_wx");
			}
		} else if ("tp".equals(sv.getFlag())) {
			mav.setViewName("order/saleorder/add_afterSales_tp");
		} else if ("tk".equals(sv.getFlag())) {
			// 获取交易信息（订单实际金额，客户已付款金额）当订单实际金额=客户已付款金额时候，不允许退款
			Map<String, BigDecimal> saleorderDataInfo = saleorderService
					.getSaleorderDataInfo(saleorder.getSaleorderId());
			mav.addObject("saleorderDataInfo", saleorderDataInfo);
			mav.setViewName("order/saleorder/add_afterSales_tk");
		} else if ("jz".equals(sv.getFlag())) {
			mav.setViewName("order/saleorder/add_afterSales_jz");
		}
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增售后
	 *
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月11日 下午1:27:56
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveAddAfterSales")
	@SystemControllerLog(operationType = "add", desc = "保存新增售后")
	public ModelAndView saveAddAfterSales(HttpServletRequest request, AfterSalesVo afterSalesVo,
										  @RequestParam(required = false, value = "afterSaleNums") String[] afterSaleNums,
										  @RequestParam(required = false, value = "fileName") String[] fileName,
										  @RequestParam(required = false, value = "fileUri") String[] fileUri,
										  @RequestParam(required = false, value = "invoiceIds") String[] invoiceIds) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSalesVo.setCompanyId(user.getCompanyId());
		afterSalesVo.setAfterSalesNum(afterSaleNums);
		afterSalesVo.setAttachName(fileName);
		afterSalesVo.setAttachUri(fileUri);
		afterSalesVo.setSubjectType(535);// 销售
		afterSalesVo.setAtferSalesStatus(0);
		// afterSalesVo.setServiceUserId(user.getUserId());
		afterSalesVo.setStatus(0);
		afterSalesVo.setValidStatus(0);
		afterSalesVo.setDomain(domain);
		afterSalesVo.setInvoiceIds(invoiceIds);
		afterSalesVo.setTraderType(1);
		ModelAndView mav = new ModelAndView();

		ResultInfo<?> res = afterSalesOrderService.saveAddAfterSales(afterSalesVo, user);
		// mav.addObject("refresh", "true_false_true");//
		// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		// mav.addObject("url", "./view.do?saleorderId=" +
		// afterSalesVo.getOrderId());
		if (res.getCode() == 0) {
			mav.addObject("url", "./viewAfterSalesDetail.do?afterSalesId=" + res.getData());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看销售订单的收货后详情
	 *
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月17日 下午1:54:47
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/viewAfterSalesDetail")
	public ModelAndView viewAfterSalesDetail(HttpServletRequest request, AfterSalesVo afterSales) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSales.setCompanyId(user.getCompanyId());
		afterSales.setTraderType(1);
		ModelAndView mav = new ModelAndView();
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		mav.addObject("curr_user", curr_user);
		AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoDetail(afterSales);

		//VDERP-1962:售后单生效后，销售人员不显示"关闭订单"按钮；设置afterSalesVo.setCloseStatus(2)来实现隐藏"关闭订单"按钮
		if (afterSalesVo.getValidStatus() > 0 && user.getOrgId() != null && user.getOrgId() != 10){
			afterSalesVo.setCloseStatus(2);
		}

		mav.addObject("afterSalesVo", afterSalesVo);
		CommunicateRecord communicateRecord = new CommunicateRecord();
		communicateRecord.setAfterSalesId(afterSalesVo.getAfterSalesId());
		List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
		mav.addObject("communicateList", crList);
		if (afterSalesVo.getType() == 539) {
			mav.setViewName("order/saleorder/view_afterSales_th");
		} else if (afterSalesVo.getType() == 540) {
			mav.setViewName("order/saleorder/view_afterSales_hh");
		} else if (afterSalesVo.getType() == 541) {
			mav.setViewName("order/saleorder/view_afterSales_at");
		} else if (afterSalesVo.getType() == 584) {
			mav.setViewName("order/saleorder/view_afterSales_wx");
		} else if (afterSalesVo.getType() == 542) {
			mav.setViewName("order/saleorder/view_afterSales_tp");
		} else if (afterSalesVo.getType() == 543) {
			mav.setViewName("order/saleorder/view_afterSales_tk");
		} else if (afterSalesVo.getType() == 544) {
			mav.setViewName("order/saleorder/view_afterSales_jz");
		} else if (afterSalesVo.getType() == 545) {
			mav.setViewName("order/saleorder/view_afterSales_qt");
		}

		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"afterSalesVerify_" + afterSalesVo.getAfterSalesId());
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		mav.addObject("taskInfo", historicInfo.get("taskInfo"));
		mav.addObject("startUser", historicInfo.get("startUser"));
		mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mav.addObject("endStatus", historicInfo.get("endStatus"));
		mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mav.addObject("commentMap", historicInfo.get("commentMap"));
		// 当前审核人
		String verifyUsers = null;
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
		}
		mav.addObject("verifyUsers", verifyUsers);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/toWarehouseDetail")
	public ResultInfo toWarehouseDetail(HttpServletRequest request,@Param(value="saleorderId") Integer saleorderId) {
		try{
			int counts = this.warehouseOutService.getWarehouseoutRecordCounts(saleorderId);

			if(counts == 0){
				return new ResultInfo(-1,"没有出库记录");
			}

			return new ResultInfo(0,"成功","3");

		}catch (Exception e){
			logger.error("查看库存详情页失败",e);
			return new ResultInfo(-1,"失败");
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 备货订单申请审核
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年10月24日 下午18:42:13
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/editApplyValidBH")
	public ResultInfo<?> editApplyValidBH(HttpServletRequest request, Saleorder saleorder, String taskId,
										  HttpSession session) {
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 查询当前订单的一些状态
			Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorder.getSaleorderId());
			List<SaleorderGoods> saleorderGoods = saleorderService.getSaleorderGoodsById(sale);
			BigDecimal beihuo = null;
			// 产品ID和结算价的Map
			Map<Integer, Object> goodsIdsMap = new HashMap<>();
			if (null != saleorderGoods) {
				List<Integer> goodsIds = new ArrayList<>();
				for (SaleorderGoods s : saleorderGoods) {
					goodsIds.add(s.getGoodsId());
				}
				if (goodsIds != null && !goodsIds.isEmpty()) {
					goodsIdsMap = saleorderService.getSaleorderGoodsSettlementPriceByGoodsIds(goodsIds,
							user.getCompanyId());
				}
				for (SaleorderGoods s : saleorderGoods) {
					if (goodsIdsMap != null) {
						BigDecimal settlementPrice = (BigDecimal) goodsIdsMap.get(s.getGoodsId());
						if (null != settlementPrice) {
							s.setSettlementPrice(settlementPrice);
							beihuo = new java.math.BigDecimal(1.02);
							beihuo = beihuo.setScale(2, BigDecimal.ROUND_HALF_UP);
							// 备货价>(结算价/1.02)
							if (s.getPrice()
									.compareTo(settlementPrice.divide(beihuo, 2, BigDecimal.ROUND_HALF_UP)) == 1) {
								saleorderInfo.setIsOverSettlementPrice(1);
							}
						}
					}
				}

			}

			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {
				variableMap.put("saleorderInfo", saleorderInfo);
				variableMap.put("currentAssinee", user.getUsername());
				variableMap.put("processDefinitionKey", "bhSaleorderVerify");
				variableMap.put("businessKey", "bhSaleorderVerify_" + saleorder.getSaleorderId());
				variableMap.put("relateTableKey", saleorder.getSaleorderId());
				variableMap.put("relateTable", "T_SALEORDER");
				actionProcdefService.createProcessInstance(request, "bhSaleorderVerify",
						"bhSaleorderVerify_" + saleorder.getSaleorderId(), variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"bhSaleorderVerify_" + saleorder.getSaleorderId());
			if (historicInfo.get("endStatus") != "审核完成") {
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 设置审核完成监听器回写参数
				variables.put("tableName", "T_SALEORDER");
				variables.put("id", "SALEORDER_ID");
				variables.put("idValue", saleorder.getSaleorderId());
				variables.put("key", "VALID_STATUS");
				variables.put("value", 1);
				variables.put("key1", "STATUS");
				variables.put("value1", 1);
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
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("editApplyValidBH:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 订单申请审核
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年10月24日 下午18:42:13
	 */
 	@FormToken(remove = true)
	@AntiOrderRepeat
	@ResponseBody
	@RequestMapping(value = "/editApplyValidSaleorder")
	public ResultInfo<?> editApplyValidSaleorder(HttpServletRequest request, Saleorder saleorder, String taskId,
												 HttpSession session) {
		ResultInfo<?> res = saleorderService.isvalidSaleorder(saleorder);
		if (res.getCode() == -1) {
			return res;
		}
		Map<String, Object> historicInfo3 = actionProcdefService.getHistoric(processEngine,
				"saleorderVerify_" + saleorder.getSaleorderId());
		if(historicInfo3.get("endStatus")!= null){
			if(!historicInfo3.get("endStatus").equals("驳回")){
				return new ResultInfo(-1, "该订单已处于审核流程，无法再次提交");
			}
		}
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 查询当前订单的一些状态
			Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorder.getSaleorderId());
			List<SaleorderGoods> goodsList = saleorderService.getSaleorderGoodsById(sale);
			User userInfo = userService.getUserByTraderId(saleorderInfo.getTraderId(), 1);// 1客户，2供应商
			saleorderInfo.setOptUserName(userInfo == null ? "" : userInfo.getUsername());
			// 客户联系人
			Integer traderContactId = saleorderInfo.getTraderContactId();
			TraderContact tcInfo = traderCustomerService.getTraderContactById(traderContactId);
			// 是否绑定微信
			if (tcInfo != null) {
				if (null != tcInfo.getWeixin() && tcInfo.getWeixin() != "") {
					saleorderInfo.setIsWeiXin(1);
				} else {
					saleorderInfo.setIsWeiXin(0);
				}
			}

			// 根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(saleorderInfo.getTraderId());
			// 获取产品核价信息
			List<SaleorderGoods> saleorderGoodsList = goodsChannelPriceService.getSaleChannelPriceList(
					saleorderInfo.getSalesAreaId(), saleorderInfo.getCustomerNature(), customer.getOwnership(),
					goodsList);
			Integer overLimit = 0;
			List<Integer> categoryList = new ArrayList<>();
			// 订单中产品类型（0未维护,1 只有设备,2 只有试剂,3 又有试剂又有设备）
			saleorderInfo.setGoodsType(0);
			List<Integer> goodsTypeList = new ArrayList<>();
			if (!saleorderGoodsList.isEmpty()) {
				for (SaleorderGoods s : saleorderGoodsList) {
					if (null != s.getChannelPrice() && s.getIsDelete() == 0
							&& s.getChannelPrice().compareTo(new BigDecimal(0)) != 0) {
						Double proportion = s.getPrice().divide(s.getChannelPrice(), 2, BigDecimal.ROUND_HALF_UP)
								.doubleValue();
						Double limit = proportion - 1;
						Integer b = (int) (Math.abs(limit) * 100);
						if ((int) (Math.abs(limit) * 100) > overLimit) {
							overLimit = (int) (Math.abs(limit) * 100);
						}
					}

					BigDecimal settlementPrice = saleorderService.getSaleorderGoodsSettlementPrice(s.getGoodsId(),
							user.getCompanyId());
					// 区域管制，货期大于核价货期，直发，报价小于结算价(货期暂时取消),参考成本为0
					if (s.getAreaControl().equals(1) || s.getDeliveryDirect().equals(1)
							|| (null != settlementPrice && settlementPrice.compareTo(s.getPrice()) == 1)
							|| s.getReferenceCostPrice().compareTo(BigDecimal.ZERO) == 0) {
						categoryList.add(s.getCategoryId());
					}
					if (s.getGoods() != null) {
						if (s.getIsDelete() != null && s.getIsDelete() == 0 && s.getGoods().getGoodsType() != null
								&& (s.getGoods().getGoodsType() == 316 || s.getGoods().getGoodsType() == 319)) {
							goodsTypeList.add(1);
						} else if (s.getIsDelete() != null && s.getIsDelete() == 0
								&& s.getGoods().getGoodsType() != null
								&& (s.getGoods().getGoodsType() == 317 || s.getGoods().getGoodsType() == 318)) {
							goodsTypeList.add(2);
						}
					}
				}
				if (!goodsTypeList.isEmpty()) {
					List<Integer> newList = new ArrayList(new HashSet(goodsTypeList));
					if (newList.size() == 2) {
						saleorderInfo.setGoodsType(3);
					}

					if (newList.size() == 1) {

						if (newList.get(0) == 1) {
							saleorderInfo.setGoodsType(1);
						} else if (newList.get(0) == 2) {
							saleorderInfo.setGoodsType(2);
						}

					}
				}

				// 刷新未添加产品成本人员ID集合
				List<Integer> categoryIdList = new ArrayList<>();
				for (int i = 0; i < saleorderGoodsList.size(); i++) {
					if (saleorderGoodsList.get(i).getGoods().getCategoryId() == null) {
						categoryIdList.add(0);
					} else {
						categoryIdList.add(saleorderGoodsList.get(i).getGoods().getCategoryId());
					}
				}
				categoryIdList = new ArrayList<Integer>(new HashSet<Integer>(categoryIdList));
				// 根据分类查询对应分类归属，如果是为分配的返回产品部默认人
				List<User> categoryUserList = categoryService.getCategoryOwner(categoryIdList, user.getCompanyId());
				for (int i = 0; i < saleorderGoodsList.size(); i++) {
					for (int j = 0; j < categoryUserList.size(); j++) {
						if (saleorderGoodsList.get(i).getGoods().getCategoryId() == null) {
							saleorderGoodsList.get(i).getGoods().setCategoryId(0);
						}
						if (categoryUserList.get(j).getCategoryId()
								.equals(saleorderGoodsList.get(i).getGoods().getCategoryId())) {
							saleorderGoodsList.get(i)
									.setGoodsUserIdStr((saleorderGoodsList.get(i).getGoodsUserIdStr() == null ? ""
											: saleorderGoodsList.get(i).getGoodsUserIdStr() + ";")
											+ categoryUserList.get(j).getUserId() + ";");
						}
					}
				}
				// 未填写成本价的采购人员集合
				List<String> noPriceBuyorderUser = new ArrayList<>();
				for (int i = 0; i < saleorderGoodsList.size(); i++) {
					// 参考成本为0
					if (saleorderGoodsList.get(i).getReferenceCostPrice().compareTo(BigDecimal.ZERO) == 0) {
						if (saleorderGoodsList.get(i).getGoodsUserIdStr() != null) {
							String goodsUserIdStr = saleorderGoodsList.get(i).getGoodsUserIdStr();
							List<String> goodsUserIds = Arrays.asList(goodsUserIdStr.split(";"));
							if (goodsUserIds != null && goodsUserIds.size() > 0) {
								noPriceBuyorderUser.addAll(goodsUserIds);
							}
						}
					}
				}
				// noPriceBuyorderUser 去重
				String costUserIds = "";
				if (noPriceBuyorderUser != null && noPriceBuyorderUser.size() > 0) {
					noPriceBuyorderUser = new ArrayList(new HashSet(noPriceBuyorderUser));
					costUserIds = StringUtils.join(noPriceBuyorderUser.toArray(), ",");
				}
				Saleorder saleorderData = new Saleorder();
				saleorderData.setSaleorderId(saleorder.getSaleorderId());
				saleorderData.setCostUserIds(costUserIds);
				saleorderService.saveEditSaleorderInfo(saleorderData, request, request.getSession());

			}
			saleorderInfo.setOverLimit(overLimit);
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {


				variableMap.put("supplyjudge", 1);
				// if(categoryList.isEmpty()){
				// variableMap.put("supplyjudge", 0);
				// }else{
				// variableMap.put("supplyjudge", 1);
				// }
				variableMap.put("orgId", user.getOrgId());
				variableMap.put("orderId", saleorderInfo.getSaleorderId());
				variableMap.put("saleorderInfo", saleorderInfo);
				// 如果是耗材订单，默认申请人是订单归属人
				if (null != saleorderInfo && null != saleorderInfo.getOrderType()
						&& saleorderInfo.getOrderType() == 5) {
					if (null != saleorderInfo && null != saleorderInfo.getOwnerUserId()) {
						User owenUser = userService.getUserById(saleorderInfo.getOwnerUserId());
						if (owenUser != null && owenUser.getUsername() != null) {
							// 如果是耗材传订单归属
							variableMap.put("currentAssinee", owenUser.getUsername());
						} else {
							// 默认传客户归属
							variableMap.put("currentAssinee", saleorderInfo.getOptUserName());
						}
					} else {
						// 默认传客户归属
						variableMap.put("currentAssinee", saleorderInfo.getOptUserName());
					}

				} else {
					// 默认传客户归属
					variableMap.put("currentAssinee", saleorderInfo.getOptUserName());
				}

				variableMap.put("processDefinitionKey", "saleorderVerify");
				variableMap.put("businessKey", "saleorderVerify_" + saleorder.getSaleorderId());
				variableMap.put("relateTableKey", saleorder.getSaleorderId());
				variableMap.put("relateTable", "T_SALEORDER");

				// 设置审核完成监听器回写参数
				variableMap.put("tableName", "T_SALEORDER");
				variableMap.put("id", "SALEORDER_ID");
				variableMap.put("idValue", saleorderInfo.getSaleorderId());
				variableMap.put("key", "VALID_STATUS");
				variableMap.put("value", 1);
				variableMap.put("key1", "STATUS");
				variableMap.put("value1", 1);
				// 回写数据的表在db中
				variableMap.put("db", 2);
				actionProcdefService.createProcessInstance(request, "saleorderVerify",
						"saleorderVerify_" + saleorder.getSaleorderId(), variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"saleorderVerify_" + saleorder.getSaleorderId());
			if (historicInfo.get("endStatus") != "审核完成") {
				// 非订货订单时
				if (saleorderInfo.getOrderType() != 3) {
					actionProcdefService.updateInfo("T_SALEORDER", "SALEORDER_ID", saleorderInfo.getSaleorderId(),
							"LOCKED_STATUS", 1, 2);
					Saleorder saleorderLocked = new Saleorder();
					saleorderLocked.setLockedReason("订单审核");
					saleorderLocked.setSaleorderId(saleorderInfo.getSaleorderId());
					saleorderService.saveEditSaleorderInfo(saleorderLocked, request, request.getSession());
				}
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 默认审批通过
				variables.put("pass", true);
				// 如果是耗材订单，默认申请人是订单归属人
				ResultInfo<?> complementStatus = new ResultInfo<>();
				if (null != saleorderInfo && null != saleorderInfo.getOrderType()
						&& saleorderInfo.getOrderType() == 5) {
					if (null != saleorderInfo && null != saleorderInfo.getOwnerUserId()) {
						User owenUser = userService.getUserById(saleorderInfo.getOwnerUserId());
						if (owenUser != null && owenUser.getUsername() != null) {
							// 如果是耗材传订单归属
							complementStatus = actionProcdefService.complementTask(request, taskId, "",
									owenUser.getUsername(), variables);
						} else {
							// 默认传客户归属
							complementStatus = actionProcdefService.complementTask(request, taskId, "",
									saleorderInfo.getOptUserName(), variables);
						}
					} else {
						// 默认传客户归属
						complementStatus = actionProcdefService.complementTask(request, taskId, "",
								saleorderInfo.getOptUserName(), variables);
					}

				} else {
					// 默认传客户归属
					complementStatus = actionProcdefService.complementTask(request, taskId, "",
							saleorderInfo.getOptUserName(), variables);
				}

				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("editApplyValidSaleorder:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 审核弹层页面
	 *
	 * @param session
	 * @param taskId
	 * @param pass
	 * @param type
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月5日 下午3:16:51 as to 20190115 添加参数saleorderId用于同步订单状态
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session, String taskId, Boolean pass, Integer type,
								   Integer saleorderId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("saleorderId", saleorderId);
		mv.addObject("pass", pass);
		mv.addObject("type", type);
		mv.setViewName("order/saleorder/complement");
		return mv;
	}

	/**
	 * <b>Description:</b>获取订单中客户资质审核状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/18
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderTraderAptitudeStatus")
	public ResultInfo getOrderTraderAptitudeStatus(Integer saleorderId){
		if(saleorderId==null){
			return new ResultInfo(-1,"订单id不得为空");
		}
		Saleorder saleorder=saleorderMapper.getSaleOrderById(saleorderId);
		if(saleorder==null||saleorder.getTraderId()==null){
			return new ResultInfo(-1,"订单信息不完整");
		}
		TraderCustomer traderCustomer=traderCustomerService.getSimpleCustomer(saleorder.getTraderId());
		if(traderCustomer==null||traderCustomer.getTraderCustomerId()==null){
			return new ResultInfo(-1,"客户信息不存在");
		}
		TraderCustomerVo traderCustomerVo=new TraderCustomerVo();
		traderCustomerVo.setTraderId(traderCustomer.getTraderId());
		traderCustomerVo.setTraderCustomerId(traderCustomer.getTraderCustomerId());
		VerifiesInfo verifiesInfo=traderCustomerService.getCustomerAptitudeVerifiesInfo(traderCustomer.getTraderCustomerId());
		if(verifiesInfo!=null&&verifiesInfo.getStatus()!=null){
			traderCustomerVo.setAptitudeStatus(verifiesInfo.getStatus());
		}
		return new ResultInfo(0,"操作成功",traderCustomerVo);
	}

	/**
	 * <b>Description:</b>订单审核操作<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/18
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/checkSaleorderFlow")
	public ResultInfo checkSaleorderFlow(HttpServletRequest request, String taskId, String comment, Boolean pass,Integer saleorderId,
										 HttpSession session){
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("pass", pass);
			variables.put("updater", user.getUserId());
			ResultInfo<?> complementStatus = null;
			int statusInfo = 0;
			if (!pass) {
				variables.put("db", 2);
				TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
				Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
				String tableName = (String) taskService.getVariable(taskId, "tableName");
				actionProcdefService.updateInfo(tableName, "SALEORDER_ID", idValue, "LOCKED_STATUS", 0, 2);
				statusInfo=2;
				verifiesRecordService.saveVerifiesInfo(taskId, statusInfo);
				complementStatus = actionProcdefService.complementTask(request, taskId, comment,
						user.getUsername(), variables);
				return new ResultInfo(0, "操作成功");
			} else {
				TaskService taskService = processEngine.getTaskService();
				HistoryService historyService = processEngine.getHistoryService(); // 任务相关service
				String processInstanceId = null;
				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
						.taskId(taskId).singleResult();
				processInstanceId = historicTaskInstance.getProcessInstanceId();
				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
				String businessKey = historicProcessInstance.getBusinessKey();

				// 获取当前活动节点
				Task taskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
				Saleorder saleorder = saleorderMapper.getSaleOrderById(saleorderId);
				if (taskInfo == null) {
					return new ResultInfo<>(-1, "流程此节点已操作完成，请确认");
				}
				if (OrderGoodsAptitudeConstants.KEY_SALE_MANAGER.equals(taskInfo.getName())) {
					variables.put(OrderGoodsAptitudeConstants.KEY_AUTO_CHECK_APTITUDE,false);
					if (saleorder != null && saleorder.getTraderId() != null) {
						TraderCustomer traderCustomer = traderCustomerService.getSimpleCustomer(saleorder.getTraderId());
						if (traderCustomer != null && traderCustomer.getTraderCustomerId() != null) {
							VerifiesInfo verifiesInfo = traderCustomerService.getCustomerAptitudeVerifiesInfo(traderCustomer.getTraderCustomerId());
							if (verifiesInfo != null && OrderGoodsAptitudeConstants.APTITTUDE_IS_PASSED.equals(verifiesInfo.getStatus())) {
								variables.remove("pass");
								variables.put(OrderGoodsAptitudeConstants.KEY_AUTO_CHECK_APTITUDE, true);
							}
						}
					}
					complementStatus = actionProcdefService.complementTask(request, taskId, comment,
							user.getUsername(), variables);
					Task autoTaskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
					if (autoTaskInfo == null) {
						return new ResultInfo<>(-1, "流程此节点已操作完成，请确认");
					}
					if (OrderGoodsAptitudeConstants.KEY_AUTOCHECK_APTITUDE.equals(autoTaskInfo.getName())) {
						ResultInfo resultInfo = saleorderService.checkGoodAptitude(saleorderId);
						boolean autoPass = false;
						if (resultInfo.getCode() == 0) {
							changeSaleorderDeliverState(saleorder);
							autoPass = true;
						}else if(resultInfo.getCode()==-1){
							comment=resultInfo.getMessage();
						}
						variables.put("pass", autoPass);
						complementStatus = actionProcdefService.complementTask(request, autoTaskInfo.getId(), comment,
								"njadmin", variables);
					}
				}else if(OrderGoodsAptitudeConstants.KEY_OPERATE_CHECK.equals(taskInfo.getName())){
					complementStatus = actionProcdefService.complementTask(request, taskId, comment,
							user.getUsername(), variables);
					changeSaleorderDeliverState(saleorder);
				}
				if (complementStatus != null) {
					// 审核节点操作失败
					if(complementStatus.getCode().equals(-1)){
						return complementStatus;
					}
					if(!complementStatus.getData().equals("endEvent")){
						verifiesRecordService.saveVerifiesInfo(taskId, statusInfo);
					}
				}
				return new ResultInfo(0, "操作成功");
			}
		}catch (Exception ex){
			logger.error("订单审核报错 ",ex);

		}
		return new ResultInfo(-1,"操作失败");
	}

	/**
	 * <b>Description:</b>改变订单的直发状态，并给bd订单发消息<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/18
	 */
	private void changeSaleorderDeliverState(Saleorder s){
		try {
			saleorderService.updateOrderdeliveryDirect(s);
			if (s.getOrderType() == 1) {
				JSONObject result2 = saleorderService.updateVedengJX(s.getSaleorderId());
				Map sTempMap = null;
//				ResultInfo senResult = expressService.sendWxMessageForArrival(s.getSaleorderId());
//				if (null != senResult) {
//					sTempMap = (Map) senResult.getData();
//					sendOrderConfirmedMsg(s, sTempMap);
//				}


			}
			//调用库存服务
			int i = warehouseStockService.updateOccupyStockService(s, 0);
		}catch (Exception ex){}
	}
	/**
	 * <b>Description:</b><br>
	 * 订单并行审核操作
	 *
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/complementTaskParallel")
	public ResultInfo<?> complementTaskParallel(HttpServletRequest request, String taskId, String comment, Boolean pass,
												HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("updater", user.getUserId());
		variables.put("autoCheckAptitude",false);
		// 审批操作
		try {
			Integer statusInfo = 0;
			if (pass) {
				// 如果审核通过
				statusInfo = 0;
			} else {
				// 如果审核不通过
				statusInfo = 2;
				// 回写数据的表在db中
				variables.put("db", 2);
				TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
				Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
				String tableName = (String) taskService.getVariable(taskId, "tableName");
				if (tableName != null && tableName.equals("T_SALEORDER_MODIFY_APPLY")) {
					Integer idValue1 = (Integer) taskService.getVariable(taskId, "idValue1");
					actionProcdefService.updateInfo("T_SALEORDER", "SALEORDER_ID", idValue1, "LOCKED_STATUS", 0, 2);
				} else {
					if (tableName != null) {
						actionProcdefService.updateInfo(tableName, "SALEORDER_ID", idValue, "LOCKED_STATUS", 0, 2);
					}
				}
				verifiesRecordService.saveVerifiesInfo(taskId, statusInfo);
			}
			ResultInfo<?> complementStatus = null;
			if (pass) {
				TaskService taskService = processEngine.getTaskService();
				HistoryService historyService = processEngine.getHistoryService(); // 任务相关service
				String processInstanceId = null;
				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
						.taskId(taskId).singleResult();
				processInstanceId = historicTaskInstance.getProcessInstanceId();
				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
				String businessKey = historicProcessInstance.getBusinessKey();
				List<HistoricVariableInstance> historicVariableInstanceList = historyService
						.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
				// 把list转为Map
				Map<String, Object> variablesMap = new HashMap<String, Object>();
				;
				for (HistoricVariableInstance hvi : historicVariableInstanceList) {
					variablesMap.put(hvi.getVariableName(), hvi.getValue());
				}

				// 获取当前活动节点
				Task taskInfo = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
				if(taskInfo == null){
					return new ResultInfo<>(-1, "流程此节点已操作完成，请确认");
				}
				if (taskInfo.getName().equals("产品线归属人审核")) {
					// 并行节点
					List<IdentityLink> candidateUserList = null;
					if (null == variablesMap.get("candidateUserList")) {
						// 待审核人员
						candidateUserList = taskService.getIdentityLinksForTask(taskId);
						// 存入全局变量里
						taskService.setVariable(taskId, "candidateUserList", candidateUserList);
					} else {
						// 待审核人员
						candidateUserList = (List<IdentityLink>) variablesMap.get("candidateUserList");
					}
					if (null == variablesMap.get("verifyUserList")) {
						// 存入全局变量里
						taskService.setVariable(taskId, "verifyUserList", user.getUsername());
						if (!candidateUserList.isEmpty()) {
							List<String> candidateUsers = new ArrayList<>();
							for (IdentityLink il : candidateUserList) {
								candidateUsers.add(il.getUserId());
							}
							String candidateUser = StringUtils.join(candidateUsers.toArray(), ",");
							if (candidateUser.equals(user.getUsername())) {
								complementStatus = actionProcdefService.complementTask(request, taskId, comment,
										user.getUsername(), variables);
							}
						} else {
							complementStatus = actionProcdefService.complementTask(request, taskId, comment,
									user.getUsername(), variables);
						}
					} else {
						String verifyUser = (String) variablesMap.get("verifyUserList") + "," + user.getUsername();
						List<String> verifyUserList = Arrays.asList(verifyUser.split(","));
						verifyUserList = new ArrayList(new HashSet(verifyUserList));
						verifyUser = StringUtils.join(verifyUserList, ",");
						// 已经审核的人存入全局变量里
						taskService.setVariable(taskId, "verifyUserList", verifyUser);
						if (!candidateUserList.isEmpty()) {
							Integer length = 0;
							List<String> candidateUsers = new ArrayList<>();
							for (IdentityLink il : candidateUserList) {
								candidateUsers.add(il.getUserId());
							}
							for (String v : verifyUserList) {
								for (String c : candidateUsers) {
									if (c.equals(v)) {
										length++;
									}
								}
							}
							if (candidateUsers.size() == length) {
								complementStatus = actionProcdefService.complementTask(request, taskId, comment,
										user.getUsername(), variables);
							}
						} else {
							complementStatus = actionProcdefService.complementTask(request, taskId, comment,
									user.getUsername(), variables);
						}
					}
				} else {
					// 非并行节点
					complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(),
							variables);
				}
				//Bd订单审核通过更新精选数据
				String[] split = businessKey.split("_");
				if(split[0].equals("saleorderVerify")&&taskInfo.getName().equals("运营部审核")) {
					Integer saleorderId = Integer.valueOf(split[1]);
					Saleorder s = saleorderMapper.getSaleOrderById(saleorderId);
					//VDERP-1511销售单生效时，根据订单里是否有直发商品，改变订单的直发状态
					saleorderService.updateOrderdeliveryDirect(s);
					if(s.getOrderType()==1) {
						JSONObject result2 = saleorderService.updateVedengJX(saleorderId);
						 Map sTempMap = null;
						 ResultInfo senResult = expressService.sendWxMessageForArrival(saleorderId);
							 if(null != senResult) {
								 sTempMap = (Map)senResult.getData();
								 // add by Tomcat.Hui 2020/3/11 1:43 下午 .Desc: VDERP-2067 删除用户确认流程 删除微信推送. start
								 //sendOrderConfirmedMsg(s,sTempMap);
								 // add by Tomcat.Hui 2020/3/11 1:43 下午 .Desc: VDERP-2067 删除用户确认流程 删除微信推送. end
							 }


					}
					//调用库存服务
					int i = warehouseStockService.updateOccupyStockService(s,0);
					//VDERP-2263   订单售后采购改动通知
					warehouseStockService.updateSaleOrderDataUpdateTime(saleorderId,null, OrderDataUpdateConstant.SALE_ORDER_VAILD);
				}


				if(split[0].equals("editSaleorderVerify")&&taskInfo.getName().equals("物流审核")) {
					Integer orderId = (Integer) variablesMap.get("orderId");
					Saleorder saleorder = saleorderMapper.getSaleOrderById(orderId);
					if(saleorder.getOrderType()==1) {
						JSONObject result2 = saleorderService.ChangeEditSaleOrder(saleorder);
					}
					//调用库存服务
					int i = warehouseStockService.updateOccupyStockService(saleorder,0);
					//VDERP-2263   订单售后采购改动通知
					warehouseStockService.updateSaleOrderDataUpdateTime(orderId,null,OrderDataUpdateConstant.SALE_ORDER_VAILD);
				}
			} else {
				complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(),
						variables);
			}
			// 如果未结束添加审核对应主表的审核状态
			if (complementStatus != null) {
				// 审核节点操作失败
				if(complementStatus.getCode().equals(-1)){
					return complementStatus;
				}
				if(!complementStatus.getData().equals("endEvent")){
					verifiesRecordService.saveVerifiesInfo(taskId, statusInfo);
				}
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("complementTaskParallel:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 订单审核操作
	 *
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	public ResultInfo<?> complementTask(HttpServletRequest request, String taskId, String comment, Boolean pass,
										HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		// 审批操作
		try {
			Integer status = 0;
			if (pass) {
				// 如果审核通过
				status = 0;
			} else {
				// 如果审核不通过
				status = 2;
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}

			ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment,
					user.getUsername(), variables);
			// 如果未结束添加审核对应主表的审核状态
			if (!complementStatus.getData().equals("endEvent")) {
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 订单售后审核操作
	 *
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/complementAfterSaleTask")
	public ResultInfo<?> complementAfterSaleTask(HttpServletRequest request, String taskId, String comment,
												 Boolean pass, HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		// 审批操作
		try {
			if (!pass) {
				// 如果审核通过
				TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
				taskService.setVariable(taskId, "value", 3);
				String tableName = (String) taskService.getVariable(taskId, "tableName");
				String id = (String) taskService.getVariable(taskId, "id");
				Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
				String key = (String) taskService.getVariable(taskId, "key");
				if (tableName != null && id != null && idValue != null && key != null) {
					actionProcdefService.updateInfo(tableName, id, idValue, key, 3, 2);
				}
				verifiesRecordService.saveVerifiesInfo(taskId, 2);
			}
			ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment,
					user.getUsername(), variables);
			// 如果审核没结束添加审核对应主表的审核状态
			if (!complementStatus.getData().equals("endEvent")) {
				Integer status = 0;
				if (pass) {
					// 如果审核通过
					status = 0;
				} else {
					// 如果审核不通过
					status = 2;
				}
				verifiesRecordService.saveVerifiesInfo(taskId, status);

			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("complementAfterSaleTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 销售售后申请审核
	 *
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月19日 下午4:14:13
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/editApplyAudit")
	public ResultInfo<?> editApplyAudit(HttpServletRequest request, AfterSalesVo afterSales, String taskId) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSales.setTraderType(1);
		afterSales.setStatus(1);// 审核中
		// afterSales.setAtferSalesStatus(1);// 进行中
		afterSales.setCompanyId(user.getCompanyId());
		afterSales.setModTime(DateUtil.sysTimeMillis());
		afterSales.setUpdater(user.getUserId());
		ResultInfo<?> res = afterSalesOrderService.editApplyAudit(afterSales);
		if (res == null) {
			return new ResultInfo<>();
		}
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			// 查询当前订单的一些状态
			AfterSalesVo afterSalesInfo = afterSalesOrderService.getAfterSalesVoDetail(afterSales);
			// 订单中产品类型（0未维护,1 只有设备,2 只有试剂,3 又有试剂又有设备）
			afterSalesInfo.setGoodsType(0);
			List<Integer> goodsTypeList = new ArrayList<>();
			if (afterSalesInfo.getAfterSalesGoodsList() != null && !afterSalesInfo.getAfterSalesGoodsList().isEmpty()) {
				for (AfterSalesGoodsVo asgv : afterSalesInfo.getAfterSalesGoodsList()) {
					if (asgv.getGoodsType() != null && (asgv.getGoodsType() == 316 || asgv.getGoodsType() == 319)) {
						goodsTypeList.add(1);
					} else if (asgv.getGoodsType() != null
							&& (asgv.getGoodsType() == 317 || asgv.getGoodsType() == 318)) {
						goodsTypeList.add(2);
					}
				}

				if (!goodsTypeList.isEmpty()) {
					List<Integer> newList = new ArrayList(new HashSet(goodsTypeList));
					if (newList.size() == 2) {
						afterSalesInfo.setGoodsType(3);
					}

					if (newList.size() == 1) {

						if (newList.get(0) == 1) {
							afterSalesInfo.setGoodsType(1);
						} else if (newList.get(0) == 2) {
							afterSalesInfo.setGoodsType(2);
						}

					}
				}
			}
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {
				variableMap.put("afterSalesInfo", afterSalesInfo);
				variableMap.put("currentAssinee", user.getUsername());
				variableMap.put("processDefinitionKey", "afterSalesVerify");
				variableMap.put("businessKey", "afterSalesVerify_" + afterSalesInfo.getAfterSalesId());
				variableMap.put("relateTableKey", afterSalesInfo.getAfterSalesId());
				variableMap.put("relateTable", "T_AFTER_SALES");
				variableMap.put("orgId", user.getOrgId());
				actionProcdefService.createProcessInstance(request, "afterSalesVerify",
						"afterSalesVerify_" + afterSalesInfo.getAfterSalesId(), variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"afterSalesVerify_" + afterSalesInfo.getAfterSalesId());
			if (historicInfo.get("endStatus") != "审核完成") {
				// 修改锁定备注
				Saleorder saleorderLocked = new Saleorder();
				saleorderLocked.setLockedReason("售后锁定");
				saleorderLocked.setSaleorderId(afterSalesInfo.getOrderId());
				saleorderService.saveEditSaleorderInfo(saleorderLocked, request, request.getSession());
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 设置审核完成监听器回写参数
				variables.put("tableName", "T_AFTER_SALES");
				variables.put("id", "AFTER_SALES_ID");
				variables.put("idValue", afterSalesInfo.getAfterSalesId());
				variables.put("key", "STATUS");
				variables.put("value", 2);
				variables.put("key1", "ATFER_SALES_STATUS");
				variables.put("value1", 1);
				variables.put("key2", "VALID_STATUS");
				variables.put("value2", 1);
				// 回写数据的表在db中
				variables.put("db", 2);
				// 默认审批通过
				ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
						user.getUsername(), variables);
				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}

				// 同步订单的状态
				/*
				 * Saleorder saleorder = new Saleorder();
				 * saleorder.setSaleorderId(afterSalesInfo.getOrderId());
				 * saleorder.setCompanyId(user.getCompanyId());
				 * saleorderService.synchronousOrderStatus(request,saleorder);
				 */
			} else {

			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("editApplyAudit:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到售后编辑页
	 *
	 * @param request
	 * @param afterSales
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月20日 上午9:00:02
	 */
	@ResponseBody
	@RequestMapping(value = "/editAfterSalesPage")
	public ModelAndView editAfterSalesPage(HttpServletRequest request, AfterSalesVo afterSales) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		if (afterSales == null) {
			return pageNotFound();
		}
		String sku = afterSales.getSku();
		afterSales.setTraderType(1);
		afterSales.setCompanyId(user.getCompanyId());
		afterSales = afterSalesOrderService.getAfterSalesVoDetail(afterSales);

		afterSales.setSku(sku);

		mav.addObject("afterSales", afterSales);

		// 获取退货原因
		List<SysOptionDefinition> sysList = new ArrayList<>();
		// if (afterSales.getSubjectType() == 908){
		// sysList = getSysOptionDefinitionList(908);
		// }else {
		sysList = getSysOptionDefinitionList(535);
		// }
		mav.addObject("sysList", sysList);
		mav.addObject("domain", domain);

		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(afterSales.getOrderId());
		if (afterSales.getType() == 539) {
			saleorder.setFlag("th");
		} else if (afterSales.getType() == 540) {
			saleorder.setFlag("hh");
		} else if (afterSales.getType() == 541) {
			saleorder.setFlag("at");
		} else if (afterSales.getType() == 584) {
			saleorder.setFlag("wx");
		} else if (afterSales.getType() == 542) {
			saleorder.setFlag("tp");
		} else if (afterSales.getType() == 543) {
			saleorder.setFlag("tk");
		} else if (afterSales.getType() == 544) {
			saleorder.setFlag("jz");
		} else if (afterSales.getType() == 545) {
			saleorder.setFlag("qt");
		}
		saleorder.setCompanyId(user.getCompanyId());
		saleorder.seteFlag("edit");
		SaleorderVo sv = saleorderService.getSaleorderGoodsVoList(saleorder);
		mav.addObject("saleorder", sv);

		if (afterSales.getType() == 545) {// 售后其他
			mav.setViewName("order/saleorder/edit_afterSales_qt");
			return mav;
		}
		if (afterSales.getType() == 539) {// 退货
			mav.setViewName("order/saleorder/edit_afterSales_th");
		} else if (afterSales.getType() == 540) {
			mav.setViewName("order/saleorder/edit_afterSales_hh");
		} else if (afterSales.getType() == 541 || afterSales.getType() == 584) {
			Integer areaId = afterSales.getAreaId();
			if (ObjectUtils.notEmpty(areaId)) {
				Region region = regionService.getRegionByRegionId(areaId);
				if (region.getRegionType() == 1) {// 省级
					mav.addObject("province", areaId);
				} else if (region.getRegionType() == 2) {
					List<Region> cityList = regionService.getRegionByParentId(region.getParentId());
					mav.addObject("cityList", cityList);
					mav.addObject("city", areaId);
					mav.addObject("province", region.getParentId());
				} else if (region.getRegionType() == 3) {
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
			if (afterSales.getType() == 541) {
				mav.setViewName("order/saleorder/edit_afterSales_at");
			} else {
				mav.setViewName("order/saleorder/edit_afterSales_wx");
			}
		} else if (afterSales.getType() == 542) {
			mav.setViewName("order/saleorder/edit_afterSales_tp");
		} else if (afterSales.getType() == 543) {
			mav.setViewName("order/saleorder/edit_afterSales_tk");
		} else if (afterSales.getType() == 544) {
			mav.setViewName("order/saleorder/edit_afterSales_jz");
		}
		// else if(afterSales.getType() == 909){
		// mav.setViewName("order/saleorder/edit_hcafterSales_th");
		// } else if(afterSales.getType() == 910){
		// mav.setViewName("order/saleorder/edit_hcafterSales_hh");
		// }
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 销售订单全部录保卡/未录保卡的产品
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月20日 下午2:47:50
	 */
	@ResponseBody
	@RequestMapping(value = "/allgoodswarranty")
	public ModelAndView allGoodsWarranty(HttpServletRequest request, Saleorder saleorder) {
		if (null == saleorder || saleorder.getSaleorderId() == null || saleorder.getSaleorderId() <= 0) {
			return pageNotFound();
		}
		ModelAndView mav = new ModelAndView("order/saleorder/all_saleordergoodswarrantys");

		List<SaleorderGoodsWarrantyVo> goodsWarrantys = saleorderService.getAllSaleorderGoodsWarrantys(saleorder);
		mav.addObject("goodsWarrantys", goodsWarrantys);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增保卡
	 *
	 * @param request
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月20日 下午4:21:44
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addgoodswarranty")
	public ModelAndView addGoodsWarranty(HttpServletRequest request, HttpSession session,
										 SaleorderGoodsWarrantyVo goodsWarrantyVo) {
		ModelAndView mav = new ModelAndView("order/saleorder/add_saleordergoodswarranty");

		SaleorderGoodsWarrantyVo goodsWarrantyInfo = saleorderService.getSaleorderGoodsInfoForWarranty(goodsWarrantyVo);
		mav.addObject("goodsWarrantyInfo", goodsWarrantyInfo);
		mav.addObject("domain", domain);
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		if (null != goodsWarrantyInfo.getSalesAreaId() && goodsWarrantyInfo.getSalesAreaId() > 0) {

			Integer areaId = goodsWarrantyInfo.getSalesAreaId();
			List<Region> regionList = (List<Region>) regionService.getRegion(areaId, 1);

			if (null != regionList && regionList.size() > 0) {
				for (Region r : regionList) {
					switch (r.getRegionType()) {
						case 1:
							List<Region> cityList = regionService.getRegionByParentId(r.getRegionId());
							mav.addObject("provinceRegion", r);
							mav.addObject("cityList", cityList);
							break;
						case 2:
							List<Region> zoneList = regionService.getRegionByParentId(r.getRegionId());
							mav.addObject("cityRegion", r);
							mav.addObject("zoneList", zoneList);
							break;
						case 3:
							mav.addObject("zoneRegion", r);
							break;
						default:
							mav.addObject("countryRegion", r);
							break;
					}
				}
			}
		}
		mav.addObject("provinceList", provinceList);

		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		mav.addObject("user", user);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑售后
	 *
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月11日 下午1:27:56
	 */
	@ResponseBody
	@RequestMapping(value = "/saveEditAfterSales")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑售后")
	public ModelAndView saveEditAfterSales(HttpServletRequest request, AfterSalesVo afterSalesVo,
										   @RequestParam(required = false, value = "afterSaleNums") String[] afterSaleNums,
										   @RequestParam(required = false, value = "fileName") String[] fileName,
										   @RequestParam(required = false, value = "fileUri") String[] fileUri,
										   @RequestParam(required = false, value = "invoiceIds") String[] invoiceIds) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSalesVo.setAfterSalesNum(afterSaleNums);
		afterSalesVo.setAttachName(fileName);
		afterSalesVo.setAttachUri(fileUri);
		afterSalesVo.setDomain(domain);
		afterSalesVo.setInvoiceIds(invoiceIds);
		afterSalesVo.setTraderType(1);
		ModelAndView mav = new ModelAndView();
		ResultInfo<?> res = afterSalesOrderService.saveEditAfterSales(afterSalesVo, user);
		// mav.addObject("refresh", "true_false_true");//
		// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		// mav.addObject("url", "./view.do?saleorderId=" +
		// afterSalesVo.getOrderId());
		if (res.getCode() == 0) {
			mav.addObject("url", "./viewAfterSalesDetail.do?afterSalesId=" + res.getData());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 关闭售后订单
	 *
	 * @param request
	 * @param afterSalesVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月20日 下午5:44:00
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveCloseAfterSales")
	// @SystemControllerLog(operationType = "edit", desc = "关闭售后订单")
	public ResultInfo<?> saveCloseAfterSales(HttpServletRequest request, AfterSalesVo afterSalesVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Integer position = 0;
		if (user.getPositions() != null) {
			position = user.getPositions().get(0).getType();
		}
		AfterSalesVo afterSales = new AfterSalesVo();
		afterSales.setAfterSalesId(afterSalesVo.getAfterSalesId());
		afterSales.setCompanyId(user.getCompanyId());
		// 如果是销售订单售后
		if (afterSalesVo.getType() != null && (afterSalesVo.getType().equals(539) || afterSalesVo.getType().equals(540)
				|| afterSalesVo.getType().equals(541) || afterSalesVo.getType().equals(542)
				|| afterSalesVo.getType().equals(543) || afterSalesVo.getType().equals(544)
				|| afterSalesVo.getType().equals(545) || afterSalesVo.getType().equals(584))) {
			// 客户
			afterSales.setTraderType(1);

		} else if (afterSalesVo.getType() != null
				&& (afterSalesVo.getType().equals(546) || afterSalesVo.getType().equals(547)
				|| afterSalesVo.getType().equals(548) || afterSalesVo.getType().equals(549))) {
			// 供应商
			afterSales.setTraderType(2);
		}
		AfterSalesVo afterSalesInfo = afterSalesOrderService.getAfterSalesVoDetail(afterSales);
		afterSalesVo.setAfterSalesNo(afterSalesInfo.getAfterSalesNo());
		// 如果是生效的售后单关闭需要审核
		if (afterSalesInfo.getValidStatus().equals(1) && !afterSalesInfo.getSubjectType().equals(536)
				&& position != 310) {
			//// 将关闭原因，关闭备注，关闭人员存在数据库
			afterSalesVo.setAfterSalesStatusUser(user.getUserId());
			ResultInfo<?> res = afterSalesOrderService.updateAfterSalesById(afterSalesVo);
			// 对售后单的流程判断金额进行计算
			// 销售售后
			if (afterSalesInfo.getSubjectType().equals(535)) {
				// 销售订单退货
				if (afterSalesInfo.getType().equals(539)) {
					if (afterSalesInfo.getRefundAmount() != null) {
						afterSalesVo.setOrderTotalAmount(afterSalesInfo.getRefundAmount());
					} else {
						afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
					}
					// 销售订单换货
				} else if (afterSalesInfo.getType().equals(540)) {
					
					//关联外借单
					List<LendOut> lendoutList = warehouseOutService.getLendOutInfoList(afterSalesVo);
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
						afterSalesVo.setOrderTotalAmount(totalAmount);
					} else {
						afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
					}
					// 销售订单退款
				} else if (afterSalesInfo.getType().equals(543)) {
					if (afterSalesInfo.getRefundAmount() != null) {
						afterSalesVo.setOrderTotalAmount(afterSalesInfo.getRefundAmount());
					} else {
						afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
					}
					// 销售订单维修
				} else if (afterSalesInfo.getType().equals(584)) {
					if (afterSalesInfo.getServiceAmount() != null) {
						afterSalesVo.setOrderTotalAmount(afterSalesInfo.getServiceAmount());
					} else {
						afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
					}
				} else {
					// 其他
					afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
				}
				// 第三方售后
			} else if (afterSalesInfo.getSubjectType().equals(537)) {
				// 第三方退款
				if (afterSalesInfo.getType().equals(551)) {
					if (afterSalesInfo.getRefundAmount() != null) {
						afterSalesVo.setOrderTotalAmount(afterSalesInfo.getRefundAmount());
					} else {
						afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
					}
					// 第三方维修
				} else if (afterSalesInfo.getType().equals(585)) {
					if (afterSalesInfo.getServiceAmount() != null) {
						afterSalesVo.setOrderTotalAmount(afterSalesInfo.getServiceAmount());
					} else {
						afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
					}
					// 其他
				} else {
					afterSalesVo.setOrderTotalAmount(BigDecimal.ZERO);
				}
			}
			try {
				// 售后单关闭
				afterSalesVo.setVerifiesType(0);
				Map<String, Object> variableMap = new HashMap<String, Object>();
				// 开始生成流程(如果没有taskId表示新流程需要生成)
				variableMap.put("afterSalesVo", afterSalesVo);
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
				}
				return new ResultInfo(0, "操作成功");
			} catch (Exception e) {
				logger.error("saveCloseAfterSales:", e);
				return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
			}
		} else {
			ResultInfo<?> res = afterSalesOrderService.saveCloseAfterSales(afterSalesVo, user);

			// 查看当前订单是否有其他售后单
			afterSalesVo.setTraderType(1);
			AfterSalesVo asVo = afterSalesOrderService.getAfterSalesVoDetail(afterSalesVo);

			// 判断是否是耗材订单
			if (asVo.getSource() == 1) {
				Map<String, Object> param = new HashMap<String, Object>();
				// 查询当前订单的售后列表
				List<AfterSalesVo> afterSalesList = afterSalesOrderService.getAfterSalesVoListByOrderId(asVo);

				if (afterSalesList.size() > 0) {
					int num = 0;
					for (AfterSalesVo asv : afterSalesList) {
						// 如果有待确认和进行中的订单
						if (asv.getAtferSalesStatus() == 0 || asv.getAtferSalesStatus() == 1) {
							num++;
						}
					}

					if (num > 0) {
						param.put("isRefund", 1);
					} else {
						param.put("isRefund", 0);
					}
				} else {
					param.put("isRefund", 0);
				}

				// 请求头
				Map<String, String> header = new HashMap<String, String>();
				header.put("version", "v1");

				param.put("orderNo", asVo.getOrderNo());
				JSONObject jsonObject = JSONObject.fromObject(param);
				// 定义反序列化 数据格式
				String url = apiUrl + "/order/after/completeBackMoney";
				try {
					final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
					};
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.put(url, jsonObject.toString(), header,
							TypeRef);

				}catch (Exception e){
					logger.error("saveCloseAfterSales 2:", e);
				}
			}

			if (res == null) {
				return new ResultInfo<>();
			}
			return res;
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 保存录保卡
	 *
	 * @param request
	 * @param session
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月23日 下午1:11:41
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveaddgoodswarranty")
	@SystemControllerLog(operationType = "add", desc = "保存录保卡")
	public ModelAndView saveAddGoodsWarranty(HttpServletRequest request, HttpSession session,
											 SaleorderGoodsWarrantyVo goodsWarrantyVo, @RequestParam(required = false, value = "fileUri") String fileUri,
											 @RequestParam(required = false, value = "fileName") String fileName) {
		ModelAndView mav = new ModelAndView();
		goodsWarrantyVo.setUri(fileUri);
		goodsWarrantyVo.setDomain(domain);
		goodsWarrantyVo.setFileName(fileName);
		SaleorderGoodsWarrantyVo warrantyVo = saleorderService.saveAddGoodsWarranty(request, session, goodsWarrantyVo);
		if (null != warrantyVo) {
			mav.addObject("refresh", "true_false_true");// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
			mav.addObject("url", "./allgoodswarranty.do?saleorderId=" + goodsWarrantyVo.getSaleorderId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看保卡信息
	 *
	 * @param request
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月23日 下午2:35:14
	 */
	@ResponseBody
	@RequestMapping(value = "/viewgoodswarrantyNew")
	public ModelAndView viewGoodsWarranty(HttpServletRequest request, SaleorderGoodsWarrantyVo goodsWarrantyVo) {
		ModelAndView mav = new ModelAndView();
		if (null == goodsWarrantyVo || goodsWarrantyVo.getSaleorderGoodsWarrantyId() == null
				|| goodsWarrantyVo.getSaleorderGoodsWarrantyId() <= 0) {
			return pageNotFound();
		}

		SaleorderGoodsWarrantyVo saleorderGoodsWarranty = saleorderService.getGoodsWarrantyInfoNew(goodsWarrantyVo);
		mav.setViewName("order/saleorder/view_saleordergoodswarranty");
		mav.addObject("goodsWarrantyInfo", saleorderGoodsWarranty);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑保卡
	 *
	 * @param request
	 * @param session
	 * @param goodsWarrantyVo
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月24日 上午11:38:28
	 */
	@ResponseBody
	@RequestMapping(value = "/editgoodswarranty")
	public ModelAndView editGoodsWarranty(HttpServletRequest request, HttpSession session,
										  SaleorderGoodsWarrantyVo goodsWarrantyVo) {
		ModelAndView mav = new ModelAndView("order/saleorder/edit_saleordergoodswarranty");
		if (null == goodsWarrantyVo || goodsWarrantyVo.getSaleorderGoodsWarrantyId() == null
				|| goodsWarrantyVo.getSaleorderGoodsWarrantyId() <= 0) {
			return pageNotFound();
		}
		SaleorderGoodsWarrantyVo goodsWarrantyInfo = saleorderService.getGoodsWarrantyInfo(goodsWarrantyVo);
		mav.addObject("goodsWarrantyInfo", goodsWarrantyInfo);
		mav.addObject("domain", domain);
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		if (null != goodsWarrantyInfo.getAreaId() && goodsWarrantyInfo.getAreaId() > 0) {

			Integer areaId = goodsWarrantyInfo.getAreaId();
			List<Region> regionList = (List<Region>) regionService.getRegion(areaId, 1);

			if (null != regionList && regionList.size() > 0) {
				for (Region r : regionList) {
					switch (r.getRegionType()) {
						case 1:
							List<Region> cityList = regionService.getRegionByParentId(r.getRegionId());
							mav.addObject("provinceRegion", r);
							mav.addObject("cityList", cityList);
							break;
						case 2:
							List<Region> zoneList = regionService.getRegionByParentId(r.getRegionId());
							mav.addObject("cityRegion", r);
							mav.addObject("zoneList", zoneList);
							break;
						case 3:
							mav.addObject("zoneRegion", r);
							break;
						default:
							mav.addObject("countryRegion", r);
							break;
					}
				}
			}
		}
		mav.addObject("provinceList", provinceList);

		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		mav.addObject("user", user);
		try {
			mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(goodsWarrantyInfo)));
		} catch (Exception e) {
			logger.error("editgoodswarranty:", e);
		}
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑录保卡
	 *
	 * @param request
	 * @param session
	 * @param goodsWarrantyVo
	 * @param fileUri
	 * @param fileName
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月24日 上午11:42:41
	 */
	@ResponseBody
	@RequestMapping(value = "/saveeditgoodswarranty")
	public ModelAndView saveEditGoodsWarranty(HttpServletRequest request, HttpSession session,
											  SaleorderGoodsWarrantyVo goodsWarrantyVo, @RequestParam(required = false, value = "fileUri") String fileUri,
											  @RequestParam(required = false, value = "fileName") String fileName, String beforeParams) {
		ModelAndView mav = new ModelAndView();
		goodsWarrantyVo.setUri(fileUri);
		goodsWarrantyVo.setDomain(domain);
		goodsWarrantyVo.setFileName(fileName);
		SaleorderGoodsWarrantyVo warrantyVo = saleorderService.saveEditGoodsWarranty(request, session, goodsWarrantyVo);
		if (null != warrantyVo) {
			mav.addObject("url",
					"./viewgoodswarranty.do?saleorderGoodsWarrantyId=" + warrantyVo.getSaleorderGoodsWarrantyId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 验证销售订单中产品是否重复
	 *
	 * @param request
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年11月20日 下午5:46:38
	 */
	@ResponseBody
	@RequestMapping(value = "vailSaleorderGoodsRepeat")
	public ResultInfo<?> vailSaleorderGoodsRepeat(HttpServletRequest request, SaleorderGoods saleorderGoods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			return saleorderService.vailSaleorderGoodsRepeat(saleorderGoods);
		}
		return new ResultInfo<>(-1, "用户登录超时");
	}

	/**
	 * <b>Description:</b><br>
	 * 打印销售单合同
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月12日 上午10:00:22
	 */
	@ResponseBody
	@RequestMapping(value = "/printOrder")
	public ModelAndView printOrder(HttpServletRequest request, Saleorder saleorder) {

		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		saleorder.setCompanyId(user.getCompanyId());
		SaleorderVo saleorderVo = saleorderService.getPrintOrderInfo(saleorder);

		// 获取销售人员信息
		user = userService.getUserByTraderId(saleorderVo.getTraderId(), 1);// 1客户，2供应商
		UserDetail detail = userDetailMapper.getUserDetail(user.getUserId());
		// add by franlin.wu for[耗材商城订单，不存在销售人员，避免出现空指针异常] at 2018-11-25 begin
		String username = "";
		// 销售订单归属的销售人员UserId
		Integer saleUserId = saleorderVo.getUserId();
		if (!(null == saleUserId || ErpConst.ZERO.equals(saleUserId))) {
			// 根据userId查询用户信息
			User saleUser = userService.getUserById(saleUserId);
			// 非空
			if (null != saleUser) {
				// 设置销售人员userName
				username = saleUser.getUsername();
			}
		}
		// add by franlin.wu for[耗材商城订单，不存在销售人员] at 2018-11-25 end

		// 获取订单下的产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorder.getSaleorderId());
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		Long currTime = DateUtil.sysTimeMillis();
		BigDecimal pageTotalPrice = new BigDecimal(0.00);
		BigDecimal zioe = pageTotalPrice;
		Integer flag = -1;
		for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
			if (saleorderGoods.getHaveInstallation() == 1) {
				flag = 1;
			}
			String price = getCommaFormat(saleorderGoods.getPrice());
			if (!price.contains(".")) {
				price += ".00";
			}
			saleorderGoods.setPrices(price);
			String allprice = getCommaFormat(
					saleorderGoods.getPrice().multiply(new BigDecimal(saleorderGoods.getNum())));
			if (!allprice.contains(".")) {
				allprice += ".00";
			}
			saleorderGoods.setAllPrice(allprice);
			pageTotalPrice = pageTotalPrice
					.add(saleorderGoods.getPrice().multiply(new BigDecimal(saleorderGoods.getNum())));
		}
		String totalAmount = getCommaFormat(pageTotalPrice);
		if (!totalAmount.contains(".")) {
			totalAmount += ".00";
		}
		mv.addObject("totalAmount", totalAmount);
		try {
			mv.addObject("chineseNumberTotalPrice",
					pageTotalPrice.compareTo(zioe) > 0
							? DigitToChineseUppercaseNumberUtils.numberToChineseNumber(pageTotalPrice)
							: null);
		} catch (ShowErrorMsgException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);
		// 运费类型
		List<SysOptionDefinition> yfTypes = getSysOptionDefinitionList(469);
		mv.addObject("yfTypes", yfTypes);

		// 获取公司信息
		Company company = companyService.getCompanyByCompangId(user.getCompanyId());
		mv.addObject("company", company);
		mv.addObject("flag", flag);
		mv.addObject("currTime", DateUtil.convertString(currTime, "YYYY-MM-dd "));
		mv.addObject("detail", detail);
		mv.addObject("username", username);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorderVo", saleorderVo);
		mv.setViewName("order/saleorder/order_print");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 打印销售订单
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月12日 上午10:00:22
	 */
	@ResponseBody
	@RequestMapping(value = "/printSaleOrder")
	public ModelAndView printSaleOrder(HttpServletRequest request, Saleorder saleorder) {

		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		saleorder.setCompanyId(user.getCompanyId());
		SaleorderVo saleorderVo = saleorderService.getPrintOrderInfo(saleorder);

		// 获取销售人员信息
		user = userService.getUserByTraderId(saleorderVo.getTraderId(), 1);// 1客户，2供应商
		UserDetail detail = userDetailMapper.getUserDetail(user.getUserId());
		String username = userService.getUserById(saleorderVo.getUserId()).getUsername();

		// 获取订单下的产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorder.getSaleorderId());
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		Long currTime = DateUtil.sysTimeMillis();

		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);

		// 获取公司信息
		Company company = companyService.getCompanyByCompangId(user.getCompanyId());
		mv.addObject("company", company);

		// 发货方式
		List<SysOptionDefinition> deliveryTypes = getSysOptionDefinitionList(480);
		mv.addObject("deliveryTypes", deliveryTypes);

		// 付款方式列表
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mv.addObject("paymentTermList", paymentTermList);

		// 运费说明
		List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(469);
		mv.addObject("freightDescriptions", freightDescriptions);

		mv.addObject("currTime", DateUtil.convertString(currTime, "YYYY-MM-dd "));
		mv.addObject("detail", detail);
		mv.addObject("username", username);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorderVo", saleorderVo);
		mv.setViewName("order/saleorder/sales_order");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增订单页面
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月16日 上午10:34:04
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("order/saleorder/add");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增的销售订单
	 *
	 * @param request
	 * @param session
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月16日 下午2:24:31
	 */
	@ResponseBody
	@RequestMapping(value = "/saveaddsaleorderinfo")
	public ModelAndView saveAddSaleorderInfo(HttpServletRequest request, HttpSession session, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		try {
			saleorder.setInvoiceType(972);
			if (saleorder.getCustomerNature() == 466) {
				TraderCustomer traderCustomer = new TraderCustomer();
				traderCustomer.setTraderId(saleorder.getTraderId());
				TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
				// 终端类型
				saleorder.setTerminalTraderType(traderBaseInfo.getCustomerType());
				// 终端名称
				saleorder.setTerminalTraderName(traderBaseInfo.getTrader().getTraderName());
				// 销售区域
				String region = (String) regionService.getRegion(traderBaseInfo.getAreaId(), 2);
				saleorder.setSalesArea(region);
			}
			saleorder = saleorderService.saveAddSaleorderInfo(saleorder, request, session);
			if (null != saleorder) {
				mv.addObject("url", "./edit.do?saleorderId=" + saleorder.getSaleorderId() + "&extraType="
						+ saleorder.getExtraType());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("saveaddsaleorderinfo:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 余额支付初始页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月21日 下午6:40:30
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/initBalancePayment")
	public ModelAndView initBalancePayment(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mav = new ModelAndView("order/saleorder/balance_payment");
		// 获取订单主信息
		Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
		// 获取订单对应客户的余额信息
		TraderCustomer traderCustomerInfo = traderCustomerService.getTraderCustomerInfo(saleorderInfo.getTraderId());

		mav.addObject("traderCustomerInfo", traderCustomerInfo);
		mav.addObject("saleorder", saleorderInfo);

		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 申请提前采购
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年1月15日 下午4:49:51
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/applyPurchasePage")
	public ModelAndView applyPurchasePage(HttpServletRequest request, Saleorder saleorder, String taskId) {
		ModelAndView mav = new ModelAndView("order/saleorder/advance_sale_purchase_apply");
		mav.addObject("taskId", taskId);
		mav.addObject("saleorder", saleorder);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存申请提前采购
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年1月15日 下午4:49:51
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveApplyPurchase")
	public ResultInfo<?> saveApplyPurchase(HttpServletRequest request, Saleorder saleorder, String taskId) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		saleorderService.saveEditSaleorderInfo(saleorder, request, request.getSession());
		// 归属销售
		Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
		User users = userService.getUserByTraderId(saleorderInfo.getTraderId(), 1);// 1客户，2供应商
		saleorder.setOptUserName(users == null ? "" : users.getUsername());
		saleorder.setSaleorderNo(saleorderInfo.getSaleorderNo());
		List<SaleorderGoods> goodsList = saleorderService.getSaleorderGoodsById(saleorder);
		// 订单中产品类型（0未维护,1 只有设备,2 只有试剂,3 又有试剂又有设备）
		saleorder.setGoodsType(0);
		List<Integer> goodsTypeList = new ArrayList<>();
		if (!goodsList.isEmpty()) {
			for (SaleorderGoods s : goodsList) {
				if (s.getGoods() != null) {
					if (s.getIsDelete() != null && s.getIsDelete() == 0 && s.getGoods().getGoodsType() != null
							&& (s.getGoods().getGoodsType() == 316 || s.getGoods().getGoodsType() == 319)) {
						goodsTypeList.add(1);
					} else if (s.getIsDelete() != null && s.getIsDelete() == 0 && s.getGoods().getGoodsType() != null
							&& (s.getGoods().getGoodsType() == 317 || s.getGoods().getGoodsType() == 318)) {
						goodsTypeList.add(2);
					}
				}
			}
			if (!goodsTypeList.isEmpty()) {
				List<Integer> newList = new ArrayList(new HashSet(goodsTypeList));
				if (newList.size() == 2) {
					saleorder.setGoodsType(3);
				}

				if (newList.size() == 1) {
					if (newList.get(0) == 1) {
						saleorder.setGoodsType(1);
					} else if (newList.get(0) == 2) {
						saleorder.setGoodsType(2);
					}

				}
			}
		}
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {
				variableMap.put("orgId", user.getOrgId());
				variableMap.put("orderId", saleorder.getSaleorderId());
				variableMap.put("saleorderInfo", saleorder);
				variableMap.put("currentAssinee", saleorder.getOptUserName());
				variableMap.put("processDefinitionKey", "earlyBuyorderVerify");
				variableMap.put("businessKey", "earlyBuyorderVerify_" + saleorder.getSaleorderId());
				variableMap.put("relateTableKey", saleorder.getSaleorderId());
				variableMap.put("relateTable", "T_SALEORDER");
				// 设置审核完成监听器回写参数
				variableMap.put("tableName", "T_SALEORDER");
				variableMap.put("id", "SALEORDER_ID");
				variableMap.put("idValue", saleorder.getSaleorderId());
				actionProcdefService.createProcessInstance(request, "earlyBuyorderVerify",
						"earlyBuyorderVerify_" + saleorder.getSaleorderId(), variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"earlyBuyorderVerify_" + saleorder.getSaleorderId());
			if (historicInfo.get("endStatus") != "审核完成") {
				actionProcdefService.updateInfo("T_SALEORDER", "SALEORDER_ID", saleorder.getSaleorderId(),
						"LOCKED_STATUS", 1, 2);
				Saleorder saleorderLocked = new Saleorder();
				saleorderLocked.setLockedReason("提前采购审核");
				saleorderLocked.setSaleorderId(saleorder.getSaleorderId());
				saleorderService.saveEditSaleorderInfo(saleorderLocked, request, request.getSession());
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 默认审批通过
				variables.put("pass", true);
				ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
						saleorder.getOptUserName(), variables);
				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("saveApplyPurchase:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 销售订单确认收货初始化页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月16日 下午2:26:30
	 */
	@ResponseBody
	@RequestMapping(value = "confirmArrivalInit")
	public ModelAndView confirmArrivalInit(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		// Integer saleorderId = saleorder.getSaleorderId();
		// 获取订单基本信息
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

		// 获取订单直发产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorder.getSaleorderId());
		sale.setExtraType("arrival");// 直发确认收货
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/confirm_arrival_init");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 销售订单确认收货
	 *
	 * @param request
	 * @param session
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月16日 下午3:22:12
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmArrival")
	public ModelAndView confirmArrival(HttpServletRequest request, HttpSession session, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		try {
			saleorder = saleorderService.confirmArrival(saleorder, request, session);
			Integer saleorderId = saleorder.getSaleorderId();

			/*new Thread() {// 异步推送数据
				@Override
				public void run() {*/
					// 查询该订单信息
					LOG.info("同步耗材订单开始。。。。start");
					Saleorder saleorders = new Saleorder();
			        Saleorder saleorders2 = new Saleorder();
					saleorders.setSaleorderId(saleorderId);
			         saleorders2.setSaleorderId(saleorderId);
					saleorders = saleorderService.getBaseSaleorderInfo(saleorders);


					String firstSkuName = "";

					if (null != saleorders && saleorders.getOrderType() == 5) {// 如果是耗材商城的订单
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("saleOrder", saleorders);
						map.put("type", 1);// 确认收货标志
						// 查询该订单下的所有商品
						List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleOrderGoodsList(saleorders2);
						// 筛选出修改了商品收货状态的订单列表
						if (null != request.getParameter("id_str") && CollectionUtils.isNotEmpty(saleorderGoodsList)) {
							List<SaleorderGoods> goodsList = new ArrayList<SaleorderGoods>();
							String[] categoryAttributeIds2 = request.getParameter("id_str").split("_");
							for (String attIds2 : categoryAttributeIds2) {
								if (request.getParameter("arrivalStatus_" + attIds2) != null && !" ".equals(attIds2)) {
									for (SaleorderGoods saleorderGoods : saleorderGoodsList) {
										// add by franln.wu for[取值] at 2019-06-20 begin
										if(EmptyUtils.isNotBlank(firstSkuName) && null != saleorderGoods && EmptyUtils.isNotBlank(saleorderGoods.getGoodsName())) {
											firstSkuName = saleorderGoods.getGoodsName();
										}
										// add by franln.wu for[取值] at 2019-06-20 end

										// 筛选订单商品对象
										if (saleorderGoods.getSaleorderGoodsId() == Integer.parseInt(attIds2)) {
											goodsList.add(saleorderGoods);
										}
									}
								}
							}
							if (goodsList != null && goodsList.size() > 0) {
								map.put("goodsList", goodsList);
								// 推数据到耗材商城
								hcSaleorderService.putExpressToHC(map);
							}
						}
					}
					LOG.info("同步耗材订单结束。。。。end");
					//如果订单是全部发货的话
					if(null != saleorders && saleorders.getArrivalStatus() == 2){
						// 用于返回避免再次查询
						Map sTempMap = null;
                        Map sTempMap2 = null;

						// 订单类型
						Integer orderType = saleorders.getOrderType();
						//全部收货签收发送微信消息给注册用户(如果订单是VS，JX，DH的订单)
						if(OrderConstant.ORDER_TYPE_SALE.equals(orderType) || OrderConstant.ORDER_TYPE_DH.equals(orderType) || OrderConstant.ORDER_TYPE_JX.equals(orderType)){
							ResultInfo senResult = expressService.sendWxMessageForArrival(saleorderId);
							if(null != senResult) {
								sTempMap = (Map)senResult.getData();
							}
						}
                        if(OrderConstant.ORDER_TYPE_SALE.equals(orderType) || OrderConstant.ORDER_TYPE_DH.equals(orderType) || OrderConstant.ORDER_TYPE_JX.equals(orderType)||OrderConstant.ORDER_TYPE_BD.equals(orderType)){
                            ResultInfo senResult = expressService.sendWxMessageForArrival(saleorderId);
                            if(null != senResult) {
                                sTempMap2 = (Map)senResult.getData();
                            }
                        }
						// add by franlin.wu for[微信推送 医械购 订单签收] at 2019-06-20 begin
						if(CommonConstants.ONE.equals(sendYxgWxTempMsgFlag)) {
							sendTemplateMsgHcForOrderOk(saleorders, sTempMap);
						}
                       //微信公众号推送
						sendOrderFor(saleorders, sTempMap2);
						// add by franlin.wu for[微信推送 医械购 订单签收] at 2019-06-20 begin
						if(OrderConstant.ORDER_TYPE_BD.equals(orderType)) {
							OrderData orderData = new OrderData();
							orderData.setOrderNo(saleorders.getSaleorderNo());
							orderData.setOrderStatus(5);//订单完成
							WebAccount web =webAccountMapper.getWenAccountInfoByMobile(saleorders.getCreateMobile());
							orderData.setAccountId(web.getWebAccountId());
							try {
								String url = mjxUrl+"/order/updateOrderConfirmFinish";
								String json = JsonUtils.translateToJson(orderData);
								CloseableHttpClient httpClient = HttpClientBuilder.create().build();
								JSONObject result2 = NewHttpClientUtils.httpPost(url, JSONObject.fromObject(orderData));
								String r = result2.toString();
							} catch (Exception e) {
								logger.error("mjx订单完成状态推送erro:{}",e);
							}
						}
					}
			/*	}
			}.start();*/
			if (null != saleorder) {
				mv.addObject("url", "./view.do?saleorderId=" + saleorder.getSaleorderId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("confirmArrival:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 销售订单申请修改初始化页面
	 *
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 上午11:20:27
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "modifyApplyInit")
	public ModelAndView modifyApplyInit(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		Integer saleorderId = saleorder.getSaleorderId();
		// 获取订单基本信息
		saleorder.setOptType("modifyApplyInit");
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
		// add by franlin.wu for[耗材商城订单，避免空指针] at 2018-11-29 begin
		if (null == saleorder) {
			mv.addObject("message", "无法操作，该订单申请修改查询失败");
			return fail(mv);
		}
		// add by franlin.wu for[耗材商城订单，避免空指针] at 2018-11-29 end
		if (saleorder.getLockedStatus() == 1) {
			mv.addObject("message", "无法操作，该订单已被锁定");
			return fail(mv);
		}
		// add by franlin.wu for[耗材商城订单，避免空指针以及非耗材订单的业务逻辑] at 2018-11-29 begin
		// 订单类型
		Integer ordreType = saleorder.getOrderType();
		//------------------分批开票--------------------------------
		//若订单状态为已完成，但开票状态为“未开票”或“部分开票”，仍然显示“申请修改”的按钮，只有收票信息模块可修改，其他内容不可修改。
		Integer status = saleorder.getStatus();
		Integer invoiceStatus = saleorder.getInvoiceStatus();
		if(status.equals(2)&&invoiceStatus!=2){
			mv.addObject("invoiceModifyflag",1);
		}
		//------------------分批开票--------------------------------
		// 耗材商城订单类型
		if (OrderConstant.ORDER_TYPE_HC.equals(ordreType)) {
			// 省级地区
			List<Region> provinceList = regionService.getRegionByParentId(1);
			mv.addObject("provinceList", provinceList);

			// 收货信息
			Integer takeTraderAreaId = saleorder.getTakeTraderAreaId();
			// 存在
			if (null != takeTraderAreaId && !ErpConst.ZERO.equals(takeTraderAreaId)) {
				// 根据地区最小Id查询省市区ID
				String takeTraderAddressIdStr = regionService.getRegionIdStringByMinRegionId(takeTraderAreaId);
				if (null != takeTraderAddressIdStr) {
					String[] takeTraderAddressIdArr = takeTraderAddressIdStr.split(",");
					// 长度为3
					if (null != takeTraderAddressIdArr && takeTraderAddressIdArr.length == 3) {
						// 省地区id
						String provinceId = takeTraderAddressIdArr[0];
						mv.addObject("takeTraderAddressIdProvince", provinceId);
						// 市地区Id
						String cityId = takeTraderAddressIdArr[1];
						mv.addObject("takeTraderAddressIdCity", cityId);
						List<Region> takeTraderAddressCityList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(provinceId));
						mv.addObject("takeTraderAddressCityList", takeTraderAddressCityList);
						// 区
						mv.addObject("takeTraderAddressIdZone", takeTraderAddressIdArr[2]);
						List<Region> takeTraderAddressZoneList = StringUtil.isBlank(cityId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(cityId));
						mv.addObject("takeTraderAddressZoneList", takeTraderAddressZoneList);
					}
				}
			}
			// 收票信息
			Integer invoiceTraderAreaId = saleorder.getInvoiceTraderAreaId();
			// 存在
			if (null != invoiceTraderAreaId && !ErpConst.ZERO.equals(invoiceTraderAreaId)) {
				// 根据地区最小Id查询省市区ID
				String invoiceTraderAddressIdStr = regionService.getRegionIdStringByMinRegionId(invoiceTraderAreaId);
				if (null != invoiceTraderAddressIdStr) {
					String[] invoiceTraderAddressIdArr = invoiceTraderAddressIdStr.split(",");
					// 长度为3
					if (null != invoiceTraderAddressIdArr && invoiceTraderAddressIdArr.length == 3) {
						// 省地区ID
						String provinceId = invoiceTraderAddressIdArr[0];
						mv.addObject("invoiceTraderAddressIdProvince", provinceId);
						// 市地区Id
						String citiyId = invoiceTraderAddressIdArr[1];
						mv.addObject("invoiceTraderAddressIdCity", citiyId);
						List<Region> invoiceCityList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(provinceId));
						mv.addObject("invoiceCityList", invoiceCityList);
						// 区地区Id
						mv.addObject("invoiceTraderAddressIdZone", invoiceTraderAddressIdArr[2]);
						List<Region> invoiceZoneList = StringUtil.isBlank(provinceId) ? null
								: regionService.getRegionByParentId(Integer.parseInt(citiyId));
						mv.addObject("invoiceZoneList", invoiceZoneList);
					}
				}
			}
		}
		// 非耗材商城订单
		else {

			// 收货信息 联系人&联系地址
			if (saleorder.getTakeTraderId() != null && saleorder.getTakeTraderId().intValue() > 0) {
				TraderContactVo takeTraderContactVo = new TraderContactVo();
				takeTraderContactVo.setTraderId(saleorder.getTakeTraderId());
				takeTraderContactVo.setIsEnable(1);
				takeTraderContactVo.setTraderType(ErpConst.ONE);
				Map<String, Object> takeMap = traderCustomerService.getTraderContactVoList(takeTraderContactVo);
				String takeTastr = (String) takeMap.get("contact");
				net.sf.json.JSONArray takeJson = net.sf.json.JSONArray.fromObject(takeTastr);
				List<TraderContactVo> takeTraderContactList = (List<TraderContactVo>) takeJson.toCollection(takeJson,
						TraderContactVo.class);
				List<TraderAddressVo> takeTraderAddressList = (List<TraderAddressVo>) takeMap.get("address");
				mv.addObject("takeTraderContactList", takeTraderContactList);
				mv.addObject("takeTraderAddressList", takeTraderAddressList);

				TraderCustomerVo takeTraderCustomerInfo = traderCustomerService
						.getCustomerBussinessInfo(saleorder.getTakeTraderId());
				mv.addObject("takeTraderCustomerInfo", takeTraderCustomerInfo);
			}

			// 收票信息 联系人&联系地址
			if (saleorder.getInvoiceTraderId() != null && saleorder.getInvoiceTraderId().intValue() > 0) {
				TraderContactVo invoiceTraderContactVo = new TraderContactVo();
				invoiceTraderContactVo.setTraderId(saleorder.getInvoiceTraderId());
				invoiceTraderContactVo.setIsEnable(1);
				invoiceTraderContactVo.setTraderType(ErpConst.ONE);
				Map<String, Object> invoiceMap = traderCustomerService.getTraderContactVoList(invoiceTraderContactVo);
				String invoiceTastr = (String) invoiceMap.get("contact");
				net.sf.json.JSONArray invoiceJson = net.sf.json.JSONArray.fromObject(invoiceTastr);
				List<TraderContactVo> invoiceTraderContactList = (List<TraderContactVo>) invoiceJson
						.toCollection(invoiceJson, TraderContactVo.class);
				List<TraderAddressVo> invoiceTraderAddressList = (List<TraderAddressVo>) invoiceMap.get("address");
				mv.addObject("invoiceTraderContactList", invoiceTraderContactList);
				mv.addObject("invoiceTraderAddressList", invoiceTraderAddressList);

				TraderCustomerVo invoiceTraderCustomerInfo = traderCustomerService
						.getCustomerBussinessInfo(saleorder.getInvoiceTraderId());
				mv.addObject("invoiceTraderCustomerInfo", invoiceTraderCustomerInfo);
			}
		}

		// add by Tomcat.Hui 2019/12/2 19:22 .Desc: VDERP-1325 分批开票 打印报错信息. start
		try{
			// 如果需要拦截开票的
			if (saleorder.getIsHaveInvoiceApply() != null && saleorder.getIsHaveInvoiceApply().equals(1)
					&& saleorderId != null) {
				List<InvoiceApply> invoiceApplyList = invoiceService.getSaleInvoiceApplyList(saleorderId, 505, -1);
				if (invoiceApplyList != null) {
					for (InvoiceApply ia : invoiceApplyList) {
						// 非不通过的
						// add by Tomcat.Hui 2019/12/3 19:28 .Desc: VDERP-1325 分批开票 去掉运营审核状态校验.
						if (!ia.getValidStatus().equals(2) && !ia.getAdvanceValidStatus().equals(2)) {
							mv.addObject("invoiceApply", ia);
							break;
						}
					}
				}
			}
		}catch (Exception e){
			logger.error(Contant.ERROR_MSG, e);
			logger.error("添加开票信息出错",e);
		}
		// add by Tomcat.Hui 2019/12/2 19:22 .Desc: VDERP-1325 分批开票 打印报错信息. end


		//获取当前订单开票审核状态 1 存在审核中 0 不存在
		Integer invoiceApplyStatus = invoiceService.getInvoiceApplyNowAuditStatus(saleorderId);
		Integer invoiceapplyFlag = 0;
		if(invoiceApplyStatus.equals(1)|| invoiceStatus!=0){
			invoiceapplyFlag = 1;
		}
		mv.addObject("invoiceapplyFlag",invoiceapplyFlag);
		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);

		// 获取订单产品信息
		Saleorder sale = new Saleorder();
		sale.setSaleorderId(saleorder.getSaleorderId());
		List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		List<Integer> skuIds = new ArrayList<>();
		saleorderGoodsList.stream().forEach(saleGood -> {
			skuIds.add(saleGood.getGoodsId());
		});
		List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
		Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
		mv.addObject("newSkuInfosMap", newSkuInfosMap);
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		//查询销售是否为科研购及其子部门
		mv.addObject("isScientificDep",orgService.getKYGOrgFlagByTraderId(saleorder));
		mv.addObject("saleorderGoodsList", saleorderGoodsList);
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/modify_apply_init");
		return mv;
	}

	/**
	 * <b>Description:</b>根据更改直发状态的商品是否有产品经理判断能否提交<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/1/13
	 */
	private void isSaleorderCanModify(SaleorderModifyApply saleorderModifyApply, HttpServletRequest request,
									 HttpSession session) throws Exception{
		saleorderModifyApply=saleorderService.convertModifyApply(saleorderModifyApply,request,session);
		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(saleorderModifyApply.getSaleorderId());
		List<SaleorderGoods> saleorderGoods = saleorderService.getSaleorderGoodsById(saleorder);
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
		boolean isChangeAdrress=false;
		if (saleorder.getTakeTraderAddressId() != null
				&& !saleorder.getTakeTraderAddressId().equals(saleorderModifyApply.getTakeTraderAddressId())
				&& saleorder.getDeliveryDirect() == 1) {
			// 如果是直发订单修改了收货地址，需要产品部审核
			isChangeAdrress=true;
			for(SaleorderGoods g:saleorderGoods){
				Integer count=goodsMapper.getAssignManageUserCountByGoods(g.getGoodsId());
				if(ObjectUtils.isEmpty(count)||count<1){
					saleorderModifyApply.setGoodsList(null);
					throw new Exception("修改直发订单的收货地址，商品"+g.getGoodsName()+"，不存在归属产品经理");
				}
			}
		}
		if(!isChangeAdrress) {
			for (SaleorderGoods sg : saleorderGoods) {
				List<SaleorderModifyApplyGoods> goodsList2 = saleorderModifyApply.getGoodsList();
				for (SaleorderModifyApplyGoods smag : goodsList2) {
					if (sg.getSaleorderGoodsId().equals(smag.getSaleorderGoodsId())) {
						// 同一个订单产品比较直发状态是否相同
						if (!sg.getDeliveryDirect().equals(smag.getDeliveryDirect())) {
							Integer count = goodsMapper.getAssignManageUserCountByGoods(sg.getGoodsId());
							if (ObjectUtils.isEmpty(count) || count < 1) {
								saleorderModifyApply.setGoodsList(null);
								throw new Exception("修改直发状态的商品" + sg.getGoodsName() + "，不存在归属产品经理");
							}
						}
					}
				}
			}
		}
		saleorderModifyApply.setGoodsList(null);
	}

	/**
	 * <b>Description:</b><br>
	 * 销售订单申请修改提交审核
	 *
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 上午11:22:15
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/modifyApplySave")
	@SystemControllerLog(operationType = "add", desc = "销售订单申请修改提交审核")
	public ModelAndView modifyApplySave(HttpServletRequest request, HttpSession session,
										SaleorderModifyApply saleorderModifyApply) {
		ModelAndView mv = new ModelAndView();
		try {
			isSaleorderCanModify(saleorderModifyApply,request,session);
			saleorderModifyApply = saleorderService.modifyApplySave(saleorderModifyApply, request, session);
			if (null != saleorderModifyApply) {
				// 生成流程
				try {
					List<Integer> changeIsDirectGoodsIds=new ArrayList<>();
					boolean isChangeTakeAdress=false;
					Map<String, Object> variableMap = new HashMap<String, Object>();
					User user = userService.getUserById(saleorderModifyApply.getCreator());
					Saleorder saleorder = new Saleorder();
					saleorder.setSaleorderId(saleorderModifyApply.getSaleorderId());
					List<SaleorderGoods> saleorderGoods = saleorderService.getSaleorderGoodsById(saleorder);
					saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
					saleorderModifyApply.setIsChangeTakeTraderAddressId(0);
					saleorderModifyApply.setIsChangeDeliveryDirect(0);
					saleorderModifyApply.setIsChangeLogisticsComments(0);
					if (saleorder.getTakeTraderAddressId() != null
							&& !saleorder.getTakeTraderAddressId().equals(saleorderModifyApply.getTakeTraderAddressId())
							&& saleorder.getDeliveryDirect() == 1) {
						// 如果是直发订单修改了收货地址，需要产品部审核
						isChangeTakeAdress=true;
						for(SaleorderGoods g:saleorderGoods){
							if(g!=null&&g.getGoodsId()!=null){
								changeIsDirectGoodsIds.add(g.getGoodsId());
							}
						}
						saleorderModifyApply.setIsChangeDeliveryDirect(1);
					}
					// 非耗材订单进行比较（耗材订单未传TakeTraderAddressId）
					if (!saleorder.getOrderType().equals(5)) {
						if (saleorder.getTakeTraderAddressId() != null && !saleorder.getTakeTraderAddressId()
								.equals(saleorderModifyApply.getTakeTraderAddressId())) {
							// 是否修改收货地址 已修改
							saleorderModifyApply.setIsChangeTakeTraderAddressId(1);
						}
					}

					if (saleorder.getLogisticsComments() != null
							&& !saleorder.getLogisticsComments().equals(saleorderModifyApply.getLogisticsComments())) {
						// 是否修改了物流备注
						saleorderModifyApply.setIsChangeLogisticsComments(1);
					}
					for (SaleorderGoods sg : saleorderGoods) {
						List<SaleorderModifyApplyGoods> goodsList2 = saleorderModifyApply.getGoodsList();
						for (SaleorderModifyApplyGoods smag : goodsList2) {
							if (sg.getSaleorderGoodsId().equals(smag.getSaleorderGoodsId())) {
								// 同一个订单产品比较直发状态是否相同
								if (!sg.getDeliveryDirect().equals(smag.getDeliveryDirect())) {
									// 是否修改直发普发状态 已修改
									saleorderModifyApply.setIsChangeDeliveryDirect(1);
									if(!isChangeTakeAdress) {
										changeIsDirectGoodsIds.add(sg.getGoodsId());
									}
								}
							}
						}
					}
					if (saleorderModifyApply.getIsChangeLogisticsComments().equals(1)
							&& saleorderModifyApply.getIsChangeDeliveryDirect().equals(0)
							&& saleorderModifyApply.getIsChangeTakeTraderAddressId().equals(0)) {
						// 如果只是修改了物流备注，给物流部发送消息
						List<Integer> varifyUserList = new ArrayList<>();
						varifyUserList = roleService.getUserIdByRoleName("物流专员", user.getCompanyId());
						if (varifyUserList != null && !varifyUserList.isEmpty()) {
							String url = "./order/saleorder/viewModifyApply.do?saleorderModifyApplyId="
									+ saleorderModifyApply.getSaleorderModifyApplyId() + "&saleorderId="
									+ saleorderModifyApply.getSaleorderId();
							Map<String, String> map = new HashMap<>();
							map.put("orderNo", saleorderModifyApply.getSaleorderModifyApplyNo());
							MessageUtil.sendMessage(75, varifyUserList, map, url, user.getUsername());//
						}
					}
					// 开始生成流程(如果没有taskId表示新流程需要生成)
					variableMap.put("saleorderModifyApplyInfo", saleorderModifyApply);
					variableMap.put("saleorderModifyApplyId", saleorderModifyApply.getSaleorderModifyApplyId());
					variableMap.put("orderId", saleorderModifyApply.getSaleorderId());
					variableMap.put("currentAssinee", user.getUsername());
					variableMap.put("processDefinitionKey", "editSaleorderVerify");
					variableMap.put("businessKey",
							"editSaleorderVerify_" + saleorderModifyApply.getSaleorderModifyApplyId());
					variableMap.put("relateTableKey", saleorderModifyApply.getSaleorderModifyApplyId());
					variableMap.put("relateTable", "T_SALEORDER_MODIFY_APPLY");
					// 设置审核完成监听器回写参数
					variableMap.put("tableName", "T_SALEORDER_MODIFY_APPLY");
					variableMap.put("id", "SALEORDER_MODIFY_APPLY_ID");
					variableMap.put("idValue", saleorderModifyApply.getSaleorderModifyApplyId());
					variableMap.put("idValue1", saleorderModifyApply.getSaleorderId());
					variableMap.put("key", "VALID_STATUS");
					variableMap.put("value", 1);
					// 回写数据的表在db中
					variableMap.put("db", 2);
					actionProcdefService.createProcessInstance(request, "editSaleorderVerify",
							"editSaleorderVerify_" + saleorderModifyApply.getSaleorderModifyApplyId(), variableMap);
					// 默认申请人通过
					// 根据BusinessKey获取生成的审核实例
					Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
							"editSaleorderVerify_" + saleorderModifyApply.getSaleorderModifyApplyId());
					if (historicInfo.get("endStatus") != "审核完成") {
						Saleorder saleorderLocked = new Saleorder();
						saleorderLocked.setLockedReason("订单修改审核");
						saleorderLocked.setSaleorderId(saleorderModifyApply.getSaleorderId());
						saleorderLocked.setIsPrintout(saleorderModifyApply.getIsPrintout());
						saleorderService.saveEditSaleorderInfo(saleorderLocked, request, request.getSession());
						Task taskInfo = (Task) historicInfo.get("taskInfo");
						String taskId = taskInfo.getId();
						Authentication.setAuthenticatedUserId(user.getUsername());
						Map<String, Object> variables = new HashMap<String, Object>();
						// 默认审批通过
						variables.put("pass", true);
						ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
								user.getUsername(), variables);
						if(saleorderModifyApply.getIsChangeDeliveryDirect().equals(1)
						&&CollectionUtils.isNotEmpty(changeIsDirectGoodsIds)){
							List<User> users=goodsMapper.getAssignManageUserByGoods(changeIsDirectGoodsIds);
							if(CollectionUtils.isNotEmpty(users)){
								List<String> userNames=new ArrayList<>();
								List<Integer> userIds=new ArrayList<>();
								for(User u:users){
									if(u==null||u.getUserId()==null||StringUtil.isBlank(u.getUsername())){
										continue;
									}
									userNames.add(u.getUsername());
									userIds.add(u.getUserId());
								}
								Map<String, Object> historicInfo1 = actionProcdefService.getHistoric(processEngine,
										"editSaleorderVerify_" + saleorderModifyApply.getSaleorderModifyApplyId());
								Task taskInfo1 = (Task) historicInfo1.get("taskInfo");
								String taskId1 = taskInfo1.getId();
								actionProcdefService.addCandidate(taskId1,userNames);
								Integer messageTemplateId = 64;
								Map<String, String> map = new HashMap<>();
								map.put("saleorderNo", saleorderModifyApply.getSaleorderModifyApplyNo());
								String url = "./order/saleorder/viewModifyApply.do?saleorderModifyApplyId="
										+ saleorderModifyApply.getSaleorderModifyApplyId() + "&saleorderId="
										+ saleorderModifyApply.getSaleorderId();
								//MessageUtil.sendMessage(messageTemplateId,userIds,map,url);
								if(CollectionUtils.isNotEmpty(userIds)) {
									MessageUtil.sendMessage(messageTemplateId, userIds, map, url);
								}
							}
						}
						// 如果未结束添加审核对应主表的审核状态
						if (!complementStatus.getData().equals("endEvent")) {
							verifiesRecordService.saveVerifiesInfo(taskId, 0);

						}else {
							//更新BD订单前端信息
							if(saleorder.getOrderType()==1) {
								saleorder.setTakeTraderAddress(saleorderModifyApply.getTakeTraderAddress());
								saleorder.setTakeTraderAddressId(saleorderModifyApply.getTakeTraderAddressId());
								saleorder.setTakeTraderName(saleorderModifyApply.getTakeTraderName());
								saleorder.setTakeTraderContactName(saleorderModifyApply.getTakeTraderContactName());
								saleorder.setTakeTraderContactMobile(saleorderModifyApply.getTakeTraderContactMobile());
								saleorder.setTakeTraderContactTelephone(saleorderModifyApply.getTakeTraderContactTelephone());
								saleorder.setInvoiceTraderAddress(saleorderModifyApply.getInvoiceTraderAddress());
								saleorder.setInvoiceTraderArea(saleorderModifyApply.getInvoiceTraderArea());
								saleorder.setInvoiceTraderAddressId(saleorderModifyApply.getInvoiceTraderAddressId());
								saleorder.setInvoiceType(saleorderModifyApply.getInvoiceType());
								saleorder.setInvoiceTraderContactName(saleorderModifyApply.getInvoiceTraderContactName());
								saleorder.setInvoiceTraderContactTelephone(saleorderModifyApply.getInvoiceTraderContactTelephone());
								saleorder.setInvoiceTraderContactMobile(saleorderModifyApply.getInvoiceTraderContactMobile());
								saleorder.setIsSendInvoice(saleorderModifyApply.getIsSendInvoice());
								List<SaleorderGoods> goodsList = saleorder.getGoodsList();
								List<SaleorderModifyApplyGoods> apply=saleorderModifyApply.getGoodsList();
								for (SaleorderModifyApplyGoods a : apply) {
									for (SaleorderGoods s : goodsList) {
										s.setSaleorderGoodsId(a.getSaleorderGoodsId());
										s.setGoodsComments(a.getGoodsComments());
									}
								}
								JSONObject reslut = saleorderService.ChangeEditSaleOrder(saleorder);
							}

						}

					}
				} catch (Exception e) {
					logger.error("modifyApplySave 1:", e);
					mv.addObject("message", "任务完成操作失败：" + e.getMessage());
					return fail(mv);
				}
				mv.addObject("url", "./viewModifyApply.do?saleorderId=" + saleorderModifyApply.getSaleorderId()
						+ "&saleorderModifyApplyId=" + saleorderModifyApply.getSaleorderModifyApplyId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} catch (Exception e) {
			logger.error("modifyApplySave 2:", e);
			mv.addObject("message", "保存操作失败：" + e.getMessage());
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 订单修改列表
	 *
	 * @param request
	 * @param saleorderModifyApply
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 下午3:51:37
	 */
	@ResponseBody
	@RequestMapping(value = "modifyApplyindex")
	public ModelAndView modifyApplyindex(HttpServletRequest request, Saleorder saleorderModifyApply,
										 HttpSession session, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
										 @RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		saleorderModifyApply.setOrderType(null);
		Page page = getPageTag(request, pageNo, pageSize);

		User user = (User) session.getAttribute(Consts.SESSION_USER);
		try {

			saleorderModifyApply.setCompanyId(user.getCompanyId());
			mv.addObject("loginUser", user);
			// 获取当前销售用户下级职位用户
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> userList = userService.getMyUserList(user, positionType, false);
			mv.addObject("userList", userList);// 操作人员
			// saleorderModifyApply.setSaleUserList(userList);

			if (null != user.getPositType() && user.getPositType() == 310) {// 销售
				/*
				 * // 销售人员所属客户（即当前订单操作人员） List<Integer> traderIdList =
				 * userService.getTraderIdListByUserList(userList, ErpConst.ONE.toString()); if
				 * (traderIdList == null || traderIdList.isEmpty()) { traderIdList.add(-1); }
				 * saleorderModifyApply.setTraderIdList(traderIdList);
				 */
				saleorderModifyApply.setSaleUserList(userList);
			}

			Map<String, Object> map = saleorderService.getSaleorderModifyApplyListPage(request, saleorderModifyApply,
					page);

			List<Saleorder> list = (List<Saleorder>) map.get("saleorderList");

			for (int i = 0; i < list.size(); i++) {
				// 销售人员
				user = userService.getUserById(list.get(i).getUserId());
				list.get(i).setSalesName(user == null ? "" : user.getUsername());
				// 归属销售
				user = userService.getUserByTraderId(list.get(i).getTraderId(), 1);// 1客户，2供应商
				list.get(i).setOptUserName(user == null ? "" : user.getUsername());

				// 销售部门
				list.get(i).setSalesDeptName(orgService.getOrgNameById(list.get(i).getOrgId()));

				// 审核人
				if (null != list.get(i).getVerifyUsername()) {
					List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));
					list.get(i).setVerifyUsernameList(verifyUsernameList);
				}

			}
			mv.addObject("saleorderList", list);
			mv.addObject("page", map.get("page"));
		} catch (Exception e) {
			logger.error("modifyApplyindex:", e);
		}

		mv.addObject("saleorder", saleorderModifyApply);
		mv.setViewName("order/saleorder/modify_apply_index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 查看订单修改详情
	 *
	 * @param request
	 * @param saleorderModifyApply
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月17日 下午5:01:30
	 */
	@ResponseBody
	@RequestMapping(value = "viewModifyApply")
	public ModelAndView viewModifyApply(HttpServletRequest request, SaleorderModifyApply saleorderModifyApply) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("curr_user", curr_user);
		Integer saleorderId = saleorderModifyApply.getSaleorderId();

		// 获取订单修改信息
		SaleorderModifyApply saleorderModifyApplyInfo = saleorderService
				.getSaleorderModifyApplyInfo(saleorderModifyApply);
		mv.addObject("saleorderModifyApplyInfo", saleorderModifyApplyInfo);

		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);

		// 获取原订单基本信息
		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderId(saleorderId);
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
		try {
			saleorder.setCompanyId(curr_user.getCompanyId());

			// 获取原订单产品信息
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorder.getSaleorderId());
			List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
			mv.addObject("saleorderGoodsList", saleorderGoodsList);


            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
            List<Integer> skuIds = new ArrayList<>();
            saleorderGoodsList.stream().forEach(saleGood -> {
                skuIds.add(saleGood.getGoodsId());
            });
            List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
            Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
            mv.addObject("newSkuInfosMap", newSkuInfosMap);
            //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

			// 获取订单修改产品信息
			List<SaleorderModifyApplyGoods> saleorderModifyApplyGoodsList = saleorderService
					.getSaleorderModifyApplyGoodsById(saleorderModifyApply);
			mv.addObject("saleorderModifyApplyGoodsList", saleorderModifyApplyGoodsList);

		} catch (Exception e) {
			logger.error("viewModifyApply:", e);
		}

		mv.addObject("saleorder", saleorder);
		// 订单修改审核信息
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"editSaleorderVerify_" + saleorderModifyApply.getSaleorderModifyApplyId());
		mv.addObject("taskInfo", historicInfo.get("taskInfo"));
		mv.addObject("startUser", historicInfo.get("startUser"));
		// 最后审核状态
		mv.addObject("endStatus", historicInfo.get("endStatus"));
		mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mv.addObject("commentMap", historicInfo.get("commentMap"));
		mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		// 当前审核人
		String verifyUsers = null;
		List<String> verifyUserList = new ArrayList<>();
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
			String verifyUser = (String) taskInfoVariables.get("verifyUserList");
			if (null != verifyUser) {
				verifyUserList = Arrays.asList(verifyUser.split(","));
			}
		}
		List<String> verifyUsersList = new ArrayList<>();
		if (verifyUsers != null && !verifyUserList.isEmpty()) {
			verifyUsersList = Arrays.asList(verifyUsers.split(","));
		}

		mv.addObject("verifyUsers", verifyUsers);
		mv.addObject("verifyUserList", verifyUserList);
		mv.addObject("verifyUsersList", verifyUsersList);

		mv.setViewName("order/saleorder/view_modify_apply");
		return mv;
	}

	/**
	 *
	 * <b>Description: 申请修改订单前校验</b><br>
	 *
	 * @param request
	 * @param saleorder
	 * @return <b>Author: Franlin.wu</b> <br>
	 * 		<b>Date: 2019年5月13日 下午4:27:57 </b>
	 */
	@ResponseBody
	@RequestMapping(value = "applyToModifyHcSaleorderForTrader")
	public ResultInfo<?> applyToModifyHcSaleorderForTrader(HttpServletRequest request, Saleorder saleorder) {
		// 默认校验失败
		ResultInfo<?> result = new ResultInfo<>(ErpConst.ONE, "申请修改订单失败，请检查订单信息");
		// 获取订单基本信息
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);
		// 从代码角度，防止空指针
		if (null == saleorder) {
			result.setMessage("获取申请修改的订单信息失败，请检查订单信息");
			return result;
		}
		// 客户ID
		Integer traderId = saleorder.getTraderId();
		// 避免下面代码逻辑出现空指针等
		if (null == traderId) {
			result.setMessage("获取申请修改订单的客户ID不存在，请检查该订单的客户信息");
			return result;
		}

		// 针对耗材订单校验
		if (OrderConstant.ORDER_TYPE_HC.equals(saleorder.getOrderType())) {
			// 根据客户ID获取客户基本信息
			Trader trader = traderCustomerService.getBaseTraderByTraderId(traderId);
			if (null == trader) {
				result.setMessage("当前申请修改的订单的客户不存在，请检查该订单的客户信息");
				return result;
			}
			// 客户资质状态信息
			Integer traderStatus = trader.getTraderStatus();
			// 认证状态：0 未审核 1 待审核 2 审核通过 3 审核不通过 只有审核通过，才可申请审核成功
			if (!ErpConst.TWO.equals(traderStatus)) {
				// 根据客户资质状态返回描述语
				String traderStatusDesc = ErpConst.ZERO.equals(traderStatus) ? "未审核"
						: ErpConst.ONE.equals(traderStatus) ? "待审核"
						: ErpConst.THREE.equals(traderStatus) ? "审核不通过" : "未知";
				result.setMessage("当前订单的客户资质状态" + traderStatusDesc + ", 请联系耗材运营人员对该客户信息进行审核");
				return result;
			}
		}
		result.setCode(ErpConst.ZERO);
		result.setMessage("校验成功");

		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 验证销售订单必填项及账期
	 *
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年1月18日 上午11:33:06
	 */
	@ResponseBody
	@RequestMapping(value = "checkSaleorder",method = RequestMethod.POST)
	public ResultInfo<?> checkSaleorder(HttpServletRequest request, Saleorder saleorder) {
		// 获取订单基本信息
		saleorder = saleorderService.getBaseSaleorderInfo(saleorder);

		// add by franlin.wu for[耗材订单列表页增加资质状态字段--订单审核逻辑] at 2019-05-13 begin
		// 从代码角度，防止空指针
		if (null == saleorder) {
			return new ResultInfo<>(-1, "获取订单信息失败，请检查订单信息");
		}
		// 客户ID
		Integer traderId = saleorder.getTraderId();
		// 避免下面代码逻辑出现空指针等
		if (null == traderId) {
			return new ResultInfo<>(-1, "当前订单的客户ID不存在，请检查订单的客户信息");
		}
		// add by franlin.wu for[耗材订单列表页增加资质状态字段--订单审核逻辑] at 2019-05-13 end

		if (saleorder != null&&null!=saleorder.getAccountPeriodAmount()&&saleorder.getPrepaidAmount()!=null) {
			BigDecimal prepayAmount = saleorder.getPrepaidAmount().add(saleorder.getAccountPeriodAmount())
					.add(saleorder.getRetainageAmount());
			if (saleorder.getTotalAmount().compareTo(prepayAmount) != 0) {
				return new ResultInfo<>(-1, "订单金额与【预付金额+账期支付金额+尾款】不一致");
			}
		}
		// modify by franlin.wu for[使用常量] at 2019-05-13 begin
		// 非耗材订单
		if (!OrderConstant.ORDER_TYPE_HC.equals(saleorder.getOrderType())) {
			// modify by franlin.wu for[使用常量] at 2019-05-13 end
			// 根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(traderId);
			// 终端类型
			if (saleorder.getCustomerNature().intValue() == 465) {// 分销
				if (saleorder.getSalesAreaId() == null) {
					return new ResultInfo(-1, "销售区域不能为空");
				}
			}
			if (null==saleorder.getTraderId()||saleorder.getTraderId() == 0 ) {
				return new ResultInfo(-1, "客户不能为空");
			}
			if (null==saleorder.getCustomerType()  ||saleorder.getCustomerType() == 0  ) {
				return new ResultInfo(-1, "客户类型不能为空");
			}
			if (null==saleorder.getCustomerNature() ||saleorder.getCustomerNature() == 0 ) {
				return new ResultInfo(-1, "客户终端类型不能为空");
			}
			if (null==saleorder.getTraderContactId() ||  null==saleorder.getTraderContactName() || saleorder.getTraderContactId() == 0
					|| saleorder.getTraderContactName() == "" ) {
				return new ResultInfo(-1, "客户联系人不能为空");
			}
			if ( null ==saleorder.getTraderContactMobile()  || saleorder.getTraderContactMobile() == "") {
				return new ResultInfo(-1, "客户联系人手机号不能为空");
			}
			if (null == saleorder.getTraderAddressId()  ||  null==saleorder.getTraderAddress() ||saleorder.getTraderAddressId() == 0
					|| saleorder.getTraderAddress() == "" ) {
				return new ResultInfo(-1, "客户联系地址不能为空");
			}
			if ( null==saleorder.getTakeTraderId() || null==saleorder.getTakeTraderName() ||saleorder.getTakeTraderId() == 0 ||
					saleorder.getTakeTraderName() == "" ) {
				return new ResultInfo(-1, "收货客户不能为空");
			}
			if (null==saleorder.getTakeTraderContactName() ||null==saleorder.getTakeTraderContactId()||saleorder.getTakeTraderContactId() == 0 ||
					saleorder.getTakeTraderContactName() == "" ) {
				return new ResultInfo(-1, "收货联系人不能为空");
			}
			if (null==saleorder.getTakeTraderContactMobile()  || saleorder.getTakeTraderContactMobile() == "") {
				return new ResultInfo(-1, "收货联系人手机号不能为空");
			}
			if (null==saleorder.getTakeTraderAddressId() || null==saleorder.getTakeTraderAddress() || saleorder.getTakeTraderAddressId() == 0 ||
					saleorder.getTakeTraderAddress() == "" ) {
				return new ResultInfo(-1, "收货地址不能为空");
			}
			if (null==saleorder.getInvoiceTraderId() ||saleorder.getInvoiceTraderId() == 0
					|| saleorder.getInvoiceTraderName() == "" || saleorder.getInvoiceTraderName() == null) {
				return new ResultInfo(-1, "收票客户不能为空");
			}
			if (null==saleorder.getInvoiceTraderContactId()|| null==saleorder.getInvoiceTraderContactName() ||saleorder.getInvoiceTraderContactId() == 0
					|| saleorder.getInvoiceTraderContactName() == ""
			) {
				return new ResultInfo(-1, "收票联系人不能为空");
			}
			if (null==saleorder.getInvoiceTraderContactMobile()  || saleorder.getInvoiceTraderContactMobile() == "") {
				return new ResultInfo(-1, "收票联系人手机号不能为空");
			}
			if (null==saleorder.getInvoiceTraderAddressId()||null==saleorder.getInvoiceTraderAddress()||saleorder.getInvoiceTraderAddressId() == 0
					|| saleorder.getInvoiceTraderAddress() == ""  ) {
				return new ResultInfo(-1, "收票地址不能为空");
			}
			if (null== saleorder.getInvoiceType() ||saleorder.getInvoiceType() == 0 ) {
				return new ResultInfo(-1, "发票类型不能为空");
			}
			if (saleorder.getHaveAccountPeriod() == 1 && saleorder.getAccountPeriodAmount()
					.compareTo(customer.getAccountPeriodLeft().add(saleorder.getAccountPeriodAmount())) == 1) {
				return new ResultInfo(-1, "使用账期金额超出剩余账期金额");
			}
		} else {
			// add by franlin.wu for[耗材订单列表页增加资质状态字段--订单审核逻辑] at 2019-05-13 begin
			// 根据客户ID获取客户基本信息
			Trader trader = traderCustomerService.getBaseTraderByTraderId(traderId);
			if (null == trader) {
				return new ResultInfo<>(-1, "当前订单的客户不存在，请检查该订单的客户信息");
			}
			// 客户资质状态信息
			Integer traderStatus = trader.getTraderStatus();
			// 认证状态：0 未审核 1 待审核 2 审核通过 3 审核不通过 只有审核通过，才可申请审核成功
			if (!ErpConst.TWO.equals(traderStatus)) {
				// 根据客户资质状态返回描述语
				String traderStatusDesc = ErpConst.ZERO.equals(traderStatus) ? "未审核"
						: ErpConst.ONE.equals(traderStatus) ? "待审核"
						: ErpConst.THREE.equals(traderStatus) ? "审核不通过" : "未知";
				return new ResultInfo<>(-1, "当前订单的客户资质状态" + traderStatusDesc + ", 请联系耗材运营人员对该客户信息进行审核");
			}
			// add by franlin.wu for[耗材订单列表页增加资质状态字段--订单审核逻辑] at 2019-05-13 end

			// add by Tomcat.Hui 2020/1/2 14:29 .Desc: VDERP-1039 票货同行. start
			try {
				boolean phtxFlag = invoiceService.getOrderIsGoodsPeer(saleorder.getSaleorderId());
				if (phtxFlag) {
					//满足票货同行条件即开票方式为电子票，必然是增值税普通发票
					TraderCertificateVo tc = new TraderCertificateVo();
					tc.setTraderId(trader.getTraderId());
					tc.setTraderType(ErpConst.ONE);
					if (trader.getCustomerNature() != null && trader.getCustomerNature() == 465) {
						tc.setCustomerType(2);
					} else {
						tc.setCustomerType(1);
					}
					Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(tc, "all");
					JSONObject json = JSONObject.fromObject(map.get("finance"));
					if (null != json) {
						TraderFinanceVo tf = (TraderFinanceVo) JSONObject.toBean(json, TraderFinanceVo.class);
						//税号为空时,必须税号审核状态为审核通过才允许申请开票
						if(StringUtil.isBlank(tf.getTaxNum())){
							if(null != tf.getCheckStatus() && tf.getCheckStatus() == 1){
								return new ResultInfo(0, "操作成功");
							}else{
								return new ResultInfo<>(-1,"客户开票资料不全，请联系归属销售进行维护");
							}
						}
					} else {
						return new ResultInfo<>(-1,"未查询到财务资质信息");
					}
				}
			}catch (Exception e) {
				logger.error("校验客户税号产生异常:{}",e);
				return new ResultInfo<>(-1,"校验客户税号产生异常");
			}
			// add by Tomcat.Hui 2020/1/2 14:29 .Desc: VDERP-1039 票货同行. end
		}

		return new ResultInfo(0, "操作成功");
	}

	@ResponseBody
	@RequestMapping(value = "/batchAddSaleGoodsInit")
	public ModelAndView batchAddSaleGoodsInit(HttpServletRequest request, Saleorder saleorder) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("saleorder", saleorder);
		mv.setViewName("order/saleorder/batch_add_sale_goods");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存销售单批量新增商品
	 *
	 * @param request
	 * @param saleorder
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年4月20日 上午10:34:11
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBatchAddSaleGoods")
	public ResultInfo<?> saveBatchAddSaleGoods(HttpServletRequest request, Saleorder saleorder,
											   @RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/saleorder");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

			if (fileInfo.getCode() == 0) {// 上传成功

				if (user != null) {
					saleorder.setCompanyId(user.getCompanyId());

					saleorder.setAddTime(DateUtil.gainNowDate());
					saleorder.setCreator(user.getUserId());
					saleorder.setUpdater(user.getUserId());
					saleorder.setModTime(DateUtil.gainNowDate());
				}
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
				int endRowNum = sheet.getLastRowNum();// 结束行

				int startCellNum = 0;// 默认从第一列开始（防止第一列为空）
				// int endCellNum = sheet.getRow(0).getLastCellNum() - 1;//列数
				List<SaleorderGoods> list = new ArrayList<>();// 保存Excel中读取的数据
				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					// 获取excel的值
					SaleorderGoods sg = new SaleorderGoods();
					setUserMsg(sg, user, DateUtil.gainNowDate());
					for (int cellNum = startCellNum; cellNum <= 3; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);
						try {
							if ((cell == null || "".equals(cell)) && cellNum <= 3) {// cell==null单元格空白（无内容，默认空）
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
								throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
							}
							Integer type = cell.getCellType();
							String str = null;
							Double pice = null;
							Integer num = null;
							BigDecimal bd = null;
							switch (cellNum) {
								case 0:// SKU（唯一识别，不为空）
									switch (type) {
										case Cell.CELL_TYPE_STRING:// 字符类型
											str = cell.getStringCellValue();
											if (str == null || "".equals(str)) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
											}
											if (!str.matches("[A-Za-z0-9]+")) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
											}
											break;
										default:
											resultInfo.setMessage(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
											throw new Exception(
													"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：SKU值不符合规则，请验证！");
									}
									for (int x = 0; x < list.size(); x++) {
										if (str.equals(list.get(x).getSku())) {
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：SKU在此Excel表格中存在重复，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1)
													+ "列：SKU在此Excel表格中存在重复，请验证！");
										}
									}
									sg.setSku(str);
									break;
								case 1:// 数量
									switch (type) {
										case Cell.CELL_TYPE_NUMERIC:// 数字类型
											num = (int) cell.getNumericCellValue();
											if (num == null || "".equals(num)) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
											}
											if (!(num + "").matches("^[1-9]+[0-9]*$")) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
											}
											break;
										default:
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
									sg.setNum(Integer.valueOf(num));
									break;
								case 2:// 单价
									switch (type) {
										case Cell.CELL_TYPE_NUMERIC:// 数字类型
											pice = cell.getNumericCellValue();
											if (pice == null) {
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不允许为空，请验证！");
											}
//											if () {
											BigDecimal priceDecimal=new BigDecimal(pice);
											if(!(StringUtil.doubleToString(pice)).matches("[0-9]{1,14}\\.{0,1}[0-9]{0,2}")||priceDecimal.compareTo(OrderConstant.AMOUNT_LIMIT)==1){
												resultInfo.setMessage(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
												throw new Exception(
														"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值不符合规则，请验证！");
											}
											break;
										default:
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
									bd = new BigDecimal(pice);
									sg.setPrice(bd.setScale(2, BigDecimal.ROUND_HALF_UP));
									if(sg.getPrice().multiply(new BigDecimal(sg.getNum())).compareTo(OrderConstant.AMOUNT_LIMIT)==1){
										resultInfo.setMessage("第:" + (rowNum + 1) + "行,单个商品总额超过三亿,请验证!");
										throw new Exception("第:" + (rowNum + 1) + "行,单个商品总额超过三亿,请验证!");
									}
									break;
								case 3:// 获取
									switch (type) {
										case Cell.CELL_TYPE_NUMERIC:// 数字类型
											str = cell.getNumericCellValue() + "";
											break;
										case Cell.CELL_TYPE_STRING:// 字符类型
											str = cell.getStringCellValue();
											break;
										case Cell.CELL_TYPE_BLANK:
											resultInfo
													.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：不允许为空，请验证！");
										default:
											resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
											throw new Exception("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值错误，请验证！");
									}
									if (StringUtils.isNotBlank(str) && str.length() > 32) {
										resultInfo.setMessage(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值超出最大范围，请验证！");
										throw new Exception(
												"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：值超出最大范围，请验证！");
									}
									sg.setDeliveryCycle(str);
									break;
								default:
									resultInfo.setMessage(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
									throw new Exception(
											"第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列：获取参数值异常，请稍等重试或联系管理员！");
							}
						} catch (Exception e) {
							logger.error("saveBatchAddSaleGoods 1:", e);
							if ("操作失败".equals(resultInfo.getMessage())) {
								resultInfo.setMessage("第:" + (rowNum + 1) + "行，第:" + (cellNum + 1) + "列读取错误，请验证！");
							}
							return resultInfo;
						}
					}
					sg.setSaleorderId(saleorder.getSaleorderId());
					list.add(sg);
				}
				if (list != null && !list.isEmpty()) {
					saleorder.setGoodsList(list);
					// 批量保存
					resultInfo = saleorderService.saveBatchAddSaleGoods(saleorder);
				} else {
					resultInfo.setMessage("Excel数据读取异常！");
				}
			}
		} catch (Exception e) {
			logger.error("saveBatchAddSaleGoods 2:", e);
			return new ResultInfo<>(-1, "Excel数据读取异常");
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存参考成本
	 *
	 * @param request
	 * @param referenceCostPriceArr
	 * @param saleorderGoodsIdArr
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年7月11日 下午2:15:06
	 */
	@ResponseBody
	@RequestMapping(value = "saveReferenceCostPrice")
	@SystemControllerLog(operationType = "edit", desc = "保存参考成本")
	public ResultInfo<?> saveReferenceCostPrice(HttpServletRequest request, Integer saleorderId,
												@RequestParam(required = false, value = "referenceCostPriceArr") String referenceCostPriceArr,
												@RequestParam(required = false, value = "saleorderGoodsIdArr") String saleorderGoodsIdArr) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

		List<Double> referenceCostPriceList = JSON.parseArray(request.getParameter("referenceCostPriceArr").toString(),
				Double.class);
		List<Integer> saleorderGoodsIdList = JSON.parseArray(request.getParameter("saleorderGoodsIdArr").toString(),
				Integer.class);
		if (referenceCostPriceList.isEmpty()) {
			return new ResultInfo(0, "操作成功");
		} else {
			List<SaleorderGoods> saleorderGoodsList = new ArrayList<>();
			for (int i = 0; i < saleorderGoodsIdList.size(); i++) {
				SaleorderGoods saleorderGoods = new SaleorderGoods();
				saleorderGoods.setSaleorderGoodsId(saleorderGoodsIdList.get(i));
				BigDecimal referenceCostPrice = new BigDecimal(referenceCostPriceList.get(i));
				saleorderGoods.setReferenceCostPrice(referenceCostPrice);
				saleorderGoodsList.add(saleorderGoods);
			}
			Saleorder saleorder = new Saleorder();
			saleorder.setGoodsList(saleorderGoodsList);
			// 保存参考成本
			ResultInfo<?> resultInfo = new ResultInfo<>();
			resultInfo = saleorderService.saveBatchReferenceCostPrice(saleorder);
			// 查询该订单下的产品哪些产品成本价未填写并记录对应的采购人员
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorderId);
			sale.setCompanyId(user.getCompanyId());
			List<SaleorderGoods> saleorderGoodsLists = saleorderService.getSaleorderGoodsById(sale);
			if (saleorderGoodsLists != null && saleorderGoodsLists.size() > 0) {
				List<Integer> categoryIdList = new ArrayList<>();
				for (int i = 0; i < saleorderGoodsLists.size(); i++) {
					if (saleorderGoodsLists.get(i).getGoods().getCategoryId() == null) {
						categoryIdList.add(0);
					} else {
						categoryIdList.add(saleorderGoodsLists.get(i).getGoods().getCategoryId());
					}
				}
				categoryIdList = new ArrayList<Integer>(new HashSet<Integer>(categoryIdList));
				// 根据分类查询对应分类归属，如果是为分配的返回产品部默认人
				List<User> categoryUserList = categoryService.getCategoryOwner(categoryIdList, user.getCompanyId());
				for (int i = 0; i < saleorderGoodsLists.size(); i++) {
					for (int j = 0; j < categoryUserList.size(); j++) {
						if (saleorderGoodsLists.get(i).getGoods().getCategoryId() == null) {
							saleorderGoodsLists.get(i).getGoods().setCategoryId(0);
						}
						if (categoryUserList.get(j).getCategoryId()
								.equals(saleorderGoodsLists.get(i).getGoods().getCategoryId())) {
							saleorderGoodsLists.get(i)
									.setGoodsUserIdStr((saleorderGoodsLists.get(i).getGoodsUserIdStr() == null ? ""
											: saleorderGoodsLists.get(i).getGoodsUserIdStr() + ";")
											+ categoryUserList.get(j).getUserId() + ";");
						}
					}
				}
				// 未填写成本价的采购人员集合
				List<String> noPriceBuyorderUser = new ArrayList<>();
				for (int i = 0; i < saleorderGoodsLists.size(); i++) {
					// 参考成本为0
					if (saleorderGoodsLists.get(i).getReferenceCostPrice().compareTo(BigDecimal.ZERO) == 0) {
						if (saleorderGoodsLists.get(i).getGoodsUserIdStr() != null) {
							String goodsUserIdStr = saleorderGoodsLists.get(i).getGoodsUserIdStr();
							List<String> goodsUserIds = Arrays.asList(goodsUserIdStr.split(";"));
							if (goodsUserIds != null && goodsUserIds.size() > 0) {
								noPriceBuyorderUser.addAll(goodsUserIds);
							}
						}
					}
				}
				// noPriceBuyorderUser 去重
				String costUserIds = "";
				if (noPriceBuyorderUser != null && noPriceBuyorderUser.size() > 0) {
					noPriceBuyorderUser = new ArrayList(new HashSet(noPriceBuyorderUser));
					costUserIds = StringUtils.join(noPriceBuyorderUser.toArray(), ",");
				}
				Saleorder saleorderInfo = new Saleorder();
				saleorderInfo.setSaleorderId(saleorderId);
				saleorderInfo.setCostUserIds(costUserIds);
				saleorderService.saveEditSaleorderInfo(saleorderInfo, request, request.getSession());

			}
			return resultInfo;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转销售聚合页
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月21日 上午11:10:59
	 */
	@ResponseBody
	@RequestMapping(value = "toSaleJHSearchPage")
	public ModelAndView toSaleJHSearchPage(HttpServletRequest request, Category cat) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView("/order/saleorder/jh_search_index");
		cat.setCompanyId(user.getCompanyId());
		cat.setLevel(1);
		List<Category> list = categoryService.getCategoryListByParam(cat);
		mav.addObject("list", list);

		// 是否展示新品图标
		AdVo adVo = new AdVo();
		adVo.setCompanyId(user.getCompanyId());
		adVo.setBannerName("销售首页广告位");
		List<AdVo> adlist = adService.getAdVoList(adVo);
		if (adlist == null || adlist.size() == 0) {
			mav.addObject("show", 0);
		} else {
			mav.addObject("show", 1);
		}
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询销售聚合产品分类信息
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月21日 上午11:10:59
	 */
	@ResponseBody
	@RequestMapping(value = "getSaleJHCategory")
	public ResultInfo<?> getSaleJHCategory(HttpServletRequest request, Category cat) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		cat.setCompanyId(user.getCompanyId());
		List<Category> list = categoryService.getCategoryListByParam(cat);
		ResultInfo<?> res = new ResultInfo<>(0, "查询成功", list);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询三级分类的属性信息
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月21日 上午11:10:59
	 */
	@ResponseBody
	@RequestMapping(value = "getSaleJHCategoryValueList")
	public ResultInfo<?> getSaleJHCategoryValueList(HttpServletRequest request, CategoryAttribute cat) {
		List<CategoryAttribute> list = categoryAttributeService.getAttributeByCategoryId(cat);
		ResultInfo<?> res = new ResultInfo<>(0, "查询成功", list);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 产品列表（分页）
	 *
	 * @param request
	 * @param goods
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年5月24日 下午6:12:34
	 */
	@ResponseBody
	@RequestMapping(value = "/getSaleJHGoodsListPage")
	public ModelAndView getSaleJHGoodsListPage(HttpServletRequest request, Goods goods,
											   @RequestParam(required = false, defaultValue = "1") Integer pageNo,
											   @RequestParam(required = false) Integer pageSize) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		goods.setCompanyId(user.getCompanyId());
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> map = goodsService.getGoodsListPage(request, goods, page, request.getSession());
		List<Goods> list = (List<Goods>) map.get("list");
		Category cat = new Category();
		cat.setCompanyId(user.getCompanyId());
		cat.setLevel(1);
		List<Category> calist = categoryService.getCategoryListByParam(cat);
		mv.addObject("list", calist);
		mv.addObject("goodsList", list);
		mv.addObject("goods", goods);
		mv.addObject("page", map.get("page"));
		mv.addObject("name1", request.getParameter("name1"));
		mv.addObject("name2", request.getParameter("name2"));
		mv.addObject("name3", request.getParameter("name3"));
		// 是否展示新品图标
		AdVo adVo = new AdVo();
		adVo.setCompanyId(user.getCompanyId());
		adVo.setBannerName("销售首页广告位");
		List<AdVo> adlist = adService.getAdVoList(adVo);
		if (adlist == null || adlist.size() == 0) {
			mv.addObject("show", 0);
		} else {
			mv.addObject("show", 1);
		}
		mv.setViewName("/order/saleorder/jh_search_index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转销售聚合页的产品详情
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月21日 上午11:10:59
	 */
	@ResponseBody
	@RequestMapping(value = "toSaleJHGoodsDetailPage")
	public ModelAndView toSaleJHGoodsDetailPage(HttpServletRequest request, Goods goods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView("/order/saleorder/sale_juhe_goods_detail");
		goods.setCompanyId(user.getCompanyId());
		// 获取核价信息
		Integer type = 0;// 销售
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goods.getGoodsId());
		map.put("type", type);
		map.put("provinceName", "中国");
		List<GoodsChannelPrice> goodsChannelPriceList = goodsChannelPriceService.getGoodsChannelPriceByGoodsId(map);
		mav.addObject("goodsChannelPriceList", goodsChannelPriceList);

		GoodsOpt goodsOpt = new GoodsOpt();
		// 获取配件信息
		goodsOpt.setParentGoodsId(goods.getGoodsId());
		List<GoodsOpt> listPackage = goodsService.getGoodsPackageList(goodsOpt);
		if (listPackage != null && listPackage.size() > 0) {
			List<GoodsOpt> list = new ArrayList<>();
			Iterator<GoodsOpt> it = listPackage.iterator();
			while (it.hasNext()) {
				GoodsOpt go = it.next();
				if (go.getGoodsType() == 317 || go.getGoodsType() == 653) {
					list.add(go);
				}
			}
			mav.addObject("listPackage", list);
		}

		// 获取管理产品信息
		goodsOpt.setGoodsId(goods.getGoodsId());
		List<GoodsOpt> listRecommend = goodsService.getGoodsRecommendList(goodsOpt);
		mav.addObject("listRecommend", listRecommend);

		// 获取应用科室
		List<GoodsSysOptionAttribute> goodsSysOptionAttributeList = goodsService.getGoodsSysOptionAttributeList(goods);
		mav.addObject("goodsSysOptionAttributeList", goodsSysOptionAttributeList);
		// 查询产品的详情信息
		GoodsVo goodsVo = goodsService.getSaleJHGoodsDetail(goods);
		mav.addObject("goods", goodsVo);
		mav.addObject("domain", domain);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增产品常见问题
	 *
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月25日 下午5:16:08
	 */
	@ResponseBody
	@RequestMapping(value = "saveGoodsVoFaqs")
	@SystemControllerLog(operationType = "add", desc = "保存新增产品常见问题")
	public ResultInfo<?> saveGoodsVoFaqs(HttpServletRequest request, GoodsVo goods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		goods.setAddTime(DateUtil.sysTimeMillis());
		goods.setCreator(user.getUserId());
		ResultInfo<?> res = goodsService.saveGoodsVoFaqs(goods);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存编辑产品常见问题
	 *
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月25日 下午5:16:08
	 */
	@ResponseBody
	@RequestMapping(value = "saveEditGoodsVoFaqs")
	@SystemControllerLog(operationType = "add", desc = "保存编辑产品常见问题")
	public ResultInfo<?> saveEditGoodsVoFaqs(HttpServletRequest request, GoodsVo goods) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		goods.setAddTime(DateUtil.sysTimeMillis());
		goods.setCreator(user.getUserId());
		ResultInfo<?> res = goodsService.saveGoodsVoFaqs(goods);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询当前产品的常见问题
	 *
	 * @param request
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月25日 下午5:16:08
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsVoFaqs")
	public ResultInfo<?> getGoodsVoFaqs(HttpServletRequest request, Goods goods) {
		ResultInfo<?> res = goodsService.getGoodsVoFaqs(goods);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 下载文件或图片
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年6月25日 下午5:16:08
	 */
	@ResponseBody
	@RequestMapping(value = "downloadFile")
	public ResultInfo<?> downloadFile(HttpServletRequest request, String url) {
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		String desktopPath = desktopDir.getAbsolutePath();
		// 创建不同的文件夹目录
		File file = new File(desktopPath + "\\download");
		// 判断文件夹是否存在
		if (!file.exists()) {
			// 如果文件夹不存在，则创建新的的文件夹
			file.mkdirs();
		}
		FileOutputStream fileOut = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 建立链接
			URL httpUrl = new URL(url);
			conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// post方式不能使用缓存
			conn.setUseCaches(false);
			conn.setConnectTimeout(30000);

			// 连接指定的资源
			conn.connect();
			// 获取网络输入流
			inputStream = conn.getInputStream();
			bis = new BufferedInputStream(inputStream);

			// 写入到文件（注意文件保存路径的后面一定要加上文件的名称）
			File filename = new File(desktopPath + "\\download" + "\\" + url.substring(url.lastIndexOf("/")));
			fileOut = new FileOutputStream(filename);
			bos = new BufferedOutputStream(fileOut);

			byte[] buf = new byte[4096];
			int length = bis.read(buf);
			// 保存文件
			while (length != -1) {
				bos.write(buf, 0, length);
				length = bis.read(buf);
			}

			return new ResultInfo<>(0, "下载成功，文件已下载到桌面download文件夹");
		} catch (Exception e) {
			logger.error("downloadFile:", e);
			return new ResultInfo<>();
		} finally {
			try {
				bos.close();
				fileOut.close();
				bis.close();
				inputStream.close();
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
			conn.disconnect();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 新增点击标志
	 *
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年7月3日 上午11:43:55
	 */
	@ResponseBody
	@RequestMapping("/saveReadNewFlag")
	public ResultInfo<?> saveReadNewFlag(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Integer res = readService.saveRead(user);
		if (res > 0) {
			return new ResultInfo<>(0, "操作成功");
		}
		return new ResultInfo<>();
	}

	// 每3位中间添加逗号的格式化显示
	public static String getCommaFormat(BigDecimal value) {
		return getFormat(",###.##", value);
	}

	// 自定义数字格式方法
	public static String getFormat(String style, BigDecimal value) {
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(style);// 将格式应用于格式化器
		return df.format(value.doubleValue());
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑销售订单商品页面初始化
	 *
	 * @param request
	 * @param saleorderGoodsIdArr
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年8月3日 上午9:35:37
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSaleGoodsInit")
	public ModelAndView updateSaleGoodsInit(HttpServletRequest request,
											@RequestParam(required = false, value = "saleorderGoodsIdArr") String saleorderGoodsIdArr,
											@RequestParam(required = false, value = "saleorderId") Integer saleorderId) {
		// User user =
		// (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("saleorderGoodsIdArr", saleorderGoodsIdArr);
		mv.addObject("saleorderId", saleorderId);
		mv.setViewName("/order/saleorder/update_sale_goods");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 销售订单商品编辑保存
	 *
	 * @param request
	 * @param saleorderGoods
	 * @param saleorderGoodsIdArr
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年8月3日 上午10:40:19
	 */
	@ResponseBody
	@RequestMapping("/updateSaleGoodsSave")
	public ResultInfo<?> updateSaleGoodsSave(HttpServletRequest request, SaleorderGoods saleorderGoods,
											 @RequestParam(required = false, value = "saleorderGoodsIdArr") String saleorderGoodsIdArr) {
		List<Integer> saleOrderGoodsIdList = JSON.parseArray("[" + saleorderGoodsIdArr + "]", Integer.class);
		if (saleOrderGoodsIdList == null || saleOrderGoodsIdList.size() == 0) {
			return new ResultInfo<>(-1, "参数错误");
		}
		saleorderGoods.setSaleorderGoodsIdList(saleOrderGoodsIdList);
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if (user != null) {
			saleorderGoods.setCompanyId(user.getCompanyId());
			saleorderGoods.setModTime(DateUtil.gainNowDate());
			saleorderGoods.setUpdater(user.getUserId());
		}
		return saleorderService.updateSaleGoodsSave(saleorderGoods);
	}

	/**
	 * <b>Description:</b><br>
	 * 合同回传审核
	 *
	 * @param request
	 * @param taskId
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年9月20日 上午9:35:02
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/editApplyValidContractReturn")
	public ResultInfo<?> editApplyValidContractReturn(HttpServletRequest request, Saleorder saleorder, String taskId,
													  HttpSession session) {
		try {
			// 增加判断：如果未上传附件，直接返回提示信息 chuck 20190308
			List<Attachment> saleorderAttachmentList = saleorderService
					.getSaleorderAttachmentList(saleorder.getSaleorderId());

			List<Attachment> tempList = new ArrayList<Attachment>();
			for (Attachment attach : saleorderAttachmentList) {
				if (attach.getAttachmentFunction() == 492) {
					tempList.add(attach);
				}
			}
			if (tempList.size() == 0) {
				return new ResultInfo(-1, "您尚未上传附件，请确认后再申请。");
			}

			Map<String, Object> variableMap = new HashMap<String, Object>();
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			Saleorder saleorderInfo = new Saleorder();
			saleorder.setOptType("orderDetail");
			saleorderInfo = saleorderService.getBaseSaleorderInfoNew(saleorder);
			// 查询当前订单的一些状态
			// TraderCustomerVo traderBaseInfo =
			// traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {
				variableMap.put("saleorderInfo", saleorderInfo);
				variableMap.put("currentAssinee", user.getUsername());
				variableMap.put("processDefinitionKey", "contractReturnVerify");
				variableMap.put("businessKey", "contractReturnVerify_" + saleorder.getSaleorderId());
				variableMap.put("relateTableKey", saleorder.getSaleorderId());
				variableMap.put("relateTable", "T_SALEORDER");
				actionProcdefService.createProcessInstance(request, "contractReturnVerify",
						"contractReturnVerify_" + saleorder.getSaleorderId(), variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"contractReturnVerify_" + saleorder.getSaleorderId());
			if (historicInfo.get("endStatus") != "审核完成") {
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 默认审批通过
				ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
						user.getUsername(), variables);
				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}
			}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("editApplyValidContractReturn:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 *
	 * <b>Description:</b>合同回传审核(无TOEKN)
	 *
	 * @param request
	 * @param saleorder
	 * @param taskId
	 * @param session
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月12日 下午7:00:41
	 */
	@ResponseBody
	@RequestMapping(value = "/editApplyValidContractReturnNoto")
	public ResultInfo<?> editApplyValidContractReturnNotoken(HttpServletRequest request, Saleorder saleorder,
															 String taskId, HttpSession session) {

		List<Attachment> saleorderAttachmentList = saleorderService
				.getSaleorderAttachmentList(saleorder.getSaleorderId());

		List<Attachment> tempList = new ArrayList<Attachment>();
		for (Attachment attach : saleorderAttachmentList) {
			if (attach.getAttachmentFunction() == 492) {
				tempList.add(attach);
			}
		}
		if (tempList.size() == 0) {
			return new ResultInfo(-1, "您尚未上传附件，请确认后再申请。");
		}
		return new ResultInfo(0, "可以上传附件。");
	}

	/**
	 * <b>Description:</b>
	 *
	 * @param request
	 * @param express
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> lijie <b>Date:</b> 2018年12月18日 下午5:04:13
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMeinianExpress")
	public ResultInfo<?> sendMeinianExpress(HttpServletRequest request, Express express) {
		ResultInfo resultInfo = new ResultInfo<>();
		if (null != express && null != express.getExpressId()) {

			String sendMeinianOrders = warehouseInService.sendMeinianOrders(express);
			if ("S".equals(sendMeinianOrders)) {
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			} else {
				resultInfo.setMessage(sendMeinianOrders);
			}
		}

		return resultInfo;
	}

	/*
	 * * 功能描述: 修改订单及开票申请税率页面初始化
	 *
	 * @param: [orderId, invoiceApplyId]
	 *
	 * @return: org.springframework.web.servlet.ModelAndView
	 *
	 * @auther: duke.li
	 *
	 * @date: 2019/3/29 11:10
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/editOrderRatioInit")
	public ModelAndView editOrderRatioInit(@RequestParam(required = false, value = "orderId") String orderId, // 订单ID
										   @RequestParam(required = false, value = "invoiceType") String invoiceType) {
		ModelAndView mv = new ModelAndView();
		// 发票类型
		List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mv.addObject("invoiceTypeList", invoiceTypeList);
		mv.addObject("orderId", orderId);
		mv.addObject("invoiceType", invoiceType);

		mv.setViewName("order/saleorder/edit_order_ratio");
		return mv;
	}

	/*
	 * * 功能描述: 修改订单税率保存
	 *
	 * @param: [orderId, invoiceApplyId, invoiceType]
	 *
	 * @return: com.vedeng.common.model.ResultInfo
	 *
	 * @auther: duke.li
	 *
	 * @date: 2019/3/29 14:46
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveOrderRatioEdit")
	public ResultInfo saveOrderRatioEdit(@RequestParam(required = false, value = "orderId") Integer orderId,
										 @RequestParam(required = false, value = "invoiceType") Integer invoiceType) {
		return saleorderService.saveOrderRatioEdit(orderId, invoiceType);
	}
	/*
	 * 功能描述 : 接收贝登精选的BD订单
	 * @return: com.vedeng.common.model.ResultInfo
	 *
	 * @auther: strange.dai
	 * @date: 2019/7/15 17:42
	 */
	@ResponseBody
	@RequestMapping(value ="/saveBDAddSaleorder", produces = "application/json;charset=UTF-8")
	public ResultInfo saveBDAddSaleorder(@RequestBody(required = false) OrderData orderData) {
		ResultInfo resultInfo = new ResultInfo<>();
		logger.info(JSONObject.fromObject(orderData).toString());
		if(StringUtils.isBlank(orderData.getPhone())) {
			resultInfo.setCode(-1);
			resultInfo.setMessage("注册手机号为空");
			return resultInfo;
		}
		Saleorder s = new Saleorder();
		s.setSaleorderNo(orderData.getOrderNo());
		Saleorder saleorder = saleorderService.getSaleorderBySaleorderNo(s);
		if(saleorder!=null) {
			resultInfo.setCode(0);
			resultInfo.setMessage("订单号重复");
			return resultInfo;
		}
		Saleorder saveAddSaleorder = saleorderService.saveBDAddSaleorder(orderData);
		if(saveAddSaleorder!=null) {
			resultInfo.setMessage("成功");
			resultInfo.setData(saveAddSaleorder);
			resultInfo.setCode(0);
		}else {
			resultInfo.setCode(-1);
			resultInfo.setMessage("失败");
		}
		return resultInfo;
	}
	/**
	 * @Title: updateBDSaleStatus
	 * @Description: TODO(同步贝登精选BD订单状态)
	 * @param @param orderData
	 * @return ResultInfo    返回类型
	 * @author strange
	 * @throws
	 * @date 2019年7月16日
	 */
	@ResponseBody
	@RequestMapping(value="/updateBDSaleStatus", produces = "application/json;charset=UTF-8")
	public ResultInfo updateBDSaleStatus(@RequestBody(required = false) OrderData orderData) {
		ResultInfo resultInfo = new ResultInfo<>();
		logger.info(JSONObject.fromObject(orderData).toString());
		if(orderData!=null) {
			Integer rows = saleorderService.updateBDSaleStatus(orderData);
			if(rows!=null&&rows>0) {
				resultInfo.setMessage("成功");
				resultInfo.setCode(0);
			}else {
				resultInfo.setMessage("失败");
				resultInfo.setCode(-1);
			}
		}else {
			resultInfo.setMessage("失败");
			resultInfo.setCode(-1);
		}
		return resultInfo;
	}


	/**
	 * @Title: returnBDStatus
	 * @Description: TODO(撤回BD订单)
	 * @return ResultInfo    返回类型
	 * @author strange
	 * @throws
	 * @date 2019年7月31日
	 */
	@ResponseBody
	@RequestMapping(value="/returnBDStatus", produces = "application/json;charset=UTF-8")
	public ResultInfo returnBDStatus(Integer saleorderId) {
		ResultInfo resultInfo = new ResultInfo<>();
		if(saleorderId!=null) {
			resultInfo = saleorderService.returnBDStatus(saleorderId);
		}else {
			resultInfo.setMessage("订单id为空");
			resultInfo.setCode(-1);
		}
		return resultInfo;
	}

	/**
	* @Description: TODO(订单号查询接口)
	* @param @param saleorder
	* @param @return
	* @return ResultInfo
	* @author strange
	* @throws
	* @date 2019年10月12日
	*/
	@ResponseBody
	@RequestMapping(value="/searchOrderInfo",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public ResultInfo searchOrderInfo(@RequestBody(required = false)Saleorder order) {
		ResultInfo resultInfo = new ResultInfo<>();
		String orderNos = order.getSaleorderNo();
		Integer type=0;
		if(null != order && order.getType() != null){
			type=order.getType();
		}
		if (StringUtil.isNotEmpty(orderNos)) {
			String[] orders = orderNos.split(",");
			ArrayList<String> orderNoList = new ArrayList<String>();
			for (int i = 0; i < orders.length; i++) {
				if(StringUtil.isNotEmpty(orders[i])) {
				orderNoList.add(orders[i]);
				}
			}

			List<Saleorder> info = saleorderService.getSaleOrderInfoBySaleorderNo(orderNoList,type);
			if (info != null) {
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setData(info);
			}else {
				resultInfo.setCode(-1);
				resultInfo.setMessage("查询失败");
			}
		}
		return resultInfo;
	}
	/**
	* @Description: TODO( HC订单修改订单收货状态)
	* @param @param orderNos
	* @param @return
	* @return ResultInfo
	* @author strange
	* @throws
	* @date 2019年10月12日
	*/
	@Transactional
	@ResponseBody
	@RequestMapping(value="/updateOrderDeliveryStatus",produces = "application/json;charset=UTF-8")
	public ResultInfo updateOrderDeliveryStatus(@RequestBody(required = false)Saleorder saleorder) {
		ResultInfo resultInfo = new ResultInfo<>();
		logger.info("开始更新:"+System.currentTimeMillis());
		if(null!=saleorder) {
			Integer i = saleorderService.updateOrderDeliveryStatus(saleorder);
			if(i>0) {
				resultInfo.setCode(0);
				resultInfo.setMessage("更改了"+i+"条订单");
			}
		}
		logger.info("结束更新:"+System.currentTimeMillis());
		return resultInfo;
	}

	/**
	 * @Description: 查询活动出库量
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2019/12/10
	 */
	@RequestMapping("/queryOutBoundQuantity")
	@ResponseBody
	public ResultInfo queryOutBoundQuantity(@RequestBody SaleorderVo saleorderVo){
		ResultInfo resultInfo = new ResultInfo<>();
		saleorderVo.setIsActionGoods(CommonConstants.IS_ACTIVIT_GOODS);
		Integer deliveryNum=saleorderService.queryOutBoundQuantity(saleorderVo);
		if(deliveryNum!=0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(deliveryNum);
			return resultInfo;
		}else{
			resultInfo.setCode(-1);
			resultInfo.setMessage("没有数据");
			return resultInfo;
		}
	}


    /**
     * <b>Description:</b><br>
     * 获取订单的基本信息（用于验证）
     *
     * @return
     * @Note <b>Author:</b> Hugo <br>
     *       <b>Date:</b> 2020年3月16日 上午11:33:06
     */
    @ResponseBody
    @RequestMapping(value = "/getBaseSaleorder",method = RequestMethod.GET)
    public ResultInfo<?> getBaseSaleorder(HttpServletRequest request, Integer saleorderId) {
        ResultInfo<Object> resultInfo = new ResultInfo<>();
        Saleorder saleorderTmp = new Saleorder();
        saleorderTmp.setSaleorderId(saleorderId);
        Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorderTmp);
        if (saleorderInfo != null){
            resultInfo.setCode(0);
            resultInfo.setMessage("查询成功");
            resultInfo.setData(saleorderInfo);
        } else {
            resultInfo.setCode(-1);
            resultInfo.setMessage("请检查订单号");
        }
        return  resultInfo;
    }
}




