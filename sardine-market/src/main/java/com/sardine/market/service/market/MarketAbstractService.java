package com.sardine.market.service.market;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sardine.market.service.WebSocketPushService;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 在该Bean初始化时，使用WebSocket订阅各个交易所的市场行情信息
 *
 * @author keith
 */
public abstract class MarketAbstractService {

    protected final WebSocketPushService webSocketPushService;

    protected final ExecutorService websocketInitPool = new ThreadPoolExecutor(
            1, 1, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("HUOBI_INIT-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public MarketAbstractService(WebSocketPushService webSocketPushService) {
        this.webSocketPushService = webSocketPushService;
    }

    @PostConstruct
    public void initWebsocket(){
        subKline();
        subDepth();
        subDetail();
        subTrade();
        subBbo();
    }

    /**
     * 订阅K线数据
     */
    protected abstract void subKline();

    /**
     * 订阅市场深度行情数据
     * 此主题发送最新市场深度快照
     */
    protected abstract void subDepth();

    /**
     * 订阅市场概要
     * 此主题提供24小时内最新市场概要快照
     *
     * 市场概要：
     * - 最新价
     * - 24小时成交量
     * - 24小时成交笔数
     * - 24小时开盘价
     * - 24小时最低价
     * - 24小时最高价
     * - 24小时成交额
     */
    protected abstract void subDetail();

    /**
     * 订阅成交明细数据
     * 此主题提供市场最新成交逐笔明细。
     */
    protected abstract void subTrade();

    /**
     * 订阅买一卖一逐笔行情
     * 当买一价、买一量、卖一价、卖一量，其中任一数据发生变化时，此主题推送逐笔更新。
     */
    protected abstract void subBbo();
}
