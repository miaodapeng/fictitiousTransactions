package com.vedeng.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vedeng.authorization.model.User;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.system.dao.ParamsConfigValueMapper;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.service.ParamsConfigValueService;

@Service("paramsConfigValueService")
public class ParamsConfigValueServiceImpl extends BaseServiceimpl implements ParamsConfigValueService{
	
	@Resource
	private ParamsConfigValueMapper paramsConfigValueMapper;
	/**
	 * <b>Description:</b><br> 查询ParamsValue的集合
	 * @param paramsConfigValue
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午4:57:12
	 */
	@Override
	public List<Integer> getParamsValueList(ParamsConfigValue paramsConfigValue) {
		
		return paramsConfigValueMapper.getParamsValueList(paramsConfigValue);
	}
	@Override
	public User getQuoteConsultant(Integer companyId, int quoteType) {
		return paramsConfigValueMapper.getQuoteConsultant(companyId, quoteType);
	}

}
