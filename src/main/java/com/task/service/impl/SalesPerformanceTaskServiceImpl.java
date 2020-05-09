package com.task.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import com.common.constants.Contant;
import com.xxl.job.core.log.XxlJobLogger;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.task.service.SalesPerformanceTaskService;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.SearchModel;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.logistics.model.WarehouseGoodsOperateLog;
import com.vedeng.saleperformance.model.SalesPerformanceOrders;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.model.CommunicateRecord;

@Service("salesPerformanceTaskService")
public class SalesPerformanceTaskServiceImpl extends BaseServiceimpl implements SalesPerformanceTaskService {
	public static Logger logger = LoggerFactory.getLogger(SalesPerformanceTaskServiceImpl.class);

	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;

	@Override
	public ResultInfo updateSalesPerformanceTrader(SearchModel searchModel,Page page) {
		ResultInfo resultInfo = new ResultInfo<>();
		Map<String, Object> map = this.getSalesPerformanceTraderListPage(searchModel, page);
		Page pageInfo = (Page) map.get("page");
		Integer total = pageInfo.getTotalPage();

		for (int i = 2; i <= total; i++) {//循环调取
			pageInfo.setPageNo(i);
			map = this.getSalesPerformanceTraderListPage(searchModel, page);
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Override
	public Map<String, Object> getSalesPerformanceTraderListPage(SearchModel searchModel, Page page) {
		String url = httpUrl + "sales/salesperformancetask/getsalesperformancetraderlistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			throw new RuntimeException();
		}
	}

	@Override
	public ResultInfo updateSalesPerformanceComm(SearchModel searchModel,Page page) {
		ResultInfo resultInfo = new ResultInfo<>();
		Map<String, Object> map = this.getSalesPerformanceCommListPage(searchModel, page);
		Page pageInfo = (Page) map.get("page");
		Integer total = pageInfo.getTotalPage();

		for (int i = 2; i <= total; i++) {//循环调取
			pageInfo.setPageNo(i);
			map = this.getSalesPerformanceCommListPage(searchModel, page);
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Override
	public Map<String, Object> getSalesPerformanceCommListPage(SearchModel searchModel, Page page) {
		String url = httpUrl + "sales/salesperformancetask/getsalesperformancecommlistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			throw new RuntimeException();
		}
	}

	@Override
	public ResultInfo updateSalesPerformanceRate(SearchModel searchModel,Page page) {
		ResultInfo resultInfo = new ResultInfo<>();
		Map<String, Object> map = this.getSalesPerformanceRateListPage(searchModel, page);
		Page pageInfo = (Page) map.get("page");
		Integer total = pageInfo.getTotalPage();

		for (int i = 2; i <= total; i++) {//循环调取
			pageInfo.setPageNo(i);
			map = this.getSalesPerformanceRateListPage(searchModel, page);
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Override
	public Map<String, Object> getSalesPerformanceRateListPage(SearchModel searchModel, Page page) {
		String url = httpUrl + "sales/salesperformancetask/getsalesperformanceratelistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			throw new RuntimeException();
		}
	}

	@Override
	public ResultInfo updateSaleorders(SearchModel searchModel, Page page) {
		ResultInfo resultInfo = new ResultInfo<>();
		Map<String, Object> map = this.getSaleordersListPage(searchModel, page);
		Page pageInfo = (Page) map.get("page");
		Integer total = pageInfo.getTotalPage();

		for (int i = 2; i <= total; i++) {//循环调取
			pageInfo.setPageNo(i);
			map = this.getSaleordersListPage(searchModel, page);
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	private Map<String, Object> getSaleordersListPage(SearchModel searchModel, Page page) {
		String url = httpUrl + "sales/salesperformancetask/getsaleorderslistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			throw new RuntimeException();
		}
	}

	@Override
	public ResultInfo updateSaleBrand(SearchModel searchModel, Page page) {
		ResultInfo resultInfo = new ResultInfo<>();
		Map<String, Object> map = this.getSaleBrandListPage(searchModel, page);
		Page pageInfo = (Page) map.get("page");
		Integer total = pageInfo.getTotalPage();

		for (int i = 2; i <= total; i++) {//循环调取
			pageInfo.setPageNo(i);
			map = this.getSaleBrandListPage(searchModel, page);
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	private Map<String, Object> getSaleBrandListPage(SearchModel searchModel, Page page) {
		String url = httpUrl + "sales/salesperformancetask/getsalebrandlistpage.htm";
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef,page);
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			throw new RuntimeException();
		}
	}

	@Override
	public ResultInfo callRecordInfoSync(SearchModel searchModel, Page page) throws Exception{
		ResultInfo resultInfo = new ResultInfo<>();
		Map<String, Object> map = this.getCallRecordInfoSyncListPage(searchModel, page);
		Page pageInfo = (Page) map.get("page");
		Integer total = pageInfo.getTotalPage();

		for (int i = 2; i <= total; i++) {//循环调取
			pageInfo.setPageNo(i);
			map = this.getCallRecordInfoSyncListPage(searchModel, page);
		}

		resultInfo.setCode(0);
		resultInfo.setMessage("操作成功");
		return resultInfo;
	}

	@Transactional
	@Override
	public Map<String, Object> getCallRecordInfoSyncListPage(SearchModel searchModel, Page page) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("searchModel", searchModel);
		map.put("page", page);
		List<CommunicateRecord> unSyncCommunicateRecord = communicateRecordMapper.getUnSyncCommunicateRecordListPage(map);
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

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("page", page);
			return returnMap;
		}

		return null;
	}

	@Override
	public ResultInfo generateSaleorderData(SearchModel searchModel) {
		String url = httpUrl + "sales/salesperformancetask/generatesaleorderdata.htm";
		List<SalesPerformanceOrders> list = null;
		final TypeReference<ResultInfo<List<SalesPerformanceOrders>>> TypeRef = new TypeReference<ResultInfo<List<SalesPerformanceOrders>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, searchModel, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				list = (List<SalesPerformanceOrders>) result.getData();

				IreportExport ireportExport = new IreportExport();
				ireportExport.generateSaleorderData("/WEB-INF/ireport/jrxml/salesPerformanceOrders.jrxml", list, "salesPerformanceOrders.xls");
			}
		} catch (Exception e) {
			logger.error("generateSaleorderData",e);
			return null;
		}
		return null;
	}

