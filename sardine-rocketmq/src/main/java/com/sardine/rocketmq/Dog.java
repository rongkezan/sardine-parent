package com.sardine.rocketmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @author keith
 */
@Data
public class Dog implements Serializable {

    private String name = "WangCai";

    private Integer age = 2;
}
