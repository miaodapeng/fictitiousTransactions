package com.vedeng.system.service;

import java.util.List;

import com.vedeng.common.service.BaseService;
import com.vedeng.system.model.Address;
import com.vedeng.system.model.RegionCode;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;

public interface AddressService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 查询列表
	 * @param addressIds
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午5:23:16
	 */
	List<AddressVo> getAddressVoList(List<Integer> addressIds);
	
	/**
	 * <b>Description:</b><br> 查询所有收货地址
	 * @param addressIds
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午5:23:16
	 */
	List<AddressVo> getAddressVoList(Integer companyId);
	
	/**
	 * <b>Description:</b><br> 主键查询
	 * @param addressId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午6:45:48
	 */
	Address getAddressById(Integer addressId);
	
	/**
	 * <b>Description:</b><br> 获取发货地址
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午5:36:03
	 */
	AddressVo getDeliveryAddress(ParamsConfigVo paramsConfigVo);
	
	/**
	 * <b>Description:</b><br> 获取采购收货地址
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午5:36:03
	 */
	List<AddressVo> getBuyReceiveAddress(ParamsConfigVo paramsConfigVo);
    /**
     * 
     * <b>Description:</b><br> 获取地区编码
     * @param r
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月16日 上午11:28:30
     */
	RegionCode getRegionCode(RegionCode r);
    /**
     * 
     * <b>Description:</b><br> 查询地址信息
     * @param traderAddressId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月30日 下午1:45:24
     */
	String getAreaArrdess(Integer traderAddressId);
	

}
