package com.vedeng.system.service;

import com.vedeng.authorization.model.*;
import com.vedeng.common.page.Page;
import com.vedeng.common.service.BaseService;
import com.vedeng.order.model.Saleorder;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * <b>Description:</b><br> 员工service
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> UserService
 * <br><b>Date:</b> 2017年4月25日 上午11:31:04
 */
public interface UserService extends BaseService {

	/**
	 * <b>Description:</b>通过用户名称和职位类型查询用户信息<br>
	 * @param
	 * @return
	 * @Note
	 * <b>Author:calvin</b>
	 * <br><b>Date:</b> 2019/12/20
	 */
	User getUserByNameAndPositionType(String userName, Integer positionType);
	/**
	 * <b>Description:</b><br> 登录
	 * @param username
	 * @param password
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:31:19
	 */
	public User login(String username, String password,Integer companyId);
	
	/**
	 * <b>Description:</b><br> 分页查询员工
	 * @param user 员工bean
	 * @param page 分页bean
	 * @return List<User>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:31:24
	 */
	public List<User> querylistPage(User user,Page page);

	/**
	 * <b>Description:</b><br> 启用/禁用员工
	 * @param user 员工bean
	 * @return Boolean
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:32:03
	 */
	public Boolean changDisabled(User user);
	
	
	/**
	 * <b>Description:</b><br> 查询员工
	 * @param userId 员工ID
	 * @return User
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:32:24
	 */
	public User getUserById(Integer userId);
	
	/**
	 * <b>Description:</b><br> 查询员工
	 * @param user 员工bean
	 * @return List<User>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:32:44
	 */
	public List<User> getAllUser(User user);
	
	/**
	 * <b>Description:</b><br> 编辑员工
	 * @param user 员工bean
	 * @return Integer
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:33:08
	 */
	Integer modifyUser(HttpSession session,User user,UserDetail userDetail,UserAddress userAddress) throws Exception;
	
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
	 * <b>Description:</b><br> 根据userid获取角色
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月10日 下午2:04:29
	 */
	List<RUserRole> getRUserRoleListByUserId(Integer userId);

	/**
	 * <b>Description:</b><br> 查询职位类型为311所有的员工
	 * @param positionType
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月16日 上午10:10:23
	 */
	List<User> getUserByPositType(Integer positionType,Integer companyId);
	
	/**
	 * <b>Description:</b><br> 根据userid获取dbcenter的trader表的主键
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月16日 下午1:18:48
	 */
	List<Integer> getTraderIdListByUserId(Integer userId , Integer traderType);
	
	/**
	 * <b>Description:</b><br> 保存或更新
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月22日 上午9:52:08
	 */
	int saveOrUpdate(User user,String ip);
	
	/**
	 * <b>Description:</b><br> 批量查询用户
	 * @param userIds
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月21日 下午4:48:31
	 */
	List<User> getUserByUserIds(List<Integer> userIds);
	
	/**
	 * <b>Description:</b><br> 根据部门ID查询用户
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月17日 下午5:52:27
	 */
	List<User> getUserListByOrgId(Integer orgId);
	//带采购列表显示
	List<User> getUserListByOrgIdcg(Integer orgId);
	
	/**
	 * <b>Description:</b><br> 根据部门ID查询用户
	 * @param orgId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月17日 下午5:52:27
	 */
	List<Integer> getUserIdListByOrgId(Integer orgId);
	
	List<String> queryUserListByOrgId(Integer orgId);
	
	/**
	 * <b>Description:</b><br> 修改密码
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年6月28日 上午11:45:12
	 */
	int modifyPassowrd(User user);

	/**
	 * <b>Description:</b><br> 根据职位类型查询此职位下人员
	 * @param id311
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月12日 下午2:24:34
	 */
	public List<User> getUserListByPositType(Integer typeId,Integer companyId);


	
	/**
	 * <b>Description:</b><br> 根据部门id集合查询所有员工
	 * @param orgIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年7月13日 下午1:13:55
	 */
	List<User> getUserListByOrgIds(List<Integer> orgIds,Integer companyId);
	/**
	 * 
	 * <b>Description:</b><br> 根据用户id获取他下级所有用户列表
	 * @param userId 用户ID
	 * @param companyId 用户所在的公司ID
	 * @param haveMyself true或者false,返回值是否包含自己
	 * @param positionLevel 当前用户职位等级
	 * @param positionType 需要检索的职位类型
	 * @return
	 * @Note
	 * <b>Author:</b> Micheal
	 * <br><b>Date:</b> 2017年7月17日 下午6:43:21
	 */
	//public List<User> getNextAllUserList(Integer userId,Integer companyId,boolean haveMyself,Integer positionLevel,Integer positionType);

