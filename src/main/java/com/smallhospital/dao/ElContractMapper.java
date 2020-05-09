package com.smallhospital.dao;

import com.smallhospital.model.ElContract;
import com.smallhospital.model.vo.ELContractVO;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public interface ElContractMapper {

    int deleteByPrimaryKey(Integer elContractId);

    int insert(ElContract record);

    int insertSelective(ElContract record);

    ELContractVO selectByPrimaryKey(Integer elContractId);

    int updateByPrimaryKeySelective(ELContractVO record);

    List<ELContractVO> querylistPage(Map<String, Object> map);

    List<Integer> findOtherValidSkus(ELContractVO contractVO);

    List<ELContractVO> findByTradeId(Integer traderId);

    List<Integer> findContractIdsByCusAndSkuIds(@Param(value = "traderId") Integer traderId, @Param(value = "skuIds") List<Integer> skuIds);
}