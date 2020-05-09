package com.vedeng.authorization.dao;

import javax.inject.Named;

import com.vedeng.authorization.model.RUserPosit;

/**
 * <b>Description:</b><br> 用户职位Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> RUserPositMapper
 * <br><b>Date:</b> 2017年4月25日 上午9:57:38
 */
@Named("rUserPositMapper")
public interface RUserPositMapper {
	
    /**
     * <b>Description:</b><br> 删除员工职位 
     * @param userId 员工ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:58:42
     */
    int deleteByUserId(Integer userId);
    
    /**
     * <b>Description:</b><br> 添加员工职位
     * @param rUserPosit 员工职位关系bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:59:23
     */
    int insert(RUserPosit rUserPosit );
}