package com.sardine.gateway.door.config;

import lombok.Data;
import org.redisson.api.RateType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author keith
 */
@Data
@ConfigurationProperties(prefix = "sardine")
public class LimiterConfigProperties {

    private List<LimitRule> limitRules;

    @Data
    public static class LimitRule {
        /**
         * url
         */
        private String url;
        /**
         * Type of Limiting
         *
         * @see RateType
         */
        private RateType rateType;
        /**
         * Limit Token Count
         */
        private Long tokenCount;
        /**
         * Create Token
         */
        private Long interval;
        /**
         * 可以针对用户下某API精准限流
         */
        private String userId;
    }
}
