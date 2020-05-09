package com.vedeng.firstengage.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.task.ReadFirstExcel;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.exception.ShowErrorMsgException;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.ResultJSON;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.firstengage.dao.FirstEngageMapper;
import com.vedeng.firstengage.model.FirstEngage;
import com.vedeng.firstengage.model.RegistrationNumber;
import com.vedeng.firstengage.service.FirstEngageService;
import com.vedeng.goods.model.LogCheckGenerate;
import com.vedeng.goods.model.StandardCategory;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.SysOptionDefinitionService;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * 首营信息
 * <p>Title: FirstEngageController</p>
 * <p>Description: </p>
 * @author Bill
 * @date 2019年3月20日
 */
@Controller
@RequestMapping("/firstengage/baseinfo")
public class FirstEngageController extends BaseController{


	@Autowired
	private FirstEngageService firstEngageService;

	@Autowired
	private SysOptionDefinitionService sysOptionDefinitionService;

	@Autowired
	private FtpUtilService ftpUtilService;

	/**
	 * 国标分类，一级
	 */
	private static final Integer STANDARD_PARRNT_ID_0 = 0;


	private static final Logger logger = LoggerFactory.getLogger(FirstEngageController.class);

	/**
	 * 商品首营列表
	 * 	 * <p>Title: getAfterSalesPage</p>
	 * 	 * <p>Description: </p>
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author Bill
	 * @date 2019年3月20日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getFirstEngageInfo")
	@ResponseBody
	public Object getFirstEngageInfoListPage(HttpServletRequest request, FirstEngage firstEngage,
												   @RequestParam(required = false, defaultValue = "1") Integer pageNo,
												   @RequestParam(required = false, defaultValue = "1") Integer tag,
												   @RequestParam(required = false, defaultValue = "10") Integer pageSize){
		ModelAndView mv = new ModelAndView();
		try {
			// 当前登陆用户
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 分页信息
			Page page = getPageTag(request, pageNo, pageSize);

//			ReadFirstExcel aa = new ReadFirstExcel();
//			aa.readExcel();

			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			if(null != firstEngage.getStatus()){
				// 首营信息审核状态
				paramMap.put("status", firstEngage.getStatus());
			}

			// 排序默认更新使用更新时间
			if(null == firstEngage.getTimeSort()){
				firstEngage.setTimeSort(1);
			}

			// 关键词
			String keyWords = firstEngage.getKeyWords();
			paramMap.put("keyWords", keyWords);

			// 注册证过期状态 3 ~ 6月
			if(null != firstEngage.getIsOverDate() && firstEngage.getIsOverDate().equals(1)){
				// 6月 时间戳
				Long firstEngageDateStart = DateUtil.convertLong(DateUtil.getDayOfMonth(3), DateUtil.DATE_FORMAT);
				// 6个月前
				Long firstEngageDateEnd = DateUtil.convertLong(DateUtil.getDayOfMonth(6), DateUtil.DATE_FORMAT);
				paramMap.put("firstEngageDateStart", firstEngageDateStart);
				paramMap.put("firstEngageDateEnd", firstEngageDateEnd);
			}
			// 1 ~ 3月
			if(null != firstEngage.getIsOverDate() && firstEngage.getIsOverDate().equals(2)){
				// 1月 时间戳
				Long firstEngageDateStart = DateUtil.convertLong(DateUtil.getDayOfMonth(1), DateUtil.DATE_FORMAT);
				// 3个月前
				Long firstEngageDateEnd = DateUtil.convertLong(DateUtil.getDayOfMonth(3), DateUtil.DATE_FORMAT);
				paramMap.put("firstEngageDateStart", firstEngageDateStart);
				paramMap.put("firstEngageDateEnd", firstEngageDateEnd);
			}

			// 0 ~ 1月
			if(null != firstEngage.getIsOverDate() && firstEngage.getIsOverDate().equals(3)){
				// 0月 时间戳
				Long firstEngageDateStart = DateUtil.convertLong(DateUtil.getDayOfMonth(0), DateUtil.DATE_FORMAT);
				// 1个月前
				Long firstEngageDateEnd = DateUtil.convertLong(DateUtil.getDayOfMonth(1), DateUtil.DATE_FORMAT);
				paramMap.put("firstEngageDateStart", firstEngageDateStart);
				paramMap.put("firstEngageDateEnd", firstEngageDateEnd);
			}

			// 已经过期
			if(null != firstEngage.getIsOverDate() && firstEngage.getIsOverDate().equals(4)){
				Long firstEngageDateEnd = DateUtil.convertLong(DateUtil.getDayOfMonth(0), DateUtil.DATE_FORMAT);
				paramMap.put("firstEngageDateEnd", firstEngageDateEnd);
			}

			if(EmptyUtils.isNotBlank(firstEngage.getEffectStartDate())){
				// 注册证有效期起始时间
				paramMap.put("registrationStartTime", DateUtil.convertLong(firstEngage.getEffectStartDate(), DateUtil.DATE_FORMAT));
			}
			if(EmptyUtils.isNotBlank(firstEngage.getEffectEndDate())){
				// 注册证有效期结束时间
				paramMap.put("registrationEndTime", DateUtil.convertLong(firstEngage.getEffectEndDate(), DateUtil.DATE_FORMAT));
			}


			// 用户信息
			paramMap.put("userId", sessUser.getUserId());

			paramMap.put("firstEngage", firstEngage);

			// 获取商品首营列表
			Map<String, Object> mapResult = firstEngageService.getFirstEngageInfoListPage(paramMap, page, firstEngage);

			// 分页信息
			paramMap.put("page", page);

			mv.addObject("firstEngageList", (List<FirstEngage>) mapResult.get("firstEngageList"));
			Page page1 = (Page) mapResult.get("page");
			mv.addObject("total", mapResult.get("total"));
			mv.addObject("one", mapResult.get("one"));
			mv.addObject("two", mapResult.get("two"));
			mv.addObject("three", mapResult.get("three"));
			mv.addObject("overDateCount", mapResult.get("overDateCount"));
			mv.addObject("page", page1);
			mv.addObject("tag", tag);
			// 关键词选中状态
			mv.addObject("searchStatus", firstEngage.getSearchStatus());
		} catch (Exception e) {
			logger.error("商品首营列表:", e);
		}
		mv.setViewName("firstengage/first/index");
		return mv;
	}

	/**
	 * 添加首营信息
	 * <p>Title: addFirstEngage</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 * @author Bill
	 * @date 2019年3月25日
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/add")
	public ModelAndView addFirstEngage(HttpServletRequest request, Integer firstEngageId) {
		ModelAndView mv = new ModelAndView();
		try {
			User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);

			// 管理类别
			Map<String, Object> paramMap = getManagermentMap(mv);

			mv.addObject("user", user);
			// 如果firstEngageId不为空，说明是编辑页
			if(null != firstEngageId){
				paramMap.put("userId", user.getUserId());
				paramMap.put("firstEngageId", firstEngageId);
				FirstEngage firstEngage = firstEngageService.getFirstSearchDetail(paramMap, firstEngageId);
				mv.addObject("zczMapList", new JSONArray(firstEngage.getZczMapList()).toString());
				mv.addObject("yzMapList", new JSONArray(firstEngage.getYzMapList()).toString());
				mv.addObject("smsMapList", new JSONArray(firstEngage.getSmsMapList()).toString());
				mv.addObject("wsMapList", new JSONArray(firstEngage.getWsMapList()).toString());
				mv.addObject("scMapList", new JSONArray(firstEngage.getScMapList()).toString());
				mv.addObject("sbMapList", new JSONArray(firstEngage.getSbMapList()).toString());
				mv.addObject("djbMapList", new JSONArray(firstEngage.getDjbMapList()).toString());
				mv.addObject("cpMapList", new JSONArray(firstEngage.getCpMapList()).toString());
				mv.addObject("firstEngage", firstEngage);
			}

		} catch (Exception e) {
			logger.error("添加首营信息:", e);
		}
		mv.setViewName("firstengage/first/add");
		return mv;
	}

	/**
	 * 新增首营商品信息
	 * <p>Title: addFirstEngageInfo</p>
	 * <p>Description: </p>
	 * @param request
	 * @param firstEngage
	 * @return
	 * @author Bill
	 * @date 2019年3月21日
	 */
	@FormToken(remove = true)
	@RequestMapping("/addFirstEngageInfo")
	public ModelAndView addFirstEngageInfo(HttpServletRequest request, FirstEngage firstEngage){
		Integer resId = null;

		try {
			// 当前登陆用户
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			try {
				// 校验首营参数
				firstEngageService.initFirstEngageInfo(firstEngage);
			} catch (ShowErrorMsgException e) {
				firstEngage.setErrors(Lists.newArrayList(e.getErrorMsg()));
				request.setAttribute("firstEngage", firstEngage);
				return new ModelAndView("forward:./newpagefirst.do");
			}

			// 添加首营商品信息，返回新增的首营id
			resId = firstEngageService.addFirstEngageInfo(firstEngage, sessUser);
		} catch (Exception e) {
			logger.error("新增首营商品信息:", e);
		}

		return new ModelAndView("redirect:/firstengage/baseinfo/getFirstSearchDetail.do?firstEngageId=" + resId);
	}

