package com.sardine.common.utils.http;

import lombok.Data;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.Asserts;

import javax.net.ssl.SSLContext;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * http执行入口:
 * 在用户不指定自定义的{@link Config}的情况下，将启用默认配置
 *
 * @author keith
 */
public class HttpRequest {
    /**
     * 默认配置
     */
    private Config config = Config.getDefaultConfig();
    /**
     * 目前只有post和get
     */
    private HttpRequestBase httpMethod;
    /**
     * 方便拼接http请求的param
     */
    private URIBuilder uriBuilder;
    /**
     * cookie存储
     */
    private CookieStore cookieStore = new BasicCookieStore();
    /**
     * 设置contentType
     */
    private ContentType contentType = ContentType.create(URLEncodedUtils.CONTENT_TYPE, Consts.UTF_8);

    private HttpRequest(HttpRequestBase httpMethod, String uri) {
        this.httpMethod = httpMethod;
        try {
            this.uriBuilder = new URIBuilder(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get 请求入口
     */
    public static HttpRequest get(final String uri) {
        return new HttpRequest(new HttpGet(), uri);
    }

    /**
     * Post 请求入口
     */
    public static HttpRequest post(final String uri) {
        return new HttpRequest(new HttpPost(), uri);
    }

    /**
     * 配置HttpClient
     */
    public HttpRequest config(Config config) {
        Asserts.notNull(config, "config should not be null");
        this.config = config;
        return this;
    }

    /**
     * post提交的基础方法入口
     */
    private HttpRequest body(final HttpEntity entity) {
        if (this.httpMethod instanceof HttpEntityEnclosingRequest) {
            ((HttpEntityEnclosingRequest) this.httpMethod).setEntity(entity);
        } else {
            // get请求会被拒绝
            throw new IllegalStateException(this.httpMethod.getMethod() + " request cannot enclose an entity");
        }
        return this;
    }

    /**
     * 设置contentType
     */
    public HttpRequest contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * 设置contentType
     */
    public HttpRequest contentType(String contentType, String charset) {
        this.contentType = ContentType.create(contentType, Charset.forName(charset));
        return this;
    }

    /**
     * post提交表单的内容，表单格式
     */
    public HttpRequest bodyForm(final Iterable<? extends NameValuePair> formParams) {
        final List<NameValuePair> paramList = new ArrayList<>();
        for (NameValuePair param : formParams) {
            paramList.add(param);
        }
        final String s = URLEncodedUtils.format(paramList, contentType.getCharset().name());
        return bodyString(s, contentType);
    }

    /**
     * post请求的Map形式的body内容，表单格式
     */
    public HttpRequest bodyForm(Map<String, String> params) {
        // 设置请求参数
        List<NameValuePair> nvps = new ArrayList<>();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return bodyForm(nvps);
    }

    /**
     * post请求的body内容，json格式
     */
    public HttpRequest bodyJson(final String json) {
        return bodyString(json, contentType);
    }

    /**
     * post请求的body内容
     */
    private HttpRequest bodyString(final String s, final ContentType contentType) {
        final Charset charset = contentType != null ? contentType.getCharset() : null;
        final byte[] raw = charset != null ? s.getBytes(charset) : s.getBytes();
        return body(new ByteArrayEntity(raw, contentType));
    }

    /**
     * 添加http请求的头部信息
     */
    public HttpRequest addHeader(final String name, final String value) {
        this.httpMethod.addHeader(name, value);
        return this;
    }

    /**
     * 设置http请求的头部信息
     */
    public HttpRequest setHeader(final String name, final String value) {
        this.httpMethod.setHeader(name, value);
        return this;
    }

    /**
     * 添加http请求的头部信息
     */
    public HttpRequest addHeaders(final Map<String, String> headers) {
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            this.httpMethod.addHeader(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 设置http请求的头部信息
     */
    public HttpRequest setHeaders(final Map<String, String> headers) {
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            this.httpMethod.setHeader(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 添加http的请求参数
     */
    public HttpRequest addParam(String param, String value) {
        this.uriBuilder.addParameter(param, value);
        return this;
    }

    /**
     * 添加http的请求参数，如get参数
     */
    public HttpRequest addParams(final Map<String, String> params) {
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            this.uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 添加http请求时的cookie信息
     */
    public HttpRequest addCookie(String name, String value, String domain) {
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setDomain(domain);
        cookieStore.addCookie(cookie);
        return this;
    }

    public HttpRequest addCookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            cookieStore.addCookie(cookie);
        }
        return this;
    }

    /**
     * 在请求失败的情况下，重试的次数
     */
    public HttpRequest retryIfFailed(int retryTimes) {
        HttpRetryHandler httpRetryHandler = config.getRetryHandler();
        Asserts.notNull(httpRetryHandler, "retry handler was not provided");
        httpRetryHandler.accept(retryTimes);
        return this;
    }

    /**
     * 根据requestConfig，执行Http请求
     *
     * @return HttpResult 执行结果
     */
    public HttpResult execute() {
        HttpResponse httpResponse = null;
        try {
            httpMethod.setURI(uriBuilder.build());
            httpMethod.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                    .setConnectTimeout(config.getConnectionTimeout())
                    .setSocketTimeout(config.getSocketTimeout())
                    .build());
            HttpClient httpClient = HttpClientCache.get(config);
            
            HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(cookieStore);
            httpResponse = httpClient.execute(httpMethod, context);
            return new HttpResult(httpResponse, context, contentType.getCharset());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            config.getRetryHandler().remove();
            HttpClientUtils.closeQuietly(httpResponse);
        }
    }

    @Data
    public static class Config {

        /**
         * 全局唯一配置主键
         */
        private String key;

        /**
         * HttpClientConnection管理器
         */
        private HttpClientConnectionManager connectionManager;

        /**
         * SSL上下文
         */
        private SSLContext sslContext;

        /**
         * 重试处理器
         */
        private HttpRetryHandler retryHandler;

        /**
         * 指客户端和服务器建立连接的 timeout，
         * 就是 http 请求的三个阶段，一：建立连接；二：数据传送；三，断开连接。
         */
        private int connectionRequestTimeout = 30000;

        /**
         * 建立连接的超时时间
         */
        private int connectionTimeout = 5000;

        /**
         * 客户端和服务端进行数据交互的时间
         */
        private int socketTimeout = 25000;

        /**
         * 连接池最大连接数
         */
        private static final int MAX_TOTAL = 1000;

        /**
         * 连接池路由最大连接数
         */
        private static final int MAX_PRE_ROUTE = 400;

        /**
         * 默认配置key
         */
        private static final String DEFAULT_CONFIG_KEY = "2b08d3a958f0887adb47e315a3207de8";

        /**
         * 默认配置
         */
        private static final Config DEFAULT_CONFIG = defaultConfig();

        /**
         * 要保证Key全局唯一, 并缓存Config对象.
         */
        public Config(String key) {
            Asserts.notNull(key, "config key should not be null");
            Asserts.check(HttpClientCache.notExist(key), String.format("duplicated config key [%s] was found", key));
            this.key = key;
        }

        public static Config getDefaultConfig(){
            return DEFAULT_CONFIG;
        }

        private static Config defaultConfig() {
            Config config = new Config(DEFAULT_CONFIG_KEY);
            config.setConnectionManager(connectionPoolingManager());
            config.setSslContext(trustAll());
            // 线程安全的重试处理机制
            config.setRetryHandler(new HttpRetryHandler());
            return config;
        }

        /**
         * Http connection 连接池管理器
         * 并提供每5秒钟的自动的无效connection的清理机制
         *
         * @return PoolingHttpClientConnectionManager
         */
        private static PoolingHttpClientConnectionManager connectionPoolingManager() {
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", new SSLConnectionSocketFactory(trustAll()))
                    .build();

            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(MAX_TOTAL);
            connManager.setDefaultMaxPerRoute(MAX_PRE_ROUTE);
            connManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());

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

        /**
         * SSL配置：信任所有
         */
        private static SSLContext trustAll() {
            try {
                return new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

