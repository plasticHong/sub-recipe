package com.subway.utils;


import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<String, Object> makeJsonFormat(String name,Object data) {

        LinkedHashMap<String , Object> map = new LinkedHashMap<>();
        map.put(name, data);

        return map;
    }
}
