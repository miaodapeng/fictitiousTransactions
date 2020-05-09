package com.vedeng.logistics.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageRoom;
import com.vedeng.logistics.model.Warehouse;
/**
 * 
 * <b>Description:</b><br> 库房管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.service
 * <br><b>ClassName:</b> Storageroom
 * <br><b>Date:</b> 2017年7月17日 下午6:42:27
 */
public interface StorageroomService extends BaseService{
	/**
	 * 
	 * <b>Description:</b><br> 根据上级仓库的id查询库房列表
	 * @param storageroom
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月17日 下午6:43:35
	 */
	List<StorageRoom> getStorageListById(Warehouse warehouses);
    /**
     * 
     * <b>Description:</b><br> 获取库房列表
     * @param storageroom
     * @param page
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月19日 下午2:35:54
     */
	Map<String, Object> getStorageroomList(StorageRoom storageroom, Page page);
	/**
	 * 
	 * <b>Description:</b><br> 根据公司id获取仓库
	 * @param warehouses
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月19日 下午3:22:27
	 */
	List<Warehouse> getWarehouseByCompanyId(Warehouse warehouses);
	/**
	 * 
	 * <b>Description:</b><br> 新增库房
	 * @param storageroom
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 上午9:41:55
	 */
	ResultInfo saveStorageRoom(StorageRoom storageroom, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 库房详情
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 上午10:21:53
	 */
	StorageRoom getStorageroomById(StorageRoom storageroom);
	/**
	 * 
	 * <b>Description:</b><br> 根据库房id获取货区
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 下午1:23:01
	 */
	List<StorageArea> getStorageArea(StorageRoom storageroom);
	/**
	 * 
	 * <b>Description:</b><br> 编辑库房
	 * @param storageroom
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月20日 下午4:55:27
	 */
	ResultInfo editStorageRoom(StorageRoom storageroom, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 禁用库房
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 上午8:35:06
	 */
	ResultInfo<?> upDisableStorageRoom(StorageRoom storageroom);
	/**
	 * <b>Description:</b><br> 根据库房名查询库房
	 * @param storageroom
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 上午8:35:06
	 */
	StorageRoom getStorageRoomByName(StorageRoom storageroom, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 查询所有的库房
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 上午11:38:15
	 */
	List<StorageRoom> getAllStorageRoom(StorageRoom storageroom);
	/**
	 * 
	 * <b>Description:</b><br> 查询库房里的商品数
	 * @param storageroom
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月18日 下午5:06:23
	 */
	StorageRoom getGoodsNum(StorageRoom storageroom);
}
