package com.sardine.common.entity.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回状态枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    WRONG_ARGUMENT(400, "入参异常"),
    NO_AUTHORIZATION(401, "未授权"),
    FAILED(500, "内部错误");

    private Integer code;
    private String message;
}
