package com.vedeng.finance.dao;

import com.vedeng.finance.model.Invoice;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;

@Named("invoiceMapper")
public interface InvoiceMapper {

    List<Invoice> getInvoiceListByInvoiceIdList(@Param("invoiceIdList") List<Integer> invoiceIdList);
    /**
    *获取当前该订单审核中的申请开票id
    * @Author:strange
    * @Date:10:33 2019-11-23
    */
    List<Integer> getInvoiceApllyNum(Integer saleorderId);


    BigDecimal getSaleOpenInvoiceAmount(@Param(value="relatedId")Integer relatedId);

    /**
     * <b>Description:</b><br> 修改销售订单发票状态
     * @param invoice
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年10月9日 下午4:32:19
     */
    int updateSaleInvoiceStatus(Invoice invoice);
}
