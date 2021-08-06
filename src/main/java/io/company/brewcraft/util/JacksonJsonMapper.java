package io.company.brewcraft.util;

import javax.measure.Quantity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonJsonMapper implements JsonMapper {

    public ObjectMapper mapper;

    public JacksonJsonMapper(ObjectMapper mapper) {
        this.mapper = mapper
                        .registerModule(new SimpleModule().addSerializer(Quantity.class, new QuantitySerializer()))
                        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
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
