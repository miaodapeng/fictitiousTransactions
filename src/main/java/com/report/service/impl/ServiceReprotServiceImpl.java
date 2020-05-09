package com.report.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.common.constants.Contant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.CommonReportMapper;
import com.report.model.export.AfterExportDetail;
import com.report.model.export.BussinessChanceExport;
import com.report.model.export.BussinessExportDetail;
import com.report.model.export.GoodsCodeExportDetail;
import com.report.service.CommonReportService;
import com.report.service.ServiceReprotService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.aftersales.model.vo.EngineerVo;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.SaleorderGoodsWarrantyVo;
import com.vedeng.system.model.SysOptionDefinition;

import net.sf.json.JSONObject;
@Service("serviceReprotService")
public class ServiceReprotServiceImpl extends BaseServiceimpl implements ServiceReprotService{
	public static Logger logger = LoggerFactory.getLogger(ServiceReprotServiceImpl.class);

	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;
	
	@Autowired
	@Qualifier("commonReportService")
	private CommonReportService commonReportService;
	
	@Override
	public List<EngineerVo> exportEngineerList(EngineerVo engineerVo, Page page) {
		List<EngineerVo> engineerList = null;
		
		Map<String, Object> engineerMap = this.getEngineerList(engineerVo, page);
		Page pageInfo = (Page) engineerMap.get("page");
		engineerList = (List<EngineerVo>) engineerMap.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);
			
