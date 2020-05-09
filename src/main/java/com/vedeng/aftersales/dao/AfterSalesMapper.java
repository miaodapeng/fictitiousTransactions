package com.vedeng.aftersales.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.vedeng.order.model.Saleorder;
import org.apache.ibatis.annotations.Param;

import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesCost;
import com.vedeng.aftersales.model.AfterSalesDetail;
import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.CostType;
import com.vedeng.aftersales.model.vo.AfterSalesCostVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.order.model.vo.SaleorderVo;

@Named("afterSalesMapper")
public interface AfterSalesMapper {
    int deleteByPrimaryKey(Integer afterSalesId);

    int insert(AfterSales record);

    int insertSelective(AfterSales record);

    AfterSales selectByPrimaryKey(Integer afterSalesId);

    int updateByPrimaryKeySelective(AfterSales record);

    int updateByPrimaryKey(AfterSales record);

    Saleorder getSaleOrderByAfterSaleorderId(Integer afterSaleorderId);

    AfterSales getAfterSalesById(Integer afterSaleorderId);

    List<AfterSales> getAfterSalesCheckedByAfterSaleorderId(List<Integer> saleorderIdList);

    /**
     * <b>Description:</b><br> 查询售后订单的分页信息
     * @param map
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月9日 下午1:10:42
     */
    List<AfterSalesVo> getAfterSalesVoListPage(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 查询采购售后列表
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月9日 下午5:37:25
	 */
	List<AfterSalesVo> getBuyorderAfterSalesListPage(Map<String, Object> map);
	
    /**
     * <b>Description:</b><br> 根据订单id查询对应的售后订单列表
     * @param afterSalesVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月9日 下午1:10:42
     */
    List<AfterSalesVo> getAfterSalesVoListByOrderId(AfterSalesVo afterSalesVo);
    
    /**
     * <b>Description:</b><br> 根据订单id查询对应的售后订单列表主键集合
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月9日 下午1:10:42
     */
    List<Integer> getAfterSalesIdListByOrderId(AfterSales afterSales);
    
    /**
     * <b>Description:</b><br> 查看售后订单详情
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午3:40:24
     */
    AfterSalesVo viewAfterSalesDetail(AfterSales afterSales);
    
    /**
     * <b>Description:</b><br> 查看销售订单的售后订单详情
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午3:40:24
     */
    AfterSalesVo viewAfterSalesDetailSaleorder(AfterSales afterSales);
    /**
     * <b>Description:</b><br> 查看采购订单的售后订单详情
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午3:40:24
     */
    AfterSalesVo viewAfterSalesDetailBuyorder(AfterSales afterSales);
    /**
     * <b>Description:</b><br> 查看第三方的售后订单详情
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午3:40:24
     */
    AfterSalesVo viewAfterSalesDetailThird(AfterSales afterSales);
    /**
     * 
     * <b>Description:</b><br> 物流仓储售后列表
     * @param map
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月17日 下午3:31:27
     */
	List<AfterSalesVo> getStorageAftersalesListPage(Map<String, Object> map);
    /**
     * 
     * <b>Description:</b><br> 根据id查询售后订单
     * @param afterSales
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月18日 下午4:15:22
     */
	AfterSalesVo getAfterSalesVoListById(AfterSales afterSales);
	
	/**
	 * 
	 * <b>Description:通过orderId来获取交易信息即采购信息和销售信息</b><br> 
	 * @param afterSales orderId 必填 [businessType = 1销售,businessType = 2 采购 ,其他非法]
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年5月4日 上午9:48:08 </b>
	 */
	AfterSalesVo getTranderInfoById(AfterSales afterSales);
	
    /**
     * 
     * <b>Description:</b><br> 根据售后产品id查询售后订单
     * @param afterSalesGoodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月20日 下午3:54:03
     */
	AfterSalesVo selectSumNumByAfterGoodsId(Integer afterSalesGoodsId);

	/**
	 * <b>Description:</b><br> 获取销售售后订单收款（客户支付服务费，手续费）/采购售后订单付款金额（支付给供应商服务费，手续费）
	 * @param afterSalesId
	 * @param orderType 1销售订单售后 2采购订单售后
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月2日 下午3:06:45
	 */
	BigDecimal getPayAmount(@Param("afterSalesId")Integer afterSalesId,@Param("orderType")Integer orderType);
    /**
     * 
     * <b>Description:</b><br> 发货数量
     * @param afterSalesGoodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月6日 下午3:57:57
     */
	AfterSalesVo selectSumNumByAfterSalesId(Integer afterSalesGoodsId);

	/**
	 * <b>Description:</b><br> 获取销售售后订单退款（退款给客户）/采购售后订单退款金额（供应商退款给我们）
	 * @param afterSalesId
	 * @param orderType 1销售订单售后 2采购订单售后
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月3日 上午9:37:53
	 */
	BigDecimal getRefundAmount(@Param("afterSalesId")Integer afterSalesId, @Param("orderType")Integer orderType);

	/**
	 * <b>Description:</b><br> 销售订单付款金额（工程师安调费用）
	 * @param afterSalesId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月3日 上午9:37:56
	 */
	BigDecimal getSaleorderAfterSalesPayAmount(@Param("afterSalesId")Integer afterSalesId);

	/**
	 * <b>Description:</b><br> 获取销售售后订单账期退款金额（退款给客户）/采购售后订单账期退款金额（供应商退款给我们）
	 * @param afterSalesId
	 * @param orderType
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月14日 下午5:17:01
	 */
	BigDecimal getPeriodAmount(@Param("afterSalesId")Integer afterSalesId, @Param("orderType")Integer orderType);

	/**
	 * <b>Description:</b><br> 获取销售售后订单退款（退款给客户）/采购售后订单退款金额（供应商退款给我们）
	 * @param afterSalesId
	 * @param orderType
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2017年12月23日 上午8:35:58
	 */
	BigDecimal getRefundTraderAmount(@Param("afterSalesId")Integer afterSalesId, @Param("orderType")Integer orderType);

	/**
	 * <b>Description:</b><br> 获取待同步售后数据
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月25日 下午1:05:34
	 */
	List<AfterSales> waitSyncAfterSalesList();
	
	/**
	 * <b>Description:</b><br> 获取当日售后订单数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getTodayAfterSalesOrderSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取当月售后订单数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getThisMonthAfterSalesOrderSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取当年售后订单数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getThisYearAfterSalesOrderSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取今日销售订单售后退货数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getTodaySaleorderAfterSalesTHSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取今日销售订单售后换货数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getTodaySaleorderAfterSalesHHSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取本月销售订单售后数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getThisMonthSaleorderAfterSalesSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取本年销售订单售后数量
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午10:48:51
	 */
	Integer getThisYearSaleorderAfterSalesSum(@Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取售后订单的数量
	 * @param afterSalesVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 下午1:18:10
	 */
	List<AfterSalesVo> getAfterSalesOrderNumByParam(AfterSalesVo afterSalesVo);

	/**
	 * <b>Description:</b><br> 获取售后单供应商退还给我们到账户的钱（非账期）
	 * @param afterSalesId
	 * @param orderType
	 * @return
	 * @Note
	 * <b>Author:</b> Administrator
	 * <br><b>Date:</b> 2018年1月17日 下午1:32:02
	 */
	BigDecimal getRefundVedengAmount(@Param("afterSalesId")Integer afterSalesId, @Param("orderType")Integer orderType);
	
	/**
	 * <b>Description:</b><br> 查询售后列表
	 * @param aftersalesIdList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月18日 上午9:54:03
	 */
	List<AfterSales> getAfterSalesByAfterSalesIdList(@Param("aftersalesIdList")List<Integer> aftersalesIdList);
	
	/**
	 * <b>Description:</b><br> 查询销售订单的所有已完结的售后退货金额
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年2月3日 下午3:37:53
	 */
	BigDecimal getRefundAmountBySaleorderId(@Param("saleorderId")Integer saleorderId);

	AfterSales getAfterSalesByAfterSalesGoodsId(@Param("afterSalesGoodsId") Integer afterSalesGoodsId);
	
	/**
	 * <b>Description:</b><br> 查询当前销售订单是否有退货换货退款
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年2月27日 上午9:10:19
	 */
	List<AfterSales> getTHKAfterSalesList(@Param("saleorderId")Integer saleorderId);
	
	/**
	 * <b>Description:</b><br> 查询当前销售订单所有已完结退货退款
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年2月27日 上午9:10:19
	 */
	List<AfterSales> getTKAfterSalesList(@Param("saleorderId")Integer saleorderId);
	
	/**
	 * <b>Description:</b><br> 查询当前销售订单所有已完结退货退款金额
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年4月11日 上午11:22:43
	 */
	BigDecimal getSumTKAfterSalesBySaleorderid(@Param("saleorderId")Integer saleorderId);
	
	/**
	 * <b>Description:</b><br> 查询当前采购订单所有已完结退货退款金额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年4月11日 上午11:22:43
	 */
	BigDecimal getSumTKAfterSalesByBuyorderid(@Param("buyorderId")Integer buyorderId);
	
	/**
	 * <b>Description:</b><br> 查询当前采购订单所有已完结退货售后
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年2月27日 上午9:10:19
	 */
	List<AfterSales> getTAfterSalesList(@Param("buyorderId")Integer buyorderId);
	
	/**
	 * <b>Description:</b><br> 获取销售第三方售后订单财务信息
	 * @param aftersaleId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月2日 下午4:23:42
	 */
	SaleorderVo getAftersaleFinanceInfo(@Param("aftersaleId")Integer aftersaleId);

	/**
	 * <b>Description:</b><br> 验证开票申请是否通过
	 * @param afterSalesId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年4月17日 下午4:14:40
	 */
	Integer getAfterOpenInvoiceApplyStatus(@Param("afterSalesId")Integer afterSalesId ,@Param("companyId")Integer companyId);

	
	/**
	 * <b>Description:</b><br> 获取售后服务费与票信息
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年5月21日 下午5:24:40
	 */
	AfterSalesVo getFinanceDetail(@Param("traderId")Integer traderId, @Param("traderType")Integer traderType);

	
	/**
	 * <b>Description:</b><br> ordderType:1-销售退余额；2-销售退客户；3-采购退余额；4-采购退客户
	 * @param afterSalesId
	 * @param orderType
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年5月17日 下午2:15:25
	 */
	BigDecimal getHaveRefundAmount(@Param("afterSalesId")Integer afterSalesId ,@Param("orderType")Integer orderType);

	/**
	 * <b>Description:</b><br> 查询销售订单退还余额的金额
	 * @param saleorderId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月4日 下午1:13:34
	 */
	BigDecimal getRefundBalanceAmountBySaleorderId(@Param("saleorderId") Integer saleorderId);

	/**
	 * <b>Description:</b>根据Id查找对应的费用类型列表<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月23日 19:33
	 */
	List<CostType> getCostTypeById(@Param("costType") Integer costType);
	
	/**
	 * 
	 * <b>Description:</b>根据订单Id查找售后费用类型列表<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:30
	 */
	List<AfterSalesCostVo> selectAfterSalesCostListById(@Param("afterSalesCost") AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>新增订单的售后类型费用<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:31
	 */
	int insertAfterSalesCost(@Param("afterSalesCost") AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>删除订单的售后费用类型记录<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:33
	 */
	int deleteAfterSalesCostById(@Param("afterSalesCost") AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>更改订单的售后费用类型记录<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月24日 19:36
	 */
	int updateAfterSalesCostById(@Param("afterSalesCost") AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>根据costId查找对应费用类型记录<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年07月25日 15:52
	 */
	AfterSalesCostVo selectAfterSalesCostBycostId(@Param("afterSalesCost") AfterSalesCost afterSalesCost);
	
	/**
	 * 
	 * <b>Description:</b>根据售后单Id修改售后单的收票状态<br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年09月04日 15:24
	 */
	int updateAfterSalesReceiveInvoiceStatus(@Param("afterSalesDetail")AfterSalesDetail afterSalesDetail);
	
	/**
	 * 根据销售订单id查询退款列表
	 * @return
	 */
	List<AfterSalesVo> getReturnBackMoneyByOrderId(@Param("afterSales")AfterSales afterSales);

	/** 
	 * <b>Description:</b>根据售后单号查询售后主表信息
	 * @param afterSalesNo
	 * @return AfterSales
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2018年12月7日 下午2:06:55
	 */
	AfterSales getAfterSalesByNo(@Param("afterSalesNo")String afterSalesNo);

	/**
	 * <b>Description:</b><br> 获取售后商品对应的原单价
	 * @param afterSalesId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2019年2月26日 下午17:14:40
	 */
    List<AfterSalesGoods> getOrderGoodsPriceByAfterId(@Param("subjectType")Integer subjectType, @Param("afterSalesId")Integer afterSalesId);

	/**
	* @Title: searchAfterOrderListPage
	* @Description: TODO(搜索销售售后单和商品信息)
	* @param @param map
	* @return List<AfterSalesVo>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月22日
	*/
	List<AfterSalesVo> searchAfterOrderListPage(Map<String, Object> map);

	/**
	 *获取销售单下非关闭售后单
	 * @param afterSales orderId为销售单id
	 * @Author:strange
	 * @Date:20:41 2019-12-30
	 */
    List<AfterSales> getAfterSaleListById(AfterSales afterSales);
	/**
	*更新
	* @Author:strange
	* @Date:15:21 2020-04-10
	*/
    Integer updateDataTimeByOrderId(Integer afterSaleOrderId);

	Integer updateDataTimeByDetailId(Integer orderDetailId);
	/**
	*获取售后单类型
	* @Author:strange
	* @Date:15:44 2020-04-10
	*/
	AfterSales getAfterSalesTypeByAfterSalesGoodsId(Integer afterSaleOrderDetailId);
}