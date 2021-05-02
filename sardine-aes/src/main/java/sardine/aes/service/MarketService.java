package sardine.aes.service;

import sardine.aes.domain.event.CandlestickEvent;
import sardine.aes.domain.event.MarketDepthEvent;
import sardine.aes.domain.event.MarketDetailEvent;
import sardine.aes.domain.event.MarketTradeEvent;
import sardine.aes.domain.request.*;

import java.util.function.Consumer;

/**
 * @author keith
 */
public interface MarketService {

    /**
     * subscribe kline
     *
     * @param request   request
     * @param callback  callback
     */
    void subCandlestick(SubCandlestickRequest request, Consumer<CandlestickEvent> callback);

    /**
     * subscribe market detail
     *
     * @param request   request
     * @param callback  callback
     */
    void subMarketDetail(SubMarketDetailRequest request, Consumer<MarketDetailEvent> callback);

    /**
     * subscribe market depth
     *
     * @param request   request
     * @param callback  callback
     */
    void subMarketDepth(SubMarketDepthRequest request, Consumer<MarketDepthEvent> callback);

    /**
     * subscribe market trade
     *
     * @param request   request
     * @param callback  callback
     */
    void subMarketTrade(SubMarketTradeRequest request, Consumer<MarketTradeEvent> callback);
}
