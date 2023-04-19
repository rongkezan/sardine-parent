package com.sardine.common.entity;

import com.sardine.common.constants.MdcConstants;
import org.slf4j.MDC;

import java.util.List;

/**
 * 结果集返回工具类
 *
 * @author keith
 */
public class Results {

    public static <T> ResultData<T> success() {
        return new ResultData<>(StateCodeEnum.SUCCESS.getCode(), true, StateCodeEnum.SUCCESS.getDesc(), MDC.get(MdcConstants.TRACE_ID), null);
    }

    public static <T> ResultData<T> success(String msg) {
        return new ResultData<>(StateCodeEnum.SUCCESS.getCode(), true, msg, MDC.get(MdcConstants.TRACE_ID), null);
    }

    public static <T> ResultData<T> success(T record) {
        return new ResultData<>(StateCodeEnum.SUCCESS.getCode(), true, StateCodeEnum.SUCCESS.getDesc(), MDC.get(MdcConstants.TRACE_ID), record);
    }

    public static <T> ResultData<T> success(String msg, T record) {
        return new ResultData<>(StateCodeEnum.SUCCESS.getCode(), true, msg, MDC.get(MdcConstants.TRACE_ID), record);
    }

    public static <T> ResultData<T> failed() {
        return new ResultData<>(StateCodeEnum.FAILED.getCode(), false, StateCodeEnum.FAILED.getDesc(), MDC.get(MdcConstants.TRACE_ID), null);
    }

    public static <T> ResultData<T> failed(String msg) {
        return new ResultData<>(StateCodeEnum.FAILED.getCode(), false, msg, MDC.get(MdcConstants.TRACE_ID), null);
    }

    public static <T> ResultData<T> failed(T record) {
        return new ResultData<>(StateCodeEnum.FAILED.getCode(), false, StateCodeEnum.FAILED.getDesc(), MDC.get(MdcConstants.TRACE_ID), record);
    }

    public static <T> ResultData<T> failed(String msg, T record) {
        return new ResultData<>(StateCodeEnum.FAILED.getCode(), false, msg, MDC.get(MdcConstants.TRACE_ID), record);
    }

    public static <T> ResultData<T> newResult(Integer code, Boolean success, String msg, T record) {
        return new ResultData<>(code, success, msg, MDC.get(MdcConstants.TRACE_ID), record);
    }

    public static <T> PageData<T> newPage(long total, long size, long current, List<T> records) {
        return new PageData<>(total, size, total % size == 0 ? total / size : (total / size) + 1, current, records);
    }

    public static <T> CursorData<T> newCursorData(int totalCount, List<T> records) {
        CursorData<T> cursorData = new CursorData<>();
        cursorData.setTotal(totalCount);
        cursorData.setRecords(records);
        return cursorData;
    }
}
