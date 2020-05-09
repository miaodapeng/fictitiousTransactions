package com.vedeng.common.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.beans.PropertyEditorSupport;

/**
 * 类说明: BigDecimal custom property editor<br>
 * 创建时间: 2008-2-26 下午03:15:03<br>
 * 
 * @author Seraph<br>
 * @email: seraph@gmail.com<br>
 */
public class CustomBigDecimalEditor extends PropertyEditorSupport {

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			setValue(null);
		} else {
			setValue(NumberUtils.createBigDecimal(text));
		}
	}
}
