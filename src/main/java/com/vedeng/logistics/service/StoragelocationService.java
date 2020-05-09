package com.vedeng.logistics.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.StorageLocation;
import com.vedeng.logistics.model.StorageRack;
/**
 * 
 * <b>Description:</b><br> 库区管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.service
 * <br><b>ClassName:</b> StoragelocationService
 * <br><b>Date:</b> 2017年7月26日 下午4:28:38
 */
public interface StoragelocationService extends BaseService{
    /**
     * 
     * <b>Description:</b><br> 货区列表
     * @param storageLocation
     * @param page
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月26日 下午4:32:28
     */
	Map<String, Object> getStorageLocationList(StorageLocation storageLocation, Page page);
    /**
     * 
     * <b>Description:</b><br> 保存库位
     * @param storageLocation
     * @param request
     * @param session
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月27日 上午11:39:17
     */
	ResultInfo saveStorageLocation(StorageLocation storageLocation, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 根据name查询库区
	 * @param storageLocation
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午1:16:28
	 */
	StorageLocation getStorageLocationByName(StorageLocation storageLocation, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 根据id获取库位
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午1:38:00
	 */
	StorageLocation getStoragelocationById(StorageLocation storageLocation);
	/**
	 * 
	 * <b>Description:</b><br> 编辑库位
	 * @param storageLocation
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午2:59:28
	 */
	ResultInfo editStorageLocation(StorageLocation storageLocation, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 禁用库位
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月27日 下午3:20:27
	 */
	ResultInfo<?> updateDisableStorageLocation(StorageLocation storageLocation);
	/**
	 * 
	 * <b>Description:</b><br> 根据公司id获取库位
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午5:30:12
	 */
	List<StorageLocation> getStorageLocationListByCId(StorageLocation storageLocation);
	/**
	 * 
	 * <b>Description:</b><br> 查询所有的库位
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 下午1:41:04
	 */
	List<StorageLocation> getAllStorageLocation(StorageLocation storageLocation);
	/**
	 * 
	 * <b>Description:</b><br> 查询库位下的商品数量
	 * @param storageLocation
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月21日 上午10:58:16
	 */
	StorageLocation getGoodsNum(StorageLocation storageLocation);
}
