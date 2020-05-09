package com.vedeng.aftersales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.aftersales.model.AfterSalesGoods;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;


public interface AfterSalesGoodsMapper {
    int deleteByPrimaryKey(Integer afterSalesGoodsId);

    int insert(AfterSalesGoods record);

    int insertSelective(AfterSalesGoods record);

    AfterSalesGoods selectByPrimaryKey(Integer afterSalesGoodsId);

    int updateByPrimaryKeySelective(AfterSalesGoods record);

    int updateByPrimaryKey(AfterSalesGoods record);
    
    /**
     * <b>Description:</b><br> 根据ORDER_DETAIL_ID和AFTER_SALES_ID查询售后退货的数量
     * @param afterSalesGoodsVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月10日 下午4:01:13
     */
    Integer getAfterSalesNumByParam(AfterSalesGoodsVo afterSalesGoodsVo);
    
    /**
     * <b>Description:</b><br> 带采购查询销售订单的售后数量
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年2月1日 下午7:05:08
     */
    List<AfterSalesGoodsVo> getPurchaseAfterSalesNum(@Param("saleorderGoodsIds") List<SaleorderGoodsVo> saleorderGoodsIds);



	/**
	 * <b>Description:</b><br> 带采购查询销售订单的售后数量
	 * @param saleorderGoodsIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2019年8月1日 下午7:05:08
	 */
	List<AfterSalesGoodsVo> getPurchaseAfterSalesNumList(@Param("saleorderGoodsIdList") List<Integer> saleorderGoodsIdList);
    
    /**
     * <b>Description:</b><br> 查询采购商品的售后退货数量---直发
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年2月1日 下午7:05:08
     */
    List<AfterSalesGoodsVo> getBuyorderAfterSalesNum(@Param("bgvList") List<SaleorderGoodsVo> bgvList);

	List<AfterSalesGoodsVo> getBuyorderAfterSalesNumList(@Param("saleorderGoodsIdList") List<Integer> saleorderGoodsIdList);

    
    /**
     * <b>Description:</b><br> 根据saleorderGoodsId查询采购商品的售后退货数量
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年2月1日 下午7:05:08
     */
    Integer getBuyorderAfterSalesNumBySaleorderGoodsId(@Param("saleorderGoodsId") Integer saleorderGoodsId);
    
    /**
     * <b>Description:</b><br> 查询采购商品的售后退货数量
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年2月1日 下午7:05:08
     */
    List<BuyorderGoodsVo> getBuyorderAfterSalesNumByIds(@Param("saleorderGoodsList")List<SaleorderGoodsVo> saleorderGoodsList);
    
    /**
     * <b>Description:</b><br> 批量查询采购商品的售后退货数量
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年2月1日 下午7:05:08
     */
    List<BuyorderGoodsVo> batchBuyorderAfterSalesNums(@Param("buyorderGoodsList")List<BuyorderGoodsVo> buyorderGoodsList);
    
    /**
     * <b>Description:</b><br> 查询销售订单的售后产品列表信息
     * @param afterSalesGoods
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午6:48:18
     */
    List<AfterSalesGoodsVo> getAfterSalesGoodsVosList(AfterSalesGoods afterSalesGoods);
    /**
     * <b>Description:</b><br> 查询采购订单的售后产品列表信息
     * @param afterSalesGoods
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午6:48:18
     */
    List<AfterSalesGoodsVo> getBuyorderAfterSalesGoodsVosList(AfterSalesGoods afterSalesGoods);
    
    /**
     * <b>Description:</b><br> 根据售后安调的id查询售后产品列表信息
     * @param afterSalesGoods
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年10月17日 下午6:48:18
     */
    List<AfterSalesGoodsVo> getAfterSalesGoodsVosListByAfterSalesInstallstionId(Integer afterSalesInstallstionId);

    /**
     * 
     * <b>Description:</b><br> 仓储物流订单下的商品
     * @param afterSalesVo
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月17日 下午5:31:10
     */
	List<AfterSalesGoodsVo> getAfterSalesGoodsVoList(AfterSalesVo afterSalesVo);
    /**
     * 
     * <b>Description:</b><br> 根据id查询售后产品
     * @param afterSalesGoods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月19日 上午11:15:10
     */
	AfterSalesGoodsVo getAfterSalesGoodsInfo(AfterSalesGoods afterSalesGoods);
	
	/**
	 * <b>Description:</b><br> 查询退换货订单的退换货手续费信息
	 * @param afterSalesGoods
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月18日 上午11:28:12
	 */
	List<AfterSalesGoods> getThhGoodsList(AfterSalesGoods afterSalesGoods);
	
