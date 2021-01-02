package com.sardine.sms.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里短信配置属性
 *
 * @author <a href="rongkz@zjport.gov.cn">KeithRong</a>
 * @date 2020/7/21 9:55
 */
@Data
@Component
@ConfigurationProperties(prefix = "sardine.sms")
public class SmsProperties {

    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;
}
