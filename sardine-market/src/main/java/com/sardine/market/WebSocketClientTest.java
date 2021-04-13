package com.sardine.market;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

@Slf4j
public class WebSocketClientTest {

    private static final String URL = "ws://localhost:18092/multicast";

    public static void main(String[] args) throws Exception {
        WsClient wsClient = new WsClient(new URI(URL));
        wsClient.connect();
        // 判断是否连接成功，未成功后面发送消息时会报错
        while (!wsClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("连接中···请稍后");
            Thread.sleep(1000);
        }
        wsClient.send("Java WebSocket Client");
        log.info("消息发送成功");
    }

    @Slf4j
    private static class WsClient extends WebSocketClient {

        public WsClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            log.info("握手成功");
        }

        @Override
        public void onMessage(String s) {
            log.info("收到消息：{}", s);
        }

        @Override
        public void onClose(int i, String s, boolean b) {
            log.info("连接关闭");
        }

        @Override
        public void onError(Exception e) {
            log.info("发生错误", e);
        }
    }
}
