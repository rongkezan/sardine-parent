package com.sardine.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用结果集
 *
 * @author keith
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8940366960899264819L;

    /** Status code */
    private int code;

    /** Message */
    private String msg;

    /** This id can help you track logs */
    private String traceId;

    /** Real data */
    private T record;
}
