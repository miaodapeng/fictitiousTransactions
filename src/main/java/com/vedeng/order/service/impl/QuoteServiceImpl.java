package com.vedeng.order.service.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.vedeng.common.model.ReqVo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.HttpRestClientUtil;
import com.vedeng.goods.dao.GoodsMapper;
import com.vedeng.system.model.Message;
import com.vedeng.system.model.MessageTemplate;
import com.vedeng.system.model.MessageUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.goods.dao.RCategoryJUserMapper;
import com.vedeng.order.dao.QuoteorderMapper;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.QuoteorderConsult;
import com.vedeng.order.model.QuoteorderGoods;
import com.vedeng.order.model.vo.QuoteorderVo;
import com.vedeng.order.service.QuoteService;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.model.CommunicateRecord;

import net.sf.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service("quoteService")
public class QuoteServiceImpl extends BaseServiceimpl implements QuoteService{
	private static Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);

	public final int ROLE_ID_17=17;

	@Autowired
	@Qualifier("communicateRecordMapper")
	private CommunicateRecordMapper communicateRecordMapper;
	
	@Autowired
	@Qualifier("rCategoryJUserMapper")
	private RCategoryJUserMapper rCategoryJUserMapper;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("quoteorderMapper")
	private QuoteorderMapper quoteorderMapper;
	@Override
	public Map<String, Object> getQuoteListPage(Quoteorder quoteorder, Page page) {
//		Map<String, Object> result_map = null;
		Map<String,Object> map = new HashMap<>();
		try {
			
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<List<Quoteorder>>> TypeRef = new TypeReference<ResultInfo<List<Quoteorder>>>() {};
			String url = httpUrl + "order/quote/getquotelistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteorder,clientId,clientKey, TypeRef,page);
			
			/*result_map = (Map<String, Object>) result.getData();
			page = result.getPage();
			net.sf.json.JSONArray json = null;
			
			String quoteStr = result_map.get("quoteList").toString();
			json = net.sf.json.JSONArray.fromObject(quoteStr);
			List<Quoteorder> quoteList = (List<Quoteorder>) json.toCollection(json, Quoteorder.class);
			map.put("quoteList", quoteList);*/
			map.put("quoteList",(List<Quoteorder>)result.getData());
			
			map.put("page", result.getPage());
		} catch (IOException e) {
			log.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public Integer getCommunicateRecordCount(CommunicateRecord cr,Integer bussinessType,Integer quoteType) {
		return communicateRecordMapper.getCommunicateRecordCount(cr,bussinessType,quoteType);
	}
	
	@Override
	public List<CommunicateRecord> getCommunicateNumList(List<Integer> saleIdList, List<Integer> quoteIdList, List<Integer> businessIdList) {
		return communicateRecordMapper.getCommunicateNumList(saleIdList,quoteIdList,businessIdList);
	}

	@Override
	public List<Integer> getCommunicateRecordByDate(Long beginDate, Long endDate,String communicateType) {
		return communicateRecordMapper.getCommunicateRecordByDate(beginDate,endDate,communicateType);
	}

	@Override
	public ResultInfo<Quoteorder> saveQuote(Quoteorder quote) {
		ResultInfo<Quoteorder> result = null;
		
		CommunicateRecord cr = new CommunicateRecord();
		if(quote.getBussinessChanceId()!=null){
			cr.setBussinessChanceId(quote.getBussinessChanceId());
			cr.setQuoteorderId(null);
			//沟通类型ID_244商机,ID_245报价
			Integer count = communicateRecordMapper.getCommunicateRecordCount(cr,SysOptionConstant.ID_244,SysOptionConstant.ID_245);
			if(count>0){
				quote.setHaveCommunicate(1);//存在沟通记录
			}
		}
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/savequote.htm";
		try {
			result = (ResultInfo<Quoteorder>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
//			quote = (Quoteorder) result.getData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Quoteorder getQuoteInfoByKey(Integer quoteorderId) {
		Quoteorder quote = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/getquoteinfobykey.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteorderId,clientId,clientKey, TypeRef);
			quote = (Quoteorder) result.getData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return quote;
	}

	@Override
	public ResultInfo<?> updateQuoteCustomer(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/updatequotecustomer.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> updateQuoteTerminal(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/updatequoteterminal.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> saveQuoteGoods(QuoteorderGoods quoteGoods,Attachment ach) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/savequotegoods.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quoteGoods,clientId,clientKey, TypeRef);
			if(result.getCode()!=-1){
				if(result.getData()!=null && ach!=null && StringUtils.isNotBlank(ach.getUri()) && StringUtils.isNotBlank(ach.getName())){
					//保存附近记录信息
					quoteGoods = (QuoteorderGoods)JSONObject.toBean(JSONObject.fromObject(result.getData()), QuoteorderGoods.class);
					ach.setRelatedId(quoteGoods.getQuoteorderGoodsId());
					ach.setAttachmentType(SysOptionConstant.ID_343);
					ach.setAttachmentFunction(SysOptionConstant.ID_494);
					// 定义反序列化 数据格式
					final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
					String url2 = httpUrl + "attachment/saveattachment.htm";
					result = (ResultInfo<?>) HttpClientUtils.post(url2, ach,clientId,clientKey, TypeRef2);
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String,Object> getQuoteGoodsByQuoteId(Integer quoteorderId,Integer companyId,HttpSession hs,Integer viewType,Integer traderId) {
		Map<String,Object> map = new HashMap<>();
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<QuoteorderGoods>>> TypeRef = new TypeReference<ResultInfo<List<QuoteorderGoods>>>() {};
		String url=httpUrl + "order/quote/getquotegoodsbyquoteid.htm";
		try {
			Map<String,Integer> map_parm = new HashMap<String,Integer>();
			if(viewType != null){
				map_parm.put("viewType", viewType);
			}
			map_parm.put("quoteorderId", quoteorderId);map_parm.put("companyId", companyId);map_parm.put("traderId", traderId);
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, map_parm,clientId,clientKey, TypeRef);
			List<QuoteorderGoods> quoteGoodsList = (List<QuoteorderGoods>) result.getData();
			if(quoteGoodsList != null){
				List<Integer> userIdList = new ArrayList<>();
//				List<Integer> categoryIdList = new ArrayList<>();
				User user = (User) hs.getAttribute(ErpConst.CURR_USER);
				//产品负责人
				for(int i=0;i<quoteGoodsList.size();i++){
					userIdList.add(quoteGoodsList.get(i).getLastReferenceUser());
					quoteGoodsList.get(i).setGoodsUserNm(rCategoryJUserMapper.getUserByCategoryNm(quoteGoodsList.get(i).getGoods().getCategoryId(), user.getCompanyId()));
//					categoryIdList.add(quoteGoodsList.get(i).getGoods().getCategoryId());
				}
				map.put("quoteGoodsList", quoteGoodsList);
				/*List<User> userList = new ArrayList<>();
				if(null != categoryIdList && categoryIdList.size() > 0){
					userList = userMapper.getCategoryUserList(categoryIdList,companyId);
				}*/
				
				userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
				if(userIdList!=null && userIdList.size() > 0){
					List<User> userList = userMapper.getUserByUserIds(userIdList);
					map.put("userList", userList);//申请人，审核人
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public QuoteorderGoods getQuoteGoodsById(Integer quoteGoodsId,HttpSession httpSession) {
		QuoteorderGoods new_quoteGoods = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<QuoteorderGoods>> TypeRef = new TypeReference<ResultInfo<QuoteorderGoods>>() {};
		String url=httpUrl + "order/quote/getquotegoodsbyid.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteGoodsId,clientId,clientKey, TypeRef);
			new_quoteGoods = (QuoteorderGoods) result.getData();
			
			User user = (User)httpSession.getAttribute(Consts.SESSION_USER);
			new_quoteGoods.setGoodsUserNm(rCategoryJUserMapper.getUserByCategoryNm(new_quoteGoods.getCategoryId(), user.getCompanyId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return new_quoteGoods;
	}

	@Override
	public ResultInfo<?> editQuoteGoods(QuoteorderGoods quoteGoods,Attachment ach) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editquotegoods.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quoteGoods,clientId,clientKey, TypeRef);
			if(quoteGoods!=null && ach!=null && StringUtils.isNotBlank(ach.getAttachmentId()+"")){
				//保存附近记录信息
				ach.setRelatedId(quoteGoods.getQuoteorderGoodsId());
				ach.setAttachmentType(SysOptionConstant.ID_343);
				ach.setAttachmentFunction(SysOptionConstant.ID_494);
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {};
				String url2 = httpUrl + "attachment/saveattachment.htm";
				result = (ResultInfo<?>) HttpClientUtils.post(url2, ach,clientId,clientKey, TypeRef2);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> delQuoteGoodsById(QuoteorderGoods quoteGoods) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/delquotegoodsbyid.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quoteGoods,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> editQuoteAmount(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editquoteamount.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public List<CommunicateRecord> getQuoteCommunicateListPage(String relatedIds, String communicateTypes,Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relatedIds", relatedIds);
		map.put("communicateTypes", communicateTypes);
		map.put("page", page);
		return communicateRecordMapper.getQuoteCommunicateListPage(map);
	}

	@Override
	public Map<Integer, String> getCommnuicateTraderTag(List<Integer> commnuicateIdList) {
		Map<Integer, String> tagMap = new HashMap<Integer, String>();
			try {
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<List<Map<String,String>>>> TypeRef = new TypeReference<ResultInfo<List<Map<String,String>>>>() {};
				String url = httpUrl + "order/quote/getcommnuicatetradertag.htm";
				
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, commnuicateIdList,clientId,clientKey, TypeRef);
				
				
				List<Map<String,String>> list = (List<Map<String, String>>) result.getData();
				
				if(list!=null){
					for(int i=0;i<list.size();i++){
						tagMap.put(Integer.valueOf(list.get(i).get("key")), list.get(i).get("value"));
					}
				}
				
//				tagMap.put(Integer.valueOf(map.get("key")), map.get("value"));
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		return tagMap;
	}
	
	@Override
	public ResultInfo<?> editQuoteValIdSave(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editquotevalidsave.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Autowired
	@Qualifier("goodsMapper")
	private GoodsMapper goodsMapper;
	@Override
	public ResultInfo<?> addQuoteConsultSave(QuoteorderConsult quoteConsult,User user) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Integer>>> TypeRef = new TypeReference<ResultInfo<List<Integer>>>() {};
		String url=httpUrl + "order/quote/addquoteconsultsave.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quoteConsult,clientId,clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					List<Integer> goodsIds = (List<Integer>)result.getData();
					if(CollectionUtils.isNotEmpty(goodsIds)) {
						//分类负责人列表
						List<Integer> userIdList = goodsMapper.getAssignUserIdsByGoods(goodsIds);
						//发送消息
                        if(CollectionUtils.isNotEmpty(userIdList)) {
							Map<String, String> map = new HashMap<>();
							map.put("quoteorderNo", quoteConsult.getQuoteorderNo());
							MessageUtil.sendMessage(31, userIdList, map, "./order/quote/getQuoteDetail.do?quoteorderId=" + quoteConsult.getQuoteorderId() + "&viewType=5");
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		} catch(Exception ex){
			logger.error(Contant.ERROR_MSG, ex);
		}
		return result;
	}
	
	@Override
	public List<QuoteorderConsult> getQuoteConsultList(Integer quoteorderId) {
		List<QuoteorderConsult> list = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<QuoteorderConsult>>> TypeRef = new TypeReference<ResultInfo<List<QuoteorderConsult>>>() {};
		String url=httpUrl + "order/quote/getquoteconsultlist.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteorderId,clientId,clientKey, TypeRef);
			list = (List<QuoteorderConsult>) result.getData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}
	
	
	@Override
	public ResultInfo<?> editQuoteHaveCommunicate(Quoteorder quote) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editquotehavecommunicate.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> editLoseOrderStatus(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editloseorderstatus.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getQuoteConsultListPage(QuoteorderConsult quoteConsult, Page page,HttpSession session) {
		Map<String, Object> result_map = null;Map<String,Object> map = new HashMap<>();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		try {
//			if(quoteConsult.getGoods() != null && quoteConsult.getGoods().getGoodsUserId() != null) {
//				List<Integer> categoryIdList = rCategoryJUserMapper.getCategoryIdsByUserId(quoteConsult.getGoods().getGoodsUserId());
//				if(categoryIdList.isEmpty()) {
//					categoryIdList.add(-1);
//				}
//				quoteConsult.setCategoryIdList(categoryIdList);
//			}
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
			String url = httpUrl + "order/quote/getquoteconsultlistpage.htm";
			
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteConsult,clientId,clientKey, TypeRef, page);
			if(result != null && result.getCode() == 0 && result.getData() != null){
				result_map = (Map<String, Object>) result.getData();
				page = result.getPage();
				net.sf.json.JSONArray json = null;

				//报价咨询回复人员
				String replyUserStr = result_map.get("replyUserList").toString();
				json = net.sf.json.JSONArray.fromObject(replyUserStr==null?"":replyUserStr);
				List<QuoteorderConsult> replyUserList = (List<QuoteorderConsult>) json.toCollection(json, QuoteorderConsult.class);
				map.put("replyUserList", replyUserList);
				
				/*List<Integer> userIdList = new ArrayList<>();
				for(int i=0;i<quoteReplyUserList.size();i++){
					userIdList.add(quoteReplyUserList.get(i).getCreator());
				}
				userIdList = new ArrayList<Integer>(new HashSet<Integer>(userIdList));
				if(userIdList!=null && userIdList.size() > 0){
					List<User> replyUserList = userMapper.getUserByUserIds(userIdList);
					map.put("replyUserList", replyUserList);
				}*/
				
				
				
				
				//报价咨询记录
				String quoteStr = result_map.get("quoteConsultList").toString();
				json = net.sf.json.JSONArray.fromObject(quoteStr==null?"":quoteStr);
				List<QuoteorderConsult> quoteConsultList = (List<QuoteorderConsult>) json.toCollection(json, QuoteorderConsult.class);
				
				//产品分类记录
				String cateoryListStr = (result_map.get("cateoryList")==null?"":result_map.get("cateoryList")).toString();
				if(StringUtils.isNotBlank(cateoryListStr)){
					json = net.sf.json.JSONArray.fromObject(cateoryListStr==null?"":cateoryListStr);
					List<QuoteorderConsult> cateoryList = (List<QuoteorderConsult>) json.toCollection(json, QuoteorderConsult.class);
					
/*					List<Integer> cateoryIdList = new ArrayList<>();
					for(int i=0;i<cateoryList.size();i++){
						cateoryIdList.add(cateoryList.get(i).getCategoryId());
					}
					cateoryIdList = new ArrayList<Integer>(new HashSet<Integer>(cateoryIdList));
					if(cateoryIdList!=null && cateoryIdList.size() > 0){
						List<User> cateoryUserList = rCategoryJUserMapper.getTypeUserByCategoryIds(userIdList, user.getCompanyId(),2);
						map.put("cateoryUserList", cateoryUserList);
					}*/
					
					
					if(null != quoteConsultList && quoteConsultList.size() > 0 && cateoryList != null){
//						String goodsUserNm = "";
						List<User> goodsUserList = null;
						for(QuoteorderConsult consult : quoteConsultList){
							if(null != consult.getCategoryId()){
								goodsUserList = new ArrayList<>();
								for(int i=0;i<cateoryList.size();i++){
									if(consult.getQuoteorderId().intValue() == cateoryList.get(i).getQuoteorderId().intValue()){
										goodsUserList.addAll((rCategoryJUserMapper.getUserByCategory(cateoryList.get(i).getCategoryId(), user.getCompanyId())));
										/*if(StringUtils.isNotBlank(str)){
											if(goodsUserNm.indexOf(str) < 0){
												goodsUserNm = (goodsUserNm.equals("")?"":(goodsUserNm + "、")) + str;
											}
										}*/
									}
								}
								
								Set<User> personSet = new TreeSet<>((o1, o2) -> o1.getUserId().compareTo(o2.getUserId()));
						        personSet.addAll(goodsUserList);
								
								consult.setUserList(new ArrayList<>(personSet));
//								consult.setGoodsUserNm(goodsUserNm);goodsUserNm = "";
							}
						}
						
					}
				}
				
				map.put("quoteConsultList", quoteConsultList);
				
				String quoteUserStr = result_map.get("quoteConsultUserList").toString();
				json = net.sf.json.JSONArray.fromObject(quoteUserStr==null?"":quoteUserStr);
				List<Integer> quoteConsultUserList = (List<Integer>) json.toCollection(json, Integer.class);
				map.put("quoteConsultUserList", quoteConsultUserList);
				
				map.put("page", page);
			}
			
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return map;
	}

	@Override
	public ResultInfo<?> saveReplyQuoteConsult(QuoteorderConsult quoteConsult) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/savereplyquoteconsult.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quoteConsult,clientId,clientKey, TypeRef);
			if(result != null){
				if(result.getCode() == 0 && result.getData() != null){
					Quoteorder quote = (Quoteorder)result.getData();
					if(quote != null && quote.getTraderId()!=null){
						//根据客户Id查询客户负责人
						List<Integer> userIdList = userMapper.getUserIdListByTraderId(quote.getTraderId(),ErpConst.ONE);
						//咨詢保存完成，發送消息給產品負責人
						MessageUtil.sendMessage(32, userIdList, new HashMap<String, String>() {{put("quoteorderNo", quote.getQuoteorderNo());}}, "./order/quote/getQuoteDetail.do?quoteorderId="+quoteConsult.getQuoteorderId()+"&viewType=2");
					}
				}
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> editConsultStatus(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editconsultstatus.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public Attachment getQuoteGoodsAttachment(Attachment ach) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Attachment>> TypeRef = new TypeReference<ResultInfo<Attachment>>() {};
		String url=httpUrl + "attachment/getattachment.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, ach,clientId,clientKey, TypeRef);
			ach = (Attachment) result.getData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return ach;
	}

	@Override
	public Integer getQuoteListCount(Quoteorder quote) {
		Integer num = 0;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Integer>> TypeRef = new TypeReference<ResultInfo<Integer>>() {};
		String url=httpUrl + "order/quote/getquotelistcount.htm";
		try {
			ResultInfo<Integer> result = (ResultInfo<Integer>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
			if(result != null && result.getCode() == 0){
				num = result.getData();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return num;
	}

	@Override
	public List<Quoteorder> getQuoteListSize(Quoteorder quote, Integer startSize, Integer endSize) {
		List<Quoteorder> list = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("quote", quote);
		map.put("startSize", startSize);
		map.put("endSize", endSize);
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<Quoteorder>>> TypeRef = new TypeReference<ResultInfo<List<Quoteorder>>>() {};
		String url=httpUrl + "order/quote/getquotelistsize.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, map,clientId,clientKey, TypeRef);
			if(result!=null && result.getCode()==0){
				list = (List<Quoteorder>) result.getData();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
		return list;
	}

	@Override
	public ResultInfo<?> getQuoteGoodsPriceAndCycle(Quoteorder quote) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/getquotegoodspriceandcycle.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public ResultInfo<?> getTraderCustomerStatus(Integer traderCustomerId) {
		ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/gettradercustomerstatus.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerId,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		if(result!=null){
			return result;
		}else{
			return new ResultInfo<>();
		}
	}

	@Override
	public ResultInfo<?> vailQuoteGoodsRepeat(QuoteorderGoods quoteGoods) {
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/vailquotegoodsrepeat.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteGoods,clientId,clientKey, TypeRef);
			if(result == null) {
				return new ResultInfo<>();
			}
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
	}

	@Override
	public ResultInfo<?> isvalidQuoteOrder(Quoteorder quote) {
		ResultInfo<?> result = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/isvalidquoteorder.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public QuoteorderVo getPrintInfo(Quoteorder quote) {
		try {
			// 定义反序列化 数据格式
			final TypeReference<ResultInfo<QuoteorderVo>> TypeRef = new TypeReference<ResultInfo<QuoteorderVo>>() {};
			String url = httpUrl + "order/quote/getprintinfo.htm";	
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
			QuoteorderVo sgw = (QuoteorderVo) result.getData();
			return sgw;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}

	@Override
	public Quoteorder getQuoteorderForSync(Integer quoteorderId) {
		Quoteorder quote = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/getquoteorderforsync.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, quoteorderId,clientId,clientKey, TypeRef);
			quote = (Quoteorder) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return quote;
	}

	@Override
	public Quoteorder getMessageInfoForSync(Integer orderId) {
		Quoteorder quote = null;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<Quoteorder>> TypeRef = new TypeReference<ResultInfo<Quoteorder>>() {};
		String url=httpUrl + "order/quote/getmessageinfoforsync.htm";
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, orderId,clientId,clientKey, TypeRef);
			quote = (Quoteorder) result.getData();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return quote;
	}

	@Override
	public List<User> getGoodsCategoryUserList(List<Integer> categoryIdList,Integer companyId) {
		try {
			return rCategoryJUserMapper.getGoodsCategoryUserList(categoryIdList, companyId);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	
	public ResultInfo<?> editQuoteOrderGoods(Quoteorder quote){
	    	ResultInfo<?> result = null;
		
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		String url=httpUrl + "order/quote/editquotegoodslist.htm";
		try {
			result = (ResultInfo<?>) HttpClientUtils.post(url, quote,clientId,clientKey, TypeRef);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return result;
	}

	@Override
	public void sendAllocation(String mobile,Integer userId) {
		try {
			List<User> userList = userMapper.getRoleUserId(ROLE_ID_17);
			List<Integer> userListId = new ArrayList<>();
			Map<String, String> map = new HashMap();
			map.put("mobile", mobile);
			for (User user : userList) {
				userListId.add(user.getUserId());
			}
			//	MessageUtil.sendErpMessage(94,userListId,"./erp/aftersales/webaccount/list.do?="+mobile);
			MessageUtil.sendMessage2(94, userListId, map, "./aftersales/webaccount/list.do?mobile=" + mobile);
		}catch (Exception e){
			log.error("sendAllocation",e);
		}

	}

	@Override
	public void getIsDisabled(String mobile, Integer userId,String saleorderNo,Integer saleorderId) {
		try {
			User user = userMapper.getIsDisabled(userId);
			List<User> userList = userMapper.getRoleUserId(ROLE_ID_17);
			List<Integer> userd=new ArrayList<>();
			userd.add(userId);
			List<Integer> userListId = new ArrayList<>();
			Map<String, String> map = new HashMap();
			Map<String, String> map2 = new HashMap();
			map.put("mobile", mobile);
			map2.put("saleorderNo",saleorderNo);
			for (User user1 : userList) {
				userListId.add(user1.getUserId());
			}
			if(null!=user){
				if (user.getIsDisabled() == 1) {
					MessageUtil.sendMessage2(95, userListId, map, "./aftersales/webaccount/list.do?mobile=" + mobile);
				}
				if(user.getIsDisabled() == 0){
					MessageUtil.sendMessage2(96,userd,map2,"./order/saleorder/view.do?saleorderId="+saleorderId);
				}
			}
		}catch (Exception e){
			log.error("getIsDisabled",e);
		}
		}

	@Override
	public void updateQuote(Quoteorder quoteorder) {
		quoteorderMapper.updateQuote(quoteorder);
	}

	@Override
	public Integer isExistBussinessChanceId(int bussinessChanceId) {
		return quoteorderMapper.isExistBussinessChanceId(bussinessChanceId);
	}


}
