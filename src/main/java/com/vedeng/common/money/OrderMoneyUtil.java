package com.vedeng.common.money;

import java.math.BigDecimal;
import java.util.Map;

public class OrderMoneyUtil {

    public static BigDecimal getPaymentAmount(Map<String,BigDecimal> map){

        if(map==null){
            return new BigDecimal(0.00);
        }
        BigDecimal payment=new BigDecimal(0.00);
        BigDecimal payAmount=map.get("paymentAmount");
        BigDecimal periodAmount=map.get("periodAmount");
        BigDecimal lackAccountPeriodAmount=map.get("lackAccountPeriodAmount");
        BigDecimal refundBalanceAmount=map.get("refundBalanceAmount");
        if(payAmount!=null)
           payment= payment.add(payAmount);
        if(periodAmount!=null)
           payment= payment.add(periodAmount);
        if(lackAccountPeriodAmount!=null)
            payment=payment.subtract(lackAccountPeriodAmount);
        if(refundBalanceAmount!=null)
           payment= payment.subtract(refundBalanceAmount);
        return payment;
    }
}
