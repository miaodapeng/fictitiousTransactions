package com.vedeng.logistics.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.vedeng.authorization.model.User;
import com.vedeng.logistics.model.FileDelivery;
@Named("fileDeliveryMapper")
public interface FileDeliveryMapper {
    int deleteByPrimaryKey(Integer fileDeliveryId);

    int insert(FileDelivery record);

    int insertSelective(FileDelivery record);

    FileDelivery selectByPrimaryKey(Integer fileDeliveryId);

    int updateByPrimaryKeySelective(FileDelivery record);

    int updateByPrimaryKey(FileDelivery record);

    /**
     * 获取文件寄送列表
     * @param map
     * @return
     */
	List<FileDelivery> getFileDeliverylistpage(Map<String, Object> map);
    /**
     * 
     * <b>Description:</b><br> 根据收件人名称查询文件
     * @param user
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2017年10月25日 上午10:24:21
     */
	List<FileDelivery> getFileDeliveryListByUName(User user);
	
	/**
	 * <b>Description:</b><br>
	 * 更新文件的关闭状态
	 * @param :a
	 *@return :a
	 *@Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/1/24 20:59
	 */
	Integer updateDeliveryCloseStatus( FileDelivery fileDelivery );
}