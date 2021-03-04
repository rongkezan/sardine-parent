package com.sardine.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RocketMqProducerConfig {

    @Value("${rocketMq.producer.groupName}")
    private String groupName;

    @Value("${rocketMq.producer.nameSrvAddr}")
    private String nameSrvAddr;

    @Bean
    public DefaultMQProducer getRocketMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(this.nameSrvAddr);
        try {
            producer.start();
        } catch (MQClientException e) {
            log.error("RocketMQ start failed. cause:{}", e.getMessage(), e);
        }
        return producer;
    }
}
