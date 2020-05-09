package com.vedeng.authorization.dao;

import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.UserVo;
import com.vedeng.saleperformance.model.vo.SalesPerformanceGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <b>Description:</b><br> 员工Mapper
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.authorization.dao
 * <br><b>ClassName:</b> UserMapper
 * <br><b>Date:</b> 2017年4月25日 上午10:55:13
 */
public interface UserMapper {

	/**
	 * <b>Description:</b>通过用户名称和职位类型查询用户信息<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/20
	 */
	User getUserByNameAndPositionType(@Param("userName") String userName,@Param("positionType") Integer positionType);

	User getUserByIdAndPositionType(@Param("userId") Integer userId,@Param("positionType") Integer positionType);
	/**
     * <b>Description:</b><br> 添加员工
     * @param user 员工bean
     * @return 新增员工ID
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:55:31
     */
    int insert(User user);

    /**
     * <b>Description:</b><br> 编辑员工 
     * @param user 员工bean
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:56:24
     */
    int update(User user);
    
    
    /**
     * <b>Description:</b><br>  根据员工用户名查询员工
     * @param username 用户名
     * @return User
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:56:44
     */
    User getByUsername(@Param("username")String username,@Param("companyId")Integer companyId);
    
    /**
     * <b>Description:</b><br> 根据主键查询员工
     * @param userId 员工ID
     * @return User
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:57:36
     */
    User selectByPrimaryKey(Integer userId);
    
    /**
     * <b>Description:</b><br> 分页查询员工 
     * @param map user&page集合
     * @return List<User>
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午10:58:21
     */
    List<User> querylistPage(Map<String, Object> map);
    
    /**
     * <b>Description:</b><br> 查询员工
     * @param user 员工bean
     * @return List<User>
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 上午11:00:08
     */
    List<User> getAllUser(User user);
    
    /**
	 * <b>Description:</b><br> 查询职位下的员工
	 * @param positionId 职位ID
	 * @return List<User>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:10:19
	 */
	List<User> getUserByPositId(Integer positionId);
	
	/**
	 * <b>Description:</b><br> 查询角色下的员工
	 * @param roleId 角色ID
	 * @return List<User>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:11:16
	 */
	List<User> getUserByRoleId(Integer roleId);
    
	/**
	 * <b>Description:</b><br> 查询员工
	 * @param user 员工bean
	 * @return User
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月27日 上午9:28:37
	 */
	User getUser(User user);
	
    
	/**
	 * <b>Description:</b><br> 查询职位类型下所有的员工
	 * @param positionType
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月16日 上午10:10:23
	 */
	List<User> getUserByPositType(@Param("positionType")Integer positionType, @Param("companyId") Integer companyId);
	List<User> getActiveUserByPositType(@Param("positionType")Integer positionType, @Param("companyId") Integer companyId);

	/**
	 * <b>Description:</b><br> 查询职位类型集合下所有的员工
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月2日 下午1:42:07
	 */
	List<User> getUserByPositTypes(User user);
	
	/**
	 * <b>Description:</b><br> 
	 * @param traderId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月16日 下午5:05:30
	 */
	User getUserByTraderId(@Param("traderId")Integer traderId,@Param("traderType")Integer traderType);
	
	List<Integer> getUserIdListByTraderId(@Param("traderId")Integer traderId,@Param("traderType")Integer traderType);
	
	/**
	 * <b>Description:</b><br> 批量查询用户
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年5月27日 上午10:33:21
	 */
	List<User> getUserByUserIds(List<Integer> userId);
	
	List<User> getUserListByOrgId(@Param("orgId")Integer orgId);

	List<User> getUserListByOrgIdcg(@Param("orgId")Integer orgId);

	List<Integer> getUserIdListByOrgId(@Param("orgId")Integer orgId);

	/**
	 * <b>Description:</b><br> 根据部门集合id查询员工
	 * @param orgIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月13日 下午1:03:09
	 */
	List<User> getUserListByOrgIdList(@Param("orgIds")List<Integer> orgIds,@Param("companyId")Integer companyId);
	
