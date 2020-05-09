package com.vedeng.homepage.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.homepage.model.SalesGoalSetting;
import com.vedeng.homepage.model.vo.SaleEngineerDataVo;

public interface SalesGoalSettingMapper {
    int deleteByPrimaryKey(Integer salesGoalSettingId);

    int insert(SalesGoalSetting record);

    int insertSelective(SalesGoalSetting record);

    SalesGoalSetting selectByPrimaryKey(Integer salesGoalSettingId);

    int updateByPrimaryKeySelective(SalesGoalSetting record);

    int updateByPrimaryKey(SalesGoalSetting record);
    
    /**
     * <b>Description:</b><br> 获取部门的销售目标
     * @param salesGoalSetting
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月22日 下午4:06:01
     */
    List<SalesGoalSetting> getSalesGoalSettingList(SalesGoalSetting salesGoalSetting);
    
    /**
     * <b>Description:</b><br> 获取当前销售工程师的本月目标
     * @param salesGoalSetting
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月22日 下午4:06:01
     */
    BigDecimal getSalesEngineerGoal(SalesGoalSetting salesGoalSetting);
    
    /**
     * <b>Description:</b><br> 获取当前年月份合计目标金额
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月24日 下午2:14:57
     */
    List<BigDecimal> getTotalMonthList(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 获取下一级部门的销售目标
     * @param saleEngineerDataVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月22日 下午4:06:01
     */
    List<SalesGoalSetting> getNextDeptSalesGoalSettingList(SaleEngineerDataVo saleEngineerDataVo);
}