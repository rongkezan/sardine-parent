package com.sardine.sms.mq;

import com.sardine.common.constants.MqConstants;
import com.sardine.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * MQ短信接收
 */
@Slf4j
@Component
public class SmsListener {

    @Resource
    SmsService smsService;

    @RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(name = MqConstants.QUEUE_PHONE_CODE_USER_SMS),
                exchange = @Exchange(name = MqConstants.EXCHANGE_PHONE_CODE_USER_SMS, type = ExchangeTypes.TOPIC),
                key = {MqConstants.ROUTE_PHONE_CODE_USER_SMS}
            )
    )
    public void listenMsg(String phone) {
        if(StringUtils.isBlank(phone))
            return;
        //发送短信验证码
        smsService.sendSms(phone);
        log.info("[短信服务] 发送短信验证码，手机号{}", phone);
    }
}
