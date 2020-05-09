package com.vedeng.order.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.User;
import com.vedeng.order.model.*;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.trader.model.TraderOrderGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 销售订单
 * @author Administrator
 *
 */
public interface SaleorderMapper {

	int insertSelective(Saleorder record);

	/**
	 * <b>Description:</b>获取一定时间段内下单天数和订单总额<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/10/30
	 */
	SaleorderCountResult getDaysCountSum(SaleorderCountParam param);
	/**
	 * <b>Description:</b>根据客户标识查询订单所属商品的sku<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> ${date} ${time}
	 */
	List<String> getOrderGoodsSkuByTraderId(TraderOrderGoods param);
	/**
	 * <b>Description:</b>根据订单时间段和TraderId查询订单个数<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/10/29
	 */
	Integer getSaleorderCountByTime(SaleorderCountParam saleorder);
	/**
	 * 查询销售订单id
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> bill.bo
	 * <br><b>Date:</b> 2019年2月27日
	 */		
	List<Integer> getSaleOrderIdListByParam(Map<String, Object> paraMap);

	/**
	 * 根据订单id查询订单信息
	 * <b>Description:</b><br> 
	 * @param request
	 * @param session
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> bill.bo
	 * <br><b>Date:</b> 2019年2月27日
	 */		
	List<Saleorder> getOrderListInfoById(Map<String, Object> paraMap);
	
	/**
	 * 
	 * <b>Description:</b>查询销售订单合同回传相关信息
	 * @param contract
	 * @return List<SaleorderContract>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月5日 下午3:31:09
	 */
	List<SaleorderContract> getContractReturnOrderListPage(Map<String, Object> paraMap);
	
	/**
	 * 
	 * <b>Description:</b>查询销售订单合同回传总数
	 * @param paraMap
	 * @return Integer
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月12日 下午1:24:19
	 */
	Integer getContractReturnOrderListCount(Map<String, Object> paraMap);
	
	/**
	 * 
	 * <b>Description:</b>查询销售订单合同回传不合格
	 * @param paraMap
	 * @return List<SaleorderContract>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月12日 下午1:25:03
	 */
	List<SaleorderContract> getContractReturnOrderNoqualityListPage(Map<String, Object> paraMap);
	
	/**
	 * 
	 * <b>Description:</b>查询销售订单合同回传不合格总数
	 * @param paraMap
	 * @return Integer
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月12日 下午1:25:28
	 */
	Integer getContractReturnOrderNoqualityListCount(Map<String, Object> paraMap);
	/* *
	 * 功能描述: 修改订单税率保存
	 * @param: [orderId, invoiceApplyId, invoiceType]
	 * @return: int
	 * @auther: duke.li
	 * @date: 2019/3/29 14:47
	 */
	int saveOrderRatioEdit(@Param(value="orderId")Integer orderId, @Param(value="invoiceType")Integer invoiceType);
    /**
    * @Description: 根据参数查询耗材订单数据
    * @Param: [saleorder]
    * @return: java.util.List<com.vedeng.order.model.Saleorder>
    * @Author: scott.zhu
    * @Date: 2019/5/14
    */
    List<Saleorder> getHcOrderList(Saleorder saleorder);
	/**
	 * <b>Description:</b><br>根据字段更新销售订单
	 *
	 *
	 * @param :[record]
	 * @return :int
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2019/5/21 7:58 PM
	 */
	int updateByPrimaryKeySelective(Saleorder record);

	/**
	* @Title: getSaleOrderlistByStatus
	* @Description: TODO(获取客户订单状态带确认,非审核中的订单)
	* @param @param traderId
	* @param @return    参数
	* @return List<Saleorder>    返回类型
	* @author strange
	* @throws
	* @date 2019年7月24日
	*/
	List<Saleorder> getSaleOrderlistByStatusTraderId(Integer traderId);

	/**
	* @Title: getSaleOrderById
	* @Description: TODO(订单id获取订单)
	* @param @param saleorderId
	* @param @return    参数
	* @return Saleorder    返回类型
	* @author strange
	* @throws
	* @date 2019年7月24日
	*/
	Saleorder getSaleOrderById(Integer saleorderId);

	List<Saleorder> getSaleOrersByIdList(List<Integer> ids);

	/**
	* @Title: getSaleorderBySaleorderNo
	* @Description: TODO(订单号获取订单信息)
	* @param @param saleorder
	* @param @return    参数
	* @return Saleorder    返回类型
	* @author strange
	* @throws
	* @date 2019年7月31日
	*/
	Saleorder getSaleorderBySaleorderNo(Saleorder saleorder);

	/**
	* @Title: getSaleorderListByStatus
	* @Description: TODO(获取待用户确认状态BD订单)
	* @param @param status
	* @param @param orderType
	* @param @return    参数
	* @return List<Saleorder>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月1日
	*/
	List<Saleorder> getSaleorderListByStatus(@Param(value="status")Integer status,@Param(value="orderType") Integer orderType);

	/**
	* @Title: getSaleOrderlistByStatusMobile
	* @Description: TODO(获取客户订单状态带确认,非审核中的订单)
	* @param @param createMobile
	* @param @return    参数
	* @return List<Saleorder>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月8日
	*/
	List<Saleorder> getSaleOrderlistByStatusMobile(@Param(value="createMobile")String createMobile);

