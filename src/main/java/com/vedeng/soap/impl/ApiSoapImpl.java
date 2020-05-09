package com.vedeng.soap.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.common.constants.Contant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserDetailMapper;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.UserDetail;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.DigitToChineseUppercaseNumberUtils;
import com.vedeng.common.util.ItextFileToPdf;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.logistics.model.Express;
import com.vedeng.logistics.model.ExpressDetail;
import com.vedeng.logistics.model.LogisticsDetail;
import com.vedeng.logistics.service.ExpressService;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.SaleorderVo;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.ordergoods.dao.OrdergoodsStoreAccountMapper;
import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.soap.ApiSoap;
import com.vedeng.soap.model.SoapResult;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.WebAccountVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component("apiSoap")
public class ApiSoapImpl extends BaseServiceimpl implements ApiSoap {
	public static Logger logger = LoggerFactory.getLogger(ApiSoapImpl.class);

	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;
	
	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;
	
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;
	
	@Autowired
	@Qualifier("goodsService")
	private GoodsService goodsService;
	
	@Autowired
	@Qualifier("webAccountMapper")
	private WebAccountMapper webAccountMapper;
	
	@Autowired
	@Qualifier("ordergoodsStoreAccountMapper")
	private OrdergoodsStoreAccountMapper ordergoodsStoreAccountMapper;

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	@Autowired
	@Qualifier("expressService")
	private ExpressService expressService;
	
	@Autowired
	@Qualifier("userDetailMapper")
	private UserDetailMapper userDetailMapper;
	
	@Override
	public String hello(String msg) {
		return "Hello" + msg;
	}

	@Override
	public String itemUpdate(String goodsId, String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		if(null == goodsId || goodsId.equals("")){
			return xmlExercise.convertToXml(resultInfo);
		}
		Goods goods = new Goods();
		Long time = DateUtil.sysTimeMillis();
		
		goods.setGoodsId(Integer.parseInt(goodsId));
		goods.setModTime(time);
		Map info = xmlExercise.xmlToMap(data);
		
		if(!info.isEmpty()){
			if(info.get("is_discard") != null){//产品废弃
				goods.setIsDiscard(Integer.parseInt(info.get("is_discard").toString()));
			}
		}
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef2 = new TypeReference<ResultInfo<Goods>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "goods/savebaseinfo.htm", goods,clientId,clientKey, TypeRef2);
			Goods res = (Goods) result2.getData();
			if(res != null){
				resultInfo.setCode(1);
				resultInfo.setMessage("操作成功");
			}
			return xmlExercise.convertToXml(resultInfo);
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	//@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public String orderSync(String data, String goodsData, String payData) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		Long time = DateUtil.sysTimeMillis();
		
		Map dataMap = null;//订单主表数据
		List<Map<String, Object>> goodsDataMap = null;//订单产品数据
		//Map payDataMap = null;//订单账期信息
		
		
		if(null != data){//订单主表信息
			dataMap = xmlExercise.xmlToMap(data);
		}
		
		//订单主表信息未空，或者订单号为空
		if(null == dataMap || dataMap.get("saleorder_no") == null || dataMap.get("saleorder_no").equals("")){
			resultInfo.setMessage("订单号/订单信息不全");
			resultInfo.setKey_id(0);
			return xmlExercise.convertToXml(resultInfo);
		}
		
		if(null != goodsData){//订单产品信息
			goodsDataMap = xmlExercise.xmlToList(goodsData);
		}
//		if(null != payData){//订单付款信息
//			payDataMap = xmlExercise.xmlToMap(payData);
//		}
		
		//判断订单是否存在
		Saleorder saleorderInfo = new Saleorder();
		saleorderInfo.setSaleorderNo(dataMap.get("saleorder_no").toString());
		Saleorder saleorder = saleorderService.getSaleorderBySaleorderNo(saleorderInfo);
		
		if(saleorder == null){
			saleorder = new Saleorder();
		}
		if(null != dataMap.get("consignee")){
			saleorder.setTakeTraderContactName(dataMap.get("consignee").toString());//收货人
		}
		if(null != dataMap.get("mobile_number")){
			saleorder.setTakeTraderContactMobile(dataMap.get("mobile_number").toString());//收货人手机
		}
		if(null != dataMap.get("phone_number")){
			saleorder.setTakeTraderContactTelephone(dataMap.get("phone_number").toString());//收货人电话
		}
		
		if(null != dataMap.get("address_area")){
			saleorder.setTakeTraderArea(dataMap.get("address_area").toString());//收货区域
		}
		
		if(null != dataMap.get("address")){//收货地址
			saleorder.setTakeTraderAddress(dataMap.get("address").toString());
		}
		
		
		if(null != dataMap.get("invoice_consignee")){//收票联系人
			saleorder.setInvoiceTraderContactName(dataMap.get("invoice_consignee").toString());
		}
		if(null != dataMap.get("invoice_mobile_number")){//收票联系人电话
			saleorder.setInvoiceTraderContactMobile(dataMap.get("invoice_mobile_number").toString());
		}
		
		if(null != dataMap.get("invoice_area")){
			saleorder.setInvoiceTraderArea(dataMap.get("invoice_area").toString());//收票区域
		}
		
		if(null != dataMap.get("invoice_address")){//收票地址
			saleorder.setInvoiceTraderAddress(dataMap.get("invoice_address").toString());
		}
		if(null != dataMap.get("total_money")){//订单总额
			saleorder.setTotalAmount(new BigDecimal(dataMap.get("total_money").toString()));
		}
		if(null != dataMap.get("is_urgent")){//是否加急
			saleorder.setIsUrgent(Integer.parseInt(dataMap.get("is_urgent").toString()));
		}
		if(null != dataMap.get("urgent_amount")){//加急费用
			saleorder.setUrgentAmount(new BigDecimal(dataMap.get("urgent_amount").toString()));
		}
//		if(null != dataMap.get("freight_amount")){//运费 新ERP无此项
//			
//		}
		
		if(null != dataMap.get("desc")){//备注
			saleorder.setComments(dataMap.get("desc").toString());
		}
		
		if(null != dataMap.get("delivery_fright")){//运费说明
			Integer FREIGHT_DESCRIPTION = 0;//运费说明 字典表
			Integer deliveryFright = Integer.parseInt(dataMap.get("delivery_fright").toString());
	        switch (deliveryFright){
	            case 0:
	                FREIGHT_DESCRIPTION = 472;
	                break;
	            case 1:
	                FREIGHT_DESCRIPTION = 471;
	                break;
	            case 2:
	                FREIGHT_DESCRIPTION = 474;
	                break;
	            case 3:
	                FREIGHT_DESCRIPTION = 470;
	                break;
	            case 4:
	                FREIGHT_DESCRIPTION = 473;
	                break;
	            case 5:
	                FREIGHT_DESCRIPTION = 475;
	                break;
	            case 6:
	                FREIGHT_DESCRIPTION = 476;
	                break;
	        }
	        saleorder.setFreightDescription(FREIGHT_DESCRIPTION);
		}
		
		if(null != dataMap.get("freight_destination") 
				&& null != dataMap.get("order_type") 
				&&( dataMap.get("order_type").toString().equals("3") || dataMap.get("order_type").toString().equals("4") )){//运费到付0否1是,订货订单
			if(dataMap.get("freight_destination").toString().equals("0")){
				saleorder.setFreightDescription(470);
			}
			if(dataMap.get("freight_destination").toString().equals("1")){
				saleorder.setFreightDescription(474);
			}
		}
		
		if(null != dataMap.get("payment_period_pay")){//含有账期支付 0无 1有
			saleorder.setHaveAccountPeriod(Integer.parseInt(dataMap.get("payment_period_pay").toString()));
		}
		if(null != dataMap.get("payment_period_pay_amount")){//账期支付金额
			saleorder.setAccountPeriodAmount(new BigDecimal(dataMap.get("payment_period_pay_amount").toString()));
		}
		
		if(null != dataMap.get("payment_type")){//付款方式
			Integer PAYMENT_TYPE = 0;//付款方式 字典库
			Integer paymentType = Integer.parseInt(dataMap.get("payment_type").toString());
	        switch (paymentType){
	            case 0:
	                PAYMENT_TYPE = 419;
	                break;
	            case 18:
	                PAYMENT_TYPE = 420;
	                break;
	            case 8:
	                PAYMENT_TYPE = 421;
	                break;
	            case 7:
	                PAYMENT_TYPE = 422;
	                break;
	            case 10:
	                PAYMENT_TYPE = 423;
	                break;
	            case 21:
	                PAYMENT_TYPE = 424;
	                break;
	        }
	        saleorder.setPaymentType(PAYMENT_TYPE);
		}
		
