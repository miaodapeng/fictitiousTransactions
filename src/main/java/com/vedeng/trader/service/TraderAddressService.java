package com.vedeng.trader.service;

import java.util.Map;

import com.vedeng.trader.model.TraderAddress;

/**
 * 客户地址
 * <p>Title: TraderAddressService</p>  
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年3月4日
 */
public interface TraderAddressService {

	/**
	 * 根据addressId查询地址信息
	 * <p>Title: getAddressInfoByParam</p>  
	 * <p>Description: </p>  
	 * @param regionParamMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月4日
	 */
	TraderAddress getAddressInfoByParam(Map<String, Object> regionParamMap);

}
