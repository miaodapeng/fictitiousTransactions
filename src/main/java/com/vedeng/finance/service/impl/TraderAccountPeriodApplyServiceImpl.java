package com.vedeng.finance.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.finance.model.AccountPeriod;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.finance.service.TraderAccountPeriodApplyService;
import com.vedeng.trader.model.TraderAmountBill;
import com.vedeng.trader.model.vo.TraderCertificateVo;

import net.sf.json.JSONObject;

@Service("accountPeriodService")
public class TraderAccountPeriodApplyServiceImpl extends BaseServiceimpl implements TraderAccountPeriodApplyService{
	public static Logger logger = LoggerFactory.getLogger(TraderAccountPeriodApplyServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Override
	public Map<String, Object> getAccountPeriodApplyListPage(TraderAccountPeriodApply tapa, Page page) throws Exception{
		List<TraderAccountPeriodApply> list = null;
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderAccountPeriodApply>>> TypeRef = new TypeReference<ResultInfo<List<TraderAccountPeriodApply>>>() {};
		String url=httpUrl + "finance/accountperiod/getaccountperiodapplylistpage.htm";
		
		ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tapa,clientId,clientKey, TypeRef,page);
		if(result!=null && result.getCode()==0){
			list = (List<TraderAccountPeriodApply>) result.getData();
			page = result.getPage();
			map.put("list", list);map.put("page", page);
		}
		return map;
	}

