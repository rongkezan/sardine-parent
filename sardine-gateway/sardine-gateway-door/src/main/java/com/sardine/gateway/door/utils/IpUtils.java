package com.sardine.gateway.door.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;

/**
 * @author keith
 */
@Slf4j
public final class IpUtils {

    private static final String COMMA = ",";

    private static final String UNKNOWN = "unknown";

    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (!notFound(ip) && ip.contains(COMMA)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = ip.split(COMMA)[0];
        }
        if (notFound(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (notFound(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (notFound(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (notFound(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (notFound(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (notFound(ip)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            if (remoteAddress != null) {
                ip = remoteAddress.getAddress().getHostAddress();
            }
        }
        return ip;
    }

    private static boolean notFound(String ip){
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }
}
