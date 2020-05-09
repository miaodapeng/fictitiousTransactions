package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import org.springframework.stereotype.Service;

/**
 * 合同是否生效的校验器
 */
@Service
public class ContractEffectiveValidator implements Validator<ELContractVO> {

    @Override
    public ValidatorResult validator(ELContractVO contractVO) {

        ValidatorResult result = ValidatorResult.newBuild();

        if(contractVO.getEffctiveStatus() == 0){
            return result.setMessage("未生效的合同不能同步");
        }

        return result.setResult(true);

    }
}
