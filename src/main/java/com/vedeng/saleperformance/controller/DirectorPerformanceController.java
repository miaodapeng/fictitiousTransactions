package com.vedeng.saleperformance.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.util.DateUtil;
import com.vedeng.saleperformance.model.vo.GroupMemberDetailsVo;
import com.vedeng.saleperformance.model.vo.SaleperformanceProcess;
import com.vedeng.saleperformance.service.DirectorPerformanceService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.UserService;


@Controller
@RequestMapping("/director/performance")
public class DirectorPerformanceController extends BaseController{
	
	@Autowired
	DirectorPerformanceService directorPerformanceService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private OrgService orgService;
	
	
	/**
	 * 
	 * <b>Description:</b>五行剑法主管五行小组成员数据<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月30日 11:40
	 */
	@ResponseBody
	@RequestMapping("/getgroupmembersdetails")
	public ModelAndView getGroupMembersDetails(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer groupId, @RequestParam(required = false, defaultValue = "0") Integer orgId3)
	{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("saleperformance/manager/director_performance");
		try 
		{
			//获取当前用户信息
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			//获取当前用户的二级部门Id
			User u = directorPerformanceService.getOrgIdsByUserId(user.getUserId(), 1);
			if (null == groupId || 0 == groupId) 
			{
				if (null != u && null != u.getOrgId2() && u.getOrgId2().intValue() == 38) 
				{
					//平台
					groupId = 1;
				}
				else if (null != u && null != u.getOrgId2() && u.getOrgId2().intValue() == 36) 
				{
					//科研
					groupId = 2;
				}
				else 
				{
					groupId = 3;
				}
			}
				
			SaleperformanceProcess req = new SaleperformanceProcess();
			req.setReqType(1);// 个人
			req.setConfigOrgId(groupId);
			
			if(null == orgId3 || 0 == orgId3)
			{				
				req.setOrgId3(u.getOrgId3());
			}
			else
			{
				req.setOrgId3(orgId3);
			}
			mv.addObject("orgId3", req.getOrgId3());
			mv.addObject("groupId", groupId);
			
			List<SaleperformanceProcess> list = directorPerformanceService.getMembersListByOrgId(req);
			mv.addObject("reulsts", list);
		}
		catch (Exception e) 
		{
			logger.error("getgroupmembersdetails:", e);
		}
		return mv;
	}
	
	
	/**
	 * 
	 * <b>Description:</b>总监五行<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年06月12日 13:59
	 */
	@ResponseBody
	@RequestMapping("/getareadetails")
	public ModelAndView getAreaDetails(HttpServletRequest request)
	{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("saleperformance/manager/chief_performance");
		try
		{

			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			Integer groupId = null;
			//获取当前用户的二级部门Id
			User u = directorPerformanceService.getOrgIdsByUserId(user.getUserId(), 1);
			if (null != u && null != u.getOrgId2() && u.getOrgId2().intValue() == 38) 
			{
				// 平台
				groupId = 1;
				
			} else if (null != u && null != u.getOrgId2() && u.getOrgId2().intValue() == 36) 
			{
				groupId = 2;
			} else 
			{
				groupId = 3;
			}
			List<SaleperformanceProcess> reulsts = directorPerformanceService.getManagerByOrgId(groupId);
			
			mv.addObject("reulsts", reulsts);
			mv.addObject("groupId", groupId);
			mv.addObject("orgId",user.getOrgId());
		} 
		catch (Exception e) 
		{
			logger.error("getareadetails:", e);
		}
		
		return mv;
	}
	
	
	
	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param user
	 * @param groupId  默认进入1-平台
	 * @return
	 * @Note 
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月02日 15:12
	 */
	@ResponseBody
	@RequestMapping("/gettotaldetails")
	public ModelAndView getTotalDetails(User user, HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer groupId)
	{
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("saleperformance/manager/manager_performance");		
		mv.addObject("groupId", groupId);
		try
		{
			// groupId， orgId3
			List<SaleperformanceProcess> reulsts = directorPerformanceService.getManagerByOrgId(groupId);
			mv.addObject("reulsts", reulsts);
		} 
		catch (Exception e) 
		{
			logger.error("gettotaldetails:", e);
		}
		
		return mv;
	}


