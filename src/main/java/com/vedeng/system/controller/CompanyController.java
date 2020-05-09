package com.vedeng.system.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Company;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.system.service.CompanyService;


/**
 * <b>Description:</b><br> 公司管理
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> CompanyController
 * <br><b>Date:</b> 2017年4月25日 上午9:13:06
 */
@Controller()
@RequestMapping("/system/company")
public class CompanyController extends BaseController {
	@Autowired
	@Qualifier("companyService")
	private CompanyService companyService;


	/**
	 * 
	 * <b>Description:</b><br> 公司列表
	 * @param request 请求
	 * @param company 公司bean
	 * @param pageNo 当前页
	 * @param pageSize 每页条数
	 * @return
	 * @Note
	 * <b>Author:</b> li
	 * <br><b>Date:</b> 2017年4月25日 上午9:01:13
	 */
	@ResponseBody
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,Company company,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,  //required = false:可不传入pageNo这个参数，true必须传入，defaultValue默认值，若无默认值，使用Page类中的默认值
            @RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView(); 
		//查询集合        
        Page page = getPageTag(request,pageNo,pageSize);
        List<Company> companyList = companyService.querylistPage(company, page);
        
        //页面传值
        mv.addObject("company", company);
        
        mv.addObject("companyList",companyList);  
        mv.addObject("page", page);
		mv.setViewName("system/company/index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 公司信息维护 
	 * @param request 请求
	 * @param company 公司bean
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:15:11
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping("/addcompany")
	public ModelAndView modifyCompany(HttpServletRequest request,Company company){
		ModelAndView mv = new ModelAndView(); 
		
		mv.setViewName("system/company/add_company");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增公司信息
	 * @param request
	 * @param company
	 * @param bindingResult
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月12日 下午3:38:40
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value="/saveaddcompany")
	@SystemControllerLog(operationType = "add",desc = "保存新增公司信息")
	public ResultInfo saveAddCompany(HttpServletRequest request,@Validated Company company,BindingResult bindingResult){
		ResultInfo resultInfo = new ResultInfo<>();
		if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			
			resultInfo.setListData(allErrors);
			return resultInfo;
		}
		Company c = new Company();
		c.setCompanyName(company.getCompanyName());
		Company info = companyService.getCompany(c);
		if( null != info ){
			resultInfo.setMessage("公司名称不允许重复");
			return resultInfo;
		}
		
		Company d = new Company();
		d.setDomain(company.getDomain());
		Company dinfo = companyService.getCompany(d);
		if(null != dinfo){
			resultInfo.setMessage("访问地址不允许重复");
			return resultInfo;
		}
		
		Integer res = companyService.modifyCompany(company);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 
	 * @param request
	 * @param company
	 * @return
	 * @throws IOException 
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年9月12日 下午3:32:50
	 */
	@ResponseBody
	@RequestMapping("/editcompany")
	public ModelAndView editCompany(HttpServletRequest request,Company company) throws IOException{
		ModelAndView mv = new ModelAndView(); 
		//编辑公司
		if(null == company
				|| null == company.getCompanyId() 
				|| company.getCompanyId() <= 0){
			return pageNotFound();
		}
		Company companyInfo = companyService.getCompanyByCompangId(company.getCompanyId());
		mv.addObject("company", companyInfo);
		
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(companyInfo)));
		mv.setViewName("system/company/modify_company");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增/编辑公司信息 
	 * @param company 公司bean
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:15:52
	 */
	@ResponseBody
	@RequestMapping(value="/saveeditcompany")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑公司信息")
	public ResultInfo saveCompany(HttpServletRequest request,@Validated Company company,BindingResult bindingResult,
			String beforeParams){
		ResultInfo resultInfo = new ResultInfo<>();
		if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			
			resultInfo.setListData(allErrors);
			return resultInfo;
		}
		Company c = new Company();
		c.setCompanyName(company.getCompanyName());
		Company info = companyService.getCompany(c);
		if(null != company.getCompanyId() 
				&& company.getCompanyId() > 0
				&& null != info
				&& !company.getCompanyId().equals(info.getCompanyId())
				){
			resultInfo.setMessage("公司名称不允许重复");
			return resultInfo;
		}
		
		Company d = new Company();
		d.setDomain(company.getDomain());
		Company dinfo = companyService.getCompany(d);
		if(null != company.getCompanyId() 
				&& company.getCompanyId() > 0
				&& null != dinfo
				&& !company.getCompanyId().equals(dinfo.getCompanyId())
				){
			resultInfo.setMessage("访问地址不允许重复");
			return resultInfo;
		}
		
		Integer res = companyService.modifyCompany(company);
		if(res > 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}
		return resultInfo;
	}
}
