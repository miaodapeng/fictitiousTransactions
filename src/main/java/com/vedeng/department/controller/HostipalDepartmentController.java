package com.vedeng.department.controller;

import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.department.model.DepartmentFeeItems;
import com.vedeng.department.model.DepartmentsHospital;
import com.vedeng.department.service.DepartmentFeeItemsMappingService;
import com.vedeng.department.service.HostipalDepartmentService;
import com.vedeng.goods.dao.SpuDepartmentMappingGenerateMapper;
import com.vedeng.goods.model.SpuDepartmentMappingGenerateExample;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 科室信息
 * <p>Title: HostipalDepartmentController</p>
 * <p>Description: </p>  
 * @author Bill  
 * @date 2019年3月20日
 */
@Controller
@RequestMapping("/firstengage/baseinfo")
public class HostipalDepartmentController extends BaseController{

	@Autowired
	private HostipalDepartmentService hostipalDepartmentService;

	@Autowired
	private DepartmentFeeItemsMappingService departmentFeeItemsMappingService;

	/**
	 * 进入详情页的标识
	 */
	private final static Integer VIEW_1 = 1;

    /**
     * 进入编辑页的标识
     */
	private final static Integer EDIT_2 = 2;
	
	/**
	 * 国标分类，一级
	 */
	private static final Integer STANDARD_PARRNT_ID_0 = 0;

	private static final Logger logger = LoggerFactory.getLogger(HostipalDepartmentController.class);

    // 重复错误
    private final static Integer ERROR_REPEAT_NUM_1 = -1;

	/**
	 * 科室列表
	 * <p>Title: getAfterSalesPage</p>
	 * <p>Description: </p>
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author Bill
	 * @date 2019年3月20日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/gethostipaldepartmentinfo")
	@ResponseBody
	public ModelAndView getHostipalDepartmentInfo(HttpServletRequest request, DepartmentsHospital departmentsHospital,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize){

		ModelAndView mv = new ModelAndView();
        try {
            // 当前登陆用户
            User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            // 分页信息
            Page page = getPageTag(request, pageNo, pageSize);

            // 参数集
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("isDelete", CommonConstants.IS_DELETE_0);

            // keywords
            paramMap.put("keyWords", departmentsHospital.getKeyWords());

            // 关键词
            if(null != departmentsHospital && null != departmentsHospital.getSearchStatus()){
                paramMap.put("searchStatus", departmentsHospital.getSearchStatus());
            }

            // 时间排序
            if(null != departmentsHospital && null != departmentsHospital.getTimeSort()){
                paramMap.put("timeSort", departmentsHospital.getTimeSort());
                mv.addObject("timeSort", departmentsHospital.getTimeSort());
            }else {
                paramMap.put("timeSort", 1);
                mv.addObject("timeSort", 1);
            }

            // 更新开始时间
            if(EmptyUtils.isNotBlank(departmentsHospital.getUpdateStartDate())){
                paramMap.put("updateStartDate", DateUtil.convertLong(departmentsHospital.getUpdateStartDate(), DateUtil.DATE_FORMAT));
            }
            // 更新时间结束时间
            if(EmptyUtils.isNotBlank(departmentsHospital.getUpdateEndDate())){
                paramMap.put("updateEndDate", DateUtil.convertLong(departmentsHospital.getUpdateEndDate()+" 23:59:59", DateUtil.TIME_FORMAT));
            }


            // 获取科室列表
            Map<String, Object> deHospitals = hostipalDepartmentService.getHostipalDepartmentInfo(paramMap, page);
            // 空判断
            if(null != deHospitals){
                mv.addObject("hospitals", (List<DepartmentsHospital>) deHospitals.get("hospitals"));
                mv.addObject("page", (Page) deHospitals.get("page"));
            }
            else{
                mv.addObject("hospitals", null);
                mv.addObject("page", null);
            }

            mv.addObject("user", sessUser);
            // 科室信息
            mv.addObject("departmentsHospital", departmentsHospital);
        } catch (Exception e) {
            logger.error("科室列表:", e);
        }
        mv.setViewName("firstengage/department/index");
        return mv;
	}


	/**
	 * @description
	 * @author bill
	 * @param
	 * @date 2019/4/15
	 */
	@FormToken(save = true)
	@RequestMapping(value = "/adddepartment")
	public ModelAndView addHostipalDepartmentInfo(HttpServletRequest request, Integer departmentId) {
        ModelAndView mv = new ModelAndView();

        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 参数
        Map<String, Object> paramMap = new HashMap<>();
        // 一级类别
        paramMap.put("parentId", STANDARD_PARRNT_ID_0);
        // 获取所有收费项目
        try {
            JSONArray jsonArray = getAllFeePro();
            mv.addObject("user", user);
            mv.addObject("feeItems", jsonArray.toString());
            // 如果departmentId不为空，查询科室信息，进入编辑页
            if(null != departmentId && departmentId > 0){
                DepartmentsHospital departmentsHospital = hostipalDepartmentService.getDepartmentsHospitalInfo(paramMap, departmentId, EDIT_2);
                // 如果科室为空，返回404页面
                if(null == departmentsHospital){
                    return pageNotFound();
                }
                mv.addObject("departmentsHospital", departmentsHospital);
            }
        } catch (Exception e) {
            logger.error("新增科室页", e);
        }
        mv.setViewName("firstengage/department/add");
        return mv;
    }



