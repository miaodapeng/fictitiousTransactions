package com.vedeng.goods.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.goods.model.Unit;
import com.vedeng.goods.model.UnitGroup;
import com.vedeng.goods.service.UnitService;
import com.vedeng.soap.service.VedengSoapService;

/**
 * <b>Description:</b><br> 产品单位管理
 * @author leo.yang
 * @Note
 * <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.goods.controller
 * <br><b>ClassName:</b> UnitController
 * <br><b>Date:</b> 2017年5月12日 下午2:39:27
 */
@Controller
@RequestMapping("/goods/unit")
public class UnitController extends BaseController {
	@Autowired
	@Qualifier("unitService")
	private UnitService unitService;
	
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;

	/**
	 * <b>Description:</b><br> 单位列表（分页）
	 * @param request
	 * @param unit
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月16日 下午5:51:29
	 */
	@ResponseBody
	@RequestMapping(value="/index")
	public ModelAndView index(HttpServletRequest request, Unit unit,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo, 
			@RequestParam(required = false) Integer pageSize) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();

		Page page = getPageTag(request,pageNo,pageSize);

		unit.setCompanyId(user.getCompanyId());
		
		Map<String,Object> map = unitService.getUnitListPage(unit,page);
		
		mv.addObject("unitList",(List<Unit>)map.get("list"));
		mv.addObject("unit",unit);
		mv.addObject("page", (Page)map.get("page"));
		mv.setViewName("goods/unit/index");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 添加产品单位
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月12日 下午4:40:04
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public ModelAndView add(HttpServletRequest request, UnitGroup unitGroup) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		unitGroup.setCompanyId(user.getCompanyId());
		List<UnitGroup> list = unitService.getUnitGroupList(unitGroup);
		
		mv.addObject("unitGroupList", list);
		mv.setViewName("goods/unit/add");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存添加的单位
	 * @param request
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月16日 下午5:49:00
	 */
	@ResponseBody
	@RequestMapping(value="/addunit")
	@SystemControllerLog(operationType = "add",desc = "保存新增单位")
	public ResultInfo<?> addUnit(HttpServletRequest request, Unit unit) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			unit.setCreator(user.getUserId());
			unit.setAddTime(System.currentTimeMillis());
			
			unit.setUpdater(user.getUserId());
			unit.setModTime(DateUtil.sysTimeMillis());
			unit.setCompanyId(user.getCompanyId());
		}
		ResultInfo<?> result = unitService.addUnit(unit);
		if(result.getCode().equals(0) && user.getCompanyId().equals(1)){
			//同步
			Unit u = (Unit) result.getData();
			vedengSoapService.unitSync(u.getUnitId());
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 编辑产品单位
	 * @param request
	 * @param unit
	 * @return
	 * @throws IOException 
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月17日 上午10:08:17
	 */
	@ResponseBody
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request, Unit unit, UnitGroup unitGroup) throws IOException {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ModelAndView mv = new ModelAndView();
		unitGroup.setCompanyId(user.getCompanyId());
		List<UnitGroup> list = unitService.getUnitGroupList(unitGroup);
		mv.addObject("unitGroupList", list);
		
		unit = unitService.getUnitById(unit);
		mv.addObject("unit", unit);
		mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(unit)));
		mv.setViewName("/goods/unit/edit");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑的单位
	 * @param request
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月17日 上午11:48:58
	 */
	@ResponseBody
	@RequestMapping(value="/editUnit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑的单位")
	public ResultInfo<?> editUnit(HttpServletRequest request, Unit unit, String beforeParams) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			unit.setCompanyId(user.getCompanyId());
			unit.setUpdater(user.getUserId());
			unit.setModTime(System.currentTimeMillis());
		}
		ResultInfo<?> result = unitService.editUnit(unit);
		if(result.getCode().equals(0) && user.getCompanyId().equals(1)){
			//同步
			vedengSoapService.unitSync(unit.getUnitId());
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 删除单位（根据主键）
	 * @param request
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月17日 下午1:32:05
	 */
	@ResponseBody
	@RequestMapping(value="/delunitbyid")
	@SystemControllerLog(operationType = "delete",desc = "删除单位")
	public ResultInfo<?> delUnitById(HttpServletRequest request, Unit unit) {
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if (user != null) {
			unit.setIsDel(1);
			unit.setUpdater(user.getUserId());
			unit.setModTime(System.currentTimeMillis());
		}
		ResultInfo<?> result = unitService.delUnitById(unit);
		if(result.getCode().equals(0) && user.getCompanyId().equals(1)){
			//同步
			vedengSoapService.unitSync(unit.getUnitId());
		}
		return result;
	}
	
	/**
	 * <b>Description:</b><br> 获取全部单位（不分页）
	 * @param request
	 * @param unit
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2017年5月19日 上午11:49:28
	 */
	@ResponseBody
	@RequestMapping(value="getallunit")
	public ResultInfo<?> getAllBrand(HttpServletRequest request,Unit unit){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		ResultInfo<Unit> resultInfo = new ResultInfo<Unit>();
		unit.setCompanyId(user.getCompanyId());
		List<Unit> unitList = unitService.getAllUnitList(unit);
		
		if(unitList != null){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
			resultInfo.setListData(unitList);
		}
		return resultInfo;
	}
	
}
