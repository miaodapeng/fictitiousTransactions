package com.vedeng.order.dao;

import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.BuyorderVo;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Named("buyorderMapper")
public interface BuyorderMapper {
    int deleteByPrimaryKey(Integer buyorderId);

    int insert(Buyorder record);

    int insertSelective(Buyorder record);

    Buyorder selectByPrimaryKey(Integer buyorderId);

    int updateByPrimaryKeySelective(Buyorder record);

    int updateByPrimaryKey(Buyorder record);
    
    /**
     * <b>Description:</b><br> 查询分页
     * @param map
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月11日 下午1:20:35
     */
    List<BuyorderVo> getbuyordervolistpage(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 查询分页的总记录数
     * @param map
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月11日 下午1:20:35
     */
    Integer getbuyordervolistpagecount(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 查询采购订单的基本信息
     * @param buyorderId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月19日 上午10:58:46
     */
    BuyorderVo getBuyorderVoById(Integer buyorderId);
    /**
     * <b>Description:</b><br> 获取产品总数以及总额
     * @param buyorderId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月19日 上午10:58:46
     */
    BuyorderVo getBuyorderVoNumById(Integer buyorderId);
    /**
     * <b>Description:</b><br> 查询采购订单的基本信息
     * @param buyorderNo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月19日 上午10:58:46
     */
    BuyorderVo getBuyorderVoByBuyorderNo(String buyorderNo);
    
    /**
     * <b>Description:</b><br> 获取客户生效的订单
     * @param traderId
     * @param limit
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 上午10:55:07
     */
    List<Buyorder> getEffectOrders(@Param("traderId") Integer traderId, @Param("limit") Integer limit);

    /**
     * <b>Description:</b><br> 获取未完成的订单
     * @param buyorder
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月13日 下午4:01:38
     */
    List<BuyorderVo> getUnCompleteBuyorder(Buyorder buyorder);

    /**
     * <b>Description:</b><br> 获取采购入库的列表
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年8月3日 下午5:02:08
     */
    List<BuyorderVo> getbuyorderinfolistpage(Map<String, Object> map);

    /**
     * <b>Description:</b><br> 获取采购订单列表
     * @param bo
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年8月17日 下午1:33:11
     */
	List<BuyorderVo> getInvoiceBuyorderList(BuyorderVo bo);

	/**
	 * <b>Description:</b><br> 产品在途数量（采购订单已发货但未入库的数量，不包含直发订单范围）
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月4日 下午5:05:43
	 */
	Integer getGoodsOnWayNum(@Param("goodsId") Integer goodsId);


	/**
	 *
	 * <b>Description:</b><br> 根据采购订单详情ID获取采购订单的订单产品总数和总到货数量
	 * @param buyorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年9月6日 上午11:41:06
	 */
	BuyorderVo selectSumNumByBuyorderGoodsId(Integer buyorderGoodsId);

	/**
	 * <b>Description:</b><br> 根据公司id查询采购订单中供应商id集合
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年9月22日 下午4:17:00
	 */
	List<Integer> getBuyOrderTraderIdList(Integer companyId);
    /**
     *
     * <b>Description:</b><br> 在库商品列表
     * @param goodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月9日 上午11:26:17
     */
	List<BuyorderVo> getBuyorderVoList(Integer goodsId);

	/**
	 * <b>Description:</b><br> 已付款金额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月17日 上午11:01:04
	 */
	BigDecimal getPaymentAmount(@Param("buyorderId") Integer buyorderId);

	/**
	 * <b>Description:</b><br> 批量查询已付款金额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月17日 上午11:01:04
	 */
	List<BuyorderVo> batchPaymentAmountList(@Param("buyorderVos") List<BuyorderVo> buyorderVos);

	/**
	 * <b>Description:</b><br> 应付账期额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月17日 上午11:01:08
	 */
	BigDecimal getLackAccountPeriodAmount(@Param("buyorderId") Integer buyorderId);

	/**
	 * <b>Description:</b><br> 已收票总额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月17日 上午11:01:15
	 */
	BigDecimal getInvoiceAmount(@Param("buyorderId") Integer buyorderId);

	/**
	 * <b>Description:</b><br> 订单金额（总额-退款额）
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月24日 上午9:16:22
	 */
	BigDecimal getRealAmount(@Param("buyorderId") Integer buyorderId);

	/**
	 * <b>Description:</b><br> 订单账期支付金额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月24日 上午9:16:37
	 */
	BigDecimal getPeriodAmount(@Param("buyorderId") Integer buyorderId);

	/**
	 * <b>Description:</b><br> 批量查询订单账期支付金额
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月24日 上午9:16:37
	 */
	List<BuyorderVo> batchPeriodAmountList(@Param("buyorderVos") List<BuyorderVo> buyorderVos);

	/**
	 * <b>Description:</b><br> 获取采购产品已收票数量
	 * @param buyorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月15日 下午6:03:03
	 */
	BigDecimal getHaveInvoiceNums(Integer buyorderGoodsId);
	/**
	 *
	 * <b>Description:</b><br> 获取采购产品已收票数量(批量)
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年7月2日 下午2:16:19
	 */
	List<BuyorderGoodsVo> getHaveInvoiceNumsByList(@Param("bgvList") List<BuyorderGoodsVo> bgvList);

	/**
	 * <b>Description:</b><br> 获取采购产品已收票数量
	 * @param buyorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月15日 下午6:03:03
	 */
	List<BuyorderGoodsVo> getHaveInvoiceNumsByParam(@Param("list") List<BuyorderGoodsVo> list);

	/**
	 * <b>Description:</b><br> getGoodsOccupyAmount
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月23日 下午6:20:20
	 */
	BigDecimal getGoodsOccupyAmount(@Param("goodsId") Integer goodsId);

	/**
	 * <b>Description:</b><br> 平均到货时间信息：最近的生效采购订单生效时间往前三个月，已付款生效采购订单中，商品入库时间-商品所在采购订单生效时间
	 * @param goodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月24日 上午9:37:32
	 */
	List<BuyorderVo> getAverageArrivalList(@Param("goodsId") Integer goodsId);

	/**
	 * <b>Description:</b><br> 批量查询平均到货时间信息
	 * @param goodsIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 下午5:41:21
	 */
	List<BuyorderVo> getAverageArrivalListByGoodsIds(@Param("goodsIds") List<Integer> goodsIds);
    /**
     *
     * <b>Description:</b><br> 打印采购单
     * @param buyorder
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年12月14日 上午10:57:56
     */
	BuyorderVo getBuyOrderPrintInfo(Buyorder buyorder);

	/**
	 * <b>Description:</b><br> 获取待同步采购订单数据
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月26日 下午2:58:16
	 */
	List<Buyorder> getWaitSyncBuyorderList();

	/**
	 * <b>Description:</b><br>
	 * @param buyorderIds
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月18日 上午9:47:31
	 */
	List<Buyorder> getBuyorderByBuyorderIdList(@Param("buyorderIds") List<Integer> buyorderIds);
	/**
	 * <b>Description:</b><br> 根据销售订单产品ID集合获取采购归属人id
	 * @param saleorderGoodIds
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年1月18日 上午9:47:31
	 */
	List<Integer> getBuyorderUserBySaleorderGoodsIds(@Param("saleorderGoodIds") List<Integer> saleorderGoodIds);

	/**
	 * <b>Description:</b><br> 订单预付款金额（预付款总额-退款额）
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月1日 下午8:18:47
	 */
	BigDecimal getRealPreAmount(Integer buyorderId);

	Buyorder getByuorderByBuyorderGoodsId(@Param("buyorderGoodsId") Integer buyorderGoodsId);

	BigDecimal getPeriodOrderAmount(@Param("traderId") Integer traderId);

	/**
	 * <b>Description:</b><br> 根据销售订单详情id获取对应的销售订单
	 * @param saleorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月11日 下午1:39:13
	 */
	List<Buyorder> getBuyorderListBySaleorderGoodsId(@Param("saleorderGoodsId") Integer saleorderGoodsId);

	/**
	 * <b>Description:</b><br> 根据销售订单产品id获取对应的生效采购订单的数量
	 * @param saleorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月11日 下午1:39:13
	 */
	Integer getBuyorderGoodsSumBySaleorderGoodsId(@Param("saleorderGoodsId") Integer saleorderGoodsId);

	/**
	 * <b>Description:</b><br> 未还帐期大于0的采购订单
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月22日 下午2:55:23
	 */
	List<Integer> getLackAccountPeriodAmountBuyorderIds(@Param("companyId") Integer companyId);

	/**
	 * <b>Description:</b><br> 查询售后产品关联的采购订单
	 * @param saleorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年2月11日 下午1:39:13
	 */
	List<Buyorder> getBuyorderListByAftersale(@Param("afterSalesGoodsVos") List<AfterSalesGoodsVo> afterSalesGoodsVos);
    /**
     *
     * <b>Description:</b><br> 查询采购单已入库的商品总数
     * @param buyorderGoodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年4月17日 下午5:43:40
     */
	BuyorderVo getIsInNum(Integer buyorderGoodsId);
    /**
     *
     * <b>Description:</b><br> 将订单下的特殊商品都改为已到货
     * @param br
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年4月17日 下午6:01:35
     */
	int updateTsGoodsDh(Buyorder br);

	BigDecimal getPaymentAndPeriodAmount(@Param("buyorderId") Integer buyorderId);
    /**
     *
     * <b>Description:</b><br> 查询采购单信息
     * @param buyorderGoodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月30日 下午7:13:14
     */
	BuyorderVo selectBuyorderInfo(Integer buyorderGoodsId);

	/**
	 * <b>Description:</b><br> 已付款总额（总计）
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年5月30日 上午9:43:08
	 */
	BigDecimal getPaymentTotalAmount(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 收票总额（总计）
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年5月30日 上午9:44:01
	 */
	BigDecimal getInvoiceTotalAmount(Map<String, Object> map);

	/**
	 * <b>Description:</b><br> 获取采购申请修改的详情
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月12日 上午11:44:09
	 */
	BuyorderVo getApplyBuyorderDetail(Buyorder buyorder);

	/**
	 * <b>Description:</b><br> 根据采购订单id查询关联销售订单的数量
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月13日 下午5:56:23
	 */
	List<Integer> getSaleorderIdListByBuyorderId(@Param("buyorderId") Integer buyorderId);

	/**
	 * <b>Description:</b><br> 根据采购订单id查询关联销售订单商品的直发普发状态
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月13日 下午5:56:23
	 */
	List<Integer> getSaleorderGoodsDeliveryDirectByBuyorderId(@Param("buyorderId") Integer buyorderId);
    /**
    /**
     *
     * <b>Description:</b><br> 销售单出库对应的采购单
     * @param saleorderGoodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月6日 上午10:42:42
     */
	List<Buyorder> getBuyorderOutListBySaleorderGoodsId(Integer saleorderGoodsId);

	BigDecimal getPeriodOrderAmountNew(@Param("traderId") Integer traderId, @Param("companyId") Integer companyId);
	/**
	 * <b>Description:</b><br> 采购单申请修改生效，修改采购单信息
	 * @param buyorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月18日 下午5:12:49
	 */
	int updateBuyorderApplyValid(Buyorder buyorder);

	/**
	 * <b>Description:</b><br> 获取订单支付的账期金额（账期支付-账期还款-退还账期）
	 * @param id
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年8月1日 下午1:05:49
	 */
	BuyorderVo getBuyorderPeriodAmount(@Param("buyorderId") Integer buyorderId);

	Integer updateDataTimeByOrderId(Integer orderId);

	Integer updateDataTimeByDetailId(Integer orderDetailId);
	/**
	*获取采购单关联的销售详情id
	* @Author:strange
	* @Date:10:31 2020-04-10
	*/
	List<Integer> getSaleorderGooodsIdByBuyorderId(Integer orderId);
}