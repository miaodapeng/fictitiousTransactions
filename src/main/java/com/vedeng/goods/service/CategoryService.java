package com.vedeng.goods.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.CategoryAttribute;
import com.vedeng.goods.model.StandardCategory;

/**
 * <b>Description:</b><br> 产品分类管理接口
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng..service
 * <br><b>ClassName:</b> CategoryService
 * <br><b>Date:</b> 2017年5月12日 上午10:05:59
 */
public interface CategoryService {

	/**
	 * <b>Description:</b><br> 查询产品分类数据列表(分页)
	 * @param category
	 * @param page
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月12日 上午10:08:01
	 */
	Map<String,Object> getCategoryListPage(Category category,Page page);
	
	/**
	 * <b>Description:</b><br> 查询全部产品分类数据列表(不分页)
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月12日 上午10:08:20
	 */
	List<Category> getCategoryTreeList(Category category);
	
	
	List<Category> getCategoryIndexList(Category category);
	
	/**
	 * <b>Description:</b><br> 查询分类数据列表(根据条件，非树状型结构)
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午5:30:18
	 */
	List<Category> getCategoryList(Category category);
	
	/**
	 * <b>Description:</b><br> 保存新增产品分类信息
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月12日 下午3:54:11
	 */
	ResultInfo<?> addCategorySave(Category category);
	
	/**
	 * <b>Description:</b><br> 根据主键查询产品分类信息
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月12日 上午10:07:42
	 */
	Category getCategoryById(Category category);
	
	/**
	 * <b>Description:</b><br> 编辑产品分类信息保存
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 上午10:16:53
	 */
	ResultInfo<?> editCategory(Category category);
	
	/**
	 * <b>Description:</b><br> 刪除分類信息
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 上午11:04:00
	 */
	ResultInfo<?> delCategoryById(Category category);
	
	/**
	 * <b>Description:</b><br> 根据节点ID查询父级列表
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月18日 下午2:10:09
	 */
	List<Category> getParentCateList(Category category);

	/**
	 * <b>Description:</b><br> 查询分类归属
	 * @param category
	 * @param companyId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午1:24:14
	 */
	List<User> getUserByCategory(Integer categoryId, Integer companyId);

	/**
	 * <b>Description:</b><br> 保存分类归属
	 * @param userId
	 * @param categoryIds
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午1:30:12
	 */
	Boolean saveEditCategoryOwner(List<Integer> userId, String categoryIds, HttpSession session);

	/**
	 * <b>Description:</b><br> 根据节点ID查询父级列表(新国标分类)
	 * @param standardCategory
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年3月14日 下午4:43:48
	 */
	List<StandardCategory> getParentStandardCateList(StandardCategory standardCategory);

	/**
	 * <b>Description:</b><br> 查询分类数据列表(根据条件，非树状型结构)(新国标分类)
	 * @param standardCategory
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年3月15日 上午11:02:15
	 */
	List<StandardCategory> getStandardCategoryList(StandardCategory standardCategory);
	
	/**
	 * 
	 * <b>Description:</b><br> 根据分类查询对应分类归属，如果是为分配的返回产品部默认人
	 * @param categoryIds
	 * @param companyId
	 * @param level
	 * @return
	 * @Note
	 * <b>Author:</b> Michael
	 * <br><b>Date:</b> 2018年6月14日 下午1:16:19
	 */
	List<User> getCategoryOwner(List<Integer> categoryIds,Integer companyId);
	
	/**
	 * <b>Description:</b><br> 查询产品分类
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年6月21日 下午1:30:52
	 */
	List<Category> getCategoryListByParam(Category category);
}
