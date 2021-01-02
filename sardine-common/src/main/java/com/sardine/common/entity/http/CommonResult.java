package com.sardine.common.entity.http;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 */
@Data
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = -8940366960899264819L;

    private Integer code;
    private String msg;
    private String traceId;
    private T data;

    public CommonResult() {

    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> success(){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(String msg){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, null);
    }

    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResult<T> success(String msg, T data){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> CommonResult<T> failed(){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(String msg){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> CommonResult<T> failed(T data){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), data);
    }

    public static <T> CommonResult<T> failed(String msg, T data){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), msg, data);
    }
}
