package com.sardine.cookbook.app.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService {

    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(defaultFallback = "back")
    public String alive(){
        String url = "http://sardine-user/provider/count";
        ResponseEntity<String> helloResponse = restTemplate.getForEntity(url, String.class);
        return "Hello: " + helloResponse.getBody();
    }

    private String back(){
        System.out.println("降级了");
        return "降级了";
    }
}
