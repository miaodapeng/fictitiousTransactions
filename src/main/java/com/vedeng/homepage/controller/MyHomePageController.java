package com.vedeng.homepage.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.OrganizationVo;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.validator.FormToken;
import com.vedeng.homepage.model.SalesGoalSetting;
import com.vedeng.homepage.model.vo.AfterSalesDataVo;
import com.vedeng.homepage.model.vo.EchartsDataVo;
import com.vedeng.homepage.model.vo.SaleEngineerDataVo;
import com.vedeng.homepage.service.MyHomePageService;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.order.model.Quoteorder;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderContract;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.service.QuoteService;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.system.model.Address;
import com.vedeng.system.model.Attachment;
import com.vedeng.system.model.vo.AddressVo;
import com.vedeng.system.model.vo.ParamsConfigVo;
import com.vedeng.system.service.AddressService;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.PositService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.vo.CommunicateRecordVo;

/**
 * <b>Description:</b><br>
 * 我的首页--控制器
 * 
 * @author east
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.homepage.controller <br>
 *       <b>ClassName:</b> MyHomePageController <br>
 *       <b>Date:</b> 2017年11月18日 上午10:42:05
 */
@Controller
@RequestMapping("home/page")
public class MyHomePageController extends BaseController {

    @Resource
    private PositService positService;

    @Resource
    private MyHomePageService myHomePageService;

    @Resource
    private UserService userService;

    @Resource
    private LogisticsService logisticsService;

    @Autowired
    @Qualifier("regionService")
    private RegionService regionService;

    @Resource
    private AddressService addressService;

    @Resource
    private OrgService orgService;

    @Autowired
    @Qualifier("quoteService")
    private QuoteService quoteService;
    
    @Autowired
	@Qualifier("saleorderService")
	protected SaleorderService saleorderService;
    
    @Autowired
	@Qualifier("actionProcdefService")
	protected ActionProcdefService actionProcdefService;
    
    @Autowired // 自动装载
	protected ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * <b>Description:</b><br>
     * 获取我的首页信息
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月18日 上午10:50:46
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/index")
    public ModelAndView getMyHomePage(HttpServletRequest request, SaleEngineerDataVo sedv,
            @RequestParam(required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer pageSize) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);

