package com.smallhospital.service.impl.visitor;

import com.smallhospital.dto.ELOrderDto;
import com.smallhospital.dto.ELOrderItemDto;
import com.smallhospital.dto.ELSkuDetailInfo;
import com.smallhospital.service.impl.CreateSaleOrderContext;
import com.smallhospital.service.impl.build.SaleorderGenerateBuild;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.money.OrderMoneyUtil;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.OrderNoDict;
import com.vedeng.order.model.SaleorderGenerate;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.TraderCustomerMapper;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 处理订单的基本信息
 */
@Service
public class CreteOrderBasicInfoVisitor implements ELSaleOrderBuildVisitor {

    @Autowired
    @Qualifier("traderCustomerMapper")
    private TraderCustomerMapper traderCustomerMapper;

    @Autowired
    @Qualifier("traderMapper")
    private TraderMapper traderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("saleorderService")
    protected SaleorderService saleorderService;

    @Override
    public void accept(SaleorderGenerateBuild saleOrderBuild) throws Exception{

        ELOrderDto orderDto = (ELOrderDto) CreateSaleOrderContext.get("orderDto");

        SaleorderGenerate saleorder = saleOrderBuild.getSaleOrder();

        saleorder.setElSaleordreNo(orderDto.getOrderNumber());

        Integer traderId = orderDto.getPurchaserId();
        saleorder.setTraderId(traderId);

        // 根据traderId查询所属客户
        TraderCustomer traderCustomer = traderCustomerMapper.getCustomerInfo(traderId);
        if (traderCustomer == null) {
            throw new Exception("未能匹配到客户信息：traderId：" + saleorder.getTraderId());
        }

        saleorder.setCustomerType(traderCustomer.getCustomerType());// 客户类型
        saleorder.setCustomerNature(traderCustomer.getCustomerNature());// 客户性质

        Trader trader = traderMapper.getTraderInfoByTraderId(traderId);
        saleorder.setTraderName(trader.getTraderName());
        //订单类型
        saleorder.setOrderType(OrderConstant.ORDER_TYPE_SALE);
        saleorder.setCompanyId(CommonConstants.COMPANY_ID_1);
        saleorder.setSource(OrderConstant.ORDER_SOURCE_EL_4);
        saleorder.setStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setInvoiceStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setPaymentStatus(OrderConstant.ORDER_STATUS_INIT0);
        saleorder.setDeliveryStatus(OrderConstant.ORDER_STATUS_INIT0);

        //物流备注信息 存放的小医院修改后的订单信息
        saleorder.setLogisticsComments(orderDto.getRemark());

        BigDecimal totalAmout = BigDecimal.ZERO;
        for(ELOrderItemDto orderItem : orderDto.getDDMX()){
            totalAmout = totalAmout.add(orderItem.getPurPrice().multiply(new BigDecimal(orderItem.getOrderQuantity()))) ;
        }

        saleorder.setTotalAmount(totalAmout);

        // 1客户，2供应商
        Optional.ofNullable(userService.getUserByTraderId(traderId, 1))
                .map(User::getUserId)
                .map(useId ->{
                    return userService.getUserById(useId);
                }).ifPresent(user -> {
                    saleorder.setOrgId(user.getOrgId());
                    saleorder.setOrgName(user.getOrgName());
                    saleorder.setUserId(user.getUserId());
                });

        // 发票类型
        saleorder.setInvoiceType(972);
        // 发送方式
        saleorder.setInvoiceMethod(new Byte("1"));
        saleorder.setComments(orderDto.getRemark());
        saleorder.setAddTime(DateUtil.gainNowDate());
    }
}
