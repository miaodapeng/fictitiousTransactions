package com.vedeng.firstengage.controller;

import com.alibaba.fastjson.JSON;
import com.common.constants.Contant;
import com.google.common.collect.Lists;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.validation.Interface.Second;
import com.vedeng.common.validator.FormToken;
import com.vedeng.firstengage.model.FirstengageBrand;
import com.vedeng.firstengage.service.FirstengageBrandService;
import com.vedeng.goods.model.Brand;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.model.Attachment;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首营品牌管理 <b>Description:</b><br>
 * 
 * @author chuck
 * @Note <b>ProjectName:</b> erp.vedeng.com <br>
 * 		<b>PackageName:</b> com.vedeng.firstengage.controller <br>
 * 		<b>ClassName:</b> FirstengageBrandController <br>
 * 		<b>Date:</b> 2019年3月25日 下午3:58:57
 */
@Controller
@RequestMapping("/firstengage/brand")
public class FirstengageBrandController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(FirstengageBrandController.class);

	@Value("${file_url}")
	protected String domain;

	@Autowired
	@Qualifier("firstengageBrandService")
	private FirstengageBrandService brandService;

	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	/**
	 * 查询首营品牌数据列表(分页) <b>Description:</b>
	 * 
	 * @param request
	 * @param brand
	 * @param pageNo
	 * @param pageSize
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午3:59:16
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(HttpServletRequest request, FirstengageBrand brand,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {

		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		try {
			Page page = getPageTag(request, pageNo, pageSize);
			brand.setSource(0);// 来源于ERP
			brand.setCompanyId(user.getCompanyId());
			Map<String, Object> map = brandService.getBrandListPage(brand, page);

			mv.addObject("brandList", (List<Brand>) map.get("list"));
			mv.addObject("brand", brand);

			mv.addObject("page", (Page) map.get("page"));

			mv.setViewName("firstengage/brand/index");
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return mv;
	}

	/**
	 * 查询首营品牌管理(全部) <b>Description:</b>
	 * @param request
	 * @param brand
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午3:59:41
	 */
	@ResponseBody
	@RequestMapping(value = "getallbrand")
	public ResultInfo<?> getAllBrand(HttpServletRequest request, FirstengageBrand brand) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ResultInfo<FirstengageBrand> resultInfo = new ResultInfo<FirstengageBrand>();
		brand.setCompanyId(user.getCompanyId());
		List<FirstengageBrand> brandList = brandService.getAllBrand(brand);

		if (brandList != null) {
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(brandList);
		}
		return resultInfo;
	}

	/**
	 * 查询首营品牌详情 <b>Description:</b>
	 * @param request
	 * @param brand
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午4:00:00
	 */
	@RequestMapping(value = "/viewBrandDetail")
	public ModelAndView viewBrandDetail(HttpServletRequest request, FirstengageBrand brand) {
		ModelAndView mav = new ModelAndView();

		try {
			FirstengageBrand br = brandService.getBrandByParam(brand.getBrandId());
			// 如果品牌为空，返回列表页带出报错信息
			if(null == br){
				return pageNotFound();
			}

			// 查询供应商
			if (EmptyUtils.isNotBlank(br.getManufacturer())) {
				String[] str = br.getManufacturer().split("@");
				List<Map<String, Object>> list = brandService.getTraderSupplierAll(str);
				mav.addObject("manufacturer", list);
			}
			mav.addObject("brand", br);
		} catch (Exception e) {
			logger.error("品牌详情页", e);
		}

		mav.setViewName("firstengage/brand/view");
		return mav;
	}

	/**
	 * 添加首营品牌信息初始化 <b>Description:</b>
	 * 
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午4:00:15
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/addBrand")
	public ModelAndView addBrand(HttpServletRequest request, Integer brandId) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("firstengage/brand/add_brand");
		List<Map<String, Object>> list = brandService.getTraderSupplierAll(null);

		// 如果品牌不为空
		if (null != brandId) {
			// 查询品牌详情
			FirstengageBrand brand = brandService.getBrandByParam(brandId);
            // 如果品牌为空，返回列表页带出报错信息
            if(null == brand){
                return pageNotFound();
            }

			mv.addObject("brand", brand);
			// 包装logo图片信息
			packPicure(mv, brand);
			// 授权证明
            proofMethod(mv, brand);
        }

		JSONArray jsonArray = new JSONArray(list);
		mv.addObject("traderSupplierList", jsonArray.toString());
		mv.addObject("domain", domain);
		return mv;
	}


	/**
	 * 首营品牌信息添加保存 <b>Description:</b>
	 * 
	 * @param request
	 * @param brand
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午4:00:32
	 */
	@FormToken(remove = true)
	@RequestMapping(value = "/saveBrand")
	public ModelAndView saveBrand(HttpServletRequest request, FirstengageBrand brand) {

		ModelAndView mav = new ModelAndView();
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			brand.setCreator(user.getUserId());
			brand.setAddTime(DateUtil.sysTimeMillis());
			brand.setUpdater(user.getUserId());
			brand.setModTime(DateUtil.sysTimeMillis());
			brand.setCompanyId(user.getCompanyId());
		}

        // 保存LOGO URI
        brand.setLogoDomain(domain);
        brand.setLogoUri(request.getParameter("logoUri.uri"));

		try {
			// 首先校验参数信息
			brandService.initBrand(brand);
		} catch (ShowErrorMsgException e) {
			brand.setErrors(Lists.newArrayList(e.getErrorMsg()));
			request.setAttribute("brand", brand);
			return new ModelAndView("forward:./newpage.do");
		}

		// 如果id为空，是新增
		if (null == brand.getBrandId()) {
			ResultInfo<?> result = brandService.saveBrand(brand);
			if (result.getCode().equals(0) && user.getCompanyId().equals(1)) {
				// 同步
				FirstengageBrand b = (FirstengageBrand) result.getData();
				vedengSoapService.brandSync(b.getBrandId(), false);
			}
			if (result != null && result.getCode() == 0) {
				FirstengageBrand b = (FirstengageBrand) result.getData();
				return new ModelAndView("redirect:/firstengage/brand/viewBrandDetail.do?brandId=" + b.getBrandId());
			}
			mav.addObject("message", result.getMessage());
		}

		// 否则是编辑
		else {
			ResultInfo<?> result = brandService.editBrand(brand);
			if (result.getCode().equals(0) && user.getCompanyId().equals(1)) {
				// 同步
				vedengSoapService.brandSync(brand.getBrandId(), false);
			}
			if (result != null && result.getCode() == 0) {
				Brand b = (Brand) result.getData();
                return new ModelAndView("redirect:/firstengage/brand/viewBrandDetail.do?brandId=" + brand.getBrandId());
			}
			mav.addObject("message", result.getMessage());
		}
		return fail(mav);
	}


	/**
	 * @description 品牌转发跳转页
	 * @author bill
	 * @param
	 * @date 2019/5/29
	 */
	@FormToken(save = true)
	@RequestMapping("/newpage")
	public ModelAndView getForwardPage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();

		FirstengageBrand brand = (FirstengageBrand) request.getAttribute("brand");

		// 生产企业
		List<Map<String, Object>> list = brandService.getTraderSupplierAll(null);

		// 包装logo图片信息
		packPicure(mav, brand);
		// 授权证明
		proofMethod(mav, brand);
		JSONArray jsonArray = new JSONArray(list);
		mav.addObject("traderSupplierList", jsonArray.toString());
		mav.setViewName("firstengage/brand/add_brand");
		mav.addObject("brand", brand);
		return mav;
	}


	/**
	 * 編輯首营品牌信息保存 <b>Description:</b>
	 * 
	 * @param request
	 * @param beforeParams
	 * @param brand
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午4:00:59
	 */
	@ResponseBody
	@RequestMapping(value = "/editBrand")
	@SystemControllerLog(operationType = "edit", desc = "保存編輯品牌信息")
	public ModelAndView editBrand(HttpServletRequest request, String beforeParams, FirstengageBrand brand) {
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
	 * 根據主鍵查詢首营品牌信息 <b>Description:</b>
	 * 
	 * @param request
	 * @param brand
	 * @return ModelAndView
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午4:01:16
	 */
	@ResponseBody
	@RequestMapping(value = "/getBrandByKey")
	public ModelAndView getBrandByKey(HttpServletRequest request, FirstengageBrand brand) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("goods/brand/edit_brand");
		try {
			brand = brandService.getBrandByKey(brand);
			mv.addObject("brand", brand);
			mv.addObject("domain", domain);
			// 日志
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(brand)));
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return mv;
	}

	/**
	 * 删除首营品牌信息 <b>Description:</b>
	 * 
	 * @param request
	 * @param brand
	 * @return ResultInfo<?>
	 * @Note <b>Author：</b> chuck <b>Date:</b> 2019年3月25日 下午4:01:34
	 */
	@ResponseBody
	@RequestMapping(value = "/delBrand")
	@SystemControllerLog(operationType = "delete", desc = "删除品牌信息")
	public ResultInfo<?> delBrand(HttpServletRequest request, FirstengageBrand brand) {
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
			logger.error(Contant.ERROR_MSG, e);
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
	public ResultInfo<?> toSave(@RequestParam(value = "abc") String abc, FirstengageBrand brand,
			RedirectAttributes attr, HttpServletRequest request) {
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
	public ModelAndView goManagerindex(HttpServletRequest request, FirstengageBrand brand,
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

	/**
	 * @description 导出品牌数据
	 * @author bill
	 * @param
	 * @date 2019/5/6
	 */
	@RequestMapping(value = "/exportbrand", method = RequestMethod.GET)
	public void exportBrand(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		// 参数
		Map<String, Object> paramMap = new HashMap<>();
		// brandIds
		paramMap.put("brandIds", null);
		// 删除状态
		paramMap.put("isDelete", CommonConstants.IS_DELETE_0);
		// 查询导出的品牌数据
		List<FirstengageBrand> brandList = brandService.getBrandListByParam(request, paramMap);

		// IreportExport.exportWrite()
		IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/brand.jrxml", brandList, "品牌信息.xls");

	}

	/**
	 * @description 根据品牌名称获取品牌
	 * @author bill
	 * @param
	 * @date 2019/5/14
	 */
	@ResponseBody
	@RequestMapping(value = "/brandName")
	public ResultInfo getBrandInfoByParam(HttpServletRequest request, String brandName, Integer pageSize) {
		if(pageSize==null){
			pageSize=5;
		}
		// 参数
		Map<String, Object> paramMap = new HashedMap();
		paramMap.put("isDelete", CommonConstants.IS_DELETE_0);
		paramMap.put("brandName", brandName);
		User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		if(sessUser != null) {
			paramMap.put("companyId", sessUser.getCompanyId());
		}else{
			request.setAttribute("error","登陆信息已失效，请重新登陆");
		}
		paramMap.put("pageSize", pageSize);
		// 根据品牌名称获取品牌t
		List<Map<String, String>> reMap = brandService.getBrandInfoByParam(paramMap);

		return new ResultInfo(CommonConstants.SUCCESS_CODE, "成功", reMap);
	}


    // 授权证明
    private void proofMethod(ModelAndView mv, FirstengageBrand brand) {
        if (CollectionUtils.isNotEmpty(brand.getProof())) {
            // 授权证明
            List<Map<String, Object>> proofMapList = new ArrayList<>();
            int size = brand.getProof().size();
            for (int i = 0; i < size; i++) {
                Attachment attachment = brand.getProof().get(i);

                // 返回logmap
                Map<String, Object> proofMap = new HashMap<>();
                String[] uriArray = attachment.getUri().split("/");
                String fileName = uriArray[uriArray.length - 1];
                String fileNameTemp = "/" + fileName;
                // 文件后缀
                String[] prefixArray = fileNameTemp.split("\\.");
                String prefix = prefixArray[prefixArray.length - 1];
                // 去除路径名
                String filePath = attachment.getUri().replaceAll(fileNameTemp, "");
                proofMap.put("fileName", fileName);
                proofMap.put("filePath", filePath);
                proofMap.put("prefix", prefix);
                proofMap.put("message", "操作成功");
                proofMap.put("httpUrl", api_http + domain);
                proofMapList.add(proofMap);
            }
            JSONArray jsonArray1 = new JSONArray(proofMapList);
            mv.addObject("proofMap", jsonArray1.toString());
        }
    }

    /**
     * @description 包装logo图片信息
     * @author bill
     * @param
     * @date 2019/5/21
     */
    private void packPicure(ModelAndView mv, FirstengageBrand brand) {
        // logo是否为空
        if (EmptyUtils.isNotBlank(brand.getLogoUri())) {
            List<Map<String, Object>> logMapList = new ArrayList<>();
            // 返回logmap
            Map<String, Object> logMap = new HashMap<>();
            String[] uriArray = brand.getLogoUri().split("/");
            String fileName = uriArray[uriArray.length - 1];
            String fileNameTemp = "/" + fileName;
            // 文件后缀
            String[] prefixArray = fileNameTemp.split("\\.");
            String prefix = prefixArray[prefixArray.length - 1];
            // 去除路径名
            String filePath = brand.getLogoUri().replaceAll(fileNameTemp, "");
            logMap.put("fileName", fileName);
            logMap.put("filePath", filePath);
            logMap.put("prefix", prefix);
            logMap.put("message", "操作成功");
            logMap.put("httpUrl", api_http + domain);
            logMapList.add(logMap);
            JSONArray jsonArray1 = new JSONArray(logMapList);
            mv.addObject("logMap", jsonArray1.toString());
        }
    }


}
