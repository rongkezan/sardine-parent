package com.sardine.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IpUtils {

    public static String getIp(HttpServletRequest request) {
        Asserts.notNull(request, "getIpAddr method HttpServletRequest Object is null");
        String ipStr = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ipStr) || "unknown".equalsIgnoreCase(ipStr)) {
            ipStr = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipStr) || "unknown".equalsIgnoreCase(ipStr)) {
            ipStr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipStr) || "unknown".equalsIgnoreCase(ipStr)) {
            ipStr = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipStr.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipStr = str;
                break;
            }
        }
        //目的是将localhost访问对应的ip 0:0:0:0:0:0:0:1 转成 127.0.0.1。
        return ipStr.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ipStr;
    }
}
