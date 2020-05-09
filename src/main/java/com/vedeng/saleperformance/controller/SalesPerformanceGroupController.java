package com.vedeng.saleperformance.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.saleperformance.service.SalesPerformanceGroupService;
/**
 * 团队详情页
 * <b>Description:</b><br>
 * @author bill.bo
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.saleperformance.controller
 * <br><b>ClassName:</b> SalesPerformanceGroupController
 * <br><b>Date:</b> 2019年2月20日 上午8:58:21
 */
@Controller
@RequestMapping("/salesperformance/group")
public class SalesPerformanceGroupController extends BaseController{

	
	@Autowired
	@Qualifier("salesPerformanceGroupService")
	private SalesPerformanceGroupService salesPerformanceGroupService;
	
	/**
	 * 团队详情页
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> bill.bo
	 * <br><b>Date:</b> 2019年2月21日
	 */
	@ResponseBody
	@RequestMapping("/getGroupDetail")
	public ModelAndView getTotalDetails(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("saleperformance/group/group_detail");
		try{
			mv.addObject("resultList", salesPerformanceGroupService.getGroupDetail(request, user));
		}catch (Exception e){
			logger.error("getGroupDetail:", e);
		}
		return mv;
	}
	
	/**
	 * 小组详情
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> bill.bo
	 * <br><b>Date:</b> 2019年2月22日
	 */
	@ResponseBody
	@RequestMapping("/getDeptDetail")
	public ModelAndView getTotalDeptDetails(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		user.setOrgId(null);
		if(null != request.getParameter("groupId") && !"".equals(request.getParameter("groupId"))) {
			user.setOrgId(Integer.valueOf(request.getParameter("groupId")));
		}
		user.setOrgId2(null);
		if(null != request.getParameter("deptId") && !"".equals(request.getParameter("deptId"))) {
			user.setOrgId2(Integer.valueOf(request.getParameter("deptId")));
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("saleperformance/group/dept_detail");
		try{

			mv.addObject("resultList", salesPerformanceGroupService.getDeptDetail(request, user));
		}catch (Exception e){
			logger.error("getDeptDetail:", e);
		}
		return mv;
	}
	
}