	/**
	 * @description 转发新增页
	 * @author bill
	 * @param
	 * @date 2019/5/29
	 */
	@FormToken(save = true)
	@RequestMapping("/newpagefirst")
	public ModelAndView getForwardPage(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mav = new ModelAndView();
		try {
			// 首营信息参数
			FirstEngage firstEngage = (FirstEngage) request.getAttribute("firstEngage");
			mav.addObject("user", user);
			// 管理类别
			getManagermentMap(mav);

			// 注册证
			RegistrationNumber registration = firstEngage.getRegistration();
			// 注册证附件/备案凭证附件
			getAttachments(registration.getZczAttachments(), mav, "zczMapList");
			// 营业执照
			getAttachments(registration.getYzAttachments(), mav, "yzMapList");
			// 说明书
			getAttachments(registration.getSmsAttachments(), mav, "smsMapList");
			// 生产企业卫生许可证
			getAttachments(registration.getWsAttachments(), mav, "wsMapList");
			// 生产企业许可证
			getAttachments(registration.getScAttachments(), mav, "scMapList");
			// 商标注册证
			getAttachments(registration.getSbAttachments(), mav, "sbMapList");
			// 注册登记表附件
			getAttachments(registration.getDjbAttachments(), mav, "djbMapList");
			// 产品图片（单包装/大包装）
			getAttachments(registration.getCpAttachments(), mav, "cpMapList");
			mav.addObject("firstEngage", firstEngage);
		} catch (Exception e) {
			logger.error("转发跳转页：", e);
		}
		mav.setViewName("firstengage/first/add");
		return mav;
	}

