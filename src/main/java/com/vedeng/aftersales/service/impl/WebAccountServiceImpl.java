package com.vedeng.aftersales.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import com.rest.traderMsg.controller.WebAccountCertificateMsg;
import com.vedeng.aftersales.model.BDTraderCertificate;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.StringUtil;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.trader.dao.*;
import com.vedeng.trader.model.*;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;

import com.vedeng.trader.service.TraderCustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.aftersales.service.WebAccountService;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.trader.dao.TraderMapper;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.WebAccountVo;

import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("webAccountService")
public class WebAccountServiceImpl extends BaseServiceimpl implements WebAccountService {
	public static Logger logger = LoggerFactory.getLogger(WebAccountServiceImpl.class);

	@Autowired
	@Qualifier("webAccountMapper")
	private WebAccountMapper webAccountMapper;
	
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("traderCustomerMapper")
	private TraderCustomerMapper traderCustomerMapper;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("traderMapper")
	private TraderMapper traderMapper;
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;
	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;

	@Autowired
	private TraderCertificateMapper traderCertificateMapper;

	@Autowired
	private TraderCustomerService traderCustomerService;


	@Override
	public Map<String, Object> getWebAccount(WebAccount webAccount,Integer used) {
		Map<String, Object> returnMap = new HashMap<>();
		WebAccountVo webAccountVo = webAccountMapper.getWebAccount(webAccount);

		TraderCustomer customer = traderCustomerMapper.selectByPrimaryKey(webAccountVo.getTraderCustomerId());


		TraderCustomerVo customerVo = null;
		List<TraderAddressVo> addressList = new ArrayList<>();
		List<TraderContact> contactlist = new ArrayList<>();
		if(null != webAccountVo && webAccountVo.getTraderContactId() != null && webAccountVo.getTraderContactId() > 0){
			try{
				TraderCustomerVo traderCustomerVo = new TraderCustomerVo();
				traderCustomerVo.setTraderAddressId(webAccountVo.getTraderAddressId());
				traderCustomerVo.setTraderContactId(webAccountVo.getTraderContactId());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<Map<String, Object>>> TypeRef = new TypeReference<ResultInfo<Map<String, Object>>>() {};
				String url=httpUrl + "tradercustomer/geteditordergoodsstoreaccountinfo.htm";
				ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomerVo,clientId,clientKey, TypeRef);
				Map<String, Object> map = (Map<String, Object>) result.getData();
				if(map.containsKey("customer")){
					net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map.get("customer"));
					customerVo = (TraderCustomerVo) json.toBean(json, TraderCustomerVo.class);
					if(null != customerVo){
						webAccountVo.setCompanyName(customerVo.getTraderName());
					}
				}
				if(map.containsKey("contact")){
					net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(map.get("contact"));
					contactlist = (List<TraderContact>) json.toCollection(json, TraderContact.class);
				}
								
				if(map.containsKey("address")){
					net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(map.get("address"));
					List<TraderAddress> traderAddressList = (List<TraderAddress>) json.toCollection(json, TraderAddress.class);
					if(null != traderAddressList && traderAddressList.size() > 0){
						for(TraderAddress address : traderAddressList){
							TraderAddressVo tav = new TraderAddressVo();
							tav.setTraderAddress(address);
							tav.setArea(getAddressByAreaId(address.getAreaId()));
							addressList.add(tav);
						}
					}
				}
			}catch (Exception e) {
				logger.error(Contant.ERROR_MSG, e);
			}
			
		}
		if(null != webAccountVo && null != webAccountVo.getMobile() && used == 1){
			//可能归属
			try {
				// 接口调用（优先查客户，客户没有查供应商库）
				String url = httpUrl + "tradercustomer/getcustomerinfobyphone.htm";
				TraderCustomerVo traderCustomer = new TraderCustomerVo();
				traderCustomer.setPhone(webAccountVo.getMobile());
				// 定义反序列化 数据格式
				final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
				};
				ResultInfo<?> result2;
				result2 = (ResultInfo<?>) HttpClientUtils.post(url, traderCustomer, clientId, clientKey, TypeRef2);
				
				JSONObject json = JSONObject.fromObject(result2.getData());
				// 对象包含另一个对象集合时用以下方法转化
				TraderCustomerVo res = JsonUtils.readValue(json.toString(), TraderCustomerVo.class);
				
				if (null != res) {// 有客户
					// 客户归属人
					User sale = userMapper.getUserByTraderId(res.getTraderId(), ErpConst.ONE);
					if (null != sale) {
						webAccountVo.setMaybeSaler(sale.getUsername());
					}
				}
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
			}
		}
		
		returnMap.put("webAccountVo", webAccountVo);

		returnMap.put("traderCustomer", customer);

		returnMap.put("customer", customerVo);
		returnMap.put("contact", contactlist);
		returnMap.put("address", addressList);
		
		return returnMap;
	}

	@Override
	public Map<String, Object> getWebAccountListPage(WebAccountVo webAccountVo, Page page, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("webAccountVo", webAccountVo);
		map.put("page", page);
		
		List<WebAccountVo> list = webAccountMapper.getWebAccountListPage(map);
		
		if(null != list && list.size() > 0){
			//归属
			List<Integer> userIds = new ArrayList<>();
//			List<Integer> contactids = new ArrayList<>();
			for(WebAccountVo accountVo : list){
				if(null != accountVo.getUserId() && accountVo.getUserId() > 0){
					userIds.add(accountVo.getUserId());
				}
				
//				if(null != accountVo.getTraderContactId() && accountVo.getTraderContactId() > 0){
//					contactids.add(accountVo.getTraderContactId());
//				}
			}
			
			if (userIds.size() > 0) {
				List<User> userList = userMapper.getUserByUserIds(userIds);
				
				for(WebAccountVo accountVo : list){
					if(null != accountVo.getUserId() && accountVo.getUserId() > 0){
						for(User user : userList){
							if(accountVo.getUserId().equals(user.getUserId())){
								accountVo.setOwner(user.getUsername());
							}
						}
					}
				}
			}
			
			//关联公司
//			if(contactids.size() > 0){
//				try{
//					// 定义反序列化 数据格式
//					final TypeReference<ResultInfo<List<TraderCustomerVo>>> TypeRef = new TypeReference<ResultInfo<List<TraderCustomerVo>>>() {};
//					String url=httpUrl + "tradercustomer/getdhtraderinfo.htm";
//					ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, contactids,clientId,clientKey, TypeRef);
//					List<TraderCustomerVo> customerList = (List<TraderCustomerVo>) result.getData();
//					if(null != customerList && customerList.size() > 0){
//						for(TraderCustomerVo customer : customerList){
//							for(WebAccountVo accountVo :list){
//								if(customer.getTraderContactId().equals(accountVo.getTraderContactId())){
//									accountVo.setRelateComapnyName(customer.getTraderName());
//								}
//							}
//						}
//					}
//				}catch (Exception e) {
//					logger.error(Contant.ERROR_MSG, e);
//				}
//			}
			
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("list", list);
		returnMap.put("page", page);
		return returnMap;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	public Integer saveEdit(WebAccountVo webAccountVo, HttpSession session,HttpServletRequest request) throws Exception {
		Integer saleorderId = Integer.valueOf(EmptyUtils.isBlank(request.getParameter("saleorderId")) ? "0" : request.getParameter("saleorderId"));
		// 销售修改客户(当前登录人员非客户归属销售)
		Integer belongPaltform=null;
		if ("saleWebConfirm".equals(webAccountVo.getOptType())) {
			User user = userMapper.getUserByTraderCustomerId(webAccountVo.getTraderCustomerId(), CommonConstants.ONE);
			// 修改ERP归属销售
			webAccountVo.setUserId(user.getUserId());
		}
		if(webAccountVo.getUserId()!=null&&webAccountVo.getUserId()>0){
//			belongPaltform=traderCustomerService.userIdPlatFromId(webAccountVo.getUserId(),1);
			belongPaltform=userService.getBelongPlatformOfUser(webAccountVo.getUserId(),1);
			webAccountVo.setBelongPlatform(belongPaltform);
		}
		WebAccount webAccount = webAccountMapper.getWebAccontAuthous(webAccountVo);
		//更新BD订单里交易者信息
		Integer k = saleorderService.updateBDChangeErp(webAccountVo, webAccountVo.getTraderId(), saleorderId);
		Integer i = webAccountMapper.update(webAccountVo);
		WebAccount webAccount2 = webAccountMapper.getWebAccontAuthous(webAccountVo);
		List<Saleorder> saleorderList=new ArrayList<>();
		Saleorder saleorder = new Saleorder();
		saleorder.setCreateMobile(webAccount.getMobile());
		if(null!=saleorder&& StringUtil.isNotBlank(saleorder.getCreateMobile())){
			 saleorderList = saleorderService.selectSaleorderNo(saleorder);
		}
		List<Integer> userd = new ArrayList<>();
		Map<String, String> hashmap = new HashMap<>();
		if (null != webAccount && null != webAccount2) {
			if (null == webAccount.getUserId()) {
				if (null != webAccount2.getUserId()) {
					userd.add(webAccount2.getUserId());
					if (null != saleorderList && saleorderList.size() > 0) {
						for (Saleorder saleorder1 : saleorderList) {
							hashmap.put("saleorderNo", saleorder1.getSaleorderNo());
							MessageUtil.sendMessage2(96, userd, hashmap, "./order/saleorder/view.do?saleorderId=" + saleorder1.getSaleorderId());
						}
					}
				}
			}
			else if (webAccount.getUserId() != webAccount2.getUserId() || webAccount.getTraderId() != webAccount2.getTraderId()) {
				userd.add(webAccount2.getUserId());
				if (null != saleorderList && saleorderList.size() > 0) {
					for (Saleorder saleorder1 : saleorderList) {
						hashmap.put("saleorderNo", saleorder1.getSaleorderNo());
						MessageUtil.sendMessage2(96, userd, hashmap, "./order/saleorder/view.do?saleorderId=" + saleorder1.getSaleorderId());
					}
				}
			}
		}
			//更新贝登会员
			traderCustomerService.updateVedengMember();
			return i + k;

	}

	@Override
	public List<WebAccount> getWebAccountByTraderContactId(WebAccount webAccount){
		return webAccountMapper.getWebAccountListByParam(webAccount);
	}

    @Override
    public ResultInfo vailTraderUser(WebAccountVo webAccountVo) {
        if("sale".equals(webAccountVo.getOwner())){
            User user = userMapper.getUserByTraderCustomerId(webAccountVo.getTraderCustomerId(), CommonConstants.ONE);
            if(user != null && webAccountVo.getUserId().equals(user.getUserId())){
                return new ResultInfo(0,"操作成功");
            }
            return new ResultInfo(-1,"该关联客户不在您名下，关联后该注册用户及相应订单将转移到其他销售名下");
        } else {
            User user = userMapper.getUserByTraderCustomerId(webAccountVo.getTraderCustomerId(), CommonConstants.ONE);
            if(user != null && webAccountVo.getUserId().equals(user.getUserId())){
                return new ResultInfo(0,"操作成功");
            }
            return new ResultInfo(-1,"关联客户的归属销售与分配的销售不一致，无法提交");
        }
    }

	@Override
	public Integer getWebAccountIdByMobile(String traderContactTelephone) {
		return webAccountMapper.getWebAccountIdByMobile(traderContactTelephone);
	}

	@Override
	public WebAccount getWebAccontAuthous(WebAccount webAccount) {
		return webAccountMapper.getWebAccontAuthous(webAccount);
	}
	@Autowired
	private WebAccountCertificateMapper certificateMapper;

	@Override
	public Map<String, Object> getCertificateByCategory(Integer webAccountId) {
		WebAccountCertificate queryCertificate = new WebAccountCertificate();
		queryCertificate.setWebAccountId(webAccountId);
		List<WebAccountCertificate> certificates = certificateMapper.getCertificateList(queryCertificate);
		Map<String, Object> result = new HashMap<>();
		if (CollectionUtils.isNotEmpty(certificates)) {
			for (WebAccountCertificate c : certificates) {
				if (c != null && c.getType() != null) {
					switch (c.getType()) {
						//营业执照
						case TraderConstants.TYPE_BUSINESS_CERTIFICATE:
							if (result.get(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE) == null) {
								result.put(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE, new ArrayList<>());
							}
							List businessCertificates = (ArrayList) result.get(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE);
							businessCertificates.add(c);
							break;
						//二类资质
						case TraderConstants.TYPE_SECOND_MEDICAL:
							if (result.get(TraderConstants.ACCOUNT_SECOND_CERTIFICATE) == null) {
								result.put(TraderConstants.ACCOUNT_SECOND_CERTIFICATE, new ArrayList<>());
							}
							List secondCertificates = (ArrayList) result.get(TraderConstants.ACCOUNT_SECOND_CERTIFICATE);
							secondCertificates.add(c);
							break;
						//三类资质
						case TraderConstants.TYPE_THIRD_MEDICAL:
							if (result.get(TraderConstants.ACCOUNT_THIRD_CERTIFICATE) == null) {
								result.put(TraderConstants.ACCOUNT_THIRD_CERTIFICATE, new ArrayList<>());
							}
							List thirdCertificates = (ArrayList) result.get(TraderConstants.ACCOUNT_THIRD_CERTIFICATE);
							thirdCertificates.add(c);
							break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * <b>Description:</b>转移客户资质<br>
	 *
	 * @param
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	@Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public ResultInfo transferCertificate(Integer erpAccountId, Integer traderId, int type, User user) {
		WebAccount webAccount=webAccountMapper.getWebAccountInfo(erpAccountId);
		TraderCustomer traderCustomer=traderCustomerMapper.getCustomerInfo(traderId);
		if(traderCustomer==null||traderCustomer.getTraderCustomerId()==null){
			return new ResultInfo(-1,"该客户不存在");
		}
		if (webAccount != null && webAccount.getBelongPlatform() != null
				&& webAccount.getBelongPlatform() == TraderConstants.PLATFORM_BEIDENG) {
			if (traderCustomer != null && traderCustomer.getCustomerNature() != null
					&& !traderCustomer.getCustomerNature().equals(SysOptionConstant.ID_465)) {
				return new ResultInfo(-1, "客户性质为终端，无法转移。");
			}
		}
		WebAccountCertificate query = new WebAccountCertificate();
		if (type == TraderConstants.TYPE_BUSINESS_CERTIFICATE) {
			//只转移营业执照
			query.setWebAccountId(erpAccountId);
			query.setType(TraderConstants.TYPE_BUSINESS_CERTIFICATE);
			List<WebAccountCertificate> businessCertificate = certificateMapper.getCertificateList(query);
			if (CollectionUtils.isNotEmpty(businessCertificate)) {
				tranferCertificateReal(businessCertificate, traderId, SysOptionConstant.ID_25, user);
			}
			setCustomerAptitudeUncheck(traderCustomer.getTraderCustomerId());
		} else if (type == TraderConstants.TYPE_THIRD_MEDICAL) {
			//只转移三类医疗资质
			query.setWebAccountId(erpAccountId);
			query.setType(TraderConstants.TYPE_THIRD_MEDICAL);
			List<WebAccountCertificate> thirdCertificate = certificateMapper.getCertificateList(query);
			if (CollectionUtils.isNotEmpty(thirdCertificate)) {
				tranferCertificateReal(thirdCertificate, traderId, SysOptionConstant.ID_29, user);
			}
			changeTraderMedicalQualication(traderId);
			setCustomerAptitudeUncheck(traderCustomer.getTraderCustomerId());
		} else if (type == TraderConstants.TYPE_SECOND_MEDICAL) {
			//只转移二类医疗资质
			query.setWebAccountId(erpAccountId);
			query.setType(TraderConstants.TYPE_SECOND_MEDICAL);
			List<WebAccountCertificate> secondCertificate = certificateMapper.getCertificateList(query);
			if (CollectionUtils.isNotEmpty(secondCertificate)) {
				transferSecondCertificateReal(secondCertificate,traderId,user);
			}
			changeTraderMedicalQualication(traderId);
			setCustomerAptitudeUncheck(traderCustomer.getTraderCustomerId());
		} else if (type == TraderConstants.TRANSFER_ALL_CERTIFICATES) {
			//批量转移
			Map<String, Object> map = getCertificateByCategory(erpAccountId);
			if (!map.containsKey(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE)
					&& !map.containsKey(TraderConstants.ACCOUNT_SECOND_CERTIFICATE)
					&& !map.containsKey(TraderConstants.ACCOUNT_THIRD_CERTIFICATE)) {
				return new ResultInfo(-1, "资质图片为空，无法操作。");
			}
			if (map.get(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE) != null) {
				List<WebAccountCertificate> businessCertificate = (ArrayList) map.get(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE);
				tranferCertificateReal(businessCertificate, traderId, SysOptionConstant.ID_25, user);
			}
			if (map.get(TraderConstants.ACCOUNT_SECOND_CERTIFICATE) != null) {
				List certificates = (ArrayList) map.get(TraderConstants.ACCOUNT_SECOND_CERTIFICATE);
				if (CollectionUtils.isNotEmpty(certificates)) {
					transferSecondCertificateReal(certificates,traderId,user);
				}
			}
			if (map.get(TraderConstants.ACCOUNT_THIRD_CERTIFICATE) != null) {
				List<WebAccountCertificate> thirdCertificate = (ArrayList) map.get(TraderConstants.ACCOUNT_THIRD_CERTIFICATE);
				tranferCertificateReal(thirdCertificate, traderId, SysOptionConstant.ID_29, user);
			}
			if (map.containsKey(TraderConstants.ACCOUNT_SECOND_CERTIFICATE) || map.containsKey(TraderConstants.ACCOUNT_THIRD_CERTIFICATE)) {
				changeTraderMedicalQualication(traderId);
			}
			setCustomerAptitudeUncheck(traderCustomer.getTraderCustomerId());
		}
		return new ResultInfo(0, "操作成功");
	}

	@Override
	public Integer getBelongPlatformOfAccount(Integer userId, Integer traderId, Integer registerPlatform) {
		if (traderId != null) {
			//若已关联公司，显示关联的公司的归属平台
			return traderMapper.getTraderInfoByTraderId(traderId).getBelongPlatform();
		} else if (userId != null){
			//若未关联公司但有归属销售，显示归属销售的所属平台；
			return userService.getBelongPlatformOfUser(userId,1);
		} else {
			//若未关联公司且无归属销售，显示注册账号的注册平台；
			return registerPlatform;
		}
	}


	/**
	 * <b>Description:</b>设置客户资质为待审核状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/4
	 */
	private void setCustomerAptitudeUncheck(Integer traderCustomerId){
        traderCustomerService.setCustomerAptitudeUncheck(traderCustomerId);
	}
	/**
	 * <b>Description:</b>修改客户医疗资质合一为否<br>
	 *
	 * @param traderId 客户标识
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	private void changeTraderMedicalQualication(Integer traderId) {
		Trader trader = new Trader();
		trader.setTraderId(traderId);
		trader.setMedicalQualification(0);
		traderMapper.updatePartBySelective(trader);
	}

	/**
	 * <b>Description:</b>转移营业执照信息<br>
	 *
	 * @param certificate 资质信息
	 * @param traderId    客户Id
	 * @param type        资质类型
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	private void tranferCertificateReal(List<WebAccountCertificate> certificates, Integer traderId, Integer type, User user) {
		if (CollectionUtils.isNotEmpty(certificates)) {
			traderCertificateMapper.delTraderCertificateAndByTypeId(traderId, type);
			for (WebAccountCertificate o : certificates) {
				if (o != null) {
					TraderCertificate traderCertificate = initTraderCertificate(o, traderId, type, user);
					certificateMapper.deleteByPrimaryKey(o.getWebAccountCertificateId());
					traderCertificateMapper.insertSelective(traderCertificate);
				}
			}
		}
	}

	/**
	 * <b>Description:</b>转移二类资质<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/4
	 */
	private void transferSecondCertificateReal(List certificates,Integer traderId,User user){
		if (CollectionUtils.isNotEmpty(certificates)) {
			traderCertificateMapper.delTraderCertificateAndByTypeId(traderId, SysOptionConstant.ID_28);
			for (Object o : certificates) {
				if (o != null) {
					WebAccountCertificate c = (WebAccountCertificate) o;
					TraderCertificate traderCertificate = initTraderCertificate(c, traderId, SysOptionConstant.ID_28, user);
					certificateMapper.deleteByPrimaryKey(c.getWebAccountCertificateId());
					traderCertificateMapper.insertSelective(traderCertificate);
				}
			}
		}
	}
	/**
	 * <b>Description:</b>生成客户资质信息<br>
	 *
	 * @param certificate 资质信息
	 * @param traderId    客户Id
	 * @param type        资质类型
	 * @return
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/3
	 */
	private TraderCertificate initTraderCertificate(WebAccountCertificate certificate, Integer traderId, Integer type, User user) {
		TraderCertificate traderCertificate = new TraderCertificate();
		traderCertificate.setSysOptionDefinitionId(type);
		traderCertificate.setAddTime(System.currentTimeMillis());
		traderCertificate.setCreator(user.getUserId());
		traderCertificate.setBegintime(0L);
		traderCertificate.setEndtime(0L);
		traderCertificate.setDomain(certificate.getDomain());
		traderCertificate.setUri(certificate.getUri());
		traderCertificate.setOssResourceId(certificate.getOssId());
		traderCertificate.setTraderId(traderId);
		if(type.equals(SysOptionConstant.ID_25)){
			traderCertificate.setName(TraderConstants.CERTIFICATE_BUSINESS_NAME);
		}else if(type.equals(SysOptionConstant.ID_28)){
			traderCertificate.setName(TraderConstants.CERTIFICATE_SECOND_NAME);
		}else if(type.equals(SysOptionConstant.ID_29)){
			traderCertificate.setName(TraderConstants.CERTIFICATE_THIRD_NAME);
		}
		return traderCertificate;
	}

	@Override
	public ResultInfo updateBDTraderCertificate(BDTraderCertificate bdTraderCertificate) {
		String url = httpUrl + ErpConst.BD_CETIFICATE_UPDATE_URL;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo> TypeRef2 = new TypeReference<ResultInfo>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bdTraderCertificate, clientId, clientKey, TypeRef2);
			if(result == null){
				result=new ResultInfo<>(1100,"操作失败");
			}
			if(result.getCode()==0){
                sendMessage(bdTraderCertificate.getVdUserTel(),TraderConstants.UPADATE_WEBACCOUNT_CERTIFICATE);
            }
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo(1100,"操作失败");
		}
	}

	@Override
	public ResultInfo addBDTraderCertificate(BDTraderCertificate bdTraderCertificate) {
		String url = httpUrl + ErpConst.BD_CETIFICATE_ADD_URL;
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo> TypeRef2 = new TypeReference<ResultInfo>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bdTraderCertificate, clientId, clientKey, TypeRef2);
			if(result == null){
				result=new ResultInfo<>(1100,"操作失败");
			}
            if(result.getCode()==0){
                sendMessage(bdTraderCertificate.getVdUserTel(),TraderConstants.ADD_WEBACCOUNT_CERTIFICATE);
            }
			return result;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo(1100,"操作失败");
		}
	}

    private ResultInfo sendWebaccountCertificateMsg( WebAccountCertificateMsg msg){
        try {
            String[] str = {"njadmin", "2"};
            Map<String, String> map = new HashMap<>();
            map.put("mobile", msg.getMobile());
            MessageUtil.sendMessage(msg.getMessageTemplateId(), msg.getUserIds(), map, msg.getUrl(), str);
            return new ResultInfo(0,"操作成功");
        }catch (Exception ex){
            return new ResultInfo(-1,"操作失败");
        }
    }

    @Autowired
    private RTraderJUserMapper rTraderJUserMapper;
    public void sendMessage(String moblie,int type) {
        WebAccount account=webAccountMapper.getWenAccountInfoByMobile(moblie);
        WebAccountCertificateMsg webAccountCertificateMsg = new WebAccountCertificateMsg();
        webAccountCertificateMsg.setMobile(account.getMobile());
        webAccountCertificateMsg.setUrl(TraderConstants.WEBACCOUNT_DETAIL_URL + account.getErpAccountId());
        List<Integer> userIds = new ArrayList<>();
        RTraderJUser rTraderJUser = rTraderJUserMapper.getUserByTraderId(account.getTraderId());
        if (rTraderJUser != null && rTraderJUser.getUserId() != null && rTraderJUser.getUserId() > 0) {
            userIds.add(rTraderJUser.getUserId());
        }else{
            return;
        }
        webAccountCertificateMsg.setUserIds(userIds);

        try {
            if (type == TraderConstants.ADD_WEBACCOUNT_CERTIFICATE) {
                webAccountCertificateMsg.setMessageTemplateId(100);
            } else if (type == TraderConstants.UPADATE_WEBACCOUNT_CERTIFICATE) {
                webAccountCertificateMsg.setMessageTemplateId(101);
            }
            sendWebaccountCertificateMsg(webAccountCertificateMsg);
        } catch (Exception ex) {
            logger.error("注册用户资质提交，消息提醒失败,erp_webaccount:" + account.getErpAccountId(), ex);
        }
    }
}
