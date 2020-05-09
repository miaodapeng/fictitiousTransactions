package com.vedeng.saleperformance.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.saleperformance.model.*;
import com.vedeng.saleperformance.model.vo.SalesPerformanceDeptUserVo;
import com.vedeng.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.UserVo;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.saleperformance.cache.SalesFiveCacheCommonUtil;
import com.vedeng.saleperformance.model.vo.RSalesPerformanceGroupJConfigVo;
import com.vedeng.saleperformance.model.vo.SalesPerformanceGroupVo;
import com.vedeng.saleperformance.service.SalesPerformanceService;
import com.vedeng.system.service.OrgService;

/**
 * <b>Description:</b><br> 
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp-sale
 * <br><b>PackageName:</b> com.vedeng.saleperformance.controller
 * <br><b>ClassName:</b> SalesPerformanceController
 * <br><b>Date:</b> 2018年6月4日 上午10:44:17
 */
@Controller
@RequestMapping("/sales/salesperformance")
public class SalesPerformanceController extends BaseController{
	@Autowired
	@Qualifier("salesPerformanceService")
	private SalesPerformanceService salesPerformanceService;
	
	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;// 自动注入orgService
	
	@Resource
	private UserService userService;
	//注入request
	@Autowired
	private HttpServletRequest request;

	/**
	 * 
	 * <b>Description:</b><br> 获取部门设置数据
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月4日 下午4:01:06
	 */
/*	@ResponseBody
	@RequestMapping(value = "getGroupConfigSetData")
	public ModelAndView task(HttpServletRequest request,HttpServletResponse response) {
		//SalesPerformanceConfig salesPerformanceConfig=new SalesPerformanceConfig();
		 ModelAndView mv = new ModelAndView();
		 //List<SalesPerformanceConfig> configList=null;
		 Map<String,Object> map=new HashMap<String,Object>();
		//查询列表
		 map=(Map<String, Object>) salesPerformanceService.getGroupConfigSetData();
		 mv.addObject("configList",map.get("configList"));
		 mv.addObject("groupList",map.get("groupList"));
		 mv.addObject("groupJConfigList",map.get("groupJConfigList"));
		// 模板赋值
		 mv.setViewName("saleperformance/configSet/configSetList");
		 return mv;
	}*/
	