	/**
	 * 首营信息详情页
	 * <p>Title: getFirstSearchDetail</p>
	 * <p>Description: </p>
	 * @param request
	 * @param firstEngageId
	 * @return
	 * @author Bill
	 * @date 2019年4月1日
	 */
	@RequestMapping("/getFirstSearchDetail")
	public ModelAndView getFirstSearchDetail(HttpServletRequest request, Integer firstEngageId){
		// 获取商品首营列表
		ModelAndView mv = new ModelAndView();

		try {
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 校验空
			if(null == firstEngageId){
				return null;
			}

			List<LogCheckGenerate> logCheckGenerateList = firstEngageService.listSkuCheckLog(firstEngageId);
			mv.addObject("logCheckGenerateList", logCheckGenerateList);

			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userId", sessUser.getUserId());
			paramMap.put("firstEngageId", firstEngageId);

			FirstEngage firstEngage = firstEngageService.getFirstSearchDetail(paramMap, firstEngageId);
			mv.addObject("firstEngage", firstEngage);
			mv.addObject("api_http", api_http);
		} catch (Exception e) {
			logger.error("首营信息详情页", e);
		}
		mv.setViewName("firstengage/first/view");
		return mv;
	}




	/**
	 * 根据注册证id查询信息
	 * <p>Title: getFirstSearchInfoById</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 * @author Bill
	 * @date 2019年3月27日
	 */
	@RequestMapping("/getFirstSearchInfoById")
	@ResponseBody
	public ResultInfo<RegistrationNumber> getFirstSearchInfoById(HttpServletRequest request, Integer registrationNumberId, String registrationNumber){
		RegistrationNumber registration = null;
		try {
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userId", sessUser.getUserId());
			paramMap.put("registrationNumberId", registrationNumberId);
			// 注册证号
			paramMap.put("registrationNumber", registrationNumber);
			// 获取商品首营列表
			registration = firstEngageService.getRegistrationNumberInfoById(paramMap);
		} catch (Exception e) {
			logger.error("根据注册证id查询信息", e);
		}
		return new ResultInfo<RegistrationNumber>(0, "查询成功", registration);
	}

