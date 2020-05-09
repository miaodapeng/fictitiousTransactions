package com.vedeng.soap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import com.common.constants.Contant;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.ordergoods.dao.OrdergoodsCategoryMapper;
import com.vedeng.ordergoods.dao.OrdergoodsGoodsAccountMapper;
import com.vedeng.ordergoods.dao.OrdergoodsGoodsMapper;
import com.vedeng.ordergoods.dao.OrdergoodsStoreAccountMapper;
import com.vedeng.ordergoods.dao.ROrdergoodsGoodsJCategoryMapper;
import com.vedeng.ordergoods.model.OrdergoodsCategory;
import com.vedeng.ordergoods.model.OrdergoodsStoreAccount;
import com.vedeng.ordergoods.model.ROrdergoodsGoodsJCategory;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsAccountVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsGoodsVo;
import com.vedeng.ordergoods.model.vo.OrdergoodsStoreAccountVo;
import com.vedeng.soap.model.SsoAccount;
import com.vedeng.soap.service.OrdergoodsSoapService;
import com.vedeng.system.model.DataSyncStatus;
import com.vedeng.system.service.DataSyncStatusService;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;

@Service("ordergoodsSoapService")
public class OrdergoodsSoapServiceImpl extends BaseServiceimpl implements OrdergoodsSoapService {
	public static Logger logger = LoggerFactory.getLogger(OrdergoodsSoapServiceImpl.class);

	@Value("${vedeng_api_url}")
	private String vedengApiUrl;

	@Value("${vedeng_namespace}")
	private String vedengNamespace;
	
	@Value("${passport_api_url}")
	private String passportApiUrl;
	
	@Value("${passport_namespace}")
	private String passportNamespace;

	@Autowired
	@Qualifier("ordergoodsCategoryMapper")
	private OrdergoodsCategoryMapper ordergoodsCategoryMapper;

	@Autowired
	@Qualifier("dataSyncStatusService")
	private DataSyncStatusService dataSyncStatusService;

	@Autowired
	@Qualifier("rOrdergoodsGoodsJCategoryMapper")
	private ROrdergoodsGoodsJCategoryMapper rOrdergoodsGoodsJCategoryMapper;

	@Autowired
	@Qualifier("ordergoodsGoodsMapper")
	private OrdergoodsGoodsMapper ordergoodsGoodsMapper;
	
	@Autowired
	@Qualifier("ordergoodsStoreAccountMapper")
	private OrdergoodsStoreAccountMapper ordergoodsStoreAccountMapper;
	
	@Autowired
	@Qualifier("ordergoodsGoodsAccountMapper")
	private OrdergoodsGoodsAccountMapper ordergoodsGoodsAccountMapper;

	@Override
	public ResultInfo ordergoodsCategorySync(Integer ordergoodsCategoryId) {
		ResultInfo resultInfo = new ResultInfo<>();
		OrdergoodsCategory ordergoodsCategory = ordergoodsCategoryMapper.selectByPrimaryKey(ordergoodsCategoryId);
		if (null != ordergoodsCategory) {
			Map data = new HashMap<>();// 数据
			data.put("ordergoods_category_id", ordergoodsCategory.getOrdergoodsCategoryId());
			data.put("ordergoods_store_id", ordergoodsCategory.getOrdergoodsStoreId());
			data.put("parent_id", ordergoodsCategory.getParentId());
			data.put("name", ordergoodsCategory.getCategoryName());
			data.put("status", ordergoodsCategory.getStatus());
			data.put("min_amount", ordergoodsCategory.getMinAmount());
			data.put("order_no", ordergoodsCategory.getSort());
			data.put("add_time", ordergoodsCategory.getAddTime() != null && ordergoodsCategory.getAddTime() > 0
					? String.format("%010d", ordergoodsCategory.getAddTime() / 1000) : 0);
			data.put("creator", ordergoodsCategory.getCreator());
			data.put("mod_time", ordergoodsCategory.getAddTime() != null && ordergoodsCategory.getModTime() > 0
					? String.format("%010d", ordergoodsCategory.getModTime() / 1000) : 0);
			data.put("updater", ordergoodsCategory.getUpdater());
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
				options.setAction("categorySync");

				// 指定sfexpressService方法的参数值
				Object[] opAddEntryArgs = new Object[] { dataXmlStr };
				// 指定sfexpressService方法返回值的数据类型的Class对象
				Class[] classes = new Class[] { String.class };
				// 指定要调用的sfexpressService方法及WSDL文件的命名空间
				QName opAddEntry = new QName(vedengApiUrl, "categorySync");
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
		}

		// 同步日志
		DataSyncStatus dataSyncStatus = new DataSyncStatus();
		Integer syncStatus = 0;
		if (resultInfo.getCode().equals(0)) {
			syncStatus = 1;
		}
		dataSyncStatus.setGoalType(631);// 网站
		dataSyncStatus.setStatus(syncStatus);
		dataSyncStatus.setSourceTable("T_ORDERGOODS_CATEGORY");
		dataSyncStatus.setRelatedId(ordergoodsCategoryId);
		dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
		dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
		return resultInfo;
	}

