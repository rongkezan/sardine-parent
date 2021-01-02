package com.sardine.common.client;

import com.sardine.common.entity.http.CommonResult;
import com.sardine.common.entity.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("sardine-user")
public interface UserClient {

    @PostMapping("identify")
    CommonResult<UserDto> identify(@RequestParam("token") String token);
}
