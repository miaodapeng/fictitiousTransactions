package com.vedeng.trader.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vedeng.trader.model.RTraderJUser;
@Repository("rTraderJUserMapper")
public interface RTraderJUserMapper {
    /**
     * <b>Description:</b><br> 新增归属
     * @param rTraderJUser
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月17日 上午9:22:09
     */
    int insert(RTraderJUser rTraderJUser);
    
    List<Integer> getRTraderJUserListByUserId(RTraderJUser rTraderJUser);
    
    /**
     * <b>Description:</b><br> 查询用户归属客户/供应商数量
     * @param rTraderJUser
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月5日 下午7:04:14
     */
    List<RTraderJUser> getUserTrader(RTraderJUser rTraderJUser);
    
    /**
     * <b>Description:</b><br> 删除归属
     * @param rTraderJUser
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月6日 下午1:38:41
     */
    int deleteRTraderJUser(RTraderJUser rTraderJUser);
    
    /**
     * <b>Description:</b><br> 批量更改归属
     * @param from_user
     * @param batch_to_user
     * @param traderType
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月6日 下午1:46:55
     */
    int update(@Param("fromUser")Integer fromUser, @Param("toUser")Integer toUser,@Param("traderType")Integer traderType);
    
    /**
     * <b>Description:</b><br> 根据主键批量更改归属
     * @param toUser
     * @param rTraderJUserIds
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月6日 下午5:10:17
     */
    int updateByKey(@Param("toUser")Integer toUser,@Param("rTraderJUserIds")List<Integer> rTraderJUserIds);
    
    /**
	 * <b>Description:</b><br> 根据交易者查询归属
	 * @param traderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月25日 下午1:32:00
	 */
	List<RTraderJUser> getRTraderJUserByTraderIds(@Param("traderIds")List<Integer> traderIds, @Param("traderType")Integer traderType);


    /**
     * 根据联系方式查找客户对应的销售
     * @param mobile 手机号
     * @return 销售
     */
	List<RTraderJUser> getSaleUserIdByContactMobile(String mobile);

	/**
	 * <b>Description:</b>根据客户标识获取用户<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/4/3
	 */
	RTraderJUser getUserByTraderId(@Param("traderId")Integer traderId);
}