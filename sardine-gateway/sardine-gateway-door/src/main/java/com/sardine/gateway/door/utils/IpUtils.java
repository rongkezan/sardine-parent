package com.sardine.gateway.door.utils;

import com.google.common.net.InetAddresses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public final class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

    public static final String LOCALHOST = "127.0.0.1";

    public static final String ANYHOST = "0.0.0.0";

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");

    /**
     * 获取http请求来源真实IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }

        // convert ip v6 to ip v4
        if (StringUtils.equals(ip, "0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }


    /**
     * ipv4地址是否合法
     *
     * @param addr ipv4地址
     * @return
     */
    public static boolean checkIpv4(String addr) {
        return InetAddresses.isInetAddress(addr);
    }


    /**
     * 是否是本地host，如localhost/127.0.0.1
     *
     * @param host
     * @return
     */
    public static boolean isLocalHost(String host) {
        return host != null
                && (LOCAL_IP_PATTERN.matcher(host).matches()
                || host.equalsIgnoreCase("localhost"));
    }

    public static boolean isCurrentMachineIp(String ip) {
        List<InetAddress> addressList = IpUtils.getLocalAddressList();
        for (InetAddress address : addressList) {
            if (address.getHostAddress().equals(ip)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否任意host
     *
     * @param host
     * @return
     */
    public static boolean isAnyHost(String host) {
        return "0.0.0.0".equals(host);
    }


    /**
     * 查询所有本机ip，不包含127.0.0.1
     *
     * @return
     */
    public static List<InetAddress> getLocalAddressList() {
        List<InetAddress> inetAddressList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            try {
                                InetAddress address = addresses.nextElement();
                                if (isValidAddress(address)) {
                                    inetAddressList.add(address);
                                }
                            } catch (Throwable e) {
                                logger.warn("Failed to retriving ip address, " + e.getMessage(), e);
                            }
                        }
                    } catch (Throwable e) {
                        logger.warn("Failed to retriving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            logger.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }
        return inetAddressList;
    }


    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
            return false;
        String name = address.getHostAddress();
        return (name != null
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }
}
