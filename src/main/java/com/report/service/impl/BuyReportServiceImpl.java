package com.report.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.report.dao.BuyReportMapper;
import com.report.dao.CommonReportMapper;
import com.report.service.BuyReportService;
import com.vedeng.aftersales.model.vo.AfterSalesVo;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsSafeStock;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.trader.model.vo.TraderSupplierVo;

import net.sf.json.JSONObject;
@Service("buyReportService")
public class BuyReportServiceImpl extends BaseServiceimpl implements BuyReportService {

	public static Logger logger = LoggerFactory.getLogger(BuyReportServiceImpl.class);

	@Autowired
	@Qualifier("buyReportMapper")
	private BuyReportMapper buyReportMapper;
	
	@Autowired
	@Qualifier("commonReportMapper")
	private CommonReportMapper commonReportMapper;
	
	@Override
	public List<GoodsVo> exportBHManageList(GoodsVo goodsVo, HttpServletRequest request, Page page) {
		List<GoodsVo> goodsList = null;
		Map<String, Object> bhManageList = this.getBHManageList(goodsVo, page);
		Page pageInfo = (Page) bhManageList.get("page");
		goodsList = (List<GoodsVo>) bhManageList.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);
			bhManageList = this.getBHManageList(goodsVo, page);
			
			List<GoodsVo> list = (List<GoodsVo>) bhManageList.get("list");
			
