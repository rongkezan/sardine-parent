package com.sardine.common.entity.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Common return value of page
 *
 * @author keith
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -8940366960899264820L;

    /** Status code */
    private int code;

    /** Message */
    private String msg;

    /** Total record count */
    private int total;

    /** Total record page */
    private int totalPage;

    /** This id can help you track logs */
    private String traceId;

    /** Real data */
    private List<T> records;

    public static <T> PageResult<T> success(String msg, int total, int totalPage, String traceId, List<T> records){
        return new PageResult<>(ResultCodeEnum.SUCCESS.getCode(), msg, total, totalPage, traceId, records);
    }

    public static <T> PageResult<T> failed(String msg, int total, int totalPage, String traceId, List<T> records){
        return new PageResult<>(ResultCodeEnum.FAILED.getCode(), msg, total, totalPage, traceId, records);
    }
}
