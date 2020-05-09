package com.vedeng.system.dao;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.system.model.Read;

public interface ReadMapper {
    int deleteByPrimaryKey(Integer readId);

    int insert(Read record);

    int insertSelective(Read record);

    Read selectByPrimaryKey(Integer readId);

    int updateByPrimaryKeySelective(Read record);

    int updateByPrimaryKey(Read record);
    
	/**
	 * <b>Description:</b><br> 查询当前用户是否已点过产品检索
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月3日 上午11:05:48
	 */
    Integer getReadByUserId(User user);
    
	/**
	 * <b>Description:</b><br> 上架新品后删除所有已读标志
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年7月3日 上午11:30:13
	 */
    Integer delAllReadByCompanyId(@Param("companyId")Integer companyId);
}