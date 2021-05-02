package com.sardine.market.websocket.constant.options;

import com.sardine.market.websocket.constant.enums.ExchangeEnum;
import lombok.Builder;

/**
 * Construct options instance using following code:
 * BinanceOptions.builder().build()
 *
 * @author keith
 */
@Builder
public class BinanceOptions implements Options {

    @Builder.Default
    private String restHost = "https://api.binance.com";

    @Builder.Default
    private String websocketHost = "wss://stream.binance.com:9443";

    @Override
    public String getRestHost() {
        return restHost;
    }

    @Override
    public String getWebSocketHost() {
        return websocketHost;
    }

    @Override
    public ExchangeEnum getExchange() {
        return ExchangeEnum.HUOBI;
    }
}
