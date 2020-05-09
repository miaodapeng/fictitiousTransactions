package com.vedeng.call.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.call.model.CallFailed;

public interface CallFailedMapper {
    int deleteByPrimaryKey(Integer callFailedId);

    CallFailed selectByPrimaryKey(Integer callFailedId);

    /**
     * <b>Description:</b><br> 更新
     * @param callFailed
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月20日 下午3:01:52
     */
    int updateByPrimaryKey(CallFailed callFailed);
    
    /**
     * <b>Description:</b><br> 新增记录
     * @param callFailed
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月20日 上午9:40:28
     */
    int insert(CallFailed callFailed);
    
    /**
     * <b>Description:</b><br> 根据logId查询记录
     * @param logId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年7月20日 上午9:45:14
     */
    List<CallFailed> getCallFailedByLogId(@Param("logId")Integer logId);

	/**
	 * <b>Description:</b><br> 漏接来电话务人员
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 上午11:39:03
	 */
	List<CallFailed> getCallFailedDialBackUser();

	/**
	 * <b>Description:</b><br> 漏接来电分页搜索
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月20日 上午11:39:49
	 */
	List<CallFailed> queryCallFailedlistPage(Map<String, Object> map);
}