			engineerMap = this.getEngineerList(engineerVo, page);
			List<EngineerVo> list = (List<EngineerVo>) engineerMap.get("list");
			if(null != list && list.size() > 0){
				engineerList.addAll(list);
			}
		}
		return engineerList;
	}

	@Override
	public Map<String, Object> getEngineerList(EngineerVo engineerVo, Page page) {
		String url = httpUrl + "report/service/getengineerlist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<EngineerVo>>> TypeRef2 = new TypeReference<ResultInfo<List<EngineerVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, engineerVo, clientId, clientKey, TypeRef2,
					page);
			List<EngineerVo> list = (List<EngineerVo>) result.getData();
			page = result.getPage();
			
			if(list.size() > 0){
				for(EngineerVo engineer : list){
					if(null != engineer.getAreaId() && engineer.getAreaId() > 0){
			    		String addressByAreaId = getAddressByAreaId(engineer.getAreaId());
			    		if(null != addressByAreaId && addressByAreaId.length() > 0){
			    			engineer.setAreaStr(addressByAreaId);
			    		}
		    		}
				}
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<SaleorderGoodsWarrantyVo> exportGoodsWarrantyList(SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo,
			Page page) {
		List<SaleorderGoodsWarrantyVo> goodsWarrantyVoList = null;
		
		Map<String, Object> goodsWarrantyMap = this.getGoodsWarrantyList(saleorderGoodsWarrantyVo, page);
		Page pageInfo = (Page) goodsWarrantyMap.get("page");
		goodsWarrantyVoList = (List<SaleorderGoodsWarrantyVo>) goodsWarrantyMap.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);
			
			goodsWarrantyMap = this.getGoodsWarrantyList(saleorderGoodsWarrantyVo, page);
			List<SaleorderGoodsWarrantyVo> list = (List<SaleorderGoodsWarrantyVo>) goodsWarrantyMap.get("list");
			if(null != list && list.size() > 0){
				goodsWarrantyVoList.addAll(list);
			}
		}
		return goodsWarrantyVoList;
	}

	@Override
	public Map<String, Object> getGoodsWarrantyList(SaleorderGoodsWarrantyVo saleorderGoodsWarrantyVo, Page page) {
		String url = httpUrl + "report/service/getgoodswarrantylist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>> TypeRef2 = new TypeReference<ResultInfo<List<SaleorderGoodsWarrantyVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, saleorderGoodsWarrantyVo, clientId, clientKey, TypeRef2,
					page);
			List<SaleorderGoodsWarrantyVo> list = (List<SaleorderGoodsWarrantyVo>) result.getData();
			page = result.getPage();
			
			if(null != list && list.size() > 0){
				List<Integer> userIds = new ArrayList<>();
				for(SaleorderGoodsWarrantyVo goodsWarrantyVo : list){
					if(null != goodsWarrantyVo.getCreator() && goodsWarrantyVo.getCreator() > 0){
						userIds.add(goodsWarrantyVo.getCreator());
					}
					//终端地区
					if(null != goodsWarrantyVo.getAreaId() && goodsWarrantyVo.getAreaId() > 0){
		    			String addressByAreaId = getAddressByAreaId(goodsWarrantyVo.getAreaId());
		    			if(null != addressByAreaId && addressByAreaId.length() > 0){
		    				goodsWarrantyVo.setArea(addressByAreaId);
		    			}
		    		}
				}
				//创建者
				if(userIds.size() > 0){
					List<User> userByUserIds = commonReportMapper.getUserByUserIds(userIds);
					if(null != userByUserIds && userByUserIds.size() > 0){
						for(User u : userByUserIds){
							for(SaleorderGoodsWarrantyVo goodsWarrantyVo : list){
								if(u.getUserId().equals(goodsWarrantyVo.getCreator())){
									goodsWarrantyVo.setCreateName(u.getUsername());
								}
							}
						}
					}
				}
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<BussinessChanceExport> exportBussinessChanceList(BussinessChanceVo bussinessChanceVo, Page page) {
		List<BussinessChanceExport> list = null;
		String url = httpUrl + "report/service/getbussinesschancelist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BussinessChanceExport>>> TypeRef2 = new TypeReference<ResultInfo<List<BussinessChanceExport>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bussinessChanceVo, clientId, clientKey, TypeRef2, page);
			list = (List<BussinessChanceExport>) result.getData();
			page = result.getPage();
			
			if(null != list && list.size() > 0){
				int list_size = list.size();
				List<Integer> orgIds = new ArrayList<>();List<Integer> userIds = new ArrayList<>();
				SysOptionDefinition sod = null;Object obj = null;
				for(int i=0;i<list_size;i++){
					if(null != list.get(i).getOrgId() && list.get(i).getOrgId() > 0){
						orgIds.add(list.get(i).getOrgId());
					}
					if(null != list.get(i).getUserId() && list.get(i).getUserId() > 0){
						userIds.add(list.get(i).getUserId());
						userIds.add(list.get(i).getCreator());
					}
					//商机类型
					if(null != list.get(i).getType() && list.get(i).getType() > 0 ){
						sod = getSysOptionDefinitionById(list.get(i).getType());
						list.get(i).setTypeName(sod.getTitle());
					}
					//商机来源
					if(null != list.get(i).getSource() && list.get(i).getSource() > 0 ){
						sod = getSysOptionDefinitionById(list.get(i).getSource());
						list.get(i).setSourceName(sod.getTitle());
					}
					//询价方式
					if(null != list.get(i).getCommunication() && list.get(i).getCommunication() > 0 ){
						sod = getSysOptionDefinitionById(list.get(i).getCommunication());
						list.get(i).setCommunicationName(sod.getTitle());
					}
					//关闭原因
					if(null != list.get(i).getStatusComments() && list.get(i).getStatusComments() > 0 ){
						sod = getSysOptionDefinitionById(list.get(i).getStatusComments());
						list.get(i).setCloseReason(sod.getTitle());
					}
					
					obj = commonReportService.getRegion(list.get(i).getAreaId(),2);
					if(obj!=null){
						list.get(i).setAreaName(obj.toString());
					}
					//响应时长
					if(null != list.get(i).getAssignTime() && list.get(i).getAssignTime() > 0 && null != list.get(i).getFirstViewTime() && list.get(i).getFirstViewTime() > 0){
						Long firstViewTime = list.get(i).getFirstViewTime();
						Long assignTime = list.get(i).getAssignTime();
						
						BigDecimal percent = BigDecimal.valueOf(new BigDecimal(firstViewTime).subtract(new BigDecimal(assignTime))
								.divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(60), 1, BigDecimal.ROUND_HALF_UP).doubleValue());
						list.get(i).setRespondTime(percent.toString());
					}
				}
				
				//销售部门 
				List<Organization> orgList = null;
				if(orgIds.size() > 0){
					orgIds = new ArrayList<Integer>(new HashSet<Integer>(orgIds));
					orgList = commonReportMapper.getOrgNameByOrgIdList(orgIds);
					if(null == orgList){
						orgList = new ArrayList<>();
					}
				}
				//销售人员
				List<User> userByUserIds = null;
				if(userIds.size() > 0){
					userIds = new ArrayList<Integer>(new HashSet<Integer>(userIds));
					userByUserIds = commonReportMapper.getUserByUserIds(userIds);
					if(null == userByUserIds){
						userByUserIds = new ArrayList<>();
					}
				}
				if(orgList != null || userByUserIds != null){
					int orgList_size = orgList.size();int userByUserIds_size = userByUserIds.size();
					for(int i=0;i<list_size;i++){
						for(int j=0;j<orgList_size;j++){
							if(orgList.get(j).getOrgId().equals(list.get(i).getOrgId())){
								list.get(i).setSaleDeptName(orgList.get(j).getOrgName());
							}
						}
						for(int j=0;j<userByUserIds_size;j++){
							if(userByUserIds.get(j).getUserId().equals(list.get(i).getUserId())){
								list.get(i).setSaleUser(userByUserIds.get(j).getUsername());
							}
							if(userByUserIds.get(j).getUserId().equals(list.get(i).getCreator())){
								list.get(i).setCreatorName(userByUserIds.get(j).getUsername());
							}
						}
					}
				}
			}
			
			//分页多次查询
			Page pageInfo = (Page) result.getPage();
			if(pageInfo.getPageNo() == 1){
				Integer total = pageInfo.getTotalPage();
				for (int i = 2; i <= total; i++) {
					pageInfo.setPageNo(i);
					List<BussinessChanceExport> result_list = this.exportBussinessChanceList(bussinessChanceVo, pageInfo);
					list.addAll(result_list);
				}
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return list;
	}

	@Override
	public List<AfterSalesVo> exportAfterSalesList(AfterSalesVo afterSalesVo, Page page) {
		List<AfterSalesVo> afterSalesVoList = null;
		
		Map<String, Object> map = this.getAfterSalesList(afterSalesVo, page);
		Page pageInfo = (Page) map.get("page");
		afterSalesVoList = (List<AfterSalesVo>) map.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);
			
			map = this.getAfterSalesList(afterSalesVo, page);
			List<AfterSalesVo> list = (List<AfterSalesVo>) map.get("list");
			if(null != list && list.size() > 0){
				afterSalesVoList.addAll(list);
			}
		}
		return afterSalesVoList;
	}

	@Override
	public Map<String, Object> getAfterSalesList(AfterSalesVo afterSalesVo, Page page) {
		String url = httpUrl + "report/service/getaftersaleslist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesVo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterSalesVo, clientId, clientKey, TypeRef2,
					page);
			if(result == null || result.getCode() ==-1){
				return null;
			}
			List<AfterSalesVo> asvList = (List<AfterSalesVo>) result.getData();
			
			if(null != asvList && asvList.size() > 0){
				List<Integer> userIds = new ArrayList<>();
				for(AfterSalesVo vo : asvList){
					if(null != vo.getCreator() && vo.getCreator() > 0){
						userIds.add(vo.getCreator());
					}
					if(null != vo.getServiceUserId() && vo.getServiceUserId() > 0){
						userIds.add(vo.getServiceUserId());
					}
				}
				
				if(userIds.size() > 0){
					List<User> userByUserIds = commonReportMapper.getUserByUserIds(userIds);
					if(null != userByUserIds && userByUserIds.size() > 0){
						for(User u : userByUserIds){
							for(AfterSalesVo vo : asvList){
								if(u.getUserId().equals(vo.getCreator())){
									vo.setCreatorName(u.getUsername());
								}
								if(u.getUserId().equals(vo.getServiceUserId())){
									vo.setServiceUserName(u.getUsername());
								}
							}
						}
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("list", asvList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public List<AfterExportDetail> exportAfterGoodsDetailList(AfterExportDetail afterExportDetail, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<AfterExportDetail>>>() {};
		String url=httpUrl + "report/service/exportaftergoodsdetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, afterExportDetail, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<AfterExportDetail> list = (List<AfterExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getServiceUserId());
						
						traderIdList.add(list.get(i).getTraderId());
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getServiceUserId())){
										list.get(i).setServiceUserNm(userList.get(j).getUsername());
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserNm(traderUserList.get(j).getUsername());
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<AfterExportDetail> result_list = this.exportAfterGoodsDetailList(afterExportDetail, pageInfo);
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
	public List<BussinessExportDetail> exportBussinessDetailList(BussinessExportDetail bussinessExportDetail, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BussinessExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<BussinessExportDetail>>>() {};
		String url=httpUrl + "report/service/exportbussinessdetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bussinessExportDetail, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<BussinessExportDetail> list = (List<BussinessExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();List<Integer> orgIdList = new ArrayList<>();
					List<String> regionIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getCreator());userIdList.add(list.get(i).getQuoteCreator());userIdList.add(list.get(i).getSaleCreator());
						traderIdList.add(list.get(i).getTraderId());
						orgIdList.add(list.get(i).getOrgId());
						if(StringUtils.isNotBlank(list.get(i).getAreaIds())){
							regionIdList.addAll(Arrays.asList(list.get(i).getAreaIds().split(",")));
						}
					}
					if(userIdList.size()>0){
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getUserByTraderIdList(traderIdList,ErpConst.ONE);//1客户，2供应商
						if(null == traderUserList){
							traderUserList = new ArrayList<>();
						}
						
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);//根据客户ID查询归属销售
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						
						Map<String,String> regionMap = new HashMap<>();
						regionIdList = new ArrayList<String>(new HashSet<String>(regionIdList));String areaStr = "";
						for(int i=0;i<regionIdList.size();i++){
							areaStr = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+regionIdList.get(i));
							if(StringUtils.isNoneBlank(areaStr)){
								regionMap.put(regionIdList.get(i),JSONObject.fromObject(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+regionIdList.get(i))).getString("regionName"));
							}
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getCreator())){
										list.get(i).setCreatorNm(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getQuoteCreator())){
										list.get(i).setQuoteCreatorNm(userList.get(j).getUsername());
									}
									if(userList.get(j).getUserId().equals(list.get(i).getSaleCreator())){
										list.get(i).setSaleCreatorNm(userList.get(j).getUsername());
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getTraderId())){
										list.get(i).setOptUserNm(traderUserList.get(j).getUsername());
									}
								}
								for(int j=0;j<orgList.size();j++){
									if(orgList.get(j).getOrgId().equals(list.get(i).getOrgId())){
										list.get(i).setOrgNm(orgList.get(j).getOrgName());
									}
								}
								if(StringUtils.isNotBlank(list.get(i).getAreaIds())){
									//省市区
									String[] split = list.get(i).getAreaIds().split(",");
									for(int j=0;j<split.length;j++){
										for(String str:regionMap.keySet()){
											if(str.equals(split[j]+"")){
												switch (j) {
												case 0:
													list.get(i).setProvinceStr(regionMap.get(str));
													break;
												case 1:
													list.get(i).setCityStr(regionMap.get(str));
													break;
												case 2:
													list.get(i).setAreaStr(regionMap.get(str));
													break;
												default:
													break;
												}
											}
										}
									}
								}
								/*if(StringUtils.isNotBlank(list.get(i).getAreaIds())){
									String[] split = list.get(i).getAreaIds().split(",");
									for(int j=0;j<split.length;j++){
										switch (j) {
										case 0:
											list.get(i).setProvinceStr(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+split[j]));
											break;
										case 1:
											list.get(i).setCityStr(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+split[j]));
											break;
										case 2:
											list.get(i).setAreaStr(JedisUtils.get(dbType+ErpConst.KEY_PREFIX_REGION_OBJECT+split[j]));
											break;
										default:
											break;
										}
									}
								}*/
							}
							regionMap.clear();
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<BussinessExportDetail> result_list = this.exportBussinessDetailList(bussinessExportDetail, pageInfo);
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
	public List<GoodsCodeExportDetail> exportGoodsCodeDetailList(GoodsCodeExportDetail gced, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<GoodsCodeExportDetail>>> TypeRef = new TypeReference<ResultInfo<List<GoodsCodeExportDetail>>>() {};
		String url=httpUrl + "report/service/exportgoodscodedetaillist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, gced, clientId, clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0){
				List<GoodsCodeExportDetail> list = (List<GoodsCodeExportDetail>) result.getData();
				if(null != list && !list.isEmpty()){
					List<Integer> userIdList = new ArrayList<>();List<Integer> categoryIdList = new ArrayList<>();
					List<Integer> orgIdList = new ArrayList<>();List<Integer> traderIdList = new ArrayList<>();
					for(int i=0;i<list.size();i++){
						userIdList.add(list.get(i).getBuyUserId());userIdList.add(list.get(i).getSaleUserId());
						orgIdList.add(list.get(i).getBuyOrgId());orgIdList.add(list.get(i).getSaleOrgId());
						categoryIdList.add(list.get(i).getCategoryId());
						traderIdList.add(list.get(i).getCustomerTraderId());
					}
					if(userIdList.size()>0 || categoryIdList.size()>0 || orgIdList.size()>0){
						//采购单创建人、销售创建人等
						userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
						List<User> userList = commonReportMapper.getUserByUserIds(userIdList);
						//采购、销售创建人归属部门
						orgIdList = new ArrayList<Integer>(new HashSet<Integer>(orgIdList));
						List<Organization> orgList = commonReportMapper.getOrgNameByOrgIdList(orgIdList);//根据客户ID查询归属销售
						if(orgList == null){
							orgList = new ArrayList<>();
						}
						//根据产品分类查询产品负责人、及负责人所属部门
						categoryIdList = new ArrayList<Integer>(new HashSet<Integer>(categoryIdList));
						List<User> categoryUserList = commonReportMapper.getCategoryUserAndOrgList(categoryIdList);
						if(categoryUserList == null){
							categoryUserList = new ArrayList<>();
						}
						//根据客户ID查询客户负责人、及负责人所属部门
						traderIdList = new ArrayList<Integer>(new HashSet<Integer>(traderIdList));
						List<User> traderUserList = commonReportMapper.getTraderUserAndOrgList(traderIdList);
						if(traderUserList == null){
							traderUserList = new ArrayList<>();
						}
						if(null != userList && !userList.isEmpty()){
							for(int i=0;i<list.size();i++){
								for(int j=0;j<categoryUserList.size();j++){
									if(categoryUserList.get(j).getCategoryId().equals(list.get(i).getCategoryId())){
										list.get(i).setOptUserNm(categoryUserList.get(j).getUsername());//商品归属人
										list.get(i).setOptUserOrgNm(categoryUserList.get(j).getOrgName());//归属人所属部门
									}
								}
								for(int j=0;j<userList.size();j++){
									if(userList.get(j).getUserId().equals(list.get(i).getBuyUserId())){
										list.get(i).setBuyUserNm(userList.get(j).getUsername());//采购订单创建者
									}
									if(userList.get(j).getUserId().equals(list.get(i).getSaleUserId())){
										list.get(i).setSaleUserNm(userList.get(j).getUsername());//销售订单创建者
									}
								}
								for(int j=0;j<orgList.size();j++){
									if(orgList.get(j).getOrgId().equals(list.get(i).getBuyOrgId())){
										list.get(i).setBuyOrgNm(orgList.get(j).getOrgName());//采购订单创建者归属部门
									}
									if(orgList.get(j).getOrgId().equals(list.get(i).getSaleOrgId())){
										list.get(i).setSaleOrgNm(orgList.get(j).getOrgName());//销售订单创建者归属部门
									}
								}
								for(int j=0;j<traderUserList.size();j++){
									if(traderUserList.get(j).getTraderId().equals(list.get(i).getCustomerTraderId())){
										list.get(i).setTraderUserNm(traderUserList.get(j).getUsername());//客户归属人
										list.get(i).setTraderUserOrgNm(traderUserList.get(j).getOrgName());//客户归属人部门
									}
								}
							}
						}
					}
					//分页多次查询
					Page pageInfo = (Page) result.getPage();
					if(pageInfo.getPageNo() == 1){
						Integer total = pageInfo.getTotalPage();
						for (int i = 2; i <= total; i++) {
							pageInfo.setPageNo(i);
							List<GoodsCodeExportDetail> result_list = this.exportGoodsCodeDetailList(gced, pageInfo);
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
