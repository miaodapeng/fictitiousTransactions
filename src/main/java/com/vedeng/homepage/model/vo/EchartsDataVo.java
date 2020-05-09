package com.vedeng.homepage.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.homepage.model.SalesGoalSetting;

/**
 * <b>Description:</b><br> Echarts数据
 * @author east
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.homepage.model.vo
 * <br><b>ClassName:</b> EchartsDataVo
 * <br><b>Date:</b> 2017年12月26日 下午3:50:23
 */
public class EchartsDataVo implements Serializable{
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Map<Integer, Integer> orgCustomerMap;//客户分部门占比
	
	private Map<Integer, Integer> orgTraderCustomerMap;//成交客户分部门占比
	
	private Map<Integer, Integer> orgManyTraderCustomerMap;//多次成交客户分部门占比
	
	private List<BigDecimal> goalList;//柱状图目标数据
	
	private List<BigDecimal>completeList;//完成数据
	
	private List<Double> anList;//同比数据
	
	private List<Double> momList;//环比数据
	
	private Integer companyId;
	
	private List<Integer> orgIdList;//当前人员下的所有部门
	
	private Map<Integer, List<Integer>> orgMap;//当前人员下一级部门的所有部门
	
	private Map<Integer, BigDecimal> moneyMap;//部门完成额或销售人员的完成额
	
	private List<SalesGoalSetting> salesGoalSettingList;//销售部门设置的月度目标
	
	private Map<Integer, BigDecimal> saleMoneyMap;//部门销售额
	
	private Map<Integer, Integer> saleorderSumMap;//部门订单数
	
	private List<String> nextDeptList;//下一级部门名称的集合
	
	private List<BigDecimal> deptSaleMoney;//部门销售额
	
	private List<Long> deptSaleorder;//部门订单数
	
	private List<Integer> areaIdList;//省份id集合
	
	private Map<Integer, Integer> customerMap;//省份客户数
	
	private List<String> proNameList;//省份名称集合
	
	private List<Integer> proCustomerList;//省份客户数集合:必须与proNameList一一对应
	
	private String customerNature;//客户性质
	
	private Integer positLevel;//当前登录人员级别
	
	private Integer orgId;//当前人员部门
	
	private List<Integer> userIdList;//当前部门下的所有人员
	
	private List<Integer> saleorderAftersaleOrderSumList;//销售订单售后统计
	
	private List<BigDecimal> saleAfterSaleIncomeList;//销售售后收入
	
	private List<BigDecimal> saleAfterSalePayList;//销售售后支出
	
	private List<BigDecimal> thirdAfterSaleIncomeList;//第三方售后收入
	
	private List<BigDecimal> thirdAfterSalePayList;//第三方售后支出
	
	private List<BigDecimal> saleorderRefundAmonutList;//销售订单退货金额
	
	private List<Double> saleorderRefundAmonutMomList;//销售订单退货金额环比数据
	
	private Integer positType;//当前人员的类型
	
	/*******************售后**********************/
	private Map<Integer, Integer> saleAftersaleMap;//销售售后类型占比
	
	private Map<Integer, Integer> thirdAftersaleMap;//第三方售后类型占比
	
	private List<String> salesNames;//销售售后类型名称
	
	private List<Integer> salesNums;//销售售后类型名称对应的数量
	
	private List<String> thirdNames;//第三方售后类型名称
	
	private List<Integer> thirdNums;//第三方售后类型名称对应的数量
	
	/******************物流*********************/
	private Map<Integer, BigDecimal> freightChargesMap;//本月运费支出占比
	
	private Map<Integer, Integer> businessDeliveryMap;//本月业务发货占比
	
	private List<String> logisticsNames;//物流公司名称
	
	private List<BigDecimal> logisticsAmount;//物流公司运费
	
	private List<String> businessNames;//业务类型名称
	
	private List<Integer> businessNums;//业务类型名称对应的发货数量 
	
	private Map<Integer,Map<Integer, BigDecimal>> businessFreightMap;//业务运费统计
	
