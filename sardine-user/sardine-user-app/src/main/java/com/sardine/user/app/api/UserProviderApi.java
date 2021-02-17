package com.sardine.user.app.api;

import com.sardine.user.api.client.UserProviderClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("sardine-user")
public interface UserProviderApi extends UserProviderClient {

}
