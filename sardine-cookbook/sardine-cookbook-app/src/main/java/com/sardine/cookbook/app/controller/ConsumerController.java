package com.sardine.cookbook.app.controller;

import com.sardine.common.entity.http.CommonResult;
import com.sardine.cookbook.app.client.UserProviderApi;
import com.sardine.user.api.client.UserProviderClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author keith
 */
@RestController
@RequestMapping("consumer")
public class ConsumerController implements ApplicationContextAware {

    private final UserProviderApi userProviderApi;

    private ApplicationContext applicationContext;

    public ConsumerController(UserProviderApi userProviderApi) {
        this.userProviderApi = userProviderApi;
    }

    @GetMapping("count")
    public CommonResult<String> userFeign(){
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
        return userProviderApi.count();
    }

    @GetMapping("timeout")
    public CommonResult<String> userTimeout(){
        CommonResult<String> count = userProviderApi.timeout();
        return CommonResult.success(count.getRecord());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
