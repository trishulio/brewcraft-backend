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
        item = new UpdateInvoiceItemDto(
            "desc2",
            new QuantityDto("kg", new BigDecimal("4")),
            new MoneyDto("CAD", new BigDecimal("5")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))),
            new MaterialDto(7L, null, null, null, null, null, null, null, null),
            1
        );

        assertEquals("desc2", item.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5")), item.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), item.getTax());
        assertEquals(new MaterialDto(7L, null, null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testDescription() {
        assertNull(item.getDescription());
        item.setDescription("Description ABC");
        assertEquals("Description ABC", item.getDescription());
    }

    @Test
    public void testQuantity() {
        assertNull(item.getQuantity());
        item.setQuantity(new QuantityDto("kg", new BigDecimal(100)));
        assertEquals(new QuantityDto("kg", new BigDecimal(100)), item.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(item.getPrice());
        item.setPrice(new MoneyDto("CAD", new BigDecimal("100.00")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100.00")), item.getPrice());
    }

    @Test
    public void testAccessTax() {
        assertNull(item.getTax());
        item.setTax(new TaxDto(new MoneyDto("CAD", new BigDecimal("100.00"))));
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("100.00"))), item.getTax());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(item.getMaterial());
        item.setMaterial(new MaterialDto());
        assertEquals(new MaterialDto(), item.getMaterial());
    }

    @Test
    public void testAccessVersion() {
        assertNull(item.getVersion());
        item.setVersion(1);
        assertEquals(1, item.getVersion());
    }
}