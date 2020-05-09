package com.vedeng.call.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.util.JsonUtils;
import com.vedeng.logistics.model.Warehouse;
import com.vedeng.phoneticWriting.model.Comment;
import com.vedeng.phoneticWriting.model.ModificationRecord;
import com.vedeng.phoneticWriting.model.PhoneticWriting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.task.service.CallTaskService;
import com.vedeng.authorization.model.Organization;
import com.vedeng.authorization.model.Region;
import com.vedeng.authorization.model.User;
import com.vedeng.call.model.CallFailed;
import com.vedeng.call.model.CallOut;
import com.vedeng.call.service.CallService;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.BrowserUtil;
import com.vedeng.common.util.DateUtil;
import com.vedeng.order.model.BussinessChance;
import com.vedeng.order.model.vo.BussinessChanceVo;
import com.vedeng.order.service.BussinessChanceService;
import com.vedeng.system.model.QCellCore;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.model.Tag;
import com.vedeng.system.service.OrgService;
import com.vedeng.system.service.RegionService;
import com.vedeng.system.service.TagService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.CommunicateRecord;
import com.vedeng.trader.model.Trader;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.CommunicateService;
import com.vedeng.trader.service.TraderCustomerService;

import net.sf.json.JSONObject;

/**
 * <b>Description:</b><br>
 * 呼叫中心控制器
 *
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.callcenter.controller <br>
 *       <b>ClassName:</b> CallCenterController <br>
 *       <b>Date:</b> 2017年4月25日 上午9:43:39
 */
@Controller
@RequestMapping("/system/call")
public class CallController extends BaseController {

    @Autowired
    @Qualifier("callService")
    private CallService callService;
    @Autowired
    @Qualifier("traderCustomerService")
    private TraderCustomerService traderCustomerService;
    @Autowired
    @Qualifier("tagService")
    private TagService tagService;
    @Autowired
    @Qualifier("communicateService")
    private CommunicateService communicateService;

    @Autowired
    @Qualifier("regionService")
    private RegionService regionService;// 自动注入regionService

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("bussinessChanceService")
    private BussinessChanceService bussinessChanceService;

    @Autowired
    @Qualifier("orgService")
    private OrgService orgService;// 自动注入orgService

    @Autowired
    @Qualifier("callTaskService")
    private CallTaskService callTaskService;

    /**
     * <b>Description:</b><br>
     * 呼叫中心初始化
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年4月25日 上午9:43:41
     */
    @ResponseBody
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();

        HashMap callParams = callService.callInit();

        mv.setViewName("call/index");
        mv.addObject("user", user);
        mv.addObject("callParams", callParams);

        //主管以上拥有监听功能agentType = 1;
        Integer agentType = 0;
        if(user.getPositLevel().equals(442)
                || user.getPositLevel().equals(443)
                || user.getPositLevel().equals(447)
                || user.getPositLevel().equals(448)
                || user.getPositLevel().equals(454)
                ){
            agentType = 1;
        }

        Integer txtSK = 1002;
        if(user.getPositType().equals(645)){//售前
            txtSK = 1001;
        }

        mv.addObject("agentType", agentType);
        mv.addObject("txtSK", txtSK);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 刷新坐席列表
     *
     * @param type
     *            弹出层类型1转接 2内部咨询
     * @param agentState
     *            模式
     * @param agentOn
     *            坐席（在线）
     * @param agentLeave
     *            分机（离线）
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月10日 下午1:12:52
     */
    @ResponseBody
    @RequestMapping(value = "/getagentlist")
    public ModelAndView getAgentList(Integer type, Integer agentState, String agentOn, String agentLeave,HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        List<User> nextAllUserList = null;
        if(agentState == 16){
            //获取自己名下的用户
            //nextAllUserList = userService.getNextAllUserList(user.getUserId(), user.getCompanyId(), true, user.getPositLevel(), 1);
            nextAllUserList = userService.getMyUserList(user, null, false);
        }
        ModelAndView mv = new ModelAndView("call/agent_list");
        // 在线空闲坐席列表
        List agentOnList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.fromObject(agentOn);
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = jsonObject.getString(key);
            JSONObject jsonArray = JSONObject.fromObject(value);

            //判断是否自己名下的用户
            HashMap agent = new HashMap<>();
            if(agentState == 16){
                for(User u : nextAllUserList){
                    if(jsonArray.get("INS").toString().equals(u.getCcNumber())){
                        agent.put("INS", jsonArray.get("INS"));
                        agent.put("AGENTNAME", jsonArray.get("AGENTNAME"));
                        agent.put("ST", jsonArray.get("ST"));
                        agent.put("WKMD", jsonArray.get("WKMD"));
                        agentOnList.add(agent);
                    }
                }
            }else{
                agent.put("INS", jsonArray.get("INS"));
                agent.put("AGENTNAME", jsonArray.get("AGENTNAME"));
                agent.put("ST", jsonArray.get("ST"));
                agent.put("WKMD", jsonArray.get("WKMD"));
                agentOnList.add(agent);
            }

        }

