package com.holycode.placeManagement.mockUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonToJavaConverter<CLASS_TO_DESERIALIZE_TO> {

    private final ObjectMapper objectMapper;

    private final String jsonFilePath;

    public JsonToJavaConverter(ObjectMapper objectMapper, String jsonFilePath) {
        this.objectMapper = objectMapper;
        this.jsonFilePath = jsonFilePath;
    }

    public CLASS_TO_DESERIALIZE_TO loadJson(Class<CLASS_TO_DESERIALIZE_TO> clazz) throws Exception {
        try {
            return objectMapper.readValue(new File(jsonFilePath), clazz);
        } catch (IOException e) {
            throw new Exception("Cannot deserialize to class " + clazz.getSimpleName() +" from json file", e);
        }
    }
}
