package org.example.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
