package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.RRoleAction;
import com.vedeng.authorization.model.User;
import com.vedeng.common.page.Page;


/**
 * <b>Description:</b><br> 功能(节点)分组接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> ActionService
 * <br><b>Date:</b> 2017年4月25日 下午6:21:26
 */
public interface ActionService{
	
	
	/**
	 * <b>Description:</b><br> 查询节点列表数据
	 * @param action
	 * @param page
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:21:51
	 */
	public List<Action> querylistpage(Action action,Page page) throws Exception;
	

	/**
	 * <b>Description:</b><br> 根据主键查询节点详细信息
	 * @param actionId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:22:08
	 */
	public Action getActionByKey(Integer actionId);
	

	/**
	 * <b>Description:</b><br> 添加节点保存数据
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:22:19
	 */
	public int insert(Action action);
	

	/**
	 * <b>Description:</b><br> 修改节点信息
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:22:30
	 */
	public int update(Action action);
	

	/**
	 * <b>Description:</b><br> 查询全部节点信息（不分页） 
	 * @param action
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:22:56
	 */
	/*public List<Action> selectActionList(Action action);*/
	
	/**
	 * <b>Description:</b><br> 删除功能
	 * @param action 功能bean
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:47:34
	 */
	Integer delete(Action action);
	
	/**
	 * <b>Description:</b><br> 按功能分组查询功能
	 * @param actiongroupId 功能分组ID
	 * @return List<Action>
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午2:16:23
	 */
	List<Action> getActionByActiongroupId(Integer actiongroupId);
	/**
	 * <b>Description:</b><br> 根据当前用户获取菜单
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月4日 下午5:35:15
	 */
	List<Action> getActionList(User user,String roleIds);

	/**
	  * 外部接口访问功能点接口
	  * @author wayne.liu
	  * @date  2019/6/19 14:11
	  * @param
	  * @return
	  */
	List<Action> getActionListForApi(User user);
	
	/**
	 * <b>Description:</b><br> 根据当前用户获取菜单的权限
	 * @param user
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月4日 下午5:35:15
	 */
	List<Action> getActionList(User user);
	
	/**
	 * <b>Description:</b><br> 根据当前用户获取菜单的权限
	 * @param userName
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月4日 下午5:35:15
	 */
	List<Action> getActionListByUserName(User user);
	
	/**
	 * <b>Description:</b><br> 获取角色与功能的列表
	 * @param actionId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月10日 上午11:06:22
	 */
	List<RRoleAction> getRRoleActionListByActionId(Integer actionId);
	
	/**
	 * <b>Description:</b><br> 跟据请求url查询功能点
	 * @param modelName
	 * @param controllerNmae
	 * @param actionName
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年9月6日 下午1:48:44
	 */
	Integer getActionId(String modelName,String controllerNmae,String actionName);

	/**
	  * 根据平台获取功能点集合
	  * @author wayne.liu
	  * @date  2019/6/10 19:50
	  * @param platformId
	  * @return list
	  */
	List<Action> getActionListByPlatformId(Integer platformId);

}
