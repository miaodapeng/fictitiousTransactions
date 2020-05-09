package com.vedeng.finance.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.functions.T;

import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.PayApply;

public interface BankBillService {
    /**
     * 
     * <b>Description:</b><br>银行流水列表 
     * @param bankBill
     * @param page
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年9月12日 上午11:42:54
     */
    Map<String, Object> getBankBillListPage(BankBill bankBill, Page page) throws Exception;
    /**
     * 
     * <b>Description:</b><br> 银行流水匹配列表 
     * @param bankBill
     * @param page
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年9月15日 下午5:14:16
     */
    Map<String, Object> getBankBillMatchListPage(BankBill bankBill, Page page);
    /**
     * 
     * <b>Description:</b><br> 修改银行流水 （暂时只用来忽略银行流水）
     * @param bankBill
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年9月26日 下午3:13:41
     */
    ResultInfo editBankBill(BankBill bankBill);
    /**
     * 
     * <b>Description:</b><br>根据ID获取银行流水信息 
     * @param bankBillId
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年9月28日 上午9:24:09
     */
    BankBill getBankBillById(Integer bankBillId);
    /**
     * 
     * <b>Description:</b><br> 新增银企直连银行付款
     * @param payApplyInfo
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年1月22日 下午1:18:58
     */
    ResultInfo  addBankPayApply(PayApply payApplyInfo);
	
    ResultInfo sendBankBillList(BankBill bankBill, Page page, HttpSession session)  throws Exception;
    /**
     * 
     * <b>Description:</b><br> 付款流水匹配付款申请
     * @param bankBill
     * @param page
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2018年8月6日 上午10:36:34
     */
    Map<String, Object> getBankBillPayMatchListPage(BankBill bankBill, Page page);
    
    /**
     * 发送付款记录至金蝶
     * <b>Description:</b><br> 
     * @param capitalBill
     * @param page
     * @param session
     * @return
     * @throws UnsupportedEncodingException 
     * @Note
     * <b>Author:</b> Bill
     * <br><b>Date:</b> 2018年8月9日 上午10:52:49
     */
	ResultInfo sendPayBillToKindlee(BankBill bankBill, Page page, HttpSession session) throws UnsupportedEncodingException;
	
	/**
	 * 发送中国银行流水记录至金蝶
	 * <b>Description:</b>
	 * @param bankBill
	 * @param page
	 * @param session
	 * @return ResultInfo
	 * @Note
	 * <b>Author：</b> bill.bo
	 * <b>Date:</b> 2018年10月18日 下午2:46:18
	 */
	ResultInfo<T> sendChPayBillToKindlee(BankBill bankBill, Page page, HttpSession session);

}
