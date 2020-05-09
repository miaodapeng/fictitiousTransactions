/*
 * Copyright 2019 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.common.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.vedeng.define.model.DefineField;
import com.vedeng.define.service.DefineService;

/**
 * DefineTag.java
 *
 * @author lijie
 */
public class DefineTag extends TagSupport {
	public static Logger logger = LoggerFactory.getLogger(DefineTag.class);

	WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

	private static final long serialVersionUID = -309669695875832682L;

	private String bussiness;

	private String field;
	
	private DefineService defineService =  (DefineService) context.getBean("defineService");

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public DefineTag() {

	}

	@Override
	public int doStartTag() throws JspException {
		try {
			//判断参数不为空
			if (null == bussiness || "".equals(bussiness) || null == field || "".equals(field)) {
				pageContext.getOut().write("");
			}else{
				DefineField defineField = new DefineField();
				defineField.setBussiness(bussiness);
				defineField.setField(field);
				//获取配置
				DefineField defineFeld = defineService.getDefineFeld(defineField);
				if(null != defineFeld && defineFeld.getIsShow() == 0){
					//不展示
					pageContext.getOut().write(" class='none'");
				}else{
					//展示
					pageContext.getOut().write("");
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return super.doStartTag();
	}

}
