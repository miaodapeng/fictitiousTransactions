/*
 * Copyright 2019 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.define.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.define.model.DefineField;
import com.vedeng.define.service.DefineService;

/**
 *  DefineController.java
 *
 * @author lijie
 */
/**
 * <b>Description:</b><br> 列表自定义项
 * @author lijie
 * @Note
 * <b>ProjectName:</b> erp.vedeng.com
 * <br><b>PackageName:</b> com.vedeng.define.controller
 * <br><b>ClassName:</b> DefineController
 * <br><b>Date:</b> 2019年3月12日 下午1:45:50
 */
@Controller
@RequestMapping("/define")
public class DefineController extends BaseController {
	@Autowired
	@Qualifier("defineService")
	private DefineService defineService;
	

	/** 
	 * <b>Description:</b>保存我的自定义信息
	 * @param request
	 * @param session
	 * @param defineField
	 * @return ResultInfo
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:46:06
	 */
	@ResponseBody
	@RequestMapping(value = "/savemydefine")
	public ResultInfo saveMyDefine(HttpServletRequest request, HttpSession session,
			DefineField defineField){
		User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
		ResultInfo resultInfo = new ResultInfo<>();
		
		defineField.setUserId(session_user.getUserId());
		defineField.setCreator(session_user.getUserId());
		Boolean save = defineService.saveMyDefine(defineField);
		if(save){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		
		return resultInfo;
	}

}
