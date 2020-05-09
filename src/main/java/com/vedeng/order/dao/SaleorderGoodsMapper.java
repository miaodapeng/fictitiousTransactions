package com.vedeng.order.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.goods.model.CoreSpuGenerate;
import com.vedeng.order.model.SaleorderCoupon;
import com.vedeng.authorization.model.User;
import com.vedeng.order.model.Spu;
import org.apache.ibatis.annotations.Param;

import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.order.model.RBuyorderSaleorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderVo;

/**
 * <b>Description:</b><br>
 * 订单产品mapper
 * 
 * @author leo.yang
 * @Note <b>ProjectName:</b> dbcenter <br>
 *       <b>PackageName:</b> com.vedeng.dao.order <br>
 *       <b>ClassName:</b> SaleorderGoodsMapper <br>
 *       <b>Date:</b> 2017年7月5日 下午4:13:01
 */
public interface SaleorderGoodsMapper {
	int deleteByPrimaryKey(Integer saleorderGoodsId);

	int insert(SaleorderGoods record);

	int insertSelective(SaleorderGoods record);

	SaleorderGoods selectByPrimaryKey(Integer saleorderGoodsId);

	int updateByPrimaryKeySelective(SaleorderGoods record);

	int updateByPrimaryKey(SaleorderGoods record);

