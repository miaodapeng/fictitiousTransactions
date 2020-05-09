package com.vedeng.finance.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vedeng.activiti.service.ActionProcdefService;
import com.vedeng.authorization.model.User;
import com.vedeng.common.annotation.SystemControllerLog;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.jasper.IreportExport;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.page.Page;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.validator.FormToken;
import com.vedeng.finance.model.AccountPeriod;
import com.vedeng.finance.model.TraderAccountPeriodApply;
import com.vedeng.finance.service.TraderAccountPeriodApplyService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.UserService;
import com.vedeng.system.service.VerifiesRecordService;
import com.vedeng.trader.model.TraderAmountBill;
import com.vedeng.trader.model.TraderSupplier;
import com.vedeng.trader.model.vo.TraderCertificateVo;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.model.vo.TraderMedicalCategoryVo;
import com.vedeng.trader.model.vo.TraderSupplierVo;
import com.vedeng.trader.service.TraderCustomerService;
import com.vedeng.trader.service.TraderSupplierService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/finance/accountperiod")
public class TraderAccountPeriodApplyController extends BaseController{
    	
    @Autowired // 自动装载
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Autowired
	@Qualifier("accountPeriodService")
	private TraderAccountPeriodApplyService accountPeriodService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;//自动注入userService
	
	@Autowired
	@Qualifier("traderCustomerService")
	private TraderCustomerService traderCustomerService;//客户-交易者
	
	@Autowired
	@Qualifier("traderSupplierService")
	private TraderSupplierService traderSupplierService;
	
	@Autowired
	@Qualifier("actionProcdefService")
	private ActionProcdefService actionProcdefService;
	
	@Autowired
	@Qualifier("verifiesRecordService")
	private VerifiesRecordService verifiesRecordService;
	
