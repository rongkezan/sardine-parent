package com.sardine.redisson;

import java.io.Serializable;

public class Cat implements Serializable {

    private String name = "tom";

    private Integer age = 3;

    private Double weight = 22D;

    private String desc = "This is a cat";

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", desc='" + desc + '\'' +
                '}';
    }
}
