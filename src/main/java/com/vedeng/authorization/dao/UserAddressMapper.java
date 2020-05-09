package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.UserAddress;

/**
 * <b>Description:</b><br> 员工地址Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> UserAddressMapper
 * <br><b>Date:</b> 2017年4月25日 上午10:04:39
 */
public interface UserAddressMapper {
    /**
     * <b>Description:</b><br> 添加员工地址
     * @param userAddress 员工地址bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:05:01
     */
    int insert(UserAddress userAddress);
    
    /**
     * <b>Description:</b><br> 编辑员工地址
     * @param userAddress 员工地址bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:05:39
     */
    int update(UserAddress userAddress);

    /**
     * <b>Description:</b><br> 查询员工地址 
     * @param userId 员工ID
     * @return UserAddress
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:53:24
     */
    UserAddress getUserAddress(Integer userId);
}