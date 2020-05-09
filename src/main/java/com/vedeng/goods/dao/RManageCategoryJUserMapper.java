package com.vedeng.goods.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.goods.model.RManageCategoryJUser;
@Named("rManageCategoryJUserMapper")
public interface RManageCategoryJUserMapper {
    /**
     * <b>Description:</b><br> 新增归属
     * @param rManageCategoryJUser
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年10月30日 下午3:40:53
     */
    int insert(RManageCategoryJUser rManageCategoryJUser);
    
    /**
     * <b>Description:</b><br> 获取管理分类归属
     * @param manageCategory
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年10月30日 下午1:01:18
     */
    List<User> getUserByManageCategory(@Param("manageCategory")Integer manageCategory,@Param("companyId")Integer companyId);
    
    /**
     * <b>Description:</b><br> 获取管理分类归属
     * @param manageCategory
     * @param companyId
     * @return
     * @Note
     * <b>Author:</b> duke
     * <br><b>Date:</b> 2017年11月16日 下午2:48:17
     */
    String getUserNmByManageCategory(@Param("manageCategory")Integer manageCategory,@Param("companyId")Integer companyId);

    /**
     * <b>Description:</b><br> 批量获取管理分类归属
     * @param manageCategories
     * @param companyId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年10月30日 下午5:53:01
     */
    List<User> getUserByManageCategories(@Param("manageCategories")List<Integer> manageCategories,@Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 删除归属
	 * @param manageCategory
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 下午3:38:18
	 */
	int deleteByCateCompany(@Param("manageCategory")Integer manageCategory, @Param("companyId")Integer companyId);
    
}