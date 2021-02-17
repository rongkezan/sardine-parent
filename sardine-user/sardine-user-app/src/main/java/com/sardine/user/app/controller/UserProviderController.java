package com.sardine.user.app.controller;

import com.sardine.common.entity.http.CommonResult;
import com.sardine.user.app.api.UserProviderApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("provider")
public class UserProviderController implements UserProviderApi {

    /* Counter in JVM, work for test balance */
    private int counter = 0;

    @GetMapping("count")
    public CommonResult<String> count(){
        return CommonResult.success("success","Count:" + counter++);
    }

    @GetMapping("timeout")
    public CommonResult<String> timeout(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CommonResult.success();
    }
}
