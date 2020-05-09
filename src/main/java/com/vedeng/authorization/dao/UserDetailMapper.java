package com.vedeng.authorization.dao;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.UserDetail;

/**
 * <b>Description:</b><br> 员工详情Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> UserDetailMapper
 * <br><b>Date:</b> 2017年4月25日 上午10:53:48
 */
public interface UserDetailMapper {
    /**
     * <b>Description:</b><br> 添加员工详情
     * @param userDetail 员工详情bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:54:07
     */
    int insert(UserDetail userDetail);

    /**
     * <b>Description:</b><br> 编辑员工详情 
     * @param userDetail 员工详情bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:54:31
     */
    int update(UserDetail userDetail);
    
    /**
     * <b>Description:</b><br> 查询员工详情 
     * @param userId 员工ID
     * @return UserDetail
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:54:48
     */
    UserDetail getUserDetail(Integer userId);

	/**
	 * <b>Description:</b><br> 根据客户获取用户详细信息
	 * @param traderId
	 * @param type
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月23日 下午3:07:25
	 */
	UserDetail getUserDetailByTraderId(@Param("traderId")Integer traderId, @Param("type")Integer type);

}