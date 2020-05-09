package com.smallhospital.dao;

import com.smallhospital.model.ElContract;
import com.smallhospital.model.ElTrader;
import com.smallhospital.model.vo.ElTraderVo;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public interface ElTraderMapper {

    int deleteByPrimaryKey(Integer elTraderId);

    int insert(ElTrader record);

    int insertSelective(ElTrader record);

    ElTrader selectByPrimaryKey(Integer elTraderId);

    int updateByPrimaryKeySelective(ElTrader record);

    List<ElTraderVo> querylistPage(Map<String, Object> map);

    ElTraderVo getElTraderByTraderId(int traderId);

    List<ElTraderVo> findCustomerByLoginId(Integer userId);

    /**
     * 获取物流单号的物流公司代码号
     * @param logisticsNo 物流单号
     * @return 物流公司代码号，比如：shunfeng
     */
    String getExpressByLogisticsNo(String logisticsNo);
}