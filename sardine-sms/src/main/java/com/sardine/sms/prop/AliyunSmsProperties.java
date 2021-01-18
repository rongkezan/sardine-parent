package com.sardine.sms.prop;

import com.sardine.common.properties.AliyunProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里短信配置属性
 */
@Data
@Component
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "sardine.aliyun.sms")
public class AliyunSmsProperties extends AliyunProperties {

    private String signName;

    private String templateCode;
}
