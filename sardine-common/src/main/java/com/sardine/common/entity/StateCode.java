package com.sardine.common.entity;

/**
 * Http返回结果接口
 *
 * @author keith
 */
public interface StateCode {

    /**
     * 获取返回状态码
     *
     * @return 返回状态码
     */
    Integer getCode();

    /**
     * 获取返回描述
     *
     * @return 返回中文描述
     */
    String getDesc();
}