	/**
	 * <b>Description:</b><br> 查询退换货订单的退换货手续费信息
	 * @param afterSalesGoods
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月18日 上午11:28:12
	 */
	AfterSalesGoodsVo getSpecialGoods(AfterSalesGoods afterSalesGoods);
	
	/**
	 * <b>Description:</b><br> 编辑售后服务费
	 * @param afterSalesId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月2日 下午2:09:20
	 */
	int updateAfterSalesServiceMoney(AfterSalesGoods afterSalesGoods);
	
	/**
	 * <b>Description:</b><br> 删除售后订单关联的产品
	 * @param afterSalesId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月23日 下午3:15:35
	 */
	int delAfterSalesGoodsByParam(Integer afterSalesId);

	/**
	 * <b>Description:</b><br> 获取售后（销售and采购）退换货产品入库信息
	 * @param asg
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年10月23日 下午1:57:11
	 */
	List<AfterSalesGoodsVo> getAfterReturnGoodsStorageList(AfterSalesGoods asgv);
    /**
     * 
     * <b>Description:</b><br> 换货商品在仓库中的位置
     * @param afterSalesGoodsVo
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月1日 上午10:33:25
     */
	List<AfterSalesGoodsVo> warehouseList(AfterSalesGoodsVo afterSalesGoodsVo);
    /**
     * 
     * <b>Description:</b><br> 快递已发商品数
     * @param afterSalesGoodsVo
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年11月7日 下午1:56:24
     */
	Integer getKdNum(AfterSalesGoodsVo afterSalesGoodsVo);

	/**
	 * <b>Description:</b><br> 
	 * @param afterGoodsIdList
	 * @param companyId
	 * @param operateType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月16日 下午5:44:10
	 */
	List<AfterSalesGoods> getAfterGoodsList(@Param("afterGoodsIdList")List<Integer> afterGoodsIdList, @Param("companyId")Integer companyId, @Param("operateType")Integer operateType);

	/**
	 * <b>Description:</b><br> 
	 * @param afterGoodsDetailList
	 * @param companyId
	 * @param i
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月10日 下午8:22:18
	 */
	List<AfterSalesGoods> getSaleGoodsWarehouseList(@Param("afterGoodsIdList")List<Integer> afterGoodsIdList, @Param("companyId")Integer companyId, @Param("operateType")Integer operateType);
	
	/**
	 * <b>Description:</b><br> 查询已完结售后的退货数量
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月9日 下午3:49:18
	 */
	Integer getSaleorderAftersaleReturnGoods(@Param("saleorderGoodsId")Integer saleorderGoodsId);
	
	/**
	 * <b>Description:</b><br> 批量查询已完结售后的退货数量
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月9日 下午3:49:18
	 */
	List<SaleorderGoodsVo> batchSaleorderAftersaleReturnGoods(@Param("saleorderGoodsList")List<SaleorderGoodsVo> saleorderGoodsList);
	/**
	 * <b>Description:</b><br> 查询已完结售后的退货数量
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月9日 下午3:49:18
	 */
	Integer getBuyorderAftersaleReturnGoods(@Param("buyorderGoodsId")Integer buyorderGoodsId);
	/**
	 * 查询已完结售后的退货数量
	 * <b>Description:</b><br> 
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年7月2日 上午11:42:07
	 */
	List<AfterSalesGoodsVo> getBuyorderAftersaleReturnGoodsByList(@Param("bgvList") List<BuyorderGoodsVo> bgvList);
	/**
	 * <b>Description:</b><br> 查询销售产品关联的采购产品的已完结售后的退货数量
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月9日 下午3:49:18
	 */
	Integer getBuyorderAftersaleReturnGoodsBySaleorderGoodsId(@Param("saleorderGoodsId")Integer saleorderGoodsId);
	
	/**
	 * <b>Description:</b><br> 批量查询销售产品关联的采购产品的已完结售后的退货数量
	 * @param bgvList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月9日 下午3:49:18
	 */
	List<SaleorderGoodsVo> batchBuyorderAftersaleReturnGoodsBySaleorderGoodsId(@Param("saleorderGoodsList")List<SaleorderGoodsVo> saleorderGoodsList);
    /**
     * 
     * <b>Description:</b><br> 查询销售售后生成的条码数
     * @param afterSalesGoodsVo
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月20日 上午8:57:19
     */
	AfterSalesGoodsVo getAfterSalesGoodsBnum(AfterSalesGoodsVo afterSalesGoodsVo);
    /**
     * 
     * <b>Description:</b><br> 更新所有的特殊商品收发货状态
     * @param afterSalesGoods
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年4月28日 上午9:36:22
     */
	void updateAllTsGoodsInfo(AfterSalesGoods afterSalesGoods);
	
