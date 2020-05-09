package com.report.service;

import java.util.List;
import java.util.Map;

import com.report.model.export.ExpressExport;
import com.report.model.export.WareHouseLogExport;
import com.vedeng.authorization.model.User;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.logistics.model.WarehouseGoodsSet;

/**
 * <b>Description:</b><br> 物流数据Service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.report.service.impl
 * <br><b>ClassName:</b> LogisticsReportService
 * <br><b>Date:</b> 2017年11月30日 上午10:42:57
 */
public interface LogisticsReportService extends BaseService{

	/**
	 * <b>Description:</b><br> 导出商品库位
	 * @param warehouseGoodsSet
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月6日 下午1:35:08
	 */
	List<WarehouseGoodsSet> exportWarehouseGoodsSetList(WarehouseGoodsSet warehouseGoodsSet, Page page);
	
	/**
	 * <b>Description:</b><br> 获取商品库位
	 * @param warehouseGoodsSet
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月6日 下午1:36:00
	 */
	Map<String, Object> getWarehouseGoodsSetList(WarehouseGoodsSet warehouseGoodsSet, Page page);

	/**
	 * <b>Description:</b><br> 导出文件寄送
	 * @param fileDelivery
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 上午11:33:16
	 */
	List<FileDelivery> exportFileDeliveryList(FileDelivery fileDelivery, List<Integer> creatorIds, Page page);

	/**
	 * <b>Description:</b><br> 获取文件寄送
	 * @param fileDelivery
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 上午11:34:01
	 */
	Map<String, Object> getFileDeliveryList(FileDelivery fileDelivery, List<Integer> creatorIds,  List<Integer> relatedIds, Page page);

	/**
	 * <b>Description:</b><br> 导出库位
	 * @param warehouseGoodsSet
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 下午3:55:38
	 */
	List<WarehouseGoodsSet> exportWarehouseList(WarehouseGoodsSet warehouseGoodsSet, Page page);
	
	/**
	 * <b>Description:</b><br> 获取库位
	 * @param warehouseGoodsSet
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月7日 下午3:56:19
	 */
	Map<String, Object> getWarehouseList(WarehouseGoodsSet warehouseGoodsSet, Page page);

	/**
	 * <b>Description:</b><br> 导出快递记录
	 * @param express
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月8日 下午4:06:39
	 */
	List<ExpressExport> exportExpressList(Express express, Page page);
	
	/**
	 * <b>Description:</b><br> 获取快递记录
	 * @param express
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月8日 下午4:06:54
	 */
//	Map<String, Object> getExpressList(Express express, Page page);

	/**
	 * <b>Description:</b><br> 通过收件名获取
	 * @param ue
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月8日 下午5:46:18
	 */
	List<FileDelivery> getFileDeliveryListByUName(User user);

	/**
	 * <b>Description:</b><br> 导出出库记录
	 * @param warehouseGoodsOperateLog
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月18日 下午6:22:27
	 */
	List<WareHouseLogExport> exportWareHouseOutList(WarehouseGoodsOperateLog warehouseGoodsOperateLog, Page page);

	/**
	 * <b>Description:</b><br> 导出入库记录
	 * @param warehouseGoodsOperateLog
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月19日 下午1:20:42
	 */
	List<WareHouseLogExport> exportWareHouseInList(WarehouseGoodsOperateLog warehouseGoodsOperateLog, Page page);
	/** 
	* @Description: 导出库位信息
	* @Param: [warehouseGoodsSet, page] 
	* @return: java.util.List<com.vedeng.logistics.model.WarehouseGoodsSet> 
	* @Author: scott.zhu 
	* @Date: 2019/5/16 
	*/
    List<WarehouseGoodsSet> exportWarehouseGoodsSetsList(WarehouseGoodsSet warehouseGoodsSet);
}
