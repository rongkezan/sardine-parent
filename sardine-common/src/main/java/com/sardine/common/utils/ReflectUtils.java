package com.sardine.common.utils;

import java.lang.reflect.Field;

/**
 * 反射工具类
 *
 * @author keith
 */
public class ReflectUtils {

    public static Object getValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getStringValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            Object value = field.get(object);
            return value == null ? "" : value.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static Long getLongValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return (Long) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }
}
