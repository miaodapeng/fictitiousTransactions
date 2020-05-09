package com.smallhospital.service.impl;

import com.smallhospital.dao.ElContractMapper;
import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.dto.ELOrderItemDto;
import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.service.ELContractService;
import com.vedeng.common.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ELContractServiceImpl implements ELContractService {

    @Autowired
    private ElContractMapper contractMapper;

    @Override
    public List<ELContractVO> querylistPage(ELContractVO contract, Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contract", contract);
        map.put("page", page);
        return contractMapper.querylistPage(map);
    }

    @Override
    public int saveContractInfo(ELContractVO contract) {
        if(contract == null){
            return 0;
        }
        contract.setAddTime(System.currentTimeMillis());
        contract.setUpdateTime(System.currentTimeMillis());
        this.contractMapper.insertSelective(contract);
        return contract.getElContractId();
    }

    /**
     * 校验合同的有效期
     * @param orderDto
     * @return
     */
    @Override
    public ValidatorResult validatorContractExpire(ELOrderDto orderDto) {

        //客户id
        Integer traderId = orderDto.getPurchaserId();

        //订购商品的sku列表
        List<Integer> skuIds = orderDto.getDDMX().stream().map(ELOrderItemDto::getProductId).collect(Collectors.toList());

        List<Integer> contractIds = contractMapper.findContractIdsByCusAndSkuIds(traderId,skuIds);

        ValidatorResult result = null;

        for(Integer contractId : contractIds){

            ELContractVO contractVO = this.contractMapper.selectByPrimaryKey(contractId);

            result = contractValidator(contractVO);

            if(!result.getResult()){
                break;
            }
        }

        return result;

    }

    /**
     * 校验合同是否过期
     * @param contract
     * @return
     */
    private ValidatorResult contractValidator(ELContractVO contract) {

        if(contract.getEffctiveStatus() == 0){
            return ValidatorResult.newBuild().setMessage("合同id="+contract.getElContractId()+"已经失效");
        }

        if(!(contract.getContractValidityDateStart() <= System.currentTimeMillis() && System.currentTimeMillis() <= contract.getContractValidityDateEnd())){
            return ValidatorResult.newBuild().setMessage("合同id="+contract.getElContractId()+"已经过了有效期");
        }

        return ValidatorResult.newBuild().setResult(true);
    }


    @Override
    public ELContractVO findById(Integer contractId) {
        return contractMapper.selectByPrimaryKey(contractId);
    }

    @Override
    public void updateContract(ELContractVO contract) {
        contractMapper.updateByPrimaryKeySelective(contract);
    }

    @Override
    public List<Integer> findOtherValidSkus(ELContractVO contractVO) {
        return contractMapper.findOtherValidSkus(contractVO);
    }

    @Override
    public List<ELContractVO> findByTradeId(Integer traderId) {
        return contractMapper.findByTradeId(traderId);
    }
}