        ModelAndView mav = new ModelAndView();
        if (user.getPositType() != null && user.getPositType() == 310) {// 销售
            if (user.getPositLevel() != null && user.getPositLevel() == 441) {// 销售总经理
                List<OrganizationVo> orgList = myHomePageService.getOrganizationVoList(user);
                mav.addObject("orgList", orgList);
                mav.setViewName("/homepage/sale/sale_director_page");
                // mav.setViewName("welcome");
            }
            else if (user.getPositLevel() != null && user.getPositLevel() == 442) {// 高级销售主管改为总监
                List<OrganizationVo> orgList = myHomePageService.getOrganizationVoList(user);
                mav.addObject("orgList", orgList);
                mav.setViewName("/homepage/sale/sale_senior_manager_page");
                // mav.setViewName("welcome");
            }
            else if (user.getPositLevel() != null && user.getPositLevel() == 444) {// 销售主管
                List<User> userList = userService.getUserListByOrgId(user.getOrgId());
                mav.addObject("userList", userList);
                mav.setViewName("/homepage/sale/sale_manager_page");
                // mav.setViewName("welcome");
            }
            // 销售工程师
            else if (user.getPositLevel() != null && user.getPositLevel() == 445) {
                sedv.setCompanyId(user.getCompanyId());
                sedv.setUserId(user.getUserId());
                // 类型
                Integer accessType = sedv.getAccessType();
                SaleEngineerDataVo saleEngineerDataVo = new SaleEngineerDataVo();
                if (accessType == null || accessType == 0) {
                    // 销售个人首页
                    mav.setViewName("/saleperformance/sale/sales_index");
                }
                // 默认进客户沟通
                else if (accessType == 1) {
                    mav.setViewName("/homepage/sale/sale_engineer_page");
                }
                // 商机跟进
                else if (accessType == 2) {
                    BussinessChanceVo bussinessChanceVo = new BussinessChanceVo();
                    Map<String, Object> map = new HashMap<String, Object>();
                    Page page = getPageTag(request, pageNo, pageSize);
                    bussinessChanceVo.setUserId(user.getUserId());
                    bussinessChanceVo.setCompanyId(user.getCompanyId());
                    map = myHomePageService.getBussinessChanceVoList(bussinessChanceVo, page);
                    mav.addObject("bussinessChanceVo", map.get("list"));
                    mav.addObject("page_bussiness", map.get("page"));
                    mav.setViewName("/homepage/sale/sale_engineer_page_bussinesschance");
                }
                // 报价跟进
                else if (accessType == 3) {
                    /*** 2018-08-01 Barry 修改销售个人首页报价页面 ***/

                    Page page = getPageTag(request, pageNo, pageSize);
                    Quoteorder quote = new Quoteorder();
                    quote.setValidStatus(1);// 设置报价订单生效状态为生效
                    quote.setFollowOrderStatus(0);// 设置跟单状态为跟单中
                    quote.setOptUserId(user.getUserId());// 设置归属销售为当前销售
                    List<Integer> positionType = new ArrayList<>();
                    positionType.add(SysOptionConstant.ID_310);
                    List<User> userList = userService.getMyUserList(user, positionType, false);
                    if (quote.getOptUserId() != null) {
                        User s_user = new User();
                        s_user.setUserId(quote.getOptUserId());
                        quote.setSaleUserList(new ArrayList() {
                            {
                                add(s_user);
                            }
                        });
                    }
                    else if (user.getPositType() != null
                            && user.getPositType().intValue() == SysOptionConstant.ID_310) {
                        quote.setSaleUserList(userList);
                    }
                    quote.setTimeType(3);// 设置时间类型为创建时间
                    Date today = new Date();
                    long endDay = DateUtil.getDateBefore(today, 90);// 获取当前时间的前90天时间
                    quote.setBeginDate(endDay);// 设置开始时间为当前时间的三个月前
                    quote.setEndDate(today.getTime());// 设置结束时间为当前日期

                    Map<String, Object> map = quoteService.getQuoteListPage(quote, page);
                    List<Quoteorder> list = (List<Quoteorder>) map.get("quoteList");
                    mav.addObject("quoteList", list);
                    mav.addObject("page", map.get("page"));
                    /*** 修改销售个人首页报价页面 --end-- ***/
                    mav.setViewName("/homepage/sale/sale_engineer_page_quote");
                }
                // 本月数据
                else if (accessType == 4) {
                    mav.setViewName("/homepage/sale/sale_engineer_page_month");
                }
                // 个人数据
                else if (accessType == 5) {
                    mav.setViewName("/homepage/sale/sale_engineer_page_personal");
                }
                else if (accessType == 6) {
                	/*** 2019-03-05 Chuck 回传合同优化 ***/
                	SaleorderContract order = new SaleorderContract();
                	String searchType = "1";
                	try {
                		order.setCompanyId(1);
	                	order.setUserId(user.getUserId());
	                //	Page pageTotal1 = getPageTag(request, pageNo, pageSize);
	                //	Page pageTotal2 = getPageTag(request, pageNo, pageSize);
	                	
	                	;
	                	mav.addObject("count1",saleorderService.getContractReturnOrderCount(order, "1"));
	                	mav.addObject("count2", saleorderService.getContractReturnOrderCount(order, "2"));
                		
        
                		if(request.getParameter("customerName") != null && request.getParameter("customerName") != "") {
                			order.setCustomerName(request.getParameter("customerName"));
                		}
                		if(request.getParameter("saleorderNo") != null && request.getParameter("saleorderNo") != "") {
                			order.setSaleorderNo(request.getParameter("saleorderNo"));
                		}
                		if(request.getParameter("contractType") != null && request.getParameter("contractType") != "") {
                			order.setContractType(request.getParameter("contractType"));
                		}
                    
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd" );  
                		if(request.getParameter("searchBegintimeStr") != null && request.getParameter("searchBegintimeStr") != "") {
                			order.setSearchBegintime(sdf.parse(request.getParameter("searchBegintimeStr")).getTime());
                		}
						if(request.getParameter("searchEndtimeStr") != null&& request.getParameter("searchEndtimeStr") != "") {
							order.setSearchEndtime(DateUtil.convertLong(request.getParameter("searchEndtimeStr") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
						}
						if(request.getParameter("searchType") != null && request.getParameter("searchType") != "") {
							searchType = request.getParameter("searchType");
                		}
					
	                	//根据查询条件查询销售订单
	                	Page page1 = getPageTag(request, pageNo, pageSize);
	                	Page page2 = getPageTag(request, pageNo, pageSize);
	                	
	                	
	                	
	                	Map<String, Object> resultMap = saleorderService.getContractReturnOrderListPage(order,page1,searchType);
	                	mav.addObject("contractSaleorderList", resultMap.get("list"));
	                	mav.addObject("page1", resultMap.get("page"));
	                	

	                	
	        			@SuppressWarnings("unchecked")
	        			List<SaleorderContract> contractSaleorderList = (List<SaleorderContract>)resultMap.get("list");

	        			// 合同回传审核信息
	        			for(SaleorderContract orderContract : contractSaleorderList) {
	        				Integer saleorderId = orderContract.getSaleorderId();
	        				
	            			Map<String, Object> historicInfoContractReturn = actionProcdefService.getHistoric(processEngine,
	            					"contractReturnVerify_" + saleorderId);
	            			Task taskInfoContractReturn = (Task) historicInfoContractReturn.get("taskInfo");
	            			String taskId = "0";
	            			if(taskInfoContractReturn != null) {
	            				taskId = taskInfoContractReturn.getId();
	            			}
	            			orderContract.setTaskId(taskId);
	            			
	            			// 获取订单合同回传&订单送货单回传列表
	            			List<Attachment> saleorderAttachmentList = saleorderService.getSaleorderAttachmentList(saleorderId);
	            			List<Attachment> tempList = new ArrayList<Attachment>();
	            			orderContract.setContractFileCount(0);
	            			if(saleorderAttachmentList != null && saleorderAttachmentList.size() != 0) {
	            				for(Attachment attach : saleorderAttachmentList) {
		            				if(attach.getAttachmentFunction() != 492) {
		            					tempList.add(attach);
		            				}
		            			}
		            			orderContract.setContractFileCount( tempList.size());
	            			}
	            			
	        			}


	                	User currUser = (User)request.getSession().getAttribute(Consts.SESSION_USER);
	    				
	                	mav.addObject("currUser", currUser.getUsername());
	                	if(request.getParameter("optUserName") != null && request.getParameter("optUserName") != "") {
                			order.setOptUserName(request.getParameter("optUserName"));
                		}
	                	mav.addObject("saleorder", order);
	                	mav.addObject("searchType", searchType);            	
                	} catch (Exception e) {
						logger.error("my home page index:", e);
					}
                	/*** 回传合同优化 --end-- ***/
                    mav.setViewName("/homepage/sale/sale_engineer_page_contract");
                }

//                /*** 原east代码 ***/
//                // 原逻辑
//                saleEngineerDataVo = myHomePageService.getSaleEngineerDataVo(sedv);
//                if (null != saleEngineerDataVo) {
//                    saleEngineerDataVo.setAccessType(accessType);
//                }
//
//                mav.addObject("saleEngineerDataVo", saleEngineerDataVo);
//
//                /*** 原east代码--end-- ***/
//
//                // begin add by Franlin for[销售--五行剑法] at 2018-06-05
//                mav.addObject("companyId", user.getCompanyId());
//                String userName = user.getUsername();
//                if (null == userName) {
//                    User u = userService.getUserById(user.getUserId());
//                    if (null != u) {
//                        userName = u.getUsername();
//                    }
//                }
              //  mav.addObject("userName", userName);
                // 销售人员个人ID用于区分展示
                mav.addObject("userFlag", 1);
                // 五行剑法页面，通用Id
                mav.addObject("five_userId", user.getUserId());
                mav.addObject("accessType", accessType);
                // end add by Franlin for[销售--五行剑法] at 2018-06-05

            }
            else {
                mav.setViewName("welcome");
            }
        }
        else if (user.getPositType() != null && user.getPositType() == 311) {// 产品
            if (user.getPositLevel() != null && user.getPositLevel() == 442) {// 产品总监
                // mav.setViewName("welcome");
                mav.setViewName("/homepage/goods/goods_director_page");
            }
            else if (user.getPositLevel() != null && user.getPositLevel() == 444) {// 产品主管
                mav.setViewName("welcome");
            }
            else if (user.getPositLevel() != null && user.getPositLevel() == 445) {// 产品人员
                mav.setViewName("welcome");
            }
            else {
                mav.setViewName("welcome");
            }
        }
        else if (user.getPositType() != null && user.getPositType() == 312) {// 售后
            if (user.getPositLevel() != null && user.getPositLevel() == 442) {// 售后总监
                AfterSalesDataVo asdv = myHomePageService.getAfterSalesDataVo(user);
                mav.addObject("asdv", asdv);
                mav.setViewName("/homepage/aftersales/aftersales_director_page");
                // mav.setViewName("welcome");
            }
            // else if(user.getPositLevel() != null && user.getPositLevel() == 455){//
            // mav.setViewName("welcome");
            // }
            else {
                List<CommunicateRecordVo> crvList = myHomePageService.getAfterSalesCommunicateRecordVoList(user);
                mav.addObject("crvList", crvList);
                mav.setViewName("/homepage/aftersales/aftersales_manager_page");
                // mav.setViewName("welcome");
            }
        }
        else if (user.getPositType() != null && user.getPositType() == 313) {// 物流
            if (user.getPositLevel() != null && user.getPositLevel() == 442) {// 总监
                mav.setViewName("/homepage/logistics/logistics_director_page");
                // mav.setViewName("welcome");
            }
            else {
                mav.setViewName("welcome");
            }
        }
        else if (user.getPositType() != null && user.getPositType() == 314) {// 财务
            mav.setViewName("welcome");
        }
        else {
            mav.setViewName("welcome");
        }
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 销售的首页数据
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月21日 下午1:31:11
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
    @RequestMapping(value = "/getMyHomePageMsg")
    public ResultInfo<?> getMyHomePageMsg(HttpServletRequest request) {
        // User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // EchartsDataVo echartsDataVo = new EchartsDataVo();
        // //查询完成数据，同比，环比数据
        // echartsDataVo = myHomePageService.getEchartsDataVo(user);
        // //查询目标数据
        // Map<String, Object> map = new HashMap<>();
        // if(user.getPositLevel() == 441 || user.getPositLevel() == 442){
        // List<Integer> orgIds = myHomePageService.getOrgIdList(user);
        // map.put("ids", orgIds);
        // }
        // if(user.getPositLevel() == 444){
        // List<Integer> positTypes = new ArrayList<>();
        // positTypes.add(SysOptionConstant.ID_310);
        // List<User> userList = userService.getMyUserList(user,positTypes , false);
        // if(userList != null){
        // for (User us : userList) {
        // if(user.getUsername().equals(us.getUsername())){
        // userList.remove(us);
        // }
        // }
        // }
        // map.put("userList", userList);
        // }
        // map.put("companyId", user.getCompanyId());
        //
        // List<BigDecimal> totalMonths = myHomePageService.getTotalMonthList(map);
        // echartsDataVo.setGoalList(totalMonths);
        // echartsDataVo.setSalesGoalSettingList(myHomePageService.getSalesGoalSettingListByOrgIds(user));
        //
        // ResultInfo res = new ResultInfo(0, "查询成功", echartsDataVo);
        return new ResultInfo(0, "查询成功", null);
    }

    /**
     * <b>Description:</b><br>
     * 售后的首页数据
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月21日 下午1:31:11
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
    @RequestMapping(value = "/getAfterSalesHomePageMsg")
    public ResultInfo<?> getAfterSalesHomePageMsg(HttpServletRequest request) {
        // User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // EchartsDataVo echartsDataVo = new EchartsDataVo();
        // //查询销售订单售后统计，同比，环比等数据
        // echartsDataVo = myHomePageService.getEchartsDataVo(user);
        // ResultInfo res = new ResultInfo(0, "查询成功", echartsDataVo);
        // return res;
        return new ResultInfo(0, "查询成功", null);
    }

    /**
     * <b>Description:</b><br>
     * 物流的首页数据
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月21日 下午1:31:11
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @ResponseBody
    @RequestMapping(value = "/getLogisticsHomePageMsg")
    public ResultInfo<?> getLogisticsHomePageMsg(HttpServletRequest request) {
        // User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        // EchartsDataVo echartsDataVo = new EchartsDataVo();
        //
        // echartsDataVo = myHomePageService.getEchartsDataVo(user);
        // ResultInfo res = new ResultInfo(0, "查询成功", echartsDataVo);
        // return res;
        return new ResultInfo(0, "查询成功", null);
    }

    /**
     * <b>Description:</b><br>
     * 异步加载地图数据
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月19日 下午4:18:56
     */
    @ResponseBody
    @RequestMapping(value = "/getCustomerData")
    public ResultInfo<?> getCustomerData(HttpServletRequest request, EchartsDataVo echartsDataVo) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        echartsDataVo.setCompanyId(user.getCompanyId());
        echartsDataVo = myHomePageService.getCustomerData(echartsDataVo);
        ResultInfo res = new ResultInfo(0, "查询成功", echartsDataVo);
        return res;
    }

    /**
     * <b>Description:</b><br>
     * 设置销售目标页面
     * 
     * @param request
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月22日 下午2:27:52
     */
    @ResponseBody
    @RequestMapping(value = "/configSaleGoalPage")
    public ModelAndView configSaleGoalPage(HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView("/homepage/sale/config_slae_goal");
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        if (ObjectUtils.notEmpty(user.getPositLevel()) && ObjectUtils.notEmpty(user.getPositType())
                && user.getPositType() == 310) {
            List<OrganizationVo> orgList = myHomePageService.getOrganizationVoList(user);
            mav.addObject("orgList", orgList);
            if (user.getPositLevel() == 441 || user.getPositLevel() == 442) {
                if (orgList != null && orgList.size() > 0) {
                    List<Integer> orgIds = new ArrayList<>();
                    for (OrganizationVo org : orgList) {
                        orgIds.add(org.getOrgId());
                    }
                    // 查询当前部门负责人设定值
                    Map<String, Object> map = new HashMap<>();
                    map.put("companyId", user.getCompanyId());
                    map.put("ids", orgIds);
                    List<BigDecimal> totalMonths = myHomePageService.getTotalMonthList(map);
                    mav.addObject("totalMonths", totalMonths);

                }
            }
            else if (user.getPositLevel() == 444) {
                List<User> userList = userService.getUserListByOrgId(user.getOrgId());
                Map<String, Object> map = new HashMap<>();
                map.put("companyId", user.getCompanyId());
                map.put("orgId", user.getOrgId());
                map.put("userList", userList);
                List<BigDecimal> totalMonths = myHomePageService.getTotalMonthList(map);
                mav.addObject("totalMonths", totalMonths);
            }
        }

        // 查询当前部门的目标值
        List<Integer> orgIds = new ArrayList<>();
        orgIds.add(user.getOrgId());
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", user.getCompanyId());
        map.put("ids", orgIds);
        List<BigDecimal> goalMonths = myHomePageService.getTotalMonthList(map);
        mav.addObject("goalMonths", goalMonths);

        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH);
        mav.addObject("month", month + 1);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(goalMonths)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存销售设置的目标
     * 
     * @param request
     * @return
     * @throws ParseException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月23日 上午9:10:11
     */
    @ResponseBody
    @RequestMapping(value = "/saveConfigSaleGoal")
    @SystemControllerLog(operationType = "edit", desc = "保存销售设置的目标")
    public ModelAndView saveConfigSaleGoal(HttpServletRequest request, String[] month_1, String[] month_2,
            String[] month_3, String[] month_4, String[] month_5, String[] month_6, String[] month_7, String[] month_8,
            String[] month_9, String[] month_10, String[] month_11, String[] month_12, String beforeParams)
            throws ParseException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        List<SalesGoalSetting> list = new ArrayList<>();
        if (month_1 != null) {
            list.addAll(getSalesGoalSetting(month_1, 1, user));
        }
        if (month_2 != null) {
            list.addAll(getSalesGoalSetting(month_2, 2, user));
        }
        if (month_3 != null) {
            list.addAll(getSalesGoalSetting(month_3, 3, user));
        }
        if (month_4 != null) {
            list.addAll(getSalesGoalSetting(month_4, 4, user));
        }
        if (month_5 != null) {
            list.addAll(getSalesGoalSetting(month_5, 5, user));
        }
        if (month_6 != null) {
            list.addAll(getSalesGoalSetting(month_6, 6, user));
        }
        if (month_7 != null) {
            list.addAll(getSalesGoalSetting(month_7, 7, user));
        }
        if (month_8 != null) {
            list.addAll(getSalesGoalSetting(month_8, 8, user));
        }
        if (month_9 != null) {
            list.addAll(getSalesGoalSetting(month_9, 9, user));
        }
        if (month_10 != null) {
            list.addAll(getSalesGoalSetting(month_10, 10, user));
        }
        if (month_11 != null) {
            list.addAll(getSalesGoalSetting(month_11, 11, user));
        }
        if (month_12 != null) {
            list.addAll(getSalesGoalSetting(month_12, 12, user));
        }
        if (list.size() > 0) {
            int res = myHomePageService.saveOrUpdateConfigSaleGoal(list);
            if (res == 0) {
                return fail(new ModelAndView());
            }
            ModelAndView mav = new ModelAndView();
            mav.addObject("url", "./configSaleGoalPage.do");
            return success(mav);
        }
        else {
            ModelAndView mav = new ModelAndView();
            mav.addObject("url", "./configSaleGoalPage.do");
            return success(mav);
        }

    }

