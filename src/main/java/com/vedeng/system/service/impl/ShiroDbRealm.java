package com.vedeng.system.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.shiro.SpringContextHolder;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.util.UsernamePasswordComapnyIdToken;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.RoleService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONArray;
/**
 * <b>Description:</b><br> 权限验证
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service.impl
 * <br><b>ClassName:</b> ShiroDbRealm
 * <br><b>Date:</b> 2017年5月2日 下午3:50:22
 */
@Service
public class ShiroDbRealm extends AuthorizingRealm {

	/**
	 * @Fields userService : TODO
	 */
	private UserService userService;

	/**
	 * @Fields roleService : TODO
	 */
	private RoleService roleService;

	/**
	 * @Fields actionService : TODO
	 */
	private ActionService actionService;

	/**
	 * @Fields logger : TODO
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//private static final int role_permission_cacheSeconds=60*60*8;
	
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
//		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		UsernamePasswordComapnyIdToken token = (UsernamePasswordComapnyIdToken) authcToken;
		
		String name = token.getUsername();
		String pwd = String.valueOf(token.getPassword());
		Integer companyId = token.getCompanyId();

		// 校验登录验证码
		// if (LoginController.isValidateCodeLogin(token.getUsername(), false,
		// false)) {
		// Session session = UserUtils.getSession();
		// String code = (String)
		// session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		// if (token.getCaptcha() == null ||
		// !token.getCaptcha().toUpperCase().equals(code)) {
		// throw new AuthenticationException("msg:验证码错误, 请重试.");
		// }
		// }
		if(!token.isNeedPassword()){
			SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(name, "", getName());
			return sai;
		}
		// 校验用户名密码
		User user = getUserService().login(name, pwd, companyId);
		if (user != null) {
			if (1 == user.getIsDisabled()) {
				throw new AuthenticationException(ErpConst.USER_DISABLED_ERROR_MSG);
			}
			//非超级管理员
			if(user.getIsAdmin() == 0) {
				//用户可登录系统，贝登ERP：1 (运营平台 ：2 经销商平台 ：3)，当前登陆系统是ERP，判断是否包含 1
				if (!user.getSystems().contains("1")) {
					throw new AuthenticationException(ErpConst.USER_NOT_LOGIN_SYSTEM_MSG);
				}
			}
			SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user.getUsername(), pwd, getName());
			return sai;
		} else {
			throw new AuthenticationException(ErpConst.USERNAME_PWD_ERROR_MSG);
		}
	}
	
	/**
	 * <b>Description:</b><br> 字符串转set
	 * @param setStr
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 上午10:34:15
	 */
	@SuppressWarnings("unchecked")
	private Set<String> convertStringToSet(String setStr){
		JSONArray jsonArray=JSONArray.fromObject(setStr);
		List<String> list=(List<String>) JSONArray.toCollection(jsonArray, String.class);
		Set<String> set = new HashSet<String>();
		for (String str : list) {
			set.add(str);
		}
		return set;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 String username = (String) principals.getPrimaryPrincipal();
		 Session session = SecurityUtils.getSubject().getSession();
		 User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		if (ObjectUtils.notEmpty(username)) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			//角色名的集合
			Set<String> roleset = new HashSet<String>();
			//权限名的集合
			Set<String> permissionset = new HashSet<String>();
			String redisdb =  getUserService().getRedisDbType();
			String url = redisdb+ErpConst.KEY_PREFIX_USER_ROLES+username.toLowerCase()+session_user.getCompanyId();
			if(JedisUtils.exists(url)){
				String roles=JedisUtils.get(userService.getRedisDbType()+ErpConst.KEY_PREFIX_USER_ROLES+username.toLowerCase()+session_user.getCompanyId());
				roleset=convertStringToSet(roles);
			}
			if(JedisUtils.exists(userService.getRedisDbType()+ErpConst.KEY_PREFIX_USER_PERMISSIONS+username.toLowerCase()+session_user.getCompanyId())){
				String permissions=JedisUtils.get(userService.getRedisDbType()+ErpConst.KEY_PREFIX_USER_PERMISSIONS+username.toLowerCase()+session_user.getCompanyId());
				permissionset=convertStringToSet(permissions);
			}
			info.addRoles(roleset);
			info.addStringPermissions(permissionset);
			return info;
		}else{
			return null;
		}

	}
	


	/**
	 * <b>Description:</b><br>
	 * 获取userService实例 获取userService对象
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年4月27日 上午10:26:13
	 */
	private UserService getUserService() {
		if (userService == null) {
			userService = SpringContextHolder.getBean(UserService.class);
		}
		return userService;
	}

	/**
	 * <b>Description:</b><br>
	 * 获取roleService实例
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月2日 下午3:16:14
	 */
	private RoleService getRoleService() {
		if (null == roleService) {
			roleService = SpringContextHolder.getBean(RoleService.class);
		}
		return roleService;
	}

	/**
	 * <b>Description:</b><br>
	 * 获取actionService对象
	 * 
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年4月27日 上午10:41:41
	 */
	public ActionService getActionService() {
		if (actionService == null) {
			actionService = SpringContextHolder.getBean(ActionService.class);
		}
		return actionService;
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		// private boolean mobileLogin; // 是否手机登录

		// private Map<String, Object> cacheMap;

		public Principal(User user) {
			this.id = user.getUserId();
			this.loginName = user.getUsername();
			this.name = user.getRealName();
			// this.mobileLogin = mobileLogin;
		}

		public Integer getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		// public boolean isMobileLogin() {
		// return mobileLogin;
		// }

		// @JsonIgnore
		// public Map<String, Object> getCacheMap() {
		// if (cacheMap==null){
		// cacheMap = new HashMap<String, Object>();
		// }
		// return cacheMap;
		// }





		@Override
		public String toString() {
			return id.toString();
		}

	}
}
