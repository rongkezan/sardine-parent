package com.sardine.market.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Store websocket client data
 *
 * @author keith
 */
@Component
public class SardineCache {

    private static BlockingQueue bq = new ArrayBlockingQueue(100);

}
