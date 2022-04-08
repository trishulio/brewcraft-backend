package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceItemDtoTest {
    InvoiceItemDto invoiceItem;

    @BeforeEach
    public void init() {
        invoiceItem = new InvoiceItemDto();
    }

    @Test
    public void testIdConstructor_SetsIdValue() {
        invoiceItem = new InvoiceItemDto(1L);

        assertEquals(1L, invoiceItem.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        invoiceItem = new InvoiceItemDto(
            2L,
            "desc2",
            new QuantityDto("kg", new BigDecimal("4")),
            new MoneyDto("CAD", new BigDecimal("5")),
            new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))),
            new MoneyDto("CAD", new BigDecimal("8")),
            new MaterialDto(7L),
            LocalDateTime.of(1999, 1, 1, 1, 1),
            LocalDateTime.of(2000, 1, 1, 1, 1),
            1
        );

        assertEquals(2L, invoiceItem.getId());
        assertEquals("desc2", invoiceItem.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), invoiceItem.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5")), invoiceItem.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), invoiceItem.getTax());
        assertEquals(new MoneyDto("CAD", new BigDecimal("8")), invoiceItem.getAmount());
        assertEquals(new MaterialDto(7L), invoiceItem.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 1, 1), invoiceItem.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 1, 1), invoiceItem.getLastUpdated());
        assertEquals(1, invoiceItem.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(invoiceItem.getId());
        invoiceItem.setId(12345L);
        assertEquals(12345L, invoiceItem.getId());
    }

    @Test
    public void testDescription() {
        assertNull(invoiceItem.getDescription());
        invoiceItem.setDescription("Description ABC");
        assertEquals("Description ABC", invoiceItem.getDescription());
    }

    @Test
    public void testQuantity() {
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
    public void testAccessAmount() {
        assertNull(invoiceItem.getAmount());
        invoiceItem.setAmount(new MoneyDto("CAD", new BigDecimal("100")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100")), invoiceItem.getAmount());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(invoiceItem.getMaterial());
        invoiceItem.setMaterial(new MaterialDto());
        assertEquals(new MaterialDto(), invoiceItem.getMaterial());
    }

    @Test
    public void testAccessVersion() {
        assertNull(invoiceItem.getVersion());
        invoiceItem.setVersion(1);
        assertEquals(1, invoiceItem.getVersion());
    }
}
