package com.vedeng.order.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.QuoteorderConsult;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.model.CommunicateRecord;

public interface QuoteService extends BaseService{

	/**
	 * <b>Description:</b><br> 查询报价列表数据
	 * @param quoteorder
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月26日 上午11:35:17
	 */
	Map<String,Object> getQuoteListPage(Quoteorder quoteorder,Page page);
	
	/**
	 * <b>Description:</b><br> 根据报价ID和商机ID...获取沟通
	 * @param list
	 * @param communicateType 类型
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月26日 上午11:36:01
	 */
	Integer getCommunicateRecordCount(CommunicateRecord cr,Integer bussinessType,Integer quoteType);
	
	/**
	 * <b>Description:</b><br> 获取订单沟通次数（包含商机，报价，销售订单）
	 * @param saleIdList
	 * @param quoteIdList
	 * @param businessIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月15日 下午1:28:10
	 */
	List<CommunicateRecord> getCommunicateNumList(List<Integer> saleIdList,List<Integer> quoteIdList, List<Integer> businessIdList);

	
	/**
	 * <b>Description:</b><br> 根据日期范围查询沟通记录
	 * @param beginDate
	 * @param endDate
	 * @param communicateType 沟通类型
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月26日 上午11:36:52
	 */
	List<Integer> getCommunicateRecordByDate(Long beginDate,Long endDate,String communicateType);
	