        // 离席分机列表
        List agentLeaveList = new ArrayList<>();
        JSONObject jsonObject2 = JSONObject.fromObject(agentLeave);
        Iterator iterator2 = jsonObject2.keys();
        while (iterator2.hasNext()) {
            String key = (String) iterator2.next();
            String value = jsonObject2.getString(key);
            JSONObject jsonArray = JSONObject.fromObject(value);
            HashMap agent = new HashMap<>();
            agent.put("INS", jsonArray.get("INS"));
            agentLeaveList.add(agent);
        }

        // 支持部门
        List deptPhone = new ArrayList<>();
        HashMap deptMap = new HashMap<>();
        deptMap.put("call", "068538253");
        deptMap.put("phone", "68538253");
        deptMap.put("name", "公司固话");
        deptPhone.add(deptMap);

        mv.addObject("type", type);
        mv.addObject("agentState", agentState);
        mv.addObject("agentOn", agentOnList);
        mv.addObject("agentLeave", agentLeaveList);
        mv.addObject("deptPhone", deptPhone);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 电话呼出号码处理，座机统一前面加一个0，手机外地的加两个0、本地一个0
     *
     * @param phone
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月11日 上午8:56:31
     */
    @ResponseBody
    @RequestMapping(value = "/getphoneinfo")
    public ResultInfo getPhoneInfo(String phone) {
        String outPhone = "";
        ResultInfo resultInfo = new ResultInfo<>();
        // 去除号码中的-和空格
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll(" ", "");

        String regEx = "1[34578]{1}\\d{9}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            // 电话
            String phoneStr = phone.substring(0, 7);
            QCellCore phoneInfo = callService.getQCellCoreByPhone(phoneStr);
            if (null != phoneInfo) {
                if (phoneInfo.getCode().equals("025")) {
                    outPhone = "0" + phone;// 本地一个0
                } else {
                    outPhone = "00" + phone;// 手机外地的加两个0
                }
            } else {
                outPhone = phone;
            }

        } else {
            outPhone = "0" + phone;// 座机统一前面加一个0
        }

        resultInfo.setCode(0);
        resultInfo.setMessage("操作成功");
        resultInfo.setData(outPhone);
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 呼出弹屏 根据用户身份弹屏
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月11日 上午9:42:09
     */
    @ResponseBody
    @RequestMapping(value = "/getcallout")
    public ModelAndView getCallOut(CallOut callOut, HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);

        ModelAndView mv = new ModelAndView();
        // 去除号码中的-和空格
        String phone = callOut.getPhone();
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll(" ", "");

        callOut.setPhone(phone);

