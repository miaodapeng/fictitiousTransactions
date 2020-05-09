package com.smallhospital.dto;

/**
 * 校验结果
 */
public class ValidatorResult {

    /**
     * 校验结果
     */
    public Boolean result = false;

    /**
     * 返回消息
     */
    public String message;


    public static ValidatorResult newBuild(){
        ValidatorResult result = new ValidatorResult();
        return result;
    }

    public Boolean getResult() {
        return result;
    }

    public ValidatorResult setResult(Boolean result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ValidatorResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
