package com.sardine.market.websocket.constant.options;

import com.sardine.market.websocket.constant.enums.ExchangeEnum;
import lombok.Builder;

/**
 * Construct options instance using following code:
 * HuobiOptions.builder().build()
 *
 * @author keith
 */
@Builder
public class HuobiOptions implements Options {

    @Builder.Default
    private String restHost = "https://api.huobi.pro";

    @Builder.Default
    private String websocketHost = "wss://api.huobi.pro";

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
