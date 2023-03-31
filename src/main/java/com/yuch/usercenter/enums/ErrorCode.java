package com.yuch.usercenter.enums;

/**
 *  自定义错误码
 */
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误", ""),
    SYSTEM_ERROR(50000, "系统异常", ""),
    NOT_ADMIN(40300, "没有管理员权限", ""),
    USER_EXIST(40301, "用户名已存在",""),
    NO_LOGIN(40302, "用户未登录", ""),
    USERNAME_NOT_EXIST(40303, "用户名不存在", ""),
    PASSWORD_ERROR(40304, "密码错误", "");

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    private final int code;
    private final String message;
    private final String description;
}
