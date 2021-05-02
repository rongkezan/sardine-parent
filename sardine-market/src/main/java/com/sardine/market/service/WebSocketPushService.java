package com.sardine.market.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于 WebSocket 的行情推送服务
 *
 * @author keith
 */
@Slf4j
@Component
@ServerEndpoint("/market")
public class WebSocketPushService {

    /**
     * Record client count.
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * Store all online clients.
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        clients.put(session.getId(), session);
        log.info("New session joined, Session ID: {}, now online client count: {}", session.getId(), onlineCount.incrementAndGet());
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session.getId());
        log.info("Session ID: {} closed, now online client count: {}", session.getId(), onlineCount.decrementAndGet());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Session ID: {}, Server received message: {}", session.getId(), message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Server occurred error, Session ID: {}", session.getId(), error);
    }

    /**
     * broadcast message
     *
     * @param message message
     */
    public void broadcast(Object message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            Session targetSession = sessionEntry.getValue();
            try {
                targetSession.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                log.warn("WebSocket broadcast exception.", e);
            }
        }
    }
}