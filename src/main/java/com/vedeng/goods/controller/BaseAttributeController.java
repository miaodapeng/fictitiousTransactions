package com.vedeng.goods.controller;


import com.alibaba.fastjson.JSON;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.StringUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.BaseAttribute;
import com.vedeng.goods.model.vo.BaseAttributeValueVo;
import com.vedeng.goods.model.vo.BaseAttributeVo;
import com.vedeng.goods.model.vo.BaseCategoryVo;
import com.vedeng.goods.service.BaseAttributeService;
import com.vedeng.goods.service.BaseAttributeValueService;
import com.vedeng.goods.service.BaseCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author bill
 * @description 属性基础信息
 * @date $
 */
@Controller
@RequestMapping("/goods/baseattribute")
public class BaseAttributeController extends BaseController {

	@Autowired
	private BaseAttributeService baseAttributeService;

	@Autowired
	private BaseCategoryService baseCategoryService;

	@Autowired
	private BaseAttributeValueService baseAttributeValueService;
	/**
	 * @description 打开新增或编辑属性页
	 * @author bill
	 * @param
	 * @date 2019/5/8
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/openAttributePage")
	public ModelAndView addAttribute(HttpServletRequest request, Integer baseAttributeId) {
		ModelAndView addAttrMv = new ModelAndView();
		try{
			// 查询单位信息列表
			Map<String, Object> paramMap = new HashedMap();
			paramMap.put("isDeleted", CommonConstants.IS_DELETE_0);//未删除
			User user = (User)request.getSession().getAttribute(ErpConst.CURR_USER);
			if(user != null) {
				paramMap.put("companyId", user.getCompanyId());
			}else{
				request.setAttribute("error","登陆信息已失效，请重新登陆");
			}
			List<Map<String, Object>> unitMap = baseAttributeService.getUnitInfoMap(paramMap);
			addAttrMv.addObject("unitInfo", unitMap);
			// 如果是编辑属性
			if (null != baseAttributeId && baseAttributeId > 0) {
				ResultInfo result = this.checkDelete(baseAttributeId);
				if (CommonConstants.FAIL_CODE.equals(result.getCode())){
					return this.pageNotFound();
				}
				paramMap.put("baseAttributeId", baseAttributeId);
				// 查询属性详情
				BaseAttributeVo attribute = baseAttributeService.getBaseAttributeByParam(paramMap);
				// 查询属性下面的属性值列表
				BaseAttributeValueVo baseAttributeValueVo = new BaseAttributeValueVo();
				baseAttributeValueVo.setIsDeleted(CommonConstants.IS_DELETE_0);
				baseAttributeValueVo.setBaseAttributeId(attribute.getBaseAttributeId());
				attribute.setAttrValue(baseAttributeValueService.getBaseAttributeValueVoListByAttrId(baseAttributeValueVo));
				addAttrMv.addObject("attribute", attribute);
			}
			addAttrMv.setViewName("goods/baseattribute/add");
			return addAttrMv;
		}catch (Exception e){
			logger.error("打开新增或编辑属性页异常：",e);
			return this.page500();
		}
	}

	/**
	 * @description 保存属性信息
	 * @author bill
	 * @param
	 * @date 2019/5/8
	 */
	@FormToken(remove = true)
	@RequestMapping(value = "/saveAttribute")
	public ModelAndView saveAttribute(HttpServletRequest request, BaseAttributeVo baseAttributeVo) {
		try{
			//字段限制验证
			ResultInfo checkInfo = this.checkAttrVlue(baseAttributeVo);
			if(CommonConstants.SUCCESS_CODE.equals(checkInfo.getCode())){
				//获取当前登录用户信息
				User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
				if (user != null) {//验证登陆信息是否已失效
					if (baseAttributeVo.getBaseAttributeId() != null){
						ResultInfo result = this.checkDelete(baseAttributeVo.getBaseAttributeId());
						if (CommonConstants.FAIL_CODE.equals(result.getCode())){
							return this.pageNotFound();
						}
						baseAttributeVo.setUpdater(user.getUserId());
					}else{
						baseAttributeVo.setCreator(user.getUserId());
						baseAttributeVo.setUpdater(user.getUserId());
					}
					if (baseAttributeVo.getIsUnit() == null){
						baseAttributeVo.setIsUnit(0);//无单位
					}
					// 保存属性信息
					ResultInfo resultInfo = baseAttributeService.saveAttribute(baseAttributeVo);
					if (resultInfo != null){
						if (CommonConstants.SUCCESS_CODE.equals(resultInfo.getCode())){// 成功，返回详情页
							return new ModelAndView("redirect:./getAttributeInfo.do?baseAttributeId=" + resultInfo.getData());
						}else{//失败返回当前页面，并返回报错信息
							request.setAttribute("error",resultInfo.getMessage());
						}
					}else {
						request.setAttribute("error","属性保存失败");
					}
				}else{
					request.setAttribute("error","登陆信息已失效，请重新登陆");
				}
			}else{
				request.setAttribute("error",checkInfo.getMessage());
			}
			return new ModelAndView("forward:./openNewAttrPage.do");
		}catch (Exception e){
			logger.error("保存属性信息异常：",e);
			return this.page500();
		}
	}

