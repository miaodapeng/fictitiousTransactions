package com.vedeng.firstengage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.vedeng.firstengage.model.FirstengageCategory;
import com.vedeng.firstengage.service.FirstengageCategoryService;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.StandardCategory;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.UserService;

/**
 * 首营产品分类管理模块
 * <b>Description:</b><br>
 * @author chuck
 * @Note
 * <b>ProjectName:</b> erp.vedeng.com
 * <br><b>PackageName:</b> com.vedeng.firstengage.controller
 * <br><b>ClassName:</b> FirstengageCategoryController
 * <br><b>Date:</b> 2019年3月28日 下午8:06:21
 */
@Controller
@RequestMapping("/firstengage/category")
public class FirstengageCategoryController extends BaseController{
	public static Logger logger = LoggerFactory.getLogger(FirstengageCategoryController.class);

	@Autowired
	@Qualifier("firstengageCategoryService")
	private FirstengageCategoryService firstengageCategoryService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	/**
	 * 
	 * <b>Description:</b>查询产品分类数据列表(不分页)
	 * @param request
	 * @param category
	 * @param session
	 * @return ModelAndView
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:11:17
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,FirstengageCategory category,HttpSession session){
		//只查詢所选择分类的平级记录（往下加载）
		ModelAndView mv = new ModelAndView();
		mv.setViewName("firstengage/category/index");
		
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		FirstengageCategory c= new FirstengageCategory();
		List<FirstengageCategory> list = new ArrayList<>();
		if(category.getParentId()==null){
		    //查询顶级产品分类
		    c.setParentId(0);
		    c.setCompanyId(user.getCompanyId());
		    c.setCategoryName(category.getCategoryName());
		    c.setCategoryId(category.getCategoryId());
		    c.setUserId(category.getUserId());
		     list = firstengageCategoryService.getCategoryIndexList(c);//查询全部产品分类
		}else{
		    //查询返回按钮的id
			FirstengageCategory pcategory = new FirstengageCategory();
		    pcategory.setCategoryId(category.getParentId());
		    pcategory = firstengageCategoryService.getCategoryById(pcategory);
		    mv.addObject("pcategory",pcategory);
		     list = firstengageCategoryService.getCategoryIndexList(category);//查询全部产品分类
		}
		mv.addObject("categoryList",list);
		
        	List<FirstengageCategory> cateList = firstengageCategoryService.getCategoryList(c);
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
	 * 
	 * <b>Description:</b>添加产品分类
	 * @param request
	 * @param category
	 * @return ModelAndView
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:11:03
	 */
	@RequestMapping(value="/addCategory")
	public ModelAndView addCategory(HttpServletRequest request,FirstengageCategory category){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("firstengage/category/add_category");
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		//查询顶级产品分类
		category.setParentId(0);
		List<FirstengageCategory> categoryList = firstengageCategoryService.getCategoryList(category);
		//List<Category> list = categoryService.getCategoryTreeList(category);
		mv.addObject("categoryList", categoryList);
		mv.addObject("category", category);
		
		return mv;
	}
	
	/**
	 * 
	 * <b>Description:</b>保存新增产品分类信息
	 * @param request
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:10:50
	 */
	@ResponseBody
	@RequestMapping(value="/addCategorySave")
	@SystemControllerLog(operationType = "add",desc = "保存新增产品分类信息")
	public ResultInfo<?> addCategorySave(HttpServletRequest request,FirstengageCategory category){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCreator(user.getUserId());
			category.setAddTime(DateUtil.sysTimeMillis());
			category.setUpdater(user.getUserId());
			category.setModTime(DateUtil.sysTimeMillis());
			category.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> result= firstengageCategoryService.addCategorySave(category);
//		if(result.getCode().equals(0) && result.getData() != null && user.getCompanyId().equals(1)){
//			FirstengageCategory cate = (FirstengageCategory) result.getData();
//			if(cate.getCategoryId() > 0){
//				vedengSoapService.categorySync(cate.getCategoryId(), false);
//			}
//		}
		return result;
	}
	
