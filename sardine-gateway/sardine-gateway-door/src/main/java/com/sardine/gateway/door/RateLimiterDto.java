package com.sardine.gateway.door;

import lombok.Data;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author keith
 */
@Data
public class RateLimiterDto {

    private String userId;

    private String api;

    private String ip;
}
