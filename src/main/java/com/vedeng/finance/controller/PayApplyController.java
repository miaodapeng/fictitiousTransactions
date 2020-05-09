package com.vedeng.finance.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.service.PayApplyService;
import com.vedeng.system.model.SysOptionDefinition;


/**
 * <b>Description:</b><br> 付款申请管理
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.finance.controller
 * <br><b>ClassName:</b> PayApplyController
 * <br><b>Date:</b> 2017年9月8日 下午2:47:24
 */
@Controller
@RequestMapping("/finance/payapply")
public class PayApplyController extends BaseController{

	@Autowired
	@Qualifier("payApplyService")
	private PayApplyService payApplyService;//自动注入payApplyService
	
	@Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value="getPayApplyListPage")
	public ModelAndView getPayApplyListPage(HttpServletRequest request,PayApply payApply,HttpSession session,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false, value="searchDateType") String searchDateType){
		ModelAndView mv = new ModelAndView();
		Page page = getPageTag(request,pageNo,pageSize);
		try {
			//获取当前日期
			Date date = new Date();
			String nowDate = DateUtil.DatePreMonth(date, 0, null);
			mv.addObject("nowDate", nowDate);
			//获取当月第一天日期
			String pre1MonthDate = DateUtil.getFirstDayOfMonth(0);
			mv.addObject("pre1MonthDate", pre1MonthDate);
			mv.addObject("searchDateType", searchDateType);
			//默认展示审核中的
			if(null == request.getParameter("validStatus") || request.getParameter("validStatus") == ""){
			    	payApply.setValidStatus(0);
			}
			if(null != request.getParameter("searchBegintimeStr") && request.getParameter("searchBegintimeStr") != ""){
				payApply.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			} else {
			    	if(request.getParameter("searchBegintimeStr") == ""){
			    	    //payApply.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			    	}else{
			    	    payApply.setSearchBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			    	}
			}
			if(null != request.getParameter("searchEndtimeStr") && request.getParameter("searchEndtimeStr") != ""){
				payApply.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			} else {
    			        if(request.getParameter("searchEndtimeStr") == ""){
    				
    			        }else{
    			            payApply.setSearchEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
    			        }
				
			}
			
			if(null != request.getParameter("searchPayBegintimeStr") && request.getParameter("searchPayBegintimeStr") != ""){
				payApply.setSearchPayBegintime(DateUtil.convertLong(request.getParameter("searchPayBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			} else {
			       if(request.getParameter("searchPayBegintimeStr") == ""){
				
			       }else{
				   payApply.setSearchPayBegintime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
				   //payApply.setSearchPayBegintime(DateUtil.convertLong(pre1MonthDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			       }
			}
			if(null != request.getParameter("searchPayEndtimeStr") && request.getParameter("searchPayEndtimeStr") != ""){
				payApply.setSearchPayEndtime(DateUtil.convertLong(request.getParameter("searchPayEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			} else {
			    	if(request.getParameter("searchPayEndtimeStr") == ""){
				
			       }else{
				   payApply.setSearchPayEndtime(DateUtil.convertLong(nowDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			       }
			}
			//交易方式
			List<SysOptionDefinition> traderModeList = getSysOptionDefinitionList(519);
			
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			
			payApply.setCompanyId(user.getCompanyId());
			//判断当前用户是否在审核人名单中
			payApply.setValidUserName(user.getUsername());
			Map<String,Object> map = payApplyService.getPayApplyListPageNew(request,payApply,page);
			if(map!=null){
				List<PayApply> list = (List<PayApply>)map.get("payApplyList");
			    	// 获取订单审核信息
			   	TaskService taskService = processEngine.getTaskService(); // 任务相关service
				for (int i = 0; i < list.size(); i++) {
				    	//如果是审核中的话去查询审核对象
				    	if(null != list.get(i).getVerifyUsername() && list.get(i).getVerifyStatus().equals(0)){
				    	    // 获取当前活动节点
				    	    Task taskInfoPay = taskService.createTaskQuery().processInstanceBusinessKey("paymentVerify_"+ list.get(i).getPayApplyId())
				   	 		.singleResult();
        				    list.get(i).setTaskInfoPayId(taskInfoPay.getId());
				    	}
					//审核人
					if(null != list.get(i).getVerifyUsername()){
					    List<String> verifyUsernameList = Arrays.asList(list.get(i).getVerifyUsername().split(","));  
					    list.get(i).setVerifyUsernameList(verifyUsernameList);
					}
				}
				mv.addObject("payApplyList",list);
			}
			mv.addObject("page", (Page)map.get("page"));
			mv.addObject("traderModeList", traderModeList);
			mv.addObject("payApply",(PayApply)map.get("payApply"));
			mv.addObject("loginUser",user);
		} catch (Exception e) {
			logger.error("getPayApplyListPage:", e);
		}
		mv.setViewName("finance/payapply/index_payapply");
		return mv;
	}
	
}
