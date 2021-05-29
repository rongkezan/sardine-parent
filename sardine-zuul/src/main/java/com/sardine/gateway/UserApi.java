package com.sardine.gateway;

import com.sardine.user.api.client.UserClient;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author keith
 */
@FeignClient("sardine-user")
public interface UserApi extends UserClient {

}
