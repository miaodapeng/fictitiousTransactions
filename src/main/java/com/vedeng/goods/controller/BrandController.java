package com.vedeng.goods.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.validation.Interface.Second;
import com.vedeng.common.validator.FormToken;
import com.vedeng.goods.model.Brand;
import com.vedeng.goods.service.BrandService;
import com.vedeng.soap.service.VedengSoapService;

/**
 * <b>Description:</b><br>
 * 品牌管理
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.goods.controller <br>
 *       <b>ClassName:</b> BrandController <br>
 *       <b>Date:</b> 2017年5月15日 上午10:06:03
 */
@Controller
@RequestMapping("/goods/brand")
public class BrandController extends BaseController {

	@Autowired
	@Qualifier("brandService")
	private BrandService brandService;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	/**
	 * <b>Description:</b><br>
	 * 查询品牌数据列表(分页)
	 * 
	 * @param request
	 * @param brand
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月16日 下午4:48:07
	 */
	@ResponseBody
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, Brand brand,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		Page page = getPageTag(request, pageNo, pageSize);
		brand.setSource(0);// 来源于ERP
		brand.setCompanyId(user.getCompanyId());
		if (request.getParameter("search") == null || "".equals(request.getParameter("search"))) {
			brand.setBrandNature(1);
		}
		Map<String, Object> map = brandService.getBrandListPage(brand, page);

		mv.addObject("brandList", (List<Brand>) map.get("list"));
		mv.addObject("brand", brand);

		mv.addObject("page", (Page) map.get("page"));

		mv.setViewName("goods/brand/index");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询品牌管理(全部)
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年5月15日 上午10:15:31
	 */
	@ResponseBody
	@RequestMapping(value = "getallbrand")
	public ResultInfo<?> getAllBrand(HttpServletRequest request, Brand brand) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ResultInfo<Brand> resultInfo = new ResultInfo<Brand>();
		brand.setCompanyId(user.getCompanyId());
		List<Brand> brandList = brandService.getAllBrand(brand);

