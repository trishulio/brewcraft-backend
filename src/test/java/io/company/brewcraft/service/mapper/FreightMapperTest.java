package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.model.Currency;
import io.company.brewcraft.model.FreightEntity;
import io.company.brewcraft.model.MoneyEntity;
import io.company.brewcraft.pojo.Freight;

public class FreightMapperTest {
    private FreightMapper mapper;

    @BeforeEach
    public void init() {
        mapper = FreightMapper.INSTANCE;
    }

    @Test
    public void testToEntity_ReturnEntityFromPojo() {
        FreightEntity entity = new FreightEntity(1L, new MoneyEntity(2L, new Currency(124, "CAD"), new BigDecimal("10.00")));
        Freight freight = mapper.fromEntity(entity);

        assertEquals(new Freight(Money.parse("CAD 10")), freight);
    }

    @Test
    public void testFromDto_ReturnsPojoFromDto() {
        FreightDto dto = new FreightDto(new MoneyDto("CAD", new BigDecimal("10.00")));
        Freight freight = mapper.fromDto(dto);

        assertEquals(new Freight(Money.parse("CAD 10")), freight);
    }

    @Test
    public void testToEntity_ReturnsEntityFromPojo() {
        Freight freight = new Freight(Money.parse("CAD 10"));
        FreightEntity entity = mapper.toEntity(freight);

        assertEquals(new FreightEntity(null, new MoneyEntity(null, new Currency(124, "CAD"), new BigDecimal("10.00"))), entity);
    }

    @Test
    public void testToDto_ReturnsDtoFromPojo() {
        Freight freight = new Freight(Money.parse("CAD 10"));
        FreightDto dto = mapper.toDto(freight);

        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10.00"))), dto);
    }
}
