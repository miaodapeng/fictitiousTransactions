package com.report.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.common.constants.Contant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.CommonReportMapper;
import com.report.dao.SaleReportMapper;
import com.report.model.export.ExportAssist;
import com.report.model.export.QuoteExportDetail;
import com.report.service.CommonReportService;
import com.report.service.SaleReportService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.vo.TraderCustomerVo;

@Service("saleReportService")
public class SaleReportServiceImpl extends BaseServiceimpl implements SaleReportService {
	public static Logger logger = LoggerFactory.getLogger(SaleReportServiceImpl.class);

	@Autowired
	@Qualifier("saleReportMapper")
	private SaleReportMapper saleReportMapper;
	
	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	
	@Override
	public List<TraderCustomerVo> exportCustomerList(TraderCustomerVo traderCustomerVo, HttpServletRequest request, Page page) {
		List<TraderCustomerVo> list = null;
		try {
			//沟通列表
			if(null != traderCustomerVo.getSearchMsg() && !"".equals(traderCustomerVo.getSearchMsg())){
				Tag tag = new Tag();
				tag.setTagName(traderCustomerVo.getSearchMsg());
				tag.setCompanyId(traderCustomerVo.getCompanyId());
				
				String url = httpUrl + "report/sale/getcommunicaterecordidsbytag.htm";
				final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
			    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tag, clientId, clientKey, TypeRef);
			    List<Integer> communicateList = (List<Integer>) result.getData();
			   
			    if(null != communicateList && communicateList.size() > 0){
			    	List<Integer> traderIds = saleReportMapper.getTraderIdsByCommunicateIds(communicateList);
			    	if(null != traderIds && traderIds.size() > 0){
			    		traderCustomerVo.setCommunicateTraderIds(traderIds);
			    	}
			    }
			}
			//客户数据
			String url = httpUrl + "report/sale/getcustomerlist.htm";
			final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerVo, clientId, clientKey, TypeRef,page);
		    list = (List<TraderCustomerVo>) result.getData();
		    
