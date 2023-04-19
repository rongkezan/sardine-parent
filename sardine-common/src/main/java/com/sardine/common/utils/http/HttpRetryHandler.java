package com.sardine.common.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * http请求重试处理类
 *
 * @author keith
 */
@Slf4j
public class HttpRetryHandler implements HttpRequestRetryHandler {

    private static final ThreadLocal<Integer> COUNTER = new ThreadLocal<>();

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        Integer maxTries = COUNTER.get();

        if (maxTries == null) {
            return false;
        }

        // 只有在请求超时和无响应的情况下进行重试
        boolean needRetryException = false;
        if (exception instanceof ConnectTimeoutException || exception instanceof SocketTimeoutException) {
            needRetryException = true;
            log.info("Http request timeout, total retry times: {}, current execute times: {}", maxTries, executionCount);
        } else if (exception instanceof NoHttpResponseException) {
            needRetryException = true;
            log.info("Http request no response, total retry times: {}, current execute times: {}", maxTries, executionCount);
        }
        return needRetryException && executionCount <= maxTries;
    }

    public void accept(int retryTimes) {
        HttpRetryHandler.COUNTER.set(retryTimes);
    }

    public void remove() {
        HttpRetryHandler.COUNTER.remove();
    }
}
