package com.smallhospital.service.impl.visitor;

import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.service.impl.CreateSaleOrderContext;
import com.smallhospital.service.impl.build.SaleorderGenerateBuild;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.order.model.SaleorderGenerate;
import com.vedeng.system.dao.TraderAddressGenerateMapper;
import com.vedeng.system.model.TraderAddressGenerate;
import com.vedeng.system.model.TraderAddressGenerateExample;
import com.vedeng.system.service.RegionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理订单的地址信息
 */
@Service
public class CreateOrderAddressInfoVisitor implements ELSaleOrderBuildVisitor {

    @Autowired
    @Qualifier("traderAddressGenerateMapper")
    private TraderAddressGenerateMapper traderAddressGenerateMapper;

    @Autowired
    private RegionService regionService;

    @Override
    public void accept(SaleorderGenerateBuild saleOrderBuild) throws Exception{

        ELOrderDto orderDto = (ELOrderDto) CreateSaleOrderContext.get("orderDto");
        SaleorderGenerate saleorder = saleOrderBuild.getSaleOrder();
        Integer traderId = orderDto.getPurchaserId();

        TraderAddressGenerate takeAddress = null;
        TraderAddressGenerate invoiceAddress = null;

        TraderAddressGenerateExample takeExample = new TraderAddressGenerateExample();

        takeExample.createCriteria()
                .andTraderIdEqualTo(saleorder.getTraderId())
                .andIsEnableEqualTo(CommonConstants.ENABLE);

        List<TraderAddressGenerate> addressGenerate = traderAddressGenerateMapper.selectByExample(takeExample);
        if (CollectionUtils.isEmpty(addressGenerate)) {
            return;
        }
        if (addressGenerate.size() == 1) {
            takeAddress = addressGenerate.get(0);
            invoiceAddress = addressGenerate.get(0);
        } else if (addressGenerate.size() >= 2) {

            for (TraderAddressGenerate generate : addressGenerate) {
                if (takeAddress == null
                        && StringUtils.equals(generate.getComments(), CommonConstants.TAKE_ADDRESS_COMMENT)) {
                    takeAddress = generate;
                    continue;
                }
                if (invoiceAddress == null
                        && StringUtils.equals(generate.getComments(), CommonConstants.INVOICE_ADDRESS_COMMENT)) {
                    invoiceAddress = generate;
                }
            }
            if (takeAddress == null) {
                takeAddress = addressGenerate.get(0);
            }
            if (invoiceAddress == null) {
                invoiceAddress = addressGenerate.get(0);
            }
        }

        String invoiceAddr = regionService.getRegionNameStringByMinRegionIds(invoiceAddress.getAreaIds());
        String takeAddr = regionService.getRegionNameStringByMinRegionIds(takeAddress.getAreaIds());

        saleorder.setInvoiceTraderAddress(invoiceAddress.getAddress());
        saleorder.setInvoiceTraderAreaId(invoiceAddress.getAreaId());
        saleorder.setInvoiceTraderArea(invoiceAddr);
        saleorder.setInvoiceTraderAddressId(invoiceAddress.getTraderAddressId());

        saleorder.setTakeTraderAddress(takeAddress.getAddress());
        saleorder.setTakeTraderAreaId(takeAddress.getAreaId());
        saleorder.setTakeTraderArea(takeAddr);
        saleorder.setTakeTraderAddressId(takeAddress.getTraderAddressId());
        // 订单跟发票地址保持一致
        saleorder.setTraderAddress(invoiceAddress.getAddress());
        saleorder.setTraderAreaId(invoiceAddress.getAreaId());
        saleorder.setTraderArea(invoiceAddr);
        saleorder.setTraderAddressId(invoiceAddress.getTraderAddressId());
    }
}
