package com.vedeng.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.fop.render.rtf.rtflib.rtfdoc.RtfExtraRowSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.vo.RegionVo;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.RegionService;

/**
 * <b>Description:</b><br> 地区管理
 * @author Jerry
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.system.controller
 * <br><b>ClassName:</b> RegionController
 * <br><b>Date:</b> 2017年4月25日 上午11:20:42
 */
@Controller
@RequestMapping("/system/region")
public class RegionController extends BaseController {
	@Autowired
	@Qualifier("regionService")
	private RegionService regionService;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	/**
	 * <b>Description:</b><br> 
	 * @param request
	 * @param region
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 上午10:25:49
	 */
	@ResponseBody
    @RequestMapping(value = "/getRegionListPage")
	public ModelAndView getRegionListPage(HttpServletRequest request, Region region,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, @RequestParam(required = false) Integer pageSize){
		ModelAndView mav = new ModelAndView("system/region/list_region");
		mav.addObject("region", region);
		Page page = getPageTag(request,pageNo,pageSize);
		List<Region> list = regionService.getRegionListPage(region, page);
		mav.addObject("list",list);  
		mav.addObject("page", page);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 获取地区
	 * @param request 请求
	 * @param region 地区bean
	 * @return ResultInfo<Region> json
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:20:48
	 */
	@ResponseBody
	@RequestMapping(value="/getregion")
	public ResultInfo<Region> getRegion(HttpServletRequest request,Region region){
		ResultInfo<Region> resultInfo = new ResultInfo<Region>();
		List<Region> regionList = regionService.getRegionByParentId(region.getRegionId());
		if(regionList != null){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(regionList);
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 新增地区
	 * @param request
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 上午11:45:46
	 */
	@ResponseBody
	@RequestMapping(value="/addRegionPage")
	public ModelAndView addRegionPage(HttpServletRequest request,Region region){
		ModelAndView mav = new ModelAndView("system/region/add_region");
		List<Region> provinceList = regionService.getRegionByParentId(1);
		mav.addObject("provinceList", provinceList);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存新增地区
	 * @param request
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 上午11:45:46
	 */
	@ResponseBody
	@RequestMapping(value="/saveAddRegion")
	public ResultInfo<?> saveAddRegion(HttpServletRequest request,RegionVo regionVo){
		int i = regionService.saveRegin(regionVo);
		if(i > 0){
			vedengSoapService.regionSync(i);//同步到WEB
			
			return new ResultInfo<>(0, "操作成功");
		}else if(i == -1){
			return new ResultInfo<>(-1, "当前城市已存在，请确认");
		}else if(i == -2){
			return new ResultInfo<>(-1, "当前区县已存在，请确认");
		}
		return new ResultInfo<>();
	}
	
	/**
	 * <b>Description:</b><br> 查看子地区
	 * @param request
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月17日 上午9:57:28
	 */
	@ResponseBody
	@RequestMapping(value="/viewRegionChilds")
	public ModelAndView viewRegionChilds(HttpServletRequest request,Region region){
		ModelAndView mav = new ModelAndView("system/region/list_region_childs");
		List<Region> provinceList = regionService.getRegionByParentId(region.getRegionId());
		mav.addObject("list", provinceList);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 编辑地区
	 * @param request
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 上午11:45:46
	 */
	@ResponseBody
	@RequestMapping(value="/editRegionPage")
	public ModelAndView editRegionPage(HttpServletRequest request,Region region){
		ModelAndView mav = new ModelAndView("system/region/edit_region");
		Region reg = regionService.getRegionByRegionId(region.getRegionId());
		mav.addObject("region", reg);
		return mav;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑地区
	 * @param request
	 * @param region
	 * @return
	 * @Note
	 * <b>Author:</b> east
	 * <br><b>Date:</b> 2018年3月16日 上午11:45:46
	 */
	@ResponseBody
	@RequestMapping(value="/saveEditRegion")
	public ResultInfo<?> saveEditRegion(HttpServletRequest request,RegionVo regionVo){
		int i = regionService.saveEditRegin(regionVo);
		if(i > 0){
			vedengSoapService.regionSync(regionVo.getRegionId());//同步到WEB
			
			return new ResultInfo<>(0, "操作成功");
		}
		return new ResultInfo<>();
	}
	
	/**
	 * <b>Description:</b><br> 获取地区
	 * @param request 请求
	 * @param region 地区bean
	 * @return ResultInfo<Region> json
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2017年4月25日 上午11:20:48
	 */
	@ResponseBody
	@RequestMapping(value="/getRegionList")
	public ResultInfo<RegionVo> getRegionList(HttpServletRequest request,RegionVo region){
		ResultInfo<RegionVo> resultInfo = new ResultInfo<RegionVo>();
		List<RegionVo> regionList = regionService.getCityList(region);
		if(regionList != null){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(regionList);
		}
		return resultInfo;
	}
	
	/**
	 * <b>Description:</b><br> 根据主键查询
	 * @param RegionId
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年1月21日 上午11:14:20
	 */
	@ResponseBody
	@RequestMapping(value="/getRegionByRegionId")
	public ResultInfo<Region> getRegionByRegionId(Integer regionId) {
		ResultInfo<Region> resultInfo = new ResultInfo<Region>();
		Region regionList = regionService.getRegionByRegionId(regionId);
		if(regionList != null){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(regionList);
		}
		return resultInfo;
	}
	
	
	/**
	 * <b>Description:</b><br> 根据主键查询三级主键的ID
	 * @param regionId
	 * @return
	 * @Note
	 * <b>Author:</b> Bert
	 * <br><b>Date:</b> 2019年1月21日 上午11:14:20
	 */
	@ResponseBody
	@RequestMapping(value="/getRegionIdStringByMinRegionId")
	public ResultInfo<String> getRegionIdStringByMinRegionId(Integer regionId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		String regionList = regionService.getRegionIdStringByMinRegionId(regionId);
		if(regionList != null){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setData(regionList);
		}
		return resultInfo;
	}
}
