package com.sardine.common.entity;

import java.io.Serializable;

/**
 * Common return value of single record
 *
 * @author keith
 */
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = -8940366960899264819L;

    /**
     * status code
     */
    private Integer code;

    /**
     * success
     */
    private Boolean success;

    /**
     * message
     */
    private String msg;

    /**
     * track logs according this id.
     */
    private String traceId;

    /**
     * data record
     */
    private T data;

    public ResultData() {

    }

    public ResultData(Integer code, Boolean success, String msg, String traceId, T data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.traceId = traceId;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
