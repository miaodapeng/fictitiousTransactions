package com.vedeng.common.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

public class CustomerHttpServletRequest extends HttpServletRequestWrapper {

    private Map<String,String[]> paramsMap;

    public CustomerHttpServletRequest(HttpServletRequest request,Map<String,String[]> paramsMap){
        super(request);
        this.paramsMap=paramsMap;
    }

    public String[] getParameterValues(String name) {
       return paramsMap.get(name);
    }

    public String getParameter(String name) {
        String[] v = paramsMap.get(name);
        if (v == null) {
            return null;
        } else {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                return strArr[0];
            } else {
                return null;
            }
        }
    }
}
