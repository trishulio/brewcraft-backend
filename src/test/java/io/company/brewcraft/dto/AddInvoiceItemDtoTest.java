package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddInvoiceItemDtoTest {
    AddInvoiceItemDto invoiceItem;

    @BeforeEach
    public void init() {
        invoiceItem = new AddInvoiceItemDto();
    }

    @Test
    public void testAllArgs() {
        invoiceItem = new AddInvoiceItemDto("desc2", new QuantityDto("kg", new BigDecimal("4")), new MoneyDto("CAD", new BigDecimal("5")), new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), 7L);

        assertEquals("desc2", invoiceItem.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), invoiceItem.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5")), invoiceItem.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), invoiceItem.getTax());
        assertEquals(7L, invoiceItem.getMaterialId());
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
        invoiceItem.setQuantity(new QuantityDto("kg", new BigDecimal(100)));
        assertEquals(new QuantityDto("kg", new BigDecimal(100)), invoiceItem.getQuantity());
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
}
