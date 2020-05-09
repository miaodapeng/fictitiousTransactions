
package com.vedeng.common.model;

import java.io.Serializable;

import com.vedeng.common.page.Page;

/**
 * <b>Description:请求参数</b><br> 
 * <b>Author: Franlin</b> 
 * @fileName ReqVo.java
 * <br><b>Date: 2018年9月28日 下午7:53:56 </b> 
 *
 */
public class ReqVo<T> implements Serializable {
    
    
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2258811614961280269L;
    
    private T req;
    private Page page;
    
    
    public T getReq() {
        return req;
    }
    public void setReq(T req) {
        this.req = req;
    }
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
    
}

