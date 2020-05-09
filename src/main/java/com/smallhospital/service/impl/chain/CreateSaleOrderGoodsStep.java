package com.smallhospital.service.impl.chain;

import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.dto.ELOrderItemDto;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.service.ElSkuService;
import com.smallhospital.service.impl.CreateSaleOrderContext;
import com.vedeng.goods.dao.CoreSkuGenerateMapper;
import com.vedeng.goods.model.CoreSkuGenerate;
import com.vedeng.order.dao.SaleorderGoodsMapper;
import com.vedeng.order.model.SaleorderGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建销售单的商品
 */
@Service
public class CreateSaleOrderGoodsStep extends AbstractCreateOrderStep {

    @Autowired
    @Qualifier("saleorderGoodsMapper")
    private SaleorderGoodsMapper saleorderGoodsMapper;

    @Autowired
    @Qualifier("coreSkuGenerateMapper")
    private CoreSkuGenerateMapper coreSkuGenerateMapper;

    @Autowired
    private ElSkuService skuService;

    @Override
    protected void doDealWith(ELOrderDto orderDto) {

        //构造销售单的商品
        List<SaleorderGoods> orderList = buildOrderList(orderDto);

        //批量新增商品
        saleorderGoodsMapper.saveBatchAddSaleGoods(orderList);

    }

    private List<SaleorderGoods> buildOrderList(ELOrderDto orderDto) {
        //销售单的id
        Integer saleorderId = (Integer) CreateSaleOrderContext.get("saleorderId");

        List<SaleorderGoods> orderList = new ArrayList<>();

        List<ELOrderItemDto> orderItems = orderDto.getDDMX();

        orderItems.forEach(orderItem->{

            ELSkuDetailInfo skuDetail = skuService.findDetailSkuInfo(orderItem.getProductId());

            SaleorderGoods orderGood = new SaleorderGoods();
            orderGood.setSaleorderId(saleorderId);
            orderGood.setElOrderlistId(Integer.valueOf(orderItem.getOrderListId()));
            orderGood.setGoodsId(orderItem.getProductId());
            orderGood.setGoodsName(skuDetail.getProductName());
            orderGood.setBrandName(skuDetail.getBrand());
            orderGood.setModel(skuDetail.getSpec());
            orderGood.setUnitName(skuDetail.getUnit());
            orderGood.setMaxSkuRefundAmount(orderItem.getPurPrice().multiply(new BigDecimal(orderItem.getOrderQuantity())));
            orderGood.setSku(skuDetail.getProductCode());
            orderGood.setNum(orderItem.getOrderQuantity());
            orderGood.setRealPrice(orderItem.getPurPrice());
            orderGood.setPrice(orderItem.getPurPrice());
            orderGood.setDeliveryCycle("14天");

            orderList.add(orderGood);
        });

        return orderList;
    }
}
