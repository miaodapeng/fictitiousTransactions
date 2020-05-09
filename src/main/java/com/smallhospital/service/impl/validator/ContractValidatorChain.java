package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;

import java.util.ArrayList;
import java.util.List;

public class ContractValidatorChain implements Validator<ELContractVO>{

    private List<Validator> validatorChains = new ArrayList<Validator>();

    public void add(Validator validator){
        validatorChains.add(validator);
    }

    @Override
    public ValidatorResult validator(ELContractVO contractVO) {

        if(validatorChains.size() == 0){
            return ValidatorResult.newBuild().setResult(true);
        }

        ValidatorResult validatorResult = ValidatorResult.newBuild().setResult(true);

        for(Validator validator : validatorChains){
            validatorResult = validator.validator(contractVO);
            if(validatorResult.getResult() == false){
                break;
            }
        }

        return validatorResult;
    }
}
