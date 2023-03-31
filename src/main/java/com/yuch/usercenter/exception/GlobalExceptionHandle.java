package com.yuch.usercenter.exception;

import com.yuch.usercenter.common.BaseResponse;
import com.yuch.usercenter.common.ResultUtils;
import com.yuch.usercenter.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，捕获相应的异常进行处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandle(BusinessException e) {
        //打印日志
      log.error("businessException: " + e.getMessage(), e);
      //封装错误信息返回给前端
      return new BaseResponse(null, e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException" + e.getMessage(), e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "");
    }
}
