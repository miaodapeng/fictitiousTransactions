package com.vedeng.system.service;

import java.util.List;

import com.vedeng.authorization.model.Actiongroup;
import com.vedeng.authorization.model.RRoleActiongroup;
import com.vedeng.authorization.model.User;
import com.vedeng.common.service.BaseService;

/**
 * <b>Description:</b><br> 功能分組接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.service
 * <br><b>ClassName:</b> ActiongroupService
 * <br><b>Date:</b> 2017年4月25日 下午6:25:58
 */
public interface ActiongroupService extends BaseService {
	
	/**
	 * <b>Description:</b><br> 查询部门层级列表 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:25:46
	 */
	List<Actiongroup> getActionGroupList();
	
	/**
	 * <b>Description:</b><br> 新增部门 保存
	 * @param actiongroup
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:25:39
	 */
	int insert(Actiongroup actiongroup);
	
	/**
	 * <b>Description:</b><br> 根据主键查询部门信息详情 
	 * @param actiongroup
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:25:32
	 */
	Actiongroup getActionGroupByKey(Actiongroup actiongroup);
	
	/**
	 * <b>Description:</b><br> 修改部门信息 
	 * @param actiongroup
	 * @return
	 * @throws Exception 
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年4月25日 下午6:25:26
	 */
	int update(Actiongroup actiongroup) throws Exception;
	
	/**
	 * <b>Description:</b><br> 删除功能分组
	 * @param actiongroup 功能分组bean
	 * @return 成功1 失败0
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 下午1:52:11
	 */
	Integer delete(Actiongroup actiongroup);
	
	/**
	 * <b>Description:</b><br>跟据actiongroupid获取角色列表
	 * @param actiongroupId
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2017年5月10日 上午11:24:55
	 */
	List<RRoleActiongroup> getRRoleActiongroupListByActiongroupId(Integer actiongroupId);

	/**
	 * <b>Description:</b><br> 查询部门列表 
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月7日 上午10:51:42
	 */
	List<Actiongroup> getMenuActionGroupList(User user);


	/**
	  *
	  * @author wayne.liu
	  * @date  2019/6/19 14:08
	  * @param
	  * @return
	  */
	List<Actiongroup> getMenuActionGroupListForApi(User user);

	/**
	  * 根据所属系统查询功能分组
	  * @author wayne.liu
	  * @date  2019/6/10 14:46
	  * @param platformId 系统Id
	  * @return
	  */
	List<Actiongroup> getActionGroupListByPlatform(Integer platformId);

}
