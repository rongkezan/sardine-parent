package com.sardine.market.websocket.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exchange enum
 *
 * @author keith
 */

@Getter
@AllArgsConstructor
public enum ExchangeEnum {

    /**
     * exchange huobi
     */
    HUOBI("huobi"),

    /**
     * exchange binance
     */
    BINANCE("binance");

    private final String code;
}
