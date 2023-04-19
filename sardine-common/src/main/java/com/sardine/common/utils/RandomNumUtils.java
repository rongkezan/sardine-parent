package com.sardine.common.utils;

import java.util.Random;
import java.util.UUID;

public class RandomNumUtils {

    private static final String SOURCE = "0123456789";

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomNums(int num) {
        StringBuilder flag = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < num; i++) {
            flag.append(SOURCE.charAt(random.nextInt(9)));
        }
        return flag.toString();
    }
}
