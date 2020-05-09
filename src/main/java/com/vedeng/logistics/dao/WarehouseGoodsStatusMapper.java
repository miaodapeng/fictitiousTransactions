package com.vedeng.logistics.dao;


import com.vedeng.logistics.model.WarehouseGoodsStatus;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.model.vo.WarehouseGoodsStatusVo;
import com.vedeng.order.model.GoodsData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.inject.Named;
import java.util.List;

@Named("warehouseGoodsStatusMapper")
public interface WarehouseGoodsStatusMapper {
    
    /**
     * <b>Description:</b><br> 根据产品id和公司id获取库存数量
     * @param record
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月1日 下午5:11:00
     */
    Integer getGoodsStock(WarehouseGoodsStatus record);

	/**
	 * <b>Description:</b><br>  批量查询产品库存总数（目前库存中的总数量）
	 * @param goodsId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月24日 下午2:22:37
	 */
	List<GoodsData> getGoodsStockList(@Param("goodsIds") List<Integer> goodsIds, @Param("companyId") Integer companyId);


	Integer getGoodsId(@Param("sku")String sku);
	/** 
	* @Description: 根据GoodID修改 
	* @Param: [warehouseGoodsStatus] 
	* @return: java.lang.Integer 
	* @Author: addis
	* @Date: 2019/8/26 
	*/
	Integer updateGoodId(WarehouseGoodsStatus warehouseGoodsStatus);
	/**
	* 查询旧库存表goodsid
	* @Author:strange
	* @Date:18:52 2019-11-05
	*/
	List<WarehouseStock> getAllStockId();
	/**
	*根据SKu获取其占用库存
	* @Author:strange
	* @Date:19:16 2019-11-05
	*/
	List<GoodsData> getOccupyNum(List<String> sku);
	/**
	*库存统计byGoodsId
	* @Author:strange
	* @Date:17:05 2019-11-06
	*/
	List<WarehouseStock> getStockNumByGoodsId(List<Integer> goodsId);
	/**
	*获取库存及库位信息
	* @Author:strange
	* @Date:13:54 2020-03-10
	*/
	List<WarehouseGoodsStatus> getWarehouseStatusByGoodsId(Integer goodsId);
	/**
	*	获取实际库存及库位信息
	* @Author:strange
	* @Date:14:16 2020-03-10
	*/
	List<WarehouseGoodsStatus> getReallGoodsStockNumByGoodsId(Integer goodsId);
	/**
	*更新库存量
	* @Author:strange
	* @Date:19:00 2020-03-10
	*/
	Integer updateStockNumById(WarehouseGoodsStatus warehouseGoodsStatus);
}