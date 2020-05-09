package com.vedeng.activiti.service;

import java.util.List;

import com.vedeng.order.model.SaleorderModifyApply;

/**
 * 
 * <b>Description:</b><br> 
 * @author Michael
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.activiti.service
 * <br><b>ClassName:</b> ActivitiAssigneeService
 * <br><b>Date:</b> 2017年11月21日 下午4:17:23
 */
public interface ActivitiAssigneeService {
    /**
     * 返回Session中的用户Name
     */
    String getSessionUser();
    
    /**
     * 
     * <b>Description:</b><br> 根据角色名称获取对应角色的人
     * @param level
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年12月4日 上午9:29:57
     */
    List<String> getUserListByRole(String roleName);
   
    /**
     * 
     * <b>Description:</b><br> 根据对人的名称获取他的直接上级（直接上级）
     * @param level
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年12月4日 上午9:29:57
     */
    String getUserParentsUser(String roleName);
    
    /**
     * 
     * <b>Description:</b><br> 根据部门orgId和对应的职位等级获取对应的人名字的集合
     * @param level
     * @return
     * @Note
     * <b>Author:</b> Michael
     * <br><b>Date:</b> 2017年12月4日 上午9:29:57
     */
    List<String> getUserByLevel(Integer orgId,Integer level);
    
    List<String> getUserByCategory(Integer categoryId);
    
    List<String> getUserByOrderId(Integer orderId,Integer type,Integer userType);

    String getAfterSaleUser(Integer orgId);
    
    List<String> getBuyorderUserIdBySMA(SaleorderModifyApply saleorderModifyApply);
}
