package com.sardine.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sardine.common.exception.SardineRuntimeException;

import java.util.List;

/**
 * Jackson工具类
 */
public class JacksonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Json -> Entity
     */
    public static <T> T toEntity(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new SardineRuntimeException("Json convert to com.sardine.user.app.entity exception", e);
        }
    }

    /**
     * Json -> List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new SardineRuntimeException("Json convert to com.sardine.user.app.entity exception", e);
        }
    }

    /**
     * T -> Json
     */
    public static <T> String toJson(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new SardineRuntimeException("Object convert to json exception", e);
        }
    }
}
