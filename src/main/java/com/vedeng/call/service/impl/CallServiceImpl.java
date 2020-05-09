package com.vedeng.call.service.impl;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import com.alibaba.fastjson.JSONArray;
import com.common.constants.Contant;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vedeng.phoneticWriting.dao.CommentMapper;
import com.vedeng.phoneticWriting.dao.ModificationRecordMapper;
import com.vedeng.phoneticWriting.dao.PhoneticWritingMapper;
import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.call.dao.CallFailedMapper;
import com.vedeng.call.model.CallFailed;
import com.vedeng.call.model.CallOut;
import com.vedeng.call.service.CallService;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.order.model.Buyorder;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.dao.QCellCoreMapper;
import com.vedeng.system.model.QCellCore;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONObject;

@Service("callService")
public class CallServiceImpl extends BaseServiceimpl implements CallService {
	/**
	 * @Fields callServerIp : TODO 呼叫中心服务器地址
	 */
	public static Logger logger = LoggerFactory.getLogger(CallServiceImpl.class);

	@Value("${call_server_ip}")
	private String callServerIp;

	@Value("${call_url}")
	private String callUrl;

	@Value("${call_namespace}")
	private String callNamespace;

	@Autowired
	@Qualifier("qCellCoreMapper")
	private QCellCoreMapper qCellCoreMapper;

	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;
	
	@Autowired
	@Qualifier("callFailedMapper")
	private CallFailedMapper callFailedMapper;

	@Autowired
	@Qualifier("modificationRecordMapper")
	private ModificationRecordMapper modificationRecordMapper;

	@Autowired
	@Qualifier("phoneticWritingMapper")
	private PhoneticWritingMapper phoneticWritingMapper;

	@Autowired
	@Qualifier("commentMapper")
	private CommentMapper commentMapper;

	@Override
	public HashMap callInit() {
		HashMap callParams = new HashMap<>();

		// 服务器地址
		callParams.put("callServerIp", callServerIp);

		return callParams;
	}

	@Override
	public QCellCore getQCellCoreByPhone(String phone) {
		return qCellCoreMapper.getQCellCoreByPhone(phone);
	}

	@Override
	public TraderSupplierVo getSupplierInfoByPhone(CallOut callOut) {
		TraderSupplierVo traderSupplier = new TraderSupplierVo();
		traderSupplier.setPhone(callOut.getPhone());

		// 历史沟通记录查询
		CommunicateRecord record = communicateRecordMapper.getSupplierCommunicateByPhone(callOut.getPhone());

		if (null != callOut.getTraderId() && callOut.getTraderId() > 0) {
			traderSupplier.setTraderId(callOut.getTraderId());
		} else {
			if (null != record) {
				traderSupplier.setLastCommunicateType(record.getCommunicateType());
				traderSupplier.setLastRelatedId(record.getRelatedId());
			}
		}

		// 指定联系人
		if (null != callOut.getTraderContactId() && callOut.getTraderContactId() > 0) {
			traderSupplier.setTraderContactId(callOut.getTraderContactId());
		}

		// 接口调用
		String url = httpUrl + "tradersupplier/getsupplierinfobyphone.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplier, clientId, clientKey,
					TypeRef2);
			JSONObject json = JSONObject.fromObject(result2.getData());
			// 对象包含另一个对象集合时用以下方法转化
			TraderSupplierVo res = JsonUtils.readValue(json.toString(), TraderSupplierVo.class);

