package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 简易的校验器的链式构造器类
 */
public class ContractValidatorChainBuild{

    private ContractValidatorChain contractValidatorChain = new ContractValidatorChain();

    public static ContractValidatorChainBuild newBuild(){
        ContractValidatorChainBuild build = new ContractValidatorChainBuild();
        return build;
    }

    public ContractValidatorChain create(){
        return contractValidatorChain;
    }

    public ContractValidatorChainBuild setContractEffectiveValidator(ContractEffectiveValidator contractEffectiveValidator){
        contractValidatorChain.add(contractEffectiveValidator);
        return this;
    }

    public ContractValidatorChainBuild setContractProductSynValidator(ContractProductSynValidator contractProductSynValidator){
        contractValidatorChain.add(contractProductSynValidator);
        return this;
    }

    public ContractValidatorChainBuild setContractHasProductValidator(ContractHasProductValidator contractHasProductValidator){
        contractValidatorChain.add(contractHasProductValidator);
        return this;
    }

    public ContractValidatorChainBuild setContractProductNumOverFlowValidator(ContractProductNumOverFlowValidator cContractProductNumOverFlowValidator){
        contractValidatorChain.add(cContractProductNumOverFlowValidator);
        return this;
    }

    public ContractValidatorChainBuild setContractSkuOverlapValidator(ContractSkuOverlapValidator contractSkuOverlapValidator){
        contractValidatorChain.add(contractSkuOverlapValidator);
        return this;
    }
}
