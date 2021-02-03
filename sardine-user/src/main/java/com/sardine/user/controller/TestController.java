package com.sardine.user.controller;

import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

/**
 * @author keith
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("hello")
    public String hello(String name){
        //拿到服务提供商
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        //拿到第一个实例
        ServiceInstance instance = list.get(0);

        //得到主机号
        String host = instance.getHost();
        //得到端口号
        int port = instance.getPort();

        //拼接完整的请求url
        String url = "http://" + host + ":" + port + "/hello?name={1}";
        //restTemple 实际返回的是一个ResponseEntity 的实例
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, name);

        StrBuilder sb = new StrBuilder();
        //拿到状态码
        HttpStatus statusCode = responseEntity.getStatusCode();
        //拿到响应体
        String body= responseEntity.getBody();
        sb.append("statusCode:")
                .append(statusCode)
                .append("<br/>")
                .append("body:")
                .append(body)
                .append("<br/>");
        //拿到响应头header
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> keySet = headers.keySet();
        for (String s : keySet) {
            sb.append(s)
                    .append(":")
                    .append(headers.get(s))
                    .append("</br>");
        }
        return sb.toString();
    }
}
