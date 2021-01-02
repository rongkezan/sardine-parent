package com.sardine.sms.service;

/**
 * 短信服务
 */
public interface SmsService {
    /**
     * 发送短信
     * @param signName
     * @param templateCode
     * @param phoneNumber
     * @param templateParam
     */
    void sendSms(String signName, String templateCode, String phoneNumber, String templateParam);
}
