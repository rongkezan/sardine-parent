package com.sardine.websocket.client;


/**
 * Catch exception wrapper
 *
 * @author keith
 */
public class ExceptionWrapper {

    public static void wrapCatch(Wrapper wrapper){
        try {
            wrapper.wrap();
        } catch (Exception e) {
            System.out.println("wrapper exception");
        }
    }
}
