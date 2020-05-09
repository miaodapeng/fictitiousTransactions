package com.vedeng.trader.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.vo.RoleVo;
import com.vedeng.common.request.CustomerHttpServletRequest;
import com.vedeng.common.util.*;
import com.vedeng.system.service.*;
import com.vedeng.trader.model.*;

import com.vedeng.goods.service.VgoodsService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.order.model.vo.BuyorderGoodsVo;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderAmountBillVo;
import com.vedeng.trader.model.vo.TraderCertificateVo;
import com.vedeng.trader.model.vo.TraderContactVo;
import com.vedeng.trader.model.vo.TraderFinanceVo;
import com.vedeng.trader.model.vo.TraderMedicalCategoryVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.model.vo.TraderVo;
import com.vedeng.trader.service.TraderCustomerService;
import com.vedeng.trader.service.TraderSupplierService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br> 供应商
 *
 * @author Jerry
 * @Note <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.controller
 * <br><b>ClassName:</b> TraderSupplierController
 * <br><b>Date:</b> 2017年5月10日 上午9:42:21
 */
@Controller
@RequestMapping("/trader/supplier")
public class TraderSupplierController extends BaseController {

    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Autowired // 自动装载
    @Qualifier("historyService")
    private HistoryService historyService;

    @Resource
    private TraderSupplierService traderSupplierService;
    @Resource
    private UserService userService;
    @Autowired
    @Qualifier("regionService")
    private RegionService regionService;
    @Resource
    private PositService positService;
    @Autowired
    @Qualifier("tagService")
    private TagService tagService;

    @Autowired
    @Qualifier("sysOptionDefinitionService")
    private SysOptionDefinitionService sysOptionDefinitionService;

    @Autowired
    @Qualifier("traderCustomerService")
    private TraderCustomerService traderCustomerService;

    @Autowired
    @Qualifier("verifiesRecordService")
    private VerifiesRecordService verifiesRecordService;

    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @Autowired
    private OrganizationService organizationService;

	@Autowired
	private VgoodsService vGoodsService;

    /**
     * <b>Description:</b><br> 供应商列表
     *
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月10日 上午9:42:59
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, TraderSupplierVo traderSupplierVo,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        Page page = getPageTag(request, pageNo, pageSize);
        traderSupplierVo.setCompanyId(user.getCompanyId());
        // 查询所有职位类型为311的员工
//		List<Integer> positionType = new ArrayList<>();
//		positionType.add(SysOptionConstant.ID_311);//采购
//		List<User> userList = userService.getMyUserList(user, positionType, false);

        List<User> userList = userService.getUserListByPositType(SysOptionConstant.ID_311, user.getCompanyId());

        List<Integer> userIdList = new ArrayList<>();
//		if(userList != null && userList.size() > 0){
//			for (User u : userList) {
//				userIdList.add(u.getUserId());
//			}
//		}
//		traderSupplierVo.setUserIdList(userIdList);

//		List<Integer> traderList = new ArrayList<>();

        //if(isNUllTraderSupplierrVo(traderSupplierVo)){//页面初始加载进来以及没有任何搜索条件
        String search = request.getParameter("search");
        if (search == null || "".equals(search)) {
//			traderList = userService.getTraderIdsByUserList(userList,ErpConst.TWO);
//			if(traderList!=null && traderList.size()>0){
//				traderSupplierVo.setTraderList(traderList);
//			}else{
//				traderList.add(-1);
//				traderSupplierVo.setTraderList(traderList);
//			}

        } else {
            if (ObjectUtils.notEmpty(traderSupplierVo.getHomePurchasing())) {
                //userIdList = userService.getTraderIdListByUserId(traderSupplierVo.getHomePurchasing(),ErpConst.TWO);
                userIdList.add(traderSupplierVo.getHomePurchasing());
            }
//			if(traderList.size()==0){
//				traderList=null;
//			}
//			traderSupplierVo.setTraderList(traderList);
            if (ObjectUtils.notEmpty(traderSupplierVo.getZone())) {
                traderSupplierVo.setAreaId(traderSupplierVo.getZone());
                List<Region> list = regionService.getRegionByParentId(traderSupplierVo.getCity());
                mv.addObject("zoneList", list);
                List<Region> cys = regionService.getRegionByParentId(traderSupplierVo.getProvince());
                mv.addObject("cityList", cys);
            } else if (ObjectUtils.notEmpty(traderSupplierVo.getProvince()) && ObjectUtils.notEmpty(traderSupplierVo.getCity()) && ObjectUtils.isEmpty(traderSupplierVo.getZone())) {
                traderSupplierVo.setAreaId(traderSupplierVo.getCity());
                List<Region> list = regionService.getRegionByParentId(traderSupplierVo.getCity());
                mv.addObject("zoneList", list);
                List<Region> cys = regionService.getRegionByParentId(traderSupplierVo.getProvince());
                mv.addObject("cityList", cys);
            } else if (ObjectUtils.notEmpty(traderSupplierVo.getProvince()) && ObjectUtils.isEmpty(traderSupplierVo.getCity()) && ObjectUtils.isEmpty(traderSupplierVo.getZone())) {
                traderSupplierVo.setAreaId(traderSupplierVo.getProvince());
                List<Region> list = regionService.getRegionByParentId(traderSupplierVo.getProvince());
                mv.addObject("cityList", list);
            } else {
                traderSupplierVo.setAreaId(null);
            }
            String queryStartTime = request.getParameter("queryStartTime");
            if (queryStartTime != null && !"".equals(queryStartTime)) {
                traderSupplierVo.setStartTime(DateUtil.convertLong(queryStartTime, "yyyy-MM-dd"));
            }
            String queryEndTime = request.getParameter("queryEndTime");
            if (queryEndTime != null && !"".equals(queryEndTime)) {
                traderSupplierVo.setEndTime(DateUtil.convertLong(queryEndTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }

        }
        if (userIdList == null || userIdList.size() == 0) {
            //userIdList.add(-1);//防止关联不到用户全表查询
            userIdList = null;
        }
        traderSupplierVo.setUserIdList(userIdList);
        //供应商分页列表信息
        Map<String, Object> map = this.traderSupplierService.getTraderSupplierList(traderSupplierVo, page, userList);
        List<TraderSupplierVo> list = null;
        if (map != null) {
            list = (List<TraderSupplierVo>) map.get("list");
            page = (Page) map.get("page");
        }
        mv.addObject("list", list);
        //省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mv.addObject("userList", userList);
        mv.addObject("provinceList", provinceList);
        mv.addObject("traderSupplierVo", traderSupplierVo);
        mv.addObject("page", page);
        mv.setViewName("trader/supplier/index");
        return mv;
    }

    /**
     * <b>Description:</b><br> 判断当前是否有搜索条件
     * @param traderCustomerVo
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午4:34:06
     */
//	private boolean isNUllTraderSupplierrVo(TraderSupplierVo traderSupplierVo){
//		if(ObjectUtils.isEmpty(traderSupplierVo.getTraderSupplierName()) && ObjectUtils.isEmpty(traderSupplierVo.getHomePurchasing())
//				&& ObjectUtils.isEmpty(traderSupplierVo.getCooperate()) && ObjectUtils.isEmpty(traderSupplierVo.getGrade())
//				&& ObjectUtils.isEmpty(traderSupplierVo.getSearchMsg()) && ObjectUtils.isEmpty(traderSupplierVo.getContactWay())
//				&& ObjectUtils.isEmpty(traderSupplierVo.getTraderSupplierStatus())&& ObjectUtils.isEmpty(traderSupplierVo.getQueryEndTime())
//				&& ObjectUtils.isEmpty(traderSupplierVo.getQueryStartTime()) && ObjectUtils.isEmpty(traderSupplierVo.getProvince())
//				&& ObjectUtils.isEmpty(traderSupplierVo.getCity()) && ObjectUtils.isEmpty(traderSupplierVo.getZone())
//				){
//			return true;
//		}
//		return false;
//	}

    /**
     * <b>Description:</b><br> 提取当前用户下面用户的id集合
     * @param userList
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午5:32:07
     */
//	private List<Integer> getUserIds(List<User> userList){
//		if(userList!=null && userList.size()>0){
//			List<Integer> userIds = new ArrayList<>();
//			for (User user : userList) {
//				userIds.add(user.getUserId());
//			}
//			return userIds;
//		}
//		return null;
//	}
    /**
     * <b>Description:</b><br> 根据省份获取市或根据市获取区(废弃)
     * @param provId
     * @return
     * @Note
     * <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月15日 下午2:44:06
     *//*
	@ResponseBody
	@RequestMapping(value="/getRegionListByParentId")
	public ResultInfo<List<Region>> getRegionListByParentId(Integer parentId){
		ResultInfo<List<Region>> result = new ResultInfo<List<Region>>();
		List<Region> list=regionService.getRegionByParentId(parentId);
		if(null!=list&&list.size()>0){
			result.setData(list);
			result.setCode(0);
		}else{
			result.setMessage("暂无数据");
		}
		return result;
	}*/

    /**
     * <b>Description:</b><br> 置顶
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isStick")
    @SystemControllerLog(operationType = "edit", desc = "置顶")
    public ResultInfo isStick(Integer id, Integer isTop, HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        ResultInfo ri = traderSupplierService.isStick(id, isTop, user);
        if (ri == null) {
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        return ri;
    }

    /**
     * <b>Description:</b><br> 初始化禁用页面
     *
     * @param request
     * @param id
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月22日 下午2:09:38
     */
    @ResponseBody
    @RequestMapping(value = "/initDisabledPage")
    public ModelAndView initDisabledPage(HttpServletRequest request, Integer id) {
        ModelAndView mav = new ModelAndView("trader/supplier/forbid");
        mav.addObject("id", id);
        return mav;
    }

