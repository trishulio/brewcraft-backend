package io.company.brewcraft.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
        procurementItemFactory = ProcurementItemFactory.INSTANCE;
    }

    @Test
    public void testBuildFromLotsAndItems_ReturnsNull_WhenBothLotsAndItemsAreNull() {
        assertNull(procurementItemFactory.buildFromLotsAndItems(null, null));
    }

    @Test
    public void testBuildFromLotsAndItems_ReturnsProcurementItemsWithNullMaterialLots_WhenMaterialLotsAreNull() {
        List<ProcurementItem> procurementItems = procurementItemFactory.buildFromLotsAndItems(null, List.of(new InvoiceItem(1L)));

        assertEquals(List.of(new ProcurementItem(null, new InvoiceItem(1L))), procurementItems);
    }

    @Test
    public void testBuildFromLotsAndItems_ReturnsProcurementItemsWithNullInvoiceItem_WhenInvoiceItemsAreNull() {
        List<ProcurementItem> procurementItems = procurementItemFactory.buildFromLotsAndItems(List.of(new MaterialLot(1L)), null);

        assertEquals(List.of(new ProcurementItem(new MaterialLot(1L), null)), procurementItems);
    }

    @Test
    public void testBuildFromLotsAndItems_ReturnsProcurementItemsWithNullMaterialLot_WhenMaterialLotsAreLessInCount() {
        List<ProcurementItem> procurementItems = procurementItemFactory.buildFromLotsAndItems(
            List.of(new MaterialLot(1L)),
            List.of(new InvoiceItem(10L), new InvoiceItem(20L))
        );

        List<ProcurementItem> expected = List.of(
            new ProcurementItem(new MaterialLot(1L), new InvoiceItem(10L)),
            new ProcurementItem(null, new InvoiceItem(20L))
        );
        assertEquals(expected, procurementItems);
    }

    @Test
    public void testBuildFromLotsAndItems_ReturnsProcurementItemsWithNullInvoiceItems_WhenInvoiceItemsAreLessInCount() {
        List<ProcurementItem> procurementItems = procurementItemFactory.buildFromLotsAndItems(
            List.of(new MaterialLot(1L), new MaterialLot(2L)),
            List.of(new InvoiceItem(10L))
        );

        List<ProcurementItem> expected = List.of(
            new ProcurementItem(new MaterialLot(1L), new InvoiceItem(10L)),
            new ProcurementItem(new MaterialLot(2L), null)
        );
        assertEquals(expected, procurementItems);
    }
}