	@Override
	public TraderAccountPeriodApply getAccountPeriodApply(TraderAccountPeriodApply tapa) throws Exception{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderAccountPeriodApply>> TypeRef = new TypeReference<ResultInfo<TraderAccountPeriodApply>>() {};
		String url=httpUrl + "finance/accountperiod/getaccountperiodapply.htm";
		
		ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tapa,clientId,clientKey, TypeRef);
		if(result!=null && result.getCode()==0){
			tapa = (TraderAccountPeriodApply)result.getData();
		}
		return tapa;
	}

	@Override
	public Map<String,Object> getTraderAptitudeInfo(Integer traderId) throws Exception{
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef1 = new TypeReference<ResultInfo<?>>() {};
		ResultInfo<?> result1 = (ResultInfo<?>) HttpClientUtils.post(httpUrl+ "tradercustomer/getcustomercategorybytrader.htm", traderId, clientId, clientKey,	TypeRef1);
		Integer traderCustomerCategoryId = (Integer) result1.getData();
		
		if(traderCustomerCategoryId!=null){
			
			TraderCertificateVo tc = new TraderCertificateVo();
			tc.setTraderId(traderId);
			tc.setTraderType(ErpConst.ONE);
			if(traderCustomerCategoryId==3||traderCustomerCategoryId==5){
				tc.setCustomerType(2);
			}else{
				tc.setCustomerType(1);
			}
			final TypeReference<ResultInfo<Map<String, Object>>> TypeRef2 = new TypeReference<ResultInfo<Map<String, Object>>>() {};
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "tradercustomer/gettraderaptitudeinfo.htm", tc, clientId, clientKey, TypeRef2);
			Map<String,Object> map = (Map<String, Object>)result2.getData();
			if(map!=null){
				map.put("customerProperty", traderCustomerCategoryId);
			}
			return map;
		}
		return null;
	}

	@Override
	public ResultInfo<?> editAccountPeriodAudit(TraderAccountPeriodApply tapa) throws Exception{
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderAccountPeriodApply>> TypeRef = new TypeReference<ResultInfo<TraderAccountPeriodApply>>() {};
		String url=httpUrl + "finance/accountperiod/editaccountperiodaudit.htm";
		
		ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tapa,clientId,clientKey, TypeRef);
		if(result==null){
			return new ResultInfo<>(-1,"操作錯誤");
		}
		return result;
	}

	@Override
	public List<TraderAmountBill> getTraderAmountBillList(TraderAmountBill tab) {
		String url = httpUrl + "finance/accountperiod/gettraderamountbilllist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderAmountBill>>> TypeRef = new TypeReference<ResultInfo<List<TraderAmountBill>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tab,clientId,clientKey, TypeRef);
			if(result!=null && result.getData() != null){
				List<TraderAmountBill> tabList = (List<TraderAmountBill>) result.getData();
				return tabList;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

	@Override
	public Map<String, Object> getCustomerAccountListPage(AccountPeriod ap, Page page) {
		Map<String,Object> map = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			String url = httpUrl + "finance/accountperiod/getcustomeraccountlistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, ap, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				Map<String,Object> result_map = (Map<String,Object>)result.getData();
				map = new HashMap<String,Object>();
				
				net.sf.json.JSONArray json = null;
				String customerAccountListStr = result_map.get("accountList").toString();
				json = net.sf.json.JSONArray.fromObject(customerAccountListStr);
				
				List<AccountPeriod> customerAccountList = (List<AccountPeriod>) json.toCollection(json, AccountPeriod.class);
				map.put("customerAccountList", customerAccountList);
				
				
				/*String customerRepayListStr = result_map.get("repayList").toString();
				json = net.sf.json.JSONArray.fromObject(customerRepayListStr);
				List<TraderAccountPeriodApply> customerRepayList = (List<TraderAccountPeriodApply>) json.toCollection(json, TraderAccountPeriodApply.class);
				map.put("customerRepayList", customerRepayList);*/
				
				List<Integer> traderIdList = new ArrayList<>();
				for(int i=0;i<customerAccountList.size();i++){
					traderIdList.add(customerAccountList.get(i).getTraderId());
				}
				if(traderIdList.size()>0){
					List<User> userList = userMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
					map.put("userList", userList);
				}
				
				map.put("page", (Page)result.getPage());
				
				ap = (AccountPeriod) JSONObject.toBean(JSONObject.fromObject(result_map.get("ap")), AccountPeriod.class);
				map.put("ap", ap);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	@Override
	public Map<String, Object> getSupplierAccountListPage(AccountPeriod ap, Page page) {
		Map<String,Object> map = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			String url = httpUrl + "finance/accountperiod/getsupplieraccountlistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, ap, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				Map<String,Object> result_map = (Map<String,Object>)result.getData();
				map = new HashMap<String,Object>();
				
				net.sf.json.JSONArray json = null;
				String supplierAccountListStr = result_map.get("accountList").toString();
				json = net.sf.json.JSONArray.fromObject(supplierAccountListStr);
				
				List<AccountPeriod> supplierAccountList = (List<AccountPeriod>) json.toCollection(json, AccountPeriod.class);
				map.put("supplierAccountList", supplierAccountList);
				
				
				/*String customerRepayListStr = result_map.get("repayList").toString();
				json = net.sf.json.JSONArray.fromObject(customerRepayListStr);
				List<TraderAccountPeriodApply> customerRepayList = (List<TraderAccountPeriodApply>) json.toCollection(json, TraderAccountPeriodApply.class);
				map.put("customerRepayList", customerRepayList);*/
				
				List<Integer> traderIdList = new ArrayList<>();
				for(int i=0;i<supplierAccountList.size();i++){
					traderIdList.add(supplierAccountList.get(i).getTraderId());
				}
				if(traderIdList.size()>0){
					List<User> userList = userMapper.getUserByTraderIdList(traderIdList,ErpConst.TWO);//1客户，2供应商
					map.put("userList", userList);
				}
				
				map.put("page", (Page)result.getPage());
				
				ap = (AccountPeriod) JSONObject.toBean(JSONObject.fromObject(result_map.get("ap")), AccountPeriod.class);
				map.put("ap", ap);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	@Override
	public List<TraderAccountPeriodApply> exportAccountPeriodApplyList(TraderAccountPeriodApply tapa) {
		String url = httpUrl + "finance/accountperiod/exportaccountperiodapplylist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderAccountPeriodApply>>> TypeRef = new TypeReference<ResultInfo<List<TraderAccountPeriodApply>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tapa,clientId,clientKey, TypeRef);
			if(result != null && result.getData() != null){
				List<TraderAccountPeriodApply> tabList = (List<TraderAccountPeriodApply>) result.getData();
				if(tabList != null){
					List<Integer> userIdList = new ArrayList<>();//创建人员
					for(int i=0;i<tabList.size();i++){
						userIdList.add(tabList.get(i).getCreator());
					}
					userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
					List<User> userList = userMapper.getUserByUserIds(userIdList);
					for(int i=0;i<tabList.size();i++){
						for(int j=0;j<userList.size();j++){
							if(tabList.get(i).getCreator().intValue() == userList.get(j).getUserId().intValue()){
								tabList.get(i).setCreatorNm(userList.get(j).getUsername());
							}
						}
					}
				}
				return tabList;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

}
