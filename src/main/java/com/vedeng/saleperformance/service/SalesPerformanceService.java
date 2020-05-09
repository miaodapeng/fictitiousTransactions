package com.vedeng.saleperformance.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.UserVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;

import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.vo.OrderDetailsVo;
import com.vedeng.saleperformance.model.*;
import com.vedeng.saleperformance.model.vo.RSalesPerformanceGroupJConfigVo;
import com.vedeng.saleperformance.model.vo.SaleperformanceProcess;
import com.vedeng.saleperformance.model.vo.SalesPerformanceDeptUserVo;
import com.vedeng.saleperformance.model.vo.SalesPerformanceGroupVo;
import com.vedeng.saleperformance.model.perf.MonthSceneModel;

public interface SalesPerformanceService extends BaseService
{
	/**
	 * 
	 * <b>Description: 获取五行销售--本月概况</b><br> 
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月5日 下午1:18:21 </b>
	 */
	public ResultInfo<MonthSceneModel> getSalesMonthSceneModel(MonthSceneModel reqM);
	
	/**
	 * 
	 * <b>Description:  业绩明细/</b><br> 
	 * @param reqM
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月6日 下午6:23:08 </b>
	 */
	public ResultInfo<MonthSceneModel> getSceneDataDetails(MonthSceneModel reqM);
	/**
	 * 获取部门设置列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月4日 下午4:08:55
	 */
	Map<String, Object> getGroupConfigSetData();
	/**
	 * 启用或者禁用分组
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> openOrClose(SalesPerformanceGroup salesPerformanceGroup);
	/**
	 * 打开编辑页面时获取当前分组的数据信息
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午4:47:23
	 */
	Map<String, Object> getOneGroupConfigSetData(RSalesPerformanceGroupJConfigVo rSalesPerformanceGroupJConfigVo);
	/**
	 * 保存部门编辑信息
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> saveOrUpdateConfigSetData(SalesPerformanceGroupVo groupVo);
	/**
	 * 获取当前分组下的品牌列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月6日 下午1:12:02
	 */
	Map<String, Object> getBrandConfigListPage(HttpServletRequest request,SalesPerformanceBrandConfig salesPerformanceBrandConfig,Page page,HttpSession session);
	/**
	 * 启用或者禁用品牌
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> changeBrandEnable(SalesPerformanceBrandConfig brandConfig);
	/**
	 * 获取新增品牌弹层的品牌列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月7日 上午9:00:19
	 */
	Map<String,Object> getBrandListPage(HttpServletRequest request,SalesPerformanceBrand salesPerformanceBrand,String groupId,Page page,HttpSession session);
	/**
	 * 新增品牌
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> addBrandConfig(Map<String,Object> map);
	/**
	 * 获取当前分组下的人员列表以及年月度目标
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月7日 下午2:12:30
	 */
	Map<String,Object> getUserConfigList(HttpServletRequest request,SalesPerformanceGroup salesPerformanceGroup,HttpSession session);
	/**
	 * 获取当前用户所在分公司所有用户的部门列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 上午9:44:43
	 */
	List<Organization> getOrgList(Integer companyId);
	/**
	 * 获取新增人员界面下的用户列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 上午10:26:51
	 */
	List<UserVo> getUserList(UserVo userVo,Integer companyId,Page page);
	/**
	 * 移除当前分组的某个人员，同时删除该人员的当年年度目标和月度目标
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> delUserConfig(Map<String,Object> map);
	/**
	 * 新增人员
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> addUserConfig(Map<String,Object> map);
	
	/**
	 * 保存或更新人员本年度目标和月度目标
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午4:47:23
	 */
	ResultInfo<?> saveOrUpdateOneUserConfigData(SalesPerformanceGroupVo groupVo);
	/**
	 * 
	 * <b>Description: 过往明细history</b><br> 
	 * @param reqM
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年6月6日 下午6:43:27 </b>
	 */
	public ResultInfo<MonthSceneModel> historyDataDetails(MonthSceneModel reqM);
	/**
	 * 查询人员当前年度目标和月度目标
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月13日 下午1:47:42
	 */
	Map<String,Object> getUserConfigGoal(Map<String,Object> map);
	
	
	/**
	 * 
	 * <b>Description: 查询销售人员的年度目标和该年度所有月份的目标,没有给null</b><br> 
	 * @param req
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月19日 下午4:09:05 </b>
	 */
	public SalesPerformanceGroupVo querySalesForGoal(SalesPerformanceGroupVo req);
	
	/**
	 * 
	 * <b>Description: 批量保存或更新</b><br> 
	 * @param req
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月19日 下午7:47:42 </b>
	 */
	public ResultInfo<?> batchSaveOrUpdate(SalesPerformanceGroupVo req);


	/**
	 * 
	 * <b>Description: 查询部门五行得分和排名</b><br> 
	 * @param req
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月23日 上午8:59:35 </b>
	 */
	public ResultInfo<?> queryDeptSoreAndSort(MonthSceneModel req);

