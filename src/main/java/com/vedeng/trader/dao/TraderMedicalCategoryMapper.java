package com.vedeng.trader.dao;



import com.vedeng.trader.model.TraderMedicalCategory;
import com.vedeng.trader.model.vo.TraderMedicalCategoryVo;

import java.util.List;

public interface TraderMedicalCategoryMapper {

    /**
     * <b>Description:</b><br> 查询列表
     * @param record
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月6日 上午11:04:07
     */
    List<TraderMedicalCategoryVo> getTraderMedicalCategoryList(TraderMedicalCategory record);

    /**
     * <b>Description:</b>不连字典表查询客户医疗资质<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/19
     */
    List<TraderMedicalCategory> getMedicalCategoryList(TraderMedicalCategory record);

}