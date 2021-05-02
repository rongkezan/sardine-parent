package sardine.aes.service.impl;

import com.alibaba.fastjson.JSONObject;
import sardine.aes.domain.enums.CandlestickIntervalEnum;
import sardine.aes.domain.event.CandlestickEvent;
import sardine.aes.domain.event.MarketDepthEvent;
import sardine.aes.domain.event.MarketDetailEvent;
import sardine.aes.domain.event.MarketTradeEvent;
import sardine.aes.domain.options.HuobiOptions;
import sardine.aes.domain.options.Options;
import sardine.aes.domain.request.SubCandlestickRequest;
import sardine.aes.domain.request.SubMarketDepthRequest;
import sardine.aes.domain.request.SubMarketDetailRequest;
import sardine.aes.domain.request.SubMarketTradeRequest;
import sardine.aes.service.MarketService;
import sardine.aes.websocket.WebsocketConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author keith
 */
public class HuobiMarketServiceImpl implements MarketService {

    public static final String WEBSOCKET_CANDLESTICK_TOPIC = "market.$symbol$.kline.$period$";

    private Options options = HuobiOptions.builder().build();

    @Override
    public void subCandlestick(SubCandlestickRequest request, Consumer<CandlestickEvent> callback) {
        List<String> symbols = request.getSymbols();
        List<String> commandList = new ArrayList<>(symbols.size());
        symbols.forEach(symbol -> {

            String topic = WEBSOCKET_CANDLESTICK_TOPIC
                    .replace("$symbol$", symbol)
                    .replace("$period$", request.getInterval().getCode());

            JSONObject command = new JSONObject();
            command.put("sub", topic);
            command.put("id", System.nanoTime());
            commandList.add(command.toJSONString());
        });
        WebsocketConnection.createConnection(options, commandList, callback);
    }

    @Override
    public void subMarketDetail(SubMarketDetailRequest request, Consumer<MarketDetailEvent> callback) {

    }

    @Override
    public void subMarketDepth(SubMarketDepthRequest request, Consumer<MarketDepthEvent> callback) {

    }

    @Override
    public void subMarketTrade(SubMarketTradeRequest request, Consumer<MarketTradeEvent> callback) {

    }
}