    /**
     * <b>Description:</b><br> 禁用供应商
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDisabledSupplier")
    @SystemControllerLog(operationType = "edit", desc = "禁用供应商")
    public ResultInfo isDisabled(Integer id, Integer isDisabled, String disabledReason, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderSupplierService.isDisabled(user, id, isDisabled, disabledReason);
        if (ri == null) {
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 禁用供应商联系人
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDisabledContact")
    @SystemControllerLog(operationType = "edit", desc = "禁用供应商联系人")
    public ResultInfo isDisabledContact(TraderContact traderContact, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDisabledContact(traderContact, user);
        if (ri == null) {
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 设置供应商默认联系人
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("all")
    @ResponseBody
    @RequestMapping(value = "/saveSupplierDefaultContact")
    @SystemControllerLog(operationType = "edit", desc = "设置供应商默认联系人")
    public ResultInfo saveSupplierDefaultContact(TraderContact traderContact, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDefaultContact(traderContact, user);
        ri.setData(traderContact.getTraderId() + "," + request.getParameter("traderSupplierId"));
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 禁用/启用供应商地址
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDisabledAddress")
    @SystemControllerLog(operationType = "edit", desc = "禁用/启用供应商地址")
    public ResultInfo isDisabledAddress(TraderAddress traderAddress, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDisabledAddress(traderAddress, user);
        if (ri == null) {
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 设置供应商默认地址
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDefaultAddress")
    @SystemControllerLog(operationType = "edit", desc = "设置供应商默认地址")
    public ResultInfo isDefaultAddress(TraderAddress traderAddress, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDefaultAddress(traderAddress, user);
        if (ri == null) {
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        ri.setData(traderAddress.getTraderId() + "," + request.getParameter("traderSupplierId"));
        return ri;
    }


    /**
     * <b>Description:</b><br> 新增供应商
     *
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月10日 上午10:21:43
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        //地区
        List<Region> provinceList = regionService.getRegionByParentId(1);


        //标签
        Tag tag = new Tag();
        tag.setTagType(SysOptionConstant.ID_31);
        tag.setIsRecommend(ErpConst.ONE);
        tag.setCompanyId(user.getCompanyId());

        Integer pageNo = 1;
        Integer pageSize = 10;

        Page page = getPageTag(request, pageNo, pageSize);
        Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

        mv.addObject("provinceList", provinceList);

        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));
        // 医疗类别
        List<SysOptionDefinition> medicalTypes = getSysOptionDefinitionList(SysOptionConstant.ID_20);

        mv.addObject("medicalTypes", medicalTypes);
        // 医疗类别级别
        List<SysOptionDefinition> medicalTypLevels = getSysOptionDefinitionList(SysOptionConstant.ID_21);

        mv.addObject("medicalTypLevels", medicalTypLevels);
        mv.addObject("domain", domain);
        mv.setViewName("trader/supplier/add_supplier");
        return mv;
    }


    /**
     * <b>Description:</b><br> 保存添加供应商
     *
     * @param trader
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月18日 上午9:55:12
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveadd")
    @SystemControllerLog(operationType = "add", desc = "保存新增供应商信息")
    public ModelAndView saveAdd(Trader trader, HttpServletRequest request
            , HttpSession session) {
        User user =(User)session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        TraderSupplier traderSupplier;
        try {
            trader.setTraderName(trader.getTraderName().trim());
            traderSupplier = traderSupplierService.saveSupplier(trader, request, session);
            if (null != traderSupplier) {
                if(traderSupplier.getTraderId()!=null) {
                    String[] traderId = new String[1];
                    traderId[0] = String.valueOf(traderSupplier.getTraderId());
                    String[] traderType=new String[]{"2"};
                    Map<String,String[]> paramMap=new HashMap<>(request.getParameterMap());
                    paramMap.put("traderId", traderId);
                    paramMap.put("traderType",traderType);
                    CustomerHttpServletRequest customerHttpServletRequest=new CustomerHttpServletRequest(request,paramMap);
                    TraderVo traderVo = new TraderVo();
                    traderVo.setTraderType(2);
                    traderVo.setTraderId(traderSupplier.getTraderId());
                    saveAptitude(customerHttpServletRequest, traderVo, null, null);
                    saveContactAndAddress(request, traderSupplier.getTraderId(), user);

                }
                mv.addObject("url", "./baseinfo.do?traderId=" + traderSupplier.getTraderId() + "&traderSupplierId=" + traderSupplier.getTraderSupplierId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("trader supplier saveadd:", e);
            return fail(mv);
        }
    }

    private void saveContactAndAddress(HttpServletRequest request,Integer traderId,User user){
        List<TraderContact> contactList=null;
        List<TraderAddress> addressList=null;
        String contactStr=request.getParameter("contactList");
        String addressStr=request.getParameter("addressList");
        try {
            if (StringUtil.isNotBlank(contactStr)) {
                TypeReference<List<TraderContact>> contactType = new TypeReference<List<TraderContact>>() {
                };
                contactList = JsonUtils.readValueByType(contactStr, contactType);
            }
            if (StringUtil.isNotBlank(addressStr)) {
                TypeReference<List<TraderAddress>> addressType = new TypeReference<List<TraderAddress>>() {
                };
                addressList = JsonUtils.readValueByType(addressStr,addressType );
            }
        }catch (Exception ex){
            logger.error("新增供应商解析联系人和地址出错",ex);
        }
        if(!CollectionUtils.isEmpty(contactList)){
            for(TraderContact c:contactList){
                if(c==null){
                    continue;
                }
                c.setTraderId(traderId);
                c.setTraderType(2);
                traderCustomerService.saveTraderContact(c,user);
            }
        }

        if(!CollectionUtils.isEmpty(addressList)){
            for(TraderAddress a:addressList){
                if(a==null){
                    continue;
                }
                a.setTraderId(traderId);
                a.setTraderType(2);
                traderCustomerService.saveTraderAddress(a,user);
            }
        }
    }
    /**
     * <b>Description:</b><br> 供应商详情
     *
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月10日 上午11:05:08
     */
    @ResponseBody
    @RequestMapping(value = "/view")
    public ModelAndView view(TraderSupplier traderSupplier) {
        if (StringUtils.isEmpty(traderSupplier)
                || null == traderSupplier.getTraderSupplierId()
                || traderSupplier.getTraderSupplierId() <= 0
        ) {
            return pageNotFound();
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("traderSupplier", traderSupplier);
        mv.setViewName("trader/supplier/view_supplier");
        return mv;
    }

    /**
     * <b>Description:</b><br> 供应商基本信息
     *
     * @param traderSupplier
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月18日 上午10:55:11
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/baseinfo")
    public ModelAndView baseinfo(TraderSupplier traderSupplier, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mv.addObject("curr_user", curr_user);
        TraderSupplierVo traderBaseInfo = traderSupplierService.getTraderSupplierBaseInfo(traderSupplier);
        //审核人
        if (null != traderBaseInfo.getVerifyUsername()) {
            List<String> verifyUsernameList = Arrays.asList(traderBaseInfo.getVerifyUsername().split(","));
            traderBaseInfo.setVerifyUsernameList(verifyUsernameList);
        }
        //地区
        if (null != traderBaseInfo.getTrader().getAreaId()
                && traderBaseInfo.getTrader().getAreaId() > 0) {
            String region = (String) regionService.getRegion(traderBaseInfo.getTrader().getAreaId(), 2);
            mv.addObject("region", region);
        }
        Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "traderSupplierVerify_" + traderBaseInfo.getTraderSupplierId());
        mv.addObject("taskInfo", historicInfo.get("taskInfo"));
        mv.addObject("startUser", historicInfo.get("startUser"));
        mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
        // 最后审核状态
        mv.addObject("endStatus", historicInfo.get("endStatus"));
        mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
        mv.addObject("commentMap", historicInfo.get("commentMap"));

        mv.addObject("traderSupplier", traderBaseInfo);
        mv.addObject("method", "baseinfo");
        mv.addObject("belongSupplyOrg",isCurrentUserBelong2SupplyOrg(curr_user));
        initAptitudePart(mv, traderSupplier);
        Task taskInfo = (Task) historicInfo.get("taskInfo");
        //当前审核人
        String verifyUsers = null;
        if (null != taskInfo) {
            Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
            verifyUsers = (String) taskInfoVariables.get("verifyUsers");
        }
        mv.addObject("verifyUsers", verifyUsers);
        mv.setViewName("trader/supplier/view_baseinfo");
        return mv;
    }

    private void initContactPart(ModelAndView mav, TraderSupplier traderSupplier) {
        TraderContactVo traderContactVo = new TraderContactVo();
        traderContactVo.setTraderId(traderSupplier.getTraderId());
        traderContactVo.setTraderType(ErpConst.TWO);
        Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
        String tastr = (String) map.get("contact");
        net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
        List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
        List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");

        TraderSupplier supplierInfoByTraderSupplier = traderSupplierService.getSupplierInfoByTraderSupplier(traderSupplier);
        mav.addObject("supplierInfoByTraderSupplier", supplierInfoByTraderSupplier);
        mav.addObject("contactList", list);
        mav.addObject("addressList", taList);
        mav.addObject("traderId", traderSupplier.getTraderId());
    }

    private void initAptitudePart(ModelAndView mav, TraderSupplier traderSupplier) {
        TraderCertificateVo tc = new TraderCertificateVo();
        tc.setTraderId(traderSupplier.getTraderId());
        tc.setTraderType(ErpConst.TWO);
        Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(tc, "all");
        List<TraderCertificateVo> bus = null;
        // 营业执照信息
        if (map.containsKey("business")) {
//			JSONObject json=JSONObject.fromObject(map.get("business"));
//			bus=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("business", bus);
            bus = (List<TraderCertificateVo>) map.get("business");
            if (!CollectionUtils.isEmpty(bus)) {
                mav.addObject("business", bus.get(0));
            }
        }
        // 税务登记信息
        TraderCertificateVo tax = null;
        if (map.containsKey("tax")) {
            JSONObject json = JSONObject.fromObject(map.get("tax"));
            tax = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("tax", tax);
        }
        // 组织机构信息
        TraderCertificateVo orga = null;
        if (map.containsKey("orga")) {
            JSONObject json = JSONObject.fromObject(map.get("orga"));
            orga = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("orga", orga);
        }
        // 二类医疗资质
        List<TraderCertificateVo> twoMedicalList = null;
        if (map.containsKey("twoMedical")) {
            //JSONObject json=JSONObject.fromObject(map.get("twoMedical"));
            twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
            mav.addObject("twoMedicalList", twoMedicalList);
        }
        // 三类医疗资质
        List<TraderCertificateVo> threeMedical = null;
        if (map.containsKey("threeMedical")) {
//			JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
            threeMedical = (List<TraderCertificateVo>) map.get("threeMedical");
            if (!CollectionUtils.isEmpty(threeMedical)) {
                mav.addObject("threeMedical", threeMedical.get(0));
            }

        }
        List<TraderMedicalCategoryVo> two = null;
        if (map.containsKey("two")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("two"));
            two = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("two", two);
        }
        List<TraderMedicalCategoryVo> three = null;
        if (map.containsKey("three")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("three"));
            three = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("three", three);
        }
        // 医疗器械生产许可证
        TraderCertificateVo product = null;
        if (map.containsKey("product")) {
            JSONObject json = JSONObject.fromObject(map.get("product"));
            product = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("product", product);
        }
        // 医疗器械经营许可证
        TraderCertificateVo operate = null;
        if (map.containsKey("operate")) {
            JSONObject json = JSONObject.fromObject(map.get("operate"));
            operate = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("operate", operate);
        }
        // begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21
        // 销售授权书与授权销售人
        TraderCertificateVo saleAuth = null;
        if (map.containsKey("saleAuth")) {
            JSONObject json = JSONObject.fromObject(map.get("saleAuth"));
            saleAuth = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("saleAuth", saleAuth);
        }
        // end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21

        //品牌授权书
        List<TraderCertificateVo> brandBookList = null;
        if (map.containsKey("brandBookList")) {
            brandBookList = (List<TraderCertificateVo>) map.get("brandBookList");
            if (brandBookList.size() == 0) {
                brandBookList = null;
            }
            mav.addObject("brandBookList", brandBookList);
        }
        //其他
        List<TraderCertificateVo> otherList = null;
        if (map.containsKey("otherList")) {
            otherList = (List<TraderCertificateVo>) map.get("otherList");
            mav.addObject("otherList", otherList);
        }
    }

    /**
     * <b>Description:</b><br> 管理信息
     *
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月10日 上午11:45:33
     */
    @ResponseBody
    @RequestMapping(value = "/manageinfo")
    public ModelAndView manageinfo(HttpServletRequest request, TraderSupplier traderSupplier) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        traderSupplier.setCompanyId(user.getCompanyId());
        ModelAndView mv = new ModelAndView();

        TraderSupplierVo traderManageInfo = traderSupplierService.getTraderSupplierManageInfo(traderSupplier);

//		//审核记录
//		VerifiesRecord verifiesRecord = new VerifiesRecord();
//		verifiesRecord.setVerifiesType(SysOptionConstant.ID_136);
//		verifiesRecord.setRelatedId(traderSupplier.getTraderSupplierId());
//		List<VerifiesRecord> verifiesList = verifiesRecordService.getVerifiesRecord(verifiesRecord);
        mv.addObject("traderSupplier", traderManageInfo);
//		mv.addObject("verifiesList", verifiesList);
        mv.addObject("method", "manageinfo");
        mv.setViewName("trader/supplier/view_manageinfo");
        return mv;
    }

    /**
     * 订单覆盖品类、品牌、区域
     * <b>Description:</b><br>
     *
     * @param request
     * @param traderOrderGoods
     * @return
     * @Note <b>Author:</b> Bill
     * <br><b>Date:</b> 2018年7月9日 上午9:25:24
     */
    @ResponseBody
    @RequestMapping(value = "/ordercoverinfo")
    public TraderSupplierVo getOrderCoverInfo(HttpServletRequest request, TraderOrderGoods traderOrderGoods) {

        TraderSupplierVo orderCoverInfo = traderSupplierService.getOrderCoverInfo(traderOrderGoods);

        return orderCoverInfo;
    }

    /**
     * <b>Description:</b><br> 编辑基本信息
     *
     * @param traderSupplier
     * @return
     * @throws IOException
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月18日 下午1:49:14
     */
    @ResponseBody
    @RequestMapping(value = "/editbaseinfo")
    public ModelAndView editBaseInfo(TraderSupplier traderSupplier) throws IOException {
        ModelAndView mv = new ModelAndView();

        TraderSupplierVo traderBaseInfo = traderSupplierService.getTraderSupplierBaseInfo(traderSupplier);

        //地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        if (null != traderBaseInfo.getTrader().getAreaId()
                && traderBaseInfo.getTrader().getAreaId() > 0
                && null != traderBaseInfo.getTrader().getAreaIds()
                && traderBaseInfo.getTrader().getAreaIds() != "") {

            Integer areaId = traderBaseInfo.getTrader().getAreaId();
            List<Region> regionList = (List<Region>) regionService.getRegion(areaId, 1);

            if (!StringUtils.isEmpty(regionList)) {
                for (Region r : regionList) {
                    switch (r.getRegionType()) {
                        case 1:
                            List<Region> cityList = regionService.getRegionByParentId(r.getRegionId());
                            mv.addObject("provinceRegion", r);
                            mv.addObject("cityList", cityList);
                            break;
                        case 2:
                            List<Region> zoneList = regionService.getRegionByParentId(r.getRegionId());
                            mv.addObject("cityRegion", r);
                            mv.addObject("zoneList", zoneList);
                            break;
                        case 3:
                            mv.addObject("zoneRegion", r);
                            break;
                        default:
                            mv.addObject("countryRegion", r);
                            break;
                    }
                }
            }
        }