	/**
	 * <b>Description:</b><br>
	 * 获取订单产品列表（根据订单ID）
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月5日 下午4:08:45
	 */
	List<SaleorderGoods> getSaleorderGoodsById(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 获取订单产品信息（根据订单产品主键ID）
	 * 
	 * @param saleorderGoodsId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月6日 下午3:18:09
	 */
	SaleorderGoods getSaleorderGoodsInfoById(Integer saleorderGoodsId);


	/**
	 * 获取订单产品ID列表 <b>Description:</b><br>
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月25日 上午11:38:38
	 */
	List<SaleorderGoods> getsaleordergoodsbyidnew(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 查询销售订单产品列表
	 * 
	 * @param saleorderGoodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月13日 下午5:14:25
	 */
	List<SaleorderGoodsVo> getSaleorderGoodsVoListByGoodsVo(GoodsVo goodsVo);

	/**
	 * <b>Description:</b><br>
	 * 查询已忽略订单列表分页
	 * 
	 * @param map
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年8月1日 下午3:35:47
	 */
	List<SaleorderGoodsVo> getIgnoreSaleorderListPage(Map<String, Object> map);

	/**
	 * <b>Description:</b><br>
	 * 验证订单产品是否重复
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月26日 下午6:16:39
	 */
	int vailSaleorderGoods(SaleorderGoods saleorderGoods);

	/**
	 * <b>Description:</b><br>
	 * 修改订单主表总价格
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月26日 下午6:26:05
	 */
	int updateSaleorderTotal(SaleorderGoods saleorderGoods);

	/**
	 * <b>Description:</b><br>
	 * 根据销售单号查询销售产品的id集合
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月31日 上午11:47:23
	 */
	List<Integer> getSaleOrderGoodsIdList(@Param("saleorderNo") String saleorderNo);

	/**
	 * <b>Description:</b><br>
	 * 跟销售产品di的集合查询销售订单产品集合
	 * 
	 * @param saleorderGoodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月13日 下午5:14:25
	 */
	List<SaleorderGoodsVo> getSaleorderGoodsVosList(@Param("saleorderGoodsIds") List<Integer> saleorderGoodsIds);

	/**
	 * <b>Description:</b><br>
	 * 跟销售产品di的集合查询销售订单产品集合
	 * 
	 * @param saleorderGoodsVo
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月13日 下午5:14:25
	 */
	List<SaleorderGoodsVo> getSaleorderGoodsVosListByIds(
			@Param("lists") List<RBuyorderSaleorder> rBuyorderSaleorderList);

	/**
	 * 根据产品列表对象查询销售订单产品集合 <b>Description:</b><br>
	 * 
	 * @param goodsVoList
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年7月3日 下午4:47:14
	 */
	List<SaleorderGoodsVo> getSaleorderGoodsVosListByLists(@Param("lists") List<BuyorderGoodsVo> goodsVoList);

	/**
	 * <b>Description:</b><br>
	 * 采购与销售关联表查询销售产品id集合
	 * 
	 * @param
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月13日 下午5:14:25
	 */
	List<Integer> getSaleorderIdList(@Param("rbsIds") List<RBuyorderSaleorder> rbsIds);

	/**
	 * <b>Description:</b><br>
	 * 验证销售订单商品是否含有直发
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月1日 上午9:25:52
	 */
	int vailSaleorderIsDirect(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 验证产品总金额是否等于原来的总金额
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月1日 下午2:51:11
	 */
	Integer vailSaleorderTotle(SaleorderGoods saleorderGoods);

	/**
	 * <b>Description:</b><br>
	 * 判断订单中产品报价或者货期是否为空
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年8月7日 下午5:45:31
	 */
	int vailSaleorderPriceDeliveryCycle(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 验证备货单批量保存产品信息中sku是否存在产品库中
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月14日 上午10:56:13
	 */
	List<String> vailSkuExistGoods(@Param("saleorderGoodsList") List<SaleorderGoods> saleorderGoodsList);

	/**
	 * <b>Description:</b><br>
	 * 验证备货单批量保存：产品信息是否已存在（已添加）
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月14日 上午11:43:01
	 */
	List<String> vailSkuExistSaleGoods(@Param("saleGoodsList") List<SaleorderGoods> saleGoodsList,
			@Param("saleorderId") Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 备货单产品信息批量保存
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月14日 下午1:29:26
	 */
	int batchSaveBhSaleorderGoods(@Param("saleGoodsList") List<SaleorderGoods> list);

	/**
	 * <b>Description:</b><br>
	 * 查询商品的总库存量
	 * 
	 * @param map
	 * @return <b>Author:</b> scott <br>
	 *         <b>Date:</b> 2017年8月14日 下午1:29:26
	 */
	Integer getTotalNum(Map<String, Object> map);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询仓库名称
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年9月12日 下午4:03:21
	 */
	List<SaleorderGoods> getWarehouseName(SaleorderGoods saleorderGoods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询快递关联的出库商品数
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年9月29日 下午4:03:09
	 */
	Integer getKdNum(SaleorderGoods saleorderGoods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 在售列表
	 * 
	 * @param goods
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月9日 上午8:42:13
	 */
	List<SaleorderGoodsVo> getSdList(Goods goods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 商品列表
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月12日 上午11:20:57
	 */
	List<SaleorderGoods> getSaleordergoodsInfo(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 根据SaleorderId查询销售订单关联的产品
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年10月10日 下午1:23:00
	 */
	List<SaleorderGoodsVo> getSaleorderGoodsVoListBySaleorderId(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 根据订单产品ID查询采购状态和入库状态
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年10月11日 上午9:29:39
	 */
	List<SaleorderGoods> getGoodsBuyWarehouseStatus(@Param(value = "saleGoodsIdList") List<SaleorderGoods> list);

	/**
	 * <b>Description:</b><br>
	 * 根据订单产品ID查询采购状态和入库状态
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年10月11日 上午9:29:39
	 */
	Integer getGoodsBuyWarehouseStatusBySaleorderGoodsId(@Param(value = "saleorderGoodsId") Integer saleorderGoodsId);

	Integer getGoodsBhWarehouseStatusBySaleorderGoodsId(@Param(value = "saleorderGoodsId") Integer saleorderGoodsId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据saleorderId获取关联的订单产品列表，排除忽略和删除的
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月12日 上午11:20:57
	 */
	List<SaleorderGoods> getSaleordergoodsList(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据saleorderId获取关联的订单产品列表，排除忽略和删除的
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年10月12日 上午11:20:57
	 */
	List<SaleorderGoodsVo> getSaleordergoodsVoList(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 订单产品客户收货数量
	 * 
	 * @param saleorderGoodsId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年10月17日 下午6:43:15
	 */
	Integer getArrivalNum(@Param("saleorderGoodsId") Integer saleorderGoodsId);

	/**
	 * <b>Description:</b><br>
	 * 订单中为删除产品数量
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年11月6日 下午3:39:12
	 */
	int vailSaleorderNotDelNum(Integer saleorderId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 获取商品的售后单价和采购单价
	 * 
	 * @param w
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年11月16日 下午3:56:38
	 */
	SaleorderGoods getPriceById(WarehouseGoodsOperateLog w);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 销售单打印合同
	 * 
	 * @param saleorder
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2017年12月12日 下午2:31:16
	 */
	SaleorderVo getPrintOrderInfo(Saleorder saleorder);

	/**
	 * <b>Description:</b><br>
	 * 获取待同步订单产品信息
	 * 
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年12月26日 下午1:46:07
	 */
	List<SaleorderGoods> getWaitSyncSaleorderGoodsList();

	/**
	 * <b>Description:</b><br>
	 * 客户交易记录
	 * 
	 * @param map
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年1月25日 下午1:58:10
	 */
	List<SaleorderGoodsVo> getBusinessListPage(Map<String, Object> map);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 销售业务出库可拣货数
	 * 
	 * @param map
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年1月26日 下午2:28:24
	 */
	int getXSTotalNum(Map<String, Object> map);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 采购业务出库可拣货数
	 * 
	 * @param map
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年1月26日 下午2:29:15
	 */
	int getCGTotalNum(Map<String, Object> map);

	/**
	 * <b>Description:</b><br>
	 * 修改销售订单产品的收货状态
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月15日 下午1:06:54
	 */
	int updateSaleorderGoodsArrivalStatus(SaleorderGoods saleorderGoods);

	/**
	 * <b>Description:</b><br>
	 * 获取同步订单产品
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年3月16日 上午11:19:32
	 */
	List<SaleorderGoods> getSaleorderGoodsForSync(@Param("saleorderId") Integer saleorderId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 将订单下的所有特殊商品都改为已收货
	 * 
	 * @param sas
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年4月16日 上午10:01:58
	 */
	int updateTsSaleorderGoods(SaleorderGoods sas);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 将订单下的所有特殊商品都改为已收货
	 * 
	 * @param sas
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年4月16日 上午10:01:58
	 */
	int updateTsSaleorderGoodsByParam(SaleorderGoods sas);

	List<SaleorderGoods> getGoodsBySku(@Param("saleOrderGoodslist") List<SaleorderGoods> saleOrderGoodslist,
			@Param("companyId") Integer companyId);

	/**
	 * <b>Description:</b><br>
	 * 保存销售单批量新增商品
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年4月20日 上午10:51:21
	 */
	int saveBatchAddSaleGoods(@Param("saleOrderGoodsList") List<SaleorderGoods> saleOrderGoodsList);

	List<String> getSaleorderGoodsSku(@Param("saleOrderId") Integer saleOrderId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 将订单下的所有特殊商品都改为已收货
	 * 
	 * @param sas
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年4月23日 下午3:19:35
	 */
	int updateShSaleorderGoods(SaleorderGoods sas);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询没出库的销售产品
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年4月26日 下午6:02:29
	 */
	List<SaleorderGoods> getSaleorderGoodNoOut(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 根据采购商品查询销售商品
	 * 
	 * @param buyorderGoodsId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年5月4日 上午8:52:30
	 */
	List<SaleorderGoods> getSaleorderGoodsVoByBuyorderGoodsId(Integer buyorderGoodsId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量更新发货状态
	 * 
	 * @param saleorderGoodsList
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年5月14日 下午7:56:48
	 */
	Integer updateByPrimaryKeySelectiveBatch(List<SaleorderGoods> saleorderGoodsList);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 批量更新销售订单产品参考成本
	 * 
	 * @param saleorderGoodsList
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年6月12日 下午5:18:15
	 */
	Integer updateReferenceCostPriceBatch(List<SaleorderGoods> saleorderGoodsList);

	int updateSaleTerminalInfo(SaleorderGoods saleorderGoods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据id查询销售产品详情
	 * 
	 * @param relatedId
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年5月29日 下午4:43:19
	 */
	SaleorderGoods selectsGoodsbyKey(Integer relatedId);

	/**
	 * <b>Description:</b><br>
	 * 查询销售商品排除特殊商品
	 * 
	 * @param saleorderId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年5月30日 下午1:08:54
	 */
	List<SaleorderGoods> getSaleorderGoodsListBySaleorderId(@Param("saleorderId") Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 销售订单商品编辑保存
	 * 
	 * @param saleorderGoods
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年8月3日 上午10:47:14
	 */
	int updateSaleGoodsSave(SaleorderGoods saleorderGoods);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询商品去除售后的总数和已出库数量
	 * 
	 * @param m
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年8月16日 上午11:42:02
	 */
	List<SaleorderGoods> getListNumbyMap(Map<String, Integer> m);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据销售订单详情ID集合获取对象
	 * 
	 * @param relatedIdList
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年8月1日 上午9:39:30
	 */
	List<SaleorderGoods> selectsGoodsbyList(List<Integer> relatedIdList);

	/**
	 * 锁定涉及到的sku
	 * 
	 * @param list
	 * @return
	 */
	int updateGoodsLockStatusBySku(List<AfterSalesGoodsVo> list);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 判断要出库的商品是否包含有已锁定
	 * 
	 * @param sd
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年9月6日 下午4:10:33
	 */
	List<SaleorderGoods> getSaleorderGoodLockList(SaleorderGoods sd);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 判断此条码对应的商品是否锁定
	 * 
	 * @param sg
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年9月7日 下午1:23:40
	 */
	SaleorderGoods getIsLockSaleorderGoods(SaleorderGoods sg);

	/**
	 * 解锁涉及的sku
	 * 
	 * @param list
	 * @return
	 */
	int updateGoodsNoLockStatusBySku(List<AfterSalesGoodsVo> list);

	/**
	 * 查询当前销售商品是否有锁定
	 * 
	 * @param saleorderGoodsIds
	 * @return
	 */
	int getLockedSaleoderGoodsNumBySaleorderGoodsIds(@Param("saleorderGoodsIds") List<Integer> saleorderGoodsIds);

//	/**
//	 * <b>Description:</b> 批量插入订单优惠券
//	 * 
//	 * @param couponList
//	 * @return int
//	 * @Note <b>Author：</b> duke.li <b>Date:</b> 2018年11月2日 下午5:24:32
//	 */
//	int insertOrderCouponBatch(@Param("couponList") List<SaleorderCoupon> couponList);

//	/**
//	 * <b>Description:</b> 插入订单运费
//	 * 
//	 * @param sf
//	 * @return int
//	 * @Note <b>Author：</b> duke.li <b>Date:</b> 2018年11月2日 下午5:34:44
//	 */
//	int insertOrderFreight(SaleorderFreight sf);

	/**
	 * 
	 * <b>Description:</b>除去售后的销售产品列表
	 * 
	 * @param saleorder
	 * @return List<SaleorderGoods>
	 * @Note <b>Author：</b> scott.zhu <b>Date:</b> 2018年10月19日 下午5:28:25
	 */
	List<SaleorderGoods> getSaleorderGoodsNoSH(Saleorder saleorder);

	/**
	 * 
	 * <b>Description:</b>订单下的所有特殊商品都收获
	 * 
	 * @param saleorderId
	 * @return int
	 * @Note <b>Author：</b> scott.zhu <b>Date:</b> 2018年11月8日 下午3:08:18
	 */
	int updateAllTsGoodsInfo(Integer saleorderId);

	/**
	 * <b>Description:</b><br>
	 * 批量插入销售（美年）产品
	 * 
	 * @param :
	 *            msaleOrderGoodsList
	 * @return : Integer
	 * @Note <b>Author:</b> Bert <br>
	 *       <b>Date:</b> 2018/11/15 16:58
	 */
	Integer saveBatchAddMSaleGoods(@Param("msaleOrderGoodsList") List<SaleorderGoods> msaleOrderGoodsList);

	/**
	 * 
	 * <b>Description:获取快递单下的所有销售商品信息列表</b>
	 * 
	 * @param expressId
	 * @return List<SaleorderGoods>
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月24日 下午3:36:09
	 */
	List<SaleorderGoods> getSaleorderGoodsListByExId(Integer expressId);

    /**
     * 
     * <b>Description:</b>根据销售商品id查询销售商品
     * @param listRInfo
     * @return List<SaleorderGoods>
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2018年11月23日 下午3:17:18
     */
    List<SaleorderGoods> getSaleorderGoodsList(@Param("rbsIds") List<RBuyorderSaleorder> rbsIds);

	/**
	* @Title: getGoodsPrice
	* @Description: TODO(得到订单商品贝登精选价格)
	* @param @param saleorderId
	* @param @return    参数
	* @return List<SaleorderGoods>    返回类型
	* @author strange
	* @throws
	* @date 2019年7月23日
	*/
	List<SaleorderGoods> getGoodsPriceList(Integer saleorderId);
	/**
	*更新订单商品占用数量
	* @Author:strange
	* @Date:10:19 2019-11-12
	*/
	int updateOccupyNum(SaleorderGoods saleorderGoods);
	/**
	*新在售列表
	* @Author:strange
	* @Date:18:58 2019-11-13
	*/
    List<SaleorderGoodsVo> getNewSdList(Goods goods);
	/**
	*获取订单商品占用信息
	* @Author:strange
	* @Date:15:29 2019-11-28
	*/
	List<SaleorderGoods> getSaleordergoodsOccupyNumList(Saleorder saleorder);
	/**
	*获取订单中活动商品
	* @Author:strange
	* @Date:13:12 2019-12-07
	*/
	List<SaleorderGoods> getActionGoodsList(Saleorder saleorder);
	/**
	 *获取订单内未出库商品数
	 * @Author:strange
	 * @Date:17:13 2019-12-07
	 */
    List<SaleorderGoods> getSaleGoodsNoOutNumList(Integer saleorderId);

	/**
	 * <b>Description:</b> 批量插入订单优惠券
	 *
	 * @param couponList
	 * @return int
	 * @Note <b>Author：</b> duke.li <b>Date:</b> 2018年11月2日 下午5:24:32
	 */
	int insertOrderCouponBatch(@Param("couponList") List<SaleorderCoupon> couponList);
	/**
	*获取订单活动商品信息
	* @Author:strange
	* @Date:10:36 2019-12-23
	*/
	List<SaleorderGoods> getActionGoodsInfo(SaleorderVo saleorderVo);

	/**
	 * 根据销售归属查询销售列表id集合
	 * @param proUserId
	 * @return
	 */
	List<Integer> getSaleOrderGoodsIdListByUserId(@Param("proUserId")Integer proUserId);

	/**
	 * 根据sku查找归属人信息
	 * @param sku
	 * @return
	 */
    Spu getSku(@Param("sku")String sku);

	/**
	 * 根据销售归属集合查询销售列表id集合
	 * @param
	 * @return
	 */
	List<Integer> getSaleOrderGoodsIdListByUserIds(@Param("userList")List<User> userList);

	List<SaleorderGoods> getSaleorderGoodsBySaleorderId(Integer saleorderId);

	Integer getCountOfNotAllReceived(Integer saleorderId);

	List<SaleorderGoods> getSaleorderGoodsByExpressDetailIdList(List<Integer> detailIdList);

	SaleorderGoods getSaleorderGoodsByExpressDetailId(Integer expressDetailId);
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

	CoreSpuGenerate getSpuBase(Integer skuId);

	/**
	 * <b>Description:</b>根据订单标识获取商品sku<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/17
	 */
	List<SaleorderGoods> getGoodsSkuByOrderId(@Param("saleorderId")Integer saleorderId);
}