	/**
	 * <b>Description:</b><br> 根据人员查客户
	 * @param userList
	 * @param one
	 * @param two
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 上午11:19:37
	 */
	public List<Integer> getTraderIdListByUserList(List<User> userList, String traderType);
	
	/**
	 * <b>Description:</b><br> 根据人员查客户
	 * @param userList
	 * @param one
	 * @param two
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 上午11:19:37
	 */
	List<Integer> getTraderIdsByUserList(List<User> userList, Integer traderType);
	
	/**
	 * <b>Description:</b><br> 根据客户ID获取对应负责人员
	 * @param traderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 下午2:55:56
	 */
	public User getUserByTraderId(Integer traderId,Integer traderType);
	
	/**
	 * <b>Description:</b><br> 根据客户ID获取对应负责人员集合
	 * @param traderId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年7月28日 下午2:55:56
	 */
	public List<Integer> getUserIdListByTraderId(Integer traderId,Integer traderType);
	
	/**
	 * <b>Description:</b><br> 
	 * @param session  
	 * @param positionType  需要检索的职位类型集合 null为全部 ； 如果当前用户在职位类型集合内，则查子集
	 * 		positionType.add(SysOptionConstant.ID_310);//销售
			positionType.add(SysOptionConstant.ID_311);//采购
			positionType.add(SysOptionConstant.ID_312);//售后
			positionType.add(SysOptionConstant.ID_313);//物流
			positionType.add(SysOptionConstant.ID_314);//财务
	 * @param haveDisabeldUser 是否包含禁用用户 true包含
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月2日 下午1:38:07
	 */
	public List<User> getMyUserList(User user,List<Integer> positionType,boolean haveDisabeldUser);

	/**
	 * <b>Description:</b><br> 根据角色的集合查询关联用户的id集合
	 * @param roleIdList
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月2日 下午1:23:16
	 */
	List<Integer> getUserIdList(List<Integer> roleIdList);

	/**
	 * <b>Description:</b><br> 新增用户
	 * @param session
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月9日 上午10:56:33
	 */
	public Integer addUser(HttpSession session, User user,UserDetail userDetail) throws Exception;

	/**
	 * <b>Description:</b><br> 保存编辑管理员
	 * @param session
	 * @param user
	 * @param userDetail
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年8月10日 上午9:27:27
	 */
	public Integer editUser(HttpSession session, User user, UserDetail userDetail) throws Exception;
	
	/**
	 * <b>Description:</b><br> 根据用户ID获取用户名称
	 * @param userId
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月24日 下午1:23:28
	 */
	public String getUserNameByUserId(Integer userId)throws Exception;
	
	/**
	 * <b>Description:</b><br> 
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年8月25日 下午4:05:25
	 */
	String getRedisDbType();
	
	/**
	 * <b>Description:</b><br> 根据用户名称与归属公司查询上级信息
	 * @param username
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月8日 下午2:38:39
	 */
	public User getUserParentInfo(String username, Integer companyId);
	
	/**
	 * <b>Description:</b><br> 根据部门级别人员
	 * @param orgId 目标部门
	 * @param level 目标部门级别 441=>总经理 442=>总监 443=>高级主管 444=>主管 445=>员工
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月25日 上午10:37:02
	 */
	public List<User> getUserByPositLevel(Integer orgId,Integer level);

	/**
	 * <b>Description:</b><br> 根据产品归属查找对应的分类
	 * @param goodsUserId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年11月29日 下午5:56:11
	 */
	public List<Integer> getCategoryIdListByUserId(Integer goodsUserId);
	
