package com.vedeng.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.UserOperationLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.system.service.UserOperationLogService;

/**
 * 
 * <b>Description:</b><br> 用户操作日志功能
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> UserOperationLogController
 * <br><b>Date:</b> 2017年4月25日 上午9:41:01
 */
@Controller
@RequestMapping("/system/useroperationlog")
public class UserOperationLogController extends BaseController {

	@Autowired
	@Qualifier("userOperationLogService")
	private UserOperationLogService userOperationLogService;
	
	/**
	 * 
	 * <b>Description:</b><br> 用户操作日志列表功能
	 * @param request
	 * @param userOperationLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月25日 上午9:40:32
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView querylistPage(HttpServletRequest request, UserOperationLog userOperationLog,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		//查询集合
        Page page = getPageTag(request,pageNo,pageSize);
        List<UserOperationLog> userOperationLogList = userOperationLogService.querylistPage(userOperationLog, page);
        
        mv.addObject("userOperationLog", userOperationLog);
        mv.addObject("userOperationLogList",userOperationLogList);  
        mv.addObject("page", page);
        mv.setViewName("system/user_operation_log/index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 查看用户操作日志详情
	 * @param request
	 * @param userOperationLog
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月25日 下午3:43:21
	 */
	@RequestMapping(value="/viewUserOperationLog")
	public ModelAndView viewUserOperationLog(HttpServletRequest request, UserOperationLog userOperationLog) {
		ModelAndView mv = new ModelAndView();
		
		userOperationLog = userOperationLogService.getUserOperationLogByKey(userOperationLog.getUserOperationLogId());
		mv.addObject(userOperationLog);
		mv.setViewName("system/user_operation_log/view");
		return mv;
	}
}
