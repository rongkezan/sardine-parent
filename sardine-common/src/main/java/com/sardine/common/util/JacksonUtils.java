package com.sardine.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.sardine.common.exception.SystemException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson工具类
 *
 * @author keith
 */
public class JacksonUtils {

    private JacksonUtils() {

    }

    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * Config ObjectMapper
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // Convert LocalDateTime, LocalDate, LocalTime to specified pattern.
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(STANDARD_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(STANDARD_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_PATTERN)));

        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * convert json string to object
     *
     * @param json  json string
     * @param clazz object class
     * @return object
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw SystemException.of("Jackson to bean exception", e);
        }
    }

    /**
     * Convert json string to generics object.
     *
     * @param json      json string
     * @param reference generics object reference
     *                  example: new TypeReference<Map<String, Object>>() {}
     *                  normally you don't need implement this interface's method.
     * @return          object
     */
    public static <T> T toBean(String json, TypeReference<T> reference) {
        try {
            return objectMapper.readValue(json, reference);
        } catch (Exception e) {
            throw SystemException.of("Jackson to bean exception", e);
        }
    }

    /**
     * convert object to json string
     *
     * @param object object
     * @return json string
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw SystemException.of("Jackson to bean exception", e);
        }
    }
}