    /**
     * <b>Description:</b><br>
     * 获取每个月的分解数据
     * 
     * @param month
     * @return
     * @throws ParseException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年11月24日 上午10:37:55
     */
    private List<SalesGoalSetting> getSalesGoalSetting(String[] months, int month, User user) throws ParseException {
        List<SalesGoalSetting> list = new ArrayList<>();
        for (int i = 0; i < months.length; i++) {
            String str = months[i];
            if (!"".equals(str) && !str.contains("undefined")) {
                SalesGoalSetting sgs = null;
                if (months[i].split("\\|").length == 3) {
                    sgs = new SalesGoalSetting();
                    sgs.setCompanyId(user.getCompanyId());
                    sgs.setCreator(user.getUserId());
                    sgs.setAddTime(DateUtil.sysTimeMillis());
                    sgs.setUpdater(user.getUserId());
                    sgs.setModTime(DateUtil.sysTimeMillis());
                    sgs.setMonth(month);
                    // sgs.setYear(getCurrentYear());
                    sgs.setGoal(new BigDecimal(months[i].split("\\|")[0]));
                    sgs.setOrgId(Integer.valueOf(months[i].split("\\|")[1]));
                    sgs.setUserId(Integer.valueOf(months[i].split("\\|")[2]));
                }
                else if (months[i].split("\\|").length == 4) {
                    sgs = new SalesGoalSetting();
                    sgs.setUpdater(user.getUserId());
                    sgs.setModTime(DateUtil.sysTimeMillis());
                    sgs.setMonth(month);
                    // sgs.setYear(getCurrentYear());
                    sgs.setGoal(new BigDecimal(months[i].split("\\|")[0]));
                    sgs.setOrgId(Integer.valueOf(months[i].split("\\|")[1]));
                    sgs.setUserId(Integer.valueOf(months[i].split("\\|")[2]));
                    sgs.setSalesGoalSettingId(Integer.valueOf(months[i].split("\\|")[3]));
                }
                if (sgs != null) {
                    list.add(sgs);
                }
            }
        }
        return list;
    }

