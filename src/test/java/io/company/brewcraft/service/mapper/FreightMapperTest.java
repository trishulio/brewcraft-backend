package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.FreightDto;
import io.company.brewcraft.dto.MoneyDto;
import io.company.brewcraft.model.Freight;

public class FreightMapperTest {
    private FreightMapper mapper;

    @BeforeEach
    public void init() {
        mapper = FreightMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnsPojoFromDto() {
        FreightDto dto = new FreightDto(new MoneyDto("CAD", new BigDecimal("10")));
        Freight freight = mapper.fromDto(dto);

        assertEquals(new Freight(null, Money.parse("CAD 10")), freight);
    }
    
    @Test
    public void testFromDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testToDto_ReturnsDtoFromPojo() {
        Freight freight = new Freight(1L, Money.parse("CAD 10"));
        FreightDto dto = mapper.toDto(freight);

        assertEquals(new FreightDto(new MoneyDto("CAD", new BigDecimal("10.00"))), dto);
    }
    
    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
