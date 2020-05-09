package com.vedeng.finance.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.common.constants.Contant;
import com.vedeng.common.constant.OrderDataUpdateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.finance.model.BuyorderData;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.CapitalBillDetail;
import com.vedeng.finance.model.SaleorderData;
import com.vedeng.finance.service.CapitalBillService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.UserService;

import net.sf.json.JSONObject;


@Service("capitalBillService")
public class CapitalBillServiceImpl extends BaseServiceimpl implements CapitalBillService{
	public static Logger logger = LoggerFactory.getLogger(CapitalBillServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Override
	public List<CapitalBill> getCapitalBillList(CapitalBill capitalBill) {
		List<CapitalBill> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CapitalBill>>> TypeRef = new TypeReference<ResultInfo<List<CapitalBill>>>() {};
		String url=httpUrl + "finance/capitalbill/getcapitalbilllist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef);
			list = (List<CapitalBill>) result.getData();
			
			// 操作人信息补充
			if (list.size() > 0) {
				List<Integer> userIds = new ArrayList<>();
				for (CapitalBill b : list) {
					if (b.getCreator() > 0) {
						userIds.add(b.getCreator());
					}
					
				}

				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);

					for (CapitalBill b : list) {
						for (User u : userList) {
							if (u.getUserId().equals(b.getCreator())) {
								b.setCreatorName(u.getUsername());
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
	public Map<String, Object> getCapitalBillListPage(HttpServletRequest request, CapitalBill capitalBill, Page page) {
		Map<String,Object> map = new HashMap<>();
		List<CapitalBill> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			String url = httpUrl + "finance/capitalbill/getcapitalbilllistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef,page);
			if(result!=null && result.getCode() == 0){
				Map<String,Object> result_map = (Map<String,Object>)result.getData();
				if(result_map!=null && result_map.size()>0){
					map = new HashMap<String,Object>();
					
					net.sf.json.JSONArray json = null;
					String str = result_map.get("capitalBillList").toString();
					json = net.sf.json.JSONArray.fromObject(str);
					
					List<CapitalBill> capitalBillList = (List<CapitalBill>) json.toCollection(json, CapitalBill.class);
					map.put("capitalBillList", capitalBillList);
					
					// 申请人信息补充
					if (capitalBillList.size() > 0) {
						List<Integer> userIds = new ArrayList<>();
						for (CapitalBill b : capitalBillList) {
							if (b.getCreator() > 0) {
								userIds.add(b.getCreator());
							}
						}

						if (userIds.size() > 0) {
							List<User> userList = userMapper.getUserByUserIds(userIds);

							for (CapitalBill b : capitalBillList) {
								for (User u : userList) {
									if (u.getUserId().equals(b.getCreator())) {
										b.setCreatorName(u.getUsername());
									}
								}
							}
						}
					}
					
					capitalBill = (CapitalBill) JSONObject.toBean(JSONObject.fromObject(result_map.get("capitalBill")), CapitalBill.class);
					map.put("capitalBill", capitalBill);
					
					page = result.getPage();
					map.put("page", page);
				}
			}
			
			/*
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef,page);
			list = (List<CapitalBill>) result.getData();
			
			// 申请人信息补充
			if (list.size() > 0) {
				List<Integer> userIds = new ArrayList<>();
				for (CapitalBill b : list) {
					if (b.getCreator() > 0) {
						userIds.add(b.getCreator());
					}
				}

				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);

					for (CapitalBill b : list) {
						for (User u : userList) {
							if (u.getUserId().equals(b.getCreator())) {
								b.setCreatorName(u.getUsername());
							}
						}
					}
				}
			}
						
			page = result.getPage();
			
			map.put("list", list);
			map.put("page", page);*/
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	@Override
	public ResultInfo<?> saveAddCapitalBill(CapitalBill capitalBill) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
		String url=httpUrl + "finance/capitalbill/saveaddcapitalbill.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					
					Map<String,Object> result_map = (Map<String,Object>) result.getData();
					if(!result_map.isEmpty()){
						if(capitalBill.getCapitalBillDetails().get(0).getOrderType().intValue() == 1){//销售
							//销售订单收款后--发送消息
							if(result_map.get("traderId") != null && result_map.get("orderNo") != null && result_map.get("orderId") != null){
								//根据客户Id查询客户负责人
								List<Integer> userIdList = userMapper.getUserIdListByTraderId(Integer.valueOf(result_map.get("traderId").toString()),ErpConst.ONE);
								Map<String,String> map = new HashMap<>();
								map.put("saleorderNo", result_map.get("orderNo").toString());
								MessageUtil.sendMessage(9, userIdList, map, "./order/saleorder/view.do?saleorderId="+result_map.get("orderId"));//
								
								//同步到网站
								vedengSoapService.messageSyncWeb(2, Integer.parseInt(result_map.get("orderId").toString()), 2);
							}
						}else if(capitalBill.getCapitalBillDetails().get(0).getOrderType().intValue() == 2){//采购
							//采购订单付款后--发送消息
							if(result_map.get("traderId") != null && result_map.get("orderNo") != null && result_map.get("orderId") != null){
								//根据客户Id查询供应商负责人
								List<Integer> userIdList = userMapper.getUserIdListByTraderId(Integer.valueOf(result_map.get("traderId").toString()),ErpConst.TWO);
								Map<String,String> map = new HashMap<>();
								map.put("buyorderNo", result_map.get("orderNo").toString());
								MessageUtil.sendMessage(17, userIdList, map, "./order/buyorder/viewBuyordersh.do?buyorderId="+result_map.get("orderId"));//
							}
						}
					}
				}
			}
			//VDERP-2263   订单售后采购改动通知
			if(result != null && result.getCode() == 0){
				if(capitalBill.getCapitalBillDetail().getOrderType() == 1){
					//销售订单
					updateSaleOrderDataUpdateTime(capitalBill.getCapitalBillDetail().getRelatedId(),null, OrderDataUpdateConstant.SALE_ORDER_PAY);
				}else if(capitalBill.getCapitalBillDetail().getOrderType() == 2){
					//采购订单
					updateBuyOrderDataUpdateTime(capitalBill.getCapitalBillDetail().getRelatedId(),null,OrderDataUpdateConstant.BUY_ORDER_PAY);
				}else if(capitalBill.getCapitalBillDetail().getOrderType() == 3){
					//售后订单
					updateAfterOrderDataUpdateTime(capitalBill.getCapitalBillDetail().getRelatedId(),null,OrderDataUpdateConstant.AFTER_ORDER_PAY);
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getPaymentRecordListPage(HttpServletRequest request, CapitalBill capitalBill, Page page) {
		Map<String,Object> map = new HashMap<>();
//		List<CapitalBill> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			String url = httpUrl + "finance/capitalbill/getpaymentrecordlistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef,page);
			if(result!=null && result.getCode() == 0){
				Map<String,Object> result_map = (Map<String,Object>)result.getData();
				if(result_map!=null && result_map.size()>0){
					map = new HashMap<String,Object>();
					
					net.sf.json.JSONArray json = null;
					String str = result_map.get("capitalBillList").toString();
					json = net.sf.json.JSONArray.fromObject(str);
					
					List<CapitalBill> capitalBillList = (List<CapitalBill>) json.toCollection(json, CapitalBill.class);
					map.put("capitalBillList", capitalBillList);
					
					// 申请人信息补充
					if (capitalBillList.size() > 0) {
						List<Integer> userIds = new ArrayList<>();
						for (CapitalBill b : capitalBillList) {
							if (b.getCreator() > 0) {
								userIds.add(b.getCreator());
							}
						}

						if (userIds.size() > 0) {
							List<User> userList = userMapper.getUserByUserIds(userIds);

							for (CapitalBill b : capitalBillList) {
								for (User u : userList) {
									if (u.getUserId().equals(b.getCreator())) {
										b.setCreatorName(u.getUsername());
									}
								}
							}
						}
					}
					
					capitalBill = (CapitalBill) JSONObject.toBean(JSONObject.fromObject(result_map.get("capitalBill")), CapitalBill.class);
					map.put("capitalBill", capitalBill);
					
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

	@Override
	public Map<String, Object> getCollectionRecordListPage(HttpServletRequest request, CapitalBill capitalBill, Page page) {
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String,Object>>> TypeRef = new TypeReference<ResultInfo<Map<String,Object>>>() {};
			String url = httpUrl + "finance/capitalbill/getcollectionrecordlistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef,page);
			if(result!=null && result.getCode() == 0){
				Map<String,Object> result_map = (Map<String,Object>)result.getData();
				if(result_map!=null && result_map.size()>0){
					map = new HashMap<String,Object>();
					
					net.sf.json.JSONArray json = null;
					String str = result_map.get("capitalBillList").toString();
					json = net.sf.json.JSONArray.fromObject(str);
					
					List<CapitalBill> capitalBillList = (List<CapitalBill>) json.toCollection(json, CapitalBill.class);
					map.put("capitalBillList", capitalBillList);
					
					// 申请人信息补充
					if (capitalBillList.size() > 0) {
						List<Integer> userIds = new ArrayList<>();
						for (CapitalBill b : capitalBillList) {
							if (b.getCreator() > 0) {
								userIds.add(b.getCreator());
							}
						}

						if (userIds.size() > 0) {
							List<User> userList = userMapper.getUserByUserIds(userIds);

							for (CapitalBill b : capitalBillList) {
								for (User u : userList) {
									if (u.getUserId().equals(b.getCreator())) {
										b.setCreatorName(u.getUsername());
									}
								}
							}
						}
					}
					
					capitalBill = (CapitalBill) JSONObject.toBean(JSONObject.fromObject(result_map.get("capitalBill")), CapitalBill.class);
					map.put("capitalBill", capitalBill);
					
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

	@Override
	public List<SaleorderData> getCapitalListBySaleorderId(List<Integer> saleorderIdList) {
		List<SaleorderData> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<SaleorderData>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderData>>>() {};
			String url = httpUrl + "finance/capitalbill/getcapitallistbysaleorderid.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderIdList,clientId,clientKey, TypeRef);
			if(result!=null){
				list = (List<SaleorderData>) result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return list;
	}
	
	@Override
	public List<BuyorderData> getCapitalListByBuyorderId(List<Integer> buyorderIdList) {
		List<BuyorderData> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<BuyorderData>>> TypeRef = new TypeReference<ResultInfo<List<BuyorderData>>>() {};
			String url = httpUrl + "finance/capitalbill/getcapitallistbybuyorderid.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderIdList,clientId,clientKey, TypeRef);
			if(result!=null){
				list = (List<BuyorderData>) result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return list;
	}

	@Override
	public Saleorder getSaleorderCapitalById(Integer saleorderId) {
	    	String url = httpUrl + "finance/capitalbill/getsaleordercapitalbyid.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Saleorder>> TypeRef = new TypeReference<ResultInfo<Saleorder>>() {
		};
		ResultInfo<?> result;
		Saleorder saleorder = new Saleorder();
		try {
		    result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderId, clientId, clientKey, TypeRef);
		    saleorder = (Saleorder) result.getData();
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		}
		return saleorder;
	}

	//退给客户
	@Override
	public ResultInfo<?> saveRefundCapitalBill(CapitalBill capitalBill) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesDetailVo>> TypeRef = new TypeReference<ResultInfo<AfterSalesDetailVo>>() {};
		String url=httpUrl + "finance/capitalbill/saverefundcapitalbill.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef);
			if(result!=null){
				if(result.getCode() == 0 && result.getData() != null){
					//销售订单退款给客户后--发送消息
					AfterSalesDetailVo new_afterDetail = (AfterSalesDetailVo)result.getData();
					//销售--退货--退款
					if(new_afterDetail.getSubjectType().intValue() == 535 && (new_afterDetail.getAfterType().intValue() == 543 || new_afterDetail.getAfterType().intValue() == 539)){
						if(new_afterDetail.getTraderId() != null && new_afterDetail.getAfterSalesNo() != null && new_afterDetail.getAfterSalesId() != null){
							//根据客户Id查询客户负责人
							List<Integer> userIdList = userMapper.getUserIdListByTraderId(new_afterDetail.getTraderId(),ErpConst.ONE);
							Map<String,String> map = new HashMap<>();
							map.put("afterorderNo", new_afterDetail.getAfterSalesNo());
							MessageUtil.sendMessage(41, userIdList, map, "./order/saleorder/viewAfterSalesDetail.do?afterSalesId="+new_afterDetail.getAfterSalesId());//
						}
					}
				}
				return result;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>(-1,"操作异常");
		}
		return new ResultInfo<>();
	}

	//付款申请添加流水记录
	@Override
	public ResultInfo<?> savePayCapitalBill(CapitalBill capitalBill) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/capitalbill/savepaycapitalbill.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill,clientId,clientKey, TypeRef);
			if(result!=null){
				return result;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>(-1,"操作异常");
		}
		return new ResultInfo<>();
	}
	//添加流水记录
	@Override
	public ResultInfo<?> saveCapitalBill(CapitalBill capitalBill) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url = httpUrl + "finance/capitalbill/savecapitalbill.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, capitalBill, clientId, clientKey, TypeRef);
			if (result != null) {
				return result;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>(-1, "操作异常");
		}
		return new ResultInfo<>();
	}

	@Override
	public ResultInfo<?> getCollectionRecordInfoAjax(List<Integer> saleOrderIdList) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url = httpUrl + "finance/capitalbill/getcollectionrecordinfoajax.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleOrderIdList, clientId, clientKey, TypeRef);
			if (result != null) {
				return result;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>(-1, "操作异常");
		}
		return new ResultInfo<>();
	}

	@Override
	public ResultInfo saveSecondCapitalBill(Saleorder saleorderInfo, CapitalBill capitalBill) {
		 	String payer = capitalBill.getPayer();
			if(payer.equals(ErpConst.TAOBAO)) {
				capitalBill.setTraderMode(520);//520  支付宝
			}else if(payer.equals(ErpConst.WEIXIN)) {
				capitalBill.setTraderMode(522);//522 微信
			}
			capitalBill.setPayer(" ");
			capitalBill.setBankBillId(0);
			capitalBill.setTranFlow(" ");
			
	        // 归属销售
	        User belongUser = new User();
	        if (capitalBill.getCapitalBillDetail().getTraderId() != null) {
	            if (capitalBill.getCapitalBillDetail().getOrderType() == 1) {
	                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 1);// 1客户，2供应商
	                if (belongUser != null && belongUser.getUserId() != null) {
	                    belongUser = userService.getUserById(belongUser.getUserId());
	                }
	            }
	            else if (capitalBill.getCapitalBillDetail().getOrderType() == 2) {
	                belongUser = userService.getUserByTraderId(capitalBill.getCapitalBillDetail().getTraderId(), 2);// 1客户，2供应商
	                if (belongUser != null && belongUser.getUserId() != null) {
	                    belongUser = userService.getUserById(belongUser.getUserId());
	                }
	            }
	        }
	        capitalBill.setCurrencyUnitId(1);
	        capitalBill.setAmount(capitalBill.getAmount().multiply(new BigDecimal(-1)));// 支付宝提现金额作负数
	        capitalBill.setTraderTime(DateUtil.sysTimeMillis());
	        //capitalBill.setTraderType(capitalBill.getTraderType() == null ? 2 : capitalBill.getTraderType());// 默認支出

	        CapitalBillDetail capitalBillDetail = new CapitalBillDetail();
	        capitalBillDetail.setAmount(capitalBill.getAmount());
//	        capitalBillDetail.setBussinessType(capitalBill.getCapitalBillDetail().getBussinessType());
	        capitalBillDetail.setOrderType(capitalBill.getCapitalBillDetail().getOrderType());
	        capitalBillDetail.setRelatedId(capitalBill.getCapitalBillDetail().getRelatedId());
	        capitalBillDetail.setOrderNo(capitalBill.getCapitalBillDetail().getOrderNo());
	        capitalBillDetail.setTraderId(capitalBill.getCapitalBillDetail().getTraderId());
	        capitalBillDetail.setTraderType(2);//交易类型支出
	        capitalBillDetail.setBussinessType(679);//对私提现
	        // capitalBillDetail.setUserId(capitalBill.getCapitalBillDetail().getUserId());
	        if (belongUser != null) {
	            capitalBillDetail.setOrgName(belongUser.getOrgName());
	            capitalBillDetail.setOrgId(belongUser.getOrgId());
	            capitalBillDetail.setUserId(belongUser.getUserId());
	        }
	        // capitalBillDetails.add(capitalBillDetail);
	        capitalBill.setTraderSubject(2);//交易主体  对私
	        capitalBill.setCapitalBillDetail(capitalBillDetail);
	        capitalBill.setTraderType(2);//交易类型支出
			capitalBill.setBdPayStatusFlag(false);
	     return saveCapitalBill(capitalBill);
	}

}
