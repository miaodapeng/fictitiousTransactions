package com.vedeng.goods.service.impl;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.http.HttpClientUtils4Stock;
import com.vedeng.common.model.ResultInfo4Stock;
import com.common.constants.Contant;
import com.vedeng.logistics.model.WarehouseStock;
import com.vedeng.logistics.service.WarehouseStockService;
import com.vedeng.soap.service.VedengSoapService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.ObjectsUtil;
import com.vedeng.goods.dao.GoodsChannelPriceExtendMapper;
import com.vedeng.goods.dao.GoodsChannelPriceMapper;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.goods.dao.GoodsSafeStockMapper;
import com.vedeng.goods.dao.GoodsSettlementPriceMapper;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.goods.dao.RManageCategoryJUserMapper;
import com.vedeng.goods.model.Goods;
import com.vedeng.goods.model.GoodsAttachment;
import com.vedeng.goods.model.GoodsAttribute;
import com.vedeng.goods.model.GoodsChannelPrice;
import com.vedeng.goods.model.GoodsChannelPriceExtend;
import com.vedeng.goods.model.GoodsExtend;
import com.vedeng.goods.model.GoodsFaq;
import com.vedeng.goods.model.GoodsOpt;
import com.vedeng.goods.model.GoodsPackage;
import com.vedeng.goods.model.GoodsRecommend;
import com.vedeng.goods.model.GoodsSafeStock;
import com.vedeng.goods.model.GoodsSettlementPrice;
import com.vedeng.goods.model.GoodsSysOptionAttribute;
import com.vedeng.goods.model.RGoodsJTraderSupplier;
import com.vedeng.goods.model.RManageCategoryJUser;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.goods.service.GoodsService;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.BuyorderVo;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.VerifiesInfo;

@Service("goodsService")
public class GoodsServiceImpl extends BaseServiceimpl implements GoodsService {
	public static Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;
	
	@Autowired
	@Qualifier("rManageCategoryJUserMapper")
	private RManageCategoryJUserMapper rManageCategoryJUserMapper;
	
	@Autowired
	@Qualifier("goodsSafeStockMapper")
	private GoodsSafeStockMapper goodsSafeStockMapper;
	
	@Autowired
	@Qualifier("goodsChannelPriceMapper")
	private GoodsChannelPriceMapper goodsChannelPriceMapper;
	
	@Autowired
	@Qualifier("goodsSettlementPriceMapper")
	private GoodsSettlementPriceMapper goodsSettlementPriceMapper;
	
	@Autowired
	@Qualifier("goodsChannelPriceExtendMapper")
	private GoodsChannelPriceExtendMapper goodsChannelPriceExtendMapper;
	
	@Autowired
	@Qualifier("goodsMapper")
	private GoodsMapper goodsMapper;

	@Autowired
	private WarehouseStockService warehouseStockService;
	