	/**
	 * 启用或者禁用分组
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@ResponseBody
	@RequestMapping(value = "changeEnable")
	@SystemControllerLog(operationType = "edit",desc = "启用或者禁用分组")
	public ResultInfo<?> changeEnable(HttpServletRequest request,SalesPerformanceGroup salesPerformanceGroup,HttpServletResponse response) {
		try {
			//ModelAndView mv = new ModelAndView();
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			if(user!=null){
				salesPerformanceGroup.setUpdater(user.getUserId());
				salesPerformanceGroup.setModTime(DateUtil.sysTimeMillis());
				salesPerformanceGroup.setCompanyId(user.getCompanyId());
			}
			if(salesPerformanceGroup==null){
				return new ResultInfo<>(-1, "参数为空");
			}
			ResultInfo<?> openOrClose = salesPerformanceService.openOrClose(salesPerformanceGroup);
			return openOrClose;
		} catch (Exception e) {
			logger.error("changeEnable:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 * 
	 * <b>Description:</b><br> 获取部门编辑数据
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月4日 下午4:01:06
	 */
	@ResponseBody
	@RequestMapping(value = "getOneGroupConfigSetData")
	public ModelAndView getOneGroupConfigSetData(HttpServletRequest request,
	                                             SalesPerformanceGroupVo salesPerformanceGroupVo,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();

		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		
		if ( ObjectUtils.allNotNull(user) ) {
			salesPerformanceGroupVo.setCompanyId(user.getCompanyId());
		}
		
		List<SalesPerformanceGroupVo> allGroup = salesPerformanceService.getAllGroup(salesPerformanceGroupVo);
		if ( ObjectUtils.allNotNull(allGroup) ) {
			//会查出来一条数据
			SalesPerformanceGroupVo groupInfo = allGroup.get(0);
			mv.addObject("groupVo", groupInfo );
			mv.addObject("configVoList",groupInfo.getConfigList());
		}
		
		
		List<User> allUser = userService.getAllUser(user);
		mv.addObject("allUser",allUser);
		
		// 模板赋值
		mv.setViewName("saleperformance/configSet/editConfigSet");
		return mv;
	}
	/**
	 * 保存部门设置信息
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "saveOrUpdateConfigSetData")
	public ResultInfo<?> saveOrUpdateConfigSetData(HttpServletRequest request,SalesPerformanceGroupVo groupVo,HttpServletResponse response) {
		try {
			//ModelAndView mv = new ModelAndView();
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			List<RSalesPerformanceGroupJConfigVo> configVoList=groupVo.getConfigVoList();
			if(user!=null){
				groupVo.setUpdater(user.getUserId());
				groupVo.setModTime(DateUtil.sysTimeMillis());
				groupVo.setCompanyId(user.getCompanyId());
				groupVo.setAddTime(DateUtil.sysTimeMillis());
				groupVo.setCreator(user.getUserId());
				if(configVoList!=null&&configVoList.size()>0){
					for(int i=0;i<configVoList.size();i++){
						configVoList.get(i).setAddTime(DateUtil.sysTimeMillis());
						configVoList.get(i).setCompanyId(user.getCompanyId());
						configVoList.get(i).setCreator(user.getUserId());
						configVoList.get(i).setModTime(DateUtil.sysTimeMillis());
						configVoList.get(i).setUpdater(user.getUserId());
					}
				}
			}
			if(groupVo==null){
				return new ResultInfo<>(-1, "参数为空");
			}
			ResultInfo<?> configSet = salesPerformanceService.saveOrUpdateConfigSetData(groupVo);
			return configSet;
		} catch (Exception e) {
			logger.error("saveOrUpdateConfigSetData:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 * 获取当前分组下的品牌列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月6日 上午11:47:36
	 */
	@ResponseBody
	@RequestMapping(value = "getBrandConfigListPage")
	public ModelAndView getBrandConfigListPage(HttpServletRequest request, SalesPerformanceBrandConfig salesPerformanceBrandConfig,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		//置入分公司id用以区分数据
		salesPerformanceBrandConfig.setCompanyId(user.getCompanyId());
		//去除前後空格
		if(null!=salesPerformanceBrandConfig.getBrandName()){
			salesPerformanceBrandConfig.setBrandName(salesPerformanceBrandConfig.getBrandName().trim());
		}
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request, pageNo, pageSize);
		Map<String,Object> map =salesPerformanceService.getBrandConfigListPage(request, salesPerformanceBrandConfig, page, session);
		mv.addObject("groupName", map.get("groupName"));
		mv.addObject("brandConfigList",map.get("brandConfigList"));
		mv.addObject("page",(Page)map.get("page"));
		mv.addObject("groupId",salesPerformanceBrandConfig.getSalesPerformanceGroupId());
		mv.setViewName("saleperformance/configSet/brandConfigList");
		return mv;
	}
	/**
	 * 启用或者禁用品牌
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@ResponseBody
	@RequestMapping(value = "changeBrandEnable")
	@SystemControllerLog(operationType = "edit",desc = "启用或者禁用品牌")
	public ResultInfo<?> changeBrandEnable(HttpServletRequest request,SalesPerformanceBrandConfig brandConfig,HttpServletResponse response) {
		try {
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			if(user!=null){
				brandConfig.setUpdater(user.getUserId());
				brandConfig.setModTime(DateUtil.sysTimeMillis());
				brandConfig.setCompanyId(user.getCompanyId());
			}
			if(brandConfig==null){
				return new ResultInfo<>(-1, "参数为空");
			}
			ResultInfo<?> openOrClose = salesPerformanceService.changeBrandEnable(brandConfig);
			return openOrClose;
		} catch (Exception e) {
			logger.error("changeBrandEnable:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 *  获取新增品牌的品牌列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月6日 上午11:47:36
	 */
	@ResponseBody
	@RequestMapping(value = "getBrandListPage")
	public ModelAndView getBrandListPage(HttpServletRequest request, SalesPerformanceBrand salesPerformanceBrand,String groupId,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		//置入分公司id用以区分数据
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		salesPerformanceBrand.setCompanyId(user.getCompanyId());
		ModelAndView mv = new ModelAndView();
		Map<String,Object> map =null;
		//如果有查询条件传过来则走后台查询
		if(null!=salesPerformanceBrand.getBrandName()){
			//去除查询条件前后空格
			salesPerformanceBrand.setBrandName(salesPerformanceBrand.getBrandName().trim());
			Page page = getPageTag(request, pageNo, pageSize);
			//进入查询
			map=salesPerformanceService.getBrandListPage(request, salesPerformanceBrand,groupId, page, session);
			mv.addObject("brandList",map.get("brandList"));
			mv.addObject("page",(Page)map.get("page"));
			mv.addObject("groupId",groupId);
			mv.addObject("searchContent",salesPerformanceBrand.getBrandName());
			mv.setViewName("saleperformance/configSet/addBrandList");
		}else{
			//没有查询条件直接跳转页面
			mv.addObject("groupId",groupId);
			mv.setViewName("saleperformance/configSet/addBrandList");
		}
		return mv;
	}
	/**
	 * 新增品牌
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@ResponseBody
	@RequestMapping(value = "addBrandConfig")
	public ResultInfo<?> addBrandConfig(HttpServletRequest request,String brandIds,Integer groupId,HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String, Object>();
		SalesPerformanceBrandConfig brandConfig=new SalesPerformanceBrandConfig();
		try {
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			if(user!=null){
				brandConfig.setCompanyId(user.getCompanyId());
				brandConfig.setSalesPerformanceGroupId(groupId);
				brandConfig.setUpdater(user.getUserId());
				brandConfig.setCreator(user.getUserId());
				brandConfig.setAddTime(DateUtil.sysTimeMillis());
				brandConfig.setModTime(DateUtil.sysTimeMillis());
			}
			if(brandIds==null){
				return new ResultInfo<>(-1, "参数为空");
			}
			//处理数据
			List<Integer> list=new ArrayList<>();
			String[] array=brandIds.split(",");
			for(int i=0;i<array.length;i++){
				list.add(Integer.parseInt(array[i]));
			}
			map.put("list", list);
			map.put("brandConfig", brandConfig);
			ResultInfo<?> addBrandConfig = salesPerformanceService.addBrandConfig(map);
			return addBrandConfig;
		} catch (Exception e) {
			logger.error("addBrandConfig:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 * 获取当前分组下的人员列表以及年月度目标
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月6日 上午11:47:36
	 */
	@ResponseBody
	@RequestMapping(value = "getUserConfigListPage")
	public ModelAndView getUserConfigListPage(HttpServletRequest request, SalesPerformanceGroup salesPerformanceGroup,HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		salesPerformanceGroup.setCompanyId(user.getCompanyId());
		//user.getCompanyId();
		ModelAndView mv = new ModelAndView();
		Map<String,Object> map =salesPerformanceService.getUserConfigList(request, salesPerformanceGroup, session);
		//List<SalesPerformanceGroupVo> groupUserList=map.get
		mv.addObject("groupId",salesPerformanceGroup.getSalesPerformanceGroupId());
		mv.addObject("groupName", map.get("groupName"));
		mv.addObject("groupUserList",map.get("list"));
		// 当前月份
		mv.addObject("month", DateUtil.getNowMonth() - 1);
		mv.setViewName("saleperformance/configSet/userConfigList");
		return mv;
	}
	
	/**
	 * 获取新增人员页面的人员列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月6日 上午11:47:36
	 */
	@ResponseBody
	@FormToken(save = true)
	@RequestMapping(value = "getUserListPage")
	public ModelAndView getUserListPage(HttpServletRequest request, UserVo userVo,Integer flag,Integer salesPerformanceDeptId,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpSession session){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		userVo.setCompanyId(user.getCompanyId());
		List<UserVo> userVoList=new ArrayList<UserVo>();
		Page page = getPageTag(request, pageNo, pageSize);
		ModelAndView mv = new ModelAndView();
		List<Organization> orgList = orgService.getOrgList(0, user.getCompanyId(), true);
		/*mv.addObject("groupId",groupId);*/
		mv.addObject("orgList", orgList);
		if(userVo.getUsername()!=null||userVo.getOrgId()!=null){
			userVoList=salesPerformanceService.getUserList(userVo,user.getCompanyId(),page);
			mv.addObject("userVoList", userVoList);
			mv.addObject("page",page);
		}
		// Bert as to 2019年2月19日13:20:13 修改不同页面新增不同人员
		mv.addObject("flag",flag);
	
		mv.addObject("hasUser",salesPerformanceService.getHasAllUser());
		
		mv.addObject("salesPerformanceDeptId",salesPerformanceDeptId);
		mv.setViewName("saleperformance/configSet/addUserList");
		return mv;
	}
	/**
	 * 移除当前分组的某个人员，同时删除该人员的当年年度目标和月度目标
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@ResponseBody
	@RequestMapping(value = "delUserConfig")
	public ResultInfo<?> delUserConfig(HttpServletRequest request,String groupId,String userId,HttpServletResponse response) {
		try {
			//User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("salesPerformanceGroupId", groupId);
			map.put("userId", userId);
			ResultInfo<?> openOrClose = salesPerformanceService.delUserConfig(map);
			// 删除成功
			if(null != openOrClose && openOrClose.getCode() == 0)
			{
				// 清空五行缓存
				SalesFiveCacheCommonUtil.clear();
			}
			return openOrClose;
		} catch (Exception e) {
			logger.error("delUserConfig:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 * 新增人员
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@ResponseBody
	@RequestMapping(value = "addUserConfig")
	public ResultInfo<?> addUserConfig(HttpServletRequest request,String userIds,Integer groupId,HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String, Object>();
		//SalesPerformanceBrandConfig brandConfig=new SalesPerformanceBrandConfig();
		try {
			//User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			if(userIds==null){
				return new ResultInfo<>(-1, "参数为空");
			}
			//处理数据
			List<Integer> list=new ArrayList<>();
			String[] array=userIds.split(",");
			for(int i=0;i<array.length;i++){
				list.add(Integer.parseInt(array[i]));
			}
			map.put("list", list);
			map.put("groupId", groupId);
			ResultInfo<?> addBrandConfig = salesPerformanceService.addUserConfig(map);
			// 新增成功
			if(null != addBrandConfig && addBrandConfig.getCode() == 0)
			{
				// 清空五行缓存
				SalesFiveCacheCommonUtil.clear();
			}
			return addBrandConfig;
		} catch (Exception e) {
			logger.error("addUserConfig:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 * 打开人员编辑弹窗
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月6日 上午11:47:36
	 */
	@ResponseBody
	@RequestMapping(value = "openUserPage")
	public ModelAndView openUserPage(HttpServletRequest request,SalesPerformanceGroupVo salesPerformanceGroupVo,HttpSession session){
		ModelAndView mv = new ModelAndView();
		mv.addObject("groupVo",salesPerformanceGroupVo);
		mv.setViewName("saleperformance/configSet/editUserConfig");
		return mv;
	}
	
	
	/**
	 * 
	 * <b>Description: 批量编辑人员的目标</b><br> 
	 * @param request
	 * @param salesPerformanceGroupVo
	 * @param session
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月19日 下午2:32:06 </b>
	 */
	@ResponseBody
	@RequestMapping(value = "batchEditUserPage")
	public ModelAndView batchEditUserPage(HttpServletRequest request, SalesPerformanceGroupVo salesPerformanceGroupVo, HttpSession session)
	{
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("saleperformance/configSet/batchEditUserPage");
		if(null != user)
		{
			salesPerformanceGroupVo.setCompanyId(user.getCompanyId());
		}
		Integer nowYear = DateUtil.getNowYear();
		if(null == salesPerformanceGroupVo.getSalesPerformanceGoalYearId())
		{
			salesPerformanceGroupVo.setYear(nowYear);
		}
		
		mv.addObject("nowYear", nowYear);
		mv.addObject("month", DateUtil.getNowMonth());

		SalesPerformanceGroupVo result = salesPerformanceService.querySalesForGoal(salesPerformanceGroupVo);
		
		mv.addObject("groupVo", result);
		
		
		return mv;
	}
	
	/**
	 * 
	 * <b>Description:</b><br> 保存或更新人员编辑数据
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月4日 下午4:01:06
	 */
	@ResponseBody
	@RequestMapping(value = "saveOrUpdateOneUserConfigData")
	public ResultInfo<?> saveOrUpdateOneUserConfigData(HttpServletRequest request,
			SalesPerformanceGroupVo salesPerformanceGroupVo,HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		salesPerformanceGroupVo.setCompanyId(user.getCompanyId());
		salesPerformanceGroupVo.setCreator(user.getUserId());
		salesPerformanceGroupVo.setUpdater(user.getUserId());
		salesPerformanceGroupVo.setAddTime(DateUtil.sysTimeMillis());
		salesPerformanceGroupVo.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> addBrandConfig =salesPerformanceService.saveOrUpdateOneUserConfigData(salesPerformanceGroupVo);
		return addBrandConfig;
	}

	/**
	 * 
	 * <b>Description: 批量保存或更新</b><br> 
	 * @param request
	 * @param extendStr
	 * @param reqType
	 * @param req
	 * @param response
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月19日 下午7:46:47 </b>
	 */
	@ResponseBody
	@RequestMapping(value = "batchSaveOrUpdate")
	public ResultInfo<?> batchSaveOrUpdate(HttpServletRequest request, @RequestParam(required = false, defaultValue = "") String extendStr, @RequestParam(required = false, defaultValue = "1") Integer reqType, SalesPerformanceGroupVo req, HttpServletResponse response) 
	{
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		
		if(reqType == 1 && StringUtil.isNotBlank(extendStr))
		{
			String[] arr = extendStr.split(",");
			Integer[] months = new Integer[12];
			String[] goalMonths = new String[12];
			if(null != arr)
			{
				for(String mg : arr)
				{
					if(StringUtil.isBlank(mg) || mg.split("_").length != 2 )
					{
						continue;
					}
					months[Integer.parseInt(mg.split("_")[0])-1] = Integer.parseInt(mg.split("_")[0]);
					goalMonths[Integer.parseInt(mg.split("_")[0])-1] = mg.split("_")[1];
				}
			}
			
			req.setMonths(months);
			req.setMonthGoals(goalMonths);
		}
		
		req.setCompanyId(user.getCompanyId());
		req.setCreator(user.getUserId());
		req.setUpdater(user.getUserId());
		req.setAddTime(DateUtil.sysTimeMillis());
		req.setModTime(DateUtil.sysTimeMillis());
		ResultInfo<?> result = salesPerformanceService.batchSaveOrUpdate(req);
		return result;
	}
	
	/**
	 *
	 * <b>Description:</b><br> 跳转新增团队
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年02月15日 下午4:01:06
	 */
	@ResponseBody
	@FormToken(save=true)
	@RequestMapping(value = "insertGroupConfigSetData")
	public ModelAndView insertGroupConfigSetData(HttpServletRequest request, RSalesPerformanceGroupJConfigVo rSalesPerformanceGroupJConfigVo) {
		ModelAndView mv = new ModelAndView();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		//获取当前登录人的ID
		rSalesPerformanceGroupJConfigVo.setCompanyId(user.getCompanyId());
		//通过id获取五行
		ResultInfo<?> byCompanyIdWuXing = salesPerformanceService.findByCompanyIdWuXing(rSalesPerformanceGroupJConfigVo);
		if (ObjectUtils.allNotNull(byCompanyIdWuXing)){
			List<RSalesPerformanceGroupJConfigVo> list = (List<RSalesPerformanceGroupJConfigVo>)byCompanyIdWuXing.getListData();
			mv.addObject("configVoList",list);
		}
		
		List<User> allUser = userService.getAllUser(user);
		mv.addObject("allUser",allUser);
		// 模板赋值
		mv.setViewName("saleperformance/configSet/addConfigSet");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 * 删除小组设置
	 * @param :id
	 *@return :ResultInfo
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/15 20:39
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteGroupById")
	public ResultInfo deleteGroupById(HttpServletRequest request,Integer salesPerformanceGroupId){
		return salesPerformanceService.deleteGroupById(request,salesPerformanceGroupId);
	}
	
	
	/**
	 * <b>Description:</b><br>
	 * 新增团队设置
	 * @param :id
	 *@return :ResultInfo
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/15 20:39
	 */
	@ResponseBody
	@FormToken(remove = true)
	@RequestMapping(value = "/insertGroup")
	@SystemControllerLog(operationType = "edit",desc = "增加团队")
	public ResultInfo insertGroup(HttpServletRequest request, SalesPerformanceGroupVo salesPerformanceGroupVo){
		if(salesPerformanceGroupVo==null){
			return new ResultInfo<>(ErpConst.ONE, "参数为空");
		}
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		//获取当前登录人的ID
		if (ObjectUtils.allNotNull(user)) {
			salesPerformanceGroupVo.setCompanyId(user.getCompanyId());
		}
		return salesPerformanceService.saveConfigSetData(request,salesPerformanceGroupVo) ;
	}
	
	/**
	 *
	 * <b>Description:</b><br> 跳转小组
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年02月17日 下午4:01:06
	 */
	@ResponseBody
	@RequestMapping(value = "teamSet")
	public ModelAndView teamSet(HttpServletRequest request , SalesPerformanceGroup salesPerformanceGroup) {
		ModelAndView mv = new ModelAndView();
		//获取当前登录的用户
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		//获取团队
		if (ObjectUtils.allNotNull(user)) {
			salesPerformanceGroup.setCompanyId(user.getCompanyId());
			mv.addObject("groupName",salesPerformanceGroup.getGroupName());
		}
		try {
			//获取团队
			ResultInfo<?> queryByCompangIdAndstatus = salesPerformanceService.queryByCompangIdAndstatus(salesPerformanceGroup);
			
			
			//获取团队成员
			SalesPerformanceDept salesPerformanceDept =	new SalesPerformanceDept();
			salesPerformanceDept.setCompanyId(user.getCompanyId());
			ResultInfo<?> getDeptMember = salesPerformanceService.getDeptMember(salesPerformanceDept);
			
			// 获取小组成员的信息
			SalesPerformanceDeptUserVo deptUserVo = new SalesPerformanceDeptUserVo();
			deptUserVo.setCompanyId(user.getCompanyId());
			ResultInfo<?> getDeptUser = salesPerformanceService.getDeptUser(deptUserVo);
			
			if (ObjectUtils.allNotNull(queryByCompangIdAndstatus)) {
				List<SalesPerformanceGroup> listData = (List<SalesPerformanceGroup>)queryByCompangIdAndstatus.getListData();
				mv.addObject("listGroup",listData);
			}
			if (ObjectUtils.allNotNull(getDeptMember)) {
				List<SalesPerformanceDept> list = (List<SalesPerformanceDept>)getDeptMember.getListData();
				mv.addObject("listDeptMember",list);
			}
			
			if (ObjectUtils.allNotNull(getDeptUser)) {
				List<SalesPerformanceDeptUserVo> lists = (List<SalesPerformanceDeptUserVo>)getDeptUser.getListData();
				mv.addObject("getDeptUserList",lists);
			}
			
		} catch (Exception e) {
			logger.error("teamSet:", e);
		}
		
		//跳转的页面
		mv.setViewName("saleperformance/configSet/teamConfigSet");
		return mv;
	}
	
	
	/**
	 *
	 * <b>Description:</b><br> 跳转新增小组
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年02月17日 下午4:01:06
	 */
	@ResponseBody
	@FormToken(save = true)
	@RequestMapping(value = "teamNewSet")
	public ModelAndView teamNewSet(SalesPerformanceGroupVo salesPerformanceGroup) {
		ModelAndView mv = new ModelAndView();
		//跳转的页面
		if (ObjectUtils.allNotNull(salesPerformanceGroup)) {
			mv.addObject("salesPerformanceGroupId",salesPerformanceGroup.getSalesPerformanceGroupId());
		}
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		//得到所有未禁用的用户
		List<User> allUser = userService.getAllUser(user);
		
		mv.addObject("allUser",allUser);
		mv.setViewName("saleperformance/configSet/addTeamConfigSet");
		return mv;
	}
	/**
	 * <b>Description:</b><br>
	 * 新增小组
	 * @param :id
	 *@return :ResultInfo
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/15 20:39
	 */
	@ResponseBody
	@FormToken(remove = true)
	@RequestMapping(value = "/insertTeam")
	@SystemControllerLog(operationType = "edit",desc = "新增小组")
	public ResultInfo insertTeam(HttpServletRequest request, SalesPerformanceDept salesPerformanceDept){
		if(salesPerformanceDept==null){
			return new ResultInfo<>(ErpConst.ONE, "参数为空");
		}
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		//获取当前登录人的ID
		if (ObjectUtils.allNotNull(user)) {
			salesPerformanceDept.setCreator(user.getUserId());
		}
		
		return salesPerformanceService.insertTeam(salesPerformanceDept);
	}
	
	/**
	 *
	 * <b>Description:</b><br> 跳转新增小组人员
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年02月17日 下午4:01:06
	 */
	@ResponseBody
	@RequestMapping(value = "addTeamPerson")
	public ModelAndView addTeamPerson(SalesPerformanceDept dept) {
		ModelAndView mv = new ModelAndView();
		//跳转的页面
		if (ObjectUtils.allNotNull(dept)) {
			mv.addObject("salesPerformanceGroupId",dept.getSalesPerformanceDeptId());
		}
		mv.setViewName("saleperformance/configSet/addTeamConfigSet");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 *  编辑小组
	 *
	 * @param :[salesPerformanceDept]
	 * @return :org.springframework.web.servlet.ModelAndView
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 20:27
	 */
	@RequestMapping(value = "editOneGroupConfigSetData")
	@FormToken(save = true)
	public ModelAndView editOneGroupConfigSetData(HttpServletRequest request, SalesPerformanceDept salesPerformanceDept){
		ModelAndView mv = new ModelAndView();
		//查找人员
		if (ObjectUtils.allNotNull(salesPerformanceDept)) {
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			salesPerformanceDept.setCompanyId(user.getCompanyId());
			ResultInfo<?> getDeptMember = salesPerformanceService.getDeptMember(salesPerformanceDept);
			if (ObjectUtils.allNotNull(getDeptMember)) {
				List<SalesPerformanceDept> list = (List<SalesPerformanceDept>)getDeptMember.getListData();
				mv.addObject("deptMember",list.get(0));
			}
			//得到所有未禁用的用户
			List<User> allUser = userService.getAllUser(user);
			
			mv.addObject("allUser",allUser);
		}
		mv.setViewName("saleperformance/configSet/editOneGroupConfigSetData");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 *  删除小组
	 *
	 * @param :[salesPerformanceDept]
	 * @return :org.springframework.web.servlet.ModelAndView
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 20:27
	 */
	@ResponseBody
	@RequestMapping(value = "deleteOneGroupConfigSetData")
	public ResultInfo<?> deleteOneGroupConfigSetData(HttpServletRequest request,SalesPerformanceDept salesPerformanceDept){
		//设置更新人
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		salesPerformanceDept.setUpdater(user.getUserId());
		return salesPerformanceService.deleteOneGroupConfigSetData(salesPerformanceDept);
		
	}
	
	/**
	 * <b>Description:</b><br>
	 * 编辑小组内人员的目标值
	 *
	 * @param :[]
	 * @return :org.springframework.web.servlet.ModelAndView
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 0:44
	 */
	@ResponseBody
	@RequestMapping(value = "editDeptPerson")
	public ModelAndView editDeptPerson(SalesPerformanceDeptUserVo salesPerformanceDeptUser){
		ModelAndView mv = new ModelAndView("saleperformance/configSet/editDeptPerson");
		try {
			salesPerformanceDeptUser.setUsername(URLDecoder.decode(salesPerformanceDeptUser.getUsername(), "UTF-8"));
			mv.addObject("salesPerformanceDeptUser",salesPerformanceDeptUser);
		} catch (Exception e) {
			logger.error("editDeptPerson:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 *  编辑保存小组
	 *
	 * @param :[salesPerformanceDept]
	 * @return :org.springframework.web.servlet.ModelAndView
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 20:27
	 */
	@ResponseBody
	@RequestMapping(value = "editDeptUser")
	public ResultInfo<?> editDeptUser(HttpServletRequest request,SalesPerformanceDeptUser salesPerformanceDeptUser){
		//设置更新人
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		salesPerformanceDeptUser.setUpdater(user.getUserId());
		ResultInfo<?> resultInfo = salesPerformanceService.editDeptUser(salesPerformanceDeptUser);
		return resultInfo;
	}
	
	
	/**
	 * <b>Description:</b><br>
	 *  删除小组内的人员
	 *
	 * @param :[salesPerformanceDept]
	 * @return :org.springframework.web.servlet.ModelAndView
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 20:27
	 */
	@ResponseBody
	@RequestMapping(value = "deleteDeptUser")
	public ResultInfo<?> deleteDeptUser(SalesPerformanceDeptUser salesPerformanceDeptUser){
		//设置更新人
		return salesPerformanceService.deleteDeptUser(salesPerformanceDeptUser);
	}
	
	/**
	 * 新增小组人员
	 * <b>Description:</b><br>
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:15:56
	 */
	@ResponseBody
	@FormToken(remove = true)
	@RequestMapping(value = "addDeptUserConfig")
	public ResultInfo<?> addDeptUserConfig(HttpServletRequest request,SalesPerformanceDeptUserVo salesPerformanceDeptUserVo) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		try {
			//参数的校验
			if(salesPerformanceDeptUserVo.getUserIds()==null){
				return new ResultInfo<>(-1, "参数为空");
			}
			//处理数据
			List<Integer> list=new ArrayList<>();
			String[] array=salesPerformanceDeptUserVo.getUserIds().split(",");
			for(int i=0;i<array.length;i++){
				list.add(Integer.parseInt(array[i]));
			}
			//设置更新用户
			if (ObjectUtils.allNotNull(user)) {
				salesPerformanceDeptUserVo.setCreator(user.getUserId());
			}
			salesPerformanceDeptUserVo.setListUserId(list);
			return salesPerformanceService.addDeptUserConfig(salesPerformanceDeptUserVo);
		} catch (Exception e) {
			logger.error("addDeptUserConfig:", e);
			return new ResultInfo<>();
		}
	}
	/**
	 *
	 * <b>Description:</b><br> 获取部门设置数据
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2018年6月4日 下午4:01:06
	 */
	@ResponseBody
	@RequestMapping(value = "getGroupConfigSetData")
	public ModelAndView getGroupConfigSetData(HttpServletRequest request,HttpServletResponse response,SalesPerformanceGroupVo salesPerformanceGroupVo) {
		ModelAndView mv = new ModelAndView();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		//设置公司ID
		if (ObjectUtils.allNotNull(user)) {
			salesPerformanceGroupVo.setCompanyId(user.getCompanyId());
		}
		List<SalesPerformanceGroupVo> allGroup = salesPerformanceService.getAllGroup(salesPerformanceGroupVo);
		if (ObjectUtils.allNotNull(allGroup)) {
			mv.addObject("groupList",allGroup);
			if (allGroup.size() > 0 ) {

				mv.addObject("configList",allGroup.get(0).getConfigList());
			}
		}
		mv.setViewName("saleperformance/configSet/configSetList");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br>
	 *
	 * 删除小组负责人
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/21 11:04
	 */
	@ResponseBody
	@RequestMapping(value = "delGroupUser")
	public ResultInfo<?> delGroupUser(SalesPerformanceGroupManager salesPerformanceGroupManager) {
		if ( ObjectUtils.allNotNull(salesPerformanceGroupManager)) {
			return salesPerformanceService.delGroupUser(salesPerformanceGroupManager);
		}
		return new ResultInfo<>();
	}
	
	/**
	 * <b>Description:</b><br>
	 *
	 * 删除小组负责人
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/21 11:04
	 */
	@ResponseBody
	@RequestMapping(value = "delDeptUser")
	public ResultInfo<?> delDeptUser(SalesPerformanceDeptManager salesPerformanceDeptManager) {
		if ( ObjectUtils.allNotNull(salesPerformanceDeptManager)) {
			return salesPerformanceService.delDeptUser(salesPerformanceDeptManager);
		}
		return new ResultInfo<>();
	}
}
