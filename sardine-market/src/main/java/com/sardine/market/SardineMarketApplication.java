package com.sardine.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author keith
 */
@EnableScheduling
@SpringBootApplication
public class SardineMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(SardineMarketApplication.class, args);
    }
}
