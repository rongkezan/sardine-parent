package com.sardine.cookbook.app.controller;

import com.sardine.common.entity.http.CommonResult;
import com.sardine.cookbook.app.entity.Cookbook;
import com.sardine.cookbook.app.entity.Student;
import com.sardine.cookbook.app.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Test RestTemplate
 */
@RestController
@RequestMapping("rest_template")
public class RestTemplateController {

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    private final RestTemplateService restTemplateService;

    public RestTemplateController(RestTemplate restTemplate, DiscoveryClient discoveryClient, RestTemplateService restTemplateService) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("get")
    public void hello(String name){
        String url = "http://127.0.0.1:8001/provider/hello?name={1}";
        ResponseEntity<String> helloResponse = restTemplate.getForEntity(url, String.class, name);
        System.out.println("Hello: " + helloResponse.getBody());

        String url2 = "http://127.0.0.1:8001/provider/cookbook?name={1}";
        ResponseEntity<Cookbook> cookBookResponse = restTemplate.getForEntity(url2, Cookbook.class, name);
        System.out.println("CookBook: " + cookBookResponse.getBody());

        String url3 = "http://127.0.0.1:8001/provider/list/cookbook";
        List<Cookbook> list = restTemplate.getForObject(url3, List.class);
        System.out.println("CookBook List: " + list);

        String url4 = "http://127.0.0.1:8001/provider/cookbook";
        Student student = new Student();
        student.setName("张三");
        ResponseEntity<Cookbook> postCookbook = restTemplate.postForEntity(url4, student, Cookbook.class);
        System.out.println("Post CookBook: " + postCookbook.getBody());
    }

    @GetMapping("balance")
    public void balance(){
        String url = "http://sardine-cookbook/provider/balance";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
    }

    @GetMapping("fallback")
    public CommonResult<String> fallback(){
        return CommonResult.success("成功", restTemplateService.alive());
    }
}
