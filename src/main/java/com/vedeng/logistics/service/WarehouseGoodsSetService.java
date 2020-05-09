package com.vedeng.logistics.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.WarehouseGoodsSet;
/**
 * 
 * <b>Description:</b><br> 库位分配
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.service
 * <br><b>ClassName:</b> StorageDistributionService
 * <br><b>Date:</b> 2017年8月1日 上午11:45:31
 */
public interface WarehouseGoodsSetService extends BaseService{
    /**
     * 
     * <b>Description:</b><br> 商品库位列表
     * @param distribution
     * @param page
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月1日 下午12:56:38
     */
	Map<String, Object> getWarehouseGoodsSetList(WarehouseGoodsSet warehouseGoodsSet, Page page);
    /**
     * 
     * <b>Description:</b><br> 删除库位商品
     * @param warehouseGoodsSet
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月3日 下午4:17:05
     */
	ResultInfo<?> delWarehouseGoods(WarehouseGoodsSet warehouseGoodsSet);
	/**
	 * 
	 * <b>Description:</b><br> 批量删除库位商品
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 上午8:45:57
	 */
	ResultInfo<?> delBatchWarehouseGoods(List<WarehouseGoodsSet> list);
	/**
	 * 
	 * <b>Description:</b><br> 批量判断订单号是否重复
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 下午3:19:09
	 */
	List<String> batchSaveSkuName(List<WarehouseGoodsSet> list);
	/**
	 * 
	 * <b>Description:</b><br> 批量分配库位
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 下午3:56:48
	 */
	ResultInfo<?> batchSaveWarehouseGoods(List<WarehouseGoodsSet> list);
	/**
	 * 
	 * <b>Description:</b><br> 查询所有的库位
	 * @param warehouseGoodsSet 
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月8日 上午10:49:21
	 */
	Map<String, Object> getWarehouseList(WarehouseGoodsSet warehouseGoodsSet, Page page);
	/**
	 * 
	 * <b>Description:</b><br> 判断订货号是否存在
	 * @param goods
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月18日 上午10:33:47
	 */
	List<Goods> getGoods(Goods goods);
	/**
	 * 
	 * <b>Description:</b><br> 
	 * @param warehouseGoodsSet 根据产品ID获取库位信息
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月23日 上午9:48:05
	 */
	List<WarehouseGoodsSet> getWarehouseSetForGood(WarehouseGoodsSet warehouseGoodsSet);

	/**
	*获取库位信息
	* @Author:strange
	* @Date:20:21 2019-12-26
	*/
	Map<String, WarehouseGoodsSet> getStorageLocationNameBuySku(List<String> skuList);
}
