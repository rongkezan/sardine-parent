package sardine.aes.domain.enums;

import lombok.Getter;

/**
 * @author keith
 */

@Getter
public enum ExchangeEnum {

    /**
     * huobi
     */
    HUOBI("huobi"),

    /**
     * binance
     */
    BINANCE("binance"),

    /**
     * okex
     */
    OKEX("okex"),

    /**
     * bitfinex
     */
    BITFINEX("bitfinex");

    private final String code;

    ExchangeEnum(String code) {
        this.code = code;
    }
}
