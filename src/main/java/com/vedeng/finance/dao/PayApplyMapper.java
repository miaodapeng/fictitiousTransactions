package com.vedeng.finance.dao;

import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.PayApply;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description: VDERP-1215 付款申请增加批量操作功能.
 * @version: 1.0.
 * @date: 2019/9/16 13:14.
 * @author: Tomcat.Hui.
 */
public interface PayApplyMapper {

    PayApply selectByPrimaryKey(Integer payApplyId);

    /**
     * 获取当前最新的申请记录
     * @param payApply
     * @return
     */
    PayApply getPayApplyMaxRecord(PayApply payApply);



    /**
     * 根据条件动态修改申请表数据
     * @param payApply
     * @return
     */
    int updatePayStutas(PayApply payApply);

    /**
     * g根据申请id获得数据
     * @param payApply
     * @return
     */
    PayApply getPayApplyRecord(PayApply payApply);


    BigDecimal getPayApplyTotalAmount(Map<String, Object> map);

    BigDecimal getPayApplyPayTotalAmount(Map<String, Object> map);

    List<PayApply> getPayApplyListPage(Map<String, Object> map);

    List<BankBill> getMatchInfo(@Param("amt") BigDecimal amt,@Param("accName1") String accName1,@Param("searchBeginTime") String searchBeginTime,@Param("searchEndTime") String searchEndTime);
}