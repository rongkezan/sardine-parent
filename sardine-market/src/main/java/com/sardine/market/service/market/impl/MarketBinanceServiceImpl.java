package com.sardine.market.service.market.impl;

import com.huobi.client.MarketClient;
import com.huobi.constant.HuobiOptions;
import com.sardine.market.service.WebSocketPushService;
import com.sardine.market.service.market.MarketAbstractService;
import com.sardine.market.websocket.WebSocketConnection;
import com.sardine.market.websocket.constant.options.BinanceOptions;
import com.sardine.market.websocket.constant.options.Options;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author keith
 */
@Slf4j
@Service
public class MarketBinanceServiceImpl extends MarketAbstractService {

    public MarketBinanceServiceImpl(WebSocketPushService webSocketPushService) {
        super(webSocketPushService);
    }

    @Override
    protected void subKline() {
        System.out.println("开始订阅币安K线数据");

        WebSocketConnection.createConnection(BinanceOptions.builder().build());
    }

    @Override
    protected void subDepth() {

    }

    @Override
    protected void subDetail() {

    }

    @Override
    protected void subTrade() {
        System.out.println("开始订阅币安交易数据");
    }

    @Override
    protected void subBbo() {

    }
}
