package com.vedeng.finance.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.key.CryptBase64Tool;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.HttpRequest;
import com.vedeng.common.util.XmlExercise;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.NjBankInterfaceInfo;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.service.BankBillService;

@Service("bankBillService")
public class BankBillServiceImpl extends BaseServiceimpl implements BankBillService {
	public static Logger logger = LoggerFactory.getLogger(BankBillServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
    
	/**
     * 获取银行流水列表
     */
    @Override
    public Map<String, Object> getBankBillListPage(BankBill bankBill, Page page) {
	Map<String, Object> map = new HashMap<>();
	// 调用银行流水列表
	String url = httpUrl + "finance/bankbill/getbankbilllistpage.htm";
	// 定义反序列化 数据格式
	List<BankBill> bankBillList = null;
	final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>() {
	};
	final TypeReference<ResultInfo<Map<String, Object>>> TypeRefM = new TypeReference<ResultInfo<Map<String, Object>>>() {
	};
	try {
	    // 获取银行流水列表
	    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef, page);
	    if (result.getCode() == 0) {
		bankBillList = (List<BankBill>) result.getData();
		map.put("list", bankBillList);
		map.put("page", result.getPage());
		String urlM = httpUrl + "finance/bankbill/getbankbilllistinfo.htm";
		ResultInfo<?> resultM = (ResultInfo<?>) HttpClientUtils.post(urlM, bankBill, clientId, clientKey,
			TypeRefM, page);
		Map<String, Object> result_map = null;
		result_map = (Map<String, Object>) resultM.getData();
		BigDecimal getAmount = new BigDecimal(result_map.get("getAmount").toString());
		BigDecimal payAmount = new BigDecimal(result_map.get("payAmount").toString());
		BigDecimal orderAmount = new BigDecimal(result_map.get("orderAmount").toString());
		BigDecimal matchAmount = new BigDecimal(result_map.get("matchAmount").toString());
		Integer orderNum = new Integer(result_map.get("orderNum").toString());
		map.put("getAmount", getAmount);
		map.put("payAmount", payAmount);
		map.put("orderAmount", orderAmount);
		map.put("matchAmount", matchAmount);
		map.put("orderNum", orderNum);

	    }
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return null;
	}
	return map;
    }

