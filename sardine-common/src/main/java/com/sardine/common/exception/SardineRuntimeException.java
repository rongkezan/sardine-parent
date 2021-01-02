package com.sardine.common.exception;

import com.sardine.common.entity.http.ResultCodeEnum;

/**
 * 自定义运行时异常类
 * @author keith
 */
public class SardineRuntimeException extends RuntimeException {

    public SardineRuntimeException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMessage());
    }

    public SardineRuntimeException(ResultCodeEnum resultCodeEnum, Throwable cause){
        super(resultCodeEnum.getMessage(), cause);
    }

    public SardineRuntimeException(String message){
        super(message);
    }

    public SardineRuntimeException(String message, Throwable cause){
        super(message, cause);
    }
}