			if(null != list && list.size() > 0){
				for(GoodsVo gv : list){
					goodsList.add(gv);
				}
			}
		}
		
		return goodsList;
	}
	
	@Override
	public Map<String, Object> getBHManageList(GoodsVo goodsVo, Page page) {
		String url = httpUrl + "report/buy/getbhmanagelist.htm";
		final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsVo, clientId, clientKey, TypeRef,page);
		    List<GoodsVo> list = (List<GoodsVo>) result.getData();
		    
		    if(null != list && list.size() > 0){
		    	List<Integer> goodsIds = new ArrayList<>();
		    	List<Integer> categoryIds = new ArrayList<>();
		    	for(GoodsVo goods : list){
		    		goodsIds.add(goods.getGoodsId());
		    		if(null != goods.getCategoryId() && goods.getCategoryId() > 0){
		    			categoryIds.add(goods.getCategoryId());
		    		}
		    		
		    		if(null != goods.getGoodsLevel() && goods.getGoodsLevel() > 0){
		    			SysOptionDefinition sysOptionDefinition = getSysOptionDefinitionById(goods.getGoodsLevel());
		    			if(null != sysOptionDefinition){
		    				goods.setGoodsLevelName(sysOptionDefinition.getTitle());
		    			}
		    		}
		    		
		    	}
		    	if(goodsIds.size() > 0){
		    		//安全库存
		    		List<GoodsSafeStock> goodsSafeStock = buyReportMapper.getGoodsSafeStock(goodsIds, goodsVo.getCompanyId());
		    		if(null != goodsSafeStock && goodsSafeStock.size() > 0){
		    			for(GoodsSafeStock stock : goodsSafeStock){
		    				for(GoodsVo goods : list){
		    					if(stock.getGoodsId().equals(goods.getGoodsId())){
		    						goods.setSafeNum(stock.getNum());
		    					}
		    				}
		    			}
		    		}
		    		//采购价
		    		List<GoodsChannelPrice> purchasePrice = buyReportMapper.getPurchasePrice(goodsIds);
		    		if(null != purchasePrice && purchasePrice.size() > 0){
		    			for(GoodsChannelPrice channelPrice : purchasePrice){
		    				for(GoodsVo goods : list){
		    					if(channelPrice.getGoodsId().equals(goods.getGoodsId())){
		    						goods.setPurchasePrice(channelPrice.getPublicPrice());
		    					}
		    				}
		    			}
		    		}
		    		
		    	}
		    	
		    	if(categoryIds .size() > 0){
		    		//归属
		    		List<User> userList = buyReportMapper.getUserByCategory(categoryIds,goodsVo.getCompanyId());
		    		if(null != userList && userList.size() > 0){
		    			for(User u : userList){
		    				for(GoodsVo goods : list){
		    					if(u.getCategoryId().equals(goods.getCategoryId())){
		    						goods.setOwner(u.getOwners());
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    
		    page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}

	@Override
	public List<TraderSupplierVo> exportSupplierList(TraderSupplierVo traderSupplierVo, HttpServletRequest request,
			Page page) {
		List<TraderSupplierVo> supplierList = null;
		
		//沟通列表
		if(null != traderSupplierVo.getSearchMsg() && !"".equals(traderSupplierVo.getSearchMsg())){
			Tag tag = new Tag();
			tag.setTagName(traderSupplierVo.getSearchMsg());
			tag.setCompanyId(traderSupplierVo.getCompanyId());
			
			String url = httpUrl + "report/buy/getcommunicaterecordidsbytag.htm";
			final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
			try {
			    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, tag, clientId, clientKey, TypeRef);
			    List<Integer> list = (List<Integer>) result.getData();
			   
			    if(null != list && list.size() > 0){
			    	List<Integer> traderIds = buyReportMapper.getTraderIdsByCommunicateIds(list);
			    	if(null != traderIds && traderIds.size() > 0){
			    		traderSupplierVo.setCommunicateTraderIds(traderIds);
			    	}
			    }
			}catch (IOException e) {
			    logger.error(Contant.ERROR_MSG, e);
			    throw new RuntimeException();
			}
		}
		
		Map<String, Object> engineerMap = this.getSupplierList(traderSupplierVo, page);
		Page pageInfo = (Page) engineerMap.get("page");
		supplierList = (List<TraderSupplierVo>) engineerMap.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);
			
			engineerMap = this.getSupplierList(traderSupplierVo, page);
			List<TraderSupplierVo> list = (List<TraderSupplierVo>) engineerMap.get("list");
			if(null != list && list.size() > 0){
				supplierList.addAll(list);
			}
		}
		return supplierList;
	}

	@Override
	public Map<String, Object> getSupplierList(TraderSupplierVo traderSupplierVo, Page page) {
		String url = httpUrl + "report/buy/getsupplierlist.htm";
		final TypeReference<ResultInfo<List<TraderSupplierVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderSupplierVo>>>() {};
		try {
		    ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplierVo, clientId, clientKey, TypeRef,page);
		    List<TraderSupplierVo> list = (List<TraderSupplierVo>) result.getData();
		    
		    if(null != list && list.size() > 0){
		    	List<Integer> traderIds = new ArrayList<>();
		    	for(TraderSupplierVo supplierVo : list){
		    		traderIds.add(supplierVo.getTraderId());
		    		//省	市区/县
		    		if(null != supplierVo.getAreaId() && supplierVo.getAreaId() > 0){
		    			String addressByAreaId = getAddressByAreaId(supplierVo.getAreaId());
		    			if(null != addressByAreaId && addressByAreaId.length() > 0){
		    				String[] addressArr = addressByAreaId.split(" ");
		    				if(addressArr.length > 0 && null != addressArr[0]){
		    					supplierVo.setProvinceName(addressArr[0]);
		    				}
		    				if(addressArr.length > 1 && null != addressArr[1]){
		    					supplierVo.setCityName(addressArr[1]);
		    				}
		    				if(addressArr.length > 2 && null != addressArr[2]){
		    					supplierVo.setZoneName(addressArr[2]);
		    				}
		    			}
		    		}
		    		//供应商等级
		    		if(null != supplierVo.getGrade()
		    				&& supplierVo.getGrade() > 0 ){
		    			SysOptionDefinition sod = getSysOptionDefinitionById(supplierVo.getGrade());
		    			supplierVo.setGradeStr(sod.getTitle());
		    		}
		    	}
		    	if(traderIds.size() > 0){
		    		//归属人
		    		List<TraderSupplierVo> supplierOwnerDataList = buyReportMapper.getSupplierOwnerData(traderIds);
		    		if(null != supplierOwnerDataList && supplierOwnerDataList.size() > 0){
		    			for(TraderSupplierVo vo : supplierOwnerDataList){
		    				for(TraderSupplierVo supplierVo : list){
		    					if(vo.getTraderId().equals(supplierVo.getTraderId())){
		    						supplierVo.setOwnerSale(vo.getOwnerSale());
		    					}
		    				}
		    			}
		    		}
		    		
		    		//沟通次数
		    		List<TraderSupplierVo> supplierCommunicateDataList = buyReportMapper.getSupplierCommunicateData(traderIds);
		    		if(null != supplierCommunicateDataList && supplierCommunicateDataList.size() > 0){
		    			for(TraderSupplierVo vo : supplierCommunicateDataList){
		    				for(TraderSupplierVo supplierVo : list){
		    					if(vo.getTraderId().equals(supplierVo.getTraderId())){
		    						supplierVo.setCommuncateCount(vo.getCommuncateCount());
		    					}
		    				}
		    			}
		    		}
		    	}
		    }
		    page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
		    logger.error(Contant.ERROR_MSG, e);
		    throw new RuntimeException();
		}
	}

	@Override
	public List<Integer> getTraderIdListByUserList(List<Integer> userIds, Integer traderType) {
		return buyReportMapper.getTraderIdListByUserList(userIds, traderType);
	}

	@Override
	public List<AfterSalesVo> exportBuyorderAfterSalesList(AfterSalesVo afterSalesVo, Page page) {
		List<AfterSalesVo> afterSalesVoList = null;
		
		Map<String, Object> map = this.getBuyorderAfterSalesList(afterSalesVo, page);
		Page pageInfo = (Page) map.get("page");
		afterSalesVoList = (List<AfterSalesVo>) map.get("list");
		Integer total = pageInfo.getTotalPage();
		for (int i = 2; i <= total; i++) {
			pageInfo.setPageNo(i);
			
			map = this.getBuyorderAfterSalesList(afterSalesVo, page);
			List<AfterSalesVo> list = (List<AfterSalesVo>) map.get("list");
			if(null != list && list.size() > 0){
				afterSalesVoList.addAll(list);
			}
		}
		return afterSalesVoList;
	}

	@Override
	public Map<String, Object> getBuyorderAfterSalesList(AfterSalesVo afterSalesVo, Page page) {
		String url = httpUrl + "report/buy/getbuyorderaftersaleslist.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<AfterSalesVo>>> TypeRef2 = new TypeReference<ResultInfo<List<AfterSalesVo>>>() {
		};
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
}
