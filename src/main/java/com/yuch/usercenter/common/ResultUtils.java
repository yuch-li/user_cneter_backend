package com.yuch.usercenter.common;

import com.yuch.usercenter.enums.ErrorCode;

public class ResultUtils {

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(data, 20000, "success", "");
    }

//    public static BaseResponse error()
    public static BaseResponse error(ErrorCode error) {
        return new BaseResponse<>(null, error.getCode(), error.getMessage(), error.getDescription());
    }

    public static BaseResponse error(ErrorCode error, String description) {
        return new BaseResponse<>(null, error.getCode(), error.getMessage(), description);
    }

}
