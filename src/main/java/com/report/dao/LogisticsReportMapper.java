package com.report.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.vedeng.logistics.model.WarehouseGoodsSet;
import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.logistics.model.FileDelivery;

@Named("logisticsReportMapper")
public interface LogisticsReportMapper {

	/**
	 * <b>Description:</b><br> 分页获取文件寄送
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 上午11:41:08
	 */
	List<FileDelivery> getFileDeliveryListPage(Map<String, Object> map);

	List<FileDelivery> getFileDeliveryListByUName(User user);

	/**
	 * <b>Description:</b><br> 根据ID获取文件寄送
	 * @param fileDeliveryIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月9日 上午9:31:52
	 */
	List<FileDelivery> getFileDeliveryListByIds(@Param("fileDeliveryIds")List<Integer> fileDeliveryIds);
    /** 
    * @Description: 查询库位信息
    * @Param: [] 
    * @return: java.util.List<com.vedeng.logistics.model.WarehouseGoodsSet> 
    * @Author: scott.zhu 
    * @Date: 2019/5/16
	 * @param warehouseGoodsSet
    */
    List<WarehouseGoodsSet> exportWarehouseGoodsSetsList(WarehouseGoodsSet warehouseGoodsSet);
}
