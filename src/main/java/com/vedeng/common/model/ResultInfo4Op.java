package com.vedeng.common.model;

/**
 * @Description: 响应结果
 * @author: hugo
 * @Date: 2020-01-09 14:20
 */
public class ResultInfo4Op {

    private String code = "success";

    private Object data = null;

    private String message;

    private boolean success = true;

    public ResultInfo4Op(String code, Object data, String message, boolean success) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public ResultInfo4Op(Object data) {
        this.data = data;
    }

    public ResultInfo4Op() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

