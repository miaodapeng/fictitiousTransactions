package com.vedeng.order.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.vedeng.finance.model.Invoice;
import com.vedeng.order.model.Saleorder;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;

/**
 * <b>Description:</b><br>
 * DTO
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.order.model.vo <br>
 *       <b>ClassName:</b> SaleorderVo <br>
 *       <b>Date:</b> 2017年7月13日 下午4:37:27
 */
public class SaleorderVo extends Saleorder {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private Long buyTime;// 可采购时间

	private Integer num;// 采购数量
	
	private Integer proBuySum;//每个待采购产品的总计

	private Integer deliveryNum;// 已发货数量

	private Integer buyNum;// 已采购数量

	private String goodsComments;// 产品备注

	private String insideComments;// 内部备注

	private String deliveryDirectComments;// 直发备注
	
	private Integer sex;//性别

	private String applicantName;// 申请人名称
	
	private Integer saleorderGoodId;// 销售订单产品id
	
	private String deliveryCycle;//销售货期
	
	private BigDecimal price;//销售产品单价
	
	private BigDecimal receivedAmount;//订单结款金额
	
    private List<SaleorderGoodsVo> sgvList;//售后订单查询使用
    
    private List<TraderContact> tcList;//售后订单查询使用
    
    private List<TraderAddress> tavList;//新增售后订单获取收货地址
    
    private List<TraderAddressVo> traderAddressVoList;//收货地址dto
    
    private BigDecimal paid;//已付款金额
    
    private String payee;//收款方名称
    
    private String bank,bankCode,bankAccount,taxNum,regAddress,regTel,averageTaxpayerUri;//开户银行,开户行支付联行号,银行帐号,税务登记号,注册地址,注册电话,一般纳税人资质地址
    
    private Integer areaId;//最小区域id
    
    private List<Invoice> invoiceList;//发票列表
    
    private BigDecimal costAmount;//成本价格
    
	private String lastBussinessTimeStr;//上次交易时间
	private String firstBussinessTimeStr;//第一次交易时间

	private String name;//联系人姓名
    
    private String fax;//传真

    private String mobile;//手机号
    
    private String email;//邮件
    
    private String shName;//销售订单审核人
    
    private Integer traderCustomerId;
    
    private List<Integer> goodsIds;
    
    private List<Long> searchBegintimeList;// 搜索开始时间

    private List<Long> searchEndtimeList;// 搜索结束时间
    
    private List<BigDecimal> monthAmountList;
    
    private Integer thk;//1-销售单包含待确认或进行中的退换货退款售后；0-不包含
    
    private Integer traderContactId;//
    
    private Integer overdueStatus;//逾期状态
    
    private Long beginTime;//搜索开始时间
    
    private Long endTime;//搜索结束时间
    
    private TraderFinanceVo traderFinanceVo;
    
    private Integer invoiceAreaId;//最小区域id

    private BigDecimal amount;
    
    private Long accountEndTime;
    
    private Long traderTime;
	//产品总数
    private Integer totalNum;
    
    private String deliveryTypeStr,paymentTypeStr;

    private Long periodTime;
    
    private Long tkTime;
    
    private Long hxTime;
    
    private BigDecimal periodAmount;
	//是否是活动商品
	private Integer isActionGoods;

	private Integer type;

	public Integer getSex() {
		return sex;
	}

	@Override
	public Integer getType() {
		return type;
	}

