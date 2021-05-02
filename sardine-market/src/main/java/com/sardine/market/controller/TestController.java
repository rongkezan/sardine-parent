package com.sardine.market.controller;

import com.sardine.market.service.MyService;
import com.sardine.market.service.market.MarketAbstractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keith
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final MyService myService;

//    private TestController(MyService myService){
//        this.myService = myService;
//    }

    @GetMapping("hello")
    public String hello(){
        return myService.hello();
    }
}
