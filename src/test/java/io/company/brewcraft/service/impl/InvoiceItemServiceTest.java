package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceItemServiceTest {

    private InvoiceItemService service;
    private UpdateService<Long, InvoiceItem, BaseInvoiceItem<?>, UpdateInvoiceItem<?>> mUpdateService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.service = new InvoiceItemService(this.mUpdateService);
    }

    @Test
    public void testGetPutItems_ReturnsNewItemsWithExistingItemsUpdated_WhenPayloadObjectsHaveIds() {
        final List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        final List<UpdateInvoiceItem<?>> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        doAnswer(inv -> inv.getArgument(1, List.class)).when(this.mUpdateService).getPutEntities(existingItems, itemUpdates);

        final List<InvoiceItem> updatedItems = this.service.getPutEntities(existingItems, itemUpdates);

        final List<UpdateInvoiceItem<?>> expected = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        assertEquals(expected, updatedItems);
    }

    @Test
    public void testGetPatchItems_ReturnsNewItemsCollectionWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        final List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        final List<UpdateInvoiceItem<?>> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        doAnswer(inv -> inv.getArgument(1, List.class)).when(this.mUpdateService).getPatchEntities(existingItems, itemUpdates);

        final List<InvoiceItem> updatedItems = this.service.getPatchEntities(existingItems, itemUpdates);

        final List<UpdateInvoiceItem<?>> expected = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        assertEquals(expected, updatedItems);
    }

    @Test
    public void testAddCollection_ReturnsCollectionOfBaseItems_WhenInputIsNotNull() {
        final List<BaseInvoiceItem<?>> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        doAnswer(inv -> inv.getArgument(0, List.class)).when(this.mUpdateService).getAddEntities(itemUpdates);

        final List<InvoiceItem> updatedItems = this.service.getAddEntities(itemUpdates);

        final List<BaseInvoiceItem<?>> expected = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(1999, 1, 1, 1, 1), 2)
        );

        assertEquals(expected, updatedItems);
    }
}
