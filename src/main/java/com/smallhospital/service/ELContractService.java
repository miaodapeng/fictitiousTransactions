package com.smallhospital.service;

import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.dto.ValidatorResult;
import com.smallhospital.model.vo.ELContractVO;
import com.vedeng.common.page.Page;

import java.util.List;

public interface ELContractService {

    List<ELContractVO> querylistPage(ELContractVO contract, Page page);

    int saveContractInfo(ELContractVO contract);

    ELContractVO findById(Integer contractId);

    void updateContract(ELContractVO contract);

    List<Integer> findOtherValidSkus(ELContractVO contractVO);

    List<ELContractVO> findByTradeId(Integer traderId);

    /**
     * 校验合同有效期
     * @param orderDto
     * @return
     */
    ValidatorResult validatorContractExpire(ELOrderDto orderDto);
}
