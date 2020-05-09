package com.vedeng.common.controller;

import com.common.constants.Contant;
import com.vedeng.authorization.model.RRoleAction;
import com.vedeng.authorization.model.RRoleActiongroup;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ApiUrlConstant;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.OrderConstant;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.exception.BusinessException;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.model.TemplateVar;
import com.vedeng.common.model.vo.ReqTemplateVariable;
import com.vedeng.common.page.Page;
import com.vedeng.common.redis.RedisUtils;
import com.vedeng.common.service.BaseService;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.DateUtil;
import com.vedeng.common.util.EmptyUtils;
import com.vedeng.common.util.WeChatSendTemplateUtil;
import com.vedeng.finance.model.Invoice;
import com.vedeng.logistics.model.Logistics;
import com.vedeng.logistics.service.LogisticsService;
import com.vedeng.order.model.Saleorder;
import com.vedeng.order.model.SaleorderGoods;
import com.vedeng.order.service.SaleorderService;
import com.vedeng.passport.api.wechat.dto.template.TemplateVariable;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.ActiongroupService;
import com.vedeng.system.service.FtpUtilService;
import com.vedeng.system.service.UserService;
import com.vedeng.trader.model.WebAccount;
import com.xxl.rpc.util.IpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <b>Description:</b><br>
 * 基础控制器
 * 
 * @author Jerry
 * @Note <b>ProjectName:</b> erp <br>
 *       <b>PackageName:</b> com.vedeng.common.controller <br>
 *       <b>ClassName:</b> BaseController <br>
 *       <b>Date:</b> 2017年4月25日 上午11:10:28
 */
public class BaseController extends BaseSonContrller{
	public static final Logger log = LoggerFactory.getLogger("controller");

	public Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;
	@Autowired
	@Qualifier("actionService")
	private ActionService actionService;
	@Autowired
	@Qualifier("actiongroupService")
	private ActiongroupService actiongroupService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("logisticsService")
	private LogisticsService logisticsService;
	
	
	@Autowired
	@Qualifier("saleorderService")
	private SaleorderService saleorderService;
	@Autowired
	@Qualifier("ftpUtilService")
	protected FtpUtilService ftpUtilService;

	@Resource
	private BaseService baseService;
	@Value("${redis_dbtype}")
	protected String dbType;
	@Value("${file_url}")
	protected String domain;

	@Value("${rest_db_url}")
	protected String restDbUrl;

	@Value("${api_http}")
	protected String api_http;

	@Value("${api_url}")
	protected String apiUrl;

	@Value("${ws_url}")
	protected String wsUrl;
	@Value("${mjx_url}")
	protected String mjxUrl;

	@Value("${oss_url}")
	protected String ossUrl;
	/**
	 * json解析失败
	 */
	protected ResultInfo<Object> JSON_ERROR = new ResultInfo<>(-1, "数据解析失败");

	/**
	 * 服务器异常(超时,未找到服务等)
	 */
	protected ResultInfo<Object> UNKNOWN_ERROR = new ResultInfo<>(-1, "请求超时或错误，请联系管理员或稍后重试");

	/**
	 * <b>Description:</b><br>
	 * 根据参数生成实体page对象（无记录总数）
	 * 
	 * @param request
	 *            请求
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @return page对象
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:10:52
	 */
	protected Page getPageTag(HttpServletRequest request, Integer pageNo, Integer pageSize) {
		String path = request.getRequestURL().toString();
		// String web_name = request.getServletContext().getContextPath();
		// return Page.newBuilder(pageNo, pageSize,
		// path.substring(path.indexOf(web_name)+web_name.length()));
		try {
			String str = "";
			Map<?, ?> map = request.getParameterMap();
			if (map != null && (!map.isEmpty())) {
				path += "?";
				for (Object key : map.keySet()) {
					if (!(key.toString().equals("pageNo") || key.toString().equals("pageSize"))) {
						str = java.net.URLDecoder.decode(request.getParameter(key.toString()), "UTF-8");
						str = java.net.URLEncoder.encode(str, "UTF-8");
						path = path + key + "=" + str + "&";
					}
				}
				path = path.substring(0, path.length() - 1);
			}
		} catch (Exception e) {
			logger.warn("分页初始化失败", e);
			return Page.newBuilder(pageNo, pageSize, path);
		}
		return Page.newBuilder(pageNo, pageSize, path);
	}

