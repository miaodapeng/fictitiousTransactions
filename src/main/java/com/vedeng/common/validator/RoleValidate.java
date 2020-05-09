package com.vedeng.common.validator;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.Consts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Hank
 *
 */
public class RoleValidate extends ValidatorHandler<String> {

	private String tip = "权限不足";


	public RoleValidate(String roleName) {

		this.tip = tip;
	}

	@Override
	public boolean validate(ValidatorContext context, String roleName) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();// .get
		User user = (User) servletRequestAttributes.getRequest().getSession().getAttribute(Consts.SESSION_USER);
		if (user == null) {
			 return false;
		}
		return user.hasRole(roleName);
	}
}
