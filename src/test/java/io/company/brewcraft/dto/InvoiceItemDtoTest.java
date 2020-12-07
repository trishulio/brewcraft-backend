package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceItemDtoTest {

    InvoiceItemDto dto;

    @BeforeEach
    public void init() {
        dto = new InvoiceItemDto();
    }

    @Test
    public void testAllArgs() {
        dto = new InvoiceItemDto(12345L, new QuantityDto("kg", 100), new MoneyDto("CAD", new BigDecimal("100.00")), new MoneyDto("CAD", new BigDecimal("500.00")), "LOT", new MaterialDto(), 1);
        dto = new InvoiceItemDto(12345L, new QuantityDto("kg", 100), new MoneyDto("CAD", new BigDecimal("100.00")), new MoneyDto("CAD", new BigDecimal("500.00")), "LOT", new MaterialDto(), 1);
        assertEquals(12345L, dto.getId());
        assertEquals(new QuantityDto("kg", 100), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), dto.getPrice());
        assertEquals(new MoneyDto("CAD", new BigDecimal("500.00")), dto.getAmount());
        assertEquals("LOT", dto.getLot());
        assertEquals(new MaterialDto(), dto.getMaterial());
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
        dto.setMaterial(new MaterialDto());
        assertEquals(new MaterialDto(), dto.getMaterial());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessAmount() {
        assertNull(dto.getAmount());
        dto.setAmount(new MoneyDto("CAD", new BigDecimal("100.00")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), dto.getAmount());
    }
}
