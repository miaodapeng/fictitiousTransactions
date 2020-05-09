package com.vedeng.datacenter.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.datacenter.service.MDTraderService;
import com.vedeng.homepage.model.vo.ChildrenEchartsDataVo;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.system.model.SysOptionDefinition;

import net.sf.json.JSONObject;


@Service("mDTraderService")
public class MDTraderServiceImpl extends BaseServiceimpl implements MDTraderService{
	public static Logger logger = LoggerFactory.getLogger(MDTraderServiceImpl.class);

	@Resource
	private LogisticsService logisticsService;

	@Override
	public Integer getTotalCustomerNum() {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef = new TypeReference<ResultInfo<Integer>>() {};
		String url=httpUrl + "datacenter/mdtrader/gettotalcustomernum.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		Integer num = (Integer) result.getData();
		return num;
	}

	@Override
	public Integer getTotalTraderCustomerNum() {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef = new TypeReference<ResultInfo<Integer>>() {};
		String url=httpUrl + "datacenter/mdtrader/gettotaltradercustomernum.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, null,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		Integer num = (Integer) result.getData();
		return num;
	}

	@Override
	public Integer getTotalManyTraderCustomerNum() {
		return null;
	}

	@Override
	public Map<String, String> getCustomerNaturePro(Map<String, Object> map) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String, String>>> TypeRef = new TypeReference<ResultInfo<Map<String, String>>>() {};
		String url=httpUrl + "datacenter/mdtrader/getcustomernaturepro.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
			if (result != null && result.getData() != null) {
				Map<String, String> mapRes = (Map<String, String>) result.getData();
				return mapRes;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public Map<String, String> getCustomerLevelPro(Map<String, Object> map) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String, String>>> TypeRef = new TypeReference<ResultInfo<Map<String, String>>>() {};
		String url=httpUrl + "datacenter/mdtrader/getcustomerlevelpro.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
			if (result != null && result.getData() != null) {
				Map<String, String> mapRes = (Map<String, String>) result.getData();
				return mapRes;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public Map<String, String> getCustomerNumInfo(Map<String, Object> map) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String, String>>> TypeRef = new TypeReference<ResultInfo<Map<String, String>>>() {};
		String url=httpUrl + "datacenter/mdtrader/getcustomernuminfo.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
			if (result != null && result.getData() != null) {
				Map<String, String> mapRes = (Map<String, String>) result.getData();
				return mapRes;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public EchartsDataVo getOrgTraderInfo(EchartsDataVo echartsDataVo) {
		ResultInfo<?> result = new ResultInfo<>();
		
		EchartsDataVo echartsDataVoRes = new EchartsDataVo();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<EchartsDataVo>> TypeRef = new TypeReference<ResultInfo<EchartsDataVo>>() {};
		String url=httpUrl + "datacenter/mdtrader/getorgtraderinfo.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, echartsDataVo,clientId,clientKey, TypeRef);
			if (result != null && result.getData() != null) {
				echartsDataVoRes = (EchartsDataVo)result.getData();
				//JSONObject json = JSONObject.fromObject(result.getData());
				//echartsDataVoRes = (EchartsDataVo) JSONObject.toBean(json, EchartsDataVo.class);
				
				//JSONObject json = JSONObject.fromObject(result.getData());
				//echartsDataVoRes = JsonUtils.readValue(json.toString(), EchartsDataVo.class);
				return echartsDataVoRes;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	/**
	 * <b>Description:</b><br> 查询物流数据中心数据
	 * @param echartsDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月11日 上午11:40:07
	 */
	@Override
	public EchartsDataVo getLogisticsEchartsDataVo(EchartsDataVo edv) {

		String url = httpUrl + ErpConst.GET_GETLOGISTICECHARTS;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, edv, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			EchartsDataVo echartsDataVo = JsonUtils.readValue(json.toString(), EchartsDataVo.class);
			//物流
			List<ChildrenEchartsDataVo> logisticsFreightList = new ArrayList<>();//快递运费堆叠柱状图数据
				
			List<ChildrenEchartsDataVo> logisticsPiecesList = new ArrayList<>();//快递件数堆叠柱状图数据

			List<Logistics> logisticsList = logisticsService.getLogisticsList(edv.getCompanyId());

			//快递运费
			Map<Integer,Map<Integer, BigDecimal>> logisticsFreightMap = echartsDataVo.getLogisticsFreightMap();
			//快递件数
			Map<Integer,Map<Integer, Integer>> logisticsPiecesMap = echartsDataVo.getLogisticsPiecesMap();
			List<String> logisticsNames = new ArrayList<>();
			//List<BigDecimal> logisticsAmount = new ArrayList<>();
				for (Logistics logistics : logisticsList) {
					
					
					ChildrenEchartsDataVo freight = new ChildrenEchartsDataVo();
					ChildrenEchartsDataVo pieces = new ChildrenEchartsDataVo();
					freight.setName(logistics.getName());
					pieces.setName(logistics.getName());
					List<String> freightList = new ArrayList<>();
					List<String> piecesList = new ArrayList<>();
					for (int i = 1; i < 13; i++) {
						if(logisticsFreightMap.containsKey(logistics.getLogisticsId())){
							Map<Integer, BigDecimal> freightMap = logisticsFreightMap.get(logistics.getLogisticsId());
							if(freightMap.containsKey(i)){
								freightList.add(freightMap.get(i).toString());
							}else{
								freightList.add("0");
							}
						}else{
							freightList.add("0");
						}
						
						if(logisticsPiecesMap.containsKey(logistics.getLogisticsId())){
							Map<Integer, Integer> piecesMap = logisticsPiecesMap.get(logistics.getLogisticsId());
							if(piecesMap.containsKey(i)){
								piecesList.add(piecesMap.get(i).toString());
							}else{
								piecesList.add("0");
							}
						}else{
							piecesList.add("0");
						}
					}
					freight.setChildrenList(freightList);
					pieces.setChildrenList(piecesList);
					
					logisticsFreightList.add(freight);
					logisticsPiecesList.add(pieces);
				}
				echartsDataVo.setLogisticsFreightList(logisticsFreightList);
				echartsDataVo.setLogisticsPiecesList(logisticsPiecesList);
				
				echartsDataVo.setLogisticsNames(logisticsNames);
				//echartsDataVo.setLogisticsAmount(logisticsAmount);
				
				List<SysOptionDefinition> list = getSysOptionDefinitionList(495);
				//本月业务发货占比
				//Map<Integer, Integer> businessDeliveryMap = echartsDataVo.getBusinessDeliveryMap();
				List<String> businessNames = new ArrayList<>();
				List<Integer> businessNums = new ArrayList<>();
				
				//业务运费统计
				Map<Integer,Map<Integer, BigDecimal>> businessFreightMap = echartsDataVo.getBusinessFreightMap();
				List<ChildrenEchartsDataVo> childrenEchartsDataVoList = new ArrayList<>();
				for (SysOptionDefinition sys : list) {
					businessNames.add(sys.getTitle());
//					if(!businessDeliveryMap.isEmpty() && businessDeliveryMap.containsKey(sys.getSysOptionDefinitionId())){
//						businessNums.add(businessDeliveryMap.get(sys.getSysOptionDefinitionId()));
//					}else if(ObjectUtils.isEmpty(sys.getRelatedField())){
//						businessNums.add(0);
//					}
					ChildrenEchartsDataVo ch = new ChildrenEchartsDataVo();
					ch.setName(sys.getTitle());
					if(!businessFreightMap.isEmpty() && businessFreightMap.containsKey(sys.getSysOptionDefinitionId())){
						Map<Integer, BigDecimal> bus = businessFreightMap.get(sys.getSysOptionDefinitionId());
						
						List<String> childrenList = new ArrayList<>();
						for (int i = 1; i < 13; i++) {
							if(!bus.isEmpty() && bus.containsKey(i)){
								childrenList.add(bus.get(i).toString());
							}else{
								childrenList.add("0");
							}
						}
						ch.setChildrenList(childrenList);
					}else{
						List<String> childrenList = new ArrayList<>();
						for (int i = 1; i < 13; i++) {
							childrenList.add("0");
						}
						ch.setChildrenList(childrenList);
					}
					childrenEchartsDataVoList.add(ch);
				
				echartsDataVo.setChildrenEchartsDataVoList(childrenEchartsDataVoList);
				echartsDataVo.setBusinessNames(businessNames);
				echartsDataVo.setBusinessNums(businessNums);
				echartsDataVo.setFreightChargesMap(null);
				echartsDataVo.setBusinessDeliveryMap(null);
				echartsDataVo.setBusinessFreightMap(null);
				echartsDataVo.setLogisticsFreightMap(null);
				echartsDataVo.setLogisticsPiecesMap(null);
				echartsDataVo.setOutWarehouseMap(null);
				echartsDataVo.setEnterWarehouseMap(null);
				echartsDataVo.setStockTurnoverMap(null);
			}
			return echartsDataVo;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	/**
	 * <b>Description:</b><br> 查询售后数据中心数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月11日 上午11:40:07
	 */
	@Override
	public EchartsDataVo getAftersalesDataCenter(EchartsDataVo edv) {
		
		String url = httpUrl + ErpConst.GET_GETAFTERSALESECHARTS;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, edv, clientId, clientKey, TypeRef2);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			JSONObject json = JSONObject.fromObject(result.getData());
			EchartsDataVo echartsDataVo = JsonUtils.readValue(json.toString(), EchartsDataVo.class);
			if(echartsDataVo == null){
				return null;
			}
			List<SysOptionDefinition> sales = getSysOptionDefinitionList(535);//销售售后类型
			Map<Integer, Integer> salesMap = echartsDataVo.getSaleAftersaleMap();
			
			Map<Integer, Integer> returnSaleAftersaleMap = echartsDataVo.getReturnSaleAftersaleMap();//销售订单退货原因
			
			Map<Integer, Integer> exchangeSaleAftersaleMap = echartsDataVo.getExchangeSaleAftersaleMap();//销售订单换货原因
			List<String> salesNames = new ArrayList<>();
			List<Integer> salesNums = new ArrayList<>();
			//退货
			List<String> returnSalesNames = new ArrayList<>();
			List<Integer> returnSalesNums = new ArrayList<>();
			//换货
			List<String> exchangeSalesNames = new ArrayList<>();
			List<Integer> exchangeSalesNums = new ArrayList<>();
			
			for (SysOptionDefinition sys : sales) {
				if(!salesMap.isEmpty() && salesMap.containsKey(sys.getSysOptionDefinitionId()) && ObjectUtils.isEmpty(sys.getRelatedField())){
					salesNames.add(sys.getTitle());
					salesNums.add(salesMap.get(sys.getSysOptionDefinitionId()));
				}else if(ObjectUtils.isEmpty(sys.getRelatedField())){
					salesNames.add(sys.getTitle());
					salesNums.add(0);
				}
				if(!returnSaleAftersaleMap.isEmpty() && returnSaleAftersaleMap.containsKey(sys.getSysOptionDefinitionId()) 
						&& ObjectUtils.notEmpty(sys.getRelatedField()) && sys.getRelatedField().equals("539")){
					returnSalesNames.add(sys.getTitle());
					returnSalesNums.add(returnSaleAftersaleMap.get(sys.getSysOptionDefinitionId()));
				}else if(ObjectUtils.notEmpty(sys.getRelatedField()) && sys.getRelatedField().equals("539")){
					returnSalesNames.add(sys.getTitle());
					returnSalesNums.add(0);
				}
				if(!exchangeSaleAftersaleMap.isEmpty() && exchangeSaleAftersaleMap.containsKey(sys.getSysOptionDefinitionId()) 
						&& ObjectUtils.notEmpty(sys.getRelatedField()) && sys.getRelatedField().equals("540")){
					exchangeSalesNames.add(sys.getTitle());
					exchangeSalesNums.add(exchangeSaleAftersaleMap.get(sys.getSysOptionDefinitionId()));
				}else if(ObjectUtils.notEmpty(sys.getRelatedField()) && sys.getRelatedField().equals("540")){
					exchangeSalesNames.add(sys.getTitle());
					exchangeSalesNums.add(0);
				}
			}
			echartsDataVo.setSalesNames(salesNames);
			echartsDataVo.setSalesNums(salesNums);			
			echartsDataVo.setReturnSalesNames(returnSalesNames);
			echartsDataVo.setReturnSalesNums(returnSalesNums);			
			echartsDataVo.setExchangeSalesNames(exchangeSalesNames);
			echartsDataVo.setExchangeSalesNums(exchangeSalesNums);
			List<SysOptionDefinition> thirds = getSysOptionDefinitionList(537);//第三方售后类型
			Map<Integer, Integer> thirdAftersaleMap = echartsDataVo.getThirdAftersaleMap();
			List<String> thirdNames = new ArrayList<>();
			List<Integer> thirdNums = new ArrayList<>();
			for (SysOptionDefinition sys : thirds) {
				if(!thirdAftersaleMap.isEmpty() && thirdAftersaleMap.containsKey(sys.getSysOptionDefinitionId()) && ObjectUtils.isEmpty(sys.getRelatedField())){
					thirdNames.add(sys.getTitle());
					thirdNums.add(thirdAftersaleMap.get(sys.getSysOptionDefinitionId()));
				}else if(ObjectUtils.isEmpty(sys.getRelatedField())){
					thirdNames.add(sys.getTitle());
					thirdNums.add(0);
				}
			}
			echartsDataVo.setThirdNames(thirdNames);
			echartsDataVo.setThirdNums(thirdNums);
			echartsDataVo.setSaleAftersaleMap(null);
			echartsDataVo.setThirdAftersaleMap(null);
			echartsDataVo.setReturnSaleAftersaleMap(null);
			echartsDataVo.setExchangeSaleAftersaleMap(null);
			return echartsDataVo;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

}
