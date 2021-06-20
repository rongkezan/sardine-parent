package com.sardine.websocket.client;

import java.io.Serializable;

/**
 * 普通撤单
 *
 * @author keith
 */
public class CancelOrderNormalActionPo implements Serializable {

    private static final long serialVersionUID = -5047140659126102283L;

    /**
     * 标的代码
     */
    private String instrumentId;

    /**
     * 报单系统唯一代码
     */
    private String orderSysId;

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getOrderSysId() {
        return orderSysId;
    }

    public void setOrderSysId(String orderSysId) {
        this.orderSysId = orderSysId;
    }

    @Override
    public String toString() {
        return "CancelOrderNormalActionPo{" +
                "instrumentId='" + instrumentId + '\'' +
                ", orderSysId='" + orderSysId + '\'' +
                '}';
    }
}
