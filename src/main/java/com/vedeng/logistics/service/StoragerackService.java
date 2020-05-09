package com.vedeng.logistics.service;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.StorageArea;
import com.vedeng.logistics.model.StorageLocation;
import com.vedeng.logistics.model.StorageRack;
/**
 * 
 * <b>Description:</b><br> 货架管理
 * @author scott
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.warehouse.service
 * <br><b>ClassName:</b> StoragerackService
 * <br><b>Date:</b> 2017年7月25日 下午2:22:31
 */
public interface StoragerackService extends BaseService{
    
	/**
	 * 
	 * <b>Description:</b><br> 货架列表
	 * @param storageRack
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午2:32:53
	 */
	Map<String, Object> getStorageRackList(StorageRack storageRack, Page page);
    /**
     * 
     * <b>Description:</b><br> 根据公司id得到所属货区
     * @param storageArea
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年7月25日 下午3:27:39
     */
	List<StorageArea> getStorageAreaList(StorageArea storageArea);
	/**
	 * 
	 * <b>Description:</b><br> 新增货架
	 * @param storageRack
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午4:39:11
	 */
	ResultInfo saveStorageRack(StorageRack storageRack, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 根据name查询货架
	 * @param storageRack
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月25日 下午4:59:59
	 */
	StorageRack getStorageRackByName(StorageRack storageRack, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 根据id获取货架
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 上午8:48:47
	 */
	StorageRack getStoragerackById(StorageRack storageRack);
	/**
	 * 
	 * <b>Description:</b><br> 获取货架下的库区
	 * @param storageRackInfo
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 上午9:43:18
	 */
	List<StorageLocation> getStorageLocationList(StorageRack storageRackInfo);
	/**
	 * 
	 * <b>Description:</b><br> 编辑货架
	 * @param storageRack
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午2:55:57
	 */
	ResultInfo editStorageRack(StorageRack storageRack, HttpServletRequest request, HttpSession session);
	/**
	 * 
	 * <b>Description:</b><br> 禁用货架
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午3:56:49
	 */
	ResultInfo<?> updateDisableStorageRack(StorageRack storageRack);
	/**
	 * 
	 * <b>Description:</b><br> 根据公司id获取货架
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年7月26日 下午5:30:12
	 */
	List<StorageRack> getStorageRackListByCId(StorageRack storageRack);
	/**
	 * 
	 * <b>Description:</b><br> 查询所有的货架
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月4日 下午1:32:53
	 */
	List<StorageRack> getAllStorageRack(StorageRack storageRack);
	/**
	 * 
	 * <b>Description:</b><br> 查询货架下商品数量
	 * @param storageRack
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年8月21日 上午10:31:05
	 */
	StorageRack getGoodsNum(StorageRack storageRack);
    
	
}
