package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import org.springframework.stereotype.Service;

/**
 * 合同产品是否同步的校验器
 */
@Service
public class ContractProductSynValidator implements Validator<ELContractVO> {

    @Override
    public ValidatorResult validator(ELContractVO contractVO) {

        ValidatorResult result = ValidatorResult.newBuild();

        if(contractVO.getProductSynStatus() != 1){
            return result.setMessage("请先同步产品，再同步合同");
        }

        return result.setResult(true);
    }
}
