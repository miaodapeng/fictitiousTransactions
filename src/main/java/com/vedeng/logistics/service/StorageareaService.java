package com.vedeng.logistics.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageRack;
import com.vedeng.logistics.model.StorageRoom;
/**
 * 
 * <b>Description:</b><br> 库房管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.service
 * <br><b>ClassName:</b> StorageareaService
 * <br><b>Date:</b> 2017年7月21日 上午9:08:46
 */
public interface StorageareaService extends BaseService{
    /**
     * 
     * <b>Description:</b><br> 货区列表
     * @param storageArea
     * @param page
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月21日 上午9:10:00
     */
	Map<String, Object> getStorageAreaList(StorageArea storageArea, Page page);
    /**
     * 
     * <b>Description:</b><br> 新增货区
     * @param storageArea
     * @param request
     * @param session
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月21日 下午4:34:29
     */
	ResultInfo saveStorageArea(StorageArea storageArea, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 货区详情
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午5:00:35
	 */
	StorageArea getStorageareaById(StorageArea storageArea);
	/**
	 * 
	 * <b>Description:</b><br> 编辑货区
	 * @param storageArea
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午5:56:44
	 */
	ResultInfo editStorageArea(StorageArea storageArea, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 获取所属库房
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午5:56:44
	 */
	List<StorageRoom> getStorageRoomList(StorageRoom storageroom);
	/**
	 * 
	 * <b>Description:</b><br> 根据name查询货区
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月21日 下午5:56:44
	 */
	StorageArea getStorageAreaByName(StorageArea storageArea, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 获取库房下的货区
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月24日 上午9:04:42
	 */
	List<StorageRack> getStorageRackByRId(StorageArea storageArea);
	/**
	 * 
	 * <b>Description:</b><br> 禁用货区
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月24日 上午10:15:54
	 */
	ResultInfo<?> upDisableStorageArea(StorageArea storageArea);
	/**
	 * 
	 * <b>Description:</b><br> 根据公司id获取货区
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午1:56:20
	 */
	List<StorageArea> getStorageAreaListByCId(StorageArea storageArea);
	/**
	 * 
	 * <b>Description:</b><br> 查询所有的货区
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 上午11:47:01
	 */
	List<StorageArea> getAllStorageArea(StorageArea storageArea);
	/**
	 * 
	 * <b>Description:</b><br> 查询货区的商品数量
	 * @param storageArea
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月21日 上午8:36:18
	 */
	StorageArea getGoodsNum(StorageArea storageArea);
	
	
}
