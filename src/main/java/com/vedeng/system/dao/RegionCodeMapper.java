package com.vedeng.system.dao;

import com.vedeng.system.model.RegionCode;

public interface RegionCodeMapper {
    int deleteByPrimaryKey(Integer regionCodeId);

    int insert(RegionCode record);

    int insertSelective(RegionCode record);

    RegionCode selectByPrimaryKey(Integer regionCodeId);

    int updateByPrimaryKeySelective(RegionCode record);

    int updateByPrimaryKey(RegionCode record);
    /**
     * 
     * <b>Description:</b><br> 根据省市查询编码
     * @param r
     * @return
     * @Note
     * <b>Author:</b> scott
     * <br><b>Date:</b> 2018年5月16日 上午11:29:39
     */
	RegionCode selectRegionCodeInfo(RegionCode r);
}