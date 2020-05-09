package com.vedeng.firstengage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.vedeng.authorization.model.User;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.firstengage.model.FirstengageCategory;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.StandardCategory;

/**
 * 
 * <b>Description:</b><br>首营产品分类管理接口
 * @author chuck
 * @Note
 * <b>ProjectName:</b> erp.vedeng.com
 * <br><b>PackageName:</b> com.vedeng.firstengage.service
 * <br><b>ClassName:</b> FirstengageCategoryService
 * <br><b>Date:</b> 2019年3月29日 上午9:30:53
 */
public interface FirstengageCategoryService {

	/**
	 * 
	 * <b>Description:</b>查询首营产品分类数据列表(分页)
	 * @param category
	 * @param page
	 * @return Map<String,Object>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:01:22
	 */
	Map<String,Object> getCategoryListPage(FirstengageCategory category,Page page);
	
	/**
	 * 
	 * <b>Description:</b>查询全部首营产品分类数据列表(不分页)
	 * @param category
	 * @return List<Category>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:01:43
	 */
	List<FirstengageCategory> getCategoryTreeList(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>
	 * @param category
	 * @return List<Category>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:02:26
	 */
	List<FirstengageCategory> getCategoryIndexList(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>查询首营分类数据列表(根据条件，非树状型结构)
	 * @param category
	 * @return List<Category>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:02:49
	 */
	List<FirstengageCategory> getCategoryList(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>保存新增首营分类信息
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:05:31
	 */
	ResultInfo<?> addCategorySave(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>根据主键查询首营分类信息
	 * @param category
	 * @return Category
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:05:52
	 */
	FirstengageCategory getCategoryById(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>编辑首营分类信息保存
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:06:15
	 */
	ResultInfo<?> editCategory(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>刪除首营分類信息
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:20:21
	 */
	ResultInfo<?> delCategoryById(FirstengageCategory category);
	
	/**
	 * 
	 * <b>Description:</b>根据节点ID查询父级列表
	 * @param category
	 * @return List<Category>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:20:41
	 */
	List<FirstengageCategory> getParentCateList(FirstengageCategory category);

	/**
	 * 
	 * <b>Description:</b>查询首营分类归属
	 * @param categoryId
	 * @param companyId
	 * @return List<User>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:20:54
	 */
	List<User> getUserByCategory(Integer categoryId, Integer companyId);

	/**
	 * 
	 * <b>Description:</b>保存首营分类归属
	 * @param userId
	 * @param categoryIds
	 * @param session
	 * @return Boolean
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:21:15
	 */
	Boolean saveEditCategoryOwner(List<Integer> userId, String categoryIds, HttpSession session);

	/**
	 * 
	 * <b>Description:</b>根据节点ID查询父级列表(新国标分类)
	 * @param standardCategory
	 * @return List<StandardCategory>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:21:31
	 */
	List<StandardCategory> getParentStandardCateList(StandardCategory standardCategory);

	/**
	 * 
	 * <b>Description:</b>查询首营分类数据列表(根据条件，非树状型结构)(新国标分类)
	 * @param standardCategory
	 * @return List<StandardCategory>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:21:43
	 */
	List<StandardCategory> getStandardCategoryList(StandardCategory standardCategory);
	
	/**
	 * 
	 * <b>Description:</b>根据首营分类查询对应分类归属，如果是为分配的返回产品部默认人
	 * @param categoryIds
	 * @param companyId
	 * @return List<User>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:22:08
	 */
	List<User> getCategoryOwner(List<Integer> categoryIds,Integer companyId);
	
	/**
	 * 
	 * <b>Description:</b>查询首营产品分类
	 * @param category
	 * @return List<Category>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月29日 上午10:22:26
	 */
	List<FirstengageCategory> getCategoryListByParam(FirstengageCategory category);
}
