package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.pojo.Material;

public class InvoiceItemEntityTest {

    private InvoiceItemEntity item;

    @BeforeEach
    public void init() {
        item = new InvoiceItemEntity();
    }

    @Test
    public void testIdConstructor_SetsId() {
        item = new InvoiceItemEntity(1L);
        assertEquals(1L, item.getId());
    }

    @Test
    public void testConstructorWithAllArgs_CallsSetForAllArgs() {
        InvoiceEntity invoice = new InvoiceEntity(2L);
        item = new InvoiceItemEntity(1L, "desc2", invoice, new QuantityEntity(8L), new MoneyEntity(9L), new TaxEntity(10L), new MaterialEntity(7L, null, null, null, null, null, null, null, null), 1);

        assertEquals(1L, item.getId());
        assertEquals("desc2", item.getDescription());
        assertEquals(new InvoiceEntity(2L), item.getInvoice());
        assertEquals(new QuantityEntity(8L), item.getQuantity());
        assertEquals(new MoneyEntity(9L), item.getPrice());
        assertEquals(new TaxEntity(10L), item.getTax());
        assertEquals(new MaterialEntity(7L, null, null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(item.getId());
        item.setId(98765L);
        assertEquals(98765L, item.getId());
    }

    @Test
    public void testAccessDescription() {
        assertNull(item.getDescription());
        item.setDescription("Description 1");
        assertEquals("Description 1", item.getDescription());
    }

    @Test
    public void testAccessTax() {
        assertNull(item.getTax());
        item.setTax(new TaxEntity(5L));
        assertEquals(new TaxEntity(5L), item.getTax());
    }

    @Test
    @Disabled
    public void testAccessMaterial() {
        assertNull(item.getMaterial());
        item.setMaterial(new MaterialEntity(11L, null, null, null, null, null, null, null, null));
        assertEquals(new Material(11L, null, null, null, null, null, null, null, null), item.getMaterial());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(item.getQuantity());
        item.setQuantity(new QuantityEntity(111L));
        assertEquals(new QuantityEntity(111L), item.getQuantity());
    }

    @Test
    public void testAccessInvoice() {
        assertNull(item.getInvoice());
        item.setInvoice(new InvoiceEntity(111L));
        assertEquals(new InvoiceEntity(111L), item.getInvoice());
    }

    @Test
    public void testAccessPrice() {
        assertNull(item.getPrice());
        item.setPrice(new MoneyEntity(222L));
        assertEquals(new MoneyEntity(222L), item.getPrice());
    }

    @Test
    public void testAccessVersion() {
        assertNull(item.getVersion());
        item.setVersion(12345);
        assertEquals(12345, item.getVersion());
    }
}