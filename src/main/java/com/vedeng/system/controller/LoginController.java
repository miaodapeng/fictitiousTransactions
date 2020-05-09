package com.vedeng.system.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.vedeng.common.shiro.SpringContextHolder;
import com.vedeng.ez.constants.CoookieConstants;
import com.vedeng.ez.utils.DoCookie;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.Company;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.UserLoginLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.Encode;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.UsernamePasswordComapnyIdToken;
import com.vedeng.common.validator.CustomSqlistAuthorityImpl;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.CompanyService;
import com.vedeng.system.service.LoginLogService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.PositService;
import com.vedeng.system.service.RoleService;
import com.vedeng.system.service.UserService;

@Controller()
public class LoginController extends BaseController {
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Resource
	private LoginLogService loginLogService;

	@Autowired
	@Qualifier("positService")
	private PositService positService;

	@Autowired
	@Qualifier("orgService")
	private OrgService orgService;// 自动注入orgService

	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;

	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	/**
	 * <b>Description:</b><br>
	 * 初始化
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月22日 上午10:42:39
	 */
	@RequestMapping(value = "/login")
	public ModelAndView login(String sessionID, HttpServletRequest request, HttpServletResponse response) {
		// 根据sessionid获取当前浏览器的session，不为空直接登录
        logger.info("login sessionId:"+sessionID);
		boolean status = false;
        SessionKey key = new WebSessionKey(sessionID,request,response);
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            if(se != null){
				Object obj = se.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
				if(obj != null){
					status = (Boolean) obj;
				}
			}
        }catch(Exception e){
            logger.error("login page:"+sessionID, e);
        }
	    if(status){
	    	return new ModelAndView("redirect:/index.do");
	    }else{
	    	ModelAndView mv = new ModelAndView();

			Cookie[] cookies = request.getCookies();
			String userName = "";
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("lastLoginName")) {
						userName =  cookie.getValue();
						break;
					}
				}
			}
			mv.addObject("lastLoginName", userName);
			mv.setViewName("login");
			return mv;
		}
	}


	/**
	 * <b>Description:</b><br>
	 * 登录
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月22日 上午10:42:28
	 */
	@RequestMapping(value = "/dologin")
	public String login(HttpServletRequest request, HttpServletResponse response, String username, String password,
			Integer companyId, Model model, HttpSession session) {
		companyId=1;
		// 所有分公司
		Company com = new Company();
		com.setIsEnable(1);

		Integer loginFaildTimes = 1;
	//	username = username.trim();

		if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
			model.addAttribute("msg", ErpConst.USERNAME_PWD_NULL_MSG);

			if (null != session.getAttribute("login_faild_times")) {
				loginFaildTimes += Integer.parseInt(session.getAttribute("login_faild_times").toString());
			}
			session.setAttribute("login_faild_times", loginFaildTimes);
			model.addAttribute("login_faild_times", loginFaildTimes);
			return "login";
		}
		username = username.trim();
		if (!username.toLowerCase().equals("admin") && companyId == 0) {
			model.addAttribute("msg", ErpConst.USERNAME_COMPANY_NULL_MSG);
			if (null != session.getAttribute("login_faild_times")) {
				loginFaildTimes += Integer.parseInt(session.getAttribute("login_faild_times").toString());
			}
			session.setAttribute("login_faild_times", loginFaildTimes);
			model.addAttribute("login_faild_times", loginFaildTimes);
			return "login";
		}
		String sessionCode = (String) session.getAttribute(ErpConst.LOGIN_AUTH_CODE);
		String code = request.getParameter("code");
		if (null != session.getAttribute("login_faild_times")
				&& !sessionCode.toLowerCase().equals(code.toLowerCase())) {
			model.addAttribute("msg", ErpConst.CODE_ERROR_MSG);
			if (null != session.getAttribute("login_faild_times")) {
				loginFaildTimes += Integer.parseInt(session.getAttribute("login_faild_times").toString());
			}
			session.setAttribute("login_faild_times", loginFaildTimes);
			model.addAttribute("login_faild_times", loginFaildTimes);
			return "login";
		}
		if(username.toLowerCase().equals("admin")){
			companyId=0;
		}


		UsernamePasswordComapnyIdToken token = new UsernamePasswordComapnyIdToken(username, password, companyId);
		Subject subject = SecurityUtils.getSubject();
		User user = null;
		String ip = super.getIpAddress(request);
		String agentInfo = request.getHeader("User-Agent");

		try {
			subject.login(token);
			user = userService.login(username, password, companyId);
			//当前登陆系统为贝登ERP
			user.setCurrentLoginSystem(1);
			String sessionId = session.getId().toString();

			// session对应的userid在redis是否存在，存在就删除，不存在就存储
			//del方法里面已经有exist判断
			if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_USERID_SESSIONID + user.getUserId())) {
			JedisUtils.del(dbType + ErpConst.KEY_PREFIX_USERID_SESSIONID + user.getUserId());
			}
			JedisUtils.set(dbType + ErpConst.KEY_PREFIX_USERID_SESSIONID + user.getUserId(), sessionId, 0);

			if (subject.isAuthenticated()) {
				//int res = userService.saveOrUpdate(user, ip);
                int res = 1;
//				int res=1;
				if (res > 0) {
					// 角色名的集合
					Set<String> roleset = new HashSet<String>();
					// 删除，保存用户的角色
					if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_USER_ROLES + user.getUserId())) {
						JedisUtils.del(dbType + ErpConst.KEY_PREFIX_USER_ROLES + user.getUserId());
					}
					// 根据用户名查找拥有的角色
					List<Role> roles = roleService.getUserRoles(user);
					if (roles != null && roles.size() > 0) {
						for (Role r : roles) {
							roleset.add(r.getRoleName());
						}
						JedisUtils.set(dbType + ErpConst.KEY_PREFIX_USER_ROLES + user.getUsername().toLowerCase()
								+ user.getCompanyId(), JsonUtils.convertConllectionToJsonStr(roleset), 0);
						user.setRoles(roles);
					}

					// 权限名的集合
					Set<String> permissionset = new HashSet<String>();

					// 删除，保存用户的权限 不需要多余的操作
					// if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_USER_PERMISSIONS
					// + user.getUsername().toLowerCase() + user.getCompanyId())) {
					// JedisUtils.del(dbType + ErpConst.KEY_PREFIX_USER_PERMISSIONS +
					// user.getUsername().toLowerCase()
					// + user.getCompanyId());
					// }

					List<Action> list = actionService.getActionListByUserName(user);
					if (list != null && list.size() > 0) {
						for (Action ac : list) {
							if(ac.getModuleName().startsWith("http")){
								permissionset.add(ac.getModuleName());
							}else{
								permissionset.add("/" + ac.getModuleName() + "/" + ac.getControllerName() + "/"
										+ ac.getActionName() + ".do");
							}
						}
						String authString = JsonUtils.convertConllectionToJsonStr(permissionset);
						JedisUtils.set(dbType + ErpConst.KEY_PREFIX_USER_PERMISSIONS + user.getUsername().toLowerCase()
								+ user.getCompanyId(), authString, 0);
						CustomSqlistAuthorityImpl.clear(companyId.toString() + "#" + user.getUsername().toLowerCase(),
								permissionset);
					}
					// 公司放缓存
					Cookie cookie = new Cookie("lastLoginName", user.getUsername());
					cookie.setMaxAge(365 * 24 * 60 * 60);
					response.addCookie(cookie);
					// userName
					String serverName=request.getServerName();
					if(StringUtils.endsWith(serverName,"ivedeng.com")){
						serverName="ivedeng.com";
					}
					DoCookie doCookie=new DoCookie(serverName,request,response	);
					doCookie.addEncodeCookie(CoookieConstants.IVEDENG_SID,user.getUserId()+"@@"+user.getUsername(),3 * 24 * 60 * 60);

					// 用户公司
					if (null != user.getCompanyId() && user.getCompanyId() > 0) {

						Company company = companyService.getCompanyByCompangId(user.getCompanyId());
						user.setCompanyName(company.getCompanyName());
					}
					saveLoginLog(user, ip, agentInfo, ErpConst.ONE);
					List<Position> positionList = positService.getPositionByUserId(user.getUserId());
					if (null != positionList) {
						// 多个部门
						if (positionList.size() > 1) {
							user.setPositions(positionList);
							SpringContextHolder.setSession(  user,session);
							return "redirect:/selectorg.do";
						}
						// 单个部门
						if (positionList.size() == 1) {
							Integer orgId = positionList.get(0).getOrgId();
							Integer positType = positionList.get(0).getType();
							Integer positLevel = positionList.get(0).getLevel();
							String orgName = positionList.get(0).getOrgName();
							user.setPositions(positionList);
							user.setPositType(positType);
							user.setPositLevel(positLevel);
							user.setOrgId(orgId);
							user.setOrgName(orgName);
						}

					}

					SpringContextHolder.setSession(  user,session);

					session.removeAttribute("login_faild_times");
					session.removeAttribute(ErpConst.LOGIN_AUTH_CODE);
					return "redirect:/index.do";
				} else {
					saveLoginLog(user, ip, agentInfo, ErpConst.TWO);
					model.addAttribute("msg", ErpConst.SYSTEM_ERROR_MSG);
					if (null != session.getAttribute("login_faild_times")) {
						loginFaildTimes += Integer.parseInt(session.getAttribute("login_faild_times").toString());
					}
					session.setAttribute("login_faild_times", loginFaildTimes);
					model.addAttribute("login_faild_times", loginFaildTimes);
					return "login";
				}
			}

		} catch (AuthenticationException e) {
			user = new User();
			user.setUserId(0);
			user.setUsername(username);
			saveLoginLog(user, ip, agentInfo, ErpConst.TWO);
			String msg = e.getMessage();
			model.addAttribute("msg", msg);
			if (null != session.getAttribute("login_faild_times")) {
				loginFaildTimes += Integer.parseInt(session.getAttribute("login_faild_times").toString());
			}
			session.setAttribute("login_faild_times", loginFaildTimes);
			model.addAttribute("login_faild_times", loginFaildTimes);
			return "login";
		}

		return "login";
	}

	/**
	 * <b>Description:</b><br>
	 * 部门选择
	 * 
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月30日 下午1:58:38
	 */
	@RequestMapping(value = "/selectorg")
	public ModelAndView selectOrg(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("select_org");
		User user = (User) session.getAttribute(ErpConst.CURR_USER);

		// 获取部门
		List<Organization> orgList = orgService.getOrgList(0, user.getCompanyId(), true);
		mv.addObject("user", user);
		mv.addObject("orgList", orgList);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 部门选择
	 * 
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月30日 下午1:58:38
	 */
	@RequestMapping(value = "/changeorg")
	public ModelAndView changeOrg(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("change_org");
		User user = (User) session.getAttribute(ErpConst.CURR_USER);

		// 获取部门
		List<Organization> orgList = orgService.getOrgList(0, user.getCompanyId(), true);
		mv.addObject("user", user);
		mv.addObject("orgList", orgList);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 保存用户选择的部门
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月30日 下午2:01:14
	 */
	@ResponseBody
	@RequestMapping(value = "/savechangeorg")
	public ResultInfo saveChangeOrg(HttpServletRequest request, HttpSession session) {
		ResultInfo resultInfo = new ResultInfo<>();
		if (null != request.getParameter("orgId")) {
			Integer orgId = Integer.parseInt(request.getParameter("orgId"));

			if (orgId <= 0) {
				return resultInfo;
			}
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			Integer positType = 0;
			Integer positLevel = 0;
			List<Position> positions = user.getPositions();
			for (Position p : positions) {
				if (p.getOrgId().equals(orgId)) {
					positType = p.getType();
					positLevel = p.getLevel();
				}
			}

			Organization org = new Organization();
			org.setOrgId(orgId);
			Organization organization = orgService.getOrg(org);

			user.setOrgId(orgId);
			if (null != organization) {
				user.setOrgName(organization.getOrgName());
			}
			user.setPositType(positType);
			user.setPositLevel(positLevel);
			session.setAttribute(ErpConst.CURR_USER, user);

			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			return resultInfo;
		} else {
			return resultInfo;
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 保存用户选择的部门
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月30日 下午2:01:14
	 */
	@RequestMapping(value = "/saveselectorg")
	public Object saveSelectOrg(HttpServletRequest request, HttpSession session) {
		if (null != request.getParameter("orgId")) {
			Integer orgId = Integer.parseInt(request.getParameter("orgId"));

			if (orgId <= 0) {
				return pageNotFound();
			}
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			Integer positType = 0;
			Integer positLevel = 0;
			List<Position> positions = user.getPositions();
			for (Position p : positions) {
				if (p.getOrgId().equals(orgId)) {
					positType = p.getType();
					positLevel = p.getLevel();
				}
			}

			Organization org = new Organization();
			org.setOrgId(orgId);
			Organization organization = orgService.getOrg(org);

			user.setOrgId(orgId);
			if (null != organization) {
				user.setOrgName(organization.getOrgName());
			}
			user.setPositType(positType);
			user.setPositLevel(positLevel);
			session.setAttribute(ErpConst.CURR_USER, user);

			return "redirect:/index.do";
		} else {
			return pageNotFound();
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 记录用户登录日志
	 * 
	 * @param user
	 * @param lastLoginTime
	 * @param ip
	 * @param agentInfo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月22日 上午10:59:16
	 */
	private void saveLoginLog(User user, String ip, String agentInfo, int accessType) {
		/************** 用户登录日志开始 ****************/
		try {
			if(user==null){
				return ;
			}
			UserLoginLog ull = new UserLoginLog();
			ull.setUserId(user.getUserId());
			ull.setAccessTime(System.currentTimeMillis());
			ull.setIp(ip);
			ull.setUsername(user.getUsername());
			ull.setAccessType(accessType);
			ull.setAgentInfo(agentInfo);
			logger.info("saveLoginLog" + JSON.toJSONString(ull));
			loginLogService.saveOrUpdate(ull);
			/************** 用户登录日志结束 ****************/
		}catch (Exception e){
			//ignor
			logger.warn("saveLoginLog" + JSON.toJSONString(user));
		}
	}

	/**
	 * <b>Description:</b><br>
	 * session有效性检测
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月8日 下午5:38:23
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/checkSession.do")
	public ResultInfo checkSession() {
		ResultInfo rs = new ResultInfo<>();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser == null || !currentUser.isAuthenticated()) {
				rs.setCode(-1);
				rs.setMessage("session超时");

			} else {
				rs.setCode(0);
				rs.setMessage("成功");
			}
		} catch (Exception e) {
			logger.error("checkSession error:", e);
			rs.setCode(-1);
			rs.setMessage("session超时");
		}
		return rs;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * <b>Description:</b><br>
	 * 登出
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月28日 下午1:58:31
	 */
	@ResponseBody
	@RequestMapping(value = "/logout")
	public ResultInfo<?> logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			saveLoginLog(user, getIpAddress(request), request.getHeader("User-Agent"), 3);
		} catch (Exception e) {
			logger.error("logout error:", e);
		}
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		Subject currentUser2 = SecurityUtils.getSubject();
		ResultInfo resultInfo = new ResultInfo<>();
		if (currentUser2 == null || !currentUser2.isAuthenticated()) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		Cookie cookieName = new Cookie("userName", "1");
		cookieName.setMaxAge(0);
		cookieName.setPath("/");
		cookieName.setHttpOnly(true);
		response.addCookie(cookieName);

		String serverName=request.getServerName();
		if(StringUtils.endsWith(serverName,"ivedeng.com")){
			serverName="ivedeng.com";
		}
		DoCookie cookie=new DoCookie(serverName,request,response);
		cookie.deleteCookie(CoookieConstants.IVEDENG_SID);
		return resultInfo;
	}

}
