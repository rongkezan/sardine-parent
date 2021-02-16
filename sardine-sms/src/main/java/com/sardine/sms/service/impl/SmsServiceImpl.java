package com.sardine.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sardine.common.constants.RedisConstants;
import com.sardine.common.util.JacksonUtils;
import com.sardine.sms.prop.AliyunSmsProperties;
import com.sardine.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private AliyunSmsProperties aliyunSmsProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /* 最小发送时间间隔(ms) */
    private static final long MIN_INTERVAL = 30000;

    private static final String SYS_DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String SYS_VERSION = "2017-05-25";

    private static final String SYS_ACTION = "SendSms";

    private static final String REGION_ID = "cn-hangzhou";

    private static final String SIGN_NAME = "乐音";

    @Override
    public void sendSms(String phone) {
        String codeValueKey = RedisConstants.USER_PHONE_CODE_VALUE + phone;
        String codeTimeKey =RedisConstants.USER_PHONE_CODE_TIMESTAMP + phone;

        // 按照手机号码限流
        String lastTime = stringRedisTemplate.opsForValue().get(codeTimeKey);
        if (!StringUtils.isEmpty(lastTime)) {
            long last = Long.parseLong(lastTime);
            if (System.currentTimeMillis() - last < MIN_INTERVAL) {
                log.info("[短信服务] 发送短信频率过高，被阻止，手机号码：{}", phone);
                return;
            }
        }

        // 构造短信模板Json
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        String templateParam = JacksonUtils.toJson(Collections.singletonMap("code", code));

        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID,
                aliyunSmsProperties.getAccessKeyId(), aliyunSmsProperties.getAccessKeySecret());

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SYS_DOMAIN);
        request.setSysVersion(SYS_VERSION);
        request.setSysAction(SYS_ACTION);
        request.putQueryParameter("RegionId", REGION_ID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("TemplateCode", aliyunSmsProperties.getTemplateCode());
        request.putQueryParameter("TemplateParam", templateParam);

        try {
            IAcsClient client = new DefaultAcsClient(profile);
            CommonResponse response = client.getCommonResponse(request);
            stringRedisTemplate.opsForValue().set(codeValueKey, code, 1, TimeUnit.MINUTES);
            stringRedisTemplate.opsForValue().set(codeTimeKey, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.MINUTES);
            log.info(response.getData());
        } catch (ClientException e) {
            log.error("短信发送异常", e);
        }
    }
}