	@RequestMapping("/getnewstandcategory")
	@ResponseBody
	public ResultInfo getNewStandCategory(HttpServletRequest request, String categoryName){
		try {
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userId", sessUser.getUserId());
			// 注册证号
			paramMap.put("categoryName", categoryName);
			// 启用状态
			paramMap.put("status", CommonConstants.STATUS_1);
			// 获取商品首营列表
			List<Map<String, Object>> newStandCategoryList = firstEngageService.getNewStandardCategoryByName(paramMap);
			if(CollectionUtils.isNotEmpty(newStandCategoryList)){
				//			JSONArray jsonArray = new JSONArray(newStandCategoryList);
				return new ResultInfo(0, "成功", newStandCategoryList);
			}
		} catch (Exception e) {
			logger.error("获取新国标分类：", e);
			return new ResultInfo(-1, "失败"+e.getMessage());
		}
		return new ResultInfo(0, "成功");
	}


	/**
	 * 删除首映商品信息
	 * <p>Title: deleteFirstEngage</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 * @author Bill
	 * @date 2019年3月21日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/deleteFirstEngage")
	@ResponseBody
	public ResultInfo deleteFirstEngage(HttpServletRequest request, Integer[] ids, String comment){

		try {
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("firstEngageArray", ids);
			paramMap.put("userId", sessUser.getUserId());
			// 删除，待审核的不给删除
			paramMap.put("status", CommonConstants.FIRST_ENGAGE_STATUS_1);
			// 删除状态
			paramMap.put("isDelete", CommonConstants.IS_DELETE_0);
			paramMap.put("comment", comment);
			// 获取商品首营列表
			Integer delResult = firstEngageService.deleteFirstEngage(paramMap);
			if(null != delResult && delResult > 0){
				return new ResultInfo<>(0, "操作成功");
			}
			// 删除失败
			else{
				return new ResultInfo<>(-1, "删除失败");
			}
		} catch (Exception e) {
			logger.error("删除首映商品信息", e);
		}
		return new ResultInfo<>();
	}

	/**
	 * 根据输入查询注册证
	 * <p>Title: getRegistrationInfoByStr</p>
	 * <p>Description: </p>
	 * @param request
	 * @param registrationStr
	 * @return
	 * @author Bill
	 * @date 2019年3月21日
	 */
	@RequestMapping("/getRegistrationInfo")
	@ResponseBody
	public ResultInfo<RegistrationNumber> getRegistrationInfoByStr(HttpServletRequest request, String registrationStr){
		List<RegistrationNumber> aaa = null;
		try {
			// 参数集
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("registrationStr", registrationStr.replaceAll(" ", ""));
			aaa = firstEngageService.getRegistrationInfoByStr(paramMap);
		} catch (Exception e) {
			logger.error("根据输入查询注册证:",e);
		}
		return new ResultInfo<RegistrationNumber>(0, "查询成功", aaa);
	}

