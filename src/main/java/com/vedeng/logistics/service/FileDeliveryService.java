package com.vedeng.logistics.service;

import java.util.List;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.logistics.model.Logistics;

public interface FileDeliveryService extends BaseService{

	/**
	 * 获取文件寄送列表
	 * @param fileDelivery
	 * @param express 
	 * @param creatorIds
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	List<FileDelivery> getFileDeliveryList(FileDelivery fileDelivery, Express express, List<Integer> creatorIds, Page page) throws Exception;
	/**
	 * 
	 * <b>Description:</b><br> 保存文件寄送
	 * @param fileDelivery
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年7月24日 下午6:44:16
	 */
	Integer saveFileDelivery(FileDelivery fileDelivery) throws Exception;
	/**
	 * 
	 * <b>Description:</b><br> 根据ID获取文件寄送详情
	 * @param fileDeliveryId
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年7月26日 上午9:54:43
	 */
	FileDelivery getFileDeliveryInfoById(Integer fileDeliveryId) throws Exception;
	/**
	 * 
	 * <b>Description:</b><br> 保存文件寄送操作
	 * @param fileDelivery
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月1日 下午1:09:49
	 */
	ResultInfo<?> saveExpress(Express express) throws Exception;
	/**
	 * 
	 * <b>Description:</b><br> 更新文件寄送
	 * @param fileDelivery
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年8月3日 上午9:33:19
	 */
	int updateFileDelivery(FileDelivery fileDelivery);
	/**
	 * 
	 * <b>Description:</b><br> 根据收件人名称查询文件
	 * @param ue
	 * @return
	 * @Note
	 * <b>Author:</b> scott
	 * <br><b>Date:</b> 2017年10月25日 上午10:21:56
	 */
	List<FileDelivery> getFileDeliveryListByUName(User ue);
	
	/**
	 * <b>Description:</b><br>
	 *  更新文件的关闭状态
	 * @param :a
	 *@return :a
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/1/24 20:40
	 */
	Integer updateDeliveryCloseStatus(FileDelivery fileDelivery);

}
