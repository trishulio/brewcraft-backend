package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MoneyDto;

public class MoneyMapperTest {
    MoneyMapper mapper;

    @BeforeEach
    public void init() {
        mapper = MoneyMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsMoneyDto_WhenMoneyIsNotNull() {
        MoneyDto dto = mapper.toDto(Money.parse("USD 100"));
        assertEquals("USD", dto.getCurrency());
        assertEquals(new BigDecimal("100.00"), dto.getAmount());
    }

    @Test
    public void testTestFromDto_ReturnsMoney_WhenDtoIsNotNull() {
        Money money = mapper.fromDto(new MoneyDto("CAD", new BigDecimal(123)));
        assertEquals(Money.parse("CAD 123"), money);
    }
}
