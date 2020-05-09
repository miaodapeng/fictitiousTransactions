package com.vedeng.soap.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.xml.namespace.QName;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.util.StringUtil;
import com.vedeng.order.controller.SaleorderController;
import com.vedeng.wechat.dao.WeChatArrMapper;
import com.vedeng.wechat.model.WeChatArr;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.RequestResponseTransport;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.axis2.AxisFault;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.UserDetail;
import com.vedeng.authorization.model.vo.RegionVo;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceDetail;
import com.vedeng.finance.model.vo.CapitalBillVo;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.CategoryAttribute;
import com.vedeng.goods.model.CategoryAttributeValue;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.GoodsAttribute;
import com.vedeng.goods.model.GoodsExtend;
import com.vedeng.goods.model.GoodsPackage;
import com.vedeng.goods.model.GoodsRecommend;
import com.vedeng.goods.model.Unit;
import com.vedeng.goods.service.CategoryAttributeService;
import com.vedeng.goods.service.CategoryService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.ordergoods.dao.OrdergoodsGoodsMapper;
import com.vedeng.ordergoods.dao.OrdergoodsStoreAccountMapper;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsStoreAccountVo;
import com.vedeng.soap.model.SsoAccount;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.DataSyncStatus;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.DataSyncStatusService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.WebAccountVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("vedengSoapService")
public class VedengSoapServiceImpl extends BaseServiceimpl implements VedengSoapService {
	public static Logger logger = LoggerFactory.getLogger(VedengSoapServiceImpl.class);

    @Value("${vedeng_api_url}")
    private String vedengApiUrl;

    @Value("${vedeng_namespace}")
    private String vedengNamespace;

    @Value("${passport_api_url}")
    private String passportApiUrl;

    @Value("${passport_namespace}")
    private String passportNamespace;

    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;

    @Autowired
    @Qualifier("dataSyncStatusService")
    private DataSyncStatusService dataSyncStatusService;

    @Autowired
    @Qualifier("ordergoodsGoodsMapper")
    private OrdergoodsGoodsMapper ordergoodsGoodsMapper;

    @Autowired
    @Qualifier("webAccountMapper")
    private WebAccountMapper webAccountMapper;

    @Autowired
    @Qualifier("quoteService")
    private QuoteService quoteService;

    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @Autowired
    @Qualifier("regionService")
    private RegionService regionService;

    @Autowired
    @Qualifier("categoryAttributeService")
    private CategoryAttributeService categoryAttributeService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("traderCustomerService")
    private TraderCustomerService traderCustomerService;

    @Autowired
    @Qualifier("ordergoodsStoreAccountMapper")
    private OrdergoodsStoreAccountMapper ordergoodsStoreAccountMapper;

    @Autowired
    private WeChatArrMapper weChatArrMapper;

	/**
	 * 记录日志
	 */

    public static Logger LOG = LoggerFactory.getLogger(VedengSoapServiceImpl.class);

    @Override
    public ResultInfo orderSync(Integer saleorderId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (saleorderId == null || saleorderId <= 0) {
	    return resultInfo;
	}
	XmlExercise xmlExercise = new XmlExercise();
	Saleorder saleorder = new Saleorder();
	saleorder.setSaleorderId(saleorderId);
	try {
	    // 订单主表信息
	    Saleorder saleorderInfo = saleorderService.getBaseSaleorderInfo(saleorder);
	    // 订单产品信息
	    List<SaleorderGoods> saleorderGoods = saleorderService.getSaleorderGoodsById(saleorder);

	    // 订单信息不全不同步
	    if (null == saleorderInfo || null == saleorderGoods || saleorderGoods.size() <= 0
		    || (!saleorderInfo.getOrderType().equals(3) && !saleorderInfo.getOrderType().equals(4))) {
		return resultInfo;
	    }

	    Map data = new HashMap<>();// 订单数据
	    List goodsDataMap = new ArrayList<>();// 订单产品数据

	    // ===================以下订单主表信息==================
	    data.put("order_no", saleorderInfo.getSaleorderNo());// 订单号
	    data.put("parent_id", saleorderInfo.getParentId());// 上级订单ID
	    data.put("employee_id", saleorderInfo.getTraderContactId());// erp客户联系人ID
	    data.put("add_time", saleorderInfo.getAddTime() != null && saleorderInfo.getAddTime() > 0
		    ? String.format("%010d", saleorderInfo.getAddTime() / 1000) : 0);// 下单时间

	    Integer status = saleorderInfo.getValidStatus() == 1 ? 2 : saleorderInfo.getValidStatus();
	    if (status.equals(2) && saleorderInfo.getArrivalStatus().equals(2)) {
		status = 3;
	    }

	    if (saleorderInfo.getStatus().equals(3)) {
		status = -2;
	    }

	    data.put("status", status);// 订单状态

	    Integer payStatus = 0;
	    if (saleorderInfo.getPaymentStatus().equals(2)) {
		payStatus = 1;
	    }
	    if (saleorderInfo.getPaymentStatus().equals(1)) {
		payStatus = 2;
	    }
	    data.put("pay_status", payStatus);// 付款状态

	    Integer shippingStatus = 0;
	    if (saleorderInfo.getDeliveryStatus().equals(2)) {
		shippingStatus = 1;
	    }
	    if (saleorderInfo.getDeliveryStatus().equals(1)) {
		shippingStatus = 2;
	    }
	    data.put("shipping_status", shippingStatus);// 发货状态

	    data.put("invoice_status",
		    saleorderInfo.getInvoiceStatus().equals("2") ? 5 : saleorderInfo.getInvoiceStatus());// 0未开票
													 // 5部分开票1已开票
	    data.put("amount", saleorderInfo.getTotalAmount());// 总金额

	    BigDecimal totalAmount = saleorderInfo.getTotalAmount();
	    BigDecimal accountPeriodAmount = saleorderInfo.getAccountPeriodAmount();
	    data.put("cash_amount", totalAmount.subtract(accountPeriodAmount));// 现金支付金额
	    data.put("consignee", saleorderInfo.getTakeTraderContactName());// 收货人
	    data.put("mobile_number", saleorderInfo.getTakeTraderContactMobile());// 手机号码
	    Integer areaId = 0;
	    if (null != saleorderInfo.getTakeTraderAddressId() && saleorderInfo.getTakeTraderAddressId() > 0) {
		String url = httpUrl + ErpConst.TRADER_ADDRESS_QUERY;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderAddress>> TypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {
		};
		try {
		    TraderAddress traderAddress = new TraderAddress();
		    traderAddress.setTraderAddressId(saleorderInfo.getTakeTraderAddressId());
		    ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderAddress, clientId,
			    clientKey, TypeRef2);

		    TraderAddress ta = (TraderAddress) result2.getData();
		    if (null != ta) {
			areaId = ta.getAreaId();
		    }
		} catch (IOException e) {
		    return null;
		}
	    } else if (null != saleorderInfo.getTakeTraderArea() && !saleorderInfo.getTakeTraderArea().equals("")) {
		RegionVo regionByArea = regionService.getRegionByArea(saleorderInfo.getTakeTraderArea());
		if (null != regionByArea.getZoneId() && regionByArea.getZoneId() > 0) {
		    areaId = regionByArea.getZoneId();
		} else if (null != regionByArea.getCity() && regionByArea.getCity() > 0) {
		    areaId = regionByArea.getCity();
		} else if (null != regionByArea.getProvince() && regionByArea.getProvince() > 0) {
		    areaId = regionByArea.getProvince();
		}
	    }
	    data.put("area_id", areaId);// 收货区域最小级id
	    data.put("address", saleorderInfo.getTakeTraderAddress());// 收货地址 详细

	    Integer invoiceAreaId = 0;
	    if (null != saleorderInfo.getInvoiceTraderAddressId() && saleorderInfo.getInvoiceTraderAddressId() > 0
		    && null != saleorderInfo.getTakeTraderAddressId() && saleorderInfo.getTakeTraderAddressId() > 0
		    && saleorderInfo.getInvoiceTraderAddressId().equals(saleorderInfo.getTakeTraderAddressId())) {
		invoiceAreaId = areaId;
	    } else if (null != saleorderInfo.getInvoiceTraderAddressId()
		    && saleorderInfo.getInvoiceTraderAddressId() > 0) {
		String url = httpUrl + ErpConst.TRADER_ADDRESS_QUERY;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderAddress>> TypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {
		};
		try {
		    TraderAddress traderAddress = new TraderAddress();
		    traderAddress.setTraderAddressId(saleorderInfo.getInvoiceTraderAddressId());
		    ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderAddress, clientId,
			    clientKey, TypeRef2);

		    TraderAddress ta = (TraderAddress) result2.getData();
		    if (null != ta) {
			invoiceAreaId = ta.getAreaId();
		    }
		} catch (IOException e) {
		    return null;
		}
	    } else if (null != saleorderInfo.getTakeTraderArea() && !saleorderInfo.getTakeTraderArea().equals("")) {
		if (null != saleorderInfo.getTakeTraderArea() && !saleorderInfo.getTakeTraderArea().equals("")
			&& saleorderInfo.getTakeTraderArea().equals(saleorderInfo.getTakeTraderArea())) {
		    invoiceAreaId = areaId;
		} else {
		    RegionVo regionByArea = regionService.getRegionByArea(saleorderInfo.getTakeTraderArea());
		    if (null != regionByArea.getZoneId() && regionByArea.getZoneId() > 0) {
			invoiceAreaId = regionByArea.getZoneId();
		    } else if (null != regionByArea.getCity() && regionByArea.getCity() > 0) {
			invoiceAreaId = regionByArea.getCity();
		    } else if (null != regionByArea.getProvince() && regionByArea.getProvince() > 0) {
			invoiceAreaId = regionByArea.getProvince();
		    }
		}
	    }
	    data.put("invoice_area_id", invoiceAreaId);// 收票区域最小级ID
	    data.put("invoice_address", saleorderInfo.getInvoiceTraderAddress());// 收票地址

	    String invoiceTypeName = "";
	    if (!saleorderInfo.getInvoiceType().equals(0)) {
		SysOptionDefinition sysOptionDefinition = getSysOptionDefinitionById(saleorderInfo.getInvoiceType());
		if (null != sysOptionDefinition) {
		    invoiceTypeName = sysOptionDefinition.getTitle();
		}
	    }

	    data.put("invoice_type_name", invoiceTypeName);// 发票类型描述
	    data.put("invoice_title", saleorderInfo.getTraderName());// 发票抬头
	    data.put("source", 4);// 订单来源0pc端 1app端2mobile端3pad端
	    data.put("is_send_invoice", saleorderInfo.getIsSendInvoice());// 是否寄送发票
									  // 0否
									  // 1是
	    data.put("invoice_method", saleorderInfo.getInvoiceMethod());// 开票方式1手动纸质开票、2自动纸质开票、3自动电子发票

	    // ===================以下订单产品信息==================
	    goodsDataMap.add(new HashMap<>());
	    for (SaleorderGoods goods : saleorderGoods) {
		Map goodsData = new HashMap<>();

		goodsData.put("item_id", goods.getGoodsId());// 商品id
		goodsData.put("item_name", goods.getGoodsName());// 商品名称
		goodsData.put("price", goods.getPrice());// 价格
		goodsData.put("quantity", goods.getNum());// 数量
		goodsData.put("delivery_send", goods.getDeliveryNum());// 发货数量
		goodsData.put("delivery_status", goods.getDeliveryStatus());// 0未发货1部分发货2全部发货

		goodsDataMap.add(goodsData);
	    }

