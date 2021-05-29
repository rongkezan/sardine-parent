package com.sardine.dubbo.provider;

import com.sardine.dubbo.api.RemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
public class LocalController {

    private final LocalService localService;

    private final RemoteService remoteService;

    @GetMapping("local/hello")
    public String hello(){
        return localService.localHello();
    }

    @GetMapping("remote/hello")
    public String hello2(){
        return remoteService.remoteHello();
    }
}
