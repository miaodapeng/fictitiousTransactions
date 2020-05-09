package com.vedeng.common.shiro;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.util.UsernamePasswordComapnyIdToken;
import org.apache.commons.lang3.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <b>Description:</b><br>
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp1 <br>
 *       <b>PackageName:</b> com.vedeng.common.service <br>
 *       <b>ClassName:</b> SpringContextHolder <br>
 *       <b>Date:</b> 2017年5月2日 下午1:52:25
 */
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("注入ApplicationContext到SpringContextHolder:{}", applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; // NOSONAR
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		Validate.validState(applicationContext != null,
				"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
	}

	public static boolean setSession(User user, HttpSession httpSession) {
		if(null == user) {
			return false;
		}
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(true);
		session.setAttribute(ErpConst.CURR_USER, user);
		httpSession.setAttribute(ErpConst.CURR_USER, user);
		UsernamePasswordComapnyIdToken token = new UsernamePasswordComapnyIdToken(user.getUsername(), "", 1);
		token.setNeedPassword(false);
		subject.login(token);
		return true;
	}
	public static User getSessionUser(HttpSession httpSession) {
		Subject currentUser = SecurityUtils.getSubject();
		User user=null;
		if(null != currentUser && null != currentUser.getSession()) {
			user=(User)currentUser.getSession().getAttribute(ErpConst.CURR_USER);
		}
		if(user==null){
			user=(User)httpSession.getAttribute(ErpConst.CURR_USER);
		}
		return user;
	}

}
