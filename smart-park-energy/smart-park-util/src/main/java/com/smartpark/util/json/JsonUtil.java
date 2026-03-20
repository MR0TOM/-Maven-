package com.smartpark.util.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 */
public class JsonUtil {

    /**
     * 对象转 JSON 字符串
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * JSON 字符串转对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * JSON 字符串转对象（支持泛型）
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * JSON 字符串转 List
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * JSON 字符串转 Map
     */
    public static Map<String, Object> parseMap(String json) {
        return JSON.parseObject(json, new TypeReference<>() {});
    }

    /**
     * 对象转 JSONObject
     */
    public static JSONObject toJsonObject(Object obj) {
        return JSON.parseObject(JSON.toJSONString(obj));
    }
}