	/**
	 * <b>Description:</b><br>
	 * 根据参数生成实体page对象（包含记录总数，无需拦截器再次计算）
	 * 
	 * @param request
	 *            请求
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页条数
	 * @param count
	 *            总数
	 * @return page对象
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:11:43
	 */
	protected Page getPageTag(HttpServletRequest request, Integer pageNo, Integer pageSize, Integer count) {
		String path = request.getRequestURL().toString();
		// String web_name = request.getServletContext().getContextPath();
		// return Page.newBuilder2(pageNo, pageSize,
		// path.substring(path.indexOf(web_name)+web_name.length()),count);
		try {
			Map<?, ?> map = request.getParameterMap();
			if (map != null && (!map.isEmpty())) {
				path = path + "?";
				for (Object key : map.keySet()) {
					if (!(key.toString().equals("pageNo") || key.toString().equals("pageSize"))) {
						path = path + key + "=" + request.getParameter(key.toString()) + "&";
					}
				}
				path = path.substring(0, path.length() - 1);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return Page.newBuilder2(pageNo, pageSize, path, count);
		}
		return Page.newBuilder2(pageNo, pageSize, path, count);
	}

	/**
	 * <b>Description:</b><br>
	 * 异常处理
	 * 
	 * @param e
	 * @param logger
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:12:15
	 */
	protected ResultInfo<Object> processException(Exception e, Logger logger) {
		logger.error("processException:", e);
		if (e instanceof BusinessException) {
			BusinessException ex = (BusinessException) e;
			String detail = getMessage(ex.getMessageCode());
			logger.error(ex.getMessageCode() + " - {}", detail, ex);
			// return new ResultInfo<Object>(-1, ex.getMessageCode(), detail);
			return new ResultInfo<Object>(-1, detail);
		} else if (e instanceof JsonProcessingException) {
			logger.error(e.getMessage(), e);
			return JSON_ERROR;
		} else {
			logger.error(e.getMessage(), e);
			return UNKNOWN_ERROR;
		}
	}

	protected String getMessage(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}

	/**
	 * <b>Description:</b><br>
	 * 操作成功
	 * 
	 * @param mv
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:12:35
	 */
	protected ModelAndView success(ModelAndView mv) {
		mv.setViewName("common/success");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 操作失败
	 * 
	 * @param mv
	 * @return
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月25日 上午11:12:46
	 */
	protected ModelAndView fail(ModelAndView mv) {
		mv.setViewName("common/fail");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 404
	 * 
	 * @return ModelAndView
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月26日 上午8:46:19
	 */
	protected ModelAndView pageNotFound() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/404");
		return mv;
	}
	/**
	 * <b>Description:</b><br>
	 * 404
	 *
	 * @return ModelAndView
	 * @Note <b>Author:</b> Jerry <br>
	 *       <b>Date:</b> 2017年4月26日 上午8:46:19
	 */
	protected ModelAndView page500() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/500");
		return mv;
	}
	/**
	 * <b>Description:</b><br>
	 * 功能点，功能分组，角色增删改，用户分配角色删除redis
	 * 
	 * @param actionId
	 * @param actiongroupId
	 * @param roleId

	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月10日 下午2:19:25
	 */
	protected void delMenuRedis(Integer actionId, Integer actiongroupId, Integer roleId, User user) {
		JedisUtils.del(dbType + ErpConst.KEY_PREFIX_GROUP_MENU + "admin");
		JedisUtils.del(dbType + ErpConst.KEY_PREFIX_MENU + "admin");
		List<Integer> rList = null;
		if (null != actionId) {
			List<RRoleAction> list = this.actionService.getRRoleActionListByActionId(actionId);
			if (null != list && list.size() > 0) {
				rList = new ArrayList<>();
				for (RRoleAction ra : list) {
					rList.add(ra.getRoleId());
				}
			}
		}
		if (null != actiongroupId) {
			List<RRoleActiongroup> list = this.actiongroupService.getRRoleActiongroupListByActiongroupId(actiongroupId);
			if (null != list && list.size() > 0) {
				rList = new ArrayList<>();
				for (RRoleActiongroup ra : list) {
					rList.add(ra.getRoleId());
				}
			}
		}
		if (null != roleId) {
			rList = new ArrayList<>();
			rList.add(roleId);
		}
		if (rList != null) {
			List<Integer> userIds = userService.getUserIdList(rList);
			for (Integer userId : userIds) {
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_GROUP_MENU + userId);
				JedisUtils.del(dbType + ErpConst.KEY_PREFIX_MENU + userId);
			}
		}

	}

