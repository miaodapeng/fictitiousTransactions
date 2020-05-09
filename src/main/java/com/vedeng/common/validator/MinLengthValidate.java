package com.vedeng.common.validator;

import org.apache.commons.lang3.StringUtils;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

/**
 * @author Hank
 *
 */
public class MinLengthValidate extends ValidatorHandler<Object> {

	private String tip = "必填";
	private int length = 1000;

	public MinLengthValidate(int length, String tip) {
		this.length = length;
		this.tip = tip;
	}

	@Override
	public boolean validate(ValidatorContext context, Object t) {
		if (t != null && StringUtils.length(String.valueOf(t)) < length) {
			context.addErrorMsg(tip);
			return false;
		}
		return true;
	}
}
