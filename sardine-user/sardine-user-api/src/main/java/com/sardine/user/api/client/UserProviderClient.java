package com.sardine.user.api.client;

import com.sardine.common.entity.http.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("provider")
public interface UserProviderClient {

    @GetMapping("count")
    CommonResult<String> count();

    @GetMapping("timeout")
    CommonResult<String> timeout();
}
