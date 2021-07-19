package com.sardine.common.httpclient;

import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

/**
 * Http Result
 *
 * @author keith
 */
@Getter
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
}
