package com.vedeng.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.system.dao.SysOptionDefinitionMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
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
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;

import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.MessageUser;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.MessageService;
import com.vedeng.system.service.PositService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.TagService;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 商机
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.controller <br>
 *       <b>ClassName:</b> BussinessChanceController <br>
 *       <b>Date:</b> 2017年6月22日 上午8:52:06
 */
@Controller
@RequestMapping("/order/bussinesschance")
public class BussinessChanceController extends BaseController {
    
    	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Autowired // 自动装载
	@Qualifier("historyService")
	private HistoryService historyService;
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	
	@Autowired
	@Qualifier("bussinessChanceService")
	private BussinessChanceService bussinessChanceService;

	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;// 自动注入regionService

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("positService")
	private PositService positService;// 自动注入positService
	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;
	@Autowired
	@Qualifier("tagService")
	private TagService tagService;
	
	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;

	@Autowired
	private SysOptionDefinitionMapper sysOptionDefinitionMapper;

	/**
	 * <b>Description:</b><br>
	 * 售后商机首页
	 * 
	 * @param request
	 * @param bussinessChance
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月22日 上午8:57:03
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "serviceindex")
	public ModelAndView serviceIndex(HttpServletRequest request, BussinessChanceVo bussinessChanceVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		bussinessChanceVo.setCompanyId(user.getCompanyId());

		// 商机类型
		List<SysOptionDefinition> typeList = getSysOptionDefinitionList(SysOptionConstant.ID_390);
		mv.addObject("typeList", typeList);
		
		//商机来源
		List<SysOptionDefinition> sourceList = getSysOptionDefinitionList(SysOptionConstant.ID_365);
		mv.addObject("sourceList", sourceList);

		List<SysOptionDefinition> entrancesList = sysOptionDefinitionMapper.getSysOptionDefinitionByParentTitle("咨询入口");
		mv.addObject("entrancesList",entrancesList);

		List<SysOptionDefinition> functionsList = sysOptionDefinitionMapper.getSysOptionDefinitionByParentTitle("功能");
		mv.addObject("functionsList",functionsList);


		List<Integer> salePositionType = new ArrayList<>();
		salePositionType.add(SysOptionConstant.ID_310);//销售
		salePositionType.add(312);//市场
		salePositionType.add(311);//产品
		user.setPositType(0);
		List<User> marketUserList = userService.getMyUserList(user, salePositionType, false);
		mv.addObject("userList", marketUserList);
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mv.addObject("provinceList", provinceList);

		if (null != bussinessChanceVo.getProvince() && bussinessChanceVo.getProvince() > 0) {
			List<Region> cityList = regionService.getRegionByParentId(bussinessChanceVo.getProvince());
			mv.addObject("cityList", cityList);
		}

		if (null != bussinessChanceVo.getCity() && bussinessChanceVo.getCity() > 0) {
			List<Region> zoneList = regionService.getRegionByParentId(bussinessChanceVo.getCity());
			mv.addObject("zoneList", zoneList);
		}

		
		if (null != bussinessChanceVo.getUserId() && bussinessChanceVo.getUserId() > 0) {
			List<Integer> userIds = new ArrayList<>();
			userIds.add(bussinessChanceVo.getUserId());
			bussinessChanceVo.setUserIds(userIds);
		}

		// 时间处理
		if (null != bussinessChanceVo.getStarttime() && bussinessChanceVo.getStarttime() != "") {
			bussinessChanceVo.setStarttimeLong(DateUtil.convertLong(bussinessChanceVo.getStarttime(), "yyyy-MM-dd"));
		}
		if (null != bussinessChanceVo.getEndtime() && bussinessChanceVo.getEndtime() != "") {
			bussinessChanceVo.setEndtimeLong(DateUtil.convertLong(bussinessChanceVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}

		// 地区处理
		if (null != bussinessChanceVo.getZone() && bussinessChanceVo.getZone() > 0) {
			bussinessChanceVo.setAreaId(bussinessChanceVo.getZone());
		} else if (null != bussinessChanceVo.getCity() && bussinessChanceVo.getCity() > 0) {
			bussinessChanceVo.setAreaId(bussinessChanceVo.getCity());
		} else if (null != bussinessChanceVo.getProvince() && bussinessChanceVo.getProvince() > 0) {
			bussinessChanceVo.setAreaId(bussinessChanceVo.getProvince());
		}

		List<BussinessChanceVo> bussinessChanceList = null;
		Map<String, Object> map = bussinessChanceService.getServiceBussinessChanceListPage(bussinessChanceVo, page);

		bussinessChanceList = (List<BussinessChanceVo>) map.get("list");

		mv.addObject("bussinessChanceVo", bussinessChanceVo);
		mv.addObject("bussinessChanceList", bussinessChanceList);
		mv.addObject("page", (Page) map.get("page"));
		mv.setViewName("order/bussinesschance/service_index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 销售商机首页
	 * 
	 * @param request
	 * @param bussinessChance
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月22日 上午8:57:06
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "saleindex")
	public ModelAndView saleIndex(HttpServletRequest request, BussinessChanceVo bussinessChanceVo,
			TraderCustomer traderCustomer, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		bussinessChanceVo.setCompanyId(user.getCompanyId());
		ModelAndView mv = new ModelAndView();

		Page page = getPageTag(request, pageNo, pageSize);
		
		//如果查询条件是“关闭时间”，则把商机状态设为关闭
		if(null != bussinessChanceVo.getTimeType() && bussinessChanceVo.getTimeType() == 5){
			//把商机状态设为关闭
			bussinessChanceVo.setStatus(4);
		}

		// 商机类型
		List<SysOptionDefinition> typeList = getSysOptionDefinitionList(SysOptionConstant.ID_390);
		mv.addObject("typeList", typeList);
		//商机来源
		List<SysOptionDefinition> sourceList = getSysOptionDefinitionList(SysOptionConstant.ID_365);
		mv.addObject("sourceList", sourceList);

		//咨询入口
		List<SysOptionDefinition> entrancesList = sysOptionDefinitionMapper.getSysOptionDefinitionByParentTitle("咨询入口");
		mv.addObject("entrancesList",entrancesList);

		//功能
		List<SysOptionDefinition> functionsList = sysOptionDefinitionMapper.getSysOptionDefinitionByParentTitle("功能");
		mv.addObject("functionsList",functionsList);


		//商机等级
        List<SysOptionDefinition> bussinessLevelList = getSysOptionDefinitionList(SysOptionConstant.ID_938);
        mv.addObject("bussinessLevelList", bussinessLevelList);
        //成单机率
        List<SysOptionDefinition> orderRateList = getSysOptionDefinitionList(SysOptionConstant.ID_951);
        mv.addObject("orderRateList", orderRateList);
        //商机阶段
        List<SysOptionDefinition> bussinessStageList = getSysOptionDefinitionList(SysOptionConstant.ID_943);
        mv.addObject("bussinessStageList", bussinessStageList);
		// 地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mv.addObject("provinceList", provinceList);

		if (null != bussinessChanceVo.getProvince() && bussinessChanceVo.getProvince() > 0) {
			List<Region> cityList = regionService.getRegionByParentId(bussinessChanceVo.getProvince());
			mv.addObject("cityList", cityList);
		}

		if (null != bussinessChanceVo.getCity() && bussinessChanceVo.getCity() > 0) {
			List<Region> zoneList = regionService.getRegionByParentId(bussinessChanceVo.getCity());
			mv.addObject("zoneList", zoneList);
		}

		// 查询用户集合
		List<Integer> userIds = new ArrayList<>();
		
		// 查询当前用户下所有职位类型为310的员工
		List<User> userList = new ArrayList<>();
		if(traderCustomer != null && ObjectUtils.notEmpty(traderCustomer.getTraderId())){
			User us = userService.getUserByTraderId(traderCustomer.getTraderId(), 1);
			if(us != null){
				userList.add(us);
			}
		}else{
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);//销售
			userList = userService.getMyUserList(user, positionType, false);
			mv.addObject("userList", userList);
		}
		if (null == bussinessChanceVo.getUserId() || bussinessChanceVo.getUserId() <= 0) {

			for(User u : userList){
				userIds.add(u.getUserId());
			}
			
//			List<Integer> traderIdList = new ArrayList<>();
//			if (userIds.size() > 0) {
//				bussinessChanceVo.setUserIds(userIds);
//				traderIdList = userService.getTraderIdListByUserList(userList, ErpConst.ONE.toString());
//			}else{//名下无用户 
//				userIds.add(-1);
//				bussinessChanceVo.setUserIds(userIds);
//			}
//			
//			if(traderIdList != null && traderIdList.size()>0){
//				bussinessChanceVo.setTraderIds(traderIdList);
//			}
			bussinessChanceVo.setUserIds(userIds);
		}else{
			userIds.add(bussinessChanceVo.getUserId());
//			List<Integer> traderIdList = new ArrayList<>();
			if (userIds.size() > 0) {
				bussinessChanceVo.setUserIds(userIds);
//				List<User> myUserList = new ArrayList<>();
//				User myUser = new User();
//				myUser.setUserId(bussinessChanceVo.getUserId());
//				myUserList.add(myUser);
//				traderIdList = userService.getTraderIdListByUserList(myUserList, ErpConst.ONE.toString());
			}else{//名下无用户 
				userIds.add(-1);
				bussinessChanceVo.setUserIds(userIds);
			}
			
//			if(traderIdList != null && traderIdList.size()>0){
//				bussinessChanceVo.setTraderIds(traderIdList);
//			}
		}

		// 预计成单时间处理
		if (null != bussinessChanceVo.getCdstarttime() && bussinessChanceVo.getCdstarttime() != "") {
			bussinessChanceVo.setCdstarttimeLong(DateUtil.convertLong(bussinessChanceVo.getCdstarttime(), "yyyy-MM-dd"));
		}
		if (null != bussinessChanceVo.getCdendtime() && bussinessChanceVo.getCdendtime() != "") {
			bussinessChanceVo.setCdendtimeLong(DateUtil.convertLong(bussinessChanceVo.getCdendtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		//默认前3个月
		// 查询商机开始时间的前3个月
		bussinessChanceVo.setTimeType(1);
		if(StringUtils.isBlank(bussinessChanceVo.getStarttime())){
			bussinessChanceVo.setStarttime(DateUtil.DatePreMonth(new Date(),-3,null));
		}
		// 时间处理
        if (null != bussinessChanceVo.getStarttime() && bussinessChanceVo.getStarttime() != "") {
            bussinessChanceVo.setStarttimeLong(DateUtil.convertLong(bussinessChanceVo.getStarttime(), "yyyy-MM-dd"));
        }
        if (null != bussinessChanceVo.getEndtime() && bussinessChanceVo.getEndtime() != "") {
            bussinessChanceVo.setEndtimeLong(DateUtil.convertLong(bussinessChanceVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }

		// 地区处理
		if (null != bussinessChanceVo.getZone() && bussinessChanceVo.getZone() > 0) {
			bussinessChanceVo.setAreaId(bussinessChanceVo.getZone());
		} else if (null != bussinessChanceVo.getCity() && bussinessChanceVo.getCity() > 0) {
			bussinessChanceVo.setAreaId(bussinessChanceVo.getCity());
		} else if (null != bussinessChanceVo.getProvince() && bussinessChanceVo.getProvince() > 0) {
			bussinessChanceVo.setAreaId(bussinessChanceVo.getProvince());
		}
		bussinessChanceVo.setCurrUserId(user.getUserId());
		List<BussinessChanceVo> bussinessChanceList = null;

		Map<String, Object> map = bussinessChanceService.getSaleBussinessChanceListPage(bussinessChanceVo, page);

		bussinessChanceList = (List<BussinessChanceVo>) map.get("list");
		BigDecimal totalAmount = new BigDecimal(0);
		if(CollectionUtils.isNotEmpty(bussinessChanceList)){
		    totalAmount =  bussinessChanceList.get(0).getTotalAmount();
		}
		if(totalAmount == null){
		    totalAmount = new BigDecimal(0);
		}
		mv.addObject("totalAmount", totalAmount);
		mv.addObject("bussinessChanceVo", bussinessChanceVo);
		mv.addObject("bussinessChanceList", bussinessChanceList);
		mv.addObject("page", (Page) map.get("page"));

		if (null != traderCustomer && null != traderCustomer.getTraderId() && traderCustomer.getTraderId() > 0) {
			mv.addObject("method", "bussinesschance");
			TraderCustomer customerInfoByTraderCustomer = traderCustomerService.getCustomerInfoByTraderCustomer(traderCustomer);
			mv.addObject("customerInfoByTraderCustomer", customerInfoByTraderCustomer);
		}
		
		mv.setViewName("order/bussinesschance/sale_index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 批量分配商机
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月22日 下午4:14:49
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "assignbussinesschance")
	@SystemControllerLog(operationType = "edit",desc = "批量分配商机")
	public ResultInfo assignBussinessChance(HttpServletRequest request, @RequestParam(required = false, value="ids") String parameter, @RequestParam(required = false, value="nos") String bussinessChanceNos, BussinessChanceVo bussinessChanceVo) 
	{
		//String parameter = request.getParameter("ids");
		String[] parameterValues = parameter.split(",");
		List<Integer> ids = new ArrayList<Integer>();
		for (String v : parameterValues)
		{
			ids.add(Integer.parseInt(v));
		}
		bussinessChanceVo.setBussinessChanceIds(ids);
		
		String[] parameterNoValues = bussinessChanceNos.split(",");
		List<String> nos = new ArrayList<String>();
		for (String str : parameterNoValues)
		{
			nos.add(str);
		}
		bussinessChanceVo.setBussinessChanceNos(nos);
		
		ResultInfo resultInfo = bussinessChanceService.assignBussinessChance(bussinessChanceVo, request.getSession());
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增售后商机
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月23日 上午9:29:26
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "addServiceBussinessChance")
	public ModelAndView addServiceBussinessChance(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("order/bussinesschance/add_afterAalesBussinessChance");
		// 商机商品分类
		List<SysOptionDefinition> goodsTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_387);
		mav.addObject("goodsTypeList", goodsTypeList);

		// 商机来源
		List<SysOptionDefinition> scoureList = getSysOptionDefinitionList(SysOptionConstant.ID_365);
		mav.addObject("scoureList", scoureList);

		// 询价方式
		List<SysOptionDefinition> inquiryList = getSysOptionDefinitionList(SysOptionConstant.ID_376);
		mav.addObject("inquiryList", inquiryList);
		
		// 省级地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mav.addObject("provinceList", provinceList);

		// 查询所有职位类型为310的员工
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);//销售
		positionType.add(312);//市场
		positionType.add(311);//产品
		user.setPositType(0);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mav.addObject("userList", userList);
		BussinessChanceVo bcv = new BussinessChanceVo();
		bcv.setAttachmentDomain(bussinessChanceService.getUploadDomain());
		bcv.setReceiveTime(DateUtil.sysTimeMillis());
		mav.addObject("bussinessChanceVo", bcv);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增售后商机
	 * 
	 * @param request
	 * @param province
	 * @param city
	 * @param zone
	 * @param fileName
	 * @param time
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月26日 下午2:01:47
	 */
	@FormToken(remove=true)
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "saveAddServiceBussinessChance")
	@SystemControllerLog(operationType = "add",desc = "保存新增售后商机")
	public ModelAndView saveAddServiceBussinessChance(HttpSession session, Integer province, Integer city, Integer zone, String time, BussinessChance bussinessChance, Attachment attachment) 
	{
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		bussinessChance.setCompanyId(user.getCompanyId());
		ModelAndView mav = new ModelAndView();
		try 
		{
			if (province != 0 && city != 0 && zone != 0)
			{
				bussinessChance.setAreaId(zone);
				bussinessChance.setAreaIds(province + "," + city + "," + zone);
			}
			else if (province != 0 && city != 0 && zone == 0)
			{
				bussinessChance.setAreaId(city);
				bussinessChance.setAreaIds(province + "," + city);
			}
			else if (province != 0 && city == 0 && zone == 0)
			{
				bussinessChance.setAreaId(province);
				bussinessChance.setAreaIds(province.toString());
			}
			// modify by Franlin at 2018-07-31 for[581 短信发送机制：未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信] begin
			Long receiveTime = null;
			if (time != null && !"".equals(time))
			{
				receiveTime = DateUtil.convertLong(time, DateUtil.TIME_FORMAT);
			}
			if(null == receiveTime)
			{
				receiveTime = new Date().getTime();
			}
			bussinessChance.setReceiveTime(receiveTime);
			// modify by Franlin at 2018-07-31 for[581 短信发送机制：未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信] end
			bussinessChance.setType(SysOptionConstant.ID_391);// 商机类型:总机询价
			ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, attachment);
			if (null != rs && rs.getCode() == 0)
			{
				JSONObject json = JSONObject.fromObject(rs.getData());
				BussinessChance bc = (BussinessChance) JSONObject.toBean(json, BussinessChance.class);
				mav.addObject("url", "./toAfterSalesDetailPage.do?bussinessChanceId=" + bc.getBussinessChanceId());

				return success(mav);
			} else {
				return fail(mav);
			}
		} catch (Exception e) {
			logger.error("saveAddServiceBussinessChance:", e);
			return fail(mav);
		}
	}
	
	/**
	 * <b>Description:</b><br>
	 * 保存编辑售后商机
	 * 
	 * @param request
	 * @param province
	 * @param city
	 * @param zone
	 * @param fileName
	 * @param time
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月26日 下午2:01:47
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "saveEditServiceBussinessChance")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑售后商机")
	public ModelAndView saveEditServiceBussinessChance(HttpSession session, Integer province, Integer city, Integer zone,String beforeParams,
			String time, BussinessChance bussinessChance, Attachment attachment) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		try {
			if (province != 0 && city != 0 && zone != 0) {
				bussinessChance.setAreaId(zone);
				bussinessChance.setAreaIds(province + "," + city + "," + zone);
			} else if (province != 0 && city != 0 && zone == 0) {
				bussinessChance.setAreaId(city);
				bussinessChance.setAreaIds(province + "," + city);
			} else if (province != 0 && city == 0 && zone == 0) {
				bussinessChance.setAreaId(province);
				bussinessChance.setAreaIds(province.toString());
			}else{
				bussinessChance.setAreaId(0);
				bussinessChance.setAreaIds("");
			}
			if (time != null && !"".equals(time)) {
				bussinessChance.setReceiveTime(DateUtil.convertLong(time, DateUtil.TIME_FORMAT));
			}

			bussinessChance.setReceiveTime(DateUtil.convertLong(time, DateUtil.TIME_FORMAT));
			bussinessChance.setType(SysOptionConstant.ID_391);// 商机类型:总机询价
			ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, attachment);
			if (null != rs && rs.getCode() == 0) {
				JSONObject json = JSONObject.fromObject(rs.getData());
				BussinessChance bc = (BussinessChance) JSONObject.toBean(json, BussinessChance.class);
				mav.addObject("url", "./toAfterSalesDetailPage.do?bussinessChanceId=" + bc.getBussinessChanceId());

				return success(mav);
			} else {
				return fail(mav);
			}
		} catch (Exception e) {
			logger.error("saveEditServiceBussinessChance:", e);
			return fail(mav);
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 关闭售后商机
	 * 
	 * @param session
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午9:45:20
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "closeSreviceBussnessChance")
	@SystemControllerLog(operationType = "edit",desc = "关闭售后商机")
	public ResultInfo closeSreviceBussnessChance(HttpSession session, BussinessChance bussinessChance) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, null);
		if (rs.getCode() == 0) {
			rs.setData(bussinessChance.getBussinessChanceId());
		}
		return rs;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询售后商机详情
	 * 
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月27日 下午6:42:42
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "toAfterSalesDetailPage")
	public ModelAndView toAfterSalesDetailPage(BussinessChance bussinessChance, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/view_afterAalesBussinessChance");
		pageSize = 10;
		Page page = getPageTag(request, pageNo, pageSize);

		Map<String, Object> map = bussinessChanceService.getAfterSalesDetail(bussinessChance, page);
		if (map.containsKey("bussinessChanceVo")) {
			mav.addObject("bussinessChanceVo", (BussinessChanceVo) map.get("bussinessChanceVo"));
		}
		if (map.containsKey("communicateList")) {
			mav.addObject("communicateList", (List<CommunicateRecord>) map.get("communicateList"));
		}
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转编辑售后商机页面
	 * 
	 * @param bussinessChance
	 * @return
	 * @throws IOException 
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月27日 下午6:42:42
	 */
	@ResponseBody
	@RequestMapping(value = "toAfterSalesEditPage")
	public ModelAndView toAfterSalesEditPage(BussinessChance bussinessChance, HttpServletRequest request,HttpSession session) throws IOException {
		ModelAndView mav = new ModelAndView("order/bussinesschance/edit_afterAalesBussinessChance");
		// 商机商品分类
		List<SysOptionDefinition> goodsTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_387);
		mav.addObject("goodsTypeList", goodsTypeList);
		
		// 商机来源
		List<SysOptionDefinition> scoureList = getSysOptionDefinitionList(SysOptionConstant.ID_365);
		mav.addObject("scoureList", scoureList);

		// 询价方式
		List<SysOptionDefinition> inquiryList = getSysOptionDefinitionList(SysOptionConstant.ID_376);
		mav.addObject("inquiryList", inquiryList);
		
		// 省级地区
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mav.addObject("provinceList", provinceList);
		// 查询所有销售
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		List<User> userList = userService.getUserByPositType(SysOptionConstant.ID_310,user.getCompanyId());
		mav.addObject("userList", userList);
		BussinessChanceVo bcv = bussinessChanceService.toAfterSalesEditPage(bussinessChance);
		bcv.setAttachmentDomain(bussinessChanceService.getUploadDomain());
		mav.addObject("bussinessChanceVo", bcv);
		String areaIds = bcv.getAreaIds();
		if (areaIds != null && !"".equals(areaIds)) {
			String areaids[] = areaIds.split(",");
			if (areaids.length == 3) {
				Integer province = Integer.valueOf(areaids[0]);
				mav.addObject("province", province);
				// 市级地区
				List<Region> cityList = regionService.getRegionByParentId(province);
				mav.addObject("cityList", cityList);
				Integer city = Integer.valueOf(areaids[1]);
				mav.addObject("city", city);
				// 县区级地区
				List<Region> zoneList = regionService.getRegionByParentId(city);
				mav.addObject("zoneList", zoneList);
				Integer zone = Integer.valueOf(areaids[2]);
				mav.addObject("zone", zone);
			} else if (areaids.length == 2) {
				Integer province = Integer.valueOf(areaids[0]);
				mav.addObject("province", province);
				// 市级地区
				List<Region> cityList = regionService.getRegionByParentId(province);
				mav.addObject("cityList", cityList);
				Integer city = Integer.valueOf(areaids[1]);
				mav.addObject("city", city);
			} else if (areaids.length == 1) {
				Integer province = Integer.valueOf(areaids[0]);
				mav.addObject("province", province);
			}
		}
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bcv)));
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到关闭售后商机页面
	 * 
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月29日 下午5:20:25
	 */
	@ResponseBody
	@RequestMapping(value = "toCloseAfterSalesPage")
	public ModelAndView toCloseAfterSalesPage(BussinessChance bussinessChance) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/close_afterAalesBussinessChance");
		// 关闭商机原因
		List<SysOptionDefinition> closeList = getSysOptionDefinitionList(SysOptionConstant.ID_395);
		mav.addObject("closeList", closeList);
		
		mav.addObject("bussinessChance", bussinessChance);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 新增销售商机
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月23日 上午9:29:26
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "addSalesBussinessChance")
	public ModelAndView addSalesBussinessChance(TraderCustomer traderCustomer) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/add_salesBussinessChance");
		// 商机商品分类
		List<SysOptionDefinition> goodsTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_387);
		mav.addObject("goodsTypeList", goodsTypeList);
		
		// 询价方式
		List<SysOptionDefinition> scoureList = getSysOptionDefinitionList(SysOptionConstant.ID_376);
		mav.addObject("inquiryList", scoureList);
		
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(traderCustomer.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.ONE);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

		TraderCustomerVo traderCustomerVo = traderCustomerService.getTraderCustomerVo(traderCustomer);

		mav.addObject("traderCustomer", traderCustomerVo);
		mav.addObject("contactList", contactList);
		BussinessChanceVo bcv = new BussinessChanceVo();
		bcv.setReceiveTime(DateUtil.sysTimeMillis());
		mav.addObject("bussinessChanceVo", bcv);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存新增销售商机
	 * 
	 * @param session
	 * @param time
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 下午2:05:44
	 */
	@FormToken(remove=true)
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "saveAddSalesBussinessChance")
	@SystemControllerLog(operationType = "add",desc = "保存新增销售商机")
	public ModelAndView saveAddSalesBussinessChance(HttpSession session, String time, BussinessChance bussinessChance,
			TraderCustomer traderCustomer) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		bussinessChance.setCompanyId(user.getCompanyId());
		ModelAndView mav = new ModelAndView();
		try {
			if (time != null && !"".equals(time)) {
				bussinessChance.setReceiveTime(DateUtil.convertLong(time, DateUtil.TIME_FORMAT));
			}
			ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, null);
			if (null != rs && rs.getCode() == 0) {
				JSONObject json = JSONObject.fromObject(rs.getData());
				BussinessChance bc = (BussinessChance) JSONObject.toBean(json, BussinessChance.class);
				mav.addObject("url",
						"./toSalesDetailPage.do?bussinessChanceId=" + bc.getBussinessChanceId() + "&traderId="
								+ traderCustomer.getTraderId());
				return success(mav);
			} else {
				return fail(mav);
			}
		} catch (Exception e) {
			logger.error("saveAddSalesBussinessChance:", e);
			return fail(mav);
		}
	}
	
	/**
	 * <b>Description:</b><br>
	 * 保存编辑销售商机
	 * 
	 * @param session
	 * @param time
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 下午2:05:44
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "saveEditSalesBussinessChance")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑销售商机")
	public ModelAndView saveEditSalesBussinessChance(HttpSession session, String time, BussinessChance bussinessChance,String beforeParams,
			TraderCustomer traderCustomer) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView();
		try {
			if (time != null && !"".equals(time)) {
				bussinessChance.setReceiveTime(DateUtil.convertLong(time, DateUtil.TIME_FORMAT));
			}
			ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, null);
			if (null != rs && rs.getCode() == 0) {
				JSONObject json = JSONObject.fromObject(rs.getData());
				BussinessChance bc = (BussinessChance) JSONObject.toBean(json, BussinessChance.class);
				mav.addObject("url",
						"./toSalesDetailPage.do?bussinessChanceId=" + bc.getBussinessChanceId() + "&traderId="
								+ traderCustomer.getTraderId());
				return success(mav);
			} else {
				return fail(mav);
			}
		} catch (Exception e) {
			logger.error("saveEditSalesBussinessChance:", e);
			return fail(mav);
		}
	}


	/**
	 * <b>Description:</b><br>
	 * 查询销售商机详情
	 * 
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月27日 下午6:42:42
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "toSalesDetailPage")
	public ModelAndView toSalesDetailPage(BussinessChance bussinessChance, TraderCustomer traderCustomer,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false, defaultValue = "0") Integer messageId) 
	{
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		// modify by Franlin at 2018-08-13 for[4508 从消息弹框点进去查看该消息，仍是未读状态] begin
		if(null != messageId && 0 != messageId)
		{
			try
			{
				MessageUser messageUser = new MessageUser();
				messageUser.setIsView(1);// 已读
				if(null != user)
				{					
					messageUser.setUserId(user.getUserId());
				}
				messageUser.setMessageId(messageId);
				messageService.modifyMessageViewStatus(messageUser);
			}
			catch(Exception e)
			{
				logger.error("toSalesDetailPage:", e);
			}
		}
		// modify by Franlin at 2018-08-13 for[4508 从消息弹框点进去查看该消息，仍是未读状态] end
		
		ModelAndView mav = new ModelAndView("order/bussinesschance/view_salesBussinessChance");
		pageSize = 20;
		Page page = getPageTag(request, pageNo, pageSize);
		
		Map<String, Object> map = bussinessChanceService.getAfterSalesDetail(bussinessChance, page);
		if (map.containsKey("bussinessChanceVo")) {
			BussinessChanceVo bcv = (BussinessChanceVo) map.get("bussinessChanceVo");
			if (bcv.getFirstViewTime() == 0) {// 保存第一次访问商机详情时间
				BussinessChance bc = new BussinessChance();
				bc.setBussinessChanceId(bussinessChance.getBussinessChanceId());
				bc.setFirstViewTime(DateUtil.sysTimeMillis());
				ResultInfo rs = bussinessChanceService.saveBussinessChance(bc, user, null);
			}
			mav.addObject("bussinessChanceVo", bcv);
			if ((traderCustomer.getTraderCustomerId() == null || traderCustomer.getTraderCustomerId() == 0)
					&& (bcv.getTraderId() != null && bcv.getTraderId() != 0)) {
				TraderCustomerVo traderCustomerVo = traderCustomerService.getCustomerBussinessInfo(bcv.getTraderId());
				mav.addObject("traderCustomer", traderCustomerVo);
			}else{
				mav.addObject("traderCustomer", traderCustomer);
			}
		}
		if (map.containsKey("communicateList")) {
			mav.addObject("communicateList", (List<CommunicateRecord>) map.get("communicateList"));
		}
		
		Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "closeBussinesschanceVerify_"+ bussinessChance.getBussinessChanceId());
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
		mav.addObject("page", page);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转编辑销售商机页面
	 * 
	 * @param bussinessChance
	 * @return
	 * @throws IOException 
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月27日 下午6:42:42
	 */
	@ResponseBody
	@RequestMapping(value = "toSalesEditPage")
	public ModelAndView toSalesEditPage(BussinessChance bussinessChance, TraderCustomer traderCustomer) throws IOException {
		ModelAndView mav = new ModelAndView("order/bussinesschance/edit_salesBussinessChance");
		// 商机商品分类
		List<SysOptionDefinition> goodsTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_387);
		mav.addObject("goodsTypeList", goodsTypeList);
		
		// 询价方式
		List<SysOptionDefinition> scoureList = getSysOptionDefinitionList(SysOptionConstant.ID_376);
		mav.addObject("inquiryList", scoureList);

		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(traderCustomer.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.ONE);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

		// traderCustomer=traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
		TraderCustomerVo traderCustomerVo = traderCustomerService.getTraderCustomerVo(traderCustomer);

		mav.addObject("traderCustomer", traderCustomerVo);
		mav.addObject("contactList", contactList);

		BussinessChanceVo bcv = bussinessChanceService.toAfterSalesEditPage(bussinessChance);
		mav.addObject("bussinessChanceVo", bcv);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bcv)));
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到编辑商机备注页面
	 * 
	 * @param bussinessChance
	 * @return
	 * @throws IOException 
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月29日 下午5:20:25
	 */
	@ResponseBody
	@RequestMapping(value = "editCommentsPage")
	public ModelAndView editCommentsPage(BussinessChance bussinessChance) throws IOException {
		ModelAndView mav = new ModelAndView("order/bussinesschance/edit_comments");
		String comments = URLDecoder.decode(URLDecoder.decode(bussinessChance.getComments(), "UTF-8"), "UTF-8");
		bussinessChance.setComments(comments);
		mav.addObject("bussinessChance", bussinessChance);
		mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(bussinessChance)));
		return mav;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 保存销售商机备注
	 * 
	 * @param session
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午9:45:20
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "saveSalesBussnessChanceComments")
	@SystemControllerLog(operationType = "edit",desc = "保存销售商机备注")
	public ResultInfo saveSalesBussnessChanceComments(HttpSession session, BussinessChance bussinessChance,String beforeParams) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		bussinessChance.setOrgId(user.getOrgId());
		ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, null);
		if (rs.getCode() == 0) {
			rs.setData(bussinessChance.getBussinessChanceId());
		}
		return rs;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 跳转到关闭销售商机页面
	 * 
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月29日 下午5:20:25
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "toCloseSalesPage")
	public ModelAndView toCloseSalesPage(BussinessChance bussinessChance, TraderCustomer traderCustomer,String taskId) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/close_salesBussinessChance");
		// 关闭商机原因
		List<SysOptionDefinition> closeList = getSysOptionDefinitionList(SysOptionConstant.ID_395);
		// 商机作废原因
		List<SysOptionDefinition> zfList = getSysOptionDefinitionList(SysOptionConstant.ID_961);
		mav.addObject("zfList", zfList);
		mav.addObject("closeList", closeList);
		mav.addObject("taskId", taskId);
		mav.addObject("bussinessChance", bussinessChance);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 关闭销售商机
	 * 
	 * @param session
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午9:45:20
	 */
	@FormToken(remove=true)
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "closeSalesBussnessChance")
	@SystemControllerLog(operationType = "edit",desc = "关闭销售商机")
	public ResultInfo closeSalesBussnessChance(HttpServletRequest request,HttpSession session, BussinessChance bussinessChance,String taskId) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		//走审核流程,原处理过程取消
		bussinessChance.setOrgId(user.getOrgId());
		//bussinessChance.setStatus(4);//关闭---由审核流程确认关闭
		ResultInfo rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, null);
		Map<String, Object> paraMap = bussinessChanceService.getAfterSalesDetail(bussinessChance, null);
		BussinessChanceVo bussinessChanceVo = (BussinessChanceVo) paraMap.get("bussinessChanceVo");
		bussinessChance.setBussinessChanceNo(bussinessChanceVo.getBussinessChanceNo());
		bussinessChance.setTraderId(bussinessChanceVo.getTraderId());
		if (rs.getCode() == 0) {
			rs.setData(bussinessChance.getBussinessChanceId());
		}
			try {
	    		Map<String, Object> variableMap = new HashMap<String, Object>();
	    		//开始生成流程(如果没有taskId表示新流程需要生成)
	    		if(StringUtils.isBlank(taskId) || taskId.equals("0")){
	    		    variableMap.put("bussinessChance", bussinessChance);
	    		    variableMap.put("currentAssinee", user.getUsername());
	    		    variableMap.put("processDefinitionKey","closeBussinesschanceVerify");
	    		    variableMap.put("businessKey","closeBussinesschanceVerify_" + bussinessChance.getBussinessChanceId());
	    		    variableMap.put("relateTableKey",bussinessChance.getBussinessChanceId());
	    		    variableMap.put("relateTable", "T_BUSSINESS_CHANCE");
	    		    variableMap.put("orgId", user.getOrgId());	
	    		    actionProcdefService.createProcessInstance(request,"closeBussinesschanceVerify","closeBussinesschanceVerify_" + bussinessChance.getBussinessChanceId(),variableMap);
	    		}
	    		//默认申请人通过
	    		//根据BusinessKey获取生成的审核实例
	    		Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "closeBussinesschanceVerify_"+ bussinessChance.getBussinessChanceId());
        	    		if(historicInfo.get("endStatus") != "审核完成"){
        	    		Task taskInfo = (Task) historicInfo.get("taskInfo");
        	    		taskId = taskInfo.getId();
        	    		Authentication.setAuthenticatedUserId(user.getUsername());
        	    		Map<String, Object> variables = new HashMap<String, Object>();
        	    		//设置审核完成监听器回写参数
        	    		variables.put("tableName", "T_BUSSINESS_CHANCE");
        	    		variables.put("id", "BUSSINESS_CHANCE_ID");
        	    		variables.put("idValue", bussinessChance.getBussinessChanceId());
        	    		variables.put("key", "STATUS");
        	    		//关闭
        	    		variables.put("value", 4);
        	    		//回写数据的表在db中
        	    		variables.put("db", 2);
        	    		//默认审批通过
        	    		ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,"",user.getUsername(),variables);
        	    		//如果未结束添加审核对应主表的审核状态
                		if(!complementStatus.getData().equals("endEvent")){
                		    verifiesRecordService.saveVerifiesInfo(taskId,0);
                		}
	    		}
				return  new ResultInfo<>(0,"操作成功",bussinessChance.getBussinessChanceId());
			} catch (Exception e) {
				logger.error("closeSalesBussnessChance:", e);
				return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
			}
		
	}

	/**
	 * <b>Description:</b><br>
	 * 确认客户页面
	 * 
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 下午4:52:49
	 */
	@ResponseBody
	@RequestMapping(value = "confirmCustomer")
	public ModelAndView confirmCustomer(BussinessChance bussinessChance, TraderCustomer traderCustomer) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/confirm_customer_index");
		mav.addObject("bussinessChance", bussinessChance);
		mav.addObject("traderCustomer", traderCustomer);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 确认客户页面搜索客户
	 * 
	 * @param customerName
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 下午5:26:47
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getCustomersByName")
	public ModelAndView getCustomersByName(String customerName, HttpServletRequest request,
			BussinessChance bussinessChance, TraderCustomer traderCustomer,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/confirm_customer_index");
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Page page = getPageTag(request, pageNo, 10);
		TraderCustomerVo tcv = new TraderCustomerVo();
		tcv.setName(customerName);
		tcv.setCompanyId(user.getCompanyId());
		//搜索只能搜索当前名下的客户
		// 查询所有职位类型为310的员工
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);//销售
		List<User> userList = userService.getMyUserList(user, positionType, false);
