package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateInvoiceItemDtoTest {
    UpdateInvoiceItemDto invoiceItem;

    @BeforeEach
    public void init() {
        invoiceItem = new UpdateInvoiceItemDto();
    }

    @Test
    public void testAllArgs() {
        invoiceItem = new UpdateInvoiceItemDto(1L, "desc2", new QuantityDto("g", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), 7L, 1);

        assertEquals(1L, invoiceItem.getId());
        assertEquals("desc2", invoiceItem.getDescription());
        assertEquals(new QuantityDto("g", new BigDecimal("4")), invoiceItem.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5")), invoiceItem.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), invoiceItem.getTax());
        assertEquals(7L, invoiceItem.getMaterialId());
        assertEquals(1, invoiceItem.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(invoiceItem.getId());
        invoiceItem.setId(5L);
        assertEquals(5L, invoiceItem.getId());
    }

    @Test
    public void testAccessDescription() {
        assertNull(invoiceItem.getDescription());
        invoiceItem.setDescription("Description ABC");
        assertEquals("Description ABC", invoiceItem.getDescription());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(invoiceItem.getQuantity());
        invoiceItem.setQuantity(new QuantityDto("g", new BigDecimal(100)));
        assertEquals(new QuantityDto("g", new BigDecimal(100)), invoiceItem.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        assertNull(invoiceItem.getPrice());
        invoiceItem.setPrice(new MoneyDto("CAD", new BigDecimal("100")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100")), invoiceItem.getPrice());
    }

    @Test
    public void testAccessTax() {
        assertNull(invoiceItem.getTax());
        invoiceItem.setTax(new TaxDto(new MoneyDto("CAD", new BigDecimal("100"))));
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("100"))), invoiceItem.getTax());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(invoiceItem.getMaterialId());
        invoiceItem.setMaterialId(8L);
        assertEquals(8L, invoiceItem.getMaterialId());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoiceItem.getVersion());
        invoiceItem.setVersion(1);
        assertEquals(1, invoiceItem.getVersion());
    }
}