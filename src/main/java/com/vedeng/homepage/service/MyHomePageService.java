package com.vedeng.homepage.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.homepage.model.SalesGoalSetting;
import com.vedeng.homepage.model.vo.AfterSalesDataVo;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.homepage.model.vo.SaleEngineerDataVo;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.system.model.Address;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.trader.model.vo.CommunicateRecordVo;

public interface MyHomePageService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 获取当前人员的下一级部门目标数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月22日 下午2:59:25
	 */
	List<OrganizationVo> getOrganizationVoList(User user);
	
	/**
	 * <b>Description:</b><br> 获取当前人员的下一级部门id集合
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月22日 下午2:59:25
	 */
	List<Integer> getOrgIdList(User user);
	
	/**
	 * <b>Description:</b><br> 保存设置的销售目标
	 * @param sgslist
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 上午10:08:10
	 */
	int saveOrUpdateConfigSaleGoal(List<SalesGoalSetting> sgslist);
	
	/**
	 * <b>Description:</b><br> 查询月份的合计
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 下午2:27:02
	 */
	List<BigDecimal> getTotalMonthList(Map<String, Object> map);
	/**
	 *  销售工程师个人首页-商机跟进列表
	 * <b>Description:</b><br> 
	 * @param map
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年8月7日 下午2:46:34
	 */
	 Map<String,Object> getBussinessChanceVoList(BussinessChanceVo bussinessChanceVo,Page page);
	/**
	 * <b>Description:</b><br> 查询完成，同比，环比数据
	 * @param echartsDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 下午5:03:24
	 */
	EchartsDataVo getEchartsDataVo(User user);
	
	/**
	 * <b>Description:</b><br> 查询销售工程师个人首页数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月28日 下午2:57:23
	 */
	SaleEngineerDataVo getSaleEngineerDataVo(SaleEngineerDataVo saleEngineerDataVo);
	
	/**
	 * <b>Description:</b><br> 查询售后总监首页数据
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月26日 上午9:59:29
	 */
	AfterSalesDataVo getAfterSalesDataVo(User user);
	
	/**
	 * <b>Description:</b><br> 查询销售总监的参数配置
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月4日 上午10:18:00
	 */
	ParamsConfigVo getParamsConfigVoByParamsKey(ParamsConfigVo paramsConfigVo);
	
	/**
	 * <b>Description:</b><br> 查询销售总监的参数配置
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月4日 上午10:18:00
	 */
	List<ParamsConfigVo> getParamsConfigVoList(ParamsConfigVo paramsConfigVo);
	
	/**
	 * <b>Description:</b><br> 保存编辑设置的参数
	 * @param user
	 * @param quote
	 * @param sale
	 * @param quoteorder
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月4日 上午11:26:31
	 */
	Integer saveEditConfigParams(User user,ParamsConfigVo paramsConfigVo);
	
	/**
	 * <b>Description:</b><br> 保存新增或编辑发/退货地址
	 * @param addressVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午6:37:53
	 */
	int saveAddressAndConfigParamsValue(AddressVo addressVo, User user,Integer paramsConfigId);
	
	/**
	 * <b>Description:</b><br> 保存新增或编辑采购的收货地址
	 * @param addressVo
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月5日 下午6:37:53
	 */
	int saveOrUpdateAddress(AddressVo addressVo, User user);
	
	/**
	 * <b>Description:</b><br> 设置采购收货默认
	 * @param address
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月6日 下午1:14:42
	 */
	int saveSetDefaultBuyAddress(Address address, User user);
	
	/**
	 * <b>Description:</b><br> 查询销售一级有效部门的负责人
	 * @param organization
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月6日 下午3:29:28
	 */
	List<User> getSalesOneOrg(Organization organization);
	
	/**
	 * <b>Description:</b><br> 查询当期那用户的下一级部门的当前月的销售目标
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月18日 下午2:42:17
	 */
	List<SalesGoalSetting> getSalesGoalSettingListByOrgIds(User user);
	
	/**
	 * <b>Description:</b><br> 查询地图的客户数据
	 * @param echartsDataVo
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月19日 下午4:25:49
	 */
	EchartsDataVo getCustomerData(EchartsDataVo echartsDataVo);
	
	/**
	 * <b>Description:</b><br> 获取售后主管以下今日沟通记录
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年8月6日 上午10:55:17
	 */
	List<CommunicateRecordVo> getAfterSalesCommunicateRecordVoList(User user);

}
