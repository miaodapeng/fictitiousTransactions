package com.vedeng.goods.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.goods.model.Category;
import com.vedeng.goods.model.CategoryAttribute;
import com.vedeng.goods.model.CategoryAttributeValue;
import com.vedeng.goods.service.CategoryAttributeService;
import com.vedeng.goods.service.CategoryService;
import com.vedeng.soap.service.VedengSoapService;

/**
 * <b>Description:</b><br> 属性管理
 * @author duke
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.controller
 * <br><b>ClassName:</b> CategoryAttributeController
 * <br><b>Date:</b> 2017年5月17日 下午2:27:25
 */
@Controller
@RequestMapping("/goods/categoryattribute")
public class CategoryAttributeController extends BaseController{
	public static Logger logger = LoggerFactory.getLogger(CategoryAttributeController.class);

	@Autowired
	@Qualifier("categoryAttributeService")
	private CategoryAttributeService categoryAttributeService;
	
	
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	/**
	 * <b>Description:</b><br> 查询产品分类属性列表（分页）
	 * @param request
	 * @param categoryAttribute
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:27:37
	 */
	@ResponseBody
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request,CategoryAttribute categoryAttribute,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView();
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			categoryAttribute.setCompanyId(user.getCompanyId());
		}
		
		Page page = getPageTag(request,pageNo,pageSize);
		
		Map<String,Object> map = categoryAttributeService.getCateAttributeListPage(categoryAttribute,page);
		
		mv.addObject("categoryAttributeList",(List<CategoryAttribute>)map.get("list"));
		mv.addObject("categoryAttribute",categoryAttribute);
		
		mv.addObject("page", (Page)map.get("page"));
		
		if(categoryAttribute.getCategoryId()==null){
			//查询顶级产品分类
			Category cg = new Category();
			cg.setParentId(0);
			cg.setCompanyId(user.getCompanyId());
			List<Category> cateList = categoryService.getCategoryList(cg);
			mv.addObject("cateList", cateList);
			
		}
		mv.setViewName("goods/categoryAttribute/index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 查詢属性信息
	 * @param request
	 * @param brand
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月16日 下午7:00:28
	 */
	@ResponseBody
	@RequestMapping(value="/getCateAttributeByKey")
	public ModelAndView getCateAttributeByKey(HttpServletRequest request,CategoryAttribute categoryAttribute){
		ModelAndView mv = new ModelAndView();
		try {
			if(categoryAttribute!=null && categoryAttribute.getCategoryAttributeId()!=null){
				categoryAttribute = categoryAttributeService.getCateAttributeByKey(categoryAttribute);
			}
			mv.addObject("categoryAttribute",categoryAttribute);
			
			//查询顶级产品分类
			Category category = new Category();
			category.setParentId(categoryAttribute.getParentId());
			List<Category> categoryList = categoryService.getCategoryList(category);
			mv.addObject("categoryList", categoryList);
			mv.setViewName("goods/categoryAttribute/edit_cateAttribute");
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(categoryAttribute)));
		} catch (Exception e) {
			logger.error("getCateAttributeByKey:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加产品属性初始化
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:47:44
	 */
	@RequestMapping(value="/addCateAttribute")
	public ModelAndView addCateAttribute(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		//查询顶级产品分类
		Category category = new Category();
		category.setParentId(0);
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			category.setCompanyId(user.getCompanyId());
		}
		List<Category> categoryList = categoryService.getCategoryList(category);
		mv.addObject("categoryList", categoryList);
		mv.setViewName("goods/categoryAttribute/add_cateAttribute");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加属性信息保存
	 * @param request
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:48:35
	 */
	@ResponseBody
	@RequestMapping(value="/saveCateAttribute")
	@SystemControllerLog(operationType = "add",desc = "保存新增属性信息")
	public ResultInfo<?> saveCateAttribute(HttpServletRequest request,CategoryAttribute categoryAttribute){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			categoryAttribute.setCreator(user.getUserId());
			categoryAttribute.setAddTime(DateUtil.sysTimeMillis());
			categoryAttribute.setUpdater(user.getUserId());
			categoryAttribute.setModTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> result = categoryAttributeService.saveCateAttribute(categoryAttribute);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 编辑属性信息保存
	 * @param request
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:48:55
	 */
	@ResponseBody
	@RequestMapping(value="/editCateAttribute")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑属性信息")
	public ResultInfo<?> editCateAttribute(HttpServletRequest request,String beforeParams,CategoryAttribute categoryAttribute){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			categoryAttribute.setUpdater(user.getUserId());
			categoryAttribute.setModTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> result = categoryAttributeService.editCateAttribute(categoryAttribute);
		if(result.getCode().equals(0) && user.getCompanyId().equals(1)){
			vedengSoapService.categoryAttrSync(categoryAttribute.getCategoryAttributeId());
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 删除属性信息
	 * @param request
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月17日 下午2:49:09
	 */
	@ResponseBody
	@RequestMapping(value="/delCateAttribute")
	@SystemControllerLog(operationType = "delete",desc = "删除属性信息")
	public ResultInfo<?> delCateAttribute(HttpServletRequest request,CategoryAttribute categoryAttribute){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			categoryAttribute.setUpdater(user.getUserId());
			categoryAttribute.setModTime(DateUtil.sysTimeMillis());
		}
		if(categoryAttribute==null){
			return new ResultInfo<>(-1, "参数为空");
		}
		ResultInfo<?> result = categoryAttributeService.delCateAttribute(categoryAttribute);
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 添加屬性值初始化
	 * @param request
	 * @param categoryAttributeValue
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月19日 下午1:53:20
	 */
	@ResponseBody
	@RequestMapping(value="/editCateAttributeValue")
	public ModelAndView editCateAttributeValue(HttpServletRequest request,CategoryAttributeValue categoryAttributeValue){
		ModelAndView mv = new ModelAndView();
		try {
//			String categoryAttributeName = java.net.URLDecoder.decode(categoryAttributeValue.getCategoryAttributeName(),"UTF-8");
//			categoryAttributeName = java.net.URLEncoder.encode(categoryAttributeName,"UTF-8");
//			categoryAttributeValue.setCategoryAttributeName(categoryAttributeName);
			CategoryAttribute cab = new CategoryAttribute();
			cab.setCategoryAttributeId(categoryAttributeValue.getCategoryAttributeId());
			cab = categoryAttributeService.getCateAttributeByKey(cab);
			categoryAttributeValue.setCategoryAttributeName(cab.getCategoryAttributeName());
			//获取属性值，根据属性ID
			List<CategoryAttributeValue> list = categoryAttributeService.getCateAttrValueByKey(categoryAttributeValue);
			
			mv.addObject("cateAttrValueList",list);
			mv.addObject("categoryAttributeValue",categoryAttributeValue);
			mv.setViewName("goods/categoryAttribute/edit_cateAttribute_value");
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(categoryAttributeValue)));
		} catch (Exception e) {
			logger.error("editCateAttributeValue:", e);
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 属性值添加、编辑保存
	 * @param request
	 * @param categoryAttributeValue
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月19日 下午1:53:41
	 */
	@ResponseBody
	@RequestMapping(value="/updateCateAttributeValue")
	@SystemControllerLog(operationType = "edit",desc = "编辑属性值保存")//异步提交
	public ResultInfo<?> updateCateAttributeValue(HttpServletRequest request,String beforeParams,CategoryAttributeValue categoryAttributeValue){
		
		List<String> attrValueArr = JSON.parseArray(request.getParameter("attrValueArr").toString(),String.class);
		List<String> sortArr = JSON.parseArray(request.getParameter("sortArr").toString(),String.class);
		
		List<String> dateTypeArr = JSON.parseArray(request.getParameter("dateTypeArr").toString(),String.class);
		List<String> categoryAttributeValueIdArr = JSON.parseArray(request.getParameter("categoryAttributeValueIdArr").toString(),String.class);
		
		
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user==null){
			return new ResultInfo<>();
			
		}
		
		try {
			CategoryAttributeValue cav = null;
			Map<String,CategoryAttributeValue> insertMap = new HashMap<String,CategoryAttributeValue>();
			Map<String,CategoryAttributeValue> updateMap = new HashMap<String,CategoryAttributeValue>();
			for(int i=0;i<dateTypeArr.size();i++){
				cav = new CategoryAttributeValue();
				cav.setDataType(dateTypeArr.get(i));
				
				if(categoryAttributeValueIdArr.get(i).isEmpty()){
					cav.setCategoryAttributeValueId(null);
				}else{
					cav.setCategoryAttributeValueId(Integer.valueOf(categoryAttributeValueIdArr.get(i)));
				}
				cav.setCategoryAttributeId(Integer.valueOf(request.getParameter("categoryAttributeId")));
				cav.setAttrValue(attrValueArr.get(i));
				cav.setSort(Integer.valueOf(sortArr.get(i)));
				
				cav.setCreator(user.getUserId());
				cav.setAddTime(DateUtil.sysTimeMillis());
				
				cav.setUpdater(user.getUserId());
				cav.setModTime(DateUtil.sysTimeMillis());
				
				if("insert".equals(dateTypeArr.get(i))){
					insertMap.put(i+"", cav);
				}else{
					updateMap.put(i+"", cav);
				}
			}
			if(!insertMap.isEmpty()){
				categoryAttributeValue.setInsertAttrMap(insertMap);
			}
			if(!updateMap.isEmpty()){
				categoryAttributeValue.setUpdateAttrMap(updateMap);
			}
		} catch (NumberFormatException e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		
		ResultInfo<?> updateCateAttributeValue = categoryAttributeService.updateCateAttributeValue(categoryAttributeValue);
		if(updateCateAttributeValue.getCode().equals(0) && user.getCompanyId().equals(1)){
			vedengSoapService.categoryAttrSync(categoryAttributeValue.getCategoryAttributeId());
		}
		return updateCateAttributeValue;
	}
	
	/**
	 * <b>Description:</b><br> 根据产品分类ID获取属性列表
	 * @param request
	 * @param categoryAttribute
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月22日 下午7:57:25
	 */
	@ResponseBody
	@RequestMapping(value="getAttributeByCategoryId")
	public ResultInfo<?> getAttributeByCategoryId(HttpServletRequest request,CategoryAttribute categoryAttribute){
		ResultInfo<CategoryAttribute> resultInfo = new ResultInfo<CategoryAttribute>();
		List<CategoryAttribute> list = categoryAttributeService.getAttributeByCategoryId(categoryAttribute);
		
		if(list != null){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(list);
		}
		return resultInfo;
	}
}
