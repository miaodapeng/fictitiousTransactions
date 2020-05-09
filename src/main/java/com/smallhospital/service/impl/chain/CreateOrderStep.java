package com.smallhospital.service.impl.chain;

import com.smallhospital.dto.ELOrderDto;

public interface CreateOrderStep {

    public void dealWith(ELOrderDto orderDto) throws Exception;

}