	@Override
	public Map<String, Object> getGoodsListPage(HttpServletRequest request, Goods goods, Page page,HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		if(StringUtils.isNotBlank(request.getParameter("searchBegintimeStr"))){
			goods.setSearchBegintime(DateUtil.convertLong(request.getParameter("searchBegintimeStr") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		
		if(StringUtils.isNotBlank(request.getParameter("searchEndtimeStr"))){
			goods.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		List<Goods> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Goods>>> TypeRef = new TypeReference<ResultInfo<List<Goods>>>() {};
			String url=httpUrl + "goods/getgoodslistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef,page);
			list = (List<Goods>) result.getData();
			page = result.getPage();
			if(null != list && list.size() > 0){
				for(Goods g:list){
					g.setUserList(rCategoryJUserMapper.getUserByCategory(g.getCategoryId(), user.getCompanyId()));
					//g.setUserList(rManageCategoryJUserMapper.getUserByManageCategory(g.getManageCategory(), user.getCompanyId()));
				}
			}
			
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
//			logger.error(Contant.ERROR_MSG, e);
			logger.error("查询商品列表发生异常", e);
		}
		return map;
	}
	
	
	@Override
	public Goods saveAdd(Goods goods, HttpServletRequest request, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		goods.setCompanyId(user.getCompanyId());
		goods.setAddTime(time);
		goods.setCreator(user.getUserId());
		goods.setModTime(time);
		goods.setUpdater(user.getUserId());
		
		
		// 校验注册证名称
		if(null != goods.getRegistrationNumberName() && !"".equals(goods.getRegistrationNumberName())){
			// 注册证名称长度不能大于128
			if(goods.getRegistrationNumberName().length() > 128){
				return null;
			}
		}

		// 校验wiki链接字段
		if(null != goods.getHref() && !"".equals(goods.getHref())){
			// 注册证名称长度不能大于128
			if(goods.getHref().length() > 256){
				return null;
			}
		}
		
		
		
		if(request.getParameter("begintimeStr") != ""){
			goods.setBegintime(DateUtil.convertLong(request.getParameter("begintimeStr"), "yyyy-MM-dd"));
		}
		
		if(request.getParameter("endtimeStr") != ""){
			goods.setEndtime(DateUtil.convertLong(request.getParameter("endtimeStr"), "yyyy-MM-dd"));
		}
		
		//应用科室
		if(null != request.getParameterValues("attributeId")){
			String[] attributeIds = request.getParameterValues("attributeId");
			List<GoodsSysOptionAttribute> goodsSysOptionAttributes = new ArrayList<>();
			for(String attIds : attributeIds){
				GoodsSysOptionAttribute goodsSysOptionAttribute = new GoodsSysOptionAttribute();
				String[] attArr = attIds.split("_");
				
				goodsSysOptionAttribute.setAttributeType(Integer.parseInt(attArr[0]));
				goodsSysOptionAttribute.setAttributeId(Integer.parseInt(attArr[1]));
				
				goodsSysOptionAttributes.add(goodsSysOptionAttribute);
			}
			goods.setGoodsSysOptionAttributes(goodsSysOptionAttributes);
		}
		
		
		List<GoodsAttribute> goodsAttributes = new ArrayList<>();
		//最小分类通用属性（复选）
		if(null != request.getParameterValues("categoryAttributeId")){
			String[] categoryAttributeIds = request.getParameterValues("categoryAttributeId");
			//List<GoodsAttribute> goodsAttributes = new ArrayList<>();
			for(String attIds : categoryAttributeIds){
				GoodsAttribute goodsAttribute = new GoodsAttribute();
				String[] attArr = attIds.split("_");
				
				goodsAttribute.setCategoryAttributeId(Integer.parseInt(attArr[0]));
				goodsAttribute.setCategoryAttrValueId(Integer.parseInt(attArr[1]));
				
				goodsAttributes.add(goodsAttribute);
			}
			goods.setGoodsAttributes(goodsAttributes);
		}
		
		//最小分类通用属性（单选）
		if (null != request.getParameter("attr_id_str")) {
			String[] categoryAttributeIds2 = request.getParameter("attr_id_str").split("_");
			//List<GoodsAttribute> goodsAttributes2 = new ArrayList<>();
			for(String attIds2 : categoryAttributeIds2){
				if (attIds2 == "" || null == request.getParameter("categoryAttributeId_"+attIds2)) continue;
				GoodsAttribute goodsAttribute2 = new GoodsAttribute();
				String[] attArr2 = request.getParameter("categoryAttributeId_"+attIds2).split("_");
				
				goodsAttribute2.setCategoryAttributeId(Integer.parseInt(attArr2[0]));
				goodsAttribute2.setCategoryAttrValueId(Integer.parseInt(attArr2[1]));
				
				goodsAttributes.add(goodsAttribute2);
			}
			
		}
			goods.setGoodsAttributes(goodsAttributes);
		
		List<GoodsAttachment> goodsAttachments = new ArrayList<>();
		//产品图片
		if (null != request.getParameterValues("uri_343")) {
			String[] uri343 = request.getParameterValues("uri_343");
			for(String uri : uri343){
				if (uri == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(343);
				goodsAttachment.setDomain(request.getParameter("domain"));
				goodsAttachment.setUri(uri);
				goodsAttachments.add(goodsAttachment);
			}
		}
		
		//产品检测报告
		if (null != request.getParameterValues("uri_658")) {
			String[] uri658 = request.getParameterValues("uri_658");
			for(String uri : uri658){
				if (uri == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(658);
				goodsAttachment.setDomain(request.getParameter("domain"));
				goodsAttachment.setUri(uri);
				goodsAttachments.add(goodsAttachment);
			}
		}
		
		//产品专利文件
		if (null != request.getParameterValues("uri_659")) {
			String[] uri659 = request.getParameterValues("uri_659");
			for(String uri : uri659){
				if (uri == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(659);
				goodsAttachment.setDomain(request.getParameter("domain"));
				goodsAttachment.setUri(uri);
				goodsAttachments.add(goodsAttachment);
			}
		}
		
		//注册证
		if (null != request.getParameterValues("uri_344")) {
			String[] uri344 = request.getParameterValues("uri_344");
			for (int i = 0; i < uri344.length; i++) {
				if (uri344[i] == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(344);
				goodsAttachment.setDomain(request.getParameter("domain"));
				goodsAttachment.setUri(uri344[i]);
				goodsAttachments.add(goodsAttachment);
			}
		}	
		
		// 备案文件 
		if (null != request.getParameter("uri_680") && request.getParameter("uri_680") != "") 
		{
			GoodsAttachment goodsAttachment = new GoodsAttachment();
			goodsAttachment.setAttachmentType(680);
			goodsAttachment.setDomain(request.getParameter("domain"));
			goodsAttachment.setUri(request.getParameter("uri_680"));
			goodsAttachments.add(goodsAttachment);
		}		
		
		goods.setGoodsAttachments(goodsAttachments);
		
		//非医疗器械，管理类别置空
		if(goods.getStandardCategoryId()==1388){
			goods.setManageCategoryLevel(0);
		}
		//产品类型为试剂，新国标分类置空
		if(goods.getGoodsType()==318){
			goods.setStandardCategoryId(0);
		}
		// 对goods下面的String类型属性进行转义  at bug[3778 在产品名称和物料编码中，输入“A30-000001---”只会显示“A30-000001-” ] by franlin 2018-05-02  TODO 暂时不改成通用模式
		ObjectsUtil.resetStringValueByEscapeType(goods, 1);
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef2 = new TypeReference<ResultInfo<Goods>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "goods/saveadd.htm", goods,clientId,clientKey, TypeRef2);
			Goods res = (Goods) result2.getData();
			
			return res;
		} catch (IOException e) {
			return null;
		}
	}


	@Override
	public ResultInfo<?> isDiscardById(Goods goods) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
		String url=httpUrl + "goods/isdiscardbyid.htm";
		try {
			Long time = DateUtil.sysTimeMillis();
			if (goods.getIsDiscard() == 1) {
				goods.setDiscardTime(time);
			} else {
				goods.setModTime(time);
			}
			result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public Goods getGoodsById(Goods goods) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
		String url=httpUrl + "goods/getgoodsbyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			if(result != null){
				goods = (Goods) result.getData();
//				if(goods != null && goods.getCategoryId() != null && goods.getCategoryId() > 0){
//					List<User> userByCategory = rCategoryJUserMapper.getUserByCategory(goods.getCategoryId(), goods.getCompanyId());
//					if(userByCategory != null && userByCategory.size() > 0){
//						goods.setUserList(userByCategory);
//					}
//				}
			}
			//goods.setUserList(rManageCategoryJUserMapper.getUserByManageCategory(goods.getManageCategory(), goods.getCompanyId()));

		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return goods;
	}


	@Override
	public Goods saveBaseInfo(Goods goods, HttpServletRequest request, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		goods.setModTime(time);
		goods.setUpdater(user.getUserId());	
		
		// 校验注册证名称
		if(null != goods.getRegistrationNumberName() && "".equals(goods.getRegistrationNumberName())){
			if(goods.getRegistrationNumberName().length() > 128){
				return null;
			}
		}
		// 校验wiki链接
		if(null != goods.getHref() && "".equals(goods.getHref())){
			if(goods.getHref().length() > 256){
				return null;
			}
		}
		
		if(StringUtils.isNotBlank(request.getParameter("begintimeStr"))){
			goods.setBegintime(DateUtil.convertLong(request.getParameter("begintimeStr"), "yyyy-MM-dd"));
		} else {
			goods.setBegintime(0l);
		}
		
		if(StringUtils.isNotBlank(request.getParameter("endtimeStr"))){
			goods.setEndtime(DateUtil.convertLong(request.getParameter("endtimeStr"), "yyyy-MM-dd"));
		} else {
			goods.setEndtime(0l);
		}
		
		List<GoodsAttribute> goodsAttributes = new ArrayList<>();
		//最小分类通用属性
		if(null != request.getParameterValues("categoryAttributeId")){
			String[] categoryAttributeIds = request.getParameterValues("categoryAttributeId");
			
			for(String attIds : categoryAttributeIds){
				GoodsAttribute goodsAttribute = new GoodsAttribute();
				String[] attArr = attIds.split("_");
				
				goodsAttribute.setCategoryAttributeId(Integer.parseInt(attArr[0]));
				goodsAttribute.setCategoryAttrValueId(Integer.parseInt(attArr[1]));
				
				goodsAttributes.add(goodsAttribute);
			}
			goods.setGoodsAttributes(goodsAttributes);
		}
		
		//最小分类通用属性（单选）
		if (null != request.getParameter("attr_id_str")) {
			String[] categoryAttributeIds2 = request.getParameter("attr_id_str").split("_");
			//List<GoodsAttribute> goodsAttributes2 = new ArrayList<>();
			for(String attIds2 : categoryAttributeIds2){
				if (attIds2 == "" || null == request.getParameter("categoryAttributeId_"+attIds2)) continue;
				GoodsAttribute goodsAttribute2 = new GoodsAttribute();
				String[] attArr2 = request.getParameter("categoryAttributeId_"+attIds2).split("_");
				
				goodsAttribute2.setCategoryAttributeId(Integer.parseInt(attArr2[0]));
				goodsAttribute2.setCategoryAttrValueId(Integer.parseInt(attArr2[1]));
				
				goodsAttributes.add(goodsAttribute2);
			}
			
		}
		goods.setGoodsAttributes(goodsAttributes);
		
		List<GoodsAttachment> goodsAttachments = new ArrayList<>();
		
		
		//产品图片
		if (null != request.getParameterValues("uri_343")) {
			String[] uri343 = request.getParameterValues("uri_343");
			for(String uri : uri343){
				if (uri == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(343);
				goodsAttachment.setDomain(request.getParameter("domain_343"));
				goodsAttachment.setUri(uri);
				goodsAttachments.add(goodsAttachment);
			}
		}
		
		//产品检测报告
		if (null != request.getParameterValues("uri_658")) {
			String[] uri658 = request.getParameterValues("uri_658");
			for(String uri : uri658){
				if (uri == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(658);
				goodsAttachment.setDomain(request.getParameter("domain_658"));
				goodsAttachment.setUri(uri);
				goodsAttachments.add(goodsAttachment);
			}
		}
		
		//产品专利文件
		if (null != request.getParameterValues("uri_659")) {
			String[] uri659 = request.getParameterValues("uri_659");
			for(String uri : uri659){
				if (uri == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(659);
				goodsAttachment.setDomain(request.getParameter("domain_659"));
				goodsAttachment.setUri(uri);
				goodsAttachments.add(goodsAttachment);
			}
		}
		
		//注册证
		if (null != request.getParameterValues("uri_344")) {
			String[] uri344 = request.getParameterValues("uri_344");
			for (int i = 0; i < uri344.length; i++) {
				if (uri344[i] == "") continue;
				GoodsAttachment goodsAttachment = new GoodsAttachment();
				goodsAttachment.setAttachmentType(344);
				goodsAttachment.setDomain(request.getParameter("domain_344"));
				goodsAttachment.setUri(uri344[i]);
				goodsAttachments.add(goodsAttachment);
			}
		}		
		// 备案文件 
		if (null != request.getParameter("uri_680") && request.getParameter("uri_680") != "") {
			GoodsAttachment goodsAttachment = new GoodsAttachment();
			goodsAttachment.setAttachmentType(680);
			goodsAttachment.setDomain(request.getParameter("domain_680"));
			goodsAttachment.setUri(request.getParameter("uri_680"));
			goodsAttachments.add(goodsAttachment);
		}	
		
		goods.setGoodsAttachments(goodsAttachments);
		
		//应用科室
		if(null != request.getParameterValues("attributeId")){
			String[] attributeIds = request.getParameterValues("attributeId");
			List<GoodsSysOptionAttribute> goodsSysOptionAttributes = new ArrayList<>();
			for(String attIds : attributeIds){
				GoodsSysOptionAttribute goodsSysOptionAttribute = new GoodsSysOptionAttribute();
				String[] attArr = attIds.split("_");
				
				goodsSysOptionAttribute.setAttributeType(Integer.parseInt(attArr[0]));
				goodsSysOptionAttribute.setAttributeId(Integer.parseInt(attArr[1]));
				
				goodsSysOptionAttributes.add(goodsSysOptionAttribute);
			}
			goods.setGoodsSysOptionAttributes(goodsSysOptionAttributes);
		}
		
		
		//产品类型为试剂，新国标分类置空
		if(null != goods.getGoodsType() && goods.getGoodsType() == 318)
		{
			goods.setStandardCategoryId(0);
		}
		
		//非医疗器械，管理类别置空  新国标分类可存在为空
		if(null != goods.getStandardCategoryId() && goods.getStandardCategoryId() == 1388)
		{
			goods.setManageCategoryLevel(0);
		}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef2 = new TypeReference<ResultInfo<Goods>>() {};
		
		// 对goods下面的String类型属性进行转义  at bug[3778 在产品名称和物料编码中，输入“A30-000001---”只会显示“A30-000001-” ] by franlin 2018-05-02  TODO 暂时不改成通用模式
		ObjectsUtil.resetStringValueByEscapeType(goods, 1);
		
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "goods/savebaseinfo.htm", goods,clientId,clientKey, TypeRef2);
			if(result2 != null && result2.getCode() == 0){
				vedengSoapService.goodsIsNoReasonReturnSync(goods.getGoodsId(),goods.getIsNoReasonReturn());
			}
			Goods res = (Goods) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}


	@Override
	public Goods saveSaleInfo(Goods goods, HttpServletRequest request, HttpSession session) {
		User user =(User)session.getAttribute(ErpConst.CURR_USER);
		Long time = DateUtil.sysTimeMillis();
		
		goods.setModTime(time);
		goods.setUpdater(user.getUserId());
		
		if(request.getParameter("begintimeStr") != ""){
			goods.setBegintime(DateUtil.convertLong(request.getParameter("begintimeStr"), "yyyy-MM-dd"));
		} else {
			goods.setBegintime(0l);
		}
		
		if(request.getParameter("endtimeStr") != ""){
			goods.setEndtime(DateUtil.convertLong(request.getParameter("endtimeStr"), "yyyy-MM-dd"));
		} else {
			goods.setEndtime(0l);
		}
		
		//应用科室
		if(null != request.getParameterValues("attributeId")){
			String[] attributeIds = request.getParameterValues("attributeId");
			List<GoodsSysOptionAttribute> goodsSysOptionAttributes = new ArrayList<>();
			for(String attIds : attributeIds){
				GoodsSysOptionAttribute goodsSysOptionAttribute = new GoodsSysOptionAttribute();
				String[] attArr = attIds.split("_");
				
				goodsSysOptionAttribute.setAttributeType(Integer.parseInt(attArr[0]));
				goodsSysOptionAttribute.setAttributeId(Integer.parseInt(attArr[1]));
				
				goodsSysOptionAttributes.add(goodsSysOptionAttribute);
			}
			goods.setGoodsSysOptionAttributes(goodsSysOptionAttributes);
		}	
		
		List<GoodsAttachment> goodsAttachments = new ArrayList<>();
		//注册证
		if (null != request.getParameter("uri_344") && null != request.getParameter("domain_344") && request.getParameter("uri_344") != "" && request.getParameter("domain_344") != "") {
			GoodsAttachment goodsAttachment = new GoodsAttachment();
			goodsAttachment.setAttachmentType(344);
			goodsAttachment.setDomain(request.getParameter("domain_344"));
			goodsAttachment.setUri(request.getParameter("uri_344"));
			goodsAttachments.add(goodsAttachment);
		}
		
		//注册标准
		if (null != request.getParameter("uri_345") && null != request.getParameter("domain_345") && request.getParameter("uri_345") != "" && request.getParameter("domain_345") != "") {
			GoodsAttachment goodsAttachment = new GoodsAttachment();
			goodsAttachment.setAttachmentType(345);
			goodsAttachment.setDomain(request.getParameter("domain_345"));
			goodsAttachment.setUri(request.getParameter("uri_345"));
			goodsAttachments.add(goodsAttachment);
		}
		goods.setGoodsAttachments(goodsAttachments);
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef2 = new TypeReference<ResultInfo<Goods>>() {};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + "goods/savesaleinfo.htm", goods,clientId,clientKey, TypeRef2);
			Goods res = (Goods) result2.getData();
			return res;
		} catch (IOException e) {
			return null;
		}
	}


	@Override
	public List<GoodsAttribute> getGoodsAttributeList(GoodsAttribute goodsAttribute) {
		List<GoodsAttribute> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsAttribute>>> TypeRef = new TypeReference<ResultInfo<List<GoodsAttribute>>>() {};
			String url=httpUrl + "goods/getgoodsattributelist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsAttribute,clientId,clientKey, TypeRef);
			list = (List<GoodsAttribute>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}


	@Override
	public List<GoodsSysOptionAttribute> getGoodsSysOptionAttributeList(Goods goods) {
		List<GoodsSysOptionAttribute> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsSysOptionAttribute>>> TypeRef = new TypeReference<ResultInfo<List<GoodsSysOptionAttribute>>>() {};
			String url=httpUrl + "goods/getgoodssysoptionattributelist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			list = (List<GoodsSysOptionAttribute>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}


	@Override
	public Map<String, Object> getGoodsOptListPage(GoodsOpt goodsOpt, Page page) {
		List<GoodsOpt> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsOpt>>> TypeRef = new TypeReference<ResultInfo<List<GoodsOpt>>>() {};
			String url=httpUrl + "goods/getgoodsoptlistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsOpt,clientId,clientKey, TypeRef,page);
			list = (List<GoodsOpt>) result.getData();
			page = result.getPage();
			
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}


	@Override
	public ResultInfo<?> saveGoodsPackage(GoodsPackage goodsPackage) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<GoodsPackage>> TypeRef = new TypeReference<ResultInfo<GoodsPackage>>() {};
		String url=httpUrl + "goods/savegoodspackage.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goodsPackage,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public ResultInfo<?> saveGoodsRecommend(GoodsRecommend goodsRecommend) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<GoodsRecommend>> TypeRef = new TypeReference<ResultInfo<GoodsRecommend>>() {};
		String url=httpUrl + "goods/savegoodsrecommend.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goodsRecommend,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public ResultInfo<?> delGoodsPackageById(GoodsPackage goodsPackage) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<GoodsPackage>> TypeRef = new TypeReference<ResultInfo<GoodsPackage>>() {};
		String url=httpUrl + "goods/delgoodspackagebyid.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goodsPackage,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}


	@Override
	public ResultInfo<?> delGoodsRecommendById(GoodsRecommend goodsRecommend) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<GoodsRecommend>> TypeRef = new TypeReference<ResultInfo<GoodsRecommend>>() {};
		String url=httpUrl + "goods/delgoodsrecommendbyid.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goodsRecommend,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}


	@Override
	public List<GoodsAttachment> getGoodsAttachmentListByGoodsId(GoodsAttachment goodsAttachment) {
		List<GoodsAttachment> goodsAttachmentList = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<GoodsAttachment>>> TypeRef = new TypeReference<ResultInfo<List<GoodsAttachment>>>() {};
		String url=httpUrl + "goods/getgoodsattachmentlistbygoodsid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsAttachment,clientId,clientKey, TypeRef);
			goodsAttachmentList = (List<GoodsAttachment>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return goodsAttachmentList;
	}

	@Override
	public List<GoodsOpt> getGoodsPackageList(GoodsOpt goodsOpt) {
		List<GoodsOpt> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsOpt>>> TypeRef = new TypeReference<ResultInfo<List<GoodsOpt>>>() {};
			String url=httpUrl + "goods/getgoodspackagelist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsOpt,clientId,clientKey, TypeRef);
			list = (List<GoodsOpt>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	
	
	@Override
	public List<GoodsOpt> getGoodsRecommendList(GoodsOpt goodsOpt) {
		List<GoodsOpt> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsOpt>>> TypeRef = new TypeReference<ResultInfo<List<GoodsOpt>>>() {};
			String url=httpUrl + "goods/getgoodsrecommendlist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goodsOpt,clientId,clientKey, TypeRef);
			list = (List<GoodsOpt>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}


	@Override
	public List<Goods> getGoodsUnitList(Goods goods) {
		List<Goods> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Goods>>> TypeRef = new TypeReference<ResultInfo<List<Goods>>>() {};
			String url=httpUrl + "goods/getgoodsunitlist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			list = (List<Goods>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	
	@Override
	public ResultInfo<?> updateGoodsTaxCategoryNo(List<Goods> list){
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "goods/updategoodstaxcategoryno.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<String> batchSaveVailGoodsName(List<Goods> list) {
		List<String> result_list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<String>>> TypeRef = new TypeReference<ResultInfo<List<String>>>() {};
			String url=httpUrl + "goods/batchsavevailgoodsname.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
			result_list = (List<String>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result_list;
	}

	@Override
	public List<String> batchEditVailGoodsName(List<Goods> list) {
		List<String> result_list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<String>>> TypeRef = new TypeReference<ResultInfo<List<String>>>() {};
			String url=httpUrl + "goods/batcheditvailgoodsname.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
			result_list = (List<String>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result_list;
	}
	
	@Override
	public ResultInfo<?> batchSaveGoods(List<Goods> list) {
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "goods/batchsavegoods.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}
	
	
	@Override
	public ResultInfo<?> batchUpdateGoodsSave(List<Goods> list) {
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "goods/batchupdategoodssave.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public ResultInfo<?> validGoodName(Goods goods) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
		String url=httpUrl + "goods/validgoodsname.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public Map<String, Object> queryGoodsListPage(Goods goods,Page page,HttpSession session) {
		List<GoodsVo> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
			String url=httpUrl + "goods/querygoodslistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef,page);
			list = (List<GoodsVo>) result.getData();
			page = result.getPage();
//			if(list != null){
//				if(session!=null){
//					User user = (User) session.getAttribute(ErpConst.CURR_USER);
//					for(int i=0;i<list.size();i++){
//						list.get(i).setProUserName(rCategoryJUserMapper.getUserByCategoryNm(list.get(i).getCategoryId(),user.getCompanyId()));
//						//list.get(i).setUserList(rManageCategoryJUserMapper.getUserByManageCategory(list.get(i).getManageCategory(),user.getCompanyId()));
//					}
//				}
//			}
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}


	@Override
	public Map<String, Object> getlistGoodsStockPage(HttpServletRequest request, Goods goods, Page page) {
		List<GoodsVo> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
			TypeReference<ResultInfo4Stock<Integer>> typeReference = new TypeReference<ResultInfo4Stock<Integer>>() {
			};
			String url=httpUrl + "goods/getlistgoodsstockpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef,page);
			list = (List<GoodsVo>) result.getData();
			page = result.getPage();
			if(!CollectionUtils.isEmpty(list)) {
				List<String> skulist = new ArrayList<>();
				for (GoodsVo goodsVo : list) {
					skulist.add(goodsVo.getSku());
				}
				Map<String, WarehouseStock> map1 = warehouseStockService.getStockInfo(skulist);
				if (map1 != null) {
					for (GoodsVo goodsVo : list) {
						WarehouseStock w = map1.get(goodsVo.getSku());
						if(w!=null) {
							goodsVo.setGoodsStock(w.getStockNum());
							goodsVo.setCanUseGoodsStock(w.getAvailableStockNum());
							goodsVo.setOccupyNum(w.getOccupyNum());
							/**
							 * 查询活动商品的锁定库存
							 */
							goodsVo.setActionLockCount(map1.get(goodsVo.getSku()).getActionLockNum());
						}
					}
				}
			}
			map.put("goodsList", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo<?> batchOptGoods(Goods goods) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
		String url=httpUrl + "goods/batchoptgoods.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<BuyorderVo> getBuyorderVoList(Goods goods) {
		List<BuyorderVo> list = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<BuyorderVo>>> TypeRef = new TypeReference<ResultInfo<List<BuyorderVo>>>() {};
			String url=httpUrl + "goods/getbuyordervolist.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			list = (List<BuyorderVo>) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}


	@Override
	public List<SysOptionDefinition> getAssignList(List<SysOptionDefinition> optionDefinitions,HttpSession session) {
		if(null != optionDefinitions && optionDefinitions.size() > 0){
			List<SysOptionDefinition> definitions = null;
			try {
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<SysOptionDefinition>>> TypeRef = new TypeReference<ResultInfo<List<SysOptionDefinition>>>() {};
				String url=httpUrl + "goods/getassignlist.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, optionDefinitions,clientId,clientKey, TypeRef);
				definitions = (List<SysOptionDefinition>) result.getData();
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
			User user = (User) session.getAttribute(ErpConst.CURR_USER);
			if(null != definitions){
				for(SysOptionDefinition optionDefinition : definitions){
					optionDefinition.setUserList(rManageCategoryJUserMapper.getUserByManageCategory(optionDefinition.getSysOptionDefinitionId(),user.getCompanyId()));
				}
			}
			
			
			return definitions;
		}
		return null;
	}


	@Override
	public List<User> getUserByManageCategory(Integer manageCategory, Integer companyId) {
		return rManageCategoryJUserMapper.getUserByManageCategory(manageCategory, companyId);
	}


	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Boolean saveEditCategoryOwner(List<Integer> userId, String manageCategories, HttpSession session) {
		if(userId.size() == 0 || null == userId || null == manageCategories || manageCategories.equals("")){
			return false;
		}
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		String[] manageCategoryList = manageCategories.split(",");
		for(String manageCategory : manageCategoryList){
			//删除归属
			rManageCategoryJUserMapper.deleteByCateCompany(Integer.parseInt(manageCategory),user.getCompanyId());
			
			for(Integer id : userId){
				RManageCategoryJUser rManageCategoryJUser = new RManageCategoryJUser();
				rManageCategoryJUser.setManageCategory(Integer.parseInt(manageCategory));
				rManageCategoryJUser.setUserId(id);
				
				rManageCategoryJUserMapper.insert(rManageCategoryJUser);
			}
			
		}
		return true;
	}


	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo saveUplodeGoodsSafeSotck(List<GoodsSafeStock> list) {
		ResultInfo resultInfo = new ResultInfo<>();
		if(null != list && list.size() > 0){
			for(GoodsSafeStock safeStock : list){
				try {
					Goods goods = new Goods();
					goods.setSku(safeStock.getSku());
					goods.setCompanyId(safeStock.getCompanyId());
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
					String url=httpUrl + "goods/getgoodsbysku.htm";
					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
					Goods goodsInfo = (Goods) result.getData();
					
					if(null == goodsInfo){
						resultInfo.setMessage("订货号："+safeStock.getSku() + "不存在！");
						throw new Exception("订货号："+safeStock.getSku() + "不存在！");
					}
					
					safeStock.setGoodsId(goodsInfo.getGoodsId());
					
					GoodsSafeStock goodsSafeStock = goodsSafeStockMapper.selectByGoodsId(goodsInfo.getGoodsId(), safeStock.getCompanyId());
					if(null != goodsSafeStock){
						goodsSafeStockMapper.updateByGoodsId(safeStock);
					}else{
						safeStock.setAddTime(safeStock.getModTime());
						safeStock.setCreator(safeStock.getUpdater());
						safeStock.setCompanyId(goodsInfo.getCompanyId());
						goodsSafeStockMapper.insert(safeStock);
					}
					
				} catch (Exception e) {
					logger.error(Contant.ERROR_MSG, e);
					return resultInfo;
				}
				
			}
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}


	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public ResultInfo<?> batchGoodsPriceSave(List<GoodsChannelPrice> list) throws Exception{
		
		//预处理list，将sku与省市id组合唯一的重复的GoodsChannelPrice取出存入map
		Map<String,List<GoodsChannelPrice>> map = new HashMap<String,List<GoodsChannelPrice>>();
		for (int i = 0; i < list.size(); i++)
		{
			List<GoodsChannelPrice> gpList = new ArrayList<GoodsChannelPrice>();
			for (int j = 0; j < list.size(); j++)
			{
				/*
				 * && list.get(i).getCustomerTypeComments().equals(list.get(j).
				 * getCustomerTypeComments())
				 */
				if (list.get(i).getSku().equals(list.get(j).getSku()) && list.get(i).getProvinceId().equals(list.get(j).getProvinceId()))
				{
					if (list.get(i).getCityId() == null)
					{
						gpList.add(list.get(j));
					}
					else if (list.get(i).getCityId() != null && (list.get(i).getCityId().equals(list.get(j).getCityId())))
					{
						gpList.add(list.get(j));
					}
				}
			}
			if (list.get(i).getCityId() == null)
			{
				list.get(i).setCityId(0);
			}
			String key = list.get(i).getSku() + list.get(i).getProvinceId() + list.get(i).getCityId();
			map.put(key, gpList);
		}
		
		for (GoodsChannelPrice goodsChannelPrice : list)
		{
			// 
			List<GoodsChannelPrice> oldList = goodsChannelPriceMapper.getGoodsChannelByG(goodsChannelPrice);
			for (GoodsChannelPrice gp2 : oldList) 
			{
				//删除核价信息
				goodsChannelPriceMapper.deleteGoodsPrice(gp2);
				//删除核价附属信息
				GoodsChannelPriceExtend gc = new GoodsChannelPriceExtend();
				gc.setGoodsChannelPriceId(gp2.getGoodsChannelPriceId());
				goodsChannelPriceExtendMapper.deleteGoodsChannelPriceExtend(gc);
			}
			
			//更新销售核价信息；sku与省市id组合唯一
			GoodsChannelPrice gp = new GoodsChannelPrice();
			gp.setType(0);   // 销售
			gp.setAddTime(goodsChannelPrice.getAddTime());
			gp.setBatchPolicy(goodsChannelPrice.getBatchPolicy());
			// 销售批量价
			gp.setBatchPrice(goodsChannelPrice.getBatchPrice());
			// min 销售批量数量
			gp.setBatchPriceMinNum(goodsChannelPrice.getBatchPriceMinNum());
			// max 销售批量数量
			gp.setBatchPriceMaxNum(goodsChannelPrice.getBatchPriceMaxNum());
			gp.setCityId(goodsChannelPrice.getCityId());
			gp.setCityName(goodsChannelPrice.getCityName());
			gp.setCostPriceEndTime(goodsChannelPrice.getCostPriceEndTime());
			gp.setCostPriceStartTime(goodsChannelPrice.getCostPriceStartTime());
			gp.setCreator(goodsChannelPrice.getCreator());
			gp.setCustomerTypeComments(goodsChannelPrice.getCustomerTypeComments());
			gp.setDistributionPrice(goodsChannelPrice.getDistributionPrice());
			gp.setGoodsId(goodsChannelPrice.getGoodsId());
			gp.setIsManufacturerAuthorization(goodsChannelPrice.getIsManufacturerAuthorization());
			gp.setIsReportTerminal(goodsChannelPrice.getIsReportTerminal());
			gp.setMarketPrice(goodsChannelPrice.getMarketPrice());
			gp.setMinAmount(goodsChannelPrice.getMinAmount());
			gp.setMinNum(goodsChannelPrice.getMinNum());
			gp.setModTime(goodsChannelPrice.getModTime());
			gp.setPeriodDate(goodsChannelPrice.getPeriodDateXs());
			gp.setPrivatePrice(goodsChannelPrice.getPrivatePrice());
			gp.setPublicPrice(goodsChannelPrice.getPublicPrice());
			gp.setProvinceId(goodsChannelPrice.getProvinceId());
			gp.setProvinceName(goodsChannelPrice.getProvinceName());
			gp.setUpdater(goodsChannelPrice.getUpdater());
			gp.setVipPrice(goodsChannelPrice.getVipPrice());
			int gcpId = goodsChannelPriceMapper.insertSelective(gp);
//			String key = goodsChannelPrice.getSku()+goodsChannelPrice.getProvinceId()+goodsChannelPrice.getCityId();
//			String keycg = goodsChannelPrice.getSku()+goodsChannelPrice.getProvinceId()+goodsChannelPrice.getCityId();
//			List<GoodsChannelPrice> gspList = map.get(key);
//			for (GoodsChannelPrice gPrice : gspList) 
//			{
//				// 批量价
//				if(gPrice.getBatchPrice()!=null && gPrice.getBatchPriceMinNum() != null)
//				{
//					GoodsChannelPriceExtend gce = new GoodsChannelPriceExtend();
//					gce.setGoodsChannelPriceId(gp.getGoodsChannelPriceId());
//					gce.setPriceType(2);
//					gce.setConditionType(2);
//					gce.setMaxNum(gPrice.getBatchPriceMaxNum());
//					gce.setMinNum(gPrice.getBatchPriceMinNum());
//					gce.setBatchPrice(gPrice.getBatchPrice());
//					goodsChannelPriceExtendMapper.insertSelective(gce);
//				}
//			}
			
			//更新采购核价信息；sku与省市id组合唯一
			GoodsChannelPrice gpcg = new GoodsChannelPrice();
			gpcg.setType(1); // 采购
			gpcg.setAddTime(goodsChannelPrice.getAddTime());
			gpcg.setBatchPolicy(goodsChannelPrice.getBatchPolicyCg());
			gpcg.setBatchPrice(goodsChannelPrice.getBatchPriceCg());
			gpcg.setBatchPriceMaxNum(goodsChannelPrice.getBatchPriceMaxNumCg());
			gpcg.setBatchPriceMinNum(goodsChannelPrice.getBatchPriceMinNumCg());
			gpcg.setCityId(goodsChannelPrice.getCityId());
			gpcg.setCityName(goodsChannelPrice.getCityName());
			gpcg.setCostPriceEndTime(goodsChannelPrice.getCostPriceEndTime());
			gpcg.setCostPriceStartTime(goodsChannelPrice.getCostPriceStartTime());
			gpcg.setCreator(goodsChannelPrice.getCreator());
			gpcg.setCustomerTypeComments(goodsChannelPrice.getCustomerTypeComments());
			gpcg.setDistributionPrice(goodsChannelPrice.getDistributionPriceCg());
			gpcg.setGoodsId(goodsChannelPrice.getGoodsId());
			gpcg.setIsManufacturerAuthorization(goodsChannelPrice.getIsManufacturerAuthorizationCg());
			gpcg.setIsReportTerminal(goodsChannelPrice.getIsReportTerminalCg());
			gpcg.setMarketPrice(goodsChannelPrice.getMarketPrice());
			gpcg.setMinAmount(goodsChannelPrice.getMinAmountCg());
			gpcg.setMinNum(goodsChannelPrice.getMinNumCg());
			gpcg.setModTime(goodsChannelPrice.getModTime());
			gpcg.setPeriodDate(goodsChannelPrice.getPeriodDateCg());
			gpcg.setPrivatePrice(goodsChannelPrice.getPrivatePriceCg());
			gpcg.setPublicPrice(goodsChannelPrice.getPublicPriceCg());
			gpcg.setProvinceId(goodsChannelPrice.getProvinceId());
			gpcg.setProvinceName(goodsChannelPrice.getProvinceName());
			gpcg.setUpdater(goodsChannelPrice.getUpdater());
			gpcg.setVipPrice(goodsChannelPrice.getVipPrice());
			goodsChannelPriceMapper.insertSelective(gpcg);
			// 拼接key
			String key = goodsChannelPrice.getSku()+goodsChannelPrice.getProvinceId()+goodsChannelPrice.getCityId();
			List<GoodsChannelPrice> gspListCg = map.get(key);
			for (GoodsChannelPrice gPrice : gspListCg) 
			{
				// 销售批量价
				if(gPrice.getBatchPrice()!=null && gPrice.getBatchPriceMinNum() != null)
				{
					GoodsChannelPriceExtend gce = new GoodsChannelPriceExtend();
					gce.setGoodsChannelPriceId(gp.getGoodsChannelPriceId());
					gce.setPriceType(2);
					gce.setConditionType(2);
					gce.setMaxNum(gPrice.getBatchPriceMaxNum());
					gce.setMinNum(gPrice.getBatchPriceMinNum());
					gce.setBatchPrice(gPrice.getBatchPrice());
					goodsChannelPriceExtendMapper.insertSelective(gce);
				}
				// 采购批量价
				if(gPrice.getBatchPriceCg()!=null && gPrice.getBatchPriceMinNumCg()!=null)
				{
					GoodsChannelPriceExtend gce = new GoodsChannelPriceExtend();
					gce.setGoodsChannelPriceId(gpcg.getGoodsChannelPriceId());
					gce.setPriceType(2);
					gce.setConditionType(2);
					gce.setMaxNum(gPrice.getBatchPriceMaxNumCg());
					gce.setMinNum(gPrice.getBatchPriceMinNumCg());
					gce.setBatchPrice(gPrice.getBatchPriceCg());
					goodsChannelPriceExtendMapper.insertSelective(gce);
				}
				//成本价
				GoodsChannelPriceExtend g = new GoodsChannelPriceExtend();
				g.setGoodsChannelPriceId(gpcg.getGoodsChannelPriceId());
				g.setPriceType(1);
				g.setConditionType(1);
				g.setStartTime(gPrice.getCostPriceStartTime());
				g.setEndTime(gPrice.getCostPriceEndTime());
				g.setBatchPrice(gPrice.getCostPrice());
				goodsChannelPriceExtendMapper.insertSelective(g);
			}
		}
	  return new ResultInfo<>(0,"操作成功");
	}


	/**
	 * 验证sku是否存在产品表
	 */
	@Override
	public List<Goods> batchVailGoodsSku(List<String> sku_list) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Goods>>> TypeRef = new TypeReference<ResultInfo<List<Goods>>>() { };
			String url = httpUrl + "goods/batchvailgoodssku.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, sku_list, clientId, clientKey, TypeRef);
			if(result != null && result.getCode() == 0){
				List<Goods> list = (List<Goods>) result.getData();
				return list;
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);return null;
		}
		return null;
	}


	@Override
	public ResultInfo<?> batchGoodsSettelmentSave(List<GoodsSettlementPrice> list) {
		// 验证产品结算信息是否存在，存在-更新，不存在-新增；goodsId唯一
		int i = goodsSettlementPriceMapper.batchGoodsSettelmentSave(list);
		if(i >=0 ){
			return new ResultInfo<>(0,"操作成功");
		}
		return new ResultInfo<>();
	}

	/**
	 * <b>Description:</b><br>  获取供应商供应的产品ID集合
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年12月5日 上午8:59:34
	 */
	@Override
	public List<Integer> getSupplierGoodsIds(Integer traderSupplierId) {
        	 try {
        		// 定义反序列化 数据格式
        		final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() { };
        		String url = httpUrl + "goods/getsuppliergoodsids.htm";
        		ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderSupplierId, clientId, clientKey, TypeRef);
        		if(result != null && result.getCode() == 0){
        			List<Integer> list = (List<Integer>) result.getData();
        			return list;
        		}
        	} catch (Exception e) {
        		logger.error(Contant.ERROR_MSG, e);
        		return null;
        	}
        	 return null;
	}


	@Override
	public ResultInfo<?> saveMainSupply(RGoodsJTraderSupplier rGoodsJTraderSupplier) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<RGoodsJTraderSupplier>> TypeRef = new TypeReference<ResultInfo<RGoodsJTraderSupplier>>() {};
		String url=httpUrl + "goods/savemainsupply.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, rGoodsJTraderSupplier,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public ResultInfo<?> delMainSupply(RGoodsJTraderSupplier rGoodsJTraderSupplier) {
		ResultInfo<?> result = new ResultInfo<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<RGoodsJTraderSupplier>> TypeRef = new TypeReference<ResultInfo<RGoodsJTraderSupplier>>() {};
		String url=httpUrl + "goods/delmainsupply.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, rGoodsJTraderSupplier,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getGoodsBaseinfoListPage(Goods goods,Page page,HttpSession session) {
		List<GoodsVo> list = null;
		Map<String,Object> map = new HashMap<>();
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsVo>>> TypeRef = new TypeReference<ResultInfo<List<GoodsVo>>>() {};
			String url=httpUrl + "goods/getgoodsbaseinfolistpage.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef,page);
			list = (List<GoodsVo>) result.getData();
			page = result.getPage();
			map.put("list", list);
			map.put("page", page);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}


	@Override
	public ResultInfo restVerify(Goods goods) {
		final TypeReference<ResultInfo> TypeRef2 = new TypeReference<ResultInfo>() {};
		String uri=httpUrl + "verifiesrecord/restverify.htm";
		ResultInfo<?> rs = null;
		try {
			VerifiesInfo verifiesInfo = new VerifiesInfo();
			verifiesInfo.setRelateTable("T_GOODS");
			verifiesInfo.setRelateTableKey(goods.getGoodsId());
			rs = (ResultInfo<?>) HttpClientUtils.post(uri, verifiesInfo,clientId,clientKey, TypeRef2);
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		
		return rs;
	}


	@Override
	public ResultInfo<?> getGoodsListExtraInfo(Goods goods) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "goods/getgoodslistextrainfo.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public GoodsExtend getGoodsExtend(Goods goods) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<GoodsExtend>> TypeRef = new TypeReference<ResultInfo<GoodsExtend>>() {};
		String url=httpUrl + "goods/getgoodsextend.htm";
		GoodsExtend goodsExtend = new GoodsExtend();
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			if(result != null){
				goodsExtend = (GoodsExtend) result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
		return goodsExtend;
	}


	@Override
	public ResultInfo<?> saveCommodityPropaganda(Map<String, Object> map) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		String url=httpUrl + "goods/savecommoditypropaganda.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, map, clientId, clientKey, TypeRef2);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}


	@Override
	public ResultInfo<?> batchSaveGoodsExtend(List<GoodsExtend> list) {
		ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsExtend>>> TypeRef = new TypeReference<ResultInfo<List<GoodsExtend>>>() {};
			String url=httpUrl + "goods/batchsavegoodsextend.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, list,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public ResultInfo<?> copyGoods(Goods goods) {
	    	ResultInfo<?> result = null;
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Goods>> TypeRef = new TypeReference<ResultInfo<Goods>>() {};
			String url=httpUrl + "goods/copygoods.htm";
			result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}


	@Override
	public GoodsVo getSaleJHGoodsDetail(Goods goods) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<GoodsVo>> TypeRef = new TypeReference<ResultInfo<GoodsVo>>() {};
			String url=httpUrl + "goods/getsalejhgoodsdetail.htm";
			ResultInfo<GoodsVo> result = (ResultInfo<GoodsVo>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			if(result == null || result.getCode() == -1){
				return null;
			}
			return result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return null;
	}


	@Override
	public ResultInfo<?> saveGoodsVoFaqs(GoodsVo goodsVo) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsFaq>>> TypeRef = new TypeReference<ResultInfo<List<GoodsFaq>>>() {};
			String url=httpUrl + "goods/savegoodsfaqs.htm";
			ResultInfo<List<GoodsFaq>> result = (ResultInfo<List<GoodsFaq>>) HttpClientUtils.post(url, goodsVo,clientId,clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
	}


	@Override
	public ResultInfo<?> getGoodsVoFaqs(Goods goods) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<GoodsFaq>>> TypeRef = new TypeReference<ResultInfo<List<GoodsFaq>>>() {};
			String url=httpUrl + "goods/getgoodsfaqsbygoodsid.htm";
			ResultInfo<List<GoodsFaq>> result = (ResultInfo<List<GoodsFaq>>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		
	}


	@Override
	public ResultInfo<?> updateGoodsInfoById(Goods goods) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
			String url=httpUrl + "goods/updategoodsinfobyid.htm";
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, goods,clientId,clientKey, TypeRef);
			if(result != null){
				return result;
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return new ResultInfo<>();
	}


	@Override
	public Integer getGoodsIdBySku(String skuNo) {
		Integer goodsId = goodsMapper.getGoodsIdBySku(skuNo);
		return goodsId;
	}

	@Override
	public List<SaleorderGoods> getGoodsPriceList(Integer saleorderId) {
		return goodsMapper.getGoodsPriceList(saleorderId);
	}


}
