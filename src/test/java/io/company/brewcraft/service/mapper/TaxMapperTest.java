package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.model.Tax;

public class TaxMapperTest {
    private TaxMapper mapper;

    @BeforeEach
    public void init() {
        mapper = TaxMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        TaxDto dto = new TaxDto(new MoneyDto("CAD", new BigDecimal("10")));
        Tax tax = mapper.fromDto(dto);

        assertEquals(new Tax(Money.parse("CAD 10")), tax);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Tax tax = new Tax(Money.parse("CAD 10"));
        TaxDto dto = mapper.toDto(tax);

        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("10.00"))), dto);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
