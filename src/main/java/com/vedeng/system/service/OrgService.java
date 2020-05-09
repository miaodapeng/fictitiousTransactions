package com.vedeng.system.service;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Saleorder;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <b>Description:</b><br> 部门service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> OrgService
 * <br><b>Date:</b> 2017年4月25日 上午11:27:29
 */
public interface OrgService extends BaseService {
	/**
	 * <b>Description:</b><br> 根据部门ID查询部门信息 
	 * @param orgId 部门ID
	 * @return Organization
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:28:00
	 */
	Organization getOrgByOrgId(Integer orgId);
	
	/**
	 * <b>Description:</b><br> 编辑部门  
	 * @param org 部门bean
	 * @param session
	 * @return 成功1 失败0
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:28:18
	 */
	Integer modifyOrg(Organization org, HttpSession session) throws Exception;
	
	/**
	 * <b>Description:</b><br> 查询部门 
	 * @param org 部门bean
	 * @return Organization
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:28:38
	 */
	Organization getOrg(Organization org);
	
	/**
	 * <b>Description:</b><br> 删除部门
	 * @param org 部门bean
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:28:57
	 */
	Integer deleteOrg(Organization org);
	
	/**
	 * 
	 * <b>Description:</b><br> 删除部门集合
	 * @param orgId 
	 * @return 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月9日 下午4:13:23
	 */
	Integer deleteOrgByOrgId(List<Integer> orgId);
	
	/**
	 * <b>Description:</b><br> 获取层级目录
	 * @param orgId 部门ID
	 * @param join 连接符
	 * @return String
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:29:16
	 */
	String getParentOrgName(Integer orgId,String join);
	
	/**
	 * <b>Description:</b><br> 查询部门
	 * @param parentId 上级ID
	 * @param companyId 公司ID
	 * @param joinChar 是否添加前缀
	 * @return List<Organization>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月28日 下午1:21:03
	 */
	List<Organization> getOrgList(Integer parentId,Integer companyId, Boolean joinChar);
	
	List<Organization> getSalesOrgList(Integer orgType,Integer companyId);
	
	List<Organization> getQuoteOrgList(List<Integer> orgList,Integer orgType);
	
	Organization getOrgNameByUserId(Integer userId);
	
	String getOrgNameById(Integer orgId);

	/**
	 * <b>Description:</b><br> 根据参数类型获取对应部门（多层）
	 * @param typeId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月12日 下午2:01:25
	 */
	List<Organization> getOrgListByPositType(Integer typeId,Integer companyId);
	
	/**
	 * <b>Description:</b><br> 根据部门ID集合查询部门信息
	 * @param orgIdList
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月29日 上午9:11:42
	 */
	List<Organization> getOrgByList(List<Integer> orgIdList) throws Exception;
	
	/**
	 * <b>Description:</b><br> 获取下一级部门
	 * @param organization
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年12月7日 上午11:38:53
	 */
	List<OrganizationVo> getOrganizationVoList(Organization organization);
	
	/**
	 * <b>Description:</b><br> 根据当前部门ID查询所有下级ID集合
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月19日 下午1:31:36
	 */
	List<Integer> getOrgIds(Integer orgId,Integer companyId);

	public List<Integer> getOrgIdsByParentId(Integer orgId,Integer companyId);

	/**
	 * <b>Description:</b><br> 根据当前部门ID查询所有上级集合
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年12月20日 下午4:18:05
	 */
	List<Organization> getParentOrgList(Integer orgId);
	
	/**
	 * <b>Description:</b><br> 根据客户ID查询客户负责人信息
	 * @param traderIdList
	 * @param one
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午6:39:07
	 */
	List<User> getUserByTraderIdList(List<Integer> traderIdList,Integer traderType);
	
	/**
	 * <b>Description:</b><br> 根据客户ID查询客户负责人信息
	 * @param traderIdList
	 * @param one
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午6:39:07
	 */
	User getTraderUserAndOrgByTraderId(Integer traderId,Integer traderType);

	/**
	 * <b>Description:</b><br> 根据参数类型获取对应部门及对应上级部门（多层）
	 * @param typeId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper.xu
	 * <br><b>Date:</b> 2019年06月22日 下午6:39:07
	 */
	List<Organization> getAllOrganizationListByType(Integer typeId,Integer companyId);

	/**
	 * <b>Description:</b><br> 根据部门ID获取相应类型的职位的用户列表
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper.xu
	 * <br><b>Date:</b> 2019年06月22日 下午6:39:07
	 */
	List<User> getUserListBtOrgId(Integer orgId, Integer type, Integer companyId);
	//待采购下拉框
	List<Organization> getOrgListByPositTypes(Integer companyId);

	List<Integer> getChildrenByParentId(Integer parentId, Integer companyId);

	/**
	*获取是否是科研购部门  true 是  false 不是
	* @Author:strange
	* @Date:10:06 2020-02-26
	*/
    Boolean getKYGOrgFlagByTraderId(Saleorder saleorder);
}
