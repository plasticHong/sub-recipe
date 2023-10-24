package com.hong.utils;


import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseUtils {
    public static <T> Map<String, T> makeJsonFormat(String name,T data) {

        LinkedHashMap<String , T> map = new LinkedHashMap<>();
        map.put(name, data);

        return map;
    }
}