	/**
	 * 
	 * <b>Description:总经理页面历史数据</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月01日 11:05
	 */
	@ResponseBody
	@RequestMapping("/getAreaDetailsList")
	public Map<String, Object> getAreaDetailsList(HttpServletRequest request,
				@RequestParam(required = true) Integer groupId){
		
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		GroupMemberDetailsVo data1 = new GroupMemberDetailsVo();
		// 用户id
		data1.setUserId(user.getUserId());
		
		//页面小组数据
		Map<String, Object> groupList = new HashMap();
		try{
			// 从一个 Calendar 对象中获取 Date 对象
			Calendar calendar = Calendar.getInstance();
			//获取最近12个月
			String[] last12months = DateUtil.getLast12MonthsWithThisMonth();
			String m = (calendar.get(Calendar.MONTH) + 1) + "";
			if (m.length() == 1) {
				m = "0" + m;
			}
			String nowTime = (calendar.get(Calendar.YEAR)) + "."+m;
			List<String> list = Arrays.asList(last12months);
			List<String> yearAndMonth = new ArrayList<String>(list);
			yearAndMonth.add(nowTime);
			data1.setYearAndMonth(yearAndMonth);
			
			//获取12个月的数据
			groupList = directorPerformanceService.getGroupList(groupId);

		} catch (Exception e) {
			logger.error("getAreaDetailsList:", e);
		}
		return groupList;
	}
	
	
	@ResponseBody
	@RequestMapping("/getMembersHistoryData")
	public Map<String, Object> getDeptDetailsList(HttpServletRequest request,
			@RequestParam(required = true) Integer deptId){
		
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		GroupMemberDetailsVo data1 = new GroupMemberDetailsVo();
		// 用户id
		data1.setUserId(user.getUserId());
		
		//页面小组数据
		Map<String, Object> deptList = new HashMap();
		try{
			// 从一个 Calendar 对象中获取 Date 对象
			Calendar calendar = Calendar.getInstance();
			//获取最近12个月
			String[] last12months = DateUtil.getLast12MonthsWithThisMonth();
			String m = (calendar.get(Calendar.MONTH) + 1) + "";
			if (m.length() == 1) {
				m = "0" + m;
			}
			String nowTime = (calendar.get(Calendar.YEAR)) + "."+m;
			List<String> list = Arrays.asList(last12months);
			List<String> yearAndMonth = new ArrayList<String>(list);
			yearAndMonth.add(nowTime);
			data1.setYearAndMonth(yearAndMonth);
			
			//获取12个月的数据
			deptList = directorPerformanceService.getDeptList(deptId);
			
			
		} catch (Exception e) {
			logger.error("getMembersHistoryData:", e);
		}
		return deptList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * <b>Description:</b>五行剑法主管页面历史数据<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月30日 11:39
	 */
//	@ResponseBody
//	@RequestMapping("/getMembersHistoryData")
//	public List<SaleperformanceProcess> getMembersHistoryData(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") Integer groupId, @RequestParam(required = false, defaultValue = "0") Integer orgId3)
//	{
//		
//		//获取当前用户信息
//		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		//获取当前用户的二级部门Id
//		User u = directorPerformanceService.getOrgIdsByUserId(user.getUserId(), 1);
//		if (null == groupId || 0 == groupId) 
//		{
//			if (null != u && null != u.getOrgId2() && u.getOrgId2().intValue() == 38) 
//			{
//				//平台
//				groupId = 1;
//			}
//			else if (null != u && null != u.getOrgId2() && u.getOrgId2().intValue() == 36) 
//			{
//				//科研
//				groupId = 2;
//			}
//			else 
//			{
//				groupId = 3;
//			}
//		}
//		
//		SaleperformanceProcess req = new SaleperformanceProcess();
//		req.setReqType(2);// 历史数据
//		req.setConfigOrgId(groupId);
//		
//		if(null == orgId3 || 0 == orgId3)
//		{				
//			req.setOrgId3(u.getOrgId3());
//		}
//		else
//		{
//			req.setOrgId3(orgId3);
//		}
//		List<SaleperformanceProcess> reulsts = directorPerformanceService.getMembersListByOrgId(req);
//		
//		return reulsts;
//	}
}
