package com.vedeng.finance.dao;

import com.vedeng.finance.model.InvoiceApply;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;

@Named("invoiceApplyMapper")
public interface InvoiceApplyMapper {

    InvoiceApply selectByPrimaryKey(Integer invoiceApplyId);

    int update(InvoiceApply record);
    /**
    *通过快递id获取开票申请
    * @Author:strange
    * @Date:10:04 2020-01-06
    */
    List<InvoiceApply> getInvoiceApplyByExpressId(Integer expressId);

    /**
     * 快递是否关联都为未通过发票
     * @param expressId
     * @return
     */
    List<InvoiceApply> getInvoiceApplyByExpressIdFaile(Integer expressId);
    //查询所有开票申请
    List<InvoiceApply> getAllSaleInvoiceApplyList();
    //根据订单id查找开票记录
    List<Integer> getInvoiceApplyIdsBySaleOrderIds(@Param("saleOrderNoList")List saleOrderNoList);
    //改变标记状态
    int changeIsSign(@Param("endInvoiceApplyIds")List<Integer> endInvoiceApplyIds);


}