	/**
	 * @description 打开属性详情页
	 * @author bill
	 * @param
	 * @date 2019/5/9
	 */
	@RequestMapping(value = "/getAttributeInfo")
	public ModelAndView getAttributeInfo(HttpServletRequest request, Integer baseAttributeId) {
		try{
			ResultInfo result = this.checkDelete(baseAttributeId);
			if (CommonConstants.FAIL_CODE.equals(result.getCode())){
				return this.pageNotFound();
			}
			ModelAndView mv = new ModelAndView();
			// 基本信息 -- 根据id查询属性属性值信息
			Map<String, Object> param = new HashMap<>();
			param.put("baseAttributeId", baseAttributeId);
			param.put("isDeleted", CommonConstants.IS_DELETE_0);
			// 查询属性详情
			BaseAttributeVo attribute = baseAttributeService.getBaseAttributeByParam(param);
			BaseAttributeValueVo baseAttributeValueVo = new BaseAttributeValueVo();
			baseAttributeValueVo.setIsDeleted(CommonConstants.IS_DELETE_0);
			baseAttributeValueVo.setBaseAttributeId(attribute.getBaseAttributeId());
			attribute.setAttrValue(baseAttributeValueService.getBaseAttributeValueVoListByAttrId(baseAttributeValueVo));
			mv.addObject("attribute", attribute);
			mv.setViewName("goods/baseattribute/view");
			return mv;
		}catch (Exception e){
			logger.error("打开属性详情页异常：",e);
			return this.page500();
		}
	}

	/**
	 * @description 打开属性列表页
	 * @author bill
	 * @param
	 * @date 2019/5/9
	 */
	@RequestMapping(value = "/index")
	public ModelAndView getAttributeListPage(HttpServletRequest request, BaseAttributeVo baseAttributeVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		try{
			ModelAndView mv = new ModelAndView();
			Page page = getPageTag(request, pageNo, pageSize);
			if (baseAttributeVo == null){
				baseAttributeVo = new BaseAttributeVo();
			}
			if (baseAttributeVo.getTimeSort() == null){
				baseAttributeVo.setTimeSort(1);//排序方式，已引用产品分类降序
			}
			baseAttributeVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
			// 获取属性列表
			List<BaseAttributeVo> list = baseAttributeService.getBaseAttributeInfoListPage(baseAttributeVo, page);
			// 获取属性值列表
			BaseAttributeValueVo baseAttributeValueVo = new BaseAttributeValueVo();
			baseAttributeValueVo.setIsDeleted(CommonConstants.IS_DELETE_0);//未删除状态
			List<BaseAttributeValueVo> baseAttributeValueVoList = baseAttributeValueService.getBaseAttributeValueVoList(baseAttributeValueVo,list);
			mv.addObject("baseAttributeVo",baseAttributeVo);
			mv.addObject("list", baseAttributeService.doAttrAndValue(list,baseAttributeValueVoList));
			mv.addObject("page", page);
			mv.setViewName("goods/baseattribute/index");
			return mv;
		}catch (Exception e){
			logger.error("打开属性列表页异常：",e);
			return this.page500();
		}
	}

