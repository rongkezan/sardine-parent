package com.sardine.shardingshpere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sardine.shardingshpere.mapper")
public class SardineShardingshpereApplication {

    public static void main(String[] args) {
        SpringApplication.run(SardineShardingshpereApplication.class, args);
    }
}
