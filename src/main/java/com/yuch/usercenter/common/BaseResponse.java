package com.yuch.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类<p/>
 * 对响应的数据进行包装，包含数据、状态码、状态码说明、详细说明
 * @param <T> 需要包装的数据的类型
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 需要包装的数据
     */
    private T data;

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态码描述
     */
    private String message;

    /**
     * 详细描述
     */
    private String description;

    public BaseResponse(T data, int code, String message, String description) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
