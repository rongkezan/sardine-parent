package com.sardine.websocket.client;

/**
 * @author keith
 */
@FunctionalInterface
public interface Wrapper {

    /**
     * wrap this target with no arguments and no return.
     */
    void wrap();
}