	@Override
	public ResultInfo ordergoodsCategoryGoodsSync(Integer ordergoodsCategoryId, String orderGoodsIds) {
		ResultInfo resultInfo = new ResultInfo<>();
		String[] idList = orderGoodsIds.split(",");
		for (String id : idList) {
			ResultInfo resultInfo2 = new ResultInfo<>();
			ROrdergoodsGoodsJCategory rOrdergoodsGoodsJCategory = new ROrdergoodsGoodsJCategory();
			rOrdergoodsGoodsJCategory.setOrdergoodsCategoryId(ordergoodsCategoryId);
			rOrdergoodsGoodsJCategory.setOrdergoodsGoodsId(Integer.parseInt(id));
			ROrdergoodsGoodsJCategory goodsJCategory = rOrdergoodsGoodsJCategoryMapper
					.getByROrdergoodsGoodsJCategory(rOrdergoodsGoodsJCategory);

			if (null != goodsJCategory) {

				Map data = new HashMap<>();// 数据
				data.put("ordergoods_item_category_id", goodsJCategory.getrOrdergoodsGoodsJCategoryId());
				data.put("item_id", goodsJCategory.getGoodsId());
				data.put("ordergoods_category_id", goodsJCategory.getOrdergoodsCategoryId());

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
					options.setAction("itemCategorySync");

					// 指定sfexpressService方法的参数值
					Object[] opAddEntryArgs = new Object[] { dataXmlStr };
					// 指定sfexpressService方法返回值的数据类型的Class对象
					Class[] classes = new Class[] { String.class };
					// 指定要调用的sfexpressService方法及WSDL文件的命名空间
					QName opAddEntry = new QName(vedengApiUrl, "itemCategorySync");
					// 调用sfexpressService方法并输出该方法的返回值
					Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

					// 接口返回xml字符串
					String xmlString = str[0].toString();
					Map result = xmlExercise.xmlToMap(xmlString);

					if (null != result && result.get("code").equals("0")) {
						resultInfo2.setCode(0);
						resultInfo2.setMessage("操作成功");
					}
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
			// 同步日志
			DataSyncStatus dataSyncStatus = new DataSyncStatus();
			Integer syncStatus = 0;
			if (resultInfo2.getCode().equals(0)) {
				syncStatus = 1;
			}
			dataSyncStatus.setGoalType(631);// 网站
			dataSyncStatus.setStatus(syncStatus);
			dataSyncStatus.setSourceTable("T_R_ORDERGOODS_GOODS_J_CATEGORY");
			dataSyncStatus.setRelatedId(goodsJCategory.getrOrdergoodsGoodsJCategoryId());
			dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
			dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
		}
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Override
	public ResultInfo delOrdergoodsCategoryGoodsSync(Integer goodsId,Integer ordergoodsGoodsId,Integer ordergoodsStoreId) {
		ResultInfo resultInfo = new ResultInfo<>();

		Map data = new HashMap<>();// 数据
		data.put("item_id", goodsId);
		data.put("ordergoods_store_id", ordergoodsStoreId);

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
			options.setAction("delItem");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { dataXmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(vedengApiUrl, "delItem");
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
		dataSyncStatus.setSourceTable("T_ORDERGOODS_GOODS");
		dataSyncStatus.setRelatedId(ordergoodsGoodsId);
		dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
		dataSyncStatusService.addDataSyncStatus(dataSyncStatus);

		return resultInfo;
	}

	@Override
	public ResultInfo ordergoodsGoodsBatchSync(List<OrdergoodsGoodsVo> goodsList) {
		ResultInfo resultInfo = new ResultInfo<>();

		Map data = new HashMap<>();// 数据

		List goodsDataMap = new ArrayList<>();// 产品数据
		goodsDataMap.add(new TreeMap<>());
		List<Integer> ids = new ArrayList<>();
		for (OrdergoodsGoodsVo goodsVo : goodsList) {
			OrdergoodsGoodsVo ordergoodsGoodsVo = new OrdergoodsGoodsVo();
			ordergoodsGoodsVo.setOrdergoodsStoreId(goodsVo.getOrdergoodsStoreId());
			ordergoodsGoodsVo.setGoodsId(goodsVo.getGoodsId());
			OrdergoodsGoodsVo ordergoodsGoods = ordergoodsGoodsMapper.getOrdergoodsGoods(ordergoodsGoodsVo);

			ids.add(ordergoodsGoods.getGoodsId());
			Map goodsData = new TreeMap<>();
			goodsData.put("c_0", ordergoodsGoods.getGoodsId());// 商品id
			goodsData.put("c_1", ordergoodsGoods.getSpec());// spec
			goodsData.put("c_2", ordergoodsGoods.getUsed());// used
			goodsData.put("c_3", ordergoodsGoods.getUnit());// unit
			goodsData.put("c_4", ordergoodsGoods.getCostPrice());// cost_price
			goodsData.put("c_5", ordergoodsGoods.getRetailPrice());// retail_price
			goodsData.put("c_6", ordergoodsGoods.getComments());// remark
			goodsData.put("c_7", ordergoodsGoods.getTestMethod());// test_method
			goodsData.put("c_8", ordergoodsGoods.getSupplyCompany());// supply_company
			goodsData.put("c_9", ordergoodsGoods.getAddTime() != null && ordergoodsGoods.getAddTime() > 0
					? String.format("%010d", ordergoodsGoods.getAddTime() / 1000) : 0);// add_time
			goodsData.put("c_91", ordergoodsGoods.getCreator());// ordergoods_store_id
			goodsData.put("c_92", ordergoodsGoods.getOrdergoodsStoreId());// creater
			goodsData.put("c_93", ordergoodsGoods.getSaleAreaId());// sale_area_id
			goodsData.put("c_94", ordergoodsGoods.getTransportRequirements());// transport_requirements

			goodsDataMap.add(goodsData);
		}
		try {
			// 信息同步
			XmlExercise xmlExercise = new XmlExercise();
			String goodsDataXmlStr = xmlExercise.listToXml(goodsDataMap, "data", "items");

			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(vedengNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("mnNewOrdergoodsPriceSync");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { goodsDataXmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(vedengApiUrl, "mnNewOrdergoodsPriceSync");
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
		for (Integer id : ids) {
			DataSyncStatus dataSyncStatus = new DataSyncStatus();
			Integer syncStatus = 0;
			if (resultInfo.getCode().equals(0)) {
				syncStatus = 1;
			}
			dataSyncStatus.setGoalType(631);// 网站
			dataSyncStatus.setStatus(syncStatus);
			dataSyncStatus.setSourceTable("T_ORDERGOODS_GOODS");
			dataSyncStatus.setRelatedId(id);
			dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
			dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
		}
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Override
	public ResultInfo ordergoodsStoreAccountSync(OrdergoodsStoreAccount ordergoodsStoreAccount,Integer type) {
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			TraderCustomerVo customerVo = null;
			TraderFinanceVo financeVo = null;
			//财务信息
			TraderCustomerVo traderCustomerVo = new TraderCustomerVo();
			traderCustomerVo.setTraderAddressId(ordergoodsStoreAccount.getTraderAddressId());
			traderCustomerVo.setTraderContactId(ordergoodsStoreAccount.getTraderContactId());
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
			String url=httpUrl + "tradercustomer/getordergoodsstoreaccountinfo.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerVo,clientId,clientKey, TypeRef);
			Map<String, Object> traderCustomerMap = (Map<String, Object>) result.getData();
			
			if(traderCustomerMap.containsKey("customer")){
				net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(traderCustomerMap.get("customer"));
				customerVo = (TraderCustomerVo) json.toBean(json, TraderCustomerVo.class);
			}
			
			if(traderCustomerMap.containsKey("finance")){
				net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(traderCustomerMap.get("finance"));
				financeVo = (TraderFinanceVo) json.toBean(json, TraderFinanceVo.class);
			}
			
			//调用passport接口创建账号
			Map passportData = new HashMap<>();// 数据
			passportData.put("mobile", customerVo.getPhone());
			String accountPassport = this.randomkeys(6);
//			if(type == 1){
//			}
			passportData.put("password", accountPassport);
			passportData.put("corporation", customerVo.getTraderName());
			
			String uuid = null;
			if(null != ordergoodsStoreAccount.getOrdergoodsStoreAccountId()){
				OrdergoodsStoreAccountVo storeAccountVo = ordergoodsStoreAccountMapper.selectByPrimaryKey(ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
				if(null != storeAccountVo){
					uuid = storeAccountVo.getUuid();
					passportData.put("uuid", uuid);
				}
			}
		
			// 信息同步
			XmlExercise xmlExercise = new XmlExercise();
			String dataXmlStr = xmlExercise.mapToXml(passportData, "data");
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetPASS = new EndpointReference(passportNamespace);
			options.setTo(targetPASS);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("insertAccount");
//			if(type == 1){
//			}else{
//				options.setAction("updateAccount");
//			}
			

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { dataXmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = null;
			opAddEntry = new QName(passportApiUrl, "insertAccount");
//			if(type == 1){
//			}else{
//				opAddEntry = new QName(passportApiUrl, "updateAccount");
//			}
				
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			Map passwordResult = xmlExercise.xmlToMap(xmlString);

			if (null != passwordResult && passwordResult.get("code").equals("1")) {
				net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(passwordResult.get("data"));
				List<SsoAccount> ssoAccountList = (List<SsoAccount>) json.toCollection(json, SsoAccount.class);
				SsoAccount ssoAccount = ssoAccountList.get(0);
				//调用web接口补充账号信息
				Map webData = new HashMap<>();// 数据
				Map accountMap = new HashMap<>(); //账号信息
				accountMap.put("mobile", customerVo.getPhone());
				accountMap.put("add_time", ssoAccount.getAdd_time());
				accountMap.put("mod_time", ssoAccount.getMod_time());
				accountMap.put("sso_account_id", ssoAccount.getSso_account_id());
				accountMap.put("uuid", ssoAccount.getUuid());
				accountMap.put("from", ssoAccount.getFrom());
				accountMap.put("is_new", ssoAccount.getIsNew());
//				if(type == 1){
//				}
				accountMap.put("password", accountPassport);
				if(uuid != null ){
					accountMap.put("olduuid", uuid);
				}
				
				Map extraMap = new HashMap<>();//扩展信息
				extraMap.put("corporation", customerVo.getTraderName());
				extraMap.put("add_time", ssoAccount.getAdd_time());
				extraMap.put("mod_time", ssoAccount.getMod_time());
				
				Map ordergoodsMap = new HashMap<>();
				ordergoodsMap.put("company_name", customerVo.getTraderName());
				ordergoodsMap.put("area_id", customerVo.getAreaId());
				ordergoodsMap.put("address", customerVo.getAddress());
				ordergoodsMap.put("invoice_title", customerVo.getTraderName());
				if(financeVo != null){
					if(financeVo.getTaxNum() != null){
						ordergoodsMap.put("invoice_no", financeVo.getTaxNum());
					}
					if(financeVo.getRegAddress() != null){
						ordergoodsMap.put("invoice_address", financeVo.getRegAddress());
					}
					if(financeVo.getRegTel() != null){
						ordergoodsMap.put("invoice_phone", financeVo.getRegTel());
					}
					if(financeVo.getBank() != null){
						ordergoodsMap.put("invoice_bank", financeVo.getBank());
					}
					if(financeVo.getBankAccount() != null){
						ordergoodsMap.put("invoice_number", financeVo.getBankAccount());
					}
				}
				ordergoodsMap.put("verify_status", 1);
				ordergoodsMap.put("verify_time", ssoAccount.getAdd_time());
				ordergoodsMap.put("add_time", ssoAccount.getAdd_time());
				ordergoodsMap.put("mod_time", ssoAccount.getMod_time());
				
				//数据组装
				webData.put("account", accountMap);
				webData.put("extra", extraMap);
				webData.put("ordergoods", ordergoodsMap);
				webData.put("stores", ordergoodsStoreAccount.getOrdergoodsStoreId());
				
				
				dataXmlStr = xmlExercise.mapToXml(webData, "data");
				// 调用接口
				ser = new RPCServiceClient();
				options = ser.getOptions();

				// 指定调用WebService的URL
				EndpointReference targetWEB = new EndpointReference(vedengNamespace);
				options.setTo(targetWEB);
				// options.setAction("命名空间/WS 方法名");
				options.setAction("accountSync");

				// 指定sfexpressService方法的参数值
				opAddEntryArgs = new Object[] { dataXmlStr, ssoAccount.getUuid()};
				// 指定sfexpressService方法返回值的数据类型的Class对象
				classes = new Class[] { String.class };
				// 指定要调用的sfexpressService方法及WSDL文件的命名空间
				opAddEntry = new QName(vedengApiUrl, "accountSync");
				// 调用sfexpressService方法并输出该方法的返回值
				str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

				// 接口返回xml字符串
				xmlString = str[0].toString();
				Map webResult = xmlExercise.xmlToMap(xmlString);

				if (null != webResult && webResult.get("code").equals("0")) {
					Integer webAccountId = Integer.parseInt(webResult.get("key_id").toString());
					resultInfo.setCode(0);
					
					ordergoodsStoreAccount.setWebAccountId(webAccountId);
					ordergoodsStoreAccount.setUuid(ssoAccount.getUuid());
					resultInfo.setData(ordergoodsStoreAccount);
					
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		
		
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br> 随机数
	 * @param length
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:10:36
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
	public ResultInfo ordergoodsStoreAccountRestPasswordSync(OrdergoodsStoreAccount ordergoodsStoreAccount) {
		ResultInfo resultInfo = new ResultInfo<>();
		OrdergoodsStoreAccountVo storeAccountVo = ordergoodsStoreAccountMapper.selectByPrimaryKey(ordergoodsStoreAccount.getOrdergoodsStoreAccountId());
		if(null != storeAccountVo){
			Map data = new HashMap<>();// 数据
			String accountPassport = this.randomkeys(6);
			data.put("password", accountPassport);
			data.put("uuid", storeAccountVo.getUuid());
			
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
					//同步web
					
					Map webData = new HashMap<>();// 数据
					
					Map accountMap = new HashMap<>(); //账号信息
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
					opAddEntryArgs = new Object[] { dataXmlStr ,storeAccountVo.getUuid()};
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
						//同步web
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

	@Override
	public ResultInfo ordergoodsGoodsAccountBatchSync(List<OrdergoodsGoodsAccountVo> goodsList) {
		ResultInfo resultInfo = new ResultInfo<>();

		Map data = new HashMap<>();// 数据

		List goodsDataMap = new ArrayList<>();// 产品数据
		goodsDataMap.add(new TreeMap<>());
		List<Integer> ids = new ArrayList<>();
		List<Integer> goodsIds = new ArrayList<>();
		//拼接商品ID
		for(OrdergoodsGoodsAccountVo oga:goodsList){
		    goodsIds.add(oga.getGoodsId());
		}
		//根据产品id批量查
		OrdergoodsGoodsAccountVo ordergoodsGoodsAccountVo = new OrdergoodsGoodsAccountVo();
		ordergoodsGoodsAccountVo.setOrdergoodsStoreId(goodsList.get(0).getOrdergoodsStoreId());
		ordergoodsGoodsAccountVo.setGoodsIds(goodsIds);
		ordergoodsGoodsAccountVo.setWebAccountId(goodsList.get(0).getWebAccountId());
		List<OrdergoodsGoodsAccountVo> ordergoodsAccountGoodsList = ordergoodsGoodsAccountMapper.getOrdergoodsAccountGoodsByGoodsList(ordergoodsGoodsAccountVo);
		if(ordergoodsAccountGoodsList !=null && ordergoodsAccountGoodsList.size()>0){
        		for (OrdergoodsGoodsAccountVo ordergoodsAccountGoods : ordergoodsAccountGoodsList) {
        		    
        			ids.add(ordergoodsAccountGoods.getOrdergoodsGoodsAccountId());
        			Map goodsData = new TreeMap<>();
        			goodsData.put("c_0", ordergoodsAccountGoods.getOrdergoodsStoreId());//店铺ID
        			goodsData.put("c_1", ordergoodsAccountGoods.getGoodsId());// 商品id
        			goodsData.put("c_2", ordergoodsAccountGoods.getWebAccountId());// account_id
        			goodsData.put("c_3", ordergoodsAccountGoods.getPrice());// 价格
        			goodsData.put("c_4",  ordergoodsAccountGoods.getAddTime() != null && ordergoodsAccountGoods.getAddTime() > 0
        					? String.format("%010d", ordergoodsAccountGoods.getAddTime() / 1000) : 0);// add_time
        			goodsData.put("c_5", ordergoodsAccountGoods.getCreator());// creater
        			goodsData.put("c_6", ordergoodsAccountGoods.getMinQuantity1());// 起订量1
        			goodsData.put("c_7", ordergoodsAccountGoods.getPrice1());// 批量价1
        			goodsData.put("c_8", ordergoodsAccountGoods.getMinQuantity2());// 起订量2
        			goodsData.put("c_9", ordergoodsAccountGoods.getPrice2());// 批量价2
        			goodsData.put("c_90", ordergoodsAccountGoods.getMinQuantity3());// 起订量3
        			goodsData.put("c_91", ordergoodsAccountGoods.getPrice3());// 批量价3
        			goodsData.put("c_92", ordergoodsAccountGoods.getMinQuantity4());// 起订量4
        			goodsData.put("c_93", ordergoodsAccountGoods.getPrice4());// 批量价4
        			goodsDataMap.add(goodsData);
        		}
		}else{
		    
		    return resultInfo;
		}
		try {
			// 信息同步
			XmlExercise xmlExercise = new XmlExercise();
			String goodsDataXmlStr = xmlExercise.listToXml(goodsDataMap, "data", "items");

			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(vedengNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("ordergoodsAccountPriceSync");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { goodsDataXmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(vedengApiUrl, "ordergoodsAccountPriceSync");
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
		for (Integer id : ids) {
			DataSyncStatus dataSyncStatus = new DataSyncStatus();
			Integer syncStatus = 0;
			if (resultInfo.getCode().equals(0)) {
				syncStatus = 1;
			}
			dataSyncStatus.setGoalType(631);// 网站
			dataSyncStatus.setStatus(syncStatus);
			dataSyncStatus.setSourceTable("T_ORDERGOODS_GOODS_ACCOUNT");
			dataSyncStatus.setRelatedId(id);
			dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
			dataSyncStatusService.addDataSyncStatus(dataSyncStatus);
		}
		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Override
	public ResultInfo delOrdergoodsAccountGoodsSync(Integer goodsId, Integer ordergoodsStoreId, Integer webAccountId,Integer ordergoodsGoodsAccountId) {
		ResultInfo resultInfo = new ResultInfo<>();

		Map data = new HashMap<>();// 数据
		data.put("ordergoods_store_id", ordergoodsStoreId);
		data.put("item_id", goodsId);
		data.put("account_id", webAccountId);

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
			options.setAction("delAccoountItem");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { dataXmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(vedengApiUrl, "delAccoountItem");
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
		dataSyncStatus.setSourceTable("T_ORDERGOODS_GOODS_ACCOUNT");
		dataSyncStatus.setRelatedId(ordergoodsGoodsAccountId);
		dataSyncStatus.setAddTime(DateUtil.sysTimeMillis());
		dataSyncStatusService.addDataSyncStatus(dataSyncStatus);

		return resultInfo;
	}

}
