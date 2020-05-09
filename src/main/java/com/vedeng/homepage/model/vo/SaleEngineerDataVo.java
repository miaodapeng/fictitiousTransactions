package com.vedeng.homepage.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;

/**
 * <b>Description:</b><br>
 * 销售工程师个人首页数据
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.homepage.model.vo <br>
 *       <b>ClassName:</b> SaleEngineerDataVo <br>
 *       <b>Date:</b> 2017年11月28日 下午2:30:06
 */
public class SaleEngineerDataVo {

    private List<Integer> todayDoList;// 今日 待办数据：

    private List<BigDecimal> thisMonthDataList;// 本月数据

    private List<BigDecimal> salePersonalList;// 销售个人数据

    private List<TraderCustomerVo> traderCustomerVoList;// 客户沟通

    private List<BussinessChanceVo> bussinessChanceVoList;// 商机跟进

    private List<QuoteorderVo> quoteorderVoList;// 报价跟进

    private List<BigDecimal> contractUploadNumber;// 销售合同上传数据

    private Integer companyId;

    private List<Integer> traderIdList;// 归属客户集合

    private Integer userId;

    private List<Integer> todayTraderCustomerList;// 今天需要沟通的客户id集合

    private List<Integer> todayBussinessChanceList;// 今天需要沟通的商机的traderid集合

    private List<Integer> todayQuoteorderList;// 今天需要沟通的报价的traderid的集合

    private List<Integer> orgIds;// 部门id集合

    private Integer accessType;// 销售工程师首页标签类型：1-沟通；2-商机；3-报价；4-本月数据；5-个人数据

    public List<Integer> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Integer> orgIds) {
        this.orgIds = orgIds;
    }

    public List<Integer> getTodayDoList() {
        return todayDoList;
    }

    public void setTodayDoList(List<Integer> todayDoList) {
        this.todayDoList = todayDoList;
    }

    public List<BigDecimal> getThisMonthDataList() {
        return thisMonthDataList;
    }

    public void setThisMonthDataList(List<BigDecimal> thisMonthDataList) {
        this.thisMonthDataList = thisMonthDataList;
    }

    public List<BigDecimal> getSalePersonalList() {
        return salePersonalList;
    }

    public void setSalePersonalList(List<BigDecimal> salePersonalList) {
        this.salePersonalList = salePersonalList;
    }

    public List<TraderCustomerVo> getTraderCustomerVoList() {
        return traderCustomerVoList;
    }

    public void setTraderCustomerVoList(List<TraderCustomerVo> traderCustomerVoList) {
        this.traderCustomerVoList = traderCustomerVoList;
    }

    public List<BussinessChanceVo> getBussinessChanceVoList() {
        return bussinessChanceVoList;
    }

    public void setBussinessChanceVoList(List<BussinessChanceVo> bussinessChanceVoList) {
        this.bussinessChanceVoList = bussinessChanceVoList;
    }

    public List<QuoteorderVo> getQuoteorderVoList() {
        return quoteorderVoList;
    }

    public void setQuoteorderVoList(List<QuoteorderVo> quoteorderVoList) {
        this.quoteorderVoList = quoteorderVoList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getTraderIdList() {
        return traderIdList;
    }

    public void setTraderIdList(List<Integer> traderIdList) {
        this.traderIdList = traderIdList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getTodayTraderCustomerList() {
        return todayTraderCustomerList;
    }

    public void setTodayTraderCustomerList(List<Integer> todayTraderCustomerList) {
        this.todayTraderCustomerList = todayTraderCustomerList;
    }

    public List<Integer> getTodayBussinessChanceList() {
        return todayBussinessChanceList;
    }

    public void setTodayBussinessChanceList(List<Integer> todayBussinessChanceList) {
        this.todayBussinessChanceList = todayBussinessChanceList;
    }

    public List<Integer> getTodayQuoteorderList() {
        return todayQuoteorderList;
    }

    public void setTodayQuoteorderList(List<Integer> todayQuoteorderList) {
        this.todayQuoteorderList = todayQuoteorderList;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public List<BigDecimal> getContractUploadNumber() {
        return contractUploadNumber;
    }

    public void setContractUploadNumber(List<BigDecimal> contractUploadNumber) {
        this.contractUploadNumber = contractUploadNumber;
    }

}
