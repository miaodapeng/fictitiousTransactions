package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.UserLoginLog;

/**
 * <b>Description:</b><br> 用户登录日志mapper
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> UserLoginLogMapper
 * <br><b>Date:</b> 2017年4月26日 下午4:27:15
 */
public interface UserLoginLogMapper {
	/**
	 * <b>Description:</b><br> 分页查询用户登录日志
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月26日 下午4:27:51
	 */
	List<UserLoginLog> querylistPage(Map<String, Object> map);
	
	/**
	 * <b>Description:</b><br> 根据主键删除
	 * @param userLoginLogId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年4月26日 下午4:28:36
	 */
    int deleteByPrimaryKey(Integer userLoginLogId);

    /**
     * <b>Description:</b><br> 新增（不判断字段是否有值）
     * @param record
     * @return
     * @Note
     * <b>Author:</b> leo.yang
     * <br><b>Date:</b> 2017年4月26日 下午4:29:09
     */
    int insert(UserLoginLog record);

    /**
     * <b>Description:</b><br> 新增（判断字段是否有值）
     * @param record
     * @return
     * @Note
     * <b>Author:</b> leo.yang
     * <br><b>Date:</b> 2017年4月26日 下午4:29:24
     */
    int insertSelective(UserLoginLog record);

    /**
     * <b>Description:</b><br> 根据主键查询
     * @param userLoginLogId
     * @return
     * @Note
     * <b>Author:</b> leo.yang
     * <br><b>Date:</b> 2017年4月26日 下午4:30:29
     */
    UserLoginLog selectByPrimaryKey(Integer userLoginLogId);

    /**
     * <b>Description:</b><br> 更新（判断字段是否有值）
     * @param record
     * @return
     * @Note
     * <b>Author:</b> leo.yang
     * <br><b>Date:</b> 2017年4月26日 下午4:30:48
     */
    int updateByPrimaryKeySelective(UserLoginLog record);

    /**
     * <b>Description:</b><br> 更新（不判断字段是否有值）
     * @param record
     * @return
     * @Note
     * <b>Author:</b> leo.yang
     * <br><b>Date:</b> 2017年4月26日 下午4:31:07
     */
    int updateByPrimaryKey(UserLoginLog record);
    
    /**
     * 
     * <b>Description: 根据userId查询用户登录情况</b><br> 
     * @param record
     * @return
     * <b>Author: Franlin</b>  
     * <br><b>Date: 2018年7月31日 上午11:01:18 </b>
     */
    UserLoginLog queryUserLogOrOutInfo(UserLoginLog record);
}