	List<String> queryUserListByOrgId(@Param("orgId")Integer orgId);
	
	String getUserNameByTraderId(@Param("traderId")Integer traderId);
	
	/**
	 * <b>Description:</b><br> 根据职位类型查询此职位下人员
	 * @param typeId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月12日 下午2:27:58
	 */
	List<User> getUserListByPositType(@Param("typeId")Integer typeId,@Param("companyId")Integer companyId);


	/**
	 * <b>Description:</b><br> 根据公司ID获取所有员工
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> micheal
	 * <br><b>Date:</b> 2017年7月18日 下午10:22:58
	 */
	List<User> getAllUserList(Integer companyId);
	
	/**
	 * <b>Description:</b><br> 通过分机号查询用户
	 * @param ccNumber
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年7月25日 下午4:16:56
	 */
	User getUserByCcNumber(@Param("ccNumber")String ccNumber);

	/**
	 * <b>Description:</b><br> 根据人员查客户
	 * @param userList
	 * @param traderType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 上午11:22:13
	 */
	List<Integer> getTraderIdListByUserList(@Param("userList")List<User> userList, @Param("traderType")String traderType);
	
	/**
	 * <b>Description:</b><br> 根据人员查客户
	 * @param userList
	 * @param traderType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 上午11:22:13
	 */
	List<Integer> getTraderIdsByUserList(@Param("userList")List<User> userList, @Param("traderType")Integer traderType);

	/**
	 * <b>Description:</b><br> 根据用户ID获取用户名称
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月24日 下午1:24:12
	 */
	String getUserNameByUserId(@Param("userId")Integer userId);
	
	/**
	 * <b>Description:</b><br> 
	 * @param traderIdList
	 * @param traderType
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月29日 上午10:27:55
	 */
	List<User> getUserByTraderIdList(@Param("traderIdList")List<Integer> traderIdList, @Param("traderType")Integer traderType);
	
	/**
	 * <b>Description:</b><br> 根据用户名称与归属公司查询上级信息
	 * @param username
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月8日 下午2:45:48
	 */
	User getUserParentInfo(@Param("username") String username, @Param("companyId") Integer companyId);
	
	/**
	 * <b>Description:</b><br> 查询部门下对应级别的用户
	 * @param orgIds
	 * @param level 目标部门级别 441=>总经理 442=>总监 443=>高级主管 444=>主管 445=>员工
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月25日 上午11:02:15
	 */
	List<User> getUserByOrgIdsAndPositLevel(@Param("orgIds")List<Integer> orgIds,@Param("level")Integer level);

	/**
	 * <b>Description:</b><br> 获取客户/供应商归属人信息（orgId，userId）
	 * @param traderId
	 * @param traderType 1客户 2供应商
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月18日 上午9:14:22
	 */
	User getUserInfoByTraderId(@Param("traderId")Integer traderId,@Param("traderType")Integer traderType);

	List<User> getTraderUserAndOrgList(@Param("traderIdList")List<Integer> traderIdList);
	
