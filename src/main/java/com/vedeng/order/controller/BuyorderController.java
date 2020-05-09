package com.vedeng.order.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.common.util.*;
import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.order.model.*;
import com.vedeng.trader.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.service.AfterSalesService;
import com.vedeng.authorization.dao.UserDetailMapper;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.UserDetail;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.putHCutil.service.HcSaleorderService;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.PayApplyDetail;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.goods.model.GoodsSafeStock;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.model.vo.WarehouseGoodsOperateLogVo;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.logistics.service.WarehouseInService;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderModifyApplyGoodsVo;
import com.vedeng.order.model.vo.BuyorderModifyApplyVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.service.BuyorderService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.AddressService;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.TagService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.model.vo.TraderVo;
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
@RequestMapping("/order/buyorder")
public class BuyorderController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(BuyorderController.class);

	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
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
	@Qualifier("expressService")
	private ExpressService expressService;
	@Autowired
	@Qualifier("warehouseInService")
	private WarehouseInService warehouseInService;
	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;
	@Autowired
	@Qualifier("payApplyService")
	private PayApplyService payApplyService;

	@Resource
	private AfterSalesService afterSalesOrderService;
	@Resource
	private FtpUtilService ftpUtilService;

	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;

	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;

	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;

	@Autowired
	@Qualifier("userDetailMapper")
	private UserDetailMapper userDetailMapper;

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;

	@Autowired
	@Qualifier("addressService")
	private AddressService addressService;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("hcSaleorderService")
	protected HcSaleorderService hcSaleorderService;



	@Value("${gongyinglian_org_id}")
	protected Integer gongyinglian_org_id;

	@Autowired
	private VgoodsService vGoodsService;

	/**
	 * <b>Description:</b><br>
	 * 查询待采购订单列表不分页
	 * 
	 * @param goodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月20日 下午4:48:42
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "indexPendingPurchase")
	public ModelAndView indexPendingPurchase(GoodsVo goodsVo, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		Page page = getPageTag(request, pageNo, pageSize);
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("order/buyorder/index_pendingPurchase");
		// 产品部门--选择条件
		/*List<Organization> productOrgList = orgService.getOrgListByPositType(SysOptionConstant.ID_311,user.getCompanyId());
		mav.addObject("productOrgList", productOrgList);*/
		List<Organization> productOrgList = orgService.getOrgListByPositTypes(user.getCompanyId());
		mav.addObject("productOrgList", productOrgList);

		// 产品负责人
		List<User> productUserList = userService.getUserListByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		//所有的分配人
		List<User> assUser = userService.selectAllAssignUser();
		// 查询所有职位类型为311的员工
		// List<Integer> positionType = new ArrayList<>();
		// positionType.add(SysOptionConstant.ID_311);// 采购
		// List<User> productUserList = userService.getMyUserList(user,
		// positionType, false);
		/*mav.addObject("productUserList", productUserList);*/
		if (ObjectUtils.notEmpty(goodsVo.getProOrgtId())){
			assUser=userService.getUserListByOrgIddcg(goodsVo.getProOrgtId());
		}
		mav.addObject("productUserList", assUser);

		// 销售类型
		List<User> salesUserList = userService.getUserListByPositType(SysOptionConstant.ID_310, user.getCompanyId());
		// 申请人（包括产品和销售）
		List<User> applicantList = new ArrayList<>();
		applicantList.addAll(productUserList);
		applicantList.addAll(salesUserList);
		mav.addObject("applicantList", applicantList);

		goodsVo.setCompanyId(user.getCompanyId());
		goodsVo.setSearchUserId(user.getUserId());
		/*if (goodsVo != null && ObjectUtils.notEmpty(goodsVo.getProOrgtId())
				&& ObjectUtils.isEmpty(goodsVo.getProUserId())) {
			List<User> userList = userService.getUserListByOrgId(goodsVo.getProOrgtId());
			List<Integer> categoryIdList = userService.getCategoryIdListByUserList(userList);
			if (categoryIdList == null || categoryIdList.size() == 0) {
				categoryIdList = new ArrayList<>();
				categoryIdList.add(-1);
			}
			goodsVo.setCategoryIdList(categoryIdList);
			// productUserList =
			// userService.getUserListByOrgId(goodsVo.getProOrgtId());
		} else if (goodsVo != null && ObjectUtils.notEmpty(goodsVo.getProUserId())
				&& ObjectUtils.notEmpty(goodsVo.getProOrgtId())) {
			List<Integer> categoryIdList = userService.getCategoryIdListByUserId(goodsVo.getProUserId());
			if (categoryIdList == null || categoryIdList.size() == 0) {
				categoryIdList = new ArrayList<>();
				categoryIdList.add(-1);
			}
			goodsVo.setCategoryIdList(categoryIdList);
			// productUserList =
			// userService.getUserListByOrgId(goodsVo.getProOrgtId());
		} else if (goodsVo != null && ObjectUtils.notEmpty(goodsVo.getProUserId())
				&& ObjectUtils.isEmpty(goodsVo.getProOrgtId())) {
			List<Integer> categoryIdList = userService.getCategoryIdListByUserId(goodsVo.getProUserId());
			if (categoryIdList == null || categoryIdList.size() == 0) {
				categoryIdList = new ArrayList<>();
				categoryIdList.add(-1);
			}
			goodsVo.setCategoryIdList(categoryIdList);
		} else {
			// List<User> userList = userService.getMyUserList(user,
			// positionType,
			// haveDisabeldUser);
			// List<Integer> categoryIdList =
			// userService.getCategoryIdListByUserList(productUserList);
			// if (categoryIdList == null || categoryIdList.size() == 0) {
			// categoryIdList = new ArrayList<>();
			// categoryIdList.add(-1);
			// }
			goodsVo.setCategoryIdList(null);
		}*/
		//2019-12-20
		//判断归属和部门
		if (goodsVo != null && ObjectUtils.notEmpty(goodsVo.getProUserId())
				&& ObjectUtils.isEmpty(goodsVo.getProOrgtId())){
			List<Integer> saleOrderGoodsIdListByUserId=saleorderService.getSaleOrderGoodsIdListByUserId(goodsVo.getProUserId());
			if (saleOrderGoodsIdListByUserId == null || saleOrderGoodsIdListByUserId.size() == 0) {
				saleOrderGoodsIdListByUserId = new ArrayList<>();
				saleOrderGoodsIdListByUserId.add(-1);
			}
			goodsVo.setSaleOrderGoodsIdList(saleOrderGoodsIdListByUserId);

		}else if (goodsVo != null && ObjectUtils.notEmpty(goodsVo.getProOrgtId())
				&& ObjectUtils.isEmpty(goodsVo.getProUserId())){
			List<User> userList = userService.getUserListByOrgId(goodsVo.getProOrgtId());
			List<Integer> saleOrderGoodsIdListByUserIds=saleorderService.getSaleOrderGoodsIdListByUserIds(userList);
				if (saleOrderGoodsIdListByUserIds == null || saleOrderGoodsIdListByUserIds.size() == 0) {
					saleOrderGoodsIdListByUserIds = new ArrayList<>();
					saleOrderGoodsIdListByUserIds.add(-1);
				}
			goodsVo.setSaleOrderGoodsIdList(saleOrderGoodsIdListByUserIds);

		}else if (goodsVo != null && ObjectUtils.notEmpty(goodsVo.getProUserId())
				&& ObjectUtils.notEmpty(goodsVo.getProOrgtId())){
			List<Integer> saleOrderGoodsIdListByUserId=saleorderService.getSaleOrderGoodsIdListByUserId(goodsVo.getProUserId());
			List<User> userList = userService.getUserListByOrgId(goodsVo.getProOrgtId());
			//判断部门有没有这个归属人,没有的话查不到
			/*boolean b=false;
			if (userList!=null && userList.size()!=0){
				for (User u:userList) {
					if (u.getUserId().equals(goodsVo.getProUserId())){
						b=true;
						break;
					}
				}
			}*/
				//if (b) {
					if (saleOrderGoodsIdListByUserId == null || saleOrderGoodsIdListByUserId.size() == 0) {
						saleOrderGoodsIdListByUserId = new ArrayList<>();
						saleOrderGoodsIdListByUserId.add(-1);
					}
				/*}else {
					saleOrderGoodsIdListByUserId = new ArrayList<>();
					saleOrderGoodsIdListByUserId.add(-1);
				}*/
			goodsVo.setSaleOrderGoodsIdList(saleOrderGoodsIdListByUserId);

		}



		if (ObjectUtils.isEmpty(goodsVo.getOrderType())) {
			goodsVo.setOrderType(1);
		}
		Map<String, Object> map = buyorderService.getSaleorderGoodsVoListPage(goodsVo, page);

		if (map != null) {
			List<SaleorderVo> list = (List<SaleorderVo>) map.get("list");
			//根据订单号查询归属人
			for (SaleorderVo saleorderVo:list) {
				List<SaleorderGoodsVo> sgvList=saleorderVo.getSgvList();
				if (sgvList!=null){
					Spu spu=null;
					for (SaleorderGoodsVo sgv:sgvList) {
						spu=saleorderService.getSpu(sgv.getSku());
						if (spu==null){
							sgv.setAssignmentManagerId(null);
							sgv.setAssignmentAssistantId(null);
						}else {
							sgv.setAssignmentManagerId(spu.getAssignmentManagerId());
							sgv.setAssignmentAssistantId(spu.getAssignmentAssistantId());
						}
					}
				}
			}

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			if(!CollectionUtils.isEmpty(list)){

				List<Integer> skuIds = new ArrayList<>();

				list.stream().forEach(saleOrder -> {
					if(!CollectionUtils.isEmpty(saleOrder.getSgvList())){
						saleOrder.getSgvList().stream().forEach(saleOrderGoods->{
							skuIds.add(saleOrderGoods.getGoodsId());
						});
					}
				});

				List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);

				Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream()
						.collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));

				mav.addObject("newSkuInfosMap", newSkuInfosMap);
			}
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


			mav.addObject("list", list);
			mav.addObject("buySum", (Integer) map.get("buySum"));
			mav.addObject("page", (Page) map.get("page"));
		} else {
			mav.addObject("buySum", 0);
		}
		mav.addObject("goodsVo", goodsVo);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 
	 * @param request
	 * @param orgId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年12月9日 下午1:09:39
	 */
	@ResponseBody
	@RequestMapping(value = "getProductorUserList")
	public ResultInfo<?> getProductorUserList(HttpServletRequest request, Integer orgId) {
		if (ObjectUtils.isEmpty(orgId)) {
			return new ResultInfo<>(-1, "查询失败！");
		}
		List<User> userList = userService.getUserListByOrgIdcg(orgId);
		return new ResultInfo<>(0, "查询成功！", userList);
	}


	@ResponseBody
	@RequestMapping(value = "getProductorUserListCount")
	public ResultInfo<?> getProductorUserListCount(HttpServletRequest request) {
		/*if (ObjectUtils.isEmpty(orgId)) {
			return new ResultInfo<>(-1, "查询失败！");
		}*/
		List<User> userList = userService.selectAllAssignUser();
		return new ResultInfo<>(0, "查询成功！", userList);
	}

	/**
	 * <b>Description:</b><br>
	 * 忽略待采购订单
	 * 
	 * @param saleorderIDs
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月21日 下午5:09:26
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "saveIgnore")
	@SystemControllerLog(type = 1, operationType = "eidt", desc = "忽略待采购订单")
	public ResultInfo saveIgnore(String saleorderGoodsIDs, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo res = buyorderService.saveIgnore(saleorderGoodsIDs, user);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增采购订单
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月25日 下午5:24:24
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAddBuyorder")
	@SystemControllerLog(operationType = "add", desc = "保存新增采购订单")
	public ModelAndView saveAddBuyorder(HttpServletRequest request, BuyorderVo buyorderVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> res = buyorderService.saveOrUpdateBuyorderVo(buyorderVo, user);
		if (res != null && res.getCode() == 0 && Integer.valueOf(res.getData().toString()) > 0) {
			ModelAndView mav = new ModelAndView("redirect:/order/buyorder/addBuyorderPage.do");
			mav.addObject("buyorderId", Integer.valueOf(res.getData().toString()));
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("common/fail");
			if (res != null && res.getCode() == -2) {
				mav.addObject("message", res.getMessage());
			}
			return mav;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 新增采购订单 --普发或直发页面
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月25日 下午5:24:24
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/addBuyorderPage")
	public ModelAndView addBuyorderPage(HttpServletRequest request, Buyorder buyorder, String uri) throws IOException {
		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getAddBuyorderVoDetail(buyorder, user);
		mav.addObject("buyorderVo", bv);
		if (bv != null && ObjectUtils.notEmpty(bv.getTraderId())) {
			TraderContactVo traderContactVo = new TraderContactVo();
			traderContactVo.setTraderId(bv.getTraderId());
			traderContactVo.setTraderType(ErpConst.TWO);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
			List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");
			mav.addObject("contactList", list);
			mav.addObject("tarderAddressList", taList);
		}
		// 普发收货地址
		if (bv != null && bv.getDeliveryDirect() == 0) {
			ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
			paramsConfigValue.setCompanyId(user.getCompanyId());
			paramsConfigValue.setParamsConfigId(ErpConst.TWO);

			// 2018-2-4 查询全部收货地址
			mav.addObject("addressList", buyorderService.getAddressVoList(paramsConfigValue));
		}
		// 付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);

		// 收票种类
		List<SysOptionDefinition> receiptTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("receiptTypes", receiptTypes);

		// 运费说明
		List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(SysOptionConstant.ID_469);
		mav.addObject("freightDescriptions", freightDescriptions);

		// 物流公司
		List<Logistics> logisticsList = getLogisticsList(user.getCompanyId());
		mav.addObject("logisticsList", logisticsList);
		if (uri == null || "".equals(uri)) {
			uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/order/buyorder/addBuyorderPage.do";
		}
		mav.addObject("uri", uri);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(bv.getBuyorderGoodsVoList())){
			List<Integer> skuIds = new ArrayList<>();
			bv.getBuyorderGoodsVoList().stream().forEach(buyOrder -> {
				skuIds.add(buyOrder.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mav.setViewName("order/buyorder/add_buyorder_pf");
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bv)));
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑采购订单 --普发或直发页面
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月25日 下午5:24:24
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/editBuyorderPage")
	public ModelAndView editBuyorderPage(HttpServletRequest request, Buyorder buyorder, String uri) throws IOException {
		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder, user);
		mav.addObject("buyorderVo", bv);
		if (bv.getTraderId() != null && bv.getTraderId() != 0) {
			TraderContactVo traderContactVo = new TraderContactVo();
			traderContactVo.setTraderId(bv.getTraderId());
			traderContactVo.setTraderType(ErpConst.TWO);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
			List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");
			mav.addObject("contactList", list);
			mav.addObject("tarderAddressList", taList);
		}
		// 普发收货地址
		if (bv.getDeliveryDirect() == 0) {
			ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
			paramsConfigValue.setCompanyId(user.getCompanyId());
			paramsConfigValue.setParamsConfigId(ErpConst.TWO);

			// 2018-2-4 查询全部收货地址
			mav.addObject("addressList", buyorderService.getAddressVoList(paramsConfigValue));
		}
		// 付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);

		// 收票种类
		List<SysOptionDefinition> receiptTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("receiptTypes", receiptTypes);

		// 运费说明
		List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(SysOptionConstant.ID_469);
		mav.addObject("freightDescriptions", freightDescriptions);

		// 物流公司
		List<Logistics> logisticsList = getLogisticsList(user.getCompanyId());
		mav.addObject("logisticsList", logisticsList);
		if (uri == null || "".equals(uri)) {
			uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/order/buyorder/addBuyorderPage.do";
		}
		mav.addObject("uri", uri);
		mav.setViewName("order/buyorder/add_buyorder_pf");
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bv)));
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 详情页修改采购订单
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月25日 下午5:24:24
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/editAddBuyorderPage")
	public ModelAndView editAddBuyorderPage(HttpServletRequest request, Buyorder buyorder, String uri)
			throws IOException {
		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder, user);
		mav.addObject("buyorderVo", bv);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(bv.getBuyorderGoodsVoList())){
			List<Integer> skuIds = new ArrayList<>();
			bv.getBuyorderGoodsVoList().stream().forEach(buyOrderGood -> {
				skuIds.add(buyOrderGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		if (bv.getTraderId() != null && bv.getTraderId() != 0) {
			TraderContactVo traderContactVo = new TraderContactVo();
			traderContactVo.setTraderId(bv.getTraderId());
			traderContactVo.setTraderType(ErpConst.TWO);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
			List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");
			mav.addObject("contactList", list);
			mav.addObject("tarderAddressList", taList);
		}
		// 普发收货地址
		if (bv.getDeliveryDirect() == 0) {
			ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
			paramsConfigValue.setCompanyId(user.getCompanyId());
			paramsConfigValue.setParamsConfigId(ErpConst.TWO);

			// 2018-2-4 查询全部收货地址
			mav.addObject("addressList", buyorderService.getAddressVoList(paramsConfigValue));
		}
		// 付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);

		// 收票种类
		List<SysOptionDefinition> receiptTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("receiptTypes", receiptTypes);

		// 运费说明
		List<SysOptionDefinition> freightDescriptions = getSysOptionDefinitionList(SysOptionConstant.ID_469);
		mav.addObject("freightDescriptions", freightDescriptions);

		// 物流公司
		List<Logistics> logisticsList = getLogisticsList(user.getCompanyId());
		mav.addObject("logisticsList", logisticsList);
		if (uri == null || "".equals(uri)) {
			uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/order/buyorder/addBuyorderPage.do";
		}
		mav.addObject("uri", uri);
		mav.setViewName("order/buyorder/edit_buyorder_pf");
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bv)));
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增采购页面提交
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月3日 上午10:26:31
	 */
	@ResponseBody
	@RequestMapping(value = "saveEditBuyorder")
	@SystemControllerLog(operationType = "edit", desc = "新增采购页面提交")
	public ModelAndView saveEditBuyorder(HttpServletRequest request, Buyorder buyorder, String beforeParams) {
		ResultInfo<?> res = buyorderService.saveEditBuyorderAndBuyorderGoods(buyorder, request);
		if (res != null && res.getCode() == 0) {
			ModelAndView mav = new ModelAndView("redirect:/order/buyorder/viewBuyorder.do");
			mav.addObject("buyorderId", buyorder.getBuyorderId());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("common/fail");
			mav.addObject("message", res==null?"":res.getMessage());
			return mav;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 搜索供应商信息页面
	 * 
	 * @param request
	 * @param traderSupplierVo
	 * @return
	 * @throws UnsupportedEncodingException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月25日 下午5:10:20
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getSupplierByName")
	public ModelAndView getSupplierByName(HttpServletRequest request, TraderSupplierVo traderSupplierVo,
			String supplierName, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,Integer lendOut,String searchTraderName) throws UnsupportedEncodingException {
		ModelAndView mav = null;
		if(lendOut==null) {
			mav = new ModelAndView("order/buyorder/search_supplier");
			supplierName = URLDecoder.decode(URLDecoder.decode(supplierName, "UTF-8"), "UTF-8");
			mav.addObject("supplierName", supplierName);
		}else {
			//外借出库搜索
			supplierName = URLDecoder.decode(URLDecoder.decode(searchTraderName, "UTF-8"), "UTF-8");
			mav = new ModelAndView("trader/customer/search_customer_list");
		}
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request, pageNo, 10);
		traderSupplierVo.setCompanyId(user.getCompanyId());
		traderSupplierVo.setTraderSupplierName(supplierName);
		traderSupplierVo.setIsEnable(ErpConst.ONE);
		traderSupplierVo.setRequestType("cg");// 采购搜索供应商用，其他地方可以不用
		// 查询所有职位类型为311的员工
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_311);// 采购
		List<User> userList = userService.getMyUserList(user, positionType, false);
		Map<String, Object> map = this.traderSupplierService.getSupplierByName(traderSupplierVo, page, userList);
		List<TraderSupplierVo> list = null;
		if (map != null) {
			list = (List<TraderSupplierVo>) map.get("list");
			page = (Page) map.get("page");
			if(lendOut!=null && lendOut==1) {
				mav.addObject("lendOut", 1);
				List<TraderVo> traderList = new ArrayList<TraderVo>();
				for (TraderSupplierVo t : list) {
					TraderVo trader = new TraderVo();
					trader.setTraderId(t.getTraderId());
					trader.setTraderName(t.getTraderSupplierName());
					trader.setOwner(t.getPersonal());
					trader.setAddress(t.getTraderSupplierAddress());
					trader.setAddTime(t.getAddTime());
					trader.setTraderType(2);
					traderList.add(trader);
				}
				mav.addObject("traderList",traderList);
				TraderCustomerVo traderCustomerVo = new TraderCustomerVo();
				traderCustomerVo.setSearchTraderName(supplierName);
				mav.addObject("traderCustomerVo", traderCustomerVo);
				mav.addObject("block", 2);//外借单选项
			}
		}
		mav.addObject("list", list);
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 获取当前供应商的联系人和地址
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月23日 上午9:08:41
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@ResponseBody
	@RequestMapping(value = "/getContactsAddress")
	public ResultInfo getContactsAddress(HttpServletRequest request, Integer traderId, Integer traderType) {
		TraderContactVo traderContactVo = new TraderContactVo();
		traderContactVo.setTraderId(traderId);
		traderContactVo.setTraderType(traderType);
		Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
		String tastr = (String) map.get("contact");
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
		List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
		List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");
		ResultInfo res = new ResultInfo<>(0, "查询成功");
		res.setData(list);
		res.setListData(taList);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 加入采购订单页面
	 * 
	 * @param goodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月8日 上午10:39:12
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "addSaleorderToBuyorderPage")
	public ModelAndView addSaleorderToBuyorderPage(BuyorderVo buyorderVo, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("order/buyorder/add_saleorderToBuyorder");
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorderVo.setCompanyId(user.getCompanyId());
		Map<String, Object> map = buyorderService.getGoodsVoList(buyorderVo);
		if (map.containsKey("-2")) {
			mav = new ModelAndView("common/fail");
			mav.addObject("message", "提交订单中含有已锁定销售商品，请确认");
		}
		if (map.containsKey("gvList")) {
			List<GoodsVo> gvList = (List<GoodsVo>) map.get("gvList");
			mav.addObject("gvList", gvList);


			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			if(!CollectionUtils.isEmpty(gvList)){
				List<Integer> skuIds = new ArrayList<>();
				gvList.stream().forEach(saleGood -> {
					skuIds.add(saleGood.getGoodsId());
				});
				List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
				Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
				mav.addObject("newSkuInfosMap", newSkuInfosMap);
			}
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		}
		if (map.containsKey("sum")) {
			Integer sum = (Integer) map.get("sum");
			mav.addObject("sum", sum);
		}
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
	public ModelAndView indexBuy(HttpServletRequest request, BuyorderVo buyorderVo, TraderSupplier traderSupplier,
								 @RequestParam(required = false, defaultValue = "1") Integer pageNo,
								 @RequestParam(required = false) Integer pageSize) {
		ModelAndView mav = new ModelAndView("order/buyorder/index");
		Page page = getPageTag(request, pageNo, pageSize);
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);

		mav.addObject("curr_user",user);

		buyorderVo.setCompanyId(user.getCompanyId());

		// 产品部门--选择条件
		List<Organization> productOrgList = orgService.getOrgListByPositType(SysOptionConstant.ID_311,
				user.getCompanyId());
		mav.addObject("productOrgList", productOrgList);

		//所有的分配人
		List<User> assUser = userService.selectAllAssignUser();
		List<User> userList = userService.getUserByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		mav.addObject("productUserList", assUser);

		List<Integer> userIds;
		userIds = getUserIdList(buyorderVo.getProOrgtId(), request);

		if (buyorderVo.getProUserId() != null && buyorderVo.getProUserId() != 0) {
			userIds=null;
		}

		if (buyorderVo.getProOrgtId() != null && buyorderVo.getProOrgtId() != 0) {
			buyorderVo.setOrgId(buyorderVo.getProOrgtId());
		}
		if (userIds!=null) {
			if (userIds.size() > 0) {
				buyorderVo.setUserIds(userIds);
			}
		}

		String start = request.getParameter("searchBegintimeStr");
		String end = request.getParameter("searchEndtimeStr");
		if (start != null && !"".equals(start)) {
			buyorderVo.setSearchBegintime(DateUtil.convertLong(start, DateUtil.DATE_FORMAT));
		}
		if (end != null && !"".equals(end)) {
			buyorderVo.setSearchEndtime(DateUtil.convertLong(end + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		//6 代表供应链管理部
		List<Integer> orgList=orgService.getOrgIdsByParentId(gongyinglian_org_id,1);

		List<User> userlist=userService.getUserListByOrgIds(orgList,1);

		mav.addObject("addUserlist",userlist);

		int addUserId=NumberUtils.toInt(request.getParameter("addUserId"));
		int currentUserId=0;
		if(CollectionUtils.isNotEmpty(userlist)){
			for(User cuser:userlist){
				if(cuser.getUserId().equals(user.getUserId())){
					currentUserId=cuser.getUserId();
					break;
				}
			}
		}
		//如果登录用户属于供应链管理部，则默认展示自己名下的
		if(buyorderVo.getCreator()==null&&currentUserId>0){
			buyorderVo.setCreator(currentUserId);
		}



		// 客户信息里面的交易记录
		if (null != buyorderVo.getTraderId() && buyorderVo.getTraderId() > 0) {
			mav.addObject("method", "buyorder");
			mav.addObject("traderId", buyorderVo.getTraderId());
		}

		if (StringUtils.isNotBlank(buyorderVo.getCurrentOperateUser())){
			List<Integer> buyOrderIds  = getBuyOrderIdsByCurrentOperateUser(page, buyorderVo.getCurrentOperateUser());
			if (buyOrderIds.size() == 0){
				mav.addObject("list",null);
				mav.addObject("page",page);
				mav.addObject("buyorderVo", buyorderVo);
				return mav;
			} else {
				buyorderVo.setBuyorderIdList(buyOrderIds);
			}
		}

		Map<String, Object> map = buyorderService.getBuyorderVoPage(buyorderVo, page);
		mav.addObject("list", map.get("list"));
		mav.addObject("page", map.get("page"));
		mav.addObject("buyorderVo", buyorderVo);
		return mav;
	}


//	@ResponseBody
//	@RequestMapping(value = "getBuyorderList")
//	public ModelAndView indexBuy(HttpServletRequest request, BuyorderVo buyorderVo, TraderSupplier traderSupplier,
//								 @RequestParam(required = false, defaultValue = "1") Integer pageNo,
//								 @RequestParam(required = false) Integer pageSize) {
//		ModelAndView mav = new ModelAndView("order/buyorder/index");
//		Page page = getPageTag(request, pageNo, pageSize);
//		String currentOperateUser = buyorderVo.getCurrentOperateUser();
//		if(StringUtils.isNotBlank(currentOperateUser)){
//			List<Integer> buyOrderIds  = getBuyOrderIdsByCurrentOperateUser(page, currentOperateUser);
//			if(CollectionUtils.isEmpty(buyOrderIds)){
//				mav.addObject("list", null);
//				mav.addObject("buyorderVo", buyorderVo);
//				mav.addObject("page", page);
//				return mav;
//			}else{
//				buyorderVo.setBuyorderIdList(buyOrderIds);
//			}
//		}else{
//			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
//			buyorderVo.setCompanyId(user.getCompanyId());
//			//待操作人员默认为当前账号
//			buyorderVo.setCurrentOperateUser(user.getUsername());
//			// 产品部门--选择条件
//			List<Organization> productOrgList = orgService.getOrgListByPositType(SysOptionConstant.ID_311,
//					user.getCompanyId());
//			mav.addObject("productOrgList", productOrgList);
//			// 产品负责人
//			// List<User> productUserList =
//			// userService.getUserListByPositType(SysOptionConstant.ID_311,user.getCompanyId());
//			// mav.addObject("productUserList", productUserList);
//
//			// 通过父部门的id查询所有子部门的id集合
//
//			// 查询所有职位类型为311的员工
//			// List<Integer> positionType = new ArrayList<>();
//			// positionType.add(SysOptionConstant.ID_311);// 采购
//			// List<User> userList = userService.getMyUserList(user, positionType,
//			// false);
//			//所有的分配人
//			List<User> assUser = userService.selectAllAssignUser();
//			List<User> userList = userService.getUserByPositType(SysOptionConstant.ID_311, user.getCompanyId());
//			mav.addObject("productUserList", assUser);
//
//			List<Integer> userIds = new ArrayList<>();
//			userIds = getUserIdList(buyorderVo.getProOrgtId(), request);
//		/*if (buyorderVo.getProUserId() != null && buyorderVo.getProUserId() != 0) {
//			List<Integer> userid=userService.getUserId(buyorderVo.getProUserId());//根据归属人查找用户id
//			if (userid!=null && userid.size()>0) {
//				userIds.add(buyorderVo.getProUserId());
//			}else {
//				userIds.add(-1);//如果归属没有userId，直接查询不到返回界面
//			}
//		}*/
//			if (buyorderVo.getProUserId() != null && buyorderVo.getProUserId() != 0) {
//			/*userIds.add(buyorderVo.getProUserId());
//			for (Organization organization:
//				 productOrgList) {
//				List<Integer> uids=getUserIdList(organization.getOrgId(), request);
//				for (Integer u:uids) {
//					userIds.add(u);
//				}
//			}*/
//				userIds=null;
//			}
//
//			if (buyorderVo.getProOrgtId() != null && buyorderVo.getProOrgtId() != 0) {
//				buyorderVo.setOrgId(buyorderVo.getProOrgtId());
//			}
//			if (userIds!=null) {
//				if (userIds.size() > 0) {
//					buyorderVo.setUserIds(userIds);
//				}
//			}
//
//
//			String start = request.getParameter("searchBegintimeStr");
//			String end = request.getParameter("searchEndtimeStr");
//			if (start != null && !"".equals(start)) {
//				buyorderVo.setSearchBegintime(DateUtil.convertLong(start, DateUtil.DATE_FORMAT));
//			}
//			if (end != null && !"".equals(end)) {
//				buyorderVo.setSearchEndtime(DateUtil.convertLong(end + " 23:59:59", DateUtil.TIME_FORMAT));
//			}
//		}
//
//		Map<String, Object> map = buyorderService.getBuyorderVoPage(buyorderVo, page);
//
//		// 客户信息里面的交易记录
//		if (null != buyorderVo.getTraderId() && buyorderVo.getTraderId() > 0) {
//			mav.addObject("method", "buyorder");
//			mav.addObject("traderId", buyorderVo.getTraderId());
//		}
//		mav.addObject("list", map.get("list"));
//		mav.addObject("buyorderVo", buyorderVo);
//		mav.addObject("page", map.get("page"));
//		return mav;
//	}

	private List<Integer> getBuyOrderIdsByCurrentOperateUser(Page page, String currentOperateUser) {
		return  buyorderService.getBuyOrderIdsByCurrentOperateUser(page, currentOperateUser);
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
	 * <b>Description:</b><br>
	 * 查看采购订单详情
	 * 
	 * @param buyorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月19日 上午10:03:21
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "viewBuyorder")
	public ModelAndView viewBuyorder(HttpServletRequest request, Buyorder buyorder, String uri) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getBuyorderVoDetailNew(buyorder, user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("order/buyorder/view_buyorder_pf");
		mav.addObject("buyorderVo", bv);


		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(bv.getBuyorderGoodsVoList())){
			List<Integer> skuIds = new ArrayList<>();
			bv.getBuyorderGoodsVoList().stream().forEach(buyOrderGood -> {
				skuIds.add(buyOrderGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		if (uri == null || "".equals(uri)) {
			uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/order/buyorder/viewBuyorder.do";
		}
		mav.addObject("uri", uri);
		CommunicateRecord communicateRecord = new CommunicateRecord();
		communicateRecord.setBuyorderId(bv.getBuyorderId());
		communicateRecord.setTraderType(2);
		List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
		mav.addObject("communicateList", crList);
		// 付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bv)));

		// 交易方式
		List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(519);
		mav.addObject("traderModeList", traderModeList);
		// 获取付款申请列表
		PayApply payApply = new PayApply();
		payApply.setCompanyId(user.getCompanyId());
		payApply.setPayType(517);// 采购付款申请
		payApply.setRelatedId(bv.getBuyorderId());
		List<PayApply> payApplyList = payApplyService.getPayApplyList(payApply);
		mav.addObject("payApplyList", payApplyList);

		// 判断是否有正在审核中的付款申请
		Integer isPayApplySh = 0;
		for (int i = 0; i < payApplyList.size(); i++) {
			if (payApplyList.get(i).getValidStatus() == 0) {
				isPayApplySh = 1;
				break;
			}
		}
		mav.addObject("isPayApplySh", isPayApplySh);
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"buyorderVerify_" + buyorder.getBuyorderId());
		mav.addObject("taskInfo", historicInfo.get("taskInfo"));
		mav.addObject("startUser", historicInfo.get("startUser"));
		mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mav.addObject("endStatus", historicInfo.get("endStatus"));
		mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mav.addObject("commentMap", historicInfo.get("commentMap"));
		Task taskInfoPay = (Task) historicInfo.get("taskInfo");
		String verifyUsersPay = null;
		if (null != taskInfoPay) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfoPay);
			verifyUsersPay = (String) taskInfoVariables.get("verifyUsers");
		}
		mav.addObject("verifyUsers", verifyUsersPay);

		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 订单新增沟通记录
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午10:17:31
	 */
	@FormToken(save = true)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "addCommunicatePagePf")
	public ModelAndView addCommunicatePage(Buyorder buyorder, TraderSupplier traderSupplier, String flag,
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

		CommunicateRecord communicate = new CommunicateRecord();
		communicate.setBegintime(DateUtil.sysTimeMillis());
		communicate.setEndtime(DateUtil.sysTimeMillis() + 2 * 60 * 1000);
		mav.addObject("communicateRecord", communicate);

		// 沟通方式
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);

		mav.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mav.addObject("page", (Page) tagMap.get("page"));
		mav.addObject("flag", flag);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 申请付款页面
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月23日 下午4:17:12
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "applyPayment")
	public ModelAndView applyPayment(HttpServletRequest request, Buyorder buyorder) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setFlag("fk");
		try {
			BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder, user);

			for (int i = 0; i < bv.getBuyorderGoodsVoList().size(); i++) {
				// 获取付款申请该产品（已申请数量、已申请总额）
				Map<String, BigDecimal> passedMap = payApplyService
						.getPassedByBuyorderGoodsId(bv.getBuyorderGoodsVoList().get(i).getBuyorderGoodsId());
				bv.getBuyorderGoodsVoList().get(i).setApplyPaymentNum(passedMap.get("passedNum"));
				bv.getBuyorderGoodsVoList().get(i).setApplyPaymentAmount(passedMap.get("passedAmount"));
			}
			mv.addObject("buyorderVo", bv);

			// 获取银行帐号列表
			TraderFinance tf = new TraderFinance();
			tf.setTraderId(bv.getTraderId());
			tf.setTraderType(ErpConst.TWO);
			List<TraderFinance> traderFinance = traderCustomerService.getTraderCustomerFinanceList(tf);
			mv.addObject("traderFinance", traderFinance);

			// 获取对应供应商主信息
			TraderSupplier traderSupplier = new TraderSupplier();
			traderSupplier.setTraderId(bv.getTraderId());
			TraderSupplierVo supplierInfo = traderSupplierService.getSupplierInfoByTraderSupplier(traderSupplier);
			mv.addObject("supplierInfo", supplierInfo);

		} catch (Exception e) {
			logger.error("applyPayment:", e);
		}

		mv.setViewName("order/buyorder/apply_payment");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存申请付款
	 * 
	 * @param request
	 * @param payApply
	 * @param priceArr
	 * @param numArr
	 * @param totalAmountArr
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月30日 下午6:12:54
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "saveApplyPayment")
	@SystemControllerLog(operationType = "add", desc = "保存申请付款")
	public ResultInfo<?> saveApplyPayment(HttpServletRequest request, PayApply payApply,
			@RequestParam(required = false, value = "priceArr") String priceArr,
			@RequestParam(required = false, value = "numArr") String numArr,
			@RequestParam(required = false, value = "totalAmountArr") String totalAmountArr) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			payApply.setCreator(user.getUserId());
			payApply.setAddTime(DateUtil.sysTimeMillis());
			payApply.setUpdater(user.getUserId());
			payApply.setModTime(DateUtil.sysTimeMillis());
			payApply.setCompanyId(user.getCompanyId());
		}
		// 获取待审核付款列表
		PayApply payApplyPre = new PayApply();
		payApplyPre.setCompanyId(user.getCompanyId());
		payApplyPre.setPayType(517);// 采购付款申请
		payApplyPre.setRelatedId(payApply.getRelatedId());
		payApplyPre.setValidStatus(0);
		List<PayApply> payApplyList = payApplyService.getPayApplyList(payApplyPre);
		if (payApplyList.size() > 0) {
			return new ResultInfo(-1, "当前有待审核的付款申请，请不要重复申请");
		}

		payApply.setCurrencyUnitId(1);
		payApply.setPayType(517);// 采购
		List<Double> priceList = JSON.parseArray(request.getParameter("priceArr").toString(), Double.class);
		List<Double> numList = JSON.parseArray(request.getParameter("numArr").toString(), Double.class);
		List<Double> totalAmountList = JSON.parseArray(request.getParameter("totalAmountArr").toString(), Double.class);

		List<Integer> buyorderGoodsIdList = JSON.parseArray(request.getParameter("buyorderGoodsIdArr").toString(),
				Integer.class);

		List<PayApplyDetail> detailList = new ArrayList<>();
		for (int i = 0; i < buyorderGoodsIdList.size(); i++) {
			if (buyorderGoodsIdList.get(i) == null)
				continue;
			PayApplyDetail payApplyDetail = new PayApplyDetail();
			payApplyDetail.setDetailgoodsId(buyorderGoodsIdList.get(i));
			payApplyDetail.setPrice(new BigDecimal(priceList.get(i)));
			payApplyDetail.setNum(new BigDecimal(numList.get(i)));
			payApplyDetail.setTotalAmount(new BigDecimal(totalAmountList.get(i)));
			detailList.add(payApplyDetail);
		}
		payApply.setDetailList(detailList);
		ResultInfo<?> result = buyorderService.saveApplyPayment(payApply);
		if (result == null || result.getCode() == -1) {
			return result;
		}
		Buyorder buyorder = new Buyorder();
		buyorder.setBuyorderId(payApply.getRelatedId());
		BuyorderVo buyorderVo = buyorderService.getBuyorderVoDetail(buyorder);
		// 付款申请ID
		Integer payApplyId = (Integer) result.getData();
		PayApply payApplyInfo = payApplyService.getPayApplyInfo(payApplyId);
		payApplyInfo.getCreatorName();
		payApplyInfo.setOrderNo(buyorderVo.getBuyorderNo());
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			// 开始生成流程(如果没有taskId表示新流程需要生成)
			variableMap.put("payApply", payApplyInfo);
			variableMap.put("currentAssinee", user.getUsername());
			variableMap.put("processDefinitionKey", "paymentVerify");
			variableMap.put("businessKey", "paymentVerify_" + payApplyInfo.getPayApplyId());
			variableMap.put("relateTableKey", payApplyInfo.getPayApplyId());
			variableMap.put("relateTable", "T_PAY_APPLY");
			variableMap.put("orgId", user.getOrgId());
			// 流程条件标识
			variableMap.put("activitiType", "buyorderPaymentVerify");
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
				// 审核完成时解锁
				variables.put("buyorderId", buyorder.getBuyorderId());
				// 回写数据的表在db中
				variables.put("db", 2);
				// 默认审批通过
				ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
						user.getUsername(), variables);
				// 如果未结束添加审核对应主表的审核状态
				if (!complementStatus.getData().equals("endEvent")) {
					verifiesRecordService.saveVerifiesInfo(taskId, 0);
				}
				// 发起审核时锁定采购单
				actionProcdefService.updateInfo("T_BUYORDER", "BUYORDER_ID", buyorder.getBuyorderId(), "LOCKED_STATUS",
						1, 2);
			}
			return result;
		} catch (Exception e) {
			logger.error("saveApplyPayment:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

		// return result;
	}

	@ResponseBody
	@RequestMapping(value = "/saveApplyPayment1")
	public ModelAndView saveApplyPayment(HttpServletRequest request, HttpSession session, PayApply payApply,
			PayApplyDetail payApplyDetail) {
		ModelAndView mv = new ModelAndView();
		try {
			payApply.setModTime(1l);
			payApplyDetail.setPayApplyId(1);

			return success(mv);
			/*
			 * buyorder = buyorderService.saveApplyPayment(payApply); if(null !=
			 * buyorder){
			 * mv.addObject("url","./viewbaseinfo.do?goodsId="+buyorder.
			 * getGoodsId()); return success(mv); }else{ return fail(mv); }
			 */
		} catch (Exception e) {
			logger.error("saveApplyPayment1:", e);
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 编辑沟通记录
	 * 
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午1:31:13
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/editcommunicate")
	public ModelAndView editCommunicate(CommunicateRecord communicateRecord, TraderSupplier traderSupplier, String flag,
			Buyorder buyorder, HttpServletRequest request, HttpSession session) throws IOException {
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
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(communicate)));
		mv.addObject("flag", flag);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增沟通
	 * 
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:36:53
	 */
	@FormToken(remove = true)
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/saveaddcommunicate")
	@SystemControllerLog(operationType = "add", desc = "保存新增采购沟通记录")
	public ResultInfo saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, String flag,
			HttpSession session) throws Exception {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_247);// 采购订单
		communicateRecord.setRelatedId(communicateRecord.getBuyorderId());
		communicateRecord.setTraderType(2);
		record = traderSupplierService.saveAddCommunicate(communicateRecord, request, session);
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getBuyorderId() + "," + flag);
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
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:36:53
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/saveeditcommunicate")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑沟通记录")
	public ResultInfo saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			String beforeParams, String flag, HttpSession session) throws Exception {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_247);// 采购订单
		communicateRecord.setRelatedId(communicateRecord.getBuyorderId());
		record = traderSupplierService.saveEditCommunicate(communicateRecord, request, session);
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getBuyorderId() + "," + flag);
		} else {
			return new ResultInfo(1, "操作失败！");
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 获取已忽略列表
	 * 
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月1日 上午9:44:49
	 */
	@ResponseBody
	@RequestMapping(value = "getIgnoreSaleorderPage")
	public ModelAndView getIgnoreSaleorderPage(HttpServletRequest request, SaleorderGoodsVo saleorderGoodsVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("order/buyorder/list_ignore");
		Page page = getPageTag(request, pageNo, pageSize);
		// 产品负责人
		List<User> productUserList = userService.getUserListByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		mav.addObject("productUserList", productUserList);
		// 销售类型
		List<User> salesUserList = userService.getUserListByPositType(SysOptionConstant.ID_310, user.getCompanyId());
		// 申请人（包括产品和销售）
		List<User> applicantList = new ArrayList<>();
		applicantList.addAll(productUserList);
		applicantList.addAll(salesUserList);
		mav.addObject("applicantList", applicantList);
		saleorderGoodsVo.setCompanyId(user.getCompanyId());
		if (ObjectUtils.notEmpty(saleorderGoodsVo.getApplicantId())) {
			List<Integer> userIds = new ArrayList<>();
			userIds.add(saleorderGoodsVo.getApplicantId());
			saleorderGoodsVo.setUserIds(userIds);
		}
		Map<String, Object> map = buyorderService.getIgnoreSaleorderPage(saleorderGoodsVo, page);

		List<SaleorderGoodsVo> saleorderGoodsLists = (List<SaleorderGoodsVo>)map.get("list");

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(saleorderGoodsLists)){
			List<Integer> skuIds = new ArrayList<>();
			saleorderGoodsLists.stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


		mav.addObject("list", saleorderGoodsLists);
		page = (Page) map.get("page");
		mav.addObject("page", page);
		mav.addObject("saleorderGoodsVo", saleorderGoodsVo);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存申请审核
	 * 
	 * @param buyorder
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月7日 下午1:23:46
	 */
	@ResponseBody
	@RequestMapping(value = "saveApplyReview")
	@SystemControllerLog(operationType = "eidt", desc = "采购订单申请审核")
	public ModelAndView saveApplyReview(Buyorder buyorder, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setCompanyId(user.getCompanyId());
		buyorder.setUpdater(user.getUserId());
		buyorder.setModTime(DateUtil.sysTimeMillis());
		// 审核暂时为直接生效
		buyorder.setValidStatus(ErpConst.ONE);
		buyorder.setCompanyName(user.getCompanyName());
		buyorder.setValidTime(DateUtil.sysTimeMillis());
		buyorder.setStatus(ErpConst.ONE);
		ResultInfo<?> res = buyorderService.saveApplyReview(buyorder);
		if (res != null && res.getCode() == 0) {
			ModelAndView mav = new ModelAndView("redirect:/order/buyorder/viewBuyordersh.do");
			mav.addObject("buyorderId", buyorder.getBuyorderId());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("common/fail");
			return mav;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 详情页大数据量时异步加载补全产品信息
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年5月31日 下午6:32:51
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "getBuyGoodsListByAjax")
	public ResultInfo<?> getBuyGoodsListByAjax(HttpServletRequest request, BuyorderVo buyorder) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		List<BuyorderGoodsVo> list = buyorderService.getBuyorderGoodsVoListByAjax(buyorder, user);
		if (list != null) {
			return new ResultInfo(0, "查询成功！", list);
		}
		return new ResultInfo(1, "查询失败！");

	}

	/**
	 * <b>Description:</b><br>
	 * 获取出入库记录
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年5月31日 下午6:32:51
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "getWarehouseGoodsOperateLogVoListPage")
	public ModelAndView getWarehouseGoodsOperateLogVoListPage(HttpServletRequest request, BuyorderVo buyorder) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setCompanyId(user.getCompanyId());
		ModelAndView mav = new ModelAndView("order/buyorder/warehouseGoodsOperateLog_page");
		List<WarehouseGoodsOperateLogVo> list = buyorderService.getWarehouseGoodsOperateLogVoListPage(buyorder);
		mav.addObject("warehouseGoodsOperateLogVoList", list);
		return mav;

	}

	/**
	 * <b>Description:</b><br>
	 * 获取采购详情的产品信息
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年5月31日 下午6:32:51
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "getBuyorderGoodsVoListPage")
	public ModelAndView getBuyorderGoodsVoListPage(HttpServletRequest request, BuyorderVo buyorder) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setCompanyId(user.getCompanyId());
		ModelAndView mav = new ModelAndView("order/buyorder/buyorderGoods_page");
		BuyorderVo bv = buyorderService.getBuyorderGoodsVoListPage(buyorder);
		mav.addObject("buyorderVo", bv);
		return mav;

	}

	/**
	 * <b>Description:</b><br>
	 * 通过ajax后补数据
	 * 
	 * @param buyorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2017年7月19日 上午10:03:21
	 */
	@ResponseBody
	@RequestMapping(value = "getSaleBuyNumByAjax")
	public ResultInfo<?> getSaleBuyNumByAjax(HttpServletRequest request, Buyorder buyorder) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// ModelAndView mav = new ModelAndView();
		// Page page = getPageTag(request, pageNo,pageSize);
		// 获取采购订单详情
		BuyorderVo bv = buyorderService.getSaleBuyNumByAjax(buyorder, user);
		/*
		 * Map<String,Object> map=(Map<String, Object>) res.getData();
		 * JSONObject json = JSONObject.fromObject(map.get("buyorderVo"));
		 * BuyorderVo bv = JsonUtils.readValue(json.toString(),
		 * BuyorderVo.class);
		 */
		return new ResultInfo<Object>(0, "查询成功", bv);
	}

	/**
	 * <b>Description:</b><br>
	 * 查看采购订单申请审核后页面
	 * 
	 * @param buyorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月19日 上午10:03:21
	 */
	@ResponseBody
	@RequestMapping(value = "viewBuyordersh")
	public ModelAndView viewBuyorderSH(HttpServletRequest request, Buyorder buyorder, String uri) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// Page page = getPageTag(request, 1, 30);
		// 产品信息分页展示
		/*****************************************************************/
		// BuyorderVo bv=buyorderService.getBuyorderVoDetail(buyorder,
		// user,page);
		BuyorderVo bv = buyorderService.getBuyorderVoDetailNew(buyorder, user);

		/*
		 * Map<String,Object> map=(Map<String, Object>) res.getData();
		 * JSONObject json = JSONObject.fromObject(map.get("buyorderVo"));
		 * BuyorderVo bv = JsonUtils.readValue(json.toString(),
		 * BuyorderVo.class);
		 */
		/*****************************************************************/
		// BuyorderVo bv = buyorderService.getBuyorderVoDetailByAjax(buyorder,
		// user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("order/buyorder/view_buyorder_sx");

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(bv.getBuyorderGoodsVoList())){
			List<Integer> skuIds = new ArrayList<>();
			bv.getBuyorderGoodsVoList().stream().forEach(buyOrderGood -> {
				skuIds.add(buyOrderGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mav.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mav.addObject("curr_user", user);
		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mav.addObject("invoiceTypes", invoiceTypes);
		mav.addObject("buyorderVo", bv);
		if (uri == null || "".equals(uri)) {
			uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/order/buyorder/viewBuyordersh.do";
		}
		mav.addObject("uri", uri);
		CommunicateRecord communicateRecord = new CommunicateRecord();
		communicateRecord.setBuyorderId(bv.getBuyorderId());
		List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
		mav.addObject("communicateList", crList);
		// 付款方式
		List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
		mav.addObject("paymentTermList", paymentTermList);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bv)));

		// 交易方式
		List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(519);
		mav.addObject("traderModeList", traderModeList);

		// 售后订单列表
		AfterSalesVo as = new AfterSalesVo();
		as.setOrderId(buyorder.getBuyorderId());
		as.setSubjectType(536);
		List<AfterSalesVo> asList = afterSalesOrderService.getAfterSalesVoListByOrderId(as);
		if (asList != null && asList.size() > 0) {
			if (asList.get(0).getAtferSalesStatus() == 2 || asList.get(0).getAtferSalesStatus() == 3) {
				mav.addObject("addAfterSales", 1);
			} else {
				mav.addObject("addAfterSales", 0);
				mav.addObject("lockedReason", "售后锁定");
			}
		} else {
			mav.addObject("addAfterSales", 1);
		}
		mav.addObject("asList", asList);

		// 获取付款申请列表
		PayApply payApply = new PayApply();
		payApply.setCompanyId(user.getCompanyId());
		payApply.setPayType(517);// 采购付款申请
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
					mav.addObject("lockedReason", "付款申请锁定");
				}
				// payApplyId = payApplyList.get(i).getPayApplyId();
				break;
			}
		}
		if (!payApplyList.isEmpty() && payApplyId == 0) {
			payApplyId = payApplyList.get(0).getPayApplyId();
		}
		mav.addObject("isPayApplySh", isPayApplySh);
		mav.addObject("payApplyId", payApplyId);
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"buyorderVerify_" + buyorder.getBuyorderId());
		mav.addObject("taskInfo", historicInfo.get("taskInfo"));
		mav.addObject("startUser", historicInfo.get("startUser"));
		mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mav.addObject("endStatus", historicInfo.get("endStatus"));
		mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		mav.addObject("commentMap", historicInfo.get("commentMap"));
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		// 当前审核人
		String verifyUsers = null;
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
		}
		mav.addObject("verifyUsers", verifyUsers);

		Map<String, Object> historicInfoPay = actionProcdefService.getHistoric(processEngine,
				"paymentVerify_" + payApplyId);
		Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
		mav.addObject("taskInfoPay", taskInfoPay);
		mav.addObject("startUserPay", historicInfoPay.get("startUser"));
		// 最后审核状态
		mav.addObject("endStatusPay", historicInfoPay.get("endStatus"));
		mav.addObject("historicActivityInstancePay", historicInfoPay.get("historicActivityInstance"));
		mav.addObject("commentMapPay", historicInfoPay.get("commentMap"));
		mav.addObject("candidateUserMapPay", historicInfoPay.get("candidateUserMap"));
		// 当前审核人
		String verifyUsersPay = null;
		if (null != taskInfoPay) {
			Map<String, Object> taskInfoVariablesPay = actionProcdefService.getVariablesMap(taskInfoPay);
			verifyUsersPay = (String) taskInfoVariablesPay.get("verifyUsers");
		}
		// 当前付款审核人
		mav.addObject("verifyUsersPay", verifyUsersPay);
		// 采购订单修改申请列表（不分页）
		BuyorderModifyApply buyorderModifyApply = new BuyorderModifyApply();
		buyorderModifyApply.setBuyorderId(buyorder.getBuyorderId());
		List<BuyorderModifyApply> buyorderModifyApplyList = buyorderService
				.getBuyorderModifyApplyList(buyorderModifyApply);
		mav.addObject("buyorderModifyApplyList", buyorderModifyApplyList);
		// 判断是否有正在审核中的付款申请
		for (int i = 0; i < buyorderModifyApplyList.size(); i++) {
			if (buyorderModifyApplyList.get(i).getVerifyStatus() == 0) {
				mav.addObject("lockedReason", "采购单修改锁定");
			}
			// payApplyId = payApplyList.get(i).getPayApplyId();
			break;
		}
		// page=(Page)map.get("page");
		/*
		 * if(page.getTotalRecord()>30){ if((page.getTotalRecord()-30)%20!=0){
		 * page.setTotalPage((page.getTotalRecord()-30)/20+2); }else{
		 * page.setTotalPage((page.getTotalRecord()-30)/20+1); } }
		 */
		// mav.addObject("page",page);
		// 入库附件
		Attachment att = new Attachment();
		att.setRelatedId(bv.getBuyorderId());
		att.setAttachmentFunction(SysOptionConstant.ID_837);
		// att.setAttachmentType(460);
		List<Attachment> AttachmentList = warehouseInService.getAttachmentList(att);
		mav.addObject("AttachmentList", AttachmentList);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到修改采购订单页面
	 * 
	 * @param buyorder
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月7日 下午1:23:46
	 */
	@ResponseBody
	@RequestMapping(value = "saveEditBuyorderPage")
	public ModelAndView saveEditBuyorder(Buyorder buyorder, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("redirect:/order/buyorder/editAddBuyorderPage.do");
		mav.addObject("buyorderId", buyorder.getBuyorderId());
		return mav;

	}

	/**
	 * <b>Description:</b><br>
	 * 保存关闭采购订单
	 * 
	 * @param buyorder
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月7日 下午1:23:46
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "saveColseBuyorder")
	@SystemControllerLog(operationType = "eidt", desc = "关闭采购订单")
	public ResultInfo saveColseBuyorder(Buyorder buyorder, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setUpdater(user.getUserId());
		buyorder.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> res = buyorderService.saveCloseBuyorder(buyorder);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 通过采购单号查询采购单详情
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月8日 下午5:08:38
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "getBuyorderByBuyorderNo")
	public ResultInfo getBuyorderByBuyorderNo(HttpServletRequest request, Buyorder buyorder) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder, user);
		if (bv != null) {
			return new ResultInfo(0, saveBeforeParamToRedis(JsonUtils.translateToJson(bv)), bv);
		} else {
			return new ResultInfo(1, "订单不存在");
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 保存加入已有的采购订单
	 * 
	 * @param buyorder
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月9日 上午10:27:32
	 */
	@ResponseBody
	@RequestMapping(value = "saveAddHavedBuyorder")
	@SystemControllerLog(operationType = "edit", desc = "加入已有的采购订单")
	public ModelAndView saveAddHavedBuyorder(Buyorder buyorder, HttpServletRequest request,
			@RequestParam(required = false, value = "buySum") String[] buysums,
			@RequestParam(required = false, value = "dbBuyNum") String[] dbbuysums,
			@RequestParam(required = false, value = "saleorderNos") String[] saleorderNos, String beforeParams) {
		Map<String, Object> map = new HashMap<>();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Buyorder bo = new Buyorder();
		bo.setBuyorderId(buyorder.getBuyorderId());
		bo.setUpdater(user.getUserId());
		bo.setModTime(DateUtil.sysTimeMillis());
		map.put("buyorder", bo);
		if (buysums != null && buysums.length > 0) {
			String buysum = "";
			for (int i = 0; i < buysums.length; i++) {
				buysum += buysums[i] + ",";

			}
			map.put("buysums", buysum);
		}
		if (saleorderNos != null && saleorderNos.length > 0) {
			String saleorderNoStr = "";
			for (int i = 0; i < saleorderNos.length; i++) {
				saleorderNoStr += saleorderNos[i] + ",";
			}
			map.put("saleorderNoStr", saleorderNoStr);
		}

		if (buysums != null && buysums.length > 0) {
			String dbbuysum = "";
			for (int i = 0; i < dbbuysums.length; i++) {
				dbbuysum += dbbuysums[i] + ",";
			}
			map.put("dbbuysums", dbbuysum);
		}
		ResultInfo<?> res = buyorderService.saveAddHavedBuyorder(map);
		if (res != null && res.getCode() == 0) {
			ModelAndView mav = new ModelAndView("redirect:/order/buyorder/viewBuyorder.do");
			mav.addObject("buyorderId", buyorder.getBuyorderId());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("common/fail");
			mav.addObject("message", res.getMessage());
			return mav;
		}
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 新增快递
	 * 
	 * @param session
	 * @param buyorderVo
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月29日 下午3:54:40
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addExpress")
	public ModelAndView addExpress(HttpSession session, BuyorderVo buyorderVo) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorderVo, session_user);
		// 物流信息
		Express express = new Express();
		express.setBusinessType(SysOptionConstant.ID_515);
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		List<Integer> relatedIds = new ArrayList<Integer>();
		for (BuyorderGoodsVo buyorderGoodsVo : buyorderInfo.getBuyorderGoodsVoList()) {
			// 拼接关联ID的组
			relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId());
			// 准备计算所有快递单中产品数量
			map.put(buyorderGoodsVo.getBuyorderGoodsId(), 0);
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
			logger.error("buy order addExpress:", e);
		}
		// 获取物流公司列表
		List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
		mv.addObject("logisticsList", logisticsList);
		mv.addObject("buyorderInfo", buyorderInfo);
		mv.setViewName("order/buyorder/add_express");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 
	 * @param session
	 * @param buyorderVo
	 * @param express
	 * @param beforeParams
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年9月12日 下午5:37:12
	 */
	@ResponseBody
	@RequestMapping(value = "/editExpress")
	public ModelAndView editExpress(HttpSession session, BuyorderVo buyorderVo, Express express) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorderVo, session_user);
		List<Integer> relatedIds = new ArrayList<Integer>();
		// 物流信息
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		Map<Integer, Object> oldmap = new HashMap<Integer, Object>();
		Express oldExpress = new Express();
		oldExpress.setBusinessType(SysOptionConstant.ID_515);
		for (BuyorderGoodsVo buyorderGoodsVo : buyorderInfo.getBuyorderGoodsVoList()) {
			// 拼接关联ID的组
			relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId());
			// 准备计算所有快递单中产品数量
			map.put(buyorderGoodsVo.getBuyorderGoodsId(), 0);
			oldmap.put(buyorderGoodsVo.getBuyorderGoodsId(), 0);
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
			logger.error("buy order editExpress:", e);
		}
		// 获取物流公司列表
		List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
		mv.addObject("logisticsList", logisticsList);
		mv.addObject("buyorderInfo", buyorderInfo);
		mv.setViewName("order/buyorder/edit_express");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 保存新增快递信息
	 * 
	 * @param express
	 * @param deliveryTimes
	 * @param amount
	 * @param id_num_price
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月30日 下午1:23:53
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveAddExpress")
	@SystemControllerLog(operationType = "add", desc = "保存新增快递信息")
	public ResultInfo saveAddExpress(HttpServletRequest request, Express express, String deliveryTimes,
			BigDecimal amount, String id_num_price, BuyorderVo buyorderVo, HttpSession session) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 准备express中的expressDetailList
		List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
		if (null != id_num_price) {
			// 切割RelatedId和num拼接成的字符串
			String[] params = id_num_price.split("_");
			// 单价
			Integer price = 0;
			// 所有产品总价
			Integer allPrice = 0;
			// 已经分配过的金额
			Double allAmount = 0.00;
			// 每种产品平摊的运费
			Double expressDetailAmount = 0.00;
			if (null != params) {
				for (String s : params) {
					String[] bid_num = s.split("\\|");
					if (null != bid_num[1] && null != bid_num[2]) {
						// 数量*金额
						allPrice += Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue();
					}
				}
				for (int j = 0; j < params.length; j++) {
					String[] bid_num = params[j].split("\\|");
					ExpressDetail expressDetail = new ExpressDetail();
					if (null != bid_num[0]) {
						// 关联字段
						expressDetail.setRelatedId(Integer.parseInt(bid_num[0]));
					}
					if (null != bid_num[1]) {
						// 数量
						expressDetail.setNum(Integer.parseInt(bid_num[1]));
					}
					if (null != bid_num[2]) {
						// 单价
						price = Double.valueOf(bid_num[2]).intValue();
					}
					// 最后一个产品时
					if (null != amount) {
						if (j == (params.length - 1)) {
							expressDetailAmount = amount.doubleValue() - allAmount;
						} else {
							// 运费价格*（产品单价*产品数量）/所有产品总价 保留两位小数
							allAmount += (double) Math.round(amount.doubleValue()
									* (Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue()) / allPrice
									* 100) / 100;
							expressDetailAmount = (double) Math.round(amount.doubleValue()
									* (Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue()) / allPrice
									* 100) / 100;
						}
						BigDecimal eda = new java.math.BigDecimal(expressDetailAmount);
						eda = eda.setScale(2, BigDecimal.ROUND_HALF_UP);

						// 金额
						expressDetail.setAmount(eda);
					}
					// 业务类型
					expressDetail.setBusinessType(SysOptionConstant.ID_515);
					expressDetailList.add(expressDetail);
				}
			}
		}
		express.setAddTime(DateUtil.sysTimeMillis());
		express.setCreator(session_user.getUserId());
		express.setUpdater(session_user.getUserId());
		express.setModTime(DateUtil.sysTimeMillis());
		express.setDeliveryTime(DateUtil.convertLong(deliveryTimes, "yyyy-MM-dd"));
		express.setExpressDetail(expressDetailList);
		express.setIsEnable(1);
		express.setCompanyId(session_user.getCompanyId());
		express.setBusinessType(SysOptionConstant.ID_515);
		try {
			result = expressService.saveExpress(express);
			if (result == null || result.getCode() == -1) {
				return new ResultInfo<>();
			}
			BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorderVo, session_user);
			// 如果采购订单是直发的话，添加快递单需要更改采购订单发货状态，和销售订单发货状态
			/*
			 * if (buyorderInfo != null && buyorderInfo.getValidStatus() == 1 )
			 * { // 物流信息 Integer saleorderId =
			 * buyorderInfo.getBuyorderGoodsVoList().get(0).
			 * getSaleorderGoodsVoList().get(0) .getSaleorderId(); Express
			 * expressInfo = new Express();
			 * expressInfo.setBusinessType(SysOptionConstant.ID_515);
			 * List<Integer> relatedIds = new ArrayList<Integer>(); Integer
			 * allnum = 0; for (BuyorderGoodsVo buyorderGoodsVo :
			 * buyorderInfo.getBuyorderGoodsVoList()) {
			 * relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId()); //
			 * 获取订单里产品总件数 allnum = allnum + buyorderGoodsVo.getNum(); }
			 * expressInfo.setRelatedIds(relatedIds); try { List<Express>
			 * expressList = expressService.getExpressList(expressInfo); if
			 * (null != expressList && !expressList.isEmpty()) { Integer
			 * allarrivalnum = 0; for (Express e : expressList) { Integer
			 * arrivalnum = 0; for (ExpressDetail ed : e.getExpressDetail()) {
			 * arrivalnum = arrivalnum + ed.getNum(); } // 获取订单里已发产品总件数
			 * allarrivalnum = allarrivalnum + arrivalnum; } if (allnum > 0) {
			 * if (allarrivalnum > 0 && allnum > allarrivalnum) { // 部分发货
			 * if(buyorderInfo.getDeliveryDirect() == 1){ Saleorder saleorder =
			 * new Saleorder(); saleorder.setSaleorderId(saleorderId);
			 * saleorder.setDeliveryStatus(1);
			 * saleorder.setDeliveryTime(DateUtil.sysTimeMillis()); saleorder =
			 * saleorderService.saveEditSaleorderInfo(saleorder, request,
			 * session);
			 * 
			 * if(session_user.getCompanyId() == 1 && saleorderId > 0){
			 * vedengSoapService.orderSync(saleorderId); } } Buyorder buyorder =
			 * new Buyorder();
			 * buyorder.setBuyorderId(buyorderVo.getBuyorderId());
			 * buyorder.setDeliveryStatus(1);
			 * buyorder.setDeliveryTime(DateUtil.sysTimeMillis());
			 * buyorder.setUpdater(session_user.getUserId());
			 * buyorder.setModTime(DateUtil.sysTimeMillis()); result =
			 * buyorderService.saveBuyorder(buyorder); } else if (allarrivalnum
			 * > 0 && allnum == allarrivalnum){ // 全部发货
			 * if(buyorderInfo.getDeliveryDirect() == 1){ Saleorder saleorder =
			 * new Saleorder(); saleorder.setSaleorderId(saleorderId);
			 * saleorder.setDeliveryStatus(2);
			 * saleorder.setDeliveryTime(DateUtil.sysTimeMillis()); saleorder =
			 * saleorderService.saveEditSaleorderInfo(saleorder, request,
			 * session);
			 * 
			 * if(session_user.getCompanyId() == 1 && saleorderId > 0){
			 * vedengSoapService.orderSync(saleorderId); } } Buyorder buyorder =
			 * new Buyorder();
			 * buyorder.setBuyorderId(buyorderVo.getBuyorderId());
			 * buyorder.setDeliveryStatus(2);
			 * buyorder.setUpdater(session_user.getUserId());
			 * buyorder.setModTime(DateUtil.sysTimeMillis());
			 * buyorder.setDeliveryTime(DateUtil.sysTimeMillis()); result =
			 * buyorderService.saveBuyorder(buyorder); } } } } catch (Exception
			 * e) { // TODO Auto-generated catch block logger.error(Contant.ERROR_MSG, e); } }
			 */

		} catch (Exception e) {

			logger.error("saveAddExpress:", e);
		}
		return result;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 保存编辑快递信息
	 * 
	 * @param express
	 * @param deliveryTimes
	 * @param amount
	 * @param id_num_price
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月30日 下午1:23:53
	 */
	@ResponseBody
	@RequestMapping(value = "/saveEditExpress")
	@SystemControllerLog(operationType = "edit", desc = "保存编辑快递信息")
	public ResultInfo saveEditExpress(HttpServletRequest request, Express express, String deliveryTimes,
			BigDecimal amount, String id_num_price, String beforeParams, BuyorderVo buyorderVo, HttpSession session) {
		ResultInfo<?> result = new ResultInfo<>();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 准备express中的expressDetailList
		List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
		if (null != id_num_price) {
			// 切割RelatedId和num拼接成的字符串
			String[] params = id_num_price.split("_");
			// 单价
			Integer price = 0;
			// 所有产品总价
			Integer allPrice = 0;
			// 已经分配过的金额
			Double allAmount = 0.00;
			// 每种产品平摊的运费
			Double expressDetailAmount = 0.00;
			if (null != params) {
				for (String s : params) {
					String[] bid_num = s.split("\\|");
					if (null != bid_num[1] && null != bid_num[2]) {
						// 数量*金额
						allPrice += Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue();
					}
				}
				for (int j = 0; j < params.length; j++) {
					String[] bid_num = params[j].split("\\|");
					ExpressDetail expressDetail = new ExpressDetail();
					if (null != bid_num[0]) {
						// 关联字段
						expressDetail.setRelatedId(Integer.parseInt(bid_num[0]));
					}
					if (null != bid_num[1]) {
						// 数量
						expressDetail.setNum(Integer.parseInt(bid_num[1]));
					}
					if (null != bid_num[2]) {
						// 单价
						price = Double.valueOf(bid_num[2]).intValue();
					}
					// 最后一个产品时
					if (null != amount) {
						if (j == (params.length - 1)) {
							expressDetailAmount = amount.doubleValue() - allAmount;
						} else {
							// 运费价格*（产品单价*产品数量）/所有产品总价 保留两位小数
							allAmount += (double) Math.round(amount.doubleValue()
									* (Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue()) / allPrice
									* 100) / 100;
							expressDetailAmount = (double) Math.round(amount.doubleValue()
									* (Integer.parseInt(bid_num[1]) * Double.valueOf(bid_num[2]).intValue()) / allPrice
									* 100) / 100;
						}
						BigDecimal eda = new java.math.BigDecimal(expressDetailAmount);
						eda = eda.setScale(2, BigDecimal.ROUND_HALF_UP);

						// 金额
						expressDetail.setAmount(eda);
					}
					// 业务类型
					expressDetail.setBusinessType(SysOptionConstant.ID_515);
					expressDetailList.add(expressDetail);
				}
			}
		}
		express.setAddTime(DateUtil.sysTimeMillis());
		express.setCreator(session_user.getUserId());
		express.setUpdater(session_user.getUserId());
		express.setModTime(DateUtil.sysTimeMillis());
		express.setDeliveryTime(DateUtil.convertLong(deliveryTimes, "yyyy-MM-dd"));
		express.setExpressDetail(expressDetailList);
		express.setIsEnable(1);
		express.setBusinessType(SysOptionConstant.ID_515);
		try {
			result = expressService.saveExpress(express);
			if (result == null || result.getCode() == -1) {
				return new ResultInfo<>();
			}
			/*BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorderVo, session_user);
			// 如果采购订单是直发的话，添加快递单需要更改采购订单发货状态，和销售订单发货状态
			if (buyorderInfo != null && buyorderInfo.getValidStatus() == 1) {
				Integer saleorderId = buyorderInfo.getBuyorderGoodsVoList().get(0).getSaleorderGoodsVoList().get(0).getSaleorderId();
				// 物流信息
				Express expressInfo = new Express();
				expressInfo.setBusinessType(SysOptionConstant.ID_515);
				List<Integer> relatedIds = new ArrayList<Integer>();
				Integer allnum = 0;
				for (BuyorderGoodsVo buyorderGoodsVo : buyorderInfo.getBuyorderGoodsVoList()) {
					relatedIds.add(buyorderGoodsVo.getBuyorderGoodsId());
					// 获取订单里产品总件数
					allnum = allnum + buyorderGoodsVo.getNum();
				}
				expressInfo.setRelatedIds(relatedIds);
				try {
					List<Express> expressList = expressService.getExpressList(expressInfo);
					if (null != expressList && !expressList.isEmpty()) {
						Integer allarrivalnum = 0;
						for (Express e : expressList) {
							Integer arrivalnum = 0;
							for (ExpressDetail ed : e.getExpressDetail()) {
								arrivalnum = arrivalnum + ed.getNum();
							}
							// 获取订单里已发产品总件数
							allarrivalnum = allarrivalnum + arrivalnum;
						}
						if (allnum > 0) {
//							if (allarrivalnum == 0) {
//								// 未发货
//								Saleorder saleorder = new Saleorder();
//								saleorder.setDeliveryStatus(0);
//								saleorder = saleorderService.saveEditSaleorderInfo(saleorder, request, session);
//								buyorderVo.setDeliveryStatus(0);
//								result = buyorderService.saveBuyorder(buyorderVo);
//
//							} else 
							if (allarrivalnum > 0 && allnum > allarrivalnum) {
								// 部分发货
								if(buyorderInfo.getDeliveryDirect() == 1){
									Saleorder saleorder = new Saleorder();
									saleorder.setSaleorderId(saleorderId);
									saleorder.setDeliveryStatus(1);
									saleorder.setDeliveryTime(DateUtil.sysTimeMillis());
									saleorder = saleorderService.saveEditSaleorderInfo(saleorder, request, session);
								}
								Buyorder buyorder = new Buyorder();
								buyorder.setBuyorderId(buyorderVo.getBuyorderId());
								buyorder.setDeliveryStatus(1);
								buyorder.setDeliveryTime(DateUtil.sysTimeMillis());
								result = buyorderService.saveBuyorder(buyorder);
							} else if(allarrivalnum > 0 && allnum == allarrivalnum){
								// 全部发货
								if(buyorderInfo.getDeliveryDirect() == 1){
									Saleorder saleorder = new Saleorder();
									saleorder.setSaleorderId(saleorderId);
									saleorder.setDeliveryStatus(2);
									saleorder.setDeliveryTime(DateUtil.sysTimeMillis());
									saleorder = saleorderService.saveEditSaleorderInfo(saleorder, request, session);
								}
								Buyorder buyorder = new Buyorder();
								buyorder.setBuyorderId(buyorderVo.getBuyorderId());
								buyorder.setDeliveryStatus(2);
								buyorder.setDeliveryTime(DateUtil.sysTimeMillis());
								result = buyorderService.saveBuyorder(buyorderVo);
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(Contant.ERROR_MSG, e);
				}
			}*/
		} catch (Exception e) {
			logger.error("saveEditExpress:", e);
		}
		return result;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到新增售后页面
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
	public ModelAndView addAfterSalesPage(HttpServletRequest request, Buyorder buyorder) {
		ModelAndView mav = new ModelAndView();
		if ("qt".equals(buyorder.getFlag())) {
			mav.addObject("buyorder", buyorder);
			mav.setViewName("order/buyorder/add_afterSales_qt");
			return mav;
		}
		BuyorderVo sv = buyorderService.getBuyorderGoodsVoList(buyorder);
		mav.addObject("buyorder", sv);
		// 获取退货原因
		List<SysOptionDefinition> sysList = getSysOptionDefinitionList(536);
		mav.addObject("sysList", sysList);
		mav.addObject("domain", domain);
		if ("th".equals(buyorder.getFlag())) {// 退货
			mav.setViewName("order/buyorder/add_afterSales_th");
		} else if ("hh".equals(buyorder.getFlag())) {
			mav.setViewName("order/buyorder/add_afterSales_hh");
		} else if ("tp".equals(buyorder.getFlag())) {
			mav.setViewName("order/buyorder/add_afterSales_tp");
		} else if ("tk".equals(buyorder.getFlag())) {
			mav.setViewName("order/buyorder/add_afterSales_tk");
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
		afterSalesVo.setAfterSalesNum(afterSaleNums);
		afterSalesVo.setAttachName(fileName);
		afterSalesVo.setAttachUri(fileUri);
		afterSalesVo.setSubjectType(536);// 采购
		afterSalesVo.setAtferSalesStatus(0);
		afterSalesVo.setServiceUserId(user.getUserId());
		afterSalesVo.setStatus(0);
		afterSalesVo.setValidStatus(0);
		afterSalesVo.setDomain(domain);
		afterSalesVo.setInvoiceIds(invoiceIds);
		afterSalesVo.setCompanyId(user.getCompanyId());
		afterSalesVo.setPayee(user.getCompanyName());
		afterSalesVo.setTraderType(2);
		ModelAndView mav = new ModelAndView();
		ResultInfo<?> res = afterSalesOrderService.saveAddAfterSales(afterSalesVo, user);
		// mav.addObject("refresh",
		// "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		// mav.addObject("url","./viewBuyordersh.do?buyorderId="+afterSalesVo.getOrderId());
		if (res.getCode() == 0) {
			mav.addObject("url", "./viewAfterSalesDetail.do?afterSalesId=" + res.getData());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看采购订单的售后详情
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
		afterSales.setTraderType(2);
		ModelAndView mav = new ModelAndView();
		AfterSalesVo afterSalesVo = afterSalesOrderService.getAfterSalesVoDetail(afterSales);
		mav.addObject("afterSalesVo", afterSalesVo);
		CommunicateRecord communicateRecord = new CommunicateRecord();
		communicateRecord.setAfterSalesId(afterSalesVo.getAfterSalesId());
		List<CommunicateRecord> crList = traderCustomerService.getCommunicateRecordList(communicateRecord);
		mav.addObject("communicateList", crList);

		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"afterSalesVerify_" + afterSalesVo.getAfterSalesId());
		Task taskInfo = (Task) historicInfo.get("taskInfo");
		mav.addObject("taskInfo", historicInfo.get("taskInfo"));
		mav.addObject("startUser", historicInfo.get("startUser"));
		mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
		// 最后审核状态
		mav.addObject("endStatus", historicInfo.get("endStatus"));
		mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
		List<HistoricActivityInstance> historicActivityInstance = (List<HistoricActivityInstance>) historicInfo
				.get("historicActivityInstance");
		mav.addObject("commentMap", historicInfo.get("commentMap"));
		// 当前审核人
		String verifyUsers = null;
		if (null != taskInfo) {
			Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
			verifyUsers = (String) taskInfoVariables.get("verifyUsers");
		}
		mav.addObject("verifyUsers", verifyUsers);


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

		if (afterSalesVo.getType() == 546) {
			mav.setViewName("order/buyorder/view_afterSales_th");
		} else if (afterSalesVo.getType() == 547) {
			mav.setViewName("order/buyorder/view_afterSales_hh");
		} else if (afterSalesVo.getType() == 548) {
			mav.setViewName("order/buyorder/view_afterSales_tp");
		} else if (afterSalesVo.getType() == 549) {
			mav.setViewName("order/buyorder/view_afterSales_tk");
		}
		return mav;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 采购订单申请审核
	 * 
	 * @param request
	 * @param afterSales
	 * @param taskId
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年12月22日 上午10:05:08
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/editApplyValidBuyorder")
	@SystemControllerLog(operationType = "edit", desc = "采购订单申请审核")
	public ResultInfo<?> editApplyValidBuyorder(HttpServletRequest request, Buyorder buyorder, String taskId) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setCompanyId(user.getCompanyId());
		buyorder.setUpdater(user.getUserId());
		buyorder.setModTime(DateUtil.sysTimeMillis());
		buyorder.setCompanyName(user.getCompanyName());
		// 锁单
		buyorder.setLockedStatus(0);

		ResultInfo<?> res = buyorderService.saveBuyorder(buyorder);
		if (res.getCode() == -1) {
			return res;
		}
		try {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			// 查询当前订单的一些状态
			BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorder, user);
			BigDecimal beihuo = null;
			// 查询供应商主要产品
			TraderSupplier traderSupplier = new TraderSupplier();
			traderSupplier.setTraderId(buyorderInfo.getTraderId());
			TraderSupplierVo traderSupplierInfo = traderSupplierService.getTraderSupplierInfo(traderSupplier);
			List<Integer> goodsIdListBySupplier = goodsService
					.getSupplierGoodsIds(traderSupplierInfo.getTraderSupplierId());
			// 是否主要供应商(预留)
			Integer isMainSupply = 0;
			// 产品ID和结算价的Map
			Map<Integer, Object> goodsIdsMap = new HashMap<>();
			if (null != buyorderInfo.getBuyorderGoodsVoList()) {
				List<Integer> goodsIds = new ArrayList<>();
				for (BuyorderGoodsVo bg : buyorderInfo.getBuyorderGoodsVoList()) {
					goodsIds.add(bg.getGoodsId());
				}
				if (goodsIds != null && !goodsIds.isEmpty()) {
					goodsIdsMap = saleorderService.getSaleorderGoodsSettlementPriceByGoodsIds(goodsIds,
							user.getCompanyId());
				}
				for (BuyorderGoodsVo b : buyorderInfo.getBuyorderGoodsVoList()) {
					if (goodsIdsMap != null) {
						BigDecimal settlementPrice = (BigDecimal) goodsIdsMap.get(b.getGoodsId());
						if (null != settlementPrice) {
							b.setSettlementPrice(settlementPrice);
							beihuo = new java.math.BigDecimal(1.02);
							beihuo = beihuo.setScale(2, BigDecimal.ROUND_HALF_UP);
							// 备货价>(结算价/1.02)
							if (b.getPrice()
									.compareTo(settlementPrice.divide(beihuo, 2, BigDecimal.ROUND_HALF_UP)) == 1) {
								buyorderInfo.setIsOverSettlementPrice(1);
							}
						}
						if (null != goodsIdListBySupplier && !goodsIdListBySupplier.contains(b.getGoodsId())) {
							isMainSupply = 1;
						}
					}
				}

			}
			User creatorInfo = userService.getUserById(buyorderInfo.getCreator());

			// 开始生成流程(如果没有taskId表示新流程需要生成)
			if (taskId.equals("0")) {
				variableMap.put("isMainSupply", isMainSupply);
				variableMap.put("buyorderInfo", buyorderInfo);
				variableMap.put("currentAssinee", creatorInfo.getUsername());
				variableMap.put("processDefinitionKey", "buyorderVerify");
				variableMap.put("businessKey", "buyorderVerify_" + buyorderInfo.getBuyorderId());
				variableMap.put("relateTableKey", buyorderInfo.getBuyorderId());
				variableMap.put("relateTable", "T_BUYORDER");
				variableMap.put("orgId", user.getOrgId());
				actionProcdefService.createProcessInstance(request, "buyorderVerify",
						"buyorderVerify_" + buyorderInfo.getBuyorderId(), variableMap);
			}
			// 默认申请人通过
			// 根据BusinessKey获取生成的审核实例
			Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
					"buyorderVerify_" + buyorderInfo.getBuyorderId());
			if (historicInfo.get("endStatus") != "审核完成") {
				Task taskInfo = (Task) historicInfo.get("taskInfo");
				taskId = taskInfo.getId();
				Authentication.setAuthenticatedUserId(user.getUsername());
				Map<String, Object> variables = new HashMap<String, Object>();
				// 设置审核完成监听器回写参数
				variables.put("tableName", "T_BUYORDER");
				variables.put("id", "BUYORDER_ID");
				variables.put("idValue", buyorderInfo.getBuyorderId());
				variables.put("key", "VALID_STATUS");
				variables.put("value", 1);
				variableMap.put("key1", "LOCKED_STATUS");
				variableMap.put("value1", 0);
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
			logger.error("editApplyValidBuyorder:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 采购售后申请审核
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
	@SystemControllerLog(operationType = "edit", desc = "采购售后申请审核")
	public ResultInfo<?> editApplyAudit(HttpServletRequest request, AfterSalesVo afterSales, String taskId) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		afterSales.setStatus(1);// 审核中
		afterSales.setCompanyId(user.getCompanyId());
		afterSales.setModTime(DateUtil.sysTimeMillis());
		afterSales.setUpdater(user.getUserId());
		afterSales.setTraderType(2);
		ResultInfo<?> res = afterSalesOrderService.editApplyAudit(afterSales);
		if (res.getCode() == -1) {
			return res;
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
	 * @throws IOException
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月20日 上午9:00:02
	 */
	@ResponseBody
	@RequestMapping(value = "/editAfterSalesPage")
	public ModelAndView editAfterSalesPage(HttpServletRequest request, AfterSalesVo afterSales) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		if (afterSales == null) {
			return pageNotFound();
		}
		afterSales.setTraderType(2);
		afterSales.setCompanyId(user.getCompanyId());
		afterSales = afterSalesOrderService.getAfterSalesVoDetail(afterSales);
		mav.addObject("afterSales", afterSales);

		// 获取退货原因
		List<SysOptionDefinition> sysList = getSysOptionDefinitionList(536);
		mav.addObject("sysList", sysList);
		mav.addObject("domain", domain);

		Buyorder buyorder = new Buyorder();
		buyorder.setBuyorderId(afterSales.getOrderId());
		if (afterSales.getType() == 546) {
			buyorder.setFlag("th");
		} else if (afterSales.getType() == 547) {
			buyorder.setFlag("hh");
		} else if (afterSales.getType() == 548) {
			buyorder.setFlag("tp");
		} else if (afterSales.getType() == 549) {
			buyorder.setFlag("tk");
		}
		BuyorderVo sv = buyorderService.getBuyorderGoodsVoList(buyorder);
		mav.addObject("buyorder", sv);
		if (afterSales.getType() == 546) {// 退货
			mav.setViewName("order/buyorder/edit_afterSales_th");
		} else if (afterSales.getType() == 547) {
			mav.setViewName("order/buyorder/edit_afterSales_hh");
		} else if (afterSales.getType() == 548) {
			mav.setViewName("order/buyorder/edit_afterSales_tp");
		} else if (afterSales.getType() == 548) {
			mav.setViewName("order/buyorder/edit_afterSales_tk");
		}
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(sv)));
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
	@SystemControllerLog(operationType = "edit", desc = "保存编辑采购售后")
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
		afterSalesVo.setPayee(user.getCompanyName());
		ModelAndView mav = new ModelAndView();
		ResultInfo<?> res = afterSalesOrderService.saveEditAfterSales(afterSalesVo, user);
		// mav.addObject("refresh",
		// "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		// mav.addObject("url","./viewBuyordersh.do?buyorderId="+afterSalesVo.getOrderId());
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
	@ResponseBody
	@RequestMapping(value = "/saveCloseAfterSales")
	@SystemControllerLog(operationType = "edit", desc = "关闭售后订单")
	public ResultInfo<?> saveCloseAfterSales(HttpServletRequest request, AfterSalesVo afterSalesVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo<?> res = afterSalesOrderService.saveCloseAfterSales(afterSalesVo, user);
		if (res == null) {
			return new ResultInfo<>();
		}
		return res;
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
	public ModelAndView contractReturnInit(HttpServletRequest request, HttpSession session, Integer buyorderId) {
		// User user =
		// (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		mv.addObject("buyorderId", buyorderId);
		mv.setViewName("order/buyorder/contract_return");
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
		String path = "/upload/buyorder";
		long size = lwfile.getSize();
		if (size > 2 * 1024 * 1024) {
			return new FileInfo(-1, "图片大小应为2MB以内");
		}
		return ftpUtilService.uploadFile(lwfile, path, request, "");

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
	@SystemControllerLog(operationType = "add", desc = "采购订单合同回传保存")
	public ResultInfo<?> contractReturnSave(HttpServletRequest request, Attachment attachment) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (attachment != null && (attachment.getUri().contains("jpg") || attachment.getUri().contains("png")
				|| attachment.getUri().contains("gif") || attachment.getUri().contains("bmp"))) {
			attachment.setAttachmentType(SysOptionConstant.ID_460);
		} else {
			attachment.setAttachmentType(SysOptionConstant.ID_461);
		}
		attachment.setAttachmentFunction(SysOptionConstant.ID_514);
		if (user != null) {
			attachment.setCreator(user.getUserId());
			attachment.setAddTime(DateUtil.sysTimeMillis());
		}
		return saleorderService.saveSaleorderAttachment(attachment);
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
	@ResponseBody
	@RequestMapping(value = "/contractReturnDel")
	@SystemControllerLog(operationType = "edit", desc = "采购订单合同回传删除")
	public ResultInfo<?> contractReturnDel(HttpServletRequest request, Attachment attachment) {
		return saleorderService.delSaleorderAttachment(attachment);
	}

	/**
	 * <b>Description:</b><br>
	 * 备货计划管理
	 * 
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年11月22日 下午4:29:57
	 */
	@ResponseBody
	@RequestMapping(value = "/bhmanage")
	public ModelAndView bhManage(HttpServletRequest request, GoodsVo goodsVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);

		List<SysOptionDefinition> levelList = getSysOptionDefinitionList(334);

		goodsVo.setCompanyId(user.getCompanyId());

		List<GoodsVo> goodsList = null;
		Map<String, Object> map = buyorderService.getBHManageList(goodsVo, page);

		goodsList = (List<GoodsVo>) map.get("list");
		mv.addObject("goodsList", goodsList);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(goodsList)){
			List<Integer> skuIds = new ArrayList<>();
			goodsList.stream().forEach(goods -> {
				skuIds.add(goods.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		mv.addObject("levelList", levelList);
		mv.addObject("page", (Page) map.get("page"));
		// mv.addObject("maybeSaleNum", map.get("maybeSaleNum"));
		// mv.addObject("maybeOccupyAmount", map.get("maybeOccupyAmount"));
		mv.setViewName("order/buyorder/bh_manage");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 备货计划管理分析
	 * 
	 * @param goodsVo
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年2月10日 下午2:00:23
	 */
	@ResponseBody
	@RequestMapping(value = "/bhmanagestat")
	public ModelAndView bhManageStat(GoodsVo goodsVo, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();

		goodsVo.setCompanyId(user.getCompanyId());

		String maybeSaleNum = "maybeSaleNum";
		String maybeOccupyAmount = "maybeOccupyAmount";

		Cookie[] cookies = request.getCookies();// 这样便可以获取一个cookie数组
		if (null == cookies) {
			Map<String, Object> map = buyorderService.getBHManageStat(goodsVo);

			Cookie cookie = new Cookie(maybeSaleNum, map.get("maybeSaleNum").toString());
			cookie.setMaxAge(30 * 60);// 设置为30min
			response.addCookie(cookie);
			Cookie cookie2 = new Cookie(maybeOccupyAmount, map.get("maybeOccupyAmount").toString());
			cookie2.setMaxAge(30 * 60);// 设置为30min
			response.addCookie(cookie2);

			mv.addObject("maybeSaleNum", map.get("maybeSaleNum"));
			mv.addObject("maybeOccupyAmount", map.get("maybeOccupyAmount"));
		} else {
			Boolean ismaybeSaleNum = false;
			Boolean ismaybeOccupyAmount = false;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(maybeSaleNum)) {
					ismaybeSaleNum = true;
					mv.addObject("maybeSaleNum", cookie.getValue());
				}
				if (cookie.getName().equals(maybeOccupyAmount)) {
					ismaybeOccupyAmount = true;
					mv.addObject("maybeOccupyAmount", cookie.getValue());
				}
			}

			if (!ismaybeSaleNum || !ismaybeOccupyAmount) {
				Map<String, Object> map = buyorderService.getBHManageStat(goodsVo);

				Cookie cookie = new Cookie(maybeSaleNum, map.get("maybeSaleNum").toString());
				cookie.setMaxAge(30 * 60);// 设置为30min
				response.addCookie(cookie);
				Cookie cookie2 = new Cookie(maybeOccupyAmount, map.get("maybeOccupyAmount").toString());
				cookie2.setMaxAge(30 * 60);// 设置为30min
				response.addCookie(cookie2);

				mv.addObject("maybeSaleNum", map.get("maybeSaleNum"));
				mv.addObject("maybeOccupyAmount", map.get("maybeOccupyAmount"));
			}
		}

		mv.setViewName("order/buyorder/bh_manage_stat");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 备货计划生成备货订单
	 * 
	 * @param request
	 * @param session
	 * @param goodsIds
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年11月23日 下午2:34:49
	 */
	@ResponseBody
	@RequestMapping(value = "/addbhorder")
	@SystemControllerLog(operationType = "add", desc = "备货计划生成备货订单")
	public ModelAndView addBHOrder(HttpServletRequest request, HttpSession session,
			@RequestParam("goodsIds") String goodsIds) {
		ModelAndView mv = new ModelAndView();
		if (null == goodsIds || goodsIds.equals("")) {
			return pageNotFound();
		}
		Saleorder saleorder = new Saleorder();
		Saleorder bhSaleorder = saleorderService.saveAddBhSaleorder(saleorder, request, session);

		if (null != bhSaleorder) {
			String[] idList = goodsIds.split(",");
			List<Integer> list = new ArrayList<>();
			for (String id : idList) {
				list.add(Integer.parseInt(id));
			}
			ResultInfo resultInfo = buyorderService.batchAddBhSaleorderGoods(list, bhSaleorder, session);
			if (resultInfo.getCode().equals(0)) {
				mv.addObject("url",
						request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/order/saleorder/editBhSaleorder.do?saleorderId="
								+ bhSaleorder.getSaleorderId());
				return success(mv);
			} else {
				return fail(mv);
			}
		} else {
			return fail(mv);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 设置安全库存
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年11月27日 下午1:39:35
	 */
	@ResponseBody
	@RequestMapping(value = "/uplodegoodssafesotck")
	public ModelAndView uplodeGoodsSafeSotck(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("order/buyorder/uplode_goods_safe_stock");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存安全库存
	 * 
	 * @param request
	 * @param lwfile
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年11月27日 下午3:11:19
	 */
	@ResponseBody
	@RequestMapping("saveuplodegoodssafesotck")
	@SystemControllerLog(operationType = "import", desc = "保存安全库存")
	public ResultInfo<?> saveUplodeGoodsSafeSotck(HttpServletRequest request, HttpSession session,
			@RequestParam("lwfile") MultipartFile lwfile) {
		ResultInfo<?> resultInfo = new ResultInfo<>();
		try {
			User user = (User) session.getAttribute(Consts.SESSION_USER);
			// 临时文件存放地址
			String path = request.getSession().getServletContext().getRealPath("/upload/goods");
			FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
			if (fileInfo.getCode() == 0) {
				List<GoodsSafeStock> list = new ArrayList<>();
				// 获取excel路径
				Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
				// 获取第一面sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 起始行
				int startRowNum = sheet.getFirstRowNum() + 1;
				int endRowNum = sheet.getLastRowNum();// 结束行

				for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
					Row row = sheet.getRow(rowNum);
					int startCellNum = row.getFirstCellNum();// 起始列
					int endCellNum = row.getLastCellNum() - 1;// 结束列
					// 获取excel的值
					GoodsSafeStock goodsSafeStock = new GoodsSafeStock();
					if (user != null) {
						goodsSafeStock.setCompanyId(user.getCompanyId());
						goodsSafeStock.setModTime(DateUtil.gainNowDate());
						goodsSafeStock.setUpdater(user.getUserId());
					}
					for (int cellNum = startCellNum; cellNum <= endCellNum; cellNum++) {// 循环列数（下表从0开始）
						Cell cell = row.getCell(cellNum);

						if (cellNum == 0) {// 第一列数据cellNum==startCellNum
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null || cell.getCellType() != 1) {
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列订货号错误！");
							} else {
								goodsSafeStock.setSku(cell.getStringCellValue().toString());
							}
						}

						if (cellNum == 3) {// 第二列数据cellNum==(startCellNum+1)
							// 若excel中无内容，而且没有空格，cell为空--默认3，空白
							if (cell == null) {
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列安全库存不允许为空！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列安全库存不允许为空！");
							} else if (cell.getCellType() != 0) {
								resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列安全库存只能为数字！");
								throw new Exception("表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列安全库存只能为数字！");
							} else if (cell.getNumericCellValue() > 999999999) {
								resultInfo.setMessage(
										"表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列安全库存数量不得超过999999999！");
								throw new Exception(
										"表格项错误，第" + (rowNum + 1) + "行第" + (cellNum + 1) + "列安全库存数量不得超过999999999！");
							} else {
								goodsSafeStock.setNum((int) cell.getNumericCellValue());
							}
						}
					}
					list.add(goodsSafeStock);
				}

				// 保存更新
				resultInfo = goodsService.saveUplodeGoodsSafeSotck(list);
			}

		} catch (Exception e) {
			logger.error("saveuplodegoodssafesotck:", e);
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 打印采购单
	 * 
	 * @param request
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月14日 上午9:34:16
	 */
	@ResponseBody
	@RequestMapping(value = "/printOrder")
	public ModelAndView printOrder(HttpServletRequest request, Buyorder buyorder) {

		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		buyorder.setCompanyId(user.getCompanyId());
		BuyorderVo buyorderVo = buyorderService.getBuyOrderPrintInfo(buyorder);
		// BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder, user);
		BuyorderVo buyorderInfo = buyorderService.getBuyorderInDetail(buyorderVo, user);
		// 获取采购人员信息
		UserDetail detail = userDetailMapper.getUserDetail(buyorderVo.getUserId());
		User userInfo = userService.getUserById(buyorderVo.getUserId());
		mv.addObject("orgId", userInfo.getOrgId());
		String username = userService.getUserById(buyorderVo.getUserId()).getUsername();

		Long currTime = DateUtil.sysTimeMillis();
		mv.addObject("currTime", DateUtil.convertString(currTime, "YYYY-MM-dd "));

		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);

		// 获取公司信息
		Company company = companyService.getCompanyByCompangId(user.getCompanyId());
		mv.addObject("company", company);

		ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
		paramsConfigVo.setCompanyId(company.getCompanyId());
		paramsConfigVo.setParamsKey(100);
		AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);

		mv.addObject("delivery", delivery);
		mv.addObject("detail", detail);
		mv.addObject("username", username);
		mv.addObject("buyorderVo", buyorderVo);
		// mv.addObject("bv", bv);
		// 运费类型
		List<SysOptionDefinition> yfTypes = getSysOptionDefinitionList(469);
		mv.addObject("yfTypes", yfTypes);
		mv.addObject("buyorderGoodsList", buyorderVo.getBuyorderGoodsVoList());
		BigDecimal pageTotalPrice = new BigDecimal(0.00);
		BigDecimal zioe = pageTotalPrice;
		Integer flag = -1;
		for (BuyorderGoodsVo buyorderGoods : buyorderVo.getBuyorderGoodsVoList()) {
			String price = getCommaFormat(buyorderGoods.getPrice());
			if (!price.contains(".")) {
				price += ".00";
			}
			buyorderGoods.setPrices(price);
			String allprice = getCommaFormat(buyorderGoods.getPrice().multiply(new BigDecimal(buyorderGoods.getNum())));
			if (!allprice.contains(".")) {
				allprice += ".00";
			}
			buyorderGoods.setAllPrice(allprice);
			pageTotalPrice = pageTotalPrice
					.add(buyorderGoods.getPrice().multiply(new BigDecimal(buyorderGoods.getNum())));
		}
		String totalAmount = getCommaFormat(pageTotalPrice);
		if (!totalAmount.contains(".")) {
			totalAmount += ".00";
		}
		mv.addObject("totalAmount", totalAmount);
		try {
			mv.addObject("chineseNumberTotalPrice", pageTotalPrice.compareTo(zioe) > 0
					? DigitToChineseUppercaseNumberUtils.numberToChineseNumber(pageTotalPrice) : null);
		} catch (ShowErrorMsgException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		if (user.getCompanyId() == 10) {
			if (buyorder.getDeliveryDirect() == 0) {
				mv.setViewName("order/buyorder/buyorder_pf_print");
			} else if (buyorder.getDeliveryDirect() == 1) {
				mv.setViewName("order/buyorder/buyoredr_zf_print");
			}
		} else {
			if (buyorder.getDeliveryDirect() == 0) {
				mv.setViewName("order/buyorder/order_pf_print");
			} else if (buyorder.getDeliveryDirect() == 1) {
				mv.setViewName("order/buyorder/order_zf_print");
			}
		}
		return mv;
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
	 * 确认审核
	 * 
	 * @param session
	 * @param taskId
	 * @param pass
	 * @param type
	 * @param buyorderId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年1月3日 下午1:54:42
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session, String taskId, Boolean pass, Integer type, Integer buyorderId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.addObject("type", type);
		if (null != buyorderId) {
			mv.addObject("buyorderId", buyorderId);
		} else {
			mv.addObject("buyorderId", 0);
		}
		mv.setViewName("order/buyorder/complement");
		return mv;
	}


	/** @description: payApplyPassBatch.
	 * @notes: VDERP-1215 付款申请增加批量操作功能.
	 * @author: Tomcat.Hui.
	 * @date: 2019/9/12 17:11.
	 * @param request
	 * @param ids
	 * @return: com.vedeng.common.model.ResultInfo.
	 * @throws: .
	 */
	@ResponseBody
	@RequestMapping(value="/batchComplementTask")
	@SystemControllerLog(operationType = "edit",desc = "批量审核通过")
	public ResultInfo batchComplementTask(HttpServletRequest request,String[] ids, HttpSession session){

		if (null != ids && ids.length > 0){
			for (String idStr : ids){

				//获取付款申请信息
				PayApply payApply = payApplyService.getPayApplyInfo(Integer.parseInt(idStr));
                Map<String, Object> historicInfoPay=actionProcdefService.getHistoric(processEngine, "paymentVerify_"+ idStr);
                Task taskInfoPay = (Task) historicInfoPay.get("taskInfo");
                Map<Object,Object> candidateUserMapPay = (Map<Object, Object>) historicInfoPay.get("candidateUserMap");
                User user = (User) session.getAttribute(ErpConst.CURR_USER);

                boolean b1 = null != payApply && null != payApply.getPayType();
                boolean b2 = null != taskInfoPay && null != taskInfoPay.getId() ;
                boolean b3 = (null != taskInfoPay.getProcessInstanceId() && null != taskInfoPay.getAssignee()) || !((List)candidateUserMapPay.get(taskInfoPay.getId())).isEmpty();
                boolean b4 = user.getUsername().equals(taskInfoPay.getAssignee());
                boolean b5 = null != candidateUserMapPay.get("belong") && (boolean)candidateUserMapPay.get("belong") == true;


                if (b1 && b2 && b3 && (b4 || b5)){

                	/** 开启子线程共享 ServletRequestAttributes .
					 * 在activiti中有使用到 RequestContextHolder.getRequestAttributes()
					 * 默认情况下该属性不会被传递到子线程，所以会出现 nullPointer
					 * */
					ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
					RequestContextHolder.setRequestAttributes(sra, true);

                    if (payApply.getPayType().equals(517)){
                        //采购
                        new Thread(() -> {
                            log.info("批量付款申请-采购线程 {} 启动",Thread.currentThread().getId());
                            this.complementTask(request, taskInfoPay.getId(), "", true, 0, session);
                        }).start();
                    } else if (payApply.getPayType().equals(518)){
                        //售后
                        new Thread(() -> {
                            log.info("批量付款申请-售后线程 {} 启动",Thread.currentThread().getId());
                            this.complementTaskSH(request,taskInfoPay.getId(),"",true,0,session);
                        }).start();
                    }
				}
			}
		}
		return new ResultInfo(0,"已提交处理，请注意跟进订单状态");
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 采购单审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove = true)
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
			String tableName = null;
			// 如果审核没结束添加审核对应主表的审核状态
			Integer status = 0;
			TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
			String id = (String) taskService.getVariable(taskId, "id");
			Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
			String key = (String) taskService.getVariable(taskId, "key");
			tableName = (String) taskService.getVariable(taskId, "tableName");
			// 使用任务id,获取任务对象，获取流程实例id
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String taskName = task.getName();
			if (pass) {
				// 如果修改的主表是付款申请表，审核节点为财务制单，则修改制单状态
				status = 0;
				if (tableName.equals("T_PAY_APPLY") && taskName.equals("财务制单")) {
					// 制单
					actionProcdefService.updateInfo(tableName, id, idValue, "IS_BILL", 1, 2);
				}
			} else {
				// 如果审核不通过
				status = 2;
				// 回写数据的表在db中
				variables.put("db", 2);
				if (tableName.equals("T_BUYORDER")) {
					// 采购单申请不通过解锁
					actionProcdefService.updateInfo(tableName, "BUYORDER_ID", buyorderId, "STATUS", 0, 2);
					actionProcdefService.updateInfo(tableName, "BUYORDER_ID", buyorderId, "LOCKED_STATUS", 0, 2);
				} else if (tableName.equals("T_PAY_APPLY")) {
					buyorderId = (Integer) taskService.getVariable(taskId, "buyorderId");
					// 采购单付款申请不通过解锁
					actionProcdefService.updateInfo("T_BUYORDER", "BUYORDER_ID", buyorderId, "LOCKED_STATUS", 0, 2);
					if (tableName != null && id != null && idValue != null && key != null) {
						actionProcdefService.updateInfo(tableName, id, idValue, key, 2, 2);
					}
				} else {
					if (tableName != null && id != null && idValue != null && key != null) {
						actionProcdefService.updateInfo(tableName, id, idValue, key, 2, 2);
					}
				}
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}
			ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment,
					user.getUsername(), variables);
			// 如果未结束添加审核对应主表的审核状态
			if (!complementStatus.getData().equals("endEvent")) {
				verifiesRecordService.saveVerifiesInfo(taskId, status);
			}
			Buyorder buyorder = new Buyorder();
			if (tableName != null && tableName.equals("T_PAY_APPLY")) {
				return new ResultInfo(0, "操作成功");
			} else {
				if (buyorderId != null && buyorderId != 0) {
					buyorder.setBuyorderId(buyorderId);
					BuyorderVo buyorderInfo = buyorderService.getBuyorderVoDetail(buyorder, user);
					Integer statusInfo = null;
					Map<String, Object> data = new HashMap<String, Object>();
					if (buyorderInfo.getValidStatus() == 1) {
						statusInfo = 1;
						data.put("buyorderId", buyorderInfo.getBuyorderId());
					} else {
						statusInfo = 0;
					}
					return new ResultInfo(0, "操作成功", statusInfo, data);
				} else {
					return new ResultInfo(0, "操作成功");
				}
			}
		} catch (Exception e) {
			logger.error("buy order complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 采购单售后审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/complementAfterSaleTask")
	@SystemControllerLog(operationType = "edit", desc = "采购单售后审核操作")
	public ResultInfo<?> complementAfterSaleTask(HttpServletRequest request, String taskId, String comment,
			Boolean pass, HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		// 审批操作
		try {
			if (!pass) {
				// 如果不通过审核
				TaskService taskService = processEngine.getTaskService();// 获取任务的Service，设置和获取流程变量
				taskService.setVariable(taskId, "value", 3);
				String tableName = (String) taskService.getVariable(taskId, "tableName");
				String id = (String) taskService.getVariable(taskId, "id");
				Integer idValue = (Integer) taskService.getVariable(taskId, "idValue");
				String key = (String) taskService.getVariable(taskId, "key");
				if (tableName != null && id != null && idValue != null && key != null) {
					actionProcdefService.updateInfo(tableName, id, idValue, key, 3, 2);
				}
			}
			// 如果审核没结束添加审核对应主表的审核状态
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
			logger.error("complementAfterSaleTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 采购订单确认收货初始化页面
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年2月11日 下午4:12:51
	 */
	@ResponseBody
	@RequestMapping(value = "confirmArrivalInit")
	public ModelAndView confirmArrivalInit(HttpServletRequest request, Buyorder buyorder) {
		ModelAndView mv = new ModelAndView("order/buyorder/confirm_arrival_init");
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		BuyorderVo bv = buyorderService.getBuyorderVoDetail(buyorder, user);
		mv.addObject("buyorderVo", bv);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 采购订单确认收货
	 * 
	 * @param request
	 * @param session
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年2月11日 下午4:13:14
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmArrival")
	public ModelAndView confirmArrival(HttpServletRequest request, HttpSession session, Buyorder buyorder) {
		ModelAndView mv = new ModelAndView();
		try {
			buyorder = buyorderService.confirmArrival(buyorder, request, session);
			if (null != buyorder) {
				mv.addObject("url", "./viewBuyorder.do?buyorderId=" + buyorder.getBuyorderId());
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
	 * 采购订单申请修改
	 * 
	 * @param request
	 * @param buyorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年5月23日 上午9:22:26
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "modifyApplyInit")
	public ModelAndView modifyApplyInit(HttpServletRequest request, Buyorder buyorder) {
		ModelAndView mv = new ModelAndView("order/buyorder/modify_apply_init");
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorder.setCompanyId(user.getCompanyId());
		BuyorderVo bv = buyorderService.getBuyorderVoApplyUpdateDetail(buyorder);
		mv.addObject("bv", bv);

		// 普发收货地址
		ParamsConfigValue paramsConfigValue = new ParamsConfigValue();
		paramsConfigValue.setCompanyId(user.getCompanyId());
		paramsConfigValue.setParamsConfigId(ErpConst.TWO);
		// 2018-2-4 查询全部收货地址
		mv.addObject("addressList", buyorderService.getAddressVoList(paramsConfigValue));

		// 获取公司信息
		Company company = companyService.getCompanyByCompangId(user.getCompanyId());
		mv.addObject("companyName", company.getCompanyName());

        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
        if(!CollectionUtils.isEmpty(bv.getBgvList())){

            List<Integer> skuIds = new ArrayList<>();

            bv.getBgvList().stream().forEach(buyOrderGoods->{
                skuIds.add(buyOrderGoods.getGoodsId());
            });

            List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
            Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
            mv.addObject("newSkuInfosMap", newSkuInfosMap);
        }
        //新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

		// 收票种类
		List<SysOptionDefinition> receiptTypes = getSysOptionDefinitionList(SysOptionConstant.ID_428);
		mv.addObject("receiptTypes", receiptTypes);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存采购申请修改
	 * 
	 * @param request
	 * @param buyorderGoodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月6日 上午10:59:38
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveBuyorderApplyUpdate")
	@SystemControllerLog(operationType = "edit", desc = "保存采购申请修改")
	public ModelAndView saveBuyorderApplyUpdate(HttpServletRequest request, BuyorderModifyApply buyorderModifyApply,
			BuyorderModifyApplyGoodsVo buyorderModifyApplyGoodsVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		buyorderModifyApply.setCompanyId(user.getCompanyId());
		buyorderModifyApply.setAddTime(DateUtil.sysTimeMillis());
		buyorderModifyApply.setCreator(user.getUserId());
		ResultInfo res = buyorderService.saveBuyorderApplyUpdate(buyorderModifyApply, buyorderModifyApplyGoodsVo);
		if (null != res && res.getCode() == 0) {
			// 生成流程
			try {
				// 获取订单修改信息
				buyorderModifyApply.setBuyorderModifyApplyId(Integer.parseInt(String.valueOf(res.getData())));
				BuyorderModifyApplyVo bmav = buyorderService.getBuyorderModifyApplyVoDetail(buyorderModifyApply);
				Map<String, Object> variableMap = new HashMap<String, Object>();
				// 开始生成流程(如果没有taskId表示新流程需要生成)
				variableMap.put("buyorderModifyApplyInfo", bmav);
				variableMap.put("orderId", buyorderModifyApply.getBuyorderId());
				variableMap.put("currentAssinee", user.getUsername());
				variableMap.put("processDefinitionKey", "editBuyorderVerify");
				variableMap.put("businessKey", "editBuyorderVerify_" + buyorderModifyApply.getBuyorderModifyApplyId());
				variableMap.put("relateTableKey", buyorderModifyApply.getBuyorderModifyApplyId());
				variableMap.put("relateTable", "T_BUYORDER_MODIFY_APPLY");
				// 设置审核完成监听器回写参数
				variableMap.put("tableName", "T_BUYORDER_MODIFY_APPLY");
				variableMap.put("id", "BUYORDER_MODIFY_APPLY_ID");
				variableMap.put("idValue", buyorderModifyApply.getBuyorderModifyApplyId());
				variableMap.put("idValue1", buyorderModifyApply.getBuyorderId());
				variableMap.put("key", "VALID_STATUS");
				variableMap.put("value", 1);
				// 回写数据的表在db中
				variableMap.put("db", 2);
				actionProcdefService.createProcessInstance(request, "editBuyorderVerify",
						"editBuyorderVerify_" + buyorderModifyApply.getBuyorderModifyApplyId(), variableMap);
				// 默认申请人通过
				// 根据BusinessKey获取生成的审核实例
				Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
						"editBuyorderVerify_" + buyorderModifyApply.getBuyorderModifyApplyId());
				if (historicInfo.get("endStatus") != "审核完成") {
					Saleorder saleorderLocked = new Saleorder();
					Task taskInfo = (Task) historicInfo.get("taskInfo");
					String taskId = taskInfo.getId();
					Authentication.setAuthenticatedUserId(user.getUsername());
					Map<String, Object> variables = new HashMap<String, Object>();
					// 默认审批通过
					variables.put("pass", true);
					ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "",
							user.getUsername(), variables);
					// 如果未结束添加审核对应主表的审核状态
					if (!complementStatus.getData().equals("endEvent")) {
						verifiesRecordService.saveVerifiesInfo(taskId, 0);
					}
				}
			} catch (Exception e) {
				logger.error("saveBuyorderApplyUpdate:", e);
				mav.addObject("message", "任务完成操作失败：" + e.getMessage());
				return fail(mav);
			}

			mav.addObject("url", "./viewModifyApply.do?buyorderModifyApplyId=" + res.getData());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 添加运费
	 * 
	 * @param request
	 * @param session
	 * @param buyorder
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年2月11日 下午4:13:14
	 */
	@FormToken(save = true)
	@ResponseBody
	@RequestMapping(value = "/addFreightPage")
	public ModelAndView addFreightPage(HttpServletRequest request, BuyorderGoodsVo buyorderGoodsVo) throws IOException {
		ModelAndView mav = new ModelAndView("order/buyorder/add_freight");
		buyorderGoodsVo = buyorderService.getFreightByBuyorderId(buyorderGoodsVo);
		mav.addObject("bgv", buyorderGoodsVo);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(buyorderGoodsVo)));
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存采购订单的运费
	 * 
	 * @param request
	 * @param buyorderGoodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月6日 上午10:59:38
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveBuyorderFreight")
	@SystemControllerLog(operationType = "edit", desc = "保存采购订单的运费")
	public ResultInfo<?> saveBuyorderFreight(HttpServletRequest request, BuyorderGoodsVo buyorderGoodsVo) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ResultInfo res = buyorderService.saveBuyorderFreight(buyorderGoodsVo, user);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存采购修改订单生效状态
	 * 
	 * @param request
	 * @param buyorderGoods
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年7月13日 上午9:31:43
	 */
	@ResponseBody
	@RequestMapping(value = "/saveApplyBuyorderModfiy")
	@SystemControllerLog(operationType = "add", desc = "保存采购修改订单生效状态")
	public ResultInfo<?> saveApplyBuyorderModfiy(HttpServletRequest request, BuyorderModifyApply buyorderModifyApply) {
		// User user = (User)
		// request.getSession().getAttribute(ErpConst.CURR_USER);
		buyorderModifyApply.setValidStatus(1);
		buyorderModifyApply.setValidTime(DateUtil.sysTimeMillis());
		ResultInfo<?> res = buyorderService.saveApplyBuyorderModfiyValidStatus(buyorderModifyApply);
		return res;
	}

	/**
	 * <b>Description:</b><br>
	 * 采购订单修改列表
	 * 
	 * @param request
	 * @param buyorderModifyApply
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> <br>
	 *       <b>Date:</b>
	 */
	@ResponseBody
	@RequestMapping(value = "getBuyorderModifyApplyListPage")
	public ModelAndView getBuyorderModifyApplyListPage(HttpServletRequest request,
			BuyorderModifyApplyVo buyorderModifyApplyVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView("order/buyorder/modify_apply_index");
		Page page = getPageTag(request, pageNo, pageSize);
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		try {
			buyorderModifyApplyVo.setCompanyId(user.getCompanyId());
			Map<String, Object> map = buyorderService.getBuyorderModifyApplyListPage(buyorderModifyApplyVo, page);
			if (map != null && map.containsKey("list")) {
				mv.addObject("list", (List<BuyorderModifyApplyVo>) map.get("list"));
			}
			if (map != null && map.containsKey("page")) {
				mv.addObject("page", (Page) map.get("page"));
			}
			mv.addObject("bmav", buyorderModifyApplyVo);
			return mv;
		} catch (Exception e) {
			logger.error("getBuyorderModifyApplyListPage:", e);
			return pageNotFound();
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 查看订单修改详情
	 * 
	 * @param request
	 * @param saleorderModifyApply
	 * @return
	 * @Note <b>Author:</b> <br>
	 *       <b>Date:</b>
	 */
	@ResponseBody
	@RequestMapping(value = "viewModifyApply")
	public ModelAndView viewModifyApply(HttpServletRequest request, BuyorderModifyApply buyorderModifyApply) {
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		mv.addObject("curr_user", curr_user);
		buyorderModifyApply.setCompanyId(curr_user.getCompanyId());

		// 获取订单修改信息
		BuyorderModifyApplyVo bmav = buyorderService.getBuyorderModifyApplyVoDetail(buyorderModifyApply);
		mv.addObject("bmav", bmav);

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(bmav.getBgvList())){
			List<Integer> skuIds = new ArrayList<>();
			bmav.getBgvList().stream().forEach((BuyorderGoodsVo buyorderGoods) -> {
				skuIds.add(buyorderGoods.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);

		// 订单修改审核信息
		Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine,
				"editBuyorderVerify_" + buyorderModifyApply.getBuyorderModifyApplyId());
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
		if (verifyUsers != null) {
			verifyUsersList = Arrays.asList(verifyUsers.split(","));
		}
		mv.addObject("verifyUsers", verifyUsers);
		mv.addObject("verifyUserList", verifyUserList);
		mv.addObject("verifyUsersList", verifyUsersList);

		mv.setViewName("order/buyorder/view_modify_apply");
		return mv;
	}

	/** @description: complementTaskSH.
     * @notes: add by Tomcat.Hui 2019/9/11 13:05 .Desc: VDERP-1215 付款申请增加批量操作功能
     * (从com.vedeng.finance.controller.InvoiceAfterController#complementTask()中拷贝而来).
     * @author: Tomcat.Hui.
     * @date: 2019/9/18 13:57.
     * @param request
     * @param taskId
     * @param comment
     * @param pass
     * @param buyorderId
     * @param session
     * @return: com.vedeng.common.model.ResultInfo<?>.
     * @throws: .
     */
    public ResultInfo<?> complementTaskSH(HttpServletRequest request, String taskId, String comment, Boolean pass,
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
                //如果修改的主表是付款申请表，审核节点为财务制单，则修改制单状态
                if(tableName.equals("T_PAY_APPLY") && taskName.equals("财务制单")){

					//根据付款申请查询关联表，如果是售后，并且是售后类型是539,543；
					//VDERP-2193 如果是售后退货、退款生成的支付申请，那么在制单之前增加限制：退款金额不能大于账户余额
					TraderCustomer traderCustomer = null;
					if (payApply.getPayType().equals(SysOptionConstant.ID_518)){

						AfterSales afterSales = afterSalesOrderService.getAfterSalesById(payApply.getRelatedId());
						if (SysOptionConstant.ID_539.equals(afterSales.getType()) || SysOptionConstant.ID_543.equals(afterSales.getType())){
							//获取售后支付申请客户的余额信息
							traderCustomer = traderCustomerService.getTraderByPayApply(idValue);
							if (payApply.getAmount().compareTo(traderCustomer.getAmount()) > 0){
								return new ResultInfo<>(-1,"退款金额大于账户余额，无法退款");
							}
						}

					}


					//制单
					actionProcdefService.updateInfo(tableName, id, idValue, "IS_BILL", 1, 2);

					////VDERP-2193 如果是售后退货、退款生成的支付申请，那么制单成功则扣减余额
					if (traderCustomer != null ){
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

	/**
	 * <b>Description:</b><br>
	 * 批量刷新采购生效时间
	 * 
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年4月25日 下午4:23:16
	 *//*
		 * @ResponseBody
		 * 
		 * @RequestMapping(value="/updatebuyordervalidtime") public void
		 * updateBuyorderValidTime(){ String str=""; int[] ids ={
		 * 18898,20423,20449,20450,20601,20622,20627,20895,20919,21036,18999,
		 * 19314,19386,19429,19458,19483,19508,19509,19516,19559,19564,19584,
		 * 19656,19669,19690,19700,19735,19738,19789,19790,19793,20001,20050,
		 * 20061,20101,20132,20201,20237,20243,20253,20254,20260,20273,20286,
		 * 20292,20293,20294,20295,20297,20312,20316,20318,20324,20325,20326,
		 * 20327,20330,20331,20332,20333,20334,20336,20339,20340,20343,20344,
		 * 20345,20347,20348,20349,20350,20351,20352,20353,20354,20357,20358,
		 * 20359,20360,20361,20362,20363,20364,20365,20366,20367,20368,20369,
		 * 20370,20371,20372,20373,20374,20375,20376,20377,20378,20379,20381,
		 * 20382,20383,20384,20386,20387,20388,20389,20391,20392,20393,20395,
		 * 20396,20397,20398,20399,20400,20401,20406,20408,20410,20411,20412,
		 * 20414,20415,20416,20417,20418,20420,20421,20427,20428,20429,20431,
		 * 20433,20434,20435,20437,20438,20440,20443,20445,20446,20447,20448,
		 * 20451,20453,20455,20456,20457,20458,20460,20462,20463,20464,20465,
		 * 20466,20467,20468,20470,20471,20472,20473,20474,20475,20477,20478,
		 * 20479,20480,20481,20483,20484,20485,20486,20487,20488,20490,20491,
		 * 20492,20493,20494,20496,20497,20498,20499,20500,20501,20502,20503,
		 * 20504,20505,20506,20507,20508,20510,20511,20512,20513,20514,20515,
		 * 20516,20517,20518,20519,20520,20522,20523,20524,20526,20527,20528,
		 * 20530,20531,20532,20533,20534,20535,20536,20537,20538,20540,20541,
		 * 20542,20543,20544,20545,20546,20547,20548,20549,20550,20551,20552,
		 * 20553,20555,20557,20558,20559,20561,20562,20563,20564,20566,20568,
		 * 20569,20571,20572,20573,20575,20576,20577,20579,20580,20581,20582,
		 * 20583,20584,20586,20587,20588,20590,20591,20592,20593,20594,20595,
		 * 20596,20598,20599,20600,20603,20604,20605,20606,20607,20608,20609,
		 * 20611,20612,20614,20615,20616,20617,20620,20621,20623,20624,20625,
		 * 20626,20628,20629,20630,20631,20632,20635,20636,20637,20638,20639,
		 * 20640,20641,20642,20643,20644,20645,20646,20647,20648,20649,20651,
		 * 20652,20653,20654,20655,20657,20658,20659,20660,20661,20662,20663,
		 * 20664,20665,20667,20668,20669,20670,20673,20674,20675,20677,20678,
		 * 20679,20680,20683,20684,20685,20686,20687,20688,20689,20690,20691,
		 * 20693,20694,20695,20696,20697,20698,20699,20700,20701,20702,20704,
		 * 20705,20706,20708,20709,20712,20713,20714,20716,20718,20719,20720,
		 * 20721,20722,20723,20724,20727,20728,20729,20730,20731,20732,20733,
		 * 20734,20735,20736,20737,20739,20740,20741,20742,20743,20745,20746,
		 * 20747,20748,20749,20750,20751,20752,20753,20755,20756,20757,20758,
		 * 20759,20760,20761,20762,20763,20764,20765,20766,20768,20769,20771,
		 * 20772,20773,20774,20775,20776,20777,20778,20779,20780,20781,20782,
		 * 20783,20786,20788,20789,20793,20794,20796,20798,20799,20800,20801,
		 * 20802,20803,20804,20806,20807,20808,20809,20810,20812,20813,20814,
		 * 20815,20816,20817,20818,20819,20820,20821,20822,20823,20824,20825,
		 * 20826,20827,20829,20830,20831,20833,20834,20835,20836,20837,20838,
		 * 20839,20840,20841,20842,20843,20844,20846,20847,20848,20849,20851,
		 * 20854,20855,20856,20857,20859,20860,20864,20866,20867,20868,20869,
		 * 20870,20871,20872,20873,20874,20875,20876,20877,20878,20879,20880,
		 * 20881,20882,20883,20884,20885,20886,20887,20888,20889,20891,20892,
		 * 20893,20894,20896,20897,20898,20899,20902,20903,20905,20906,20907,
		 * 20908,20910,20911,20912,20913,20915,20917,20918,20920,20921,20922,
		 * 20923,20924,20925,20926,20927,20929,20930,20932,20935,20939,20940,
		 * 20941,20942,20943,20944,20945,20947,20948,20950,20952,20953,20954,
		 * 20955,20956,20957,20959,20960,20961,20963,20964,20965,20966,20967,
		 * 20968,20969,20970,20971,20974,20977,20978,20979,20981,20982,20984,
		 * 20986,20987,20988,20990,20992,20993,20994,20995,20996,20998,21000,
		 * 21002,21003,21004,21005,21006,21007,21008,21009,21011,21012,21013,
		 * 21014,21015,21016,21017,21018,21019,21022,21023,21025,21027,21029,
		 * 21030,21031,21032,21033,21034,21035,21037,21038,21039,21040,21041,
		 * 21042,21043,21044,21045,21046,21047,21048,21049,21050,21051,21052,
		 * 21053,21054,21055,21056,21057,21059,21060,21061,21062,21064,21065,
		 * 21066,21068,21069,21070,21071,21072,21073,21074,21075,21076,21077,
		 * 21078,21080,21081,21083,21084,21085,21086,21087,21088,21089,21090,
		 * 21091,21094,21100,21101,21102,21103,21104,21105,21106,21109,21110,
		 * 21111,21112,21113,21114,21115,21116,21117,21118,21119,21121,21123,
		 * 21124,21125,21126,21127,21128,21129,21130,21131,21132,21133,21134,
		 * 21135,21136,21138,21139,21140,21141,21143,21144,21145,21146,21147,
		 * 21149,21150,21152,21154,21155,21156,21157,21158,21159,21160,21161,
		 * 21163,21164,21166,21168,21169,21170,21171,21172,21173,21174,21175,
		 * 21176,21177,21179,21180,21183,21184,21185,21186,21187,21188,21189,
		 * 21191,21192,21193,21194,21196,21197,21198,21199,21200,21202,21203,
		 * 21204,21207,21208,21210,21211,21212,21213,21214,21218,21219,21220,
		 * 21221,21223,21224,21225,21226,21228,21229,21230,21231,21232,21233,
		 * 21236,21238,21239,21241,21242,21246,21248,21249,21250,21251,21253,
		 * 21254,21255,21257,21258,21259,21260,21261,21262,21263,21264,21265,
		 * 21269,21270,21272,21273,21274,21275,21277,21279,21284,21285,21287,
		 * 21289,21291,21294}; for(int i=0 ;i<ids.length;i++){ Map<String,
		 * Object> historicInfo =
		 * actionProcdefService.getHistoric(processEngine, "buyorderVerify_" +
		 * ids[i]); List<HistoricActivityInstanceEntity> object = (List)
		 * historicInfo.get("historicActivityInstance");
		 * for(HistoricActivityInstanceEntity entity : object){ if(null !=
		 * entity.getActivityName() && entity.getActivityName().equals("审核完成")
		 * && null != entity.getEndTime() ){ str
		 * +=ids[i]+","+DateUtil.DateToLong(entity.getEndTime())+";"; } }
		 * 
		 * } System.out.println(str);
		 * 
		 * }
		 */

}
