package com.sardine.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Websocket endpoint
 *
 * request url : ws://127.0.0.1:18001
 *
 * @author keith
 */
@Slf4j
@Component
@ServerEndpoint("/")
public class WebsocketServer {

    private static final AtomicInteger CONNECT_COUNT = new AtomicInteger();

    private static final ConcurrentHashMap<String, WebsocketServer> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        WEBSOCKET_MAP.put(session.getId(), this);
        log.info("[Websocket] 1 connection opened, now connect count is {}", CONNECT_COUNT.incrementAndGet());
    }

    @OnClose
    public void onClose() {
        WEBSOCKET_MAP.remove(this.session.getId());
        log.info("[Websocket] 1 connection closed, now connect count is {}", CONNECT_COUNT.decrementAndGet());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("[Websocket] Session id: {}, received message: {}", this.session.getId(), message);
        sendMessage("On response: " + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[Websocket] Session id: {}, occur error: {}", session.getId(), error.getMessage());
    }

    public void sendMessage(String message) {
        log.info("[Websocket] Session id: {}, send message: {}", this.session.getId(), message);
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("[Websocket] Session id: {}, send message error: {}", session.getId(), e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebsocketServer that = (WebsocketServer) o;
        return Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session);
    }
}
