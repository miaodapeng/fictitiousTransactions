package com.smallhospital.service.impl.chain;

import com.smallhospital.dto.ELOrderDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class AbstractCreateOrderStep implements CreateOrderStep {

    private CreateOrderStep next;

    public CreateOrderStep getNext() {
        return next;
    }

    public void setNext(CreateOrderStep next) {
        this.next = next;
    }

    @Override
    @Transactional
    public void dealWith(ELOrderDto orderDto) throws Exception{

        //处理订单
        this.doDealWith(orderDto);

        if(next != null){
            next.dealWith(orderDto);
        }
    }

    protected abstract void doDealWith(ELOrderDto orderDto) throws Exception;
}
