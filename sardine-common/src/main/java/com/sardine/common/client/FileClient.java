package com.sardine.common.client;

import com.sardine.common.entity.http.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("sardine-file")
public interface FileClient {

    @GetMapping("hello")
    CommonResult<String> hello();
}
