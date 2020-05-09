package com.vedeng.finance.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.model.AfterSales;
import com.vedeng.aftersales.model.AfterSalesInvoice;
import com.vedeng.aftersales.model.vo.AfterSalesDetailVo;
import com.vedeng.aftersales.model.vo.AfterSalesGoodsVo;
import com.vedeng.aftersales.model.vo.AfterSalesInvoiceVo;
import com.vedeng.aftersales.model.vo.AfterSalesRecordVo;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.finance.model.Invoice;
import com.vedeng.finance.model.InvoiceAfter;
import com.vedeng.finance.service.InvoiceAfterService;
import com.vedeng.goods.dao.RCategoryJUserMapper;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service("invoiceAfterService")
public class InvoiceAfterServiceImpl extends BaseServiceimpl implements InvoiceAfterService{
	public static Logger logger = LoggerFactory.getLogger(InvoiceAfterServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;
	
	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;
	
	@Override
	public Map<String,Object> getFinanceAfterListPage(InvoiceAfter invoiceAfter, Page page) {
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<InvoiceAfter>>> TypeRef = new TypeReference<ResultInfo<List<InvoiceAfter>>>() {};
		String url=httpUrl + "finance/after/getfinanceafterlistpage.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, invoiceAfter,clientId,clientKey, TypeRef ,page);
			if(result!=null && result.getCode()==0){
				map.put("page", result.getPage());
				
				List<InvoiceAfter> invoiceAfterList = (List<InvoiceAfter>)result.getData();
				map.put("invoiceAfterList", invoiceAfterList);
				List<Integer> userIdList = new ArrayList<>();//创建人员
				for(int i=0;i<invoiceAfterList.size();i++){
					userIdList.add(invoiceAfterList.get(i).getCreator());
					userIdList.add(invoiceAfterList.get(i).getServiceUserId());
				}
				userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
				List<User> userList = new ArrayList<>();
				if(userIdList.size() > 0){
					userList = userMapper.getUserByUserIds(userIdList);
					map.put("userList", userList);
				}
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return map;
	}

	@Override
	public AfterSalesVo getFinanceAfterSaleDetail(AfterSalesVo afterSales,HttpSession session) {
		try {
			if(afterSales.getSubjectType() == null || afterSales.getType() == null){
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<AfterSalesVo>> TypeRef2 = new TypeReference<ResultInfo<AfterSalesVo>>() {};
				AfterSales as = new AfterSales();
				as.setAfterSalesId(afterSales.getAfterSalesId());
				String url2 = httpUrl + "aftersales/order/selectbyid.htm";
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url2, as, clientId, clientKey, TypeRef2);
				if(result2 != null && result2.getCode() == 0){
					afterSales = (AfterSalesVo)result2.getData();
					afterSales.setTraderType(afterSales.getSubjectType());
				}
			}
			String url = "";
			//获取售后订单基本信息-售后申请-所属订单
			if(afterSales.getType().intValue() == 539){//销售订单退货
				url = httpUrl + "finance/after/getfinanceaftersalethdetail.htm";
			}else if(afterSales.getType().intValue() == 540){//销售换货
				url = httpUrl + "finance/after/getfinanceaftersalehhdetail.htm";
			}else if(afterSales.getType().intValue() == 541 || afterSales.getType().intValue() == 584){//541销售安调--584销售维修
				url = httpUrl + "finance/after/getfinanceaftersaleatdetail.htm";
			}else if(afterSales.getType().intValue() == 542){//销售退票
				url = httpUrl + "finance/after/getfinanceaftersaletpdetail.htm";
			}else if(afterSales.getType().intValue() == 543){//销售退款
				url = httpUrl + "finance/after/getfinanceaftersaletkdetail.htm";
			}else if(afterSales.getType().intValue() == 546){//采购退货
				url = httpUrl + "finance/after/getfinanceafterbuythdetail.htm";
			}else if(afterSales.getType().intValue() == 547){//采购换货
				url = httpUrl + "finance/after/getfinanceafterbuyhhdetail.htm";
			}else if(afterSales.getType().intValue() == 550 || afterSales.getType().intValue() == 585){//550第三方安调--585第三方维修
				url = httpUrl + "finance/after/getfinanceafterotheratdetail.htm";
			}else if(afterSales.getType().intValue() == 551){//第三方退款
				url = httpUrl + "finance/after/getfinanceafterothertkdetail.htm";
			}
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef);
			if(result != null && result.getCode() == 0){
				
				JSONObject json = JSONObject.fromObject(result.getData());
				AfterSalesVo afterSalesVo = JsonUtils.readValue(json.toString(), AfterSalesVo.class);
				if(afterSalesVo != null){
					User user = (User)session.getAttribute(ErpConst.CURR_USER);
					
					afterSalesVo.setOrgName(getOrgNameByOrgId(afterSalesVo.getOrgId()));
					List<Integer> userIdList = new ArrayList<>();//创建人员
					userIdList.add(afterSalesVo.getCreator());//创建者
					userIdList.add(afterSalesVo.getUserId());//归属销售
					//退货产品信息
					if(afterSalesVo.getAfterSalesGoodsList() != null){
						for (AfterSalesGoodsVo asgv : afterSalesVo.getAfterSalesGoodsList()) {
							//产品负责人
							asgv.setGoodsHeader(rCategoryJUserMapper.getUserByCategoryNm(asgv.getCategoryId(), user.getCompanyId()));
						}
					}
					//退货入库记录
					if(afterSalesVo.getAfterReturnGoodsInStorageList() != null){
						for(int i=0;i<afterSalesVo.getAfterReturnGoodsInStorageList().size();i++){
							userIdList.add(afterSalesVo.getAfterReturnGoodsInStorageList().get(i).getCheckStatusUser());//退货入库操作人
							userIdList.add(afterSalesVo.getAfterReturnGoodsInStorageList().get(i).getCreator());//退货入库操作人
						}
					}
					//退货出库记录
					if(afterSalesVo.getAfterReturnGoodsOutStorageList() != null){
						for(int i=0;i<afterSalesVo.getAfterReturnGoodsOutStorageList().size();i++){
							userIdList.add(afterSalesVo.getAfterReturnGoodsOutStorageList().get(i).getCheckStatusUser());//退货入库操作人
							userIdList.add(afterSalesVo.getAfterReturnGoodsOutStorageList().get(i).getCreator());//退货入库操作人
						}
					}
					//售后过程
					if(afterSalesVo.getAfterSalesRecordVoList() != null){
						for (AfterSalesRecordVo asrv : afterSalesVo.getAfterSalesRecordVoList()) {
							userIdList.add(asrv.getCreator());//售后过程操作人
						}
					}
					//发票记录
					if(afterSalesVo.getAfterOpenInvoiceList() != null){
						for(int i=0;i<afterSalesVo.getAfterOpenInvoiceList().size();i++){
							if(afterSalesVo.getAfterOpenInvoiceList().get(i)!=null){
								userIdList.add(afterSalesVo.getAfterOpenInvoiceList().get(i).getCreator());//发票记录操作人
								userIdList.add(afterSalesVo.getAfterOpenInvoiceList().get(i).getValidUserId());//发票审核人
							}
						}
					}
					//录票记录
					if(afterSalesVo.getAfterInInvoiceList() != null){
						for(int i=0;i<afterSalesVo.getAfterInInvoiceList().size();i++){
							if(afterSalesVo.getAfterInInvoiceList().get(i)!=null){
								userIdList.add(afterSalesVo.getAfterInInvoiceList().get(i).getCreator());//发票记录操作人
								userIdList.add(afterSalesVo.getAfterInInvoiceList().get(i).getValidUserId());//发票审核人
							}
						}
					}
					//已退票记录
					if(afterSalesVo.getAfterReturnInvoiceVoList() != null){
						for(int i=0;i<afterSalesVo.getAfterReturnInvoiceVoList().size();i++){
							if(afterSalesVo.getAfterReturnInvoiceVoList().get(i)!=null){
								userIdList.add(afterSalesVo.getAfterReturnInvoiceVoList().get(i).getCreator());//发票记录操作人
							}
						}
					}
					//交易记录
					if(afterSalesVo.getAfterCapitalBillList() != null){
						for(int i=0;i<afterSalesVo.getAfterCapitalBillList().size();i++){
							userIdList.add(afterSalesVo.getAfterCapitalBillList().get(i).getCreator());//交易记录操作人
						}
					}
					//退票材料
					if(afterSalesVo.getAfterInvoiceAttachmentList() != null){
						for(int i=0;i<afterSalesVo.getAfterInvoiceAttachmentList().size();i++){
							userIdList.add(afterSalesVo.getAfterInvoiceAttachmentList().get(i).getCreator());//退票材料操作人
						}
					}
					//售后过程
					if(afterSalesVo.getAfterSalesRecordVoList() != null){
						for(int i=0;i<afterSalesVo.getAfterSalesRecordVoList().size();i++){
							userIdList.add(afterSalesVo.getAfterSalesRecordVoList().get(i).getCreator());//售后过程操作人
						}
					}
					//合同回传
					if(afterSalesVo.getAfterContractAttachmentList() != null){
						for(int i=0;i<afterSalesVo.getAfterContractAttachmentList().size();i++){
							userIdList.add(afterSalesVo.getAfterContractAttachmentList().get(i).getCreator());//合同回传操作人
						}
					}
					//付款记录
					if(afterSalesVo.getAfterPayApplyList() != null){
						for(int i=0;i<afterSalesVo.getAfterPayApplyList().size();i++){
							userIdList.add(afterSalesVo.getAfterPayApplyList().get(i).getCreator());//付款记录申请人
						}
					}
					//物流信息
					if(afterSalesVo.getExpresseList() != null){
						for(int i=0;i<afterSalesVo.getExpresseList().size();i++){
							userIdList.add(afterSalesVo.getExpresseList().get(i).getCreator());//物流操作人
						}
					}
					userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
					List<User> userList = userMapper.getUserByUserIds(userIdList);
					afterSalesVo.setUserList(userList);
				}
				return afterSalesVo;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

	@Override
	public AfterSalesDetailVo getAfterCapitalBillInfo(AfterSalesDetailVo afterDetailVo) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<AfterSalesDetailVo>> TypeRef = new TypeReference<ResultInfo<AfterSalesDetailVo>>() {};
		String url=httpUrl + "finance/after/getaftercapitalbillinfo.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterDetailVo,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				
				AfterSalesDetailVo afterSalesDetailVo = (AfterSalesDetailVo)result.getData();
				return afterSalesDetailVo;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

	@Override
	public AfterSalesInvoiceVo getAfterReturnInvoiceInfo(AfterSalesInvoiceVo afterInvoiceVo) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/after/getafterreturninvoiceinfo.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterInvoiceVo,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				JSONObject json = JSONObject.fromObject(result.getData());
				AfterSalesInvoiceVo afterSalesInvoiceVo = JsonUtils.readValue(json.toString(), AfterSalesInvoiceVo.class);
				return afterSalesInvoiceVo;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

	@Override
	public ResultInfo<?> saveAfterReturnInvoice(Invoice invoice) {
		// 保存售后退票发票信息
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/after/saveafterreturninvoice.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, invoice,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				return result;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

	@Override
	public AfterSalesGoodsVo getAfterOpenInvoiceInfoAt(AfterSalesGoodsVo afterSalesGoodsVo) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/after/getafteropeninvoiceinfoat.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesGoodsVo,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				JSONObject json = JSONObject.fromObject(result.getData());
				AfterSalesGoodsVo afterGoodsVo = JsonUtils.readValue(json.toString(), AfterSalesGoodsVo.class);
				return afterGoodsVo;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

	//保存财务售后安调开票信息
	@Override
	public ResultInfo<?> saveAfterOpenInvoiceAt(Invoice invoice) {
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/after/saveafteropeninvoiceat.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, invoice,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				return result;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

	//获取售后退票开具发票信息
	@Override
	public Invoice getAfterOpenInvoiceInfoTp(Invoice invoice) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/after/getafteropeninvoiceinfotp.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, invoice,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				JSONObject json = JSONObject.fromObject(result.getData());
				Invoice invoiceResult = JsonUtils.readValue(json.toString(), Invoice.class);
				return invoiceResult;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return null;
	}

	@Override
	public ResultInfo<?> saveAfterOpenInvoiceTp(Invoice invoice) {
		final TypeReference<ResultInfo<AfterSalesDetailVo>> TypeRef = new TypeReference<ResultInfo<AfterSalesDetailVo>>() {};
		String url=httpUrl + "finance/invoice/saveopeninvoice.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, invoice,clientId,clientKey, TypeRef);
			if(result!=null){
				if(result.getCode() == 0 && result.getData() != null){
					AfterSalesDetailVo asdv = (AfterSalesDetailVo)result.getData();
					if(asdv.getTraderId() != null){
						//根据客户Id查询客户负责人
						List<Integer> userIdList = userMapper.getUserIdListByTraderId(asdv.getTraderId(),ErpConst.ONE);
						Map<String,String> map = new HashMap<>();
						map.put("saleorderNo", asdv.getAfterSalesNo());
						//售後開票后，發送消息給客戶負責人，單號為售後單號
						MessageUtil.sendMessage(11, userIdList, map, "./order/saleorder/viewAfterSalesDetail.do?afterSalesId="+asdv.getAfterSalesId());
					}
				}
				return result;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

	//编辑售后发票记录信息 
	@Override
	public ResultInfo<?> editAfterInvoice(AfterSalesInvoice afterSalesInvoice) {
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "finance/after/editafterinvoice.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSalesInvoice, clientId, clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				return result;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

	@Override
	public ResultInfo<?> afterThRefundAmountBalance(AfterSales afterSales) {
		final TypeReference<ResultInfo<AfterSalesDetailVo>> TypeRef = new TypeReference<ResultInfo<AfterSalesDetailVo>>() {};
		String url=httpUrl + "finance/after/afterthrefundamountbalance.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, afterSales, clientId, clientKey, TypeRef);
			if(result!=null){
				if(result.getCode() == 0 && result.getData() != null){
					//审核通过--进行中
					if(afterSales.getStatus() == 2 && afterSales.getAtferSalesStatus() == 1){
						//销售--退货
						if(afterSales.getSubjectType() != null && afterSales.getSubjectType()==535 && afterSales.getType() == 539){
							//发送消息--售后销售订单退货-退款审核通过（钱退到账户余额）
							AfterSalesDetailVo new_afterSales = (AfterSalesDetailVo)result.getData();
							if(new_afterSales != null && new_afterSales.getTraderId() != null){
								//根据客户Id查询客户负责人
								List<Integer> userIdList = userMapper.getUserIdListByTraderId(new_afterSales.getTraderId(),ErpConst.ONE);
								Map<String,String> map = new HashMap<>();
								map.put("afterorderNo", new_afterSales.getAfterSalesNo());
								MessageUtil.sendMessage(40, userIdList, map, "./order/saleorder/viewAfterSalesDetail.do?afterSalesId="+new_afterSales.getAfterSalesId());//
							}
						}
					}
				}
				return result;
			}
		}catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

}
