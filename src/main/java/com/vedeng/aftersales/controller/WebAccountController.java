package com.vedeng.aftersales.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.rest.traderMsg.controller.WebAccountCertificateMsg;
import com.vedeng.aftersales.model.BDTraderCertificate;
import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.util.*;
import com.vedeng.soap.ApiSoap;
import com.vedeng.system.controller.UserController;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.service.TraderCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.aftersales.service.WebAccountService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.WebAccountVo;

@Controller
@RequestMapping("/aftersales/webaccount")
public class WebAccountController extends BaseController{
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	@Autowired
	@Qualifier("webAccountService")
	private WebAccountService webAccountService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("apiSoap")
	private ApiSoap apiSoap;


	@Autowired
	@Qualifier("userController")
	private UserController userController;

	@Resource
	private TraderCustomerService traderCustomerService;
	/**
	 * <b>Description:</b><br> 注册用户列表
	 * @param request
	 * @param webAccountVo
	 * @param pageNo
	 * @param pageSize
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:04:21
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
    public ModelAndView list(HttpServletRequest request, HttpSession session, WebAccountVo webAccountVo,
                             @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                             @RequestParam(required = false) Integer pageSize,
                             @RequestParam(required = false, value = "startAddDateStr") String startAddDateStr,
                             @RequestParam(required = false, value = "endAddDateStr") String endAddDateStr,
                             @RequestParam(required = false, value = "startMemberDateStr") String startMemberDateStr,
                             @RequestParam(required = false, value = "endMemberDateStr") String endMemberDateStr) {
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        ModelAndView mv = new ModelAndView("aftersales/webaccount/list_webaccount");
        Page page = getPageTag(request, pageNo, pageSize);
        if (EmptyUtils.isNotBlank(startAddDateStr)) {
            webAccountVo.setStartAddDate(DateUtil.convertLong(startAddDateStr + " 00:00:00", DateUtil.TIME_FORMAT));
        }
        if (EmptyUtils.isNotBlank(endAddDateStr)) {
            webAccountVo.setEndAddDate(DateUtil.convertLong(endAddDateStr + " 23:59:59", DateUtil.TIME_FORMAT));
        }

        if (EmptyUtils.isNotBlank(startMemberDateStr)) {
            webAccountVo.setStartMemberDate(DateUtil.convertLong(startMemberDateStr + " 00:00:00", DateUtil.TIME_FORMAT));
        }
        if (EmptyUtils.isNotBlank(endMemberDateStr)) {
            webAccountVo.setEndMemberDate(DateUtil.convertLong(endMemberDateStr + " 23:59:59", DateUtil.TIME_FORMAT));
        }
        mv.addObject("startAddDateStr",startAddDateStr);
        mv.addObject("endAddDateStr",endAddDateStr);
        mv.addObject("startMemberDateStr",startMemberDateStr);
        mv.addObject("endMemberDateStr",endMemberDateStr);

        Map<String, Object> map = webAccountService.getWebAccountListPage(webAccountVo, page, session);
        //根据traderid查找tradercustomerid
		/*TraderCustomer traderCustomer=new TraderCustomer();*/
		List<WebAccountVo> list=(List<WebAccountVo>) map.get("list");
		TraderCustomer traderBaseInfo=null;
		TraderCustomerVo traderCustomerVo=new TraderCustomerVo();
		TraderCustomerVo tcustomerVo=null;
		if (list!=null && list.size()!=0){
			WebAccountVo web=null;
			for (int i = 0; i <list.size() ; i++) {
				web=list.get(i);
				if(web.getTraderId()!=0){
					/*traderCustomer.setTraderId(web.getTraderId());*/
					traderBaseInfo = traderCustomerService.getTraderCustomerId(web.getTraderId());
					if(traderBaseInfo!=null) {
						web.setTraderCustomerId(traderBaseInfo.getTraderCustomerId());
					}
					traderCustomerVo.setTraderId(web.getTraderId());
					traderCustomerVo.setTraderCustomerId(web.getTraderCustomerId());
					tcustomerVo = traderCustomerService.getTraderCustomerBaseInfo(traderCustomerVo);
					web.setTraderCustomerVo(tcustomerVo);
				}
				list.set(i,web);
			}
		}
		//根据traderid，tradercustomerid查找customerNature，aptitudeStatus



        // 获取当前销售用户下级职位用户
        List<Integer> positionType = new ArrayList<>();
        positionType.add(SysOptionConstant.ID_310);
        List<User> userList = userService.getMyUserList(user, positionType, false);
        mv.addObject("userList", userList);// 操作人员

