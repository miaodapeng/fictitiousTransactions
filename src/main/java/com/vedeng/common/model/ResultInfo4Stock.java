package com.vedeng.common.model;

/**
 * @Author: Hugo
 * @param <T>
 * @Data: 2019 12/11
 */
public class ResultInfo4Stock<T> {
    /**
     * 请求是否成功
     */
    private String code;

    /**
     * 返回数据集
     */
    private T data;

    /**
     * 接口返回信息
     */
    private String message;

    public ResultInfo4Stock() {
    }

    public ResultInfo4Stock(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultInfo4Stock{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
