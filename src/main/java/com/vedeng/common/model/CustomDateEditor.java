package com.vedeng.common.model;

import com.xxl.job.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * 类说明: BigDecimal custom property editor<br>
 * 创建时间: 2008-2-26 下午03:15:03<br>
 * 
 * @author Seraph<br>
 * @email: seraph@gmail.com<br>
 */
public class CustomDateEditor extends PropertyEditorSupport {

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			setValue(null);
		} else {
			setValue(DateUtil.parseDate(text));
		}
	}
}