		    if(null != list && list.size() > 0){
		    	List<Integer> traderIds = new ArrayList<>();
		    	for(TraderCustomerVo customerVo : list){
		    		//省市区、
		    		if(null != customerVo.getAreaId() && customerVo.getAreaId() > 0){
			    		String addressByAreaId = getAddressByAreaId(customerVo.getAreaId());
			    		if(null != addressByAreaId && addressByAreaId.length() > 0){
			    			String[] addressArr = addressByAreaId.split(" ");
			    			if(addressArr.length > 0 && null != addressArr[0]){
			    				customerVo.setProvinceName(addressArr[0]);
			    			}
			    			if(addressArr.length > 1 && null != addressArr[1]){
			    				customerVo.setCityName(addressArr[1]);
			    			}
			    			if(addressArr.length > 2 && null != addressArr[2]){
			    				customerVo.setZoneName(addressArr[2]);
			    			}
			    		}
		    		}
		    		
		    		traderIds.add(customerVo.getTraderId());
		    		
		    		//客户来源、客户等级、销售评级
		    		if(null != customerVo.getCustomerFrom() && customerVo.getCustomerFrom() > 0 ){
		    			SysOptionDefinition sod = getSysOptionDefinitionById(customerVo.getCustomerFrom());
		    			customerVo.setCustomerFromStr(sod.getTitle());
		    		}
		    		
		    		if(null != customerVo.getCustomerLevel() && customerVo.getCustomerLevel() > 0 ){
		    			SysOptionDefinition sod = getSysOptionDefinitionById(customerVo.getCustomerLevel());
		    			customerVo.setCustomerLevelStr(sod.getTitle());
		    		}
		    		
		    		if(null != customerVo.getUserEvaluate() && customerVo.getUserEvaluate() > 0 ){
		    			SysOptionDefinition sod = getSysOptionDefinitionById(customerVo.getUserEvaluate());
		    			customerVo.setUserEvaluateStr(sod.getTitle());
		    		}
		    	}
		    	if(traderIds.size() > 0){
		    		//归属人、归属人部门、
		    		List<TraderCustomerVo> customerOwnerDataList = saleReportMapper.getCustomerOwnerData(traderIds);
		    		if(null != customerOwnerDataList && customerOwnerDataList.size() > 0){
		    			for(TraderCustomerVo vo : customerOwnerDataList){
		    				for(TraderCustomerVo customerVo : list){
		    					if(vo.getTraderId().equals(customerVo.getTraderId())){
		    						customerVo.setOwnerSale(vo.getOwnerSale());
		    						customerVo.setOrgName(vo.getOrgName());
		    					}
		    				}
		    			}
		    		}
		    		
		    		//沟通次数
		    		List<TraderCustomerVo> customerCommunicateDataList = saleReportMapper.getCustomerCommunicateData(traderIds);
		    		if(null != customerCommunicateDataList && customerCommunicateDataList.size() > 0){
		    			for(TraderCustomerVo vo : customerCommunicateDataList){
		    				for(TraderCustomerVo customerVo : list){
		    					if(vo.getTraderId().equals(customerVo.getTraderId())){
		    						customerVo.setCommuncateCount(vo.getCommuncateCount());
		    						customerVo.setLastCommuncateTimeStr(vo.getLastCommuncateTimeStr());
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    page = result.getPage();
		    //分页多次查询
		    Page pageInfo = (Page) result.getPage();
		    if(pageInfo.getPageNo() == 1){
		    	Integer total = pageInfo.getTotalPage();
		    	for (int i = 2; i <= total; i++) {
		    		pageInfo.setPageNo(i);
		    		List<TraderCustomerVo> result_list = this.exportCustomerList(traderCustomerVo, request, pageInfo);
		    		list.addAll(result_list);
		    	}
		    }
		} catch (Exception e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
		return list;
	}


	@Override
	public List<Quoteorder> exportQuoteList(Quoteorder quote, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Quoteorder>>> TypeRef = new TypeReference<ResultInfo<List<Quoteorder>>>() {};
		String url=httpUrl + "report/sale/exportquotelist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quote, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<Quoteorder> list = (List<Quoteorder>) result.getData();
				
//				List<Integer> userIdList = new ArrayList<Integer>();//操作人员ID
				List<Integer> traderIdList = new ArrayList<Integer>();//客户Id
				List<Integer> orgIdList = new ArrayList<Integer>();//销售部门部门ID
				List<CommunicateRecord> communicateRecordList = new ArrayList<CommunicateRecord>();//沟通记录
				CommunicateRecord cr = null;
				for(int i=0;i<list.size();i++){
//					userIdList.add(list.get(i).getUserId());
					traderIdList.add(list.get(i).getTraderId());
					orgIdList.add(list.get(i).getOrgId());
//					list.get(i).setCustomerNatureStr(getSysOptionDefinition(list.get(i).getCustomerNature()).getTitle());
					cr = new CommunicateRecord();
					if(list.get(i).getQuoteorderId()!=null){
						cr.setQuoteorderId(list.get(i).getQuoteorderId());
					}
					/*if(list.get(i).getBussinessChanceId()!=null){
						cr.setBussinessChanceId(list.get(i).getBussinessChanceId());
					}*/
					communicateRecordList.add(cr);
				}
				//------------------------------------------------------------------------------------------
				//除去重复的操作人员
//				userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
				//查询操作人员
//				List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
				//------------------------------------------------------------------------------------------
				//除去重复客户ID
				traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
				//根据客户ID查询客户归属销售信息
				List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
				//------------------------------------------------------------------------------------------
				//除去重复部门ID
				orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
				//根据部门ID查询部门信息
				List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
				//------------------------------------------------------------------------------------------
				//查询沟通记录数
				List<ExportAssist> communicateCountList = commonReportMapper.getCommunicateRecordCountList(communicateRecordList,null,SysOptionConstant.ID_245);
				
				for(int i=0;i<list.size();i++){
					/*for(int j=0;j<userList.size();j++){
						if(list.get(i).getUserId() == userList.get(j).getUserId()){
							list.get(i).setSalesName(userList.get(j).getUsername());//销售单对应销售人员
						}
					}*/
					for(int j=0;j<traderUserList.size();j++){
						if(list.get(i).getTraderId().equals(traderUserList.get(j).getTraderId())){
							list.get(i).setOptUserName(traderUserList.get(j).getUsername());//客户归属销售人员
						}
					}
					for(int j=0;j<orgList.size();j++){
						if(list.get(i).getOrgId().equals(orgList.get(j).getOrgId())){
							list.get(i).setSalesDeptName(orgList.get(j).getOrgName());
						}
					}
					for(int j=0;j<communicateCountList.size();j++){
						/*if(list.get(i).getBussinessChanceId().equals(communicateCountList.get(j).getRelatedId())){
							list.get(i).setCommunicateNum((list.get(i).getCommunicateNum()==null?0:list.get(i).getCommunicateNum()) + (communicateCountList.get(j).getCommunicateCount()==null?0:communicateCountList.get(j).getCommunicateCount()));
						}*/
						if(list.get(i).getQuoteorderId().equals(communicateCountList.get(j).getRelatedId())){
							list.get(i).setCommunicateNum(communicateCountList.get(j).getRelatedData()==null?0:communicateCountList.get(j).getRelatedData());
						}
					}
				}
				
				//分页多次查询
				Page pageInfo = (Page) result.getPage();
				if(pageInfo.getPageNo() == 1){
					Integer total = pageInfo.getTotalPage();
					for (int i = 2; i <= total; i++) {
						pageInfo.setPageNo(i);
						List<Quoteorder> result_list = this.exportQuoteList(quote, pageInfo);
						list.addAll(result_list);
					}
				}
				return list;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<QuoteExportDetail> exportQuoteDetailList(Quoteorder quote, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<QuoteExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<QuoteExportDetail>>>() {};
		String url=httpUrl + "report/sale/exportquotedetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quote, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<QuoteExportDetail> list = (List<QuoteExportDetail>) result.getData();
				
//				List<Integer> userIdList = new ArrayList<Integer>();//操作人员ID
				List<Integer> traderIdList = new ArrayList<Integer>();//客户Id
				List<Integer> orgIdList = new ArrayList<Integer>();//销售部门部门ID
				List<CommunicateRecord> communicateRecordList = new ArrayList<CommunicateRecord>();//沟通记录
				CommunicateRecord cr = null;
				for(int i=0;i<list.size();i++){
//					userIdList.add(list.get(i).getUserId());
					traderIdList.add(list.get(i).getTraderId());
					orgIdList.add(list.get(i).getOrgId());
//					list.get(i).setCustomerNatureStr(getSysOptionDefinition(list.get(i).getCustomerNature()).getTitle());
					cr = new CommunicateRecord();
					if(list.get(i).getQuoteorderId()!=null){
						cr.setQuoteorderId(list.get(i).getQuoteorderId());
					}
					if(list.get(i).getBussinessChanceId()!=null){
						cr.setBussinessChanceId(list.get(i).getBussinessChanceId());
					}
					communicateRecordList.add(cr);
				}
				//------------------------------------------------------------------------------------------
				/*//除去重复的操作人员
				userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
				//查询操作人员
				List<User> userList = commonReportMapper.getUserByUserIds(userIdList);*/
				//------------------------------------------------------------------------------------------
				//除去重复客户ID
				traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
				//根据客户ID查询客户归属销售信息
				List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
				//------------------------------------------------------------------------------------------
				//除去重复部门ID
				orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
				//根据部门ID查询部门信息
				List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
				//------------------------------------------------------------------------------------------
				//查询沟通记录
				List<ExportAssist> communicateCountList = commonReportMapper.getCommunicateRecordCountList(communicateRecordList,SysOptionConstant.ID_244,SysOptionConstant.ID_245);
				//------------------------------------------------------------------------------------------
				//付款时间（字典库）
				List<SysOptionDefinition> purchasingTimeList = getSysOptionDefinitionList(SysOptionConstant.ID_410);
				
				for(int i=0;i<list.size();i++){
					/*for(int j=0;j<userList.size();j++){
						if(list.get(i).getUserId() == userList.get(j).getUserId()){
							list.get(i).setSalesName(userList.get(j).getUsername());//销售单对应销售人员
						}
					}*/
					for(int j=0;j<traderUserList.size();j++){
						if(list.get(i).getTraderId().equals(traderUserList.get(j).getTraderId())){
							System.out.println(list.get(i).getTraderId() +"--"+ traderUserList.get(j).getTraderId());
							list.get(i).setOptUserName(traderUserList.get(j).getUsername());//客户归属销售人员
						}
					}
					for(int j=0;j<orgList.size();j++){
						if(list.get(i).getOrgId().equals(orgList.get(j).getOrgId())){
							list.get(i).setSalesDeptName(orgList.get(j).getOrgName());
						}
					}
					for(int j=0;j<communicateCountList.size();j++){
						if(list.get(i).getBussinessChanceId().equals(communicateCountList.get(j).getRelatedId())){
							list.get(i).setCommunicateNum((list.get(i).getCommunicateNum()==null?0:list.get(i).getCommunicateNum()) + (communicateCountList.get(j).getRelatedData()==null?0:communicateCountList.get(j).getRelatedData()));
						}
						if(list.get(i).getQuoteorderId().equals(communicateCountList.get(j).getRelatedId())){
							list.get(i).setCommunicateNum((list.get(i).getCommunicateNum()==null?0:list.get(i).getCommunicateNum()) + (communicateCountList.get(j).getRelatedData()==null?0:communicateCountList.get(j).getRelatedData()));
						}
					}
					for(int j=0;j<purchasingTimeList.size();j++){
						if(list.get(i).getPurchasingTime().equals(purchasingTimeList.get(j).getSysOptionDefinitionId())){
							list.get(i).setPurchasingTimeStr(purchasingTimeList.get(j).getTitle());
						}
					}
				}
				
				//分页多次查询
				Page pageInfo = (Page) result.getPage();
				if(pageInfo.getPageNo() == 1){
					Integer total = pageInfo.getTotalPage();
					for (int i = 2; i <= total; i++) {
						pageInfo.setPageNo(i);
						List<QuoteExportDetail> result_list = this.exportQuoteDetailList(quote, pageInfo);
						list.addAll(result_list);
					}
				}
				return list;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<Saleorder> exportSaleOrderList(Saleorder saleorder, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Saleorder>>> TypeRef = new TypeReference<ResultInfo<List<Saleorder>>>() {};
		String url=httpUrl + "report/sale/exportsaleorderlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<Saleorder> list = (List<Saleorder>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> saleIdList = new ArrayList<>();List<Integer> quoteIdList = new ArrayList<>();List<Integer> businessIdList = new ArrayList<>();
					List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						saleIdList.add(list.get(i).getSaleorderId());
						if(null != list.get(i).getQuoteorderId()){
							quoteIdList.add(list.get(i).getQuoteorderId());
						}
						if(null != list.get(i).getBussinessChanceId()){
							businessIdList.add(list.get(i).getBussinessChanceId());
						}
						
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
					}
					List<CommunicateRecord> recordNumList = commonReportMapper.getCommunicateNumList(saleIdList,quoteIdList,businessIdList);
					if(traderIdList.size() > 0){
						//操作人员
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						
						//部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						
						if((null != recordNumList && !recordNumList.isEmpty()) ||(null != traderUserList && !traderUserList.isEmpty()) || (null != orgList && !orgList.isEmpty())){
							int recordNum = 0;
							for(int i=0;i<list.size();i++){
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserName(traderUserList.get(j).getUsername());
									}
								}
								for(int j=0;j<orgList.size();j++){
									if(orgList.get(j).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setSalesDeptName(orgList.get(j).getOrgName());
									}
								}
								for(int j=0;j<recordNumList.size();j++){
									if(recordNumList.get(j).getRelatedId().equals(list.get(i).getSaleorderId())
											|| recordNumList.get(j).getRelatedId().equals(list.get(i).getQuoteorderId())
											|| recordNumList.get(j).getRelatedId().equals(list.get(i).getBussinessChanceId())){
										recordNum = recordNum + recordNumList.get(j).getCommunicateCount();
									}
								}
								list.get(i).setCommunicateNum(recordNum);
								recordNum = 0;
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<Saleorder> result_list = this.exportSaleOrderList(saleorder, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public List<SaleorderGoods> exportSaleOrderDetailList(Saleorder saleorder, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<SaleorderGoods>>>() {};
		String url=httpUrl + "report/sale/exportsaleorderdetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorder, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<SaleorderGoods> list = (List<SaleorderGoods>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> saleIdList = new ArrayList<>();List<Integer> quoteIdList = new ArrayList<>();List<Integer> businessIdList = new ArrayList<>();
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						saleIdList.add(list.get(i).getSaleorder().getSaleorderId());
						if(null != list.get(i).getSaleorder().getQuoteorderId()){
							quoteIdList.add(list.get(i).getSaleorder().getQuoteorderId());
						}
						if(null != list.get(i).getSaleorder().getBussinessChanceId()){
							businessIdList.add(list.get(i).getSaleorder().getBussinessChanceId());
						}
						if(null != list.get(i).getCreatorNm()){
							//将1,2,3转换成list数组
							List<String> asList = java.util.Arrays.asList(list.get(i).getCreatorNm().split(","));
							if(null != asList && asList.size() > 0){
								//去除重复的值
								asList = new ArrayList<String>(new HashSet<String>(asList));
								for(int x=0;x<asList.size();x++){
									userIdList.add(Integer.valueOf(asList.get(x)));
								}
								//将list数组转成1,2,3
								list.get(i).setCreatorNm(org.apache.commons.lang3.StringUtils.join(asList, ","));
							}
						}
						traderIdList.add(list.get(i).getSaleorder().getTraderId());
						orgIdList.add(list.get(i).getSaleorder().getOrgId());
					}
					List<CommunicateRecord> recordNumList = commonReportMapper.getCommunicateNumList(saleIdList,quoteIdList,businessIdList);
					if(traderIdList.size() > 0){
						//除去重复的操作人员
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						//查询操作人员
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						
						//销售单归属人员
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						
						//部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						
						if((null != recordNumList && !recordNumList.isEmpty()) ||(null != traderUserList && !traderUserList.isEmpty()) || (null != userList && !userList.isEmpty()) || (null != orgList && !orgList.isEmpty())){
							int recordNum = 0;String buyCreatorIds = "";
							for(int i=0;i<list.size();i++){
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getSaleorder().getTraderId())){
										list.get(i).getSaleorder().setOptUserName(traderUserList.get(j).getUsername());
									}
								}
								for(int j=0;j<orgList.size();j++){
									if(orgList.get(j).getOrgId().equals(list.get(i).getSaleorder().getOrgId())){
										list.get(i).getSaleorder().setSalesDeptName(orgList.get(j).getOrgName());
									}
								}
								buyCreatorIds = list.get(i).getCreatorNm()==null?"":list.get(i).getCreatorNm();
								list.get(i).setCreatorNm("");
								if(StringUtils.isNotBlank(buyCreatorIds)){
									for(int j=0;j<userList.size();j++){
										if(buyCreatorIds.indexOf(userList.get(j).getUserId()+"") != -1){
											list.get(i).setCreatorNm(userList.get(j).getUsername()+","+list.get(i).getCreatorNm());
										}
									}
								}
								
								for(int j=0;j<recordNumList.size();j++){//沟通次数
									if(recordNumList.get(j).getRelatedId().equals(list.get(i).getSaleorder().getSaleorderId())
											|| recordNumList.get(j).getRelatedId().equals(list.get(i).getSaleorder().getQuoteorderId())
											|| recordNumList.get(j).getRelatedId().equals(list.get(i).getSaleorder().getBussinessChanceId())){
										recordNum = recordNum + recordNumList.get(j).getCommunicateCount();
									}
								}
								list.get(i).getSaleorder().setCommunicateNum(recordNum);
								recordNum = 0;
								//省市
								/*String regionNm = commonReportService.getRegion(list.get(i).getSalesAreaId(), 2).toString();
								if(org.apache.commons.lang.StringUtils.isNotBlank(regionNm)){
									
								}*/
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<SaleorderGoods> result_list = this.exportSaleOrderDetailList(saleorder, pageInfo);
							list.addAll(result_list);
						}
					}
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

}
