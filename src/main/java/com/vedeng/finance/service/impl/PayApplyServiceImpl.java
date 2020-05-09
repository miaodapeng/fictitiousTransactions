package com.vedeng.finance.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.common.constants.Contant;
import com.vedeng.common.util.DateUtil;
import com.vedeng.finance.dao.PayApplyMapper;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.finance.model.BankBill;
import com.vedeng.finance.model.PayApply;
import com.vedeng.finance.model.PayApplyDetail;
import com.vedeng.finance.service.PayApplyService;

import net.sf.json.JSONObject;


@Service("payApplyService")
public class PayApplyServiceImpl extends BaseServiceimpl implements PayApplyService{
	public static Logger logger = LoggerFactory.getLogger(PayApplyServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	// add by Tomcat.Hui 2019/9/16 13:18 .Desc: VDERP-1215 付款申请增加批量操作功能. start
	@Autowired
	@Qualifier("payApplyMapper")
	private PayApplyMapper payApplyMapper;
	// add by Tomcat.Hui 2019/9/16 13:18 .Desc: VDERP-1215 付款申请增加批量操作功能. end


	@Override
	public List<PayApply> getPayApplyList(PayApply payApply) {
		List<PayApply> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<PayApply>>> TypeRef = new TypeReference<ResultInfo<List<PayApply>>>() {};
		String url=httpUrl + "finance/payapply/getpayapplylist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApply,clientId,clientKey, TypeRef);
			list = (List<PayApply>) result.getData();
			
			// 操作人信息补充
			if (list.size() > 0) {
				List<Integer> userIds = new ArrayList<>();
				for (PayApply b : list) {
					if (b.getCreator() > 0) {
						userIds.add(b.getCreator());
					}
					if (b.getUpdater() > 0) {
						userIds.add(b.getUpdater());
					}
				}

				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);

					for (PayApply b : list) {
						for (User u : userList) {
							if (u.getUserId().equals(b.getCreator())) {
								b.setCreatorName(u.getUsername());
							}
							if (u.getUserId().equals(b.getUpdater())) {
								b.setUpdaterName(u.getUsername());
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo<?> payApplyNoPass(PayApply payApply) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<PayApply>> TypeRef = new TypeReference<ResultInfo<PayApply>>() {};
		String url=httpUrl + "finance/payapply/payapplynopass.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, payApply,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> payApplyPass(PayApply payApply) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<PayApply>> TypeRef = new TypeReference<ResultInfo<PayApply>>() {};
		String url=httpUrl + "finance/payapply/payapplypass.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, payApply,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getPayApplyListPage(HttpServletRequest request, PayApply payApply, Page page) {
		Map<String,Object> map = new HashMap<>();
		List<PayApply> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			String url = httpUrl + "finance/payapply/getpayapplylistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApply,clientId,clientKey, TypeRef,page);
			if(result!=null && result.getCode() == 0){
				Map<String,Object> result_map = (Map<String,Object>)result.getData();
				if(result_map!=null && result_map.size()>0){
					map = new HashMap<String,Object>();
					
					net.sf.json.JSONArray json = null;
					String str = result_map.get("payApplyList").toString();
					json = net.sf.json.JSONArray.fromObject(str);
					List<PayApply> payApplyList = new ArrayList<>();
					for(int i=0;i<json.size();i++){
					    Map<String,Class<?>> classMap = new HashMap<String,Class<?>>();
					    classMap.put("bankBillList", BankBill.class);
					    JSONObject jsonObject = (JSONObject) json.get(i);
					    PayApply payApplys = (PayApply) JSONObject.toBean(jsonObject, PayApply.class,classMap);
					    payApplyList.add(payApplys);
					}
					map.put("payApplyList", payApplyList);
					
					// 申请人信息补充
					if (payApplyList.size() > 0) {
						List<Integer> userIds = new ArrayList<>();
						for (PayApply b : payApplyList) {
							if (b.getCreator() > 0) {
								userIds.add(b.getCreator());
							}
						}

						if (userIds.size() > 0) {
							List<User> userList = userMapper.getUserByUserIds(userIds);

							for (PayApply b : payApplyList) {
								for (User u : userList) {
									if (u.getUserId().equals(b.getCreator())) {
										b.setCreatorName(u.getUsername());
									}
								}
							}
						}
					}
					
					payApply = (PayApply) JSONObject.toBean(JSONObject.fromObject(result_map.get("payApply")), PayApply.class);
					map.put("payApply", payApply);
					
					page = result.getPage();
					map.put("page", page);
					
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}
	public List<PayApply> getPayApplyListPage(PayApply payApply, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("payApply", payApply);
		//付款申请总额
		BigDecimal payApplyTotalAmount = payApplyMapper.getPayApplyTotalAmount(map);
		payApply.setPayApplyTotalAmount(payApplyTotalAmount);

		//付款申请付款总额
		BigDecimal payApplyPayTotalAmount = payApplyMapper.getPayApplyPayTotalAmount(map);
		payApply.setPayApplyPayTotalAmount(payApplyPayTotalAmount);

		map.put("payApply", payApply);

		List<PayApply> payApplyList =  payApplyMapper.getPayApplyListPage(map);
		if(payApply.getSearch() == null){
			if(null != payApplyList){
				for(PayApply pa:payApplyList){
					//如果已制单，去匹配流水
					if(pa.getIsBill()!=0 && pa.getTraderMode().equals(521) && pa.getValidStatus().equals(0)){
						List<BankBill> selectByBankBillId = matchBankBill(pa.getAmount(),pa.getTraderName(),
								DateUtil.convertString(payApply.getSearchPayBegintime() == null ? 0L : payApply.getSearchPayBegintime(), "yyyyMMdd"),
								DateUtil.convertString(payApply.getSearchPayEndtime() == null ? 0L : payApply.getSearchPayEndtime(), "yyyyMMdd"));
						if(null!=selectByBankBillId && selectByBankBillId.size()>0){
							pa.setBankBillList(selectByBankBillId);
						}
					}
				}
			}
		}


		//map.put("payApplyList", JSONArray.fromObject(payApplyList).toString());
		return payApplyList;
	}
	public List<BankBill> matchBankBill (BigDecimal amt,String accName1, String searchBeginTime, String searchEndTime){
		List<BankBill> data = new ArrayList<>();
		String accName = null;
		data = payApplyMapper.getMatchInfo(amt,accName1,searchBeginTime,searchEndTime);
		return data;
	}
	@Override
	public Map<String, Object> getPayApplyListPageNew(HttpServletRequest request, PayApply payApply, Page page) {


		Map<String,Object> map = new HashMap<>();
		List<PayApply> list = null;
		try {
			// 定义反序列化 数据格式
			//final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			//String url = httpUrl + "finance/payapply/getpayapplylistpage.htm";

//			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApply,clientId,clientKey, TypeRef,page);
//			if(result!=null && result.getCode() == 0){
				//Map<String,Object> result_map =;
				//if(result_map!=null && result_map.size()>0){
					map = new HashMap<String,Object>();
//
//					net.sf.json.JSONArray json = null;
//					String str = result_map.get("payApplyList").toString();
//					json = net.sf.json.JSONArray.fromObject(str);
			//payApply.setValidUserName("");
					List<PayApply> payApplyList =getPayApplyListPage(payApply,page);
//					for(int i=0;i<json.size();i++){
//						Map<String,Class<?>> classMap = new HashMap<String,Class<?>>();
//						classMap.put("bankBillList", BankBill.class);
//						JSONObject jsonObject = (JSONObject) json.get(i);
//						PayApply payApplys = (PayApply) JSONObject.toBean(jsonObject, PayApply.class,classMap);
//						payApplyList.add(payApplys);
//					}
					map.put("payApplyList", payApplyList);

					// 申请人信息补充
//					if (payApplyList.size() > 0) {
//						List<Integer> userIds = new ArrayList<>();
//						for (PayApply b : payApplyList) {
//							if (b.getCreator() > 0) {
//								userIds.add(b.getCreator());
//							}
//						}
//
//						if (userIds.size() > 0) {
//							List<User> userList = userMapper.getUserByUserIds(userIds);
//
//							for (PayApply b : payApplyList) {
//								for (User u : userList) {
//									if (u.getUserId().equals(b.getCreator())) {
//										b.setCreatorName(u.getUsername());
//									}
//								}
//							}
//						}
//					}

					//payApply = (PayApply) JSONObject.toBean(JSONObject.fromObject(result_map.get("payApply")), PayApply.class);
					map.put("payApply", payApply);

					//page = result.getPage();
					map.put("page", page);

				//}
			//}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	@Override
	public Map<String, BigDecimal> getPassedByBuyorderGoodsId(Integer buyorderGoodsId) {
		Map<String,BigDecimal> resultMap = new HashMap<>();
		Map<String, BigDecimal> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String, BigDecimal>>> TypeRef = new TypeReference<ResultInfo<Map<String, BigDecimal>>>() {};
			String url = httpUrl + "finance/payapply/getpassedbybuyordergoodsid.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderGoodsId,clientId,clientKey, TypeRef);
			resultMap = (Map<String, BigDecimal>) result.getData();
			if (null != resultMap) {
				map.put("passedNum", resultMap.get("passedNum"));
				map.put("passedAmount", resultMap.get("passedAmount"));
			} else {
				map.put("passedNum", new BigDecimal(0.00));
				map.put("passedAmount", new BigDecimal(0.00));
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	/** @description: getPayApplyInfo.
	 * @notes: VDERP-1215 付款申请增加批量操作功能,直接查询,不再调用dbcenter.
	 * @author: Tomcat.Hui.
	 * @date: 2019/9/16 13:19.
	 * @param payApplyId
	 * @return: com.vedeng.finance.model.PayApply.
	 * @throws: .
	 */
	@Override
	public PayApply getPayApplyInfo(Integer payApplyId) {
		return payApplyMapper.selectByPrimaryKey(payApplyId);
	}

	@Override
	public List<PayApplyDetail> getPayApplyDetailList(Integer payApplyId) {
		List<PayApplyDetail> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<PayApplyDetail>>> TypeRef = new TypeReference<ResultInfo<List<PayApplyDetail>>>() {};
		String url=httpUrl + "finance/payapply/getpayapplydetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, payApplyId,clientId,clientKey, TypeRef);
			list = (List<PayApplyDetail>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public PayApply getPayApplyMaxRecord(PayApply payApply) {
		return payApplyMapper.getPayApplyMaxRecord(payApply);
	}

	@Override
	public int updatePayStutas(PayApply payApply) {
		return payApplyMapper.updatePayStutas(payApply);
	}

	@Override
	public PayApply getPayApplyRecord(PayApply payApply) {
		return payApplyMapper.getPayApplyRecord(payApply);
	}

	/**
	 * @Description: 根据relatedId获取当前最新申请表得申请付款状态
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/2/26
	 */
	@Override
	public int getPayStatusBill(Integer payType, Integer payApplyId) {
		PayApply payApply1 = new PayApply();
		payApply1.setPayType(payType);//采购
		payApply1.setPayApplyId(payApplyId);
		PayApply payApply2 = payApplyMapper.getPayApplyRecord(payApply1);
		if (payApply2 != null) {
			if (payApply2.getPayStatus().equals(1)) {
				return -1;
			}
		}
		return 0;
	}


	/**
	 * @Description: 修改申请付款状态
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/2/26
	 */
	@Override
	public int updatePayStatusBill(Integer payType, Integer payStatus, Integer payApplyId){
		PayApply payApply1 = new PayApply();
		payApply1.setPayType(payType);//采购
		payApply1.setPayStatus(payStatus);//已付款
		payApply1.setPayApplyId(payApplyId);
		int payApply2 = payApplyMapper.updatePayStutas(payApply1);
		return payApply2;
	}

}