    /**
     * 获取银行流水匹配列表
     */
    @Override
    public Map<String, Object> getBankBillMatchListPage(BankBill bankBill, Page page) {
	Map<String, Object> map = new HashMap<>();
	// 调用银行流水列表
	String url = httpUrl + "finance/bankbill/getbankbillmatchlistpage.htm";
	// 定义反序列化 数据格式
	List<BankBill> bankBillList = null;
	final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>() {
	};
	final TypeReference<ResultInfo<Map<String, Object>>> TypeRefM = new TypeReference<ResultInfo<Map<String, Object>>>() {
	};
	try {
	    // 获取匹配过后的银行流水列表
	    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef,
		    page);
	    if (result.getCode() == 0) {
		bankBillList = (List<BankBill>) result.getData();
		map.put("list", bankBillList);
		map.put("page", result.getPage());
		String urlM = httpUrl + "finance/bankbill/getbankbilllistinfo.htm";
		ResultInfo<?> resultM = (ResultInfo<?>) HttpClientUtils.post(urlM, bankBill, clientId, clientKey,
			TypeRefM, page);
		Map<String, Object> result_map = null;
		result_map = (Map<String, Object>) resultM.getData();
		BigDecimal getAmount = new BigDecimal(result_map.get("getAmount").toString());
		BigDecimal payAmount = new BigDecimal(result_map.get("payAmount").toString());
		BigDecimal orderAmount = new BigDecimal(result_map.get("orderAmount").toString());
		BigDecimal matchAmount = new BigDecimal(result_map.get("matchAmount").toString());
		Integer orderNum = new Integer(result_map.get("orderNum").toString());
		map.put("getAmount", getAmount);
		map.put("payAmount", payAmount);
		map.put("orderAmount", orderAmount);
		map.put("matchAmount", matchAmount);
		map.put("orderNum", orderNum);

	    }
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return null;
	}
	return map;
    }

    @Override
    public ResultInfo editBankBill(BankBill bankBill) {
	String url = httpUrl + "finance/bankbill/editbankbill.htm";
	final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {
	};
	try {
	    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef);
	    // 接口返回条码生成的记录
	    if (result.getCode() == 0) {
		return new ResultInfo(0, "修改成功");
	    } else {
		return new ResultInfo();
	    }
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG, e);
	    return new ResultInfo();
	}
    }

    @Override
    public BankBill getBankBillById(Integer bankBillId) {
	String url = httpUrl + "finance/bankbill/getbankbillbyid.htm";
	// 定义反序列化 数据格式
	final TypeReference<ResultInfo<BankBill>> TypeRef = new TypeReference<ResultInfo<BankBill>>() {
	};
	ResultInfo<?> result;
	BankBill bankBill = new BankBill();
	try {
	    result = (ResultInfo<?>) HttpClientUtils.post(url, bankBillId, clientId, clientKey, TypeRef);
	    bankBill = (BankBill) result.getData();
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG, e);
	}
	return bankBill;
    }

    @Override
    public ResultInfo addBankPayApply(PayApply payApplyInfo) {
	String url = httpUrl + "finance/bankbill/addbankpayapply.htm";
	// 定义反序列化 数据格式
	final TypeReference<ResultInfo<NjBankInterfaceInfo>> TypeRef = new TypeReference<ResultInfo<NjBankInterfaceInfo>>() {
	};
	ResultInfo<?> result = new ResultInfo<>();
	try {
	    result = (ResultInfo<?>) HttpClientUtils.post(url, payApplyInfo, clientId, clientKey, TypeRef);
	} catch (IOException e) {
	    logger.error(Contant.ERROR_MSG+"addBankPayApply", e);
	}
	return result;
    }

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo sendBankBillList(BankBill bankBill, Page page, HttpSession session) throws Exception {
		ResultInfo result = new ResultInfo();
		List<BankBill> mapBillList = null;
		
		User user = (User) session.getAttribute(Consts.SESSION_USER);
		
		//获取发送至金蝶银行流水
		ResultInfo<List<BankBill>> resultMap = (ResultInfo<List<BankBill>>) this.sendBankBillListPage(bankBill, page);
		mapBillList = (List<BankBill>) resultMap.getData();
		if(null != mapBillList && mapBillList.size() > 0){
			for (BankBill bankBill2 : mapBillList) {
				//查询销售人员名称
				String[] userId = bankBill2.getUserId().split(",");
				bankBill2.setUserId(userId[0]);
				
				//可优化（批量查）
				if(userId.length > 1) {
					bankBill2.setUserName(userMapper.getUserNameByUserId(Integer.parseInt(userId[0].trim())) + "等");
				}else {
					bankBill2.setUserName(userMapper.getUserNameByUserId(Integer.parseInt(userId[0].trim())));
				}
				//销售订单编号
				String[] orderNo = bankBill2.getSaleorderNo().split(",");
				if(orderNo.length > 1){
					bankBill2.setSaleorderNo(orderNo[0] + "等");
				}
				
				//客户名称
				if(null != bankBill2.getTraderName() && !("".equals(bankBill2.getTraderName()))){
					String[] traderName = bankBill2.getTraderName().split(",");
					if(traderName.length > 1) {
						bankBill2.setTraderName(traderName[0]);
					}
				}
				//客户Id
				if(null != bankBill2.getTraderId() && !("".equals(bankBill2.getTraderId()))){
					String[] traderId = bankBill2.getTraderId().split(",");
					if(traderId.length > 0) {
						bankBill2.setTraderId(traderId[0]);
					}
				}
				//备注
				bankBill2.setComments(bankBill2.getTranFlow() + "#+" + bankBill2.getSaleorderNo() + "+" + bankBill2.getUserName());
				
				bankBill2.setUserId(user.getUserId().toString());
			}
		}
		
		Integer pageCount =  resultMap.getPage().getTotalPage();
		for (int i = 2; i <= pageCount; i++) {
			page.setPageNo(i);
			ResultInfo<List<BankBill>> listMap = (ResultInfo<List<BankBill>>) this.sendBankBillListPage(bankBill, page);
			List<BankBill> list = (List<BankBill>) listMap.getData();
			if(null != list && list.size() > 0){
				for (BankBill bankBill2 : list) {
					//查询销售人员名称
					String[] userId = bankBill2.getUserId().split(",");
					if(userId.length > 1) {
						bankBill2.setUserName(userMapper.getUserNameByUserId(Integer.valueOf(userId[0])) + "等");
					}else {
						bankBill2.setUserName(userMapper.getUserNameByUserId(Integer.valueOf(userId[0])));
					}
					//销售订单编号
					String[] orderNo = bankBill2.getSaleorderNo().split(",");
					if(orderNo.length > 1){
						bankBill2.setSaleorderNo(orderNo[0] + "等");
					}
					//客户名称
					if(null != bankBill2.getTraderName() && !("".equals(bankBill2.getTraderName()))){
						String[] traderName = bankBill2.getTraderName().split(",");
						if(traderName.length > 1) {
							bankBill2.setTraderName(traderName[0]);
						}
					}
					//客户Id
					if(null != bankBill2.getTraderId() && !("".equals(bankBill2.getTraderId()))){
						String[] traderId = bankBill2.getTraderId().split(",");
						if(traderId.length > 0) {
							bankBill2.setTraderId(traderId[0]);
						}
					}
					//备注
					bankBill2.setComments(bankBill2.getTranFlow() + "#+" + bankBill2.getSaleorderNo() + "+" + bankBill2.getUserName());
					
					bankBill2.setUserId(user.getUserId().toString());
				}
				mapBillList.addAll(list);
			}
		}
		
		//保存发送至金蝶数据至本地库
		if(null != mapBillList && mapBillList.size() > 0){
			XmlExercise xmlExercise = new XmlExercise();
			Map data = new LinkedHashMap<>();
			data.put("companyid", 1);
			
			List<Map<String,Object>> dataList = new ArrayList<>();
			for(BankBill bankBill2 : mapBillList){
				//不允许traderId为null
				if(null == bankBill2.getTraderId() || "".equals(bankBill2.getTraderId()) || "0".equals(bankBill2.getTraderId())){
					result.setMessage("推送失败，不允许存在traderId为空的值！");
					result.setCode(-1);
					return result;
				}
				bankBill2.setUserIdNow(bankBill.getUserIdNow());
				Map data1 = new LinkedHashMap<>();
				Map data2 = new HashMap<>();
				data1.put("id", bankBill2.getBankBillId());
				data1.put("date", bankBill2.getTrandate());
				data1.put("tranFlow", bankBill2.getTranFlow());
				data1.put("orderNo", bankBill2.getSaleorderNo());
				data1.put("saler", bankBill2.getUserName());
				data1.put("amount", bankBill2.getAmt());
				data1.put("traderName", bankBill2.getTraderName());
				data1.put("traderId", bankBill2.getTraderId());
				data1.put("remark", bankBill2.getComments());
				data2.put("info", data1);				
				dataList.add(data2);
			}
			data.put("list", dataList);
			String dataXmlStr = xmlExercise.mapToListXml(data, "data");
			String params = CryptBase64Tool.desEncrypt(dataXmlStr, kingdleeKey);
			try {
				String sendPost = HttpRequest.sendPost(kingdleeReceamountUrl, "msg="+URLEncoder.encode(params, "utf-8"));
				Map info = xmlExercise.xmlToMap(sendPost);
				if (null != info && info.get("code").equals("0")) {
					page.setTotalRecord(mapBillList.size());
					result = this.saveBankBillList(mapBillList);
					result.setPage(page);
				}
			} catch (Exception e) {
				result.setCode(-1);
				result.setMessage("推送失败，对方接口异常！");
				return result;
			}
		}
		return result;
	}

	private ResultInfo saveBankBillList(List<BankBill> mapBillList) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "finance/bankbill/savesendbankbilllistpage.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, mapBillList,clientId,clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					return result;
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	//获取发送至金蝶银行流水
	private ResultInfo<?> sendBankBillListPage(BankBill bankBill, Page page) {
		String url = httpUrl + "report/finance/getsendbankbilllistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>(){};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef, page);
		    return result;
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public Map<String, Object> getBankBillPayMatchListPage(BankBill bankBill, Page page) {
	    Map<String, Object> map = new HashMap<>();
		// 调用银行流水列表
		String url = httpUrl + "finance/bankbill/getbankbillpaymatchlistpage.htm";
		// 定义反序列化 数据格式
		List<BankBill> bankBillList = null;
		final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>() {
		};
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRefM = new TypeReference<ResultInfo<Map<String, Object>>>() {
		};
		try {
			    // 获取匹配过后的银行流水列表
			    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef, page);
			    if (result.getCode() == 0) {
				bankBillList = (List<BankBill>) result.getData();
				map.put("list", bankBillList);
				map.put("page", result.getPage());
				String urlM = httpUrl + "finance/bankbill/getbankbilllistinfo.htm";
				ResultInfo<?> resultM = (ResultInfo<?>) HttpClientUtils.post(urlM, bankBill, clientId, clientKey,
					TypeRefM, page);
				Map<String, Object> result_map = null;
				result_map = (Map<String, Object>) resultM.getData();
				BigDecimal getAmount = new BigDecimal(result_map.get("getAmount").toString());
				BigDecimal payAmount = new BigDecimal(result_map.get("payAmount").toString());
				BigDecimal orderAmount = new BigDecimal(result_map.get("orderAmount").toString());
				BigDecimal matchAmount = new BigDecimal(result_map.get("matchAmount").toString());
				Integer orderNum = new Integer(result_map.get("orderNum").toString());
				map.put("getAmount", getAmount);
				map.put("payAmount", payAmount);
				map.put("orderAmount", orderAmount);
				map.put("matchAmount", matchAmount);
				map.put("orderNum", orderNum);
		    }
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    return null;
		}
		return map;
	}
	
	//发送付款记录至金蝶
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo sendPayBillToKindlee(BankBill bankBill, Page page, HttpSession session) throws UnsupportedEncodingException {
		ResultInfo result = new ResultInfo();
		List<BankBill> mapBankBillList = null;
		
		//获取发送至金蝶的付款记录
		ResultInfo<List<BankBill>> resultMap = (ResultInfo<List<BankBill>>) this.sendPayBilllistPage(bankBill, page);
		
		mapBankBillList = (List<BankBill>) resultMap.getData();
		if(null != mapBankBillList && mapBankBillList.size() > 0){
			for (BankBill bankBill2 : mapBankBillList) {
				//备注
				bankBill2.setComments(bankBill2.getTranFlow() + "#+" + bankBill2.getBuyorderNo());
			}
		}
		
		Integer pageCount =  resultMap.getPage().getTotalPage();
		for (int i = 2; i <= pageCount; i++) {
			page.setPageNo(i);
			ResultInfo<List<BankBill>> listMap = (ResultInfo<List<BankBill>>) this.sendPayBilllistPage(bankBill, page);
			List<BankBill> list = (List<BankBill>) listMap.getData();
			if(null != list && list.size() > 0){
				for (BankBill bankBill2 : list) {
					//备注
					bankBill2.setComments(bankBill2.getTranFlow() + "#+" + bankBill2.getBuyorderNo());
				}
				mapBankBillList.addAll(list);
			}
		}
		
		//记录推送至金蝶的付款记录
		if(null != mapBankBillList && mapBankBillList.size() > 0){
			XmlExercise xmlExercise = new XmlExercise();
			Map data = new LinkedHashMap<>();
			data.put("companyid", 1);
			
			List<Map<String,Object>> dataList = new ArrayList<>();
			
			for(BankBill bankBill2 : mapBankBillList){
				//不允许traderId为null
				if(null == bankBill2.getTraderId() || "".equals(bankBill2.getTraderId()) || "0".equals(bankBill2.getTraderId())){
					result.setMessage("推送失败，不允许存在traderId为空的值！");
					result.setCode(-1);
					return result;
				}
				bankBill2.setUserIdNow(bankBill.getUserIdNow());
				Map data1 = new LinkedHashMap<>();
				Map data2 = new HashMap<>();
				data1.put("id", bankBill2.getBankBillId());
				data1.put("date", bankBill2.getTrandate());
				data1.put("type", bankBill2.getFlag2());
				data1.put("tranFlow", bankBill2.getTranFlow());
				data1.put("orderNo", bankBill2.getBuyorderNo());
				data1.put("amount", bankBill2.getAmt());
				data1.put("traderName", bankBill2.getTraderName());
				data1.put("traderId", bankBill2.getTraderId());
				data1.put("remark", bankBill2.getComments());
				data2.put("info", data1);				
				dataList.add(data2);
			}
			
			data.put("list", dataList);
			String dataXmlStr = xmlExercise.mapToListXml(data, "data");
			
			String params = CryptBase64Tool.desEncrypt(dataXmlStr, kingdleeKey);
			
			try {
				String sendPost = HttpRequest.sendPost(kingdleePayamountUrl, "msg="+URLEncoder.encode(params, "utf-8"));
				Map info = xmlExercise.xmlToMap(sendPost);
				if (null != info && info.get("code").equals("0")) {
					page.setTotalRecord(mapBankBillList.size());
					result = this.savePayBillToKindlee(mapBankBillList);
					result.setPage(page);
				}
			} catch (Exception e) {
				result.setCode(-1);
				result.setMessage("推送失败，对方接口异常！");
				return result;
			}
		}
		return result;
	}
	
	
	private ResultInfo savePayBillToKindlee(List<BankBill> mapBankBillList) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
		String url=httpUrl + "finance/bankbill/savepaybilltokindlee.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, mapBankBillList,clientId,clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					return result;
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	private ResultInfo<?> sendPayBilllistPage(BankBill bankBill, Page page) {
		String url = httpUrl + "finance/bankbill/sendpaybilllistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>(){};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef, page);
		    
		    return result;
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		}
		return new ResultInfo<>();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo<T> sendChPayBillToKindlee(BankBill bankBill, Page page, HttpSession session){
		ResultInfo<T> result = new ResultInfo<T>();
		List<BankBill> mapBankBillList = null;
		//获取发送至金蝶的付款记录
		ResultInfo<List<BankBill>> resultMap = (ResultInfo<List<BankBill>>) this.sendChPayBilllistPage(bankBill, page);
		
		mapBankBillList = (List<BankBill>) resultMap.getData();
		if(null != mapBankBillList && mapBankBillList.size() > 0){
			for (BankBill bankBill2 : mapBankBillList) {
				//备注
				bankBill2.setComments(bankBill2.getTranFlow() + "#+" + bankBill2.getBuyorderNo());
			}
		}
		
		Integer pageCount =  resultMap.getPage().getTotalPage();
		for (int i = 2; i <= pageCount; i++) {
			page.setPageNo(i);
			@SuppressWarnings("unchecked")
			ResultInfo<List<BankBill>> listMap = (ResultInfo<List<BankBill>>) this.sendChPayBilllistPage(bankBill, page);
			List<BankBill> list = (List<BankBill>) listMap.getData();
			if(null != list && list.size() > 0){
				for (BankBill bankBill2 : list) {
					//备注
					bankBill2.setComments(bankBill2.getTranFlow() + "#+" + bankBill2.getBuyorderNo());
				}
				mapBankBillList.addAll(list);
			}
		}
		
		//记录推送至金蝶的付款记录
		if(null != mapBankBillList && mapBankBillList.size() > 0){
			XmlExercise xmlExercise = new XmlExercise();
			Map data = new LinkedHashMap<>();
			data.put("companyid", 1);
			
			List<Map<String,Object>> dataList = new ArrayList<>();
			
			for(BankBill bankBill2 : mapBankBillList){
				//不允许traderId为null
				if(null == bankBill2.getTraderId() || "".equals(bankBill2.getTraderId()) || "0".equals(bankBill2.getTraderId())){
					result.setMessage("推送失败，不允许存在traderId为空的值！");
					result.setCode(-1);
					return result;
				}
				bankBill2.setUserIdNow(bankBill.getUserIdNow());
				Map data1 = new LinkedHashMap<>();
				Map data2 = new HashMap<>();
				data1.put("id", bankBill2.getBankBillId());
				data1.put("date", bankBill2.getTrandate());
				data1.put("type", bankBill2.getFlag2());
				data1.put("tranFlow", bankBill2.getTranFlow());
				data1.put("orderNo", bankBill2.getBuyorderNo());
				data1.put("amount", bankBill2.getAmt());
				data1.put("traderName", bankBill2.getTraderName());
				data1.put("traderId", bankBill2.getTraderId());
				data1.put("remark", bankBill2.getComments());
				data2.put("info", data1);				
				dataList.add(data2);
			}
			
			data.put("list", dataList);
			String dataXmlStr = xmlExercise.mapToListXml(data, "data");
			
			String params = CryptBase64Tool.desEncrypt(dataXmlStr, kingdleeKey);
			
			try {
				String sendPost = HttpRequest.sendPost(kingdleePayamountUrl, "msg="+URLEncoder.encode(params, "utf-8"));
				Map info = xmlExercise.xmlToMap(sendPost);
				if (null != info && info.get("code").equals("0")) {
					page.setTotalRecord(mapBankBillList.size());
					result = this.saveChPayBillToKindlee(mapBankBillList);
					result.setPage(page);
				}
			} catch (Exception e) {
				result.setCode(-1);
				result.setMessage("推送失败，对方接口异常！");
				return result;
			}
		}
		return result;
	}
	
	private ResultInfo<?> sendChPayBilllistPage(BankBill bankBill, Page page) {
		String url = httpUrl + "finance/bankbill/sendchpaybilllistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BankBill>>> TypeRef = new TypeReference<ResultInfo<List<BankBill>>>(){};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bankBill, clientId, clientKey, TypeRef, page);
		    
		    return result;
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		}
		return new ResultInfo<>();
	}

	private ResultInfo saveChPayBillToKindlee(List<BankBill> mapBankBillList) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
		String url=httpUrl + "finance/bankbill/savechpaybilltokindlee.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, mapBankBillList,clientId,clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					return result;
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
}