	/**
	* @Description: 获取订单编号
	* @Param: [saleorder]
	* @return: com.vedeng.order.model.Saleorder
	* @Author: addis
	* @Date: 2019/8/13
	*/
	List<Saleorder> selectSaleorderNo(Saleorder saleorder);

	List<GoodsData> getGoodsOccupyNumList(@Param("goodsIds") List<Integer> goodsIds);

	Integer getGoodsOccupyNum(@Param("goodsId") Integer goodsId);
	
	/** 
	* @Description: 查询订单里是否有该报价存在 
	* @Param: [quoteorderId] 
	* @return: java.lang.Integer 
	* @Author: addis
	* @Date: 2019/8/30 
	*/ 
	Integer isExistQuoteorderId(Integer quoteorderId);

	/**
	 * <b>Description:</b><br>
	 * 剩余账期未还金额
	 * add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 .
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月16日 上午10:27:20
	 */
	BigDecimal getSaleorderLackAccountPeriodAmount(@Param("saleorderId") Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 获取订单已收款账期金额
	 * add by Tomcat.Hui 2019/9/5 10:44 .Desc: VDERP-1053 .
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月23日 上午11:06:47
	 */
	BigDecimal getPeriodAmount(@Param("saleorderId") Integer saleorderId);

	/**
	* @Description: TODO(订单号获取订单基本信息)
	* @param @param orderNoList
	* @param @return
	* @return List<Saleorder>
	* @author strange
	* @throws
	* @date 2019年10月12日
	*/
	List<Saleorder> getSaleorderBySaleorderNoList(ArrayList<String> orderNoList);

	/**
	* @Description: TODO(更改订单收货状态)
	* @param @param saleorder
	* @param @return
	* @return Integer
	* @author strange
	* @throws
	* @date 2019年10月14日
	*/
	Integer updateDeliveryStatusBySaleorderNo(Saleorder saleorder);
	/**
	*非备货非关闭订单id
	* @Author:strange
	* @Date:19:34 2019-11-11
	*/
	Integer getSaleorderidByStatus();

	/**
	* @Description: 根据订单id查询用户id
	* @Param:
	* @return:
	* @Author: addis
	* @Date: 2019/11/8
	*/
	Saleorder getWebAccountId(Integer saleorderId);
	/**
	*分页查询订单id
	* @Author:strange
	* @Date:01:20 2019-11-28
	*/
	List<Integer> getSaleorderidByStatusLimit(Integer orgId);

	void updateSaleorderStatusById(@Param("idList") List<Integer> idList, @Param("modTime") Long modTime, @Param("userId") Integer userId);

	/**
	 * 根据订单号查询Id
	 * @param saleorderNo
	 * @Author:Rock
	 * @return
	 */
    Saleorder getSaleOrderId(String saleorderNo);


	/**
	*订单商品id获取订单信息
	* @Author:strange
	* @Date:17:36 2019-12-04
	*/
    Saleorder getSaleorderBySaleorderGoodsId(Integer saleorderGoodsId);
	/**
	*分页获取活动订单id
	* @Author:strange
	* @Date:15:43 2019-12-05
	*/
	List<Integer> getActionOrderLimit(Integer orgId);
	/**
	 *根据订单号取消订单（用于耗材商城订单状态同步）
	 * <b>Description:</b>
	 * @param saleorder
	 * @return Integer
	 * @Note
	 * <b>Author：</b> barry.xu
	 * <b>Date:</b> 2018年11月27日 上午10:05:32
	 */
    int updateOrderStatusByOrderNo(Saleorder saleorder);
	/**
	*获取所有订单活动id
	* @Author:strange
	* @Date:13:08 2019-12-23
	*/
	List<Integer> getAllActionId();
	//第一次物流增加评论
    int updateLogisticsComments(@Param("saleorderId")Integer saleorderId, @Param("logisticsComments")String s);

    List<Saleorder> getSaleorderByExpressDetailId(@Param("list") List<Integer> list);

    Saleorder getSaleorderByOrderListId(Integer orderListId);

	List<SaleorderGoods> getSaleorderGoodsByOrderListId(List<Integer> orderListId);

	/**
	 *
	 * @param ownerUserId
	 * @return
	 */
    User getUserDetailInfoByUserId(Integer ownerUserId);

	User getUserInfoByTraderId(Integer traderId);
	/**
	*宝石花出库单列表
	* @Author:strange
	* @Date:16:58 2020-02-20
	*/
    List<Saleorder> getFlowerPrintOutListPage(Map<String, Object> map);

    int cleanSaleOrder();

	int cleanAfterSale();
	/**
	*更新updateDataTime
	* @Author:strange
	* @Date:11:37 2020-04-06
	*/
    Integer updateDataTimeByOrderId(Integer orderId);
	/**
	 *更新updateDataTime
	 * @Author:strange
	 * @Date:11:37 2020-04-06
	 */
	Integer updateDataTimeByDetailId(Integer orderDetailId);

    int clearBussiness();
	/*校验锁的状态*/
	void updateLockedStatus(Integer saleorderId);
}

