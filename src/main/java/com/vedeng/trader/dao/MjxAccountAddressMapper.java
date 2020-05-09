package com.vedeng.trader.dao;

import java.util.List;

import com.vedeng.trader.model.MjxAccountAddress;

public interface MjxAccountAddressMapper {
    int deleteByPrimaryKey(Integer addressId);

    int insert(MjxAccountAddress record);

    int insertSelective(MjxAccountAddress record);

    MjxAccountAddress selectByPrimaryKey(Integer addressId);

    int updateByPrimaryKeySelective(MjxAccountAddress record);

    int updateByPrimaryKey(MjxAccountAddress record);

	/**
	* @Title: getAddressInfoByParam
	* @Description: TODO(地址查重)
	* @param @param mjx
	* @return MjxAccountAddress    返回类型
	* @author strange
	* @throws
	* @date 2019年8月20日
	*/
	MjxAccountAddress getAddressInfoByParam(MjxAccountAddress mjx);

	/**
	* @Title: getMjxAccountAddressList
	* @Description: TODO(查询mjx客户地址信息)
	* @param @return    参数
	* @return List<MjxAccountAddress>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月20日
	*/
	List<MjxAccountAddress> getMjxAccountAddressList(MjxAccountAddress mjx);
}