		if(null != dataMap.get("defray_id")
				&& null != dataMap.get("order_type") 
				&&( dataMap.get("order_type").toString().equals("3")||dataMap.get("order_type").toString().equals("4"))){//订货订单支付方式
			if(dataMap.get("defray_id").toString().equals("1")){//账期
				saleorder.setPaymentType(423);
			}
			if(dataMap.get("defray_id").toString().equals("2")){//100%预付
				saleorder.setPaymentType(419);
				saleorder.setPrepaidAmount(saleorder.getTotalAmount());
			}
		}
//		if(null != dataMap.get("defray_name")){
//			
//		}
		
		if(null != dataMap.get("payment_term")){//账期天数
			saleorder.setPeriodDay(Integer.parseInt(dataMap.get("payment_term").toString()));
		}
		
		if(null != dataMap.get("update_datetime")){
			saleorder.setModTime(DateUtil.convertLong(dataMap.get("update_datetime").toString(),"yyyy-MM-dd HH:mm:ss"));
		}
		if(null != dataMap.get("flag_del")){
			if(dataMap.get("flag_del").toString().equals("1")){//关闭订单
				saleorder.setStatus(3);
				//saleorder.setValidStatus(0);
			}
		}
		
		
		if(null != goodsDataMap && goodsDataMap.size() > 0){//订单产品明细
			List<SaleorderGoods> goodsList = new ArrayList<>();
			for(Map goods : goodsDataMap){
				SaleorderGoods saleorderGoods = new SaleorderGoods();
				if(null != goods.get("product_id")){
					saleorderGoods.setGoodsId(Integer.parseInt(goods.get("product_id").toString()));
					if(null != goods.get("product_price")){
						saleorderGoods.setPrice(new BigDecimal(goods.get("product_price").toString()));
					}
					if(null != goods.get("product_num")){
						saleorderGoods.setNum(Integer.parseInt(goods.get("product_num").toString()));
					}
					if(null != goods.get("product_unit")){
						saleorderGoods.setUnitName(goods.get("product_unit").toString());
					}
					
					Goods goodsInfo = new Goods(); 
					goodsInfo.setGoodsId(saleorderGoods.getGoodsId());
					Goods goodsById = goodsService.getGoodsById(goodsInfo);
					
					saleorderGoods.setSku(goodsById.getSku());
					saleorderGoods.setGoodsName(goodsById.getGoodsName());
					saleorderGoods.setBrandName(goodsById.getBrandName());
					saleorderGoods.setUnitName(goodsById.getUnitName());
					saleorderGoods.setModel(goodsById.getModel());
					saleorderGoods.setAddTime(time);
					saleorderGoods.setModTime(time);
					
					saleorderGoods.setDeliveryCycle("14");
					
					goodsList.add(saleorderGoods);
				}
			}
			
			saleorder.setGoodsList(goodsList);
		}
		
		OrdergoodsStoreAccount webAccountVo = null;
		if(null != dataMap.get("customer_saleorder")){
			Integer webAccountId = Integer.parseInt(dataMap.get("customer_saleorder").toString());
			OrdergoodsStoreAccount ordergoodsStoreAccount = new OrdergoodsStoreAccount();
			ordergoodsStoreAccount.setWebAccountId(webAccountId);
			Integer oStoreId= 1;
			if(null != dataMap.get("order_type")){//订单类型
				if(dataMap.get("order_type").toString().equals("4")){
					 oStoreId= 2;
				}
			}
			ordergoodsStoreAccount.setOrdergoodsStoreId(oStoreId);
			webAccountVo = ordergoodsStoreAccountMapper.getOrdergoodsStoreAccount(ordergoodsStoreAccount);
			if(null == webAccountVo || webAccountVo.getTraderContactId() == null || webAccountVo.getTraderAddressId() == null){
				resultInfo.setMessage("账号信息错误");
				resultInfo.setKey_id(0);
				return xmlExercise.convertToXml(resultInfo);
			}
		}else{
			resultInfo.setMessage("无客户信息");
			resultInfo.setKey_id(0);
			return xmlExercise.convertToXml(resultInfo);
		}
		
