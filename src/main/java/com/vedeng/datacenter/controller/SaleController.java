package com.vedeng.datacenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.util.DateUtil;
import com.vedeng.datacenter.service.SaleService;
import com.vedeng.finance.model.PayApply;
import com.vedeng.system.service.UserService;

@Controller
@RequestMapping("/datacenter/sale")
public class SaleController extends BaseController{

	@Autowired
	@Qualifier("saleService")
	private SaleService saleService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	/**
	 * <b>Description:</b><br> 销售人员分析（数据统计）
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:17:03
	 */
	@ResponseBody
	@RequestMapping(value="saleuser")
	public ModelAndView getSaleUser(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		//获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList",userList);//销售人员
		
		String userId = request.getParameter("userId");
		if(StringUtils.isNoneBlank(userId)){
			//获取销售基本信息
			User userInfo = userService.getUserById(Integer.parseInt(userId));
			mv.addObject("userInfo",userInfo);
			mv.addObject("userId", userId);
		}
		
		mv.setViewName("datacenter/sale/index_saleuser");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 销售人员分析（数据分析）
	 * @param request
	 * @param payApply
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:18:02
	 */
	@ResponseBody
	@RequestMapping(value="saleuserstatistics")
	public ModelAndView getSaleUserStatistics(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		//获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList",userList);//销售人员
		
		String userId = request.getParameter("userId");
		if(StringUtils.isNoneBlank(userId)){
			//获取销售基本信息
			User userInfo = userService.getUserById(Integer.parseInt(userId));
			mv.addObject("userInfo",userInfo);
			mv.addObject("userId", userId);
		}
		
		mv.setViewName("datacenter/sale/index_saleuser_statistics");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 销售指标
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:21:14
	 */
	@ResponseBody
	@RequestMapping(value="saleStatistics")
	public ModelAndView saleStatistics(HttpServletRequest request,HttpSession session){
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Long nowTime = DateUtil.sysTimeMillis();//当前时间戳
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		map.put("companyId", user.getCompanyId());
		map.put("nowTime", nowTime);
		
		mv.addObject("nowTime",nowTime);
		mv.setViewName("datacenter/sale/sale_statistics");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 销售部门数据
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午6:22:18
	 */
	@ResponseBody
	@RequestMapping(value="saleOrg")
	public ModelAndView saleOrg(HttpServletRequest request,HttpSession session){
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		//获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList",userList);//销售人员
		
		String userId = request.getParameter("userId");
		if(StringUtils.isNoneBlank(userId)){
			//获取销售基本信息
			User userInfo = userService.getUserById(Integer.parseInt(userId));
			mv.addObject("userInfo",userInfo);
			mv.addObject("userId", userId);
		}
		
		mv.setViewName("datacenter/sale/sale_org");
		return mv;
	}
	
}