    @FormToken(remove = true)
    @RequestMapping("/adddepartmentinfo")
    public ModelAndView addDepartmentServiceInfo(HttpServletRequest request, DepartmentsHospital departmentsHospital,
												String[] feeOne, String[] feeTwo, String[] feeThree){
        // 当前登陆用户
        User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        departmentsHospital.setCreator(sessUser.getUserId());

        departmentsHospital.setIsDelete(CommonConstants.IS_DELETE_0);
        departmentsHospital.setCreator(sessUser.getUserId());

        // 校验收费项目
        if(null == feeOne && feeOne.length <= 0){
            request.setAttribute("error", "收费项目不能为空");
            return new ModelAndView("forward:./newpage.do");
        }

        // 收费项目
        getFeePro(departmentsHospital, feeOne, feeTwo, feeThree);

		// 添加科室信息
		Integer departmentsHospitalId = hostipalDepartmentService.addDepartmentServiceInfo(departmentsHospital, sessUser);
		// 如果返回错误码是-1，表示重复添加了，返回页面错误信息
        if(ERROR_REPEAT_NUM_1.equals(departmentsHospitalId)){
            request.setAttribute("error", "该科室名称已经存在，无法提交");
            return new ModelAndView("forward:./newpage.do");
        }
        return new ModelAndView("redirect:/firstengage/baseinfo/getDepartmentsHospitalInfo.do?departmentsHospitalId=" + departmentsHospitalId);
    }

    /**
     * 转发 页面
     * @param request
     * @param departmentsHospital
     * @param feeOne
     * @param feeTwo
     * @param feeThree
     * @return
     */
    @FormToken(save = true)
    @RequestMapping("/newpage")
    public ModelAndView getForwardPage(HttpServletRequest request, DepartmentsHospital departmentsHospital,
                                       String[] feeOne, String[] feeTwo, String[] feeThree){

        // 收费项目
        getFeePro(departmentsHospital, feeOne, feeTwo, feeThree);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("firstengage/department/add");
        mv.addObject("departmentsHospital", departmentsHospital);
        // 获取所有收费项目
        JSONArray jsonArray = getAllFeePro();
        mv.addObject("feeItems", jsonArray.toString());
        // 错误
        mv.addObject("error", request.getAttribute("error"));

	    return mv;
    }

