package com.sardine.sms.service;

/**
 * 短信服务
 */
public interface SmsService {
    /**
     * 发送短信
     * @param phone 手机号
     */
    void sendSms(String phone);
}
