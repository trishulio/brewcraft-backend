package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoneyDtoTest {

    MoneyDto dto;

    @BeforeEach
    public void init() {
        dto = new MoneyDto();
    }

    @Test
    public void testAllArgsConstructor() {
        dto = new MoneyDto("CAD", new BigDecimal("999.00"));
        assertEquals("CAD", dto.getCurrency());
        assertEquals(new BigDecimal("999.00"), dto.getAmount());
    }

    @Test
    public void testAccessCurrency() {
        assertNull(dto.getCurrency());
        dto.setCurrency("CAD");
        assertEquals("CAD", dto.getCurrency());
    }

    @Test
    public void testAccessAmount() {
        assertNull(dto.getAmount());
        dto.setAmount(new BigDecimal("999.00"));
        assertEquals(new BigDecimal("999.00"), dto.getAmount());
    }
}