		if(saleorder.getSaleorderId() == null || saleorder.getSaleorderId() <= 0){//新增
			saleorder.setCompanyId(1);
			saleorder.setSaleorderNo(dataMap.get("saleorder_no").toString());
			if(null != dataMap.get("parent_id")){//商机ID
				saleorder.setParentId(Integer.parseInt(dataMap.get("parent_id").toString()));
			}
			if(null != dataMap.get("order_type")){//订单类型
				saleorder.setOrderType(Integer.parseInt(dataMap.get("order_type").toString()));
			}
			
			
			List<Integer> ids = new ArrayList<>();
			ids.add(webAccountVo.getTraderContactId());
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
			String url=httpUrl + "tradercustomer/getdhtraderinfo.htm";
			ResultInfo<?> result;
			try {
				result = (ResultInfo<?>) HttpClientUtils.post(url, ids,clientId,clientKey, TypeRef);
				List<TraderCustomerVo> customerList = (List<TraderCustomerVo>) result.getData();
				if(null != customerList && customerList.size() > 0){
					for(TraderCustomerVo customer : customerList){
						if(customer.getTraderContactId().equals(webAccountVo.getTraderContactId())){
							saleorder.setTraderId(customer.getTraderId());//交易者ID
							
							saleorder.setTakeTraderId(customer.getTraderId());
							saleorder.setInvoiceTraderId(customer.getTraderId());
							
							saleorder.setCustomerType(customer.getCustomerType());
							saleorder.setCustomerNature(customer.getCustomerNature());
							saleorder.setTraderName(customer.getTraderName());
							
							saleorder.setTakeTraderName(customer.getTraderName());
							saleorder.setInvoiceTraderName(customer.getTraderName());
							//如果客户是终端的话
							if(customer.getCustomerNature() != null && customer.getCustomerNature().equals(466)){
							    //终端客户ID
							    saleorder.setTerminalTraderId(customer.getTraderId());
							    //终端名称
							    saleorder.setTerminalTraderName(customer.getTraderName());
							    //终端类型
							    saleorder.setTerminalTraderType(customer.getCustomerType());
							    //销售区域ID
							    saleorder.setSalesAreaId(customer.getAreaId());
							    if(customer.getAreaId() != null && customer.getAreaId() != 0){
								String region = (String) regionService.getRegion(customer.getAreaId(), 2);
								if(region != null){
								    //销售区域
								    saleorder.setSalesArea(region);
								}
							    }
							    
							}
							
						}
					}
				}
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
			
			TraderContact traderContact = new TraderContact();
			traderContact.setName(saleorder.getTakeTraderContactName());
			traderContact.setMobile(saleorder.getTakeTraderContactMobile());
			traderContact.setTelephone(saleorder.getTakeTraderContactTelephone());
			traderContact.setTraderId(saleorder.getTraderId());
			traderContact.setTraderType(1);
			traderContact.setAddTime(time);
			traderContact.setModTime(time);
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<TraderContact>> traderContactTypeRef2 = new TypeReference<ResultInfo<TraderContact>>() {};
			try {
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettradercontactoraddtradercontact.htm", traderContact,clientId,clientKey, traderContactTypeRef2);
				TraderContact res = (TraderContact) result2.getData();
				if(res != null){
					saleorder.setTakeTraderContactId(res.getTraderContactId());//收货人ID
				}
			} catch (IOException e) {
				resultInfo.setMessage("联系人错误");
				resultInfo.setKey_id(0);
				return xmlExercise.convertToXml(resultInfo);
			}
			
			TraderContact invoiceTraderContact = new TraderContact();
			invoiceTraderContact.setName(saleorder.getInvoiceTraderContactName());
			invoiceTraderContact.setMobile(saleorder.getInvoiceTraderContactMobile());
			invoiceTraderContact.setTelephone(saleorder.getInvoiceTraderContactTelephone());
			invoiceTraderContact.setTraderId(saleorder.getTraderId());
			invoiceTraderContact.setTraderType(1);
			invoiceTraderContact.setAddTime(time);
			invoiceTraderContact.setModTime(time);
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<TraderContact>> TypeRef2 = new TypeReference<ResultInfo<TraderContact>>() {};
			try {
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettradercontactoraddtradercontact.htm", invoiceTraderContact,clientId,clientKey, TypeRef2);
				TraderContact res = (TraderContact) result2.getData();
				if(res != null){
					saleorder.setInvoiceTraderContactId(res.getTraderContactId());//收票人ID
				}
			} catch (IOException e) {
				resultInfo.setMessage("联系人错误2");
				resultInfo.setKey_id(0);
				return xmlExercise.convertToXml(resultInfo);
			}
			
			if(null != dataMap.get("area_id")){//收货地址最小级ID
				//地址是否存在，不存在就新增
				TraderAddress address = new TraderAddress();
				address.setTraderType(1);
				address.setTraderId(saleorder.getTraderId());
				address.setAreaId(Integer.parseInt(dataMap.get("area_id").toString()));
				address.setAddTime(time);
				address.setModTime(time);
				List<Region> region = (List<Region>) regionService.getRegion(address.getAreaId(),1);
				if(null != region && region.size() > 0){
					String areaIds = ""; 
					Integer i = 1;
					for(Region r : region){
						if(i.equals(region.size())){
							areaIds += r.getRegionId();
						}else{
							areaIds += r.getRegionId()+",";
						}
						i++;
					}
					
					address.setAreaIds(areaIds);
				}
				address.setAddress(dataMap.get("address").toString());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<TraderAddress>> addressTypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {};
				try {
					ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettraderaddressoraddtraderaddress.htm", address,clientId,clientKey, addressTypeRef2);
					TraderAddress res = (TraderAddress) result2.getData();
					if(res != null){
						saleorder.setTakeTraderAddressId(res.getTraderAddressId());
					}
				} catch (IOException e) {
					return xmlExercise.convertToXml(resultInfo);
				}
			}
			
			if(null != dataMap.get("invoice_area_id")){
				//地址是否存在，不存在就新增
				TraderAddress address = new TraderAddress();
				address.setTraderType(1);
				address.setTraderId(saleorder.getTraderId());
				address.setAreaId(Integer.parseInt(dataMap.get("invoice_area_id").toString()));
				address.setAddTime(time);
				address.setModTime(time);
				List<Region> region = (List<Region>) regionService.getRegion(address.getAreaId(),1);
				if(null != region && region.size() > 0){
					String areaIds = ""; 
					Integer i = 1;
					for(Region r : region){
						if(i.equals(region.size())){
							areaIds += r.getRegionId();
						}else{
							areaIds += r.getRegionId()+",";
						}
						i++;
					}
					
					address.setAreaIds(areaIds);
				}
				address.setAddress(dataMap.get("invoice_address").toString());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<TraderAddress>> addressTypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {};
				try {
					ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettraderaddressoraddtraderaddress.htm", address,clientId,clientKey, addressTypeRef2);
					TraderAddress res = (TraderAddress) result2.getData();
					if(res != null){
						saleorder.setInvoiceTraderAddressId(res.getTraderAddressId());
					}
				} catch (IOException e) {
					return xmlExercise.convertToXml(resultInfo);
				}
			}
			if(null != dataMap.get("create_datetime")){
				saleorder.setAddTime(DateUtil.convertLong(dataMap.get("create_datetime").toString(),"yyyy-MM-dd HH:mm:ss"));
			}
			
			//归属部门+人
			if(saleorder.getTraderId() != null && saleorder.getTraderId() > 0){
				User userInfo = userService.getUserInfoByTraderId(saleorder.getTraderId(), 1);
				saleorder.setOrgId(userInfo.getOrgId());
				saleorder.setUserId(userInfo.getUserId());
			}
			
//			if(null != payDataMap){//账期付款信息不存 订单生效时自动触发资金流水 
//				
//			}
		}else{//编辑
			
			TraderContact traderContact = new TraderContact();
			traderContact.setName(saleorder.getTakeTraderContactName());
			traderContact.setMobile(saleorder.getTakeTraderContactMobile());
			traderContact.setTelephone(saleorder.getTakeTraderContactTelephone());
			traderContact.setTraderId(saleorder.getTraderId());
			traderContact.setTraderType(1);
			traderContact.setAddTime(time);
			traderContact.setModTime(time);
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<TraderContact>> traderContactTypeRef2 = new TypeReference<ResultInfo<TraderContact>>() {};
			try {
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettradercontactoraddtradercontact.htm", traderContact,clientId,clientKey, traderContactTypeRef2);
				TraderContact res = (TraderContact) result2.getData();
				if(res != null){
					saleorder.setTakeTraderContactId(res.getTraderContactId());//收货人ID
				}
			} catch (IOException e) {
				resultInfo.setMessage("联系人错误3");
				resultInfo.setKey_id(0);
				return xmlExercise.convertToXml(resultInfo);
			}
			
			TraderContact invoiceTraderContact = new TraderContact();
			invoiceTraderContact.setName(saleorder.getInvoiceTraderContactName());
			invoiceTraderContact.setMobile(saleorder.getInvoiceTraderContactMobile());
			invoiceTraderContact.setTelephone(saleorder.getInvoiceTraderContactTelephone());
			invoiceTraderContact.setTraderId(saleorder.getTraderId());
			invoiceTraderContact.setTraderType(1);
			invoiceTraderContact.setAddTime(time);
			invoiceTraderContact.setModTime(time);
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<TraderContact>> TypeRef2 = new TypeReference<ResultInfo<TraderContact>>() {};
			try {
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettradercontactoraddtradercontact.htm", invoiceTraderContact,clientId,clientKey, TypeRef2);
				TraderContact res = (TraderContact) result2.getData();
				if(res != null){
					saleorder.setInvoiceTraderContactId(res.getTraderContactId());//收票人ID
				}
			} catch (IOException e) {
				resultInfo.setMessage("联系人错误4");
				resultInfo.setKey_id(0);
				return xmlExercise.convertToXml(resultInfo);
			}
			
			if(null != dataMap.get("area_id")){//收货地址最小级ID
				//地址是否存在，不存在就新增
				TraderAddress address = new TraderAddress();
				address.setTraderType(1);
				address.setTraderId(saleorder.getTraderId());
				address.setAreaId(Integer.parseInt(dataMap.get("area_id").toString()));
				address.setAddTime(time);
				address.setModTime(time);
				List<Region> region = (List<Region>) regionService.getRegion(address.getAreaId(),1);
				if(null != region && region.size() > 0){
					String areaIds = ""; 
					Integer i = 1;
					for(Region r : region){
						if(i.equals(region.size())){
							areaIds += r.getRegionId();
						}else{
							areaIds += r.getRegionId()+",";
						}
						i++;
					}
					
					address.setAreaIds(areaIds);
				}
				address.setAddress(dataMap.get("address").toString());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<TraderAddress>> addressTypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {};
				try {
					ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettraderaddressoraddtraderaddress.htm", address,clientId,clientKey, addressTypeRef2);
					TraderAddress res = (TraderAddress) result2.getData();
					if(res != null){
						saleorder.setTakeTraderAddressId(res.getTraderAddressId());
					}
				} catch (IOException e) {
					return xmlExercise.convertToXml(resultInfo);
				}
			}
			
			if(null != dataMap.get("invoice_area_id")){
				//地址是否存在，不存在就新增
				TraderAddress address = new TraderAddress();
				address.setTraderType(1);
				address.setTraderId(saleorder.getTraderId());
				address.setAreaId(Integer.parseInt(dataMap.get("invoice_area_id").toString()));
				address.setAddTime(time);
				address.setModTime(time);
				List<Region> region = (List<Region>) regionService.getRegion(address.getAreaId(),1);
				if(null != region && region.size() > 0){
					String areaIds = ""; 
					Integer i = 1;
					for(Region r : region){
						if(i.equals(region.size())){
							areaIds += r.getRegionId();
						}else{
							areaIds += r.getRegionId()+",";
						}
						i++;
					}
					
					address.setAreaIds(areaIds);
				}
				address.setAddress(dataMap.get("invoice_address").toString());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<TraderAddress>> addressTypeRef2 = new TypeReference<ResultInfo<TraderAddress>>() {};
				try {
					ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "trader/gettraderaddressoraddtraderaddress.htm", address,clientId,clientKey, addressTypeRef2);
					TraderAddress res = (TraderAddress) result2.getData();
					if(res != null){
						saleorder.setInvoiceTraderAddressId(res.getTraderAddressId());
					}
				} catch (IOException e) {
					return xmlExercise.convertToXml(resultInfo);
				}
			}
		}
		
//		if(saleorder.getTraderContactId() == null){
//			saleorder.setTraderContactId(webAccountVo.getTraderContactId());
//		}
//		if(saleorder.getTraderAddressId() == null){
//			saleorder.setTraderAddressId(webAccountVo.getTraderAddressId());
//		}
//		if(saleorder.getTakeTraderContactId() == null){
//			saleorder.setTakeTraderContactId(webAccountVo.getTraderContactId());
//		}
//		if(saleorder.getTakeTraderAddressId() == null){
//			saleorder.setTakeTraderAddressId(webAccountVo.getTraderAddressId());
//		}
//		if(saleorder.getInvoiceTraderContactId() == null){
//			saleorder.setInvoiceTraderContactId(webAccountVo.getTraderContactId());
//		}
//		if(saleorder.getInvoiceTraderAddressId() == null){
//			saleorder.setInvoiceTraderAddressId(webAccountVo.getTraderAddressId());
//		}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/saleorder/saveorupdatesaleorder.htm", saleorder,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			Saleorder resData = (Saleorder) result2.getData();
			if(code.equals(0)){
				
				//站内信（新增订单、取消订单）
				if(saleorder.getUserId() != null && saleorder.getUserId() > 0){
					List<Integer> userIds = new ArrayList<>();
					userIds.add(saleorder.getUserId());
					Map<String, String> map = new HashMap<>();
					String url = null;
					map.put("saleorderNo", saleorder.getSaleorderNo());
					if(saleorder.getSaleorderId() == null || saleorder.getSaleorderId() <= 0){
						url = "./order/saleorder/view.do?saleorderId=" + resData.getSaleorderId();
						MessageUtil.sendMessage(67, userIds, map, url);
					}else{
						if(null != dataMap.get("flag_del")){
							if(dataMap.get("flag_del").toString().equals("1")){//关闭订单
								url = "./order/saleorder/view.do?saleorderId=" + saleorder.getSaleorderId();
								MessageUtil.sendMessage(68, userIds, map, url);
							}
						}
					}
				}
				
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setKey_id(resData.getSaleorderId());
				return xmlExercise.convertToXml(resultInfo);
			}else{
				resultInfo.setMessage("新增/编辑失败");
				resultInfo.setKey_id(0);
				return xmlExercise.convertToXml(resultInfo);
			}
		} catch (IOException e) {
			resultInfo.setMessage("新增/编辑失败");
			resultInfo.setKey_id(0);
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String accountSync(String data, String uuid) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		if(null == uuid || uuid.equals("")){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		Map info = xmlExercise.xmlToMap(data);
		
		if(!info.isEmpty()){
			Map account = (Map) info.get("account");
			
			WebAccountVo web = new WebAccountVo();
			web.setUuid(account.get("uuid").toString());
			WebAccountVo webAccount = webAccountMapper.getWebAccount(web);
			if(webAccount != null){
				web.setErpAccountId(webAccount.getErpAccountId());
				web.setWebAccountId(Integer.parseInt(account.get("web_account_id").toString()));
				web.setCompanyStatus(Integer.parseInt(account.get("company_status").toString()));
				web.setIndentityStatus(Integer.parseInt(account.get("indentity_status").toString()));
				web.setIsOpenStore(Integer.parseInt(account.get("is_open_store").toString()));
				web.setAccount(account.get("account").toString());
				web.setEmail(account.get("email").toString());
				web.setMobile(account.get("mobile").toString());
				web.setWeixinOpenid(account.get("weixin_openid").toString());
				web.setLastLoginTime(Long.parseLong(account.get("last_login_time").toString())*1000);
				web.setIsEnable(Integer.parseInt(account.get("status").toString()));
				
				if(info.containsKey("extra")){
					Map extra = (Map) info.get("extra");
					web.setCompanyName(extra.get("corporation").toString());
					web.setName(extra.get("real_name").toString());
					web.setSex(Integer.parseInt(extra.get("sex").toString()));
				}
				
				int update = webAccountMapper.update(web);
				if(update > 0){
					resultInfo.setCode(1);
					resultInfo.setMessage("操作成功");
					resultInfo.setKey_id(Integer.parseInt(account.get("web_account_id").toString()));
				}
				
			}else{
				webAccount = new WebAccountVo();
				if(null != account.get("web_account_id")){
					webAccount.setWebAccountId(Integer.parseInt(account.get("web_account_id").toString()));
				}
				if(null != account.get("company_status")){
					webAccount.setCompanyStatus(Integer.parseInt(account.get("company_status").toString()));
				}
				if(null != account.get("company_status")){
					webAccount.setCompanyStatus(Integer.parseInt(account.get("company_status").toString()));
				}
				if(null != account.get("indentity_status")){
					webAccount.setIndentityStatus(Integer.parseInt(account.get("indentity_status").toString()));
				}
				if(null != account.get("is_open_store")){
					webAccount.setIsOpenStore(Integer.parseInt(account.get("is_open_store").toString()));
				}
				if(null != account.get("account")){
					webAccount.setAccount(account.get("account").toString());
				}
				if(null != account.get("email")){
					webAccount.setEmail(account.get("email").toString());
				}
				if(null != account.get("last_login_time")){
					webAccount.setLastLoginTime(Long.parseLong(account.get("last_login_time").toString())*1000);
				}
					
				webAccount.setMobile(account.get("mobile").toString());
				webAccount.setUuid(account.get("uuid").toString()); 
				webAccount.setWeixinOpenid(account.get("weixin_openid").toString());
				webAccount.setFrom(Integer.parseInt(account.get("from").toString()));
				webAccount.setAddTime(Long.parseLong(account.get("add_time").toString())*1000);
				
				if(info.containsKey("extra")){
					Map extra = (Map) info.get("extra");
					webAccount.setCompanyName(extra.get("corporation").toString());
					
					if(null != extra.get("real_name")){
						webAccount.setName(extra.get("real_name").toString());
					}
					if(null != extra.get("sex")){
						webAccount.setSex(Integer.parseInt(extra.get("sex").toString()));
					}
				}
				
				int insert = webAccountMapper.insert(webAccount);
				if(insert > 0){
					resultInfo.setCode(1);
					resultInfo.setMessage("操作成功");
					resultInfo.setKey_id(webAccount.getWebAccountId());
				}
			}
			
		}
		
		return xmlExercise.convertToXml(resultInfo);
	}

	@Override
	public String attachmentSync(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据
		
		if(null == data ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		dataMap = xmlExercise.xmlToMap(data);
		Attachment attachment = new Attachment();
		attachment.setAddTime(Long.parseLong(dataMap.get("addTime").toString()));
		attachment.setAttachmentFunction(Integer.parseInt(dataMap.get("attachmentFunction").toString()));;
		attachment.setAttachmentType(Integer.parseInt(dataMap.get("attachmentType").toString()));
		attachment.setDomain(dataMap.get("domain").toString());
		attachment.setName(dataMap.get("name").toString());
		
		//判断订单是否存在
		Saleorder saleorderInfo = new Saleorder();
		saleorderInfo.setSaleorderNo(dataMap.get("relatedNo").toString());
		Saleorder saleorder = saleorderService.getSaleorderBySaleorderNo(saleorderInfo);
		if(null == saleorder){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		attachment.setRelatedId(saleorder.getSaleorderId());
		attachment.setUri(dataMap.get("uri").toString());
		attachment.setAlt(dataMap.get("alt").toString());
		attachment.setIsDefault(Integer.parseInt(dataMap.get("isDefault").toString()));
		attachment.setSort(Integer.parseInt(dataMap.get("sort").toString()));
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Attachment>> TypeRef2 = new TypeReference<ResultInfo<Attachment>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "attachment/syncsaveattachment.htm", attachment,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			Attachment resData = (Attachment) result2.getData();
			if(code.equals(0)){
				
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setKey_id(resData.getAttachmentId());
				
				return xmlExercise.convertToXml(resultInfo);
			}else{
				return xmlExercise.convertToXml(resultInfo);
			}
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String quoteorderSync(String data, String orderItemData) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据
		List<Map<String, Object>> goodsDataMap = null;//产品数据
		
		if(null == data || null == orderItemData){//报价主表、产品缺失直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		
		dataMap = xmlExercise.xmlToMap(data);
		goodsDataMap = xmlExercise.xmlToList(orderItemData);
		
		Quoteorder quoteorder = new Quoteorder();
		
		//分配销售
		Integer webAccountId = Integer.parseInt(dataMap.get("web_account_id").toString());
		
		WebAccount webAccount = webAccountMapper.getAccountOwnerInfo(webAccountId);
		if(null != webAccount){
			quoteorder.setUserId(webAccount.getUserId());
			quoteorder.setOrgId(webAccount.getOrgId());
			quoteorder.setTraderContactId(webAccount.getTraderContactId());
			quoteorder.setTraderAddressId(webAccount.getTraderAddressId());
		}else{
			resultInfo.setMessage("账号未绑定客户");
			return xmlExercise.convertToXml(resultInfo);
		}
		quoteorder.setCompanyId(1);
		quoteorder.setSource(Integer.parseInt(dataMap.get("source").toString()));
		quoteorder.setAddTime(Long.parseLong(dataMap.get("add_time").toString()));
		quoteorder.setModTime(Long.parseLong(dataMap.get("mod_time").toString()));
		quoteorder.setTotalAmount(new BigDecimal(dataMap.get("total_money").toString()));
		
		if(null != goodsDataMap && goodsDataMap.size() > 0){//产品明细
			List<QuoteorderGoods> goodsList = new ArrayList<>();
			for(Map goods : goodsDataMap){
				QuoteorderGoods quoteorderGoods = new QuoteorderGoods();
				if(null != goods.get("product_id")){
					quoteorderGoods.setGoodsId(Integer.parseInt(goods.get("product_id").toString()));
					if(null != goods.get("product_num")){
						quoteorderGoods.setNum(Integer.parseInt(goods.get("product_num").toString()));
					}
					if(null != goods.get("product_comments")){
						quoteorderGoods.setGoodsComments(goods.get("product_comments").toString());
					}
					if(null != goods.get("product_price")){
						quoteorderGoods.setPrice(new BigDecimal(goods.get("product_price").toString()));;
					}
					
					goodsList.add(quoteorderGoods);
				}
			}
			
			quoteorder.setQuoteorderGoods(goodsList);
		}
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef2 = new TypeReference<ResultInfo<Quoteorder>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/quote/syncsavequote.htm", quoteorder,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			Quoteorder resData = (Quoteorder) result2.getData();
			if(code.equals(0)){
				
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setKey_id(resData.getQuoteorderId());
				resultInfo.setData(resData.getQuoteorderNo());//返回的报价单号
				return xmlExercise.convertToXml(resultInfo);
			}else{
				return xmlExercise.convertToXml(resultInfo);
			}
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String saleorderInvoiceApplySync(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据

		if(null == data ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		dataMap = xmlExercise.xmlToMap(data);
		Saleorder saleorder = new Saleorder();
		
		saleorder.setSaleorderNo(dataMap.get("saleorderNo").toString());
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "finance/invoice/syncsaveinvoiceapply.htm", saleorder,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			if(code.equals(0)){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
			}else if(code.equals(-2)){
				resultInfo.setCode(0);
				resultInfo.setMessage(result2.getMessage());
			}
			return xmlExercise.convertToXml(resultInfo);
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String saleorderArrivalSync(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据

		if(null == data ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		dataMap = xmlExercise.xmlToMap(data);
		Saleorder saleorder = new Saleorder();
		
		saleorder.setSaleorderNo(dataMap.get("saleorderNo").toString());
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef2 = new TypeReference<ResultInfo<Saleorder>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/saleorder/syncsavesaleorderarrivalstatus.htm", saleorder,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			if(code.equals(0)){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				
				//客户收货通知
				vedengSoapService.messageSyncWeb(2, (Integer)result2.getData(), 5);
			}else if(code.equals(-2)){
				resultInfo.setCode(0);
				resultInfo.setMessage(result2.getMessage());
			}
			return xmlExercise.convertToXml(resultInfo);
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String periodInfoSync(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据

		if(null == data ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		dataMap = xmlExercise.xmlToMap(data);
//		Saleorder saleorder = new Saleorder();
//		saleorder.setSaleorderNo(dataMap.get("saleorderNo").toString());
		Integer webAccountId = Integer.parseInt(dataMap.get("webAccountId").toString());
		WebAccount webAccount = webAccountMapper.getAccountOwnerInfo(webAccountId);
		if(webAccount == null){
			return xmlExercise.convertToXml(resultInfo);
		}
		SaleorderVo saleorderVo = new SaleorderVo();
		saleorderVo.setTraderContactId(webAccount.getTraderContactId());
		if(dataMap.containsKey("saleorderNo")){
			saleorderVo.setSaleorderNo(dataMap.get("saleorderNo").toString());
		}
		if(dataMap.containsKey("overdueStatus")){
			saleorderVo.setOverdueStatus(Integer.parseInt(dataMap.get("overdueStatus").toString()));
		}
		if(dataMap.containsKey("beginTime")){
			saleorderVo.setBeginTime(Long.parseLong(dataMap.get("beginTime").toString()));
		}
		if(dataMap.containsKey("endTime")){
			saleorderVo.setEndTime(Long.parseLong(dataMap.get("endTime").toString()));
		}
		Integer pageNo = 1;
		if(dataMap.containsKey("pageNo")){
			pageNo = Integer.parseInt(dataMap.get("pageNo").toString());
		}
		Integer pageSize = 10;
		if(dataMap.containsKey("pageSize")){
			pageSize = Integer.parseInt(dataMap.get("pageSize").toString());
		}
		Page page = Page.newBuilder(pageNo, pageSize, null);
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "report/sale/getsaleorderperiodlistpage.htm",saleorderVo,clientId,clientKey, TypeRef2,page);
			Integer code = result2.getCode();
			if(code.equals(0)){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				page = result2.getPage();
				
				Map<String, Object> map = (Map<String, Object>) result2.getData();
				
				if(map.containsKey("list")){
					JSONArray jsonarray = JSONArray.fromObject(map.get("list"));
					
					JSONObject json = JSONObject.fromObject(map.get("customer"));
				
					resultInfo.setData(jsonarray.toString()+"|"+json.toString()+"|"+page.getTotalRecord());
					
					
				}else{
					resultInfo.setData(null);
				}
				//resultInfo.setKey_id();
			}else if(code.equals(-2)){
				resultInfo.setCode(0);
				resultInfo.setMessage(result2.getMessage());
			}
			return xmlExercise.convertToXml(resultInfo);
		} catch (IOException e) { 
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String orderSyncErp(String data, String goodsData) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		Long time = DateUtil.sysTimeMillis();
		
		Map dataMap = null;//订单主表数据
		List<Map<String, Object>> goodsDataMap = null;//订单产品数据
		
		if(null == data || null == goodsData){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		//订单主表信息
		dataMap = xmlExercise.xmlToMap(data);
		
		//订单产品信息
		goodsDataMap = xmlExercise.xmlToList(goodsData);
		
		SaleorderVo saleorder = new SaleorderVo();
		
		//分配销售
		Integer webAccountId = Integer.parseInt(dataMap.get("web_account_id").toString());
		
		WebAccount webAccount = webAccountMapper.getAccountOwnerInfo(webAccountId);
		if(null != webAccount){
			saleorder.setUserId(webAccount.getUserId());
			saleorder.setOrgId(webAccount.getOrgId());
			saleorder.setTraderContactId(webAccount.getTraderContactId());
			saleorder.setTraderAddressId(webAccount.getTraderAddressId());
		}else{
			resultInfo.setMessage("账号未绑定客户");
			return xmlExercise.convertToXml(resultInfo);
		}
		saleorder.setQuoteorderNo(dataMap.get("quoteorderNo").toString());
		saleorder.setCompanyId(1);
		saleorder.setSource(Integer.parseInt(dataMap.get("source").toString()));
		saleorder.setTotalAmount(new BigDecimal(dataMap.get("totalAmount").toString()));
		saleorder.setPrepaidAmount(new BigDecimal(dataMap.get("totalAmount").toString()));
		saleorder.setPaymentType(419);//默认100%预付
		
		saleorder.setTakeTraderContactName(dataMap.get("takeTraderContactName").toString());
		saleorder.setTakeTraderContactMobile(dataMap.get("takeTraderContactMobile").toString());
		saleorder.setTakeTraderContactTelephone(dataMap.get("takeTraderContactTelephone").toString());
		saleorder.setTakeTraderAddress(dataMap.get("takeTraderAddress").toString());
		
		saleorder.setInvoiceTraderContactName(dataMap.get("invoiceTraderContactName").toString());
		saleorder.setInvoiceTraderContactMobile(dataMap.get("invoiceTraderContactMobile").toString());
		saleorder.setInvoiceTraderAddress(dataMap.get("invoiceTraderAddress").toString());
		
		saleorder.setComments(dataMap.get("comments").toString());
		saleorder.setAddTime(Long.parseLong(dataMap.get("addTime").toString()));
		saleorder.setModTime(Long.parseLong(dataMap.get("modTime").toString()));
		
		
		if(null != goodsDataMap && goodsDataMap.size() > 0){//产品明细
			List<SaleorderGoods> goodsList = new ArrayList<>();
			for(Map goods : goodsDataMap){
				SaleorderGoods saleorderGoods = new SaleorderGoods();
				if(null != goods.get("goodsId")){
					saleorderGoods.setGoodsId(Integer.parseInt(goods.get("goodsId").toString()));
					if(null != goods.get("num")){
						saleorderGoods.setNum(Integer.parseInt(goods.get("num").toString()));
					}
					if(null != goods.get("price")){
						saleorderGoods.setPrice(new BigDecimal(goods.get("price").toString()));
					}
					if(null != goods.get("addTime")){
						saleorderGoods.setAddTime(Long.parseLong(goods.get("addTime").toString()));
					}
					if(null != goods.get("modTime")){
						saleorderGoods.setModTime(Long.parseLong(goods.get("modTime").toString()));
					}
					if(null != goods.get("deliveryCycle")){
						saleorderGoods.setDeliveryCycle(goods.get("deliveryCycle").toString());
					}
					
					goodsList.add(saleorderGoods);
				}
			}
			
			saleorder.setGoodsList(goodsList);
		}
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<SaleorderVo>> TypeRef2 = new TypeReference<ResultInfo<SaleorderVo>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/saleorder/syncsavesaleorder.htm", saleorder,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			SaleorderVo resData = (SaleorderVo) result2.getData();
			if(code.equals(0)){
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setKey_id(resData.getSaleorderId());
				
				String returnData = "";
				returnData = resData.getSaleorderNo();
				if(null != resData.getTraderFinanceVo()){
					TraderFinanceVo traderFinanceVo = resData.getTraderFinanceVo();
					returnData += "|&|"+resData.getTraderName();
					returnData += "|&|"+"";
					returnData += "|&|"+resData.getTraderName();
					returnData += "|&|"+traderFinanceVo.getTaxNum();
					returnData += "|&|"+traderFinanceVo.getRegAddress();
					returnData += "|&|"+traderFinanceVo.getRegTel();
					returnData += "|&|"+traderFinanceVo.getBank();
					returnData += "|&|"+traderFinanceVo.getBankAccount();
					
				}
				
				resultInfo.setData(returnData);//返回的单号+发票信息
				return xmlExercise.convertToXml(resultInfo);
			}else{
				return xmlExercise.convertToXml(resultInfo);
			}
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
		
	}

	@Override
	public String enquirySyncErp(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据
		
		if(null == data){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		dataMap = xmlExercise.xmlToMap(data);
		
		BussinessChance bussinessChance = new BussinessChance();
		
		bussinessChance.setType(394);
		bussinessChance.setWebBussinessChanceId(Integer.parseInt(dataMap.get("webBussinessChanceId").toString()));
		bussinessChance.setWebAccountId(Integer.parseInt(dataMap.get("webAccountId").toString()));
		bussinessChance.setCompanyId(Integer.parseInt(dataMap.get("companyId").toString()));
		bussinessChance.setReceiveTime(Long.parseLong(dataMap.get("receiveTime").toString()));
		bussinessChance.setSource(Integer.parseInt(dataMap.get("source").toString()));
		bussinessChance.setCommunication(Integer.parseInt(dataMap.get("communication").toString()));
		bussinessChance.setTraderName(dataMap.get("traderName").toString());
		bussinessChance.setTraderContactName(dataMap.get("traderContactName").toString());
		bussinessChance.setGoodsBrand(dataMap.get("goodsBrand").toString());
		bussinessChance.setGoodsName(dataMap.get("goodsName").toString());
		bussinessChance.setMobile(dataMap.get("mobile").toString());
		bussinessChance.setContent(dataMap.get("content").toString());
		bussinessChance.setAddTime(Long.parseLong(dataMap.get("addTime").toString()));
		bussinessChance.setModTime(Long.parseLong(dataMap.get("modTime").toString()));
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<BussinessChance>> TypeRef2 = new TypeReference<ResultInfo<BussinessChance>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/bussinesschance/syncsaveenquiry.htm", bussinessChance,clientId,clientKey, TypeRef2);
			Integer code = result2.getCode();
			BussinessChance resData = (BussinessChance) result2.getData();
			if(code.equals(0)){
				
				resultInfo.setCode(0);
				resultInfo.setMessage("操作成功");
				resultInfo.setKey_id(resData.getBussinessChanceId());
				resultInfo.setData(resData.getBussinessChanceNo());//返回单号
				return xmlExercise.convertToXml(resultInfo);
			}else{
				return xmlExercise.convertToXml(resultInfo);
			}
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String applyInvoice(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;
		dataMap = xmlExercise.xmlToMap(data);
		
		if(null == dataMap || dataMap.get("order_no") == null || dataMap.get("order_no").equals("")){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		
		String orderNo = dataMap.get("order_no").toString();
		
		Saleorder saleorder = new Saleorder();
		saleorder.setSaleorderNo(orderNo);
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "order/saleorder/applyinvoicecheck.htm", saleorder,clientId,clientKey, TypeRef2);
			resultInfo.setCode(result2.getCode());
			resultInfo.setMessage(result2.getMessage());
			return xmlExercise.convertToXml(resultInfo);
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String accountPeriodInfoSync(String data) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据

		if(null == data ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		dataMap = xmlExercise.xmlToMap(data);
		Integer webAccountId = Integer.parseInt(dataMap.get("webAccountId").toString());
		Integer ordergoodsStoreId = Integer.parseInt(dataMap.get("ordergoodsStoreId").toString());
		
		OrdergoodsStoreAccount ordergoodsStoreAccount = new OrdergoodsStoreAccount();
		ordergoodsStoreAccount.setWebAccountId(webAccountId);
		ordergoodsStoreAccount.setOrdergoodsStoreId(ordergoodsStoreId);
		OrdergoodsStoreAccount webAccount = ordergoodsStoreAccountMapper.getOrdergoodsStoreAccount(ordergoodsStoreAccount);
		
		if(webAccount == null){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		if(webAccount.getTraderContactId() == null && webAccount.getTraderContactId() <= 0){
			return xmlExercise.convertToXml(resultInfo);
		}
		
		Integer traderContactId = webAccount.getTraderContactId();
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderCustomer>> TypeRef2 = new TypeReference<ResultInfo<TraderCustomer>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "tradercustomer/getaccountperiodinfobycontact.htm", traderContactId,clientId,clientKey, TypeRef2);
			
			if(result2.getCode().equals(0)){
				TraderCustomer traderCustomer = (TraderCustomer) result2.getData();
				resultInfo.setData(traderCustomer.getPeriodAmount().toString()+"|"+traderCustomer.getAccountPeriodLeft().toString());
			}
			resultInfo.setCode(result2.getCode());
			resultInfo.setMessage(result2.getMessage());
			
			return xmlExercise.convertToXml(resultInfo);
		} catch (IOException e) {
			return xmlExercise.convertToXml(resultInfo);
		}
	}

	@Override
	public String printSaleContractPDF(String orderNo) {
		try {
//			String orderId = request.getParameter("saleorder_id").toString();
			if (StringUtils.isBlank(orderNo)) {
//				return (new ResultInfo<>(-1,"参数为空")).toSimpleString();
				return ("{\"code\":\"-1\",\"message\":\"参数为空\"}");
			}
			Saleorder saleorder = new Saleorder();
			saleorder.setCompanyId(1);
			saleorder.setSaleorderNo(orderNo);
			SaleorderVo saleorderVo = saleorderService.getPrintOrderInfo(saleorder);
			if(saleorderVo == null || saleorderVo.getSaleorderId() == null){
				return ("{\"code\":\"-1\",\"message\":\"未查询到相关订单信息\"}");
			}
			// 获取销售人员信息
			User user = userService.getUserByTraderId(saleorderVo.getTraderId(), 1);// 1客户，2供应商
			UserDetail detail = userDetailMapper.getUserDetail(user.getUserId());

			// 获取订单下的产品信息
			Saleorder sale = new Saleorder();
			sale.setSaleorderId(saleorderVo.getSaleorderId());
			List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
			
			
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String path = request.getSession().getServletContext().getRealPath("static/template/");
//			System.out.println(path);
			String htmlPath = path + "saleContract.html";
			String date = DateUtil.gainNowDate() + "";
			String newHtmlPath = path + date +".html";
			try {
				// read file content from file
				StringBuffer sb = new StringBuffer("");

				//将html读到
				InputStreamReader isr = new InputStreamReader(new FileInputStream(htmlPath), "UTF-8"); //或GB2312,GB18030
		        BufferedReader read = new BufferedReader(isr);
				BufferedReader br = new BufferedReader(read);
				String str = null;
				while ((str = br.readLine()) != null) {
					sb.append(str + "\r\n");
				}
				br.close();
				read.close();
				
				String string = sb.toString();
				
				int start_index = string.indexOf("<tr id='saleGoodsList'>");
				int end_index =  string.indexOf("<tr id='saleOrderAmount'>");
				StringBuffer saleGoodsSb = new StringBuffer("");
				
				BigDecimal pageTotalPrice = new BigDecimal(0.00);
				BigDecimal zioe = pageTotalPrice;
				Integer flag = -1;
				for(int i=0;i<saleorderGoodsList.size();i++){
					if (saleorderGoodsList.get(i).getHaveInstallation() == 1) {
						flag = 1;//是否有安调
					}
					String price = getCommaFormat(saleorderGoodsList.get(i).getPrice());
					if (!price.contains(".")) {//单价
						price += ".00";
					}
					saleorderGoodsList.get(i).setPrices(price);
					//每个商品总额
					String allprice = getCommaFormat(saleorderGoodsList.get(i).getPrice().multiply(new BigDecimal(saleorderGoodsList.get(i).getNum())));
					if (!allprice.contains(".")) {
						allprice += ".00";
					}
					//订单总额
					saleorderGoodsList.get(i).setAllPrice(allprice);
					pageTotalPrice = pageTotalPrice.add(saleorderGoodsList.get(i).getPrice().multiply(new BigDecimal(saleorderGoodsList.get(i).getNum())));
					
					saleGoodsSb.append("<tr>");
					saleGoodsSb.append("<td style='width: 4%;border-bottom:1px solid #000;'>"+(i+1)+"</td>");
					saleGoodsSb.append("<td style='width: 6%;border-bottom:1px solid #000;'>"+(saleorderGoodsList.get(i).getSku()==null?"":saleorderGoodsList.get(i).getSku())+"</td>");
					saleGoodsSb.append("<td style='width: 26%; text-align:left;border-bottom:1px solid #000;'>"+saleorderGoodsList.get(i).getGoodsName()+"</td>");
					saleGoodsSb.append("<td style='border-bottom:1px solid #000;'>"+(saleorderGoodsList.get(i).getBrandName()==null?"":saleorderGoodsList.get(i).getBrandName())+"</td>");
					saleGoodsSb.append("<td style='border-bottom:1px solid #000;'>"+(saleorderGoodsList.get(i).getModel()==null?"":saleorderGoodsList.get(i).getModel())+"</td>");
					saleGoodsSb.append("<td style='width: 4%;border-bottom:1px solid #000;'>"+saleorderGoodsList.get(i).getNum()+"</td>");
					saleGoodsSb.append("<td style='width: 4%;border-bottom:1px solid #000;'>"+(saleorderGoodsList.get(i).getUnitName()==null?"":saleorderGoodsList.get(i).getUnitName())+"</td>");
					saleGoodsSb.append("<td style='width: 10%;border-bottom:1px solid #000;'>"+saleorderGoodsList.get(i).getPrices()+"</td>");
					saleGoodsSb.append("<td style='width: 10%;border-bottom:1px solid #000;'>"+saleorderGoodsList.get(i).getAllPrice()+"</td>");
					saleGoodsSb.append("<td style='width: 6%;border-bottom:1px solid #000;'>"+(saleorderGoodsList.get(i).getDeliveryCycle()==null?"":saleorderGoodsList.get(i).getDeliveryCycle())+"</td>");
					saleGoodsSb.append("<td style='border-bottom:1px solid #000;'>"+(saleorderGoodsList.get(i).getGoodsComments()==null?"":saleorderGoodsList.get(i).getGoodsComments())+"</td>");
					saleGoodsSb.append("</tr>");
				}
				//替换商品列表
				sb.replace(start_index, end_index, saleGoodsSb.toString());

				//订单总金额
				String totalAmount = getCommaFormat(pageTotalPrice);
				if (!totalAmount.contains(".")) {
					totalAmount += ".00";
				}
				String chineseNumberTotalPrice = pageTotalPrice.compareTo(zioe) > 0 ? (DigitToChineseUppercaseNumberUtils.numberToChineseNumber(pageTotalPrice)) : null;

				string = sb.toString();
				string = string.replaceAll("\\{hetonghaoma\\}", saleorderVo.getSaleorderNo());//合同号码
				string = string.replaceAll("\\{zhidanriqi\\}", saleorderVo.getValidTime()==0?(DateUtil.DateToString(new Date(), "yyyy-MM-dd")):(DateUtil.convertString(saleorderVo.getValidTime(), "yyyy-MM-dd")));//制单日期
				string = string.replaceAll("\\{jiafang\\}", (saleorderVo.getTraderName()==null?"":saleorderVo.getTraderName()));//甲方
				string = string.replaceAll("\\{caigourenyuan\\}", (saleorderVo.getName()==null?"":saleorderVo.getName()) + "&nbsp;" + (saleorderVo.getSex()==null?"":(saleorderVo.getSex().equals(0)?"女士":"先生")));//采购人员
				string = string.replaceAll("\\{lianxifangshi\\}", (saleorderVo.getMobile()==null?"":saleorderVo.getMobile()));//联系方式
				
				string = string.replaceAll("\\{zhucexinxi\\}", (saleorderVo.getRegAddress()==null?"":saleorderVo.getRegAddress()) + "/" + (saleorderVo.getRegTel()==null?"":saleorderVo.getRegTel()));//注册地址/电话
				string = string.replaceAll("\\{kaihuhang\\}", (saleorderVo.getBank()==null?"":saleorderVo.getBank()));//开户行
				string = string.replaceAll("\\{shuihao\\}", (saleorderVo.getTaxNum()==null?"":saleorderVo.getTaxNum()));//税号
				string = string.replaceAll("\\{zhanghao\\}", (saleorderVo.getBankAccount()==null?"":saleorderVo.getBankAccount()));//账号
				string = string.replaceAll("\\{xiaoshourenyuan\\}", (detail.getRealName()==null?"":detail.getRealName()) + "&nbsp;" + (saleorderVo.getSex()==null?"":(saleorderVo.getSex().equals(0)?"女士":"先生")));//销售人员
				string = string.replaceAll("\\{lianxifangshi\\}", (detail.getTelephone()==null?"":detail.getTelephone()) + "/" + (detail.getMobile()==null?"":detail.getMobile()));//联系方式
				
				string = string.replaceAll("\\{shoupiaodanwei\\}", (saleorderVo.getInvoiceTraderName()==null?"":saleorderVo.getInvoiceTraderName()));//收票单位
				string = string.replaceAll("\\{shoupiaoren\\}", (saleorderVo.getInvoiceTraderContactName()==null?"":saleorderVo.getInvoiceTraderContactName())
																						+ (StringUtils.isBlank(saleorderVo.getInvoiceTraderContactMobile())?"":("/" + saleorderVo.getInvoiceTraderContactMobile())) 
																						+ (StringUtils.isBlank(saleorderVo.getInvoiceTraderContactTelephone())?"":("/" + saleorderVo.getInvoiceTraderContactTelephone())));//收票人
				string = string.replaceAll("\\{shoupiaodizhi\\}", (saleorderVo.getInvoiceTraderArea()==null?"":saleorderVo.getInvoiceTraderArea()) + " " + (saleorderVo.getInvoiceTraderAddress()==null?"":saleorderVo.getInvoiceTraderAddress()));//地址
				string = string.replaceAll("\\{shouhuodanwei\\}", (saleorderVo.getTakeTraderName()==null?"":saleorderVo.getTakeTraderName()));//收货单位
				string = string.replaceAll("\\{shouhuoren\\}", (saleorderVo.getTakeTraderContactName()==null?"":saleorderVo.getTakeTraderContactName())
																						+ (StringUtils.isBlank(saleorderVo.getTakeTraderContactMobile())?"":("/" + saleorderVo.getTakeTraderContactMobile())) 
																						+ (StringUtils.isBlank(saleorderVo.getTakeTraderContactTelephone())?"":("/" + saleorderVo.getTakeTraderContactTelephone())));//收货人
				string = string.replaceAll("\\{shouhuodizhi\\}", (saleorderVo.getTakeTraderArea()==null?"":saleorderVo.getTakeTraderArea()) + " " + (saleorderVo.getTakeTraderAddress()==null?"":saleorderVo.getTakeTraderAddress()));//地址
				
				String fukuanfangshi = "";
				switch (saleorderVo.getPaymentType()) {
				case 419:
					fukuanfangshi = "先款后货，预付100% ，预付金额"+saleorderVo.getPrepaidAmount()+"元";
					break;
				case 420:
					fukuanfangshi = "先货后款，预付80%，预付金额"+saleorderVo.getPrepaidAmount()+"元，账期支付"+saleorderVo.getAccountPeriodAmount()+"元";
					break;
				case 421:
					fukuanfangshi = "先货后款，预付50%，预付金额"+saleorderVo.getPrepaidAmount()+"元，账期支付"+saleorderVo.getAccountPeriodAmount()+"元";
					break;
				case 422:
					fukuanfangshi = "先货后款，预付30%，预付金额"+saleorderVo.getPrepaidAmount()+"元，账期支付"+saleorderVo.getAccountPeriodAmount()+"元";
					break;
				case 423:
					fukuanfangshi = "先货后款，预付0%，预付金额"+saleorderVo.getPrepaidAmount()+"元，账期支付"+saleorderVo.getAccountPeriodAmount()+"元";
					break;
				case 424:
					fukuanfangshi = "自定义，预付金额"+saleorderVo.getPrepaidAmount()+"元，账期支付"+saleorderVo.getAccountPeriodAmount()+"元，尾款"+saleorderVo.getRetainageAmount()+"元	，尾款期限"+saleorderVo.getRetainageAmountMonth()+"月";
					break;
				default:
					break;
				}
				string = string.replaceAll("\\{fukuanfangshi\\}", fukuanfangshi);//付款方式
				string = string.replaceAll("\\{hejijinedaxie\\}", chineseNumberTotalPrice);//大写金额
				string = string.replaceAll("\\{hejijine\\}", totalAmount);//合计金额
				// 发票类型
				List<SysOptionDefinition> invoiceTypes = getSysOptionDefinitionList(428);
				String fapiaoleixing = "";
				for(int x=0;x<invoiceTypes.size();x++){
					if(saleorderVo.getInvoiceType().equals(invoiceTypes.get(x).getSysOptionDefinitionId())){
						fapiaoleixing = invoiceTypes.get(x).getTitle();
					}
				}
				string = string.replaceAll("\\{fapiaoleixing\\}", fapiaoleixing);//发票类型
				
				String yunfei = "";//运费
				// 运费类型
				List<SysOptionDefinition> yfTypes = getSysOptionDefinitionList(469);
				for(int x=0;x<yfTypes.size();x++){
					if(saleorderVo.getFreightDescription().equals(yfTypes.get(x).getSysOptionDefinitionId())){
						yunfei = invoiceTypes.get(x).getTitle();
					}
				}
				string = string.replaceAll("\\{yunfei\\}", yunfei);//运费
				string = string.replaceAll("\\{antiao\\}", flag==-1?"无":"有");//安调、培训
				
				string = string.replaceAll("\\{diliquyu\\}", (saleorderVo.getSalesArea()==null?"":saleorderVo.getSalesArea()));
				string = string.replaceAll("\\{kehuleixing\\}", saleorderVo.getCustomerType().equals(426)?"科研医疗":((saleorderVo.getCustomerType().equals(427) || saleorderVo.getCustomerType().equals(0))?"临床医疗":""));
				
				//附加条款
				if(StringUtils.isBlank(saleorderVo.getAdditionalClause())){
					string = string.replaceAll("\\{fujiatiaokuan\\}", "");
				}else{
					string = string.replaceAll("\\{fujiatiaokuan\\}", "<div class=\"contract-print-title2\">十一、补充条款：</div><div class=\"contract-details\">"+saleorderVo.getAdditionalClause()+"</div>");
				}
				
				string = string.replaceAll("\\{shouquanren\\}", (detail.getRealName()==null?"":detail.getRealName()));//授权人
//				string = string.replaceAll("\\{zhidanriqi\\}", saleorderVo.getValidTime()==0?(DateUtil.DateToString(new Date(), "yyyy-MM-dd")):(DateUtil.convertString(saleorderVo.getValidTime(), "yyyy-MM-dd")));//授权日期
				
				// write string to file
				BufferedWriter writer = null;
				BufferedWriter bw = null;
				try {
//					writer = new FileWriter(newHtmlPath);
					writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream (newHtmlPath,true),"UTF-8"));
					bw = new BufferedWriter(writer);
					bw.write(string);
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
					return ("{\"code\":\"-1\",\"message\":\"读写时发生错误\"}");
				}finally{
					if(br != null){
						br.close();
					}
					if(read != null){
						read.close();
					}
					//关闭文件流
					if(bw != null){
						bw.close();
					}
					if(writer != null){
						writer.close();
					}
				}
				ItextFileToPdf itext = new ItextFileToPdf();
				String url = itext.htmlToPdf(newHtmlPath,path + date +".pdf");
				File file = new File(newHtmlPath);
				file.delete();
				if(url.equals("-1")){
					return ("{\"code\":\"-1\",\"message\":\"生成pdf时发生错误\"}");
				}
				return ("{\"code\":\"0\",\"message\":\""+url+"\"}");
			} catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
				//删除文件
				File file = new File(newHtmlPath);
				file.delete();
				return ("{\"code\":\"-1\",\"message\":\""+e.getMessage()+"\"}");
			}
			
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return ("{\"code\":\"-1\",\"message\":\""+e.getMessage()+"\"}");
//			return (new ResultInfo<>(-1,e.getMessage())).toSimpleString();
		}
	}
	
	@Override
	public String logisticsDetailSync(String logisticsNo) {
		SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据

		if(null == logisticsNo ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		Express express = new Express();
		express.setLogisticsNo(logisticsNo);
		LogisticsDetail logisticsDetail= expressService.getLogisticsDetailInfo(express);
		if(logisticsDetail != null){
		    resultInfo.setCode(0);
		    resultInfo.setMessage("操作成功");
		    resultInfo.setData(logisticsDetail.getContent());
		    return xmlExercise.convertToXml(resultInfo);
		}else{
		    return xmlExercise.convertToXml(resultInfo);
		}
	}
	//每3位中间添加逗号的格式化显示
	public static String getCommaFormat(BigDecimal value){
		return getFormat(",###.##",value);
	}
	//自定义数字格式方法
	public static String getFormat(String style,BigDecimal value){
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(style);// 将格式应用于格式化器
		return df.format(value.doubleValue());
	}

	@Override
	public String logisticsExpressByOrderNoSync(String saleorderNo) {
	    SoapResult resultInfo = new SoapResult<>();
		XmlExercise xmlExercise = new XmlExercise();
		
		Map dataMap = null;//主表数据

		if(null == saleorderNo ){//直接返回
			return xmlExercise.convertToXml(resultInfo);
		}
		
		List<Express> expressList = new ArrayList<>();
		try {
		    expressList = expressService.getExpressListBySaleorderNo(saleorderNo);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    return xmlExercise.convertToXml(resultInfo);
		}
		if(expressList != null && expressList.size()>0){
		    Map expressDataMap = new LinkedHashMap<>();
		    List<Map<String,Object>> dataList = new ArrayList<>();
		    expressDataMap.put("code", 0);
		    expressDataMap.put("message", "操作成功");
		    expressDataMap.put("saleorderNo", saleorderNo);
			for(Express e : expressList){
				
				List expressDetailDataMap = new ArrayList<>();
			    	String expressDetailDataXmlStr = null;
				if(e.getExpressDetail() != null && e.getExpressDetail().size()>0){
				    for(ExpressDetail ed:e.getExpressDetail()){
					Map expressDetailData = new HashMap<>();
					expressDetailData.put("goods_name", ed.getGoodName());//商品名称
					expressDetailData.put("goods_num", ed.getNum());//商品数量
					expressDetailDataMap.add(expressDetailData);
				    }
				   
				}
				Map expressData = new HashMap<>();
				Map data2 = new HashMap<>();
				String deliveryTime = null;
				if(e.getDeliveryTime() != null && e.getDeliveryTime()>0){
				     deliveryTime = DateUtil.convertString(e.getDeliveryTime(), "yyyy-MM-dd HH:mm:ss");
				}else{
				     deliveryTime = "";
				}
				expressData.put("delivery_time", deliveryTime);//发货时间
				expressData.put("logistics_no", e.getLogisticsNo());//物流单号
				expressData.put("logistics_name", e.getLogisticsName());//物流名称
				expressData.put("business_type", e.getBusiness_Type());//业务类型
				expressData.put("content", e.getContent());//物流信息
				expressData.put("invoice_no", e.getInvoiceNo());//对应发票单号
				expressData.put("invoice_amount", e.getInvoiceAmount());//对应发票金额
				expressData.put("express_detail",com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(expressDetailDataMap)));//快递详情
				data2.put("express",expressData);
				dataList.add(data2);
			}
			expressDataMap.put("list", dataList);
			String expressDataXmlStr = xmlExercise.mapToListXml(expressDataMap, "data");
			//String expressDataXmlStr = xmlExercise.listToXml(expressDataMap, "expressList", "express");
		    return expressDataXmlStr;
		}else{
		    return xmlExercise.convertToXml(resultInfo);
		}
	}
	/**
	 * <b>Description:</b><br>根据手机号查询该用户是否是老用户
	 *
	 *
	 * @param :int
	 * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/1 3:24 PM
	 */
	@Override
	public int getMobileCount(WebAccount webAccount) {
		return webAccountMapper.getMobileCount(webAccount);
	}
	/**
	 * <b>Description:</b><br>根据手机号修改是否加入贝登精选状态
	 *
	 *
	 * @param :int
	 * @return :java.util.List<com.vedeng.trader.model.vo.WebAccountVo>
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/1 3:24 PM
	 */
	@Override
	public int updateisVedengJoin(WebAccount webAccount) {
		return webAccountMapper.updateisVedengJoin(webAccount);
	}
	/**
	 * <b>Description:</b><br> 加入注册信息
	 * @param webAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Addis
	 * <br><b>Date:</b> 2018年1月11日 下午4:14:00
	 */
	@Override
	public int insert(WebAccount webAccount) {
		return webAccountMapper.insert(webAccount);
	}
	/**
	 * <b>Description:</b><br>根据手机号码查询1、是否有效 2、是否是贝登会员 3、是否申请加入贝登精选
	 *
	 *
	 * @param :webAccount
	 * @return :webAccount
	 * @Note <b>Author:</b> Addis <br>
	 *       <b>Date:</b> 2019/7/2 3:24 PM
	 */
	@Override
	public WebAccount selectMobileResult(WebAccount webAccount) {
		return webAccountMapper.selectMobileResult(webAccount);
	}

}
