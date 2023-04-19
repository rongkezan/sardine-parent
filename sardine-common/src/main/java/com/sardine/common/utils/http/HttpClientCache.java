package com.sardine.common.utils.http;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.Asserts;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpClient缓存: 使用相同key的HttpClient将直接从缓存取，在缓存中若未找到相应的key，则会新建并加入缓存
 *
 * key: Config.getKey()
 * value: HttpClient
 *
 * @author keith
 */
public class HttpClientCache {

    private final static Map<String, CloseableHttpClient> HTTP_CLIENT_CACHE = new HashMap<>();

    /**
     * HttpClient对象缓存
     *
     * @param config HttpClient的配置
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient get(HttpRequest.Config config) {
        Asserts.notNull(config, "config object is null");
        Asserts.notNull(config.getKey(), "config.key is null");

        if (notExist(config.getKey())) {
            synchronized (HttpClientCache.class) {
                if (notExist(config.getKey())) {
                    CloseableHttpClient httpClient = HttpClients.custom()
                            .setRetryHandler(config.getRetryHandler())
                            .setConnectionManager(config.getConnectionManager())
                            .setSSLSocketFactory(new SSLConnectionSocketFactory(config.getSslContext()))
                            .build();
                    HTTP_CLIENT_CACHE.put(config.getKey(), httpClient);
                }
            }
        }

        return HTTP_CLIENT_CACHE.get(config.getKey());
    }

    /**
     * 查看Config的key是否已经存在
     *
     * @param key 缓存key
     * @return 是否存在
     */
    public static boolean notExist(String key) {
        return !HTTP_CLIENT_CACHE.containsKey(key);
    }
}