	/**
	 * 上传
	 * <p>Title: fileUploadImg</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 * @author Bill
	 * @date 2019年4月2日
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUploadImg")
	public FileInfo fileUploadImg(HttpServletRequest request, HttpServletResponse response, @RequestBody MultipartFile file) {
		FileInfo fi = null;

		try {
			//临时文件存放地址
			String path = "";
			String str = request.getParameter("type");
			// 判断是否为空
			if(EmptyUtils.isBlank(str)) {
				path = "/upload/default";
			}else{
				path = "/upload/" + str;
			}

			// 判断路径
			if (EmptyUtils.isEmpty(path)){
				return new FileInfo(-1,"文件类型非法!");
			}

			// 上传附件到远程服务器上
			fi = ftpUtilService.uploadFile(file, path, request, "");
			fi.setHttpUrl(api_http + fi.getHttpUrl());
		} catch (Exception e) {
			logger.error("上传首营信息注册证图片：", e);
		}
		return fi;
	}


	/**
	 * 移除文件（单文件移除）
	 * <p>Title: delFile</p>
	 * <p>Description: </p>
	 * @param request
	 * @param response
	 * @param path
	 * @param filename
	 * @return
	 * @author Bill
	 * @date 2019年4月2日
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/delFile")
	public ResultInfo delFile(HttpServletRequest request, HttpServletResponse response, String path,String filename) {
		try {
			File file=new File(path+"/"+filename);
			if(file.exists() && file.isFile()){
				file.delete();
				return new ResultInfo<>(0, "删除成功");
			}
		} catch (Exception e) {
			logger.error("移除图片：",e);
		}
		return new ResultInfo<>(-1, "删除失败");
	}

	/**
	 * @description 获取所有新国标分类
	 * @author bill
	 * @param
	 * @date 2019/5/21
	 */
	@ResponseBody
	@RequestMapping(value = "/allstandard")
	public ResultJSON  getallStandard(HttpServletRequest request, HttpServletResponse response){
		try {
			// 获取所有新国标分类
			List<StandardCategory> newStand = firstEngageService.getallStandard();
			return ResultJSON.success().data(newStand);
		} catch (Exception e) {
			logger.error("获取所有新国标分类：",e);
			return ResultJSON.failed(e);
		}
	}


	@ResponseBody
	@RequestMapping(value = "/allcompany")
	public List<Map<String,Object>> getallcompany(HttpServletRequest request, HttpServletResponse response){
		try {
			// 获取所有新国标分类
			List<Map<String,Object>> companies = firstEngageService.getallcompany("");
			if(CollectionUtils.isNotEmpty(companies)){
				return companies;
			}
		} catch (Exception e) {
			logger.error("获取所有新国标分类：",e);
		}

		return Collections.emptyList();
	}

	/**
	 * @description 过期处理状态
	 * @author bill
	 * @param
	 * @date 2019/5/28
	 */
	@ResponseBody
	@RequestMapping(value = "/dealstatus")
	public ResultInfo dealstatus(HttpServletRequest request, Integer registrationNumberId){
		try {

			return firstEngageService.dealstatus(registrationNumberId);

		} catch (Exception e) {
			logger.error("过期处理状态：",e);
		}

		return new ResultInfo();
	}

	/**
	 * @description 首营信息审核
	 * @author bill
	 * @param
	 * @date 2019/6/10
	 */
	@ResponseBody
	@RequestMapping(value = "/check")
	public ResultJSON checkFirstengage(HttpServletRequest request, FirstEngage firstEngage){
		try {
			User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			firstEngageService.checkFirstengage(firstEngage, sessUser);
		} catch (Exception e) {
			logger.error("首营信息审核：",e);
			return ResultJSON.failed().message(e.getMessage());
		}
		return ResultJSON.success().message("操作成功");
	}



