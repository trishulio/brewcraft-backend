package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.ProcurementItemId;

public class ProcurementItemIdTest {
    private ProcurementItemId id;

    @BeforeEach
    public void init() {
        id = new ProcurementItemId();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(id.getInvoiceItemId());
        assertNull(id.getLotId());
    }

    @Test
    public void testAllArgConstructor() {
        id = new ProcurementItemId(1L, 10L);

        assertEquals(1L, id.getLotId());
        assertEquals(10L, id.getInvoiceItemId());
    }

    @Test
    public void testAccessInvoiceItemId() {
        id.setInvoiceItemId(1L);
        assertEquals(1L, id.getInvoiceItemId());
    }

    @Test
    public void testAccessLotId() {
        id.setLotId(10L);
        assertEquals(10L, id.getLotId());
    }
}
