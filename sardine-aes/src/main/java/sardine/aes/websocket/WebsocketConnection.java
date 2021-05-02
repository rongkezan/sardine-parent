package sardine.aes.websocket;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import sardine.aes.domain.enums.ConnectionStateEnum;
import sardine.aes.domain.options.Options;
import sardine.aes.utils.IdGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author keith
 */
@Slf4j
@Data
public class WebsocketConnection extends WebSocketListener {

    private static final String HUOBI_MARKET_WEBSOCKET_PATH = "/ws";

    private Long id;

    private Request okHttpRequest;

    private String host;

    private ConnectionStateEnum state;

    private WebSocket webSocket;

    private Consumer<?> callback;

    public static WebsocketConnection createConnection(Options options, List<String> commandList, Consumer<?> callback) {

        WebsocketConnection connection = new WebsocketConnection();

        String url = options.getWebSocketHost() + HUOBI_MARKET_WEBSOCKET_PATH;

        connection.setId(IdGenerator.getNextId());
        connection.setOkHttpRequest(new Request.Builder().url(url).build());

        try {
            connection.setHost(new URL(options.getRestHost()).getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        connection.connect();

        return connection;
    }


    private void connect() {
        if (state == ConnectionStateEnum.CONNECTED) {
            log.info("[Connection][" + id + "] Already connected");
            return;
        }
        log.info("[Connection][" + id + "] Connecting...");
        webSocket = ConnectionFactory.createWebSocket(okHttpRequest, this);
    }
}
