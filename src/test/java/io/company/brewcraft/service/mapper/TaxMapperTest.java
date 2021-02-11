package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.dto.TaxDto;
import io.company.brewcraft.model.Currency;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.model.TaxEntity;
import io.company.brewcraft.pojo.Tax;

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
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        Tax tax = new Tax(Money.parse("CAD 10"));
        TaxDto dto = mapper.toDto(tax);

        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("10.00"))), dto);
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        TaxEntity entity = new TaxEntity(1L, new MoneyEntity(2L, new Currency(124, "CAD"), new BigDecimal("10")));
        Tax tax = mapper.fromEntity(entity);

        assertEquals(new Tax(Money.parse("CAD 10")), tax);
    }

    @Test
    public void testToEntity_ReturnsEntity_WhenPojoIsNotNull() {
        Tax tax = new Tax(Money.parse("CAD 10"));
        TaxEntity entity = mapper.toEntity(tax);

        assertEquals(new TaxEntity(null, new MoneyEntity(null, new Currency(124, "CAD"), new BigDecimal("10.00"))), entity);
    }
}
