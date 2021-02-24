package com.sardine.sharding;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author keith
 */
@SuppressWarnings("ALL")
@Service
public class TestService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public void insert(){
        insertOne();
        int i = 1 / 0;
    }

    /**
     * 即使开启了新事务 也会回滚 因为没有被切面所管理
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertOne(){
        TOrder tOrder = new TOrder();
        tOrder.setName("aaa");
        orderMapper.insert(tOrder);
    }
}
