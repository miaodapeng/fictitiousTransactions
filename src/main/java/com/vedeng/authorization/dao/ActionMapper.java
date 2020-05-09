package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.User;

@Named("actionMapper")
public interface ActionMapper {
	
	List<Action> querylistpage(Map<String, Object> map);
	
    /**
     * <b>Description:</b><br> 删除功能
     * @param actionId 功能ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 下午1:53:49
     */
    int delete(Integer actionId);

    Action getActionByKey(Integer actionId);

    int insert(Action record);
    
    int update(Action record);
    
    List<Action> selectActionList(@Param("actiongroupId")Integer actiongroupId);
    
    /**
     * <b>Description:</b><br> 根据userId获取菜单
     * @param userId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月4日 下午5:39:05
     */
    List<Action> getMenuListByUserId(@Param("userId")Integer userId,@Param("currentLoginSystem")Integer currentLoginSystem);
    
    /**
     * <b>Description:</b><br> admin账户获取所有菜单
     * @param 
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月4日 下午5:39:05
     */
    List<Action> getAdminMenuList();
    
    /**
     * <b>Description:</b><br> 根据userId获取菜单权限
     * @param userId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月4日 下午5:39:05
     */
    List<Action> getMenuPowerListByUserId(@Param("userId")Integer userId);
    
    /**
     * <b>Description:</b><br> 根据userName获取菜单权限
     * @param userName
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月4日 下午5:39:05
     */
    List<Action> getMenuPowerListByUserName(User user);
    
    /**
     * <b>Description:</b><br> admin获取所有菜单权限
     * @param 
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月4日 下午5:39:05
     */
    List<Action> getAdminMenuPowerList();
    
    /**
     * <b>Description:</b><br>获取功能点的id根据请求url 
     * @param record
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年9月6日 下午1:52:59
     */
    Integer getActionId(Action record);

    /**
      * 根据平台获取功能点
      * @author wayne.liu
      * @date  2019/6/10 19:52
      * @param platformId
      * @return
      */
    List<Action> getActionListByPlatformId(@Param("platformId")Integer platformId);
}