	/**
	 * <b>Description:</b><br> 查询账期申请记录列表信息
	 * @param request
	 * @param tapa
	 * @param session
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月1日 下午2:01:48
	 */
	@ResponseBody
	@RequestMapping(value="accountPeriodApplyList")
	public ModelAndView accountPeriodApplyList(HttpServletRequest request,TraderAccountPeriodApply tapa,HttpSession session,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView();
		
		try {
			
			if(StringUtils.isNoneBlank(startTime)){
				tapa.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
			}
			if(StringUtils.isNoneBlank(endTime)){
				tapa.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
			}
			mv.addObject("startTime", startTime);mv.addObject("endTime", endTime);
			User user = (User)session.getAttribute(Consts.SESSION_USER);
			mv.addObject("loginUser",user);//登陆用户
			//申请人员列表
			List<Integer> list = new ArrayList<>();
			list.add(SysOptionConstant.ID_310);//销售
			list.add(SysOptionConstant.ID_311);//采购
			List<User> userList = userService.getMyUserList(user,list,false);
			mv.addObject("userList",userList);
			//默认展示审核中的
			if(null == request.getParameter("status") || request.getParameter("status") == ""){
			    tapa.setStatus(0);
			}
			Page page = getPageTag(request,pageNo,pageSize);
			
			tapa.setCompanyId(user.getCompanyId());
			//判断当前用户是否在审核人名单中
			Integer position = 0;
			if(user.getPositions() != null){
			     position = user.getPositions().get(0).getType();
			}
			
			if(position == 314){
			    tapa.setValidUserName(user.getUsername());
			}
			Map<String,Object> map = accountPeriodService.getAccountPeriodApplyListPage(tapa,page);
			
			List<TraderAccountPeriodApply> accountPeriodApplyList = (List<TraderAccountPeriodApply>)map.get("list");
			if(accountPeriodApplyList!=null && !accountPeriodApplyList.isEmpty()){
				List<Integer> userIds = new ArrayList<>();
				for(int i=0;i<accountPeriodApplyList.size();i++){
					userIds.add(accountPeriodApplyList.get(i).getCreator());
					if(null != accountPeriodApplyList.get(i).getVerifyUsername()){
					    List<String> verifyUsernameList = Arrays.asList(accountPeriodApplyList.get(i).getVerifyUsername().split(","));  
					    accountPeriodApplyList.get(i).setVerifyUsernameList(verifyUsernameList);
					}
				}
				List<User> creatorUserList = userService.getUserByUserIds(userIds);
				mv.addObject("creatorUserList",creatorUserList);
			}
			
			mv.addObject("accountPeriodApplyList",accountPeriodApplyList);
			mv.addObject("tapa",tapa);
			
			mv.addObject("page", (Page)map.get("page"));
			
			//发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_464);
			mv.addObject("invoiceTypeList", invoiceTypeList);
		} catch (Exception e) {
			logger.error("accountPeriodApplyList:", e);
			return null;
		}
		
		mv.setViewName("finance/accountPeriod/list_accountPeriod");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 导出账期申请记录1
	 * @param model
	 * @param request
	 * @param tapa
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月20日 下午6:09:37
	 */
	@RequestMapping(value = "/export_{1}", method = RequestMethod.GET)
	public String report(Model model,HttpServletRequest request,HttpServletResponse response,TraderAccountPeriodApply tapa) {
		try {
			List<TraderAccountPeriodApply> accountPeriodApplyList = accountPeriodService.exportAccountPeriodApplyList(tapa);
			if(accountPeriodApplyList == null || accountPeriodApplyList.size() == 0){
				return null;
			}
			// 报表数据源  
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(accountPeriodApplyList);
			
			model = IreportExport.exportOut(model, "/WEB-INF/ireport/jasper/accountPeriod.jasper", jrDataSource, "xls");
			
			// 动态指定报表模板url  
			/*model.addAttribute("url", "/WEB-INF/ireport/jasper/accountPeriod.jasper");
			model.addAttribute("format", "xls"); // 报表格式  PDF，XLS，RTF，HTML，CSV
			model.addAttribute("jrDataSource", jrDataSource);*/
			
		} catch (Exception e) {
			logger.error("export_", e);
		}
	    return "iReportView"; // 对应jasper-defs.xml中的bean id  
	}
	
	/**
	 * <b>Description:</b><br> 导出账期申请记录2
	 * @param model
	 * @param request
	 * @param response
	 * @param tapa
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年11月21日 下午7:02:08
	 */
	@RequestMapping(value = "/exportAccountperiod", method = RequestMethod.GET)
	public void exportAccountperiod(Model model,HttpServletRequest request,HttpServletResponse response,TraderAccountPeriodApply tapa) {
		
		try {
			List<TraderAccountPeriodApply> accountPeriodApplyList = accountPeriodService.exportAccountPeriodApplyList(tapa);
			if(accountPeriodApplyList != null && accountPeriodApplyList.size() > 0){
				JRDataSource dataSource = new JRBeanCollectionDataSource(accountPeriodApplyList);
				IreportExport.exportWrite(request, response, "/WEB-INF/ireport/jrxml/账期申请.jrxml", dataSource, "账期列表.xls");
			}
		} catch (Exception e) {
			logger.error("exportAccountperiod:", e);
		}

	}
	
	/**
	 * <b>Description:</b><br> 根据主键获取账期申请信息
	 * @param request
	 * @param tapa
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月2日 上午10:22:36
	 */
	@ResponseBody
	@RequestMapping(value="getAccountPeriodApply")
	public ModelAndView getAccountPeriodApply(HttpServletRequest request,TraderAccountPeriodApply tapa){
		ModelAndView mv = new ModelAndView();
		User curr_user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		mv.addObject("curr_user", curr_user);
		try {
			tapa = accountPeriodService.getAccountPeriodApply(tapa);
			mv.addObject("tapa", tapa);
			Integer customerNature = null;
			TraderCertificateVo tc = new TraderCertificateVo();
			tc.setTraderId(tapa.getTraderId());
			if(tapa.getTraderType()==1){//客户
				//根据客户ID查询客户信息
				TraderCustomerVo customer = traderCustomerService.getTraderCustomerInfo(tapa.getTraderId());
				mv.addObject("customer", customer);
				tc.setTraderType(ErpConst.ONE);
				if(customer.getCustomerNature() == 466){
					tc.setCustomerType(1);
				}else{
					tc.setCustomerType(2);
				}
				customerNature = customer.getCustomerNature();
			}else{//供应商
				TraderSupplier ts = new TraderSupplier();
				ts.setTraderId(tapa.getTraderId());
				TraderSupplierVo traderSupplier = traderSupplierService.getTraderSupplierInfo(ts);
				mv.addObject("traderSupplier", traderSupplier);
				tc.setTraderType(ErpConst.TWO);
			}
			
			Map<String, Object> map = traderCustomerService.getFinanceAndAptitudeByTraderId(tc, "all");
			//资质信息
			if(map!=null){
//				Integer customerProperty = Integer.valueOf(map.get("customerProperty").toString());
				List<TraderCertificateVo> bus = null;
				// 营业执照信息
				if(map.containsKey("business")){
//			JSONObject json=JSONObject.fromObject(map.get("business"));
//			bus=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//			mav.addObject("business", bus);
					bus=(List<TraderCertificateVo>)map.get("business");
					if(!CollectionUtils.isEmpty(bus)) {
						mv.addObject("business", bus.get(0));
					}
				}
				// 税务登记信息
				TraderCertificateVo tax = null;
				if(map.containsKey("tax")){
					JSONObject json=JSONObject.fromObject(map.get("tax"));
					tax=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
					mv.addObject("tax", tax);
				}
				// 组织机构信息
				TraderCertificateVo orga = null;
				if(map.containsKey("orga")){
					JSONObject json=JSONObject.fromObject(map.get("orga"));
					orga=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
					mv.addObject("orga", orga);
				}
				mv.addObject("traderType", tapa.getTraderType());
				if(tapa.getTraderType()==2){//供应商
					// 二类医疗资质
					List<TraderCertificateVo> twoMedicalList = null;
					if(map.containsKey("twoMedical")){
//						JSONObject json=JSONObject.fromObject(map.get("twoMedical"));
					    twoMedicalList=(List<TraderCertificateVo>) map.get("twoMedical");
						mv.addObject("twoMedicalList", twoMedicalList);
					}
					// 三类医疗资质
					TraderCertificateVo threeMedical1 = null;
//					if(map.containsKey("threeMedical")){
//						JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
//						threeMedical=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//						mv.addObject("threeMedical", threeMedical);
//					}
					// 三类医疗资质
					List<TraderCertificateVo> threeMedicalList = null;
					if(map.containsKey("threeMedical")){
						try{
							// JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
							threeMedicalList=(List<TraderCertificateVo>)map.get("threeMedical");
							if (CollectionUtils.isNotEmpty(threeMedicalList)) {
								mv.addObject("threeMedical", threeMedicalList.get(0));
							}
						}catch(Exception e){
							logger.error("threeMedical",e);
						}
					}


					List<TraderMedicalCategoryVo> list=null;
					if(map.containsKey("medicalCertificate")){
						JSONArray jsonArray=JSONArray.fromObject(map.get("medicalCertificate"));
						list=(List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
						mv.addObject("medicalCertificates", list);
					}
					
					List<TraderMedicalCategoryVo> two=null;
					if(map.containsKey("two")){
						JSONArray jsonArray=JSONArray.fromObject(map.get("two"));
						two=(List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
						mv.addObject("two", two);
					}
					List<TraderMedicalCategoryVo> three=null;
					if(map.containsKey("three")){
						JSONArray jsonArray=JSONArray.fromObject(map.get("three"));
						three=(List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
						mv.addObject("three", three);
					}
					
					// 医疗器械生产许可证
					TraderCertificateVo product = null;
					if(map.containsKey("product")){
						JSONObject json=JSONObject.fromObject(map.get("product"));
						product=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("product", product);
					}
					// 医疗器械经营许可证
					TraderCertificateVo operate = null;
					if(map.containsKey("operate")){
						JSONObject json=JSONObject.fromObject(map.get("operate"));
						operate=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("operate", operate);
					}
					// 销售授权书与授权销售人
					TraderCertificateVo saleAuth = null;
					if (map.containsKey("saleAuth"))
					{
						JSONObject json = JSONObject.fromObject(map.get("saleAuth"));
						saleAuth = (TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
						mv.addObject("saleAuth", saleAuth);
					}
					// 品牌授权书
                    List<TraderCertificateVo> brandList = null;
                    if(map.containsKey("brandBookList")){
                        brandList=(List<TraderCertificateVo>) map.get("brandBookList");
                        mv.addObject("brandBookList", brandList);
                    }
                 // 其他医疗资质
                    List<TraderCertificateVo> otherList = null;
                    if(map.containsKey("otherList")){
                        otherList=(List<TraderCertificateVo>) map.get("otherList");
                        mv.addObject("otherList", otherList);
                    }
				}else{//客户
					/*Integer customerProperty = null;
					customerProperty = traderCustomerService.getCustomerCategory(tapa.getTraderId());
					mv.addObject("customerProperty", customerProperty);*/
					
					if(customerNature == 465){
						// 二类医疗资质
						List<TraderCertificateVo> twoMedicalList = null;
						if(map.containsKey("twoMedical")){
//							JSONObject json=JSONObject.fromObject(map.get("twoMedical"));
						    twoMedicalList=(List<TraderCertificateVo>) map.get("twoMedical");
							mv.addObject("twoMedicalList", twoMedicalList);
						}
						// 三类医疗资质
//						TraderCertificateVo threeMedical = null;
//						if(map.containsKey("threeMedical")){
//							JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
//							threeMedical=(TraderCertificateVo) JSONObject.toBean(json, TraderCertificateVo.class);
//							mv.addObject("threeMedical", threeMedical);
//						}

						// 三类医疗资质
						List<TraderCertificateVo> threeMedicalList = null;
						if(map.containsKey("threeMedical")){
							try{
								// JSONObject json=JSONObject.fromObject(map.get("threeMedical"));
								threeMedicalList=(List<TraderCertificateVo>)map.get("threeMedical");
								if (!CollectionUtils.isEmpty(threeMedicalList)) {
									mv.addObject("threeMedical", threeMedicalList.get(0));
								}
							}catch(Exception e){
								logger.error("threeMedical",e);
							}
						}



						List<TraderMedicalCategoryVo> list=null;
						if(map.containsKey("medicalCertificate")){
							JSONArray jsonArray=JSONArray.fromObject(map.get("medicalCertificate"));
							list=(List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
							mv.addObject("medicalCertificates", list);
						}
						
						List<TraderMedicalCategoryVo> two=null;
						if(map.containsKey("two")){
							JSONArray jsonArray=JSONArray.fromObject(map.get("two"));
							two=(List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
							mv.addObject("two", two);
						}
						
						List<TraderMedicalCategoryVo> three=null;
						if(map.containsKey("three")){
							JSONArray jsonArray=JSONArray.fromObject(map.get("three"));
							three=(List<TraderMedicalCategoryVo>) JSONArray.toCollection(jsonArray, TraderMedicalCategoryVo.class);
							mv.addObject("three", three);
						}
						// 品牌授权书
                        List<TraderCertificateVo> brandList = null;
                        if(map.containsKey("brandBookList")){
                            brandList=(List<TraderCertificateVo>) map.get("brandBookList");
                            mv.addObject("brandBookList", brandList);
                        }
                     // 其他医疗资质
                        List<TraderCertificateVo> otherList = null;
                        if(map.containsKey("otherList")){
                            otherList=(List<TraderCertificateVo>) map.get("otherList");
                            mv.addObject("otherList", otherList);
                        }
					} 
					if(customerNature == 466){
						// 医疗机构执业许可证
						List<TraderCertificateVo> practiceList = null;
						if(map.containsKey("practice")){
//							JSONObject json=JSONObject.fromObject(map.get("practice"));
						    practiceList=(List<TraderCertificateVo>) map.get("practice");
							mv.addObject("practiceList", practiceList);
						}
					}
				}
			}
			//查询审核记录
			TraderAmountBill tab = new TraderAmountBill();
			tab.setTraderType(tapa.getTraderType());
			tab.setTraderId(tapa.getTraderId());
			tab.setAmountType(ErpConst.ONE);
			tab.setEvent(SysOptionConstant.ID_433);
			List<TraderAmountBill> amountBillList = accountPeriodService.getTraderAmountBillList(tab);
			if(amountBillList!=null && !amountBillList.isEmpty()){
				List<Integer> userIdList = new ArrayList<>();
				for(TraderAmountBill bill:amountBillList){
					userIdList.add(bill.getCreator());
				}
				List<User> userList = userService.getUserByUserIds(userIdList);
				mv.addObject("userList", userList);
				mv.addObject("amountBillList", amountBillList);
			}
			Map<String, Object> historicInfo=actionProcdefService.getHistoric(processEngine, "customerPeriodVerify_"+ tapa.getTraderAccountPeriodApplyId());
			Task taskInfo = (Task) historicInfo.get("taskInfo");
			mv.addObject("taskInfo", historicInfo.get("taskInfo"));
			mv.addObject("startUser", historicInfo.get("startUser"));
			mv.addObject("candidateUserMap", historicInfo.get("candidateUserMap"));
			// 最后审核状态
			mv.addObject("endStatus",historicInfo.get("endStatus"));
			mv.addObject("historicActivityInstance", historicInfo.get("historicActivityInstance"));
			mv.addObject("commentMap", historicInfo.get("commentMap"));
		    	
		    	String verifyUsers = null;
		    	if(null!=taskInfo){
		    	    Map<String, Object> taskInfoVariables= actionProcdefService.getVariablesMap(taskInfo);
		    	    verifyUsers = (String) taskInfoVariables.get("verifyUsers");
		    	}
		    	mv.addObject("verifyUsers", verifyUsers);
			mv.setViewName("finance/accountPeriod/audit_accountPeriod");
		} catch (Exception e) {
			logger.error("getAccountPeriodApply:", e);
			return null;
		}
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 账期审核原因填写初始化
	 * @param request
	 * @param tapa
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月2日 下午6:08:59
	 */
	@ResponseBody
	@RequestMapping(value="accountPeriodAuditReasonInit")
	public ModelAndView accountPeriodAuditReasonInit(HttpServletRequest request,TraderAccountPeriodApply tapa){
		ModelAndView mv = new ModelAndView();
		mv.addObject("tapa", tapa);
		mv.setViewName("finance/accountPeriod/audit_accountPeriod_reason");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 修改账期审核状态
	 * @param request
	 * @param tapa
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月3日 下午4:23:57
	 */
	@ResponseBody
	@RequestMapping(value="editAccountPeriodAudit")
	@SystemControllerLog(operationType = "edit",desc = "修改账期审核状态")
	public ResultInfo<?> editAccountPeriodAudit(HttpServletRequest request,TraderAccountPeriodApply tapa){
		User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
		if(user!=null){
			tapa.setUpdater(user.getUserId());
			tapa.setModTime(DateUtil.sysTimeMillis());
		}
		try {
			return accountPeriodService.editAccountPeriodAudit(tapa);
		} catch (Exception e) {
			logger.error("trader account period apply edit:", e);
			return new ResultInfo<>();
		}
	}
	
	/**
	 * <b>Description:</b><br> 客户账期记录
	 * @param request
	 * @param tapa
	 * @param session
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月5日 下午5:25:05
	 */
	@ResponseBody
	@RequestMapping(value="getCustomerAccountListPage")
	public ModelAndView getCustomerAccountListPage(HttpServletRequest request,AccountPeriod ap,
			@RequestParam(required = false, value="startTime") String startTime,
			@RequestParam(required = false, value="endTime") String endTime,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,@RequestParam(required = false) Integer pageSize){
		ModelAndView mv = new ModelAndView();
		
		try {
			
			if(StringUtils.isNoneBlank(startTime)){
				ap.setStartDate(DateUtil.convertLong(startTime + " 00:00:00",""));
			}
			if(StringUtils.isNoneBlank(endTime)){
				ap.setEndDate(DateUtil.convertLong(endTime + " 23:59:59",""));
			}
			mv.addObject("startTime", startTime);mv.addObject("endTime", endTime);
			
			if(ap.getTraderUserId()!=null){
				List<Integer> traderIdList = userService.getTraderIdListByUserId(ap.getTraderUserId(), ErpConst.ONE);//1客户，2供应商
				if(traderIdList!=null && !traderIdList.isEmpty()){//销售人员无客户，默认不出数据
					ap.setTraderIdList(traderIdList);
				}
			}
			User user = (User)request.getSession().getAttribute(Consts.SESSION_USER);
			ap.setCompanyId(user.getCompanyId());
			//获取当前销售用户下级职位用户
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_310);
			List<User> ascriptionUserList = userService.getMyUserList(user, positionType, false);//归属销售
			mv.addObject("ascriptionUserList", ascriptionUserList);
			
			List<Integer> userIdList = new ArrayList<>();
			for(int i=0;i<ascriptionUserList.size();i++){
				userIdList.add(ascriptionUserList.get(i).getUserId());
			}
			ap.setUserIdList(userIdList);
			//发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
//			if(JedisUtils.exists(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_428)){
//				String json_result = JedisUtils.get(dbType+ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + SysOptionConstant.ID_428);
//				JSONArray jsonArray = JSONArray.fromObject(json_result);
//				invoiceTypeList = (List<SysOptionDefinition>) JSONArray.toCollection(jsonArray, SysOptionDefinition.class);
//			}
			mv.addObject("invoiceTypeList", invoiceTypeList);
			
			Page page = getPageTag(request,pageNo,pageSize);
			
			Map<String,Object> map = accountPeriodService.getCustomerAccountListPage(ap,page);
			if(map!=null){
				mv.addObject("customerAccountList",(List<AccountPeriod>)map.get("customerAccountList"));
//				mv.addObject("customerRepayList",(List<TraderAccountPeriodApply>)map.get("customerRepayList"));
				mv.addObject("ap",(AccountPeriod)map.get("ap"));
				mv.addObject("userList", (List<User>)map.get("userList"));
				mv.addObject("page", (Page)map.get("page"));
				mv.addObject("sysdate", DateUtil.gainNowDate());
			}
			
			
		} catch (Exception e) {
			logger.error("getCustomerAccountListPage:", e);
			return null;
		}
		
		mv.setViewName("finance/accountPeriod/list_customer_account");
		return mv;
	}
	
	/**
	 * <b>Description:</b><br> 供应商账期记录
	 * @param request
	 * @param tapa
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年9月7日 上午10:13:50
	 */
	@ResponseBody
	@RequestMapping(value="getSupplierAccountListPage")
	public ModelAndView getSupplierAccountListPage(HttpServletRequest request, AccountPeriod ap,
			@RequestParam(required = false, value = "startTime") String startTime,
			@RequestParam(required = false, value = "endTime") String endTime,
			@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false) Integer pageSize) {
		ModelAndView mv = new ModelAndView();
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		try {
			if (StringUtils.isNoneBlank(startTime)) {
				ap.setStartDate(DateUtil.convertLong(startTime + " 00:00:00", ""));
			}
			if (StringUtils.isNoneBlank(endTime)) {
				ap.setEndDate(DateUtil.convertLong(endTime + " 23:59:59", ""));
			}
			mv.addObject("startTime", startTime);
			mv.addObject("endTime", endTime);

			if (ap.getTraderUserId() != null) {
				List<Integer> traderIdList = userService.getTraderIdListByUserId(ap.getTraderUserId(), ErpConst.TWO);// 1客户，2供应商
				if (traderIdList != null && !traderIdList.isEmpty()) {// 销售人员无客户，默认不出数据
					ap.setTraderIdList(traderIdList);
				}else{
					traderIdList = new ArrayList<Integer>(){{add(-1);}};
					ap.setTraderIdList(traderIdList);
				}
			}

			// 获取当前销售用户下级职位用户
			List<Integer> positionType = new ArrayList<>();
			positionType.add(SysOptionConstant.ID_311);
			List<User> ascriptionUserList = userService.getMyUserList(user, positionType, false);// 归属销售
			mv.addObject("ascriptionUserList", ascriptionUserList);

			// 发票类型
			List<SysOptionDefinition> invoiceTypeList = getSysOptionDefinitionList(SysOptionConstant.ID_428);
			mv.addObject("invoiceTypeList", invoiceTypeList);

			Page page = getPageTag(request, pageNo, pageSize);
			ap.setCompanyId(user.getCompanyId());
			Map<String, Object> map = accountPeriodService.getSupplierAccountListPage(ap, page);
			if (map != null) {
				mv.addObject("supplierAccountList", (List<AccountPeriod>) map.get("supplierAccountList"));
				// mv.addObject("customerRepayList",(List<TraderAccountPeriodApply>)map.get("customerRepayList"));
				mv.addObject("ap", (AccountPeriod) map.get("ap"));
				mv.addObject("userList", (List<User>) map.get("userList"));
				mv.addObject("page", (Page) map.get("page"));
				mv.addObject("sysdate", DateUtil.gainNowDate());
			}
		} catch (Exception e) {
			logger.error("getSupplierAccountListPage:", e);
			return null;
		}
		mv.setViewName("finance/accountPeriod/list_supplier_account");
		return mv;
	}
	
	/**
	 * 
	 * <b>Description:</b><br>
	 * 账期申请审核页面
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(save=true)
	@ResponseBody
	@RequestMapping(value = "/complement")
	public ModelAndView complement(HttpSession session,Integer traderAccountPeriodApplyId, String taskId, Boolean pass) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("taskId", taskId);
		mv.addObject("pass", pass);
		mv.addObject("traderAccountPeriodApplyId", traderAccountPeriodApplyId);
		mv.setViewName("finance/accountPeriod/complement");
		return mv;
	}

	/**
	 * 
	 * <b>Description:</b><br>
	 * 账期申请审核操作
	 * 
	 * @Note <b>Author:</b> Michael <br>
	 *       <b>Date:</b> 2017年11月10日 下午1:39:42
	 */
	@FormToken(remove=true)
	@ResponseBody
	@RequestMapping(value = "/complementTask")
	public ResultInfo<?> complementTask(HttpServletRequest request,Integer traderAccountPeriodApplyId, String taskId, String comment, Boolean pass,
			HttpSession session) {
		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
		Map<String, Object> variables = new HashMap<String, Object>();
		if(pass == false){
		    variables.put("value", 2);
		}
		variables.put("pass", pass);
		variables.put("updater",user.getUserId());
		try {
		    	Integer status = 0;
			if(pass){
			    //如果审核通过
			     status = 0;
			}else{
			    //如果审核不通过
			    status = 2;
			    verifiesRecordService.saveVerifiesInfo(taskId,status);
			}
		    ResultInfo<?> complementStatus = actionProcdefService.complementTask(request,taskId,comment,user.getUsername(),variables);
		    //如果未结束添加审核对应主表的审核状态
	    	    if(!complementStatus.getData().equals("endEvent")){
	    		verifiesRecordService.saveVerifiesInfo(taskId,status);
	    	    }
		    return new ResultInfo(0, "操作成功");
		} catch (Exception e) {
			logger.error("trader account period apply complementTask:", e);
			return new ResultInfo(-1, "任务完成操作失败：" + e.getMessage());
		}

	}
}
