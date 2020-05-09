package com.vedeng.common.shiro;

import com.vedeng.authorization.dao.UserMapper;
import com.vedeng.authorization.model.Action;
import com.vedeng.authorization.model.Position;
import com.vedeng.authorization.model.Role;
import com.vedeng.authorization.model.User;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.Consts;
import com.vedeng.common.model.ResultJSON;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.validator.CustomSqlistAuthorityImpl;
import com.vedeng.ez.constants.CoookieConstants;
import com.vedeng.ez.utils.DoCookie;
import com.vedeng.ez.utils.StringUtil;
import com.vedeng.system.service.ActionService;
import com.vedeng.system.service.PositService;
import com.vedeng.system.service.RoleService;
import com.vedeng.system.service.UserService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * 自动登陆
 */
public class AuthorityFilter extends OncePerRequestFilter {
    private final Logger LOG = LoggerFactory.getLogger("login");
    // 不需要登录就可以访问的路径(比如:注册登录等)
    private String exclude = ".*(\\.(js|css|jpg|png|txt|gif|jpeg|ico|bmp|json));";
    private List<Pattern> excludePattern = new ArrayList<Pattern>(1);

    @Autowired
    private PositService positService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Value("${redis_dbtype}")
    protected String dbType;
    @Autowired
    private ActionService actionService;
    @Override
    public void initFilterBean() throws ServletException {
        exclude += "/batSyncToPhp.html;";
        exclude += "/nopower.do;";
        exclude += "/checkpreload.html;";
        exclude += "/(yxg/|mjx/).*;";
        exclude += "/dologin.do;";
        exclude += "/login.do;";
        exclude += "/logout.do;";
        exclude += "/code.do;";
        exclude += "/checkSession.do;";
//        exclude += "/index.do;";
//        exclude += "/menu.do;";
//        exclude += "/welcome.do;";
        exclude += "/selectorg.do;";
        exclude += "/changeorg.do;";
        exclude += "/savechangeorg.do;";
        exclude += "/saveselectorg.do;";
        exclude += "/fileUpload/ajaxFileUpload.do;";
        exclude += "/fileUpload/uploadFile2Oss.do;";
        exclude += "/fileUpload/ueditFileUpload.do;";
        exclude += "/order/adkorder/add.do;";
        exclude += "(/sqlist/).*;";
        exclude += "/system/message/getAllMessageNoread.do;";
        exclude += "/system/message/queryNoReadMsgNum.do;";
        exclude += "/phoneticTranscription/phonetic/viewContent.do;";
        exclude += "/phoneticTranscription/phonetic/addComments.do;";
        exclude += "/tradermsg/sendMsg/sendTraderMsg.do;";
        exclude += "/tradermsg/sendMsg/sendWebaccountCertificateMsg.do;";
        exclude += "/order/saleorder/saveBDAddSaleorder.do;";
        exclude += "/order/saleorder/updateBDSaleStatus.do;";
        exclude += "/goods/vgoods/viewSku.do;";
        exclude += "/goods/vgoods/viewSpu.do;";
        exclude += "/goods/static/vgoods/skuTip.do;";
        exclude += "/vgoods/operate/handleOldData.do;";
        exclude += "/trader/customer/saveMjxContactAdders.do;";
        exclude += "/warehouse/warehousesout/updateWarehouseProblem.do;";
        exclude += "/goods/vgoods/static/getCostPrice.do;";

        excludePattern.addAll(StringUtil.loadPattern(exclude).keySet());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        String originatingUrl = urlPathHelper.getOriginatingRequestUri(httpServletRequest);
        //其他请求不走此filter
        if (isStaticUrl(originatingUrl, httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String requestType = (String) httpServletRequest.getHeader("X-Requested-With");
        User sessionUser = SpringContextHolder.getSessionUser(httpServletRequest.getSession());
        if (sessionUser == null) {
            String serverName = httpServletRequest.getServerName();
            if (StringUtils.endsWith(serverName, "ivedeng.com")) {
                serverName = "ivedeng.com";
            }
            DoCookie cookie = new DoCookie(serverName, httpServletRequest, httpServletResponse);
            String idName = cookie.getDecodeCookie(CoookieConstants.IVEDENG_SID);
            try {
                if (StringUtils.isNotBlank(idName)) {
                    String[] idNameArray = StringUtils.split(idName, "@@");
                    User user = userService.getUserById(NumberUtils.toInt(idNameArray[0]));
                    if (user != null) {
                        user.setCurrentLoginSystem(1);
                        Set<String> roleset = new HashSet<String>();
                        List<Role> roles = roleService.getUserRoles(user);
                        if (roles != null && roles.size() > 0) {
                            for (Role r : roles) {
                                roleset.add(r.getRoleName());
                            }
                            JedisUtils.set(dbType + ErpConst.KEY_PREFIX_USER_ROLES + user.getUsername().toLowerCase()
                                    + user.getCompanyId(), JsonUtils.convertConllectionToJsonStr(roleset), 0);
                            user.setRoles(roles);
                        }
                        Set<String> permissionset = new HashSet<String>();
                        List<Action> list = actionService.getActionListByUserName(user);
                        if (list != null && list.size() > 0) {
                            for (Action ac : list) {
                                if(ac.getModuleName().startsWith("http")){
                                    permissionset.add(ac.getModuleName());
                                }else{
                                    permissionset.add("/" + ac.getModuleName() + "/" + ac.getControllerName() + "/"
                                            + ac.getActionName() + ".do");
                                }
                            }
                            String authString = JsonUtils.convertConllectionToJsonStr(permissionset);
                            JedisUtils.set(dbType + ErpConst.KEY_PREFIX_USER_PERMISSIONS + user.getUsername().toLowerCase()
                                    + user.getCompanyId(), authString, 0);
                        }

                        List<Position> positionList = positService.getPositionByUserId(user.getUserId());
                        if (null != positionList) {
                            // 多个部门
                            if (positionList.size() > 1) {
                                user.setPositions(positionList);
                                Integer positLevel = positionList.get(0).getLevel();
                                user.setPositLevel(positLevel);
                                SpringContextHolder.setSession(user,httpServletRequest.getSession());
                                httpServletResponse.sendRedirect("/selectorg.do");
                                return;
                            }
                            // 单个部门
                            if (positionList.size() == 1) {
                                Integer orgId = positionList.get(0).getOrgId();
                                Integer positType = positionList.get(0).getType();
                                Integer positLevel = positionList.get(0).getLevel();
                                String orgName = positionList.get(0).getOrgName();
                                user.setPositions(positionList);
                                user.setPositType(positType);
                                user.setPositLevel(positLevel);
                                user.setOrgId(orgId);
                                user.setOrgName(orgName);
                            }

                        }
                        SpringContextHolder.setSession(user,httpServletRequest.getSession());
                    }
                }
            }catch(Exception e){
                logger.error("",e);
            }
        }
        sessionUser = SpringContextHolder.getSessionUser(httpServletRequest.getSession());

        if (sessionUser == null) {

//            if (requestType != null && requestType.equals("XMLHttpRequest")) {
                httpServletResponse.sendRedirect("/login.do");
               // filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
        //    }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    private boolean isStaticUrl(String originatingUrl, HttpServletRequest httpRequest) {
        return StringUtil.match(excludePattern, originatingUrl);
    }

//
//	/**
//	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
//	 */
//	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
//
//
//		HttpServletRequest request = (HttpServletRequest) servletRequest;
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//
//		String requestType =(String)request.getHeader("X-Requested-With");
//		User user = (User) request.getSession().getAttribute(ErpConst.CURR_USER);
//
//		ServletContext sc = request.getSession().getServletContext();
//        XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
//        if(cxt != null && cxt.getBean("userService") != null && userService == null)
//        	userService = (UserService) cxt.getBean("userService");
//
//        String url = request.getContextPath()+request.getServletPath();
//        //给op用
//        if(url.contains("getgoodslistextrainfo")){
//			chain.doFilter(request, response);
//        	return;
//		}
//        //
//        if(!url.contains("login")&&!url.contains("logout")){
//        	JSONArray jsonArray = JSONArray.fromObject(JedisUtils.get(userService.getRedisDbType()+ErpConst.KEY_PREFIX_USER_PERMISSIONS+user.getUsername()));
//         	List<String> permissionList = (List<String>) JSONArray.toCollection(jsonArray, String.class);
//			if(!permissionList.contains(url.substring(4))){
//				 /**判断是否是ajax提交方式*/
//		        if (requestType != null && requestType.equals("XMLHttpRequest")) {
//		        	//向http头添加 状态 sessionstatus
//		        	response.setHeader("permissionstatus","nopermission");//没有权限
//		        	response.setStatus(1001);//没有权限的状态码
//                    //向http头添加登录的url
////		        	response.addHeader("loginPath", ctxpath);
////                    chain.doFilter(request, response);
//					response.getWriter().write(JsonUtils.translateToJson(ResultJSON.failed().message("没有权限").code(1001)));
//                    return ;
//		        }else{
//		        //	request.getRequestDispatcher("/common/nopower.jsp").forward(request,response);
//		        	response.sendRedirect("/erp/jsp/common/nopower.jsp");
//		        	chain.doFilter(request, response);
//		        	return;
//		        }
//			}
//        }
//		chain.doFilter(request, response);
//	}

}