//		tcv.setTraderList(userService.getTraderIdListByUserList(userList, ErpConst.ONE.toString()));
		
		Map<String, Object> map = traderCustomerService.getTraderCustomerVoPage(tcv, page,userList);
		List<TraderCustomerVo> list = (List<TraderCustomerVo>) map.get("list");
		page = (Page) map.get("page");
		mav.addObject("list", list);
		mav.addObject("page", page);
		mav.addObject("customerName", customerName);
		mav.addObject("traderCustomer", traderCustomer);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 跳转到选择后确认客户页面
	 * 
	 * @param bussinessChance
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月3日 下午2:29:56
	 */
	@ResponseBody
	@RequestMapping(value = "/viewConfirmCustomer")
	public ModelAndView viewConfirmCustomer(BussinessChance bussinessChance, TraderCustomer traderCustomer) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/view_confirm_customer");
		TraderContact traderContact = new TraderContact();
		// 联系人
		traderContact.setTraderId(traderCustomer.getTraderId());
		traderContact.setIsEnable(ErpConst.ONE);
		traderContact.setTraderType(ErpConst.ONE);
		List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

		// traderCustomer=traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
		TraderCustomerVo traderCustomerVo = traderCustomerService.getTraderCustomerVo(traderCustomer);
		mav.addObject("bussinessChance", bussinessChance);
		mav.addObject("traderCustomer", traderCustomerVo);
		mav.addObject("contactList", contactList);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 确认客户页面添加联系人
	 * 
	 * @param bussinessChance
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月3日 下午4:05:19
	 */
	@ResponseBody
	@RequestMapping(value = "/addConfirmCustomer")
	public ModelAndView addConfirmCustomer(BussinessChance bussinessChance, TraderCustomer traderCustomer) {
		ModelAndView mav = new ModelAndView("order/bussinesschance/add_confirm_customer");
		TraderCustomerVo traderCustomerVo = traderCustomerService.getTraderCustomerVo(traderCustomer);
		mav.addObject("bussinessChance", bussinessChance);
		mav.addObject("traderCustomer", traderCustomerVo);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存确认后的客户信息,并返回客户的主键id
	 * 
	 * @param bussinessChance
	 * @param session
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 下午5:35:35
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "saveConfirmCustomer")
	@SystemControllerLog(operationType = "edit",desc = "保存确认后的客户信息")
	public ResultInfo saveConfirmCustomer(BussinessChance bussinessChance, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		TraderCustomerVo tcv = bussinessChanceService.saveConfirmCustomer(bussinessChance, user, null);
		ResultInfo rs = null;
		if (tcv != null) {
			rs = new ResultInfo(0, "操作成功！",
					bussinessChance.getBussinessChanceId() + "," + tcv.getTraderId() + "," + tcv.getTraderCustomerId());
		} else {
			rs = new ResultInfo(1, "操作失败！");
		}
		return rs;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存确认后的客户信息,并返回客户的主键id,新增客户联系人
	 * 
	 * @param bussinessChance
	 * @param session
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 下午5:35:35
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "addSaveConfirmCustomer")
	@SystemControllerLog(operationType = "edit",desc = "保存确认后的客户信息，新增客户联系人")
	public ResultInfo saveConfirmCustomer(BussinessChance bussinessChance, TraderContact traderContact,
			HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		TraderCustomerVo tcv = bussinessChanceService.saveConfirmCustomer(bussinessChance, user, traderContact);
		ResultInfo rs = null;
		if (tcv != null) {
			if(tcv.getTraderCustomerId() == -1){
				rs = new ResultInfo(-1, "该客户已存在相同联系人");
			}else{
				rs = new ResultInfo(0, "操作成功！",
						bussinessChance.getBussinessChanceId() + "," + tcv.getTraderId() + "," + tcv.getTraderCustomerId());
			}
		} else {
			rs = new ResultInfo(1, "操作失败！");
		}
		return rs;
	}

	/**
	 * <b>Description:</b><br>
	 * 销售新增沟通记录
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月30日 上午10:17:31
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "addCommunicatePage")
	public ModelAndView addCommunicatePage(BussinessChance bussinessChance, TraderCustomer traderCustomer,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mav = new ModelAndView("order/bussinesschance/add_communicate");
		TraderContact traderContact = new TraderContact();
		// 联系人
		List<TraderContact> contactList = new ArrayList<>();
		if(traderCustomer.getTraderId()!=null && traderCustomer.getTraderId()!=0){
		    traderContact.setTraderId(traderCustomer.getTraderId());
	        traderContact.setIsEnable(ErpConst.ONE);
	        traderContact.setTraderType(ErpConst.ONE);
	        contactList = traderCustomerService.getTraderContact(traderContact);
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

		mav.addObject("traderCustomer", traderCustomer);
		mav.addObject("bussinessChance", bussinessChance);
		mav.addObject("contactList", contactList);
		
		CommunicateRecord communicate = new CommunicateRecord();
		communicate.setBegintime(DateUtil.sysTimeMillis());
		communicate.setEndtime(DateUtil.sysTimeMillis()+2*60*1000);
		mav.addObject("communicateRecord", communicate);
		// 沟通方式
		List<SysOptionDefinition> communicateList = getSysOptionDefinitionList(SysOptionConstant.ID_23);
		mav.addObject("communicateList", communicateList);
		//当前时间
		mav.addObject("nowDate", DateUtil.subDateByDays(new Date(System.currentTimeMillis()), 1).getTime());
		//15天后的时间
        mav.addObject("hideDate", DateUtil.subDateByDays(new Date(System.currentTimeMillis()), 30).getTime());
		mav.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mav.addObject("page", (Page) tagMap.get("page"));
		return mav;
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
	@ResponseBody
	@RequestMapping(value = "/editcommunicate")
	public ModelAndView editCommunicate(CommunicateRecord communicateRecord, TraderCustomer traderCustomer,
			BussinessChance bussinessChance, HttpServletRequest request, HttpSession session) throws IOException {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("order/bussinesschance/edit_communicate");
		CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
		communicate.setTraderCustomerId(communicateRecord.getTraderCustomerId());
		communicate.setTraderId(communicateRecord.getTraderId());

		TraderContact traderContact = new TraderContact();
		List<TraderContact> contactList = new ArrayList<>();
		// 联系人
		if(communicateRecord.getTraderId()!=0){
			traderContact.setTraderId(communicateRecord.getTraderId());
			traderContact.setIsEnable(ErpConst.ONE);
			traderContact.setTraderType(ErpConst.ONE);
			contactList = traderCustomerService.getTraderContact(traderContact);
		}else {
			traderContact.setTraderContactId(0);
			traderContact.setIsEnable(ErpConst.ONE);
			traderContact.setName(communicate.getContact());
			traderContact.setMobile(communicate.getContactMob());
			contactList.add(traderContact);
		}

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
		//当前时间
		mv.addObject("nowDate", DateUtil.subDateByDays(new Date(System.currentTimeMillis()), 1).getTime());
        //15天后的时间
		mv.addObject("hideDate", DateUtil.subDateByDays(new Date(System.currentTimeMillis()), 30).getTime());
		mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
		mv.addObject("page", (Page) tagMap.get("page"));
		mv.addObject("method", "communicaterecord");
		mv.addObject("traderCustomer", traderCustomer);
		mv.addObject("bussinessChance", bussinessChance);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(communicate)));
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
	@FormToken(remove=true)
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = "/saveaddcommunicate")
	@SystemControllerLog(operationType = "add",desc = "保存商机新增沟通记录")
	public ResultInfo saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			HttpSession session) throws Exception {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Boolean record = false;
		communicateRecord.setCompanyId(user.getCompanyId());
		communicateRecord.setCommunicateType(SysOptionConstant.ID_244);// 询价
		communicateRecord.setRelatedId(communicateRecord.getBussinessChanceId());
		if (null != communicateRecord.getCommunicateRecordId() && communicateRecord.getCommunicateRecordId() > 0) {
			record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
		} else {
			record = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
		}
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getBussinessChanceId() + "," + communicateRecord.getTraderId());
		} else {
			return new ResultInfo(1, "操作失败！");
		}

	}
	
	/**
	 * <b>Description:</b><br>
	 * 保存商机编辑沟通记录
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
	@SystemControllerLog(operationType = "edit",desc = "保存商机编辑沟通记录")
	public ResultInfo saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,String beforeParams,
			HttpSession session) throws Exception {
		Boolean record = false;
		communicateRecord.setCommunicateType(SysOptionConstant.ID_244);// 询价
		communicateRecord.setRelatedId(communicateRecord.getBussinessChanceId());
		if (null != communicateRecord.getCommunicateRecordId() && communicateRecord.getCommunicateRecordId() > 0) {
			record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
		} else {
			record = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
		}
		if (record) {
			return new ResultInfo(0, "操作成功！", communicateRecord.getBussinessChanceId() + "," + communicateRecord.getTraderId());
		} else {
			return new ResultInfo(1, "操作失败！");
		}

	}
	
	/**
	 * <b>Description:</b><br> 确认审核页面
	 * @param session
	 * @param taskId
	 * @param pass
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月3日 下午4:27:02
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session, String taskId, Boolean pass) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.setViewName("order/bussinesschance/complement");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	@SystemControllerLog(operationType = "edit",desc = "商机审核操作")
	public ResultInfo<?> complementTask(HttpServletRequest request, String taskId, String comment,String reason,Boolean pass,
			HttpSession session) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("reason", reason);
		//审批操作
		try {
		    	Integer status = 0;
			if(pass){
			    //如果审核通过
			     status = 0;
			}else{
			    //如果审核不通过
			    status = 2;
			    verifiesRecordService.saveVerifiesInfo(taskId,status);	
			}
			
		    User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		    ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,comment,user.getUsername(),variables);
		    //如果未结束添加审核对应主表的审核状态
    		    if(!complementStatus.getData().equals("endEvent")){
    		    verifiesRecordService.saveVerifiesInfo(taskId,status);	
    		    }
		    return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("business chnace complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}
	}
	/**
     * 
     * <b>Description:</b>新增联系人
     * @param bussinessChance
     * @param traderCustomer
     * @param request
     * @return ModelAndView
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月1日 上午9:30:51
     */
    //@FormToken(save=true)
    @ResponseBody
    @RequestMapping(value = "/addTraderContact")
    public ModelAndView addTraderContact(BussinessChance bussinessChance, TraderCustomer traderCustomer,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 联系人
        if(traderCustomer.getTraderId()!=null && traderCustomer.getTraderId()!=0){
            //查询客户信息
            mv.setViewName("trader/customer/add_contact");
            mv.addObject("traderCustomer", traderCustomer);
        }else{
            mv.setViewName("order/bussinesschance/add_contact");
        }
        return mv;
    }
    /**
     * 
     * <b>Description:</b>更新商机情况（新增/编辑）
     * @param bussinessChance
     * @param request
     * @return ModelAndView
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月4日 下午12:59:56
     */
    @FormToken(save=true)
    @ResponseBody
    @RequestMapping(value = "/addBussinessStatus")
    public ModelAndView addBussinessStatus(BussinessChance bussinessChance,Integer type,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        BussinessChance bc = bussinessChanceService.getBussinessChanceInfo(bussinessChance);
        //商机等级
        List<SysOptionDefinition> bussinessLevelList = getSysOptionDefinitionList(SysOptionConstant.ID_938);
        mv.addObject("bussinessLevelList", bussinessLevelList);
        //商机阶段
        List<SysOptionDefinition> bussinessStageList = getSysOptionDefinitionList(SysOptionConstant.ID_943);
        mv.addObject("bussinessStageList", bussinessStageList);
        //询价类型
        List<SysOptionDefinition> enquiryTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_948);
        mv.addObject("enquiryTypeList", enquiryTypeList);
        //成单机率
        List<SysOptionDefinition> orderRateList = getSysOptionDefinitionList(SysOptionConstant.ID_951);
        mv.addObject("orderRateList", orderRateList);
        //当前时间
        mv.addObject("nowDate", DateUtil.subDateByDays(new Date(System.currentTimeMillis()), 1).getTime());
        
        mv.addObject("bussinessChance", bc);
        mv.addObject("type", type);
        mv.setViewName("order/bussinesschance/add_bussinessStatus");
        return mv;
    }
    /**
     * 
     * <b>Description:</b>更新的商机信息
     * @param bussinessChance
     * @param request
     * @param session
     * @return
     * @throws Exception ResultInfo
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月4日 下午2:07:22
     */
    @FormToken(remove=true)
    @ResponseBody
    @RequestMapping(value = "/saveAddBussinessStatus")
    public ResultInfo saveAddBussinessStatus(BussinessChance bussinessChance, HttpServletRequest request,
            HttpSession session) throws Exception {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Boolean record = false;
        if(StringUtils.isNotBlank(bussinessChance.getOrderTimeStr())){
        	bussinessChance.setOrderTime(DateUtil.convertLong(bussinessChance.getOrderTimeStr() + " 00:00:00", ""));
        }
        record = bussinessChanceService.saveAddBussinessStatus(bussinessChance);
        if (record) {
            return new ResultInfo(0, "操作成功！",bussinessChance);
        } else {
            return new ResultInfo(1, "操作失败！");
        }

    }
}