	/**
	 * 
	 * <b>Description:</b>根据主键查询产品分类信息
	 * @param request
	 * @param category
	 * @return ModelAndView
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:10:39
	 */
	@RequestMapping(value="/getCategoryById")
	public ModelAndView getCategoryById(HttpServletRequest request,FirstengageCategory category){
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("goods/category/edit_category");
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			if(user!=null){
				category.setCompanyId(user.getCompanyId());
			}
			FirstengageCategory c = new FirstengageCategory();
			c.setCompanyId(category.getCompanyId());
			
			//查询顶级产品分类
			c.setParentId(0);
			List<FirstengageCategory> list = firstengageCategoryService.getCategoryList(c);
			//List<Category> list = categoryService.getCategoryTreeList(c);
			mv.addObject("categoryList", list);
			
			category = firstengageCategoryService.getCategoryById(category);
			mv.addObject("category", category);
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(category)));
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return mv;
	}
	
	/**
	 * 
	 * <b>Description:</b>编辑产品分类信息保存
	 * @param request
	 * @param beforeParams
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:10:25
	 */
	@ResponseBody
	@RequestMapping(value="/editCategory")
	@SystemControllerLog(operationType = "edit",desc = "编辑产品分类信息保存")
	public ResultInfo<?> editCategory(HttpServletRequest request,String beforeParams,FirstengageCategory category){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
			
			category.setUpdater(user.getUserId());
			category.setModTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> resule= firstengageCategoryService.editCategory(category);
		if(resule.getCode().equals(0) && user.getCompanyId().equals(1)){
			vedengSoapService.categorySync(category.getCategoryId(), false);
		}
		return resule;
	}
	
	/**
	 * 
	 * <b>Description:</b>刪除分類信息
	 * @param request
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:10:10
	 */
	@ResponseBody
	@RequestMapping(value="/delCategoryById")
	@SystemControllerLog(operationType = "delete",desc = "刪除分類信息")
	public ResultInfo<?> delCategoryById(HttpServletRequest request,FirstengageCategory category){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
			category.setUpdater(user.getUserId());
			category.setModTime(System.currentTimeMillis());
		}
		ResultInfo<?> resule= firstengageCategoryService.delCategoryById(category);
		if(resule.getCode().equals(0) && user.getCompanyId().equals(1)){
			vedengSoapService.categorySync(category.getCategoryId(), true);
		}
		return resule;
	}
	
	/**
	 * 
	 * <b>Description:</b>查询分类数据列表(根据条件，非树状型结构)
	 * @param request
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:09:56
	 */
	@ResponseBody
	@RequestMapping(value="/getCategoryList")
	public ResultInfo<?> getCategoryList(HttpServletRequest request,FirstengageCategory category){
		ResultInfo<FirstengageCategory> result = new ResultInfo<>();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		List<FirstengageCategory> categoryList = firstengageCategoryService.getCategoryList(category);
		result.setCode(0);result.setMessage("操作成功");
		result.setListData(categoryList);
		return result;
	}
	
	/**
	 * 
	 * <b>Description:</b>根据节点ID查询父级列表
	 * @param request
	 * @param category
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:09:35
	 */
	@ResponseBody
	@RequestMapping(value="/getParentCateList")
	public ResultInfo<?> getParentCateList(HttpServletRequest request,FirstengageCategory category){
		ResultInfo<FirstengageCategory> result = new ResultInfo<>();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		List<FirstengageCategory> categoryList = firstengageCategoryService.getParentCateList(category);
		result.setCode(0);result.setMessage("操作成功");
		result.setListData(categoryList);
		return result;
	}
	
	/**
	 * 
	 * <b>Description:</b>编辑分类归属
	 * @param request
	 * @param session
	 * @param categoryIds
	 * @return ModelAndView
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:09:25
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
			List<User> users = firstengageCategoryService.getUserByCategory(categoryId, user.getCompanyId());
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
	 * 
	 * <b>Description:</b>保存编辑分类归属
	 * @param request
	 * @param session
	 * @param categoryIds
	 * @param userId
	 * @return ResultInfo
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:08:45
	 */
	@ResponseBody
	@RequestMapping(value="/saveeditcategoryowner")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑分类归属")
	public ResultInfo saveEditCategoryOwner(HttpServletRequest request,HttpSession session,
			@RequestParam("categoryIds")String categoryIds,
			@RequestParam("userId")List<Integer> userId){
		ResultInfo resultInfo = new ResultInfo<>();
		Boolean res = firstengageCategoryService.saveEditCategoryOwner(userId,categoryIds,session);
		if(res){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * 
	 * <b>Description:</b>查询分类数据列表(根据条件，非树状型结构)(新国标分类)
	 * @param request
	 * @param standardCategory
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:08:36
	 */
	@ResponseBody
	@RequestMapping(value="/getStandardCategoryList")
	public ResultInfo<?> getStandardCategoryList(HttpServletRequest request,StandardCategory standardCategory){
		ResultInfo<StandardCategory> result = new ResultInfo<>();
		List<StandardCategory> standardCategoryList = firstengageCategoryService.getStandardCategoryList(standardCategory);
		result.setCode(0);result.setMessage("操作成功");
		result.setListData(standardCategoryList);
		return result;
	}
	
	/**
	 * 根据节点ID查询父级列表（新国标分类）
	 * <b>Description:</b>
	 * @param request
	 * @param standardCategory
	 * @return ResultInfo<?>
	 * @Note
	 * <b>Author：</b> chuck
	 * <b>Date:</b> 2019年3月28日 下午8:08:26
	 */
	@ResponseBody
	@RequestMapping(value="/getParentStandardCateList")
	public ResultInfo<?> getParentStandardCateList(HttpServletRequest request,StandardCategory standardCategory){
		ResultInfo<StandardCategory> result = new ResultInfo<>();
		List<StandardCategory> standardCategoryList = firstengageCategoryService.getParentStandardCateList(standardCategory);
		result.setCode(0);
		result.setMessage("操作成功");
		result.setListData(standardCategoryList);
		return result;
	}
}
