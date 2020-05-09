package com.vedeng.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.system.model.ParamsConfigValue;
import com.vedeng.system.model.vo.ParamsConfigVo;

public interface ParamsConfigValueMapper {
    int deleteByPrimaryKey(Integer paramsConfigValueId);

    int insert(ParamsConfigValue record);

    int insertSelective(ParamsConfigValue record);

    ParamsConfigValue selectByPrimaryKey(Integer paramsConfigValueId);

    int updateByPrimaryKeySelective(ParamsConfigValue record);

    int updateByPrimaryKey(ParamsConfigValue record);
	/**
	 * <b>Description:</b><br> 查询ParamsValue的集合
	 * @param paramsConfigValue
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午4:57:12
	 */
    List<Integer> getParamsValueList(ParamsConfigValue record);
    /**
     * <b>Description:</b><br> 删除参数设置
     * @param paramsConfigIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月4日 上午11:39:46
     */
    int delParamsConfigValue(@Param("paramsConfigIds")List<Integer> paramsConfigIds,@Param("companyId")Integer companyId);
    
	/**
	 * <b>Description:</b><br> 查询ParamsConfigValue
	 * @param paramsConfig
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月22日 下午4:57:12
	 */
    ParamsConfigValue getParamsValue(ParamsConfigVo record);

	User getQuoteConsultant(@Param("companyId")Integer companyId, @Param("paramsKey")Integer paramsKey);
}