	/**
	 * 
	 * <b>Description: 查询订单详情</b><br> 
	 * @param req
	 * @param page
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月23日 下午5:03:53 </b>
	 */
	public ResultInfo<?> queryOrderDetails(OrderDetailsVo req, Page page);
	
	/**
	 * 
	 * <b>Description: 查询所有五行数据</b><br> 
	 * @param longTime
	 * @param page
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年8月13日 上午9:41:19 </b>
	 */
	List<SaleperformanceProcess> queryAllSalesData(Long longTime, Page page);
	
	/**
	 * <b>Description:</b><br>
	 * 查询五行
	 * @param :a
	* @return :a
	* @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/16 14:12
	 */
	ResultInfo<?> findByCompanyIdWuXing(RSalesPerformanceGroupJConfigVo rSalesPerformanceGroupJConfigVo);
	
	/**
	 * 保存部门编辑信息
	 * <b>Description:</b><br>
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 下午2:38:36
	 */
	ResultInfo<?> saveConfigSetData(HttpServletRequest request,SalesPerformanceGroupVo groupVo);
	
	/**
	 * <b>Description:</b><br>
	 * 删除团队
	* @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/16 18:52
	 */
	ResultInfo<?> deleteGroupById(HttpServletRequest request,Integer salesPerformanceGroupId);
	
	/**
	 * <b>Description:</b><br>
	 * 查找该公司下的所有团队
	* @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/17 15:42
	 */
	ResultInfo<?> queryByCompangIdAndstatus(SalesPerformanceGroup salesPerformanceGroup);
	
	/**
	 * 新增小组
	 * <b>Description:</b><br>
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年02月17日 下午2:38:36
	 */
	ResultInfo<?> insertTeam(SalesPerformanceDept salesPerformanceDept);
	
	/**
	 * <b>Description:</b><br>
	 * 返回各个团队部门下的小组以及负责人
	 * @param :[]
	 * @return :java.util.List<com.vedeng.model.saleperformance.SalesPerformanceDept>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 17:49
	 */
	ResultInfo<?> getDeptMember(SalesPerformanceDept salesPerformanceDept);
	
	/**
	 * <b>Description:</b><br>
	 *  删除小组
	 * @param :[]
	 * @return :java.util.List<com.vedeng.model.saleperformance.SalesPerformanceDept>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 17:49
	 */
	ResultInfo<?> deleteOneGroupConfigSetData(SalesPerformanceDept salesPerformanceDept);
	
	/**
	 * <b>Description:</b><br>
	 *  得到小组用户
	 * @param :[]
	 * @return :java.util.List<com.vedeng.model.saleperformance.SalesPerformanceDept>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 17:49
	 */
	ResultInfo<?> getDeptUser(SalesPerformanceDeptUserVo salesPerformanceDept);
	
	/**
	 * <b>Description:</b><br>
	 *  编辑小组人员
	 * @param :[]
	 * @return :java.util.List<com.vedeng.model.saleperformance.SalesPerformanceDept>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 17:49
	 */
	ResultInfo<?> editDeptUser(SalesPerformanceDeptUser salesPerformanceDeptUser);
	
	/**
	 * <b>Description:</b><br>
	 *  删除小组人员
	 * @param :[]
	 * @return :java.util.List<com.vedeng.model.saleperformance.SalesPerformanceDept>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/18 17:49
	 */
	ResultInfo<?> deleteDeptUser(SalesPerformanceDeptUser salesPerformanceDeptUser);
	
	/**
	 * <b>Description:</b><br>
	 * 小组内新增人员
	 *
	 * @param :[salesPerformanceDeptUserVo]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 13:50
	 */
	ResultInfo<?> addDeptUserConfig(SalesPerformanceDeptUserVo salesPerformanceDeptUserVo);
	
	/**
	 * <b>Description:</b><br>
	 * 小组内新增人员
	 *
	 * @param :[salesPerformanceDeptUserVo]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/19 13:50
	 */
	List<SalesPerformanceGroupVo> getAllGroup(SalesPerformanceGroupVo salesPerformanceGroupVo);
	
	/**
	 * 获取所有已经被绑定的用户
	 * <b>Description:</b><br>
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2019年02月19日 下午9:44:43
	 */
	List<User> getHasAllUser();
	
	/**
	 * <b>Description:</b><br>
	 *
	 * 删除小组的负责人
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/21 11:00
	 */
	ResultInfo<?> delDeptUser(SalesPerformanceDeptManager salesPerformanceDeptManager);
	
	/**
	 * <b>Description:</b><br>
	 *
	 * 删除团队的负责人
	 * @param :[salesPerformanceDeptUser]
	 * @return :com.vedeng.common.model.ResultInfo<?>
	 * @Note <b>Author:</b> Bert <br>
	 * <b>Date:</b> 2019/2/21 11:00
	 */
	ResultInfo<?> delGroupUser(SalesPerformanceGroupManager salesPerformanceGroupManager);
}