        QCellCore phoneInfo = new QCellCore();
        String regEx = "1[34578]{1}\\d{9}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            // 电话
            String phoneStr = phone.substring(0, 7);
            phoneInfo = callService.getQCellCoreByPhone(phoneStr);
        } else {
            if (phone.length() == 8) {
                phoneInfo.setProvince("江苏");
                phoneInfo.setCity("南京");
            }
        }

        String traderName = "";

        Integer positType = user.getPositType();
        Integer traderType = ErpConst.ONE;// 默认客户
        if (positType.equals(SysOptionConstant.ID_310)) {// 销售
            callOut.setTraderType(ErpConst.ONE);
            mv.setViewName("call/call_out");
            TraderCustomerVo traderCustomerVo = callService.getCustomerInfoByPhone(callOut);
            if(traderCustomerVo != null){
                traderName = traderCustomerVo.getName();

                if (callOut.getTraderId().equals(0) && null != traderCustomerVo.getTraderId()) {
                    callOut.setTraderId(traderCustomerVo.getTraderId());
                }

                mv.addObject("traderCustomer", traderCustomerVo);
            }
        }
        if (positType.equals(SysOptionConstant.ID_311)) {// 采购
            traderType = ErpConst.TWO;
            callOut.setTraderType(ErpConst.TWO);
            mv.setViewName("call/call_out_supplier");
            TraderSupplierVo traderSupplierVo = callService.getSupplierInfoByPhone(callOut);
            if(traderSupplierVo!= null){

                traderName = traderSupplierVo.getTraderSupplierName();

                if (callOut.getTraderId().equals(0) && null != traderSupplierVo.getTraderId()) {
                    callOut.setTraderId(traderSupplierVo.getTraderId());
                }

                mv.addObject("traderSupplier", traderSupplierVo);
            }
        }
        if (positType.equals(SysOptionConstant.ID_312) || positType.equals(SysOptionConstant.ID_645)) {// 售后
            if (callOut.getTraderType().equals(1)) {// 客户
                callOut.setTraderType(ErpConst.ONE);
                mv.setViewName("call/call_out");
                TraderCustomerVo traderCustomerVo = callService.getCustomerInfoByPhone(callOut);

                if(null != traderCustomerVo){

                    traderName = traderCustomerVo.getName();

                    if (callOut.getTraderId().equals(0) && null != traderCustomerVo.getTraderId()) {
                        callOut.setTraderId(traderCustomerVo.getTraderId());
                    }

                    mv.addObject("traderCustomer", traderCustomerVo);
                }
            } else if (callOut.getTraderType().equals(2)) {// 供应商
                traderType = ErpConst.TWO;
                callOut.setTraderType(ErpConst.TWO);
                mv.setViewName("call/call_out_supplier");
                TraderSupplierVo traderSupplierVo = callService.getSupplierInfoByPhone(callOut);
                if(null != traderSupplierVo){

                    traderName = traderSupplierVo.getTraderSupplierName();

                    if (callOut.getTraderId().equals(0) && null != traderSupplierVo.getTraderId()) {
                        callOut.setTraderId(traderSupplierVo.getTraderId());
                    }

                    mv.addObject("traderSupplier", traderSupplierVo);
                }
            } else {
                mv.setViewName("call/call_out_service");

                callOut.setTraderType(ErpConst.ONE);
                TraderCustomerVo traderCustomerVo = callService.getCustomerInfoByPhone(callOut);
                mv.addObject("traderCustomer", traderCustomerVo);

                CallOut supplierCallout = new CallOut();
                supplierCallout = callOut;
                supplierCallout.setTraderType(ErpConst.TWO);
                TraderSupplierVo traderSupplierVo = callService.getSupplierInfoByPhone(callOut);
                mv.addObject("traderSupplier", traderSupplierVo);

                if (null != traderCustomerVo) {
                    traderName = traderCustomerVo.getName();
                    callOut.setTraderId(traderCustomerVo.getTraderId());
                } else if (null != traderSupplierVo) {
                    traderName = traderSupplierVo.getTraderSupplierName();
                    callOut.setTraderId(traderSupplierVo.getTraderId());
                }
            }
        }

        Integer callType = 0;// 呼出类型
        Integer orderId = 0;// 呼出订单ID
        Integer traderContactId = 0;// 联系人ID

        if (null != callOut.getCallType() && callOut.getCallType() > 0) {
            callType = callOut.getCallType();
            orderId = callOut.getOrderId();
        }
        if (null != callOut.getTraderContactId() && callOut.getTraderContactId() > 0) {
            traderContactId = callOut.getTraderContactId();
        }

        // 增加沟通记录
        CommunicateRecord communicateRecord = new CommunicateRecord();
        Integer communicateType = SysOptionConstant.ID_242;// 沟通类型，默认客户
        Integer relatedId = callOut.getTraderId();
        // 以是否有订单为主：呼出类型 1商机2销售订单3报价4售后5采购订单
        if (null != callOut.getCallType() && callOut.getCallType() > 0) {
            if (callOut.getCallType() == 1) {
                communicateType = SysOptionConstant.ID_244;// 商机
            }
            if (callOut.getCallType() == 2) {
                communicateType = SysOptionConstant.ID_246;// 销售订单
            }
            if (callOut.getCallType() == 3) {
                communicateType = SysOptionConstant.ID_245;// 报价
            }
            if (callOut.getCallType() == 4) {
                communicateType = SysOptionConstant.ID_248;// 售后
            }
            if (callOut.getCallType() == 5) {
                communicateType = SysOptionConstant.ID_247;// 采购订单
            }

            relatedId = callOut.getOrderId();
        } else if (null != callOut.getTraderType() && callOut.getTraderType() == 2) {
            communicateType = SysOptionConstant.ID_243;// 供应商
        } else {
            communicateType = SysOptionConstant.ID_242;// 客户
        }

        Long time = DateUtil.sysTimeMillis();

        communicateRecord.setCommunicateType(communicateType);
        communicateRecord.setRelatedId(relatedId);
        communicateRecord.setPhone(callOut.getPhone());
        communicateRecord.setCoid(callOut.getCoid());
        communicateRecord.setCoidType(ErpConst.TWO);
        communicateRecord.setTraderType(traderType);
        communicateRecord.setTraderId(callOut.getTraderId());
        communicateRecord.setBegintime(time);
        communicateRecord.setEndtime(time);
        communicateRecord.setCompanyId(user.getCompanyId());

        communicateService.addCommunicate(communicateRecord, session);

        mv.addObject("phone", phone);
        mv.addObject("phoneInfo", phoneInfo);
        mv.addObject("communicateMode", SysOptionConstant.ID_250);// 沟通方式去电
        mv.addObject("callType", callType);
        mv.addObject("orderId", orderId);
        mv.addObject("traderContactId", traderContactId);
        mv.addObject("positType", positType);
        mv.addObject("traderName", traderName);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 来电弹屏
     *
     * @param callOut
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月12日 上午10:55:12
     */
    @ResponseBody
    @RequestMapping(value = "/getcallin")
    public ModelAndView getCallIn(CallOut callOut, HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();
        // 去除号码中的-和空格
        String phone = callOut.getPhone();
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll(" ", "");

        QCellCore phoneInfo = new QCellCore();
        String regEx = "1[34578]{1}\\d{9}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            // 电话
            String phoneStr = phone.substring(0, 7);
            phoneInfo = callService.getQCellCoreByPhone(phoneStr);
        } else {
            if (phone.length() == 8) {
                phoneInfo.setProvince("江苏");
                phoneInfo.setCity("南京");
            }
        }

        String traderName = "";
        // 用户职位类型
        Integer positType = user.getPositType();
        Integer traderType = ErpConst.ONE;// 默认客户
        // 增加沟通记录
        CommunicateRecord communicateRecord = new CommunicateRecord();
        Integer communicateType = SysOptionConstant.ID_242;// 沟通类型，默认客户
        Integer relatedId = 0;
        if (positType.equals(SysOptionConstant.ID_310)) {// 销售
            callOut.setTraderType(ErpConst.ONE);
            mv.setViewName("call/call_in_sale");
            TraderCustomerVo traderCustomerVo = callService.getCustomerInfoByPhone(callOut);

            if (null != traderCustomerVo) {
                relatedId = traderCustomerVo.getTraderId();
                traderName = traderCustomerVo.getName();
            }
            mv.addObject("traderCustomer", traderCustomerVo);
        }
        if (positType.equals(SysOptionConstant.ID_311)) {// 采购
            communicateType = SysOptionConstant.ID_243;// 供应商
            traderType = ErpConst.TWO;
            callOut.setTraderType(ErpConst.TWO);
            mv.setViewName("call/call_in_supplier");
            TraderSupplierVo traderSupplierVo = callService.getSupplierInfoByPhone(callOut);

            if (null != traderSupplierVo) {
                relatedId = traderSupplierVo.getTraderId();
                traderName = traderSupplierVo.getTraderSupplierName();
            }
            mv.addObject("traderSupplier", traderSupplierVo);

        }
        if (positType.equals(SysOptionConstant.ID_312) || positType.equals(SysOptionConstant.ID_645)) {// 售后
            mv.setViewName("call/call_in_service");

            callOut.setTraderType(ErpConst.ONE);
            TraderCustomerVo traderCustomerVo = callService.getCustomerInfoByPhone(callOut);
            mv.addObject("traderCustomer", traderCustomerVo);

            CallOut supplierCallout = new CallOut();
            supplierCallout = callOut;
            supplierCallout.setTraderType(ErpConst.TWO);
            TraderSupplierVo traderSupplierVo = callService.getSupplierInfoByPhone(callOut);
            mv.addObject("traderSupplier", traderSupplierVo);

            // 优先挂客户沟通记录，只有供应商信息挂供应商
            if (null != traderCustomerVo) {
                relatedId = traderCustomerVo.getTraderId();
                traderName = traderCustomerVo.getName();
            } else if (null != traderSupplierVo) {
                traderType = ErpConst.TWO;
                communicateType = SysOptionConstant.ID_243;// 供应商
                relatedId = traderSupplierVo.getTraderId();
                traderName = traderSupplierVo.getTraderSupplierName();
            }
        }

        // 增加沟通记录
        Long time = DateUtil.sysTimeMillis();
        communicateRecord.setCommunicateType(communicateType);
        communicateRecord.setRelatedId(relatedId);
        communicateRecord.setPhone(callOut.getPhone());
        communicateRecord.setCoid(callOut.getCoid());
        communicateRecord.setCoidType(ErpConst.ONE);
        communicateRecord.setTraderType(traderType);
        communicateRecord.setTraderId(relatedId);
        communicateRecord.setBegintime(time);
        communicateRecord.setEndtime(time);
        communicateRecord.setCompanyId(user.getCompanyId());

        communicateService.addCommunicate(communicateRecord, session);

        mv.addObject("communicateMode", SysOptionConstant.ID_249);// 沟通方式来电
        mv.addObject("phone", phone);
        mv.addObject("phoneInfo", phoneInfo);
        mv.addObject("positType", positType);
        mv.addObject("traderName", traderName);
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 新增联系
     *
     * @param callOut
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月12日 上午10:57:00
     */
    @ResponseBody
    @RequestMapping(value = "/getaddcomm")
    public ModelAndView getAddComm(CallOut callOut, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        ModelAndView mv = new ModelAndView();

        // 客户基本信息(获取客户名称)

        Trader trader = callService.getTraderByTraderId(callOut.getTraderId());

        List<TraderContact> contactList = null;// 联系人
        Integer traderType = callOut.getTraderType();
        if (traderType.equals(1)) {// 客户

            TraderContact traderContact = new TraderContact();
            // 联系人
            traderContact.setTraderId(callOut.getTraderId());
            traderContact.setIsEnable(ErpConst.ONE);
            traderContact.setTraderType(ErpConst.ONE);
            contactList = traderCustomerService.getTraderContact(traderContact);
        }
        if (traderType.equals(2)) {// 供应商
            TraderContact traderContact = new TraderContact();
            // 联系人
            traderContact.setTraderId(callOut.getTraderId());
            traderContact.setIsEnable(ErpConst.ONE);
            traderContact.setTraderType(ErpConst.TWO);
            contactList = traderCustomerService.getTraderContact(traderContact);
        }

        // 客户标签
        Tag tag = new Tag();
        tag.setTagType(SysOptionConstant.ID_32);
        tag.setIsRecommend(ErpConst.ONE);
        tag.setCompanyId(user.getCompanyId());

        Integer pageNo = 1;
        Integer pageSize = 10;

        Page page = getPageTag(request, pageNo, pageSize);
        Map<String, Object> tagMap = tagService.getTagListPage(tag, page);

        // 沟通目的
        List<SysOptionDefinition> communicateGoalList = getSysOptionDefinitionList(SysOptionConstant.ID_24);
        mv.addObject("communicateGoalList", communicateGoalList);
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_24)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_24);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> communicateGoalList = (List<SysOptionDefinition>) JSONArray
//					.toCollection(jsonArray, SysOptionDefinition.class);
//			mv.addObject("communicateGoalList", communicateGoalList);
//		}

        // 沟通类型
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_22)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_22);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> communicateTypeList = (List<SysOptionDefinition>) JSONArray
//					.toCollection(jsonArray, SysOptionDefinition.class);
//			mv.addObject("communicateTypeList", communicateTypeList);
//		}
        mv.addObject("communicateTypeList", getSysOptionDefinitionList(SysOptionConstant.ID_22));
        mv.setViewName("call/add_comm");
        mv.addObject("callOut", callOut);
        mv.addObject("trader", trader);
        mv.addObject("contactList", contactList);
        mv.addObject("tagList", (List<Tag>) tagMap.get("list"));
        mv.addObject("page", (Page) tagMap.get("page"));
        return mv;
    }

    /**
     * <b>Description:</b><br>
     * 根据沟通类型查询订单
     *
     * @param communicateType
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月13日 上午10:10:47
     */
    @ResponseBody
    @RequestMapping(value = "/getorderlist")
    public ResultInfo getOrderList(CommunicateRecord communicateRecord) {
        ResultInfo resultInfo = new ResultInfo<>();
        List<?> orderList = callService.getOrderList(communicateRecord);
        if (orderList != null) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setListData(orderList);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 保存沟通联系
     *
     * @param communicateRecord
     * @param request
     * @param session
     * @return
     * @throws Exception
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月13日 上午11:22:23
     */
    @ResponseBody
    @RequestMapping(value = "/saveaddcommunicate")
    @SystemControllerLog(operationType = "add",desc = "保存新增电话沟通")
    public ResultInfo saveAddCommunicate(CommunicateRecord communicateRecord, HttpServletRequest request,
                                         HttpSession session) throws Exception {
        Integer traderType = Integer.parseInt(request.getParameter("traderType"));
        Integer traderId = Integer.parseInt(request.getParameter("traderId"));
        communicateRecord.setTraderId(traderId);
        communicateRecord.setTraderType(traderType);
        if (communicateRecord.getCommunicateType() == 0) {// 未选择类型
            communicateRecord.setRelatedId(communicateRecord.getTraderId());
            if (traderType == 1) { // 客户
                communicateRecord.setCommunicateType(SysOptionConstant.ID_242);
            }
            if (traderType == 2) { // 供应商
                communicateRecord.setCommunicateType(SysOptionConstant.ID_243);
            }
        }
        Integer callFrom = Integer.parseInt(request.getParameter("callFrom"));
        if (callFrom == 0) {// 呼入
            communicateRecord.setCoidType(ErpConst.ONE);
        }
        if (callFrom == 1) {// 呼出
            communicateRecord.setCoidType(ErpConst.TWO);
        }

        Boolean res = callService.saveCommunicate(communicateRecord, request, session);

        if (res) {
            return new ResultInfo(0, "操作成功！");
        } else {
            return new ResultInfo(1, "操作失败！");
        }
    }

    /**
     * <b>Description:</b><br>
     * 新增商机
     *
     * @param callOut
     * @param request
     * @param session
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月14日 下午3:34:50
     */
    @ResponseBody
    @RequestMapping(value = "/getaddbussinesschance")
    public ModelAndView getAddBussinessChance(CallOut callOut, HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // 商机商品分类
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_387)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_387);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> goodsTypeList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
//					SysOptionDefinition.class);
//
//			mav.addObject("goodsTypeList", goodsTypeList);
//		}
        mav.addObject("goodsTypeList", getSysOptionDefinitionList(SysOptionConstant.ID_387));
        // 商机来源
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_365)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_365);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> scoureList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
//					SysOptionDefinition.class);
//
//			mav.addObject("scoureList", scoureList);
//		}
        mav.addObject("scoureList", getSysOptionDefinitionList(SysOptionConstant.ID_365));
        // 询价方式