    /**
     * @description 科室详情页
     * @author bill
     * @param  
     * @date 2019/4/16
     */
    @RequestMapping("/getDepartmentsHospitalInfo")
    public ModelAndView getDepartmentsHospitalInfo(HttpServletRequest request, Integer departmentsHospitalId){

        User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 校验空
        if(null == departmentsHospitalId){
            return null;
        }

        // 参数集
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", sessUser.getUserId());
        paramMap.put("departmentsHospitalId", departmentsHospitalId);
        // 删除状态
        paramMap.put("isDelete", CommonConstants.IS_DELETE_0);
        DepartmentsHospital departmentsHospital = hostipalDepartmentService.getDepartmentsHospitalInfo(paramMap, departmentsHospitalId, VIEW_1);
        // 如果科室为空，返回404页面
        if(null == departmentsHospital){
            return pageNotFound();
        }

        // 获取科室列表信息
        ModelAndView mv = new ModelAndView();

        mv.addObject("departmentsHospital", departmentsHospital);
        mv.setViewName("firstengage/department/view");
        return mv;
    }
    @Autowired
SpuDepartmentMappingGenerateMapper spuDepartmentMappingGenerateMapper;
    @RequestMapping("/deletedepthospital")
    @ResponseBody
    public ResultInfo deleteHospital(HttpServletRequest request, Integer id){

        User sessUser = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // 参数
		DepartmentsHospital departmentsHospital = new DepartmentsHospital();
        // 参数集
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("deptHospitalId", id);
        paramMap.put("departmentId", id);
        paramMap.put("userId", sessUser.getUserId());
		// id
        departmentsHospital.setDepartmentId(id);
		departmentsHospital.setUpdater(sessUser.getUserId());

        SpuDepartmentMappingGenerateExample example=new SpuDepartmentMappingGenerateExample();
        example.createCriteria().andStatusEqualTo(CommonConstants.STATUS_1).andDepartmentIdEqualTo(id);
        if(spuDepartmentMappingGenerateMapper.countByExample(example)>0){
            return new ResultInfo<>(ERROR_REPEAT_NUM_1, "科室下存在商品，不可删除");
        }
        // 获取商品首营列表
        Integer delResult = hostipalDepartmentService.deleteDeptHospital(paramMap, departmentsHospital);
        // 如果删除的结果是-1，表示该科室已经被删除
		if(ERROR_REPEAT_NUM_1.equals(delResult)){
			return new ResultInfo<>(ERROR_REPEAT_NUM_1, "该科室已经被删除，无法操作");
		}
		// 删除成功操作
        if(null != delResult && delResult > CommonConstants.RESULTINFO_CODE_SUCCESS_0){
            return new ResultInfo<>(CommonConstants.RESULTINFO_CODE_SUCCESS_0, "操作成功");
        }
        return new ResultInfo<>();
    }



    /**
     * @description 获取所有收费项目
     * @author bill
     * @param
     * @date 2019/5/21
     */
    private JSONArray getAllFeePro() {
        // 参数
        Map<String, Object> paramMap = new HashMap<>();
        // 一级类别
        paramMap.put("parentId", STANDARD_PARRNT_ID_0);
        // 收费项目
        List<Map<String, Object>> feeItems = hostipalDepartmentService.getDepartmentFeeItemsInfo(paramMap);
        return new JSONArray(feeItems);
    }

    private void getFeePro(DepartmentsHospital departmentsHospital, String[] feeOne, String[] feeTwo, String[] feeThree) {
        Set<Integer> feeIds = new HashSet<>();
        int i = 0;
        // 遍历一级收费项目
        for (int t = 0; t < feeOne.length; t++){
            // 判断当前元素是否为空
            if(EmptyUtils.isBlank(feeOne[t])){
                continue;
            }

            // 判断三级收费项目id是否为空
            if(null != feeThree && feeThree.length > 0 && EmptyUtils.isNotBlank(feeThree[i])){
                // 根据逗号分解三级收费项目
                String[] feeThreeArray = feeThree[i].split("@");
                if(null != feeThreeArray && feeThreeArray.length > 0){
                    for (int j = 0; j < feeThreeArray.length; j++){
                        feeIds.add(Integer.valueOf(feeThreeArray[j]));
                    }
                }
                i++;
                continue;
            }

            // 判断二级收费项目id是否为空
            else if(null != feeTwo && feeTwo.length > 0 && EmptyUtils.isNotBlank(feeTwo[i])){
                feeIds.add(Integer.valueOf(feeTwo[i]));
                i++;
                continue;
            }
            // 如果都为空，取一级收费项目id
            else{
                feeIds.add(Integer.valueOf(feeOne[i]));
                i++;
            }
        }

        if(CollectionUtils.isNotEmpty(feeIds)){
            List<Integer> feeProIds = departmentFeeItemsMappingService.getMinFeesIds(feeIds);
            // 去重
            Set<Integer> setCollection = new HashSet<>();
            setCollection.addAll(feeProIds);
            // 收费项目
            List<DepartmentFeeItems> departmentFeeItems = departmentFeeItemsMappingService.getFeesByIds(feeIds);
            departmentsHospital.setDepartmentFeeItems(departmentFeeItems);
            departmentsHospital.setFeePros(feeProIds);
        }
    }
}
