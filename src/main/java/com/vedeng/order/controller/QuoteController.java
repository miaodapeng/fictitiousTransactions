package com.vedeng.order.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.goods.service.VgoodsService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.dao.UserDetailMapper;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.UserDetail;
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
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.service.GoodsChannelPriceService;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.goods.service.GoodsSettlementPriceService;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.QuoteorderConsult;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.order.service.QuoteService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.ParamsConfigValueService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.TagService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;


/**
 * <b>Description:</b><br> 报价管理
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.order.controller
 * <br><b>ClassName:</b> QuoteorderController
 * <br><b>Date:</b> 2017年6月21日 上午10:03:38
 */
@Controller
@RequestMapping("/order/quote")
public class QuoteController extends BaseController{
	public static Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired // 自动装载
	@Qualifier("historyService")
	private HistoryService historyService;
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("quoteService")
	private QuoteService quoteService;
	
	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;//部门
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService
	
	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;//客户-交易者
	
	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;
	
	@Autowired
	@Qualifier("tagService")
	private TagService tagService;
	
	@Autowired
	@Qualifier("ftpUtilService")
	private FtpUtilService ftpUtilService;

	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService
	
	@Autowired
	@Qualifier("userDetailMapper")
	private UserDetailMapper userDetailMapper;
	
	
	@Autowired
	@Qualifier("goodsChannelPriceService")
	private GoodsChannelPriceService goodsChannelPriceService;
	
	@Autowired
	@Qualifier("goodsSettlementPriceService")
	private GoodsSettlementPriceService goodsSettlementPriceService;
	@Autowired
	@Qualifier("bussinessChanceService")
	private BussinessChanceService bussinessChanceService;
	
	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;
	
	@Autowired
	@Qualifier("paramsConfigValueService")
	private ParamsConfigValueService paramsConfigValueService;

	@Autowired
	private VgoodsService vGoodsService;

	/**
	 * <b>Description:</b><br> 报价信息列表查询
	 * @param request
	 * @param quoteorder
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月21日 上午10:04:12
	 */
	@ResponseBody
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request,Quoteorder quote,HttpSession session,
			@RequestParam(required = false, value="beginTime") String beginTime,
			@RequestParam(required = false, value="endTime") String endTime,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView();
		
		Page page = getPageTag(request,pageNo,pageSize);
		