			if(null == res){
				return null;
			}
			// 等级
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getGrade())) {
//				JSONObject jsonObject = JSONObject
//						.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getGrade()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				res.setGradeStr(sod.getTitle());
//			}
			res.setGradeStr(getSysOptionDefinitionById(res.getGrade()).getTitle());
			if (null != record) {
				res.setLastCommuncateTime(record.getLastCommunicateTime());
			}

			// 沟通记录
			CommunicateRecord comm = new CommunicateRecord();
			comm.setTraderType(ErpConst.TWO);
			comm.setPhone(callOut.getPhone());
			List<CommunicateRecord> communicateRecordList = communicateRecordMapper.getCommunicateTraderByPhone(comm);

			// 订单销售人员查询
			List<Integer> userIds = new ArrayList<>();
			if (null != res.getBuyorderList()) {
				for (BuyorderVo b : res.getBuyorderList()) {
					userIds.add(b.getUserId());
				}
			}
			if (null != communicateRecordList) {
				for (CommunicateRecord c : communicateRecordList) {
					userIds.add(c.getCreator());
				}
			}

			if (userIds.size() > 0) {
				List<User> userList = userMapper.getUserByUserIds(userIds);
				// 信息补充
				if (null != res.getBuyorderList()) {
					for (BuyorderVo b : res.getBuyorderList()) {
						for (User u : userList) {
							if (b.getUserId().equals(u.getUserId())) {
								b.setBuyPerson(u.getUsername());
							}
						}
					}
				}

				if (null != communicateRecordList) {
					for (CommunicateRecord c : communicateRecordList) {
						for (User u : userList) {
							if (c.getCreator().equals(u.getUserId())) {
								c.setCreatorName(u.getUsername());
							}
						}
					}
				}
			}

			res.setCommunicateRecordList(communicateRecordList);
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public TraderCustomerVo getCustomerInfoByPhone(CallOut callOut) {
		TraderCustomerVo traderCustomer = new TraderCustomerVo();
		traderCustomer.setPhone(callOut.getPhone());

		// 历史沟通记录查询
		CommunicateRecord record = communicateRecordMapper.getCustomerCommunicateByPhone(callOut.getPhone());

		if (null != callOut.getTraderId() && callOut.getTraderId() > 0) {
			traderCustomer.setTraderId(callOut.getTraderId());
		} else {
			if (null != record) {
				traderCustomer.setLastCommunicateType(record.getCommunicateType());
				traderCustomer.setLastRelatedId(record.getRelatedId());
			}
		}

		// 指定联系人
		if (null != callOut.getTraderContactId() && callOut.getTraderContactId() > 0) {
			traderCustomer.setTraderContactId(callOut.getTraderContactId());
		}

		// 接口调用
		String url = httpUrl + "tradercustomer/getcustomerinfobyphone.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
					TypeRef2);
			JSONObject json = JSONObject.fromObject(result2.getData());
			// 对象包含另一个对象集合时用以下方法转化
			TraderCustomerVo res = JsonUtils.readValue(json.toString(), TraderCustomerVo.class);
			if(null == res){
				return null;
			}
			// 客户类型
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getCustomerType())) {
//				JSONObject jsonObject = JSONObject
//						.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getCustomerType()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				res.setCustomerTypeStr(sod.getTitle());
//			}
			res.setCustomerTypeStr(getSysOptionDefinitionById(res.getCustomerType()).getTitle());
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getCustomerNature())) {
//				JSONObject jsonObject = JSONObject.fromObject(
//						JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getCustomerNature()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				res.setCustomerPropertyStr(sod.getTitle());
//			}
			res.setCustomerPropertyStr(getSysOptionDefinitionById(res.getCustomerNature()).getTitle());
			// 客户等级
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getCustomerLevel())) {
//				JSONObject jsonObject = JSONObject.fromObject(
//						JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + res.getCustomerLevel()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				res.setCustomerLevelStr(sod.getTitle());
//			}
			res.setCustomerLevelStr(getSysOptionDefinitionById(res.getCustomerLevel()).getTitle());
			if (null != record) {
				res.setLastCommuncateTime(record.getLastCommunicateTime());
			}

			// 沟通记录
			CommunicateRecord comm = new CommunicateRecord();
			comm.setTraderType(ErpConst.ONE);
			comm.setPhone(callOut.getPhone());
			List<CommunicateRecord> communicateRecordList = communicateRecordMapper.getCommunicateTraderByPhone(comm);

			// 商机、报价、订单销售人员查询
			List<Integer> userIds = new ArrayList<>();
			if (null != res.getBussinessChanceList()) {
				for (BussinessChanceVo b : res.getBussinessChanceList()) {
					userIds.add(b.getUserId());
				}
			}
			if (null != res.getQuoteorderList()) {
				for (Quoteorder q : res.getQuoteorderList()) {
					userIds.add(q.getUserId());
				}
			}
			if (null != res.getSaleorderList()) {
				for (Saleorder s : res.getSaleorderList()) {
					userIds.add(s.getUserId());
				}
			}
			if (null != communicateRecordList) {
				for (CommunicateRecord c : communicateRecordList) {
					userIds.add(c.getCreator());
				}
			}

			if (userIds.size() > 0) {
				List<User> userList = userMapper.getUserByUserIds(userIds);
				// 信息补充
				if (null != res.getBussinessChanceList()) {
					for (BussinessChanceVo b : res.getBussinessChanceList()) {
						for (User u : userList) {
							if (b.getUserId().equals(u.getUserId())) {
								b.setSalerName(u.getUsername());
							}
						}
					}
				}
				if (null != res.getQuoteorderList()) {
					for (Quoteorder q : res.getQuoteorderList()) {
						for (User u : userList) {
							if (q.getUserId().equals(u.getUserId())) {
								q.setSalesName(u.getUsername());
							}
						}

						// 沟通次数
						List<Integer> keyList = new ArrayList<>();
						keyList.add(q.getQuoteorderId());
						List<CommunicateRecord> communicateRecord = communicateRecordMapper
								.getCommunicateRecord(keyList, SysOptionConstant.ID_245.toString());
						q.setCommunicateNum(communicateRecord.size());

					}
				}
				if (null != res.getSaleorderList()) {
					for (Saleorder s : res.getSaleorderList()) {
						for (User u : userList) {
							if (s.getUserId().equals(u.getUserId())) {
								s.setSalesName(u.getUsername());
							}
						}
					}
				}
				if (null != communicateRecordList) {
					for (CommunicateRecord c : communicateRecordList) {
						for (User u : userList) {
							if (c.getCreator().equals(u.getUserId())) {
								c.setCreatorName(u.getUsername());
							}
						}
					}
				}
			}
			res.setCommunicateRecordList(communicateRecordList);
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<?> getOrderList(CommunicateRecord communicateRecord) {
		String url = httpUrl + "call/getordersbycommunicatetype.htm";

		List<?> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecord, clientId, clientKey,
					TypeRef2);
			switch (communicateRecord.getCommunicateType()) {
			case 244:// 商机
				list = (List<BussinessChanceVo>) result.getData();
				break;
			case 245:// 报价
				list = (List<Quoteorder>) result.getData();
				break;
			case 246:// 销售订单
				list = (List<Saleorder>) result.getData();
				break;
			case 247:// 采购订单
				list = (List<Buyorder>) result.getData();
				break;
			case 248:// 售后订单

				break;
			}
			return list;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Boolean saveCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, HttpSession session)
			throws Exception {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		Boolean res = false;
		CommunicateRecord record = null;
		communicateRecord.setCompanyId(user.getCompanyId());
		
		// 查询沟通记录是否存在
		if (null != communicateRecord.getCoid() && communicateRecord.getCoid() != "") {
			record = communicateRecordMapper.getCommunicateByCoidAndUserId(communicateRecord.getCoid(),
					user.getUserId());
		}

		if (null != record) {// 编辑
			communicateRecord.setCommunicateRecordId(record.getCommunicateRecordId());
			res = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
		} else {// 新增
			res = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
		}
		return res;
	}

	@Override
	public Trader getTraderByTraderId(Integer traderId) {
		String url = httpUrl + "trader/gettraderbytraderid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Trader>> TypeRef2 = new TypeReference<ResultInfo<Trader>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderId, clientId, clientKey, TypeRef2);
			Trader trader = (Trader) result.getData();
			return trader;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public BussinessChanceVo getBussincessChance(CallOut callOut) {
		String url = httpUrl + "call/getbussincesschance.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<BussinessChanceVo>> TypeRef2 = new TypeReference<ResultInfo<BussinessChanceVo>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, callOut, clientId, clientKey, TypeRef2);
			BussinessChanceVo bussinessChance = (BussinessChanceVo) result.getData();

			if (null == bussinessChance) {
				return null;
			}
			if (null != bussinessChance.getCreator() && bussinessChance.getCreator() > 0) {
				bussinessChance
						.setCreatorName(userMapper.selectByPrimaryKey(bussinessChance.getCreator()).getUsername());
			}
			// 商机来源
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + bussinessChance.getSource())) {
//				JSONObject jsonObject = JSONObject.fromObject(
//						JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + bussinessChance.getSource()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				bussinessChance.setSourceName(sod.getTitle());
//			}
			bussinessChance.setSourceName(getSysOptionDefinitionById(bussinessChance.getSource()).getTitle());
			// 询价方式
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + bussinessChance.getCommunication())) {
//				JSONObject jsonObject = JSONObject.fromObject(JedisUtils
//						.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + bussinessChance.getCommunication()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				bussinessChance.setCommunicationName(sod.getTitle());
//			}
			bussinessChance.setCommunicationName(getSysOptionDefinitionById(bussinessChance.getCommunication()).getTitle());
			// 商品分类
//			if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + bussinessChance.getGoodsCategory())) {
//				JSONObject jsonObject = JSONObject.fromObject(JedisUtils
//						.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + bussinessChance.getGoodsCategory()));
//				SysOptionDefinition sod = (SysOptionDefinition) JSONObject.toBean(jsonObject,
//						SysOptionDefinition.class);
//				bussinessChance.setGoodsCategoryName(sod.getTitle());
//			}
			bussinessChance.setGoodsCategoryName(getSysOptionDefinitionById(bussinessChance.getGoodsCategory()).getTitle());
			// 地区
			if (bussinessChance.getAreaId() != null && bussinessChance.getAreaId() != 0) {
				bussinessChance.setAreas(getAddressByAreaId(bussinessChance.getAreaId()));
			}

			return bussinessChance;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public Trader getTraderByPhone(CallOut callOut) {
		String url = httpUrl + "call/gettraderbyphone.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Trader>> TypeRef2 = new TypeReference<ResultInfo<Trader>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, callOut, clientId, clientKey, TypeRef2);
			Trader trader = (Trader) result.getData();
			return trader;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String getIsInit(String number) {
		String login = null; // 返回登录状态
		try {
			// 需要传递的参数封装成xml格式
			Map map = new HashMap<>();
			map.put("employeeID", number);

			XmlExercise xmlExercise = new XmlExercise();
			String xmlStr = xmlExercise.mapToXml(map, "data");
			
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(callNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("getEmployeeLoginInfo");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { xmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(callUrl, "getEmployeeLoginInfo");
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			
			Map result = xmlExercise.xmlToMap(xmlString);
			
			if(null != result && result.get("code").equals("0")){
				Map infoData = (Map) result.get("data");
				//Map infoData = (Map) resultMap.get(0);
				login = infoData.get("type").toString();
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return login;
	}

	@Override
	public Map<String,Object> getStatistics(String starttime, String endtime) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			// 需要传递的参数封装成xml格式
			Map map = new HashMap<>();
			map.put("starttime", starttime);
			map.put("endtime", endtime);
			
			XmlExercise xmlExercise = new XmlExercise();
			String xmlStr = xmlExercise.mapToXml(map, "data");
			
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(callNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("getStatistics");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { xmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(callUrl, "getStatistics");
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			
			Map result = xmlExercise.xmlToMap(xmlString);
			
			if(null != result && result.get("code").equals("0")){
				JSONObject jsonObject = JSONObject.fromObject(result.get("data"));
				resultMap = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return resultMap;
	}

	@Override
	public List getUserDetail(String starttime, String endtime, List<String> numberList) {
		List resultMap = new ArrayList<>();
		try {
			// 需要传递的参数封装成xml格式
			Map map = new HashMap<>();
			map.put("starttime", starttime);
			map.put("endtime", endtime);
			map.put("employeeID", numberList);
			
			XmlExercise xmlExercise = new XmlExercise();
			String xmlStr = xmlExercise.mapToXml(map, "data");
			
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(callNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("getUserDetail");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { xmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(callUrl, "getUserDetail");
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			
			Map result = xmlExercise.xmlToMapList(xmlString);
			
			if(null != result && result.get("code").equals("0")){
				resultMap = (List) result.get("data");
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return resultMap;
	}

	@Override
	public List<CallFailed> queryCallFailedlistPage(CallFailed callFailed, Page page) {
		
		if(null != callFailed.getStarttime()){
			callFailed.setStarttimeLong(DateUtil.convertLong(callFailed.getStarttime(), "yyyy-MM-dd"));
		}
		if(null != callFailed.getEndtime()){
			callFailed.setEndtimeLong(DateUtil.convertLong(callFailed.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("callFailed", callFailed);
		map.put("page", page);
		
		List<CallFailed> list = callFailedMapper.queryCallFailedlistPage(map);
		
		//信息补充
		if(list.size() > 0){
			List<String> coids = new ArrayList<>();
			
			for(CallFailed c : list){
				if(c.getCoid() != null && !c.getCoid().equals("")){
					coids.add(c.getCoid());
				}
				
				String regEx = "1[34578]{1}\\d{9}$";
				// 编译正则表达式
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(c.getCallerNumber());
				if (matcher.matches()) {
					// 电话
					String phoneStr = c.getCallerNumber().substring(0, 7);
					QCellCore qCellCoreByPhone = this.getQCellCoreByPhone(phoneStr);
					if(null != qCellCoreByPhone){
						c.setPhoneArea(qCellCoreByPhone.getProvince()+qCellCoreByPhone.getCity());
					}
				} else if (c.getCallerNumber().length() == 8) {
					c.setPhoneArea("江苏南京");
				}else{
					String code = c.getCallerNumber().substring(0, 4);
					QCellCore qCellCoreByCode = this.getQCellCoreByCode(code);
					if(null != qCellCoreByCode){
						c.setPhoneArea(qCellCoreByCode.getProvince()+qCellCoreByCode.getCity());
					}else{
						String code2 = c.getCallerNumber().substring(0, 3);
						QCellCore qCellCoreByCode2 = this.getQCellCoreByCode(code2);
						if(null != qCellCoreByCode2){
							c.setPhoneArea(qCellCoreByCode2.getProvince()+qCellCoreByCode2.getCity());
						}
					}
				}
			}
			
			//有录音信息
			if(coids.size() > 0){
				List resultList = new ArrayList<>();
				try {
					// 需要传递的参数封装成xml格式
					Map coidMap = new HashMap<>();
					map.put("coid", coids);
					
					XmlExercise xmlExercise = new XmlExercise();
					String xmlStr = xmlExercise.mapToXml(map, "data");
					
					// 调用接口
					RPCServiceClient ser = new RPCServiceClient();
					Options options = ser.getOptions();

					// 指定调用WebService的URL
					EndpointReference targetEPR = new EndpointReference(callNamespace);
					options.setTo(targetEPR);
					// options.setAction("命名空间/WS 方法名");
					options.setAction("getRecordByCoid");

					// 指定sfexpressService方法的参数值
					Object[] opAddEntryArgs = new Object[] { xmlStr };
					// 指定sfexpressService方法返回值的数据类型的Class对象
					Class[] classes = new Class[] { String.class };
					// 指定要调用的sfexpressService方法及WSDL文件的命名空间
					QName opAddEntry = new QName(callUrl, "getRecordByCoid");
					// 调用sfexpressService方法并输出该方法的返回值
					Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

					// 接口返回xml字符串
					String xmlString = str[0].toString();
					
					Map result = xmlExercise.xmlToMapList(xmlString);
					
					if(null != result && result.get("code").equals("0")){
						resultList = (List) result.get("data");
						
						if(resultList.size() > 0){
							for(CallFailed c : list){
								if(null != c.getCoid() && !c.getCoid().equals("")){
									for(int i=0;i<resultList.size();i++){
										Map dataMap = new HashMap<>();
										dataMap = (Map) resultList.get(i);
										
										if(dataMap.get("COID").toString().equals(c.getCoid())){
											c.setUrl(dataMap.get("URL").toString());
											c.setFilelen(Integer.parseInt(dataMap.get("FILELEN").toString()));
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
				}
			}
		}
		return list;
	}

	@Override
	public List<CallFailed> getCallFailedDialBackUser() {
		return callFailedMapper.getCallFailedDialBackUser();
	}

	@Override
	public QCellCore getQCellCoreByCode(String code) {
		return qCellCoreMapper.getQCellCoreByCode(code);
	}

	@Override
	public Boolean editCallFailedCoid(CallFailed callFailed, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		callFailed.setIsDialBack(ErpConst.ONE);
		callFailed.setDialBackUserId(user.getUserId());
		Integer op = callFailedMapper.updateByPrimaryKey(callFailed);
		if(op > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<CommunicateRecord> queryRecordlistPage(CommunicateRecord communicateRecord, Page page,HttpSession session) {
		//客户名称
		if(null != communicateRecord.getTraderName() && !communicateRecord.getTraderName().equals("")){
			String url = httpUrl + "trader/gettradeidsbytradername.htm";
			Trader trader = new Trader();
			trader.setTraderName(communicateRecord.getTraderName());
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			trader.setCompanyId(user.getCompanyId());
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Integer>>> TypeRef2 = new TypeReference<ResultInfo<List<Integer>>>() {
			};
			try {
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader, clientId, clientKey, TypeRef2);
				List<Integer> traderIds = (List<Integer>) result.getData();
				if(null != traderIds){
					communicateRecord.setTraderIds(traderIds);
				}
			} catch (IOException e) {
				logger.error("queryRecordlistPage",e);
				return null;
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("communicateRecord", communicateRecord);
		map.put("page", page);
		
		List<CommunicateRecord> list = communicateRecordMapper.queryCallRecordListPage(map);
		if(list.size() > 0){
			List<String> coids = new ArrayList<>();
			List<Integer> traderIds = new ArrayList<>();
			for(CommunicateRecord c : list){
				if(c.getCoid() != null && !c.getCoid().equals("") && !c.getSyncStatus().equals("0")){
					coids.add(c.getCoid());
				}
				if(c.getTraderId() > 0){
					traderIds.add(c.getTraderId());
				}
				
				//号码归属地
				String regEx = "1[34578]{1}\\d{9}$";
				// 编译正则表达式
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(c.getPhone());
				if (matcher.matches()) {
					// 电话
					String phoneStr = c.getPhone().substring(0, 7);
					QCellCore qCellCoreByPhone = this.getQCellCoreByPhone(phoneStr);
					if(null != qCellCoreByPhone){
						c.setPhoneArea(qCellCoreByPhone.getProvince()+qCellCoreByPhone.getCity());
					}
				} else if (c.getPhone().length() == 8) {
					c.setPhoneArea("江苏南京");
				}else{
					QCellCore qCellCoreByCode = null;
					if(null != c.getPhone() && c.getPhone().length() > 4){
						String code = c.getPhone().substring(0, 4);
						qCellCoreByCode = this.getQCellCoreByCode(code);
					}
					if(null != qCellCoreByCode){
						c.setPhoneArea(qCellCoreByCode.getProvince()+qCellCoreByCode.getCity());
					}else{
						if(null != c.getPhone() && c.getPhone().length() > 3){
							String code2 = c.getPhone().substring(0, 3);
							QCellCore qCellCoreByCode2 = this.getQCellCoreByCode(code2);
							if(null != qCellCoreByCode2){
								c.setPhoneArea(qCellCoreByCode2.getProvince()+qCellCoreByCode2.getCity());
							}
						}
					}
				}
				
				//归属人
				if(c.getTraderId() > 0 && c.getTraderType() > 0){
					User sale = userMapper.getUserByTraderId(c.getTraderId(), c.getTraderType());
					if (null != sale && null != sale.getUsername()) {
						c.setOwnerUsername(sale.getUsername());
					}
				}
				//查询录音点评
				List<Comment> commentList =  commentMapper.getComList(c);
                c.setCommentList(commentList);
                //判断是否转译完成
				PhoneticWriting phoneticWriting = phoneticWritingMapper.getPhoneticWriting(c.getCommunicateRecordId());
				if(phoneticWriting != null){
					if(StringUtils.isNotBlank(phoneticWriting.getOriginalContent())){
                          c.setIsTranslation(1);
					}else {
						c.setIsTranslation(0);
					}
				}else{
					c.setIsTranslation(0);
				}
			}
			
			//客户名称
			if(traderIds.size() > 0){
				String url = httpUrl + "trader/gettraderbytraderids.htm";
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Trader>>> TypeRef2 = new TypeReference<ResultInfo<List<Trader>>>() {
				};
				try {
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderIds, clientId, clientKey, TypeRef2);
					List<Trader> traderList = (List<Trader>) result.getData();
					if(null != traderList){
						for(Trader t : traderList){
							for(CommunicateRecord c : list){
								if(t.getTraderId().equals(c.getTraderId())){
									c.setTraderName(t.getTraderName());
								}
							}
						}
					}
				} catch (IOException e) {
					logger.error("queryRecordlistPage",e);
				}
			}
			
			//有录音信息
			if(coids.size() > 0){
				List resultList = new ArrayList<>();
				try {
					// 需要传递的参数封装成xml格式
					Map coidMap = new HashMap<>();
					map.put("coid", coids);

					XmlExercise xmlExercise = new XmlExercise();
					String xmlStr = xmlExercise.mapToXml(map, "data");

					// 调用接口
					RPCServiceClient ser = new RPCServiceClient();
					Options options = ser.getOptions();

					// 指定调用WebService的URL
					EndpointReference targetEPR = new EndpointReference(callNamespace);
					options.setTo(targetEPR);
					// options.setAction("命名空间/WS 方法名");
					options.setAction("getRecordByCoid");

					// 指定sfexpressService方法的参数值
					Object[] opAddEntryArgs = new Object[] { xmlStr };
					// 指定sfexpressService方法返回值的数据类型的Class对象
					Class[] classes = new Class[] { String.class };
					// 指定要调用的sfexpressService方法及WSDL文件的命名空间
					QName opAddEntry = new QName(callUrl, "getRecordByCoid");
					// 调用sfexpressService方法并输出该方法的返回值
					Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

					//logger.info("沟通记录webservice接口返回：" + JSONArray.toJSONString(str));

					// 接口返回xml字符串
					String xmlString = str[0].toString();

					Map result = xmlExercise.xmlToMapList(xmlString);
					//logger.info("沟通记录webservice接口返回result：" + JSONObject.fromObject(result));
					if(null != result && result.get("code").equals("0")){
						resultList = (List) result.get("data");

						if(resultList.size() > 0){
							for(CommunicateRecord c : list){
								if(null != c.getCoid() && !c.getCoid().equals("")){
									Map<String,CommunicateRecord> coidsSet= Maps.newHashMap();//同一个通话包含两个录音，取长的那个。
									for(int i=0;i<resultList.size();i++){
										Map dataMap = (Map) resultList.get(i);
										if(dataMap.get("COID").toString().equals(c.getCoid())){
											if(coidsSet.containsKey(dataMap.get("COID").toString())){
												int current=Integer.parseInt(dataMap.get("FILELEN").toString())	;
												int last=coidsSet.get(dataMap.get("COID").toString()).getCoidLength();
												if(current>last){
													coidsSet.get(dataMap.get("COID").toString()).setCoidUri(dataMap.get("URL").toString());
													coidsSet.get(dataMap.get("COID").toString()).setCoidLength(current);
												}
											}else{
												int current=Integer.parseInt(dataMap.get("FILELEN").toString())	;
												CommunicateRecord m=new CommunicateRecord();
												m.setCoidUri(dataMap.get("URL").toString());
												m.setCoidLength(current);
												coidsSet.put(dataMap.get("COID").toString(),m);
											}
										}
									}
									if(coidsSet.containsKey(c.getCoid())){
										c.setCoidUri(coidsSet.get(c.getCoid()).getCoidUri());
										c.setCoidLength(coidsSet.get(c.getCoid()).getCoidLength());
									}

								}
							}
						}
					}
				} catch (Exception e) {
					logger.error("queryRecordlistPage",e);
				}
			}
		}
		return list;
	}

	@Override
	public List<User> getRecordUser() {
		return communicateRecordMapper.getRecordUser();
	}

	@Override
	public List<CommunicateRecord> selectRecordlistPage(CommunicateRecord communicateRecord, Page page,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("communicateRecord", communicateRecord);
		map.put("page", page);
		
		List<CommunicateRecord> list = communicateRecordMapper.selectCallRecordListPage(map);
		
		return list;
	}

	@Override
	public ResultInfo<?> getRecordCoidURIByCommunicateRecordId(Integer communicateRecordId) {
		String uri = "";
		uri = communicateRecordMapper.getRecordCoidURIByCommunicateRecordId(communicateRecordId);
		if (StringUtil.isNotBlank(uri)) {
			return new ResultInfo(0, "查询成功",uri);
		}else {
			return new ResultInfo(1, "该录音文件没有上传",uri);
		}
		
	}

	@Override
	public PhoneticWriting getPhoneticWriting(CommunicateRecord communicateRecord) {
		return phoneticWritingMapper.getPhoneticWriting(communicateRecord.getCommunicateRecordId());
	}

	@Override
	public List<ModificationRecord> getMrList(CommunicateRecord communicateRecord) {
		return modificationRecordMapper.getMrInfoList(communicateRecord);
	}

	@Override
	public List<Comment> getComList(CommunicateRecord communicateRecord) {
		List<Comment> commentList = commentMapper.getComList(communicateRecord);
		for(int i=0;i < commentList.size();i++){
			// 查询点评人
			commentList.get(i).setUserName(getUserNameByUserId(commentList.get(i).getCreator()));
		}
		return commentList;
	}

	@Override
	public CommunicateRecord getCommunicateRecordById(CommunicateRecord communicateRecord) {
		return communicateRecordMapper.getCommunicate(communicateRecord);
	}

}
