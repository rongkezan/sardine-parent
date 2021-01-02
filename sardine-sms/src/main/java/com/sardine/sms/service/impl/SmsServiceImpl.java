package com.sardine.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sardine.common.constants.RedisConstants;
import com.sardine.sms.prop.SmsProperties;
import com.sardine.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 短信服务
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    SmsProperties smsProperties;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /* 最小发送时间间隔(ms) */
    private static final long MIN_INTERVAL = 30000;

    @Override
    public void sendSms(String signName, String templateCode, String phoneNumber, String templateParam) {
        String key = RedisConstants.SMS_PHONE + phoneNumber;

        //按照手机号码限流
        String lastTime = stringRedisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(lastTime)){
            long last = Long.parseLong(lastTime);
            if(System.currentTimeMillis() - last < MIN_INTERVAL){
                log.info("[短信服务] 发送短信频率过高，被阻止，手机号码：{}", phoneNumber);
                return;
            }
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("收到短信回执: {}", response);
            //发送短信成功后写入redis，指定生存时间为1分钟
            stringRedisTemplate.opsForValue().set(key, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES);
        } catch (ClientException e) {
            log.error("[短信服务] 发送短信失败，手机号：{}", phoneNumber, e);
        }
    }
}
