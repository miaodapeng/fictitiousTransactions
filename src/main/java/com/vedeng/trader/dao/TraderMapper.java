
package com.vedeng.trader.dao;

import com.vedeng.system.model.vo.AccountSalerToGo;
import org.apache.ibatis.annotations.Param;

import com.vedeng.trader.model.Trader;

import java.util.List;
import java.util.Map;


/**
 * <b>Description: 客户基本信息mapper</b><br> 
 * <b>Author: Franlin.wu</b> 
 * @fileName TraderMapper.java
 * <br><b>Date: 2019年5月13日 下午1:33:45 </b> 
 *
 */
public interface TraderMapper
{
	/**
	 * 
	 * <b>Description: 根据traderId</b><br> 
	 * @param traderId
	 * @param string 
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2019年5月13日 下午1:35:23 </b>
	 */
	Trader getTraderByTraderId(@Param("traderId") Integer traderId);
	/**
	 * 
	 * <b>Description: 根据traderId,和手机号获取客户基本信息</b><br> 
	 * @param traderId
	 * @param string 
	 * @return
	 * <b>Author: strange</b>  
	 * <br><b>Date: 2019年5月13日 下午1:35:23 </b>
	 */
	Trader getTraderInfoByTraderId(@Param("traderId") Integer traderId);
	
	/**
	* @Title: getTraderBussinessAreaByTraderId
	* @Description: TODO(根据traderid获取交易者的经营范围areaids)
	* @param @param traderId
	* @return String    返回类型
	* @author strange
	* @throws
	* @date 2019年7月22日
	*/
	String getTraderBussinessAreaByTraderId(@Param("traderId") Integer traderId);
	
	
	/**
	* @Title: getTraderCompanyByTraderId
	* @Description: TODO(获取交易者公司信息)
	* @param @param traderId
	* @return Trader    返回类型
	* @author strange
	 * @param integer 
	* @throws
	* @date 2019年7月25日
	*/
	Trader getTraderCompanyByTraderId(@Param("traderId")Integer traderId,@Param("traderAddressId") Integer traderAddressId);
	/**
	* @Title: getTraderNameByTraderContactId
	* @Description: TODO(获取交易者姓名电话和id)
	* @param @param traderContactId
	* @param @return    参数
	* @return Trader    返回类型
	* @author strange
	* @throws
	* @date 2019年7月25日
	*/
	Trader getTraderNameByTraderContactId(Integer traderContactId);

	/**
	 * 客户归属推送（不要调用）
	 * <b>Description:</b>
	 *
	 * @param map
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2019年9月24日 下午6:44:07
	 */
	List<AccountSalerToGo> getAccountSaler(List<Integer> list);
	/**
	* @Title: getTraderTypeById
	* @Description: TODO(获取客户类型)
	* @param @param traderId
	* @return Integer    返回类型
	* @author strange
	* @throws
	* @date 2019年8月26日
	*/
	Integer getTraderTypeById(@Param("traderId")Integer traderId);


	/**
	 * <b>Description:</b>分页获取客户信息，traderType=1<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/2/29
	 */
	List<Trader> getTraderListPage(Map<String,Object> map);


	/**
	* @Description: 修改客户列表
	* @Param:
	* @return:
	* @Author: addis
	* @Date: 2020/3/3
	*/
	Integer updateTrader(Trader trader);

	/**
	 * <b>Description:</b>修改客户列表部分字段<br>
	 * @param trader 客户信息
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	void updatePartBySelective(Trader trader);

	Trader getTraderWrapByTraderId(Integer traderId);

	List<Integer> getAllUserId();

	void updateTraderPlatformByUserList(@Param("belongPlatform") Integer belongPlatform, @Param("userList") List<Integer> userList);


	Trader getTraderAptitudeCheckedByTraderId(@Param("traderId")Integer traderId);

	/**
	*获取宝石花客户id
	* @Author:strange
	* @Date:16:23 2020-02-20
	*/
    List<Integer> getFlowerTraderId(@Param("traderNameList") List<String> traderNameList);
}