	/**
	 * @description 删除属性信息
	 * @author bill
	 * @param
	 * @date 2019/5/15
	 */
	@RequestMapping(value = "/delAttribute")
	@ResponseBody
	public ResultInfo delAttribute(HttpServletRequest request, Integer baseAttributeId) {
		try{
			// 当前用户
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			if (user != null) {//登陆已失效
				BaseAttribute baseAttribute = baseAttributeService.selectByPrimaryKey(baseAttributeId);
				if (CommonConstants.IS_DELETE_1.equals(baseAttribute.getIsDeleted())){
					return new ResultInfo(CommonConstants.FAIL_CODE,"该属性已经被删除，无法操作");
				}
				ResultInfo res = new ResultInfo();
				//验证是否可以删除
				res = this.checkAttrIsUsed(request,baseAttributeId,CommonConstants.DO_DELETED_1);
				if (CommonConstants.SUCCESS_CODE.equals(res.getCode())){//验证通过，继续删除操作
					// 参数
					Map<String, Object> paramMap = new HashedMap();
					// 用户id
					paramMap.put("userId", user.getUserId());
					// 属性id
					paramMap.put("baseAttributeId", baseAttributeId);
					// 删除状态
					paramMap.put("isDelete", CommonConstants.IS_DELETE_1);
					// 删除操作
					res = baseAttributeService.delAttribute(paramMap);
					return res;
				}else {
					return res;
				}
			}else{
				return new ResultInfo(CommonConstants.FAIL_CODE,"登陆信息已失效，请重新登陆！");
			}
		}catch (Exception e){
			logger.error("删除属性信息异常：",e);
			return new ResultInfo(CommonConstants.FAIL_CODE,"删除属性异常！");
		}
	}

