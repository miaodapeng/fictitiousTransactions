package com.vedeng.goods.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.StandardCategory;
import com.vedeng.goods.service.CategoryService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.UserService;

/**
 * <b>Description:</b><br> 产品分类管理模块
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.controller
 * <br><b>ClassName:</b> TraderCategoryController
 * <br><b>Date:</b> 2017年5月11日 下午2:56:23
 */
@Controller
@RequestMapping("/goods/category")
public class CategoryController extends BaseController{
	
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	/**
	 * <b>Description:</b><br> 查询产品分类数据列表(不分页)
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月11日 下午2:56:53
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,Category category,HttpSession session
//			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize
			){
		
//		Page page = getPageTag(request,pageNo,pageSize);
//		Map<String,Object> map = categoryService.getCategoryListPage(category,page);
//		mv.addObject("categoryList",(List<Category>)map.get("list"));mv.addObject("category",category);
//		mv.addObject("page", (Page)map.get("page"));
		//只查詢所选择分类的平级记录（往下加载）
		ModelAndView mv = new ModelAndView();
		mv.setViewName("goods/category/index");
		
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		 Category c= new Category();
		 List<Category> list = new ArrayList<>();
		if(category.getParentId()==null){
		    //查询顶级产品分类
		    c.setParentId(0);
		    c.setCompanyId(user.getCompanyId());
		    c.setCategoryName(category.getCategoryName());
		    c.setCategoryId(category.getCategoryId());
		    c.setUserId(category.getUserId());
		     list = categoryService.getCategoryIndexList(c);//查询全部产品分类
		}else{
		    //查询返回按钮的id
		    Category pcategory = new Category();
		    pcategory.setCategoryId(category.getParentId());
		    pcategory = categoryService.getCategoryById(pcategory);
		    mv.addObject("pcategory",pcategory);
		     list = categoryService.getCategoryIndexList(category);//查询全部产品分类
		}
		mv.addObject("categoryList",list);
		
        	List<Category> cateList = categoryService.getCategoryList(c);
		mv.addObject("cateList", cateList);
		mv.addObject("category",category);
		
		//采购人员
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_311);//采购
		
		List<User> productUserList = userService.getUserListByPositType(SysOptionConstant.ID_311, user.getCompanyId());
		
		mv.addObject("productUserList", productUserList);
		
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加产品分类
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月11日 下午5:34:28
	 */
	@RequestMapping(value="/addCategory")
	public ModelAndView addCategory(HttpServletRequest request,Category category){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("goods/category/add_category");
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		//查询顶级产品分类
		category.setParentId(0);
		List<Category> categoryList = categoryService.getCategoryList(category);
		//List<Category> list = categoryService.getCategoryTreeList(category);
		mv.addObject("categoryList", categoryList);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增产品分类信息
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月12日 下午3:53:52
	 */
	@ResponseBody
	@RequestMapping(value="/addCategorySave")
	@SystemControllerLog(operationType = "add",desc = "保存新增产品分类信息")
	public ResultInfo<?> addCategorySave(HttpServletRequest request,Category category){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCreator(user.getUserId());
			category.setAddTime(DateUtil.sysTimeMillis());
			category.setUpdater(user.getUserId());
			category.setModTime(DateUtil.sysTimeMillis());
			
			category.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> resule= categoryService.addCategorySave(category);
		if(resule.getCode().equals(0) && resule.getData() != null && user.getCompanyId().equals(1)){
			Category cate = (Category) resule.getData();
			if(cate.getCategoryId() > 0){
				vedengSoapService.categorySync(cate.getCategoryId(), false);
			}
		}
		return resule;
	}
	
	/**
	 * <b>Description:</b><br> 根据主键查询产品分类信息
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月12日 上午10:05:17
	 */
	@RequestMapping(value="/getCategoryById")
	public ModelAndView getCategoryById(HttpServletRequest request,Category category){
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("goods/category/edit_category");
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			if(user!=null){
				category.setCompanyId(user.getCompanyId());
			}
			Category c = new Category();
			c.setCompanyId(category.getCompanyId());
			
			//查询顶级产品分类
			c.setParentId(0);
			List<Category> list = categoryService.getCategoryList(c);
			//List<Category> list = categoryService.getCategoryTreeList(c);
			mv.addObject("categoryList", list);
			
			category = categoryService.getCategoryById(category);
			mv.addObject("category", category);
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(category)));
		} catch (Exception e) {
			logger.error("getCategoryById:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 编辑产品分类信息保存
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 上午10:16:35
	 */
	@ResponseBody
	@RequestMapping(value="/editCategory")
	@SystemControllerLog(operationType = "edit",desc = "编辑产品分类信息保存")
	public ResultInfo<?> editCategory(HttpServletRequest request,String beforeParams,Category category){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
			
			category.setUpdater(user.getUserId());
			category.setModTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> resule= categoryService.editCategory(category);
		if(resule.getCode().equals(0) && user.getCompanyId().equals(1)){
			vedengSoapService.categorySync(category.getCategoryId(), false);
		}
		return resule;
	}
	
	/**
	 * <b>Description:</b><br> 刪除分類信息
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月15日 上午11:03:19
	 */
	@ResponseBody
	@RequestMapping(value="/delCategoryById")
	@SystemControllerLog(operationType = "delete",desc = "刪除分類信息")
	public ResultInfo<?> delCategoryById(HttpServletRequest request,Category category){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
			category.setUpdater(user.getUserId());
			category.setModTime(System.currentTimeMillis());
		}
		ResultInfo<?> resule= categoryService.delCategoryById(category);
		if(resule.getCode().equals(0) && user.getCompanyId().equals(1)){
			vedengSoapService.categorySync(category.getCategoryId(), true);
		}
		return resule;
	}
	
	/**
	 * <b>Description:</b><br> 查询分类数据列表(根据条件，非树状型结构)
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午5:38:48
	 */
	@ResponseBody
	@RequestMapping(value="/getCategoryList")
	public ResultInfo<?> getCategoryList(HttpServletRequest request,Category category){
		ResultInfo<Category> result = new ResultInfo<>();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		List<Category> categoryList = categoryService.getCategoryList(category);
		result.setCode(0);result.setMessage("操作成功");
		result.setListData(categoryList);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 根据节点ID查询父级列表
	 * @param request
	 * @param category
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月18日 下午2:10:15
	 */
	@ResponseBody
	@RequestMapping(value="/getParentCateList")
	public ResultInfo<?> getParentCateList(HttpServletRequest request,Category category){
		ResultInfo<Category> result = new ResultInfo<>();User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		List<Category> categoryList = categoryService.getParentCateList(category);
		result.setCode(0);result.setMessage("操作成功");
		result.setListData(categoryList);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 编辑分类归属
	 * @param request
	 * @param session
	 * @param manageCategories
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年11月22日 下午1:22:24
	 */
	@ResponseBody
	@RequestMapping(value="/editcategoryowner")
	public ModelAndView editCategoryOwner(HttpServletRequest request,HttpSession session,@RequestParam("categoryIds")String categoryIds){
		if(null == categoryIds || categoryIds.equals("")){
			return pageNotFound();
		}
		ModelAndView mv = new ModelAndView();
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		
		String[] categoryList = categoryIds.split(",");
		
		//单个分类归属更改
		if(categoryList.length == 1){
			Integer categoryId = Integer.parseInt(categoryList[0]);
			List<User> users = categoryService.getUserByCategory(categoryId, user.getCompanyId());
			mv.addObject("users", users);
		}
		
		//采购人员
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_311);//采购
		List<User> productUserList = userService.getMyUserList(user, positionType, false);
		
		mv.addObject("productUserList", productUserList);
		mv.addObject("categoryIds", categoryIds);
		
		mv.setViewName("goods/category/edit_assign");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑分类归属
	 * @param request
	 * @param session
	 * @param manageCategories
	 * @param userId
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年10月30日 下午3:30:55
	 */
	@ResponseBody
	@RequestMapping(value="/saveeditcategoryowner")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑分类归属")
	public ResultInfo saveEditCategoryOwner(HttpServletRequest request,HttpSession session,
			@RequestParam("categoryIds")String categoryIds,
			@RequestParam("userId")List<Integer> userId){
		ResultInfo resultInfo = new ResultInfo<>();
		Boolean res = categoryService.saveEditCategoryOwner(userId,categoryIds,session);
		if(res){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 查询分类数据列表(根据条件，非树状型结构)(新国标分类)
	 * @param request
	 * @param standardCategory
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年3月16日 下午2:48:51
	 */
	@ResponseBody
	@RequestMapping(value="/getStandardCategoryList")
	public ResultInfo<?> getStandardCategoryList(HttpServletRequest request,StandardCategory standardCategory){
		ResultInfo<StandardCategory> result = new ResultInfo<>();
		List<StandardCategory> standardCategoryList = categoryService.getStandardCategoryList(standardCategory);
		result.setCode(0);result.setMessage("操作成功");
		result.setListData(standardCategoryList);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 根据节点ID查询父级列表（新国标分类）
	 * @param request
	 * @param standardCategory
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年3月16日 下午2:50:17
	 */
	@ResponseBody
	@RequestMapping(value="/getParentStandardCateList")
	public ResultInfo<?> getParentStandardCateList(HttpServletRequest request,StandardCategory standardCategory){
		ResultInfo<StandardCategory> result = new ResultInfo<>();
		List<StandardCategory> standardCategoryList = categoryService.getParentStandardCateList(standardCategory);
		result.setCode(0);
		result.setMessage("操作成功");
		result.setListData(standardCategoryList);
		return result;
	}
}
