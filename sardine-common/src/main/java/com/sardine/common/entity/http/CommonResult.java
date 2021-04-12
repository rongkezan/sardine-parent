package com.sardine.common.entity.http;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Common return value of single record
 *
 * @author keith
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@SuppressWarnings("unused")
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -8940366960899264819L;

    /** Status code */
    private int code;

    /** Message */
    private String msg;

    /** This id can help you track logs */
    private String traceId;

    /** Real data */
    private T record;

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
