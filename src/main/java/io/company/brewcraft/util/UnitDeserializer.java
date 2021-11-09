package io.company.brewcraft.util;

import java.io.IOException;

import javax.measure.Unit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.company.brewcraft.dto.UnitDto;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;

public class UnitDeserializer extends JsonDeserializer<Unit<?>> {
    @Override
    public Unit<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        return QuantityUnitMapper.INSTANCE.fromSymbol(node.get(UnitDto.ATTR_SYMBOL).asText());
    }
}