	    // ===================以下调用接口==================
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    String goodsDataXmlStr = xmlExercise.listToXml(goodsDataMap, "data", "items");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("orderSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr, goodsDataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "orderSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_SALEORDER");
	    dataSyncStatus.setRelatedId(saleorderId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo paylogSync(Integer capitalBillId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (capitalBillId == null || capitalBillId <= 0) {
	    return resultInfo;
	}
	try {
	    Map data = new HashMap<>();

	    // 获取流水信息
	    CapitalBill bill = new CapitalBill();
	    bill.setCapitalBillId(capitalBillId);

	    CapitalBillVo capitalBillVo = new CapitalBillVo();

	    // 定义反序列化 数据格式
	    final TypeReference<ResultInfo<CapitalBillVo>> TypeRef = new TypeReference<ResultInfo<CapitalBillVo>>() {
	    };
	    String url = httpUrl + "finance/capitalbill/getcapitalbillbyid.htm";
	    ResultInfo<?> apiResult = null;
	    try {
		apiResult = (ResultInfo<?>) HttpClientUtils.post(url, bill, clientId, clientKey, TypeRef);
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	    if (null != apiResult && apiResult.getCode().equals(0)) {
		capitalBillVo = (CapitalBillVo) apiResult.getData();
		// (TRADER_TYPE:1收入||3转入||4转移 )&&BUSSINESS_TYPE:526订单收款 状态下同步
		if ((capitalBillVo.getTraderType().equals(1) || capitalBillVo.getTraderType().equals(3)
			|| capitalBillVo.getTraderType().equals(4)) && capitalBillVo.getBussinessType().equals(526)) {

		    data.put("saleorder_no", capitalBillVo.getOrderNo());// 销售订单单号
		    data.put("log_id", capitalBillVo.getCapitalBillId());// 付款ID
		    data.put("buyorder_id", 0);// 采购单ID
		    data.put("pay_userid", capitalBillVo.getCreator());// 付款人
		    data.put("pay_time", capitalBillVo.getAddTime() != null && capitalBillVo.getAddTime() > 0
			    ? DateUtil.convertString(capitalBillVo.getAddTime(), "yyyy-MM-dd HH:mm:ss") : "");// 付款时间
		    data.put("pay_total", capitalBillVo.getAmount());// 付款金额
		    data.put("pay_currency", 1);// 伙伴单位
		    data.put("pay_type", capitalBillVo.getTraderSubject());// 打款方式：1公司
									   // 2个人
		    data.put("pay_name", capitalBillVo.getPayer());// 打款名称
		    data.put("amount_type", capitalBillVo.getTraderType().equals(4) ? 2 : 1);// 1订单支付2信用还款
		    data.put("remark", capitalBillVo.getComments());// 备注
		    data.put("pay_amount", capitalBillVo.getPaymentAmount());// 已付款金额

		    try {
			// 信息同步
			XmlExercise xmlExercise = new XmlExercise();
			String dataXmlStr = xmlExercise.mapToXml(data, "data");
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(vedengNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("paylogSync");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { dataXmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(vedengApiUrl, "paylogSync");
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			Map result = xmlExercise.xmlToMap(xmlString);

			if (null != result && result.get("code").equals("0")) {
			    resultInfo.setCode(0);
			    resultInfo.setMessage("操作成功");
			}
		    } catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		    }

		    // 同步日志
		    DataSyncStatus dataSyncStatus = new DataSyncStatus();
		    Integer syncStatus = 0;
		    if (resultInfo.getCode().equals(0)) {
			syncStatus = 1;
		    }
		    dataSyncStatus.setGoalType(631);// 网站
		    dataSyncStatus.setStatus(syncStatus);
		    dataSyncStatus.setSourceTable("T_CAPITAL_BILL");
		    dataSyncStatus.setRelatedId(capitalBillId);
		    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
		    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
		}
	    }

	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}

    }

    @Override
    public ResultInfo invoiceSync(Integer invoiceId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (invoiceId == null || invoiceId <= 0) {
	    return resultInfo;
	}
	try {
	    Map data = new HashMap<>();
	    List detailDataMap = new ArrayList<>();// 数据

	    Invoice invoice = new Invoice();
	    invoice.setType(505);
	    invoice.setInvoiceId(invoiceId);

	    // 定义反序列化 数据格式
	    final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
	    };
	    String url = httpUrl + "finance/invoice/getinvoiceinfobyid.htm";
	    ResultInfo<?> apiResult = null;
	    try {
		apiResult = (ResultInfo<?>) HttpClientUtils.post(url, invoice, clientId, clientKey, TypeRef);
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }

	    if (apiResult.getCode().equals(0)) {
		Map<String, Object> apiMap = (Map<String, Object>) apiResult.getData();

		Invoice invoiceBaseInfo = null;
		if (apiMap.containsKey("inovice")) {
		    JSONObject jsonObject = JSONObject.fromObject(apiMap.get("inovice"));
		    invoiceBaseInfo = (Invoice) jsonObject.toBean(jsonObject, Invoice.class);
		}

		List<InvoiceDetail> invoiceDetails = new ArrayList<>();
		if (apiMap.containsKey("inoviceDetails")) {
		    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(apiMap.get("inoviceDetails"));
		    invoiceDetails = (List<InvoiceDetail>) json.toCollection(json, InvoiceDetail.class);
		}

		if (invoiceBaseInfo == null) {
		    return resultInfo;
		}

		data.put("saleorder_no", invoiceBaseInfo.getSaleorderNo());//
		data.put("invoice_id", invoiceBaseInfo.getInvoiceId());//
		data.put("customer_id", invoiceBaseInfo.getTraderId());//
		data.put("supply_id", 0);//
		data.put("invoice_title", invoiceBaseInfo.getTraderName());//
		data.put("invoice_no", invoiceBaseInfo.getInvoiceNo());//
		data.put("invoice_type", invoiceBaseInfo.getInvoiceType() - 507);//
		data.put("ratio", Double.valueOf(invoiceBaseInfo.getRatio().toString()) * 100);//
		data.put("red_blue", invoiceBaseInfo.getColorType().equals(1) ? "red" : "blue");//
		data.put("is_valid", invoiceBaseInfo.getValidStatus().equals(1) ? 1 : 0);//
		data.put("total", invoiceBaseInfo.getAmount());//
		data.put("date", DateUtil.convertString(invoiceBaseInfo.getAddTime(), "yyyy-MM-dd"));//
		data.put("details", "");//
		data.put("invoice_print_status", invoiceBaseInfo.getInvoicePrintStatus());//
		data.put("invoice_cancel_status", invoiceBaseInfo.getInvoiceCancelStatus());//
		data.put("invoice_href", invoiceBaseInfo.getInvoiceHref());//

		detailDataMap.add(new HashMap<>());
		if (null != invoiceDetails && invoiceDetails.size() > 0) {
		    for (InvoiceDetail detail : invoiceDetails) {
			Map<String, Object> detailMap = new HashMap<>();
			detailMap.put("item_id", detail.getGoodsId());
			detailMap.put("price", detail.getPrice());
			detailMap.put("num", detail.getNum());
			detailMap.put("total_amount", detail.getTotalAmount());

			detailDataMap.add(detailMap);
		    }
		}

		// ===================以下调用接口==================
		XmlExercise xmlExercise = new XmlExercise();
		String dataXmlStr = xmlExercise.mapToXml(data, "data");
		String goodsDataXmlStr = xmlExercise.listToXml(detailDataMap, "data", "items");

		// 调用接口
		RPCServiceClient ser = new RPCServiceClient();
		Options options = ser.getOptions();

		// 指定调用WebService的URL
		EndpointReference targetEPR = new EndpointReference(vedengNamespace);
		options.setTo(targetEPR);
		// options.setAction("命名空间/WS 方法名");
		options.setAction("invoiceSync");

		// 指定sfexpressService方法的参数值
		Object[] opAddEntryArgs = new Object[] { dataXmlStr, goodsDataXmlStr };
		// 指定sfexpressService方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { String.class };
		// 指定要调用的sfexpressService方法及WSDL文件的命名空间
		QName opAddEntry = new QName(vedengApiUrl, "invoiceSync");
		// 调用sfexpressService方法并输出该方法的返回值
		Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

		// 接口返回xml字符串
		String xmlString = str[0].toString();
		Map result = xmlExercise.xmlToMap(xmlString);

		if (null != result && result.get("code").equals("0")) {
		    resultInfo.setCode(0);
		    resultInfo.setMessage("操作成功");
		}
		// 同步日志
		DataSyncStatus dataSyncStatus = new DataSyncStatus();
		Integer syncStatus = 0;
		if (resultInfo.getCode().equals(0)) {
		    syncStatus = 1;
		}
		dataSyncStatus.setGoalType(631);// 网站
		dataSyncStatus.setStatus(syncStatus);
		dataSyncStatus.setSourceTable("T_INVOICE");
		dataSyncStatus.setRelatedId(invoiceId);
		dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
		dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    }

	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo expressSync(Integer expressId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (expressId == null || expressId <= 0) {
	    return resultInfo;
	}
	try {
	    Map data = new HashMap<>();
	    List detailData = new ArrayList<>();

	    Express express = new Express();
	    express.setExpressId(expressId);

	    // 定义反序列化 数据格式
	    final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
	    };
	    String url = httpUrl + "logistics/express/getexpressinfobyid.htm";
	    ResultInfo<?> apiResult = null;
	    try {
		apiResult = (ResultInfo<?>) HttpClientUtils.post(url, express, clientId, clientKey, TypeRef);
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }

	    if (apiResult.getCode().equals(0)) {
		Map<String, Object> apiMap = (Map<String, Object>) apiResult.getData();
		JSONObject jsonObject = JSONObject.fromObject(apiMap.get("express"));
		Express expressInfo = (Express) JSONObject.toBean(jsonObject, Express.class);

		JSONArray jsonArray = JSONArray.fromObject(apiMap.get("expressDetails"));
		List<ExpressDetail> expressDetails = (List<ExpressDetail>) JSONArray.toCollection(jsonArray,
			ExpressDetail.class);

		detailData.add(new HashMap<>());
		String order_type = "";
		String order_no = "";
		for (ExpressDetail detail : expressDetails) {
		    Map detailMap = new HashMap<>();
		    switch (detail.getBusinessType()) {
		    case 496:
			order_type = "sale";
			detailMap.put("item_id", detail.getGoodsId());
			break;
		    case 497:
			order_type = "invoice";
			break;
		    }
		    detailMap.put("order_detail_type", order_type);// 'buy','sale','invoice'
		    detailMap.put("order_detail_id", detail.getRelatedId());
		    detailMap.put("remark", "");
		    detailMap.put("num", detail.getNum());

		    detailData.add(detailMap);

		    order_no = detail.getRelatedNo();
		}

		if (expressInfo.getLogisticsNo() == null || expressInfo.getLogisticsNo().equals("")) {
		    return resultInfo;
		}
		data.put("order_no", order_no);//
		data.put("eps_id", expressInfo.getExpressId());//
		data.put("order_type", order_type);// 'buy','sale','invoice'
		data.put("order_id", expressInfo.getRelatedId());//
		data.put("eps_agent", expressInfo.getLogisticsId());//
		data.put("eps_no", expressInfo.getLogisticsNo());//
		data.put("eps_from", "");//
		data.put("eps_to", "");//
		data.put("eps_send_date", DateUtil.convertString(expressInfo.getDeliveryTime(), "yyyy-MM-dd"));//
		data.put("eps_receive_date", DateUtil.convertString(expressInfo.getArrivalTime(), "yyyy-MM-dd"));//
		data.put("eps_freight", 0);//
		data.put("remark", expressInfo.getLogisticsComments());//
		data.put("is_del", 0);//

		// ===================以下调用接口==================
		XmlExercise xmlExercise = new XmlExercise();
		String dataXmlStr = xmlExercise.mapToXml(data, "data");
		String goodsDataXmlStr = xmlExercise.listToXml(detailData, "data", "items");

		// 调用接口
		RPCServiceClient ser = new RPCServiceClient();
		Options options = ser.getOptions();

		// 指定调用WebService的URL
		EndpointReference targetEPR = new EndpointReference(vedengNamespace);
		options.setTo(targetEPR);
		// options.setAction("命名空间/WS 方法名");
		options.setAction("expressSync");

		// 指定sfexpressService方法的参数值
		Object[] opAddEntryArgs = new Object[] { dataXmlStr, goodsDataXmlStr };
		// 指定sfexpressService方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { String.class };
		// 指定要调用的sfexpressService方法及WSDL文件的命名空间
		QName opAddEntry = new QName(vedengApiUrl, "expressSync");
		// 调用sfexpressService方法并输出该方法的返回值
		Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

		// 接口返回xml字符串
		String xmlString = str[0].toString();
		Map result = xmlExercise.xmlToMap(xmlString);

		if (null != result && result.get("code").equals("0")) {
		    resultInfo.setCode(0);
		    resultInfo.setMessage("操作成功");
		}
		// 同步日志
		DataSyncStatus dataSyncStatus = new DataSyncStatus();
		Integer syncStatus = 0;
		if (resultInfo.getCode().equals(0)) {
		    syncStatus = 1;
		}
		dataSyncStatus.setGoalType(631);// 网站
		dataSyncStatus.setStatus(syncStatus);
		dataSyncStatus.setSourceTable("T_EXPRESS");
		dataSyncStatus.setRelatedId(expressId);
		dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
		dataSyncStatusService.addDataSyncStatus(dataSyncStatus);

	    }

	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo goodsSync(Integer goodsId) {
		System.out.println("k开始填充数据："+goodsId);
	ResultInfo resultInfo = new ResultInfo<>();
	if (goodsId == null || goodsId <= 0) {
	    return resultInfo;
	}
	Map data = new HashMap<>();

	Map itemInfoData = new HashMap<>();
	List attrData = new ArrayList<>();
	List packagboxData = new ArrayList<>();
	List relationboxData = new ArrayList<>();
	List attachmentData = new ArrayList<>();

	data.put("goods_id", goodsId);// 产品ID

	Goods goods = new Goods();
	goods.setGoodsId(goodsId);
	// 定义反序列化 数据格式
	final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
	};
	String url = httpUrl + "goods/getgoodssyncinfo.htm";
	ResultInfo<?> apiResult = null;
	try {
	    apiResult = (ResultInfo<?>) HttpClientUtils.post(url, goods, clientId, clientKey, TypeRef);
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	Map<String, Object> goodsMap = (Map<String, Object>) apiResult.getData();

	Goods goodsVo = new Goods();
	// 产品基本信息=====start
	if (goodsMap.containsKey("goodsInfo")) {
	    net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(goodsMap.get("goodsInfo"));
	    goodsVo = (Goods) json.toBean(json, Goods.class);
	}

	itemInfoData.put("goods_id", goodsId);
	itemInfoData.put("goods_name", goodsVo.getGoodsName());
	itemInfoData.put("brand_id", goodsVo.getBrandId());
	itemInfoData.put("goods_zzssn", goodsVo.getModel());
	itemInfoData.put("material_code", goodsVo.getMaterialCode());
	itemInfoData.put("cat_id", goodsVo.getCategoryId());// 分类id
	itemInfoData.put("unit_it", goodsVo.getUnitId());
	itemInfoData.put("register_no", goodsVo.getRegistrationNumber());// 注册证号
	itemInfoData.put("record_number", goodsVo.getRecordNumber());// 备案编号
	itemInfoData.put("performance_parameter", goodsVo.getPerformanceParameter());// 性能参数
	itemInfoData.put("spec_parameter", goodsVo.getSpecParameter());// 规格参数
	Integer type = 0;
	switch (goodsVo.getGoodsType()) {// 商品类型
	case 316:
	    type = 1;
	    break;
	case 317:
	    type = 2;
	    break;
	case 318:
	    type = 3;
	    break;
	case 319:
	    type = 4;
	    break;
	case 653:
	    type = 5;
	    break;
	}

	itemInfoData.put("type", type);// 1设备2耗材3试剂4其它5高值耗材

	Integer medical_category_id = 0;
	if (goodsVo.getManageCategory()!=null&&goodsVo.getManageCategory() > 0) {
	    medical_category_id = goodsVo.getManageCategory() - 193;
	}
	itemInfoData.put("medical_category_id", medical_category_id);// 旧医疗器械类别

	itemInfoData.put("tax_category_no", goodsVo.getTaxCategoryNo());
	itemInfoData.put("goods_tiji_l", goodsVo.getGoodsLength());
	itemInfoData.put("goods_tiji_w", goodsVo.getGoodsWidth());
	itemInfoData.put("goods_tiji_h", goodsVo.getGoodsHeight());
	itemInfoData.put("gross_weight", goodsVo.getGrossWeight());
	itemInfoData.put("goods_weight", goodsVo.getNetWeight());
	itemInfoData.put("goods_desc_attrs", goodsVo.getTechnicalParameter());
	itemInfoData.put("package_length", goodsVo.getPackageLength());
	itemInfoData.put("package_width", goodsVo.getPackageWidth());
	itemInfoData.put("package_height", goodsVo.getPackageHeight());

	OrdergoodsGoodsVo ordergoodsGoodsVo = new OrdergoodsGoodsVo();
	ordergoodsGoodsVo.setGoodsId(goodsId);
	ordergoodsGoodsVo.setOrdergoodsStoreId(1);
	OrdergoodsGoodsVo ordergoodsGoods = ordergoodsGoodsMapper.getOrdergoodsGoods(ordergoodsGoodsVo);
	Integer isOrdergoods = 0;
	if (null != ordergoodsGoods) {
	    isOrdergoods = 1;
	}
	itemInfoData.put("is_ordergoods", isOrdergoods);// 是否订货产品
	itemInfoData.put("medical_effective_starttime", 0);
	itemInfoData.put("medical_effective_endtime", 0);

	Integer itemType = 0;
	if (null != goodsVo.getManageCategoryLevel()) {
	    switch (goodsVo.getManageCategoryLevel()) {
	    case 339:
		itemType = 1;
		break;
	    case 340:
		itemType = 2;
		break;
	    case 341:
		itemType = 3;
		break;
	    }
	}
	itemInfoData.put("item_type", itemType);// 产品类别1：一类2：二类3：三类0：无
	itemInfoData.put("is_discard", goodsVo.getIsDiscard());

	itemInfoData.put("packing_list", goodsVo.getPackingList());
	itemInfoData.put("spec", goodsVo.getSpec());
	itemInfoData.put("product_address", goodsVo.getSpec());
	itemInfoData.put("supply_model", goodsVo.getSupplyModel());
	itemInfoData.put("standard_category_id", goodsVo.getStandardCategoryId());
	itemInfoData.put("standard_category_level", goodsVo.getStandardCategoryLevel());

	SysOptionDefinition sysOptionDefinition = getSysOptionDefinitionById(goodsVo.getStandardCategoryLevel());
	String standard_category_level_name = "";
	if (null != sysOptionDefinition) {
	    standard_category_level_name = sysOptionDefinition.getTitle();
	}
	itemInfoData.put("standard_category_level_name", standard_category_level_name);

	data.put("item_info", itemInfoData);// 产品主表

	// 产品基本信息=====end

	// 产品属性=====start
	List<GoodsAttribute> attributes = new ArrayList<>();
	if (goodsMap.containsKey("goodsAttr")) {
	    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(goodsMap.get("goodsAttr"));
	    attributes = (List<GoodsAttribute>) json.toCollection(json, GoodsAttribute.class);
	}

	if (null != attributes && attributes.size() > 0) {
	    attrData.add(new HashMap<>());
	    for (GoodsAttribute attribute : attributes) {
		Map attributeMap = new HashMap<>();

		attributeMap.put("item_id", goodsId);
		attributeMap.put("category_attribute_id", attribute.getCategoryAttributeId());
		attributeMap.put("input_type", attribute.getInputType());
		attributeMap.put("attr_name", attribute.getCategoryAttributeName());
		attributeMap.put("attr_value", attribute.getAttrValue());
		attributeMap.put("add_time", attribute.getAddTime() != null && attribute.getAddTime() > 0
			? String.format("%010d", attribute.getAddTime() / 1000) : 0);

		attrData.add(attributeMap);
	    }
	}
	data.put("attribute_info", attrData);// 产品属性

	// 产品属性=====end

	// 产品扩展=====start
	GoodsExtend goodsExtend = null;
	if (goodsMap.containsKey("goodsExtend")) {
	    JSONObject jsonObject = JSONObject.fromObject(goodsMap.get("goodsExtend"));
	    goodsExtend = (GoodsExtend) jsonObject.toBean(jsonObject, GoodsExtend.class);
	}
	if (null != goodsExtend) {
	    itemInfoData.put("havaExtend", 1);
	    itemInfoData.put("customer_names", goodsExtend.getCustomerNames());
	    itemInfoData.put("selling_words", goodsExtend.getSellingWords());
	    itemInfoData.put("market_strategy", goodsExtend.getMarketStrategy());
	    itemInfoData.put("competitive_analysis", goodsExtend.getCompetitiveAnalysis());
	    itemInfoData.put("promotion_policy", goodsExtend.getPromotionPolicy());

	    itemInfoData.put("warranty_period", goodsExtend.getWarrantyPeriod());
	    itemInfoData.put("warranty_period_rule", goodsExtend.getWarrantyPeriodRule());
	    itemInfoData.put("warranty_repair_fee", goodsExtend.getWarrantyRepairFee());
	    itemInfoData.put("response_time", goodsExtend.getResponseTime());
	    itemInfoData.put("have_standby_machine", goodsExtend.getHaveStandbyMachine());

	    itemInfoData.put("conditions", goodsExtend.getConditions());
	    itemInfoData.put("extended_warranty_fee", goodsExtend.getExtendedWarrantyFee());
	    itemInfoData.put("is_refund", goodsExtend.getIsRefund());
	    itemInfoData.put("exchange_conditions", goodsExtend.getExchangeConditions());
	    itemInfoData.put("exchange_mode", goodsExtend.getExchangeMode());

	    itemInfoData.put("freight_description", goodsExtend.getFreightDescription());
	    itemInfoData.put("delivery", goodsExtend.getDelivery());
	    itemInfoData.put("futures_delivery", goodsExtend.getFuturesDelivery());
	    itemInfoData.put("transport_requirements", goodsExtend.getTransportRequirements());
	    itemInfoData.put("transport_weight", goodsExtend.getTransportWeight());

	    itemInfoData.put("is_hvae_freight", goodsExtend.getIsHvaeFreight());
	    itemInfoData.put("transportation_completion_standard", goodsExtend.getTransportationCompletionStandard());
	    itemInfoData.put("acceptance_notice", goodsExtend.getAcceptanceNotice());
	    itemInfoData.put("packing_number", goodsExtend.getPackingNumber());
	    itemInfoData.put("packing_quantity", goodsExtend.getPackingQuantity());
	    itemInfoData.put("aftersales_content", goodsExtend.getAftersalesContent());
	    itemInfoData.put("special_transport_conditions", goodsExtend.getSpecialTransportConditions());
	} else {
	    itemInfoData.put("havaExtend", 0);
	}
	// 产品扩展=====end

	// 产品附件(宣传视频、彩页等)=====start
	List<Attachment> goodsAttachment = new ArrayList<>();
	if (goodsMap.containsKey("goodsAttachment")) {
	    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(goodsMap.get("goodsAttachment"));
	    goodsAttachment = (List<Attachment>) json.toCollection(json, Attachment.class);
	}

	List<GoodsAttachment> goodsAttachments = new ArrayList<>();
	if (goodsMap.containsKey("goodsAttachmentList")) {
	    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(goodsMap.get("goodsAttachmentList"));
	    goodsAttachments = (List<GoodsAttachment>) json.toCollection(json, GoodsAttachment.class);
	}

	if ((null != goodsAttachment && goodsAttachment.size() > 0)
		|| (null != goodsAttachments && goodsAttachments.size() > 0)) {
	    attachmentData.add(new HashMap<>());
	}
	if (null != goodsAttachment && goodsAttachment.size() > 0) {
	    for (Attachment attachment : goodsAttachment) {
		Map attachmentMap = new HashMap<>();
		attachmentMap.put("attachment_type", attachment.getAttachmentType());
		attachmentMap.put("attachment_function", attachment.getAttachmentFunction());
		attachmentMap.put("related_id", attachment.getRelatedId());
		attachmentMap.put("name", attachment.getName());
		attachmentMap.put("domain", attachment.getDomain());
		attachmentMap.put("uri", attachment.getUri());
		attachmentMap.put("alt", attachment.getAlt());
		attachmentMap.put("sort", attachment.getSort());
		attachmentMap.put("is_default", attachment.getIsDefault());
		attachmentMap.put("add_time", attachment.getAddTime() != null && attachment.getAddTime() > 0
			? String.format("%010d", attachment.getAddTime() / 1000) : 0);

		attachmentData.add(attachmentMap);
	    }
	}

	if (null != goodsAttachments && goodsAttachments.size() > 0) {
	    for (GoodsAttachment attachment : goodsAttachments) {
		Map attachmentMap = new HashMap<>();
		attachmentMap.put("attachment_type", 460);
		attachmentMap.put("attachment_function", attachment.getAttachmentType());
		attachmentMap.put("related_id", attachment.getGoodsId());
		if (attachment.getAttachmentType().equals(344)) {
		    attachmentMap.put("name", goodsVo.getSku() + "注册证");
		}
		if (attachment.getAttachmentType().equals(680)) {
		    attachmentMap.put("name", goodsVo.getSku() + "备案文件");
		}
		attachmentMap.put("domain", attachment.getDomain());
		attachmentMap.put("uri", attachment.getUri());
		attachmentMap.put("alt", attachment.getAlt());
		attachmentMap.put("sort", attachment.getSort());
		attachmentMap.put("is_default", attachment.getIsDefault());
		attachmentMap.put("add_time", 0);

		attachmentData.add(attachmentMap);
	    }
	}
	data.put("goods_attachment", attachmentData);
	// 产品附件(宣传视频、彩页等)=====end

	// 产品配件====start
	List<GoodsPackage> goodsPackages = new ArrayList<>();
	if (goodsMap.containsKey("goodsPackages")) {
	    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(goodsMap.get("goodsPackages"));
	    goodsPackages = (List<GoodsPackage>) json.toCollection(json, GoodsPackage.class);
	}

	if (null != goodsPackages && goodsPackages.size() > 0) {
	    packagboxData.add(new HashMap<>());
	    for (GoodsPackage goodsPackage : goodsPackages) {
		Map packMap = new HashMap<>();

		packMap.put("parent_item_id", goodsId);// 主产品ID
		packMap.put("item_id", goodsPackage.getGoodsId());// 配件ID
		packMap.put("package_price", goodsPackage.getPackagePrice());
		packMap.put("is_standard", goodsPackage.getIsStandard());
		packMap.put("add_time", goodsPackage.getAddTime() != null && goodsPackage.getAddTime() > 0
			? String.format("%010d", goodsPackage.getAddTime() / 1000) : 0);

		packagboxData.add(packMap);
	    }
	}
	data.put("packagbox_info", packagboxData);// 产品配件
	// 产品配件====end

	// 产品关联产品====start
	List<GoodsRecommend> goodsRecommends = new ArrayList<>();
	if (goodsMap.containsKey("goodsRecommends")) {
	    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(goodsMap.get("goodsRecommends"));
	    goodsRecommends = (List<GoodsRecommend>) json.toCollection(json, GoodsRecommend.class);
	}

	if (null != goodsRecommends && goodsRecommends.size() > 0) {
	    relationboxData.add(new HashMap<>());
	    for (GoodsRecommend recommend : goodsRecommends) {
		Map recommendMap = new HashMap<>();

		recommendMap.put("parent_item_id", goodsId);// 主产品ID
		recommendMap.put("item_id", recommend.getGoodsId());// 相关产品ID
		recommendMap.put("add_time", recommend.getAddTime() != null && recommend.getAddTime() > 0
			? String.format("%010d", recommend.getAddTime() / 1000) : 0);

		relationboxData.add(recommendMap);
	    }
	}
	data.put("relationbox_info", relationboxData);// 产品相关
	// 产品关联产品====end
		System.out.println("k开始同步"+goodsId);
	// ==========接口调用 start==============
	try {
	    // 信息同步
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("itemSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "itemSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	}

	// ==========接口调用 end==============

	// ======同步日志start======
	DataSyncStatus dataSyncStatus = new DataSyncStatus();
	Integer syncStatus = 0;
	if (resultInfo.getCode().equals(0)) {
	    syncStatus = 1;
	}
	dataSyncStatus.setGoalType(631);// 网站
	dataSyncStatus.setStatus(syncStatus);
	dataSyncStatus.setSourceTable("T_GOODS");
	dataSyncStatus.setRelatedId(goodsId);
	dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
//	dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	// ======同步日志end======
	return resultInfo;
    }

    @Override
    public ResultInfo accountSync(Integer accountId) {
	return null;
    }

    @Override
    public ResultInfo webAccountRestPasswordSync(WebAccount webAccount) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (null == webAccount) {
	    return resultInfo;
	}
	WebAccountVo account = webAccountMapper.getWebAccount(webAccount);
	if (null != account) {
	    Map data = new HashMap<>();// 数据
	    String accountPassport = this.randomkeys(6);
	    data.put("password", accountPassport);
	    data.put("uuid", account.getUuid());

	    try {
		// 信息同步
		XmlExercise xmlExercise = new XmlExercise();
		String dataXmlStr = xmlExercise.mapToXml(data, "data");
		// 调用接口
		RPCServiceClient ser = new RPCServiceClient();
		Options options = ser.getOptions();

		// 指定调用WebService的URL
		EndpointReference targetEPR = new EndpointReference(passportNamespace);
		options.setTo(targetEPR);
		// options.setAction("命名空间/WS 方法名");
		options.setAction("updateAccount");

		// 指定sfexpressService方法的参数值
		Object[] opAddEntryArgs = new Object[] { dataXmlStr };
		// 指定sfexpressService方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { String.class };
		// 指定要调用的sfexpressService方法及WSDL文件的命名空间
		QName opAddEntry = new QName(passportApiUrl, "updateAccount");
		// 调用sfexpressService方法并输出该方法的返回值
		Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

		// 接口返回xml字符串
		String xmlString = str[0].toString();
		Map result = xmlExercise.xmlToMap(xmlString);

		if (null != result && result.get("code").equals("1")) {
		    net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(result.get("data"));
		    List<SsoAccount> ssoAccountList = (List<SsoAccount>) json.toCollection(json, SsoAccount.class);
		    SsoAccount ssoAccount = ssoAccountList.get(0);
		    // 同步web

		    Map webData = new HashMap<>();// 数据

		    Map accountMap = new HashMap<>(); // 账号信息
		    accountMap.put("mod_time", ssoAccount.getMod_time());
		    accountMap.put("password", accountPassport);
		    webData.put("account", accountMap);

		    xmlExercise = new XmlExercise();
		    dataXmlStr = xmlExercise.mapToXml(webData, "data");
		    // 调用接口
		    ser = new RPCServiceClient();
		    options = ser.getOptions();

		    targetEPR = new EndpointReference(vedengNamespace);
		    options.setTo(targetEPR);
		    // options.setAction("命名空间/WS 方法名");
		    options.setAction("accountSync");

		    // 指定sfexpressService方法的参数值
		    opAddEntryArgs = new Object[] { dataXmlStr, account.getUuid() };
		    // 指定sfexpressService方法返回值的数据类型的Class对象
		    classes = new Class[] { String.class };
		    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
		    opAddEntry = new QName(vedengApiUrl, "accountSync");
		    // 调用sfexpressService方法并输出该方法的返回值
		    str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

		    // 接口返回xml字符串
		    xmlString = str[0].toString();
		    result = xmlExercise.xmlToMap(xmlString);

		    if (null != result && result.get("code").equals("0")) {
			// 同步web
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		    }
		}
	    } catch (Exception e) {
		logger.error(Contant.ERROR_MSG, e);
	    }
	}
	return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 随机数
     * 
     * @param length
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2018年1月11日 下午3:10:36
     */
    private String randomkeys(Integer length) {
	String number = "a2b3c4d5e6f7g8h9i2j3k4m5n6p7q8r9s2t3u4v5w6x7y8";
	String keys = "";
	for (int i = 0; i < length; i++) {
	    int max = 40;
	    int min = 0;
	    Random random = new Random();

	    int s = random.nextInt(max) % (max - min + 1) + min;

	    keys += number.substring(s, s + 1);
	}

	return keys;
    }

    @Override
    public ResultInfo brandSync(Integer brandId, Boolean isDel) {
	ResultInfo resultInfo = new ResultInfo<>();

	if (brandId == null || brandId <= 0) {
	    return resultInfo;
	}
	Map data = new HashMap<>();

	if (isDel) {
	    data.put("brand_id", brandId);
	    data.put("is_del", 1);
	} else {
	    Brand brandInfo = null;
	    Brand brand = new Brand();
	    brand.setBrandId(brandId);
	    // 定义反序列化 数据格式
	    final TypeReference<ResultInfo<Brand>> TypeRef = new TypeReference<ResultInfo<Brand>>() {
	    };
	    String url = httpUrl + "goods/brand/getbrandbykey.htm";

	    ResultInfo<?> result;
	    try {
		result = (ResultInfo<?>) HttpClientUtils.post(url, brand, clientId, clientKey, TypeRef);
		brandInfo = (Brand) result.getData();
	    } catch (IOException e) {
		logger.error(Contant.ERROR_MSG, e);
	    }

	    if (null != brandInfo) {
		data.put("brand_id", brandId);
		data.put("brand_name", brandInfo.getBrandName());
		// data.put("initial", brandInfo.getInitialEn());//首字母单词(英文)
		// data.put("initial_1", brandInfo.getInitialCn());//首字母单词(中文)
		data.put("brand_nature", brandInfo.getBrandNature());// 品牌性质 1国产
								     // 2进口
		// data.put("owner", brandInfo.getOwner());//品牌商
		// data.put("logo_domain", brandInfo.getLogoDomain());//商标图片域名
		// data.put("logo", brandInfo.getLogoUri());//商标图片地址
		// data.put("url", brandInfo.getBrandWebsite());//品牌链接地址
		data.put("order_no", brandInfo.getSort());// 排序
		data.put("desc", brandInfo.getDescription());// 描述
		data.put("from", 1);// 1后台添加2用户添加
		data.put("source_id", brandId);// ERP 品牌ID
	    } else {
		return resultInfo;
	    }
	}

	try {
	    // 信息同步
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("brandSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "brandSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map resultMap = xmlExercise.xmlToMap(xmlString);

	    if (null != resultMap && resultMap.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	}

	// 同步日志
	DataSyncStatus dataSyncStatus = new DataSyncStatus();
	Integer syncStatus = 0;
	if (resultInfo.getCode().equals(0)) {
	    syncStatus = 1;
	}
	dataSyncStatus.setGoalType(631);// 网站
	dataSyncStatus.setStatus(syncStatus);
	dataSyncStatus.setSourceTable("T_BRAND");
	dataSyncStatus.setRelatedId(brandId);
	dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	return resultInfo;
    }

    @Override
    public ResultInfo unitSync(Integer unitId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (unitId == null || unitId <= 0) {
	    return resultInfo;
	}
	Map data = new HashMap<>();
	Unit unitInfo = null;
	Unit unit = new Unit();
	unit.setUnitId(unitId);
	// 定义反序列化 数据格式
	final TypeReference<ResultInfo<Unit>> TypeRef = new TypeReference<ResultInfo<Unit>>() {
	};
	String url = httpUrl + "goods/unit/getunitbyid.htm";
	try {
	    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, unit, clientId, clientKey, TypeRef);
	    unitInfo = (Unit) result.getData();
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG, e);
	}

	if (null != unitInfo) {
	    data.put("unit_id", unitId);
	    data.put("unit_group_id", unitInfo.getUnitGroupId());
	    data.put("name", unitInfo.getUnitName());
	    data.put("name_en", unitInfo.getUnitNameEn());
	    data.put("is_del", unitInfo.getIsDel());

	    try {
		// 信息同步
		XmlExercise xmlExercise = new XmlExercise();
		String dataXmlStr = xmlExercise.mapToXml(data, "data");
		// 调用接口
		RPCServiceClient ser = new RPCServiceClient();
		Options options = ser.getOptions();

		// 指定调用WebService的URL
		EndpointReference targetEPR = new EndpointReference(vedengNamespace);
		options.setTo(targetEPR);
		// options.setAction("命名空间/WS 方法名");
		options.setAction("unitSync");

		// 指定sfexpressService方法的参数值
		Object[] opAddEntryArgs = new Object[] { dataXmlStr };
		// 指定sfexpressService方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { String.class };
		// 指定要调用的sfexpressService方法及WSDL文件的命名空间
		QName opAddEntry = new QName(vedengApiUrl, "unitSync");
		// 调用sfexpressService方法并输出该方法的返回值
		Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

		// 接口返回xml字符串
		String xmlString = str[0].toString();
		Map resultMap = xmlExercise.xmlToMap(xmlString);

		if (null != resultMap && resultMap.get("code").equals("0")) {
		    resultInfo.setCode(0);
		    resultInfo.setMessage("操作成功");
		}
	    } catch (Exception e) {
		logger.error(Contant.ERROR_MSG, e);
	    }
	}

	// 同步日志
	DataSyncStatus dataSyncStatus = new DataSyncStatus();
	Integer syncStatus = 0;
	if (resultInfo.getCode().equals(0)) {
	    syncStatus = 1;
	}
	dataSyncStatus.setGoalType(631);// 网站
	dataSyncStatus.setStatus(syncStatus);
	dataSyncStatus.setSourceTable("T_UNIT");
	dataSyncStatus.setRelatedId(unitId);
	dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	return resultInfo;
    }

    @Override
    public ResultInfo quoteorderSync(Integer quoteorderId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (quoteorderId == null || quoteorderId <= 0) {
	    return resultInfo;
	}
	XmlExercise xmlExercise = new XmlExercise();
	Quoteorder quoteorder = new Quoteorder();

	// 报价信息
	quoteorder = quoteService.getQuoteorderForSync(quoteorderId);

	if (null == quoteorder) {
	    return resultInfo;
	}

	Map data = new HashMap<>();// 订单数据
	data.put("quoteorder_no", quoteorder.getQuoteorderNo());
	data.put("valid_status", quoteorder.getValidStatus());
	data.put("valid_datetime", DateUtil.convertString(quoteorder.getValidTime(), "yyyy-MM-dd HH:mm:ss"));
	data.put("invoice_type", quoteorder.getInvoiceType());
	data.put("invoice_type_name", quoteorder.getInvoiceTypeName());
	data.put("invoice_rate", quoteorder.getInvoiceTypeRate());
	data.put("total_money", quoteorder.getTotalAmount());
	data.put("is_quote", 1);
	data.put("contract_item", quoteorder.getAdditionalClause());
	data.put("price_validperiod", quoteorder.getPeriod());
	data.put("delivery_fright", quoteorder.getFreightDescription());
	data.put("delivery_fright_name", quoteorder.getFreightDescriptionName());

	data.put("customer_employee_name", quoteorder.getTraderContactName());
	data.put("customer_employee_tel", quoteorder.getTelephone());
	data.put("customer_employee_phone", quoteorder.getMobile());

	data.put("payment_type", quoteorder.getPaymentType());
	data.put("payment_type_name", quoteorder.getPaymentTypeStr());
	TraderFinanceVo traderFinanceVo = quoteorder.getTraderFinanceVo();
	String taxNum = "";
	String regAddress = "";
	String regTel = "";
	String bank = "";
	String bankAccount = "";
	if (null != traderFinanceVo) {
	    taxNum = traderFinanceVo.getTaxNum();
	    regAddress = traderFinanceVo.getRegAddress();
	    regTel = traderFinanceVo.getRegTel();
	    bank = traderFinanceVo.getBank();
	    bankAccount = traderFinanceVo.getBankAccount();
	}
	data.put("invoice_no", taxNum);
	data.put("invoice_register_address", regAddress);
	data.put("invoice_register_phone", regTel);
	data.put("invoice_bank", bank);
	data.put("invoice_bank_account", bankAccount);

	UserDetail userInfo = userService.getUserDetailByTraderId(quoteorder.getTraderId(), 1);
	String quote_username = "";
	String quote_tel = "";
	String quote_mobile = "";
	String quote_qq = "";
	String quote_email = "";
	if (userInfo != null) {
	    quote_username = userInfo.getRealName();
	    quote_tel = userInfo.getTelephone();
	    quote_mobile = userInfo.getMobile();
	    quote_qq = userInfo.getQq();
	    quote_email = userInfo.getEmail();
	}
	data.put("quote_username", quote_username);
	data.put("quote_tel", quote_tel);
	data.put("quote_mobile", quote_mobile);
	data.put("quote_qq", quote_qq);
	data.put("quote_email", quote_email);

	// 补充web accountId;
	Integer accountId = 0;
	if (quoteorder.getTraderContactId() != null && quoteorder.getTraderContactId() > 0) {
	    WebAccount webAccount = webAccountMapper.getTraderContactWebInfo(quoteorder.getTraderContactId());
	    if (null != webAccount) {
		accountId = webAccount.getWebAccountId();
	    }
	}
	data.put("account_id", accountId);

	String bussinessChangeNo = "";
	if (null != quoteorder.getBussinessChanceNo() && !quoteorder.getBussinessChanceNo().equals("")) {
	    bussinessChangeNo = quoteorder.getBussinessChanceNo();
	}
	data.put("enquiry_no", bussinessChangeNo);

	List goodsDataMap = new ArrayList<>();// 订单产品数据
	goodsDataMap.add(new HashMap<>());
	List<QuoteorderGoods> quoteorderGoods = quoteorder.getQuoteorderGoods();
	if (null != quoteorderGoods && quoteorderGoods.size() > 0) {
	    for (QuoteorderGoods goods : quoteorderGoods) {
		Map goodsData = new HashMap<>();

		goodsData.put("product_id", goods.getGoodsId());// 商品id
		goodsData.put("product_name", goods.getGoodsName());// 商品名称
		goodsData.put("product_no", goods.getSku());//
		goodsData.put("product_brand", goods.getBrandName());//
		goodsData.put("product_model", goods.getModel());//
		// goodsData.put("product_desc", goods.getGoodsComments());//
		goodsData.put("product_price", goods.getPrice());// 价格
		goodsData.put("product_num", goods.getNum());//
		goodsData.put("product_unit", goods.getUnitName());//
		goodsData.put("remark", goods.getDeliveryCycle());// 货期
		goodsData.put("remark2", goods.getGoodsComments());//

		goodsDataMap.add(goodsData);
	    }
	}

	try {
	    // ===================以下调用接口==================
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    String goodsDataXmlStr = xmlExercise.listToXml(goodsDataMap, "data", "items");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("quoteorderSyncErp");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr, goodsDataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "quoteorderSyncErp");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_QUOTEORDER");
	    dataSyncStatus.setRelatedId(quoteorderId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo regionSync(Integer regionId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (regionId == null || regionId <= 0) {
	    return resultInfo;
	}
	XmlExercise xmlExercise = new XmlExercise();
	Region region = regionService.getRegionByRegionId(regionId);
	if (null == region) {
	    return resultInfo;
	}

	Map data = new HashMap<>();
	data.put("region_id", region.getRegionId());
	data.put("parent_id", region.getParentId());
	data.put("region_name", region.getRegionName());
	data.put("region_type", region.getRegionType());

	try {
	    // ===================以下调用接口==================
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("regionSyncErp");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "regionSyncErp");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_REGION");
	    dataSyncStatus.setRelatedId(regionId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo categorySync(Integer categoryId, Boolean isDel) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (categoryId == null || categoryId <= 0) {
	    return resultInfo;
	}

	Map data = new HashMap<>();
	if (isDel) {
	    data.put("category_id", categoryId);
	    data.put("is_del", 1);
	} else {
	    Category category = new Category();
	    category.setCategoryId(categoryId);
	    Category categoryInfo = categoryService.getCategoryById(category);
	    if (null == categoryInfo) {
		return resultInfo;
	    }
	    data.put("category_id", categoryInfo.getCategoryId());
	    data.put("parent_id", categoryInfo.getParentId());
	    data.put("name", categoryInfo.getCategoryName());
	    data.put("treenodes", categoryInfo.getTreenodes());
	    data.put("status", categoryInfo.getStatus());
	    data.put("level", categoryInfo.getLevel());
	    data.put("order_no", categoryInfo.getSort());
	    data.put("add_time", categoryInfo.getAddTime() != null && categoryInfo.getAddTime() > 0
		    ? String.format("%010d", categoryInfo.getAddTime() / 1000) : 0);
	    data.put("mod_time", categoryInfo.getModTime() != null && categoryInfo.getModTime() > 0
		    ? String.format("%010d", categoryInfo.getModTime() / 1000) : 0);
	}

	try {
	    // ===================以下调用接口==================
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("categorySyncErp");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "categorySyncErp");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_CATEGORY");
	    dataSyncStatus.setRelatedId(categoryId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo orderSyncWeb(Integer saleorderId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (saleorderId == null || saleorderId <= 0) {
	    return resultInfo;
	}
	XmlExercise xmlExercise = new XmlExercise();
	Saleorder saleorder = new Saleorder();
	saleorder.setSaleorderId(saleorderId);
	try {
	    // 订单主表信息
	    SaleorderVo saleorderInfo = saleorderService.getSaleorderForSync(saleorderId);

	    // 订单产品信息
	    List<SaleorderGoods> saleorderGoods = saleorderInfo.getGoodsList();

	    // 订单信息不全不同步
	    if (null == saleorderInfo || null == saleorderGoods || saleorderGoods.size() <= 0) {
		return resultInfo;
	    }

	    Map data = new HashMap<>();// 订单数据
	    List goodsDataMap = new ArrayList<>();// 订单产品数据

	    // ===================以下订单主表信息==================
	    data.put("order_no", saleorderInfo.getSaleorderNo());// 订单号
	    data.put("parent_id", saleorderInfo.getParentId());// 上级订单ID

	    // status订单状态(''0'' =>''未分配，未审核'',''1'' =>''已分配，未审核''，''2''
	    // =>''审核通过''，''3''=>''已完成'',''4''=>''退款'',''-1''=>''审核不通过'',''-2''=>''已作废'
	    Integer status = 1;
	    if (saleorderInfo.getStatus().equals(3)) {
		status = -2;
	    } else {
		if (saleorderInfo.getValidStatus().equals(0)) {
		    status = 1;
		} else if (saleorderInfo.getValidStatus().equals(1)) {
		    status = 2;
		    if (saleorderInfo.getArrivalStatus().equals(2)) {
			status = 3;
		    }
		} else if (saleorderInfo.getValidStatus().equals(2)) {
		    status = -1;
		}
	    }
	    data.put("status", status);// 订单状态
	    data.put("amount", saleorderInfo.getTotalAmount());// 总金额

	    // data.put("add_time", saleorderInfo.getAddTime() != null &&
	    // saleorderInfo.getAddTime() > 0 ? String.format("%010d",
	    // saleorderInfo.getAddTime()/1000) : 0);//下单时间
	    data.put("confirm_time", saleorderInfo.getValidTime() != null && saleorderInfo.getValidTime() > 0
		    ? String.format("%010d", saleorderInfo.getValidTime() / 1000) : 0);// 验证时间
	    data.put("pay_time", saleorderInfo.getPaymentTime() != null && saleorderInfo.getPaymentTime() > 0
		    ? String.format("%010d", saleorderInfo.getPaymentTime() / 1000) : 0);//

	    Integer payStatus = 0;
	    if (saleorderInfo.getPaymentStatus().equals(2)) {
		payStatus = 1;
	    }
	    if (saleorderInfo.getPaymentStatus().equals(1)) {
		payStatus = 2;
	    }
	    data.put("pay_status", payStatus);// 付款状态

	    Integer shippingStatus = 0;
	    if (saleorderInfo.getDeliveryStatus().equals(2)) {
		shippingStatus = 1;
	    }
	    if (saleorderInfo.getDeliveryStatus().equals(1)) {
		shippingStatus = 2;
	    }
	    data.put("shipping_status", shippingStatus);// 发货状态

	    data.put("invoice_status",
		    saleorderInfo.getInvoiceStatus().equals("2") ? 5 : saleorderInfo.getInvoiceStatus());// 0未开票
													 // 5部分开票1已开票

	    BigDecimal totalAmount = saleorderInfo.getTotalAmount();
	    BigDecimal accountPeriodAmount = saleorderInfo.getAccountPeriodAmount();
	    data.put("cash_amount", totalAmount.subtract(accountPeriodAmount));// 现金支付金额
	    data.put("consignee", saleorderInfo.getTakeTraderContactName());// 收货人
	    data.put("mobile_number", saleorderInfo.getTakeTraderContactMobile());// 手机号码
	    data.put("area_id", saleorderInfo.getAreaId());//
	    data.put("address", saleorderInfo.getTakeTraderAddress());// 收货地址 详细
	    data.put("invoice_type_name", saleorderInfo.getInvoiceTypeStr());// 发票类型描述
	    data.put("invoice_title", saleorderInfo.getTraderName());// 发票抬头
	    data.put("is_send_invoice", saleorderInfo.getIsSendInvoice());// 是否寄送发票
									  // 0否
									  // 1是
	    data.put("invoice_method", saleorderInfo.getInvoiceMethod());// 开票方式1手动纸质开票、2自动纸质开票、3自动电子发票

	    TraderFinanceVo traderFinanceVo = saleorderInfo.getTraderFinanceVo();
	    String taxNum = "";
	    String regAddress = "";
	    String regTel = "";
	    String bank = "";
	    String bankAccount = "";
	    if (null != traderFinanceVo) {
		taxNum = traderFinanceVo.getTaxNum();
		regAddress = traderFinanceVo.getRegAddress();
		regTel = traderFinanceVo.getRegTel();
		bank = traderFinanceVo.getBank();
		bankAccount = traderFinanceVo.getBankAccount();
	    }
	    data.put("invoice_content", "");
	    data.put("invoice_company", saleorderInfo.getTraderName());
	    data.put("invoice_no", taxNum);
	    data.put("invoice_register_address", regAddress);
	    data.put("invoice_register_phone", regTel);
	    data.put("invoice_bank", bank);
	    data.put("invoice_bank_account", bankAccount);

	    data.put("invoice_consignee", saleorderInfo.getInvoiceTraderContactName());
	    data.put("invoice_mobile_number", saleorderInfo.getInvoiceTraderContactMobile());
	    data.put("invoice_area_id", saleorderInfo.getInvoiceAreaId());
	    data.put("invoice_address", saleorderInfo.getInvoiceTraderAddress());

	    data.put("payment_period_pay", saleorderInfo.getHaveAccountPeriod());
	    data.put("payment_period_pay_amount", saleorderInfo.getAccountPeriodAmount());

	    data.put("purchase_username", saleorderInfo.getName());
	    data.put("purchase_tel", saleorderInfo.getMobile());
	    data.put("purchase_fax", saleorderInfo.getFax());
	    data.put("purchase_email", saleorderInfo.getEmail());

	    UserDetail userInfo = userService.getUserDetailByTraderId(saleorderInfo.getTraderId(), 1);
	    String order_username = "";
	    String order_tel = "";
	    String order_fax = "";
	    String order_email = "";
	    if (null != userInfo) {
		order_username = userInfo.getRealName();
		order_tel = userInfo.getTelephone();
		order_fax = userInfo.getFax();
		order_email = userInfo.getEmail();
	    }
	    data.put("order_username", order_username);
	    data.put("order_tel", order_tel);
	    data.put("order_fax", order_fax);
	    data.put("order_email", order_email);
	    data.put("delivery_fright_name", saleorderInfo.getDeliveryTypeStr());
	    data.put("prepaid_amount", saleorderInfo.getPrepaidAmount());
	    data.put("pay_remark", saleorderInfo.getPaymentTypeStr());

	    // 订单web信息
	    Integer accountId = 0;
	    if (saleorderInfo.getTraderContactId() != null && saleorderInfo.getTraderContactId() > 0) {
		WebAccount webAccount = webAccountMapper.getTraderContactWebInfo(saleorderInfo.getTraderContactId());
		if (null != webAccount) {
		    accountId = webAccount.getWebAccountId();
		}
	    }

	    data.put("account_id", accountId);
	    // ===================以下订单产品信息==================
	    goodsDataMap.add(new HashMap<>());
	    for (SaleorderGoods goods : saleorderGoods) {
		Map goodsData = new HashMap<>();

		goodsData.put("item_id", goods.getGoodsId());// 商品id
		goodsData.put("item_name", goods.getGoodsName());// 商品名称
		goodsData.put("price", goods.getPrice());// 价格
		goodsData.put("quantity", goods.getNum());// 数量
		goodsData.put("delivery_send", goods.getDeliveryNum());// 发货数量
		goodsData.put("delivery_status", goods.getDeliveryStatus());// 0未发货1部分发货2全部发货
		goodsData.put("delivery_cycle", goods.getDeliveryCycle());// 货期
		goodsData.put("goods_comments", goods.getGoodsComments());// 备注

		goodsDataMap.add(goodsData);
	    }

	    // ===================以下调用接口==================
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    String goodsDataXmlStr = xmlExercise.listToXml(goodsDataMap, "data", "items");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("orderSyncErp");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr, goodsDataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "orderSyncErp");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_SALEORDER");
	    dataSyncStatus.setRelatedId(saleorderId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo messageSyncWeb(Integer type, Integer orderId, Integer messageType) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (type == null || type <= 0 || orderId == null || orderId <= 0) {
	    return resultInfo;
	}

	Map data = new HashMap<>();
	switch (type) {
	case 1:// 报价（报价通知）
	    Quoteorder quoteorder = quoteService.getMessageInfoForSync(orderId);
	    if (null == quoteorder) {
		return resultInfo;
	    }
	    data.put("type", type);
	    data.put("order_id", orderId);
	    data.put("orderNo", quoteorder.getQuoteorderNo());
	    data.put("messageType", messageType);
	    data.put("totalAmount", quoteorder.getTotalAmount());
	    data.put("totalNum", quoteorder.getTotalNum());

	    break;
	case 2:// 订单
	    if (messageType == null || messageType <= 0) {
		return resultInfo;
	    }
	    SaleorderVo saleorder = saleorderService.getMessageInfoForSync(orderId);
	    if (null == saleorder || !saleorder.getCompanyId().equals(1)) {
		return resultInfo;
	    }

	    if (messageType == 3 && !saleorder.getDeliveryStatus().equals(2)) {
		return resultInfo;
	    }

	    // 全部付款判断
	    if (messageType == 2 && !saleorder.getPaymentStatus().equals(2)) {
		return resultInfo;
	    }

	    data.put("type", type);
	    data.put("order_id", orderId);
	    data.put("orderNo", saleorder.getSaleorderNo());
	    data.put("messageType", messageType);
	    // 付款
	    data.put("pay_time", saleorder.getPaymentTime() != null && saleorder.getPaymentTime() > 0
		    ? String.format("%010d", saleorder.getPaymentTime() / 1000) : 0);//
	    // 发货
	    data.put("shipping_time", saleorder.getDeliveryTime() != null && saleorder.getDeliveryTime() > 0
		    ? String.format("%010d", saleorder.getDeliveryTime() / 1000) : 0);//
	    // 已签
	    data.put("sign_time", saleorder.getArrivalTime() != null && saleorder.getArrivalTime() > 0
		    ? String.format("%010d", saleorder.getArrivalTime() / 1000) : 0);//
	    data.put("totalAmount", saleorder.getTotalAmount());
	    data.put("totalNum", saleorder.getTotalNum());
	    break;
	}

	try {
	    // ===================以下调用接口==================
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("messageSyncWeb");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "messageSyncWeb");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("WEB_T_MESSAGE");// 网站站内信
	    dataSyncStatus.setRelatedId(orderId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo categoryAttrSync(Integer categoryAttributeId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (categoryAttributeId == null || categoryAttributeId <= 0) {
	    return resultInfo;
	}

	// 定义反序列化 数据格式
	final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {
	};
	String url = httpUrl + "goods/categoryattribute/getcategoryattributesync.htm";
	ResultInfo<?> apiResult = null;
	try {
	    apiResult = (ResultInfo<?>) HttpClientUtils.post(url, categoryAttributeId, clientId, clientKey, TypeRef);
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	Map<String, Object> syncMap = (Map<String, Object>) apiResult.getData();

	if (!syncMap.containsKey("attr") || !syncMap.containsKey("attrValue")) {
	    return resultInfo;
	}

	// 属性
	net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(syncMap.get("attr"));
	CategoryAttribute attribute = (CategoryAttribute) json.toBean(json, CategoryAttribute.class);

	// 属性值
	net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(syncMap.get("attrValue"));
	List<CategoryAttributeValue> attributeValues = (List<CategoryAttributeValue>) jsonArray.toCollection(jsonArray,
		CategoryAttributeValue.class);

	if (null == attribute || null == attributeValues || attributeValues.size() == 0) {
	    return resultInfo;
	}

	Map data = new HashMap<>();
	List valueDataMap = new ArrayList<>();// 属性值

	data.put("category_attribute_id", attribute.getCategoryAttributeId());
	data.put("category_id", attribute.getCategoryId());
	data.put("name", attribute.getCategoryAttributeName());
	data.put("input_type", attribute.getInputType());
	data.put("is_selected", attribute.getIsSelected());
	data.put("is_filter", attribute.getIsFilter());
	data.put("order_no", attribute.getSort());
	data.put("is_enable", attribute.getIsEnable());

	valueDataMap.add(new HashMap<>());
	for (CategoryAttributeValue attributeValue : attributeValues) {
	    Map valueMap = new HashMap<>();
	    valueMap.put("category_attribute_value_id", attributeValue.getCategoryAttributeValueId());
	    valueMap.put("category_attribute_id", attributeValue.getCategoryAttributeId());
	    valueMap.put("attr_value", attributeValue.getAttrValue());
	    valueMap.put("order_no", attributeValue.getSort());
	    valueMap.put("is_enable", attributeValue.getIsEnable());

	    valueDataMap.add(valueMap);
	}

	try {
	    // ===================以下调用接口==================
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    String valuesDataXmlStr = xmlExercise.listToXml(valueDataMap, "data", "items");
	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("categoryAttrSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr, valuesDataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "categoryAttrSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_CATEGORY_ATTRIBUTE");
	    dataSyncStatus.setRelatedId(categoryAttributeId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo messageBtachSignSyncWeb(List<SaleorderVo> saleorderList) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (saleorderList == null || saleorderList.size() == 0) {
	    return resultInfo;
	}

	List data = new ArrayList<>();
	data.add(new HashMap<>());

	List<WeChatArr> arrList = new LinkedList<WeChatArr>();
	Date addTime = new Date();
	for (SaleorderVo saleorder : saleorderList) {
	    if (null == saleorder)
			continue;

	    Map map = new HashMap<>();
		String saleorderNo = saleorder.getSaleorderNo();
	    // 已签
	    map.put("orderNo", saleorderNo);
	    map.put("sign_time", saleorder.getArrivalTime() != null && saleorder.getArrivalTime() > 0
		    ? String.format("%010d", saleorder.getArrivalTime() / 1000) : 0);//
	    map.put("totalAmount", saleorder.getTotalAmount());
	    map.put("totalNum", saleorder.getTotalNum());
	    data.add(map);
		// ---------------------------------------------------------
		logger.info("messageBtachSignSyncWeb | orderType:{}", saleorder.getOrderType());
		// 耗材订单 && 全部已签收
		if(OrderConstant.ORDER_TYPE_HC.equals(saleorder.getOrderType()) && Integer.valueOf(2).equals(saleorder.getArrivalStatus())) {
			WeChatArr arr = new WeChatArr();

			arr.setOrderType(OrderConstant.ORDER_TYPE_HC);
			arr.setSaleorderId(saleorder.getSaleorderId());
			arr.setTraderAddress(saleorder.getTraderAddress());
			arr.setTraderContactMobile(saleorder.getTraderContactMobile());
			arr.setTraderContactName(saleorder.getTraderContactName());
			arr.setTraderName(saleorder.getTraderName());
			arr.setAddTime(addTime);
			arr.setSaleorderNo(saleorderNo);
			arr.setCompanyId(null == saleorder.getCompanyId() ? 1 : saleorder.getCompanyId());

			arrList.add(arr);
		}
		if(OrderConstant.ORDER_TYPE_BD.equals(saleorder.getOrderType()) && Integer.valueOf(2).equals(saleorder.getArrivalStatus())
		|| OrderConstant.ORDER_TYPE_DH.equals(saleorder.getOrderType()) && Integer.valueOf(2).equals(saleorder.getArrivalStatus())
		|| OrderConstant.ORDER_TYPE_JX.equals(saleorder.getOrderType()) && Integer.valueOf(2).equals(saleorder.getArrivalStatus())
		|| OrderConstant.ORDER_TYPE_SALE.equals(saleorder.getOrderType()) && Integer.valueOf(2).equals(saleorder.getArrivalStatus())
		) {
			WeChatArr arr = new WeChatArr();
			arr.setOrderType(saleorder.getOrderType());
			arr.setSaleorderId(saleorder.getSaleorderId());
			arr.setTraderAddress(saleorder.getTraderAddress());
			arr.setTraderContactMobile(saleorder.getTraderContactMobile());
			arr.setTraderContactName(saleorder.getTraderContactName());
			arr.setTraderName(saleorder.getTraderName());
			arr.setAddTime(addTime);
			arr.setSaleorderNo(saleorderNo);
			arr.setCompanyId(null == saleorder.getCompanyId() ? 1 : saleorder.getCompanyId());
			arr.setPlatfromId(1);
			arrList.add(arr);
		}
		// ---------------------------------------------------------
	}
	logger.info("messageBtachSignSyncWeb.weChatArrMapper | arrList:{}", arrList);
	try {
		// 入库  医械购 用于 微信订单签收发送
		if(CollectionUtils.isNotEmpty(arrList)) {
			int addNum = weChatArrMapper.insertBatch(arrList);
			logger.info("messageBtachSignSyncWeb.weChatArrMapper | 要发送的数据addNum:{}", addNum);
		}

	} catch (Exception e) {
		logger.error("messageBtachSignSyncWeb.weChatArrMapper | error ", e);
	}

	try {
	    // ===================以下调用接口==================
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.listToXml(data, "data", "items");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("messageBtachSignSyncWeb");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "messageBtachSignSyncWeb");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo saleorderAttachmentSyncWeb(Integer attachmentId, Boolean isDel) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (attachmentId == null || attachmentId <= 0) {
	    return resultInfo;
	}
	Map data = new HashMap<>();

	if (isDel) {
	    data.put("attachmentId", attachmentId);
	    data.put("is_del", 1);
	} else {
	    Attachment attachment = null;
	    // 定义反序列化 数据格式
	    final TypeReference<ResultInfo<Attachment>> TypeRef = new TypeReference<ResultInfo<Attachment>>() {
	    };
	    String url = httpUrl + "order/saleorder/getsaleorderattachment.htm";

	    ResultInfo<?> result;
	    try {
		result = (ResultInfo<?>) HttpClientUtils.post(url, attachmentId, clientId, clientKey, TypeRef);
		attachment = (Attachment) result.getData();
	    } catch (IOException e) {
		logger.error(Contant.ERROR_MSG, e);
	    }

	    if (null != attachment) {
		data.put("attachmentId", attachmentId);
		data.put("attachment_type", attachment.getAttachmentType());
		data.put("attachment_function", attachment.getAttachmentFunction());
		data.put("saleorder_no", attachment.getRelatedNo());
		data.put("name", attachment.getName());
		data.put("domain", attachment.getDomain());
		data.put("uri", attachment.getUri());
		data.put("alt", attachment.getAlt());
		data.put("sort", attachment.getSort());
		data.put("is_default", attachment.getIsDefault());
		data.put("add_time", attachment.getAddTime());
		data.put("erp_id", attachmentId);

	    } else {
		return resultInfo;
	    }
	}

	try {
	    // 信息同步
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("attachmentSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "attachmentSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map resultMap = xmlExercise.xmlToMap(xmlString);

	    if (null != resultMap && resultMap.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	}

	// 同步日志
	DataSyncStatus dataSyncStatus = new DataSyncStatus();
	Integer syncStatus = 0;
	if (resultInfo.getCode().equals(0)) {
	    syncStatus = 1;
	}
	dataSyncStatus.setGoalType(631);// 网站
	dataSyncStatus.setStatus(syncStatus);
	dataSyncStatus.setSourceTable("T_ATTACHMENT");
	dataSyncStatus.setRelatedId(attachmentId);
	dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	return resultInfo;
    }

    @Override
    public ResultInfo quoteoderToOrderSync(Integer quoteorderId) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (quoteorderId == null || quoteorderId <= 0) {
	    return resultInfo;
	}
	XmlExercise xmlExercise = new XmlExercise();
	Quoteorder quoteorder = new Quoteorder();

	// 报价信息
	quoteorder = quoteService.getQuoteorderForSync(quoteorderId);

	if (null == quoteorder) {
	    return resultInfo;
	}

	Map data = new HashMap<>();// 订单数据
	data.put("quoteorder_no", quoteorder.getQuoteorderNo());

	try {
	    // ===================以下调用接口==================
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("quoteoderToOrderSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "quoteoderToOrderSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    return resultInfo;
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return resultInfo;
	}
    }

    @Override
    public ResultInfo warehouseinAttachmentSyncWeb(Integer attachmentId, Boolean isDel) {
	ResultInfo resultInfo = new ResultInfo<>();
	if (attachmentId == null || attachmentId <= 0) {
	    return resultInfo;
	}
	Map data = new HashMap<>();

	if (isDel) {
	    data.put("attachmentId", attachmentId);
	    data.put("is_del", 1);
	} else {
	    Attachment attachment = null;
	    // 定义反序列化 数据格式
	    final TypeReference<ResultInfo<Attachment>> TypeRef = new TypeReference<ResultInfo<Attachment>>() {
	    };
	    String url = httpUrl + "logistics/warehousein/getattachmentbyatt.htm";

	    ResultInfo<?> result;
	    try {
		result = (ResultInfo<?>) HttpClientUtils.post(url, attachmentId, clientId, clientKey, TypeRef);
		attachment = (Attachment) result.getData();
	    } catch (IOException e) {
		logger.error(Contant.ERROR_MSG, e);
	    }

	    if (null != attachment) {
		data.put("attachmentId", attachmentId);
		data.put("attachment_type", attachment.getAttachmentType());
		data.put("attachment_function", attachment.getAttachmentFunction());
		data.put("saleorder_no", attachment.getRelatedNo());
		data.put("name", attachment.getName());
		data.put("domain", attachment.getDomain());
		data.put("uri", attachment.getUri());
		data.put("alt", attachment.getAlt());
		data.put("sort", attachment.getSort());
		data.put("is_default", attachment.getIsDefault());
		data.put("add_time", attachment.getAddTime());
		data.put("erp_id", attachmentId);

	    } else {
		return resultInfo;
	    }
	}

	try {
	    // 信息同步
	    XmlExercise xmlExercise = new XmlExercise();
	    String dataXmlStr = xmlExercise.mapToXml(data, "data");
	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("attachmentSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { dataXmlStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "attachmentSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map resultMap = xmlExercise.xmlToMap(xmlString);

	    if (null != resultMap && resultMap.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }
	} catch (Exception e) {
	    logger.error(Contant.ERROR_MSG, e);
	}

	// 同步日志
	DataSyncStatus dataSyncStatus = new DataSyncStatus();
	Integer syncStatus = 0;
	if (resultInfo.getCode().equals(0)) {
	    syncStatus = 1;
	}
	dataSyncStatus.setGoalType(631);// 网站
	dataSyncStatus.setStatus(syncStatus);
	dataSyncStatus.setSourceTable("T_ATTACHMENT");
	dataSyncStatus.setRelatedId(attachmentId);
	dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	return resultInfo;
    }

    @Override
    public ResultInfo financeInfoSync(Integer traderId) {
	ResultInfo resultInfo = new ResultInfo<>();
	// 如果traderId未传 抛出
	if (traderId == null || traderId <= 0) {
	    return resultInfo;
	}
	TraderContactVo traderContactVo = new TraderContactVo();
	traderContactVo.setTraderId(traderId);
	traderContactVo.setTraderType(ErpConst.ONE);
	Map traderFinanceMap = new HashMap<>();
	Map webAccountIdMap = new HashMap<>();// 属性值
	Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
	String tastr = (String) map.get("contact");
	net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
	// 获取当前客户的所有联系人
	List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
	// 如果有联系人的话
	if (list != null && list.size() > 0) {
	    List<Integer> traderContactIds = new ArrayList<>();
	    for (TraderContactVo tcv : list) {
		traderContactIds.add(tcv.getTraderContactId());
	    }
	    List<OrdergoodsStoreAccountVo> ordergoodsStoreAccountList = ordergoodsStoreAccountMapper
		    .getOrdergoodsStoreAccountByTraderContactIds(traderContactIds);
	    if (ordergoodsStoreAccountList != null && ordergoodsStoreAccountList.size() > 0) {
		for (OrdergoodsStoreAccountVo osa : ordergoodsStoreAccountList) {
		    webAccountIdMap.put("accountId", osa.getWebAccountId());
		}
		// 如果有对应的网站账号
		TraderFinanceVo traderFinance = new TraderFinanceVo();
		traderFinance.setTraderId(traderId);
		// 客户
		traderFinance.setTraderType(ErpConst.ONE);
		TraderFinanceVo traderFinanceInfo = traderCustomerService.getTraderFinanceByTraderId(traderFinance);
		if (traderFinanceInfo != null) {
		    if (traderFinanceInfo.getTaxNum() != null) {
			traderFinanceMap.put("invoice_no", traderFinanceInfo.getTaxNum());
		    }
		    if (traderFinanceInfo.getRegAddress() != null) {
			traderFinanceMap.put("invoice_address", traderFinanceInfo.getRegAddress());
		    }
		    if (traderFinanceInfo.getRegTel() != null) {
			traderFinanceMap.put("invoice_phone", traderFinanceInfo.getRegTel());
		    }
		    if (traderFinanceInfo.getBank() != null) {
			traderFinanceMap.put("invoice_bank", traderFinanceInfo.getBank());
		    }
		    if (traderFinanceInfo.getBankAccount() != null) {
			traderFinanceMap.put("invoice_number", traderFinanceInfo.getBankAccount());
		    }
		}
	    } else {
		// 如果没有对应的网站账号，则不需要走接口
		return resultInfo;
	    }
	} else {
	    // 没有联系人则 抛出
	    return resultInfo;
	}

	try {
	    // ===================以下调用接口==================
	    XmlExercise xmlExercise = new XmlExercise();
	    String traderFinanceXmlStr = xmlExercise.mapToXml(traderFinanceMap, "data");
	    String webAccountIdStr = xmlExercise.mapToXml(webAccountIdMap, "list");

	    // 调用接口
	    RPCServiceClient ser = new RPCServiceClient();
	    Options options = ser.getOptions();

	    // 指定调用WebService的URL
	    EndpointReference targetEPR = new EndpointReference(vedengNamespace);
	    options.setTo(targetEPR);
	    // options.setAction("命名空间/WS 方法名");
	    options.setAction("financeInfoSync");

	    // 指定sfexpressService方法的参数值
	    Object[] opAddEntryArgs = new Object[] { traderFinanceXmlStr, webAccountIdStr };
	    // 指定sfexpressService方法返回值的数据类型的Class对象
	    Class[] classes = new Class[] { String.class };
	    // 指定要调用的sfexpressService方法及WSDL文件的命名空间
	    QName opAddEntry = new QName(vedengApiUrl, "financeInfoSync");
	    // 调用sfexpressService方法并输出该方法的返回值
	    Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

	    // 接口返回xml字符串
	    String xmlString = str[0].toString();
	    Map result = xmlExercise.xmlToMap(xmlString);

	    if (null != result && result.get("code").equals("0")) {
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
	    }

	    // 同步日志
	    DataSyncStatus dataSyncStatus = new DataSyncStatus();
	    Integer syncStatus = 0;
	    if (resultInfo.getCode().equals(0)) {
		syncStatus = 1;
	    }
	    dataSyncStatus.setGoalType(631);// 网站
	    dataSyncStatus.setStatus(syncStatus);
	    dataSyncStatus.setSourceTable("T_TRADER_FINANCE");
	    dataSyncStatus.setRelatedId(traderId);
	    dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
	    dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
	    return resultInfo;
	} catch (Exception e) {
	    // TODO: handle exception
	}
	return resultInfo;
    }

	@Override public ResultInfo webAccountIsVedengJxSync(WebAccount webAccount) {
		ResultInfo resultInfo = new ResultInfo<>();
		if (null == webAccount) {
			//如果webAccount对象为null抛出
			LOG.error("注册用户同步WEB贝登精选状态失败 webAccount为NULL");
			return resultInfo;
		}
		WebAccountVo account = webAccountMapper.getWebAccount(webAccount);
		if (null != account) {
			Map data = new HashMap<>();// 数据
			Map accountInfo = new HashMap<>();//贝登官网用户基础数据
			Map extra = new HashMap<>();// 贝登官网用户扩展数据
			/******************组装用户数据，如果贝登官网中UUID查不到数据需要新增****************************/
			accountInfo.put("account", account.getAccount());
			accountInfo.put("email", account.getEmail());
			accountInfo.put("uuid", account.getUuid());
			accountInfo.put("mobile", account.getMobile());
			accountInfo.put("weixin_openid", account.getWeixinOpenid());
			accountInfo.put("is_vedeng_jx", account.getIsVedengJx());

			extra.put("corporation", account.getCompanyName());
			extra.put("real_name", account.getName());
			extra.put("sex", account.getSex());
			/******************组装用户数据，如果贝登官网中UUID查不到数据需要新增****************************/
			data.put("account",accountInfo);
			data.put("extra",extra);

			System.out.println("data:"+JSON.toJSONString(data));

			try {
					// 同步web
					LOG.info("注册用户同步WEB贝登精选状态 UUID="+account.getUuid()+"isVedengJx="+account.getIsVedengJx());
					String dataXmlStr = XmlExercise.mapToXml(data, "data");

					System.out.println("dataXmlStr:"+dataXmlStr);

					// 调用接口
					RPCServiceClient ser = new RPCServiceClient();
					Options options = ser.getOptions();

					EndpointReference targetEPR = new EndpointReference(vedengNamespace);
					options.setProperty(HTTPConstants.CHUNKED, false);
					options.setTo(targetEPR);
					// options.setAction("命名空间/WS 方法名");
					options.setAction("accountIsVedengJxSync");

					// 指定accountIsVedengJxSync方法的参数值
					Object[] opAddEntryArgs = new Object[] {dataXmlStr, account.getUuid()};
					// 指定accountIsVedengJxSync方法返回值的数据类型的Class对象
					Class[] classes = new Class[] { String.class };
					// 指定要调用的accountIsVedengJxSync方法及WSDL文件的命名空间
					QName opAddEntry = new QName(vedengApiUrl, "accountIsVedengJxSync");
					// 调用accountIsVedengJxSync方法并输出该方法的返回值
					Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

					// 接口返回xml字符串
					String xmlString = str[0].toString();
					Map result = XmlExercise.xmlToMap(xmlString);

					if (null != result && result.get("code").equals("0")) {
						// 同步web
						resultInfo.setCode(0);
						resultInfo.setMessage("操作成功");
					}else{
						// 同步失败
						LOG.error("注册用户同步WEB贝登精选状态失败 UUID="+account.getUuid()+"isVedengJx="+account.getIsVedengJx());
						resultInfo.setCode(-1);
						resultInfo.setMessage("操作失败");

					}
			} catch (Exception e) {
				LOG.error("注册用户同步WEB贝登精选状态失败 UUID="+account.getUuid()+"isVedengJx="+account.getIsVedengJx(),e);
			}
		}
		return resultInfo;
	}

	@Override
	public ResultInfo goodsIsNoReasonReturnSync(Integer goodsId, Integer isNoReasonReturn) {
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			// 同步web
			LOG.info("同步商品7天无理由退换货状态：goodsId = " + goodsId + ":isNoReasonReturn = " + isNoReasonReturn);
			Map data = new HashMap<>();// 数据
			data.put("goods_id", goodsId);
			data.put("is_no_reason_return", isNoReasonReturn);
			String dataXmlStr = XmlExercise.mapToXml(data, "data");
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			EndpointReference targetEPR = new EndpointReference(vedengNamespace);
					options.setProperty(HTTPConstants.CHUNKED, false);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("goodsIsNoReasonReturnSync");

			// 指定accountIsVedengJxSync方法的参数值
			Object[] opAddEntryArgs = new Object[] {dataXmlStr};
			// 指定accountIsVedengJxSync方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的accountIsVedengJxSync方法及WSDL文件的命名空间
			QName opAddEntry = new QName(vedengApiUrl, "goodsIsNoReasonReturnSync");
			// 调用accountIsVedengJxSync方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			Map result = XmlExercise.xmlToMap(xmlString);

			if (null != result && result.get("code").equals("0")) {
				// 同步web
				LOG.info("同步商品7天无理由退换货状态成功：goodsId = " + goodsId + ":isNoReasonReturn = " + isNoReasonReturn);
				// 同步web
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}else{
				// 同步失败
				LOG.error("同步商品7天无理由退换货状态失败：goodsId = " + goodsId + ":isNoReasonReturn = " + isNoReasonReturn);
				resultInfo.setCode(-1);
				resultInfo.setMessage("操作失败");
			}
		} catch (AxisFault axisFault) {
			axisFault.printStackTrace();
			LOG.error("同步商品7天无理由退换货状态发生异常：goodsId = " + goodsId + ":isNoReasonReturn = " + isNoReasonReturn, axisFault);
		}
		return resultInfo;
	}


	@Override
	public ResultInfo sendWxMessage(Map messageInfo,Integer wxTemplateNo,String uuid){
		ResultInfo resultInfo = new ResultInfo<>();
		if (null == messageInfo || messageInfo.isEmpty()) {
			//如果messageInfo对象为null抛出或者messageInfo对象为空抛出
			LOG.error("发送微信模版消息失败 messageInfo为空或为null");
			return resultInfo;
		}
		if(null == wxTemplateNo){
			LOG.error("发送微信模版消息失败 wxTemplateNo为空或为null");
			return resultInfo;
		}
		if(StringUtil.isBlank(uuid)){
			LOG.error("发送微信模版消息失败 uuid为空或为null");
			return resultInfo;
		}

			Map data = new HashMap<>();//消息数据
			try {
				// 同步web
				LOG.info("开始发送微信模版消息 messagesInfo ="+JSONObject.fromObject(messageInfo).toString()+" wxTemplateNo="+wxTemplateNo+"uuid="+uuid);
				String dataXmlStr = XmlExercise.mapToXml(messageInfo, "data");
				// 调用接口
				RPCServiceClient ser = new RPCServiceClient();
				Options options = ser.getOptions();

				EndpointReference targetEPR = new EndpointReference(vedengNamespace);
				options.setProperty(HTTPConstants.CHUNKED, false);
				options.setTo(targetEPR);
				// options.setAction("命名空间/WS 方法名");
				options.setAction("sendWxMessage");

				// 指定sendWxMessage方法的参数值
				Object[] opAddEntryArgs = new Object[] {dataXmlStr, wxTemplateNo,uuid};
				// 指定sendWxMessage方法返回值的数据类型的Class对象
				Class[] classes = new Class[] { String.class };
				// 指定要调用的sendWxMessage方法及WSDL文件的命名空间
				QName opAddEntry = new QName(vedengApiUrl, "sendWxMessage");
				// 调用sendWxMessage方法并输出该方法的返回值
				Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

				// 接口返回xml字符串
				String xmlString = str[0].toString();
				Map result = XmlExercise.xmlToMap(xmlString);

				if (null != result && result.get("code").equals("0")) {
					// 同步web
					resultInfo.setCode(0);
					resultInfo.setMessage("操作成功");
				}else{
					// 同步失败
					LOG.error("发送微信模版消息失败 messagesInfo ="+JSONObject.fromObject(messageInfo).toString()+" wxTemplateNo="+wxTemplateNo+"uuid="+uuid);
					resultInfo.setCode(-1);
					resultInfo.setMessage("操作失败");

				}
			} catch (Exception e) {
				LOG.error("发送微信模版消息失败 messagesInfo ="+JSONObject.fromObject(messageInfo).toString()+" wxTemplateNo="+wxTemplateNo+"uuid="+uuid,e);
			}

		return resultInfo;
	}


}
