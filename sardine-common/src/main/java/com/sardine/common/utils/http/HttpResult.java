package com.sardine.common.utils.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Http 请求结果类
 *
 * @author keith
 */
public class HttpResult {

    private HttpResponse httpResp;

    private HttpClientContext context;

    private String data;

    HttpResult(HttpResponse httpResp, HttpClientContext context, Charset charset) {
        this.httpResp = httpResp;
        this.context = context;
        try {
            this.data = EntityUtils.toString(httpResp.getEntity(), charset);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 请求是否成功
     */
    public boolean isSuccess() {
        return httpResp.getStatusLine().getStatusCode() == 200;
    }

    /**
     * http请求获取的结果数据
     */
    public String getData() {
        return data;
    }

    /**
     * 获取cookies
     */
    public List<Cookie> getCookies() {
        return context.getCookieStore().getCookies();
    }

    /**
     * 返回Http状态码
     */
    public int getStatusCode() {
        return httpResp.getStatusLine().getStatusCode();
    }

    /**
     * 返回HttpResponse对象，可以获取更详细信息，注意这个是输出流已经被关闭掉的HttpResponse
     */
    public HttpResponse getHttpResponse() {
        return this.httpResp;
    }
}
