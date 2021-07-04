package com.sardine.common.exception;

import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * 业务异常
 *
 * @author keith
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6372509565885529200L;

    private int code;

    public BusinessException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public static BusinessException of(String msg) {
        return new BusinessException(-50000, msg);
    }

    public static BusinessException of(String msg, Throwable cause) {
        return new BusinessException(-50000, msg, cause);
    }

    public static BusinessException of(String msg, int code) {
        return new BusinessException(code, msg);
    }

    public static BusinessException of(String msg, int code, Throwable cause) {
        return new BusinessException(code, msg, cause);
    }

    public static BusinessException of(String msg, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new BusinessException(-50000, parameterizedMessage.getFormattedMessage());
    }

    public static BusinessException of(String msg, Throwable cause, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new BusinessException(-50000, parameterizedMessage.getFormattedMessage(), cause);
    }

    public static BusinessException of(String msg, int code, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new BusinessException(code, parameterizedMessage.getFormattedMessage());
    }

    public static BusinessException of(String msg, int code, Throwable cause, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new BusinessException(code, parameterizedMessage.getFormattedMessage(), cause);
    }

    public int getCode() {
        return code;
    }
}
