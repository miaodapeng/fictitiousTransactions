package com.vedeng.logistics.dao;


import com.vedeng.goods.model.Goods;
import com.vedeng.logistics.model.WarehouseGoodsSet;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named("warehouseGoodsSetMapper")
public interface WarehouseGoodsSetMapper {
    /**
	 * 
	 * <b>Description:</b><br> 商品库区列表
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月1日 下午1:13:29
	 */
	List<WarehouseGoodsSet> getWarehouseGoodsSetListPage(Map<String, Object> map);
    /**
     * 
     * <b>Description:</b><br> 删除库位商品
     * @param warehouseGoodsSet
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月3日 下午4:28:23
     */
	int delWarehouseGoods(WarehouseGoodsSet warehouseGoodsSet);
    /**
     * 
     * <b>Description:</b><br> 批量删除库位商品
     * @param list
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月4日 上午8:55:39
     */
	int delBatchWarehouseGoods(List<WarehouseGoodsSet> list);
    /**
     * 
     * <b>Description:</b><br> 验证批量分配时订单号是否重复
     * @param list
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月4日 下午3:27:26
     */
	List<String> batchSaveSkuname(List<WarehouseGoodsSet> list);
    /**
     * 
     * <b>Description:</b><br> 批量分配库房
     * @param list
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月4日 下午4:22:15
     */
	int batchSaveWarehouseGoods(List<WarehouseGoodsSet> list);
    /**
     * 
     * <b>Description:</b><br> 查询所有的库位
     * @param map
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月8日 上午10:56:01
     */
	List<WarehouseGoodsSet> getWarehouseListPage(Map<String, Object> map);
    /**
     * 
     * <b>Description:</b><br> 根据订货号查询goods
     * @param goods
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年8月18日 上午10:40:34
     */
	List<Goods> getGoods(Goods goods);
    /**
     * 
     * <b>Description:</b><br> 
     * @param warehouseGoodsSet 根据产品ID查询分配的库位信息（包含临时库位）
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年8月23日 上午10:11:50
     */
	List<WarehouseGoodsSet> getWarehouseSetForGood(WarehouseGoodsSet warehouseGoodsSet);
    /**
     * 
     * <b>Description:</b><br> 查询单个的记录
     * @param wgd
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年9月7日 下午4:59:42
     */
	WarehouseGoodsSet getWarehouseGoodsSet(WarehouseGoodsSet wgd);
    /**
     * 
     * <b>Description:</b><br> 更新 数据
     * @param wgd
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年9月7日 下午5:08:24
     */
	int updateWs(WarehouseGoodsSet wgd);
    /**
     * 
     * <b>Description:</b><br> 插入记录
     * @param wgd
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年9月7日 下午5:18:25
     */
	int insertWs(WarehouseGoodsSet wgd);
	
    /**
     * 
     * <b>Description:</b><br> 根据公司id获取全部五级仓
     * @param warehouseGoodsSet
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年3月2日 下午6:19:13
     */
	List<WarehouseGoodsSet> getWarehouseAll(WarehouseGoodsSet warehouseGoodsSet);
    /**
     * 
     * <b>Description:</b><br> 查询产品货仓
     * @param w
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年3月8日 上午10:01:14
     */
	WarehouseGoodsSet getWarehouseGoodsSetByGoodId(WarehouseGoodsSet w);
    /** 
    * @Description: 单个删除商品库位 
    * @Param: [w] 
    * @return: int 
    * @Author: scott.zhu 
    * @Date: 2019/4/11 
    */
	int delWarehouseGoodsById(WarehouseGoodsSet w);
    /** 
    * @Description: 批量删除商品库位 
    * @Param: [list] 
    * @return: int 
    * @Author: scott.zhu 
    * @Date: 2019/4/11 
    */
	int delBatchWarehouseGoodsById(List<WarehouseGoodsSet> list);
	/**
	*获取库位信息
	* @Author:strange
	* @Date:20:26 2019-12-26
	*/
	WarehouseGoodsSet getStorageLocationNameBuySku(String sku);
}