	/**
	 * <b>Description:</b><br> 查询退货订单的产品
	 * @param afterSalesGoods
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年10月18日 上午11:28:12
	 */
	List<SaleorderGoodsVo> getReturnGoodsList(@Param("afterSalesId")Integer afterSalesId);
    /**
     * 
     * <b>Description:</b><br> 查询订单下未出库的售后商品
     * @param afterSalesId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月17日 下午1:18:35
     */
	List<AfterSalesGoods> getAfterSalesGoodsNoOutList(Integer afterSalesId);
    /**
     * 
     * <b>Description:</b><br> 批量更新售后产品状态
     * @param afterSalesGoodsList
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月17日 下午1:42:37
     */
	Integer updateByPrimaryKeySelectiveBatch(List<AfterSalesGoods> afterSalesGoodsList);
    /**
     * 
     * <b>Description:</b><br> 批量更新售后产品入库状态
     * @param afterorderGoodList
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年7月19日 上午11:25:29
     */
	Integer updateByPrimaryKeySelectiveInBatch(List<AfterSalesGoods> afterorderGoodList);
	
    /**
     * <b>Description:</b><br> 查询采购商品的售后退货数量
     * @param saleorderGoodsIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2018年2月1日 下午7:05:08
     */
    List<AfterSalesGoodsVo> getBuyorderAfterSalesNumByBgvList(@Param("bgvList") List<BuyorderGoodsVo> bgvList);
    /**
     * 
     * <b>Description:</b><br> 售后单下除去特殊商品的所有商品
     * @param afterGoodsId
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年9月6日 上午8:56:45
     */
	List<AfterSalesGoods> getAfterGoodsShList(Integer afterGoodsId);

    /**
     * 
     * <b>Description:</b><br>  根据多个ORDER_DETAIL_ID和AFTER_SALES_ID查询售后退货的数量
     * @param afterSalesGoodsVo
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年7月26日 下午3:00:42
     */
    List<BuyorderGoodsVo> getAfterSalesListNumByParam(AfterSalesGoodsVo afterSalesGoodsVo);
    
    /**
     * 
     * <b>Description:</b><br>根据afterSaleGoodsId集合获取 
     * @param relatedIdList
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年8月1日 上午8:57:56
     */
    List<AfterSalesGoods> getAfterSalesGoodsInfoByList(List<Integer> relatedIdList);

    /**
     * 
     * <b>Description:</b><br>根据afterSaleList去批量更新收货信息
     * @param asgList
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年8月1日 上午9:21:03
     */
    int batchUpdateArrivalByList(List<AfterSalesGoods> asgList);

    /**
     * 
     * <b>Description:</b><br>根据afterSaleList去批量更新发货信息
     * @param asgList
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年8月1日 上午9:21:03
     */
    int batchUpdateDeliveryByList(List<AfterSalesGoods> asgList);
    
    
    /**
     * 
     * <b>Description: 根据afterSalesId获取当前售后订单商品（不包含特殊商品）</b><br> 
     * @param afterSalesVoList
     * @param parendId    [数字字典配置的特殊商品的父类ID]
     * @return
     * <b>Author: Franlin.wu</b>  
     * <br><b>Date: 2018年12月17日 下午8:38:47 </b>
     */
    List<AfterSalesGoods> selectByAfterSalesId(@Param("afterSalesVoList") List<AfterSalesVo> afterSalesVoList, @Param("parendId") Integer parendId);

    /**
     * 
     * <b>Description: 根据商品ID和售后主键ID</b><br> 
     * @param record
     * @return
     * <b>Author: Franlin.wu</b>  
     * <br><b>Date: 2018年12月18日 下午1:47:20 </b>
     */
    int updateByGoodsIdAndAfterSalesId(AfterSalesGoods record);
	/**
	*通过售后商品单id获取归属订单id
	* @Author:strange
	* @Date:17:44 2019-11-13
	*/
	Integer getSaleOrderIdByafterSalesGoodsId(@Param("relatedId")Integer relatedId);
	/**
	 *获取售后单内商品未出库数量
	 * @Author:strange
	 * @Date:17:42 2019-12-07
	 */
    List<AfterSalesGoods> getAfterSalesGoodsNoOutNumList(Integer afterSalesId);

	Integer updateDataTimeByOrderId(Integer orderId);

	Integer updateDataTimeByDetailId(Integer orderDetailId);
}