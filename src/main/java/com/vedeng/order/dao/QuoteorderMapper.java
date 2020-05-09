package com.vedeng.order.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.QuoteorderConsult;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.trader.model.TraderCustomer;


@Named("quoteorderMapper")
public interface QuoteorderMapper {
	
	/**
	 * <b>Description:</b><br> 查询报价列表数据
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:32:09
	 */
	List<Quoteorder> getQuoteListPage(Map<String, Object> map);
	
	/**
	 * <b>Description:</b><br> 获取报价记录中部门信息
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:32:15
	 */
	List<Integer> getQuoteOrgList();
	
	/**
	 * <b>Description:</b><br> 获取报价信息中销售人员信息
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:32:31
	 */
	List<Integer> getQuoteUserList(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 根据主键删除报价信息
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:32:50
	 */
    int deleteByPrimaryKey(Integer quoteorderId);

    /**
     * <b>Description:</b><br> 添加保存报价信息
     * @param record
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月4日 上午10:33:09
     */
    int saveQuote(Quoteorder record);
    
    /**
     * <b>Description:</b><br> 修改报价单号
     * @param record
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月4日 上午10:33:22
     */
    int updateQuoteNo(Quoteorder record);

    /**
     * <b>Description:</b><br> 根据ID获取报价信息
     * @param quoteorderId
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月4日 上午10:33:34
     */
    Quoteorder getQuoteInfoByKey(Integer quoteorderId);

    /**
     * <b>Description:</b><br> 修改报价信息
     * @param record
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月4日 上午10:33:44
     */
    int updateQuote(Quoteorder record);
    
    /**
     * <b>Description:</b><br> 修改终端用户信息
     * @param record
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月4日 上午10:34:09
     */
    int updateQuoteTerminal(Quoteorder record);

    
    /**
     * <b>Description:</b><br> 查询报价
     * @param record
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月28日 下午2:43:02
     */
    Quoteorder getQuoteorder(Quoteorder record);
    
    int editQuoteAmount(Quoteorder record);
    
    /**
     * <b>Description:</b><br> 获取客户所有报价的ID
     * @param traderId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月5日 下午2:54:04
     */
    List<Integer> getCustomerQuoterorderIds(@Param("traderId")Integer traderId);

    /**
     * <b>Description:</b><br> 修改报价是否生效
     * @param quote
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年7月7日 下午1:44:26
     */
	int editQuoteValIdSave(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 修改报价主表信息(有沟通记录0无 1有)
	 * @param record
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午6:06:33
	 */
	int editQuoteHaveCommunicate(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 修改报价为失单状态（备注）
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 上午11:09:40
	 */
	int editLoseOrderStatus(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 获取报价咨询列表（包括销售人员）
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 下午3:52:54
	 */
	List<QuoteorderConsult> getQuoteConsultListPage(Map<String, Object> map);
	
	/**
	 * <b>Description:</b><br> 获取客户近一个月内的报价
	 * @param quoteorder
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月11日 下午5:30:27
	 */
	List<Quoteorder> getLastMonthQuoteorder(Quoteorder quoteorder);
	
	/**
	 * <b>Description:</b><br> 修改报价咨询回复状态保存
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月11日 下午3:11:48
	 */
	int editConsultStatus(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 获取客户生效的订单
	 * @param traderId
	 * @param limit
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月13日 上午10:46:36
	 */
	List<Quoteorder> getEffectOrders(@Param("traderId")Integer traderId,@Param("limit")Integer limit);

	/**
	 * <b>Description:</b><br> 清理过期的报价单（使之失效）
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月17日 上午11:46:39
	 */
	int editQuoteExpired(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 根据报价ID集合查询报价信息
	 * @param quoteOrderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月18日 上午10:48:55
	 */
	List<Quoteorder> getQuoteorderByIds(@Param("quoteOrderIds") List<Integer> quoteOrderIds);
	
	/**
	 * <b>Description:</b><br> 查询销售工程师跟单中的报价
	 * @param quoteOrderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月18日 上午10:48:55
	 */
	List<QuoteorderVo> getQuoteorderByParam(@Param("quoteOrderIds") List<Integer> quoteOrderIds);
	
	/**
	 * <b>Description:</b><br> 查询销售工程师跟单中的报价--创建日期为今天
	 * @param quoteOrderIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月18日 上午10:48:55
	 */
	List<QuoteorderVo> getQuoteorderByAddTime(Quoteorder quoteorder);
	
	/**
	 * <b>Description:</b><br> 修改商机状态
	 * @param bussinessChanceId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月25日 上午10:36:39
	 */
	int updateBussinessStatus(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 验证报价产品（报价金额和货期）
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月25日 下午5:04:59
	 */
	Map<String,BigDecimal> vailQuoteGoodsPriceAndCycle(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 验证报价产品数量
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月4日 上午11:25:25
	 */
	int vailQuoteGoodsNum(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 验证客户
	 * @param traderCustomerId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月26日 下午5:54:51
	 */
	TraderCustomer getTraderCustomerStatus(@Param("traderCustomerId")Integer traderCustomerId);

	/**
	 * <b>Description:</b><br> 获取查询报价记录总数
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月27日 下午2:53:51
	 */
	int getQuoteListCount(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 获取指定记录报价信息条数
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月27日 下午3:46:29
	 */
	List<Quoteorder> getQuoteListSize(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 修改报价中客户信息
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月4日 下午4:03:28
	 */
	int updateQuoteCustomer(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 有报价的客户id集合
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年9月13日 下午3:22:54
	 */
	List<Integer> getQuoteTraderIdList(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 产品报价数量（订单生效时间）
	 * @param goodsId
	 * @param time
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月9日 下午6:41:50
	 */
	Integer getQuoteNumByTime(@Param("goodsId")Integer goodsId, @Param("time")long time);
	/**
	 * <b>Description:</b><br> 获取报价产品分类
	 * @param quoteList
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 下午5:37:53
	 */
	List<QuoteorderConsult> getQuoteorderGoodsCateory(@Param("quoteList")List<QuoteorderConsult> quoteList);
	
	/**
	 * <b>Description:</b><br> 获取销售工程师的本月报价和报价金额
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月30日 下午1:08:24
	 */
	QuoteorderVo getSalesEngineerQuoteorder(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 获取待同步的报价主信息
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月9日 下午2:17:41
	 */
	List<Quoteorder> getWaitSyncQuoteorderList();
    /**
     * 
     * <b>Description:</b><br> 打印报价单
     * @param quote
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月13日 上午10:31:58
     */
	QuoteorderVo getPrintInfo(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 获取报价信息
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月16日 上午9:23:39
	 */
	Quoteorder getQuoteorderInfo(@Param("quoteorderId")Integer quoteorderId);

	/**
	 * <b>Description:</b><br> 报价消息通知内容
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月17日 下午3:07:26
	 */
	Quoteorder getMessageInfoForSync(@Param("quoteorderId")Integer quoteorderId);

	/**
	 * <b>Description:</b><br> 通过单号查询报价单
	 * @param quoteorderNo
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月20日 下午1:49:41
	 */
	Quoteorder getQuoteorderByNo(@Param("quoteorderNo")String quoteorderNo);

	/**
	 * <b>Description:</b><br> 验证商机是否已报价 
	 * @param bussinessChanceId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年4月20日 下午4:46:29
	 */
	int getQuoteOrderByBussinessChanceId(@Param("bussinessChanceId")Integer bussinessChanceId,@Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 查询报价单咨询回复人员
	 * @param quoteIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年7月20日 上午9:09:57
	 */
	List<QuoteorderConsult> getQuoteConsultListByQuoteId(@Param("quoteIdList")List<Integer> quoteIdList);

	/**
	* @Title: getQuoteInfoByQuoteordeNo
	* @Description: TODO(根据报价单号获取报价单信息)
	* @param @param record
	* @param @return    参数
	* @return Quoteorder    返回类型
	* @author strange
	* @throws
	* @date 2019年7月27日
	*/
	Quoteorder getQuoteInfoByQuoteordeNo(Quoteorder record);

	/**
	* @Title: getQuoteInfoByQuoteordeId
	* @Description: TODO(根据报价id获取报价单信息)
	* @param @param record
	* @param @return    参数
	* @return Quoteorder    返回类型
	* @author strange
	* @throws
	* @date 2019年7月29日
	*/
	Quoteorder getQuoteInfoByQuoteordeId(Quoteorder record);
    
	 
	/**
	* @Description: 保存报价时，报价列表是是否存在 
	* @Param: [bussinessChanceId] 
	* @return: java.lang.Integer 
	* @Author: addis
	* @Date: 2019/8/30 
	*/ 
	Integer isExistBussinessChanceId(int bussinessChanceId);
}