		if (brandList != null) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(brandList);
		}
		return resultInfo;
	}

	/**
	 * <b>Description:</b><br>
	 * 查询品牌详情
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2018年3月13日 下午1:36:53
	 */
	@ResponseBody
	@RequestMapping(value = "/viewBrandDetail")
	public ModelAndView viewBrandDetail(HttpServletRequest request, Brand brand) {
		ModelAndView mav = new ModelAndView("goods/brand/view");
		Brand br = brandService.getBrandByKey(brand);
		if (br != null && ObjectUtils.notEmpty(br.getLogoUri())) {
			br.setLogoUriName(br.getLogoUri().substring(br.getLogoUri().lastIndexOf("/") + 1));
		}
		mav.addObject("brand", br);
		return mav;
	}

	/**
	 * <b>Description:</b><br>
	 * 添加品牌信息初始化
	 * 
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月16日 下午5:57:58
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/addBrand")
	public ModelAndView addBrand() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("goods/brand/add_brand");
		mv.addObject("domain", domain);
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 品牌信息添加保存
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月16日 下午6:03:24
	 */
	@FormToken(remove = true)
	@ResponseBody
	@RequestMapping(value = "/saveBrand")
	@SystemControllerLog(operationType = "add", desc = "保存品牌信息添加")
	public ModelAndView saveBrand(HttpServletRequest request, Brand brand) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			brand.setCreator(user.getUserId());
			brand.setAddTime(DateUtil.sysTimeMillis());
			brand.setUpdater(user.getUserId());
			brand.setModTime(DateUtil.sysTimeMillis());
			brand.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> result = brandService.saveBrand(brand);
		if (result.getCode().equals(0) && user.getCompanyId().equals(1)) {
			// 同步
			Brand b = (Brand) result.getData();
			vedengSoapService.brandSync(b.getBrandId(), false);
		}
		ModelAndView mav = new ModelAndView();
		if (result != null && result.getCode() == 0) {
			Brand b = (Brand) result.getData();
			mav.addObject("refresh", "true_false_true");
			mav.addObject("url", "./viewBrandDetail.do?brandId=" + b.getBrandId());
			return success(mav);
		}
		mav.addObject("message", result.getMessage());
		return fail(mav);
	}

	/**
	 * <b>Description:</b><br>
	 * 編輯品牌信息保存
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月16日 下午6:03:24
	 */
	@ResponseBody
	@RequestMapping(value = "/editBrand")
	@SystemControllerLog(operationType = "edit", desc = "保存編輯品牌信息")
	public ModelAndView editBrand(HttpServletRequest request, String beforeParams, Brand brand) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			brand.setUpdater(user.getUserId());
			brand.setModTime(DateUtil.sysTimeMillis());
			brand.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> result = brandService.editBrand(brand);
		if (result.getCode().equals(0) && user.getCompanyId().equals(1)) {
			// 同步
			vedengSoapService.brandSync(brand.getBrandId(), false);
		}
		ModelAndView mav = new ModelAndView();
		if (result != null && result.getCode() == 0) {
			Brand b = (Brand) result.getData();
			mav.addObject("refresh", "true_false_true");
			mav.addObject("url", "./viewBrandDetail.do?brandId=" + brand.getBrandId());
			return success(mav);
		}
		mav.addObject("message", result.getMessage());
		return fail(mav);
	}

	/**
	 * <b>Description:</b><br>
	 * 根據主鍵查詢品牌信息
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月16日 下午7:00:28
	 */
	@ResponseBody
	@RequestMapping(value = "/getBrandByKey")
	public ModelAndView getBrandByKey(HttpServletRequest request, Brand brand) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("goods/brand/edit_brand");
		try {
			brand = brandService.getBrandByKey(brand);
			mv.addObject("brand", brand);
			mv.addObject("domain", domain);
			// 日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(brand)));
		} catch (Exception e) {
			logger.error("getBrandByKey:", e);
		}
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 删除品牌信息
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月17日 上午10:58:49
	 */
	@ResponseBody
	@RequestMapping(value = "/delBrand")
	@SystemControllerLog(operationType = "delete", desc = "删除品牌信息")
	public ResultInfo<?> delBrand(HttpServletRequest request, Brand brand) {
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
			if (user != null) {
				brand.setUpdater(user.getUserId());
				brand.setModTime(DateUtil.sysTimeMillis());
			}
			if (brand == null) {
				return new ResultInfo<>(-1, "参数为空");
			}
			ResultInfo<?> delBrand = brandService.delBrand(brand);
			if (delBrand.getCode().equals(0) && user.getCompanyId().equals(1)) {
				vedengSoapService.brandSync(brand.getBrandId(), true);
			}
			return delBrand;
		} catch (Exception e) {
			logger.error("delBrand:", e);
			return new ResultInfo<>();
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * <b>Description:</b><br>
	 * 服务端验证页面初始化
	 * 
	 * @param request
	 * @param brand
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月15日 下午2:22:35
	 */
	@RequestMapping(value = "/testVailInit")
	public ModelAndView testVailInit(HttpServletRequest request, Brand brand) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("brand", brand);// 页面中变量名称需在此bean中
		mv.setViewName("goods/brand/service_vail");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 页面提交保存
	 * 
	 * @param model
	 * @param brand
	 * @param br
	 * @param attr
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月15日 下午4:53:06
	 */
	@RequestMapping(value = "/saveTestVail")
	// @Valid @ModelAttribute("brand")
	// @Validated({Second.class})指定验证方式 - 默认@Valid
	public String saveTestVail(Model model, @Validated({ Second.class }) Brand brand, BindingResult br,
			RedirectAttributes attr, HttpServletRequest request) {
		if (br.hasErrors()) {// 若验证未通过
			System.out.println(model.containsAttribute("category"));// model缓存中是否存在category信息
			// model.addAttribute("category",category);
			return "/goods/brand/service_vail";// 重定向添加页面
		} else {
			attr.addFlashAttribute("brand", brand);// 原理是放到session中，session在跳到后马上移除对象
			attr.addAttribute("abc", "" + Math.random());
			// model.addAttribute("category",category);
			return "redirect:toSave.do";// 重定向url
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 重定向保存
	 * 
	 * @param abc
	 * @param brand
	 * @param attr
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年6月15日 下午4:56:58
	 */
	@ResponseBody
	@RequestMapping(value = "/toSave")
	public ResultInfo<?> toSave(@RequestParam(value = "abc") String abc, Brand brand, RedirectAttributes attr,
			HttpServletRequest request) {
		System.out.println(abc);// 字符串参数接收
		// request.getSession().removeAttribute("category");
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			brand.setCreator(user.getUserId());
			brand.setAddTime(DateUtil.sysTimeMillis());
		}
		ResultInfo<?> resule = brandService.saveBrand(brand);
		return resule;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * <b>Description:耗材商城品牌列表</b>
	 * 
	 * @param request
	 * @param brand
	 * @param pageNo
	 * @param pageSize
	 * @return ModelAndView
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年11月14日 上午11:21:38
	 */
	@ResponseBody
	@RequestMapping(value = "brandList")
	public ModelAndView goManagerindex(HttpServletRequest request, Brand brand,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		Page page = getPageTag(request, pageNo, pageSize);
		brand.setSource(1);// 来源于耗材商城
		brand.setCompanyId(user.getCompanyId());
		if (request.getParameter("search") == null || "".equals(request.getParameter("search"))) {
			brand.setBrandNature(1);
		}
		Map<String, Object> map = brandService.getBrandListPage(brand, page);

		mv.addObject("brandList", (List<Brand>) map.get("list"));
		mv.addObject("brand", brand);

		mv.addObject("page", (Page) map.get("page"));

		mv.setViewName("goods/brand/index");
		return mv;
	}
}