	/**
	 * @description 检查属性是否被引用
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	@RequestMapping(value = "/checkAttrIsUsed")
	@ResponseBody
	public ResultInfo checkAttrIsUsed(HttpServletRequest request, Integer baseAttributeId, Integer doType) {
		try{
			// 检查该属性是否已经被产品分类引用
			List<BaseCategoryVo>  categoryList = baseCategoryService.getBaseCategoryListPageByAttr(baseAttributeId, null);
			if (CollectionUtils.isNotEmpty(categoryList)){//存在被引用
				if (CommonConstants.DO_DELETED_1.equals(doType)){
					return new ResultInfo(CommonConstants.FAIL_CODE,"该属性已被引用，无法删除!");
				}else{
					return new ResultInfo(CommonConstants.FAIL_CODE,"该属性已被引用，无法修改!");
				}

			}else {
				return new ResultInfo(CommonConstants.SUCCESS_CODE,null);
			}
		}catch (Exception e){
			logger.error("检查属性是否被引用异常：",e);
			return new ResultInfo(CommonConstants.FAIL_CODE,"检查属性是否被引用异常!");
		}
	}

	/**
	 * @description 转发打开新增或编辑属性页
	 * @author bill
	 * @param
	 * @date 2019/5/8
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/openNewAttrPage")
	public ModelAndView openNewAttrPage(HttpServletRequest request, BaseAttributeVo baseAttributeVo) {
		try {
			if (baseAttributeVo.getBaseAttributeId()!= null && !"".equals(baseAttributeVo.getBaseAttributeId())) {
				ResultInfo result = this.checkDelete(baseAttributeVo.getBaseAttributeId());
				if (CommonConstants.FAIL_CODE.equals(result.getCode())) {
					return this.pageNotFound();
				}
			}
			ModelAndView mv = new ModelAndView();
			Map<String, Object> paramMap = new HashedMap();
			if (baseAttributeVo.getIsUnit() == null){
				baseAttributeVo.setIsUnit(0);//无单位
			}
			paramMap.put("isDeleted", CommonConstants.IS_DELETE_0);//未删除
			List<Map<String, Object>> unitMap = baseAttributeService.getUnitInfoMap(paramMap);
			mv.addObject("unitInfo", unitMap);
			mv.addObject("attribute", baseAttributeVo);
			mv.addObject("error", request.getAttribute("error"));
			mv.setViewName("goods/baseattribute/add");
			return mv;
		}catch (Exception e){
			logger.error("转发打开新增或编辑属性页异常：",e);
			return this.page500();
		}
	}

	/**
	 * @description 属性字段校验
	 * @author cooper
	 * @param
	 * @date 2019/5/22
	 */
	public ResultInfo checkAttrVlue(BaseAttributeVo baseAttributeVo){
		ResultInfo resultInfo = new ResultInfo();
		try{
			//判断值是否为空
			if (baseAttributeVo != null){
				if (StringUtils.isEmpty(baseAttributeVo.getBaseAttributeName())){
					resultInfo.setCode(CommonConstants.FAIL_CODE);
					resultInfo.setMessage("属性名称为空，无法提交");
					return resultInfo;
				}else{
					Integer length = baseAttributeVo.getBaseAttributeName().length();
					if (length > 16){
						resultInfo.setCode(CommonConstants.FAIL_CODE);
						resultInfo.setMessage("属性名称最多填写16个字，无法提交");
						return resultInfo;
					}
				}
				if (CollectionUtils.isEmpty(baseAttributeVo.getAttrValue())
						&& StringUtil.isEmpty(baseAttributeVo.getAttrValue().get(0).getAttrValue())){
					resultInfo.setCode(CommonConstants.FAIL_CODE);
					resultInfo.setMessage("属性值少于一条，无法提交");
					return resultInfo;
				}else{
					List<BaseAttributeValueVo> attrValueList = baseAttributeVo.getAttrValue();
					for (int i = 0 ; i < attrValueList.size() ; i ++){
						if (attrValueList.get(i).getSort() == null){
							resultInfo.setCode(CommonConstants.FAIL_CODE);
							resultInfo.setMessage("属性值排序存在空值，无法提交");
							return resultInfo;
						}
						if (StringUtils.isEmpty(attrValueList.get(i).getAttrValue())){
							resultInfo.setCode(CommonConstants.FAIL_CODE);
							resultInfo.setMessage("属性值存在空值，无法提交");
							return resultInfo;
						}
						if (baseAttributeVo.getIsUnit() != null && baseAttributeVo.getIsUnit().equals(1)){
							if (attrValueList.get(i).getUnitId() == null || attrValueList.get(i).getUnitId() == 0){
								resultInfo.setCode(CommonConstants.FAIL_CODE);
								resultInfo.setMessage("属性值单位存在空值，无法提交");
								return resultInfo;
							}
						}
						for (int j = i+1 ; j < attrValueList.size() ; j ++){
							if (attrValueList.get(i).getSort().equals(attrValueList.get(j).getSort())){
								resultInfo.setCode(CommonConstants.FAIL_CODE);
								resultInfo.setMessage("属性值的排序值存在重复，无法提交");
								return resultInfo;
							}
							if (baseAttributeVo.getIsUnit()==null){
								if (attrValueList.get(i).getAttrValue().equals(attrValueList.get(j).getAttrValue())){
									resultInfo.setCode(CommonConstants.FAIL_CODE);
									resultInfo.setMessage("属性值存在重复，无法提交");
									return resultInfo;
								}
							}else{
								if (attrValueList.get(i).getAttrValue().equals(attrValueList.get(j).getAttrValue())
										&& attrValueList.get(i).getUnitId().equals(attrValueList.get(j).getUnitId())){
									resultInfo.setCode(CommonConstants.FAIL_CODE);
									resultInfo.setMessage("属性值存在重复，无法提交");
									return resultInfo;
								}
							}
						}
					}
				}
			}else{
				resultInfo.setCode(CommonConstants.FAIL_CODE);
				resultInfo.setMessage("属性信息不完整，无法提交");
				return resultInfo;
			}
			return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);
		}catch (Exception e){
			logger.error("属性字段校验异常：",e);
			return new ResultInfo(CommonConstants.FAIL_CODE,"属性字段校验异常");
		}
	}
	/**
	 * 检查是否已经删除
	 * @param baseAttributeId
	 * @return
	 */
	public ResultInfo checkDelete(Integer baseAttributeId){
		BaseAttribute baseAttribute = baseAttributeService.selectByPrimaryKey(baseAttributeId);
		if (CommonConstants.IS_DELETE_1.equals(baseAttribute.getIsDeleted())){
			return new ResultInfo(CommonConstants.FAIL_CODE,CommonConstants.FAIL_MSG);
		}
		return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG);
	}

	/**
	 * 根据分类ID获取分类下绑定的属性列表
	 * @param baseCategoryId
	 * @return
	 */
	@RequestMapping("/getAttributeListByCategoryId")
	@ResponseBody
	public ResultInfo getAttributeListByCategoryId(Integer baseCategoryId){
		try{
			List<BaseCategoryVo> list = new ArrayList<>();
			BaseCategoryVo baseCategoryVo = new BaseCategoryVo();
			baseCategoryVo.setBaseCategoryId(baseCategoryId);
			list.add(baseCategoryVo);
			List<BaseAttributeVo> baseAttributeVoList = baseAttributeService.getAttributeListByCategoryId(list);
			return new ResultInfo(CommonConstants.SUCCESS_CODE,CommonConstants.SUCCESS_MSG,baseAttributeVoList);
		}catch (Exception e){
			logger.error("根据分类ID获取分类下绑定的属性列表异常：",e);
			return new ResultInfo(CommonConstants.FAIL_CODE,"根据分类ID获取分类下绑定的属性列表异常");
		}
	}
}
