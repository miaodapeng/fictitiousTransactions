package com.vedeng.trader.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.trader.dao.TraderAddressMapper;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.service.TraderAddressService;

/**
 * 客户地址信息
 * <p>Title: TraderAddressServiceImpl</p>  
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年3月4日
 */
@Service("traderAddressService")
public class TraderAddressServiceImpl extends BaseServiceimpl implements TraderAddressService {
	
	@Autowired
	@Qualifier("traderAddressMapper")
	private TraderAddressMapper traderAddressMapper;
	

	@Override
	public TraderAddress getAddressInfoByParam(Map<String, Object> regionParamMap) {
		
		return traderAddressMapper.getAddressInfoByParam(regionParamMap);
	}
	
}
