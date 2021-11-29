package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.service.impl.procurement.ProcurementItemFactory;

public class ProcurementItemFactoryTest {
    private ProcurementItemFactory procurementItemFactory;

    @BeforeEach
    public void init() {
        procurementItemFactory = new ProcurementItemFactory();
    }

    @Test
    public void testBuild_ReturnsNull_WhenInvoiceItemAndLotAreNull() {
        assertNull(procurementItemFactory.buildFromLot(null));
    }

    @Test
    public void testBuild_ReturnsProcurementItem_WhenInvoiceOrLotIsNotNull() {
        MaterialLot lot = new MaterialLot(1L);
        lot.setInvoiceItem(new InvoiceItem(10L));

        ProcurementItem procurementItem = procurementItemFactory.buildFromLot(lot);

        ProcurementItem expected = new ProcurementItem(new MaterialLot(1L), new InvoiceItem(10L));
        assertEquals(expected, procurementItem);
    }
}
