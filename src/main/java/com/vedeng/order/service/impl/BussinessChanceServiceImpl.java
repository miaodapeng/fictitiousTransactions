package com.vedeng.order.service.impl;

import com.common.constants.Contant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.dao.RUserRoleMapper;
import com.vedeng.authorization.dao.UserDetailMapper;
import com.vedeng.authorization.dao.UserLoginLogMapper;
import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.UserDetail;
import com.vedeng.authorization.model.UserLoginLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.http.HttpClientUtils;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.impl.BaseServiceimpl;
import com.vedeng.common.thread.ServiceExecutor;
import com.vedeng.common.thread.ext.ThreadExt;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.MessageUtil;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.order.dao.BussinessChanceMapper;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.phoneticWriting.dao.PhoneticWritingMapper;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import com.vedeng.system.dao.MessageMapper;
import com.vedeng.system.model.Attachment;
import com.vedeng.trader.dao.CommunicateRecordMapper;
import com.vedeng.trader.dao.RTraderJUserMapper;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.RTraderJUser;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("bussinessChanceService")
public class BussinessChanceServiceImpl extends BaseServiceimpl implements BussinessChanceService {
	public static Logger logger = LoggerFactory.getLogger(BussinessChanceServiceImpl.class);

	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Autowired
	@Qualifier("rTraderJUserMapper")
	private RTraderJUserMapper rTraderJUserMapper;

	@Resource
	private CommunicateRecordMapper communicateRecordMapper;
	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;

	@Autowired
	@Qualifier("userLoginLogMapper")
	private UserLoginLogMapper userLoginLogMapper;

	@Autowired
	@Qualifier("userDetailMapper")
	private UserDetailMapper userDetailMapper;

	@Autowired
	@Qualifier("messageMapper")
	private MessageMapper messageMapper;

	@Autowired
	@Qualifier("bussinessChanceMapper")
	private BussinessChanceMapper bussinessChanceMapper;

	@Autowired
	@Qualifier("phoneticWritingMapper")
	private PhoneticWritingMapper phoneticWritingMapper;

	@Value("${oss_url}")
	private String ossUrl;

	@Autowired
	private RUserRoleMapper rUserRoleMapper;


	@Override
	public Map<String, Object> getServiceBussinessChanceListPage(BussinessChanceVo bussinessChance, Page page) {
		// 调用接口
		String url = httpUrl + "order/bussinesschance/getservicebussinesschancelistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BussinessChanceVo>>> TypeRef = new TypeReference<ResultInfo<List<BussinessChanceVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bussinessChance, clientId, clientKey,
					TypeRef, page);
			if (result == null || result.getCode() == -1) {
				return null;
			}
			List<BussinessChanceVo> list = (List<BussinessChanceVo>) result.getData();
			// 归属销售人员信息补充
			if (list.size() > 0) {
				List<Integer> userIds = new ArrayList<>();
				List<Integer> traderIds = new ArrayList<>();
				for (BussinessChanceVo b : list) {
					if (b.getUserId() > 0) {
						userIds.add(b.getUserId());
					}
					if(b.getTraderId() > 0){
						traderIds.add(b.getTraderId());
					}
				}

				List<RTraderJUser> rTraderJUserByTraderIds = new ArrayList<>();
				if(traderIds.size() > 0){
					rTraderJUserByTraderIds = rTraderJUserMapper.getRTraderJUserByTraderIds(traderIds, ErpConst.ONE);
					if(null != rTraderJUserByTraderIds
							&& rTraderJUserByTraderIds.size() > 0){
						for(RTraderJUser r : rTraderJUserByTraderIds){
							userIds.add(r.getUserId());
						}
					}
				}

				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);

