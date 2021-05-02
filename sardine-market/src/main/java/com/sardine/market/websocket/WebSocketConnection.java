package com.sardine.market.websocket;

import com.huobi.constant.enums.ConnectionStateEnum;
import com.sardine.market.websocket.constant.options.Options;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * @author keith
 */
@Slf4j
public class WebSocketConnection extends WebSocketListener {

    private Long id;

    private ConnectionStateEnum state;

    private WebSocket webSocket;

    private Request okHttpRequest;

    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool(20, 300, TimeUnit.SECONDS);

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .followSslRedirects(false)
            .followRedirects(false)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .connectionPool(CONNECTION_POOL)
            .addNetworkInterceptor(chain -> {
                Request request = chain.request();
                return chain.proceed(request);
            }).build();

    public static WebSocketConnection createConnection(Options options) {
        WebSocketConnection connection = new WebSocketConnection();
        connection.setOkHttpRequest(new Request.Builder().url(options.getWebSocketHost()).build());
        connection.connect();
        return connection;
    }

    public void connect() {
        if (state == ConnectionStateEnum.CONNECTED) {
            log.info("[Connection][" + this.id + "] Already connected");
            return;
        }
        log.info("[Connection][" + this.id + "] Connecting...");
        webSocket = OK_HTTP_CLIENT.newWebSocket(okHttpRequest, this);
    }

    public void setOkHttpRequest(Request okHttpRequest) {
        this.okHttpRequest = okHttpRequest;
    }

    public Long getId() {
        return id;
    }
}
