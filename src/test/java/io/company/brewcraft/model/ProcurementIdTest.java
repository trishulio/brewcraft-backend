package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.ProcurementId;

public class ProcurementIdTest {

    private ProcurementId id;

    @BeforeEach
    public void init() {
        id = new ProcurementId();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(id.getInvoiceId());
        assertNull(id.getShipmentId());
    }

    @Test
    public void testAllArgConstructor() {
        id = new ProcurementId(1L, 10L);

        assertEquals(1L, id.getShipmentId());
        assertEquals(10L, id.getInvoiceId());
    }

    @Test
    public void testAccessInvoiceId() {
        id.setInvoiceId(1L);
        assertEquals(1L, id.getInvoiceId());
    }

    @Test
    public void testAccessShipmentId() {
        id.setShipmentId(10L);
        assertEquals(10L, id.getShipmentId());
    }
}
