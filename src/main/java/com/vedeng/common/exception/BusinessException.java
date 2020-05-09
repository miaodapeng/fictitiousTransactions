package com.vedeng.common.exception;

import com.alibaba.druid.wall.violation.ErrorCode;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3425545575477750228L;

    private String messageCode;
    private ErrorCode errorCode;

    public BusinessException(String messageCode) {
        this.messageCode = messageCode;
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessageCode() {
        return messageCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
