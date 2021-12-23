package io.company.brewcraft.util;

import java.io.IOException;
import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.service.mapper.CurrencyMapper;

public class MoneyDeserializer extends JsonDeserializer<Money> {

    @Override
    public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        CurrencyUnit currency = CurrencyMapper.INSTANCE.toUnit(node.get(MoneyDto.ATTR_CURRENCY).asText());
        BigDecimal amount = new BigDecimal(node.get(MoneyDto.ATTR_AMOUNT).asText());

        return Money.of(currency, amount);
    }
}
