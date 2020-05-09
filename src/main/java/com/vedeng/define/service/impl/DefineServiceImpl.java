/*
 * Copyright 2019 Focus Technology, Co., Ltd. All rights reserved.
 */
package com.vedeng.define.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.define.dao.DefineFieldMapper;
import com.vedeng.define.model.DefineField;
import com.vedeng.define.service.DefineService;

/**
 *  DefineServiceImpl.java
 *
 * @author lijie
 */
@Service("defineService")
public class DefineServiceImpl extends BaseServiceimpl implements DefineService {
	@Autowired
	@Qualifier("defineFieldMapper")
	private DefineFieldMapper defineFieldMapper;

	@Override
	public DefineField getDefineFeld(DefineField defineField) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;  
    	HttpServletRequest request = sra.getRequest();   
        HttpSession session = request.getSession();    
        //读取session中的用户    
        User user = (User) session.getAttribute(ErpConst.CURR_USER);  
        
        defineField.setUserId(user.getUserId());
        
        DefineField newField = defineFieldMapper.getDefineFeld(defineField);
        
        return newField;
	}

	@Override
	public Boolean saveMyDefine(DefineField defineField) {
		//删除所有的
		//defineFieldMapper.deleteMyDefine(defineField);
		
		//批量更新
		//defineField.setIsShow(0);
		//defineFieldMapper.updateMyDefine(defineField);
		
		defineField.setAddTime(DateUtil.sysTimeMillis());
		if(null != defineField.getFields() && !"".equals(defineField.getFields())){
			String[] defineFieldArr = defineField.getFields().split(",");
			//循环增加数据  批量？单个？
			for(String field : defineFieldArr){
				defineField.setField(field);
				
				DefineField df = defineFieldMapper.getDefineFeld(defineField);
				if(null != df){
					df.setIsShow(1);
					defineFieldMapper.updateMyDefine(df);
				}else{
					defineField.setIsShow(1);
					defineFieldMapper.insertSelective(defineField);
				}
			}
		}
		
		if(null != defineField.getUnfields() && !"".equals(defineField.getUnfields())){
			String[] unDefineFieldArr = defineField.getUnfields().split(",");
			//循环增加数据  批量？单个？
			for(String field : unDefineFieldArr){
				defineField.setField(field);
				
				DefineField df = defineFieldMapper.getDefineFeld(defineField);
				if(null != df){
					df.setIsShow(0);
					defineFieldMapper.updateMyDefine(df);
				}else{
					defineField.setIsShow(0);
					defineFieldMapper.insertSelective(defineField);
				}
			}
		}
		
		return true;
	}

}
