package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateInvoiceItemDtoTest {
    UpdateInvoiceItemDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateInvoiceItemDto();
    }

    @Test
    public void testAllArgs() {
        dto = new UpdateInvoiceItemDto(12345L, new QuantityDto("kg", 100), new MoneyDto("CAD", new BigDecimal("100.00")), "LOT", new Object(), 1);
        assertEquals(12345L, dto.getId());
        assertEquals(new QuantityDto("kg", 100), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), dto.getPrice());
        assertEquals("LOT", dto.getLot());
//        assertEquals(new Object(), dto.getMaterial());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());
        dto.setId(12345L);
        assertEquals(12345L, dto.getId());
    }

    @Test
    public void testQuantity() {
        assertNull(dto.getQuantity());
        dto.setQuantity(new QuantityDto("kg", 100));
        assertEquals(new QuantityDto("kg", 100), dto.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(dto.getPrice());
        dto.setPrice(new MoneyDto("CAD", new BigDecimal("100.00")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), dto.getPrice());
    }

    @Test
    public void testAccessLot() {
        assertNull(dto.getLot());
        dto.setLot("LOT");
        assertEquals("LOT", dto.getLot());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(dto.getMaterial());
        dto.setMaterial(new Object());
//        assertEquals(new Object(), dto.getMaterial());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}