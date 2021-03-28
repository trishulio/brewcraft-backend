package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateInvoiceItemDtoTest {
    UpdateInvoiceItemDto item;

    @BeforeEach
    public void init() {
        item = new UpdateInvoiceItemDto();
    }

    @Test
    public void testAllArgs() {
        item = new UpdateInvoiceItemDto(1L, "desc2", new QuantityDto("kg", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), 7L, 1);

        assertEquals(1L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5")), item.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), item.getTax());
        assertEquals(7L, item.getMaterialId());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(item.getId());
        item.setId(5L);
        assertEquals(5L, item.getId());
    }

    @Test
    public void testAccessDescription() {
        assertNull(item.getDescription());
        item.setDescription("Description ABC");
        assertEquals("Description ABC", item.getDescription());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(item.getQuantity());
        item.setQuantity(new QuantityDto("kg", new BigDecimal(100)));
        assertEquals(new QuantityDto("kg", new BigDecimal(100)), item.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(item.getPrice());
        item.setPrice(new MoneyDto("CAD", new BigDecimal("100")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100")), item.getPrice());
    }

    @Test
    public void testAccessTax() {
        assertNull(item.getTax());
        item.setTax(new TaxDto(new MoneyDto("CAD", new BigDecimal("100"))));
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("100"))), item.getTax());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(item.getMaterialId());
        item.setMaterialId(8L);
        assertEquals(8L, item.getMaterialId());
    }

    @Test
    public void testAccessVersion() {
        assertNull(item.getVersion());
        item.setVersion(1);
        assertEquals(1, item.getVersion());
    }
}