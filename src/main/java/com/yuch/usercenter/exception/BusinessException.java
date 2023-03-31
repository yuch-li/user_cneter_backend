package com.yuch.usercenter.exception;

import com.yuch.usercenter.enums.ErrorCode;

/**
 * 自定义异常类
 */
public class BusinessException extends RuntimeException {
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.description = error.getDescription();
    }

    public BusinessException(ErrorCode error, String decs) {
        super(error.getMessage());
        this.code = error.getCode();
        this.description = decs;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