        mv.addObject("list", list);
        mv.addObject("page", (Page) map.get("page"));
        mv.addObject("webAccountVo", webAccountVo);
        return mv;
    }
	
	/**
	 * <b>Description:</b><br> 注册用户信息查看
	 * @param request
	 * @param webAccount
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:04:19
	 */
	@ResponseBody
	@RequestMapping(value = "/view")
	public ModelAndView view(HttpServletRequest request, WebAccount webAccount, HttpSession session,@RequestParam(value="saleorderId",required=false  )Integer saleorderId){
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("aftersales/webaccount/view_webaccount");
		if(null == webAccount || null == webAccount.getErpAccountId()){
			return pageNotFound();
		}
		Map<String, Object> map = webAccountService.getWebAccount(webAccount,1);
		WebAccountVo webAccountVo = (WebAccountVo) map.get("webAccountVo");
		TraderCustomerVo traderCustomerVo = (TraderCustomerVo) map.get("customer");
		List<TraderAddressVo> addressList = (List<TraderAddressVo>) map.get("address");
		List<TraderContact> contactList = (List<TraderContact>) map.get("contact");
		Map<String,Object> certificatesMap=webAccountService.getCertificateByCategory(webAccount.getErpAccountId());
		try {
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(webAccountVo)));
		} catch (Exception e) {
			logger.error("web account view:", e);
		}
		// 获取当前销售用户下级职位用户
		List<Integer> positionType = new ArrayList<>();
		positionType.add(SysOptionConstant.ID_310);
		List<User> userList = userService.getMyUserList(user, positionType, false);
		mv.addObject("userList", userList);// 操作人员
		mv.addObject("webAccountVo", webAccountVo);
		mv.addObject("saleorderId", saleorderId);
		mv.addObject("traderCustomer", traderCustomerVo);
		mv.addObject("addressList", addressList);
		mv.addObject("contactList", contactList);
		mv.addObject(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE,certificatesMap.get(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE));
		mv.addObject(TraderConstants.ACCOUNT_SECOND_CERTIFICATE,certificatesMap.get(TraderConstants.ACCOUNT_SECOND_CERTIFICATE));
		mv.addObject(TraderConstants.ACCOUNT_THIRD_CERTIFICATE,certificatesMap.get(TraderConstants.ACCOUNT_THIRD_CERTIFICATE));
		return mv;
	}


	/**
	 * 功能描述: 验证分配的用户和选择的客户归属是否同一个人
	 * @param: [request, webAccount]
	 * @return: com.vedeng.common.model.ResultInfo
	 * @auther: duke.li
	 * @date: 2019/7/27 14:15
	 */
	@ResponseBody
	@RequestMapping(value = "/vailTraderUser")
	public ResultInfo vailTraderUser(HttpServletRequest request, WebAccountVo webAccountVo){
		try {
			return webAccountService.vailTraderUser(webAccountVo);
		}catch (Exception e){
			logger.error("验证售后注册用户失败：vailTraderUser" ,e);
			return new ResultInfo();
		}

	}

	/**
	 * <b>Description:</b><br> 保存编辑/分配注册账号
	 * @param request
	 * @param webAccountVo
	 * @param session
	 * @param beforeParams
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:09:08
	 */
	@ResponseBody
	@RequestMapping(value = "/saveedit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑/分配注册账号")
	public ModelAndView saveEdit(HttpServletRequest request, WebAccountVo webAccountVo, HttpSession session,String beforeParams){
		ModelAndView mv = new ModelAndView();
		if(null == webAccountVo || null == webAccountVo.getErpAccountId()){
			return pageNotFound();
		}
		try {
			Integer suc = webAccountService.saveEdit(webAccountVo,session,request);
			if(suc > 0){
				//WebAccount webAccountRecond = apiSoap.selectMobileResult(webAccountVo);
				//userController.booleanSend(webAccountRecond);
				mv.addObject("url", "./view.do?erpAccountId=" + webAccountVo.getErpAccountId());
				mv.addObject("refresh", "true_false_false");// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
				return success(mv);
			}
			return fail(mv);
		}catch (Exception e){
			logger.error("saveEdit保存发生异常：",e);
			return fail(mv);
		}

	}
	/**
	 * <b>Description:</b><br> 注册账号重置密码
	 * @param webAccount
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:08:34
	 */
	@ResponseBody
	@RequestMapping(value = "/resetpassword")
	@SystemControllerLog(operationType = "edit",desc = "注册账号重置密码")
	public ResultInfo resetPassword(WebAccount webAccount,HttpSession session){
		ResultInfo resultInfo = new ResultInfo<>();
		if(null == webAccount || webAccount.getErpAccountId() == null){
			return resultInfo;
		}
		resultInfo = vedengSoapService.webAccountRestPasswordSync(webAccount);
		return resultInfo;
	}

	@RequestMapping(value = "/transferCertificate")
    @ResponseBody
    @SystemControllerLog(operationType = "edit",desc = "转移资质")
    public ResultInfo transferCertificate(Integer erpAccountId, Integer traderId, int type, HttpSession session){
        if (erpAccountId == null || traderId == null || type < 1) {
            return new ResultInfo(-1, "操作失败，相关参数不得为空");
        }
        User user = (User) session.getAttribute(ErpConst.CURR_USER);
        try {
            return webAccountService.transferCertificate(erpAccountId, traderId, type, user);
        } catch (Exception ex) {
            logger.error("转移资质失败，erpaccountId:" + erpAccountId, ex);
            return new ResultInfo(-1, "操作失败");
        }
    }

	@ResponseBody
	@RequestMapping(value = "/certificate/update", method = RequestMethod.POST)
	public ResultInfo updateBDTraderCertificate(@RequestBody() BDTraderCertificate bdTraderCertificate) {
			return webAccountService.updateBDTraderCertificate(bdTraderCertificate);
	}

	/**
	 * <b>Description:</b>新增用户关联企业资质<br>
	 *
	 * @param bdTraderCertificate 关联企业资质信息
	 * @return resultInfo 操作结果
	 * @Note <b>Author:calvin</b>
	 * <br><b>Date:</b> 2020/2/27
	 */
	@ResponseBody
	@RequestMapping(value = "/certificate/add")
	public ResultInfo addBDTraderCertificate(@RequestBody() BDTraderCertificate bdTraderCertificate) {
			return webAccountService.addBDTraderCertificate(bdTraderCertificate);
	}


}
