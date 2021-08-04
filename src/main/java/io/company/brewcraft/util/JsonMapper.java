package io.company.brewcraft.util;

import javax.measure.Quantity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public interface JsonMapper {
    final JsonMapper INSTANCE = new JacksonJsonMapper(
        new ObjectMapper()
            .registerModule(new SimpleModule().addSerializer(Quantity.class, new QuantitySerializer()))
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    );

    <T> String writeString(T o);

    <T> T readString(String json, Class<T> clazz);
}
