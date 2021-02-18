package com.sardine.cookbook.app.controller;

import com.sardine.common.entity.http.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("hello")
    public CommonResult<String> hello(){
        return CommonResult.success("成功", "Hello CookBook");
    }
}
