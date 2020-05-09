package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.service.ELContractSkuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractHasProductValidator implements Validator<ELContractVO> {

    @Autowired
    private ELContractSkuService contractSkuService;

    @Override
    public ValidatorResult validator(ELContractVO contractVO) {

        ValidatorResult result = ValidatorResult.newBuild();

        if(!contractHasProduct(contractVO.getElContractId())){
            return result.setMessage("该合同中无产品信息，请添加产品信息后重试");
        }

        return result.setResult(true);
    }

    /**
     * 判断合同下是否有商品
     * @param contractId
     * @return
     */
    private boolean contractHasProduct(Integer contractId) {
        return CollectionUtils.isEmpty(this.contractSkuService.findByContractId(contractId))? false : true;
    }
}