        mv.addObject("traderSupplier", traderBaseInfo);
        mv.addObject("provinceList", provinceList);
        mv.addObject("method", "baseinfo");
        traderSupplier.setTraderId(traderBaseInfo.getTraderId());
        initEditAptitudePart(mv, traderSupplier);
        mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderBaseInfo)));

        mv.setViewName("trader/supplier/edit_baseinfo");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存编辑客户
     *
     * @param trader
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月18日 上午8:42:50
     */
    @ResponseBody
    @RequestMapping(value = "/saveeditbaseinfo")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑供应商信息")
    public ModelAndView saveEditBaseInfo(Trader trader, TraderVo childTraderVo, HttpServletRequest request, HttpSession session, String beforeParams) {
        ModelAndView mv = new ModelAndView();
        TraderSupplier customer;
        try {
            trader.setTraderName(trader.getTraderName().trim());
            customer = traderSupplierService.saveEditBaseInfo(trader, request, session);
            if (null != trader.getTraderSupplier().getTraderSupplierId()) {
                actionProcdefService.updateVerifyInfo("T_TRADER_SUPPLIER", trader.getTraderSupplier().getTraderSupplierId(), 3);
            }
            saveAptitude(request, childTraderVo, null, null);
            if (null != customer) {
                mv.addObject("refresh", "true_false_true");
//				mv.addObject("url","./baseinfo.do?traderSupplierId="+customer.getTraderSupplierId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("saveeditbaseinfo:", e);
            return fail(mv);
        }
    }


    /**
     * <b>Description:</b><br> 编辑管理信息
     *
     * @param traderSupplier
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月17日 上午9:54:47
     */
    @ResponseBody
    @RequestMapping(value = "/editmanageinfo")
    public ModelAndView editManageInfo(TraderSupplier traderSupplier, HttpServletRequest request, HttpSession session) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        //管理信息
        TraderSupplier traderManageInfo = traderSupplierService.getTraderSupplierManageInfo(traderSupplier);

        //标签
        Tag tag = new Tag();
        tag.setTagType(SysOptionConstant.ID_31);
        tag.setIsRecommend(ErpConst.ONE);
        tag.setCompanyId(user.getCompanyId());

        Integer pageNo = 1;
        Integer pageSize = 10;

        Page page = getPageTag(request, pageNo, pageSize);
        Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

        mv.addObject("traderSupplier", traderManageInfo);

        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));
        mv.addObject("method", "manageinfo");
        try {
            mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderManageInfo)));
        } catch (IOException e) {
            logger.error("editmanageinfo:", e);
        }
        mv.setViewName("trader/supplier/edit_manageinfo");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存编辑管理信息
     *
     * @param traderSupplier
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月17日 上午11:21:08
     */
    @ResponseBody
    @RequestMapping(value = "/saveeditmanageinfo")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑供应商管理信息")
    public ModelAndView saveEditManageInfo(TraderSupplier traderSupplier, HttpServletRequest request, HttpSession session, String beforeParams) {
        ModelAndView mv = new ModelAndView();
        TraderSupplier customer;
        try {
            customer = traderSupplierService.saveEditManageInfo(traderSupplier, request, session);
            if (null != customer) {
                mv.addObject("url", "./manageinfo.do?traderSupplierId=" + customer.getTraderSupplierId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("saveeditmanageinfo:", e);
            return fail(mv);
        }
    }


    /**
     * <b>Description:</b><br> 沟通记录
     *
     * @param request
     * @param communicateRecord
     * @param traderSupplier
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午3:26:34
     */
    @ResponseBody
    @RequestMapping(value = "/communicaterecord")
    public ModelAndView communicateRecord(HttpServletRequest request, CommunicateRecord communicateRecord, TraderSupplier traderSupplier,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(required = false) Integer pageSize, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request, pageNo, pageSize);

        communicateRecord.setTraderType(ErpConst.TWO);//客户类型

        List<CommunicateRecord> communicateRecordList = traderSupplierService.getCommunicateRecordListPage(communicateRecord, page);
        TraderSupplier supplierInfoByTraderSupplier = traderSupplierService.getSupplierInfoByTraderSupplier(traderSupplier);
        mv.addObject("supplierInfoByTraderSupplier", supplierInfoByTraderSupplier);
        mv.addObject("communicateRecordList", communicateRecordList);
        mv.addObject("page", page);
        mv.addObject("method", "communicaterecord");
        mv.setViewName("trader/supplier/communicate_record");
        return mv;
    }

    /**
     * <b>Description:</b><br> 新增沟通
     *
     * @param traderSupplier
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午3:32:49
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/addcommunicate")
    public ModelAndView addCommunicate(TraderSupplier traderSupplier, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();

        TraderContact traderContact = new TraderContact();
        //联系人
        traderContact.setTraderId(traderSupplier.getTraderId());
        traderContact.setIsEnable(ErpConst.ONE);
        traderContact.setTraderType(ErpConst.TWO);
        List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1024);//沟通目的
        scopeList.add(SysOptionConstant.SCOP_1023);//沟通方式

        Map<Integer, List<SysOptionDefinition>> optionList = sysOptionDefinitionService.getOptionByScopeList(scopeList);

        // 客户标签
        Tag tag = new Tag();
        tag.setTagType(SysOptionConstant.ID_32);
        tag.setIsRecommend(ErpConst.ONE);
        tag.setCompanyId(user.getCompanyId());

        Integer pageNo = 1;
        Integer pageSize = 10;

        Page page = getPageTag(request, pageNo, pageSize);
        Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

        mv.addObject("traderSupplier", traderSupplier);

        mv.addObject("contactList", contactList);

        mv.addObject("communicateGoal", optionList.get(SysOptionConstant.SCOP_1024));
        mv.addObject("communicateMode", optionList.get(SysOptionConstant.SCOP_1023));

        CommunicateRecord communicate = new CommunicateRecord();
        communicate.setBegintime(DateUtil.sysTimeMillis());
        communicate.setEndtime(DateUtil.sysTimeMillis() + 2 * 60 * 1000);
        mv.addObject("communicateRecord", communicate);

        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));
        mv.addObject("method", "communicaterecord");
        mv.setViewName("trader/supplier/add_communicate");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存添加沟通
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午3:33:28
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveaddcommunicate")
    @SystemControllerLog(operationType = "add", desc = "保存新增供应商沟通记录")
    public ModelAndView saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Boolean record;
        try {
            communicateRecord.setCommunicateType(SysOptionConstant.ID_243);
            communicateRecord.setRelatedId(communicateRecord.getTraderSupplierId());
            record = traderSupplierService.saveAddCommunicate(communicateRecord, request, session);
            if (record) {
                mv.addObject("url", "./communicaterecord.do?traderId=" + communicateRecord.getTraderId() + "&traderSupplierId=" + communicateRecord.getTraderSupplierId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("saveaddcommunicate:", e);
            return fail(mv);
        }
    }

    /**
     * <b>Description:</b><br> 编辑沟通
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午3:34:10
     */
    @ResponseBody
    @RequestMapping(value = "/editcommunicate")
    public ModelAndView editCommunicate(CommunicateRecord communicateRecord, TraderSupplier traderSupplier,
                                        HttpServletRequest request, HttpSession session) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
        communicate.setTraderSupplierId(communicateRecord.getTraderSupplierId());
        communicate.setTraderId(communicateRecord.getTraderId());

        TraderContact traderContact = new TraderContact();
        //联系人
        traderContact.setTraderId(communicateRecord.getTraderId());
        traderContact.setIsEnable(ErpConst.ONE);
        traderContact.setTraderType(ErpConst.TWO);
        List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1024);//沟通目的
        scopeList.add(SysOptionConstant.SCOP_1023);//沟通方式

        Map<Integer, List<SysOptionDefinition>> optionList = sysOptionDefinitionService.getOptionByScopeList(scopeList);

        // 客户标签
        Tag tag = new Tag();
        tag.setTagType(SysOptionConstant.ID_32);
        tag.setIsRecommend(ErpConst.ONE);
        tag.setCompanyId(user.getCompanyId());

        Integer pageNo = 1;
        Integer pageSize = 10;

        Page page = getPageTag(request, pageNo, pageSize);
        Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

        mv.addObject("communicateRecord", communicate);

        mv.addObject("contactList", contactList);

        mv.addObject("communicateGoal", optionList.get(SysOptionConstant.SCOP_1024));
        mv.addObject("communicateMode", optionList.get(SysOptionConstant.SCOP_1023));

        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));
        mv.addObject("method", "communicaterecord");

        mv.addObject("traderSupplier", traderSupplier);
        try {
            mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(communicate)));
        } catch (Exception e) {
            logger.error("editcommunicate:", e);
        }
        mv.setViewName("trader/supplier/edit_communicate");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存编辑沟通
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年5月24日 下午3:36:11
     */
    @ResponseBody
    @RequestMapping(value = "/saveeditcommunicate")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑供应商沟通记录")
    public ModelAndView saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, HttpSession session, String beforeParams) {
        ModelAndView mv = new ModelAndView();
        Boolean record;
        try {
            record = traderSupplierService.saveEditCommunicate(communicateRecord, request, session);
            if (record) {
                mv.addObject("url", "./communicaterecord.do?traderId=" + communicateRecord.getTraderId() + "&traderSupplierId=" + communicateRecord.getTraderSupplierId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("saveeditcommunicate:", e);
            return fail(mv);
        }
    }

    /**
     * <b>Description:</b><br> 分配供应商
     *
     * @param request
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月5日 下午5:58:50
     */
    @ResponseBody
    @RequestMapping(value = "/assign")
    public ModelAndView assign(HttpServletRequest request,
                               @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) Integer pageSize,
                               HttpSession session) {
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 查询所有职位类型为311的员工
        List<User> userList = userService.getUserByPositType(SysOptionConstant.ID_311, session_user.getCompanyId());

        String type = request.getParameter("type");
        String traderName = request.getParameter("traderName");
        String from_user = request.getParameter("from_user");

        if (type != null) {
            if (type.equals("1")) {//单个分配查询
                from_user = "0";

                //查询集合
                Page page = getPageTag(request, pageNo, pageSize);
                RTraderJUser rTraderJUser = new RTraderJUser();
                rTraderJUser.setTraderName(traderName);
                rTraderJUser.setCompanyId(session_user.getCompanyId());
                ;

                Map<String, Object> map = traderSupplierService.getUserTraderByTraderNameListPage(rTraderJUser, page);

                mv.addObject("traderList", (List<RTraderJUser>) map.get("list"));
                mv.addObject("page", (Page) map.get("page"));
            }
            if (type.equals("2")) {//批量分配查询
                traderName = "";

                User user = userService.getUserById(Integer.parseInt(from_user));
                Integer userSupplierNum = traderSupplierService.getUserSupplierNum(Integer.parseInt(from_user));

                mv.addObject("user", user);
                mv.addObject("userSupplierNum", userSupplierNum);
            }
        }

        mv.addObject("userList", userList);
        mv.addObject("type", type);
        mv.addObject("traderName", traderName);
        mv.addObject("from_user", from_user);
        mv.setViewName("trader/supplier/assign");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存分配供应商
     *
     * @param type
     * @param traderId
     * @param single_to_user
     * @param from_user
     * @param batch_to_user
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月6日 下午1:26:39
     */
    @ResponseBody
    @RequestMapping(value = "/saveassign")
    @SystemControllerLog(operationType = "add", desc = "保存分配供应商")
    public ResultInfo<?> saveAssign(Integer type, Integer traderId, Integer single_to_user,
                                    Integer from_user, Integer batch_to_user) {
        ResultInfo<?> resultInfo = new ResultInfo<>();

        Boolean succ = false;
        if (type.equals(1)) {
            succ = traderSupplierService.assignSingleSupplier(traderId, single_to_user);
        }

        if (type.equals(2)) {
            succ = traderSupplierService.assignBatchSupplier(from_user, batch_to_user);
        }
        if (succ) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 获取当前供应商的联系人和地址
     *
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 上午9:08:41
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getContactsAddress")
    public ModelAndView getContactsAddress(HttpServletRequest request, TraderSupplier traderSupplier) {
        ModelAndView mav = new ModelAndView("trader/supplier/view_contactsaddress");
        TraderContactVo traderContactVo = new TraderContactVo();
        traderContactVo.setTraderId(traderSupplier.getTraderId());
        traderContactVo.setTraderType(ErpConst.TWO);
        Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
        String tastr = (String) map.get("contact");
        net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
        List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
        List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");

        TraderSupplier supplierInfoByTraderSupplier = traderSupplierService.getSupplierInfoByTraderSupplier(traderSupplier);
        mav.addObject("supplierInfoByTraderSupplier", supplierInfoByTraderSupplier);
        mav.addObject("contactList", list);
        mav.addObject("addressList", taList);
        mav.addObject("traderSupplier", traderSupplier);
        mav.addObject("traderId", traderSupplier.getTraderId());
        mav.addObject("method", "contactsaddress");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 初始化新增联系人页面
     *
     * @param request
     * @param traderId
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:11:47
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/toAddContactPage")
    public ModelAndView toAddContactPage(HttpServletRequest request, Integer traderId,Integer traderSupplierId) {
        ModelAndView mav = new ModelAndView("trader/supplier/add_contact");
        mav.addObject("traderId", traderId);
        mav.addObject("traderSupplierId",traderSupplierId);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 初始化编辑联系人页面
     *
     * @param request
     * @param traderId
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:11:47
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/toEditContactPage")
    public ModelAndView toEditContactPage(HttpServletRequest request, Integer traderContactId, TraderSupplier traderSupplier) {
        ModelAndView mav = new ModelAndView("trader/supplier/add_contact");
        TraderContact tc = traderCustomerService.getTraderContactById(traderContactId);
        mav.addObject("traderContact", tc);
        List<SysOptionDefinition> xgList = getSysOptionDefinitionList(SysOptionConstant.ID_4);//性格
        List<SysOptionDefinition> xlList = getSysOptionDefinitionList(SysOptionConstant.ID_3);//学历
        mav.addObject("xgList", xgList);
        mav.addObject("xlList", xlList);
        mav.addObject("method", "contactsaddress");
        mav.addObject("traderSupplierId", traderSupplier.getTraderSupplierId());
        mav.addObject("traderId",traderSupplier.getTraderId());
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 跳转到转移联系人页面
     *
     * @return
     * @throws UnsupportedEncodingException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月25日 下午5:34:13
     */
    @ResponseBody
    @RequestMapping(value = "/toTransferContactPage")
    public ModelAndView toTransferContactPage(TraderContact traderContact,TraderSupplier traderSupplier, HttpServletRequest request)
            throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView("trader/supplier/transfer_contact");
        // get 方式提交转码
        String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
        String department = URLDecoder.decode(request.getParameter("department"), "UTF-8");
        String position = URLDecoder.decode(request.getParameter("position"), "UTF-8");
        traderContact.setName(name);
        traderContact.setDepartment(department);
        traderContact.setPosition(position);
        mav.addObject("traderContact", traderContact);
        mav.addObject("traderSupplier",traderSupplier);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 初始化新增地址页面
     *
     * @param request
     * @param traderId
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:11:47
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/toAddAddressPage")
    public ModelAndView toAddAddressPage(HttpServletRequest request, Integer traderId,Integer traderSupplierId) {
        ModelAndView mav = new ModelAndView("trader/supplier/add_address");
        mav.addObject("traderId", traderId);
        mav.addObject("traderSupplierId", traderSupplierId);
        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);

        mav.addObject("provinceList", provinceList);

        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 初始化编辑地址页面
     *
     * @param request
     * @param traderId
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:11:47
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/toEditAddressPage")
    public ModelAndView toEditAddressPage(HttpServletRequest request, TraderAddress traderAddress,Integer traderSupplierId)
            throws IOException {
        ModelAndView mav = new ModelAndView("trader/supplier/add_address");
        traderAddress = traderCustomerService.getTraderAddress(traderAddress);
        mav.addObject("traderAddress", traderAddress);
        if (ObjectUtils.notEmpty(traderAddress.getAreaIds())) {
            if (traderAddress.getAreaIds().split(",").length == 3) {
                List<Region> list = regionService
                        .getRegionByParentId(Integer.valueOf(traderAddress.getAreaIds().split(",")[1]));
                mav.addObject("zoneList", list);
                List<Region> cys = regionService
                        .getRegionByParentId(Integer.valueOf(traderAddress.getAreaIds().split(",")[0]));
                mav.addObject("cityList", cys);
                mav.addObject("zone", Integer.valueOf(traderAddress.getAreaIds().split(",")[2]));
                mav.addObject("city", Integer.valueOf(traderAddress.getAreaIds().split(",")[1]));
            } else if (traderAddress.getAreaIds().split(",").length == 2) {
                List<Region> list = regionService
                        .getRegionByParentId(Integer.valueOf(traderAddress.getAreaIds().split(",")[0]));
                mav.addObject("cityList", list);
                mav.addObject("city", Integer.valueOf(traderAddress.getAreaIds().split(",")[1]));
            }
            mav.addObject("province", Integer.valueOf(traderAddress.getAreaIds().split(",")[0]));
        }
        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);

        mav.addObject("traderId", traderAddress.getTraderId());
        mav.addObject("traderSupplierId",traderSupplierId);
        mav.addObject("method", "contactsaddress");
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderAddress)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存地址
     *
     * @param session
     * @param traderAddress
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月27日 上午11:09:54
     */
    //@FormToken(remove=true)
    @ResponseBody
    @RequestMapping(value = "/saveAddress")
    @SystemControllerLog(operationType = "add", desc = "保存新增供应商地址")
    public ResultInfo saveAddress(HttpSession session, TraderAddress traderAddress, Integer province, Integer city,
                                  Integer zone, String beforeParams) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        if (zone != 0) {
            traderAddress.setAreaId(zone);
            traderAddress.setAreaIds(province + "," + city + "," + zone);
        } else if (zone == 0 && city != 0) {
            traderAddress.setAreaId(city);
            traderAddress.setAreaIds(province + "," + city);
        }
        ResultInfo res = traderCustomerService.saveTraderAddress(traderAddress, user);
        return res;
    }

    /**
     * <b>Description:</b><br>
     * 新增保存联系人
     *
     * @param request
     * @param traderContact
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:13:43
     */
    @FormToken(remove = true)
    @SuppressWarnings("all")
    @ResponseBody
    @RequestMapping(value = "/addSaveContact")
    @SystemControllerLog(operationType = "add", desc = "新增保存联系人")
    public ResultInfo addSaveContact(HttpSession session, TraderSupplier traderSupplier, TraderContact traderContact) throws IOException {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Integer traderContactId = traderCustomerService.saveTraderContact(traderContact, user);
        if (traderContactId > 0) {
            return new ResultInfo(0, "操作成功", traderSupplier.getTraderSupplierId() + "," + traderSupplier.getTraderId() + "," + traderContactId);
        } else if (traderContactId == -1) {
            return new ResultInfo(-1, "该供应商已存在相同联系人");
        } else {
            return new ResultInfo(1, "操作失败");
        }
    }

    /**
     * <b>Description:</b><br>
     * 编辑保存联系人
     *
     * @param request
     * @param traderContact
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:13:43
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/editSaveContact")
    @SystemControllerLog(operationType = "edit", desc = "编辑保存联系人")
    public ModelAndView editSaveContact(HttpServletRequest request, HttpSession session, HttpServletResponse response,
                                        TraderContact traderContact, TraderSupplier traderSupplier, String beforeParams) throws IOException {
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        if (null != traderContact.getTraderContactId()) {
            String[] sg = request.getParameterValues("xg");
            StringBuffer sb = new StringBuffer();
            if (null != sg && sg.length > 0) {
                for (String str : sg) {
                    sb.append(str).append(",");
                }
                traderContact.setCharacter(sb.toString());
            }
        }
        Integer traderContactId = traderCustomerService.saveTraderContact(traderContact, user);
        if (traderContactId > 0) {
            mav.addObject("url", "./getContactsAddress.do?traderId=" + traderContact.getTraderId() + "&&traderSupplierId=" + traderSupplier.getTraderSupplierId());
            return success(mav);
        } else if (traderContactId == -1) {
            List<ObjectError> allErrors = new ArrayList<>();
            allErrors.add(new ObjectError("1", "该客户已存在相同的联系人"));
            request.setAttribute("allErrors", allErrors);
            return toEditContactPage(request, traderContact.getTraderContactId(), traderSupplier);
        } else {
            return fail(mav);
        }

    }

    /**
     * <b>Description:</b><br>
     * 转移联系人页面搜索供应商
     *
     * @param name
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月26日 下午1:23:07
     */
    @ResponseBody
    @RequestMapping(value = "/getSupplisersByName")
    public ModelAndView getSupplisersByName(TraderContact traderContact, String supplierName, HttpServletRequest request,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView("trader/supplier/transfer_contact");
        Page page = getPageTag(request, pageNo, 10);
        // 查询所有职位类型为311的员工
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_311);//采购
        List<User> userList = userService.getMyUserList(user, positionType, false);
        //List<Integer> traderList = userService.getTraderIdListByUserList(userList,ErpConst.ONE.toString());
        TraderSupplierVo tsv = new TraderSupplierVo();
        //tsv.setTraderList(traderList);
        tsv.setTraderSupplierName(supplierName);
        Map<String, Object> map = traderSupplierService.getTraderSupplierList(tsv, page, userList);
        List<TraderSupplierVo> list = (List<TraderSupplierVo>) map.get("list");
        page = (Page) map.get("page");
        mav.addObject("list", list);
        mav.addObject("page", page);
        mav.addObject("supplierName", supplierName);
        mav.addObject("traderContact", traderContact);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 转移联系人
     *
     * @param traderContact
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月26日 下午5:38:55
     */
    @ResponseBody
    @RequestMapping(value = "/transferContact")
    @SystemControllerLog(operationType = "edit", desc = "转移联系人")
    public ResultInfo transferContact(TraderContact traderContact, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo res = traderCustomerService.transferContact(traderContact, user);
        return res;
    }

    /**
     * <b>Description:</b><br>
     * 获取财务与资质信息
     *
     * @param traderId
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月31日 上午10:08:19
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getFinanceAndAptitude")
    public ModelAndView getFinanceAndAptitude(HttpServletRequest request, TraderSupplier traderSupplier) {
        ModelAndView mav = new ModelAndView("trader/supplier/view_financeAndAptitude");
        mav.addObject("traderSupplier", traderSupplier);
        TraderCertificateVo tc = new TraderCertificateVo();
        tc.setTraderId(traderSupplier.getTraderId());
        tc.setTraderType(ErpConst.TWO);
        Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(tc, "all");
        List<TraderCertificateVo> bus = null;
        // 营业执照信息
        if (map.containsKey("business")) {
//			JSONObject json=JSONObject.fromObject(map.get("business"));
//			bus=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("business", bus);
            bus = (List<TraderCertificateVo>) map.get("business");
            if (!CollectionUtils.isEmpty(bus)) {
                mav.addObject("business", bus.get(0));
            }
        }
        // 税务登记信息
        TraderCertificateVo tax = null;
        if (map.containsKey("tax")) {
            JSONObject json = JSONObject.fromObject(map.get("tax"));
            tax = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("tax", tax);
        }
        // 组织机构信息
        TraderCertificateVo orga = null;
        if (map.containsKey("orga")) {
            JSONObject json = JSONObject.fromObject(map.get("orga"));
            orga = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("orga", orga);
        }
        // 二类医疗资质
        List<TraderCertificateVo> twoMedicalList = null;
        if (map.containsKey("twoMedical")) {
            //JSONObject json=JSONObject.fromObject(map.get("twoMedical"));
            twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
            mav.addObject("twoMedicalList", twoMedicalList);
        }
        // 三类医疗资质
        List<TraderCertificateVo> threeMedical = null;
        if (map.containsKey("threeMedical")) {
//			JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
            threeMedical = (List<TraderCertificateVo>) map.get("threeMedical");
            if (!CollectionUtils.isEmpty(threeMedical)) {
                mav.addObject("threeMedical", threeMedical.get(0));
            }

        }
        List<TraderMedicalCategoryVo> two = null;
        if (map.containsKey("two")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("two"));
            two = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("two", two);
        }
        List<TraderMedicalCategoryVo> three = null;
        if (map.containsKey("three")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("three"));
            three = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("three", three);
        }
        // 医疗器械生产许可证
        TraderCertificateVo product = null;
        if (map.containsKey("product")) {
            JSONObject json = JSONObject.fromObject(map.get("product"));
            product = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("product", product);
        }
        // 医疗器械经营许可证
        TraderCertificateVo operate = null;
        if (map.containsKey("operate")) {
            JSONObject json = JSONObject.fromObject(map.get("operate"));
            operate = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("operate", operate);
        }
        // begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21
        // 销售授权书与授权销售人
        TraderCertificateVo saleAuth = null;
        if (map.containsKey("saleAuth")) {
            JSONObject json = JSONObject.fromObject(map.get("saleAuth"));
            saleAuth = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("saleAuth", saleAuth);
        }
        // end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21

        //品牌授权书
        List<TraderCertificateVo> brandBookList = null;
        if (map.containsKey("brandBookList")) {
            brandBookList = (List<TraderCertificateVo>) map.get("brandBookList");
            if (brandBookList.size() == 0) {
                brandBookList = null;
            }
            mav.addObject("brandBookList", brandBookList);
        }
        //其他
        List<TraderCertificateVo> otherList = null;
        if (map.containsKey("otherList")) {
            otherList = (List<TraderCertificateVo>) map.get("otherList");
            mav.addObject("otherList", otherList);
        }
        // 财务信息
        TraderFinanceVo tf = null;
        if (map.containsKey("finance")) {
            JSONObject json = JSONObject.fromObject(map.get("finance"));
            tf = (TraderFinanceVo) JSONObject.toBean(json, TraderFinanceVo.class);
            if (tf != null && ObjectUtils.notEmpty(tf.getAverageTaxpayerUri())) {
                tf.setAverageTaxpayerDomain(domain);
            }
            mav.addObject("finance", tf);
        }
        //帐期列表
        if (map.containsKey("billList")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("billList"));
            List<TraderAccountPeriodApply> billList = (List<TraderAccountPeriodApply>) JSONArray.toCollection(jsonArray, TraderAccountPeriodApply.class);
            mav.addObject("billList", billList);
        }

        //银行帐号列表
        if (map.containsKey("bankList")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("bankList"));
            List<TraderFinance> bankList = (List<TraderFinance>) JSONArray.toCollection(jsonArray, TraderFinance.class);
            mav.addObject("bankList", bankList);
        }
        //TraderSupplierVo supplierInfoByTraderSupplier = traderSupplierService.getSupplierInfoByTraderSupplier(traderSupplier);
        if (map.containsKey("supplier")) {
            JSONObject json = JSONObject.fromObject(map.get("supplier"));
            TraderSupplierVo supplier = (TraderSupplierVo) JSONObject.toBean(json, TraderSupplierVo.class);
            mav.addObject("supplierInfoByTraderSupplier", supplier);
        }
        //mav.addObject("supplierInfoByTraderSupplier", supplierInfoByTraderSupplier);
        mav.addObject("method", "financeandaptitude");
        return mav;
    }

    /**
     * <b>Description:</b><br> 获取交易者的账期分页信息
     *
     * @param request
     * @param traderCustomer
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月10日 下午4:09:21
     */
    @ResponseBody
    @RequestMapping(value = "/getAmountBillPage")
    public ModelAndView getAmountBillPage(HttpServletRequest request, TraderVo traderVo,
                                          @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(required = false) Integer pageSize) {
        ModelAndView mav = new ModelAndView("trader/supplier/amount_bill_page");
        Page page = getPageTag(request, pageNo, 10);
        Map<String, Object> map = traderCustomerService.getAmountBillListPage(traderVo, page);
        //帐期记录
        if (map.containsKey("list")) {
            List<TraderAmountBillVo> billList = (List<TraderAmountBillVo>) map.get("list");
            mav.addObject("billList", billList);
        }
        if (map.containsKey("page")) {
            page = (Page) map.get("page");
            mav.addObject("page", page);
        }
        mav.addObject("trader", traderVo);
        return mav;
    }

    /**
     * <b>Description:</b><br> 获取交易者的交易流水分页信息
     *
     * @param request
     * @param traderCustomer
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年11月10日 下午4:09:21
     */
    @ResponseBody
    @RequestMapping(value = "/getCapitalBillPage")
    public ModelAndView getCapitaltBillPage(HttpServletRequest request, TraderVo traderVo,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                            @RequestParam(required = false) Integer pageSize) {
        ModelAndView mav = new ModelAndView("trader/supplier/capital_bill_page");
        Page page = getPageTag(request, pageNo, 10);
        Map<String, Object> map = traderCustomerService.getCapitalBillListPage(traderVo, page);
        //资金流水
        if (map.containsKey("list")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("list"));
            List<CapitalBill> capitalBillList = (List<CapitalBill>) JSONArray.toCollection(jsonArray, CapitalBill.class);
            mav.addObject("capitalBill", capitalBillList);
        }
        if (map.containsKey("page")) {
            page = (Page) map.get("page");
            mav.addObject("page", page);
        }
        mav.addObject("trader", traderVo);
        return mav;
    }

    /**
     * <b>Description:</b>初始化编辑供应商资质部分<br>
     *
     * @param
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/4/2
     */
    private void initEditAptitudePart(ModelAndView mav, TraderSupplier traderSupplier) {
        TraderCertificateVo traderCertificate = new TraderCertificateVo();
        traderCertificate.setTraderId(traderSupplier.getTraderId());
        traderCertificate.setTraderType(ErpConst.TWO);
        mav.addObject("traderSupplier1", traderSupplier);
        //Page page = Page.newBuilder(null, null, null);
        Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(traderCertificate, "zz");
        TraderCertificateVo tc = null;
        StringBuffer sb = new StringBuffer();

        // 营业执照信息
        List<TraderCertificateVo> bus = null;
        // 营业执照信息
        if (map.containsKey("business")) {
//			JSONObject json=JSONObject.fromObject(map.get("business"));
//			bus=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("business", bus);
            bus = (List<TraderCertificateVo>) map.get("business");
            if (!CollectionUtils.isEmpty(bus)) {
                mav.addObject("business", bus.get(0));
            }
        }
        // 税务登记信息
        if (map.containsKey("tax")) {
            JSONObject json = JSONObject.fromObject(map.get("tax"));
            tc = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("tax", tc);
        }
        // 组织机构信息
        if (map.containsKey("orga")) {
            JSONObject json = JSONObject.fromObject(map.get("orga"));
            tc = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("orga", tc);

        }
        // 二类医疗资质
        List<TraderCertificateVo> twoMedicalList = null;
        if (map.containsKey("twoMedical")) {
            twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
            mav.addObject("twoMedicalList", twoMedicalList);
        }
        // 三类医疗资质
        if (map.containsKey("threeMedical")) {
//			JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
//			tc=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("threeMedical", tc);
//			sb=sb.append(JsonUtils.translateToJson(tc));

            List<TraderCertificateVo> threeMedical = (List<TraderCertificateVo>) map.get("threeMedical");
            if (!CollectionUtils.isEmpty(threeMedical)) {
                mav.addObject("threeMedical", threeMedical.get(0));
            }
        }
        List<TraderMedicalCategoryVo> two = null;
        if (map.containsKey("two")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("two"));
            two = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("two", two);
        }
        List<TraderMedicalCategoryVo> three = null;
        if (map.containsKey("three")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("three"));
            three = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("three", three);
        }
        // 医疗器械生产许可证
        TraderCertificateVo product = null;
        if (map.containsKey("product")) {
            JSONObject json = JSONObject.fromObject(map.get("product"));
            product = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("product", product);
        }

        // 医疗器械经营许可证
        TraderCertificateVo operate = null;
        if (map.containsKey("operate")) {
            JSONObject json = JSONObject.fromObject(map.get("operate"));
            operate = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("operate", operate);
        }
        // 销售授权书 与 授权销售人
        TraderCertificateVo saleAuth = null;
        if (map.containsKey("saleAuth")) {
            JSONObject json = JSONObject.fromObject(map.get("saleAuth"));
            saleAuth = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("saleAuth", saleAuth);
        }
        // 品牌授权书
        List<TraderCertificateVo> brandBookList = null;
        if (map.containsKey("brandBookList")) {
            brandBookList = (List<TraderCertificateVo>) map.get("brandBookList");
            mav.addObject("brandBookList", brandBookList);
        }
        //其他证书
        List<TraderCertificateVo> otherList = null;
        if (map.containsKey("otherList")) {
            otherList = (List<TraderCertificateVo>) map.get("otherList");
            mav.addObject("otherList", otherList);
        }
        // 医疗类别
        List<SysOptionDefinition> medicalTypes = getSysOptionDefinitionList(SysOptionConstant.ID_20);

        mav.addObject("medicalTypes", medicalTypes);
        // 医疗类别级别
        List<SysOptionDefinition> medicalTypLevels = getSysOptionDefinitionList(SysOptionConstant.ID_21);

        mav.addObject("medicalTypLevels", medicalTypLevels);
        mav.addObject("domain", domain);
    }

    /**
     * <b>Description:</b><br>
     * 初始化编辑资质页面
     *
     * @param traderId
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月31日 下午3:51:00
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/editAptitude")
    public ModelAndView editAptitude(TraderSupplier traderSupplier) throws IOException {
        ModelAndView mav = new ModelAndView("trader/supplier/edit_aptitude");
        TraderCertificateVo traderCertificate = new TraderCertificateVo();
        traderCertificate.setTraderId(traderSupplier.getTraderId());
        traderCertificate.setTraderType(ErpConst.TWO);
        mav.addObject("traderSupplier", traderSupplier);
        //Page page = Page.newBuilder(null, null, null);
        Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(traderCertificate, "zz");
        TraderCertificateVo tc = null;
        StringBuffer sb = new StringBuffer();

        // 营业执照信息
        List<TraderCertificateVo> bus = null;
        // 营业执照信息
        if (map.containsKey("business")) {
//			JSONObject json=JSONObject.fromObject(map.get("business"));
//			bus=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("business", bus);
            bus = (List<TraderCertificateVo>) map.get("business");
            if (!CollectionUtils.isEmpty(bus)) {
                mav.addObject("business", bus.get(0));
            }
        }
        // 税务登记信息
        if (map.containsKey("tax")) {
            JSONObject json = JSONObject.fromObject(map.get("tax"));
            tc = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("tax", tc);
            sb = sb.append(JsonUtils.translateToJson(tc));
        }
        // 组织机构信息
        if (map.containsKey("orga")) {
            JSONObject json = JSONObject.fromObject(map.get("orga"));
            tc = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("orga", tc);
            sb = sb.append(JsonUtils.translateToJson(tc));
        }
        // 二类医疗资质
        List<TraderCertificateVo> twoMedicalList = null;
        if (map.containsKey("twoMedical")) {
            twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
            mav.addObject("twoMedicalList", twoMedicalList);
            sb = sb.append(JsonUtils.translateToJson(tc));
        }
        // 三类医疗资质
        if (map.containsKey("threeMedical")) {
//			JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
//			tc=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("threeMedical", tc);
//			sb=sb.append(JsonUtils.translateToJson(tc));

            List<TraderCertificateVo> threeMedical = (List<TraderCertificateVo>) map.get("threeMedical");
            if (!CollectionUtils.isEmpty(threeMedical)) {
                mav.addObject("threeMedical", threeMedical.get(0));
            }
        }
        List<TraderMedicalCategoryVo> two = null;
        if (map.containsKey("two")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("two"));
            two = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("two", two);
            sb = sb.append(JsonUtils.translateToJson(two));
        }
        List<TraderMedicalCategoryVo> three = null;
        if (map.containsKey("three")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("three"));
            three = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
            mav.addObject("three", three);
            sb = sb.append(JsonUtils.translateToJson(three));
        }
        // 医疗器械生产许可证
        TraderCertificateVo product = null;
        if (map.containsKey("product")) {
            JSONObject json = JSONObject.fromObject(map.get("product"));
            product = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("product", product);
            sb = sb.append(JsonUtils.translateToJson(product));
        }

        // 医疗器械经营许可证
        TraderCertificateVo operate = null;
        if (map.containsKey("operate")) {
            JSONObject json = JSONObject.fromObject(map.get("operate"));
            operate = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("operate", operate);
            sb = sb.append(JsonUtils.translateToJson(operate));
        }
        // 销售授权书 与 授权销售人
        TraderCertificateVo saleAuth = null;
        if (map.containsKey("saleAuth")) {
            JSONObject json = JSONObject.fromObject(map.get("saleAuth"));
            saleAuth = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            mav.addObject("saleAuth", saleAuth);
            sb = sb.append(JsonUtils.translateToJson(saleAuth));
        }
        // 品牌授权书
        List<TraderCertificateVo> brandBookList = null;
        if (map.containsKey("brandBookList")) {
            brandBookList = (List<TraderCertificateVo>) map.get("brandBookList");
            mav.addObject("brandBookList", brandBookList);
            sb = sb.append(JsonUtils.translateToJson(brandBookList));
        }
        //其他证书
        List<TraderCertificateVo> otherList = null;
        if (map.containsKey("otherList")) {
            otherList = (List<TraderCertificateVo>) map.get("otherList");
            mav.addObject("otherList", otherList);
            sb = sb.append(JsonUtils.translateToJson(otherList));
        }
        // 医疗类别
        List<SysOptionDefinition> medicalTypes = getSysOptionDefinitionList(SysOptionConstant.ID_20);

        mav.addObject("medicalTypes", medicalTypes);
        // 医疗类别级别
        List<SysOptionDefinition> medicalTypLevels = getSysOptionDefinitionList(SysOptionConstant.ID_21);

        mav.addObject("medicalTypLevels", medicalTypLevels);
        mav.addObject("domain", domain);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(sb.toString())));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存供应商资质
     *
     * @return
     * @throws CloneNotSupportedException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:08:16
     */
    @ResponseBody
    @RequestMapping(value = "saveAptitude")
    @SystemControllerLog(operationType = "edit", desc = "保存客户资质")
    public ModelAndView saveAptitude(HttpServletRequest request, TraderVo traderVo, TraderSupplier traderSupplier, String beforeParams) throws CloneNotSupportedException {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        List<TraderCertificateVo> list = new LinkedList<TraderCertificateVo>();
        String threeInOne = request.getParameter("threeInOne");
        String medicalQualification = request.getParameter("medicalQualification");

        if (threeInOne != null && Integer.valueOf(threeInOne) == 1) {// 三证合一
            //营业执照
            TraderCertificateVo bus = saveBussiness(request);
            if (bus != null) {
                bus.setThreeInOne(1);
                list.add(bus);
                //税务登记
                TraderCertificateVo tax = (TraderCertificateVo) bus.clone();
//				String taxTraderCertificateId = request.getParameter("taxTraderCertificateId");
//				if(null != taxTraderCertificateId && !"".equals(taxTraderCertificateId)){
//					tax.setTraderCertificateId(Integer.valueOf(taxTraderCertificateId));
//				}
                tax.setThreeInOne(1);
                tax.setSysOptionDefinitionId(SysOptionConstant.ID_26);
                list.add(tax);
                //组织机构证
                TraderCertificateVo org = (TraderCertificateVo) bus.clone();
                org.setSysOptionDefinitionId(SysOptionConstant.ID_27);
                org.setThreeInOne(1);
//				String orgaTraderCertificateId = request.getParameter("orgaTraderCertificateId");
//				if(null != orgaTraderCertificateId && !"".equals(orgaTraderCertificateId)){
//					org.setTraderCertificateId(Integer.valueOf(orgaTraderCertificateId));
//				}
                list.add(org);
            }

        } else {
            TraderCertificateVo bus = saveBussiness(request);
            if (bus != null) {
                bus.setThreeInOne(0);
                list.add(bus);
            }
            TraderCertificateVo tax = saveTax(request);
            if (tax != null) {
                tax.setThreeInOne(0);
                list.add(tax);
            }
            TraderCertificateVo org = saveOrg(request);
            if (org != null) {
                org.setThreeInOne(0);
                list.add(org);
            }

        }
        List<TraderMedicalCategory> twomcList = null;
        List<TraderMedicalCategory> threemcList = null;
        if (medicalQualification != null && Integer.valueOf(medicalQualification) == 1) {// 医疗资质合一
            TraderCertificateVo two = saveTwoMedical(request);
            if (two != null) {
                two.setMedicalQualification(1);
                list.add(two);
                TraderCertificateVo three = (TraderCertificateVo) two.clone();
                three.setSysOptionDefinitionId(SysOptionConstant.ID_29);
                three.setMedicalQualification(1);
                list.add(three);
                //根据资质类别的数量获取资质类别所属的分类
                twomcList = saveMedicalAptitudes(request);
                threemcList = saveThreeMedicalAptitudes(request);
            }

        } else {
            TraderCertificateVo two = saveTwoMedical(request);
            if (two != null) {
                two.setMedicalQualification(0);
                list.add(two);
                //根据资质类别的数量获取资质类别所属的分类
                twomcList = saveMedicalAptitudes(request);
            }
            TraderCertificateVo three = saveThreeMedical(request);
            if (three != null) {
                three.setMedicalQualification(0);
                list.add(three);
                threemcList = saveThreeMedicalAptitudes(request);
            }
        }

        TraderCertificateVo product = saveProduct(request);
        if (product != null) {
            list.add(product);
        }

        TraderCertificateVo operate = saveOperate(request);
        if (operate != null) {
            list.add(operate);
        }

        // begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21

        TraderCertificateVo saleAuth = saveSaleAuth(request, user);
        if (null != saleAuth) {
            list.add(saleAuth);
        }
        // end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21

//		if(list.size()==0&&tcmcList==null){
//			ResultInfo res =new ResultInfo(2, "请至少填写/选择一项数据");
//			return res;
//		}

        TraderCertificateVo brandBook = saveBrandBook(request);//获取品牌授权书
        if (brandBook != null) {
            list.add(brandBook);
        }

        TraderCertificateVo theOther = saveTheOther(request);//获取其他资格证书
        if (theOther != null) {
            list.add(theOther);
        }
        ResultInfo res = traderCustomerService.saveMedicaAptitude(traderVo, list, twomcList, threemcList);
        ModelAndView mav = new ModelAndView();
        if (res != null && res.getCode() == 0) {
            mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
            //mav.addObject("url", "./getFinanceAndAptitude.do??traderId="+traderSupplier.getTraderId()+"&traderCustomerId="+traderCustomer.getTraderCustomerId());
            return success(mav);
        } else {
            return fail(new ModelAndView());
        }
    }

    /**
     * <b>Description:</b><br>
     * 获取二类医疗资质信息
     *
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午4:10:55
     */
    private List<TraderMedicalCategory> saveMedicalAptitudes(HttpServletRequest request) {
        String medicalTypes[] = request.getParameterValues("twoMedicalType");
        String traderId = request.getParameter("traderId");
        String traderType = request.getParameter("traderType");
        TraderMedicalCategory tcac = null;
        if (medicalTypes.length > 0) {
            List<TraderMedicalCategory> list = new ArrayList<>();
            for (int i = 0; i < medicalTypes.length; i++) {
                if (ObjectUtils.notEmpty(medicalTypes[i])) {
                    tcac = new TraderMedicalCategory();
                    tcac.setTraderId(Integer.valueOf(traderId));
                    tcac.setTraderType(Integer.valueOf(traderType));

                    tcac.setMedicalCategoryId(Integer.valueOf(medicalTypes[i]));

                    tcac.setMedicalCategoryLevel(239);

                    list.add(tcac);
                }
            }
            return list;
        } else {
            return null;
        }

    }

    /**
     * <b>Description:</b><br>
     * 获取三类医疗资质信息
     *
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午4:10:55
     */
    private List<TraderMedicalCategory> saveThreeMedicalAptitudes(HttpServletRequest request) {
        String medicalTypes[] = request.getParameterValues("threeMedicalType");
        String traderId = request.getParameter("traderId");
        String traderType = request.getParameter("traderType");
        TraderMedicalCategory tcac = null;
        if (medicalTypes.length > 0) {
            List<TraderMedicalCategory> list = new ArrayList<>();
            for (int i = 0; i < medicalTypes.length; i++) {
                if (ObjectUtils.notEmpty(medicalTypes[i])) {
                    tcac = new TraderMedicalCategory();
                    tcac.setTraderId(Integer.valueOf(traderId));
                    tcac.setTraderType(Integer.valueOf(traderType));
                    tcac.setMedicalCategoryId(Integer.valueOf(medicalTypes[i]));
                    tcac.setMedicalCategoryLevel(240);
                    list.add(tcac);
                }
            }
            return list;
        } else {
            return null;
        }

    }

    /**
     * <b>Description:</b><br>
     * 保存营业执照
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveBussiness(HttpServletRequest request) {
        String busStartTime = request.getParameter("busStartTime");
        if (ObjectUtils.isEmpty(busStartTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        //String busTraderCertificateId = request.getParameter("busTraderCertificateId");
        String traderId = request.getParameter("traderId");

        // 营业执照
        TraderCertificateVo bus = new TraderCertificateVo();

        String busName = request.getParameter("busName");
        if (ObjectUtils.notEmpty(busName)) {
            String busUri = request.getParameter("busUri");
            bus.setName(busName);
            bus.setUri(busUri);
        }

        bus.setCreator(user.getUserId());
        bus.setAddTime(System.currentTimeMillis());
        bus.setUpdater(user.getUserId());
        bus.setModTime(System.currentTimeMillis());

        bus.setTraderId(Integer.valueOf(traderId));
        bus.setTraderType(2);
        bus.setSysOptionDefinitionId(SysOptionConstant.ID_25);// 营业执照
        String isMedical = request.getParameter("isMedical");
        if (ObjectUtils.notEmpty(isMedical)) {
            bus.setIsMedical(1);
        } else {
            bus.setIsMedical(0);
        }

        if (ObjectUtils.notEmpty(busStartTime)) {
            bus.setBegintime(DateUtil.StringToDate(busStartTime).getTime());
        }
        String busEndTime = request.getParameter("busEndTime");
        if (ObjectUtils.notEmpty(busEndTime)) {
            bus.setEndtime(DateUtil.convertLong(busEndTime + " 23:59:59", null));
        }
        return bus;
    }

    /**
     * <b>Description:</b><br>
     * 保存税务登记证
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveTax(HttpServletRequest request) {
        String taxStartTime = request.getParameter("taxStartTime");
        if (ObjectUtils.isEmpty(taxStartTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String taxTraderCertificateId = request.getParameter("taxTraderCertificateId");
        String traderId = request.getParameter("traderId");
        String taxName = request.getParameter("taxName");

        String taxEndTime = request.getParameter("taxEndTime");
        // 税务登记证
        TraderCertificateVo tax = new TraderCertificateVo();
        tax.setCreator(user.getUserId());
        tax.setAddTime(System.currentTimeMillis());
        tax.setUpdater(user.getUserId());
        tax.setModTime(System.currentTimeMillis());

        tax.setTraderId(Integer.valueOf(traderId));
        tax.setTraderType(2);
        tax.setSysOptionDefinitionId(SysOptionConstant.ID_26);// 税务登记证
        tax.setIsMedical(0);
        if (ObjectUtils.notEmpty(taxName)) {
            String taxUri = request.getParameter("taxUri");
            tax.setUri(taxUri);
            tax.setName(taxName);
        }
        if (ObjectUtils.notEmpty(taxStartTime)) {
            tax.setBegintime(DateUtil.StringToDate(taxStartTime).getTime());
        }
        if (ObjectUtils.notEmpty(taxEndTime)) {
            tax.setEndtime(DateUtil.convertLong(taxEndTime + " 23:59:59", null));
        }
        return tax;
    }

    /**
     * <b>Description:</b><br>
     * 保存组织机构代码证
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveOrg(HttpServletRequest request) {
        String orgaStartTime = request.getParameter("orgaStartTime");
        if (ObjectUtils.isEmpty(orgaStartTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String orgaTraderCertificateId = request.getParameter("orgaTraderCertificateId");
        String traderId = request.getParameter("traderId");

        String orgName = request.getParameter("orgName");
        String orgaEndTime = request.getParameter("orgaEndTime");
        // 组织机构代码证
        TraderCertificateVo org = new TraderCertificateVo();
        org.setCreator(user.getUserId());
        org.setAddTime(System.currentTimeMillis());
        org.setUpdater(user.getUserId());
        org.setModTime(System.currentTimeMillis());

        org.setTraderId(Integer.valueOf(traderId));
        org.setTraderType(2);
        org.setSysOptionDefinitionId(SysOptionConstant.ID_27);// 组织机构代码证
        org.setIsMedical(0);
        if (ObjectUtils.notEmpty(orgName)) {
            String orgUri = request.getParameter("orgaUri");
            org.setUri(orgUri);
            org.setName(orgName);
        }
        if (ObjectUtils.notEmpty(orgaStartTime)) {
            org.setBegintime(DateUtil.StringToDate(orgaStartTime).getTime());
        }
        if (ObjectUtils.notEmpty(orgaEndTime)) {
            org.setEndtime(DateUtil.convertLong(orgaEndTime + " 23:59:59", null));
        }
        return org;
    }

    /**
     * <b>Description:</b><br>
     * 保存二类医疗资质
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveTwoMedical(HttpServletRequest request) {
        String twoStartTime = request.getParameter("twoStartTime");
        if (ObjectUtils.isEmpty(twoStartTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String twoTraderCertificateId = request.getParameter("twoTraderCertificateId");
        String traderId = request.getParameter("traderId");
        String twoName = request.getParameter("twoName");
        String otherPicName = request.getParameter("name_4");//多张图片的URI

        String twoEndTime = request.getParameter("twoEndTime");
        String twoSn = request.getParameter("twoSn");
        // 二类医疗资质
        TraderCertificateVo two = new TraderCertificateVo();
        two.setCreator(user.getUserId());
        two.setAddTime(System.currentTimeMillis());
        two.setUpdater(user.getUserId());
        two.setModTime(System.currentTimeMillis());

        two.setSn(twoSn);
        two.setTraderId(Integer.valueOf(traderId));
        two.setTraderType(2);
        two.setSysOptionDefinitionId(SysOptionConstant.ID_28);
        two.setIsMedical(0);
        if (ObjectUtils.notEmpty(twoName)) {
            String twoUri = request.getParameter("twoUri");
            two.setUri(twoUri);
            two.setName(twoName);
        }
        //判断是否有多张图片
        if (ObjectUtils.notEmpty(otherPicName)) {
            String[] uris = request.getParameterValues("uri_4");
            two.setUris(uris);
        }
        if (ObjectUtils.notEmpty(twoStartTime)) {
            two.setBegintime(DateUtil.StringToDate(twoStartTime).getTime());
        }
        if (ObjectUtils.notEmpty(twoEndTime)) {
            two.setEndtime(DateUtil.convertLong(twoEndTime + " 23:59:59", null));
        }
        return two;
    }

    /**
     * <b>Description:</b><br>
     * 保存三类医疗资质
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveThreeMedical(HttpServletRequest request) {
        String threeStartTime = request.getParameter("threeStartTime");
        if (ObjectUtils.isEmpty(threeStartTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String threeTraderCertificateId = request.getParameter("threeTraderCertificateId");
        String traderId = request.getParameter("traderId");

        String threeName = request.getParameter("threeName");

        String threeEndTime = request.getParameter("threeEndTime");
        String threeSn = request.getParameter("threeSn");
        // 三类医疗资质
        TraderCertificateVo three = new TraderCertificateVo();

        three.setCreator(user.getUserId());
        three.setAddTime(System.currentTimeMillis());
        three.setUpdater(user.getUserId());
        three.setModTime(System.currentTimeMillis());

        three.setSn(threeSn);
        three.setTraderId(Integer.valueOf(traderId));
        three.setTraderType(2);
        three.setSysOptionDefinitionId(SysOptionConstant.ID_29);
        three.setIsMedical(0);
        if (ObjectUtils.notEmpty(threeName)) {
            String threeUri = request.getParameter("threeUri");
            three.setUri(threeUri);
            three.setName(threeName);
        }

        if (ObjectUtils.notEmpty(threeStartTime)) {
            three.setBegintime(DateUtil.StringToDate(threeStartTime).getTime());
        }
        if (ObjectUtils.notEmpty(threeEndTime)) {
            three.setEndtime(DateUtil.convertLong(threeEndTime + " 23:59:59", null));
        }
        return three;
    }

    /**
     * <b>Description:</b><br>
     * 保存医疗机构执业许可证
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo savePractice(HttpServletRequest request) {
        String practiceTime = request.getParameter("practiceStartTime");
        if (ObjectUtils.isEmpty(practiceTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String practiceTraderCertificateId = request.getParameter("practiceTraderCertificateId");
        String traderId = request.getParameter("traderId");

        String practiceName = request.getParameter("practiceName");
        String practiceEndTime = request.getParameter("practiceEndTime");
        String practiceSn = request.getParameter("practiceSn");
        // 医疗机构执业许可证
        TraderCertificateVo practice = new TraderCertificateVo();
        practice.setCreator(user.getUserId());
        practice.setAddTime(System.currentTimeMillis());
        practice.setUpdater(user.getUserId());
        practice.setModTime(System.currentTimeMillis());

        practice.setSn(practiceSn);
        practice.setTraderId(Integer.valueOf(traderId));
        practice.setTraderType(2);
        practice.setSysOptionDefinitionId(SysOptionConstant.ID_438);
        if (ObjectUtils.notEmpty(practiceTime)) {
            String practiceUri = request.getParameter("practiceUri");
            practice.setUri(practiceUri);
            practice.setName(practiceName);
        }

        if (ObjectUtils.notEmpty(practiceTime)) {
            practice.setBegintime(DateUtil.StringToDate(practiceTime).getTime());
        }
        if (ObjectUtils.notEmpty(practiceEndTime)) {
            practice.setEndtime(DateUtil.convertLong(practiceEndTime + " 23:59:59", null));
        }
        return practice;
    }

    /**
     * <b>Description:</b><br>
     * 保存医疗器械生产许可证
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveProduct(HttpServletRequest request) {
        String productTime = request.getParameter("productStartTime");
        if (ObjectUtils.isEmpty(productTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String productTraderCertificateId = request.getParameter("productTraderCertificateId");
        String traderId = request.getParameter("traderId");

        String productName = request.getParameter("productName");
        String productEndTime = request.getParameter("productEndTime");
        String productSn = request.getParameter("productSn");
        // 医疗器械生产许可证
        TraderCertificateVo product = new TraderCertificateVo();
        product.setCreator(user.getUserId());
        product.setAddTime(System.currentTimeMillis());
        product.setUpdater(user.getUserId());
        product.setModTime(System.currentTimeMillis());

        product.setSn(productSn);
        product.setTraderId(Integer.valueOf(traderId));
        product.setTraderType(2);
        product.setSysOptionDefinitionId(SysOptionConstant.ID_439);
        if (ObjectUtils.notEmpty(productName)) {
            String productUri = request.getParameter("productUri");
            product.setUri(productUri);
            product.setName(productName);
        }
        if (ObjectUtils.notEmpty(productTime)) {
            product.setBegintime(DateUtil.StringToDate(productTime).getTime());
        }
        if (ObjectUtils.notEmpty(productEndTime)) {
            product.setEndtime(DateUtil.convertLong(productEndTime + " 23:59:59", null));
        }
        return product;
    }

    /**
     * <b>Description: 保存销售授权书</b><br>
     *
     * @param request
     * @param user
     * @return <b>Author: Franlin</b>
     * <br><b>Date: 2018年6月21日 下午1:44:32 </b>
     */
    private TraderCertificateVo saveSaleAuth(HttpServletRequest request, User user) {
        TraderCertificateVo saleAuth = new TraderCertificateVo();
        String traderId = request.getParameter("traderId");
        String saleAuthBookName = request.getParameter("saleAuthBookName");
        String saleAuthBookUri = request.getParameter("saleAuthBookUri");
        String saleAuthBookStartTime = request.getParameter("saleAuthBookStartTime");
        String saleAuthBookEndTime = request.getParameter("saleAuthBookEndTime");
        String authPost = request.getParameter("authPost");
        String authUserName = request.getParameter("authUserName");
        String authContactInfo = request.getParameter("authContactInfo");

        saleAuth.setTraderId(Integer.valueOf(traderId));
        saleAuth.setTraderType(2);
        saleAuth.setSysOptionDefinitionId(SysOptionConstant.ID_1100);
        if (null != user) {
            saleAuth.setCreator(user.getUserId());
            saleAuth.setUpdater(user.getUserId());
        }
        saleAuth.setAddTime(System.currentTimeMillis());
        saleAuth.setModTime(System.currentTimeMillis());

        if (StringUtil.isNotBlank(saleAuthBookName)) {
            saleAuth.setName(saleAuthBookName);
            saleAuth.setUri(saleAuthBookUri);
        }
        if (StringUtil.isNotBlank(saleAuthBookStartTime)) {
            saleAuth.setBegintime(DateUtil.StringToDate(saleAuthBookStartTime).getTime());
        }
        if (StringUtil.isNotBlank(saleAuthBookStartTime)) {
            saleAuth.setEndtime(DateUtil.convertLong(saleAuthBookEndTime + " 23:59:59", null));
        }

        if (StringUtil.isNotBlank(authPost)) {
            saleAuth.setAuthPost(authPost);
        }
        if (StringUtil.isNotBlank(authUserName)) {
            saleAuth.setAuthUserName(authUserName);
        }
        if (StringUtil.isNotBlank(authContactInfo)) {
            saleAuth.setAuthContactInfo(authContactInfo);
        }

        return saleAuth;
    }

    /**
     * <b>Description:</b><br>
     * 保存医疗器械经营许可证
     *
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:54:45
     */
    private TraderCertificateVo saveOperate(HttpServletRequest request) {
        String operateTime = request.getParameter("operateStartTime");
        if (ObjectUtils.isEmpty(operateTime)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        String traderId = request.getParameter("traderId");

        String operateName = request.getParameter("operateName");
        String operateEndTime = request.getParameter("operateEndTime");
        String operateSn = request.getParameter("operateSn");
        // 医疗器械经营许可证
        TraderCertificateVo operate = new TraderCertificateVo();
        operate.setCreator(user.getUserId());
        operate.setAddTime(System.currentTimeMillis());
        operate.setUpdater(user.getUserId());
        operate.setModTime(System.currentTimeMillis());

        operate.setSn(operateSn);
        operate.setTraderId(Integer.valueOf(traderId));
        operate.setTraderType(2);
        operate.setSysOptionDefinitionId(678);
        if (ObjectUtils.notEmpty(operateName)) {
            String operateUri = request.getParameter("operateUri");
            operate.setUri(operateUri);
            operate.setName(operateName);
        }
        if (ObjectUtils.notEmpty(operateTime)) {
            operate.setBegintime(DateUtil.StringToDate(operateTime).getTime());
        }
        if (ObjectUtils.notEmpty(operateEndTime)) {
            operate.setEndtime(DateUtil.convertLong(operateEndTime + " 23:59:59", null));
        }
        return operate;
    }

    /**
     * <b>Description:</b><br> 供应商的财务信息
     *
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:07:00
     */
    @ResponseBody
    @RequestMapping(value = "/toAddFinancePage")
    public ModelAndView toAddFinancePage(TraderSupplier traderSupplier) {
        ModelAndView mav = new ModelAndView("trader/supplier/edit_finance");
        mav.addObject("traderSupplier", traderSupplier);
        TraderFinanceVo tf = new TraderFinanceVo();
        tf.setTraderId(traderSupplier.getTraderId());
        tf.setTraderType(ErpConst.TWO);
        //TraderFinanceVo traderFinance=traderCustomerService.getTraderFinanceByTraderId(tf);
        mav.addObject("traderFinance", tf);
        return mav;
    }

    /**
     * <b>Description:</b><br> 供应商的财务信息
     *
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:07:00
     */
    @ResponseBody
    @RequestMapping(value = "/toEditFinancePage")
    public ModelAndView toEditFinancePage(TraderFinanceVo traderFinance) throws IOException {
        ModelAndView mav = new ModelAndView("trader/supplier/edit_finance");
        //mav.addObject("traderSupplier", traderSupplier);
        //TraderFinanceVo tf=new TraderFinanceVo();
        //tf.setTraderId(traderSupplier.getTraderId());
        //tf.setTraderType(ErpConst.TWO);
        TraderFinanceVo tf = traderCustomerService.getTraderFinanceByTraderId(traderFinance);
        mav.addObject("traderFinance", tf);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderFinance)));
        return mav;
    }

    /**
     * <b>Description:</b><br> 删除供应商的银行帐号
     *
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:07:00
     */
    @ResponseBody
    @RequestMapping(value = "/delBank")
    @SystemControllerLog(operationType = "del", desc = "删除供应商的银行帐号")
    public ResultInfo<?> delBank(TraderFinance traderFinance) {
        ResultInfo<?> res = traderCustomerService.delSupplierBank(traderFinance);
        return res;
    }

    /**
     * <b>Description:</b><br> 保存供应商财务信息
     *
     * @param session
     * @param traderCustomer
     * @param traderFinance
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:58:17
     */
    @ResponseBody
    @RequestMapping(value = "/saveCustomerFinance")
    @SystemControllerLog(operationType = "edit", desc = "保存供应商财务信息")
    public ResultInfo saveCustomerFinance(HttpServletRequest request, HttpSession session, TraderSupplier traderSupplier,
                                          TraderFinance traderFinance, String beforeParams) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo res = traderCustomerService.saveCustomerFinance(traderFinance, user);
        return res;
    }

    /**
     * <b>Description:</b><br> 供应商申请账期
     *
     * @param traderSupplierVo
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2018年1月3日 下午2:26:35
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/accountperiodapply")
    public ModelAndView accountPeriodApply(TraderSupplierVo traderSupplierVo) {
        if (null == traderSupplierVo.getTraderId()
                || traderSupplierVo.getTraderId() <= 0
        ) {
            return pageNotFound();
        }

        TraderAccountPeriodApply lastAccountPeriodApply = traderSupplierService.getTraderSupplierLastAccountPeriodApply(traderSupplierVo.getTraderId());

        ModelAndView mav = new ModelAndView("trader/supplier/apply_accountperiod");
        if (null != lastAccountPeriodApply
                && lastAccountPeriodApply.getStatus() == 0) {
            mav.addObject("message", "当前有账期未审核，请等待当前帐期审核完成！");
        } else {
            TraderSupplierVo traderSupplierForAccountPeriod = traderSupplierService.getTraderSupplierForAccountPeriod(traderSupplierVo);
            mav.addObject("traderSupplier", traderSupplierForAccountPeriod);
        }
        return mav;
    }

    /**
     * <b>Description:</b><br> 保存申请供应商账期
     *
     * @param request
     * @param session
     * @param traderAccountPeriodApply
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年8月8日 上午10:58:13
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveaccountperiodapply")
    @SystemControllerLog(operationType = "add", desc = "保存供应商账期申请")
    public ResultInfo saveAccountPeriodApply(HttpServletRequest request, HttpSession session, TraderAccountPeriodApply traderAccountPeriodApply) {
        traderAccountPeriodApply.setTraderType(ErpConst.TWO);
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ResultInfo resultInfo = traderCustomerService.saveAccountPeriodApply(traderAccountPeriodApply, session);
        //如果操作成功触发审核操作
        if (resultInfo.getCode() == 0) {
            Integer periodApplyId = (Integer) resultInfo.getData();
            TraderAccountPeriodApply tapaInfo = traderCustomerService.getAccountPeriodDaysApplyInfo(periodApplyId);
            try {
                Map<String, Object> variableMap = new HashMap<String, Object>();
                // 查询当前账期创建者
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "customerPeriodVerify");
                variableMap.put("businessKey", "customerPeriodVerify_" + periodApplyId);
                variableMap.put("relateTableKey", periodApplyId);
                variableMap.put("relateTable", "T_TRADER_ACCOUNT_PERIOD_APPLY");
                variableMap.put("orgId", user.getOrgId());
                variableMap.put("traderAccountPeriodApply", tapaInfo);
                variableMap.put("accountPeriodApply", tapaInfo.getAccountPeriodApply());
                variableMap.put("accountPeriodDaysApply", tapaInfo.getAccountPeriodDaysApply());
                variableMap.put("traderId", tapaInfo.getTraderId());
                variableMap.put("traderType", tapaInfo.getTraderType());
                variableMap.put("traderAccountPeriodApplyId", tapaInfo.getTraderAccountPeriodApplyId());
                actionProcdefService.createProcessInstance(request, "customerPeriodVerify", "customerPeriodVerify_" + periodApplyId, variableMap);
                //默认申请人通过
                //根据BusinessKey获取生成的审核实例
                Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerPeriodVerify_" + periodApplyId);
                if (historicInfo.get("endStatus") != "审核完成") {
                    Task taskInfo = (Task) historicInfo.get("taskInfo");
                    String taskId = taskInfo.getId();
                    Authentication.setAuthenticatedUserId(user.getUsername());
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("tableName", "T_TRADER_ACCOUNT_PERIOD_APPLY");
                    variables.put("id", "TRADER_ACCOUNT_PERIOD_APPLY_ID");
                    variables.put("idValue", periodApplyId);
                    variables.put("key", "STATUS");
                    variables.put("value", 1);
                    //回写的表在dbcenter中
                    variables.put("db", 2);
                    //默认审批通过
                    ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variables);
                    //如果未结束添加审核对应主表的审核状态
                    if (!complementStatus.getData().equals("endEvent")) {
                        verifiesRecordService.saveVerifiesInfo(taskId, 0);
                    }
                }
                //return new ResultInfo(0, "操作成功");
            } catch (Exception e) {
                logger.error("saveaccountperiodapply:", e);
                return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
            }
        }
        resultInfo.setData(request.getParameter("traderId") + "," + request.getParameter("traderSupplierId"));
        return resultInfo;
    }

    @Autowired
    private RoleService roleService;

    /**
     * <b>Description:</b><br>
     * 供应商申请审核
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年10月24日 下午18:42:13
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/editApplyValidSupplier")
    @SystemControllerLog(operationType = "edit", desc = "申请审核")
    public ResultInfo<?> editApplyValidSupplier(HttpServletRequest request, TraderSupplier traderSupplier, String taskId, HttpSession session) {
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            // 查询当前订单的一些状态
            TraderSupplierVo traderBaseInfo = traderSupplierService.getTraderSupplierBaseInfo(traderSupplier);
            //开始生成流程(如果没有taskId表示新流程需要生成)
            if (taskId==null||taskId.equals("")||taskId.equals("0")) {
                variableMap.put("traderSupplierVo", traderBaseInfo);
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "traderSupplierVerify");
                variableMap.put("businessKey", "traderSupplierVerify_" + traderBaseInfo.getTraderSupplierId());
                variableMap.put("relateTableKey", traderBaseInfo.getTraderSupplierId());
                variableMap.put("relateTable", "T_TRADER_SUPPLIER");
                actionProcdefService.createProcessInstance(request, "traderSupplierVerify", "traderSupplierVerify_" + traderBaseInfo.getTraderSupplierId(), variableMap);
            }
            //默认申请人通过
            //根据BusinessKey获取生成的审核实例
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "traderSupplierVerify_" + traderBaseInfo.getTraderSupplierId());
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                Authentication.setAuthenticatedUserId(user.getUsername());
                Map<String, Object> variables = new HashMap<String, Object>();
                //默认审批通过
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variables);
                //如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfo(taskId, 0);
                }
            }
            if (traderBaseInfo != null && traderBaseInfo.getTrader() != null && StringUtil.isNotBlank(traderBaseInfo.getTrader().getTraderName())) {
                List<Integer> userIds = new ArrayList<>();
                Page page = new Page(1, 2);
                RoleVo role = new RoleVo();
                role.setRoleName("供应管理组助理");
                List<Role> roles = roleService.queryListPage(role, page);
                if (org.apache.commons.collections.CollectionUtils.isNotEmpty(roles)) {
                    for (Role r : roles) {
                        List<User> users = userService.getUserByRoleId(r.getRoleId());
                        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(users)) {
                            for (User u : users) {
                                userIds.add(u.getUserId());
                            }
                        }
                    }
                }
                Map<String, String> map = new HashMap<>();

                map.put("traderName", traderBaseInfo.getTrader().getTraderName());
                String url = ErpConst.SUPPLIER_BASE_URL + traderBaseInfo.getTraderId();
                MessageUtil.sendMessage(61, userIds, map, url);
            }
            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("editApplyValidSupplier:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }

    }

    /**
     * <b>Description:</b><br>
     * 供应商审核弹层页面
     *
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年11月10日 下午1:39:42
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/complement")
    public ModelAndView complement(HttpSession session, Integer traderSupplierId, String taskId, Boolean pass) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("taskId", taskId);
        mv.addObject("pass", pass);
        mv.addObject("traderSupplierId", traderSupplierId);
        mv.setViewName("trader/customer/complement");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 供应商审核操作
     *
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年11月10日 下午1:39:42
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/complementTask")
    @SystemControllerLog(operationType = "edit", desc = "审核操作")
    public ResultInfo<?> complementTask(HttpServletRequest request, Integer traderSupplierId, String taskId, String comment, Boolean pass,
                                        HttpSession session) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pass", pass);
        variables.put("updater", user.getUserId());
        try {
            //如果审核没结束添加审核对应主表的审核状态
            Integer status = 0;
            if (pass) {
                //如果审核通过
                status = 0;
            } else {
                //如果审核不通过
                status = 2;
                verifiesRecordService.saveVerifiesInfo(taskId, status);
            }
            ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variables);
            //如果未结束添加审核对应主表的审核状态
            if (!complementStatus.getData().equals("endEvent")) {
                verifiesRecordService.saveVerifiesInfo(taskId, status);
            }
            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("trader supplier complementTask:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }

	}
	
	/**
	 * <b>Description:</b><br> 交易记录
	 * @param request
	 * @param saleorderGoodsVo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月25日 下午1:42:53
	 */
	@ResponseBody
	@RequestMapping(value="/businesslist")
	public ModelAndView businessList(HttpServletRequest request,BuyorderGoodsVo buyorderGoodsVo,TraderSupplierVo traderSupplierVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView();
		if(buyorderGoodsVo.getTraderId() == null){
			return pageNotFound();
		}
		
		// 时间处理
		if (null != buyorderGoodsVo.getStarttime() && buyorderGoodsVo.getStarttime() != "") {
			buyorderGoodsVo.setStarttimeLong(DateUtil.convertLong(buyorderGoodsVo.getStarttime(), "yyyy-MM-dd"));
		}
		if (null != buyorderGoodsVo.getEndtime() && buyorderGoodsVo.getEndtime() != "") {
			buyorderGoodsVo.setEndtimeLong(DateUtil.convertLong(buyorderGoodsVo.getEndtime()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		Page page = getPageTag(request, pageNo, pageSize);
		
		List<BuyorderGoodsVo> list = null;
		Map<String, Object> map = traderSupplierService.getBusinessListPage(buyorderGoodsVo,page);
		
		list = (List<BuyorderGoodsVo>) map.get("list");

		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 start
		if(!CollectionUtils.isEmpty(list)){
			List<Integer> skuIds = new ArrayList<>();
			list.stream().forEach(saleGood -> {
				skuIds.add(saleGood.getGoodsId());
			});
			List<Map<String,Object>> skuTipsMap = this.vGoodsService.skuTipList(skuIds);
			Map<String,Map<String,Object>> newSkuInfosMap = skuTipsMap.stream().collect(Collectors.toMap(key->key.get("SKU_NO").toString(), v -> v, (k, v) -> v));
			mv.addObject("newSkuInfosMap", newSkuInfosMap);
		}
		//新商品流切换 VDERP-1970 add by brianna 2020/3/16 end


		mv.setViewName("trader/supplier/bussiness_list");
		mv.addObject("method", "buyorder");
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("buyorderGoodsVo", buyorderGoodsVo);
		mv.addObject("businessList", list);
		mv.addObject("traderSupplier", traderSupplierVo);
		return mv;
	}
	
	 /**
     * <b>Description:</b><br>
     * 保存品牌授权书
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> Barry <br>
     *       <b>Date:</b> 2018年9月19日 上午11:33:45
     */
    private TraderCertificateVo saveBrandBook(HttpServletRequest request) {
        String brandName = request.getParameter("brandBookName");
        String otherPicName = request.getParameter("name_9");//多张图片的URI        
        if(ObjectUtils.isEmpty(brandName) && ObjectUtils.isEmpty(otherPicName)){
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        String traderId = request.getParameter("traderId");
        String brandStartTime = request.getParameter("brandBookStartTime");
        String brandEndTime = request.getParameter("brandBookEndTime");

        //品牌授权书
        TraderCertificateVo brand = new TraderCertificateVo();
        brand.setCreator(user.getUserId());
        brand.setAddTime(System.currentTimeMillis());
        brand.setUpdater(user.getUserId());
        brand.setModTime(System.currentTimeMillis());

        brand.setTraderId(Integer.valueOf(traderId));
        brand.setTraderType(2);
        brand.setSysOptionDefinitionId(SysOptionConstant.ID_894);
        brand.setIsMedical(0);
        if (ObjectUtils.notEmpty(brandName)) {
            String brandUri = request.getParameter("brandBookUri");
            brand.setUri(brandUri);
            brand.setName(brandName);
        }

        //判断是否有多张图片
        if (ObjectUtils.notEmpty(otherPicName)) {
            String[] uris = request.getParameterValues("uri_9");
            brand.setUris(uris);
        }

        if (ObjectUtils.notEmpty(brandStartTime)) {
            brand.setBegintime(DateUtil.StringToDate(brandStartTime).getTime());
        }
        if (ObjectUtils.notEmpty(brandEndTime)) {
            brand.setEndtime(DateUtil.convertLong(brandEndTime + " 23:59:59", null));
        }
        return brand;
    }

    /**
     * <b>Description:</b><br>
     * 保存其他资格证书
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Barry <br>
     * <b>Date:</b> 2018年9月19日 上午11:33:45
     */
    private TraderCertificateVo saveTheOther(HttpServletRequest request) {

        String otherName = request.getParameter("otherName");
        String otherPicName = request.getParameter("name_10");//多张图片的URI
        if (ObjectUtils.isEmpty(otherName) && ObjectUtils.isEmpty(otherPicName)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        String traderId = request.getParameter("traderId");

        //其他资格证书
        TraderCertificateVo other = new TraderCertificateVo();
        other.setCreator(user.getUserId());
        other.setAddTime(System.currentTimeMillis());
        other.setUpdater(user.getUserId());
        other.setModTime(System.currentTimeMillis());

        other.setTraderId(Integer.valueOf(traderId));
        other.setTraderType(2);
        other.setSysOptionDefinitionId(SysOptionConstant.ID_895);
        other.setIsMedical(0);
        if (ObjectUtils.notEmpty(otherName)) {
            String otherUri = request.getParameter("otherUri");
            other.setUri(otherUri);
            other.setName(otherName);
        }
        //判断是否有多张图片
        if (ObjectUtils.notEmpty(otherPicName)) {
            String[] uris = request.getParameterValues("uri_10");
            other.setUris(uris);
        }
        return other;
    }

    @Value("${supply_department_name}")
    protected String supplyDepartmentName;

    private boolean isCurrentUserBelong2SupplyOrg(User user){
        if(user==null||user.getUserId()==null){
            return false;
        }
        Organization orgOfParent=organizationService.getOrganizationByName(supplyDepartmentName);
        if(orgOfParent==null||orgOfParent.getOrgId()==null){
            return true;
        }
        List<Organization> organizations=organizationService.getOrganizationChild(orgOfParent);
        if(!CollectionUtils.isEmpty(organizations)){
            for(Organization org:organizations){
                if(org!=null&&org.getOrgId()!=null&&org.getOrgId().equals(user.getOrgId())){
                    return true;
                }
            }
        }
        return false;
    }
}
