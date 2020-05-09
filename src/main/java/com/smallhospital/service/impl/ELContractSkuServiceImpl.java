package com.smallhospital.service.impl;

import com.smallhospital.dao.ElContractSkuMapper;
import com.smallhospital.model.ElContractSku;
import com.smallhospital.model.vo.ElContractSkuVO;
import com.smallhospital.service.ELContractSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医疗合同sku
 */
@Service
public class ELContractSkuServiceImpl implements ELContractSkuService {

    @Autowired
    private ElContractSkuMapper contractSkuMapper;

    @Override
    public List<ElContractSkuVO> findByContractId(Integer contractId) {
        return contractSkuMapper.findByContractId(contractId);
    }

    @Override
    public void batchAddContractSkus(List<ElContractSku> skuLists) {
        contractSkuMapper.batchAddContractSkus(skuLists);
    }

    @Override
    public void deleteById(Integer contactSkuId) {
        contractSkuMapper.deleteByPrimaryKey(contactSkuId);
    }

    @Override
    public ElContractSku findById(Integer contactSkuId) {
        return contractSkuMapper.selectByPrimaryKey(contactSkuId);
    }

    @Override
    public void modify(ElContractSku sku) {
        contractSkuMapper.updateByPrimaryKeySelective(sku);
    }
}
