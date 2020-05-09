package com.vedeng.trader.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vedeng.common.constant.TraderConstants;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.TraderCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.aftersales.service.WebAccountService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.soap.service.VedengSoapService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.TraderContact;
import com.vedeng.trader.model.WebAccount;
import com.vedeng.trader.model.vo.TraderAddressVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.WebAccountVo;

@Controller
@RequestMapping("/trader/accountweb")
public class AccountWebController extends BaseController{
	@Autowired
	@Qualifier("vedengSoapService")
	private VedengSoapService vedengSoapService;
	
	@Autowired
	@Qualifier("webAccountService")
	private WebAccountService webAccountService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
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
	public ModelAndView list(HttpServletRequest request, WebAccountVo webAccountVo,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
							 @RequestParam(required = false, value = "startAddDateStr") String startAddDateStr,
							 @RequestParam(required = false, value = "endAddDateStr") String endAddDateStr,
							 @RequestParam(required = false, value = "startJoinDateStr") String startJoinDateStr,
							 @RequestParam(required = false, value = "endJoinDateStr") String endJoinDateStr,
							 @RequestParam(required = false, value = "startMemberDateStr") String startMemberDateStr,
							 @RequestParam(required = false, value = "endMemberDateStr") String endMemberDateStr,
			@RequestParam(required = false) Integer pageSize, HttpSession session) {
		User user = (User) session.getAttribute(ErpConst.CURR_USER);
		ModelAndView mv = new ModelAndView("trader/webaccount/list_webaccount");
		Page page = getPageTag(request, pageNo, pageSize);
		webAccountVo.setUserId(user.getUserId());
		if (EmptyUtils.isNotBlank(startAddDateStr)) {
			webAccountVo.setStartAddDate(DateUtil.convertLong(startAddDateStr + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (EmptyUtils.isNotBlank(endAddDateStr)) {
			webAccountVo.setEndAddDate(DateUtil.convertLong(endAddDateStr + " 23:59:59", DateUtil.TIME_FORMAT));
		}

		if (EmptyUtils.isNotBlank(startJoinDateStr)) {
			webAccountVo.setStartJoinDate(DateUtil.convertLong(startJoinDateStr + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (EmptyUtils.isNotBlank(endJoinDateStr)) {
			webAccountVo.setEndJoinDate(DateUtil.convertLong(endJoinDateStr + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		if (EmptyUtils.isNotBlank(startMemberDateStr)) {
			webAccountVo.setStartMemberDate(DateUtil.convertLong(startMemberDateStr + " 00:00:00", DateUtil.TIME_FORMAT));
		}
		if (EmptyUtils.isNotBlank(endMemberDateStr)) {
			webAccountVo.setEndMemberDate(DateUtil.convertLong(endMemberDateStr + " 23:59:59", DateUtil.TIME_FORMAT));
		}
		mv.addObject("startAddDateStr",startAddDateStr);mv.addObject("endAddDateStr",endAddDateStr);
		mv.addObject("startJoinDateStr",startJoinDateStr);mv.addObject("endJoinDateStr",endJoinDateStr);
		mv.addObject("startMemberDateStr",startMemberDateStr);
		mv.addObject("endMemberDateStr",endMemberDateStr);
		Map<String, Object> map = webAccountService.getWebAccountListPage(webAccountVo,page,session);
		
		mv.addObject("list", (List<WebAccountVo>) map.get("list"));
		mv.addObject("page", (Page) map.get("page"));
		mv.addObject("webAccountVo", webAccountVo);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 注册用户信息查看
	 * @param request
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:04:19
	 */
	@ResponseBody
	@RequestMapping(value = "/view")
	public ModelAndView view(HttpServletRequest request, WebAccount webAccount, HttpSession session,@RequestParam(value="saleorderId",required=false  )Integer saleorderId){
		ModelAndView mv = new ModelAndView("trader/webaccount/view_webaccount");
		if(null == webAccount || null == webAccount.getErpAccountId()){
			return pageNotFound();
		}

		try {

			Map<String, Object> map = webAccountService.getWebAccount(webAccount,2);
			Map<String,Object> certificatesMap=webAccountService.getCertificateByCategory(webAccount.getErpAccountId());
			WebAccountVo webAccountVo = (WebAccountVo) map.get("webAccountVo");
			TraderCustomerVo traderCustomerVo = (TraderCustomerVo) map.get("customer");
			List<TraderAddressVo> addressList = (List<TraderAddressVo>) map.get("address");
			List<TraderContact> contactList = (List<TraderContact>) map.get("contact");

			TraderCustomer traderCustomer = (TraderCustomer)map.get("traderCustomer");
			mv.addObject("beforeParams", saveBeforeParamToRedis(JsonUtils.translateToJson(webAccountVo)));
			mv.addObject("webAccountVo", webAccountVo);

			mv.addObject("traderCustomer", traderCustomer);
			mv.addObject("traderCustomer", traderCustomerVo);
			mv.addObject("addressList", addressList);
			mv.addObject("contactList", contactList);
			mv.addObject(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE,certificatesMap.get(TraderConstants.ACCOUNT_BUSINESS_CERTIFICATE));
			mv.addObject(TraderConstants.ACCOUNT_SECOND_CERTIFICATE,certificatesMap.get(TraderConstants.ACCOUNT_SECOND_CERTIFICATE));
			mv.addObject(TraderConstants.ACCOUNT_THIRD_CERTIFICATE,certificatesMap.get(TraderConstants.ACCOUNT_THIRD_CERTIFICATE));
		} catch (Exception e) {
			logger.error("account error:", e);
		}

		mv.addObject("saleorderId", saleorderId);
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 保存编辑/分配注册账号
	 * @param request
	 * @param session
	 * @return
	 * @Note
	 * <b>Author:</b> Jerry
	 * <br><b>Date:</b> 2018年1月11日 下午3:09:08
	 */
	@ResponseBody
	@RequestMapping(value = "/saveedit")
	@SystemControllerLog(operationType = "edit",desc = "保存编辑/分配注册账号")
	public ModelAndView saveEdit(HttpServletRequest request, WebAccountVo webAccountVo, HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(null == webAccountVo || null == webAccountVo.getErpAccountId()){
			return pageNotFound();
		}
		try {
			Integer suc = webAccountService.saveEdit(webAccountVo,session,request);
			if(suc > 0){
				mv.addObject("url", "./view.do?erpAccountId=" + webAccountVo.getErpAccountId());
				mv.addObject("refresh", "true_false_false");// 是否关闭当前页，是否刷新当前页，是否刷新上层页面----三个参数为必传项
				return success(mv);
			}
		}catch (Exception e){
			return fail(mv);
		}
		return fail(mv);
	}

	@ResponseBody
	@RequestMapping(value = "/vailTraderUser")
	public ResultInfo vailTraderUser(HttpServletRequest request, WebAccountVo webAccountVo){
		try {
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			webAccountVo.setUserId(user.getUserId());
			return webAccountService.vailTraderUser(webAccountVo);
		}catch (Exception e){
			logger.error("验证销售注册用户失败：vailTraderUser" ,e);
			return new ResultInfo();
		}
	}
}
