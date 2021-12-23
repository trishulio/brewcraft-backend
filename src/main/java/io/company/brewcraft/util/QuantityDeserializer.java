package io.company.brewcraft.util;

import java.io.IOException;
import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.measure.Unit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import tec.uom.se.quantity.Quantities;

public class QuantityDeserializer extends JsonDeserializer<Quantity<?>> {
    @Override
    public Quantity<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        Unit<?> unit = QuantityUnitMapper.INSTANCE.fromSymbol(node.get(QuantityDto.ATTR_SYMBOL).asText());
        Number value = new BigDecimal(node.get(QuantityDto.ATTR_VALUE).asText());

        return Quantities.getQuantity(value, unit);
    }
}