					for (BussinessChanceVo b : list) {
						if(b.getTraderId().equals(0)){
							for (User u : userList) {
								if (u.getUserId().equals(b.getUserId())) {
									b.setSaleUser(u.getUsername());
									b.setSaleDeptName(getOrgaNameByUserId(b.getUserId()));
									break;
								}
							}

						}else{
							if(rTraderJUserByTraderIds.size() >0 ){
								Integer user_id = 0;
								for(RTraderJUser r : rTraderJUserByTraderIds){
									if(r.getTraderId().equals(b.getTraderId())){
										user_id = r.getUserId();
										break;
									}
								}
								if(user_id > 0){
									for (User u : userList) {
										if (u.getUserId().equals(user_id)) {
											b.setSaleUser(u.getUsername());
											b.setSaleDeptName(getOrgaNameByUserId(user_id));
											break;
										}
									}
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
			throw new RuntimeException();
		}
	}

	@Override
	public Map<String, Object> getSaleBussinessChanceListPage(BussinessChanceVo bussinessChance, Page page) {
		// 调用接口
		String url = httpUrl + "order/bussinesschance/getsalebussinesschancelistpage.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BussinessChanceVo>>> TypeRef = new TypeReference<ResultInfo<List<BussinessChanceVo>>>() {
		};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bussinessChance, clientId, clientKey,
					TypeRef, page);
			if (result == null || result.getCode() == -1) {
				return null;
			}
			List<BussinessChanceVo> list = (List<BussinessChanceVo>) result.getData();
			List <Integer>businessIdList = new ArrayList<>();
			// 归属销售人员信息补充
			if (list != null && list.size() > 0) {
				List<Integer> userIds = new ArrayList<>();
				List<Integer> traderIds = new ArrayList<>();

				for (BussinessChanceVo b : list) {
					businessIdList.add(b.getBussinessChanceId());
//					b.setSaleDeptName(getOrgNameByOrgId(b.getOrgId()));
//					b.setSaleUser(getUserNameByUserId(b.getUserId()));
					if (b.getUserId() > 0) {
						userIds.add(b.getUserId());
					}

					if(b.getTraderId() > 0){
						traderIds.add(b.getTraderId());
					}
				}

				List<RTraderJUser> rTraderJUserByTraderIds = new ArrayList<>();
				if(traderIds.size() > 0){
					rTraderJUserByTraderIds = rTraderJUserMapper.getRTraderJUserByTraderIds(traderIds, ErpConst.ONE);
					if(null != rTraderJUserByTraderIds
							&& rTraderJUserByTraderIds.size() > 0){
						for(RTraderJUser r : rTraderJUserByTraderIds){
							userIds.add(r.getUserId());
						}
					}
				}
				if (userIds.size() > 0) {
					List<User> userList = userMapper.getUserByUserIds(userIds);

					for (BussinessChanceVo b : list) {

						if(b.getTraderId().equals(0)){
							for (User u : userList) {
								if (u.getUserId().equals(b.getUserId())) {
									b.setSaleUser(u.getUsername());
									b.setSaleDeptName(getOrgaNameByUserId(b.getUserId()));
									break;
								}
							}
						}else{
							if(rTraderJUserByTraderIds.size() >0 ){
								Integer user_id = 0;
								for(RTraderJUser r : rTraderJUserByTraderIds){
									if(r.getTraderId().equals(b.getTraderId())){
										user_id = r.getUserId();
										break;
									}
								}
								if(user_id > 0){
									for (User u : userList) {
										if (u.getUserId().equals(user_id)) {
											b.setSaleUser(u.getUsername());
											b.setSaleDeptName(getOrgaNameByUserId(user_id));
											break;
										}
									}
								}

							}
						}
					}
				}


			}
			//补充商机备注信息
			CommunicateRecord comRe = new CommunicateRecord();
			if(CollectionUtils.isNotEmpty(businessIdList)){
				comRe.setBussinessChanceIds(businessIdList);
				List<CommunicateRecord> recordList = communicateRecordMapper.getCommunicateList(comRe);
				for(int i=0;i<list.size();i++){
					for(int j=0;j<recordList.size();j++){
						if(list.get(i).getBussinessChanceId().equals(recordList.get(j).getRelatedId())){
							if(StringUtil.isNotBlank(recordList.get(j).getContactContent())){
								list.get(i).setComments(recordList.get(j).getContactContent());
								break;
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
			throw new RuntimeException();
		}
	}

	@Override
	public ResultInfo<?> assignBussinessChance(BussinessChanceVo bussinessChanceVo, HttpSession session)
	{
		Long time = DateUtil.sysTimeMillis();

		bussinessChanceVo.setAssignTime(time);
		bussinessChanceVo.setFirstViewTime(new Long(0));//重置第一次查看时间

		// 接口调用
		String url = httpUrl + "order/bussinesschance/assignbussinesschance.htm";
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<List<BussinessChanceVo>>> TypeRef = new TypeReference<ResultInfo<List<BussinessChanceVo>>>() {};
		// 分配销售userId
		Integer saleUserId = bussinessChanceVo.getUserId();
		try
		{
			@SuppressWarnings("unchecked")
			ResultInfo<List<BussinessChanceVo>> result = (ResultInfo<List<BussinessChanceVo>>) HttpClientUtils.post(url, bussinessChanceVo, clientId, clientKey, TypeRef);
			if(null != result && result.getCode() == 0)
			{
				List<BussinessChanceVo> resultList = result.getData();
				if(null != resultList && resultList.size() > 0)
				{
					for(BussinessChanceVo vo : resultList)
					{
						if(null == vo)
							continue;
						//商机分配销售
						List<Integer> userIdList = new LinkedList<Integer>();
						userIdList.add(saleUserId);
						Map<String,String> mes_map = new HashMap<>();
						mes_map.put("bussinessChanceNo", vo.getBussinessChanceNo());
						//商机分配给销售后，发送消息给销售
						MessageUtil.sendMessage(69, userIdList, mes_map, "./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId="+vo.getBussinessChanceId()+"&traderId=0");

						// 发送短信
						// modify by Franlin at 2018-07-31 for[581 短信发送机制：未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信] begin
						UserDetail userDetail = userDetailMapper.getUserDetail(saleUserId);
						// 查询不到该销售人员的信息和手机号，不发送短信
						if(null != userDetail && null != userDetail.getMobile())
						{
							// 商机分配成功后,未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信	sam	新增功能
							UserLoginLog log = new UserLoginLog();
							log.setUserId(saleUserId);
							log.setAccessTime(DateUtil.convertLong(DateUtil.parseString(new Date(), DateUtil.DATE_FORMAT) + " 00:00:00", DateUtil.TIME_FORMAT));
							UserLoginLog logResult = userLoginLogMapper.queryUserLogOrOutInfo(log);

							ThreadExt ext = new ThreadExt();
							ext.setAcceptTelPhone(userDetail.getMobile());
							ext.setTraderContactName(vo.getTraderContactName());
							String conTel = StringUtil.isBlank(vo.getMobile()) ? ( StringUtil.isBlank(vo.getTelephone()) ? ( StringUtil.isBlank(vo.getOtherContact()) ? "" : vo.getOtherContact() ) : vo.getTelephone() ) : vo.getMobile();
							ext.setTraderContactTelephone(conTel);
							ext.setProductName( StringUtil.isBlank(vo.getGoodsName()) ? "" : vo.getGoodsName() );
							ext.setAcceptUserId(saleUserId);
							ext.setBussinessChanceId(vo.getBussinessChanceId());
							// null：无登录登出则为不在线/登出/登录错误
							if(null == logResult || null == logResult.getAccessType() || 3 == logResult.getAccessType() || 2 == logResult.getAccessType())
							{
								// 异步立即发送
								new ServiceExecutor(messageMapper, ext).execute(ServiceExecutor.SEND_SMS_TYPE);
							}
							else
							{
								// 异步等待10分钟发送
								new ServiceExecutor(messageMapper, ext, 10, TimeUnit.MINUTES).delayExecute(ServiceExecutor.SEND_SMS_TYPE);
							}
						}
						// modify by Franlin at 2018-07-31 for[581 短信发送机制：未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信] end
					}
				}
			}
			return result;
		}
		catch (IOException e)
		{
			return null;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 保存商机
	 *
	 * @param bussinessChance
	 * @param user
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月26日 下午2:17:38
	 */
	@Override
	public ResultInfo<?> saveBussinessChance(BussinessChance bussinessChance, User user, Attachment attachment)
	{
		//商机对应销售（售后添加商机时，需分配销售人员；；销售管理商机页面无指定人员）
		Integer bussinessChanceUserId = bussinessChance.getUserId();
		Map<String, Object> map = new HashMap<>();
		if (bussinessChance.getBussinessChanceId() == null || bussinessChance.getBussinessChanceId()==0) {
			bussinessChance.setCreator(user.getUserId());
			bussinessChance.setAddTime(DateUtil.sysTimeMillis());
			if(bussinessChance.getType().equals(SysOptionConstant.ID_392))
			{
				bussinessChance.setOrgId(user.getOrgId());
				bussinessChance.setUserId(user.getUserId());
			}
		}

		map.put("bussinessChance", bussinessChance);
		bussinessChance.setUpdater(user.getUserId());
		bussinessChance.setModTime(DateUtil.sysTimeMillis());

		if (attachment != null) {
			attachment.setCreator(user.getUserId());
			attachment.setAddTime(DateUtil.sysTimeMillis());
			attachment.setAttachmentFunction(SysOptionConstant.ID_463);

			//根据attachmentId来区分附件的存储位置，当attachmentId = 1时，存储在oss上；否则存储在ftp server上
			if (attachment.getAttachmentId() != null && attachment.getAttachmentId() == -1){
				attachment.setDomain(ossUrl);
				//attachmentId置为空
				attachment.setAttachmentId(null);
			} else {
				attachment.setDomain(picUrl);
				String str = attachment.getUri().substring(attachment.getUri().lastIndexOf(".") + 1);
				if (str.equals("jpg") || str.equals("png") || str.equals("gif")) {
					attachment.setAttachmentType(SysOptionConstant.ID_460);
				} else {
					attachment.setAttachmentType(SysOptionConstant.ID_461);
				}
			}

			map.put("attachment", attachment);
		}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<BussinessChance>> TypeRef = new TypeReference<ResultInfo<BussinessChance>>() {};
		try
		{
			ResultInfo<BussinessChance> result = (ResultInfo<BussinessChance>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_BUSSNESS_CHANCE, map, clientId, clientKey, TypeRef);

			if(result != null && result.getCode() == 0 && result.getData() != null && null != bussinessChanceUserId)
			{
				BussinessChance bc = (BussinessChance)result.getData();

				if (null != bc){

					List<Integer> userIdList = new ArrayList<Integer>();
					Map<String,String> mesMap = new HashMap<>();
					mesMap.put("bussinessChanceNo", bc.getBussinessChanceNo());

					if (bc.getUserId() == null || bc.getUserId() == 0) {
						//如果商机没有分配到销售，则给售前总机角色的账户发送站内消息
						userIdList = rUserRoleMapper.getUserIdList(Collections.singletonList(17));
					} else {
						userIdList.add(bc.getUserId());
					}
					//推送消息，如果商机关联到销售，则向销售推送消息，如果没有关联销售，则向售前总机角色推送消息
					MessageUtil.sendMessage(69, userIdList, mesMap, "./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId="+bc.getBussinessChanceId()+"&traderId=0");

					// 关联到销售，则发送短信
					// modify by Franlin at 2018-07-31 for[581 短信发送机制：未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信] begin
					if (bc.getUserId() != null && bc.getUserId() > 0) {
						Integer saleUserId = bc.getUserId();
						UserDetail userDetail = userDetailMapper.getUserDetail(saleUserId);
						// 查询不到该销售人员的信息和手机号，不发送短信
						if(null != userDetail && null != userDetail.getMobile())
						{
							// 商机分配成功后,未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信	sam	新增功能
							UserLoginLog log = new UserLoginLog();
							log.setUserId(saleUserId);
							log.setAccessTime(DateUtil.convertLong(DateUtil.parseString(new Date(), DateUtil.DATE_FORMAT) + " 00:00:00", DateUtil.TIME_FORMAT));
							UserLoginLog logResult = userLoginLogMapper.queryUserLogOrOutInfo(log);

							ThreadExt ext = new ThreadExt();
							ext.setAcceptTelPhone(userDetail.getMobile());
							ext.setTraderContactName(bc.getTraderContactName());
							String conTel = StringUtil.isBlank(bc.getMobile()) ? ( StringUtil.isBlank(bc.getTelephone()) ? ( StringUtil.isBlank(bc.getOtherContact()) ? "" : bc.getOtherContact() ) : bc.getTelephone() ) : bc.getMobile();
							ext.setTraderContactTelephone(conTel);
							ext.setProductName( StringUtil.isBlank(bc.getGoodsName()) ? "" : bc.getGoodsName() );
							ext.setAcceptUserId(saleUserId);
							ext.setBussinessChanceId(bc.getBussinessChanceId());
							// 登出/登录错误
							if(null == logResult || null == logResult.getAccessType() || 3 == logResult.getAccessType() || 2 == logResult.getAccessType())
							{
								// 异步立即发送
								new ServiceExecutor(messageMapper, ext).execute(ServiceExecutor.SEND_SMS_TYPE);
							}
							else
							{
								// 异步等待10分钟发送
								new ServiceExecutor(messageMapper, ext, 10, TimeUnit.MINUTES).delayExecute(ServiceExecutor.SEND_SMS_TYPE);
							}
						}
						// modify by Franlin at 2018-07-31 for[581 短信发送机制：未登陆erp的销售员，立即发送短信，在线的销售员如果10分钟未处理商机询价，推送短信] end
					}
				}

			}
			return result;
		}
		catch (IOException e)
		{
			return null;
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 查询售后商机详情
	 *
	 * @param bussinessChance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月27日 下午6:54:40
	 */
	@Override
	public Map<String, Object> getAfterSalesDetail(BussinessChance bussinessChance, Page page) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_BUSSNESS_CHANCE_DETAIL,
					bussinessChance, clientId, clientKey, TypeRef2);
			Map<String, Object> map = new HashMap<>();
			JSONObject json = JSONObject.fromObject(result2.getData());
			BussinessChanceVo bcv = (BussinessChanceVo) JSONObject.toBean(json, BussinessChanceVo.class);
			if(ObjectUtils.notEmpty(bcv.getCreator())){
				bcv.setCreatorName(userMapper.selectByPrimaryKey(bcv.getCreator()).getUsername());
			}

			if(ObjectUtils.notEmpty(bcv.getUserId())){
				User user = userMapper.selectByPrimaryKey(bcv.getUserId());
				bcv.setSalerName(user==null?null:user.getUsername());
			}
			if (ObjectUtils.notEmpty(bcv.getAreaId())) {
				bcv.setAreas(getAddressByAreaId(bcv.getAreaId()));
			}
			map.put("bussinessChanceVo", bcv);
			CommunicateRecord cr = new CommunicateRecord();
			cr.setBussinessChanceId(bcv.getBussinessChanceId());
			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("communicateRecord", cr);
			paraMap.put("page", page);
			List<CommunicateRecord> communicateList = new ArrayList<>();
			try {
				communicateList = traderCustomerService.getCommunicateRecordListPage(cr, page);
				for (int i=0;i<communicateList.size();i++){
					//判断是否转译完成
					PhoneticWriting phoneticWriting = phoneticWritingMapper.getPhoneticWriting(communicateList.get(i).getCommunicateRecordId());
					if(phoneticWriting != null){
						if(StringUtils.isNotBlank(phoneticWriting.getOriginalContent())){
							communicateList.get(i).setIsTranslation(1);
						}else {
							communicateList.get(i).setIsTranslation(0);
						}
					}else{
						communicateList.get(i).setIsTranslation(0);
					}
				}
			} catch (Exception e) {

				logger.error(Contant.ERROR_MSG, e);
			}
			if (communicateList != null && communicateList.size() > 0) {
				map.put("communicateList", communicateList);
			}
			return map;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 跳转编辑售后商机页面
	 * @param bussinessChance
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年6月29日 下午1:08:47
	 */
	@Override
	public BussinessChanceVo toAfterSalesEditPage(BussinessChance bussinessChance) {
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.GET_BUSSNESS_CHANCE_DETAIL,
					bussinessChance, clientId, clientKey, TypeRef2);
			JSONObject json = JSONObject.fromObject(result2.getData());
			BussinessChanceVo bcv = (BussinessChanceVo) JSONObject.toBean(json, BussinessChanceVo.class);
//			bcv.setCreatorName(userMapper.selectByPrimaryKey(bcv.getCreator()).getUsername());
//			bcv.setSalerName(userMapper.selectByPrimaryKey(bcv.getUserId()).getUsername());
			if (bcv.getAreaId() != null && bcv.getAreaId() != 0) {
				bcv.setAreas(getAddressByAreaId(bcv.getAreaId()));
			}
			return bcv;
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 保存确认的客户信息
	 * @param bussinessChance
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年6月30日 下午5:42:28
	 */
	@Override
	public TraderCustomerVo saveConfirmCustomer(BussinessChance bussinessChance, User user,TraderContact traderContact) {
		bussinessChance.setOrgId(user.getOrgId());
		bussinessChance.setUpdater(user.getUserId());
		bussinessChance.setModTime(DateUtil.sysTimeMillis());
		Map<String, Object> paraMap=new HashMap<>();
		paraMap.put("bussinessChance", bussinessChance);
		if((bussinessChance.getTraderContactId()==null||bussinessChance.getTraderContactId()==0)&&traderContact!=null){
			traderContact.setCreator(user.getUserId());
			traderContact.setAddTime(DateUtil.sysTimeMillis());
			traderContact.setUpdater(user.getUserId());
			traderContact.setModTime(DateUtil.sysTimeMillis());
			traderContact.setTraderId(bussinessChance.getTraderId());
			traderContact.setIsOnJob(ErpConst.ONE);
			traderContact.setTraderType(ErpConst.ONE);
			traderContact.setIsEnable(ErpConst.ONE);
			paraMap.put("traderContact", traderContact);
		}
		// 定义反序列化 数据格式
		final TypeReference<ResultInfo<?>> TypeRef2 = new TypeReference<ResultInfo<?>>() {
		};
		try {
			ResultInfo<?> result2 = (ResultInfo<?>) HttpClientUtils.post(httpUrl + ErpConst.SAVE_CONFIRM_CUSTOMER, paraMap,
					clientId, clientKey, TypeRef2);
			JSONObject josn=JSONObject.fromObject(result2.getData());
			TraderCustomerVo tcv=(TraderCustomerVo) JSONObject.toBean(josn, TraderCustomerVo.class);
			return tcv;
		} catch (IOException e) {
			return null;
		}
	}
	/**
	 * <b>Description:</b><br> 获取上传文件的域名
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月17日 下午3:47:39
	 */
	@Override
	public String getUploadDomain() {
		return picUrl;
	}

	@Override
	public ResultInfo editBussinessChance(BussinessChance bussinessChance) {
		String url = httpUrl + "order/bussinesschance/editbussinesschance.htm";
		final TypeReference<ResultInfo<?>> TypeRef = new TypeReference<ResultInfo<?>>() {};
		try {
			ResultInfo<?> result = (ResultInfo<?>) HttpClientUtils.post(url, bussinessChance, clientId, clientKey, TypeRef);
			if (result.getCode() == 0) {
				return new ResultInfo(0, "操作成功");
			}else{
				return new ResultInfo();
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo();
		}
	}

	@Override
	public BussinessChance getBussinessChanceInfo(BussinessChance bussinessChance) {
		return bussinessChanceMapper.selectByPrimaryKey(bussinessChance.getBussinessChanceId());
	}

	@Override
	public Boolean saveAddBussinessStatus(BussinessChance bussinessChance) {
		Boolean flag = false;
		bussinessChance.setType(null);
		int i = bussinessChanceMapper.updateByPrimaryKeySelective(bussinessChance);
		if(i>0){
			flag = true;
		}
		return flag;
	}

}
