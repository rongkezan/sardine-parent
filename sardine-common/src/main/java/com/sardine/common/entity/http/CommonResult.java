package com.sardine.common.entity.http;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 */
@Data
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = -8940366960899264819L;

    /* 返回码 */
    private int code;

    /* 返回信息 */
    private String msg;

    /* 追踪码 */
    private String traceId;

    /* 记录 */
    private T record;

    public CommonResult() {

    }

    public CommonResult(int code, String msg, T record) {
        this.code = code;
        this.msg = msg;
        this.record = record;
    }

    public static <T> CommonResult<T> success(){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> CommonResult<T> success(String msg){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, null);
    }

    public static <T> CommonResult<T> success(T record){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), record);
    }

    public static <T> CommonResult<T> success(String msg, T record){
        return new CommonResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, record);
    }

    public static <T> CommonResult<T> failed(){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), null);
    }

    public static <T> CommonResult<T> failed(String msg){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> CommonResult<T> failed(T record){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), record);
    }

    public static <T> CommonResult<T> failed(String msg, T record){
        return new CommonResult<>(ResultCodeEnum.FAILED.getCode(), msg, record);
    }
}
