package com.vedeng.authorization.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.Actiongroup;

@Named("actiongroupMapper")
public interface ActiongroupMapper {

/*    List<Actiongroup> findActiongroupByParentId(Actiongroup actiongroup);
    
    
    List<Actiongroup> findActiongroupByActiongroupId(Actiongroup actiongroup);*/
    
    /**
     * 查询全部部门信息
     * @Title: getActionGroupList 
     * @Description: TODO 
     * @param @return
     * @return List<Actiongroup>
     * @throws
     */
    List<Actiongroup> getActionGroupList();

    /**
      * 根据系统Id查询功能组
      * @author wayne.liu
      * @date  2019/6/10 14:53
      * @param  platformId
      * @return List<Actiongroup>
      */
    List<Actiongroup> getActionGroupListByPlatformId(@Param("platformId") Integer platformId);

    /**
      * 读取当前用户 所在系统的 所有角色下的功能分组
      * @author wayne.liu
      * @date  2019/6/13 16:40
      * @param
      * @return
      */
    List<Actiongroup> getActionGroupListByRoleIds(@Param("roleIds")List<Integer> roleIds,@Param("currentLoginSystem")Integer platformId);
    /**
     * 
     * @Title: insert 
     * @Description: 新增部门信息保存 
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    int insert(Actiongroup record);
    

    /**
     * 
     * @Title: getActionGroupByKey 
     * @Description: 根据主键查询部门信息详情  
     * @param @param record
     * @param @return
     * @return Actiongroup
     * @throws
     */
    Actiongroup getActionGroupByKey(Actiongroup record);
    
    /**
     * 
     * @Title: selectByPrimaryKey 
     * @Description: 根据主键查询部门信息详情   
     * @param @param actiongroupId
     * @param @return
     * @return Actiongroup
     * @throws
     */
    Actiongroup  selectByPrimaryKey(Integer actiongroupId);

    /**
     * 修改部门信息
     * @Title: update 
     * @Description: TODO 
     * @param @param record
     * @param @return
     * @return int
     * @throws
     */
    int update(Actiongroup record);
    
    Integer getParentLevel(Actiongroup record);
    
    int batchUpdateLevel(List<Map<String,Object>> list);
    
    /**
     * <b>Description:</b><br> 删除功能分组
     * @param actiongroupId 功能分组ID
     * @return 成功1 失败0
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年4月25日 下午1:53:20
     */
    Integer delete(Integer actiongroupId);
    

}