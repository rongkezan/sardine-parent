package com.sardine.dubbo.provider;

import com.sardine.dubbo.api.RemoteService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
@RequiredArgsConstructor
public class LocalServiceImpl implements LocalService {

    private final RemoteService remoteService;

    @Override
    public String localHello() {
        return "local " + remoteService.remoteHello();
    }
}