	/**
	 * <b>Description:</b><br> 保存报价信息
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:42:58
	 */
	ResultInfo<Quoteorder> saveQuote(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 查询报价详情信息
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:43:14
	 */
	Quoteorder getQuoteInfoByKey(Integer quoteorderId);
	
	/**
	 * <b>Description:</b><br> 修改报价中客户信息
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:43:29
	 */
	ResultInfo<?> updateQuoteCustomer(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 修改报价终端信息
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月29日 下午5:43:41
	 */
	ResultInfo<?> updateQuoteTerminal(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 保存报价产品信息
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:17:49
	 */
	ResultInfo<?> saveQuoteGoods(QuoteorderGoods quoteGoods,Attachment ach);
	
	/**
	 * <b>Description:</b><br> 根据报价产品ID查询报价产品信息
	 * @param quoteGoodsId
	 * @param httpSession 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:28:22
	 */
	QuoteorderGoods getQuoteGoodsById(Integer quoteGoodsId, HttpSession httpSession);
	
	/**
	 * <b>Description:</b><br> 根据报价ID查询报价产品（全部）
	 * @param quoteorderId
	 * @param hs 
	 * @param viewType 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:28:59
	 */
	Map<String, Object> getQuoteGoodsByQuoteId(Integer quoteorderId,Integer companyId, HttpSession hs, Integer viewType,Integer traderId);
	
	/**
	 * <b>Description:</b><br> 编辑保存报价产品信息
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:18:56
	 */
	ResultInfo<?> editQuoteGoods(QuoteorderGoods quoteGoods,Attachment ach);
	
	/**
	 * <b>Description:</b><br> 删除报价产品信息
	 * @param request
	 * @param quoteGoods
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月4日 上午10:19:35
	 */
	ResultInfo<?> delQuoteGoodsById(QuoteorderGoods quoteGoods);
	
	/**
	 * <b>Description:</b><br> 编辑报价付款金额等信息
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月5日 上午11:24:54
	 */
	ResultInfo<?> editQuoteAmount(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 根据外键查询沟通记录（分页）
	 * @param relatedIds
	 * @param communicateType
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月5日 上午11:36:22
	 */
	List<CommunicateRecord> getQuoteCommunicateListPage(String relatedIds,String communicateTypes,Page page);
	
	Map<Integer,String> getCommnuicateTraderTag(List<Integer> commnuicateIdList);
	
	/**
	 * <b>Description:</b><br> 修改报价是否生效
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午1:23:39
	 */
	ResultInfo<?> editQuoteValIdSave(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 保存报价咨询记录
	 * @param quoteConsult
	 * @param user 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午2:32:06
	 */
	ResultInfo<?> addQuoteConsultSave(QuoteorderConsult quoteConsult, User user);
	
	/**
	 * <b>Description:</b><br> 根据报价ID查找咨询记录
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午3:02:21
	 */
	List<QuoteorderConsult> getQuoteConsultList(Integer quoteorderId);

	/**
	 * <b>Description:</b><br> 修改报价主表信息(有沟通记录0无 1有)
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月7日 下午6:03:07
	 */
	ResultInfo<?> editQuoteHaveCommunicate(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 修改报价为失单状态（备注）
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 上午11:04:36
	 */
	ResultInfo<?> editLoseOrderStatus(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 验证报价单中产品货期和报价是否为空
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月25日 下午5:23:57
	 */
	ResultInfo<?> getQuoteGoodsPriceAndCycle(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 查询报价咨询列表（包括销售人员）
	 * @param quote
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月10日 下午3:32:50
	 */
	Map<String, Object> getQuoteConsultListPage(QuoteorderConsult quoteConsult, Page page,HttpSession session);

	/**
	 * <b>Description:</b><br> 咨询答复内容保存
	 * @param quoteConsult
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月11日 下午2:14:44
	 */
	ResultInfo<?> saveReplyQuoteConsult(QuoteorderConsult quoteConsult);

	/**
	 * <b>Description:</b><br> 修改报价咨询回复状态保存
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月11日 下午3:00:47
	 */
	ResultInfo<?> editConsultStatus(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 根据条件查询產品附件表信息
	 * @param ach
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月18日 下午6:42:24
	 */
	Attachment getQuoteGoodsAttachment(Attachment ach);
	
	/**
	 * <b>Description:</b><br> 根据条件查询报价记录数
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月24日 下午6:54:04
	 */
	Integer getQuoteListCount(Quoteorder quote);
	
	/**
	 * <b>Description:</b><br> 获取指定记录报价信息条数
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月24日 下午6:56:15
	 */
	List<Quoteorder> getQuoteListSize(Quoteorder quote,Integer startSize,Integer endSize);

	/**
	 * <b>Description:</b><br> 验证客户是否被禁用
	 * @param traderCustomerId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月26日 下午5:18:05
	 */
	ResultInfo<?> getTraderCustomerStatus(Integer traderCustomerId);

	/**
	 * <b>Description:</b><br> 验证报价中新添加的产品是否重复
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2017年9月11日 下午6:55:10
	 */
	ResultInfo<?> vailQuoteGoodsRepeat(QuoteorderGoods quoteGoods);
	/**
	 * 
	 * <b>Description:</b><br> 判断订单是否可以生效
	 * @param saleorder
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年11月10日 上午9:57:27
	 */
	ResultInfo<?> isvalidQuoteOrder(Quoteorder quote);
    /**
     * 
     * <b>Description:</b><br> 打印报价单
     * @param quote
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月13日 上午10:27:00
     */
	QuoteorderVo getPrintInfo(Quoteorder quote);

	/**
	 * <b>Description:</b><br> 获取报价同步信息
	 * @param quoteorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月16日 上午9:11:22
	 */
	Quoteorder getQuoteorderForSync(Integer quoteorderId);

	/**
	 * <b>Description:</b><br> 报价消息通知内容
	 * @param orderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月17日 下午3:02:07
	 */
	Quoteorder getMessageInfoForSync(Integer orderId);

	/**
	 * <b>Description:</b><br> 根据产品分类ID查询商品归属人员
	 * @param categoryIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年5月3日 上午10:28:05
	 */
	List<User> getGoodsCategoryUserList(List<Integer> categoryIdList,Integer companyId);
	
	/**
	 * 
	 * <b>Description:</b><br> 修改报价产品信息 
	 * @param quote
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年6月20日 下午3:29:46
	 */
	
	ResultInfo<?> editQuoteOrderGoods(Quoteorder quote);

	/**
	* @Description:  发送消息（您有一个待分配的注册用户，请查看）
	* @Param: []
	* @return: void
	* @Author: addis
	* @Date: 2019/7/30
	*/
	void sendAllocation(String mobile,Integer userId);
	
	/** 
	* @Description: 发送消息（离职人员指：用户为禁用状态） 
	* @Param: [mobile, userId] 
	* @return: void 
	* @Author: addis
	* @Date: 2019/7/30 
	*/ 
	void getIsDisabled(String mobile,Integer userId,String saleorderNo,Integer saleorderId);

	/**
	* @Title: updateQuote
	* @Description: TODO(更新报价)
	* @param @param quoteorder    参数
	* @return void    返回类型
	* @author strange
	* @throws
	* @date 2019年8月12日
	*/
	void updateQuote(Quoteorder quoteorder);

	/**
	 * @Description: 保存报价时，报价列表是是否存在
	 * @Param: [bussinessChanceId]
	 * @return: java.lang.Integer
	 * @Author: addis
	 * @Date: 2019/8/30
	 */
	Integer isExistBussinessChanceId(int bussinessChanceId);

}
