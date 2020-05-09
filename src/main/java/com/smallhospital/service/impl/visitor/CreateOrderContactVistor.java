package com.smallhospital.service.impl.visitor;

import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.service.impl.CreateSaleOrderContext;
import com.smallhospital.service.impl.build.SaleorderGenerateBuild;
import com.vedeng.order.model.SaleorderGenerate;
import com.vedeng.trader.dao.TraderContactGenerateMapper;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.TraderContactGenerateExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理订单的联系人信息
 */
@Service
public class CreateOrderContactVistor implements ELSaleOrderBuildVisitor {

    @Autowired
    @Qualifier("traderContactGenerateMapper")
    private TraderContactGenerateMapper traderContactGenerateMapper;

    @Override
    public void accept(SaleorderGenerateBuild saleOrderBuild) throws Exception {

        ELOrderDto orderDto = (ELOrderDto) CreateSaleOrderContext.get("orderDto");
        SaleorderGenerate saleOrder = saleOrderBuild.getSaleOrder();
        Integer traderId = orderDto.getPurchaserId();

        // 初始化订单的发票联系人
        TraderContactGenerate invoiceContact = getContactByMobileAndName(traderId);

        // 发票
        saleOrder.setInvoiceTraderContactMobile(invoiceContact.getMobile());
        saleOrder.setInvoiceTraderContactTelephone(invoiceContact.getTelephone());
        saleOrder.setInvoiceTraderContactId(invoiceContact.getTraderContactId());
        saleOrder.setInvoiceTraderContactName(invoiceContact.getName());
        saleOrder.setInvoiceTraderName(saleOrder.getTraderName());
        saleOrder.setInvoiceTraderId(traderId);

        // 订单跟发票联系人保持一直
        saleOrder.setTraderContactId(invoiceContact.getTraderContactId());
        saleOrder.setTraderContactMobile(invoiceContact.getMobile());
        saleOrder.setTraderContactName(invoiceContact.getName());
        saleOrder.setTraderContactTelephone(invoiceContact.getTelephone());

        // 初始化订单的收件人
        TraderContactGenerate takeContact = getContactByMobileAndName(traderId);
        saleOrder.setTakeTraderId(traderId);
        saleOrder.setTakeTraderName(saleOrder.getTraderName());
        saleOrder.setTakeTraderContactId(takeContact.getTraderContactId());
        saleOrder.setTakeTraderContactMobile(takeContact.getMobile());
        saleOrder.setTakeTraderContactTelephone(takeContact.getTelephone());
        saleOrder.setTakeTraderContactName(takeContact.getName());
    }

    private TraderContactGenerate getContactByMobileAndName(Integer traderId) {
        // 联系人
        TraderContactGenerateExample contactExample = new TraderContactGenerateExample();
        contactExample.createCriteria().andTraderIdEqualTo(traderId);
        List<TraderContactGenerate> list = traderContactGenerateMapper.selectByExample(contactExample);
        if (CollectionUtils.isEmpty(list)) {
            return new TraderContactGenerate();
        } else {
            return list.get(0);
        }
    }
}
