package com.sardine.common.entity;

/**
 * The enum to correlate status code and message
 *
 * @author keith
 */
public enum StateCodeEnum implements StateCode {

    SUCCESS(200, "成功"),

    UNAUTHORIZED(401, "登录已失效，请重新登录"),

    FAILED(500, "网络错误"),
    ;

    private final Integer code;

    private final String desc;

    StateCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
