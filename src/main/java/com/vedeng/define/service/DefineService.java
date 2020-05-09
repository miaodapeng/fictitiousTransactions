/*
 * Copyright 2019 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.define.service;

import com.vedeng.common.service.BaseService;
import com.vedeng.define.model.DefineField;

/**
 *  DefineService.java
 *
 * @author lijie
 */
public interface DefineService extends BaseService{
	/** 
	 * <b>Description:</b>获取配置
	 * @param defineField
	 * @return DefineField
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:46:41
	 */
	DefineField getDefineFeld(DefineField defineField);

	/** 
	 * <b>Description:</b>保存配置
	 * @param defineField
	 * @return Boolean
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:46:49
	 */
	Boolean saveMyDefine(DefineField defineField);
}
