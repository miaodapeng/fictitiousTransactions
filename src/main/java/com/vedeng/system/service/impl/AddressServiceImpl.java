package com.vedeng.system.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.system.dao.AddressMapper;
import com.vedeng.system.dao.RegionCodeMapper;
import com.vedeng.system.model.Address;
import com.vedeng.system.model.RegionCode;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.AddressService;
@Service("addressService")
public class AddressServiceImpl extends BaseServiceimpl implements AddressService {
	
	@Resource
	private AddressMapper addressMapper;
	
	@Resource
	private RegionCodeMapper regionCodeMapper;
	
	/**
	 * <b>Description:</b><br> 查询列表
	 * @param addressIds
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午5:23:16
	 */
	@Override
	public List<AddressVo> getAddressVoList(List<Integer> addressIds) {
		return addressMapper.getAddressVoList(addressIds);
	}
	/**
	 * <b>Description:</b><br> 主键查询
	 * @param addressId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午6:45:48
	 */
	public Address getAddressById(Integer addressId) {
		return addressMapper.selectByPrimaryKey(addressId);
	}
	
	/**
	 * <b>Description:</b><br> 获取发货地址
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午5:36:03
	 */
	@Override
	public AddressVo getDeliveryAddress(ParamsConfigVo paramsConfigVo) {
		AddressVo add = addressMapper.getAddressVoByConfigParams(paramsConfigVo);
		if(add != null){
			add.setAreas(getAddressByAreaId(add.getAreaId()));
		}
		return add;
	}
	
	/**
	 * <b>Description:</b><br> 获取采购收货地址
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午5:36:03
	 */
	@Override
	public List<AddressVo> getBuyReceiveAddress(ParamsConfigVo paramsConfigVo) {
		List<AddressVo> addList = addressMapper.getAddressVoListByConfigParams(paramsConfigVo);
		if(addList != null && addList.size() > 0){
			for (AddressVo add : addList) {
				add.setAreas(getAddressByAreaId(add.getAreaId()));
			}
		}
		return addList;
	}
	
	/**
	 * <b>Description:</b><br> 查询默认收货地址
	 * @param addressIds
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午5:23:16
	 */
	@Override
	public List<AddressVo> getAddressVoList(Integer companyId) {
		return addressMapper.getAddressVoListByParam(companyId);
	}
	@Override
	public RegionCode getRegionCode(RegionCode r) {
		return regionCodeMapper.selectRegionCodeInfo(r);
	}
	@Override
	public String getAreaArrdess(Integer traderAddressId) {
		// 接口调用
		String url = httpUrl + "/api/trader/gettraderaddressbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<String>> TypeRef2 = new TypeReference<ResultInfo<String>>() {};
		try {
			ResultInfo<String> result2 = (ResultInfo<String>) HttpClientUtils.post(url, traderAddressId, clientId, clientKey,TypeRef2);
			if (null == result2) {
				return null;
			}
			String res = (String) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}
}
