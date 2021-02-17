package com.sardine.cookbook.app.client;

import com.sardine.user.api.client.UserProviderClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "sardine-user", fallbackFactory = UserProviderBackFactory.class)
public interface UserProviderApi extends UserProviderClient {

}
