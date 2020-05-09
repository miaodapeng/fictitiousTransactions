package com.smallhospital.service.impl.validator;

import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import com.smallhospital.model.vo.ElContractSkuVO;
import com.smallhospital.service.ELContractService;
import com.smallhospital.service.ELContractSkuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractSkuOverlapValidator implements Validator<ELContractVO> {

    @Autowired
    private ELContractService contractService;

    @Autowired
    private ELContractSkuService contractSkuService;

    @Override
    public ValidatorResult validator(ELContractVO contractVO) {

        ValidatorResult result = ValidatorResult.newBuild();

        if(!validContract(contractVO.getElContractId())){
            return result.setMessage("该合同的SKU与已有合同中的SKU重复，请检查");
        }

        return result.setResult(true);
    }

    /**
     * 是否有效合同
     * @param contractId
     * @return
     */
    private boolean validContract(Integer contractId) {

        //查询当前客户其他有效合同中的所有sku,保证sku不重叠
        ELContractVO contractVO = this.contractService.findById(contractId);

        List<Integer> existSkuIds = this.contractService.findOtherValidSkus(contractVO);
        if(CollectionUtils.isEmpty(existSkuIds)){
            return true;
        }

        List<Integer> contratSkus = this.contractSkuService.findByContractId(contractId).stream()
                .map(ElContractSkuVO:: getSkuId).collect(Collectors.toList());

        existSkuIds.retainAll(contratSkus);

        return existSkuIds.size() > 0 ? false : true;
    }

}