	@Override
	public void setType(Integer type) {
		this.type = type;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getProBuySum() {
		return proBuySum;
	}

	public void setProBuySum(Integer proBuySum) {
		this.proBuySum = proBuySum;
	}

	public String getShName() {
		return shName;
	}

	public void setShName(String shName) {
		this.shName = shName;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTaxNum() {
		return taxNum;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getRegTel() {
		return regTel;
	}

	public void setRegTel(String regTel) {
		this.regTel = regTel;
	}

	public String getAverageTaxpayerUri() {
		return averageTaxpayerUri;
	}

	public void setAverageTaxpayerUri(String averageTaxpayerUri) {
		this.averageTaxpayerUri = averageTaxpayerUri;
	}

	public BigDecimal getReceivedAmount() {
	    return receivedAmount;
	}

	public void setReceivedAmount(BigDecimal receivedAmount) {
	    this.receivedAmount = receivedAmount;
	}

	public Long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Long buyTime) {
		this.buyTime = buyTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Integer deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public String getGoodsComments() {
		return goodsComments;
	}

	public void setGoodsComments(String goodsComments) {
		this.goodsComments = goodsComments;
	}

	public String getInsideComments() {
		return insideComments;
	}

	public void setInsideComments(String insideComments) {
		this.insideComments = insideComments;
	}

	public String getDeliveryDirectComments() {
		return deliveryDirectComments;
	}

	public void setDeliveryDirectComments(String deliveryDirectComments) {
		this.deliveryDirectComments = deliveryDirectComments;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Integer getSaleorderGoodId() {
		return saleorderGoodId;
	}

	public void setSaleorderGoodId(Integer saleorderGoodId) {
		this.saleorderGoodId = saleorderGoodId;
	}

	public String getDeliveryCycle() {
		return deliveryCycle;
	}

	public void setDeliveryCycle(String deliveryCycle) {
		this.deliveryCycle = deliveryCycle;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<SaleorderGoodsVo> getSgvList() {
		return sgvList;
	}

	public void setSgvList(List<SaleorderGoodsVo> sgvList) {
		this.sgvList = sgvList;
	}

	public List<TraderContact> getTcList() {
		return tcList;
	}

	public void setTcList(List<TraderContact> tcList) {
		this.tcList = tcList;
	}

	public List<TraderAddress> getTavList() {
		return tavList;
	}

	public void setTavList(List<TraderAddress> tavList) {
		this.tavList = tavList;
	}

	public List<TraderAddressVo> getTraderAddressVoList() {
		return traderAddressVoList;
	}

	public void setTraderAddressVoList(List<TraderAddressVo> traderAddressVoList) {
		this.traderAddressVoList = traderAddressVoList;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee == null ? null : payee.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.replaceAll("\\(","（").replaceAll("\\)","）").trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public String getLastBussinessTimeStr() {
		return lastBussinessTimeStr;
	}

	public void setLastBussinessTimeStr(String lastBussinessTimeStr) {
		this.lastBussinessTimeStr = lastBussinessTimeStr;
	}

	public String getFirstBussinessTimeStr() {
		return firstBussinessTimeStr;
	}

	public void setFirstBussinessTimeStr(String firstBussinessTimeStr) {
		this.firstBussinessTimeStr = firstBussinessTimeStr;
	}

	public Integer getTraderCustomerId() {
		return traderCustomerId;
	}

	public void setTraderCustomerId(Integer traderCustomerId) {
		this.traderCustomerId = traderCustomerId;
	}

	public List<Integer> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<Integer> goodsIds) {
		this.goodsIds = goodsIds;
	}

	public List<Long> getSearchBegintimeList() {
		return searchBegintimeList;
	}

	public void setSearchBegintimeList(List<Long> searchBegintimeList) {
		this.searchBegintimeList = searchBegintimeList;
	}

	public List<Long> getSearchEndtimeList() {
		return searchEndtimeList;
	}

	public void setSearchEndtimeList(List<Long> searchEndtimeList) {
		this.searchEndtimeList = searchEndtimeList;
	}

	public List<BigDecimal> getMonthAmountList() {
		return monthAmountList;
	}

	public void setMonthAmountList(List<BigDecimal> monthAmountList) {
		this.monthAmountList = monthAmountList;
	}

	public Integer getThk() {
		return thk;
	}

	public void setThk(Integer thk) {
		this.thk = thk;
	}

	public Integer getTraderContactId() {
		return traderContactId;
	}

	public void setTraderContactId(Integer traderContactId) {
		this.traderContactId = traderContactId;
	}

	public Integer getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(Integer overdueStatus) {
		this.overdueStatus = overdueStatus;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public TraderFinanceVo getTraderFinanceVo() {
		return traderFinanceVo;
	}

	public void setTraderFinanceVo(TraderFinanceVo traderFinanceVo) {
		this.traderFinanceVo = traderFinanceVo;
	}

	public Integer getInvoiceAreaId() {
		return invoiceAreaId;
	}

	public void setInvoiceAreaId(Integer invoiceAreaId) {
		this.invoiceAreaId = invoiceAreaId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getAccountEndTime() {
		return accountEndTime;
	}

	public void setAccountEndTime(Long accountEndTime) {
		this.accountEndTime = accountEndTime;
	}

	public Long getTraderTime() {
		return traderTime;
	}

	public void setTraderTime(Long traderTime) {
		this.traderTime = traderTime;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getDeliveryTypeStr() {
		return deliveryTypeStr;
	}

	public void setDeliveryTypeStr(String deliveryTypeStr) {
		this.deliveryTypeStr = deliveryTypeStr;
	}

	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}

	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}

	public Long getPeriodTime() {
		return periodTime;
	}

	public void setPeriodTime(Long periodTime) {
		this.periodTime = periodTime;
	}

	public Long getTkTime() {
		return tkTime;
	}

	public void setTkTime(Long tkTime) {
		this.tkTime = tkTime;
	}

	public Long getHxTime() {
		return hxTime;
	}

	public void setHxTime(Long hxTime) {
		this.hxTime = hxTime;
	}

	public BigDecimal getPeriodAmount() {
		return periodAmount;
	}

	public void setPeriodAmount(BigDecimal periodAmount) {
		this.periodAmount = periodAmount;
	}

	public Integer getIsActionGoods() {
		return isActionGoods;
	}

	public void setIsActionGoods(Integer isActionGoods) {
		this.isActionGoods = isActionGoods;
	}
}
