/**
 * Copyright © 2020 Renrenbit All Rights Reserved
 */
package com.sardine.gateway.door;

import lombok.Data;
import org.redisson.api.RateType;

/**
 * Created by chenjh on 2020/9/29.
 * <p>
 * 限流规则
 */
@Data
public class ApiLimiterConfig {
    /** api */
    private String api;
    /** 限流方式，OVERALL：多节点总限流，PER_CLIENT: 每个单节点限流 */
    private RateType rateType;
    /** 令牌数 */
    private Long rate;
    /** 投放间隔，默认毫秒 */
    private Long interval;
    /** 可以针对用户下某API精准限流 */
    private String userId;

}
