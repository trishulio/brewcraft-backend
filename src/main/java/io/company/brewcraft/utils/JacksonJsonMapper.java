package io.company.brewcraft.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonMapper implements JsonMapper {

    public ObjectMapper mapper;

    public JacksonJsonMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> String writeString(T o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Failed to serialize object: '%s' because %s", o, e.getMessage()), e);
        }
    }

    @Override
    public <T> T readString(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Failed to de-serialize string: '%s' because %s", json, e.getMessage()), e);
        }
    }
}
