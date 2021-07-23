package com.sardine.gateway.server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Cloud Gateway Server2
 *
 * @author keith
 */
@RestController
@SpringBootApplication
public class Server2Application {
    public static void main(String[] args) {
        SpringApplication.run(Server2Application.class, args);
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello Server2";
    }
}
