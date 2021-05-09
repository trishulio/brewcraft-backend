package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceItemDtoTest {

    InvoiceItemDto item;

    @BeforeEach
    public void init() {
        item = new InvoiceItemDto();
    }

    @Test
    public void testIdConstructor_SetsIdValue() {
        item = new InvoiceItemDto(1L);

        assertEquals(1L, item.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        item = new InvoiceItemDto(
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

        assertEquals(2L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(new QuantityDto("KG", new BigDecimal("4")), item.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("5")), item.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("6"))), item.getTax());
        assertEquals(new MoneyDto("CAD", new BigDecimal("8")), item.getAmount());
        assertEquals(new MaterialDto(7L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 1, 1), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 1, 1), item.getLastUpdated());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(item.getId());
        item.setId(12345L);
        assertEquals(12345L, item.getId());
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
    public void testAccessAmount() {
        assertNull(item.getAmount());
        item.setAmount(new MoneyDto("CAD", new BigDecimal("100")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("100")), item.getAmount());
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
