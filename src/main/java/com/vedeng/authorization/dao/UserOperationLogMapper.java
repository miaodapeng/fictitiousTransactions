package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.UserOperationLog;

/**
 * <b>Description:</b><br> 用户操作日志mapper
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> UserOperationLogMapper
 * <br><b>Date:</b> 2017年4月26日 下午4:33:52
 */
public interface UserOperationLogMapper {
	/**
	 * <b>Description:</b><br> 分页查询
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月26日 下午4:34:10
	 */
	List<UserOperationLog> querylistPage(Map<String, Object> map);
	
    int deleteByPrimaryKey(Integer userOperationLogId);

    int insert(UserOperationLog record);

    int insertSelective(UserOperationLog record);

    UserOperationLog selectByPrimaryKey(Integer userOperationLogId);

    int updateByPrimaryKeySelective(UserOperationLog record);

    int updateByPrimaryKey(UserOperationLog record);
}