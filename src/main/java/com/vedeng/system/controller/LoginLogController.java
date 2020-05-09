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

import com.vedeng.authorization.model.UserLoginLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.system.service.LoginLogService;

/**
 * <b>Description:</b><br> 用户登录日志
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> LoginLogController
 * <br><b>Date:</b> 2017年4月25日 上午10:12:31
 */
@Controller
@RequestMapping("/system/loginlog")
public class LoginLogController extends BaseController {
	
	@Autowired
	@Qualifier("loginLogService")
	private LoginLogService loginLogService;
	
	/**
	 * <b>Description:</b><br> 用户登录日志列表
	 * @param request
	 * @param userLoginLog
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月25日 上午10:13:11
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView querylistPage(HttpServletRequest request, UserLoginLog userLoginLog,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		//查询集合
        Page page = getPageTag(request,pageNo,pageSize);
        List<UserLoginLog> userLoginLogList = loginLogService.querylistPage(userLoginLog, page);
        
        mv.addObject("userLoginLog", userLoginLog);
        mv.addObject("userLoginLogList",userLoginLogList);  
        mv.addObject("page", page);
        mv.setViewName("system/user_login_log/index");
        return mv;  
	}
}
