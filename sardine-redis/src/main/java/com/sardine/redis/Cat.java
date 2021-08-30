package com.sardine.redis;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Cat implements Serializable {

    private String name = "tom";

    private Integer age = 3;

    private Double weight = 22D;

    private String desc = "This is a cat";

    private LocalDateTime now = LocalDateTime.now();
}
