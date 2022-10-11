package ru.vtb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonCreator {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> String getStringJsonNodeFromObject(T object) {
        String data = "";
        try {
            data = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }
}
