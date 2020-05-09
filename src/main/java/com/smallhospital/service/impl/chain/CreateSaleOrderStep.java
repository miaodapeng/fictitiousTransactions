package com.smallhospital.service.impl.chain;

import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.service.impl.CreateSaleOrderContext;
import com.smallhospital.service.impl.build.SaleorderGenerateBuild;
import com.smallhospital.service.impl.visitor.CreateOrderAddressInfoVisitor;
import com.smallhospital.service.impl.visitor.CreateOrderContactVistor;
import com.smallhospital.service.impl.visitor.CreteOrderBasicInfoVisitor;
import com.vedeng.common.util.OrderNoDict;
import com.vedeng.order.dao.SaleorderGenerateMapper;
import com.vedeng.order.model.SaleorderGenerate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * 创建销售单
 */
@Service
public class CreateSaleOrderStep extends AbstractCreateOrderStep {

    @Autowired
    @Qualifier("saleorderGenerateMapper")
    private SaleorderGenerateMapper saleorderGenerateMapper;

    @Autowired
    private CreteOrderBasicInfoVisitor creteOrderBasicInfoVisitor;

    @Autowired
    private CreateOrderAddressInfoVisitor createOrderAddressInfoVisitor;

    @Autowired
    private CreateOrderContactVistor createOrderContactVistor;

    @Override
    protected void doDealWith(ELOrderDto orderDto) throws Exception {

        //设置全局的dto对象，后面随时可以去
        CreateSaleOrderContext.put("orderDto",orderDto);

        SaleorderGenerate saleorder = SaleorderGenerateBuild.newBuild()
                .setBasicInfO(creteOrderBasicInfoVisitor)
                .setAddressInfo(createOrderAddressInfoVisitor)
                .setContractInfo(createOrderContactVistor)
                .create();

        // 插入订单
        saleorderGenerateMapper.insertSelective(saleorder);

        //修改订单号
        updateOrderNo(saleorder);

        //设置订单id
        CreateSaleOrderContext.put("saleorderId",saleorder.getSaleorderId());
    }

    /**
     * 更新订单号
     * @param saleorder
     */
    private void updateOrderNo(SaleorderGenerate saleorder) {

        SaleorderGenerate generateOrderNoUpdate = new SaleorderGenerate();
        generateOrderNoUpdate.setSaleorderId(saleorder.getSaleorderId());
        //OrderNoDict.getOrderNum(saleorder.getSaleorderId(), OrderNoDict.EL_OUT_TYPE)
        generateOrderNoUpdate
                .setSaleorderNo(saleorder.getElSaleordreNo());
        saleorderGenerateMapper.updateByPrimaryKeySelective(generateOrderNoUpdate);
    }

}
