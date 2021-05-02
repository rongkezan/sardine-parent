package com.sardine.market.websocket;

import com.huobi.client.MarketClient;
import com.huobi.client.req.market.*;
import com.huobi.constant.Options;
import com.huobi.model.market.*;
import com.huobi.utils.ResponseCallback;
import com.huobi.utils.WebSocketConnection;

import java.util.List;

/**
 * Binance market client
 *
 * @author keith
 */
public class BinanceMarketClient implements MarketClient {

    private Options options;

    private RestConnection restConnection;

    public BinanceMarketClient(Options options) {
        this.options = options;
        this.restConnection = new RestConnection(options);
    }

    @Override
    public List<Candlestick> getCandlestick(CandlestickRequest request) {
        return null;
    }

    @Override
    public MarketDetailMerged getMarketDetailMerged(MarketDetailMergedRequest request) {
        return null;
    }

    @Override
    public MarketDetail getMarketDetail(MarketDetailRequest request) {
        return null;
    }

    @Override
    public List<MarketTicker> getTickers() {
        return null;
    }

    @Override
    public MarketDepth getMarketDepth(MarketDepthRequest request) {
        return null;
    }

    @Override
    public List<MarketTrade> getMarketTrade(MarketTradeRequest request) {
        return null;
    }

    @Override
    public List<MarketTrade> getMarketHistoryTrade(MarketHistoryTradeRequest request) {
        return null;
    }

    @Override
    public void subCandlestick(SubCandlestickRequest request, ResponseCallback<CandlestickEvent> callback) {

    }

    @Override
    public void subMarketDetail(SubMarketDetailRequest request, ResponseCallback<MarketDetailEvent> callback) {

    }

    @Override
    public void subMarketDepth(SubMarketDepthRequest request, ResponseCallback<MarketDepthEvent> callback) {

    }

    @Override
    public void subMarketTrade(SubMarketTradeRequest request, ResponseCallback<MarketTradeEvent> callback) {

    }

    @Override
    public void subMarketBBO(SubMarketBBORequest request, ResponseCallback<MarketBBOEvent> callback) {

    }

    @Override
    public void subMbpRefreshUpdate(SubMbpRefreshUpdateRequest request, ResponseCallback<MbpRefreshUpdateEvent> callback) {

    }

    @Override
    public WebSocketConnection subMbpIncrementalUpdate(SubMbpIncrementalUpdateRequest request, ResponseCallback<MbpIncrementalUpdateEvent> callback) {
        return null;
    }

    @Override
    public WebSocketConnection reqMbpIncrementalUpdate(SubMbpIncrementalUpdateRequest request, WebSocketConnection connection) {
        return null;
    }

    @Override
    public void reqCandlestick(ReqCandlestickRequest request, ResponseCallback<CandlestickReq> callback) {

    }

    @Override
    public void reqMarketDepth(ReqMarketDepthRequest request, ResponseCallback<MarketDepthReq> callback) {

    }

    @Override
    public void reqMarketTrade(ReqMarketTradeRequest request, ResponseCallback<MarketTradeReq> callback) {

    }

    @Override
    public void reqMarketDetail(ReqMarketDetailRequest request, ResponseCallback<MarketDetailReq> callback) {

    }
}
