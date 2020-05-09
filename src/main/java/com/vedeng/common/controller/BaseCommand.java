package com.vedeng.common.controller;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.goods.GoodsConstants;
import com.vedeng.common.exception.ShowErrorMsgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

public class BaseCommand {

	public Logger logger = LoggerFactory.getLogger(getClass());
	private Integer pageNo = 1;
	private Integer pageSize = 10;
	private List<String> errors;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public User getUser() throws ShowErrorMsgException {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();// .get


		User user = (User) servletRequestAttributes.getRequest().getSession().getAttribute(Consts.SESSION_USER);
		if (user == null) {
			logger.error("session中找不到user");
			throw new ShowErrorMsgException(CommonConstants.FAIL_CODE, "登陆信息过期");
		}
		return user;
	}
	// 市场部商品组全员可编辑非临时商品，市场部商品组全员、供应链综合商务组部门全员可编辑临时商品，所有审核为商品组主管
	public boolean isHasEditAuth() throws ShowErrorMsgException {
		User user = getUser();
		if (isHasCheckAuth()) {
			return true;
		}
		return user.hasRole(GoodsConstants.GOODS_EDIT_ROLE);
	}
	public boolean isHasEditTempAuth() throws ShowErrorMsgException {
		if (isHasCheckAuth() ) {
			return true;
		}
		return getUser().hasRole(GoodsConstants.GOODS_EDIT_TEMP_ROLE);
	}
	public boolean isHasCheckAuth() throws ShowErrorMsgException {
		return getUser().hasRole(GoodsConstants.GOODS_CHECK_ROLE);
	}

}
