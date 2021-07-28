package com.sardine.utils.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum to correlate status code and message
 *
 * @author keith
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 入参异常
     */
    WRONG_ARGUMENT(400, "入参异常"),
    /**
     * 未授权
     */
    NO_AUTHORIZATION(401, "未授权"),
    /**
     * 内部错误
     */
    FAILED(500, "内部错误");

    private final int code;
    private final String message;
}
