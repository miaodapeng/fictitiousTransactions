package com.vedeng.trader.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.RegionMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.RegionService;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderOrderGoods;
import com.vedeng.trader.model.TraderSupplier;
import com.vedeng.trader.model.TraderSupplierSupplyBrand;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.CommunicateService;
import com.vedeng.trader.service.TraderSupplierService;

import net.sf.json.JSONArray;
@Service("traderSupplierService")
public class TraderSupplierServiceImpl extends BaseServiceimpl implements TraderSupplierService {

	public static Logger logger = LoggerFactory.getLogger(TraderSupplierServiceImpl.class);

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RegionMapper regionMapper;
	
	@Autowired
	@Qualifier("rTraderJUserMapper")
	private RTraderJUserMapper rTraderJUserMapper;
	
	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;
	
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;
	
	@Autowired
	@Qualifier("communicateService")
	private CommunicateService communicateService;
	

	/**
	 * <b>Description:</b><br> 获取供应商列表
	 * @param traderSupplier
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 下午1:28:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getTraderSupplierList(TraderSupplierVo traderSupplierVo, Page page,List<User> userList) {
//		if(ObjectUtils.notEmpty(traderSupplierVo.getSearchMsg())){
//			TraderSupplierVo tsv = new TraderSupplierVo();
//			tsv.setCompanyId(traderSupplierVo.getCompanyId());
//			tsv.setSearchMsg(traderSupplierVo.getSearchMsg());
//			List<Integer> list = getTagTraderIdList(tsv);
//			if(list != null && list.size() > 0){
//				Map<String, Object> map = new HashMap<>();
//				map.put("companyId", traderSupplierVo.getCompanyId());
//				map.put("traderType", 2);
//				map.put("comIdList", list);
//				List<Integer> traderIdList = communicateRecordMapper.getTraderIdListByList(map);
//				if(traderIdList != null && traderIdList.size() > 0){
//					traderSupplierVo.setCommunicateTraderIds(traderIdList);
//				}else{
//					List<Integer> traders = new ArrayList<>();
//					traders.add(-1);
//					traderSupplierVo.setCommunicateTraderIds(traders);
//				}
//			}else{
//				List<Integer> traders = new ArrayList<>();
//				traders.add(-1);
//				traderSupplierVo.setCommunicateTraderIds(traders);
//			}
//			
//		}
		
		String url = httpUrl + ErpConst.TRADER_SUPPLIER_PAGE;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderSupplierVo>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderSupplierVo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, traderSupplierVo,clientId,clientKey, TypeRef2, page);
			if(result==null){
				return null;
			}
			List<TraderSupplierVo> traderSupplierList = (List<TraderSupplierVo>) result.getData();
			User user=null;
			for (TraderSupplierVo tsv : traderSupplierList) {
				user=userMapper.getUserByTraderId(tsv.getTraderId(),ErpConst.TWO);
				if(null!=user){
					//归属采购
					tsv.setPersonal(user.getUsername());
//					if(userList !=null && userList.size() > 0){
//						for (User u : userList) {
//							if(user.getUserId().equals(u.getUserId())){
//								tsv.setIsView(ErpConst.ONE);//页面能查看一级编辑
//							}
//						}
//					}
				}
				if(ObjectUtils.notEmpty(tsv.getAreaId())){
					tsv.setTraderSupplierAddress(getAddressByAreaId(tsv.getAreaId()));
				}
				
			}
			page = result.getPage();
			Map<String,Object> map=new HashMap<>();
			map.put("list", traderSupplierList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * <b>Description:</b><br> 获取供应商列表
	 * @param traderSupplier
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月11日 下午1:28:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getSupplierByName(TraderSupplierVo traderSupplierVo, Page page,List<User> userList) {
		String url = httpUrl + ErpConst.TRADER_SUPPLIER_PAGE;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderSupplierVo>>> TypeRef2 = new TypeReference<ResultInfo<List<TraderSupplierVo>>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, traderSupplierVo,clientId,clientKey, TypeRef2, page);
			if(result==null){
				return null;
			}
			List<TraderSupplierVo> traderSupplierList = (List<TraderSupplierVo>) result.getData();
//			User user=null;
			for (TraderSupplierVo tsv : traderSupplierList) {
				//产品负责人----预留
				User user=userMapper.getUserByTraderId(tsv.getTraderId(),ErpConst.TWO);
				if(null!=user){
					tsv.setPersonal(user.getUsername());
//					if(userList !=null && userList.size() > 0){
//						for (User u : userList) {
//							if(user.getUserId().equals(u.getUserId())){
//								tsv.setIsView(ErpConst.ONE);//页面
//							}
//						}
//					}
				}
//				user=userMapper.getUserByTraderId(tsv.getTraderId(),ErpConst.TWO);
//				if(null!=user){
//					tsv.setPersonal(user.getUsername());
//				}
				if(ObjectUtils.notEmpty(tsv.getAreaId())){
					tsv.setTraderSupplierAddress(getAddressByAreaId(tsv.getAreaId()));
				}
				
			}
			page = result.getPage();
			Map<String,Object> map=new HashMap<>();
			map.put("list", traderSupplierList);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * <b>Description:</b><br> 是否置顶
	 * @param id
	 * @param isTop
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResultInfo isStick(Integer id, Integer isTop,User user) {
		TraderSupplier ts=new TraderSupplier();
		ts.setTraderSupplierId(id);
		ts.setIsTop(isTop);
		ts.setUpdater(user.getUserId());
		String url = httpUrl + ErpConst.TRADER_SUPPLIER_TOP;
		return update(ts, url);
	}
	/**
	 * <b>Description:</b><br> 是否禁用
	 * @param id
	 * @param isDisabled
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResultInfo isDisabled(User user, Integer id, Integer isDisabled, String disabledReason) {
		TraderSupplier ts=new TraderSupplier();
		ts.setTraderSupplierId(id);
		ts.setIsEnable(isDisabled);
		ts.setUpdater(user.getUserId());
		ts.setModTime(System.currentTimeMillis());
		if(isDisabled==0){
			ts.setDisableTime(System.currentTimeMillis());
			ts.setDisableReason(disabledReason);
		}else{
			//ts.setDisableTime((long)0);
			//ts.setDisableReason("");
		}
		
		String url = httpUrl + ErpConst.TRADER_SUPPLIER_DISABLED;
		return update(ts, url);
	}
	
	/**
	 * <b>Description:</b><br> 更新
	 * @param ts
	 * @param url
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月17日 上午11:47:29
	 */
	@SuppressWarnings("rawtypes")
	public ResultInfo update(TraderSupplier ts,String url){
		final TypeReference<ResultInfo<TraderSupplier>> TypeRef = new TypeReference<ResultInfo<TraderSupplier>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, ts,clientId,clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public TraderSupplier saveSupplier(Trader trader, HttpServletRequest request, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		//交易者基本信息
		trader.setCompanyId(user.getCompanyId());
		trader.setIsEnable(ErpConst.ONE);
		
		if(Integer.parseInt(request.getParameter("zone")) > 0){
			trader.setAreaId(Integer.parseInt(request.getParameter("zone")));
			trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}else{
			trader.setAreaId(Integer.parseInt(request.getParameter("city")));
			trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}
		
		trader.setAddTime(time);
		trader.setCreator(user.getUserId());
		trader.setModTime(time);
		trader.setUpdater(user.getUserId());
		
		
		//供应商
		TraderSupplier traderSupplier = trader.getTraderSupplier();
		traderSupplier.setIsEnable(ErpConst.ONE);
		traderSupplier.setAddTime(time);
		traderSupplier.setCreator(user.getUserId());
		traderSupplier.setModTime(time);
		traderSupplier.setUpdater(user.getUserId());
		traderSupplier.setHotTelephone(request.getParameter("hotTelephone"));
		traderSupplier.setLogisticsName(request.getParameter("logisticsName"));
		traderSupplier.setServiceTelephone(request.getParameter("serviceTelephone"));
		traderSupplier.setWebsite(request.getParameter("website"));
		//品牌
		if(null != request.getParameterValues("bussinessBrandId")){
			String[] bussinessBrandIds = request.getParameterValues("bussinessBrandId");
			List<TraderSupplierSupplyBrand> supplyBrands = new ArrayList<>();
			for(String brandId : bussinessBrandIds){
				TraderSupplierSupplyBrand traderSupplierSupplyBrand = new TraderSupplierSupplyBrand();
				traderSupplierSupplyBrand.setBrandId(Integer.parseInt(brandId));
				supplyBrands.add(traderSupplierSupplyBrand);
			}
			
			traderSupplier.setTraderSupplierSupplyBrands(supplyBrands);
		}
		
		//标签
		if(null != request.getParameterValues("tagId")){//标签库标签
			String[] tagIds = request.getParameterValues("tagId");
			List<Tag> tagList = new ArrayList<>();
			for(String tagId : tagIds){
				Tag tag = new Tag();
				tag.setTagType(SysOptionConstant.ID_31);
				tag.setTagId(Integer.parseInt(tagId));
				tag.setCompanyId(user.getCompanyId());
				tagList.add(tag);
			}
			
			traderSupplier.setTag(tagList);
		}
		if(null != request.getParameterValues("tagName")){//自定义标签
			String[] tagNames = request.getParameterValues("tagName");
			List<String> tagNameList = new ArrayList<>();
			for(String tagName : tagNames){
				tagNameList.add(tagName);
			}
			
			traderSupplier.setTagName(tagNameList);
		}
		//企业宣传片
		if( null != request.getParameterValues("companyUri")){
			String[] companyUris = request.getParameterValues("companyUri");
			traderSupplier.setCompanyUris(companyUris);
		}
		
		//接口调用
		String url = httpUrl + "tradersupplier/addsupplierinfo.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplier>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplier>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef2);
			TraderSupplier res = (TraderSupplier) result2.getData();
			
			if(null != res){
				//保存归属
				RTraderJUser rTraderJUser = new RTraderJUser();
				rTraderJUser.setTraderId(res.getTraderId());
				rTraderJUser.setUserId(user.getUserId());
				rTraderJUser.setTraderType(ErpConst.TWO);
				
				rTraderJUserMapper.insert(rTraderJUser);
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public TraderSupplierVo getTraderSupplierBaseInfo(TraderSupplier traderSupplier) {
		//接口调用
		String url = httpUrl + "tradersupplier/getsupplierbaseinfo.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplierVo>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplierVo>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplier,clientId,clientKey, TypeRef2);
			TraderSupplierVo res = (TraderSupplierVo) result2.getData();
			
			if(null != res.getTraderCustomerVo()){
				TraderCustomerVo traderCustomerVo = res.getTraderCustomerVo();
				User sale = userMapper.getUserByTraderId(traderCustomerVo.getTraderId(), ErpConst.ONE);
				if(null != sale){
					traderCustomerVo.setOwnerSale(sale.getUsername());
				}
			}
			
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public TraderSupplierVo getTraderSupplierManageInfo(TraderSupplier traderSupplier) {
		//接口调用
		String url = httpUrl + "tradersupplier/getsuppliermanageinfo.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplierVo>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplierVo>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplier,clientId,clientKey, TypeRef2);
			TraderSupplierVo res = (TraderSupplierVo) result2.getData();
			if(null != res){
				User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.TWO);
				if(null != sale){
					res.setOwnerSale(sale.getUsername());
				}
				
				CommunicateRecord communicateRecord = new  CommunicateRecord();
				
				communicateRecord.setTraderId(res.getTraderId());
				communicateRecord.setTraderType(ErpConst.TWO);
				communicateRecord.setCompanyId(traderSupplier.getCompanyId());
				
				CommunicateRecord customerCommunicateCount = communicateRecordMapper.getTraderCommunicateCount(communicateRecord);
				if(null != customerCommunicateCount){
					res.setCommuncateCount(customerCommunicateCount.getCommunicateCount());
					res.setLastCommuncateTime(customerCommunicateCount.getLastCommunicateTime());
				}
				User user = null;
				user = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.TWO);
				if (null != user) {
				    res.setPersonal(user.getUsername());
				}
				/*//供应商
				List<Integer> traderSupplierIds = new ArrayList<>();
				traderSupplierIds.add(res.getTraderSupplierId());
				communicateRecord.setTraderSupplierIds(traderSupplierIds);
				
				//采购订单
				if(null != res.getBuyorderIds() && res.getBuyorderIds().size() > 0){
					communicateRecord.setBuyOrderIds(res.getBuyorderIds());
				}
				//售后
				if(null != res.getServiceOrderIds() && res.getServiceOrderIds().size() > 0){
					communicateRecord.setServiceOrderIds(res.getServiceOrderIds());
				}
				CommunicateRecord customerCommunicateCount = communicateRecordMapper.getCustomerCommunicateCount(communicateRecord);
				*/
				
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public TraderSupplierVo getOrderCoverInfo(TraderOrderGoods traderOrderGoods) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplierVo>> TypeRef = new TypeReference<ResultInfo<TraderSupplierVo>>() {};
		String url = httpUrl + "trader/getordercoverinfo.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, traderOrderGoods, clientId, clientKey, TypeRef);
			TraderSupplierVo res = (TraderSupplierVo) result.getData();
			
			//去重订单覆盖品类，订单覆盖品牌，订单覆盖产品+
			List<String> categoryNameList = new ArrayList<>();
			List<String> brandNameList = new ArrayList<>();
			List<String> goodNameList = new ArrayList<>();
			
			List<TraderOrderGoods> traderOrderGoodsList = res.getTraderOrderGoodsList();
			if(traderOrderGoodsList != null && traderOrderGoodsList.size() > 0){
				for(TraderOrderGoods traderOrderGoods1 : traderOrderGoodsList){
					categoryNameList.add(traderOrderGoods1.getCategoryName());
					brandNameList.add(traderOrderGoods1.getBrandName());
					goodNameList.add(traderOrderGoods1.getGoodsName());
				}
			}
			res.setCategoryNameList(new ArrayList(new HashSet(categoryNameList)));
			res.setBrandNameList(new ArrayList(new HashSet(brandNameList)));
			res.setGoodNameList(new ArrayList(new HashSet(goodNameList)));
			
			return res;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}

	@Override
	public TraderSupplier saveEditManageInfo(TraderSupplier traderSupplier, HttpServletRequest request,
			HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		Trader trader = new Trader();
		trader.setCompanyId(user.getCompanyId());
		
		traderSupplier.setModTime(time);
		traderSupplier.setUpdater(user.getUserId());
		traderSupplier.setTrader(trader);
		
		//标签
		if(null != request.getParameterValues("tagId")){//标签库标签
			String[] tagIds = request.getParameterValues("tagId");
			List<Tag> tagList = new ArrayList<>();
			for(String tagId : tagIds){
				Tag tag = new Tag();
				tag.setTagType(SysOptionConstant.ID_31);
				tag.setTagId(Integer.parseInt(tagId));
				tag.setCompanyId(user.getCompanyId());
				tagList.add(tag);
			}
			
			traderSupplier.setTag(tagList);
		}
		if(null != request.getParameterValues("tagName")){//自定义标签
			String[] tagNames = request.getParameterValues("tagName");
			List<String> tagNameList = new ArrayList<>();
			for(String tagName : tagNames){
				tagNameList.add(tagName);
			}
			
			traderSupplier.setTagName(tagNameList);
		}
		
		//接口调用
		String url = httpUrl + "tradersupplier/saveeditmanageinfo.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplier>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplier>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplier,clientId,clientKey, TypeRef2);
			TraderSupplier res = (TraderSupplier) result2.getData();
			
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public TraderSupplier saveEditBaseInfo(Trader trader, HttpServletRequest request, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		if(Integer.parseInt(request.getParameter("zone")) > 0){
			trader.setAreaId(Integer.parseInt(request.getParameter("zone")));
			trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}else{
			trader.setAreaId(Integer.parseInt(request.getParameter("city")));
			trader.setAreaIds(request.getParameter("province")+","+request.getParameter("city")+","+request.getParameter("zone"));
		}
		
		trader.setCompanyId(user.getCompanyId());
		
		trader.setModTime(time);
		trader.setUpdater(user.getUserId());
		
		TraderSupplier traderSupplier = trader.getTraderSupplier();
		traderSupplier.setModTime(time);
		traderSupplier.setUpdater(user.getUserId());
		traderSupplier.setHotTelephone(request.getParameter("hotTelephone"));
		traderSupplier.setLogisticsName(request.getParameter("logisticsName"));
		traderSupplier.setServiceTelephone(request.getParameter("serviceTelephone"));
		traderSupplier.setWebsite(request.getParameter("website"));

		if(StringUtil.isNotBlank(request.getParameter("brief"))){
			traderSupplier.setBrief(request.getParameter("brief"));
		}
		if(StringUtil.isNotBlank(request.getParameter("comments"))){
			traderSupplier.setComments(request.getParameter("comments"));
		}
		//品牌
		if(null != request.getParameterValues("bussinessBrandId")){
			String[] bussinessBrandIds = request.getParameterValues("bussinessBrandId");
			List<TraderSupplierSupplyBrand> supplyBrands = new ArrayList<>();
			for(String brandId : bussinessBrandIds){
				TraderSupplierSupplyBrand traderSupplierSupplyBrand = new TraderSupplierSupplyBrand();
				traderSupplierSupplyBrand.setBrandId(Integer.parseInt(brandId));
				supplyBrands.add(traderSupplierSupplyBrand);
			}
			
			traderSupplier.setTraderSupplierSupplyBrands(supplyBrands);
		}
		//企业宣传片
		if( null != request.getParameterValues("companyUri")){
			String[] companyUris = request.getParameterValues("companyUri");
			traderSupplier.setCompanyUris(companyUris);
		}
		
		
		//接口调用
		String url = httpUrl + "tradersupplier/saveeditbaseinfo.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplier>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplier>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef2);
			TraderSupplier res = (TraderSupplier) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public List<CommunicateRecord> getCommunicateRecordListPage(CommunicateRecord communicateRecord, Page page) {
		/*List<Integer> traderSupplerIdList = new ArrayList<>();
		if(communicateRecord.getTraderSupplierId() != null
				&& communicateRecord.getTraderSupplierId() > 0){
			traderSupplerIdList.add(communicateRecord.getTraderSupplierId());
		}
		communicateRecord.setTraderSupplierIds(traderSupplerIdList);
		
		//后期调用接口查询询价、报价、订单、售后 沟通记录
		 */		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("communicateRecord", communicateRecord);
		map.put("page", page);
		List<CommunicateRecord> communicateRecordList = communicateRecordMapper.getCommunicateRecordList(map);
		
		//调用接口补充信息（联系人，沟通目的、方式 ，沟通内容（标签））
		String url = httpUrl + "trader/tradercommunicaterecord.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<CommunicateRecord>>> TypeRef2 = new TypeReference<ResultInfo<List<CommunicateRecord>>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecordList,clientId,clientKey, TypeRef2);
			
			List<CommunicateRecord> list = (List<CommunicateRecord>) result2.getData();
			return list;
		} catch (IOException e) {
			return null;
		}
		
	}

	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Boolean saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			HttpSession session) throws Exception {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		communicateRecord.setCompanyId(user.getCompanyId());
		//communicateRecord.setCommunicateType(SysOptionConstant.ID_243);
		//communicateRecord.setRelatedId(communicateRecord.getTraderSupplierId());
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		communicateRecord.setBegintime(DateUtil.convertLong(begin, "yyyy-MM-dd HH:mm:ss"));
		communicateRecord.setEndtime(DateUtil.convertLong(end, "yyyy-MM-dd HH:mm:ss"));
		
		if(request.getParameter("nextDate") != ""){
			communicateRecord.setNextContactDate(request.getParameter("nextDate"));
			communicateRecord.setIsDone(ErpConst.ZERO);
		}
		//communicateRecord.setTraderType(ErpConst.TWO);
		communicateRecord.setAddTime(time);
		communicateRecord.setCreator(user.getUserId());
		communicateRecord.setModTime(time);
		communicateRecord.setUpdater(user.getUserId());
		
		//历史沟通信息处理
		CommunicateRecord old = new CommunicateRecord();
		
		old.setCommunicateType(communicateRecord.getCommunicateType());
		old.setRelatedId(communicateRecord.getRelatedId());
		communicateService.updateCommunicateDone(communicateRecord, session);
		
		communicateRecordMapper.insert(communicateRecord);
		Integer communicateRecordId = communicateRecord.getCommunicateRecordId();
		if(communicateRecordId > 0){
			//标签
			if(null != request.getParameterValues("tagId")){//标签库标签
				String[] tagIds = request.getParameterValues("tagId");
				List<Tag> tagList = new ArrayList<>();
				for(String tagId : tagIds){
					Tag tag = new Tag();
					tag.setTagType(SysOptionConstant.ID_32);
					tag.setTagId(Integer.parseInt(tagId));
					tag.setCompanyId(user.getCompanyId());
					tagList.add(tag);
				}
				
				communicateRecord.setTag(tagList);
			}
			if(null != request.getParameterValues("tagName")){//自定义标签
				String[] tagNames = request.getParameterValues("tagName");
				List<String> tagNameList = new ArrayList<>();
				for(String tagName : tagNames){
					tagNameList.add(tagName);
				}
				
				communicateRecord.setTagName(tagNameList);
			}
			
			//接口调用
			String url = httpUrl + "trader/saveaddcommunicatetag.htm";
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
			try {
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecord,clientId,clientKey, TypeRef2);
				Integer res = (Integer) result2.getCode();
				
				if(res.equals(0)){
					return true;
				}
				return false;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}
	
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public Boolean saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
			HttpSession session) throws Exception {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();

		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		communicateRecord.setBegintime(DateUtil.convertLong(begin, "yyyy-MM-dd HH:mm:ss"));
		communicateRecord.setEndtime(DateUtil.convertLong(end, "yyyy-MM-dd HH:mm:ss"));

		if (request.getParameter("nextDate") != "") {
			communicateRecord.setNextContactDate(request.getParameter("nextDate"));
		}

		communicateRecord.setModTime(time);
		communicateRecord.setUpdater(user.getUserId());
		communicateRecord.setCompanyId(user.getCompanyId());
		Integer succ = 0;
		if(null != communicateRecord.getCoid() && communicateRecord.getCoid() != ""){//呼叫中心编辑沟通记录
			communicateRecord.setCreator(user.getUserId());
			succ = communicateRecordMapper.updateByCoidAUserId(communicateRecord);
		}else{
			
			succ = communicateRecordMapper.update(communicateRecord);
		}
		if (succ > 0) {

			// 标签
			if (null != request.getParameterValues("tagId")) {// 标签库标签
				String[] tagIds = request.getParameterValues("tagId");
				List<Tag> tagList = new ArrayList<>();
				for (String tagId : tagIds) {
					Tag tag = new Tag();
					tag.setTagType(SysOptionConstant.ID_32);
					tag.setTagId(Integer.parseInt(tagId));
					tag.setCompanyId(user.getCompanyId());
					tagList.add(tag);
				}

				communicateRecord.setTag(tagList);
			}
			if (null != request.getParameterValues("tagName")) {// 自定义标签
				String[] tagNames = request.getParameterValues("tagName");
				List<String> tagNameList = new ArrayList<>();
				for (String tagName : tagNames) {
					tagNameList.add(tagName);
				}

				communicateRecord.setTagName(tagNameList);
			}

			// 接口调用
			String url = httpUrl + "trader/saveaddcommunicatetag.htm";

			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
			try {
				ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, communicateRecord, clientId, clientKey, TypeRef2);
				Integer res = (Integer) result2.getCode();

				if (res.equals(0)) {
					return true;
				}
				return false;
			} catch (IOException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public Integer getUserSupplierNum(Integer userId) {
		RTraderJUser rTraderJUser = new RTraderJUser();
		rTraderJUser.setUserId(userId);
		rTraderJUser.setTraderType(ErpConst.TWO);
		List<RTraderJUser> userTrader = rTraderJUserMapper.getUserTrader(rTraderJUser);
		
		return userTrader.size();
	}

	@Override
	public Map<String, Object> getUserTraderByTraderNameListPage(RTraderJUser rTraderJUser, Page page) {
		List<RTraderJUser> rTraderJUserList = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		try {
			
			List<TraderSupplier> traderList = null;
			Trader trader = new Trader();
			trader.setCompanyId(rTraderJUser.getCompanyId());
			trader.setTraderName(rTraderJUser.getTraderName());
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<TraderSupplier>>> TypeRef = new TypeReference<ResultInfo<List<TraderSupplier>>>() {};
			String url=httpUrl + "tradersupplier/getusersupplierbytradernamelistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, trader,clientId,clientKey, TypeRef,page);
			traderList = (List<TraderSupplier>) result.getData();
			page = result.getPage();
			
			if(traderList.size() > 0){
				for(TraderSupplier t : traderList){
					RTraderJUser traderJUser = new RTraderJUser();
					traderJUser.setTraderId(t.getTraderId());
					traderJUser.setTraderName(t.getTrader().getTraderName());
					traderJUser.setChangeTimes(t.getOrderTimes());
					if(null != t.getSysOptionDefinition() 
							&& null != t.getSysOptionDefinition().getTitle()){
						traderJUser.setLevel(t.getSysOptionDefinition().getTitle());
					}
					
					String region = (String) regionService.getRegion(t.getTrader().getAreaId(), 2);
					if(null != region){
						traderJUser.setAreaStr(region);
					}
					
					User sale = userMapper.getUserByTraderId(t.getTraderId(), ErpConst.TWO);
					if(null != sale){
						traderJUser.setOwnerUser(sale.getUsername());
					}
					
					rTraderJUserList.add(traderJUser);
				}
			}
			
			map.put("list", rTraderJUserList);map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public Boolean assignSingleSupplier(Integer traderId, Integer single_to_user) {
		RTraderJUser rTraderJUser = new RTraderJUser();
		rTraderJUser.setTraderType(ErpConst.TWO);
		rTraderJUser.setTraderId(traderId);
		rTraderJUserMapper.deleteRTraderJUser(rTraderJUser);
		
		RTraderJUser traderJUser = new RTraderJUser();
		traderJUser.setTraderType(ErpConst.TWO);
		traderJUser.setTraderId(traderId);
		traderJUser.setUserId(single_to_user);
		int insert = rTraderJUserMapper.insert(traderJUser);
		if(insert > 0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean assignBatchSupplier(Integer from_user, Integer batch_to_user) {
		int update = rTraderJUserMapper.update(from_user, batch_to_user, ErpConst.TWO);
		if(update > 0){
			return true;
		}
		return false;
	}

	@Override
	public TraderSupplierVo getSupplierInfoByTraderSupplier(TraderSupplier traderSupplier) {
		//接口调用
		String url = httpUrl + "tradersupplier/getsupplierinfobytradersupplier.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplierVo>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplierVo>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplier,clientId,clientKey, TypeRef2);
			TraderSupplierVo res = (TraderSupplierVo) result2.getData();
			
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	@Override
	public TraderSupplierVo getTraderSupplierInfo(TraderSupplier ts) {
		//接口调用
		String url = httpUrl + "tradersupplier/gettradersupplierinfo.htm";
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplierVo>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplierVo>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, ts,clientId,clientKey, TypeRef2);
			TraderSupplierVo res = (TraderSupplierVo) result2.getData();
			if(null != res){
				//数据处理(地区)
				if(res.getAreaId() > 0){
					String region = (String) regionService.getRegion(res.getAreaId(), 2);
					res.setTraderSupplierAddress(region);
				}
				User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.TWO);
				if(null != sale){
					res.setOwnerSale(sale.getUsername());
				}
			}
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	@Override
	public TraderAccountPeriodApply getTraderSupplierLastAccountPeriodApply(Integer traderId) {
		TraderAccountPeriodApply accountPeriodApply = new TraderAccountPeriodApply();
		accountPeriodApply.setTraderId(traderId);	
		accountPeriodApply.setTraderType(ErpConst.TWO);
		// 接口调用
		String url = httpUrl + "trader/gettraderlastaccountperiodapply.htm";

		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderAccountPeriodApply>> TypeRef2 = new TypeReference<ResultInfo<TraderAccountPeriodApply>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, accountPeriodApply, clientId, clientKey,
					TypeRef2);
			TraderAccountPeriodApply res = (TraderAccountPeriodApply) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	@Override
	public TraderSupplierVo getTraderSupplierForAccountPeriod(TraderSupplierVo traderSupplierVo) {
		// 接口调用
		String url = httpUrl + "tradersupplier/gettradersupplierforaccountperiod.htm";

		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<TraderSupplierVo>> TypeRef2 = new TypeReference<ResultInfo<TraderSupplierVo>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplierVo, clientId, clientKey,
					TypeRef2);
			TraderSupplierVo res = (TraderSupplierVo) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}
	@Override
	public List<TraderSupplierVo> getMainSupplyListByGoodsId(Integer goodsId) {
		List<TraderSupplierVo> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<TraderSupplierVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderSupplierVo>>>() {};
		String url=httpUrl + "tradersupplier/getmainsupplylistbygoodsid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsId,clientId,clientKey, TypeRef);
			list = (List<TraderSupplierVo>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	
	/**
	 * <b>Description:</b><br> 查询沟通记录的主键
	 * @param traderSupplierVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年1月16日 下午1:41:50
	 */
	@Override
	public List<Integer> getTagTraderIdList(TraderSupplierVo traderSupplierVo) {
		String url = httpUrl + ErpConst.GET_TAG_TRADERLIST;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>)HttpClientUtils.post(url, traderSupplierVo,clientId,clientKey, TypeRef2);
			if(result==null || result.getCode() == -1){
				return null;
			}
			JSONArray jsonArray = JSONArray.fromObject(result.getData());
			List<Integer> list = (List<Integer>) JSONArray.toCollection(jsonArray, Integer.class);			
			return list;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public Map<String, Object> getBusinessListPage(BuyorderGoodsVo buyorderGoodsVo, Page page) {
		// 调用接口
		String url = httpUrl + "tradersupplier/getbusinesslistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BuyorderGoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<BuyorderGoodsVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, buyorderGoodsVo, clientId, clientKey,
					TypeRef, page);
			if (result == null || result.getCode() == -1) {
				return null;
			}
			List<BuyorderGoodsVo> list = (List<BuyorderGoodsVo>) result.getData();
			page = result.getPage();
			Map<String, Object> map = new HashMap<>();
			map.put("list", list);
			map.put("page", page);
			return map;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}


	
}
