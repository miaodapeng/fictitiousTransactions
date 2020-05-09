package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.service.ELContractSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractProductNumOverFlowValidator implements Validator<ELContractVO> {

    @Autowired
    private ELContractSkuService contractSkuService;

    @Override
    public ValidatorResult validator(ELContractVO contractVO) {

        ValidatorResult result = ValidatorResult.newBuild();

        if(productNumOverflow(contractVO.getElContractId())){
            return result.setMessage("合同中的产品数量超过1000，请重试");
        }

        return result.setResult(true);
    }

    /**
     * 合同产品数量是否超标
     * @return
     */
    public boolean productNumOverflow(Integer contractId){
        return this.contractSkuService.findByContractId(contractId).size() > 1000? true:false;
    }
}
