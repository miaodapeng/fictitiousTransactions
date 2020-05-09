package com.vedeng.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vedeng.system.model.ParamsConfig;
import com.vedeng.system.model.vo.ParamsConfigVo;

public interface ParamsConfigMapper {
    int deleteByPrimaryKey(Integer paramsConfigId);

    int insert(ParamsConfig record);

    int insertSelective(ParamsConfig record);

    ParamsConfig selectByPrimaryKey(Integer paramsConfigId);

    int updateByPrimaryKeySelective(ParamsConfig record);

    int updateByPrimaryKey(ParamsConfig record);
    
    /**
     * <b>Description:</b><br> 根据paramsKey查询参数设置
     * @param paramsKey
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月4日 上午10:21:39
     */
    ParamsConfigVo getParamsConfigVoByParamsKey(ParamsConfigVo paramsConfigVo);
    
    /**
     * <b>Description:</b><br> 根据paramsKey查询参数设置
     * @param paramsKey
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月4日 上午10:21:39
     */
    List<ParamsConfigVo> getParamsConfigVoList(ParamsConfigVo paramsConfigVo);
    
    /**
     * <b>Description:</b><br> 删除参数设置
     * @param paramsConfigIds
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年12月4日 上午11:39:46
     */
    int delParamsConfig(@Param("paramsKey")Integer paramsKey);
}