    /**
     * <b>Description:</b><br>
     * 配置参数
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月4日 上午10:02:22
     */
    @ResponseBody
    @RequestMapping(value = "/configSaleParamsPage")
    public ModelAndView configSaleParamsPage(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView();
        ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
        paramsConfigVo.setCompanyId(user.getCompanyId());
        if (user.getPositType() == 310) {
            mav.setViewName("/homepage/sale/config_slae_param");
            // 报价有效期paramsKey = 103
            paramsConfigVo.setParamsKey(103);
            ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("quote", quote);
            // 订单有效期paramsKey = 104
            paramsConfigVo.setParamsKey(104);
            ParamsConfigVo sale = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("sale", sale);
            // 订单有效期paramsKey = 104
            paramsConfigVo.setParamsKey(105);
            ParamsConfigVo quoteorder = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("quoteorder", quoteorder);
        }
        else if (user.getPositType() == 311) {// 产品
            paramsConfigVo.setParamsKey(107);
            ParamsConfigVo param = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            User use = new User();
            if (null != param.getParamsValue()) {
                use = userService.getUserById(Integer.valueOf(param.getParamsValue()));
            }
            mav.addObject("user", use);
            mav.setViewName("/homepage/goods/config_goods_param");
        }
        else if (user.getPositType() == 312) {// 售后
            paramsConfigVo.setParamsKey(109);
            List<ParamsConfigVo> saledefaultList = myHomePageService.getParamsConfigVoList(paramsConfigVo);
            mav.addObject("saledefaultList", saledefaultList);
            mav.setViewName("/homepage/aftersales/config_aftersales_param");
        }
        else if (user.getPositType() == 313) {
            // 快递超时天数
            paramsConfigVo.setParamsKey(106);
            ParamsConfigVo logistics = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("logistics", logistics);

            // 快递列表
            List<Logistics> logisticsList = logisticsService.getAllLogisticsList(user.getCompanyId());
            mav.addObject("logisticsList", logisticsList);

            // 发货信息paramsKey = 100
            paramsConfigVo.setParamsKey(100);
            AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
            mav.addObject("delivery", delivery);

            // 退货信息paramsKey = 102
            paramsConfigVo.setParamsKey(102);
            AddressVo ret = addressService.getDeliveryAddress(paramsConfigVo);
            mav.addObject("ret", ret);

            // 采购收货信息
            paramsConfigVo.setParamsKey(101);
            List<AddressVo> buyList = addressService.getBuyReceiveAddress(paramsConfigVo);
            mav.addObject("buyList", buyList);
            mav.setViewName("/homepage/logistics/config_logistics_param");
        }

        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 编辑参数配置
     * 
     * @param request
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月4日 上午10:47:01
     */
    @ResponseBody
    @RequestMapping(value = "/editParamsConfigPage")
    public ModelAndView editParamsConfigPage(HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView();
        ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
        paramsConfigVo.setCompanyId(user.getCompanyId());
        StringBuffer sb = new StringBuffer();
        if (user.getPositType() == 310) {// 销售
            // 报价有效期paramsKey = 103
            paramsConfigVo.setParamsKey(103);
            ParamsConfigVo quote = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("quote", quote);
            sb = sb.append(JsonUtils.translateToJson(quote));
            // 订单有效期paramsKey = 104
            paramsConfigVo.setParamsKey(104);
            ParamsConfigVo sale = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("sale", sale);
            sb = sb.append(JsonUtils.translateToJson(sale));
            // 订单有效期paramsKey = 105
            paramsConfigVo.setParamsKey(105);
            ParamsConfigVo quoteorder = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("quoteorder", quoteorder);
            sb = sb.append(JsonUtils.translateToJson(quoteorder));
            mav.setViewName("/homepage/sale/edit_config_params");
        }
        else if (user.getPositType() == 311) {// 采购
            paramsConfigVo.setParamsKey(107);
            ParamsConfigVo params = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("params", params);
            sb = sb.append(JsonUtils.translateToJson(params));
            // 先查询销售所有已启用一级部门
            List<Integer> positionType = new ArrayList<>();
            positionType.add(SysOptionConstant.ID_311);// 采购
            List<User> userList = userService.getMyUserList(user, positionType, false);
            // Organization organization = new Organization();
            // organization.setCompanyId(user.getCompanyId());
            // organization.setLevel(4);//一级销售部门
            // organization.setType(310);//销售
            // List<User> userList = myHomePageService.getSalesOneOrg(organization);
            mav.addObject("userList", userList);
            mav.setViewName("/homepage/goods/edit_config_params");
        }
        else if (user.getPositType() == 312) {// 售后
            // 默认负责人
            paramsConfigVo.setParamsKey(108);
            ParamsConfigVo defaults = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("defaults", defaults);
            sb = sb.append(JsonUtils.translateToJson(defaults));
            // 一级销售部门的售后负责人
            paramsConfigVo.setParamsKey(109);
            List<ParamsConfigVo> saledefaultList = myHomePageService.getParamsConfigVoList(paramsConfigVo);
            mav.addObject("saledefaultList", saledefaultList);
            sb = sb.append(JsonUtils.translateToJson(saledefaultList));
            // 先查询销售所有已启用一级部门
            Organization organization = new Organization();
            organization.setCompanyId(user.getCompanyId());
            organization.setType(310);// 销售
            organization.setLevel(4);
            List<OrganizationVo> org4List = orgService.getOrganizationVoList(organization);// 一级销售部门

            organization.setLevel(5);// 二级销售部门
                                     // com.vedeng.aftersales.service.impl.AfterSalesServiceImpl.getAfterSalesServiceUser(User)此方法中比对级别的值需相应修改

            List<OrganizationVo> org5List = orgService.getOrganizationVoList(organization);// 二级销售部门
            if (org4List != null && org4List.size() > 0) {
                Set<Integer> ids4List = new HashSet<>();// 有子部门
                for (OrganizationVo orgv4 : org4List) {
                    for (OrganizationVo orgv5 : org5List) {
                        if (orgv4.getOrgId().equals(orgv5.getParentId())) {
                            ids4List.add(orgv4.getOrgId());
                        }
                    }
                }
                Iterator<OrganizationVo> iterator = org4List.iterator();
                while (iterator.hasNext()) {
                    OrganizationVo orgvo = iterator.next();
                    for (Integer id : ids4List) {
                        if (orgvo.getOrgId().equals(id)) {
                            iterator.remove();
                        }
                    }
                }
                org4List.addAll(org5List);
            }
            // List<OrganizationVo> orgList = orgService.getOrganizationVoList(organization);
            mav.addObject("orgList", org4List);
            // 售后人员
            List<User> serviceUserList =
                    userService.getUserListByPositType(SysOptionConstant.ID_312, user.getCompanyId());
            mav.addObject("serviceUserList", serviceUserList);
            mav.setViewName("/homepage/aftersales/edit_config_params");
        }
        else if (user.getPositType() == 313) {
            // 快递超时paramsKey = 106
            paramsConfigVo.setParamsKey(106);
            ParamsConfigVo logistics = myHomePageService.getParamsConfigVoByParamsKey(paramsConfigVo);
            mav.addObject("logistics", logistics);
            mav.setViewName("/homepage/logistics/edit_config_params");
            sb = sb.append(JsonUtils.translateToJson(logistics));
        }
        mav.addObject("beforeParams", saveBeforeParamToRedis(sb.toString()));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑的参数设置
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月4日 上午11:24:40
     */
    @ResponseBody
    @RequestMapping(value = "/saveEditConfigParams")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑的参数设置")
    public ResultInfo<?> saveEditConfigParams(HttpServletRequest request, ParamsConfigVo paramsConfigVo,
            String beforeParams) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        int res = myHomePageService.saveEditConfigParams(user, paramsConfigVo);
        if (res == 0) {
            return new ResultInfo<>();
        }
        return new ResultInfo<>(0, "操作成功");
    }

    /**
     * <b>Description:</b><br>
     * 新增快递公司页面
     * 
     * @param request
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午1:17:09
     */
    @ResponseBody
    @RequestMapping(value = "/addLogisticsPage")
    public ModelAndView addLogisticsPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/homepage/logistics/add_logistics");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存新增快递公司
     * 
     * @param request
     * @param logistics
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 上午10:49:11
     */
    @ResponseBody
    @RequestMapping(value = "/saveAddLogistics")
    @SystemControllerLog(operationType = "add", desc = "保存新增快递公司")
    public ResultInfo<?> saveAddLogistics(HttpServletRequest request, Logistics logistics) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        return logisticsService.saveOrUpdateLogistice(user, logistics);
    }

