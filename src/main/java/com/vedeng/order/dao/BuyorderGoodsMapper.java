package com.vedeng.order.dao;

import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.BuyorderGoods;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BuyorderGoodsMapper {
    int deleteByPrimaryKey(Integer buyorderGoodsId);

    int insert(BuyorderGoods record);

    int insertSelective(BuyorderGoods record);

    BuyorderGoods selectByPrimaryKey(Integer buyorderGoodsId);

    int updateByPrimaryKeySelective(BuyorderGoods record);

    int updateByPrimaryKey(BuyorderGoods record);
    /**
     * <b>Description:</b><br> 根据saleorderGoodsId获取采购订单产品数量的合计
     * @param buyorderGoodsId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月20日 上午11:11:15
     */
    Integer getBuyorderGoodsSum(@Param("saleorderGoodsId") Integer saleorderGoodsId);

    /**
     * <b>Description:</b><br> 根据saleorderGoodsId批量查询已采购数量的合计
     * @param buyorderGoodsId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月20日 上午11:11:15
     */
    List<SaleorderGoodsVo> batchBuyorderGoodsSum(@Param("saleorderGoodsList") List<SaleorderGoodsVo> saleorderGoodsList);

    /**
     * <b>Description:</b><br> 批量查询采购订单产品数量
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2018年2月1日 下午5:14:53
     */
    List<BuyorderGoodsVo> getBuyorderGoodsSumBySaleorderGoodsIds(@Param("saleorderGoodsIds") List<SaleorderGoodsVo> saleorderGoodsIds);


	/**
	 * <b>Description:</b><br> 批量查询采购订单产品数量
	 * @param saleorderGoodsIdList
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年2月1日 下午5:14:53
	 */
	List<BuyorderGoodsVo> getBuyorderGoodsSumBySaleorderGoodsIdList(@Param("saleorderGoodsIdList") List<Integer> saleorderGoodsIdList);

    /**
     * <b>Description:</b><br> 根据采购订单的id查询采购商品的集合
     * @param buyorderId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月26日 下午3:29:34
     */
    List<BuyorderGoodsVo> getBuyorderGoodsVoListByBuyorderId(@Param("buyorderId") Integer buyorderId);
    /**
     * 根据采购订单的id查询采购商品的集合,一对多查询出个商品对应的收票列表
     * <b>Description:</b><br>
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年7月2日 下午3:31:24
     */
    List<BuyorderGoodsVo> getbuyorderdetaillistnew(Map<String, Object> map);
    /**
     *
     * <b>Description:</b><br> 根据采购订单的id查询采购商品的集合(分页)
     * @param map
     * @return
     * @Note
     * <b>Author:</b> Cooper
     * <br><b>Date:</b> 2018年6月20日 下午4:55:48
     */
    List<BuyorderGoodsVo> getBuyorderGoodsVoListPage(Map<String, Object> map);
    /**
     * <b>Description:</b><br> 根据采购单查询运费
     * @param buyorderId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月26日 下午3:29:34
     */
    BuyorderGoodsVo getFreightByBuyorderId(BuyorderGoodsVo buyorderGoodsVo);

    /**
     * <b>Description:</b><br> 获取buyorderid的集合
     * @param buyorderGoodsVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年9月1日 上午8:19:24
     */
    List<Integer> getBuyorderIdList(BuyorderGoodsVo buyorderGoodsVo);

	/**
	 * <b>Description:</b><br> 订单产品发货数量
	 * @param buyorderGoodsId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月19日 下午3:30:06
	 */
	Integer getDeliveryNum(@Param("buyorderGoodsId") Integer buyorderGoodsId);
    /**
     *
     * <b>Description:</b><br> 查询采购单价和销售单价
     * @param
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月16日 下午4:55:18
     */
	BuyorderGoods getPriceById(WarehouseGoodsOperateLog w);

	/**
	 * <b>Description:</b><br> 获取待同步采购订单产品数据
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年12月26日 下午3:05:43
	 */
	List<BuyorderGoods> getWaitSyncBuyorderGoodsList();

	/**
     *
     * <b>Description:</b><br> 获取订单下的产品
     * @param b
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年1月20日 下午2:34:49
     */
	List<SaleorderGoods> getBuyorderGoodsVoList(Buyorder b);

	/**
	 * <b>Description:</b><br> 交易记录
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月25日 下午2:09:26
	 */
	List<BuyorderGoodsVo> getBusinessListPage(Map<String, Object> map);
	/**
	 *
	 * <b>Description:</b><br> 根据采购订单的id查询采购商品的集合(排除特殊商品)
	 * @param buyorderId
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年3月7日 下午1:46:07
	 */
	List<BuyorderGoodsVo> getBuyorderGoodsVoListByBuyorderIdNoSpecial(@Param("buyorderId") Integer buyorderId);
	/**
	 *
	 * <b>Description:</b><br> 根据采购订单详情的id集合查询采购商品的集合(排除特殊商品)
	 * @param buyorderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年8月1日 下午4:34:03
	 */
	List<BuyorderGoodsVo> getBuyorderGoodsVoListByBuyorderGoodsIdListNoSpecial(List<Integer> buyorderIdList);
	/**
	 *
	 * <b>Description:</b><br> 批量修改采购附表中的部分字段
	 * @param buyorderGoodList
	 * @return
	 * @Note
	 * <b>Author:</b> Michale
	 * <br><b>Date:</b> 2018年5月11日 下午4:24:52
	 */
	Integer updateByPrimaryKeySelectiveBatch(List<BuyorderGoods> buyorderGoodList);
    /**
     *
     * <b>Description:</b><br> 根据主键获取采购产品
     * @param relatedId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月29日 下午3:21:26
     */
	BuyorderGoods selectbGoodsKey(Integer relatedId);

	/**
	 * <b>Description:</b><br> 修改采购商品的采购备注
	 * @param buyorderGoods
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月18日 下午5:30:31
	 */
	int updateBuyorderGoodsInsideComments(BuyorderGoods buyorderGoods);

	/**
	 * <b>Description:</b><br> 批量修改采购商品的采购备注
	 * @param buyorderGoods
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月18日 下午5:30:31
	 */
	int batchUpdateBuyorderGoodsInsideComments(@Param("buyorderGoodsList") List<BuyorderGoods> buyorderGoodsList);

	/**
	 * 
	 * <b>Description:</b><br> 根据主键集合查询采购订单详情信息
	 * @param relatedIdList
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年7月31日 下午6:14:17
	 */
	List<BuyorderGoods> selectBuyorderGoodsByList(List<Integer> relatedIdList);
    /**
     * 
     * <b>Description:</b>查询订单下的所有商品
     * @param buyorderId
     * @return List<BuyorderGoodsVo>
     * @Note
     * <b>Author：</b> scott.zhu
     * <b>Date:</b> 2018年11月28日 下午2:23:58
     */
    List<BuyorderGoodsVo> getBuyorderGoodsVoListByBuyorderIds(Integer buyorderId);

	Integer updateDataTimeByOrderId(Integer orderId);

	Integer updateDataTimeByDetailId(Integer orderDetailId);
}