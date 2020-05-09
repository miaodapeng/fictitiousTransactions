package com.vedeng.goods.dao;

import java.util.List;

import javax.inject.Named;

import org.apache.ibatis.annotations.Param;

import com.vedeng.authorization.model.User;
import com.vedeng.goods.model.RCategoryJUser;
import com.vedeng.goods.model.vo.GoodsVo;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
@Named("rCategoryJUserMapper")
public interface RCategoryJUserMapper {
    /**
     * <b>Description:</b><br> 新增分类归属
     * @param rCategoryJUser
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月22日 下午1:32:48
     */
    int insert(RCategoryJUser rCategoryJUser);
    
	/**
	 * <b>Description:</b><br> 获取分类归属
	 * @param categoryId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午1:27:43
	 */
	List<User> batchUserByCategory(@Param("categoryIds")List<GoodsVo> categoryIds, @Param("companyId")Integer companyId);
    
	/**
	 * <b>Description:</b><br> 获取分类归属
	 * @param categoryId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午1:27:43
	 */
	List<User> batchUserCategoryBySaleorderVo(@Param("categoryIds")List<SaleorderGoodsVo> categoryIds, @Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 获取分类归属
	 * @param categoryId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午1:27:43
	 */
	List<User> getUserByCategory(@Param("categoryId")Integer categoryId, @Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 批量查询分类归属
	 * @param categoryIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 下午4:52:31
	 */
	List<User> getUserByCategoryIds(@Param("categoryIds")List<Integer> categoryIds, @Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 批量查询分类归属直接上级
	 * @param categoryIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 下午4:52:31
	 */
	List<User> getUserParentByCategoryIds(@Param("categoryIds")List<Integer> categoryIds, @Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 根据level查询分类归属
	 * @param categoryIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月30日 下午4:52:31
	 */
	List<User> getTypeUserByCategoryIds(@Param("categoryIds")List<Integer> categoryIds, @Param("companyId")Integer companyId, @Param("level")Integer level);
	/**
	 * <b>Description:</b><br> 批量查询分类归属人员ID
	 * @param categoryIds
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年12月14日 上午9:59:36
	 */
	List<Integer> getUserIdByCategoryIdList(@Param("categoryIdList")List<Integer> categoryIdList, @Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 获取分类归属（字符串：多个负责人拼接）
	 * @param categoryId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月28日 下午4:58:36
	 */
	String getUserByCategoryNm(@Param("categoryId")Integer categoryId, @Param("companyId")Integer companyId);
	
	/**
	 * <b>Description:</b><br> 删除归属
	 * @param categoryId
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 下午3:38:18
	 */
	int deleteByCateCompany(@Param("categoryId")Integer categoryId, @Param("companyId")Integer companyId);

	/**
	 * <b>Description:</b><br> 根据归属查询分类ID集合
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午2:09:50
	 */
	List<Integer> getCategoryIdsByUserId(@Param("userId")Integer userId);
	
	/**
	 * <b>Description:</b><br> 根据归属查询分类ID集合
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午2:09:50
	 */
	List<Integer> getCategoryIdsByUserList(@Param("userList")List<User> userList);

	/**
	 * <b>Description:</b><br> 根据产品分类ID查询商品归属人员
	 * @param categoryIdList
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2018年5月3日 上午10:43:24
	 */
	List<User> getGoodsCategoryUserList(@Param("categoryIdList")List<Integer> categoryIdList, @Param("companyId")Integer companyId);
    
}