		Map<String, Object> map;
		try {
			if(StringUtils.isNotBlank(beginTime)){
				quote.setBeginDate(DateUtil.convertLong(beginTime + " 00:00:00",""));
			}
			if(StringUtils.isNotBlank(endTime)){
				quote.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
			}
			mv.addObject("beginTime", beginTime);mv.addObject("endTime", endTime);
			if(quote.getFollowOrderStatus()==null){
				quote.setFollowOrderStatus(0);//默认查询跟单中
			}else if(quote.getFollowOrderStatus().equals(-1)){
				quote.setFollowOrderStatus(null);//查询全部
			}
			//查询沟通记录
			if(quote.getTimeType()!=null && quote.getTimeType()==2){
				if(quote.getBeginDate()!=null || quote.getEndDate()!=null){//若都为空，则查询全部报价列表，不需要查询沟通记录
					//根据时间获取沟通记录中外键ID
					List<Integer> keyIds = quoteService.getCommunicateRecordByDate(quote.getBeginDate(),quote.getEndDate(),"" + SysOptionConstant.ID_245);
					if(null == keyIds){
						keyIds = new ArrayList<Integer>(){{add(-1);}};
					}else if(keyIds.size() == 0){
						keyIds.add(-1);
					}
					quote.setKeyIds(keyIds);
				}
			}
			
			//商机来源
			List<SysOptionDefinition> bussSource=getSysOptionDefinitionList(365);
			mv.addObject("bussSource", bussSource);
			//客户性质
			List<SysOptionDefinition> customerNatureList = getSysOptionDefinitionList(464);
			mv.addObject("customerNatureList", customerNatureList);

			User user = (User)session.getAttribute(Consts.SESSION_USER);
			//获取销售部门
			List<Organization> orgList = orgService.getSalesOrgList(SysOptionConstant.ID_310,user.getCompanyId());
			mv.addObject("orgList",orgList);
			
			
			quote.setCompanyId(user.getCompanyId());
			//获取当前销售用户下级职位用户
			//List<User> saleUserList = userService.getNextAllUserList(user.getUserId(), user.getCompanyId(), true, user.getPositLevel(), SysOptionConstant.ID_310);
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> userList = userService.getMyUserList(user, positionType, false);
//			quote.setSaleUserList(userList);
			mv.addObject("userList",userList);//归属销售人员
			mv.addObject("loginUser",user);//登陆用户
			if(quote.getOptUserId()!=null){
				User s_user = new User();
				s_user.setUserId(quote.getOptUserId());
				quote.setSaleUserList(new ArrayList(){{add(s_user);}});
			}else if(user.getPositType()!=null && user.getPositType().intValue()==SysOptionConstant.ID_310){
				quote.setSaleUserList(userList);
			}
			map = quoteService.getQuoteListPage(quote,page);
			
			List<Quoteorder> list = (List<Quoteorder>)map.get("quoteList");
			
			List<Integer> userIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
			List<Integer> traderIdList = new ArrayList<>();
			List<Integer> quoteIdList = new ArrayList<>();List<Integer> businessIdList = new ArrayList<>();
			if(list!=null && list.size()>0){
				int list_size = list.size();
				for(int i=0;i<list_size;i++){
					userIdList.add(list.get(i).getUserId());orgIdList.add(list.get(i).getOrgId());
					//客户ID
					traderIdList.add(list.get(i).getTraderId());
					quoteIdList.add(list.get(i).getQuoteorderId());
					businessIdList.add(list.get(i).getBussinessChanceId());
					
					//审核人
					if(null != list.get(i).getVerifyUsername()){
					    List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));  
					    list.get(i).setVerifyUsernameList(verifyUsernameList);
					}
					list.get(i).setCustomerNatureStr(getSysOptionDefinition(list.get(i).getCustomerNature()).getTitle());
				}
				List<CommunicateRecord> communicateNumList = quoteService.getCommunicateNumList(new ArrayList<>(), quoteIdList, new ArrayList<>());
				List<User> traderUserList = userService.getUserByTraderIdList(traderIdList,1);
				List<User> orderUserList = userService.getUserByUserIds(userIdList);
				List<Organization> OrgList = userService.getOrgNameByOrgIdList(orgIdList,user.getCompanyId());
				int communicateNum = 0;
				for(int i=0;i<list_size;i++){
					for(int a=0;a<traderUserList.size();a++){
						if(traderUserList.get(a).getTraderId().equals(list.get(i).getTraderId())){
							list.get(i).setOptUserName(traderUserList.get(a).getUsername());
						}
					}
					for(int b=0;b<orderUserList.size();b++){
						if(orderUserList.get(b).getUserId().equals(list.get(i).getUserId())){
							list.get(i).setSalesName(orderUserList.get(b).getUsername());
						}
					}
					for(int c=0;c<OrgList.size();c++){
						if(OrgList.get(c).getOrgId().equals(list.get(i).getOrgId())){
							list.get(i).setSalesDeptName(OrgList.get(c).getOrgName());
						}
					}
					
					for(int d=0;d<communicateNumList.size();d++){
						if(communicateNumList.get(d).getRelatedId() != null && communicateNumList.get(d).getRelatedId().equals(list.get(i).getQuoteorderId())){
							communicateNum = communicateNum + communicateNumList.get(d).getCommunicateCount();
						}
					}
					list.get(i).setCommunicateNum(communicateNum);communicateNum = 0;
				}
			}
			
			
			mv.addObject("quoteList",list);
			mv.addObject("quote",quote);
			
			mv.addObject("page", (Page)map.get("page"));
		} catch (Exception e) {
			logger.error("quote index:", e);
		}
		
		mv.setViewName("order/quote/index_quote");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 新增报价前验证客户是否被禁用
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月26日 下午5:10:06
	 */
	@ResponseBody
	@RequestMapping(value="/getTraderCustomerStatus")
	public ResultInfo<?> getTraderCustomerStatus(HttpServletRequest request,@RequestParam(value="traderCustomerId")Integer traderCustomerId){
		return quoteService.getTraderCustomerStatus(traderCustomerId);
	}
	/**
	 * <b>Description:</b><br> 添加报价信息初始化
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月26日 下午1:23:06
	 */
	@FormToken(save=true)
	@RequestMapping(value="/addQuote")
	//@RequestParam(value="traderId",required=false)required=false非必须项，默认true
	public ModelAndView addQuote(HttpServletRequest request,Quoteorder quote){
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject("quote", quote);
			
			//根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getCustomerBussinessInfo(quote.getTraderId());
			mv.addObject("customer", customer);

			//查询联系人和联系地址
			TraderContactVo traderContactVo=new TraderContactVo();
			traderContactVo.setTraderId(quote.getTraderId());
			traderContactVo.setTraderType(ErpConst.ONE);
			traderContactVo.setIsEnable(1);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			// 联系人
			List<TraderContactVo> userList = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
			List<TraderAddressVo> addressList = (List<TraderAddressVo>)map.get("address");
			mv.addObject("userList", userList);
			mv.addObject("addressList", addressList);
			
			//采购类型
			List<SysOptionDefinition> purchasingTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_404);
			mv.addObject("purchasingTypeList", purchasingTypeList);
			
			//付款条件
			List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_407);
			mv.addObject("paymentTermList", paymentTermList);
			
			//采购时间
			List<SysOptionDefinition> purchasingTimeList = getSysOptionDefinitionList(SysOptionConstant.ID_410);
			mv.addObject("purchasingTimeList", purchasingTimeList);
		} catch (Exception e) {
			logger.error("addQuote:", e);
		}
		mv.setViewName("order/quote/add_quote");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存报价信息
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:39:36
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveQuote")
	@SystemControllerLog(operationType = "add",desc = "保存报价信息")
	public ModelAndView saveQuote(HttpServletRequest request,Quoteorder quote,@RequestParam(value="quoteSource",required=false)String quoteSource){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		try {
			if(quoteService.isExistBussinessChanceId(quote.getBussinessChanceId())>0){
			 	mv.addObject("message", "该商机已存在");
				return fail(mv);
			}
			if(user!=null){
				quote.setCompanyId(user.getCompanyId());
				quote.setCreator(user.getUserId());
				quote.setAddTime(DateUtil.sysTimeMillis());
				
				quote.setUpdater(user.getUserId());
				quote.setModTime(DateUtil.sysTimeMillis());
				
				quote.setUserId(user.getUserId());
				//销售部门（若一个多个部门，默认取第一个部门）
				Organization org = orgService.getOrgNameByUserId(user.getUserId());
				quote.setOrgId(org==null?null:org.getOrgId());
			}
		} catch (Exception e) {
			logger.error("saveQuote:", e);
		}
		//客户等级
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + quote.getCustomerLevel())){
//			String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + quote.getCustomerLevel());
//			net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(json_result);
//			sod = (SysOptionDefinition)  net.sf.json.JSONObject.toBean(object, SysOptionDefinition.class);
//			quote.setCustomerLevel(sod.getTitle());
//		}
		SysOptionDefinition sysOptionDefinition = getSysOptionDefinition(Integer.valueOf(quote.getCustomerLevel()));
		if(sysOptionDefinition!=null){
			quote.setCustomerLevel(sysOptionDefinition.getTitle());
		}
		ResultInfo<Quoteorder> result = quoteService.saveQuote(quote);

		mv.addObject("refresh", "false_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		if(result.getCode()==0){
			mv.addObject("url","./getQuoteDetail.do?quoteorderId="+result.getData().getQuoteorderId()+"&viewType=1&quoteSource="+quoteSource);
			return success(mv);
		}else{
			mv.addObject("message", result.getMessage());
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 查询报价基本信息（需添加产品、付款记录等其他信息）
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:39:55
	 */
	@FormToken(save=true)
	@RequestMapping(value="/getQuoteDetail")
	public ModelAndView getQuoteDetail(HttpServletRequest request,Quoteorder quote,HttpSession hs
			,@RequestParam(value="viewType",required=false)Integer viewType
			,@RequestParam(value="quoteSource",required=false)String quoteSource){
		ModelAndView mv  = new ModelAndView();
		try {
			quote = quoteService.getQuoteInfoByKey(quote.getQuoteorderId());
			//日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(quote)));
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			mv.addObject("curr_user", user);
			//根据报价ID查询报价产品（全部）
			Map<String,Object> map = quoteService.getQuoteGoodsByQuoteId(quote.getQuoteorderId(),user.getCompanyId(),hs,viewType,quote.getTraderId());
			List<QuoteorderGoods> quoteGoodsList = (List<QuoteorderGoods>)map.get("quoteGoodsList");

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			List<Integer> skuIds = new ArrayList<>();
			quoteGoodsList.stream().forEach(quoteGood -> {
				skuIds.add(quoteGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end

			//根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(quote.getTraderId());
			mv.addObject("customer", customer);
			//产品核算价
			quoteGoodsList = goodsChannelPriceService.getQuoteChannelPriceList(quote.getSalesAreaId(),quote.getCustomerNature(),customer.getOwnership(),quoteGoodsList);
			//产品结算价
			quoteGoodsList = goodsSettlementPriceService.getGoodsSettlePriceByGoodsList(user.getCompanyId(),quoteGoodsList);
			mv.addObject("quoteGoodsList", quoteGoodsList);
			mv.addObject("userList", (List<User>)map.get("userList"));
			mv.addObject("loginUser",user);
			
			//销售人员名称
			user = userService.getUserByTraderId(quote.getTraderId(), ErpConst.ONE);
			if(user!=null){
				quote.setOptUserName(user.getUsername());
			}
			
			//创建人员
			user = userService.getUserById(quote.getCreator());
			quote.setCreatorName(user==null?"":user.getUsername());
			
			//销售部门名称(根据部门ID)
			quote.setSalesDeptName(orgService.getOrgNameById(quote.getOrgId()));
			
			//客户类型
			quote.setCustomerTypeStr(getSysOptionDefinition(quote.getCustomerType()).getTitle());
			//客户性质
			quote.setCustomerNatureStr(getSysOptionDefinition(quote.getCustomerNature()).getTitle());
			//采购方式
			quote.setPurchasingTypeStr(getSysOptionDefinition(quote.getPurchasingType()).getTitle());
			//付款条件
			quote.setPaymentTermStr(getSysOptionDefinition(quote.getPaymentTerm()).getTitle());
			//采购时间
			quote.setPurchasingTimeStr(getSysOptionDefinition(quote.getPurchasingTime()).getTitle());
			//终端类型
			quote.setTerminalTraderTypeStr(getSysOptionDefinition(quote.getTerminalTraderType()).getTitle());
			mv.addObject("quote", quote);
			
			//付款条件列表
			List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(SysOptionConstant.ID_418);
			mv.addObject("paymentTermList", paymentTermList);
			
			//发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			mv.addObject("invoiceTypeList", invoiceTypeList);
			
			//运费说明
			List<SysOptionDefinition> freightList = getSysOptionDefinitionList(SysOptionConstant.ID_469);
			mv.addObject("freightList", freightList);
			Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "quoteVerify_"+ quote.getQuoteorderId());
			mv.addObject("taskInfo", historicInfo.get("taskInfo"));
			mv.addObject("startUser", historicInfo.get("startUser"));
			// 最后审核状态
			mv.addObject("endStatus",historicInfo.get("endStatus"));
			mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
			mv.addObject("commentMap", historicInfo.get("commentMap"));
			mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
			Map<String, Object> historicInfoClose=actionProcdefService.getHistoric(processEngine, "closeQuoteorderVerify_"+ quote.getQuoteorderId());
	    		Task taskInfoClose = (Task) historicInfoClose.get("taskInfo");
	    		mv.addObject("taskInfoClose", taskInfoClose);
			mv.addObject("startUserClose", historicInfoClose.get("startUser"));
			// 最后审核状态
			mv.addObject("endStatusClose",historicInfoClose.get("endStatus"));
			mv.addObject("historicActivityInstanceClose", historicInfoClose.get("historicActivityInstance"));
			mv.addObject("commentMapClose", historicInfoClose.get("commentMap"));
			mv.addObject("candidateUserMapClose", historicInfoClose.get("candidateUserMap"));
			Task taskInfo = (Task) historicInfo.get("taskInfo");
	    	//当前审核人
	    	String verifyUsers = null;
	    	List<String> verifyUserList = new ArrayList<>();
	    	String reason = null;
	    	if(null!=taskInfo){
	    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfo);
	    	    verifyUsers = (String) taskInfoVariables.get("verifyUsers");
	    	}
	    	mv.addObject("verifyUsers", verifyUsers);
	    	
	    	
	    	String verifyUsersClose = null;
	    	if(null!=taskInfoClose){
	    	    Map<String, Object> taskInfoVariablesClose= actionProcdefService.getVariablesMap(taskInfoClose);
	    	    String verifyUser = (String) taskInfoVariablesClose.get("verifyUserList");
	    	    if(null != verifyUser){
	    	    	verifyUserList = Arrays.asList(verifyUser.split(","));
	    	    }
	    	    verifyUsersClose = (String) taskInfoVariablesClose.get("verifyUsers");
	    	}
	    	Map<String, Object> taskInfoVariablesReason= actionProcdefService.getVariablesMap("closeQuoteorderVerify_"+ quote.getQuoteorderId());
	    	if(taskInfoVariablesReason != null){
	    	    reason = (String) taskInfoVariablesReason.get("reason");
	    	}
	    	mv.addObject("verifyUserList", verifyUserList);
	    	mv.addObject("reason", reason);
	    	mv.addObject("verifyUsersClose", verifyUsersClose);
 		    
	    	
	    	
			if(viewType != null && viewType != 1){//null添加完报价后转入，1报价列表转入
				//查询报价咨询记录
				List<QuoteorderConsult> consultList = quoteService.getQuoteConsultList(quote.getQuoteorderId());
				if(!consultList.isEmpty()){
					mv.addObject("consultList", consultList);
					List<Integer> userIds = new ArrayList<Integer>();
					for(QuoteorderConsult qc:consultList){
						userIds.add(qc.getCreator());
					}
					List<User> userList = userService.getUserByUserIds(userIds);
					mv.addObject("userList", userList);
				}
				if(viewType!=5){
					//查询沟通记录
					//沟通类型为商机和报价
					CommunicateRecord cr = new CommunicateRecord();
					cr.setQuoteorderId(quote.getQuoteorderId());
					cr.setBussinessChanceId(quote.getBussinessChanceId());
					List<CommunicateRecord> communicateList = traderCustomerService.getCommunicateRecordList(cr);
					if(!communicateList.isEmpty()){
						//沟通内容
						mv.addObject("communicateList", communicateList);
					}
				}
				if(viewType==2){//编辑报价后视图
					if (StringUtils.isNotEmpty(quote.getSaleorderId()) // 生成订单后
							// 或者订单生效，未成单，审核通过
							|| (quote.getFollowOrderStatus().equals(0) && quote.getValidStatus().equals(1) && quote.getVerifyStatus().equals(1))) {
						mv.setViewName("order/quote/view_quote_sale");
					} else {
						mv.setViewName("order/quote/edit_quote_detail_2");
					}
				}else if(viewType==3){//销售报价视图
					mv.setViewName("order/quote/view_quote_sale");
				}else if(viewType==4){//销售主管报价视图
					mv.setViewName("order/quote/view_quote_director");
				}else if(viewType==5){//报价咨询详细视图
					mv.setViewName("order/quote/view_quote_consult");
				}
				if(viewType == 2 || viewType == 5){
					//根据分类获取产品负责人
					if(quoteGoodsList != null && quoteGoodsList.size() > 0){
						List<Integer> categoryIdList = new ArrayList<>();
						for(int i=0;i<quoteGoodsList.size();i++){
							categoryIdList.add(quoteGoodsList.get(i).getCategoryId());
						}
						
						List<QuoteorderGoods> my_quoteGoodsList = new ArrayList<>();
						List<QuoteorderGoods> they_quoteGoodsList = new ArrayList<>();
						categoryIdList = new ArrayList<Integer>(new HashSet<Integer>(categoryIdList));
						List<User> categoryUserList = quoteService.getGoodsCategoryUserList(categoryIdList,quote.getCompanyId());
						if(categoryUserList != null && categoryUserList.size() > 0){
							user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
							mv.addObject("loginUserId", user.getUserId()+";");
							//默认报价咨询人
							User contsultantUser = paramsConfigValueService.getQuoteConsultant(user.getCompanyId(),107);
							if(contsultantUser == null){
								contsultantUser = new User();
							}
							mv.addObject("contsultantUserId", contsultantUser.getUserId()+";");
							for(int i=0;i<quoteGoodsList.size();i++){
								for(int j=0;j<categoryUserList.size();j++){
									if(categoryUserList.get(j).getCategoryId().equals(quoteGoodsList.get(i).getCategoryId())){
										quoteGoodsList.get(i).setGoodsUserIdStr((quoteGoodsList.get(i).getGoodsUserIdStr()==null?";":quoteGoodsList.get(i).getGoodsUserIdStr()) + categoryUserList.get(j).getUserId() + ";");
									}
								}
								//归属当前登录用户的商品排列在前
								if(quoteGoodsList.get(i).getGoodsUserIdStr() != null && quoteGoodsList.get(i).getGoodsUserIdStr().contains(user.getUserId()+";")){
									my_quoteGoodsList.add(quoteGoodsList.get(i));
								}else if(user.getUserId().equals(contsultantUser.getUserId())){//当前登录的用户是报价咨询默认人
									my_quoteGoodsList.add(quoteGoodsList.get(i));
								}else{
									they_quoteGoodsList.add(quoteGoodsList.get(i));
								}
							}
							my_quoteGoodsList.addAll(they_quoteGoodsList);
							quoteGoodsList.clear();
							quoteGoodsList.addAll(my_quoteGoodsList);
						}
					}
				}
			}else{//报价添加产品视图
				if(quote.getCustomerNature().intValue() == 465){//分销
					// 省级地区
					List<Region> provinceList = regionService.getRegionByParentId(1);
					mv.addObject("provinceList", provinceList);
					if(quote.getSalesAreaId() != null){
						// 地区
						List<Region> regionList = (List<Region>) regionService.getRegion(quote.getSalesAreaId(), 1);
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
				mv.setViewName("order/quote/edit_quote_detail_1");
			}
			mv.addObject("quoteSource", quoteSource);//标记报价来源
		} catch (Exception e) {
			logger.error("getQuoteDetail:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 报价下一步信息加载初始化
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:40:20
	 */
	@RequestMapping(value="/editQuote")
	public ModelAndView editQuote(HttpServletRequest request,Quoteorder quote){
		ModelAndView mv = new ModelAndView();
		try {
			quote = quoteService.getQuoteInfoByKey(quote.getQuoteorderId());
			mv.addObject("quote", quote);
			//日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(quote)));
			
			//查询联系人和联系地址
			TraderContactVo traderContactVo=new TraderContactVo();
			traderContactVo.setTraderId(quote.getTraderId());
			traderContactVo.setTraderType(ErpConst.ONE);
			Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
			String tastr = (String) map.get("contact");
			net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
			// 联系人
			List<TraderContactVo> userList = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
			List<TraderAddressVo> addressList = (List<TraderAddressVo>)map.get("address");
			mv.addObject("userList", userList);
			mv.addObject("addressList", addressList);
			
			//采购类型
			List<SysOptionDefinition> purchasingTypeList = getSysOptionDefinitionList(404);
//			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 404)){
//				String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 404);
//				JSONArray jsonArray = JSONArray.fromObject(json_result);
//				purchasingTypeList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			}
			mv.addObject("purchasingTypeList", purchasingTypeList);
			
			//付款条件
			List<SysOptionDefinition> paymentTermList = getSysOptionDefinitionList(407);
//			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 407)){
//				String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 407);
//				JSONArray jsonArray = JSONArray.fromObject(json_result);
//				paymentTermList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			}
			mv.addObject("paymentTermList", paymentTermList);
			
			//采购时间
			List<SysOptionDefinition> purchasingTimeList = getSysOptionDefinitionList(410);
//			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 410)){
//				String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 410);
//				JSONArray jsonArray = JSONArray.fromObject(json_result);
//				purchasingTimeList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			}
			mv.addObject("purchasingTimeList", purchasingTimeList);
		} catch (Exception e) {
			logger.error("editQuote:", e);
		}
		mv.setViewName("order/quote/edit_quote");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 修改报价中客户信息
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:40:41
	 */
	@ResponseBody
	@RequestMapping(value="/updateQuoteCustomer")
	@SystemControllerLog(operationType = "edit",desc = "修改报价中客户信息")
	public ResultInfo<?> updateQuoteCustomer(HttpServletRequest request,String beforeParams,Quoteorder quote){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
		}
		return quoteService.updateQuoteCustomer(quote);
	}
	
	/**
	 * <b>Description:</b><br> 修改报价终端信息
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:42:19
	 */
	@ResponseBody
	@RequestMapping(value="/updateQuoteTerminal")
	public ResultInfo<?> updateQuoteTerminal(HttpServletRequest request,Quoteorder quote){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
		}
		return quoteService.updateQuoteTerminal(quote);
	}
	
	/**
	 * <b>Description:</b><br> 添加报价产品信息初始化
	 * @param request
	 * @param searchContent
	 * @param quoteorderId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:17:22
	 */
	@FormToken(save=true)
	@RequestMapping(value="/addQuoteGoods")
	public ModelAndView addQuoteGoods(HttpServletRequest request,@RequestParam(value="searchContent",required=false)String searchContent,
			@RequestParam(value="quoteorderId")Integer quoteorderId,@RequestParam(required = false)Integer salesAreaId,HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		try {
			if(StringUtils.isNoneBlank(searchContent)){
				Page page = getPageTag(request,pageNo,pageSize);
				Goods goods = new Goods();
				goods.setCompanyId(user.getCompanyId());
				goods.setSearchContent(searchContent);goods.setUpdater(23);
				Map<String, Object> map = goodsService.queryGoodsListPage(goods,page,session);
				List<Goods> goodsList = (List<Goods>)map.get("list");
				Quoteorder quoteorder = quoteService.getQuoteInfoByKey(quoteorderId);
				//根据客户ID查询客户信息
				TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(quoteorder.getTraderId());
				goodsList = goodsChannelPriceService.getGoodsChannelPriceList(quoteorder.getSalesAreaId() == 0?salesAreaId:quoteorder.getSalesAreaId(), quoteorder.getCustomerNature(),customer.getOwnership(), goodsList);
				mv.addObject("goodsList",goodsList);
				mv.addObject("page", (Page)map.get("page"));
				mv.addObject("searchContent", searchContent);
				
			}
			mv.addObject("quoteorderId", quoteorderId);
		} catch (Exception e) {
			logger.error("addQuoteGoods:", e);
		}
		mv.setViewName("order/quote/add_quote_goods");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存报价产品信息
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:17:49
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveQuoteGoods")
	@SystemControllerLog(operationType = "add",desc = "保存报价产品信息")
	public ResultInfo<?> saveQuoteGoods(HttpServletRequest request,QuoteorderGoods quoteGoods,Attachment ach){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quoteGoods.setCreator(user.getUserId());
			quoteGoods.setAddTime(DateUtil.sysTimeMillis());
			
			quoteGoods.setUpdater(user.getUserId());
			quoteGoods.setModTime(DateUtil.sysTimeMillis());
			
			ach.setAddTime(DateUtil.sysTimeMillis());
			ach.setCreator(user.getUserId());
		}
		return quoteService.saveQuoteGoods(quoteGoods,ach);
	}
	
	/**
	 * <b>Description:</b><br> 产品附件上传
	 * @param request
	 * @param response
	 * @param lwfile
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月18日 下午1:46:37
	 */
	@ResponseBody
	@RequestMapping(value = "/goodsImgUpload")
	@SystemControllerLog(operationType = "edit",desc = "产品附件上传")
	public FileInfo goodsImgUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("lwfile") MultipartFile lwfile) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			String path = "/upload/quote/goods";
			long size = lwfile.getSize();
			if(size > 2*1024*1024){
				return new FileInfo(-1,"图片大小应为2MB以内");
			}
			System.out.println(lwfile.getOriginalFilename());
			String fileName = lwfile.getOriginalFilename();
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			if(!prefix.equals("jpg")){
				return new FileInfo(-1,"请选择jpg格式图片");
			}
			/*if(!FileType.getFileHeader(lwfile.getOriginalFilename()).equals("jpg")){
				return new FileInfo(-1,"请选择jpg格式图片");
			}*/
			return ftpUtilService.uploadFile(lwfile, path,request,"");
			/*Attachment ach = new Attachment();
			ach.setAttachmentType(SysOptionConstant.ID_343);
			ach.setAttachmentFunction(SysOptionConstant.ID_494);
			ach.setName(info.getFileName());
			ach.setDomain(info.getHttpUrl());
			ach.setUri(info.getFilePath());*/
		}else{
			return new FileInfo(-1,"登录用户不能为空");
		}
	}
	
	/**
	 * <b>Description:</b><br> 编辑报价产品信息初始化
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:18:31
	 */
	@ResponseBody
	@RequestMapping(value="/editQuoteGoodsInit")
	public ModelAndView editQuoteGoodsInit(HttpServletRequest request,QuoteorderGoods quoteGoods){
		ModelAndView mv = new ModelAndView();
		try {
			//根据报价产品ID获取对应产品信息
			quoteGoods = quoteService.getQuoteGoodsById(quoteGoods.getQuoteorderGoodsId(),request.getSession());
			Quoteorder quoteorder = quoteService.getQuoteInfoByKey(quoteGoods.getQuoteorderId());
			List<Goods> goodsLists = new ArrayList<>();
			Goods goods = new Goods();
			goods.setGoodsId(quoteGoods.getGoodsId());
			goodsLists.add(goods);
			//根据客户ID查询客户信息
			TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(quoteorder.getTraderId());
			List<Goods> goodsList = goodsChannelPriceService.getGoodsChannelPriceList(quoteorder.getSalesAreaId() == 0?0:quoteorder.getSalesAreaId(), quoteorder.getCustomerNature(),customer.getOwnership(), goodsLists);
			for(Goods g:goodsList){
			    quoteGoods.setChannelPrice(g.getChannelPrice());
			}
			//日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(quoteGoods)));

			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
			Map<String,Object> newSkuInfo = vGoodsService.skuTip(quoteGoods.getGoodsId());
			mv.addObject("newSkuInfo", newSkuInfo);
			//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end
			
			if(quoteGoods!=null && quoteGoods.getIsTemp()==0){//非临时产品
				mv.setViewName("order/quote/edit_quote_formal_goods");
			}else{
				mv.setViewName("order/quote/edit_quote_temp_goods");
			}
			//根据条件查询產品附件表信息
			Attachment ach2 = new Attachment();
			ach2.setAttachmentType(SysOptionConstant.ID_343);
			ach2.setAttachmentFunction(SysOptionConstant.ID_494);
			ach2.setRelatedId(quoteGoods.getQuoteorderGoodsId());
			Attachment ach = quoteService.getQuoteGoodsAttachment(ach2);
			mv.addObject("ach", ach);
			
			mv.addObject("quoteGoods", quoteGoods);
		} catch (Exception e) {
			logger.error("editQuoteGoodsInit:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 编辑保存报价产品信息
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:18:56
	 */
	@ResponseBody
	@RequestMapping(value="/editQuoteGoods")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑报价产品信息")
	public ResultInfo<?> editQuoteGoods(HttpServletRequest request,String beforeParams,QuoteorderGoods quoteGoods,Attachment ach){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quoteGoods.setUpdater(user.getUserId());
			quoteGoods.setModTime(DateUtil.sysTimeMillis());
			
			ach.setAddTime(DateUtil.sysTimeMillis());
			ach.setCreator(user.getUserId());
		}
		//根据报价产品ID获取对应产品信息
		return quoteService.editQuoteGoods(quoteGoods,ach);
	}
	
	/**
	 * <b>Description:</b><br> 删除报价产品信息
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:19:35
	 */
	@ResponseBody
	@RequestMapping(value="/delQuoteGoodsById")
	@SystemControllerLog(operationType = "delete",desc = "删除报价产品信息")
	public ResultInfo<?> delQuoteGoodsById(HttpServletRequest request,QuoteorderGoods quoteGoods){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quoteGoods.setUpdater(user.getUserId());
			quoteGoods.setModTime(DateUtil.sysTimeMillis());
		}
		//根据报价产品ID获取对应产品信息
		return quoteService.delQuoteGoodsById(quoteGoods);
	}
	
	/**
	 * <b>Description:</b><br> 修改报价付款信息
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月6日 下午2:29:13
	 */
	@ResponseBody
	@RequestMapping(value="/editQuoteAmount")
	@SystemControllerLog(operationType = "edit",desc = "修改报价付款信息")
	public ModelAndView editQuoteAmount(HttpServletRequest request,String beforeParams,Quoteorder quote,@RequestParam(value="quoteSource",required=false)String quoteSource){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
		}
		if(quote.getPaymentType() != null && quote.getPaymentType().equals(419)){//419 : 100%预付--其他付款计划：均设置默认值
			BigDecimal bd = new BigDecimal(0.00);
			quote.setAccountPeriodAmount(bd);//账期支付金额
			quote.setPeriodDay(0);//账期天数
			quote.setLogisticsCollection(0);//物流代收0否 1是
			quote.setRetainageAmount(bd);//尾款
			quote.setRetainageAmountMonth(0);//尾款期限(月)
		}
		ResultInfo<?> result = quoteService.editQuoteAmount(quote);
		ModelAndView mv = new ModelAndView();
		if(StringUtils.isNotBlank(quoteSource)){
			mv.addObject("refresh", "false_true_false");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		}else{
			mv.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
		}
		mv.addObject("url","./getQuoteDetail.do?quoteorderId="+quote.getQuoteorderId()+"&viewType=2");
		if(result.getCode()==0){
			return success(mv);
		}else{
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 添加报价沟通记录
	 * @param quote
	 * @param traderCustomer
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 上午10:57:44
	 */
	@ResponseBody
	@RequestMapping(value="/addComrecord")
	public ModelAndView addComrecord(Quoteorder quote,TraderCustomer traderCustomer,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav=new ModelAndView("order/quote/add_communicate");
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(traderCustomer.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.ONE);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);
		// 客户标签
		Tag tag = new Tag();
		tag.setTagType(SysOptionConstant.ID_32);
		tag.setIsRecommend(ErpConst.ONE);
		tag.setCompanyId(user.getCompanyId());

		//默认沟通时间
		Date now = new Date();
		String startTime = DateUtil.DateToString(now,"yyyy-MM-dd HH:mm:ss");
		Date endDate = new Date(now.getTime() + 120000);//当前时间添加2分钟
		String endTime = DateUtil.DateToString(endDate,"yyyy-MM-dd HH:mm:ss");
		mav.addObject("startTime", startTime);mav.addObject("endTime", endTime);
		
		Integer pageNo = 1;
		Integer pageSize = 10;

		Page page = getPageTag(request, pageNo, pageSize);
		Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

		mav.addObject("quote", quote);
		mav.addObject("contactList", contactList);

		// 沟通方式
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_23)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_23);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> communicateList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			mav.addObject("communicateList", communicateList);
//		}
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);
		mav.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mav.addObject("page", (Page) tagMap.get("page"));
		
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存报价沟通记录
	 * @param request
	 * @param communicateRecord
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午1:12:34
	 */
	@ResponseBody
	@RequestMapping(value="/saveCommunicate")
	@SystemControllerLog(operationType = "add",desc = "保存报价沟通记录")
	public ResultInfo<?> saveCommunicate(HttpServletRequest request,CommunicateRecord communicateRecord,HttpSession session){
		Boolean record=false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_245);//报价
		communicateRecord.setRelatedId(communicateRecord.getQuoteorderId());
		try {
			if(null != communicateRecord.getCommunicateRecordId() && communicateRecord.getCommunicateRecordId() > 0){
				record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
			}else{
				record = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
			}
		} catch (Exception e) {
			logger.error("saveCommunicate:", e);
		}
		if(record){
			if (communicateRecord.getQuoteorderId()!=null) {
				//修改报价主表信息(有沟通记录0无 1有)
				Quoteorder quote = new Quoteorder();
				User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
				if(user!=null){
					quote.setUpdater(user.getUserId());
					quote.setModTime(DateUtil.sysTimeMillis());
				}
				quote.setQuoteorderId(communicateRecord.getQuoteorderId());
//			ResultInfo<?> resultInfo = quoteService.editQuoteHaveCommunicate(quote);
				return quoteService.editQuoteHaveCommunicate(quote);
			}
			return new ResultInfo<>(0,"操作成功");
		} else {
			return new ResultInfo<>(-1, "操作失败！");
		}
	}
	
	/**
	 * <b>Description:</b><br> 编辑报价沟通记录
	 * @param communicateRecord
	 * @param quote
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午1:13:02
	 */
	@ResponseBody
	@RequestMapping(value = "/editCommunicate")
	public ModelAndView editCommunicate(CommunicateRecord communicateRecord, Quoteorder quote, HttpServletRequest request,HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("order/quote/edit_communicate");
		try {
			CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
//		communicate.setTraderCustomerId(communicateRecord.getTraderCustomerId());
			communicate.setTraderId(communicateRecord.getTraderId());

			TraderContact traderContact = new TraderContact();
			// 联系人
			traderContact.setTraderId(communicateRecord.getTraderId());
			traderContact.setIsEnable(ErpConst.ONE);
			traderContact.setTraderType(ErpConst.ONE);
			List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

			// 沟通方式
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_23)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_23);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> communicateList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			mv.addObject("communicateList", communicateList);
//		}
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

			mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
			mv.addObject("page", (Page) tagMap.get("page"));
			quote.setQuoteorderId(communicateRecord.getQuoteorderId());
			mv.addObject("quote", quote);
			//日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(quote)));
		} catch (Exception e) {
			logger.error("editCommunicate:", e);
		}
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param request
	 * @param quote
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月15日 上午11:38:32
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/editApplyValidQuoteorder")
	public ResultInfo<?> editApplyValidQuoteorder(HttpServletRequest request, Quoteorder quote,String taskId, HttpSession session) {
	    	ResultInfo<?> res = quoteService.isvalidQuoteOrder(quote);
		if (res.getCode() == -1) {
			return res;
		}
		try {
		    Map<String, Object> variableMap = new HashMap<String, Object>();
		    // 查询当前订单的一些状态
		    Quoteorder quoteorderInfo = quoteService.getQuoteInfoByKey(quote.getQuoteorderId());
		    User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		    Map<String,Object> map = quoteService.getQuoteGoodsByQuoteId(quote.getQuoteorderId(),user.getCompanyId(),session,null,quote.getTraderId());
		    //报价单中产品类型（0未维护,1 只有设备,2 只有试剂,3 又有试剂又有设备）
		    quoteorderInfo.setGoodsType(0);
    			List<Integer> goodsTypeList = new ArrayList<>();
		    List<QuoteorderGoods> quoteGoodsList = (List<QuoteorderGoods>)map.get("quoteGoodsList");
		    for(QuoteorderGoods qg:quoteGoodsList) {
			    if(qg.getGoods() != null ){
				if(qg.getIsDelete() !=null && qg.getIsDelete() ==0 && qg.getGoods().getGoodsType() != null && (qg.getGoods().getGoodsType() == 316 || qg.getGoods().getGoodsType() == 319)){
				    goodsTypeList.add(1);
				}else if(qg.getIsDelete() !=null && qg.getIsDelete() ==0 && qg.getGoods().getGoodsType() != null && (qg.getGoods().getGoodsType() == 317 || qg.getGoods().getGoodsType() == 318)){
				    goodsTypeList.add(2);
				}
			    }
		    }
		    //试剂和设备都有
		    if(!goodsTypeList.isEmpty()) {
    			List<Integer> newList = new ArrayList(new HashSet(goodsTypeList)); 
	    			if(newList.size() == 2) {
	    				quoteorderInfo.setGoodsType(3);
	    			}
    			
    			if(newList.size() == 1) {
    				
    				if(newList.get(0) == 1) {
    					//只有设备
    					quoteorderInfo.setGoodsType(1);
    				}else if(newList.get(0) == 2){
    					//只有试剂
    					quoteorderInfo.setGoodsType(2);
    				}
    				
    			}
    			}
		    //开始生成流程(如果没有taskId表示新流程需要生成)
		    // 设置当前审核人(订单归属人)
		    User userInfo = userService.getUserByTraderId(quoteorderInfo.getTraderId(), 1);// 1客户，2供应商
		    Long userTime = userInfo.getAddTime();
		    Long nowTime = DateUtil.sysTimeMillis();
		    Integer day = (int) ((nowTime-userTime)/(3600*24*1000));
		    variableMap.put("day", day);
		    quoteorderInfo.setOptUserName(user==null?"":userInfo.getUsername());
		    if(taskId.equals("0")){
		    /**
		     * 流程名称quoteorder+variables+businessKey
		     */
		    
		    variableMap.put("quoteorder", quoteorderInfo);
		    variableMap.put("currentAssinee", quoteorderInfo.getOptUserName());
		    variableMap.put("processDefinitionKey","quoteVerify");
		    variableMap.put("businessKey","quoteVerify_" + quoteorderInfo.getQuoteorderId());
		    variableMap.put("relateTableKey",quoteorderInfo.getQuoteorderId());
		    variableMap.put("relateTable", "T_QUOTEORDER");
		    actionProcdefService.createProcessInstance(request,"quoteVerify","quoteVerify_" + quoteorderInfo.getQuoteorderId(),variableMap);
		    }
		    //默认申请人通过
		    //根据BusinessKey获取生成的审核实例
		    Map<String, Object> data = new HashMap<String, Object>();
		    Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "quoteVerify_"+ quoteorderInfo.getQuoteorderId());
		   
		    data.put("quoteorderId", quote.getQuoteorderId());
		    if(historicInfo.get("endStatus") != "审核完成"){
			Task taskInfo = (Task) historicInfo.get("taskInfo");
			taskId = taskInfo.getId();
			Authentication.setAuthenticatedUserId(user.getUsername());
			Map<String, Object> variables = new HashMap<String, Object>();
        		//设置审核完成监听器回写参数
        		variables.put("tableName", "T_QUOTEORDER");
        		variables.put("id", "QUOTEORDER_ID");
        		variables.put("idValue", quote.getQuoteorderId());
        		variables.put("key", "VALID_STATUS");
        		variables.put("value", 1);
        		variables.put("key1", "BUSSINESS_CHANCE_ID");
        		variables.put("value1", quoteorderInfo.getBussinessChanceId());
        		//回写数据的表在db中
        		variables.put("db", 2);
			//默认审批通过
        		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,"",quoteorderInfo.getOptUserName(),variables);
			//如果未结束添加审核对应主表的审核状态
        		if(!complementStatus.getData().equals("endEvent")){
        		    verifiesRecordService.saveVerifiesInfo(taskId,0);
        		}
			Integer status = null;
			// 查询当前订单的一些状态
			Quoteorder quoteorderInfoNew = quoteService.getQuoteInfoByKey(quote.getQuoteorderId());
			if(quoteorderInfoNew.getValidStatus() == 1){
			    status = 1;
			}else{
			    status = 0;
			}
			return new ResultInfo(0, "操作成功",status,data);
		    }
			return new ResultInfo(0, "操作成功",1,data);
		} catch (Exception e) {
			logger.error("editApplyValidQuoteorder:", e);
		    return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
		
	}
	
	/**
	 * <b>Description:</b><br> 修改报价是否生效
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午1:19:21
	 */
	@ResponseBody
	@RequestMapping(value = "/editQuoteValIdSave")
	@SystemControllerLog(operationType = "edit",desc = "修改报价是否生效保存")
	public ModelAndView editQuoteValIdSave(HttpServletRequest request,String beforeParams,Quoteorder quote){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
			
			quote.setValidTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> result = quoteService.editQuoteValIdSave(quote);
		ModelAndView mv = new ModelAndView();
		if(result.getCode()==0){
			mv.addObject("url","./getQuoteDetail.do?quoteorderId="+quote.getQuoteorderId()+"&viewType=3");
			return success(mv);
		}else{
			mv.addObject("message", result.getMessage());
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 撤销报价生效状态
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午5:05:45
	 */
	@ResponseBody
	@RequestMapping(value = "/revokeValIdStatus")
	@SystemControllerLog(operationType = "edit",desc = "撤销报价生效状态")
	public ModelAndView revokeValIdStatus(HttpServletRequest request,String beforeParams,Quoteorder quote){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
			
			quote.setValidTime(DateUtil.sysTimeMillis());
		}
		BussinessChance bussinessChance = new BussinessChance();
		bussinessChance.setBussinessChanceId(quote.getBussinessChanceId());
		//撤销生效更改商机报价状态，改为报价中
		bussinessChance.setStatus(1);
		ResultInfo<?> res = bussinessChanceService.editBussinessChance(bussinessChance);
		ResultInfo<?> result = quoteService.editQuoteValIdSave(quote);
		ModelAndView mv = new ModelAndView();
		mv.addObject("url","./getQuoteDetail.do?quoteorderId="+quote.getQuoteorderId()+"&viewType=2");
		if(result.getCode()==0){
			return success(mv);
		}else{
			return fail(mv);
		}
	}
	
	/**
	 * <b>Description:</b><br> 保存报价咨询记录
	 * @param request
	 * @param quoteConsult
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午2:30:27
	 */
	@ResponseBody
	@RequestMapping(value = "/addQuoteConsultSave")
	@SystemControllerLog(operationType = "edit",desc = "保存报价咨询记录")
	public ResultInfo<?> addQuoteConsultSave(HttpServletRequest request,String beforeParams,QuoteorderConsult quoteConsult){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quoteConsult.setCreator(user.getUserId());
			quoteConsult.setAddTime(DateUtil.sysTimeMillis());
		}
		return quoteService.addQuoteConsultSave(quoteConsult,user);
	}
	
	/**
	 * <b>Description:</b><br> 失单原因填写初始化
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 上午11:05:37
	 */
	@ResponseBody
	@RequestMapping(value="/reasonOfLostOrder")
	public ModelAndView reasonOfLostOrder(HttpServletRequest request,Quoteorder quote){
		ModelAndView mv = new ModelAndView();
		try {
			//根据报价产品ID获取对应产品信息
			mv.setViewName("order/quote/lose_order_reason");
			mv.addObject("quote", quote);
			//日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(quote)));
		} catch (Exception e) {
			logger.error("reasonOfLostOrder:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 修改报价为失单状态（备注）
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 上午11:04:36
	 */
	@ResponseBody
	@RequestMapping(value = "/editLoseOrderStatus")
	@SystemControllerLog(operationType = "edit",desc = "修改报价为失单状态（备注）")
	public ResultInfo<?> editLoseOrderStatus(HttpServletRequest request,String beforeParams,Quoteorder quote){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
			
			quote.setFollowOrderTime(DateUtil.sysTimeMillis());
		}
		return quoteService.editLoseOrderStatus(quote);
	}
	
	/**
	 * <b>Description:</b><br> 报价咨询列表（包括销售人员）
	 * @param request
	 * @param quote
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 下午3:28:33
	 */
	@ResponseBody
	@RequestMapping(value="getQuoteConsultListPage")
	public ModelAndView getQuoteConsultListPage(HttpServletRequest request,@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize,QuoteorderConsult quoteConsult,@RequestParam(required = false, value="searchBeginTime") String searchBeginTime,
			@RequestParam(required = false, value="searchEndTime") String searchEndTime,
			HttpSession session){
		User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		
		Page page = getPageTag(request,pageNo,pageSize);
		
		try {
			mv.addObject("searchBeginTime", searchBeginTime);mv.addObject("searchEndTime", searchEndTime);
			
			mv.addObject("quoteConsult",quoteConsult);
			
			if(StringUtils.isNotBlank(searchBeginTime)){
				quoteConsult.setBeginTime(DateUtil.convertLong(searchBeginTime + " 00:00:00",""));
			}
			if(StringUtils.isNotBlank(searchEndTime)){
				quoteConsult.setEndTime(DateUtil.convertLong(searchEndTime + " 23:59:59",""));
			}
			
			quoteConsult.setCompanyId(sessUser.getCompanyId());
			Map<String, Object> map = quoteService.getQuoteConsultListPage(quoteConsult,page,session);
			if(map != null && !map.isEmpty()){
				List<QuoteorderConsult> quoteConsultList = (List<QuoteorderConsult>)map.get("quoteConsultList");
				User user = null;
				for(int i=0;i<quoteConsultList.size();i++){
					//销售人员
					user = userService.getUserById(quoteConsultList.get(i).getSaleUserId());
					quoteConsultList.get(i).setSaleUserName(user==null?"":user.getUsername());
				}
				mv.addObject("quoteConsultList",(List<QuoteorderConsult>)map.get("quoteConsultList"));
				
				mv.addObject("replyUserList",(List<User>)map.get("replyUserList"));
				
				//销售人员选择条件
				List<User> quoteConsultUserList = userService.getUserByUserIds((List<Integer>)map.get("quoteConsultUserList"));
				mv.addObject("quoteConsultUserList",quoteConsultUserList);
			}
			
			//产品部门--选择条件
			List<Organization> productOrgList = orgService.getOrgListByPositType(SysOptionConstant.ID_311,sessUser.getCompanyId());
			mv.addObject("productOrgList", productOrgList);
			//产品负责人
			//先从会话中查看有没有 产品负责人，没有再从数据库查 ，以减少数据库查询次数。
			List<User> productUserList = (List<User>)request.getSession().getAttribute("productUserList");
			if(null == productUserList || productUserList.size()==0){
				productUserList = userService.selectAllAssignUser();
				request.getSession().setAttribute("productUserList", productUserList);
			}
			mv.addObject("productUserList", productUserList);
			
			mv.addObject("page", (Page)map.get("page"));
		} catch (Exception e) {
			logger.error("getQuoteConsultListPage:", e);
		}
		
		mv.setViewName("order/quote/index_consult");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 验证报价单中产品货期和报价是否为空
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月25日 下午5:23:22
	 */
	@ResponseBody
	@RequestMapping(value="getQuoteGoodsPriceAndCycle")
	public ResultInfo<?> getQuoteGoodsPriceAndCycle(HttpServletRequest request,Quoteorder quote){
		return quoteService.getQuoteGoodsPriceAndCycle(quote);
	}
	
	/**
	 * <b>Description:</b><br> 咨询答复内容保存
	 * @param request
	 * @param quoteConsult
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月11日 下午2:15:06
	 */
	@ResponseBody
	@FormToken(remove=true)
	@RequestMapping(value="saveReplyQuoteConsult")
	@SystemControllerLog(operationType = "edit",desc = "咨询答复内容保存")
	public ResultInfo<?> saveReplyQuoteConsult(HttpServletRequest request,String beforeParams,QuoteorderConsult quoteConsult,Quoteorder quote,
							@RequestParam(required = false, value="referenceCostPriceArr") String referenceCostPriceArr,
							@RequestParam(required = false, value="referencePriceArr") String referencePriceArr,
							@RequestParam(required = false, value="referenceDeliveryCycleArr") String referenceDeliveryCycleArr,
							@RequestParam(required = false, value="reportStatusArr") String reportStatusArr,
							@RequestParam(required = false, value="reportCommentsArr") String reportCommentsArr,
							@RequestParam(required = false, value="registrationNumberArr") String registrationNumberArr,
							@RequestParam(required = false, value="supplierNameArr") String supplierNameArr,
							@RequestParam(required = false, value="quoteorderConsultIdArr") String quoteorderConsultIdArr){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quoteConsult.setCreator(user.getUserId());
			quoteConsult.setAddTime(DateUtil.sysTimeMillis());
			
			quoteConsult.setType(2);//采购回复
		}
		List<Double> referenceCostPriceList = JSON.parseArray(request.getParameter("referenceCostPriceArr").toString(),Double.class);
		List<Double> referencePriceList = JSON.parseArray(request.getParameter("referencePriceArr").toString(),Double.class);
		List<String> referenceDeliveryCycleList = JSON.parseArray(request.getParameter("referenceDeliveryCycleArr").toString(),String.class);
		List<Integer> reportStatusList = JSON.parseArray(request.getParameter("reportStatusArr").toString(),Integer.class);
		List<String> reportCommentsList = JSON.parseArray(request.getParameter("reportCommentsArr").toString(),String.class);
		List<String> registrationNumberList = JSON.parseArray(request.getParameter("registrationNumberArr").toString(),String.class);
		List<String> supplierNameList = JSON.parseArray(request.getParameter("supplierNameArr").toString(),String.class);
		
		List<Integer> quoteorderConsultIdList = JSON.parseArray(request.getParameter("quoteorderConsultIdArr").toString(),Integer.class);
		
		Map<String , Map<String,Object>> consult_map = new HashMap<>();
		Map<String,Object> map = null;
		for(int i=0;i<quoteorderConsultIdList.size();i++){
			map = new HashMap<String,Object>();
			map.put("REFERENCE_COST_PRICE", referenceCostPriceList.get(i)==null?0:referenceCostPriceList.get(i));
			map.put("REFERENCE_PRICE", referencePriceList.get(i)==null?0:referencePriceList.get(i));
			map.put("REFERENCE_DELIVERY_CYCLE", referenceDeliveryCycleList.get(i));
			map.put("REPORT_STATUS", reportStatusList.get(i));
			map.put("REPORT_COMMENTS", reportCommentsList.get(i));
			map.put("REGISTRATION_NUMBER", registrationNumberList.get(i));
			map.put("SUPPLIER_NAME", supplierNameList.get(i));
			map.put("LAST_REFERENCE_USER", user.getUserId());
			consult_map.put(quoteorderConsultIdList.get(i).toString(), map);
		}
		quoteConsult.setConsultMap(consult_map);

		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
		}
		quoteService.saveReplyQuoteConsult(quoteConsult);

		
		 return quoteService.editConsultStatus(quote);
	}
	
	/**
	 * <b>Description:</b><br> 修改报价咨询回复状态保存
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月11日 下午2:59:27
	 */
/*	@ResponseBody
	@RequestMapping(value="editConsultStatus")
	@SystemControllerLog(operationType = "edit",desc = "修改报价咨询回复状态保存")
	public ResultInfo<?> editConsultStatus(HttpServletRequest request,String beforeParams,Quoteorder quote){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			quote.setUpdater(user.getUserId());
			quote.setModTime(DateUtil.sysTimeMillis());
		}
		return quoteService.editConsultStatus(quote);
	}*/
	
	/**
	 * <b>Description:</b><br> 验证报价中新添加的产品是否重复
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2017年9月11日 下午6:53:39
	 */
	@ResponseBody
	@RequestMapping(value="vailQuoteGoodsRepeat")
	public ResultInfo<?> vailQuoteGoodsRepeat(HttpServletRequest request,QuoteorderGoods quoteGoods){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			return quoteService.vailQuoteGoodsRepeat(quoteGoods);
		}
		return new ResultInfo<>(-1, "用户登录超时");
	}
	
	
	
	/**
	 * <b>Description:</b><br> 报价信息列表导出
	 * @param request
	 * @param quote
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月24日 下午6:46:10
	 */
	@RequestMapping(value="quoteExport")
	public void quoteExport(HttpServletRequest request,Quoteorder quote,HttpSession session,HttpServletResponse response,
			@RequestParam(required = false, value="beginTime") String beginTime,
			@RequestParam(required = false, value="endTime") String endTime){
		
		OutputStream out = null;SXSSFWorkbook wb = null;
		try {
			//-------------------查询条件----------------------------------------

			if(StringUtils.isNoneBlank(beginTime)){
				quote.setBeginDate(DateUtil.convertLong(beginTime + " 00:00:00",""));
			}
			if(StringUtils.isNoneBlank(endTime)){
				quote.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
			}
			//查询沟通记录
			if(quote.getTimeType()!=null && quote.getTimeType()==2){
				if(quote.getBeginDate()!=null || quote.getEndDate()!=null){//若都为空，则查询全部报价列表，不需要查询沟通记录
					//根据时间获取沟通记录中外键ID
					quote.setKeyIds(quoteService.getCommunicateRecordByDate(quote.getBeginDate(),quote.getEndDate(), SysOptionConstant.ID_244 + "," + SysOptionConstant.ID_245));
				}
			}
			
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			//List<User> userList = userService.getNextAllUserList(user.getUserId(), user.getCompanyId(), true, user.getPositLevel(), SysOptionConstant.ID_310);
			
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> userList = userService.getMyUserList(user, positionType, false);
			
			if(quote.getOptUserId()==null){
				//销售人员所属客户（即当前报价单操作人员）
				List<Integer> traderIdList = userService.getTraderIdListByUserList(userList, ErpConst.ONE + "," +ErpConst.TWO);
				quote.setTraderIdList(traderIdList);
			}else{
				//销售人员所属客户（即当前报价单操作人员）
				List<Integer> traderIdList = userService.getTraderIdListByUserId(quote.getOptUserId(), 1);//1客户，2供应商
				quote.setTraderIdList(traderIdList);
			}
			//------------------------------------------------------------------------------------
			

			
//			User user = (User)session.getAttribute(Consts.SESSION_USER);
			
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setContentType("multipart/form-data");
			String fileName = System.currentTimeMillis() + ".xlsx";
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

			
			int sheetNum = 1;// 工作薄sheet编号
			int currentRowCount = 1;// 当前的行号
			int bodyRowCount = 1;// 正文内容行号

			out = response.getOutputStream();
			wb = new SXSSFWorkbook(Consts.EXP_TEM_NUM);//内存中保留 x 条数据，以免内存溢出，其余写入 硬盘
			Sheet sh = wb.createSheet("工作簿" + sheetNum);
			writeTitleContent(sh);
			
			Row row_value = null;
			Cell cel_value = null;

			// List<Action> list = reportService.selectList();
			List<Quoteorder> list = new ArrayList<>();

			
			CommunicateRecord cr = null;
			// ------------------定义表头----------------------------------------
			int page_size = Consts.EXP_SEARCH_SIZE;// 数据库中每次查询条数
			int list_count = quoteService.getQuoteListCount(quote);//查询报价记录数
			int export_times = list_count % page_size > 0 ? list_count / page_size + 1 : list_count / page_size;
			for (int i = 0; i < export_times; i++) {
				list = quoteService.getQuoteListSize(quote,page_size * i, page_size);//一次导出开始和结束记录行
				int len = list.size() < page_size ? list.size() : page_size;
				for (int j = 0; j < len; j++) {
					// Row row_value = sh.createRow(j * page_size + i + 1);
					row_value = sh.createRow(bodyRowCount);

					cel_value = row_value.createCell(0);//报价单号
					/*if (list.get(j).getQuoteorderNo() instanceof Integer) {// 判断对象类型
						cel_value.setCellType(XSSFCell.CELL_TYPE_NUMERIC);// 设置文本框类型
					}*/
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getQuoteorderNo());
					
					cel_value = row_value.createCell(1);//创建时间
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					String addTime = DateUtil.convertString(list.get(j).getAddTime(), "yyyy-MM-dd HH:mm:ss");
					cel_value.setCellValue(addTime);

					
					cel_value = row_value.createCell(2);//客户名称
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getTraderName());
					
					cel_value = row_value.createCell(3);//客户类型
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getCustomerTypeStr());
					/*CellRangeAddress cra = new CellRangeAddress(j+1, (short)(j+1), 3, (short)4);//合并单元格：参数：起始行号，终止行号， 起始列号，终止列号
					sh.addMergedRegion(cra);*/
					
					cel_value = row_value.createCell(4);//客户性质
					cel_value.setCellValue(list.get(j).getCustomerNatureStr());
					
					cel_value = row_value.createCell(5);//客户地区
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getArea());
					
					cel_value = row_value.createCell(6);//新/老客户
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getIsNewCustomer()==0?"否":"是");
					
					cel_value = row_value.createCell(7);//客户等级
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getCustomerLevel());
					
					cel_value = row_value.createCell(8);//联系人
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getTraderContactName());
					
					cel_value = row_value.createCell(9);//座机
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getTelephone());
					
					cel_value = row_value.createCell(10);//手机号
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getMobile());
					
					cel_value = row_value.createCell(11);//联系地址
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getAddress());
					
					cel_value = row_value.createCell(12);//销售区域
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getSalesArea());
					
					cel_value = row_value.createCell(13);//终端名称
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getTerminalTraderName());
					
					cel_value = row_value.createCell(14);//终端类型
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getTerminalTraderTypeStr());
					
					cel_value = row_value.createCell(15);//报价金额
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(String.format("%.2f", list.get(j).getTotalAmount().doubleValue()));
					
					cel_value = row_value.createCell(16);//销售部门
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					//销售部门
					cel_value.setCellValue(orgService.getOrgNameById(list.get(i).getOrgId()));
					
					cel_value = row_value.createCell(17);//销售人员
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					//销售人员
					user = userService.getUserById(list.get(i).getUserId());
					cel_value.setCellValue(user==null?"":user.getUsername());
					
					cel_value = row_value.createCell(18);//沟通次数
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					//沟通记录次数(参数使用List，多个参数，使方法能复用)
					cr = new CommunicateRecord();
					if(list.get(i).getQuoteorderId()!=null){
						cr.setQuoteorderId(list.get(i).getQuoteorderId());
					}
					if(list.get(i).getBussinessChanceId()!=null){
						cr.setBussinessChanceId(list.get(i).getBussinessChanceId());
					}
					//沟通类型为商机和报价
					cel_value.setCellValue(quoteService.getCommunicateRecordCount(cr,SysOptionConstant.ID_244,SysOptionConstant.ID_245));

					
					cel_value = row_value.createCell(19);//报价有效期
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getPeriod());
					
					cel_value = row_value.createCell(20);//审核状态
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue("--");
					
					cel_value = row_value.createCell(21);//生效状态
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getValidStatus()==0?"否":"是");
					
					cel_value = row_value.createCell(22);//咨询答复状态
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getConsultStatus()==0?"":(list.get(j).getConsultStatus()==1?"未处理":(list.get(j).getConsultStatus()==2?"处理中":"已处理")));
					
					cel_value = row_value.createCell(23);//跟单状态
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getFollowOrderStatus()==0?"跟单中":(list.get(j).getFollowOrderStatus()==1?"成单":"失单"));
					
					cel_value = row_value.createCell(24);//失单原因
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getFollowOrderStatusComments());
					
					cel_value = row_value.createCell(25);//转化订单号
					cel_value.setCellType(XSSFCell.CELL_TYPE_STRING);
					cel_value.setCellValue(list.get(j).getSaleorderNo());
					

					/*例如：每个工作簿5条，总共10条，会生成3个工作簿，因为第二页最后一次循环时，当页是5条，下面公式成立*/
					if (currentRowCount % Consts.EXP_PRE_NUM == 0) {// 每个工作薄显示50000条数据
						sh = null;
						sheetNum++;// 工作薄编号递增1
						bodyRowCount = 0;// 正文内容行号置位为0
						sh = wb.createSheet("工作簿" + sheetNum);// 创建一个新的工作薄
						// setSheetColumn(sh);//设置工作薄列宽
						writeTitleContent(sh);// 写入标题
					}
					currentRowCount++;// 当前行号递增1
					bodyRowCount++;
				}
				list.clear();
			}

			wb.write(out);
			out.close();
			wb.dispose();
		
		} catch (Exception e) {
			logger.error("quoteExport:", e);
		}finally {
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
			if(wb != null){
				wb.dispose();
			}
		}
	}
	
	public void writeTitleContent(Sheet sh) {
		Row row = sh.createRow(0);
		Cell cel = null;
//		CellRangeAddress cra = null;
		// --------------------------------------------------
		cel = row.createCell(0);
		sh.setColumnWidth(0, 3000);// 设置列宽度
		cel.setCellValue("报价单号");
		
		cel = row.createCell(1);
		sh.setColumnWidth(1, 5000);// 设置列宽度
		cel.setCellValue("创建时间");
		
		cel = row.createCell(2);
		sh.setColumnWidth(2, 6000);// 设置列宽度
		cel.setCellValue("客户名称");
		
		cel = row.createCell(3);
		cel.setCellValue("客户类型");
//		cra = new CellRangeAddress(0, (short)0, 3, (short)4);//合并单元格：参数：起始行号，终止行号， 起始列号，终止列号
//		sh.addMergedRegion(cra);
		
		cel = row.createCell(4);
		cel.setCellValue("客户性质");
//		cra = new CellRangeAddress(0, (short)0, 5, (short)6);//合并单元格：参数：起始行号，终止行号， 起始列号，终止列号
//		sh.addMergedRegion(cra);
		
		cel = row.createCell(5);
		cel.setCellValue("客户地区");
		
		cel = row.createCell(6);
		cel.setCellValue("新/老客户");
		
		cel = row.createCell(7);
		cel.setCellValue("客户等级");
		
		cel = row.createCell(8);
		cel.setCellValue("联系人");
		
		cel = row.createCell(9);
		sh.setColumnWidth(9, 3000);// 设置列宽度
		cel.setCellValue("座机");
		
		cel = row.createCell(10);
		sh.setColumnWidth(10, 3200);// 设置列宽度
		cel.setCellValue("手机号");
		
		cel = row.createCell(11);
		sh.setColumnWidth(11, 6500);// 设置列宽度
		cel.setCellValue("联系地址");
		
		cel = row.createCell(12);
		sh.setColumnWidth(12, 5500);// 设置列宽度
		cel.setCellValue("销售区域");
		
		cel = row.createCell(13);
		sh.setColumnWidth(13, 6000);// 设置列宽度
		cel.setCellValue("终端名称");
		
		cel = row.createCell(14);
		cel.setCellValue("终端类型");
		
		cel = row.createCell(15);
		cel.setCellValue("报价金额");
		
		cel = row.createCell(16);
		sh.setColumnWidth(16, 5500);// 设置列宽度
		cel.setCellValue("销售部门");
		
		cel = row.createCell(17);
		cel.setCellValue("销售人员");
		
		cel = row.createCell(18);
		cel.setCellValue("沟通次数");
		
		cel = row.createCell(19);
		sh.setColumnWidth(19, 3000);// 设置列宽度
		cel.setCellValue("报价有效期");
		
		cel = row.createCell(20);
		cel.setCellValue("审核状态");
		
		cel = row.createCell(21);
		cel.setCellValue("生效状态");
		
		cel = row.createCell(22);
		cel.setCellValue("咨询答复");
		
		cel = row.createCell(23);
		cel.setCellValue("跟单状态");
		
		cel = row.createCell(24);
		sh.setColumnWidth(24, 5000);// 设置列宽度
		cel.setCellValue("失单原因");
		
		cel = row.createCell(25);
		sh.setColumnWidth(25, 3000);// 设置列宽度
		cel.setCellValue("转化订单号");
		
	}
	/**
	 * 
	 * <b>Description:</b><br>
	 * 报价单关闭页面
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/closeQuote")
	public ModelAndView closeQuote(HttpSession session,Integer quoteorderId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("quoteorderId", quoteorderId);
		mv.setViewName("order/quote/closeQuote");
		return mv;
	}
	/**
	 * 
	 * <b>Description:</b><br> 报价关闭审核申请
	 * @param request
	 * @param quote
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月15日 上午11:38:32
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/closeQuoteVerify")
	public ResultInfo<?> closeQuoteVerify(HttpServletRequest request, Quoteorder quote,String reason, HttpSession session) {
		try {
		    Map<String, Object> variableMap = new HashMap<String, Object>();
		    // 查询当前订单的一些状态
		    Quoteorder quoteorderInfo = quoteService.getQuoteInfoByKey(quote.getQuoteorderId());
		    User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		    //开始生成流程(如果没有taskId表示新流程需要生成)
		    // 设置当前审核人(订单归属人)
		    User userInfo = userService.getUserByTraderId(quoteorderInfo.getTraderId(), 1);// 1客户，2供应商
		    variableMap.put("quoteorder", quoteorderInfo);
		    quoteorderInfo.setOptUserName(user==null?"":userInfo.getUsername());
		    /**
		     * 流程名称quoteorder+variables+businessKey
		     */
		    variableMap.put("quoteorder", quoteorderInfo);
		    variableMap.put("currentAssinee", quoteorderInfo.getOptUserName());
		    variableMap.put("processDefinitionKey","closeQuoteorderVerify");
		    variableMap.put("businessKey","closeQuoteorderVerify_" + quoteorderInfo.getQuoteorderId());
		    variableMap.put("relateTableKey",quoteorderInfo.getQuoteorderId());
		    variableMap.put("relateTable", "T_QUOTEORDER");
		    
		    //设置审核完成监听器回写参数
		    variableMap.put("tableName", "T_QUOTEORDER");
		    variableMap.put("id", "QUOTEORDER_ID");
		    variableMap.put("idValue", quoteorderInfo.getQuoteorderId());
		    variableMap.put("key", "FOLLOW_ORDER_STATUS");
		    variableMap.put("orderId", quoteorderInfo.getQuoteorderId());
		    variableMap.put("reason", reason);
		    //关闭
		    variableMap.put("value", 2);
	    	    //回写数据的表在db中
		    variableMap.put("db", 2);
		    actionProcdefService.createProcessInstance(request,"closeQuoteorderVerify","closeQuoteorderVerify_" + quoteorderInfo.getQuoteorderId(),variableMap);
		    Map<String, Object> data = new HashMap<String, Object>();
		    data.put("quoteorderId", quote.getQuoteorderId());
		    //默认申请人通过
		    //根据BusinessKey获取生成的审核实例
		    	Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "closeQuoteorderVerify_"+ quoteorderInfo.getQuoteorderId());
		    	if(historicInfo.get("endStatus") != "审核完成"){
		    	Task taskInfo = (Task) historicInfo.get("taskInfo");
    			String taskId = taskInfo.getId();
    			Authentication.setAuthenticatedUserId(user.getUsername());
    			Map<String, Object> variables = new HashMap<String, Object>();
    			variables.put("reason", reason);
    			//默认审批通过
    			ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,"",quoteorderInfo.getOptUserName(),variables);
    			//如果未结束添加审核对应主表的审核状态
        		if(!complementStatus.getData().equals("endEvent")){
        		    verifiesRecordService.saveVerifiesInfo(taskId,0);
        		}
			// 查询当前订单的一些状态
			Quoteorder quoteorderInfoNew = quoteService.getQuoteInfoByKey(quote.getQuoteorderId());
			Integer status = null;
			if(quoteorderInfoNew.getValidStatus() == 1){
			     status = 1;
			}else{
			     status = 0;
			}
				return new ResultInfo(0, "操作成功",status,data);
		    	}
		    	return new ResultInfo(0, "操作成功",1,data);
		} catch (Exception e) {
			logger.error("closeQuoteVerify:", e);
		    return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
		
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 报价并行审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementTaskParallel")
	public ResultInfo<?> complementTaskParallel(HttpServletRequest request,Integer quoteorderId, String taskId, String comment, Boolean pass,
			HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("updater", user.getUserId());
		// 审批操作
		try {
			Integer statusInfo = 0;
			if (pass) {
				// 如果审核通过
				statusInfo = 0;
			} else {
				// 如果审核不通过
				statusInfo = 2;
				verifiesRecordService.saveVerifiesInfo(taskId, statusInfo);
			}
			ResultInfo<?> complementStatus = null;
			if (pass) {
				TaskService taskService = processEngine.getTaskService();
				HistoryService historyService = processEngine.getHistoryService(); // 任务相关service
				String processInstanceId = null;
				HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
				processInstanceId = historicTaskInstance.getProcessInstanceId();
//				HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
//						.processInstanceId(processInstanceId).singleResult();
//				String businessKey = historicProcessInstance.getBusinessKey();
				List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
				// 把list转为Map
				Map<String, Object> variablesMap = new HashMap<String, Object>();
				;
				for (HistoricVariableInstance hvi : historicVariableInstanceList) {
					variablesMap.put(hvi.getVariableName(), hvi.getValue());
				}
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
							complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variables);
						}
					} else {
						complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variables);
					}
				} else {
					String verifyUser = (String) variablesMap.get("verifyUserList") + "," + user.getUsername();
					List<String> verifyUserList = Arrays.asList(verifyUser.split(","));
					verifyUserList = new ArrayList(new HashSet(verifyUserList));
					verifyUser = StringUtils.join(verifyUserList.toArray(), ",");
					// 已经审核的人存入全局变量里
					taskService.setVariable(taskId, "verifyUserList", verifyUser);
					if (!candidateUserList.isEmpty()) {
						List<String> candidateUsers = new ArrayList<>();
						for (IdentityLink il : candidateUserList) {
							candidateUsers.add(il.getUserId());
						}
						String candidateUser = StringUtils.join(candidateUsers.toArray(), ",");
						if (candidateUser.equals(verifyUser)) {
							complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variables);
						}
					} else {
						complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variables);
					}
				}
			} else {
				complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variables);
			}
			// 如果未结束添加审核对应主表的审核状态
			if (complementStatus != null && !complementStatus.getData().equals("endEvent")) {
				verifiesRecordService.saveVerifiesInfo(taskId, statusInfo);
			}
			Quoteorder quoteInfo = quoteService.getQuoteInfoByKey(quoteorderId);
			Integer status = null;
			Map<String, Object> data = new HashMap<String, Object>();
			if (quoteInfo.getValidStatus() == 1) {
				status = 1;
				data.put("quoteorderId", quoteorderId);
			} else {
				status = 0;
			}
			return new ResultInfo(0, "操作成功", status, data);
		} catch (Exception e) {
			logger.error("complementTaskParallel:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}
	/**
	 * 
	 * <b>Description:</b><br>
	 * 报价单审核页面
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session,Integer quoteorderId, String taskId, Boolean pass,Integer type) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.addObject("quoteorderId", quoteorderId);
		mv.addObject("type", type);
		mv.setViewName("order/quote/complement");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 报价单审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	public ResultInfo<?> complementTask(HttpServletRequest request,Integer quoteorderId, String taskId, String comment, Boolean pass,
			HttpSession session) {
		logger.info("================报价单完成节点开始===taskId:"+taskId+"===quoteorderId:"+quoteorderId+"===");
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("updater",user.getUserId());
		//审批操作
		try {
		    	Integer statusInfo = 0;
			if(pass){
			    //如果审核通过
			     statusInfo = 0;
			}else{
			    //如果审核不通过
			    statusInfo = 2;
			    verifiesRecordService.saveVerifiesInfo(taskId,statusInfo);
			}
			
		    ResultInfo<?> complementStatus= actionProcdefService.complementTask(request,taskId,comment,user.getUsername(),variables);
		    //如果未结束添加审核对应主表的审核状态
	    	    if(!complementStatus.getData().equals("endEvent")){
	    		verifiesRecordService.saveVerifiesInfo(taskId,statusInfo);
	    	    }
		    if(quoteorderId != null && quoteorderId != 0){
		    Quoteorder quoteInfo = quoteService.getQuoteInfoByKey(quoteorderId);
			Integer status = null;
			Map<String, Object> data = new HashMap<String, Object>();
			if(quoteInfo.getValidStatus() == 1){
			     status = 1;
			     data.put("quoteorderId", quoteorderId);
			}else{
			     status = 0;
			}
			logger.info("================报价单完成节点结束===taskId:"+taskId+"===quoteorderId:"+quoteorderId+"===");
			return new ResultInfo(0, "操作成功",status,data);
		    }else{
			return new ResultInfo(0, "操作成功");
		    }
		} catch (Exception e) {
		    logger.error("quote complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}
	/**
	 * 
	 * <b>Description:</b><br> 打印报价单
	 * @param request
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年12月13日 上午9:12:08
	 */
	@ResponseBody
	@RequestMapping(value = "/printOrder")
	public ModelAndView printOrder(HttpServletRequest request,Quoteorder quote,HttpSession hs) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		quote.setCompanyId(user.getCompanyId());
		QuoteorderVo quoteVo = quoteService.getPrintInfo(quote);

		//归属人员信息
		UserDetail detail = userDetailMapper.getUserDetail(quoteVo.getUserId());
		String username = userService.getUserById(quoteVo.getUserId()).getUsername();
		
		user = userService.getUserByTraderId(quoteVo.getTraderId(), ErpConst.ONE);
		UserDetail detailUser = userDetailMapper.getUserDetail(user.getUserId());
		
		//报价单下的产品
		Map<String,Object> map = quoteService.getQuoteGoodsByQuoteId(quote.getQuoteorderId(),user.getCompanyId(),hs,null,quote.getTraderId());
		mv.addObject("quoteGoodsList", (List<QuoteorderGoods>)map.get("quoteGoodsList"));
		
		Long currTime = DateUtil.sysTimeMillis();
		mv.addObject("currTime",DateUtil.convertString(currTime, "YYYY-MM-dd "));
		
		// 发票类型
		List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
		mv.addObject("invoiceTypes", invoiceTypes);
		
		//运费类型
		List<SysOptionDefinition> yfTypes = getSysOptionDefinitionList(469);
		mv.addObject("yfTypes", yfTypes);
		
		//获取公司信息
		Company company = companyService.getCompanyByCompangId(user.getCompanyId());
		mv.addObject("company", company);
		/*String totalAmount= getCommaFormat(quoteVo.getTotalAmount());
		if(!totalAmount.contains(".")){
			totalAmount+=".00";
		}*/
		mv.addObject("totalAmount", quoteVo.getTotalAmount());
		mv.addObject("detail", detail);
		mv.addObject("username", username);
		mv.addObject("detailUser", detailUser);
		mv.addObject("quoteVo", quoteVo);
		mv.setViewName("order/quote/bjOrderPrint");
		return mv;
	}
	//每3位中间添加逗号的格式化显示
		public static String getCommaFormat(BigDecimal value){
			return getFormat(",###.##",value);
		}
		
		//自定义数字格式方法
		public static String getFormat(String style,BigDecimal value){
			DecimalFormat df = new DecimalFormat();
			df.applyPattern(style);// 将格式应用于格式化器
			return df.format(value.doubleValue());
		}

}