    /**
     * <b>Description:</b><br>
     * 编辑快递公司页面
     * 
     * @param request
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午1:17:09
     */
    @ResponseBody
    @RequestMapping(value = "/editLogisticsPage")
    public ModelAndView editLogisticsPage(HttpServletRequest request, Logistics logistics) throws IOException {
        ModelAndView mav = new ModelAndView("/homepage/logistics/edit_logistics");
        logistics.setName(URLDecoder.decode(URLDecoder.decode(logistics.getName(), "UTF-8"), "UTF-8"));
        mav.addObject("logistics", logistics);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(logistics)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑快递公司
     * 
     * @param request
     * @param logistics
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 上午10:49:11
     */
    @ResponseBody
    @RequestMapping(value = "/saveEditLogistics")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑快递公司")
    public ResultInfo<?> saveEditLogistics(HttpServletRequest request, Logistics logistics, String beforeParams) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        return logisticsService.saveOrUpdateLogistice(user, logistics);
    }

    /**
     * <b>Description:</b><br>
     * 设置默认的快递公司
     * 
     * @param request
     * @param logistics
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午2:29:35
     */
    @ResponseBody
    @RequestMapping(value = "/saveSetDefaultLogistics")
    @SystemControllerLog(operationType = "edit", desc = "设置默认的快递公司")
    public ResultInfo<?> saveSetDefaultLogistics(HttpServletRequest request, Logistics logistics) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        return logisticsService.saveSetDefaultLogistics(user, logistics);
    }

    /**
     * <b>Description:</b><br>
     * 新增发货地址
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午3:42:19
     */
    @ResponseBody
    @RequestMapping(value = "/addDeliveryAddressPage")
    public ModelAndView addDeliveryAddressPage(HttpServletRequest request, Address address) {
        ModelAndView mav = new ModelAndView("/homepage/logistics/add_delivery_address");
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存新增发货信息
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午5:11:15
     */
    @ResponseBody
    @RequestMapping(value = "/saveAddDeliveryAdderss")
    @SystemControllerLog(operationType = "add", desc = "保存新增发货信息")
    public ResultInfo<?> saveAddDeliveryAdderss(HttpServletRequest request, AddressVo addressVo) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        addressVo.setType(1);// 发货
        int res = myHomePageService.saveAddressAndConfigParamsValue(addressVo, user, 1);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功！");
        }

    }

    /**
     * <b>Description:</b><br>
     * 编辑发货地址
     * 
     * @param request
     * @param address
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午3:42:19
     */
    @ResponseBody
    @RequestMapping(value = "/editDeliveryAddressPage")
    public ModelAndView editDeliveryAddressPage(HttpServletRequest request, Address address) throws IOException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView("/homepage/logistics/edit_delivery_address");
        ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
        paramsConfigVo.setCompanyId(user.getCompanyId());
        paramsConfigVo.setParamsKey(100);
        AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
        if (delivery.getAreaIds().split(",").length == 3) {
            List<Region> list = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[1]));
            mav.addObject("zoneList", list);
            List<Region> cys = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[0]));
            mav.addObject("cityList", cys);
            mav.addObject("zone", Integer.valueOf(delivery.getAreaIds().split(",")[2]));
            mav.addObject("city", Integer.valueOf(delivery.getAreaIds().split(",")[1]));
        }
        else if (delivery.getAreaIds().split(",").length == 2) {
            List<Region> list = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[0]));
            mav.addObject("cityList", list);
            mav.addObject("city", Integer.valueOf(delivery.getAreaIds().split(",")[1]));
        }
        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        mav.addObject("province", Integer.valueOf(delivery.getAreaIds().split(",")[0]));
        mav.addObject("address", delivery);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(delivery)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑发货信息
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午5:11:15
     */
    @ResponseBody
    @RequestMapping(value = "/saveEditDeliveryAdderss")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑发货信息")
    public ResultInfo<?> saveEditDeliveryAdderss(HttpServletRequest request, AddressVo addressVo, String beforeParams) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        addressVo.setType(1);// 发货
        int res = myHomePageService.saveAddressAndConfigParamsValue(addressVo, user, 1);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功！");
        }

    }

    /**
     * <b>Description:</b><br>
     * 新增收货地址
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午3:42:19
     */
    @ResponseBody
    @RequestMapping(value = "/addReceiveAddressPage")
    public ModelAndView addReceiveAddressPage(HttpServletRequest request, Address address) {
        ModelAndView mav = new ModelAndView("/homepage/logistics/add_receive_address");
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存新增收货信息
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午5:11:15
     */
    @ResponseBody
    @RequestMapping(value = "/saveAddReceiveAdderss")
    @SystemControllerLog(operationType = "add", desc = "保存新增收货信息")
    public ResultInfo<?> saveAddReceiveAdderss(HttpServletRequest request, AddressVo addressVo) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        addressVo.setType(2);// 收货
        int res = myHomePageService.saveOrUpdateAddress(addressVo, user);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功！");
        }

    }

    /**
     * <b>Description:</b><br>
     * 编辑收货地址
     * 
     * @param request
     * @param address
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午3:42:19
     */
    @ResponseBody
    @RequestMapping(value = "/editReceiveAddressPage")
    public ModelAndView editReceiveAddressPage(HttpServletRequest request, Address address) throws IOException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView("/homepage/logistics/edit_receive_address");
        ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
        paramsConfigVo.setCompanyId(user.getCompanyId());
        paramsConfigVo.setParamsKey(101);
        AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
        if (delivery.getAreaIds().split(",").length == 3) {
            List<Region> list = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[1]));
            mav.addObject("zoneList", list);
            List<Region> cys = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[0]));
            mav.addObject("cityList", cys);
            mav.addObject("zone", Integer.valueOf(delivery.getAreaIds().split(",")[2]));
            mav.addObject("city", Integer.valueOf(delivery.getAreaIds().split(",")[1]));
        }
        else if (delivery.getAreaIds().split(",").length == 2) {
            List<Region> list = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[0]));
            mav.addObject("cityList", list);
            mav.addObject("city", Integer.valueOf(delivery.getAreaIds().split(",")[1]));
        }
        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        mav.addObject("province", Integer.valueOf(delivery.getAreaIds().split(",")[0]));
        mav.addObject("address", delivery);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(delivery)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑收货信息
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午5:11:15
     */
    @ResponseBody
    @RequestMapping(value = "/saveEditReceiveAdderss")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑收货信息")
    public ResultInfo<?> saveEditReceiveAdderss(HttpServletRequest request, AddressVo addressVo, String beforeParams) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        addressVo.setType(2);// 收货
        int res = myHomePageService.saveOrUpdateAddress(addressVo, user);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功！");
        }

    }

    /**
     * <b>Description:</b><br>
     * 新增售后收货地址
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午3:42:19
     */
    @ResponseBody
    @RequestMapping(value = "/addAfterSalesReceiveAddressPage")
    public ModelAndView addAfterSalesReceiveAddressPage(HttpServletRequest request, Address address) {
        ModelAndView mav = new ModelAndView("/homepage/logistics/add_aftersales_receive_address");
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存新增售后收货信息
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午5:11:15
     */
    @ResponseBody
    @RequestMapping(value = "/saveAddAfterSalesReceiveAdderss")
    @SystemControllerLog(operationType = "add", desc = "保存新增售后收货信息")
    public ResultInfo<?> saveAddAfterSalesReceiveAdderss(HttpServletRequest request, AddressVo addressVo) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        addressVo.setType(3);// 退货
        int res = myHomePageService.saveAddressAndConfigParamsValue(addressVo, user, 3);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功！");
        }

    }

    /**
     * <b>Description:</b><br>
     * 编辑售后收货地址
     * 
     * @param request
     * @param address
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午3:42:19
     */
    @ResponseBody
    @RequestMapping(value = "/editAfterSalesReceiveAddressPage")
    public ModelAndView editAfterSalesReceiveAddressPage(HttpServletRequest request, Address address)
            throws IOException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView("/homepage/logistics/edit_aftersales_receive_address");
        ParamsConfigVo paramsConfigVo = new ParamsConfigVo();
        paramsConfigVo.setCompanyId(user.getCompanyId());
        paramsConfigVo.setParamsKey(102);
        AddressVo delivery = addressService.getDeliveryAddress(paramsConfigVo);
        if (delivery.getAreaIds().split(",").length == 3) {
            List<Region> list = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[1]));
            mav.addObject("zoneList", list);
            List<Region> cys = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[0]));
            mav.addObject("cityList", cys);
            mav.addObject("zone", Integer.valueOf(delivery.getAreaIds().split(",")[2]));
            mav.addObject("city", Integer.valueOf(delivery.getAreaIds().split(",")[1]));
        }
        else if (delivery.getAreaIds().split(",").length == 2) {
            List<Region> list = regionService.getRegionByParentId(Integer.valueOf(delivery.getAreaIds().split(",")[0]));
            mav.addObject("cityList", list);
            mav.addObject("city", Integer.valueOf(delivery.getAreaIds().split(",")[1]));
        }
        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        mav.addObject("province", Integer.valueOf(delivery.getAreaIds().split(",")[0]));
        mav.addObject("address", delivery);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(delivery)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑售后收货信息
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月5日 下午5:11:15
     */
    @ResponseBody
    @RequestMapping(value = "/saveEditAfterSalesReceiveAdderss")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑售后收货信息")
    public ResultInfo<?> saveEditAfterSalesReceiveAdderss(HttpServletRequest request, AddressVo addressVo,
            String beforeParams) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        addressVo.setType(3);// 退货
        int res = myHomePageService.saveAddressAndConfigParamsValue(addressVo, user, 3);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功！");
        }

    }

    /**
     * <b>Description:</b><br>
     * 设置采购收货默认
     * 
     * @param request
     * @param address
     * @return
     * @Note <b>Author:</b> east <br>
     *       <b>Date:</b> 2017年12月6日 下午1:11:34
     */
    @ResponseBody
    @RequestMapping(value = "/saveSetDefaultBuyAddress")
    @SystemControllerLog(operationType = "edit", desc = "设置采购收货默认")
    public ResultInfo<?> saveSetDefaultBuyAddress(HttpServletRequest request, Address address, String beforeParams) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        int res = myHomePageService.saveSetDefaultBuyAddress(address, user);
        if (res == 0) {
            return new ResultInfo<>();
        }
        else {
            return new ResultInfo<>(0, "操作成功");
        }
    }

    /**
     * 首页拨打电话
     * 
     * @param request
     * @return <b>Author:</b> Barry <br>
     *         <b>Date:</b> 2018年9月17日 上午 9:41:31
     */
    @ResponseBody
    @RequestMapping(value = "/contact")
    public ModelAndView contectPerson(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/homepage/sale/cross_departmental_contact_person");
        return mv;
    }
}
