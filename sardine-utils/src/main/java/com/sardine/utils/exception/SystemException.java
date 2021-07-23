package com.sardine.utils.exception;

import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * 系统异常
 *
 * @author keith
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -7194118031124667151L;

    private int code;

    public SystemException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public SystemException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public static SystemException of(String msg) {
        return new SystemException(-50000, msg);
    }

    public static SystemException of(String msg, Throwable cause) {
        return new SystemException(-50000, msg, cause);
    }

    public static SystemException of(String msg, int code) {
        return new SystemException(code, msg);
    }

    public static SystemException of(String msg, int code, Throwable cause) {
        return new SystemException(code, msg, cause);
    }

    public static SystemException of(String msg, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new SystemException(-50000, parameterizedMessage.getFormattedMessage());
    }

    public static SystemException of(String msg, Throwable cause, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new SystemException(-50000, parameterizedMessage.getFormattedMessage(), cause);
    }

    public static SystemException of(String msg, int code, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new SystemException(code, parameterizedMessage.getFormattedMessage());
    }

    public static SystemException of(String msg, int code, Throwable cause, Object... args) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(msg, args);
        return new SystemException(code, parameterizedMessage.getFormattedMessage(), cause);
    }

    public int getCode() {
        return code;
    }
}
