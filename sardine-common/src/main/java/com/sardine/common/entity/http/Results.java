package com.sardine.common.entity.http;

import com.sardine.common.constants.MdcConstants;
import org.slf4j.MDC;

/**
 * Http return value util.
 *
 * @author keith
 */
public class Results {

    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), MDC.get(MdcConstants.MDC_KEY_TRACE_ID), null);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), msg, MDC.get(MdcConstants.MDC_KEY_TRACE_ID), null);
    }

    public static <T> Result<T> success(T record) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), MDC.get(MdcConstants.MDC_KEY_TRACE_ID), record);
    }

    public static <T> Result<T> success(String msg, T record) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), msg, MDC.get(MdcConstants.MDC_KEY_TRACE_ID), record);
    }

    public static <T> Result<T> failed() {
        return new Result<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), MDC.get(MdcConstants.MDC_KEY_TRACE_ID), null);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<>(ResultCodeEnum.FAILED.getCode(), msg, MDC.get(MdcConstants.MDC_KEY_TRACE_ID), null);
    }

    public static <T> Result<T> failed(T record) {
        return new Result<>(ResultCodeEnum.FAILED.getCode(), ResultCodeEnum.FAILED.getMessage(), MDC.get(MdcConstants.MDC_KEY_TRACE_ID), record);
    }

    public static <T> Result<T> failed(String msg, T record) {
        return new Result<>(ResultCodeEnum.FAILED.getCode(), msg, MDC.get(MdcConstants.MDC_KEY_TRACE_ID), record);
    }

    public static <T> Result<T> newResult(ResultCodeEnum resultCode, String msg, T record) {
        return new Result<>(resultCode.getCode(), msg, MDC.get(MdcConstants.MDC_KEY_TRACE_ID), record);
    }
}
