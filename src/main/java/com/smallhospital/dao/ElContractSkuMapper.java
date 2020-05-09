package com.smallhospital.dao;

import com.smallhospital.model.ElContractSku;
import com.smallhospital.model.vo.ElContractSkuVO;

import javax.inject.Named;
import java.util.List;

@Named
public interface ElContractSkuMapper {

    int deleteByPrimaryKey(Integer elContractSkuId);

    int insert(ElContractSku record);

    int insertSelective(ElContractSku record);

    ElContractSku selectByPrimaryKey(Integer elContractSkuId);

    int updateByPrimaryKeySelective(ElContractSku record);

    int updateByPrimaryKey(ElContractSku record);


    List<ElContractSku> getElContractSkuBySkuId(List<Integer> skuList);

    List<ElContractSkuVO> findByContractId(Integer contractId);

    void batchAddContractSkus(List<ElContractSku> skuLists);
}