	/**
	 * <b>Description:</b><br> 根据部门id集合查询所有员工
	 * @param orgIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月19日 下午5:00:41
	 */
	List<Organization> getOrgNameByOrgIdList(@Param("orgIdList")List<Integer> orgIdList, @Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 查询分类对应人员（产品归属人员）
	 * @param categoryIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午4:43:22
	 */
	List<User> getCategoryUserList(@Param(value="categoryIdList")List<Integer> categoryIdList,Integer companyId);
	/**
	 * 查询user所在部门
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月7日 下午6:13:39
	 */
	List<UserVo> getUserOrgList(@Param("groupUserVoList")List<SalesPerformanceGroupVo> groupUserVoList);
	/**
	 * 获取当前分公司下未加入部门的用户列表
	 * <b>Description:</b><br> 
	 * @param request
	 * @param response
	 * @return
	 * @Note
	 * <b>Author:</b> Cooper
	 * <br><b>Date:</b> 2018年6月8日 上午11:11:12
	 */
	List<UserVo> getUserlistpage(Map<String,Object> map);

	/**
	 * <b>Description:</b><br> 获取销售部门部门信息列表（二级部，三级部）
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月11日 下午2:13:21
	 */
	List<User> getSaleUserOrgList(Integer companyId);

	List<User> getSaleUserOrgListAll(Integer companyId);

	/**
	 * 
	 * <b>Description:获取销售部门部门信息列表（二级部，三级部）</b><br> 
	 * @param 
	 * @return
	 * @Note
	 * <b>Author:</b> Barry
	 * <br><b>Date:</b> 2018年08月02日 19:11
	 */
	User getOrgIdsByUserId(@Param("userId")Integer userId,@Param("companyId")Integer companyId);
	
	/**
	 * 
	 * <b>Description: 根据userId的list批量查询用户信息</b><br> 
	 * @param userIdList
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2018年11月24日 下午4:12:10 </b>
	 */
	List<User> getUserListByUserIdList(@Param("userIdList")List<Integer> userIdList);

	List<User> selectAllAssignUser();

	/**
	  * 获取所有下级员工
	  * @author wayne.liu
	  * @date  2019/6/20 14:27
	  * @param
	  * @return
	  */
	List<User> getSubUserList(User user);

    User getUserByTraderCustomerId(@Param("traderCustomerId")Integer traderCustomerId, @Param("traderType")Integer traderType);

    /**
     * @Title: getUserInfoByMobile
     * @Description: TODO(通过注册手机号查找用户归属销售和erp客户信息)
     * @param @param traderContactMobile
     * @param @param traderType
     * @param @return    参数
     * @return User    返回类型
     * @author strange
     * @throws
     * @date 2019年7月17日
     */
    User getUserInfoByMobile(@Param("traderContactMobile")String traderContactMobile,@Param("traderType") Integer traderType);

    /**
     * @Title: getUserInfoById
     * @Description: TODO(T_WEB_ACCOUNT表主键id查找用户归属销售和erp客户信息)
     * @param @param erpAccountId
     * @param @param i
     * @param @return    参数
     * @return User    返回类型
     * @author strange
     * @throws
     * @date 2019年7月23日
     */
    User getUserInfoById(@Param("erpAccountId") Integer erpAccountId,@Param("traderType") Integer traderType);


    /**
     * @Description: 查出相应角色的userID
     * @Param: [roleId]
     * @return: java.util.List<com.vedeng.authorization.model.User>
     * @Author: addis
     * @Date: 2019/7/29
     */
    List<User> getRoleUserId(Integer roleId);
    /**
     * @Description: 根据userid 查出是否禁用
     * @Param: [userId]
     * @return: com.vedeng.authorization.model.User
     * @Author: addis
     * @Date: 2019/7/30
     */
    User getIsDisabled(Integer userId);

	/**
	* @Title: getUserInfoById
	* @Description: TODO(获取客户归属信息)
	* @param @param erpAccountId
	* @param @return    参数
	* @return User    返回类型
	* @author strange
	* @throws
	* @date 2019年8月12日
	*/
	User getBDUserInfoById(Integer erpAccountId);

	/**
	* @Title: getBDUserInfoByMobile
	* @Description: TODO((通过注册手机号查找用户归属销售和erp客户信息)
	* @param @param phone
	* @param @return    参数
	* @return User    返回类型
	* @author strange
	* @throws
	* @date 2019年8月12日
	*/
	User getBDUserInfoByMobile(String phone);

	User getUserByName(@Param("userName")String userName);


	/**
	 * 根据归属人查找有无讯息
	 * @param proUserId
	 * @return
	 */
	List<Integer> getUserId(Integer proUserId);

	/**
	*获取用户通过用户名
	* @Author:strange
	* @Date:09:29 2020-04-03
	*/
	User getUserByName2(User u);
	/**
	*获取用户信息by工号
	* @Author:strange
	* @Date:09:29 2020-04-03
	*/
	User getUserByNumber(User u);
	/**
	* @Description: 通过userId查询职位和部门
	* @Param:
	* @return:
	* @Author: addis
	* @Date: 2020/3/2
	*/
	List<Position> userPositionOrganization(@Param("userId") Integer userId,@Param("companyId") Integer companyId);



}