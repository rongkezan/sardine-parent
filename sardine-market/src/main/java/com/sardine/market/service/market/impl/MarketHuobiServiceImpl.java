package com.sardine.market.service.market.impl;

import com.huobi.client.GenericClient;
import com.huobi.client.MarketClient;
import com.huobi.client.req.market.*;
import com.huobi.constant.HuobiOptions;
import com.huobi.constant.enums.CandlestickIntervalEnum;
import com.sardine.market.service.WebSocketPushService;
import com.sardine.market.service.market.MarketAbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author keith
 */
@Slf4j
@Service
public class MarketHuobiServiceImpl extends MarketAbstractService {

    private final MarketClient marketClient = MarketClient.create(HuobiOptions.builder().build());

    private final GenericClient genericClient = GenericClient.create(HuobiOptions.builder().build());

    private String symbols;

    {
        symbols = "btcusdt,ethusdt,dogeusdt,xrpusdt";
    }

    public MarketHuobiServiceImpl(WebSocketPushService webSocketPushService) {
        super(webSocketPushService);
    }

    @Override
    protected void subKline() {
        log.info("开始订阅火币K线数据");
        websocketInitPool.execute(() ->
                marketClient.subCandlestick(SubCandlestickRequest.builder()
                        .symbol(symbols)
                        .interval(CandlestickIntervalEnum.MIN15)
                        .build(), webSocketPushService::broadcast)
        );
    }

    @Override
    protected void subDepth() {
        log.info("开始订阅火币深度数据");
        websocketInitPool.execute(() ->
                marketClient.subMarketDepth(SubMarketDepthRequest.builder()
                        .symbol(symbols)
                        .build(), webSocketPushService::broadcast)
        );
    }

    @Override
    protected void subDetail() {
        log.info("开始订阅火币成交明细数据");
        websocketInitPool.execute(() ->
                marketClient.subMarketDetail(SubMarketDetailRequest.builder()
                        .symbol(symbols)
                        .build(), webSocketPushService::broadcast)
        );
    }

    @Override
    protected void subTrade() {
        log.info("开始订阅火币交易数据");
        marketClient.subMarketTrade(SubMarketTradeRequest.builder()
                .symbol(symbols).build(), (marketTradeEvent) -> {
            webSocketPushService.broadcast(marketTradeEvent.getCh());
            webSocketPushService.broadcast(marketTradeEvent.getTs());
            marketTradeEvent.getList().forEach(webSocketPushService::broadcast);
        });
    }

    @Override
    protected void subBbo() {
        log.info("开始订阅火币买一卖一逐笔行情数据");
        websocketInitPool.execute(() ->
            marketClient.subMarketBBO(SubMarketBBORequest.builder()
                    .symbol(symbols)
                    .build(), webSocketPushService::broadcast)
        );
    }
}