	/**
	 * @description 处理管理类别
	 * @author bill
	 * @param
	 * @date 2019/5/29
	 */
	private Map<String, Object> getManagermentMap(ModelAndView mv) {
		List<Integer> scopeList = new ArrayList<>();
		// 管理类别作用域
		scopeList.add(CommonConstants.SCOPE_1090);
		// 产品类型 作用域
		scopeList.add(CommonConstants.SCOPE_1035);
		// 存储条件 作用域
		scopeList.add(CommonConstants.SCOPE_1092);
		// 医疗类别 作用域 1020
		scopeList.add(CommonConstants.SCOPE_1020);

		// 管理类别
		Map<String,List<SysOptionDefinition>> sysOptionMap = sysOptionDefinitionService.getSysOptionDefinitionByParam(scopeList);

		// 参数
		Map<String, Object> paramMap = new HashMap<>();
		// 启用状态
		paramMap.put("status", CommonConstants.STATUS_1);
		// 一级类别
		paramMap.put("parentId", STANDARD_PARRNT_ID_0);

		// 新国标分类信息
		List<Map<String, Object>> newStandCategoryList = firstEngageService.getNewStandardCategory(paramMap);

		// 旧国标分类解析成json格式
		List<SysOptionDefinition> oldStand = sysOptionMap.get("1020");
		if(EmptyUtils.isEmpty(oldStand)){
			mv.addObject("oldStandCategoryList", null);
		}
		List<Map<String, Object>> oldStandCategoryList = new ArrayList<>();
		int oldSize = oldStand.size();
		// 遍历旧国标分类
		for(int i=0; i < oldSize; i++){
			SysOptionDefinition sysOptionDefinition = oldStand.get(i);
			Map<String, Object> dataMap = new HashMap<>();
			// 旧国标名称
			dataMap.put("label", sysOptionDefinition.getTitle());
			// id
			dataMap.put("value", sysOptionDefinition.getSysOptionDefinitionId());
			oldStandCategoryList.add(dataMap);
		}
		JSONArray oldJsonArray = new JSONArray(oldStandCategoryList);
		mv.addObject("oldStandCategoryList", oldJsonArray.toString());
		mv.addObject("sysOptionMap", sysOptionMap);
		JSONArray jsonArray = new JSONArray(newStandCategoryList);
		mv.addObject("newStandCategoryList", jsonArray.toString());
		return paramMap;
	}


	/**
	 * @description 附件信息
	 * @author bill
	 * @param
	 * @date 2019/5/29
	 */
	private void getAttachments(List<Attachment> attachments, ModelAndView mv, String name){
		// 空判断
		if(CollectionUtils.isNotEmpty(attachments)){
			// 定义返回值
			List<Map<String, Object>> resMapList = new ArrayList<>();
			int size = attachments.size();
			for (int i = 0; i < size; i++) {
				Attachment attachment = attachments.get(i);
				// 编辑注册证附件信息
				Map<String, Object> attachmentMap = new HashMap<>();
				attachmentMap.put("message", "操作成功");
				attachmentMap.put("httpUrl", api_http+domain);
				// uri
				String uri = attachment.getUri();
				if(EmptyUtils.isEmpty(uri)){
					continue;
				}
				String[] uriArray = uri.split("/");
				String fileName = uriArray[uriArray.length-1];
				String fileNameTemp = "/" + fileName;
				// 文件后缀
				String[] prefixArray = fileNameTemp.split("\\.");
				String prefix = prefixArray[prefixArray.length-1];
				// 去除路径名
				String filePath = uri.replaceAll(fileNameTemp, "");
				attachmentMap.put("fileName", fileName);
				attachmentMap.put("filePath", filePath);
				attachmentMap.put("prefix", prefix);
				resMapList.add(attachmentMap);
			}
			// 放入modelandview
			if(CollectionUtils.isNotEmpty(resMapList)){
				mv.addObject(name, new JSONArray(resMapList).toString());
			}
		}
	}





}
