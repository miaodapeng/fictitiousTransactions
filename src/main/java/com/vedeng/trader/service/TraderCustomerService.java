package com.vedeng.trader.service;

import com.newtask.model.YXGTraderAptitude;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.logistics.model.FileDelivery;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.model.vo.AccountSalerToGo;
import com.vedeng.trader.model.*;
import com.vedeng.trader.model.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <b>Description:</b><br>
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.trader.service <br>
 *       <b>ClassName:</b> TraderCustomerService <br>
 *       <b>Date:</b> 2017年5月12日 上午10:13:14
 */
public interface TraderCustomerService extends BaseService {


	/**
	 * <b>Description:</b>更改归属人<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/20
	 */
	int changeTraderOwner(RTraderJUser rTraderJUser);
	/**
	 * <b>Description:</b>获取客户财务信息<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/11
	 */
	TraderFinanceVo getTraderFinance(TraderFinanceVo finance);
	/**
	 * <b>Description:</b>将税号同步到医械购平台<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/5
	 */
	ResultInfo syncTraderFinanceToPlatformOfYxg(TraderFinance traderFinance);
	/**
	 * <b>Description:</b>根据客户标识查询订单覆盖的科室<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> ${date} ${time}
	 */
	String getOrderMedicalOfficeStr(TraderOrderGoods param);
    /**
     * <b>Description:</b><br> 根据客户标识获取客户备注信息
     * @param traderId
     * @return
     * @Note
     * <b>Author:</b>
     * <br><b>Date:</b>
     */
	CustomerAptitudeComment getCustomerAptitudeCommentByTraderId(Integer traderId);

	/**
	 * <b>Description:</b><br> 添加分销客户资质备注
	 * @param
	 * @return
	 * @Note
	 * <b>Author:</b>
	 * <br><b>Date:</b>
	 */
	int addCustomerAptitudeComment(CustomerAptitudeComment comment);
	/**
	 * <b>Description:</b><br> 根据客户id获取归属的销售
	 * @param traderId 客户id
	 * @return
	 * @Note
	 * <b>Author:</b>
	 * <br><b>Date:</b>
	 */
	User getPersonalUser(Integer traderId);
	/**
	 * <b>Description:</b><br>
	 * 客户分页
	 * 
	 * @param traderCustomerVo
	 * @param page
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月18日 下午1:22:49
	 */
	Map<String, Object> getTraderCustomerVoPage(TraderCustomerVo traderCustomerVo, Page page, List<User> userList);

	/**
	 * <b>Description:</b><br>
	 * 获取子集分类
	 * 
	 * @param traderCustomerCategory
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月11日 上午9:17:10
	 */
	List<TraderCustomerCategory> getTraderCustomerCategoryByParentId(TraderCustomerCategory traderCustomerCategory);

	/**
	 * <b>Description:</b><br>
	 * 客户属性
	 * 
	 * @param traderCustomerAttributeCategory
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月11日 下午2:46:33
	 */
	List<TraderCustomerAttributeCategory> getTraderCustomerAttributeByCategoryId(
			TraderCustomerAttributeCategory traderCustomerAttributeCategory);

	/**
	 * <b>Description:</b><br>
	 * 子集属性
	 * 
	 * @param traderCustomerAttributeCategory
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月11日 下午2:46:33
	 */
	List<TraderCustomerAttributeCategory> getTraderCustomerChildAttribute(
			TraderCustomerAttributeCategory traderCustomerAttributeCategory);

