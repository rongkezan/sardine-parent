package com.sardine.dubbo.provider;

import com.sardine.dubbo.api.RemoteService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class RemoteServiceImpl implements RemoteService {
    @Override
    public String remoteHello() {
        return "remote hello";
    }
}