	private Map<Integer,Map<Integer, BigDecimal>> logisticsFreightMap;//快递运费统计
	
	private Map<Integer,Map<Integer, Integer>> logisticsPiecesMap;//快递件数统计
	
	private Map<Integer, Integer> outWarehouseMap;//出库统计
	
	private Map<Integer, Integer> enterWarehouseMap;//入库统计
	
	private Map<Integer, Double> stockTurnoverMap;//库存周转率
	
	private List<ChildrenEchartsDataVo> childrenEchartsDataVoList;//业务运费堆叠柱状图数据
	
	private List<ChildrenEchartsDataVo> logisticsFreightList;//快递运费堆叠柱状图数据
	
	private List<ChildrenEchartsDataVo> logisticsPiecesList;//快递件数堆叠柱状图数据
	
	private List<Integer> outWarehouseList;
	
	private List<Integer> enterWarehouseList;
	
	private List<Double> stockTurnoverList;
	
	private Integer year;
	
	private List<Double> returnRateList;//退货率
	
	private List<Double> momReturnRateList;//环比退货率
	
	private Map<Integer, Integer> returnSaleAftersaleMap;//销售订单退货原因占比
	
	private Map<Integer, Integer> exchangeSaleAftersaleMap;//销售订单换货原因占比
	
	private List<String> returnSalesNames;
	
	private List<Integer> returnSalesNums;
	
	private List<String> exchangeSalesNames;
	
	private List<Integer> exchangeSalesNums;
	
	private Integer min;
	
	private Integer max;
	
	

	public Integer getPositLevel() {
		return positLevel;
	}

	public void setPositLevel(Integer positLevel) {
		this.positLevel = positLevel;
	}

