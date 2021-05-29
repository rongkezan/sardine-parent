package com.sardine.websocket.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * @author keith
 */
@Slf4j
public class WebsocketClient {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build();

    private static final String URL = "ws://127.0.0.1:18001";

    private static WebSocket webSocket;

    public static void main(String[] args) {
        Request request = new Request.Builder().get().url(URL).build();
        webSocket = CLIENT.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                log.info("[Websocket] on closed, reason: {}", reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                log.info("[Websocket] on closing, reason: {}", reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                log.info("[Websocket] on failure, reason: {}", t.getMessage());
            }

            @SneakyThrows
            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                log.info("[Websocket] on string message: {} ", text);
                Thread.sleep(500);
                webSocket.send("Hello On Response");
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                log.info("[Websocket] on byte message: {} ", bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                log.info("[Websocket] on open");
                webSocket.send("Hello Websocket");
            }
        });
    }
}