	/**
	 * <b>Description:</b><br>
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年5月22日 上午9:46:39
	 */
	protected String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * <b>Description:</b><br>
	 * 文件上传异常处理
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @throws Exception
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月25日 下午2:33:30
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	protected void doException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.error("global error handler", e);
		long maxSize = ((MaxUploadSizeExceededException) e).getMaxUploadSize();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		// 用UTF-8转码，而不是用默认的ISO8859
		response.setCharacterEncoding("UTF-8");
		// response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("上传文件太大，不能超过" + maxSize + "k");
		// return new ResultInfo<>(-1,"上传文件太大，不能超过" + maxSize / 1024 + "k");
		/*
		 * } else if (e instanceof RuntimeException) {
		 * response.setHeader("Content-type", "text/html;charset=UTF-8"); //
		 * 用UTF-8转码，而不是用默认的ISO8859 response.setCharacterEncoding("UTF-8");
		 * response.getWriter().write("未选中文件"); } else {
		 * response.setHeader("Content-type", "text/html;charset=UTF-8"); //
		 * 用UTF-8转码，而不是用默认的ISO8859 response.setCharacterEncoding("UTF-8");
		 * response.getWriter().write("上传失败");
		 */
	}

	@ExceptionHandler(Exception.class)
	protected ModelAndView doExceptionAll(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userName="";
		try{
			User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
			userName=user.getUsername();
		}catch (Exception e2){

		}
		logger.error("global error handler"+"\t"+request.getRequestURI()+userName+ IpUtil.getIp()+
                request.getQueryString(), e);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/500");
		return mv;
	}

	/**
	 * <b>Description:</b><br>
	 * 根据companyId获取物流公司集合
	 * 
	 * @param companyId
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月27日 下午4:26:04
	 */
	@SuppressWarnings("unchecked")
	protected List<Logistics> getLogisticsList(Integer companyId) {
		List<Logistics> logisticsList = null;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + companyId)) {
			JSONArray jsonaArray = JSONArray
					.fromObject(JedisUtils.get(dbType + ErpConst.KEY_PREFIX_LOGISTICS_LIST + companyId));
			logisticsList = (List<Logistics>) JSONArray.toCollection(jsonaArray, Logistics.class);
		} else {
			logisticsList = logisticsService.getLogisticsList(companyId);
		}
		return logisticsList;
	}

	/**
	 * <b>Description:</b><br>
	 * 获取字典表中的集合
	 * 

	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月27日 下午4:32:06
	 */
	@SuppressWarnings("unchecked")
	protected List<SysOptionDefinition> getSysOptionDefinitionList(Integer parentId) {
		// modify by franlin.wu
		// for[针对只判断redis缓存的key是否存在来获取数字字典值,可能存在从redis缓存获取为null的场景,做以下修改] at
		// 2018-11-23 begin
		// 数字字典list
		List<SysOptionDefinition> resultList = null;
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + parentId)) {
			String jsonStr = JedisUtils.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_LIST + parentId);
			// 避免json为空或null的字符串或[null]的字符串
			if (StringUtils.isNotBlank(jsonStr) && !"null".equalsIgnoreCase(jsonStr)
					&& !"[null]".equalsIgnoreCase(jsonStr)) {
				JSONArray json = JSONArray.fromObject(jsonStr);
				resultList = (List<SysOptionDefinition>) JSONArray.toCollection(json, SysOptionDefinition.class);
			}
		}
		// 从redis中获取为null，则从库中查询
		if (CollectionUtils.isEmpty(resultList)) {
			// 调用根据parendId获取数字字典子list
			resultList = baseService.getSysOptionDefinitionListByParentId(parentId);
		}
		return resultList;
		// modify by franlin.wu
		// for[针对只判断redis缓存的key是否存在来获取数字字典值,可能存在从redis缓存获取为null的场景,做以下修改] at
		// 2018-11-23 end

	}

	/**
	 * <b>Description:</b><br>
	 * 获取字典表中对象
	 * 

	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年7月27日 下午4:41:43
	 */
	protected SysOptionDefinition getSysOptionDefinition(Integer sysOptionDefinitionId) {
		if (JedisUtils.exists(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sysOptionDefinitionId)) {
			String jsonStr = JedisUtils
					.get(dbType + ErpConst.KEY_PREFIX_DATA_DICTIONARY_OBJECT + sysOptionDefinitionId);
			JSONObject json = JSONObject.fromObject(jsonStr);
			SysOptionDefinition sys = (SysOptionDefinition) JSONObject.toBean(json, SysOptionDefinition.class);
			return sys;
		} else {
			SysOptionDefinition sys = baseService.getSysOptionDefinitionById(sysOptionDefinitionId);
			return sys;
		}

	}

	/**
	 *
	 * <b>Description: 根据id查询数字字典值</b><br>
	 * @param defaultValue  默认值
	 * @param sysOptId      数字字典主键ID
	 * @return
	 * <b>Author: Franlin.wu</b>
	 * <br><b>Date: 2018年12月14日 下午2:37:40 </b>
	 */
	public String getConfigStringByDefault(String defaultValue, Integer sysOptId) {
		logger.debug("查询id：{}, 默认值：{}", sysOptId, defaultValue);

		try
		{
			// 根据id查询数字字典值
			SysOptionDefinition sysOpt = getSysOptionDefinition(sysOptId);
			defaultValue = sysOpt.getTitle();
		}
		catch(Exception e)
		{
			logger.error("根据id查询数字字典配置发生异常", e);
		}

		return defaultValue;
	}

	/**
	 *
	 * <b>Description: 根据id查询数字字典值</b><br>
	 * @param defaultValue  默认值
	 * @param optionType
	 * @return
	 * <b>Author: Franlin.wu</b>
	 * <br><b>Date: 2018年12月14日 下午2:37:40 </b>
	 */
	public String getConfigStringByDefault(String defaultValue, String optionType) {
		logger.debug("查询id：{}, 默认值：{}", optionType, defaultValue);

		try
		{
			// 根据id查询数字字典值
			SysOptionDefinition option = baseService.getFirstSysOptionDefinitionList(optionType);
			if(null != option && EmptyUtils.isNotBlank(option.getTitle())) {
				defaultValue = option.getTitle();
			}
		}
		catch(Exception e)
		{
			logger.error("根据id查询数字字典配置发生异常", e);
		}

		return defaultValue;
	}



	protected Integer getOptionIdByOptionType(String optionType) {
		SysOptionDefinition option = baseService.getFirstSysOptionDefinitionList(optionType);
		if (option != null) {
			return option.getSysOptionDefinitionId();
		}
		return -1;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * <b>Description:</b><br>
	 * 将修改前的数据保存进redis返回key
	 * 
	 * @param beforeParams
	 * @return
	 * @Note <b>Author:</b> east <br>
	 *       <b>Date:</b> 2017年9月25日 下午1:49:23
	 */
	protected String saveBeforeParamToRedis(String beforeParams) {
		if (beforeParams == null) {
			beforeParams = "";
		}
		String redisKey = dbType + "beforeParams:" + UUID.randomUUID().toString();
		JedisUtils.set(redisKey, beforeParams, ErpConst.REDIS_USERID_SESSIONID_TIMEOUT);
		return redisKey;
	}

	/**
	 * 
	 * <b>Description: 获取session中用户信息</b><br>
	 * 
	 * @param request
	 * @return <b>Author: Franlin</b> <br>
	 *         <b>Date: 2018年6月5日 上午11:49:13 </b>
	 */
	public User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(ErpConst.CURR_USER);
	}

	/**
	 * <b>Description: 医械购微信发送订单签收模板消息</b>
	 * @param saleorders
	 * @param sTempMap
	 * <b>Author：</b> franlin.wu
	 * <b>Date:</b> 2019年5月24日 下午4:28:32
	 * @return
	 */
	public void sendTemplateMsgHcForOrderOk(Saleorder saleorders, Map sTempMap) {
		logger.info("sendTemplateMsgHcForOrderOk | 医械购微信发送订单签收模板消息 | begin .......");
		logger.info("sendTemplateMsgHcForOrderOk | 医械购微信发送订单签收模板消息 | saleorders:{}, sTempMap:{}", saleorders, sTempMap);
		if(null == saleorders) {
			return;
		}

		Integer orderType = saleorders.getOrderType();

		Integer saleorderId = saleorders.getSaleorderId();
		//修改于VDERP-1331 ,2019-10-14 开始
        Saleorder saleorder = new Saleorder();
        if(OrderConstant.ORDER_TYPE_HC.equals(orderType)) {
        	saleorder = saleorderService.getsaleorderbySaleorderId(saleorderId);
        }
		// add by franlin.wu for[微信推送 医械购 ] at 2019-06-20 begin
		if(OrderConstant.ORDER_TYPE_HC.equals(orderType)&&saleorder.getWebTakeDeliveryTime().equals(0L)) {
		//修改于VDERP-1331 ,2019-10-14 前台没有确认收货所以WebTakeDeliveryTime为默认值0 结束
			// add by franlin.wu for [微信推送 订单签收] at 2019-06-19 begin
			ReqTemplateVariable reqTemp = new ReqTemplateVariable();
			logger.info("sendTemplateMsgHcForOrderOk | traderConMobile:{}", saleorders.getTraderContactMobile());
			// 订单客户联系人
			reqTemp.setPhoneNumber(saleorders.getTraderContactMobile());
			// 模板消息数字字典Id
			reqTemp.setTemplateId(WeChatSendTemplateUtil.TEMPLATE_ORDER_SIGNING_NOTICE);

			TemplateVar first = new TemplateVar();
			String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已全部收货", SysOptionConstant.ID_TEMPLATE_ORDER_SIGNING_NOTICE_FIRST);
			logger.info("获取数据配置 | firstStr：{} ", firstStr);
			first.setValue(firstStr + "\r\n");

			TemplateVar keyword1 = new TemplateVar();
			TemplateVar keyword2 = new TemplateVar();
			TemplateVar keyword3 = new TemplateVar();
			TemplateVar keyword4 = new TemplateVar();

			TemplateVar remark = new TemplateVar();
			String remarkStr = getConfigStringByDefault("感谢您对医械购的支持与信任，如有疑问请联系：18651638763", SysOptionConstant.ID_WECHAT_TEMPLATE_REMARK);
			remark.setValue(remarkStr);

			if(null != sTempMap) {
				// 订单金额
				keyword1.setValue(sTempMap.get("totalAmount") + "元");
			//	String saleorderAllNum = NumberUtils.toInt(sTempMap.get("saleorderAllNum"));
				// 商品详情
				keyword2.setValue(sTempMap.get("saleorderFirstGoodsName") + "等 " + sTempMap.get("saleorderAllNum") + "个商品");
				// 收货信息
				keyword3.setValue(sTempMap.get("traderContactName") + " " + sTempMap.get("traderContactMobile") + " " +sTempMap.get("traderAddress"));

			} else {
				// （取耗材商城-销售详情页-交易信息，“订单实际金额”信息
				// 获取交易信息（订单实际金额，客户已付款金额）
				Map<String, BigDecimal> saleorderDataInfo = saleorderService.getSaleorderDataInfo(saleorderId);
				BigDecimal realAmount =  saleorderDataInfo.get("realAmount");
				// 订单金额
				keyword1.setValue(null == realAmount ? "0.00" : realAmount.toString() + "元");

				// 获取订单产品信息(与订单详情中获取相同)
				Saleorder sale = new Saleorder();
				sale.setSaleorderId(saleorders.getSaleorderId());
				sale.setTraderId(saleorders.getTraderId());
				sale.setCompanyId(saleorders.getCompanyId());
				List<SaleorderGoods> saleorderGoodsList = saleorderService.getSaleorderGoodsById(sale);
				//商品总数量（除去售后数量）
				Integer saleorderAllNum = 0;
				String saleorderFirstGoodsName = "";
				if(saleorderGoodsList != null && saleorderGoodsList.size()>0) {
					for (SaleorderGoods sg : saleorderGoodsList) {
						// 运费
						if(null == sg || "V127063".equals(sg.getSku()) || "V251526".equals(sg.getSku()) || "V252843".equals(sg.getSku()) || "V256675".equals(sg.getSku())) {
							continue;
						}
						if(EmptyUtils.isBlank(saleorderFirstGoodsName)) {
							saleorderFirstGoodsName = sg.getGoodsName();
						}
						//商品数-售后数
						saleorderAllNum = saleorderAllNum+(sg.getNum()-sg.getAfterReturnNum());
					}
				}
				// 商品详情
				keyword2.setValue(saleorderFirstGoodsName + "等 " + saleorderAllNum + "个商品");
				// 收货信息
				keyword3.setValue(saleorders.getTraderContactName() + " " + saleorders.getTraderContactMobile() + " " + saleorders.getTraderAddress());
			}
			// 订单编号
			keyword4.setValue(saleorders.getSaleorderNo() + "\r\n");

			reqTemp.setFirst(first);
			reqTemp.setKeyword1(keyword1);
			reqTemp.setKeyword2(keyword2);
			reqTemp.setKeyword3(keyword3);
			reqTemp.setKeyword4(keyword4);

			reqTemp.setRemark(remark);
			// 发送 微信服务 消息模板 订单签收
			WeChatSendTemplateUtil.sendTemplateMsg(apiUrl + ApiUrlConstant.API_WECHAT_SEND_TEMPLATE_MSG, reqTemp);
		}
		logger.info("sendTemplateMsgHcForOrderOk | 医械购微信发送订单签收模板消息 | end .......");
		// add by franlin.wu for[微信推送 医械购  订单签收] at 2019-06-20 begin
	}

	/**
	 * <b>Description: 医械购微信发送 发货 模板消息</b>
	 * @param saleorder
	 * @param sTempMap
	 * <b>Author：</b> franlin.wu
	 * <b>Date:</b> 2019年5月24日 下午4:28:32
	 * @return
	 */
	public void sendTemplateMsgHcForShip(Saleorder saleorder, Map<String, String> sTempMap) {
		logger.info("sendTemplateMsgHcForShip | 医械购微信 | saleorder:{}, sTempMap:{}", saleorder, sTempMap);

		if(null != saleorder && null != sTempMap) {
			// 非耗材订单
			if(!OrderConstant.ORDER_TYPE_HC.equals(saleorder.getOrderType())) {
				logger.warn("sendTemplateMsgHcForShip | 医械购微信 | 非医械购订单");
				return;
			}
			logger.info("sendTemplateMsgHcForShip | 医械购微信 发货 模板消息 | begin .......");
			// add by franlin.wu for [微信推送 发货提醒] at 2019-06-19 begin
			ReqTemplateVariable reqTemp = new ReqTemplateVariable();
			logger.info("sendTemplateMsgHcForShip | traderConMobile:{}", saleorder.getTraderContactMobile());
			// 订单客户联系人
			reqTemp.setPhoneNumber(saleorder.getTraderContactMobile());
			// 模板消息数字字典Id
			reqTemp.setTemplateId(WeChatSendTemplateUtil.TEMPLATE_SHIPING_REMINDER);

			// 头
			TemplateVar first = new TemplateVar();
			String firstStr = getConfigStringByDefault("尊敬的客户，您的订单已发货，请注意查收：", SysOptionConstant.ID_WECHAT_TEMPLATE_ORDER_SIGNING_NOTICE_FIRST);
			logger.info("sendTemplateMsgHcForShip | first :{}", firstStr);
			first.setValue(firstStr + "\r\n");

			// 商品名称
			TemplateVar keyword1 = new TemplateVar();
			// 订单号
			keyword1.setValue(sTempMap.get("saleorderNo"));

			// 商品名称
			TemplateVar keyword2 = new TemplateVar();
			// saleorderAllNum
			keyword2.setValue(sTempMap.get("expressFirstGoodsName") + "等" + " " + sTempMap.get("saleorderAllNum") + "个商品");

			TemplateVar keyword3 = new TemplateVar();
			// 商品数量
			keyword3.setValue(sTempMap.get("expressAllNum"));

			TemplateVar keyword4 = new TemplateVar();
			// 下单时间
			keyword4.setValue(sTempMap.get("validTime"));

			// 客户信息 联系人 以及 客户手机号
			TemplateVar keyword5 = new TemplateVar();
			String keyword5Str = sTempMap.get("customerInfo");
			String keyword6Str = sTempMap.get("logisticsName") + " " + sTempMap.get("logisticsNo");
			keyword5Str += "\r\n物流信息:" + keyword6Str + "\r\n";
			keyword5.setValue(keyword5Str);

			TemplateVar keyword6= new TemplateVar();
			//快递信息（快递公司名称+快递单号）
			keyword6.setValue(keyword6Str);

			TemplateVar remark = new TemplateVar();
			String remarkStr = getConfigStringByDefault("感谢您对医械购的支持与信任，如有疑问请联系：18651638763", SysOptionConstant.ID_WECHAT_TEMPLATE_REMARK);
			logger.info("sendTemplateMsgHcForShip | remark :{}", remarkStr);
			remark.setValue(remarkStr);

			reqTemp.setFirst(first);
			reqTemp.setKeyword1(keyword1);
			reqTemp.setKeyword2(keyword2);
			reqTemp.setKeyword3(keyword3);
			reqTemp.setKeyword4(keyword4);
			reqTemp.setKeyword5(keyword5);
			reqTemp.setKeyword6(keyword6);
			reqTemp.setRemark(remark);

			WeChatSendTemplateUtil.sendTemplateMsg(apiUrl + ApiUrlConstant.API_WECHAT_SEND_TEMPLATE_MSG, reqTemp);

			logger.info("sendTemplateMsgHcForShip | 医械购微信 发货 模板消息 | end .......");
			// add by franlin.wu for [微信推送 发货提醒] at 2019-06-19 end
		}
	}
	/**
	* @Description: 贝登发货提醒公众号
	* @Param: [saleorders, sTempMap]
	* @return: void
	* @Author: addis
	* @Date: 2019/9/25
	*/
    public void sendvxService(Saleorder saleorders, Map<String, String> sTempMap){
		sendTemplateVxService(saleorders,sTempMap);
	}

	/**
	* @Description: 贝登订单签收消息推送
	* @Param:
	* @return:
	* @Author: addis
	* @Date: 2019/9/25
	*/

	public void sendOrderForMsg(Saleorder saleorders, Map sTempMap) {
		sendOrderFor(saleorders, sTempMap);
	}

	/**
	 * @Description: 贝登发票发送消息推送
	 * @Param:
	 * @return:
	 * @Author: addis
	 * @Date: 2019/9/25
	 */

	public void wxSendMsgForInoice2(List<Invoice> invoList) {
		wxSendMsgForInoice(invoList);
	}

	/**
	* @Description:订单待确认
	* @Param: [saleorders, sTempMap]
	* @return: void
	* @Author: addis
	* @Date: 2019/9/26
	*/
	public void sendOrderConfirmedMsg(Saleorder saleorders, Map sTempMap){
		sendOrderConfirmed(saleorders,sTempMap);
	}
	
	/** 
	* @Description: 申请通过贝登消息推送 
	* @Param: [webAccount] 
	* @return: void 
	* @Author: addis
	* @Date: 2019/9/27 
	*/ 
	public Boolean passReminderMsg(WebAccount webAccount){
		Boolean flag=passReminder(webAccount);
	    return flag;
	}



}