	public String getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}

	public Map<Integer, Integer> getCustomerMap() {
		return customerMap;
	}

	public void setCustomerMap(Map<Integer, Integer> customerMap) {
		this.customerMap = customerMap;
	}

	public List<String> getProNameList() {
		return proNameList;
	}

	public void setProNameList(List<String> proNameList) {
		this.proNameList = proNameList;
	}

	public List<Integer> getProCustomerList() {
		return proCustomerList;
	}

	public void setProCustomerList(List<Integer> proCustomerList) {
		this.proCustomerList = proCustomerList;
	}

	public List<Integer> getAreaIdList() {
		return areaIdList;
	}

	public void setAreaIdList(List<Integer> areaIdList) {
		this.areaIdList = areaIdList;
	}

	public List<String> getNextDeptList() {
		return nextDeptList;
	}

	public void setNextDeptList(List<String> nextDeptList) {
		this.nextDeptList = nextDeptList;
	}

	public List<BigDecimal> getDeptSaleMoney() {
		return deptSaleMoney;
	}

	public void setDeptSaleMoney(List<BigDecimal> deptSaleMoney) {
		this.deptSaleMoney = deptSaleMoney;
	}

	public List<Long> getDeptSaleorder() {
		return deptSaleorder;
	}

	public void setDeptSaleorder(List<Long> deptSaleorder) {
		this.deptSaleorder = deptSaleorder;
	}

	public Map<Integer, BigDecimal> getSaleMoneyMap() {
		return saleMoneyMap;
	}

	public void setSaleMoneyMap(Map<Integer, BigDecimal> saleMoneyMap) {
		this.saleMoneyMap = saleMoneyMap;
	}

	public Map<Integer, Integer> getSaleorderSumMap() {
		return saleorderSumMap;
	}

	public void setSaleorderSumMap(Map<Integer, Integer> saleorderSumMap) {
		this.saleorderSumMap = saleorderSumMap;
	}

	public List<SalesGoalSetting> getSalesGoalSettingList() {
		return salesGoalSettingList;
	}

	public void setSalesGoalSettingList(List<SalesGoalSetting> salesGoalSettingList) {
		this.salesGoalSettingList = salesGoalSettingList;
	}

	public Map<Integer, List<Integer>> getOrgMap() {
		return orgMap;
	}

	public void setOrgMap(Map<Integer, List<Integer>> orgMap) {
		this.orgMap = orgMap;
	}

	public Map<Integer, BigDecimal> getMoneyMap() {
		return moneyMap;
	}

	public void setMoneyMap(Map<Integer, BigDecimal> moneyMap) {
		this.moneyMap = moneyMap;
	}

	public List<BigDecimal> getGoalList() {
		return goalList;
	}

	public void setGoalList(List<BigDecimal> goalList) {
		this.goalList = goalList;
	}

	public List<BigDecimal> getCompleteList() {
		return completeList;
	}

	public void setCompleteList(List<BigDecimal> completeList) {
		this.completeList = completeList;
	}

	public List<Double> getAnList() {
		return anList;
	}

	public void setAnList(List<Double> anList) {
		this.anList = anList;
	}

	public List<Double> getMomList() {
		return momList;
	}

	public void setMomList(List<Double> momList) {
		this.momList = momList;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<Integer> getOrgIdList() {
		return orgIdList;
	}

	public void setOrgIdList(List<Integer> orgIdList) {
		this.orgIdList = orgIdList;
	}

	public Map<Integer, Integer> getOrgCustomerMap() {
		return orgCustomerMap;
	}

	public void setOrgCustomerMap(Map<Integer, Integer> orgCustomerMap) {
		this.orgCustomerMap = orgCustomerMap;
	}

	public Map<Integer, Integer> getOrgTraderCustomerMap() {
		return orgTraderCustomerMap;
	}

	public void setOrgTraderCustomerMap(Map<Integer, Integer> orgTraderCustomerMap) {
		this.orgTraderCustomerMap = orgTraderCustomerMap;
	}

	public Map<Integer, Integer> getOrgManyTraderCustomerMap() {
		return orgManyTraderCustomerMap;
	}

	public void setOrgManyTraderCustomerMap(Map<Integer, Integer> orgManyTraderCustomerMap) {
		this.orgManyTraderCustomerMap = orgManyTraderCustomerMap;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public List<Integer> getSaleorderAftersaleOrderSumList() {
		return saleorderAftersaleOrderSumList;
	}

	public void setSaleorderAftersaleOrderSumList(List<Integer> saleorderAftersaleOrderSumList) {
		this.saleorderAftersaleOrderSumList = saleorderAftersaleOrderSumList;
	}

	public List<BigDecimal> getSaleAfterSaleIncomeList() {
		return saleAfterSaleIncomeList;
	}

	public void setSaleAfterSaleIncomeList(List<BigDecimal> saleAfterSaleIncomeList) {
		this.saleAfterSaleIncomeList = saleAfterSaleIncomeList;
	}

	public List<BigDecimal> getSaleAfterSalePayList() {
		return saleAfterSalePayList;
	}

	public void setSaleAfterSalePayList(List<BigDecimal> saleAfterSalePayList) {
		this.saleAfterSalePayList = saleAfterSalePayList;
	}

	public List<BigDecimal> getThirdAfterSaleIncomeList() {
		return thirdAfterSaleIncomeList;
	}

	public void setThirdAfterSaleIncomeList(List<BigDecimal> thirdAfterSaleIncomeList) {
		this.thirdAfterSaleIncomeList = thirdAfterSaleIncomeList;
	}

	public List<BigDecimal> getThirdAfterSalePayList() {
		return thirdAfterSalePayList;
	}

	public void setThirdAfterSalePayList(List<BigDecimal> thirdAfterSalePayList) {
		this.thirdAfterSalePayList = thirdAfterSalePayList;
	}

	public List<BigDecimal> getSaleorderRefundAmonutList() {
		return saleorderRefundAmonutList;
	}

	public void setSaleorderRefundAmonutList(List<BigDecimal> saleorderRefundAmonutList) {
		this.saleorderRefundAmonutList = saleorderRefundAmonutList;
	}

	public List<Double> getSaleorderRefundAmonutMomList() {
		return saleorderRefundAmonutMomList;
	}

	public void setSaleorderRefundAmonutMomList(List<Double> saleorderRefundAmonutMomList) {
		this.saleorderRefundAmonutMomList = saleorderRefundAmonutMomList;
	}

	public Integer getPositType() {
		return positType;
	}

	public void setPositType(Integer positType) {
		this.positType = positType;
	}

	public Map<Integer, Integer> getSaleAftersaleMap() {
		return saleAftersaleMap;
	}

	public void setSaleAftersaleMap(Map<Integer, Integer> saleAftersaleMap) {
		this.saleAftersaleMap = saleAftersaleMap;
	}

	public Map<Integer, Integer> getThirdAftersaleMap() {
		return thirdAftersaleMap;
	}

	public void setThirdAftersaleMap(Map<Integer, Integer> thirdAftersaleMap) {
		this.thirdAftersaleMap = thirdAftersaleMap;
	}

	public List<String> getSalesNames() {
		return salesNames;
	}

	public void setSalesNames(List<String> salesNames) {
		this.salesNames = salesNames;
	}

	public List<Integer> getSalesNums() {
		return salesNums;
	}

	public void setSalesNums(List<Integer> salesNums) {
		this.salesNums = salesNums;
	}

	public List<String> getThirdNames() {
		return thirdNames;
	}

	public void setThirdNames(List<String> thirdNames) {
		this.thirdNames = thirdNames;
	}

	public List<Integer> getThirdNums() {
		return thirdNums;
	}

	public void setThirdNums(List<Integer> thirdNums) {
		this.thirdNums = thirdNums;
	}

	public Map<Integer, BigDecimal> getFreightChargesMap() {
		return freightChargesMap;
	}

	public void setFreightChargesMap(Map<Integer, BigDecimal> freightChargesMap) {
		this.freightChargesMap = freightChargesMap;
	}

	public Map<Integer, Integer> getBusinessDeliveryMap() {
		return businessDeliveryMap;
	}

	public void setBusinessDeliveryMap(Map<Integer, Integer> businessDeliveryMap) {
		this.businessDeliveryMap = businessDeliveryMap;
	}

	public List<String> getLogisticsNames() {
		return logisticsNames;
	}

	public void setLogisticsNames(List<String> logisticsNames) {
		this.logisticsNames = logisticsNames;
	}

	public List<BigDecimal> getLogisticsAmount() {
		return logisticsAmount;
	}

	public void setLogisticsAmount(List<BigDecimal> logisticsAmount) {
		this.logisticsAmount = logisticsAmount;
	}

	public List<String> getBusinessNames() {
		return businessNames;
	}

	public void setBusinessNames(List<String> businessNames) {
		this.businessNames = businessNames;
	}

	public List<Integer> getBusinessNums() {
		return businessNums;
	}

	public void setBusinessNums(List<Integer> businessNums) {
		this.businessNums = businessNums;
	}

	public Map<Integer, Map<Integer, BigDecimal>> getBusinessFreightMap() {
		return businessFreightMap;
	}

	public void setBusinessFreightMap(Map<Integer, Map<Integer, BigDecimal>> businessFreightMap) {
		this.businessFreightMap = businessFreightMap;
	}

	public Map<Integer, Map<Integer, BigDecimal>> getLogisticsFreightMap() {
		return logisticsFreightMap;
	}

	public void setLogisticsFreightMap(Map<Integer, Map<Integer, BigDecimal>> logisticsFreightMap) {
		this.logisticsFreightMap = logisticsFreightMap;
	}

	public Map<Integer, Map<Integer, Integer>> getLogisticsPiecesMap() {
		return logisticsPiecesMap;
	}

	public void setLogisticsPiecesMap(Map<Integer, Map<Integer, Integer>> logisticsPiecesMap) {
		this.logisticsPiecesMap = logisticsPiecesMap;
	}

	public Map<Integer, Integer> getOutWarehouseMap() {
		return outWarehouseMap;
	}

	public void setOutWarehouseMap(Map<Integer, Integer> outWarehouseMap) {
		this.outWarehouseMap = outWarehouseMap;
	}

	public Map<Integer, Integer> getEnterWarehouseMap() {
		return enterWarehouseMap;
	}

	public void setEnterWarehouseMap(Map<Integer, Integer> enterWarehouseMap) {
		this.enterWarehouseMap = enterWarehouseMap;
	}

	public Map<Integer, Double> getStockTurnoverMap() {
		return stockTurnoverMap;
	}

	public void setStockTurnoverMap(Map<Integer, Double> stockTurnoverMap) {
		this.stockTurnoverMap = stockTurnoverMap;
	}

	public List<ChildrenEchartsDataVo> getChildrenEchartsDataVoList() {
		return childrenEchartsDataVoList;
	}

	public void setChildrenEchartsDataVoList(List<ChildrenEchartsDataVo> childrenEchartsDataVoList) {
		this.childrenEchartsDataVoList = childrenEchartsDataVoList;
	}

	public List<ChildrenEchartsDataVo> getLogisticsFreightList() {
		return logisticsFreightList;
	}

	public void setLogisticsFreightList(List<ChildrenEchartsDataVo> logisticsFreightList) {
		this.logisticsFreightList = logisticsFreightList;
	}

	public List<ChildrenEchartsDataVo> getLogisticsPiecesList() {
		return logisticsPiecesList;
	}

	public void setLogisticsPiecesList(List<ChildrenEchartsDataVo> logisticsPiecesList) {
		this.logisticsPiecesList = logisticsPiecesList;
	}

	public List<Integer> getOutWarehouseList() {
		return outWarehouseList;
	}

	public void setOutWarehouseList(List<Integer> outWarehouseList) {
		this.outWarehouseList = outWarehouseList;
	}

	public List<Integer> getEnterWarehouseList() {
		return enterWarehouseList;
	}

	public void setEnterWarehouseList(List<Integer> enterWarehouseList) {
		this.enterWarehouseList = enterWarehouseList;
	}

	public List<Double> getStockTurnoverList() {
		return stockTurnoverList;
	}

	public void setStockTurnoverList(List<Double> stockTurnoverList) {
		this.stockTurnoverList = stockTurnoverList;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Double> getReturnRateList() {
		return returnRateList;
	}

	public void setReturnRateList(List<Double> returnRateList) {
		this.returnRateList = returnRateList;
	}

	public List<Double> getMomReturnRateList() {
		return momReturnRateList;
	}

	public void setMomReturnRateList(List<Double> momReturnRateList) {
		this.momReturnRateList = momReturnRateList;
	}

	public Map<Integer, Integer> getReturnSaleAftersaleMap() {
		return returnSaleAftersaleMap;
	}

	public void setReturnSaleAftersaleMap(Map<Integer, Integer> returnSaleAftersaleMap) {
		this.returnSaleAftersaleMap = returnSaleAftersaleMap;
	}

	public Map<Integer, Integer> getExchangeSaleAftersaleMap() {
		return exchangeSaleAftersaleMap;
	}

	public void setExchangeSaleAftersaleMap(Map<Integer, Integer> exchangeSaleAftersaleMap) {
		this.exchangeSaleAftersaleMap = exchangeSaleAftersaleMap;
	}

	public List<String> getReturnSalesNames() {
		return returnSalesNames;
	}

	public void setReturnSalesNames(List<String> returnSalesNames) {
		this.returnSalesNames = returnSalesNames;
	}

	public List<Integer> getReturnSalesNums() {
		return returnSalesNums;
	}

	public void setReturnSalesNums(List<Integer> returnSalesNums) {
		this.returnSalesNums = returnSalesNums;
	}

	public List<String> getExchangeSalesNames() {
		return exchangeSalesNames;
	}

	public void setExchangeSalesNames(List<String> exchangeSalesNames) {
		this.exchangeSalesNames = exchangeSalesNames;
	}

	public List<Integer> getExchangeSalesNums() {
		return exchangeSalesNums;
	}

	public void setExchangeSalesNums(List<Integer> exchangeSalesNums) {
		this.exchangeSalesNums = exchangeSalesNums;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

}
