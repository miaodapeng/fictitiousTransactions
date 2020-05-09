package com.smallhospital.service.impl.build;

import com.smallhospital.service.impl.chain.CreateOrderStep;
import com.smallhospital.service.impl.chain.CreateSaleOrderGoodsStep;
import com.smallhospital.service.impl.chain.CreateSaleOrderStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderStepBuild {

   @Autowired
   private CreateSaleOrderStep createSaleOrderStep;

   @Autowired
   private CreateSaleOrderGoodsStep createSaleOrderGoodsStep;

    /**
     * 组装创建订单的步骤
     * @return
     */
   public CreateOrderStep getCreateOrderStep(){
       createSaleOrderStep.setNext(createSaleOrderGoodsStep);
       return createSaleOrderStep;
   }

}