//		if (JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_376)) {
//			String strJson = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_376);
//			JSONArray jsonArray = JSONArray.fromObject(strJson);
//			List<SysOptionDefinition> scoureList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray,
//					SysOptionDefinition.class);
//			mav.addObject("inquiryList", scoureList);
//		}
        mav.addObject("inquiryList", getSysOptionDefinitionList(SysOptionConstant.ID_376));
        // 省级地区
        List<Region> provinceList = regionService.getRegionByParentId(1);
        mav.addObject("provinceList", provinceList);
        // 查询所有销售
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        List<User> userList = userService.getUserByPositType(SysOptionConstant.ID_310, user.getCompanyId());

        TraderCustomerVo traderCustomerVo = null;
        Integer province = 0;
        Integer city = 0;
        Integer zone = 0;
        if (null != callOut.getTraderId() && callOut.getTraderId() > 0) {
            TraderCustomer traderCustomer = new TraderCustomer();
            traderCustomer.setTraderId(callOut.getTraderId());
            traderCustomerVo = traderCustomerService.getTraderCustomerVo(traderCustomer);
            if (null != traderCustomerVo && null != traderCustomerVo.getAreaIds()) {
                String[] ids = traderCustomerVo.getAreaIds().split(",");
                if (null != ids[0]) {
                    province = Integer.parseInt(ids[0]);

                }
                if (null != ids[1]) {
                    city = Integer.parseInt(ids[1]);
                    // 市级地区
                    List<Region> cityList = regionService.getRegionByParentId(province);
                    mav.addObject("cityList", cityList);
                }
                if (null != ids[2]) {
                    zone = Integer.parseInt(ids[2]);
                    // 县区级地区
                    List<Region> zoneList = regionService.getRegionByParentId(city);
                    mav.addObject("zoneList", zoneList);
                }
            }
        }

        // 去除号码中的-和空格
        String phone = callOut.getPhone();
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll(" ", "");

        String regEx = "1[34578]{1}\\d{9}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        String mobile = "";
        String tel = "";
        if (matcher.matches()) {
            mobile = phone;
        } else {
            tel = phone;
        }
        mav.addObject("traderCustomer", traderCustomerVo);
        mav.addObject("userList", userList);
        BussinessChanceVo bcv = new BussinessChanceVo();
        bcv.setReceiveTime(DateUtil.sysTimeMillis());
        mav.addObject("bussinessChanceVo", bcv);

        mav.addObject("province", province);
        mav.addObject("city", city);
        mav.addObject("zone", zone);
        mav.addObject("mobile", mobile);
        mav.addObject("tel", tel);

        mav.setViewName("call/add_bussinesschance");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 保存商机
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月14日 下午5:19:31
     */
    @ResponseBody
    @RequestMapping(value = "/savebussinesschance")
    @SystemControllerLog(operationType = "add",desc = "保存来电新增商机")
    public ResultInfo saveBussinessChance(HttpSession session, Integer province, Integer city, Integer zone,
                                          String time, BussinessChance bussinessChance) {
        ResultInfo rs = new ResultInfo<>();
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        try {
            if (province != 0 && city != 0 && zone != 0) {
                bussinessChance.setAreaId(zone);
                bussinessChance.setAreaIds(province + "," + city + "," + zone);
            } else if (province != 0 && city != 0 && zone == 0) {
                bussinessChance.setAreaId(city);
                bussinessChance.setAreaIds(province + "," + city);
            } else if (province != 0 && city == 0 && zone == 0) {
                bussinessChance.setAreaId(province);
                bussinessChance.setAreaIds(province.toString());
            }

            if (time != null && !"".equals(time)) {
                bussinessChance.setReceiveTime(DateUtil.convertLong(time, DateUtil.TIME_FORMAT));
            }

            bussinessChance.setReceiveTime(DateUtil.convertLong(time, DateUtil.TIME_FORMAT));
            bussinessChance.setType(SysOptionConstant.ID_391);// 商机类型:总机询价
            bussinessChance.setCompanyId(user.getCompanyId());
            rs = bussinessChanceService.saveBussinessChance(bussinessChance, user, null);
            return rs;
		} catch (Exception e) {
			logger.error("savebussinesschance:", e);
			return rs;
		}
    }

    /**
     * <b>Description:</b><br>
     * 获取最近一条商机信息
     *
     * @param callOut
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月17日 上午10:29:54
     */
    @ResponseBody
    @RequestMapping(value = "/getbussincesschance")
    public ModelAndView getBussincessChance(CallOut callOut) {
        ModelAndView mav = new ModelAndView();

        // 去除号码中的-和空格
        String phone = callOut.getPhone();
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll(" ", "");

        callOut.setPhone(phone);

        QCellCore phoneInfo = null;
        String regEx = "1[34578]{1}\\d{9}$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            // 电话
            String phoneStr = phone.substring(0, 7);
            phoneInfo = callService.getQCellCoreByPhone(phoneStr);
        } else {
            if (phone.length() == 8) {
                phoneInfo = new QCellCore();
                phoneInfo.setProvince("江苏");
                phoneInfo.setCity("南京");
            }
        }

        BussinessChance bussinessChance = callService.getBussincessChance(callOut);

        mav.addObject("phone", phone);
        mav.addObject("phoneInfo", phoneInfo);
        mav.addObject("bussinessChance", bussinessChance);
        mav.setViewName("call/bussinesschance_info");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 根据来电获取交易者信息
     *
     * @param callOut
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月17日 下午1:29:32
     */
    @ResponseBody
    @RequestMapping(value = "/gettraderinfo")
    public ResultInfo getTraderInfo(CallOut callOut, HttpSession session) {
        ResultInfo resultInfo = new ResultInfo<>();
        User user = (User) session.getAttribute(Consts.SESSION_USER);

        // 用户职位类型
        Integer positType = user.getPositType();
        if (positType.equals(SysOptionConstant.ID_310)) {// 销售
            callOut.setTraderType(ErpConst.ONE);
        }
        if (positType.equals(SysOptionConstant.ID_311)) {// 采购
            callOut.setTraderType(ErpConst.TWO);
        }

        Trader trader = callService.getTraderByPhone(callOut);
        if (null != trader) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
            resultInfo.setData(trader);
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 话机状态
     *
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月17日 下午2:32:11
     */
    @ResponseBody
    @RequestMapping(value = "/getisinit")
    public ResultInfo getIsInit(HttpSession session) {
        ResultInfo resultInfo = new ResultInfo<>();
        User user = (User) session.getAttribute(Consts.SESSION_USER);
        if (null != user.getUserDetail().getCcNumber()) {
            String type = callService.getIsInit(user.getNumber());
            if (null != type) {
                if (type.equals("logon")) {
                    resultInfo.setCode(0);
                    resultInfo.setMessage("操作成功");
                }
            }
        }
        return resultInfo;
    }

    /**
     * <b>Description:</b><br>
     * 话务综合统计
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月19日 下午1:53:17
     */
    @ResponseBody
    @RequestMapping(value = "/getstatistics")
    public ModelAndView getStatistics(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String starttime = "";
        String endtime = "";
        if (null != request.getParameter("starttime") || null != request.getParameter("endtime")) {
            if (null != request.getParameter("starttime") && !request.getParameter("starttime").equals("")) {
                starttime = request.getParameter("starttime");
            }
            if (null != request.getParameter("endtime") && !request.getParameter("endtime").equals("")) {
                endtime = request.getParameter("endtime");
            }

            Map<String, Object> info = callService.getStatistics(starttime, endtime);

            mav.addObject("info", info);
        }
        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);

        mav.setViewName("call/statistics");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 座席明细统计
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月19日 下午3:20:16
     */
    @ResponseBody
    @RequestMapping(value = "/getuserdetail")
    public ModelAndView getUserDetail(HttpServletRequest request, HttpSession session) {
        User session_user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mav = new ModelAndView();
        // 获取部门
        List<Organization> orgList = orgService.getOrgList(0, session_user.getCompanyId(), true);
//		List<User> userList = userService.getNextAllUserList(session_user.getUserId(), session_user.getCompanyId(),
//				true, session_user.getPositLevel(), 0);
        List<User> userList = userService.getMyUserList(session_user, null, false);

        String starttime = DateUtil.convertString(DateUtil.sysTimeMillis(), "yyyy-MM-dd");
        String endtime = starttime;
        Integer orgId = 0;
        String userNumber = "all";
        if (null != request.getParameter("starttime") || null != request.getParameter("endtime")
                || null != request.getParameter("orgId") || null != request.getParameter("userId")) {
            List<String> numberList = new ArrayList<>();
            if (null != request.getParameter("starttime") && !request.getParameter("starttime").equals("")) {
                starttime = request.getParameter("starttime");
            }
            if (null != request.getParameter("endtime") && !request.getParameter("endtime").equals("")) {
                endtime = request.getParameter("endtime");
            }
            if (null != request.getParameter("orgId") && !request.getParameter("orgId").equals("0")) {
                orgId = Integer.parseInt(request.getParameter("orgId"));
            }
            if (null != request.getParameter("userNumber") && request.getParameter("userNumber") != "all") {
                userNumber = request.getParameter("userNumber");
            }

            if (userNumber.equals("all")) {
                List<Integer> orgIds = new ArrayList<>();
                List<Organization> orgs = orgService.getOrgList(orgId, session_user.getCompanyId(), true);
                orgIds.add(orgId);
                if (orgs.size() > 0) {
                    for (Organization o : orgs) {
                        orgIds.add(o.getOrgId());
                    }
                }

                List<User> userListByOrgIds = userService.getUserListByOrgIds(orgIds, session_user.getCompanyId());
                if (userListByOrgIds.size() > 0) {
                    for (User u : userListByOrgIds) {
                        if (null != u.getNumber() && !u.getNumber().equals("")) {
                            numberList.add(u.getNumber());
                        }
                    }

                }
            } else {
                numberList.add(userNumber);
                // numberList.add("0237");
                // numberList.add("0225");
            }

            if (numberList.size() > 0) {
                List info = callService.getUserDetail(starttime, endtime, numberList);
                mav.addObject("info", info);
            }
        }

        mav.addObject("starttime", starttime);
        mav.addObject("endtime", endtime);
        mav.addObject("orgId", orgId);
        mav.addObject("userNumber", userNumber);
        mav.addObject("orgList", orgList);
        mav.addObject("userList", userList);
        mav.setViewName("call/statistics_user");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 通话记录
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月19日 下午3:23:22
     */
    @ResponseBody
    @RequestMapping(value = "/getrecord")
    public ModelAndView getRecord(HttpServletRequest request, CommunicateRecord communicateRecord,
                                  @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false) Integer pageSize, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = (User) request.getSession().getAttribute(Consts.SESSION_USER);
        // 查询所有职位类型为310的员工
        List<User> userList = null;
        if(null != user.getPositType() && user.getPositType().equals(310)){
            List<Integer> positionType = new ArrayList<>();
            positionType.add(SysOptionConstant.ID_310);//销售
            userList = userService.getMyUserList(user, positionType, false);
            mav.addObject("userList", userList);
            if(userList == null || userList.size() == 0){
                userList.add(new User());
            }
        }else{
            userList = callService.getRecordUser();
            mav.addObject("userList", userList);
        }

        List<Integer> userIds = new ArrayList<>();

        if(null != userList && userList.size() > 0){
            for(User u : userList){
                userIds.add(u.getUserId());
            }
        }else{
            userIds.add(-1);
        }
        communicateRecord.setUserIds(userIds);
        Page page = getPageTag(request, pageNo, pageSize);

        String begindate = request.getParameter("begindate");
        if (null != begindate && !"".equals(begindate)) {
            communicateRecord.setBegintime(DateUtil.convertLong(begindate, "yyyy-MM-dd"));
        }
        String enddate = request.getParameter("enddate");
        if (null != enddate && !"".equals(enddate)) {
            communicateRecord.setEndtime(DateUtil.convertLong(enddate + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }



        List<CommunicateRecord> recordList = callService.queryRecordlistPage(communicateRecord, page, session);

        mav.addObject("page", page);
        mav.addObject("communicateRecord", communicateRecord);

        mav.addObject("recordList", recordList);
        mav.setViewName("call/record_list");
        return mav;
    }


    /**
     * <b>Description:</b><br>
     * 漏接来电
     *
     * @param request
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月19日 下午3:23:22
     */
    @ResponseBody
    @RequestMapping(value = "/getcallfailed")
    public ModelAndView getCallFailed(HttpServletRequest request, CallFailed callFailed,
                                      @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                      @RequestParam(required = false) Integer pageSize, HttpSession session) {
        ModelAndView mav = new ModelAndView();

        User user = (User) session.getAttribute(Consts.SESSION_USER);
        Page page = getPageTag(request, pageNo, pageSize);

        callFailed.setCompanyId(user.getCompanyId());
        List<CallFailed> callFailedList = callService.queryCallFailedlistPage(callFailed, page);
        List<CallFailed> dialBackUserList = callService.getCallFailedDialBackUser();

        mav.addObject("dialBackUserList", dialBackUserList);
        mav.addObject("callFailedList", callFailedList);
        mav.addObject("page", page);
        mav.addObject("callFailed", callFailed);
        mav.setViewName("call/failed_list");
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 录音播放
     *
     * @param url
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月20日 下午2:14:40
     */
    @ResponseBody
    @RequestMapping(value = "/getrecordpaly")
    public ModelAndView getRecordPaly(HttpServletRequest request, String url) {
        ModelAndView mav = new ModelAndView();

        // 浏览器信息
        String agent = request.getHeader("User-Agent").toLowerCase();
        BrowserUtil browserUtil = new BrowserUtil();
        String browserName = browserUtil.getBrowserName(agent);

        Integer type = 1;
        if (browserName.indexOf("ie") != -1) {
            type = 2;
        }

        mav.setViewName("call/record_play");
        mav.addObject("url", url);
        mav.addObject("type", type);
        return mav;
    }

    /**
     * <b>Description:</b><br>
     * 漏接来电更新
     *
     * @param session
     * @param callFailed
     * @return
     * @Note <b>Author:</b> Jerry <br>
     *       <b>Date:</b> 2017年7月20日 下午3:04:43
     */
    @ResponseBody
    @RequestMapping(value = "/editcallfailedcoid")
    @SystemControllerLog(operationType = "edit",desc = "保存漏接来电更新")
    public ResultInfo editCallFailedCoid(HttpSession session, CallFailed callFailed) {
        ResultInfo resultInfo = new ResultInfo<>();
        Boolean res = callService.editCallFailedCoid(callFailed, session);
        if (res) {
            resultInfo.setCode(0);
            resultInfo.setMessage("操作成功");
        }

        return resultInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/test")
    public ModelAndView test() throws Exception {
//		callTaskService.failedRecordSync();
//		callTaskService.callRecordTraderModify();
//		callTaskService.callRecordInfoSync();
//		callTaskService.callRecordUnSync();
        return null;
    }

}
