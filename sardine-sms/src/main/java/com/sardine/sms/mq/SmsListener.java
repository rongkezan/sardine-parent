package com.sardine.sms.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sardine.sms.prop.SmsProperties;
import com.sardine.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * MQ短信接收
 */
@Slf4j
@Component
public class SmsListener {

    @Resource
    SmsService smsService;

    @Resource
    SmsProperties smsProperties;

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(name = "sms.verify.code.queue", durable = "true"),
                exchange = @Exchange(name = "sardine.sms.exchange", type = ExchangeTypes.TOPIC),
                key = {"sms.verify.code"}
            )
    )
    public void ListenInsertOrUpdate(Map<String, String> msg) throws JsonProcessingException {
        if(CollectionUtils.isEmpty(msg)) return;
        String phone = msg.remove("phone");
        if(StringUtils.isEmpty(phone)) return;
        //发送短信验证码
        ObjectMapper objectMapper = new ObjectMapper();
        smsService.sendSms(smsProperties.getSignName(), smsProperties.getVerifyCodeTemplate(),
                phone, objectMapper.writeValueAsString(msg));
        log.info("[短信服务] 发送短信验证码，手机号{}", phone);
    }
}