	@Override
	public ResultInfo generateSaleorderBdCount(SearchModel searchModel) {
		String url = httpUrl + "sales/salesperformancetask/salesperformancebdcount.htm";
		XxlJobLogger.log("erp开始统计BD新客数...");
		XxlJobLogger.log("erp统计BD新客数，请求地址：" + url);
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			return (ResultInfo<?>) HttpClientUtils.post(url, null, clientId, clientKey, TypeRef);
		} catch (IOException e) {
			XxlJobLogger.log("erp统计BD新客数失败，",e);
			logger.error("erp发起统计BD新客数失败，错误：",e);
			return null;
		}
	}

	@Override
	public ResultInfo generateSaleorderBdSort(SearchModel searchModel) {
		String url = httpUrl + "sales/salesperformancetask/salesperformancebdsort.htm";
		XxlJobLogger.log("erp开始统计BD新客数排名...");
		XxlJobLogger.log("erp统计BD新客数排名，请求地址：" + url);
		final TypeReference<ResultInfo> TypeRef = new TypeReference<ResultInfo>() {};
		try {
			return (ResultInfo<?>) HttpClientUtils.post(url, null, clientId, clientKey, TypeRef);
		} catch (IOException e) {
			XxlJobLogger.log("erp统计bd新客数排名错误，",e);
			logger.error("erp统计bd新客数排名错误，",e);
			return null;
		}
	}


}
