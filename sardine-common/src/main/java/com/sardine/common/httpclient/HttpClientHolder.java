package com.sardine.common.httpclient;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;

import static lombok.Lombok.checkNotNull;

/**
 * HttpClient的持有和维护类，内部会维护一个map根据具体传入的Config.key来关联
 *
 * @author keith
 */
public class HttpClientHolder {

    private static final Map<String, CloseableHttpClient> CLIENT_CACHE = new HashMap<>();

    /**
     * HttpClient对象的缓存管理
     *
     * @param config HttpClient的配置
     * @return HttpClient
     */
    public static CloseableHttpClient get(Config config) {
        checkNotNull(config, "config object is null");
        checkNotNull(config.getKey(), "config.key is null");
        if (!CLIENT_CACHE.containsKey(config.getKey())) {
            synchronized (HttpClientHolder.class) {
                if (!isExist(config.getKey())) {
                    CloseableHttpClient httpClient = createHttpClient(config);
                    CLIENT_CACHE.put(config.getKey(), httpClient);
                }
            }
        }
        return CLIENT_CACHE.get(config.getKey());
    }

    private static CloseableHttpClient createHttpClient(Config config) {
        return HttpClients.custom()
                .setConnectionManager(config.getConnectionManager())
                .setSSLSocketFactory(new SSLConnectionSocketFactory(config.getSslContext()))
                .build();
    }

    /**
     * 查看Config的key是否已经存在
     *
     * @param key
     * @return true 存在，false 不存在
     */
    public static boolean isExist(String key) {
        return CLIENT_CACHE.containsKey(key);
    }
}