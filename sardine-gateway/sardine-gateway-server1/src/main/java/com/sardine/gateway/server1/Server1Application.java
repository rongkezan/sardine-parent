package com.sardine.gateway.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Cloud Gateway Server1
 *
 * @author keith
 */
@RestController
@SpringBootApplication
public class Server1Application {
    public static void main(String[] args) {
        SpringApplication.run(Server1Application.class, args);
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello Server1";
    }
}
