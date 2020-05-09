package com.vedeng.logistics.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.logistics.service.FileDeliveryService;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.TraderSupplier;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.TraderAddressService;
import com.vedeng.trader.service.TraderCustomerService;
import com.vedeng.trader.service.TraderSupplierService;

@Controller
@RequestMapping("/logistics/filedelivery")
public class FileDeliveryController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(FileDeliveryController.class);

    	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Autowired
	@Qualifier("fileDeliveryService")
	private FileDeliveryService fileDeliveryService;

	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;

	@Resource
	@Qualifier("traderSupplierService")
	private TraderSupplierService traderSupplierService;

	@Autowired
	@Qualifier("expressService")
	private ExpressService expressService;
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	
	@Autowired
	@Qualifier("traderAddressService")
	private TraderAddressService traderAddressService;

	
	// 中通快递id
	public static final Integer LOGISTICS_ID_2 = 2;

	// 顺丰快递id
	public static final Integer LOGISTICS_ID_5 = 5;

	// 有效
	public static final Integer IS_ENABLE_1 = 1;
	
	
	/**
	 * 
	 * <b>Description:</b><br>
	 * 文件寄送列表
	 * 
	 * @param request
	 * @param fileDelivery
	 * @param express
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @param searchBeginTime
	 * @param searchEndTime
	 * @return
	 * @Note <b>Author:</b> Micheal <br>
	 *       <b>Date:</b> 2017年7月19日 上午10:23:57
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, FileDelivery fileDelivery, Express express, Integer creatorId,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, // required
			// false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
			@RequestParam(required = false) Integer pageSize, HttpSession session,
			@RequestParam(required = false, value = "searchBeginTime") String searchBeginTime,
			@RequestParam(required = false, value = "searchEndTime") String searchEndTime) {
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		try {
			//默认未寄送
			if(null == request.getParameter("deliveryStatus")){
				fileDelivery.setDeliveryStatus(0);
			}
			
			// 查询文件寄送列表
			Page page = getPageTag(request, pageNo, pageSize);
			// 获取session中user信息
			User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
			mv.addObject("loginUser", session_user);
			mv.addObject("searchBeginTime", searchBeginTime);
			mv.addObject("searchEndTime", searchEndTime);
			if (searchBeginTime != null && !searchBeginTime.equals("")) {
				fileDelivery.setBeginTime(DateUtil.convertLong(searchBeginTime + " 00:00:00", DateUtil.TIME_FORMAT));
			}
			if (searchEndTime != null && !searchEndTime.equals("")) {
				fileDelivery.setEndTime(DateUtil.convertLong(searchEndTime + " 23:59:59", DateUtil.TIME_FORMAT));
			}
			
			// 校验单号
			if(EmptyUtils.isNotBlank(fileDelivery.getFileDeliveryNo())){
				// 去空格
				fileDelivery.setFileDeliveryNo(fileDelivery.getFileDeliveryNo().replaceAll(" ", ""));
			}
			
			
			// 申请人
			// List<User> netAllUserList =
			// userService.getNextAllUserList(session_user.getUserId(),session_user.getCompanyId(),true,session_user.getPositLevel(),1);
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);// 销售
			positionType.add(SysOptionConstant.ID_311);// 采购
			positionType.add(SysOptionConstant.ID_312);// 售后
			positionType.add(SysOptionConstant.ID_314);// 财务
			positionType.add(SysOptionConstant.ID_313);// 物流
			List<User> netAllUserList = new ArrayList<>();
			Integer type = session_user.getPositions().get(0).getType();
			if(type.equals(310)  || type.equals(311) ){
			     netAllUserList = userService.getMyUserList(user, positionType, false);
			}else{
			     netAllUserList = userService.getAllUser(session_user);
			}
			if(type.equals(314)){
				//财务人员登录
				fileDelivery.setPositionType(314);
			}else if(type.equals(313)){
				//物流人员登录
				fileDelivery.setPositionType(313);
			}
			mv.addObject("netAllUserList", netAllUserList);
			List<Integer> creatorIds = new ArrayList<Integer>();
			// 如果申请人条件是全部的话
			if (netAllUserList != null && (creatorId == null || creatorId == -1)) {
				for (User c : netAllUserList) {
					creatorIds.add(c.getUserId());
				}
				creatorIds.add(session_user.getUserId());
			}
			// 防止跟express对象中的creator相冲突
			if (null != creatorId && creatorId != -1) {
				fileDelivery.setCreator(creatorId);
			}
			
		
			fileDelivery.setCompanyId(session_user.getCompanyId());

			// 获取文件寄送列表
			List<FileDelivery> fileDeliveryList = fileDeliveryService.getFileDeliveryList(fileDelivery, express,
					creatorIds, page);
			mv.addObject("fileDeliveryList", fileDeliveryList);
			// 获取物流公司列表
			List<Logistics> logisticsList = logisticsService.getLogisticsList(session_user.getCompanyId());
			mv.addObject("logisticsList", logisticsList);
			// 获取寄送类型
			List<SysOptionDefinition> sendTypeName = getSysOptionDefinitionList(SysOptionConstant.ID_488);
			// if (JedisUtils.exists(dbType +
			// ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
			// SysOptionConstant.ID_488)) {
			// String jsonStr = JedisUtils
			// .get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
			// SysOptionConstant.ID_488);
			// JSONArray json = JSONArray.fromObject(jsonStr);
			// sendTypeName = (List<SysOptionDefinition>)
			// JSONArray.toCollection(json, SysOptionDefinition.class);
			// }
			mv.addObject("sendTypeName", sendTypeName);
			// 物流部人员
			List<User> logisticsUserList = userService.getUserListByPositType(SysOptionConstant.ID_313,session_user.getCompanyId());
			mv.addObject("logisticsUserList", logisticsUserList);
			mv.addObject("userId",user.getUserId());
			
			boolean flag = false;
			
			boolean flags = false;
			if ( type.equals(313) || type.equals(314)) {
			    flag = true;
			}
			//判断段他属于某一种角色
			mv.addObject("role",flag);

			
			mv.addObject("fileDelivery", fileDelivery);
			mv.addObject("express", express);
			mv.addObject("page", page);
			mv.setViewName("logistics/filedelivery/index");
		} catch (Exception e) {
			logger.error("file delivery index:", e);
		}
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 新增文件寄送
	 * 
	 * @return
	 * @Note <b>Author:</b> Micheal <br>
	 *       <b>Date:</b> 2017年7月19日 上午11:07:25
	 */
	@FormToken(save=true)
	@RequestMapping(value = "/addFileDelivery")
	public ModelAndView addFileDelivery(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		// 获取消息类型
		List<SysOptionDefinition> sendTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_488);
		// if (JedisUtils.exists(dbType +
		// ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_488))
		// {
		// String jsonStr = JedisUtils
		// .get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST +
		// SysOptionConstant.ID_488);
		// JSONArray json = JSONArray.fromObject(jsonStr);
		// sendTypeList = (List<SysOptionDefinition>)
		// JSONArray.toCollection(json, SysOptionDefinition.class);
		// }
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		// 获取职位类型
		Integer positType = null;
		if (session_user.getPositType() !=null && session_user.getPositType() == 310) {
			// 销售
			positType = 1;
		} else if (session_user.getPositType() !=null && session_user.getPositType() == 311) {
			// 采购
			positType = 2;
		} else {
			// 其他部门
			positType = 3;
		}
		mv.addObject("positType", positType);
		mv.addObject("sendTypeList", sendTypeList);
		mv.setViewName("logistics/filedelivery/add_filedelivery");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据传来的类型返回供应商列表或者经销商列表
	 * 
	 * @param request
	 * @param searchTraderName
	 * @param traderType
	 *            1::经销商（包含终端）2:供应商
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> Micheal <br>
	 *       <b>Date:</b> 2017年7月20日 下午2:48:42
	 */
	@RequestMapping(value = "/getTraderList")
	public ModelAndView getTraderList(HttpServletRequest request, String searchTraderName, Integer traderType,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
		  //java : 字符解码 
			searchTraderName = java.net.URLDecoder.decode(java.net.URLDecoder.decode(searchTraderName, "UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		if (traderType == 1) {
			if (searchTraderName != null) {
				Page page = getPageTag(request, pageNo, pageSize);
				TraderCustomerVo traderCustomerVo = new TraderCustomerVo();
				traderCustomerVo.setSearchTraderName(searchTraderName);
				traderCustomerVo.setCompanyId(session_user.getCompanyId());
				Map<String, Object> map = traderCustomerService.searchCustomerPageList(traderCustomerVo, page);
				mv.addObject("page", (Page) map.get("page"));
				mv.addObject("traderList", (List<TraderCustomerVo>) map.get("searchCustomerList"));
			}
		} else if (traderType == 2) {
			if (searchTraderName != null) {
				Page page = getPageTag(request, pageNo, pageSize);
				TraderSupplierVo traderSupplierVo = new TraderSupplierVo();
				traderSupplierVo.setTraderSupplierName(searchTraderName);
				traderSupplierVo.setCompanyId(session_user.getCompanyId());
				Map<String, Object> map = traderSupplierService.getTraderSupplierList(traderSupplierVo, page, null);
				mv.addObject("page", (Page) map.get("page"));
				mv.addObject("traderList", (List<TraderSupplierVo>) map.get("list"));
			}
		}
		// 获取职位类型
		System.out.println(session_user.getPositType());
		Integer positType = null;
		if (session_user.getPositType() !=null && session_user.getPositType() == 310) {
			// 销售
			positType = 1;
		} else if (session_user.getPositType() !=null && session_user.getPositType() == 311) {
			// 采购
			positType = 2;
		} else {
			// 其他部门
			positType = 3;
		}
		mv.addObject("positType", positType);
		mv.addObject("username", session_user.getUsername());
		mv.addObject("searchTraderName", searchTraderName);
		mv.addObject("traderType", traderType);
		mv.setViewName("logistics/filedelivery/list_trader");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据交易者ID和交易者类型获取联系方式和地址
	 * 
	 * @param traderId
	 * @param traderType
	 * @return
	 * @Note <b>Author:</b> Micheal <br>
	 *       <b>Date:</b> 2017年7月21日 下午5:28:32
	 */
	@ResponseBody
	@RequestMapping(value = "/getContactsAddress")
	public ResultInfo getContactsAddress(Integer traderId, Integer traderType) {
		TraderContactVo traderContactVo = new TraderContactVo();
		traderContactVo.setTraderId(traderId);
		traderContactVo.setTraderType(traderType);
		// 根据交易者ID和交易者类型获取联系方式和地址
		Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
		String tastr = (String) map.get("contact");
		net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
		List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
		map.put("contact", list);
		ResultInfo resultInfo = new ResultInfo<>();
		if (map != null && !map.isEmpty()) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 保存文件寄送
	 * 
	 * @param request
	 * @param fileDelivery
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年7月24日 下午6:45:20
	 */
	@ResponseBody
	@FormToken(remove=true)
	@RequestMapping(value = "/saveFileDelivery")
	@SystemControllerLog(operationType = "add",desc = "保存文件寄送")
	public ModelAndView saveFileDelivery(HttpServletRequest request, FileDelivery fileDelivery) {
		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user == null) {
			return fail(mav);

		}
		fileDelivery.setCreator(user.getUserId());
		fileDelivery.setAddTime(DateUtil.sysTimeMillis());
		
		fileDelivery.setUpdater(user.getUserId());
		fileDelivery.setModTime(DateUtil.sysTimeMillis());
		
		fileDelivery.setCompanyId(user.getCompanyId());
		ResultInfo<?> result = new ResultInfo<>();
		int i = 0;
		try {
			i = fileDeliveryService.saveFileDelivery(fileDelivery);
		} catch (Exception e) {
			logger.error("saveFileDelivery 1:", e);
		}
		if (i > 0) {
		    try {
        		Map<String, Object> variableMap = new HashMap<String, Object>();
        		// 查询当前订单的一些状态
        		FileDelivery fileDeliveryInfo = fileDeliveryService.getFileDeliveryInfoById(i);
        		String taskId = "";
        		//开始生成流程(如果没有taskId表示新流程需要生成)
        		    variableMap.put("fileDelivery", fileDeliveryInfo);
        		    variableMap.put("currentAssinee", user.getUsername());
        		    variableMap.put("processDefinitionKey","fileDeliveryVerify");
        		    variableMap.put("businessKey","fileDeliveryVerify_" + fileDeliveryInfo.getFileDeliveryId());
        		    variableMap.put("relateTableKey",fileDeliveryInfo.getFileDeliveryId());
        		    variableMap.put("relateTable", "T_FILE_DELIVERY");
        		    actionProcdefService.createProcessInstance(request,"fileDeliveryVerify","fileDeliveryVerify_" + fileDeliveryInfo.getFileDeliveryId(),variableMap);
        		//默认申请人通过
        		//根据BusinessKey获取生成的审核实例
        		Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "fileDeliveryVerify_"+ fileDeliveryInfo.getFileDeliveryId());
        			if(historicInfo.get("endStatus") != "审核完成"){
                        		Task taskInfo = (Task) historicInfo.get("taskInfo");
                        		taskId = taskInfo.getId();
                        		Authentication.setAuthenticatedUserId(user.getUsername());
                        		Map<String, Object> variables = new HashMap<String, Object>();
                        		//设置审核完成监听器回写参数
                        		variables.put("tableName", "T_FILE_DELIVERY");
                        		variables.put("id", "FILE_DELIVERY_ID");
                        		variables.put("idValue", fileDelivery.getFileDeliveryId());
                        		variables.put("key", "VERIFY_STATUS");
                        		variables.put("value", 2);
                        		//回写数据的表在erp中
                        		variables.put("db", 1);
                        		//默认审批通过
                        		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,"",user.getUsername(),variables);
                			//如果未结束添加审核对应主表的审核状态
                        		if(!complementStatus.getData().equals("endEvent")){
                        		    verifiesRecordService.saveVerifiesInfo(taskId,0);
                        		}
                			
                			FileDelivery fileDeliveryUpdate = new FileDelivery();
                			//修改主表审核中
                			fileDeliveryUpdate.setVerifyStatus(1);
                			fileDeliveryUpdate.setFileDeliveryId(fileDeliveryInfo.getFileDeliveryId());
                     		    	fileDeliveryService.updateFileDelivery(fileDeliveryUpdate);
        			}
        			result.setCode(0);
        			result.setMessage("操作成功");
        		} catch (Exception e) {
					logger.error("saveFileDelivery 2:", e);
        		    	result.setCode(-1);
    				result.setMessage("任务完成操作失败"+ e.getMessage());
        		}
			
		}
		
		if (null != result && result.getCode() == 0) {
			mav.addObject("refresh", "false_false_true");// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
			mav.addObject("url", "./getFileDeliveryDetail.do?fileDeliveryId=" + fileDelivery.getFileDeliveryId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 文件寄送详情
	 * 
	 * @param session
	 * @param fileDeliveryId
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年7月31日 上午8:58:32
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/getFileDeliveryDetail")
	public ModelAndView getFileDeliveryDetail(HttpSession session, Integer fileDeliveryId, Integer expressId,
			Integer expressDetailId) {
		ModelAndView mv = new ModelAndView();
		// 获取session中user信息
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		mv.addObject("curr_user", session_user);
		try {
			// 根据主键获取FileDelivery对象
			FileDelivery fileDelivery = fileDeliveryService.getFileDeliveryInfoById(fileDeliveryId);
			// 根据客户类型去查，判断是供应商还是客户

			if (fileDelivery.getTraderType() == 1) {
				// 经销商（包含终端）
				TraderCustomer traderCustomer = new TraderCustomer();
				traderCustomer.setTraderId(fileDelivery.getTraderId());
				TraderCustomerVo traderCustomerInfo = traderCustomerService.getTraderCustomerManageInfoSeconed(traderCustomer, session);
				mv.addObject("traderCustomerInfo", traderCustomerInfo);
			} else {
				// 供应商
				TraderSupplier traderSupplier = new TraderSupplier();
				traderSupplier.setTraderId(fileDelivery.getTraderId());
				TraderSupplierVo traderCustomerInfo = traderSupplierService.getTraderSupplierManageInfo(traderSupplier);
				mv.addObject("traderCustomerInfo", traderCustomerInfo);
			}
			// 获取寄送类型
//			if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + fileDelivery.getSendType())) {
//				JSONObject jsonObject = JSONObject.fromObject(JedisUtils
//						.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + fileDelivery.getSendType()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				fileDelivery.setSendTypeName(sod.getTitle());
//			}
			fileDelivery.setSendTypeName(getSysOptionDefinition(fileDelivery.getSendType()).getTitle());
			// 获取物流信息
			Express express = new Express();
			express.setRelatedId(fileDeliveryId);
			express.setBusinessType(SysOptionConstant.ID_498);
			List<Express> expressInfo = expressService.getExpressList(express);
			// 获取物流操作者信息
			User dedeliveryUser = new User();
			User dedeliveryUserInfo = new User();
			if(null != fileDelivery.getDeliveryUserId() && fileDelivery.getDeliveryUserId() !=0 ){
			    dedeliveryUser.setUserId(fileDelivery.getDeliveryUserId());
			    dedeliveryUserInfo = userService.getUser(dedeliveryUser);
			}
			
			// -----------查询物流公司列表 （现在只展示中通快递、顺丰快递）----------
			Map<String, Object> logisticsParam = new HashMap<>();
			// 中通快递id
			logisticsParam.put("ZT_LOGISTICS", LOGISTICS_ID_2);
			// 顺丰快递
			logisticsParam.put("SF_LOGISTICS", LOGISTICS_ID_5);
			// 有效	
			logisticsParam.put("IS_ENABLE", IS_ENABLE_1);
			// 公司id
			logisticsParam.put("companyId", session_user.getCompanyId());
			// 获取物流公司列表
			List<Logistics> logisticsList = logisticsService.getLogisticsListByParam(logisticsParam);
			
			//--------------根据收货地址查询快递费用------------------------------
			// 快递bug
			mv.addObject("deliverFree", 0);
			// 省份id
			mv.addObject("regionId", 0);
			if(null != fileDelivery.getTraderAddressId()){
				Map<String, Object> regionParamMap = new HashMap<>();
				// 1.根据addressId查询到regionId和regionType
				regionParamMap.put("addressId", fileDelivery.getTraderAddressId());
				// 地址是否有效
				regionParamMap.put("isEnable", IS_ENABLE_1);
				TraderAddress traderAddress = traderAddressService.getAddressInfoByParam(regionParamMap);
				// 如果结果不为空
				regionParamMap.put("regionId", 0);
				if(null != traderAddress && EmptyUtils.isNotBlank(traderAddress.getAreaIds())){
					// areaIds 第一个就是rengionId的省级
					regionParamMap.put("regionId", Integer.valueOf(traderAddress.getAreaIds().split(",")[0]));
				}
				// 查询中通快递费用
				regionParamMap.put("logisticsId", LOGISTICS_ID_2);
				// 根据省id和快递id查询费用
				BigDecimal deliverFree = logisticsService.getFreeByParam(regionParamMap);
				mv.addObject("deliverFree", deliverFree);
				if(traderAddress != null){
					mv.addObject("regionId", Integer.valueOf(traderAddress.getAreaIds().split(",")[0]));
				}else{
					mv.addObject("regionId", "");
				}
				
			}
			
			
			
			mv.addObject("logisticsList", logisticsList);
			// 获取申请人信息
			User user = new User();
			user.setUserId(fileDelivery.getCreator());
			User userInfo = userService.getUser(user);
			if (expressId != null && expressDetailId != null) {
				mv.addObject("expressId", expressId);
				mv.addObject("expressDetailId", expressDetailId);
			}
			//设置更新人
			user.setUserId(fileDelivery.getUpdater());
			User updateUser = userService.getUser(user);
			if ( null != updateUser){
				fileDelivery.setUpdaterName(updateUser.getUsername());
			}
			
			mv.addObject("expressInfo", expressInfo);
			mv.addObject("dedeliveryUserInfo", dedeliveryUserInfo);
			mv.addObject("userInfo", userInfo);
			mv.addObject("fileDelivery", fileDelivery);
			mv.addObject("expressName", expressInfo.isEmpty()||expressInfo==null?"":expressInfo.get(0).getLogisticsName());
			mv.addObject("epInfo", expressInfo.isEmpty()||expressInfo==null?"":expressInfo.get(0).getExpressDetail().get(0));
			
			
			Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "fileDeliveryVerify_"+ fileDeliveryId);
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
			mv.setViewName("logistics/filedelivery/view_filedelivery");
		} catch (Exception e) {
			logger.error("getFileDeliveryDetail:", e);
		}
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 文件寄送操作
	 * 
	 * @param request
	 * @param fileDelivery
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月1日 上午11:48:33
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/saveExpress")
	@SystemControllerLog(operationType = "add",desc = "保存文件寄送快递信息")
	public ModelAndView saveExpress(HttpServletRequest request, Express express) {
		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user == null) {
			return fail(mav);
		}
		express.setCompanyId(user.getCompanyId());
		// 补充快递表中的一些添加人和添加日期
		express.setAddTime(DateUtil.sysTimeMillis());
		express.setCreator(user.getUserId());
		express.setUpdater(user.getUserId());
		express.setModTime(DateUtil.sysTimeMillis());
		express.setDeliveryTime(DateUtil.sysTimeMillis());
		express.setIsEnable(1);
		express.setBusinessType(SysOptionConstant.ID_498);
		// 模拟白下区的地址
		express.setDeliveryFrom(1838);

		List<ExpressDetail> expressDetailList = new ArrayList<>();
		ExpressDetail expressDetail = new ExpressDetail();
		expressDetail.setAmount(express.getAmount());
		expressDetail.setBusinessType(express.getBusinessType());
		expressDetail.setNum(express.getNum());
		expressDetail.setRelatedId(express.getRelatedId());
		expressDetailList.add(expressDetail);
		express.setExpressDetail(expressDetailList);
		ResultInfo<?> result = new ResultInfo<>();
		try {
			result = fileDeliveryService.saveExpress(express);
		} catch (Exception e) {
			logger.error("file delivery saveExpress:", e);
		}
		
		if (null != result && result.getCode() == 0) {
			mav.addObject("url", "./getFileDeliveryDetail.do?fileDeliveryId=" + express.getRelatedId());
			return success(mav);
		} else {
			return fail(mav);
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 文件寄送申请审核
	 * @param request
	 * @param fileDelivery
	 * @param taskId
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月23日 上午9:37:02
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/editApplyValidFileDelivery")
	public ResultInfo<?> editApplyValidFileDelivery(HttpServletRequest request, FileDelivery fileDelivery,String taskId,HttpSession session) {
		try {
        		Map<String, Object> variableMap = new HashMap<String, Object>();
        		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        		// 查询当前订单的一些状态
        		fileDelivery = fileDeliveryService.getFileDeliveryInfoById(fileDelivery.getFileDeliveryId());
        		//开始生成流程(如果没有taskId表示新流程需要生成)
        		if(taskId.equals("0")){
        		    variableMap.put("fileDelivery", fileDelivery);
        		    variableMap.put("currentAssinee", user.getUsername());
        		    variableMap.put("processDefinitionKey","fileDeliveryVerify");
        		    variableMap.put("businessKey","fileDeliveryVerify_" + fileDelivery.getFileDeliveryId());
        		    variableMap.put("relateTableKey",fileDelivery.getFileDeliveryId());
        		    variableMap.put("relateTable", "T_FILE_DELIVERY");
        		    actionProcdefService.createProcessInstance(request,"fileDeliveryVerify","fileDeliveryVerify_" + fileDelivery.getFileDeliveryId(),variableMap);
        		}
        		//默认申请人通过
        		//根据BusinessKey获取生成的审核实例
        		Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "fileDeliveryVerify_"+ fileDelivery.getFileDeliveryId());
        		if(historicInfo.get("endStatus") != "审核完成"){
                		Task taskInfo = (Task) historicInfo.get("taskInfo");
                		taskId = taskInfo.getId();
                		Authentication.setAuthenticatedUserId(user.getUsername());
                		Map<String, Object> variables = new HashMap<String, Object>();
                		//设置审核完成监听器回写参数
                		variables.put("tableName", "T_FILE_DELIVERY");
                		variables.put("id", "FILE_DELIVERY_ID");
                		variables.put("idValue", fileDelivery.getFileDeliveryId());
                		variables.put("key", "VERIFY_STATUS");
                		variables.put("value", 2);
                		//回写数据的表在erp中
                		variables.put("db", 1);
                		//默认审批通过
                		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,"",user.getUsername(),variables);
        			//如果未结束添加审核对应主表的审核状态
                		if(!complementStatus.getData().equals("endEvent")){
                		    verifiesRecordService.saveVerifiesInfo(taskId,0);
                		}
        			
        			FileDelivery fileDeliveryInfo = new FileDelivery();
        			//修改主表审核中
        			fileDeliveryInfo.setVerifyStatus(1);
        			fileDeliveryInfo.setFileDeliveryId(fileDelivery.getFileDeliveryId());
             		    	fileDeliveryService.updateFileDelivery(fileDeliveryInfo);
        		}
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("editApplyValidFileDelivery:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
		
	}
	
	
	/**
	 * 
	 * <b>Description:</b><br>
	 * 文件寄送审核页面
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session,Integer fileDeliveryId, String taskId, Boolean pass) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.addObject("fileDeliveryId", fileDeliveryId);
		mv.setViewName("logistics/filedelivery/complement");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 文件寄送审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	public ResultInfo<?> complementTask(HttpServletRequest request,Integer fileDeliveryId, String taskId, String comment, Boolean pass,
			HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("updater",user.getUserId());
		try {
		    	Integer status = 0;
			if(pass){
			    //如果审核通过
			     status = 0;
			}else{
			    //如果审核不通过
			    status = 2;
			    //如果不通过审核
		    	    TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量  
		    	    String tableName= (String) taskService.getVariable(taskId, "tableName");
		    	    String id=(String) taskService.getVariable(taskId, "id");
		    	    Integer idValue=(Integer) taskService.getVariable(taskId, "idValue");
		    	    String key= (String) taskService.getVariable(taskId, "key");
		    	    if(tableName != null && id != null && idValue != null && key != null){
		    		actionProcdefService.updateInfo(tableName, id, idValue, key, 3, 1);
		    	    }
			    verifiesRecordService.saveVerifiesInfo(taskId,status);	
			}
		    ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,comment,user.getUsername(),variables);
		    //如果未结束添加审核对应主表的审核状态
	    	    if(!complementStatus.getData().equals("endEvent")){
	    		verifiesRecordService.saveVerifiesInfo(taskId,status);	
	    	    }
			return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("file delivery complementTask", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}
	/**
	 * 
	 * <b>Description:</b><br>
	 * 审核通过操作
	 * 
	 * @param request
	 * @param fileDelivery
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年8月1日 上午11:48:33
	 */
	@ResponseBody
	@RequestMapping(value = "/editVerifyStatusPass")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑文件审核状态")
	public ResultInfo editVerifyStatusPass(HttpServletRequest request, FileDelivery fileDelivery,String beforeParams) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			fileDelivery.setUpdater(user.getUserId());
			fileDelivery.setModTime(DateUtil.sysTimeMillis());
			fileDelivery.setVerifyStatus(2);
		}
		ResultInfo<?> result = new ResultInfo<>();
		int i = 0;
		try {
			i = fileDeliveryService.updateFileDelivery(fileDelivery);
		} catch (Exception e) {
			logger.error("editVerifyStatusPass:", e);
		}
		if (i == 1) {
			result.setCode(0);
			result.setMessage("操作成功");
		} else {
			result.setCode(-1);
			result.setMessage("操作失败");
		}
		
		return result;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 审核不通过原因
	 * 
	 * @param session
	 * @param fileDeliveryId
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年7月28日 上午9:32:13
	 */
	@ResponseBody
	@RequestMapping(value = "/editVerifyStatusNoPass")
	public ModelAndView editVerifyStatusNoPass(HttpSession session, Integer fileDeliveryId) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileDeliveryId", fileDeliveryId);
		mv.setViewName("logistics/filedelivery/modify_verify_status");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 跳转到文件的关闭页面
	 * @param :a
	 *@return :a
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/1/24 15:46
	 */
	@ResponseBody
	@RequestMapping(value = "/closeFileDelivery")
	public ModelAndView closeFileDelivery(Integer fileDeliveryId ){
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileDeliveryId", fileDeliveryId);
		mv.setViewName("logistics/filedelivery/modify_verify_close");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 更新文件的关闭状态
	 * @param :a
	 *@return :a
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/1/24 15:46
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDeliveryCloseStatus")
	public ResultInfo updateDeliveryCloseStatus( HttpServletRequest request, FileDelivery fileDelivery ){
		ResultInfo<?> result = new ResultInfo<>();
		//获取当前用户
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if ( null != user ) {
			//设置更新人
			fileDelivery.setUpdater(user.getUserId());
		}
		//更新操作
		Integer integer = fileDeliveryService.updateDeliveryCloseStatus(fileDelivery);
		//因为integer为包装类，所以需要处理
		Optional<Integer> optionalInteger = Optional.ofNullable(integer);
		if ( optionalInteger.isPresent() ) {
			if ( optionalInteger.get().equals(ErpConst.ONE) ) {
				//成功的状态
				result.setCode(ErpConst.ZERO);
				result.setStatus(ErpConst.ZERO);
				result.setMessage("操作成功");
			} else {
				//失败状态
				result.setCode(ErpConst.ONE);
				result.setMessage("关闭失败");
			}
	
		}
		return result ;
	}

	/**
	 * 根据根据快递公司id和省份改变快递费用
	 * <p>Title: changeDeliveryFree</p>  
	 * <p>Description: </p>  
	 * @param request
	 * @param regionId
	 * @param logisticsId
	 * @return  
	 * @author Bill
	 * @date 2019年3月5日
	 */
	@ResponseBody
	@RequestMapping(value = "/changeDeliveryFree")
	public ResultInfo<Map<String, Object>> changeDeliveryFree( HttpServletRequest request, Integer regionId, Integer logisticsId){
		
		try {
			// 结果集
			Map<String, Object> resMap = new HashMap<>();
			// 参数集
			Map<String, Object> regionParamMap = new HashMap<>();
			// 如果结果不为空
			regionParamMap.put("regionId", regionId);
			// 查询中通快递费用
			regionParamMap.put("logisticsId", logisticsId);
			// 根据省id和快递id查询费用
			BigDecimal deliverFree = logisticsService.getFreeByParam(regionParamMap);
			
			// 如果快递费用不为空
			if(null != deliverFree){
				resMap.put("deliverFree", deliverFree.toString());
				
				return new ResultInfo<Map<String,Object>>(0, "成功", 200, resMap);
			}else{
				return new ResultInfo<Map<String,Object>>(-1, "失败", 500, resMap);
			}
		} catch (Exception e) {
			logger.error("changeDeliveryFree:", e);
		}
		return new ResultInfo(0, "成功", 200);
	}
	
}
