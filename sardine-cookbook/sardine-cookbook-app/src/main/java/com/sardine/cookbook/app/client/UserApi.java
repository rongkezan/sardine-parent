package com.sardine.cookbook.app.client;

import com.sardine.user.api.client.UserClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("sardine-user")
public interface UserApi extends UserClient {

}
