package com.vedeng.common.shiro;

import com.alibaba.fastjson.JSON;
import com.vedeng.ez.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    private final Logger LOG = LoggerFactory.getLogger("visitor");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //给op用
        if (!httpRequest.getRequestURI().contains("doLogin")
                && !httpRequest.getRequestURI().contains("adkorder")

                && !httpRequest.getRequestURI().contains("newerp.vedeng.com")
                && !httpRequest.getRequestURI().contains("checkSession")
        ) {
            long start = System.currentTimeMillis();
            String param = "";
            try {
                filterChain.doFilter(httpRequest, response);
                param = JSON.toJSONString(((HttpServletRequest) servletRequest).getParameterMap());
            } finally {
                LOG.info("erp:::end：" + (System.currentTimeMillis() - start) + "ms \t" + ((HttpServletRequest) servletRequest).getRequestURL() + "\t" +
                        param + "\t" + IpUtils.getRealIp(((HttpServletRequest) servletRequest))
                );
            }
            return;
        }
        String originHeader = httpRequest.getHeader("Origin");
        if (null != originHeader && (originHeader.contains(".ivedeng.com")
                || originHeader.contains(".vedeng.com") || originHeader.contains(".vedeng.com.cn"))) {
            response.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
            // 服务器端 Access-Control-Allow-Credentials = true时，参数Access-Control-Allow-Origin 的值不能为 '*'
            response.setHeader("Access-Control-Allow-Credentials", "true");
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,version,gomanager,token,content-type,formtoken,source");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,X-Requested-With,version,gomanager,token,formtoken,source");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(httpRequest, response);
        }
    }

    @Override
    public void destroy() {

    }
}
