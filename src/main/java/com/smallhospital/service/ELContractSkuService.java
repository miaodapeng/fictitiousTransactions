package com.smallhospital.service;

import com.smallhospital.model.ElContractSku;
import com.smallhospital.model.vo.ElContractSkuVO;

import java.util.List;

public interface ELContractSkuService {

    List<ElContractSkuVO> findByContractId(Integer contractId);

    void batchAddContractSkus(List<ElContractSku> skuLists);

    void deleteById(Integer contactSkuId);

    ElContractSku findById(Integer contactSkuId);

    void modify(ElContractSku sku);
}