	/**
	 * <b>Description:</b><br> 根据产品归属查找对应的分类
	 * @param goodsUserId
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年11月29日 下午5:56:11
	 */
	List<Integer> getCategoryIdListByUserList(List<User> userList);
	/**
	 * 
	 * <b>Description:</b><br> 根据名称获取user
	 * @param username
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2017年12月20日 下午4:02:28
	 */
	public User getByUsername(String username,Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取客户/供应商归属人信息（orgId，userId）
	 * @param traderId
	 * @param traderType 1客户 2供应商
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月18日 上午9:12:48
	 */
	public User getUserInfoByTraderId(Integer traderId,Integer traderType);
	
	/**
	 * <b>Description:</b><br> 根据客户ID查询客户负责人、及负责人所属部门
	 * @param traderIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月1日 下午1:53:24
	 */
	List<User> getTraderUserAndOrgList(List<Integer> traderIdList);
	
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
	 * <b>Description:</b><br> 根据部门id集合查询所有员工
	 * @param orgIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年1月19日 下午5:00:41
	 */
	List<Organization> getOrgNameByOrgIdList(List<Integer> orgIdList, Integer companyId);
	
	/**
	 * <b>Description:</b><br> 查询分类对应人员（产品归属人员）
	 * @param categoryIdList
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年2月2日 下午4:43:22
	 */
	List<User> getCategoryUserList(List<Integer> categoryIdList,Integer companyId);

	/**
	 * <b>Description:</b><br> 根据客户获取用户详细信息
	 * @param traderId
	 * @param i
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年3月23日 下午3:05:45
	 */
	public UserDetail getUserDetailByTraderId(Integer traderId, Integer type);

	/**
	 * <b>Description:</b><br> 获取销售部门部门信息列表（二级部，三级部）
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年6月11日 下午2:09:44
	 */
	public List<User> getSaleUserOrgList(Integer companyId);

	public List<User> getSaleUserOrgListAll(Integer companyId);
	
	/**
	 * 
	 * <b>Description: 根据userId获取用户的详情信息 例如手机号等</b><br> 
	 * @param userId
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月31日 上午10:53:25 </b>
	 */
	public UserDetail queryUserDetailByUserId(Integer userId);

	/**
	 * 
	 * <b>Description: 查询用户登录情况</b><br> 
	 * @param userLoginInfo
	 * @return
	 * <b>Author: Franlin</b>  
	 * <br><b>Date: 2018年7月31日 上午11:03:32 </b>
	 */
	public UserLoginLog queryLoginOrOutInfo(UserLoginLog userLoginInfo);
	
	/**
	 * 
	 * <b>Description: 根据userId的list批量查询用户信息</b><br> 
	 * @param userIdList
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2018年11月24日 下午4:14:10 </b>
	 */
	public List<User> getUserListByUserIdList(List<Integer> userIdList);

	/**
	 * 获取所有产品分配的人
	 * @return
	 */
	public List<User> selectAllAssignUser();


	/**
	  * 获取当前下级员工
	  * @author wayne.liu
	  * @date  2019/6/20 14:26
	  * @param user 用户信息
	  * @return
	  */
	public Set<User> getSubUserListForSaleApi(User user);

	/**
	* @Title: getUserInfoByMobile
	* @Description: TODO(通过注册手机号查找用户归属销售和erp客户信息)
	* @param @param traderContactMobile
	* @param @param i
	* @param @return    参数
	* @return User    返回类型
	* @author strange
	* @throws
	* @date 2019年7月17日
	*/
	public User getUserInfoByMobile(String traderContactMobile, Integer i);
	
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

	public User getBDUserInfoByMobile(String phone);

	public List<User> getActiveUserByPositType(Integer positionType, Integer companyId) ;

	List<Integer> getUserId(Integer proUserId);
	//刷新界面的带采购列表
	List<User> getUserListByOrgIddcg(Integer proOrgtId);

	List<User> getLeadersByParentId(Integer parentId,Integer positionType);
	/**
	*是否是销售  true 是 false 不是
	* @Author:strange
	* @Date:16:50 2020-02-21
	*/
    Boolean getSaleFlagUserId(Integer userId);
	/**
	*是否是物流人员  true 是 false 不是
	* @Author:strange
	* @Date:09:07 2020-02-26
	*/
    Boolean getLogisticeFlagByUserId(Integer userId);
	/**
	*获取用户角色
	* @Author:strange
	* @Date:09:10 2020-02-26
	*/
	List<Role> getRoleByUserId(Integer userId);
	/**
	*	获取该部门所有人员
	* @Author:strange
	* @Date:09:47 2020-03-11
	*/
    List<User> getOrgUserList(Saleorder saleorder, Integer companyId);
	/**
	*获取用户信息
	* @Author:strange
	* @Date:09:23 2020-04-03
	*/
	User getUserByName(User u);
	/**
	*获取用户信息通过工号
	* @Author:strange
	* @Date:09:27 2020-04-03
	*/
	User getUserByNumber(User u);

	/**
	 * 获取销售的归属平台
	 * @param userId 用户id
	 * @return 归属平台
	 */
	Integer getBelongPlatformOfUser(Integer userId, Integer companyId);
}