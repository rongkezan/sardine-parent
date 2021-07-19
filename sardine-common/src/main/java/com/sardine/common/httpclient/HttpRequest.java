package com.sardine.common.httpclient;

import com.google.common.collect.Lists;
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
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * <pre>
 *   http执行入口:
 *   在用户不指定自定义的{@link Config}的情况下，将启用默认配置
 *   设置如下(具体详见{@link DefaultConfigs}):
 *   <ul>
 *      <li>最大连接数500</li>
 *      <li>每个Route的默认最大连接数200</li>
 *      <li>从pool中获取连接最多等待时间为2s</li>
 *      <li>连接超时时间为1.5s</li>
 *      <li>数据返回超时时间为5s</li>
 *      <li>SSL默认设置为信任所有</li>
 *   </ul>
 * </pre>
 *
 * @author keith
 */
public class HttpRequest {
    /**
     * Default config
     */
    private Config config = DefaultConfigs.get();
    /**
     * Support get and post
     */
    private HttpRequestBase httpMethod;
    /**
     * 方便拼接http请求的param
     */
    private URIBuilder uriBuilder;
    /**
     * 用于cookie的存储
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
     * Get method
     */
    public static HttpRequest get(final String uri) {
        return new HttpRequest(new HttpGet(), uri);
    }

    /**
     * Post method
     */
    public static HttpRequest post(final String uri) {
        return new HttpRequest(new HttpPost(), uri);
    }

    /**
     * Config HttpClient
     */
    public HttpRequest config(Config config) {
        this.config = checkNotNull(config, "config should not be null");
        return this;
    }

    /**
     * Set body
     */
    private HttpRequest body(HttpEntity entity) {
        if (this.httpMethod instanceof HttpEntityEnclosingRequest) {
            ((HttpEntityEnclosingRequest) this.httpMethod).setEntity(entity);
        } else {
            throw new IllegalStateException(this.httpMethod.getMethod() + " request cannot enclose an entity");
        }
        return this;
    }

    /**
     * Set Content Type
     */
    public HttpRequest contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * 设置contextType
     */
    public HttpRequest contentType(String contentType, String charset) {
        this.contentType = ContentType.create(contentType, Charset.forName(charset));
        return this;
    }

    /**
     * post提交表单的内容，表单格式
     */
    public HttpRequest bodyForm(final Iterable<? extends NameValuePair> formParams) {
        final List<NameValuePair> paramList = Lists.newArrayList();
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
        List<NameValuePair> nvps = Lists.newArrayList();
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

    /**
     * 批量添加cookie {@link CookieBuilder}
     */
    public HttpRequest addCookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            cookieStore.addCookie(cookie);
        }
        return this;
    }

    /**
     * 使用基础的RequestConfig，执行Http请求
     */
    public HttpResult execute() {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)
                .setConnectTimeout(3000)
                .setSocketTimeout(15000).build();
        return execute(config);
    }

    /**
     * 根据requestConfig，执行Http请求
     *
     * @param requestConfig 本次执行的Http请求的配置
     * @return HttpResult 执行结果
     */
    public HttpResult execute(RequestConfig requestConfig) {
        checkNotNull(requestConfig, "requestConfig should not be null");

        HttpResponse httpResp = null;
        try {
            httpMethod.setURI(uriBuilder.build());
            httpMethod.setConfig(requestConfig);

            HttpClient httpClient = HttpClientHolder.get(config);

            HttpClientContext context = HttpClientContext.create();
            //添加对cookie的支持
            context.setCookieStore(cookieStore);

            httpResp = httpClient.execute(httpMethod, context);
            return new HttpResult(httpResp, context, contentType.getCharset());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            HttpClientUtils.closeQuietly(httpResp);
        }
    }
}

