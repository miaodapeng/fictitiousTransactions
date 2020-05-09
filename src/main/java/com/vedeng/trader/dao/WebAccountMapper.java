package com.vedeng.trader.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.WebAccountVo;

public interface WebAccountMapper {
	/**
	 * <b>Description:</b><br> 加入注册信息
	 * @param webAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Addis
	 * <br><b>Date:</b> 2018年1月11日 下午4:14:00
	 */
    int insert(WebAccount webAccount);

    int update(WebAccountVo webAccountVo);
    
    /**
     * <b>Description:</b><br> 获取账号信息
     * @param webAccount
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2018年1月11日 下午1:31:58
     */
    WebAccountVo getWebAccount(WebAccount webAccount);

	/**
	 * <b>Description:</b><br> 注册用户列表
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午4:14:00
	 */
	List<WebAccountVo> getWebAccountListPage(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 获取账号归属信息
	 * @param webAccountId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月14日 下午7:22:32
	 */
	WebAccount getAccountOwnerInfo(@Param("webAccountId")Integer webAccountId);

	/**
	 * <b>Description:</b><br> 获取联系人WEB信息
	 * @param traderContactId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月27日 下午5:46:05
	 */
	WebAccount getTraderContactWebInfo(@Param("traderContactId")Integer traderContactId);

	/**
	 * <b>Description:</b><br>根据参数查询注册用户列表
	 *
	 *
	 * @param :[webAccount]
	 * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/20 3:24 PM
	 */
	List<WebAccount> getWebAccountListByParam(WebAccount webAccount);

	/**
	 * <b>Description:</b><br>根据手机号查询该用户是否是老用户
	 *
	 *
	 * @param :int
	 * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/1 3:24 PM
	 */
	int getMobileCount(WebAccount webAccount);

    /**
     * <b>Description:</b><br>根据手机号修改是否加入贝登精选状态
     *
     *
     * @param :int
     * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
     * @Note <b>Author:</b> Addis <br>
     *       <b>Date:</b> 2019/7/1 3:24 PM
     */
    int updateisVedengJoin(WebAccount webAccount);

	/**
	 * <b>Description:</b><br>根据手机号码查询1、是否有效 2、是否是贝登会员 3、是否申请加入贝登精选
	 *
	 *
	 * @param :webAccount
	 * @return :webAccount
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/2 3:24 PM
	 */
	WebAccount selectMobileResult(WebAccount webAccount);

    /**
     * 功能描述: 修改注册用户客户归属
     * @param: [webAccountVo]
     * @return: int
     * @auther: duke.li
     * @date: 2019/7/30 14:27
     */
    int updateErpUserId(WebAccountVo webAccountVo);

	/**
	* @Title: getWebAccountIdByMobile
	* @Description: TODO 注册手机号获取注册id
	* @param @param traderContactTelephone
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月23日
	*/
	Integer getWebAccountIdByMobile(String traderContactTelephone);

	/**
	* @Title: getTraderIdByErpAccountId
	* @Description: TODO(获取traderId)
	* @param @param erpAccountId
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月24日
	*/
	Integer getTraderIdByErpAccountId(Integer erpAccountId);

	/**
	* @Title: updateTraderUser
	* @Description: TODO 更新交易者和用户关系表
	* @param @param traderId
	* @param @param userId
	* @param @return    参数
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年7月24日
	*/
	Integer updateTraderUser(@Param("traderId")Integer traderId,@Param("userId") Integer userId);

	/**
	* @Title: getWebAccountInfo
	* @Description: TODO(获取注册用户信息)
	* @param @param erpAccountId
	* @return WebAccount    返回类型
	* @author strange
	* @throws
	* @date 2019年8月6日
	*/
	WebAccount getWebAccountInfo(@Param("erpAccountId")Integer erpAccountId);

	/**
	* @Title: getWenAccountInfoByMobile
	* @Description: TODO(注册手机号获取注册用户信息)
	* @param @param createMobile
	* @param @return    参数
	* @return WebAccount    返回类型
	* @author strange
	* @throws
	* @date 2019年8月6日
	*/
	WebAccount getWenAccountInfoByMobile(String createMobile);

	List<WebAccount> getWebAccountListByMobile(String mobile);
	
	/** 
	* @Description: 获取归属销售id和客户id
	* @Param: [webAccount] 
	* @return: com.vedeng.trader.model.WebAccount 
	* @Author: addis
	* @Date: 2019/8/13 
	*/ 
	WebAccount getWebAccontAuthous(WebAccount webAccount);
	
	/** 
	* @Description: 查询所有trader下的手机号 
	* @Param: [webAccount] 
	* @return: java.util.List<com.vedeng.trader.model.WebAccount>
	* @Author: addis
	* @Date: 2019/8/13 
	*/ 
	List<WebAccount> getWebAccontTrader(WebAccount webAccount);


	/**
	 * 更新已关联企业的账号的归属平台，归属平台为关联企业的归属平台
	 */
	void updateBelongPlatformOfHasTrader();


	/**
	 * 更新没有销售和关联企业的账号的归属平台为注册平台；
	 */
	void updateBelongPlatformOfNoUserIdAndNoTrader();

	/**
	 * 查找WebAccount表中没有关联企业但关联的销售
	 * @return
	 */
	List<Integer> getSalerIdListOfNoTrader();

	void updateBelongPlatformByUserIdList(@Param("userList") List<Integer> userList, @Param("belongPlatform") Integer belongPlatform);
	/**
	*获取符合变更为贝登会员的用户
	* @Author:strange
	* @Date:10:31 2020-04-07
	*/
    List<Integer> getWebAccountIsMember();
	/**
	*更新贝登会员状态
	* @Author:strange
	* @Date:11:27 2020-04-07
	*/
	Integer updateVedengMember(List<Integer> webAccountList);
	/**
	*更新贝登会员开通时间
	* @Author:strange
	* @Date:18:47 2020-04-07
	*/
	Integer updateVedengMemberTime(List<Integer> webAccountList);
	/**
	*获取是贝登会员的用户信息
	* @Author:strange
	* @Date:14:18 2020-04-08
	*/
	List<WebAccount> getWebAccountMemberInfo();
	/**
	*更新贝登会员状态
	* @Author:strange
	* @Date:14:40 2020-04-08
	*/
	Integer updateVedengNoMember(List<Integer> noMemberidList);
	/**
	*获取是贝登会员的客户信息
	* @Author:strange
	* @Date:18:35 2020-04-08
	*/
	List<WebAccount> getTraderCustomerMemberInfo();
}
