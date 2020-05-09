package com.vedeng.trader.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newtask.model.YXGTraderAptitude;
import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.authorization.model.vo.RoleVo;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.CommonConstants;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.ReqVo;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.*;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.CapitalBill;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.goods.service.VgoodsService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.model.vo.SaleorderGoodsVo;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.dao.VerifiesInfoMapper;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.model.VerifiesInfo;
import com.vedeng.system.model.vo.AccountSalerToGo;
import com.vedeng.system.service.*;
import com.vedeng.trader.dao.WebAccountMapper;
import com.vedeng.trader.model.*;
import com.vedeng.trader.model.vo.*;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <b>Description:</b><br>
 * 客户+经销商
 *
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 * <b>PackageName:</b> com.vedeng.trader.controller <br>
 * <b>ClassName:</b> TraderController <br>
 * <b>Date:</b> 2017年5月10日 上午9:38:28
 */

/**
 * <b>Description:</b><br>
 *
 * @author Jerry
 * @Note <b>ProjectName:</b> erp
 * <br><b>PackageName:</b> com.vedeng.trader.controller
 * <br><b>ClassName:</b> TraderCustomerController
 * <br><b>Date:</b> 2017年6月5日 下午5:42:35
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/trader/customer")
public class TraderCustomerController extends BaseController {
    Logger logger= LoggerFactory.getLogger(TraderCustomerController.class);
    @Autowired
    @Qualifier("regionService")
    private RegionService regionService;// 自动注入regionService

    @Autowired // 自动装载
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Autowired // 自动装载
    @Qualifier("historyService")
    private HistoryService historyService;
    //	@Autowired
//	@Qualifier("traderCustomerService")
    @Resource
    private TraderCustomerService traderCustomerService;

    @Autowired
    @Qualifier("sysOptionDefinitionService")
    private SysOptionDefinitionService sysOptionDefinitionService;
    @Resource
    private UserService userService;
    @Resource
    private PositService positService;
    @Autowired
    @Qualifier("tagService")
    private TagService tagService;

    @Autowired
    @Qualifier("verifiesRecordService")
    private VerifiesRecordService verifiesRecordService;

    @Resource
    private DictionaryService dictionaryService;

    @Autowired
    @Qualifier("ftpUtilService")
    private FtpUtilService ftpUtilService;


    @Autowired
    @Qualifier("actionProcdefService")
    private ActionProcdefService actionProcdefService;

    @Autowired
    @Qualifier("saleorderService")
    private SaleorderService saleorderService;

    @Autowired
    @Qualifier("vedengSoapService")
    private VedengSoapService vedengSoapService;

    @Autowired
    @Qualifier("orgService")
    protected OrgService orgService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VgoodsService vGoodsService;
    @Resource
    WebAccountMapper webAccountMapper;


    @RequestMapping(value = "/changeTraderOwnerPage")
    public ModelAndView changeTraderOwnerPage(){
        return new ModelAndView("trader/customer/batch_change_trader_owner");
    }
    /**
     * <b>Description:</b>分配客户实现模板导入批量划拨客户的功能<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b> 
     * <br><b>Date:</b> 2019/12/20
     */
    @ResponseBody
    @RequestMapping(value = "/batchChangeTraderOwner")
    public ResultInfo<?> batchChangeTraderOwner(HttpServletRequest request,
                                                @RequestParam("lwfile") MultipartFile lwfile) throws IOException{
        FileInputStream fileInputStream=null;
        try {
            User currentUser = (User) request.getSession().getAttribute(Consts.SESSION_USER);
            String path = request.getSession().getServletContext().getRealPath("/upload/saleorder");
            FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);

            if (fileInfo.getCode() == 0) {// 上传成功

                List<RTraderJUser> traderJUsers = new ArrayList<>();
                fileInputStream=new FileInputStream(new File(fileInfo.getFilePath()));
                // 获取excel路径
                Workbook workbook = WorkbookFactory.create(fileInputStream);
                // 获取第一面sheet
                Sheet sheet = workbook.getSheetAt(0);
                // 起始行
                int startRowNum = sheet.getFirstRowNum() + 1;// 第一行标题
                int endRowNum = sheet.getLastRowNum();// 结束行

                int startCellNum = 0;// 默认从第一列开始（防止第一列为空）
                // int endCellNum = sheet.getRow(0).getLastCellNum() - 1;//列数
                List<Trader> list = new ArrayList<>();// 保存Excel中读取的数据

                Map<Integer, List> traderIdMap = new HashMap<>();
                for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
                    // 循环行数
                    Row row = sheet.getRow(rowNum);
                    Cell cellOne = row.getCell(0);
                    Cell cellTwo = row.getCell(1);
                    int r = rowNum + 1;
                    if (cellOne == null || cellOne.equals("")) {

                        return new ResultInfo<>(-1, String.format(TraderConstants.ROW_TRADER_NAME_IS_NULL, r));
                    }
                    if (cellTwo == null || cellTwo.equals("")) {
                        return new ResultInfo<>(-1, String.format(TraderConstants.ROW_USER_NAME_IS_NULL, r));
                    }

                    String traderName = cellOne.getStringCellValue();
                    String userName = cellTwo.getStringCellValue();
                    Trader trader = new Trader();
                    trader.setTraderName(traderName);
                    trader.setTraderType(1);
                    trader = traderCustomerService.getTraderByTraderName(trader, 1);

                    if (trader == null || trader.getTraderId() == null) {
                        return new ResultInfo<>(-1, String.format(TraderConstants.ROW_TRADER_IS_NOT_EXIST, r));
                    }

                    User user = userService.getUserByNameAndPositionType(userName, TraderConstants.SALESPERSON_POSITION_TYPE);

                    if (user == null || user.getUserId() == null) {
                        return new ResultInfo<>(-1, String.format(TraderConstants.ROW_USER_IS_NOT_EXIST, r));
                    }
                    if (trader.getSource() != null && trader.getSource() == 1) {
                        if (traderIdMap.containsKey(user.getUserId())) {
                            traderIdMap.get(user.getUserId()).add(trader.getTraderId());
                        } else {
                            List<Integer> traderIds = new ArrayList<>();
                            traderIds.add(trader.getTraderId());
                            traderIdMap.put(user.getUserId(), traderIds);
                        }
                    }
                    RTraderJUser traderJUser = new RTraderJUser();
                    traderJUser.setCompanyId(1);
                    traderJUser.setTraderId(trader.getTraderId());
                    traderJUser.setUserId(user.getUserId());
                    traderJUser.setSource(trader.getSource());
                    traderJUser.setTraderType(1);
                    traderJUser.setUserName(user.getUsername());
                    traderJUsers.add(traderJUser);

                }

                for (RTraderJUser t : traderJUsers) {
                    traderCustomerService.changeTraderOwner(t);
                    if (t.getSource() != null && t.getSource() == 1 && traderIdMap.containsKey(t.getUserId())) {
                        Map<String, Object> map = Maps.newHashMap();
                        map.put("salerId", t.getUserId());
                        map.put("salerName", t.getUserName());
                        map.put("traderIdList", traderIdMap.get(t.getUserId()));
                        ResultInfo resultInfo = traderCustomerService.putTraderSaleUserIdtoHC(map);
                        traderIdMap.remove(t.getUserId());
                    }
                }
            }
        } catch (Exception ex) {

            logger.error("批量导入失败", ex);
            return new ResultInfo<>(-1, "操作失败");
        }finally {
            if (fileInputStream != null) {
                fileInputStream.close();
                fileInputStream = null;
            }

        }
        return new ResultInfo<>(0, "操作成功");
    }
    /**
     * <b>Description:</b><br>
     * 客户+经销商列表
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月10日 上午9:40:51
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, TraderCustomerVo traderCustomerVo,
                              @RequestParam(required = false, defaultValue = "1") Integer pageType,
                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                              @RequestParam(required = false) Integer pageSize) {
        // 客户列表页面
        return searchTraderListPage(request, traderCustomerVo, pageType, pageNo, pageSize);
    }

    /**
     * <b>Description: 耗材商城的客户列表</b><br>
     *
     * @param request
     * @param traderCustomerVo
     * @param pageType         [页面类型] 默认1--erp的客户列表页面/2--耗材商城的客户列表页面
     * @param pageNo
     * @param pageSize
     * @return <b>Author: Franlin.wu</b>
     * <br><b>Date: 2018年11月21日 下午6:53:47 </b>
     */
    @RequestMapping(value = "/hcTraderListPage")
    public ModelAndView hcTraderListPage(HttpServletRequest request, TraderCustomerVo traderCustomerVo,
                                         @RequestParam(required = false, defaultValue = "0") Integer pageType,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                         @RequestParam(required = false) Integer pageSize) {
        // 客户来源： 0 erp / 默认1耗材
        traderCustomerVo.setSource(ErpConst.ONE);
        return searchTraderListPage(request, traderCustomerVo, pageType, pageNo, pageSize);
    }


    /**
     * <b>Description:</b>判断该客户财务信息是否允许开票<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/12/11
     */
    @ResponseBody
    @RequestMapping(value="/isTraderAllowInvoice",method = RequestMethod.POST)
    public ResultInfo isTraderAllowInvoice(Saleorder order) {
        if (order == null || order.getTraderId() == null || order.getInvoiceType() == null || StringUtil.isBlank(order.getTraderName())) {
            return new ResultInfo(-1, "该客户信息不全，不能开票");
        }
        String traderName = order.getTraderName();
        Integer invoiceType = order.getInvoiceType();
        Trader trader = traderCustomerService.getBaseTraderByTraderId(order.getTraderId());

       if (trader != null) {
            if ((invoiceType == 430 || invoiceType == 681 || invoiceType == 683 || invoiceType == 685 || invoiceType == 971
                    || invoiceType == 687)) {
                TraderFinanceVo financeVo = new TraderFinanceVo();
                financeVo.setTraderId(order.getTraderId());
                financeVo = traderCustomerService.getTraderFinance(financeVo);
                if (financeVo == null) {
                    return new ResultInfo(-1, "该客户没有财务信息，无法开票");
                }
                if (StringUtil.isBlank(financeVo.getTaxNum()) && (
                        financeVo.getCheckStatus() == null ||
                                financeVo.getCheckStatus() == 0 || financeVo.getCheckStatus() == 2)) {
                    return new ResultInfo(-1, "税号为空，需财务审核通过后才可开票");
                }
            }
        }

        return new ResultInfo(0, "允许开票");
    }
    /**
     * <b>Description: 客户列表页面</b><br>
     *
     * @param request
     * @param traderCustomerVo
     * @param pageType         [页面类型] 默认1--erp的客户列表页面/2--耗材商城的客户列表页面
     * @param pageNo
     * @param pageSize
     * @return <b>Author: Franlin.wu</b>
     * <br><b>Date: 2018年11月21日 下午6:39:59 </b>
     */
    private ModelAndView searchTraderListPage(HttpServletRequest request, TraderCustomerVo traderCustomerVo,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageType,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                              @RequestParam(required = false) Integer pageSize) {
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request, pageNo, pageSize);
        // 查询所有职位类型为310的员工
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);//销售
        List<User> userList = userService.getMyUserList(user, positionType, false);
        if (userList == null || userList.size() == 0) {
            userList = new ArrayList<User>();
            userList.add(new User());
        }
        traderCustomerVo.setCompanyId(user.getCompanyId());
        List<Integer> userIdList = new ArrayList<>();

        String search = request.getParameter("search");
        // modify by frinlin.wu for[耗材商城客户列表] to add[!ErpConst.ZERO.equals(pageType)] at 2018-11-22 begin
        if ((search == null || "".equals(search)) && !ErpConst.ZERO.equals(pageType)) {
            // modify by frinlin.wu for[耗材商城客户列表] !ErpConst.ZERO.equals(pageType) at 2018-11-22 end
            for (User us : userList) {
                userIdList.add(us.getUserId());
            }
        } else {
            if (ObjectUtils.notEmpty(traderCustomerVo.getHomePurchasing())) {
                userIdList.add(traderCustomerVo.getHomePurchasing());
            }
//			else{
//				for (User us : userList) {
//					userIdList.add(us.getUserId());
//				}
//			}

            if (ObjectUtils.notEmpty(traderCustomerVo.getZone())) {
                traderCustomerVo.setAreaId(traderCustomerVo.getZone());
                List<Region> list = regionService.getRegionByParentId(traderCustomerVo.getCity());
                mv.addObject("zoneList", list);
                List<Region> cys = regionService.getRegionByParentId(traderCustomerVo.getProvince());
                mv.addObject("cityList", cys);
            } else if (ObjectUtils.notEmpty(traderCustomerVo.getProvince()) && ObjectUtils.notEmpty(traderCustomerVo.getCity()) && ObjectUtils.isEmpty(traderCustomerVo.getZone())) {
                traderCustomerVo.setAreaId(traderCustomerVo.getCity());
                List<Region> list = regionService.getRegionByParentId(traderCustomerVo.getCity());
                mv.addObject("zoneList", list);
                List<Region> cys = regionService.getRegionByParentId(traderCustomerVo.getProvince());
                mv.addObject("cityList", cys);
            } else if (ObjectUtils.notEmpty(traderCustomerVo.getProvince()) && ObjectUtils.isEmpty(traderCustomerVo.getCity()) && ObjectUtils.isEmpty(traderCustomerVo.getZone())) {
                traderCustomerVo.setAreaId(traderCustomerVo.getProvince());
                List<Region> cys = regionService.getRegionByParentId(traderCustomerVo.getProvince());
                mv.addObject("cityList", cys);
            } else {
                traderCustomerVo.setAreaId(null);
            }
            if (ObjectUtils.notEmpty(traderCustomerVo.getQueryStartTime())) {
                traderCustomerVo.setStartTime(DateUtil.convertLong(traderCustomerVo.getQueryStartTime(), "yyyy-MM-dd"));
            }
            if (ObjectUtils.notEmpty(traderCustomerVo.getQueryEndTime())) {
                traderCustomerVo.setEndTime(DateUtil.convertLong(traderCustomerVo.getQueryEndTime() + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
        }
        if (userIdList == null || userIdList.size() == 0) {
            //userIdList.add(-1);//防止关联不到用户全表查询
            userIdList = null;
        }
        traderCustomerVo.setUserIdList(userIdList);

        // 客户分页列表信息
        Map<String, Object> map = this.traderCustomerService.getTraderCustomerVoPage(traderCustomerVo, page, userList);
        List<TraderCustomerVo> list = null;
        if (map != null) {
            list = (List<TraderCustomerVo>) map.get("list");
            page = (Page) map.get("page");
        }
        mv.addObject("list", list);


        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        //客户等级
        List<SysOptionDefinition> customerLevers = getSysOptionDefinitionList(SysOptionConstant.ID_11);

        mv.addObject("customerLevers", customerLevers);
        //销售评级
        List<SysOptionDefinition> salersLevers = getSysOptionDefinitionList(SysOptionConstant.ID_12);

        mv.addObject("salersLevers", salersLevers);
        //战略合作伙伴
        List<SysOptionDefinition> partners = getSysOptionDefinitionList(SysOptionConstant.ID_303);

        mv.addObject("partners", partners);
        mv.addObject("userList", userList);
        mv.addObject("provinceList", provinceList);

        mv.addObject("traderCustomerVo", traderCustomerVo);
        mv.addObject("page", page);

        // modify by franlin.wu for[耗材商城的客户同步，新增客户来源类型] begin
        // 1 erp客户列表
        if (ErpConst.ONE == pageType) {
            mv.setViewName("trader/customer/index");
        }
        // 其他页面传的是初始化0/2 耗材商城客户列表
        else {
            mv.setViewName("trader/customer/hc/hc_trader_list");
        }
        // modify by franlin.wu for[耗材商城的客户同步，新增客户来源类型] end

        return mv;
    }

    /**
     * <b>Description:</b><br> 判断当前是否有搜索条件
     *
     * @param traderCustomerVo
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年8月10日 下午4:34:06
     */
    private boolean isNUllTraderCustomerVo(TraderCustomerVo traderCustomerVo) {
        if (ObjectUtils.isEmpty(traderCustomerVo.getName()) && ObjectUtils.isEmpty(traderCustomerVo.getHomePurchasing())
                && ObjectUtils.isEmpty(traderCustomerVo.getCustomerType()) && ObjectUtils.isEmpty(traderCustomerVo.getCustomerProperty())
                && ObjectUtils.isEmpty(traderCustomerVo.getCooperate()) && ObjectUtils.isEmpty(traderCustomerVo.getQuote())
                && ObjectUtils.isEmpty(traderCustomerVo.getSearchMsg()) && ObjectUtils.isEmpty(traderCustomerVo.getCustomerLevel())
                && ObjectUtils.isEmpty(traderCustomerVo.getUserEvaluate()) && ObjectUtils.isEmpty(traderCustomerVo.getPartnerId())
                && ObjectUtils.isEmpty(traderCustomerVo.getContactWay()) && ObjectUtils.isEmpty(traderCustomerVo.getCustomerStatus())
                && ObjectUtils.isEmpty(traderCustomerVo.getWxStatus()) && ObjectUtils.isEmpty(traderCustomerVo.getQueryEndTime())
                && ObjectUtils.isEmpty(traderCustomerVo.getQueryStartTime()) && ObjectUtils.isEmpty(traderCustomerVo.getProvince())
                && ObjectUtils.isEmpty(traderCustomerVo.getCity()) && ObjectUtils.isEmpty(traderCustomerVo.getZone())
        ) {
            return true;
        }
        return false;
    }


    /**
     * <b>Description:</b><br>
     * 置顶
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isStick")
    @SystemControllerLog(operationType = "edit", desc = "置顶/取消置顶客户")
    public ResultInfo isStick(TraderCustomer traderCustomer, HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        ResultInfo ri = traderCustomerService.isStick(traderCustomer, user);
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 初始化禁用页面
     *
     * @param request
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月22日 下午2:09:38
     */
    @ResponseBody
    @RequestMapping(value = "/initDisabledPage")
    public ModelAndView initDisabledPage(HttpServletRequest request, TraderCustomer traderCustomer) {
        ModelAndView mav = new ModelAndView("trader/customer/forbid");
        mav.addObject("traderCustomer", traderCustomer);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 禁用客户
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDisabledCustomer")
    @SystemControllerLog(operationType = "edit", desc = "禁用/启用客户")
    public ResultInfo isDisabledCustomer(TraderCustomer traderCustomer, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDisabled(user, traderCustomer);
        if (ri == null) {
            ri = new ResultInfo<>();
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        ri.setData(traderCustomer.getTraderId() + "," + traderCustomer.getTraderCustomerId());
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 禁用客户联系人
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDisabledContact")
    @SystemControllerLog(operationType = "edit", desc = "启用/禁用客户联系人")
    public ResultInfo isDisabledContact(TraderContact traderContact, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDisabledContact(traderContact, user);
        if (ri == null) {
            ri = new ResultInfo<>();
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 设置客户默认联系人
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("all")
    @ResponseBody
    @RequestMapping(value = "/saveCustomerDefaultContact")
    @SystemControllerLog(operationType = "edit", desc = "设置客户默认联系人")
    public ResultInfo saveCustomerDefaultContact(TraderContact traderContact, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDefaultContact(traderContact, user);
        if (ri != null && ri.getCode() == 0) {
            ri.setData(traderContact.getTraderId() + "," + request.getParameter("traderCustomerId"));
        }
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 禁用/启用客户地址
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDisabledAddress")
    @SystemControllerLog(operationType = "edit", desc = "禁用/启用客户地址")
    public ResultInfo isDisabledAddress(TraderAddress traderAddress, HttpSession session) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDisabledAddress(traderAddress, user);
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 设置客户默认地址
     *
     * @param id
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月17日 上午10:34:37
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/isDefaultAddress")
    @SystemControllerLog(operationType = "edit", desc = "设置客户默认地址")
    public ResultInfo isDefaultAddress(TraderAddress traderAddress, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ResultInfo ri = traderCustomerService.isDefaultAddress(traderAddress, user);
        if (ri == null) {
            ri = new ResultInfo<>();
            ri.setCode(-1);
            ri.setMessage("操作失败");
        }
        if (traderAddress.getTraderType() == 1) {
            ri.setData(traderAddress.getTraderId() + "," + request.getParameter("traderCustomerId"));
        } else {
            ri.setData(traderAddress.getTraderId() + "," + request.getParameter("traderSupplierId"));
        }
        return ri;
    }

    /**
     * <b>Description:</b><br>
     * 新增客户
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月10日 上午10:21:26
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/add")
    public ModelAndView add(String traderName) {
        ModelAndView mv = new ModelAndView();
        TraderInfoTyc tycInfo = new TraderInfoTyc();
        if (traderName != null && !"".equals(traderName)) {
            tycInfo = traderCustomerService.getTycInfo(2, traderName);
            mv.addObject("tycInfo", tycInfo);
            mv.addObject("traderName", traderName);
        }
        // 地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mv.addObject("provinceList", provinceList);
        mv.setViewName("trader/customer/add_customer");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存新增客户信息
     *
     * @param traderCustomer
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月15日 上午11:07:28
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveadd")
    @SystemControllerLog(operationType = "add", desc = "保存新增客户信息")
    public ModelAndView saveAdd(Trader trader, HttpServletRequest request, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        TraderCustomer traderCustomer;
        try {
            trader.setTraderName(trader.getTraderName().trim());
            traderCustomer = traderCustomerService.saveCustomer(trader, request, session);
            if (null != traderCustomer) {
                mv.addObject("url", "./baseinfo.do?traderId=" + traderCustomer.getTraderId() + "&traderCustomerId="
                        + traderCustomer.getTraderCustomerId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("trader customer saveadd:", e);
            return fail(mv);
        }
    }

    /**
     * <b>Description:</b><br>
     * 客户详情
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月10日 上午11:04:33
     */
    @ResponseBody
    @RequestMapping(value = "/view")
    public ModelAndView view(TraderCustomer traderCustomer) {
        if (StringUtils.isEmpty(traderCustomer) || null == traderCustomer.getTraderCustomerId()
                || traderCustomer.getTraderCustomerId() <= 0) {
            return pageNotFound();
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("traderCustomer", traderCustomer);
        mv.setViewName("trader/customer/view_customer");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 基本信息
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月10日 上午11:44:59
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/baseinfo")
    public ModelAndView baseinfo(TraderCustomer traderCustomer, HttpServletRequest request, String traderName) {
        if ((null == traderCustomer.getTraderCustomerId() && null == traderCustomer.getTraderId())
                || ((null != traderCustomer.getTraderCustomerId() && traderCustomer.getTraderCustomerId() == 0)
                || (null != traderCustomer.getTraderId() && traderCustomer.getTraderId() == 0))) {
            return pageNotFound();
        }
        ModelAndView mv = new ModelAndView();
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mv.addObject("curr_user", curr_user);
        TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
        //审核人
        if (null != traderBaseInfo.getVerifyUsername()) {
            List<String> verifyUsernameList = Arrays.asList(traderBaseInfo.getVerifyUsername().split(","));
            traderBaseInfo.setVerifyUsernameList(verifyUsernameList);
        }
        // 地区
        if (null != traderBaseInfo.getTrader().getAreaId() && traderBaseInfo.getTrader().getAreaId() > 0) {
            String region = (String) regionService.getRegion(traderBaseInfo.getTrader().getAreaId(), 2);
            mv.addObject("region", region);
        }

        // 经营地区
        if (null != traderBaseInfo.getTraderCustomerBussinessAreas()) {
            for (TraderCustomerBussinessArea bussinessArea : traderBaseInfo.getTraderCustomerBussinessAreas()) {
                String region = (String) regionService.getRegion(bussinessArea.getAreaId(), 2);
                bussinessArea.setAreaStr(region);
            }
        }

        //是否分销
        List<TraderCustomerCategory> customerCategories = traderBaseInfo.getCustomerCategories();

        Boolean show_fenxiao = false;
        if (customerCategories != null && customerCategories.size() > 0) {
            for (TraderCustomerCategory c : customerCategories) {
                if (c.getTraderCustomerCategoryId() == 3
                        || c.getTraderCustomerCategoryId() == 5) {
                    show_fenxiao = true;
                }
            }
        }
        TraderInfoTyc tycInfo = new TraderInfoTyc();
        if (traderName != null && !"".equals(traderName)) {
            tycInfo = traderCustomerService.getTycInfo(2, traderName);
            mv.addObject("tycInfo", tycInfo);
            mv.addObject("traderName", traderName);
        }
        Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "traderCustomerVerify_" + traderBaseInfo.getTraderCustomerId());
        mv.addObject("taskInfo", historicInfo.get("taskInfo"));
        mv.addObject("startUser", historicInfo.get("startUser"));
        mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
        // 最后审核状态
        mv.addObject("endStatus", historicInfo.get("endStatus"));
        mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
        mv.addObject("commentMap", historicInfo.get("commentMap"));

        //客户名称审核记录
        Map<String, Object> historicInfoName = actionProcdefService.getHistoric(processEngine, "editTraderCustomerName_" + traderBaseInfo.getTraderId());
        mv.addObject("taskInfoName", historicInfoName.get("taskInfo"));
        mv.addObject("startUserName", historicInfoName.get("startUser"));
        mv.addObject("candidateUserMapName", historicInfoName.get("candidateUserMap"));
        // 最后审核状态
        mv.addObject("endStatusName", historicInfoName.get("endStatus"));
        mv.addObject("historicActivityInstanceName", historicInfoName.get("historicActivityInstance"));
        mv.addObject("commentMapName", historicInfoName.get("commentMap"));

        mv.addObject("traderCustomer", traderBaseInfo);
        mv.addObject("show_fenxiao", show_fenxiao);
        mv.addObject("method", "baseinfo");
        Task taskInfo = (Task) historicInfo.get("taskInfo");
        Task taskInfoName = (Task) historicInfoName.get("taskInfo");
        //当前审核人
        String verifyUsers = null;
        if (null != taskInfo) {
            Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfo);
            verifyUsers = (String) taskInfoVariables.get("verifyUsers");
        }
        mv.addObject("verifyUsers", verifyUsers);

        //当前审核人
        String verifyUsersName = null;
        Trader traderInfo = null;
        if (null != taskInfoName) {
            Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap(taskInfoName);
            verifyUsersName = (String) taskInfoVariables.get("verifyUsers");
            traderInfo = (Trader) taskInfoVariables.get("trader");
        }
        mv.addObject("traderInfo", traderInfo);
        mv.addObject("verifyUsersName", verifyUsersName);
        mv.setViewName("trader/customer/view_baseinfo");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 客户申请审核
     *
     * @param request
     * @param afterSales
     * @return
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年10月24日 下午18:42:13
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/editApplyValidCustomer")
    public ResultInfo<?> editApplyValidCustomer(HttpServletRequest request, TraderCustomer traderCustomer, String taskId, HttpSession session) {
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            // 查询当前订单的一些状态
            TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
            //开始生成流程(如果没有taskId表示新流程需要生成)
            if (taskId.equals("0")) {
                variableMap.put("traderCustomerVo", traderBaseInfo);
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "traderCustomerVerify");
                variableMap.put("businessKey", "traderCustomerVerify_" + traderBaseInfo.getTraderCustomerId());
                variableMap.put("relateTableKey", traderBaseInfo.getTraderCustomerId());
                variableMap.put("relateTable", "T_TRADER_CUSTOMER");
                actionProcdefService.createProcessInstance(request, "traderCustomerVerify", "traderCustomerVerify_" + traderBaseInfo.getTraderCustomerId(), variableMap);
            }
            //默认申请人通过
            //根据BusinessKey获取生成的审核实例
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "traderCustomerVerify_" + traderBaseInfo.getTraderCustomerId());
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                Authentication.setAuthenticatedUserId(user.getUsername());
                Map<String, Object> variables = new HashMap<String, Object>();
                //默认审批通过
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variables);
                //如果未结束添加审核对应主表的审核状态
                if (complementStatus != null && complementStatus.getData() != null && !complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfo(taskId, 0);
                }
            }
            //更新贝登会员
            traderCustomerService.updateVedengMember();
            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("editApplyValidCustomer:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }

    }

    /**
     * <b>Description:</b><br>
     * 管理信息
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月10日 上午11:45:33
     */
    @ResponseBody
    @RequestMapping(value = "/manageinfo")
    public ModelAndView manageinfo(TraderCustomer traderCustomer, HttpSession session) {
        if ((null == traderCustomer.getTraderCustomerId() && null == traderCustomer.getTraderId())
                || ((null != traderCustomer.getTraderCustomerId() && traderCustomer.getTraderCustomerId() == 0)
                || (null != traderCustomer.getTraderId() && traderCustomer.getTraderId() == 0))) {
            return pageNotFound();
        }
        ModelAndView mv = new ModelAndView();
        TraderCustomerVo traderManageInfo = traderCustomerService.getTraderCustomerManageInfo(traderCustomer, session);

//		// 审核记录
//		VerifiesRecord verifiesRecord = new VerifiesRecord();
//		verifiesRecord.setVerifiesType(SysOptionConstant.ID_135);
//		verifiesRecord.setRelatedId(traderCustomer.getTraderCustomerId());
//		List<VerifiesRecord> verifiesList = verifiesRecordService.getVerifiesRecord(verifiesRecord);

        mv.addObject("traderCustomer", traderManageInfo);
//		mv.addObject("verifiesList", verifiesList);
        mv.addObject("method", "manageinfo");
        mv.setViewName("trader/customer/view_manageinfo");
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
     * <br><b>Date:</b> 2018年7月6日 下午2:19:16
     */
    @ResponseBody
    @RequestMapping(value = "/ordercoverinfo")
    public TraderCustomerVo getOrderCoverInfo(HttpServletRequest request, TraderOrderGoods traderOrderGoods) {

        TraderCustomerVo orderCoverInfo = traderCustomerService.getOrderCoverInfo(traderOrderGoods);
        orderCoverInfo.setOfficeStr(traderCustomerService.getOrderMedicalOfficeStr(traderOrderGoods));
        return orderCoverInfo;
    }

    /**
     * <b>Description:</b><br>
     * 编辑基本信息
     *
     * @param traderCustomer
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月17日 上午9:54:47
     */
    @ResponseBody
    @RequestMapping(value = "/editbaseinfo")
    public ModelAndView editBaseInfo(TraderCustomer traderCustomer) {
        ModelAndView mv = new ModelAndView();

        TraderCustomer traderBaseInfo = traderCustomerService.getTraderCustomerEditBaseInfo(traderCustomer);

        // 地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        if (null != traderBaseInfo.getTrader().getAreaId() && traderBaseInfo.getTrader().getAreaId() > 0
                && null != traderBaseInfo.getTrader().getAreaIds() && traderBaseInfo.getTrader().getAreaIds() != "") {

            Integer areaId = traderBaseInfo.getTrader().getAreaId();
            List<Region> regionList = (List<Region>) regionService.getRegion(areaId, 1);

            if (!regionList.isEmpty()) {
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

        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1016);// 员工人数
        scopeList.add(SysOptionConstant.SCOP_1017);// 年销售额
        scopeList.add(SysOptionConstant.SCOP_1018);// 销售模式
        scopeList.add(SysOptionConstant.SCOP_1013);// 所有制
        scopeList.add(SysOptionConstant.SCOP_1014);// 医学类型
        scopeList.add(SysOptionConstant.SCOP_1015);// 医院等级

        Map<Integer, List<SysOptionDefinition>> optionList = sysOptionDefinitionService.getOptionByScopeList(scopeList);

        // 客户类型
        List<TraderCustomerCategory> customerCategories = traderBaseInfo.getCustomerCategories();
        Boolean show_fenxiao = false;
        TraderCustomerAttributeCategory traderCustomerAttributeCategory = new TraderCustomerAttributeCategory();
        if (customerCategories != null && !customerCategories.isEmpty()) {
            for (TraderCustomerCategory cate : customerCategories) {
                if (cate.getTraderCustomerCategoryId().equals(3) || cate.getTraderCustomerCategoryId().equals(5)) {
                    show_fenxiao = true;
                }

                traderCustomerAttributeCategory.setTraderCustomerCategoryId(cate.getTraderCustomerCategoryId());
            }
        }
        // 客户属性
        List<TraderCustomerAttributeCategory> attributes = traderCustomerService
                .getTraderCustomerAttributeByCategoryId(traderCustomerAttributeCategory);

        Collections.reverse(attributes);
        // 属性子集处理
        List<TraderCustomerAttributeCategory> attributeCategories = new ArrayList<>();
        for (TraderCustomerAttributeCategory attr : attributes) {
            attributeCategories.add(attr);
            List<TraderCustomerAttributeCategory> childAttribute = traderCustomerService
                    .getTraderCustomerChildAttribute(attr);
            if (null != childAttribute) {
                for (TraderCustomerAttributeCategory childAttr : childAttribute) {
                    attributeCategories.add(childAttr);
                }
            }
        }

        // 经营地区
        List<TraderCustomerBussinessArea> customerBussinessAreas = traderBaseInfo.getTraderCustomerBussinessAreas();

        Map<Integer, Map<String, String>> bussinessMap = new HashMap<>();
        if (customerBussinessAreas.size() > 0) {
            for (TraderCustomerBussinessArea bussinessArea : customerBussinessAreas) {
                Map<String, String> regionMap = new HashMap<>();
                String regionStr = (String) regionService.getRegion(bussinessArea.getAreaId(), 2);
                regionMap.put("areaIds", bussinessArea.getAreaIds());
                regionMap.put("areaStr", regionStr);

                bussinessMap.put(bussinessArea.getAreaId(), regionMap);
            }

        }

        mv.addObject("traderCustomer", traderBaseInfo);

        mv.addObject("provinceList", provinceList);

        mv.addObject("employees", optionList.get(SysOptionConstant.SCOP_1016));
        mv.addObject("annualSales", optionList.get(SysOptionConstant.SCOP_1017));
        mv.addObject("salesModel", optionList.get(SysOptionConstant.SCOP_1018));

        mv.addObject("ownership", optionList.get(SysOptionConstant.SCOP_1013));
        mv.addObject("medicalType", optionList.get(SysOptionConstant.SCOP_1014));
        mv.addObject("hospitalLevel", optionList.get(SysOptionConstant.SCOP_1015));

        mv.addObject("show_fenxiao", show_fenxiao);
        mv.addObject("attributes", attributeCategories);

        mv.addObject("bussinessMap", bussinessMap);
        mv.addObject("method", "baseinfo");

        try {
            mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderBaseInfo)));
        } catch (Exception e) {
            logger.error("trader customer editbaseinfo:", e);
        }
        mv.setViewName("trader/customer/edit_baseinfo");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑客户
     *
     * @param trader
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月18日 上午8:42:50
     */
    @ResponseBody
    @RequestMapping(value = "/saveeditbaseinfo")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑客户信息")
    public ModelAndView saveEditBaseInfo(Trader trader, HttpServletRequest request, HttpSession session, String beforeParams,Integer isCheckAptitudeStatus) {
        ModelAndView mv = new ModelAndView();
        TraderCustomer customer;
        if(trader==null||trader.getTraderCustomer()==null||trader.getTraderCustomer().getTraderCustomerId()==null){
            fail(mv);
        }
        try {
            if(isCheckAptitudeStatus!=null&&isCheckAptitudeStatus==1){
                ResultInfo resultInfo=getAptitudeStatus(trader.getTraderCustomer().getTraderCustomerId());
                Integer status=(Integer) resultInfo.getData();
                if(TraderConstants.APTITUDE_IN_CHECK.equals(status)){
                    Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + trader.getTraderCustomer().getTraderCustomerId());
                    TaskInfo taskInfo=(TaskInfo) historicInfo.get("taskInfo");
                    ResultInfo resultUpdateTable = verifiesRecordService.saveVerifiesInfoForTrader(taskInfo.getId(), 3);
                    ResultInfo resultDeleteInstance = actionProcdefService.deleteProcessInstance(taskInfo.getId());
                }else if(TraderConstants.APTITUDE_PASSED.equals(status)){
                    verifiesRecordService.deleteVerifiesInfoByRelateKey(trader.getTraderCustomer().getTraderCustomerId()
                            ,TraderConstants.APTITUDE_CHECK_TABLE_STR);
                }
            }
            trader.setTraderName(trader.getTraderName().trim());
            Page page = getPageTag(request, 1, 10);
            Map<String, Object> map;
            // 搜索该客户已生效订单
            Saleorder saleorder = new Saleorder();
            if (null != trader.getTraderNameBefore()) {
                saleorder.setTraderName(trader.getTraderNameBefore().trim());
            }
            saleorder.setValidStatus(1);
            map = saleorderService.getSaleorderListPage(request, saleorder, page, 0);
            Page p = (Page) map.get("page");
            if (p.getTotalRecord() != null && p.getTotalRecord() > 0 && !trader.getTraderName().equals(trader.getTraderNameBefore())) {
                //如果有已经生效的订单并且名字有所变更
                try {
                    Map<String, Object> variableMap = new HashMap<String, Object>();
                    User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
                    //开始生成流程(如果没有taskId表示新流程需要生成)
                    variableMap.put("trader", trader);
                    variableMap.put("currentAssinee", user.getUsername());
                    variableMap.put("processDefinitionKey", "editTraderCustomerName");
                    variableMap.put("businessKey", "editTraderCustomerName_" + trader.getTraderId());
                    variableMap.put("relateTableKey", trader.getTraderId());
                    variableMap.put("relateTable", "T_TRADER");
                    actionProcdefService.createProcessInstance(request, "editTraderCustomerName", "editTraderCustomerName_" + trader.getTraderId(), variableMap);
                    //默认申请人通过
                    //根据BusinessKey获取生成的审核实例
                    Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "editTraderCustomerName_" + trader.getTraderId());
                    if (historicInfo.get("endStatus") != "审核完成") {
                        Task taskInfo = (Task) historicInfo.get("taskInfo");
                        String taskId = taskInfo.getId();
                        Authentication.setAuthenticatedUserId(user.getUsername());
                        Map<String, Object> variables = new HashMap<String, Object>();
                        //默认审批通过
                        ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variables);
                        //如果未结束添加审核对应主表的审核状态
                        if (!complementStatus.getData().equals("endEvent")) {
                            verifiesRecordService.saveVerifiesInfo(taskId, 0);
                        }
                    }
                    //return new ResultInfo(0, "操作成功");
                } catch (Exception e) {
                    //return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
                }
                if (null != trader.getTraderNameBefore()) {
                    trader.setTraderName(trader.getTraderNameBefore().trim());
                }
            } else {

            }
            customer = traderCustomerService.saveEditBaseInfo(trader, request, session);
            if (null != trader.getTraderCustomer().getTraderCustomerId()) {
                actionProcdefService.updateVerifyInfo("T_TRADER_CUSTOMER", trader.getTraderCustomer().getTraderCustomerId(), 3);
            }
            if (null != customer) {
                mv.addObject("url", "./baseinfo.do?traderCustomerId=" + customer.getTraderCustomerId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("trader customer saveeditbaseinfo:", e);
            return fail(mv);
        }
    }

    /**
     * <b>Description:</b><br>
     * 编辑管理信息
     *
     * @param traderCustomer
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月17日 上午9:54:47
     */
    @ResponseBody
    @RequestMapping(value = "/editmanageinfo")
    public ModelAndView editManageInfo(TraderCustomer traderCustomer, HttpServletRequest request, HttpSession session) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 管理信息
        TraderCustomer traderManageInfo = traderCustomerService.getTraderCustomerManageInfo(traderCustomer, session);

        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1009);// 客户来源
        scopeList.add(SysOptionConstant.SCOP_1010);// 首次询价方式
        scopeList.add(SysOptionConstant.SCOP_1012);// 销售评级
        scopeList.add(SysOptionConstant.SCOP_1033);// 战略合作伙伴

        Map<Integer, List<SysOptionDefinition>> optionList = sysOptionDefinitionService.getOptionByScopeList(scopeList);
        // 客户标签
        Tag tag = new Tag();
        tag.setTagType(SysOptionConstant.ID_30);
        tag.setIsRecommend(ErpConst.ONE);
        tag.setCompanyId(user.getCompanyId());

        Integer pageNo = 1;
        Integer pageSize = 10;

        Page page = getPageTag(request, pageNo, pageSize);
        Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

        mv.addObject("traderCustomer", traderManageInfo);
        mv.addObject("customerFrom", optionList.get(SysOptionConstant.SCOP_1009));
        mv.addObject("firstEnquiryType", optionList.get(SysOptionConstant.SCOP_1010));
        mv.addObject("userEvaluate", optionList.get(SysOptionConstant.SCOP_1012));
        mv.addObject("zlhz", optionList.get(SysOptionConstant.SCOP_1033));

        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));

        mv.addObject("method", "manageinfo");
        try {
            mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderManageInfo)));
        } catch (Exception e) {
            logger.error("trader customer editmanageinfo:", e);
        }
        mv.setViewName("trader/customer/edit_manageinfo");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑管理信息
     *
     * @param traderCustomer
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月17日 上午11:21:08
     */
    @ResponseBody
    @RequestMapping(value = "/saveeditmanageinfo")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑客户管理信息")
    public ModelAndView saveEditManageInfo(TraderCustomer traderCustomer, HttpServletRequest request,
                                           HttpSession session, String beforeParams) {
        ModelAndView mv = new ModelAndView();
        TraderCustomer customer;
        try {
            customer = traderCustomerService.saveEditManageInfo(traderCustomer, request, session);
            if (null != customer) {
                mv.addObject("url", "./manageinfo.do?traderCustomerId=" + customer.getTraderCustomerId());
                return success(mv);
            } else {
                return fail(mv);
            }
        } catch (Exception e) {
            logger.error("trader customer saveeditmanageinfo:", e);
            return fail(mv);
        }
    }

    /**
     * <b>Description:</b><br>
     * 客户分类
     *
     * @param request
     * @param traderCustomerCategory
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月11日 下午2:39:06
     */
    @ResponseBody
    @RequestMapping(value = "/gettradercustomercategory")
    public ResultInfo<TraderCustomerCategory> getTraderCustomerCategory(HttpServletRequest request,
                                                                        TraderCustomerCategory traderCustomerCategory) {
        ResultInfo<TraderCustomerCategory> resultInfo = new ResultInfo<TraderCustomerCategory>();
        List<TraderCustomerCategory> categoryList = traderCustomerService
                .getTraderCustomerCategoryByParentId(traderCustomerCategory);

        if (categoryList != null) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setListData(categoryList);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 客户属性
     *
     * @param request
     * @param traderCustomerCategory
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月11日 下午2:44:35
     */
    @ResponseBody
    @RequestMapping(value = "/gettradercustomerattribute")
    public ResultInfo<TraderCustomerAttributeCategory> getTraderCustomerAttribute(HttpServletRequest request,
                                                                                  TraderCustomerAttributeCategory traderCustomerAttributeCategory) {
        ResultInfo<TraderCustomerAttributeCategory> resultInfo = new ResultInfo<TraderCustomerAttributeCategory>();
        List<TraderCustomerAttributeCategory> categoryList = traderCustomerService
                .getTraderCustomerAttributeByCategoryId(traderCustomerAttributeCategory);

        if (categoryList != null) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setListData(categoryList);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 获取子集属性
     *
     * @param request
     * @param traderCustomerAttributeCategory
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月11日 下午5:21:12
     */
    @ResponseBody
    @RequestMapping(value = "/gettradercustomerchildattribute")
    public ResultInfo<TraderCustomerAttributeCategory> getTraderCustomerChildAttribute(HttpServletRequest request,
                                                                                       TraderCustomerAttributeCategory traderCustomerAttributeCategory) {
        ResultInfo<TraderCustomerAttributeCategory> resultInfo = new ResultInfo<TraderCustomerAttributeCategory>();
        List<TraderCustomerAttributeCategory> categoryList = traderCustomerService
                .getTraderCustomerChildAttribute(traderCustomerAttributeCategory);

        if (categoryList != null) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setListData(categoryList);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 获取客户字典库属性（员工人数\年销售额\客户来源\首次询价方式\战略合作伙伴）
     *
     * @param request
     * @return
     * @throws IOException
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月12日 下午2:06:27
     */
    @ResponseBody
    @RequestMapping(value = "/gettradercustomeroption")
    public ResultInfo<?> getTraderCustomerOption(HttpServletRequest request) throws IOException {
        ResultInfo<?> resultInfo = new ResultInfo<>();
        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1016);// 员工人数
        scopeList.add(SysOptionConstant.SCOP_1017);// 年销售额
        scopeList.add(SysOptionConstant.SCOP_1018);// 销售模式
        scopeList.add(SysOptionConstant.SCOP_1009);// 客户来源
        scopeList.add(SysOptionConstant.SCOP_1010);// 首次询价方式
        scopeList.add(SysOptionConstant.SCOP_1033);// 战略合作伙伴

        Map<Integer, List<SysOptionDefinition>> optionList = sysOptionDefinitionService.getOptionByScopeList(scopeList);

        if (optionList != null) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setParam(JsonUtils.translateToJson(optionList));
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 查询交易者
     *
     * @param request
     * @param trader
     * @return
     * @throws IOException
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月22日 上午10:29:31
     */
    @ResponseBody
    @RequestMapping(value = "/gettraderbytradername")
    public ResultInfo<Trader> getTraderByTraderName(HttpServletRequest request, Trader trader, HttpSession session)
            throws IOException {
        ResultInfo<Trader> resultInfo = new ResultInfo<>();
        Trader traderInfo = traderCustomerService.getTraderByTraderName(trader, session);
        if (null != traderInfo) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setData(traderInfo);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 沟通记录
     *
     * @param request
     * @param communicateRecord
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月23日 上午9:12:06
     */
    @ResponseBody
    @RequestMapping(value = "/communicaterecord")
    public ModelAndView communicateRecord(HttpServletRequest request, CommunicateRecord communicateRecord,
                                          TraderCustomer traderCustomer, @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                          @RequestParam(required = false) Integer pageSize, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Page page = getPageTag(request, pageNo, pageSize);

        communicateRecord.setTraderType(ErpConst.ONE);//客户类型
        List<CommunicateRecord> communicateRecordList = traderCustomerService.getCommunicateRecordListPage(communicateRecord, page);

        TraderCustomer customerInfoByTraderCustomer = traderCustomerService.getCustomerInfoByTraderCustomer(traderCustomer);
        mv.addObject("customerInfoByTraderCustomer", customerInfoByTraderCustomer);

        mv.addObject("communicateRecordList", communicateRecordList);
        mv.addObject("page", page);
        mv.addObject("method", "communicaterecord");
        mv.setViewName("trader/customer/communicate_record");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 新增沟通
     *
     * @param traderCustomer
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月23日 上午11:35:17
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/addcommunicate")
    public ModelAndView addCommunicate(TraderCustomer traderCustomer, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();

        TraderContact traderContact = new TraderContact();
        // 联系人
        traderContact.setTraderId(traderCustomer.getTraderId());
        traderContact.setIsEnable(ErpConst.ONE);
        traderContact.setTraderType(ErpConst.ONE);
        List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1024);// 沟通目的
        scopeList.add(SysOptionConstant.SCOP_1023);// 沟通方式

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

        mv.addObject("traderCustomer", traderCustomer);

        mv.addObject("contactList", contactList);

        mv.addObject("communicateGoal", optionList.get(SysOptionConstant.SCOP_1024));
        mv.addObject("communicateMode", optionList.get(SysOptionConstant.SCOP_1023));

        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));

        CommunicateRecord communicate = new CommunicateRecord();
        communicate.setBegintime(DateUtil.sysTimeMillis());
        communicate.setEndtime(DateUtil.sysTimeMillis() + 2 * 60 * 1000);
        mv.addObject("communicateRecord", communicate);

        mv.addObject("method", "communicaterecord");
        mv.setViewName("trader/customer/add_communicate");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 新增沟通
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月24日 下午2:36:53
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveaddcommunicate")
    @SystemControllerLog(operationType = "add", desc = "保存新增客户沟通")
    public ModelAndView saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
                                           HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Boolean record;
        try {
            communicateRecord.setCommunicateType(SysOptionConstant.ID_242);
            communicateRecord.setRelatedId(communicateRecord.getTraderCustomerId());
            record = traderCustomerService.saveAddCommunicate(communicateRecord, request, session);
            if (record) {
                mv.addObject("url", "./communicaterecord.do?traderId=" + communicateRecord.getTraderId()
                        + "&traderCustomerId=" + communicateRecord.getTraderCustomerId());
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
     * <b>Description:</b><br>
     * 获取当前客户的联系人和地址
     *
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 上午9:08:41
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getContactsAddress")
    public ModelAndView getContactsAddress(HttpServletRequest request, TraderCustomer traderCustomer) {
        ModelAndView mav = new ModelAndView("trader/customer/view_contactsaddress");
        TraderContactVo traderContactVo = new TraderContactVo();
        traderContactVo.setTraderId(traderCustomer.getTraderId());
        traderContactVo.setTraderType(ErpConst.ONE);
        Map<String, Object> map = traderCustomerService.getTraderContactVoList(traderContactVo);
        String tastr = (String) map.get("contact");
        net.sf.json.JSONArray json = net.sf.json.JSONArray.fromObject(tastr);
        List<TraderContactVo> list = (List<TraderContactVo>) json.toCollection(json, TraderContactVo.class);
        List<TraderAddressVo> taList = (List<TraderAddressVo>) map.get("address");
        TraderCustomer customerInfoByTraderCustomer = traderCustomerService.getCustomerInfoByTraderCustomer(traderCustomer);
        List<MjxAccountAddress> mjxlist = traderCustomerService.getMjxAccountAddressList(traderContactVo);
        mav.addObject("customerInfoByTraderCustomer", customerInfoByTraderCustomer);
        mav.addObject("mjxlist", mjxlist);
        mav.addObject("contactList", list);
        mav.addObject("addressList", taList);
        mav.addObject("traderCustomer", traderCustomer);
        mav.addObject("traderId", traderCustomer.getTraderId());
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
    public ModelAndView toAddContactPage(TraderCustomer traderCustomer) {
        ModelAndView mav = new ModelAndView("trader/customer/add_contact");
        mav.addObject("traderCustomer", traderCustomer);
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
    public ModelAndView toAddAddressPage(HttpServletRequest request, Integer traderId) {
        ModelAndView mav = new ModelAndView("trader/customer/add_address");
        mav.addObject("traderId", traderId);
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
    public ModelAndView toEditAddressPage(HttpServletRequest request, TraderAddress traderAddress)
            throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/add_address");
        traderAddress = traderCustomerService.getTraderAddress(traderAddress);
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mav.addObject("currentUser", curr_user);
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
    @SystemControllerLog(operationType = "add", desc = "保存新增客户地址")
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
    //@FormToken(remove=true)
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping(value = "/addSaveContact")
    @SystemControllerLog(operationType = "add", desc = "新增保存客户联系人")
    public ResultInfo addSaveContact(HttpSession session, TraderCustomer traderCustomer, TraderContact traderContact) throws IOException {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        Integer traderContactId = traderCustomerService.saveTraderContact(traderContact, user);
        if (traderContactId > 0) {
            return new ResultInfo(0, "操作成功", traderCustomer.getTraderCustomerId() + "," + traderCustomer.getTraderId() + "," + traderContactId);
        } else if (traderContactId == -1) {
            return new ResultInfo(-1, "该客户已存在相同联系人");
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
    @SystemControllerLog(operationType = "edit", desc = "编辑保存客户联系人")
    public ModelAndView editSaveContact(HttpServletRequest request, @RequestParam(required = false, value = "xg") String[] sg,
                                        TraderContact traderContact, TraderCustomer traderCustomer, String beforeParams) throws IOException {
        ModelAndView mav = new ModelAndView();
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        if (null != traderContact.getTraderContactId()) {
            //String[] sg = request.getParameterValues("xg");
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
            mav.addObject("url", "./getContactsAddress.do?traderId=" + traderContact.getTraderId() + "&&traderCustomerId=" +
                    traderCustomer.getTraderCustomerId());
            return success(mav);
        } else if (traderContactId == -1) {
            List<ObjectError> allErrors = new ArrayList<>();
            allErrors.add(new ObjectError("1", "该客户已存在相同的联系人"));
            request.setAttribute("allErrors", allErrors);
            return toEditContactPage(request, traderContact.getTraderContactId(), traderCustomer);
        } else {
            return fail(mav);
        }

    }

    /**
     * <b>Description:</b><br> 联系人详情页
     *
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月5日 下午2:40:05
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/getContactInfo")
    public ModelAndView getContactInfo(TraderContact traderContact, TraderCustomer traderCustomer) {
        ModelAndView mav = new ModelAndView("trader/customer/view_contacts");
        Map<String, Object> map = traderCustomerService.viewTraderContact(traderContact);
        if (map.containsKey("contact")) {
            JSONObject json = JSONObject.fromObject(map.get("contact"));
            TraderContactVo tcv = (TraderContactVo) JSONObject.toBean(json, TraderContactVo.class);
            //从redis查询性格名称集合
            if (tcv != null && tcv.getCharacter() != null && !"".equals(tcv.getCharacter())) {
                String[] chars = tcv.getCharacter().split(",");
                StringBuffer sb = new StringBuffer();
                for (String c : chars) {
                    SysOptionDefinition sod = getSysOptionDefinition(Integer.valueOf(c));
                    sb = sb.append(sod.getTitle()).append("、");

                }
                tcv.setCharacterName(sb.toString());
            }
            mav.addObject("traderContact", tcv);
        }
        if (map.containsKey("experience")) {
            List<TraderContactExperienceVo> tceList = (List<TraderContactExperienceVo>) map.get("experience");
            mav.addObject("experienceList", tceList);
        }
        mav.addObject("method", "contactsaddress");
        mav.addObject("traderCustomer", traderCustomer);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 初始化编辑联系人页面
     *
     * @param request
     * @param traderId
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月23日 下午3:11:47
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/toEditContactPage")
    public ModelAndView toEditContactPage(HttpServletRequest request, Integer traderContactId, TraderCustomer traderCustomer) throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/edit_contact");
        TraderContact tc = traderCustomerService.getTraderContactById(traderContactId);
        mav.addObject("traderContact", tc);
        List<SysOptionDefinition> xgList = getSysOptionDefinitionList(SysOptionConstant.ID_4);//性格
        List<SysOptionDefinition> xlList = getSysOptionDefinitionList(SysOptionConstant.ID_3);//学历
        mav.addObject("xgList", xgList);
        mav.addObject("xlList", xlList);
        mav.addObject("method", "contactsaddress");
        mav.addObject("traderCustomer", traderCustomer);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(tc)));
        return mav;
    }

    /**
     * <b>Description:</b><br> 跳转新增客户联系人的行业背景页面
     *
     * @param traderContact
     * @param traderContactExperience
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月6日 下午3:22:54
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/addContactExperience")
    public ModelAndView addContactExperience(TraderContact traderContact, TraderCustomer traderCustomer) {
        ModelAndView mav = new ModelAndView("trader/customer/add_contactexperience");
        // 地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        mav.addObject("traderContact", traderContact);
        mav.addObject("traderCustomer", traderCustomer);
        return mav;
    }

    /**
     * <b>Description:</b><br> 保存新增客户联系人的行业背景
     *
     * @param traderContactExperience
     * @param traderCustomer
     * @param request
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月10日 上午8:42:46
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveAddContactExperience")
    @SystemControllerLog(operationType = "add", desc = "保存新增客户联系人的行业背景")
    public ResultInfo saveAddContactExperience(TraderContactExperience traderContactExperience, TraderCustomer traderCustomer, String start, String end,
                                               @RequestParam(required = false, value = "bussinessAreaId") String[] bussinessAreaIds,
                                               @RequestParam(required = false, value = "bussinessAreaIds") String[] bussinessAreaIdsStr,
                                               @RequestParam(required = false, value = "bussinessBrandId") String[] bussinessBrandIds,
                                               HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        List<TraderContactExperienceBussinessArea> bussinessAreas = null;
        List<TraderContactExperienceBussinessBrand> bussinessBrands = null;
        // 客户经营区域
        if (null != bussinessAreaIds) {
            //String[] bussinessAreaIds = request.getParameterValues("bussinessAreaId");
            //String[] bussinessAreaIdsStr = request.getParameterValues("bussinessAreaIds");
            bussinessAreas = new ArrayList<>();
            for (Integer i = 0; i < bussinessAreaIds.length; i++) {
                TraderContactExperienceBussinessArea traderCustomerBussinessArea = new TraderContactExperienceBussinessArea();
                traderCustomerBussinessArea.setAreaId(Integer.parseInt(bussinessAreaIds[i]));
                traderCustomerBussinessArea.setAreaIds(bussinessAreaIdsStr[i]);
                bussinessAreas.add(traderCustomerBussinessArea);
            }
        }

        // 客户经营品牌
        if (null != bussinessBrandIds) {
            //String[] bussinessBrandIds = request.getParameterValues("bussinessBrandId");
            bussinessBrands = new ArrayList<>();
            for (String brandId : bussinessBrandIds) {
                TraderContactExperienceBussinessBrand traderCustomerBussinessBrand = new TraderContactExperienceBussinessBrand();
                traderCustomerBussinessBrand.setBrandId(Integer.parseInt(brandId));
                bussinessBrands.add(traderCustomerBussinessBrand);
            }
        }
        //String start=request.getParameter("start");
        //String end = request.getParameter("end");
        if (start != null && !"".equals(start)) {
            traderContactExperience.setBegintime(DateUtil.convertLong(start, "yyyy-MM"));
        }
        if (end != null && !"".equals(end)) {
            traderContactExperience.setEndtime(DateUtil.convertLong(end, "yyyy-MM"));
        }
        if (bussinessAreas == null && bussinessBrands == null && "".equals(traderContactExperience.getCompany())
                && "".equals(traderContactExperience.getPosition()) && traderContactExperience.getBegintime() == null && traderContactExperience.getEndtime() == null) {
            return new ResultInfo(2, "请至少填写/选择一项数据");
        }
        ResultInfo re = traderCustomerService.saveAddContactExperience(traderContactExperience, user, bussinessAreas, bussinessBrands);
        if (re.getCode() == 0) {
            return new ResultInfo(0, "操作成功", traderCustomer.getTraderCustomerId() + "," + traderCustomer.getTraderId() + "," + traderContactExperience.getTraderContactId());
        } else {
            return new ResultInfo(1, "操作失败");
        }
    }

    /**
     * <b>Description:</b><br> 保存编辑客户联系人的行业背景
     *
     * @param traderContactExperience
     * @param traderCustomer
     * @param request
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月10日 上午8:42:46
     */
    @ResponseBody
    @RequestMapping(value = "/saveEditContactExperience")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑客户联系人的行业背景")
    public ResultInfo saveEditContactExperience(TraderContactExperience traderContactExperience, TraderCustomer traderCustomer, String start, String end, String beforeParams,
                                                @RequestParam(required = false, value = "bussinessAreaId") String[] bussinessAreaIds,
                                                @RequestParam(required = false, value = "bussinessAreaIds") String[] bussinessAreaIdsStr,
                                                @RequestParam(required = false, value = "bussinessBrandId") String[] bussinessBrandIds, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        List<TraderContactExperienceBussinessArea> bussinessAreas = null;
        List<TraderContactExperienceBussinessBrand> bussinessBrands = null;
        // 客户经营区域
        if (null != bussinessAreaIds) {
            //String[] bussinessAreaIds = request.getParameterValues("bussinessAreaId");
            //String[] bussinessAreaIdsStr = request.getParameterValues("bussinessAreaIds");
            bussinessAreas = new ArrayList<>();
            for (Integer i = 0; i < bussinessAreaIds.length; i++) {
                TraderContactExperienceBussinessArea traderCustomerBussinessArea = new TraderContactExperienceBussinessArea();
                traderCustomerBussinessArea.setAreaId(Integer.parseInt(bussinessAreaIds[i]));
                traderCustomerBussinessArea.setAreaIds(bussinessAreaIdsStr[i]);
                bussinessAreas.add(traderCustomerBussinessArea);
            }
        }

        // 客户经营品牌
        if (null != bussinessBrandIds) {
            //String[] bussinessBrandIds = request.getParameterValues("bussinessBrandId");
            bussinessBrands = new ArrayList<>();
            for (String brandId : bussinessBrandIds) {
                TraderContactExperienceBussinessBrand traderCustomerBussinessBrand = new TraderContactExperienceBussinessBrand();
                traderCustomerBussinessBrand.setBrandId(Integer.parseInt(brandId));
                bussinessBrands.add(traderCustomerBussinessBrand);
            }
        }
        //String start=request.getParameter("start");
        //String end = request.getParameter("end");
        if (start != null && !"".equals(start)) {
            traderContactExperience.setBegintime(DateUtil.convertLong(start, "yyyy-MM"));
        } else {
            traderContactExperience.setBegintime(Long.valueOf(0));
        }
        if (end != null && !"".equals(end)) {
            traderContactExperience.setEndtime(DateUtil.convertLong(end, "yyyy-MM"));
        } else {
            traderContactExperience.setEndtime(Long.valueOf(0));
        }
        if (bussinessAreas == null && bussinessBrands == null && "".equals(traderContactExperience.getCompany())
                && "".equals(traderContactExperience.getPosition()) && traderContactExperience.getBegintime() == null && traderContactExperience.getEndtime() == null) {
            return new ResultInfo(2, "请至少填写/选择一项数据");
        }
        ResultInfo re = traderCustomerService.saveAddContactExperience(traderContactExperience, user, bussinessAreas, bussinessBrands);
        if (re.getCode() == 0) {
            return new ResultInfo(0, "操作成功", traderCustomer.getTraderCustomerId() + "," + traderCustomer.getTraderId() + "," + traderContactExperience.getTraderContactId());
        } else {
            return new ResultInfo(1, "操作失败");
        }
    }

    /**
     * <b>Description:</b><br> 跳转编辑客户联系人的行业背景页面
     *
     * @param traderContact
     * @param traderContactExperience
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月6日 下午3:22:54
     */
    @ResponseBody
    @RequestMapping(value = "/editContactExperience")
    public ModelAndView editContactExperience(TraderContact traderContact, TraderCustomer traderCustomer, TraderContactExperience traderContactExperience) throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/edit_contactexperience");
        Map<String, Object> map = traderCustomerService.getTraderContactExperience(traderContactExperience);
        if (map.containsKey("traderContactExperience")) {
            JSONObject json = JSONObject.fromObject(map.get("traderContactExperience"));
            traderContactExperience = (TraderContactExperience) JSONObject.toBean(json, TraderContactExperience.class);
            mav.addObject("traderContactExperience", traderContactExperience);
        }
        //经营品牌
        if (map.containsKey("tcebbList")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("tcebbList"));
            List<TraderContactExperienceBussinessBrandVo> tcebbList =
                    (List<TraderContactExperienceBussinessBrandVo>) JSONArray.toCollection(jsonArray, TraderContactExperienceBussinessBrandVo.class);
            mav.addObject("tcebbList", tcebbList);
        }
        // 经营地区
        if (map.containsKey("tcebaList")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("tcebaList"));
            List<TraderContactExperienceBussinessAreaVo> tcebaList =
                    (List<TraderContactExperienceBussinessAreaVo>) JSONArray.toCollection(jsonArray, TraderContactExperienceBussinessAreaVo.class);
            mav.addObject("tcebaList", tcebaList);
        }
        // 地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        mav.addObject("traderContact", traderContact);
        mav.addObject("traderCustomer", traderCustomer);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(map)));
        return mav;
    }

    /**
     * <b>Description:</b><br> 删除联系人背景
     *
     * @param traderContactExperience
     * @param traderCustomer
     * @param request
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年7月10日 下午2:58:15
     */

    @ResponseBody
    @RequestMapping(value = "/delContactExperience")
    @SystemControllerLog(operationType = "del", desc = "删除联系人行业背景")
    public ResultInfo delContactExperience(TraderContactExperience traderContactExperience, TraderCustomer traderCustomer, HttpServletRequest request) {
        ResultInfo re = traderCustomerService.delContactExperience(traderContactExperience);
        if (re != null && re.getCode() == 0) {
            return new ResultInfo(0, "操作成功", traderCustomer.getTraderCustomerId() + "," + traderCustomer.getTraderId() + "," + traderContactExperience.getTraderContactId());
        } else {
            return new ResultInfo(1, "操作失败");
        }

    }

    /**
     * <b>Description:</b><br>
     * 编辑沟通记录
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @throws IOException
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月24日 下午1:31:13
     */
    @ResponseBody
    @RequestMapping(value = "/editcommunicate")
    public ModelAndView editCommunicate(CommunicateRecord communicateRecord, TraderCustomer traderCustomer,
                                        HttpServletRequest request, HttpSession session) throws IOException {
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        CommunicateRecord communicate = traderCustomerService.getCommunicate(communicateRecord);
        communicate.setTraderCustomerId(communicateRecord.getTraderCustomerId());
        communicate.setTraderId(communicateRecord.getTraderId());

        TraderContact traderContact = new TraderContact();
        // 联系人
        traderContact.setTraderId(communicateRecord.getTraderId());
        traderContact.setIsEnable(ErpConst.ONE);
        traderContact.setTraderType(ErpConst.ONE);
        List<TraderContact> contactList = traderCustomerService.getTraderContact(traderContact);

        List<Integer> scopeList = new ArrayList<Integer>();
        scopeList.add(SysOptionConstant.SCOP_1024);// 沟通目的
        scopeList.add(SysOptionConstant.SCOP_1023);// 沟通方式

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
        mv.addObject("traderCustomer", traderCustomer);
        mv.setViewName("trader/customer/edit_communicate");
        mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(communicate)));
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 保存编辑沟通
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     * <b>Date:</b> 2017年5月24日 下午2:37:47
     */
    @ResponseBody
    @RequestMapping(value = "/saveeditcommunicate")
    @SystemControllerLog(operationType = "edit", desc = "保存编辑沟通记录")
    public ModelAndView saveEditCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request, String beforeParams,
                                            HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Boolean record;
        try {
            record = traderCustomerService.saveEditCommunicate(communicateRecord, request, session);
            if (record) {
                mv.addObject("url", "./communicaterecord.do?traderId=" + communicateRecord.getTraderId()
                        + "&traderCustomerId=" + communicateRecord.getTraderCustomerId());
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
     * <b>Description:</b><br>
     * 跳转到转移联系人页面
     *
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月25日 下午5:34:13
     */
    @ResponseBody
    @RequestMapping(value = "/toTransferContactPage")
    public ModelAndView toTransferContactPage(TraderContact traderContact, HttpServletRequest request)
            throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/transfer_contact");
        // get 方式提交转码
        String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
        String department = URLDecoder.decode(request.getParameter("department"), "UTF-8");
        String position = URLDecoder.decode(request.getParameter("position"), "UTF-8");
        traderContact.setName(name);
        traderContact.setDepartment(department);
        traderContact.setPosition(position);
        mav.addObject("traderContact", traderContact);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderContact)));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 转移联系人页面搜索客户
     *
     * @param name
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年5月26日 下午1:23:07
     */
    @ResponseBody
    @RequestMapping(value = "/getCustomersByName")
    public ModelAndView getCustomersByName(TraderContact traderContact, String customerName, HttpServletRequest request,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/transfer_contact");
        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        Page page = getPageTag(request, pageNo, 10);
        // 查询所有职位类型为310的员工
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);//销售
        List<User> userList = userService.getMyUserList(user, positionType, false);
        //List<Integer> traderList = userService.getTraderIdListByUserList(userList,ErpConst.ONE+"");
        TraderCustomerVo tcv = new TraderCustomerVo();
        tcv.setCompanyId(user.getCompanyId());
        tcv.setName(customerName);
        //tcv.setTraderList(traderList);
        Map<String, Object> map = traderCustomerService.getTraderCustomerVoPage(tcv, page, userList);
        List<TraderCustomerVo> list = (List<TraderCustomerVo>) map.get("list");
        page = (Page) map.get("page");
        mav.addObject("list", list);
        mav.addObject("page", page);
        mav.addObject("customerName", customerName);
        mav.addObject("traderContact", traderContact);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderContact)));
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
    public ResultInfo transferContact(TraderContact traderContact, HttpSession session, String beforeParams) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ResultInfo res = traderCustomerService.transferContact(traderContact, user);
        return res;
    }


    /**
     * <b>Description:</b><br> 开始审核资质
     *
     * @param traderCustomer 客户信息
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:2019/9/5</b>
     */
    @Transactional
    public ResultInfo startCheckAptitude(HttpServletRequest request, TraderCustomerVo traderCustomer, String taskId) {
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            // 查询当前订单的一些状态
            TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
            //开始生成流程(如果没有taskId表示新流程需要生成)
            if (StringUtil.isBlank(taskId) || taskId.equals("0")) {
//                variableMap.put("traderCustomerVo", traderBaseInfo);
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "customerAptitudeVerify");
                variableMap.put("businessKey", "customerAptitude_" + traderBaseInfo.getTraderCustomerId());
                variableMap.put("relateTableKey", traderBaseInfo.getTraderCustomerId());
                variableMap.put("relateTable", "T_CUSTOMER_APTITUDE");
                actionProcdefService.createProcessInstance(request, "customerAptitudeVerify", "customerAptitude_" + traderBaseInfo.getTraderCustomerId(), variableMap);
            }
            //默认申请人通过
            //根据BusinessKey获取生成的审核实例
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + traderBaseInfo.getTraderCustomerId());
            if (StringUtil.isBlank(taskId)&&historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                String newtaskId = taskInfo.getId();
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, newtaskId, "", user.getUsername(), variableMap);
                //如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfoForTrader(newtaskId, 0);
                }

            }
            if (StringUtil.isNotBlank(taskId)) {
                verifiesRecordService.saveVerifiesInfoForTrader(taskId, 0);
            }
            List<Integer> userIds=new ArrayList<>();
            Page page=new Page(1,2);
            RoleVo role=new RoleVo();
            role.setRoleName("运营专员");
            List<Role> roles=roleService.queryListPage(role,page);
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(roles)) {
                for (Role r : roles) {
                    List<User> users = userService.getUserByRoleId(r.getRoleId());
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(users)) {
                        for (User u:users){
                            userIds.add(u.getUserId());
                        }
                    }
                }
            }
            Map<String,String> map=new HashMap<>();
            map.put("traderName",traderBaseInfo.getName());
            String url=ErpConst.GET_FINANCE_AND_APTITUDE_URL+traderBaseInfo.getTraderId();

            WebAccount webAccount=new WebAccount();
            webAccount.setTraderId(traderBaseInfo.getTraderId());
            List<WebAccount> webAccountList=webAccountMapper.getWebAccountListByParam(webAccount);
            if(webAccountList!=null&&webAccountList.size()>0){
                MessageUtil.sendMessage(104,userIds,map,url);
            }else{
                MessageUtil.sendMessage(97,userIds,map,url);
            }

            //更新贝登会员
            traderCustomerService.updateVedengMember();
            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("editApplyValidCustomer:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }
    }

    /**
     * <b>Description:</b><br> 完成审核
     *
     * @param traderCustomerVo 客户信息
     * @param passed           是否通过
     * @return
     * @Note <b>Author:calvin</b>
     * <br><b>Date:2019/9/5</b>
     */
    @ResponseBody
    @RequestMapping(value = "/completeCheckAptitude")
    public ResultInfo completeAptitude(HttpServletRequest request, TraderCustomerVo traderCustomer, String comment, String taskId, boolean pass) {


        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + traderBaseInfo.getTraderCustomerId());

            variableMap.put("pass", pass);
            variableMap.put("updater", user.getUserId());
            if (pass) {
                verifiesRecordService.saveVerifiesInfoForTrader(taskId, 1);
            } else {
                User user1 = traderCustomerService.getPersonalUser(traderBaseInfo.getTraderId());
                List<Integer> userIds = new ArrayList<>();
                if (user1 != null && user1.getUserId() != null) {
                    userIds.add(user1.getUserId());
                    Map<String, String> map = new HashMap<>();
                    map.put("traderName", traderBaseInfo.getName());
                    String url = ErpConst.GET_FINANCE_AND_APTITUDE_URL + traderBaseInfo.getTraderId();
                    MessageUtil.sendMessage(98, userIds, map, url);
                }
                verifiesRecordService.saveVerifiesInfoForTrader(taskId, 2);
            }
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variableMap);


            }
            return new ResultInfo(0, "操作成功");
        }catch (Exception ex){
            return new ResultInfo(-1, "操作失败");
        }
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
    public ModelAndView getFinanceAndAptitude(HttpServletRequest request, TraderCustomerVo traderCustomer) {
        ModelAndView mav = new ModelAndView("trader/customer/view_financeAndAptitude");
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mav.addObject("currentUser", curr_user);
        TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
        if(traderCustomer.getCustomerNature()==null&&traderBaseInfo!=null){
            traderCustomer.setCustomerNature(traderBaseInfo.getCustomerNature());
        }
        mav.addObject("traderCustomer", traderBaseInfo);
        //Integer customerProperty = getCustomerCategory(traderCustomer.getTraderCustomerId());
        //mav.addObject("customerProperty", customerProperty);
        //traderCustomer.setCustomerProperty(customerProperty);
        TraderCertificateVo tc = new TraderCertificateVo();
        tc.setTraderId(traderCustomer.getTraderId());
        tc.setTraderType(ErpConst.ONE);
        if (traderCustomer.getCustomerNature() != null && traderCustomer.getCustomerNature() == 465) {
            tc.setCustomerType(2);
        } else {
            tc.setCustomerType(1);
        }
        Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + traderCustomer.getTraderCustomerId());
        mav.addObject("taskInfo", historicInfo.get("taskInfo"));
        mav.addObject("startUser", historicInfo.get("startUser"));
        mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
        // 最后审核状态
        mav.addObject("endStatus", historicInfo.get("endStatus"));
        mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
        mav.addObject("commentMap", historicInfo.get("commentMap"));
        String verifyUsers = null;
        String verifyUsersFinance = null;
        if (null != historicInfo.get("taskInfo")) {
            Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap((Task) historicInfo.get("taskInfo"));
            verifyUsers = (String) taskInfoVariables.get("verifyUsers");
        }
        mav.addObject("verifyUsers", verifyUsers);
        Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(tc, "all");
        CustomerAptitudeComment comment=traderCustomerService.getCustomerAptitudeCommentByTraderId(traderCustomer.getTraderId());
        if(comment!=null&&StringUtil.isNotBlank(comment.getComment())){
            mav.addObject("comment",comment.getComment());
        }
        List<TraderCertificateVo> bussinessList = null;
        // 营业执照信息
        if (map.containsKey("business")) {

//            bussinessList = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
            bussinessList = (List<TraderCertificateVo>) map.get("business");
            mav.addObject("bussinessList", bussinessList);
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
        // add by fralin.wu for[耗材商城的客户管理--代付款证明] at 2018-11-22 begin
        if (map.containsKey("pofPayList")) {
            mav.addObject("pofPayList", map.get("pofPayList"));
        }
        if (map.containsKey("trader")) {
            JSONObject json = JSONObject.fromObject(map.get("trader"));
            mav.addObject("trader", JSONObject.toBean(json, Trader.class));
        }
        // add by fralin.wu for[耗材商城的客户管理--代付款证明] at 2018-11-22 end

        if (traderCustomer.getCustomerNature() == 465) {
            // 二类医疗资质
            List<TraderCertificateVo> twoMedicalList = null;
            if (map.containsKey("twoMedical")) {
//				JSONObject json=JSONObject.fromObject(map.get("twoMedical"));
                twoMedicalList = (List<TraderCertificateVo>) map.get("twoMedical");
                mav.addObject("twoMedicalList", twoMedicalList);
            }
            // 三类医疗资质
            List<TraderCertificateVo> threeMedicalList = null;
            if (map.containsKey("threeMedical")) {
                try {
//				 JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
                    threeMedicalList = (List<TraderCertificateVo>) map.get("threeMedical");
                    mav.addObject("threeMedical", threeMedicalList);
                } catch (Exception e) {
                    logger.error("threeMedical", e);
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
            List<TraderMedicalCategoryVo> newTwo=null;
            if (map.containsKey("newTwo")) {
                JSONArray jsonArray = JSONArray.fromObject(map.get("newTwo"));
                newTwo = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
                mav.addObject("newTwo", newTwo);
            }
            List<TraderMedicalCategoryVo> newThree=null;
            if (map.containsKey("newThree")) {
                JSONArray jsonArray = JSONArray.fromObject(map.get("newThree"));
                three = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
                mav.addObject("newThree", three);
            }
        } else if (traderCustomer.getCustomerNature() == 466) {
            // 医疗机构执业许可证
            List<TraderCertificateVo> practiceList = null;
            if (map.containsKey("practice")) {
                // JSONObject json=JSONObject.fromObject(map.get("practice"));
                practiceList = (List<TraderCertificateVo>) map.get("practice");
                mav.addObject("practiceList", practiceList);
            }
        }
        // 财务信息
        TraderFinanceVo tf = null;
        if (map.containsKey("finance")) {
            JSONObject json = JSONObject.fromObject(map.get("finance"));
            tf = (TraderFinanceVo) JSONObject.toBean(json, TraderFinanceVo.class);
            if (tf != null && ObjectUtils.notEmpty(tf.getAverageTaxpayerUri())) {
                tf.setAverageTaxpayerDomain(domain);
            }
            if(tf!=null&&tf.getTraderFinanceId()!=null){
                Map<String, Object> historicInfoFinance = actionProcdefService.getHistoric(processEngine, "TraderFinanceCheck_" + tf.getTraderFinanceId());
                mav.addObject("financeTaskInfo", historicInfoFinance.get("taskInfo"));
                mav.addObject("financeStartUser", historicInfoFinance.get("startUser"));
                mav.addObject("financeCandidateUserMap", historicInfoFinance.get("candidateUserMap"));
                // 最后审核状态
                mav.addObject("financeEndStatus", historicInfoFinance.get("endStatus"));
                mav.addObject("financeHistoricActivityInstance", historicInfoFinance.get("historicActivityInstance"));
                mav.addObject("financeCommentMap", historicInfoFinance.get("commentMap"));
                if (null != historicInfoFinance.get("taskInfo")) {
                    Map<String, Object> taskInfoVariables = actionProcdefService.getVariablesMap((Task) historicInfoFinance.get("taskInfo"));
                    verifyUsersFinance = (String) taskInfoVariables.get("verifyUsers");
                }
                mav.addObject("verifyUsersFinance", verifyUsersFinance);
            }
            mav.addObject("finance", tf);
        }
        //帐期列表
        if (map.containsKey("billList")) {
            JSONArray jsonArray = JSONArray.fromObject(map.get("billList"));
            List<TraderAccountPeriodApply> billList = (List<TraderAccountPeriodApply>) JSONArray.toCollection(jsonArray, TraderAccountPeriodApply.class);
            mav.addObject("billList", billList);
        }


        User user = traderCustomerService.getPersonalUser(traderCustomer.getTraderId());
        if (isTraderBelongToUser(user,curr_user)) {
            mav.addObject("isbelong", true);
            mav.addObject("isFinanceBelong",true);
        } else if(traderBaseInfo.getSource()!=null&&traderBaseInfo.getSource()==1&&curr_user.getUsername().equals("haocai.vedeng")){
            mav.addObject("isFinanceBelong",true);
        }else {
            mav.addObject("isbelong", false);
            mav.addObject("isFinanceBelong",false);
        }


		TraderCustomerVo customerInfoByTraderCustomer = traderCustomerService.getCustomerInfoByTraderCustomer(traderCustomer);
		mav.addObject("customerInfoByTraderCustomer", customerInfoByTraderCustomer);

        mav.addObject("method", "financeandaptitude");
        return mav;
    }


    private boolean isTraderBelongToUser(User user,User currentUser){
        //查询该销售的领导
        if(user==null){
            return false;
        }
        List<User> leaders=userService.getLeadersByParentId(user.getParentId(),SysOptionConstant.ID_310);

        if(CollectionUtils.isEmpty(leaders)){
            leaders=new ArrayList<>();
        }
        leaders.add(user);
        for(User u:leaders){
            if(u!=null
                    &&u.getUserId()!=null
                    &&u.getUserId().equals(currentUser.getUserId())){
                return true;
            }
        }
        return false;
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
        ModelAndView mav = new ModelAndView("trader/customer/amount_bill_page");
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
        ModelAndView mav = new ModelAndView("trader/customer/capital_bill_page");
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
    @FormToken(save=true)
    public ModelAndView editAptitude(HttpServletRequest request, TraderCustomerVo traderCustomer) throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/edit_aptitude");

        TraderCertificateVo traderCertificate = new TraderCertificateVo();
        traderCertificate.setTraderId(traderCustomer.getTraderId());
        traderCertificate.setTraderType(ErpConst.ONE);
        TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomer);
        mav.addObject("traderCustomer", traderBaseInfo);
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mav.addObject("currentUser", curr_user);
        //Integer customerProperty=getCustomerCategory(traderCustomer.getTraderCustomerId());
        //mav.addObject("customerProperty", customerProperty);
        //traderCustomer.setCustomerProperty(customerProperty);
        CustomerAptitudeComment comment=traderCustomerService.getCustomerAptitudeCommentByTraderId(traderCustomer.getTraderId());
        if(comment!=null&&StringUtil.isNotBlank(comment.getComment())){
            mav.addObject("comment",comment.getComment());
        }
        Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "customerAptitude_" + traderCustomer.getTraderCustomerId());
        mav.addObject("taskInfo", historicInfo.get("taskInfo"));
        mav.addObject("startUser", historicInfo.get("startUser"));
        mav.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
        // 最后审核状态
        mav.addObject("endStatus", historicInfo.get("endStatus"));
        mav.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
        mav.addObject("commentMap", historicInfo.get("commentMap"));
        Page page = Page.newBuilder(null, null, null);
        if (traderCustomer.getCustomerNature() == 465) {
            traderCertificate.setCustomerType(2);
        } else {
            traderCertificate.setCustomerType(1);
        }
        Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(traderCertificate, "zz");
        StringBuffer sb = new StringBuffer();
        TraderCertificateVo tc = null;
        // 营业执照信息
        List<TraderCertificateVo> bussinessList=null;
        if (map.containsKey("business")) {
            bussinessList=(List<TraderCertificateVo>) map.get("business");
            mav.addObject("bussinessList", bussinessList);
            sb = sb.append(JsonUtils.translateToJson(bussinessList));
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
        if (traderCustomer.getCustomerNature() == 465) {
            // 二类医疗资质
            List<TraderCertificateVo> twoMedicalList = null;
            if (map.containsKey("twoMedical")) {
//				JSONObject json=JSONObject.fromObject(map.get("twoMedical"));
			    twoMedicalList=(List<TraderCertificateVo>) map.get("twoMedical");
				mav.addObject("twoMedicalList", twoMedicalList);
				if(MapUtils.isNotEmpty(map)){
					List<LinkedHashMap> twoList=(List<LinkedHashMap>)map.get("twoMedical");
					if(!CollectionUtils.isEmpty(twoList)){
						mav.addObject("qualification",twoList.get(0).get("medicalQualification"));
					}
				}

				sb=sb.append(JsonUtils.translateToJson(twoMedicalList));
			}
			// 三类医疗资质
			List<TraderCertificateVo> threeMedical = null;
			if(map.containsKey("threeMedical")){
//				JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
                threeMedical = (List<TraderCertificateVo>) map.get("threeMedical");
                mav.addObject("threeMedical", threeMedical);
                sb = sb.append(JsonUtils.translateToJson(threeMedical));
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
            List<TraderMedicalCategoryVo> newTwo = null;
            if (map.containsKey("newTwo")) {
                JSONArray jsonArray1 = JSONArray.fromObject(map.get("newTwo"));
                newTwo = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray1, TraderMedicalCategoryVo.class);
                mav.addObject("newTwo", newTwo);
                sb = sb.append(JsonUtils.translateToJson(three));
            }
            List<TraderMedicalCategoryVo> newThree = null;
            if (map.containsKey("newThree")) {
                JSONArray jsonArray = JSONArray.fromObject(map.get("newThree"));
                newThree = (List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
                mav.addObject("newThree", newThree);
                sb = sb.append(JsonUtils.translateToJson(three));
            }
        } else if (traderCustomer.getCustomerNature() == 466) {
            // 医疗机构执业许可证
            List<TraderCertificateVo> practiceList = null;
            if (map.containsKey("practice")) {
                practiceList = (List<TraderCertificateVo>) map.get("practice");
                mav.addObject("practiceList", practiceList);
                sb = sb.append(JsonUtils.translateToJson(practiceList));
            }
        }
        User user = traderCustomerService.getPersonalUser(traderCustomer.getTraderId());
        if (isTraderBelongToUser(user,curr_user)) {
            mav.addObject("isbelong", true);
        } else {
            mav.addObject("isbelong", false);
        }
        // 医疗类别
        List<SysOptionDefinition> medicalTypes = getSysOptionDefinitionList(SysOptionConstant.ID_20);
        List<SysOptionDefinition> newMedicalTypes=getSysOptionDefinitionList(SysOptionConstant.ID_1024);
        mav.addObject("medicalTypes", medicalTypes);
        mav.addObject("newMedicalTypes", newMedicalTypes);
        // 医疗类别级别
        List<SysOptionDefinition> medicalTypLevels = getSysOptionDefinitionList(SysOptionConstant.ID_21);

        mav.addObject("medicalTypLevels", medicalTypLevels);
        mav.addObject("domain", ossUrl);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(sb.toString())));
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 获取医疗类别
     *
     * @return
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月2日 下午5:41:14
     */
    @ResponseBody
    @RequestMapping(value = "/getMedicalTypeByAjax")
    public ResultInfo getMedicalTypeByAjax() {
        // 医疗类别
        List<SysOptionDefinition> medicalTypes = getSysOptionDefinitionList(SysOptionConstant.ID_20);

        // 医疗类别级别
        List<SysOptionDefinition> medicalTypLevels = getSysOptionDefinitionList(SysOptionConstant.ID_21);
        ResultInfo res = new ResultInfo<>(1, "操作成功");
        res.setData(medicalTypes);
        res.setListData(medicalTypLevels);
        return res;
    }

    /**
     * <b>Description:</b><br>
     * 保存客户资质
     *
     * @return
     * @throws CloneNotSupportedException
     * @Note <b>Author:</b> east <br>
     * <b>Date:</b> 2017年6月5日 下午1:08:16
     */
    @ResponseBody
    @RequestMapping(value = "saveAptitude")
    @SystemControllerLog(operationType = "edit", desc = "保存客户资质")
    @FormToken(remove = true)
//    @Transactional
    public ModelAndView saveAptitude(HttpServletRequest request, TraderVo traderVo, TraderCustomerVo traderCustomer, String beforeParams,String taskId) throws CloneNotSupportedException {
        List<TraderCertificateVo> list = new ArrayList<>();
        String traderId = request.getParameter("traderId");
        String threeInOne = request.getParameter("threeInOne");
        String medicalQualification = request.getParameter("medicalQualification");
        String comment=request.getParameter("comment");
        CustomerAptitudeComment addComment=new CustomerAptitudeComment();
        addComment.setComment(comment);
        addComment.setTraderCustomerId(Integer.valueOf(traderId));
        traderCustomerService.addCustomerAptitudeComment(addComment);
        //Integer customerProperty=getCustomerCategory(Integer.valueOf(request.getParameter("traderCustomerId")));
        if (threeInOne != null && Integer.valueOf(threeInOne) == 1) {// 三证合一
            //营业执照
            TraderCertificateVo bus = saveBussiness(request);
            if (bus != null) {
                bus.setThreeInOne(1);
                list.add(bus);
                //税务登记
                TraderCertificateVo tax = (TraderCertificateVo) bus.clone();
                tax.setThreeInOne(1);
                tax.setSysOptionDefinitionId(SysOptionConstant.ID_26);
                tax.setUris(null);
                list.add(tax);
                //组织机构证
                TraderCertificateVo org = (TraderCertificateVo) bus.clone();
                org.setSysOptionDefinitionId(SysOptionConstant.ID_27);
                org.setThreeInOne(1);
                org.setUris(null);
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
        List<TraderMedicalCategory> newTwomcList = null;
        List<TraderMedicalCategory> newThreemcList = null;
        if (traderCustomer.getCustomerNature() == 465) {
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
                    newTwomcList=saveNewTwoMedicalAptitude(request);
                    newThreemcList=saveNewThreeMedicalAptitude(request);
                }

            } else {
                TraderCertificateVo two = saveTwoMedical(request);
                twomcList = saveMedicalAptitudes(request);
                newTwomcList=saveNewTwoMedicalAptitude(request);
                if (two != null) {
                    two.setMedicalQualification(0);
                    list.add(two);
                    //根据资质类别的数量获取资质类别所属的分类

                }
                TraderCertificateVo three = saveThreeMedical(request);
                threemcList = saveThreeMedicalAptitudes(request);
                newThreemcList=saveNewThreeMedicalAptitude(request);
                if (three != null) {
                    three.setMedicalQualification(0);
                    list.add(three);
                    //根据资质类别的数量获取资质类别所属的分类

                }
            }

        } else if (traderCustomer.getCustomerNature() == 466) {
            TraderCertificateVo practice = savePractice(request);
            if (practice != null) {
                list.add(practice);
            }
            String isProfit = request.getParameter("isProfit");
            traderCustomerService.updateCustomerIsProfit(Integer.valueOf(traderId),Integer.valueOf(isProfit));
        }
        TraderCertificateVo product = saveProduct(request);
        if (product != null) {
            list.add(product);
        }
//		if(list.size()==0&&tcmcList==null){
//			ResultInfo res =new ResultInfo(2, "请至少填写/选择一项数据");
//			return res;
//		}
        ResultInfo res = traderCustomerService.saveNewMedicaAptitude(traderVo, list, twomcList, threemcList,newTwomcList,newThreemcList);
        String customerAptitude=request.getParameter("customerAptitude");
        if (res != null && res.getCode() == 0 && (StringUtil.isBlank(customerAptitude) || Integer.valueOf(customerAptitude).intValue() != 0)) {
            startCheckAptitude(request, traderCustomer, taskId);
        }
        ModelAndView mav = new ModelAndView();
        if (res != null && res.getCode() == 0) {
            //更新贝登会员
            traderCustomerService.updateVedengMember();
            mav.addObject("refresh", "true_false_true");//是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
            mav.addObject("url", "./getFinanceAndAptitude.do??traderId=" + traderCustomer.getTraderId() + "&traderCustomerId=" + traderCustomer.getTraderCustomerId());
            return success(mav);
        } else {
            return fail(new ModelAndView());
        }
    }

    /**
     * <b>Description:</b><br> 获取二类医疗资质新国标
     * @param request 请求参数
     * @return
     * @Note
     * <b>Author:</b>
     * <br><b>Date:</b>
     */
    private List<TraderMedicalCategory> saveNewTwoMedicalAptitude(HttpServletRequest request){
        String medicalTypes[] = request.getParameterValues("newTwoMedicalType");
        String traderId = request.getParameter("traderId");
        String traderType = request.getParameter("traderType");
        TraderMedicalCategory tcac = null;
        if(medicalTypes==null)
            return null;
        if (medicalTypes.length > 0) {
            List<TraderMedicalCategory> list = new ArrayList<>();
            for (int i = 0; i < medicalTypes.length; i++) {
                if (ObjectUtils.notEmpty(medicalTypes[i])) {
                    tcac = new TraderMedicalCategory();
                    tcac.setTraderId(Integer.valueOf(traderId));
                    tcac.setTraderType(Integer.valueOf(traderType));

                    tcac.setMedicalCategoryId(Integer.valueOf(medicalTypes[i]));

                    tcac.setMedicalCategoryLevel(SysOptionConstant.NEW_TWO_MEDICAL_CATEGORY);

                    list.add(tcac);
                }
            }
            return list;
        } else {
            return null;
        }
    }
    /**
     * <b>Description:</b><br> 获取三类医疗资质新国标
     * @param request 请求参数
     * @return
     * @Note
     * <b>Author:</b>
     * <br><b>Date:</b>
     */
    private List<TraderMedicalCategory> saveNewThreeMedicalAptitude(HttpServletRequest request){
        String medicalTypes[] = request.getParameterValues("newThreeMedicalType");
        String traderId = request.getParameter("traderId");
        String traderType = request.getParameter("traderType");
        TraderMedicalCategory tcac = null;
        if(medicalTypes==null)
            return null;
        if (medicalTypes.length > 0) {
            List<TraderMedicalCategory> list = new ArrayList<>();
            for (int i = 0; i < medicalTypes.length; i++) {
                if (ObjectUtils.notEmpty(medicalTypes[i])) {
                    tcac = new TraderMedicalCategory();
                    tcac.setTraderId(Integer.valueOf(traderId));
                    tcac.setTraderType(Integer.valueOf(traderType));

                    tcac.setMedicalCategoryId(Integer.valueOf(medicalTypes[i]));

                    tcac.setMedicalCategoryLevel(SysOptionConstant.NEW_THREE_MEDICAL_CATEGORY);

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
        if(medicalTypes==null)
            return null;
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
        if(medicalTypes==null)
            return null;
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
        String[] otherPicName = request.getParameterValues("name_1");
        // 营业执照
        TraderCertificateVo bus = new TraderCertificateVo();

        String busName = request.getParameter("busName");
        if (ObjectUtils.notEmpty(busName)) {
            String busUri = request.getParameter("busUri");
            bus.setName(busName);
            bus.setUri(busUri);
        }
        if (otherPicName!=null&&otherPicName.length>0) {
            String[] uris = request.getParameterValues("uri_1");
            bus.setUris(uris);
            bus.setNames(otherPicName);
        }
        bus.setCreator(user.getUserId());
        bus.setAddTime(System.currentTimeMillis());
        bus.setUpdater(user.getUserId());
        bus.setModTime(System.currentTimeMillis());

        bus.setTraderId(Integer.valueOf(traderId));
        bus.setTraderType(ErpConst.ONE);
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
        tax.setTraderType(ErpConst.ONE);
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
        org.setTraderType(ErpConst.ONE);
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
	 *       <b>Date:</b> 2017年6月5日 下午1:54:45
	 */
	private TraderCertificateVo saveTwoMedical(HttpServletRequest request) {
		String twoStartTime = request.getParameter("twoStartTime");
		String[] otherPicName = request.getParameterValues("name_4");
		if(ObjectUtils.isEmpty(twoStartTime)){
			return null;
		}
		User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String twoTraderCertificateId = request.getParameter("twoTraderCertificateId");
        String traderId = request.getParameter("traderId");
        String twoName = request.getParameter("twoName");

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
        two.setTraderType(ErpConst.ONE);
        two.setSysOptionDefinitionId(SysOptionConstant.ID_28);
        two.setIsMedical(0);
        if (ObjectUtils.notEmpty(twoName)) {
            String twoUri = request.getParameter("twoUri");
            two.setUri(twoUri);
            two.setName(twoName);
        }
        if (otherPicName!=null&&otherPicName.length>0) {
            String[] uris = request.getParameterValues("uri_4");
            two.setUris(uris);
            two.setNames(otherPicName);
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
        String[] otherPicName = request.getParameterValues("name_5");
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
        three.setTraderType(ErpConst.ONE);
        three.setSysOptionDefinitionId(SysOptionConstant.ID_29);
        three.setIsMedical(0);
        if (ObjectUtils.notEmpty(threeName)) {
            String threeUri = request.getParameter("threeUri");
            three.setUri(threeUri);
            three.setName(threeName);
        }

        if (otherPicName!=null&&otherPicName.length>0) {
            String[] uris = request.getParameterValues("uri_5");
            three.setUris(uris);
            three.setNames(otherPicName);
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
        String practiceName = request.getParameter("practiceName");
        String[] otherPicName = request.getParameterValues("name_4");//多张图片的URI
        if (ObjectUtils.isEmpty(practiceName)) {
            return null;
        }
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
//		String practiceTraderCertificateId = request.getParameter("practiceTraderCertificateId");
        String traderId = request.getParameter("traderId");

        String practiceTime = request.getParameter("practiceStartTime");
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
        practice.setTraderType(ErpConst.ONE);
        practice.setSysOptionDefinitionId(SysOptionConstant.ID_438);
        if (ObjectUtils.notEmpty(practiceTime)) {
            String practiceUri = request.getParameter("practiceUri");
            practice.setUri(practiceUri);
            practice.setName(practiceName);
        }
        //判断是否有多张图片
        if (otherPicName!=null&&otherPicName.length>0) {
            String[] uris = request.getParameterValues("uri_4");
            practice.setUris(uris);
            practice.setNames(otherPicName);
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
        product.setTraderType(ErpConst.ONE);
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
     * <b>Description:</b><br> 分配客户
     *
     * @param request
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月5日 下午5:53:47
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/assign")
    public ModelAndView assign(HttpServletRequest request,
                               @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) Integer pageSize,
                               HttpSession session) {
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView();
        // 查询所有职位类型为310的员工
        List<User> userList = userService.getActiveUserByPositType(SysOptionConstant.ID_310, session_user.getCompanyId());

        //地区
        List<Region> provinceList = regionService.getRegionByParentId(1);

        String type = request.getParameter("type");
        String traderName = request.getParameter("traderName");
        String from_user = request.getParameter("from_user");
        String provinceId = request.getParameter("province");
        String cityId = request.getParameter("city");
        String zoneId = request.getParameter("zone");

        if (type != null) {
            if (type.equals("1")) {//单个分配查询
                from_user = "0";

                //查询集合
                Page page = getPageTag(request, pageNo, pageSize);
                RTraderJUser rTraderJUser = new RTraderJUser();
                rTraderJUser.setTraderName(traderName);
                rTraderJUser.setCompanyId(session_user.getCompanyId());

                Map<String, Object> map = traderCustomerService.getUserTraderByTraderNameListPage(rTraderJUser, page);

                mv.addObject("traderList", (List<RTraderJUser>) map.get("list"));
                mv.addObject("page", (Page) map.get("page"));
            }
            if (type.equals("2")) {//批量分配查询
                traderName = "";

                User user = userService.getUserById(Integer.parseInt(from_user));
                RTraderJUser rTraderJUser = new RTraderJUser();
                rTraderJUser.setUserId(Integer.parseInt(from_user));
                rTraderJUser.setCompanyId(session_user.getCompanyId());
                if (!zoneId.equals("0")) {
                    rTraderJUser.setAreaId(Integer.parseInt(zoneId));
                } else {
                    //String areaIds = "";
                    if (!provinceId.equals("0")) {
                        //areaIds += provinceId+",";
                        rTraderJUser.setAreaId(Integer.parseInt(provinceId));
                    }
                    if (!cityId.equals("0")) {
                        //areaIds += ","+cityId;
                        rTraderJUser.setAreaId(Integer.parseInt(cityId));
                    }

                    //rTraderJUser.setAreaIds(areaIds);
                }

                Integer userSupplierNum = traderCustomerService.getUserCustomerNum(rTraderJUser, Integer.parseInt(from_user)).size();

                if (!provinceId.equals("0")) {
                    List<Region> cityList = regionService.getRegionByParentId(Integer.parseInt(provinceId));
                    mv.addObject("cityList", cityList);
                }
                if (!cityId.equals("0")) {
                    List<Region> zoneList = regionService.getRegionByParentId(Integer.parseInt(cityId));
                    mv.addObject("zoneList", zoneList);
                }
                mv.addObject("user", user);
                mv.addObject("userSupplierNum", userSupplierNum);
            }
        }
        //获取销售部门
        List<Organization> orgList = orgService.getAllOrganizationListByType(SysOptionConstant.ID_310,
                session_user.getCompanyId());
        mv.addObject("orgList", orgList);
        mv.addObject("userList", userList);
        mv.addObject("provinceList", provinceList);

        mv.addObject("type", type);
        mv.addObject("traderName", traderName);
        mv.addObject("from_user", from_user);
        mv.addObject("provinceId", provinceId);
        mv.addObject("cityId", cityId);
        mv.addObject("zoneId", zoneId);
        mv.setViewName("trader/customer/assign");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存分配客户
     *
     * @param type
     * @param traderId
     * @param single_to_user
     * @param from_user
     * @param batch_to_user
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月6日 下午2:17:09
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveassign")
    @SystemControllerLog(operationType = "edit", desc = "保存分配客户")
    public ResultInfo<?> saveAssign(Integer type, Integer traderId, Integer single_to_user,
                                    Integer from_user, Integer batch_to_user,
                                    Integer provinceId, Integer cityId, Integer zoneId,
                                    HttpSession session) {
        try {
            User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
            ResultInfo<?> resultInfo = new ResultInfo<>();

            Boolean succ = false;
            //推送客户Id列表
            List<Integer> traderIdList = Lists.newArrayList();
            Integer salerId = 0;
            String salerName = "";
            if (type.equals(1)) {
                succ = traderCustomerService.assignSingleCustomer(traderId, single_to_user,session_user.getCompanyId());
                traderIdList.add(traderId);
                salerId = single_to_user;
                try {
                    salerName = userService.getUserNameByUserId(salerId);
                } catch (Exception e) {
                    logger.error("TraderCustomerController--saveassign--查询用户名异常", e);
                }
            }

            if (type.equals(2)) {
                String areaIds = "";
                Integer areaId = 0;
                if (zoneId > 0) {
                    areaId = zoneId;
                } else {
                    if (provinceId > 0) {
                        areaIds += provinceId;
                        areaId = provinceId;
                    }
                    if (cityId > 0) {
                        areaIds += "," + cityId + ",";
                        areaId = cityId;
                    }
                }
                succ = traderCustomerService.assignBatchCustomer(session_user.getCompanyId(), from_user, batch_to_user, areaId, areaIds);
                //根据销售ID查询该销售下的所有所属客户
                RTraderJUser rTraderJUser = new RTraderJUser();
                rTraderJUser.setUserId(from_user);
                rTraderJUser.setCompanyId(session_user.getCompanyId());
                if (!zoneId.equals("0")) {
                    rTraderJUser.setAreaId(zoneId);
                } else {
                    //String areaIds = "";
                    if (!provinceId.equals("0")) {
                        //areaIds += provinceId+",";
                        rTraderJUser.setAreaId(provinceId);
                    }
                    if (!cityId.equals("0")) {
                        //areaIds += ","+cityId;
                        rTraderJUser.setAreaId(cityId);
                    }

                    //rTraderJUser.setAreaIds(areaIds);
                }
                List<RTraderJUser> traderList = traderCustomerService.getUserCustomerNum(rTraderJUser, from_user);
                salerId = batch_to_user;
                try {
                    salerName = userService.getUserNameByUserId(salerId);
                } catch (Exception e) {
                    logger.error("TraderCustomerController--saveassign--查询用户名异常", e);
                }
                if (!CollectionUtils.isEmpty(traderList)) {
                    for (RTraderJUser rTraderJUserTemp : traderList) {
                        traderIdList.add(rTraderJUserTemp.getTraderId());
                    }
                }
            }
            if (succ) {
                resultInfo.setCode(0);
                resultInfo.setMessage("操作成功");
                //客户分配推送到医械购
                Map<String, Object> map = Maps.newHashMap();
                map.put("salerId", salerId);
                map.put("salerName", salerName);
                map.put("traderIdList", traderIdList);
                traderCustomerService.putTraderSaleUserIdtoHC(map);
            }
            return resultInfo;
        } catch (Exception e) {
            logger.error("保存分配客户信息异常：", e);
            return new ResultInfo<>(CommonConstants.FAIL_CODE, CommonConstants.FAIL_MSG);
        }
    }

    /**
     * <b>Description:</b>保存财务信息<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/12/4
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveYxgTraderFinance")
    public ResultInfo saveYxgTraderFinance(HttpServletRequest request,TraderFinance traderFinance){
        ResultInfo result=saveTraderFinance(request,traderFinance);
        if(result.getCode()==0 && StringUtil.isBlank(traderFinance.getTaxNum())){
            if(!(StringUtil.isBlank(traderFinance.getPreTaxNum())
                    &&traderFinance.getCheckStatus()!=null
                    &&traderFinance.getCheckStatus()==1)){
                startFinanceCheck(request,traderFinance,null);
            }
        }else if(result.getCode()==0){
            traderCustomerService.syncTraderFinanceToPlatformOfYxg(traderFinance);
        }
        ResultInfo res=new ResultInfo(0,"操作成功");
        res.setData(request.getParameter("traderId") + "," + request.getParameter("traderCustomerId"));
        return res;
    }

    private ResultInfo saveTraderFinance(HttpServletRequest request,TraderFinance traderFinance){

        User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        ResultInfo res = traderCustomerService.saveCustomerFinance(traderFinance, user);
        return res;
    }

    /**
     * <b>Description:</b>完成财务信息审核接口<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/12/4
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/finishYxgFinanceCheck")
    @Transactional
    public ResultInfo finishFinanceCheck(HttpServletRequest request,TraderFinance traderFinance,String comment,String taskId,boolean pass) throws Exception{
        ResultInfo resultInfo=completeFinanceCheck(request,traderFinance,comment,taskId,pass);
        if(pass&&resultInfo.getCode()==0){
            saveTraderFinance(request,traderFinance);
            ResultInfo res=traderCustomerService.syncTraderFinanceToPlatformOfYxg(traderFinance);
//            if(res.getCode()==-1){
//                throw new Exception("更新到医械购平台失败");
//            }
        }
        resultInfo.setData(request.getParameter("traderId") + "," + request.getParameter("traderCustomerId"));
        return resultInfo;
    }

    /**
     * <b>Description:</b>完成医械购财务信息审核操作数据库的接口<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/12/4
     */
    @Transactional
    public ResultInfo completeFinanceCheck(HttpServletRequest request,TraderFinance traderFinance, String comment, String taskId, boolean pass){
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            TraderCustomerVo traderBaseInfo = traderCustomerService.getTraderCustomerInfo(traderFinance.getTraderId());
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "TraderFinanceCheck_" + traderFinance.getTraderFinanceId());

            variableMap.put("pass", pass);
            variableMap.put("updater", user.getUserId());
            if (pass) {
                verifiesRecordService.saveVerifiesInfoForTrader(taskId, 1);
            } else {
//                User user1 = traderCustomerService.getPersonalUser(traderBaseInfo.getTraderId());
//                List<Integer> userIds = new ArrayList<>();
//                if (user1 != null && user1.getUserId() != null) {
//                    userIds.add(user1.getUserId());
//                    Map<String, String> map = new HashMap<>();
//                    map.put("traderName", traderBaseInfo.getName());
//                    String url = ErpConst.GET_FINANCE_AND_APTITUDE_URL + traderBaseInfo.getTraderId();
//                    MessageUtil.sendMessage(99, userIds, map, url);
//                }
                verifiesRecordService.saveVerifiesInfo(taskId, 2);
            }
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, comment, user.getUsername(), variableMap);


            }
            return new ResultInfo(0, "操作成功");
        }catch (Exception ex){
            throw  ex;
        }
    }
    @Transactional
    public ResultInfo startFinanceCheck(HttpServletRequest request,TraderFinance finance, String taskId){
        try {
            Map<String, Object> variableMap = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            TraderFinanceVo traderFinanceVo= new TraderFinanceVo();
            traderFinanceVo.setTraderId(finance.getTraderId());
            traderFinanceVo.setTraderType(1);
            TraderFinanceVo traderFinance=traderCustomerService.getTraderFinanceByTraderId(traderFinanceVo);
            //开始生成流程(如果没有taskId表示新流程需要生成)
            if (StringUtil.isBlank(taskId) || taskId.equals("0")) {
//                variableMap.put("traderCustomerVo", traderBaseInfo);
                variableMap.put("currentAssinee", user.getUsername());
                variableMap.put("processDefinitionKey", "customerAptitudeVerify");
                variableMap.put("businessKey", "TraderFinanceCheck_" + traderFinance.getTraderFinanceId());
                variableMap.put("relateTableKey", traderFinance.getTraderFinanceId());
                variableMap.put("relateTable", "T_TRADER_FINANCE");
                actionProcdefService.createProcessInstance(request, "TraderFinanceCheck", "TraderFinanceCheck_" + traderFinance.getTraderFinanceId(), variableMap);
            }
            //默认申请人通过
            //根据BusinessKey获取生成的审核实例
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "TraderFinanceCheck_" + traderFinance.getTraderFinanceId());
            if (historicInfo.get("endStatus") != "审核完成") {
                Task taskInfo = (Task) historicInfo.get("taskInfo");
                taskId = taskInfo.getId();
                ResultInfo<?> complementStatus = actionProcdefService.complementTask(request, taskId, "", user.getUsername(), variableMap);
                //如果未结束添加审核对应主表的审核状态
                if (!complementStatus.getData().equals("endEvent")) {
                    verifiesRecordService.saveVerifiesInfo(taskId, 0);
                }

            }
            List<Integer> userIds=new ArrayList<>();
            Page page=new Page(1,20);
            RoleVo role=new RoleVo();
            role.setRoleName("财务专员");
            List<Role> roles=roleService.queryListPage(role,page);
            TraderCustomerVo traderCustomer=traderCustomerService.getTraderCustomerInfo(traderFinance.getTraderId());
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(roles)) {
                for (Role r : roles) {
                    if(r.getCompanyId()!=1){
                        continue;
                    }
                    List<User> users = userService.getUserByRoleId(r.getRoleId());
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(users)) {
                        for (User u:users){
                            userIds.add(u.getUserId());
                        }
                    }
                }
            }
            Map<String,String> map=new HashMap<>();
            map.put("traderName",traderCustomer.getTraderName());
            String url=ErpConst.GET_FINANCE_AND_APTITUDE_URL+traderCustomer.getTraderId();
            MessageUtil.sendMessage(99,userIds,map,url);
            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("editApplyValidCustomer:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }
    }
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/yxgTraderFinanceComplement")
    public ModelAndView yxgTraderFinanceComplement(HttpServletRequest request,TraderCustomer customer,TraderFinance finance,String taskId,boolean pass){
        ModelAndView mv=new ModelAndView("trader/customer/yxg_finance_check_complement");
        mv.addObject("pass",pass);
        mv.addObject("taskId",taskId);
        mv.addObject("traderCustomer",customer);
        mv.addObject("finance",finance);
        return mv;
    }
    /**
     * <b>Description:</b>医械购客户专用改税号接口<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2019/12/3
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/editYxgFinancePage")
    public ModelAndView editYXGTraderFinance(HttpServletRequest request,TraderCustomer traderCustomer, Integer customerProperty){
        ModelAndView mav = new ModelAndView("trader/customer/edit_yxg_trader_finance");
        User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
        mav.addObject("traderCustomer", traderCustomer);
        TraderFinanceVo tf = new TraderFinanceVo();
        tf.setTraderId(traderCustomer.getTraderId());
        tf.setTraderType(ErpConst.ONE);

        TraderFinanceVo traderFinance = traderCustomerService.getTraderFinanceByTraderId(tf);
        if(traderFinance!=null) {
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "TraderFinanceCheck_" + traderFinance.getTraderFinanceId());
            mav.addObject("financeTaskInfo", historicInfo.get("taskInfo"));
            mav.addObject("financeStartUser", historicInfo.get("startUser"));
            mav.addObject("financeCandidateUserMap", historicInfo.get("candidateUserMap"));
            // 最后审核状态
            mav.addObject("financeEndStatus", historicInfo.get("endStatus"));
            mav.addObject("financeHistoricActivityInstance", historicInfo.get("historicActivityInstance"));
            mav.addObject("financeCommentMap", historicInfo.get("commentMap"));
            mav.addObject("traderFinance", traderFinance);
            mav.addObject("customerProperty", customerProperty);
            mav.addObject("domain", domain);
        }
        User user = traderCustomerService.getPersonalUser(traderCustomer.getTraderId());
        if (user != null && user.getUserId() != null && curr_user.getUserId().intValue() == Integer.valueOf(user.getUserId()).intValue()) {
            mav.addObject("isbelong", true);
        } else if(curr_user.getUsername().equals("haocai.vedeng")){
            mav.addObject("isbelong", true);
        }else{
            mav.addObject("isbelong", false);
        }
        try {
            mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderFinance)));
        }catch (Exception ex){}
        return mav;
    }
    /**
     * <b>Description:</b><br> 客户的财务信息
     *
     * @return
     * @throws IOException
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:07:00
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/toEditFinancePage")
    public ModelAndView toEditFinancePage(TraderCustomer traderCustomer, Integer customerProperty) throws IOException {
        ModelAndView mav = new ModelAndView("trader/customer/edit_finance");
        mav.addObject("traderCustomer", traderCustomer);
        TraderFinanceVo tf = new TraderFinanceVo();
        tf.setTraderId(traderCustomer.getTraderId());
        tf.setTraderType(ErpConst.ONE);

        TraderFinanceVo traderFinance = traderCustomerService.getTraderFinanceByTraderId(tf);

        mav.addObject("traderFinance", traderFinance);
        if(traderFinance!=null) {
            Map<String, Object> historicInfo = actionProcdefService.getHistoric(processEngine, "TraderFinanceCheck_" + traderFinance.getTraderFinanceId());
            mav.addObject("financeTaskInfo", historicInfo.get("taskInfo"));
        }
        mav.addObject("customerProperty", customerProperty);
        mav.addObject("domain", domain);
        mav.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(traderFinance)));
        return mav;
    }

    /**
     * <b>Description:</b><br> 保存客户财务信息
     *
     * @param session
     * @param traderCustomer
     * @param traderFinance
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月7日 下午3:58:17
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveCustomerFinance")
    @SystemControllerLog(operationType = "add", desc = "保存客户财务信息")
    public ResultInfo saveCustomerFinance(HttpServletRequest request, HttpSession session, TraderFinance traderFinance, String beforeParams) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        //String upload_file_tmp=request.getParameter("upload_file_tmp");
        if(traderFinance==null){
            return new ResultInfo(-1,"财务信息不得为空");
        }
        ResultInfo res = traderCustomerService.saveCustomerFinance(traderFinance, user);
        res.setData(request.getParameter("traderId") + "," + request.getParameter("traderCustomerId"));
        //保存客户财务信息后调用同步网站接口
        if(StringUtil.isBlank(traderFinance.getTaxNum())){
            if(!(StringUtil.isBlank(traderFinance.getPreTaxNum())
                    &&traderFinance.getCheckStatus()!=null
                    &&traderFinance.getCheckStatus()==1)
                   ){
                startFinanceCheck(request,traderFinance,null);
            }



        }
        vedengSoapService.financeInfoSync(traderFinance.getTraderId());
        return res;
    }


    /**
     * <b>Description:</b><br>
     * @param traderCustomerId
     * @return
     * @Note
     * <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月21日 下午1:57:50
     */
	/*@ResponseBody
	@RequestMapping(value="/accountperiodapplycheck")
	public ResultInfo accountPeriodApplyCheck(Integer traderCustomerId){
		ResultInfo resultInfo = new ResultInfo<>();
		if(traderCustomerId <= 0){
			return resultInfo;
		}
		TraderAccountPeriodApply lastAccountPeriodApply = traderCustomerService.getTraderCustomerLastAccountPeriodApply(traderCustomerId);

		if(null == lastAccountPeriodApply
				&& lastAccountPeriodApply.getStatus() != 0){
			resultInfo.setCode(0);
			resultInfo.setMessage("操作成功");
		}else{
			resultInfo.setMessage("请等待当前帐期审核完成");
		}
		return resultInfo;
	}*/

    /**
     * <b>Description:</b><br> 客户账期申请
     *
     * @param traderCustomer
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月21日 上午10:43:56
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/accountperiodapply")
    public ModelAndView accountPeriodApply(TraderCustomer traderCustomer) {
        TraderAccountPeriodApply lastAccountPeriodApply = traderCustomerService.getTraderCustomerLastAccountPeriodApply(traderCustomer.getTraderId());

        ModelAndView mav = new ModelAndView("trader/customer/apply_accountperiod");
        if (null != lastAccountPeriodApply
                && lastAccountPeriodApply.getStatus() == 0) {
            mav.addObject("message", "当前有账期未审核，请等待当前帐期审核完成！");
        } else {
            TraderCustomer traderCustomerForAccountPeriod = traderCustomerService.getTraderCustomerForAccountPeriod(traderCustomer);
            mav.addObject("traderCustomer", traderCustomerForAccountPeriod);
        }
        return mav;
    }

    /**
     * <b>Description:</b><br> 保存账期申请
     *
     * @param request
     * @param session
     * @param traderAccountPeriodApply
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年6月21日 上午11:47:02
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/saveaccountperiodapply")
    @SystemControllerLog(operationType = "add", desc = "保存客户账期申请")
    public ResultInfo saveAccountPeriodApply(HttpServletRequest request, HttpSession session, TraderAccountPeriodApply traderAccountPeriodApply) {
        traderAccountPeriodApply.setTraderType(ErpConst.ONE);
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
                // return new ResultInfo(0, "操作成功");
            } catch (Exception e) {
                logger.error("saveaccountperiodapply:", e);
                return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
            }
        }
        resultInfo.setData(request.getParameter("traderId") + "," + request.getParameter("traderCustomerId"));
        return resultInfo;
    }

    /**
     * <b>Description:</b><br> 查询客户属于分销还是终端
     *
     * @param traderCustomerId
     * @return
     * @Note <b>Author:</b> east
     * <br><b>Date:</b> 2017年6月26日 下午4:23:40
     */
    private Integer getCustomerCategory(Integer traderCustomerId) {
        return traderCustomerService.getCustomerCategory(traderCustomerId);
    }

    /**
     * <b>Description:</b><br> 查询终端客户列表
     *
     * @param request
     * @param traderCustomerVo
     * @return
     * @Note <b>Author:</b> duke
     * <br><b>Date:</b> 2017年6月27日 下午3:13:04
     */
    @RequestMapping(value = "/getTerminalList")
    public ModelAndView getTerminalList(HttpServletRequest request, TraderCustomerVo traderCustomerVo,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        ModelAndView mv = new ModelAndView();

        try {
            User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
            traderCustomerVo.setCompanyId(user.getCompanyId());

            String linename = request.getParameter("searchTraderName");
            //java : 字符解码
            traderCustomerVo.setSearchTraderName(java.net.URLDecoder.decode(java.net.URLDecoder.decode(linename, "UTF-8"), "UTF-8"));

            if (traderCustomerVo != null && org.apache.commons.lang.StringUtils.isNotBlank(traderCustomerVo.getSearchTraderName())) {
                Page page = getPageTag(request, pageNo, pageSize);

                Map<String, Object> map = traderCustomerService.getTerminalPageList(traderCustomerVo, page);

                mv.addObject("page", (Page) map.get("page"));
                mv.addObject("terminalList", (List<TraderCustomerVo>) map.get("terminalList"));

            }
            mv.addObject("traderCustomerVo", traderCustomerVo);

            //终端类型
            List<SysOptionDefinition> terminalTypeList = getSysOptionDefinitionList(425);
//			if(JedisUtils.exists(ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 425)){
//				String json_result = JedisUtils.get(ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 425);
//				JSONArray jsonArray = JSONArray.fromObject(json_result);
//				terminalTypeList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			}
            mv.addObject("terminalTypeList", terminalTypeList);

            // 省级地区
            List<Region> provinceList = regionService.getRegionByParentId(1);
            mv.addObject("provinceList", provinceList);

            mv.setViewName("trader/customer/list_terminal");
        } catch (Exception e) {
            logger.error("getTerminalList:", e);
        }
        mv.addObject("traderCustomerVo", traderCustomerVo);

        //终端类型
        List<SysOptionDefinition> terminalTypeList = getSysOptionDefinitionList(425);
//		if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 425)){
//			String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + 425);
//			JSONArray jsonArray = JSONArray.fromObject(json_result);
//			terminalTypeList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//		}
        mv.addObject("terminalTypeList", terminalTypeList);

        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mv.addObject("provinceList", provinceList);
        mv.setViewName("trader/customer/list_terminal");
        return mv;
    }

    /**
     * <b>Description:</b><br> 搜索客户列表（弹框简单版）
     *
     * @param request
     * @param traderCustomerVo
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> leo.yang
     * <br><b>Date:</b> 2017年7月7日 下午5:35:54
     */
    @ResponseBody
    @RequestMapping(value = "/searchCustomerList")
    public ModelAndView searchCustomerList(HttpServletRequest request, TraderCustomerVo traderCustomerVo,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) Integer lendOut,
                                           @RequestParam(required = false) Integer traderType) {
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        try {
            String linename = request.getParameter("searchTraderName");
            //java : 字符解码
            traderCustomerVo.setSearchTraderName((java.net.URLDecoder.decode(java.net.URLDecoder.decode(linename, "UTF-8"), "UTF-8")).trim());
            traderCustomerVo.setCompanyId(user.getCompanyId());
            mv.addObject("lendOut", lendOut);
            if (traderCustomerVo != null && org.apache.commons.lang.StringUtils.isNotBlank(traderCustomerVo.getSearchTraderName())) {
                Page page = getPageTag(request, pageNo, pageSize);
                Map<String, Object> map = traderCustomerService.searchCustomerPageList(traderCustomerVo, page);
                mv.addObject("page", (Page) map.get("page"));
                //外借商品出库搜索
                if (lendOut != null && lendOut == 1) {
                    mv.addObject("lendOut", 1);
                    List<TraderCustomerVo> searchCustomerList = (List<TraderCustomerVo>) map.get("searchCustomerList");
                    List<TraderVo> traderList = new ArrayList<TraderVo>();
                    for (TraderCustomerVo t : searchCustomerList) {
                        TraderVo trader = new TraderVo();
                        trader.setTraderId(t.getTraderId());
                        trader.setTraderName(t.getTraderName());
                        trader.setOwner(t.getPersonal());
                        trader.setAddress(t.getAddress());
                        trader.setAddTime(t.getAddTime());
                        trader.setTraderType(1);
                        traderList.add(trader);
                    }
                    mv.addObject("traderList", traderList);
                } else {
                    mv.addObject("searchCustomerList", (List<TraderCustomerVo>) map.get("searchCustomerList"));

                }
            }
            mv.addObject("traderCustomerVo", traderCustomerVo);

            // 省级地区
            List<Region> provinceList = regionService.getRegionByParentId(1);
            mv.addObject("provinceList", provinceList);

            String indexId = request.getParameter("indexId");

            mv.addObject("indexId", indexId);
        } catch (Exception e) {
            logger.error("searchCustomerList:", e);
        }
        mv.setViewName("trader/customer/search_customer_list");
        return mv;
    }

    /**
     * <b>Description:</b><br> 批量新增客户
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月28日 下午1:07:21
     */
    @ResponseBody
    @RequestMapping(value = "/uplodebatchcustomer")
    public ModelAndView uplodeBatchCustomer(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("trader/customer/uplode_batch_customer");
        return mv;
    }

    /**
     * <b>Description:</b><br> 保存批量新增客户
     *
     * @param request
     * @param session
     * @param lwfile
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2017年11月28日 下午1:16:54
     */
    @ResponseBody
    @RequestMapping("saveuplodebatchcustomer")
    @SystemControllerLog(operationType = "import", desc = "保存批量新增客户")
    public ResultInfo<?> saveUplodeBatchCustomer(HttpServletRequest request, HttpSession session,
                                                 @RequestParam("lwfile") MultipartFile lwfile) {
        ResultInfo<?> resultInfo = new ResultInfo<>();
        try {
            User user = (User) session.getAttribute(Consts.SESSION_USER);
            //临时文件存放地址
            String path = request.getSession().getServletContext().getRealPath("/upload/trader");
            FileInfo fileInfo = ftpUtilService.fileUploadServe(path, lwfile);
            if (fileInfo.getCode() == 0) {
                List<Trader> list = new ArrayList<>();
                // 获取excel路径
                Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(fileInfo.getFilePath())));
                // 获取第一面sheet
                Sheet sheet = workbook.getSheetAt(0);
                // 起始行
                int startRowNum = sheet.getFirstRowNum() + 1;
                int endRowNum = sheet.getLastRowNum();// 结束行
                for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {// 循环行数
                    Row row = sheet.getRow(rowNum);
                    int startCellNum = row.getFirstCellNum();// 起始列
                    int endCellNum = row.getLastCellNum() - 1;// 结束列
                    // 获取excel的值
                    Trader trader = new Trader();
                    trader.setIsEnable(ErpConst.ONE);

                    TraderCustomer traderCustomer = new TraderCustomer();
                    traderCustomer.setIsEnable(ErpConst.ONE);
                    if (user != null) {
                        trader.setCompanyId(user.getCompanyId());
                        trader.setAddTime(DateUtil.gainNowDate());
                        trader.setCreator(user.getUserId());
                        trader.setModTime(DateUtil.gainNowDate());
                        trader.setUpdater(user.getUserId());

                        traderCustomer.setAddTime(DateUtil.gainNowDate());
                        traderCustomer.setCreator(user.getUserId());
                        traderCustomer.setModTime(DateUtil.gainNowDate());
                        traderCustomer.setUpdater(user.getUserId());
                    }
                    Integer areaId = 0;
                    String areaIds = "";
                    for (int cellNum = startCellNum; cellNum <= 6; cellNum++) {// 循环列数（下表从0开始）--注意---此处的6是根据表格的列数来确定的，表格列数修改此处要跟着修改
                        Cell cell = row.getCell(cellNum);

                        String traderNameRegex = "^[a-zA-Z0-9\\u4e00-\\u9fa5\\.\\(\\)\\,\\（\\）\\s]{2,128}$";
                        if (cellNum == 0) {//客户名称
                            //若excel中无内容，而且没有空格，cell为空--默认3，空白
                            if (cell == null) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户名称不允许为空！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户名称不允许为空！");
                            } else if (cell.getStringCellValue().toString().length() < 2 || cell.getStringCellValue().toString().length() > 128) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户名称长度为2-128个字符长度！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户名称长度为2-128个字符长度！");
                            } else if (!RegexUtil.match(traderNameRegex, cell.getStringCellValue().toString())) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户名称不允许使用特殊字符！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户名称不允许使用特殊字符！");
                            } else {
                                trader.setTraderName(cell.getStringCellValue().toString());
                            }
                        }
                        if (cellNum == 1) {//省
                            if (cell == null) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列省不能为空！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列省不能为空！");
                            } else {
                                Region region = new Region();
                                region.setRegionName(cell.getStringCellValue().toString());
                                region.setParentId(1);
                                Region regionInfo = regionService.getRegion(region);
                                if (null == regionInfo) {
                                    resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列省不存在！");
                                    throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列省不存在！");
                                } else {
                                    areaId = regionInfo.getRegionId();
                                    areaIds = regionInfo.getRegionId().toString();
                                }
                            }
                        }
                        if (cellNum == 2) {//市
                            if (cell == null) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列市不能为空！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列市不能为空！");
                            } else {
                                Region region = new Region();
                                region.setRegionName(cell.getStringCellValue().toString());
                                region.setParentId(areaId);
                                Region regionInfo = regionService.getRegion(region);
                                if (null == regionInfo) {
                                    resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列市不存在！");
                                    throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列市不存在！");
                                } else {
                                    areaId = regionInfo.getRegionId();
                                    areaIds += "," + regionInfo.getRegionId().toString();
                                }
                            }
                        }
                        if (cellNum == 3) {//区
                            if (cell != null) {
                                Region region = new Region();
                                region.setRegionName(cell.getStringCellValue().toString());
                                region.setParentId(areaId);
                                Region regionInfo = regionService.getRegion(region);
                                if (null == regionInfo) {
                                    resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列区不存在！");
                                    throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列区不存在！");
                                } else {
                                    areaId = regionInfo.getRegionId();
                                    areaIds += "," + regionInfo.getRegionId().toString();
                                }
                            }
                        }

                        if (cellNum == 4) {//客户类型
                            if (cell == null) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户类型不允许为空！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户类型不允许为空！");
                            } else {
                                if (!cell.getStringCellValue().toString().equals("临床医疗")
                                        && !cell.getStringCellValue().toString().equals("科研医疗")) {
                                    resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户类型错误！");
                                    throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户类型错误！");
                                } else {
                                    Integer customerType = 0;
                                    String customerTypeString = cell.getStringCellValue().toString();
                                    switch (customerTypeString) {
                                        case "临床医疗":
                                            customerType = 427;
                                            break;
                                        case "科研医疗":
                                            customerType = 426;
                                            break;
                                    }
                                    traderCustomer.setCustomerType(customerType);
                                }
                            }
                        }
                        if (cellNum == 5) {//客户性质
                            if (cell == null || cell.getCellType() != 1) {
                                resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户性质不允许为空！");
                                throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户性质不允许为空！");
                            } else {
                                if (!cell.getStringCellValue().toString().equals("分销")
                                        && !cell.getStringCellValue().toString().equals("终端")) {
                                    resultInfo.setMessage("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户性质错误！");
                                    throw new Exception("表格项错误，第" + (rowNum + 1) + "行" + (cellNum + 1) + "列客户性质错误！");
                                } else {
                                    Integer customerNature = 0;
                                    String customerNatureString = cell.getStringCellValue().toString();
                                    switch (customerNatureString) {
                                        case "分销":
                                            customerNature = 465;
                                            break;
                                        case "终端":
                                            customerNature = 466;
                                            break;
                                    }
                                    traderCustomer.setCustomerNature(customerNature);
                                }
                            }
                        }
                    }

                    Integer traderCustomerCategoryId = 0;
                    if (traderCustomer.getCustomerType().equals(426)) {
                        switch (traderCustomer.getCustomerNature()) {
                            case 465://科研分销
                                traderCustomerCategoryId = 3;
                                break;
                            case 466://科研终端
                                traderCustomerCategoryId = 4;
                                break;
                        }
                    }
                    if (traderCustomer.getCustomerType().equals(427)) {
                        switch (traderCustomer.getCustomerNature()) {
                            case 465://临床分销
                                traderCustomerCategoryId = 5;
                                break;

                            case 466://临床终端
                                traderCustomerCategoryId = 6;
                                break;
                        }
                    }

                    traderCustomer.setTraderCustomerCategoryId(traderCustomerCategoryId);
                    trader.setAreaId(areaId);
                    trader.setAreaIds(areaIds);
                    trader.setTraderType(1);
                    trader.setTraderCustomer(traderCustomer);
                    list.add(trader);
                }

                //保存更新
                resultInfo = traderCustomerService.saveUplodeBatchCustomer(list);
            }
        } catch (Exception e) {
            logger.error("saveuplodebatchcustomer:", e);
            return resultInfo;
        }
        return resultInfo;
    }


    /**
     * <b>Description:</b><br>
     * 客户审核弹层页面
     *
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年11月10日 下午1:39:42
     */
    @FormToken(save = true)
    @ResponseBody
    @RequestMapping(value = "/complement")
    public ModelAndView complement(HttpSession session, Integer traderCustomerId, String taskId, Boolean pass, Integer type) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("taskId", taskId);
        mv.addObject("pass", pass);
        mv.addObject("traderCustomerId", traderCustomerId);
        mv.addObject("type", type);
        mv.setViewName("trader/customer/complement");
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 客户审核操作
     *
     * @Note <b>Author:</b> Michael <br>
     * <b>Date:</b> 2017年11月10日 下午1:39:42
     */
    @FormToken(remove = true)
    @ResponseBody
    @RequestMapping(value = "/complementTask")
    @SystemControllerLog(operationType = "edit", desc = "客户审核操作")
    public ResultInfo<?> complementTask(HttpServletRequest request, Integer traderCustomerId, String taskId, String comment, Boolean pass,
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
            if (complementStatus != null && complementStatus.getData() != null && !complementStatus.getData().equals("endEvent")) {
                verifiesRecordService.saveVerifiesInfo(taskId, status);
            }
            return new ResultInfo(0, "操作成功");
        } catch (Exception e) {
            logger.error("complementTask:", e);
            return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
        }

    }

    /**
     * <b>Description:</b><br> 模糊查询客户列表
     *
     * @param request
     * @param session
     * @param traderCustomer
     * @return
     * @Note <b>Author:</b> scott
     * <br><b>Date:</b> 2018年1月11日 上午9:08:35
     */
    @ResponseBody
    @RequestMapping(value = "/queryEyeCheck")
    public ModelAndView queryEyeCheck(HttpServletRequest request, HttpSession session, TraderCustomerVo traderCustomerVo, Integer type, String traderName) {
        ModelAndView mv = new ModelAndView();
        ResultInfo<List<TraderCustomerVo>> resultInfo = new ResultInfo<List<TraderCustomerVo>>();
        List<TraderCustomerVo> list = new ArrayList<TraderCustomerVo>();

        String result = HttpSendUtil.queryDetails(type, traderName);
        JSONObject jsonObject = JSONObject.fromObject(result);
        String code = jsonObject.getString("error_code");
        if (!"0".equals(code)) {
            mv.addObject("code", code);
            mv.setViewName("trader/customer/list_traderCustomers");
        } else {
            JSONObject json = JSONObject.fromObject(jsonObject.get("result"));
            JSONArray jsonArray = JSONArray.fromObject(json.get("items"));
            for (int i = 0; i < jsonArray.size(); i++) {
                TraderCustomerVo tv = new TraderCustomerVo();
                tv.setTraderName(jsonArray.getJSONObject(i).getString("name").replaceAll("<em>", "").replaceAll("</em>", ""));
                list.add(tv);
            }
            mv.addObject("TraderCustomerVoList", list);
            mv.setViewName("trader/customer/list_traderCustomers");
        }
        return mv;
    }

    /**
     * <b>Description:</b><br> 天眼查客户的详情
     *
     * @param request
     * @param session
     * @param traderCustomerVo
     * @param traderName
     * @return
     * @Note <b>Author:</b> scott
     * <br><b>Date:</b> 2018年1月12日 上午9:51:00
     */
    @ResponseBody
    @RequestMapping(value = "/eyeCheckInfo")
    public ResultInfo eyeCheckInfo(HttpServletRequest request, HttpSession session, TraderCustomerVo traderCustomerVo, String traderName) {
        //返回天眼查详情
        TraderInfoTyc tycInfo = traderCustomerService.getTycInfo(2, traderName);
        ResultInfo res = null;
        if (tycInfo == null) {
            res = new ResultInfo<>(-1, "接口错误");
        } else {
            if (tycInfo.getCodeType() == 2) {
                res = new ResultInfo<>(2, "没有查询到" + traderName + "的信息");
            } else if (tycInfo.getCodeType() == 3) {
                res = new ResultInfo<>(3, "余额不足");
            } else {
                res = new ResultInfo<>(1, "操作成功");
            }
            res.setData(traderName);
        }
        return res;
    }

    /**
     * <b>Description:</b><br> 客户重置为待审核
     *
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2018年1月23日 上午11:49:04
     */
    @ResponseBody
    @RequestMapping(value = "/restverify")
    @SystemControllerLog(operationType = "edit", desc = "客户重置为待审核")
    public ResultInfo restVerify(HttpServletRequest request, HttpSession session, TraderCustomer traderCustomer) {
        if (null == traderCustomer || traderCustomer.getTraderCustomerId() == null) {
            return new ResultInfo<>();
        }

        ResultInfo resultInfo = traderCustomerService.restVerify(traderCustomer);
        return resultInfo;
    }

    /**
     * <b>Description:</b><br> 交易记录
     *
     * @param request
     * @param buyorderGoodsVo
     * @param traderSupplierVo
     * @param pageNo
     * @param pageSize
     * @return
     * @Note <b>Author:</b> Jerry
     * <br><b>Date:</b> 2018年1月25日 下午2:45:14
     */
    @ResponseBody
    @RequestMapping(value = "/businesslist")
    public ModelAndView businessList(HttpServletRequest request, SaleorderGoodsVo saleorderGoodsVo, TraderCustomerVo traderCustomerVo,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                     @RequestParam(required = false) Integer pageSize) {
        ModelAndView mv = new ModelAndView();
        if (saleorderGoodsVo.getTraderId() == null) {
            return pageNotFound();
        }

        // 时间处理
        if (null != saleorderGoodsVo.getStarttime() && saleorderGoodsVo.getStarttime() != "") {
            saleorderGoodsVo.setStarttimeLong(DateUtil.convertLong(saleorderGoodsVo.getStarttime(), "yyyy-MM-dd"));
        }
        if (null != saleorderGoodsVo.getEndtime() && saleorderGoodsVo.getEndtime() != "") {
            saleorderGoodsVo.setEndtimeLong(DateUtil.convertLong(saleorderGoodsVo.getEndtime() + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }

        Page page = getPageTag(request, pageNo, pageSize);

        List<SaleorderGoodsVo> list = null;
        Map<String, Object> map = traderCustomerService.getBusinessListPage(saleorderGoodsVo, page);

        list = (List<SaleorderGoodsVo>) map.get("list");

        Map<String,Map<String,Object>> newSkuInfos = new HashMap<>();

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

        mv.setViewName("trader/customer/bussiness_list");
        mv.addObject("method", "saleorder");
        mv.addObject("page", (Page) map.get("page"));
        mv.addObject("saleorderGoodsVo", saleorderGoodsVo);
        mv.addObject("businessList", list);
        mv.addObject("traderCustomer", traderCustomerVo);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/getAccountSaler", method = RequestMethod.POST)
    public ResultInfo getAccountSaler(HttpServletRequest request, @RequestBody ReqVo rv) {
        List<Integer> traderIdList = (List<Integer>) rv.getReq();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(traderIdList)) {
            List<AccountSalerToGo> accountSalerToGos = traderCustomerService.getAccountSaler(traderIdList);
            return new ResultInfo(0, "返回数据成功", JSON.toJSON(accountSalerToGos));
        }
        return new ResultInfo(-1, "获取失败");
    }

    /**
     * @param @param traderMjxContactAdderss
     * @return ResultInfo    返回类型
     * @throws
     * @Title: saveMjxContactAdders
     * @Description: TODO(同步MJX地址)
     * @author strange
     * @date 2019年8月20日
     */
    @ResponseBody
    @RequestMapping(value = "/saveMjxContactAdders")
    public ResultInfo saveMjxContactAdders(@RequestBody() TraderMjxContactAdderss traderMjxContactAdderss) {
        ResultInfo resultInfo = new ResultInfo<>();
        if (StringUtils.isEmpty(traderMjxContactAdderss.getPhone())) {
            resultInfo.setCode(-1);
            resultInfo.setMessage("注册手机号为空");
            return resultInfo;
        }
        resultInfo = traderCustomerService.saveMjxContactAdders(traderMjxContactAdderss);
        logger.info("saveMjxContactAdders:" + resultInfo);
        return resultInfo;
    }

//    /**
//     * <b>Description:</b>初始化客户资质审核流程<br>
//     * @param request 请求信息
//     * @return
//     * @Note
//     * <b>Author:calvin</b>
//     * <br><b>Date:</b> ${date} ${time}
//     */
//    @ResponseBody
//    @RequestMapping(value = "/initCustomerAptitudeCheck")
//    public ResultInfo initCustomerAptitudeCheck(HttpServletRequest request){
//        Page page=getPageTag(request,1,30);
//        return traderCustomerService.initCustomerAptitudeCheck(request,page);
//    }

    @ResponseBody
    @RequestMapping(value="/recallChangeAptitude")
    public ResultInfo recallChangeAptitude(String taskId,Integer traderCustomerId){
        if (traderCustomerId == null || StringUtil.isBlank(taskId)) {
            return new ResultInfo(-1, "客户标识或审核流程标识为空");
        }
        ResultInfo resultInfo = getAptitudeStatus(traderCustomerId);
        if (resultInfo != null || resultInfo.getData() != null) {
            Integer status = (Integer) resultInfo.getData();
            if (status != null && status != 0) {
                return new ResultInfo(-1, "客户资质审核状态已发生改变，请刷新页面");
            }
        }
        ResultInfo resultUpdateTable = verifiesRecordService.saveVerifiesInfoForTrader(taskId, 3);
        ResultInfo resultDeleteInstance = actionProcdefService.deleteProcessInstance(taskId);
        if (resultUpdateTable.getCode() == 0 && resultDeleteInstance.getCode() == 0) {
            return resultDeleteInstance;
        }
        return new ResultInfo(-1, "操作失败");
    }




    @ResponseBody
    @RequestMapping(value = "/getAptitudeStatus")
    public ResultInfo getAptitudeStatus(Integer traderCustomerId){
        VerifiesInfo verifiesInfo=traderCustomerService.getCustomerAptitudeVerifiesInfo(traderCustomerId);
        Integer aptitudeStatus=-3;
        if(verifiesInfo==null||verifiesInfo.getStatus()==null){
            aptitudeStatus=-3;
        }else{
            aptitudeStatus=verifiesInfo.getStatus();
        }
        ResultInfo<Integer> resultInfo=new ResultInfo<>(0,"操作成功");
        resultInfo.setData(aptitudeStatus);
        return resultInfo;
    }
    @ResponseBody
    @RequestMapping(value = "/isCanCheckAptitude")
    public ResultInfo canCheckAptitude(Integer traderCustomerId){
        VerifiesInfo verifiesInfo=traderCustomerService.getCustomerAptitudeVerifiesInfo(traderCustomerId);
        if(verifiesInfo==null||verifiesInfo.getStatus()==null){
            return new ResultInfo(-1,"资质审核状态异常，无法审核");
        }
        if(verifiesInfo.getStatus()==3){
            return new ResultInfo(-1,"销售已更新资质信息，请刷新页面重新操作");
        }
        if(verifiesInfo.getStatus()==0){
            return new ResultInfo(0,"操作成功");
        }
        return new ResultInfo(-1,"资质审核状态异常，无法审核");
    }

    /**
     * <b>Description:</b>同步医械购平台客户资质审核结果<br>
     * @param
     * @return
     * @Note
     * <b>Author:calvin</b>
     * <br><b>Date:</b> 2020/3/19
     */
    @ResponseBody
    @RequestMapping(value = "/aptitude/status",method = RequestMethod.PUT)
    public ResultInfo syncYxgAptitudeStatus(@RequestBody() YXGTraderAptitude yxgTraderAptitude){
        return traderCustomerService.syncYxgTraderStatus(yxgTraderAptitude);
    }
}








