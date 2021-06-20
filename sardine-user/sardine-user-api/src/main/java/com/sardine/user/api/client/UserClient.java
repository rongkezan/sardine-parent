package com.sardine.user.api.client;

import com.sardine.common.entity.domain.UserDto;
import com.sardine.common.entity.http.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserClient {

    @PostMapping("identify")
    Result<UserDto> identify(@RequestParam String token);
}
