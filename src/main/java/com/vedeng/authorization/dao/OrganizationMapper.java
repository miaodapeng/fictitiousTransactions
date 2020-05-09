package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import org.apache.ibatis.annotations.Param;

import javax.inject.Named;
import java.util.List;

/**
 * <b>Description:</b><br> 部门Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> OrganizationMapper
 * <br><b>Date:</b> 2017年4月25日 上午9:51:43
 */
@Named("organizationMapper")
public interface OrganizationMapper {
	
    /**
     * <b>Description:</b><br> 添加部门 
     * @param org 部门bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:52:08
     */
    int insert(Organization org);

    
    /**
     * <b>Description:</b><br> 根据主键查询部门  
     * @param orgId 部门ID
     * @return Organization
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:52:50
     */
    Organization selectByPrimaryKey(Integer orgId);

    /**
     * <b>Description:</b><br> 编辑部门
     * @param org 部门bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:53:28
     */
    int update(Organization org);
    
    /**
     * <b>Description:</b><br> 删除部门
     * @param orgId 部门ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:53:53
     */
    int delete(Integer orgId);
    
    /**
     * 
     * <b>Description:</b><br> 删除部门集合
     * @param orgId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月9日 下午4:08:19
     */
    int deleteByOrgId(List<Integer> orgId);
    
    /**
     * <b>Description:</b><br> 根据上级部门ID查询子集部门 
     * @param parentId 上级部门ID
     * @param companyId 公司ID
     * @return List<Organization>
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:54:20
     */
    List<Organization> findOrgsByParentId(@Param("parentId")Integer parentId,@Param("companyId")Integer companyId);
    
    /**
     * <b>Description:</b><br> 查询部门
     * @param org 部门bean
     * @return Organization
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午9:54:51
     */
    Organization getByOrg(Organization org);
    
    /**
	 * <b>Description:</b><br> 查询部门
	 * @param parentId 上级ID
	 * @param companyId 公司ID
	 * @return List<Organization>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月28日 下午1:21:03
	 */
	List<Organization> getOrgList(@Param("parentId")Integer parentId, @Param("companyId")Integer companyId);
	
	List<Organization> getSalesOrgList(@Param("orgType")Integer orgType, @Param("companyId")Integer companyId);
	
	List<Organization> getQuoteOrgList(@Param("orgList")List<Integer> orgList,@Param("orgType")Integer orgType);
	
	Organization getOrgNameByUserId(@Param("userId")Integer userId);
	
	String getOrgNameById(@Param("orgId")Integer orgId);

	/**
	 * <b>Description:</b><br> 根据参数类型获取对应部门（多层）
	 * @param id311
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月12日 下午2:05:20
	 */
	List<Organization> getOrgListByPositType(@Param(value="typeId")Integer typeId,@Param("companyId")Integer companyId);


	/**
	 * <b>Description:</b><br> 根据部门ID集合查询部门信息
	 * @param orgIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月29日 上午9:12:54
	 */
	List<Organization> getOrgByList(@Param("orgIdList")List<Integer> orgIdList);
	
	/**
	 * <b>Description:</b><br> 获取下一级的部门
	 * @param organization
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月22日 下午3:14:23
	 */
	List<OrganizationVo> getOrganizationVoList(Organization organization);
	
	/**
	 * <b>Description:</b><br> 获取下一级的部门id集合
	 * @param organization
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月22日 下午3:14:23
	 */
	List<Integer> getOrgIdsList(Organization organization);
	
	/**
	 * <b>Description:</b><br> 获取当前部门下所有的部门（递归查询）
	 * @param parentId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年11月24日 下午5:09:04
	 */
	List<Integer> getAllOrgIdList(Integer parentId);

	/**
	 * <b>Description:</b><br> 根据客户ID查询客户负责人信息
	 * @param traderIdList
	 * @param one
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月5日 下午6:39:07
	 */
	List<User> getUserByTraderIdList(@Param("traderIdList")List<Integer> traderIdList, @Param("traderType")Integer traderType);

	User getTraderUserAndOrgByTraderId(@Param("traderId")Integer traderId, @Param("traderType")Integer traderType);
	/**
	 * 获取当前分公司下所有用户的部门信息
	 * <b>Description:</b><br> 
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 上午9:53:57
	 */
	List<Organization> getUserOrgList(@Param("companyId")Integer companyId);

	/**
	 * 获取当前分公司下所有用户的部门信息
	 * <b>Description:</b><br>
	 * @param list
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2019年6月22日 上午9:53:57
	 */
	List<Organization> getParentOrgListByList(List<Organization> list);

	/**
	 * <b>Description:</b><br> 根据部门ID获取用户列表
	 * @param orgIdList
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper.xu
	 * <br><b>Date:</b> 2019年06月22日 下午6:39:07
	 */
	List<User> getUserListBtOrgId(@Param("orgIdList") List<Integer> orgIdList,@Param("type") Integer type,@Param("companyId") Integer companyId);
	//待采购列表
    List<Organization> getOrgListByPositTypes(Integer companyId);


    /**
    * @Description: 根据父类查询下面子类
    * @Param:
    * @return:
    * @Author: addis
    * @Date: 2020/3/2
    */
	List<Organization>  childOrganization(Integer orgId);

	List<Organization> getAllOrganizationOfCompany(Integer companyId);

	Integer getOrgIdByOrgName(@Param("orgName") String orgName, @Param("companyId") Integer companyId);
	/**
	*获取部门
	* @Author:strange
	* @Date:10:11 2020-02-26
	*/
	List<User> getOrgByTraderId(@Param("traderId")Integer traderId, @Param("traderType")Integer traderType);
	/**
	*获取用户全部部门
	* @Author:strange
	* @Date:11:43 2020-02-26
	*/
	List<User> getOrgNameListByUserId(Integer userId);
}