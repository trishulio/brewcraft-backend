package io.company.brewcraft.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public interface JsonMapper {
    final JsonMapper INSTANCE = new JacksonJsonMapper(new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false));

    <T> String writeString(T o);

    <T> T readString(String json, Class<T> clazz);
}
