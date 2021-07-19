package com.sardine.common.httpclient;

import lombok.Data;
import org.apache.http.conn.HttpClientConnectionManager;

import javax.net.ssl.SSLContext;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 在配置HttpClient时的相关配置
 *
 * @author keith
 */
@Data
public class Config {
    /**
     * 必要字段不能为空，要保证全局唯一
     */
    private String key;
    /**
     * http connection相关的配置
     */
    private HttpClientConnectionManager connectionManager;
    /**
     * ssl的相关配置
     */
    private SSLContext sslContext;
    /**
     * 要保证Key全局唯一, 并缓存Config对象.
     */
    public Config(String key) {
        checkNotNull(key, "config key should not be null");
        checkArgument(!HttpClientHolder.isExist(key), String.format("duplicated config key [%s] was found", key));
        this.key = key;
    }
}
