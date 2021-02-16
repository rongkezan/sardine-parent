package com.sardine.user.api.client;

import com.sardine.common.entity.domain.UserDto;
import com.sardine.common.entity.http.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserClient {

    @PostMapping("identify")
    CommonResult<UserDto> identify(@RequestParam String token);

    @GetMapping("count")
    CommonResult<String> count();

    @GetMapping("timeout")
    CommonResult<String> timeout();
}
