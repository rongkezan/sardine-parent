package com.sardine.market.websocket.constant.options;

import com.sardine.market.websocket.constant.enums.ExchangeEnum;

/**
 * Options of create websocket connection.
 *
 * @author keith
 */
public interface Options {

    /**
     * get rest http request host.
     *
     * @return rest http url
     */
    String getRestHost();

    /**
     * get websocket request host.
     *
     * @return websocket url
     */
    String getWebSocketHost();

    /**
     * get exchange
     *
     * @return exchange enum
     */
    ExchangeEnum getExchange();
}
