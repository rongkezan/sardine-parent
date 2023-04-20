package com.sardine.seata.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keith
 */
@RestController
@RequestMapping("config")
@RefreshScope
public class ConfigController {

    @Value("${common.name}")
    private String name;

    @Value("${common.age}")
    private Integer age;

    @GetMapping("get")
    public String get(){
        return name + "," + age;
    }
}
