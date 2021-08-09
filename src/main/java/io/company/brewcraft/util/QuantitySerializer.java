package io.company.brewcraft.util;

import java.io.IOException;
import java.math.BigDecimal;

import javax.measure.Quantity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.service.mapper.QuantityMapper;

@SuppressWarnings("rawtypes")
public class QuantitySerializer extends JsonSerializer<Quantity> {
    protected QuantitySerializer() {
        super();
    }

    @Override
    public void serialize(Quantity value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            QuantityDto dto = QuantityMapper.INSTANCE.toDto(value);
            gen.writeStringField("symbol", dto.getSymbol());
            gen.writeNumberField("value", (BigDecimal) dto.getValue());
            gen.writeEndObject();
        }
    }
}
