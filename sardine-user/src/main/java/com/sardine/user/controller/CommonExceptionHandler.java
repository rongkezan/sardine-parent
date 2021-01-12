package com.sardine.user.controller;

import com.sardine.common.entity.http.CommonResult;
import com.sardine.common.exception.SardineRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用异常处理类
 */
@Slf4j
@RestController
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(SardineRuntimeException.class)
    public CommonResult<Long> handleException(SardineRuntimeException e) {
        Long timestamp = System.currentTimeMillis();
        log.error("[Controller异常信息捕获], 时间戳: {}", timestamp, e);
        return CommonResult.failed(e.getMessage());
    }
}
