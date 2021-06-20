package com.sardine.user.api.client;

import com.sardine.common.entity.http.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("provider")
public interface UserProviderClient {

    @GetMapping("count")
    Result<String> count();

    @GetMapping("timeout")
    Result<String> timeout();
}
