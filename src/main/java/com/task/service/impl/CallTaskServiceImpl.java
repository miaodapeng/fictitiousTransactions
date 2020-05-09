package com.task.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import com.common.constants.Contant;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.task.service.CallTaskService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.call.dao.CallFailedMapper;
import com.vedeng.call.model.CallFailed;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;

import net.sf.json.JSONObject;

@Service("callTaskService")
public class CallTaskServiceImpl extends BaseServiceimpl implements CallTaskService {
	public static Logger logger = LoggerFactory.getLogger(CallTaskServiceImpl.class);

	@Value("${call_url}")
	private String callUrl;

	@Value("${call_namespace}")
	private String callNamespace;
	
	@Autowired
	@Qualifier("callFailedMapper")
	private CallFailedMapper callFailedMapper;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;
	
	//@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public ResultInfo failedRecordSync() throws Exception{
		ResultInfo resultInfo = new ResultInfo<>();
		try {
			XmlExercise xmlExercise = new XmlExercise();
			
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(callNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("getFailedRecord");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(callUrl, "getFailedRecord");
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			
			Map result = xmlExercise.xmlToMapList(xmlString);
			
			if(null != result && result.get("code").equals("0")){
				List resultList = (List) result.get("data");
				
				if(resultList.size() > 0){
					for(int i=0;i<resultList.size();i++){
						Map dataMap = new HashMap<>();
						dataMap = (Map) resultList.get(i);
						
						List<CallFailed> callFailedByLogId = callFailedMapper.getCallFailedByLogId(Integer.parseInt(dataMap.get("LogID").toString()));
						if(callFailedByLogId.size() == 0 ){
							CallFailed callFailed = new CallFailed();
							callFailed.setLogId(Integer.parseInt(dataMap.get("LogID").toString()));
							callFailed.setCallerNumber(dataMap.get("CallerNumber").toString());
							
							
							// 接口调用（优先查客户，客户没有查供应商库）
							String url = httpUrl + "tradercustomer/getcustomerinfobyphone.htm";
							TraderCustomerVo traderCustomer = new TraderCustomerVo();
							traderCustomer.setPhone(dataMap.get("CallerNumber").toString());
							// 定义反序列化 数据格式
							final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
							};
							ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
									TypeRef2);
							JSONObject json = JSONObject.fromObject(result2.getData());
							// 对象包含另一个对象集合时用以下方法转化
							TraderCustomerVo res = JsonUtils.readValue(json.toString(), TraderCustomerVo.class);

							if(null != res){//有客户
								//客户名称补充
								callFailed.setTraderName(res.getName());
								//客户归属人
								
								User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
								if(null != sale){
									callFailed.setUserId(sale.getUserId());
								}
							}else{//无客户查供应商
								// 接口调用
								String urlSupplier = httpUrl + "tradersupplier/getsupplierinfobyphone.htm";
								TraderSupplierVo traderSupplier = new TraderSupplierVo();
								traderSupplier.setPhone(dataMap.get("CallerNumber").toString());
								// 定义反序列化 数据格式
								final TypeReference<ResultInfo<?>> TypeRefSupplier = new TypeReference<ResultInfo<?>>() {
								};
								ResultInfo<?> resultSupplier = (ResultInfo<?>) HttpClientUtils.post(urlSupplier, traderSupplier, clientId, clientKey,
										TypeRefSupplier);
								JSONObject jsonSupplier = JSONObject.fromObject(resultSupplier.getData());
								// 对象包含另一个对象集合时用以下方法转化
								TraderSupplierVo resSupplier = JsonUtils.readValue(jsonSupplier.toString(), TraderSupplierVo.class);

								if(null != resSupplier){
									//客户名称补充
									callFailed.setTraderName(resSupplier.getTraderSupplierName());
									//客户归属人
									
									User sale = userMapper.getUserByTraderId(resSupplier.getTraderId(), ErpConst.TWO);
									if(null != sale){
										callFailed.setUserId(sale.getUserId());
									}
								}
							}
							
							//默认信息
							callFailed.setIsDialBack(0);
							callFailed.setDialBackUserId(0);
							
							//时间
							String addTime = dataMap.get("CreateTime").toString();
							long time = DateUtil.convertLong(addTime, "yyyy-MM-dd HH:mm:ss");
							callFailed.setAddTime(time);
							
							callFailedMapper.insert(callFailed);
						}
					}
				}
			}
			
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return resultInfo;
	}

	@Override
	public ResultInfo callRecordTraderModify() throws Exception{
		ResultInfo resultInfo = new ResultInfo<>();
		Date date = new Date();
		long addTime = DateUtil.getDateBefore(date, 5);//5天内
		
		List<CommunicateRecord> communicateRecordList = communicateRecordMapper.getNoTraderCommunicateRecord(addTime);
		if(communicateRecordList.size() > 0){
			for(CommunicateRecord c : communicateRecordList){
				
				CommunicateRecord nc = new CommunicateRecord();
				nc.setCommunicateRecordId(c.getCommunicateRecordId());
				// 接口调用（优先查客户，客户没有查供应商库）
				String url = httpUrl + "tradercustomer/getcustomerinfobyphone.htm";
				TraderCustomerVo traderCustomer = new TraderCustomerVo();
				traderCustomer.setPhone(c.getPhone());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
				};
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey,
						TypeRef2);
				JSONObject json = JSONObject.fromObject(result2.getData());
				// 对象包含另一个对象集合时用以下方法转化
				TraderCustomerVo res = JsonUtils.readValue(json.toString(), TraderCustomerVo.class);

				if(null != res){//有客户
					nc.setTraderId(res.getTraderId());
					nc.setTraderType(ErpConst.ONE);
				}else{//无客户查供应商
					// 接口调用
					String urlSupplier = httpUrl + "tradersupplier/getsupplierinfobyphone.htm";
					TraderSupplierVo traderSupplier = new TraderSupplierVo();
					traderSupplier.setPhone(c.getPhone());
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<?>> TypeRefSupplier = new TypeReference<ResultInfo<?>>() {
					};
					ResultInfo<?> resultSupplier = (ResultInfo<?>) HttpClientUtils.post(urlSupplier, traderSupplier, clientId, clientKey,
							TypeRefSupplier);
					JSONObject jsonSupplier = JSONObject.fromObject(resultSupplier.getData());
					// 对象包含另一个对象集合时用以下方法转化
					TraderSupplierVo resSupplier = JsonUtils.readValue(jsonSupplier.toString(), TraderSupplierVo.class);

					if(null != resSupplier){
						nc.setTraderId(resSupplier.getTraderId());
						nc.setTraderType(ErpConst.TWO);
					}
				}
				
				//有traderId则跟新
				if(null != nc.getTraderId()){
					communicateRecordMapper.update(nc);
				}
			}
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}else{
			resultInfo.setCode(0);
			resultInfo.setMessage("无数据");
		}
		return resultInfo;
	}

	@Override
	public ResultInfo callRecordInfoSync() throws Exception {

		ResultInfo resultInfo = new ResultInfo<>();
		
		Calendar beginNow = Calendar.getInstance();  
		beginNow.set(Calendar.HOUR, beginNow.get(Calendar.HOUR) - 4000);  
        Date beginDate = beginNow.getTime();
        Long beginTime = beginDate.getTime();
        
        Calendar endNow = Calendar.getInstance();  
        endNow.set(Calendar.SECOND, endNow.get(Calendar.SECOND) - 1800);  
        Date endDate = endNow.getTime();
        Long endTime = endDate.getTime();
		
//        System.out.println(DateUtil.convertString(beginTime, "yyyy-MM-dd HH:mm:ss"));
//        System.out.println(DateUtil.convertString(endTime, "yyyy-MM-dd HH:mm:ss"));
        
		List<CommunicateRecord> unSyncCommunicateRecord = communicateRecordMapper.getUnSyncCommunicateRecord(beginTime,endTime);
		if(unSyncCommunicateRecord.size() > 0){
			List list = new ArrayList<>();
			for(CommunicateRecord c : unSyncCommunicateRecord){
				Map infoMap = new HashMap<>();
				infoMap.put("record_id", c.getCommunicateRecordId());
				infoMap.put("coid", c.getCoid());
				infoMap.put("employeeID", c.getNumber());
				
				list.add(infoMap);
			}
			
			XmlExercise xmlExercise = new XmlExercise();
			String xmlStr = xmlExercise.listToXml(list, "data","record");
			
			// 调用接口
			RPCServiceClient ser = new RPCServiceClient();
			Options options = ser.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(callNamespace);
			options.setTo(targetEPR);
			// options.setAction("命名空间/WS 方法名");
			options.setAction("getRecords");

			// 指定sfexpressService方法的参数值
			Object[] opAddEntryArgs = new Object[] { xmlStr };
			// 指定sfexpressService方法返回值的数据类型的Class对象
			Class[] classes = new Class[] { String.class };
			// 指定要调用的sfexpressService方法及WSDL文件的命名空间
			QName opAddEntry = new QName(callUrl, "getRecords");
			// 调用sfexpressService方法并输出该方法的返回值
			Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);

			// 接口返回xml字符串
			String xmlString = str[0].toString();
			
			Map result = xmlExercise.xmlToMapList(xmlString);
			
			List resultList = new ArrayList<>();
			if(null != result && result.get("code").equals("0")){
				resultList = (List) result.get("data");
				
				if(resultList.size() > 0){
					for(int i=0;i<resultList.size();i++){
						Map dataMap = new HashMap<>();
						dataMap = (Map) resultList.get(i);
						if(null != dataMap.get("URL") && null != dataMap.get("FILELEN")){
							CommunicateRecord nc = new CommunicateRecord();
							nc.setCommunicateRecordId(Integer.parseInt(dataMap.get("record_id").toString()));
							nc.setSyncStatus(ErpConst.ONE);
							nc.setCoidLength(Integer.parseInt(dataMap.get("FILELEN").toString()));
							nc.setCoidUri(dataMap.get("URL").toString());
							
							communicateRecordMapper.update(nc);
						}
					}
				}
			}
		}else{
			resultInfo.setCode(0);
			resultInfo.setMessage("无数据");
		}
		
		return resultInfo;
	}

	@Override
	public ResultInfo callRecordUnSync() throws Exception {
		ResultInfo resultInfo = new ResultInfo<>();
		Calendar beginNow = Calendar.getInstance();  
        beginNow.setTime(new Date());
        beginNow.set(Calendar.HOUR_OF_DAY, 0);
        beginNow.set(Calendar.MINUTE, 0);
        beginNow.set(Calendar.SECOND, 0);
        Date beginDate = beginNow.getTime();
        Long beginTime = beginDate.getTime();
        
        Calendar endNow = Calendar.getInstance();  
        endNow.setTime(new Date());
        endNow.set(Calendar.HOUR_OF_DAY, 23);
        endNow.set(Calendar.MINUTE, 59);
        endNow.set(Calendar.SECOND, 59);
        Date endDate = endNow.getTime();
        Long endTime = endDate.getTime();
		
        System.out.println(DateUtil.convertString(beginTime, "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.convertString(endTime, "yyyy-MM-dd HH:mm:ss"));
        
        List<CommunicateRecord> communicateRecordByTime = communicateRecordMapper.getCommunicateRecordByTime(beginTime, endTime);
        Map map = new HashMap<>();
        List coids = new ArrayList<>();
        if(communicateRecordByTime.size() > 0){
        	for(CommunicateRecord c : communicateRecordByTime){
        		coids.add(c.getCoid());
        	}
        	
        }
        
        map.put("start", DateUtil.convertString(beginTime, "yyyy-MM-dd HH:mm:ss"));
        map.put("end", DateUtil.convertString(endTime, "yyyy-MM-dd HH:mm:ss"));
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
        options.setAction("getRecordUnSync");
        
        // 指定sfexpressService方法的参数值
        Object[] opAddEntryArgs = new Object[] { xmlStr };
        // 指定sfexpressService方法返回值的数据类型的Class对象
        Class[] classes = new Class[] { String.class };
        // 指定要调用的sfexpressService方法及WSDL文件的命名空间
        QName opAddEntry = new QName(callUrl, "getRecordUnSync");
        // 调用sfexpressService方法并输出该方法的返回值
        Object[] str = ser.invokeBlocking(opAddEntry, opAddEntryArgs, classes);
        
        // 接口返回xml字符串
        String xmlString = str[0].toString();
        
        Map result = xmlExercise.xmlToMapList(xmlString);
        List resultList = new ArrayList<>();
        if(null != result && result.get("code").equals("0")){
        	resultList = (List) result.get("data");
        	
        	if(resultList.size() > 0){
        		for(int i=0;i<resultList.size();i++){
					Map dataMap = new HashMap<>();
					dataMap = (Map) resultList.get(i);
					CommunicateRecord nc = new CommunicateRecord();
					
					String phone = null;
					Integer coidType = 1;
					if(dataMap.get("CALLTYPE").toString().equals("1")){//呼入
						phone = dataMap.get("DNIS").toString();
	                }else{//呼出
	                	phone = dataMap.get("ANI").toString();
	                	coidType = 2;
	                }
					
					String ccNumber = dataMap.get("AGENTID").toString();
					
					String startTime = dataMap.get("StartTime").toString();
					String stopTime = dataMap.get("StopTime").toString();
					
					
					
					if(null != ccNumber && !ccNumber.equals("")){
						User userByCcNumber = userMapper.getUserByCcNumber(ccNumber);
						if(null != userByCcNumber){
							nc.setCreator(userByCcNumber.getUserId());
							nc.setUpdater(userByCcNumber.getUserId());
						}
					}
					nc.setAddTime(DateUtil.convertLong(startTime, "yyyy-MM-dd HH:mm:ss"));
					nc.setModTime(DateUtil.sysTimeMillis());
					
					nc.setBegintime(DateUtil.convertLong(startTime, "yyyy-MM-dd HH:mm:ss"));
					nc.setEndtime(DateUtil.convertLong(stopTime, "yyyy-MM-dd HH:mm:ss"));
					nc.setPhone(phone);
					nc.setCoid(dataMap.get("COID").toString());
					nc.setCoidType(coidType);
					nc.setCoidLength(Integer.parseInt(dataMap.get("FILELEN").toString()));
					nc.setCoidUri(dataMap.get("URL").toString());
					nc.setSyncStatus(ErpConst.ONE);
					
					communicateRecordMapper.insert(nc);
        		}
        	}
        }
        
		return resultInfo;
	}

}
