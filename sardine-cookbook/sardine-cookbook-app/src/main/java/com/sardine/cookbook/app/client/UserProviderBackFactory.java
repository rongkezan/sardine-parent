package com.sardine.cookbook.app.client;

import com.sardine.common.entity.http.CommonResult;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserProviderBackFactory implements FallbackFactory<UserProviderApi> {

    @Override
    public UserProviderApi create(Throwable throwable) {
        return new UserProviderApi() {
            @Override
            public CommonResult<String> count() {
                System.out.println(throwable);
                if (throwable instanceof FeignException.InternalServerError){
                    return CommonResult.failed("远程服务器异常");
                }
                return CommonResult.failed("降级了");
            }

            @Override
            public CommonResult<String> timeout() {
                return null;
            }
        };
    }
}
