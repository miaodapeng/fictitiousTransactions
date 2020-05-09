package com.vedeng.trader.dao;

import java.util.Map;

import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import org.apache.ibatis.annotations.Param;

public interface TraderAddressMapper {

	/**
	 * 根据参数获取地址信息
	 * <p>Title: getAddressInfoByParam</p>  
	 * <p>Description: </p>  
	 * @param regionParamMap
	 * @return  
	 * @author Bill
	 * @date 2019年3月4日
	 */
	TraderAddress getAddressInfoByParam(Map<String, Object> regionParamMap);
    /**
     * 
     * <b>Description:</b>文件寄送获取默认联系人联系方式
     * @param fileDelivery
     * @return TraderContact
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2019年3月25日 下午6:44:00
     */
	TraderAddress getTraderContactInfo(FileDelivery fileDelivery);
	/**
	 * 
	 * <b>Description:</b>文件寄送获取默认联系人地址
	 * @param fileDelivery
	 * @return TraderContact
	 * @Note
	 * <b>Author：</b> scott.zhu
	 * <b>Date:</b> 2019年3月28日 上午9:23:36
	 */
	TraderAddress getTraderContactLxInfo(FileDelivery fileDelivery);

	/**
	 * 功能描述: 根据条件验证客户地址是否存在
	 * @param: [traderId, traderType, areaIds, areaId, address]
	 * @return: com.vedeng.trader.model.TraderAddress
	 * @auther: duke.li
	 * @date: 2019/8/13 17:08
	 */
	TraderAddress getAddressInfoByAddress(@Param("traderId") Integer traderId, @Param("traderType")Integer traderType, @Param("areaIds")String areaIds,@Param("areaId") Integer areaId, @Param("address")String address);

	/**
	 * 功能描述: 保存客户地址
	 * @param: [record]
	 * @return: int
	 * @auther: duke.li
	 * @date: 2019/8/13 17:08
	 */
	int insertSelective(TraderAddress record);
	/**
	* @Title: getAddressInfoById
	* @Description: TODO(根据TraderAddressId获取交易者地址信息)
	* @param @param takeTraderAddressId
	* @return TraderAddress    返回类型
	* @author strange
	* @throws
	* @date 2019年8月19日
	*/
	TraderAddress getAddressInfoById(@Param("traderAddressId")Integer traderAddressId,@Param("isEnable")Integer isEnable);

	TraderAddress getTraderDefaultAdressInfo(Integer traderId);
}
