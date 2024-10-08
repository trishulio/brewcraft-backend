package io.company.brewcraft.util;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.Money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.service.mapper.MoneyMapper;

public class MoneySerializer extends JsonSerializer<Money> {
    @Override
    public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeStartObject();
            MoneyDto dto = MoneyMapper.INSTANCE.toDto(value);
            gen.writeStringField(MoneyDto.ATTR_CURRENCY, dto.getCurrency());
            gen.writeNumberField(MoneyDto.ATTR_AMOUNT, (BigDecimal) dto.getAmount());
            gen.writeEndObject();
        }
    }
}
