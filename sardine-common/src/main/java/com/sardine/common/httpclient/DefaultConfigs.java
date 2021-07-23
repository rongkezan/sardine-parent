package com.sardine.common.httpclient;

import com.sardine.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author keith
 */
@Slf4j
public class DefaultConfigs {

    private final static String DEFAULT_CONFIG_KEY = "2b08d3a958f0887adb47e315a3207de8";

    private final static Config DEFAULT_CONFIG = createSimpleConfig(DEFAULT_CONFIG_KEY);

    /**
     * Http connection的pool管理器
     * 并提供每5秒钟的自动的无效connection的清理机制
     *
     * @return PoolingHttpClientConnectionManager
     */
    public static PoolingHttpClientConnectionManager simpleConnectionPooling() {
        SSLConnectionSocketFactory sf;
        try {
            sf = new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build());
        } catch (Exception e) {
            throw SystemException.of("CreateSimpleConfig Error", e);
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sf)
                .build();

        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        // 最大连接数
        connManager.setMaxTotal(1000);
        // 路由最大连接数
        connManager.setDefaultMaxPerRoute(400);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connManager.setDefaultSocketConfig(socketConfig);
        // 5s清理一次过时的connection
        new Timer(true).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                connManager.closeExpiredConnections();
                connManager.closeIdleConnections(30, TimeUnit.SECONDS);
            }
        }, new Date(), TimeUnit.SECONDS.toMillis(5));

        return connManager;
    }

    public static Config createSimpleConfig(String key) {
        try {
            Config config = new Config(key);
            config.setConnectionManager(simpleConnectionPooling());
            config.setSslContext(new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build());
            return config;
        } catch (Exception e) {
            throw SystemException.of("CreateSimpleConfig Error", e);
        }
    }

    public static Config get() {
        return DEFAULT_CONFIG;
    }
}