	/**
	 * <b>Description:</b><br>
	 * 保存客户信息
	 * 
	 * @param trader
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月15日 上午11:02:20
	 */
	TraderCustomer saveCustomer(Trader trader, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 客户基本信息
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月16日 上午8:47:16
	 */
	TraderCustomerVo getTraderCustomerBaseInfo(TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 客户基本信息编辑
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月17日 下午3:32:47
	 */
	TraderCustomer getTraderCustomerEditBaseInfo(TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 客户管理信息
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月16日 下午5:30:05
	 */
	TraderCustomerVo getTraderCustomerManageInfo(TraderCustomer traderCustomer, HttpSession session);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 客户管理信息
	 * 
	 * @param traderCustomer
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Cooper <br>
	 *       <b>Date:</b> 2018年5月30日 下午3:50:37
	 */
	TraderCustomerVo getTraderCustomerManageInfoSeconed(TraderCustomer traderCustomer, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 保存编辑管理信息
	 * 
	 * @param traderCustomer
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月17日 上午11:22:36
	 */
	TraderCustomer saveEditManageInfo(TraderCustomer traderCustomer, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 是否置顶
	 * 
	 * @param id
	 * @param isTop
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo<?> isStick(TraderCustomer traderCustomer, User user);

	/**
	 * <b>Description:</b><br>
	 * 是否禁用
	 * 
	 * @param id
	 * @param isTop
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo<?> isDisabled(User user, TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 转移联系人
	 * 
	 * @param id
	 * @param isTop
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo<?> transferContact(TraderContact traderContact, User user);

	/**
	 * <b>Description:</b><br>
	 * 是否禁用联系人
	 * 
	 * @param id
	 * @param isTop
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo<?> isDisabledContact(TraderContact traderContact, User user);

	/**
	 * <b>Description:</b><br>
	 * 设置默认联系人
	 * 
	 * @param id
	 * @param isTop
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo<?> isDefaultContact(TraderContact traderContact, User user);

	/**
	 * <b>Description:</b><br>
	 * 是否禁用客户地址
	 * 
	 * @param id
	 * @param isTop
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:41:36
	 */
	ResultInfo<?> isDisabledAddress(TraderAddress traderAddress, User user);

	/**
	 * <b>Description:</b><br>
	 * 设置客户的默认地址
	 * 
	 * @param traderAddress
	 * @param user
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月27日 下午3:58:49
	 */
	ResultInfo<?> isDefaultAddress(TraderAddress traderAddress, User user);

	/**
	 * <b>Description:</b><br>
	 * 保存编辑基本信息
	 * 
	 * @param trader
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月18日 上午8:44:18
	 */
	TraderCustomer saveEditBaseInfo(Trader trader, HttpServletRequest request, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 查询交易者
	 * 
	 * @param trader
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月22日 上午10:33:03
	 */
	Trader getTraderByTraderName(Trader trader, HttpSession session);

	Trader getTraderByTraderName(Trader trader, Integer comId);

	/**
	 * <b>Description:</b><br>
	 * 沟通记录
	 * 
	 * @param communicateRecord
	 * @param page
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月23日 上午9:15:11
	 */
	List<CommunicateRecord> getCommunicateRecordListPage(CommunicateRecord communicateRecord, Page page);

	/**
	 * <b>Description:</b><br>
	 * 沟通记录(不分页)
	 * 
	 * @param communicateRecord
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年8月24日 上午11:32:29
	 */
	List<CommunicateRecord> getCommunicateRecordList(CommunicateRecord communicateRecord);

	/**
	 * <b>Description:</b><br>
	 * 获取当前客户的联系人
	 * 
	 * @param traderCustomerId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月23日 上午10:00:55
	 */
	Map<String, Object> getTraderContactVoList(TraderContactVo traderContactVo);

	/**
	 * <b>Description:</b><br>
	 * 保存联系人
	 * 
	 * @param traderContact
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月23日 下午3:56:30
	 */
	Integer saveTraderContact(TraderContact traderContact, User user);

	/**
	 * <b>Description:</b><br>
	 * 保存地址
	 * 
	 * @param traderAddress
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月23日 下午3:56:30
	 */
	ResultInfo<?> saveTraderAddress(TraderAddress traderAddress, User user);

	/**
	 * <b>Description:</b><br>
	 * 获取联系人
	 * 
	 * @param traderContactId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:28:20
	 */
	TraderContact getTraderContactById(Integer traderContactId);

	/**
	 * <b>Description:</b><br>
	 * 查看联系人的详情包括行业背景
	 * 
	 * @param traderContact
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月5日 下午2:49:21
	 */
	Map<String, Object> viewTraderContact(TraderContact traderContact);

	/**
	 * <b>Description:</b><br>
	 * 保存新增客户联系人的行业背景
	 * 
	 * @param traderContactExperience
	 * @param tcebaList
	 * @param tcebbList
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月6日 下午5:14:59
	 */
	ResultInfo<?> saveAddContactExperience(TraderContactExperience traderContactExperience, User user,
			List<TraderContactExperienceBussinessArea> tcebaList,
			List<TraderContactExperienceBussinessBrand> tcebbList);

	/**
	 * <b>Description:</b><br>
	 * 删除联系人的行业背景
	 * 
	 * @param traderContactExperience
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月6日 下午5:14:59
	 */
	ResultInfo<?> delContactExperience(TraderContactExperience traderContactExperience);

	/**
	 * <b>Description:</b><br>
	 * 交易者联系人
	 * 
	 * @param traderContact
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月23日 下午3:21:28
	 */
	List<TraderContact> getTraderContact(TraderContact traderContact);

	/**
	 * <b>Description:</b><br>
	 * 联系人背景
	 * 
	 * @param traderContact
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月23日 下午3:21:28
	 */
	Map<String, Object> getTraderContactExperience(TraderContactExperience traderContactExperience);

	/**
	 * <b>Description:</b><br>
	 * 交易者地址
	 * 
	 * @param traderAddress
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月23日 下午3:21:28
	 */
	TraderAddress getTraderAddress(TraderAddress traderAddress);

	/**
	 * <b>Description:</b><br>
	 * 新增沟通记录
	 * 
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月23日 下午4:28:07
	 */
	Boolean saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, HttpSession session)
			throws Exception;

	/**
	 * <b>Description:</b><br>
	 * 查询沟通
	 * 
	 * @param communicateRecord
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午1:37:06
	 */
	CommunicateRecord getCommunicate(CommunicateRecord communicateRecord);

	/**
	 * <b>Description:</b><br>
	 * 保存编辑沟通
	 * 
	 * @param communicateRecord
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月24日 下午2:38:31
	 */
	Boolean saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, HttpSession session)
			throws Exception;

	/**
	 * <b>Description:</b><br>
	 * 获取客户的财务与资质信息
	 * 
	 * @param traderId
	 * @param page
	 * @param queryType
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月31日 下午4:34:31
	 */
	Map<String, Object> getFinanceAndAptitudeByTraderId(TraderCertificateVo traderCertificateVo, String queryType);

	/**
	 * <b>Description:</b><br>
	 * 查询交易者的账期分页信息
	 * 
	 * @param traderVo
	 * @param page
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年11月10日 下午4:13:12
	 */
	Map<String, Object> getAmountBillListPage(TraderVo traderVo, Page page);

	/**
	 * <b>Description:</b><br>
	 * 查询交易者的交易流水分页信息
	 * 
	 * @param traderVo
	 * @param page
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年11月10日 下午4:13:12
	 */
	Map<String, Object> getCapitalBillListPage(TraderVo traderVo, Page page);

	/**
	 * <b>Description:</b><br>
	 * 保存资质
	 * 
	 * @param tcvList
	 * @param tcmcList
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月5日 下午4:58:30
	 */
	ResultInfo<?> saveMedicaAptitude(TraderVo traderVo, List<TraderCertificateVo> tcvList,
			List<TraderMedicalCategory> twomcList, List<TraderMedicalCategory> threemcList);

	/**
	 * <b>Description:</b><br>
	 * 保存资质
	 *
	 * @param tcvList
	 * @param tcmcList
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月5日 下午4:58:30
	 */
	ResultInfo<?> saveNewMedicaAptitude(TraderVo traderVo, List<TraderCertificateVo> tcvList,
			List<TraderMedicalCategory> twomcList, List<TraderMedicalCategory> threemcList
			,List<TraderMedicalCategory> newTwomcList,List<TraderMedicalCategory> newThreemcList);


	/**
	 * <b>Description:</b><br>
	 * 查询客户归属
	 * 
	 * @param rTraderJUser
	 * @param page
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月6日 下午2:19:07
	 */
	Map<String, Object> getUserTraderByTraderNameListPage(RTraderJUser rTraderJUser, Page page);

	/**
	 * <b>Description:</b><br>
	 * 查询用户客户列表
	 * 
	 * @param rTraderJUser
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月6日 下午3:03:34
	 */
	List<RTraderJUser> getUserCustomerNum(RTraderJUser rTraderJUser, Integer userId);

	/**
	 * <b>Description:</b><br>
	 * 单个分配
	 * 
	 * @param traderId
	 * @param single_to_user
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月6日 下午1:29:09
	 */
	Boolean assignSingleCustomer(Integer traderId, Integer single_to_user,Integer companyId);

	/**
	 * <b>Description:</b><br>
	 * 批量分配
	 * 
	 * @param from_user
	 * @param batch_to_user
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月6日 下午1:30:03
	 */
	Boolean assignBatchCustomer(Integer companyId, Integer from_user, Integer batch_to_user, Integer areaId,
			String areaIds);

	/**
	 * <b>Description:</b><br>
	 * 获取客户财务信息
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月7日 下午3:16:24
	 */
	TraderFinanceVo getTraderFinanceByTraderId(TraderFinanceVo traderFinance);

	/**
	 * <b>Description:</b><br>
	 * 删除供应商的银行帐号
	 * 
	 * @param traderFinance
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年12月21日 下午5:06:45
	 */
	ResultInfo<?> delSupplierBank(TraderFinance traderFinance);

	/**
	 * <b>Description:</b><br>
	 * 保存客户财务信息
	 * 
	 * @param traderFinance
	 * @param user
	 * @param uri
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月7日 下午4:12:18
	 */
	ResultInfo<?> saveCustomerFinance(TraderFinance traderFinance, User user);

	/**
	 * <b>Description:</b><br>
	 * 账期信息
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月21日 下午1:00:12
	 */
	TraderCustomer getTraderCustomerForAccountPeriod(TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 客户最近一次账期申请信息
	 * 
	 * @param traderCustomerId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月21日 下午1:50:31
	 */
	TraderAccountPeriodApply getTraderCustomerLastAccountPeriodApply(Integer traderId);

	/**
	 * <b>Description:</b><br>
	 * 保存账期申请
	 * 
	 * @param traderAccountPeriodApply
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月21日 下午2:32:39
	 */
	ResultInfo<?> saveAccountPeriodApply(TraderAccountPeriodApply traderAccountPeriodApply, HttpSession session);

	/**
	 * <b>Description:</b><br>
	 * 查询客户所属分销还是终端
	 * 
	 * @param traderId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年6月26日 下午3:53:54
	 */
	Integer getCustomerCategory(Integer traderId);

	/**
	 * <b>Description:</b><br>
	 * 获取客户的交易信息
	 * 
	 * @param traderId
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年6月26日 下午1:51:03
	 */
	TraderCustomerVo getCustomerBussinessInfo(Integer traderId);

	/**
	 * <b>Description:</b><br>
	 * 查询终端客户列表
	 * 
	 * @param vo
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月27日 下午3:13:52
	 */
	Map<String, Object> getTerminalPageList(TraderCustomerVo vo, Page page);

	/**
	 * <b>Description:</b><br>
	 * 客户基本信息
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月16日 上午8:47:16
	 */
	TraderCustomerVo getTraderCustomerVo(TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 查询客户列表（弹框简单版）
	 * 
	 * @param traderCustomerVo
	 * @param page
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2017年7月7日 下午4:48:14
	 */
	Map<String, Object> searchCustomerPageList(TraderCustomerVo traderCustomerVo, Page page);

	/**
	 * <b>Description:</b><br>
	 * 根据客户交易者IDor客户ID获取客户基本表信息
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年7月28日 上午9:39:43
	 */
	TraderCustomerVo getCustomerInfoByTraderCustomer(TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 获取客户基本信息
	 * 
	 * @param traderId
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年8月2日 下午1:54:07
	 */
	TraderCustomerVo getTraderCustomerInfo(Integer traderId);

	/**
	 * <b>Description:</b><br>
	 * 批量新增客户
	 * 
	 * @param list
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年11月28日 下午2:32:40
	 */
	ResultInfo<?> saveUplodeBatchCustomer(List<Trader> list);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 根据主键获取
	 * 
	 * @param accountPeriodDaysApplyInfo
	 * @return
	 * @Note <b>Author:</b> Administrator <br>
	 *       <b>Date:</b> 2017年12月2日 下午2:44:20
	 */
	TraderAccountPeriodApply getAccountPeriodDaysApplyInfo(Integer accountPeriodDaysApplyId);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 查询天眼查详情
	 * 
	 * @param i
	 * @param traderName
	 * @return
	 * @Note <b>Author:</b> scott <br>
	 *       <b>Date:</b> 2018年1月12日 下午1:11:18
	 */
	TraderInfoTyc getTycInfo(int i, String traderName);

	/**
	 * <b>Description:</b><br>
	 * 获取交易者财务信息列表
	 * 
	 * @param tf
	 * @return
	 * @Note <b>Author:</b> leo.yang <br>
	 *       <b>Date:</b> 2018年1月12日 下午1:03:44
	 */
	List<TraderFinance> getTraderCustomerFinanceList(TraderFinance tf);

	/**
	 * <b>Description:</b><br>
	 * 客户重置为待审核
	 * 
	 * @param traderCustomer
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年1月23日 上午11:51:31
	 */
	ResultInfo<?> restVerify(TraderCustomer traderCustomer);

	/**
	 * <b>Description:</b><br>
	 * 交易记录
	 * 
	 * @param saleorderGoodsVo
	 * @param page
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2018年1月25日 下午2:46:24
	 */
	Map<String, Object> getBusinessListPage(SaleorderGoodsVo saleorderGoodsVo, Page page);

	/**
	 * 
	 * <b>Description:</b><br>
	 * 更新客户名称
	 * 
	 * @param trader
	 * @return
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2018年6月28日 下午1:29:21
	 */
	ResultInfo<?> saveTraderName(Trader trader);

	/**
	 * 订单覆盖品类品牌区域 <b>Description:</b><br>
	 * 
	 * @param traderOrderGoods
	 * @return
	 * @Note <b>Author:</b> Bill <br>
	 *       <b>Date:</b> 2018年7月6日 下午2:19:50
	 */
	TraderCustomerVo getOrderCoverInfo(TraderOrderGoods traderOrderGoods);

	/**
	 * @param traderId
	 * @return
	 */
	TraderCustomerVo getCustomerInfo(Integer traderId);

	/**
	 * 
	 * <b>Description:</b>查询客户/供应商的联系人
	 * 
	 * @param fileDelivery
	 * @return TraderContact
	 * @Note <b>Author：</b> scott.zhu <b>Date:</b> 2019年3月25日 下午4:38:47
	 */
	TraderContact getTraderContactInfo(FileDelivery fileDelivery);

	/**
	 * 
	 * <b>Description: 根据traderId获取基本客户信息</b><br> 
	 * @param traderId
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2019年5月13日 下午1:31:25 </b>
	 */
	Trader getBaseTraderByTraderId(Integer traderId);

    /**
     * 客户分配推送到医械购
     * <b>Description:</b>
     *
     * @param map
     * @return ResultInfo<?>
     * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2019年6月24日 下午6:44:07
     */
    ResultInfo<?> putTraderSaleUserIdtoHC(Map<String, Object> map);


	/**
	 * 客户归属推送（不要调用）
	 * <b>Description:</b>
	 *
	 * @param map
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2019年9月24日 下午6:44:07
	 */
    List<AccountSalerToGo> getAccountSaler(List<Integer> traderIdList);

	/**
	* @Title: saveMjxContactAdders
	* @Description: TODO(同步mjx地址)
	* @param @param traderMjxContactAdderss
	* @param @return    参数
	* @return ResultInfo    返回类型
	* @author strange
	* @throws
	* @date 2019年8月19日
	*/
	ResultInfo saveMjxContactAdders(TraderMjxContactAdderss traderMjxContactAdderss);

	/**
	* @Title: getMjxAccountAddressList
	* @Description: TODO(查询mjx客户地址信息)
	* @param @param traderContactVo
	* @return List<TraderContactVo>    返回类型
	* @author strange
	* @throws
	* @date 2019年8月20日
	*/
	List<MjxAccountAddress> getMjxAccountAddressList(TraderContactVo traderContactVo);

	/**
	 * <b>Description:</b>更新是否为盈利机构的状态<br>
	 * @param traderId 客户的标识
	 * @param isProfit 是否为盈利机构
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:2019/9/12</b>
	 */
	int updateCustomerIsProfit(Integer traderId,Integer isProfit);


	/**
	 * <b>Description:</b>数据初始化，如果客户资质必填项已经填写初始化审核流程为审核中<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:2019/9/17</b>
	 */
	ResultInfo initCustomerAptitudeCheck(HttpServletRequest request,Page page);

	/**
	 * 根据traderId查找TraderCustomerId
	 * @param traderId
	 * @return
	 */
	TraderCustomer getTraderCustomerId(Integer traderId);

	/**
	 * @Description: 判断该销售是否是这个部门
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/3/2
	 */
	Map childOrganization(Organization organization,Map<String,Integer> orgHashMap);
	/**
	 * <b>Description:</b>设置客户资质为待审核状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/4
	 */
	void setCustomerAptitudeUncheck(Integer traderCustomerId);

	/**
	 * <b>Description:</b>撤回资质审核<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/4
	 */
	ResultInfo recallChangeAptitude(String taskId);

	/**
	 * <b>Description:</b>获取资质审核状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/4
	 */
	VerifiesInfo getCustomerAptitudeVerifiesInfo(Integer traderCustomerId);

	 /**
	 * @Description: 查询该用户是哪个平台
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2020/3/4
	 */
	 Integer userIdPlatFromId(Integer userId,Integer companyId);
	/**
	* 更新贝登会员
	* @Author:strange
	* @Date:10:19 2020-04-07
	*/
	List<Integer> updateVedengMember();


	/**
	 * <b>Description:</b>获取简单的客户信息<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/18
	 */
	TraderCustomer getSimpleCustomer(Integer traderId);

	/**
	 * <b>Description:</b>同步医械购客户资质审核状态<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/3/19
	 */
	ResultInfo syncYxgTraderStatus(YXGTraderAptitude traderAptitude);


	/**
	 * 根据客户的售后支付申请来查看客户的余额
	 * @param payApplyId 支付申请id
	 * @return 余额
	 */
	TraderCustomer getTraderByPayApply(Integer payApplyId);

	/**
	 * 更新客户的账户余额
	 * @param traderId 客户id
	 * @param amount 更新的金额
	 */
	void updateTraderAmount(Integer traderId, BigDecimal amount);

    TraderContactGenerate getTraderContactByTraderContactId(Integer takeTraderContactId);

	TraderAddress getTraderAddressById(Integer takeTraderAddressId);

	Trader getTraderByTraderId(Integer traderId);
}
