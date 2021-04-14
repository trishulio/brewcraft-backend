package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceItemServiceTest {

    private InvoiceItemService service;
    
    private UtilityProvider mUtilProvider;
    
    @BeforeEach
    public void init() {
        mUtilProvider = mock(UtilityProvider.class);
        doReturn(new Validator()).when(mUtilProvider).getValidator();
        
        service = new InvoiceItemService(mUtilProvider);
    }
    
    @Test
    public void testGetPutItems_ReturnsNewItemsWithExistingItemsUpdated_WhenPayloadObjectsHaveIds() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), 1),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), 2)
        );

        List<InvoiceItem> updatedItems = service.getPutItems(existingItems, itemUpdates);
        Iterator<InvoiceItem> it = updatedItems.iterator();
        
        InvoiceItem item1 = it.next();
        assertEquals(1L, item1.getId());
        assertEquals("New_Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 101"), item1.getPrice());
        assertEquals(null, item1.getTax());
        assertEquals(new Material(11L), item1.getMaterial());
        assertEquals(1, item1.getVersion());

        InvoiceItem item2 = it.next();
        assertEquals(2L, item2.getId());
        assertEquals("New_Description_2", item2.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("21"), SupportedUnits.KILOGRAM), item2.getQuantity());
        assertEquals(Money.parse("CAD 201"), item2.getPrice());
        assertEquals(null, item2.getTax());
        assertEquals(new Material(21L), item2.getMaterial());
        assertEquals(2, item2.getVersion());
    }

    @Test
    public void testGetPutItems_ReturnsNewItemsWithExistingItemsUpdatedAndNewItemsAdded_WhenPayloadObjectsDoNotHaveIds() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );

        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), 1),
            new InvoiceItem(null, "Description_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), null)
        );

        List<InvoiceItem> updatedItems = service.getPutItems(existingItems, itemUpdates);
        Iterator<InvoiceItem> it = updatedItems.iterator();

        InvoiceItem item1 = it.next();
        assertEquals(1L, item1.getId());
        assertEquals("New_Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 101"), item1.getPrice());
        assertEquals(null, item1.getTax());
        assertEquals(new Material(11L), item1.getMaterial());
        assertEquals(1, item1.getVersion());

        InvoiceItem item2 = it.next();
        assertEquals(null, item2.getId());
        assertEquals("Description_2", item2.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), item2.getQuantity());
        assertEquals(Money.parse("CAD 200"), item2.getPrice());
        assertEquals(new Tax(), item2.getTax());
        assertEquals(new Material(20L), item2.getMaterial());
        assertEquals(null, item2.getVersion());
    }

    @Test
    public void testGetPutItems_ReturnsNewItemsWithExistingItemRemoved_WhenExistingItemDoesNotExistInPayloadObjects() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        List<UpdateInvoiceItem> itemUpdates = List.of();

        List<InvoiceItem> updatedItems = service.getPutItems(existingItems, itemUpdates);

        assertEquals(0, updatedItems.size());
    }
    
    @Test
    public void testGetPutItems_AddsValidatorException_WhenPayloadObjectIdsDoNotExistInExistingItems() {
        List<InvoiceItem> existingItems = List.of();
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L),
            new InvoiceItem()
        );
        
        List<InvoiceItem> updatedItems = service.getPutItems(existingItems, itemUpdates);
        
        assertEquals(1, updatedItems.size());
        
        assertThrows(ValidationException.class, () -> mUtilProvider.getValidator().raiseErrors(), "1. No existing invoice item found with Id: 1. To add a new item to the invoice, don't include the version and id in the payload.\n2. No existing invoice item found with Id: 2. To add a new item to the invoice, don't include the version and id in the payload.\n 3. No existing invoice item found with Id: 3. To add a new item to the invoice, don't include the version and id in the payload.");
    }

    @Test
    public void testGetPutItems_ThrowsOptimisticLockingException_WhenExistingItemVersionIsDifferentFromUpdateVersion() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L)
        );
        existingItems.get(0).setVersion(1);
        existingItems.get(1).setVersion(1);

        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L)
        );

        itemUpdates.get(0).setVersion(1);
        itemUpdates.get(1).setVersion(2);
        
        assertThrows(OptimisticLockException.class, () -> service.getPutItems(existingItems, itemUpdates), "Cannot update entity with Id: 2 of version: 1 with payload of version: 2");
    }

    @Test
    public void testGetPatchItems_ReturnsNewItemsCollectionWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), null, new Tax(Money.parse("CAD 100")), null, 1)
        );

        List<InvoiceItem> updatedItems = service.getPatchItems(existingItems, itemUpdates);
        Iterator<InvoiceItem> it = updatedItems.iterator();
        
        InvoiceItem item1 = it.next();
        assertEquals(1L, item1.getId());
        assertEquals("New_Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11"), SupportedUnits.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 100"), item1.getPrice());
        assertEquals(new Tax(Money.parse("CAD 100")), item1.getTax());
        assertEquals(new Material(10L), item1.getMaterial());
        assertEquals(1, item1.getVersion());
    }

    @Test
    public void testGetPutItems_ReturnsNull_WhenUpdateItemsAreNull() {
        assertNull(service.getPutItems(List.of(), null));
    }
    
    @Test
    public void testGetPatchItems_AddsValidationException_WhenPayloadObjectsIdDoNotExistInExistingItems() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(2L),
            new InvoiceItem(3L)
        );

        assertThrows(ValidationException.class, () -> service.getPatchItems(existingItems, itemUpdates), "1. No existing invoice item found with Id: 2.\n2. No existing invoice item found with Id: 3.");
    }
    
    @Test
    public void testGetPatchItems_ReturnsExistingItems_WhenPatchItemsAreNull() {
        List<InvoiceItem> existingItems = List.of(new InvoiceItem(1L));
        assertEquals(List.of(new InvoiceItem(1L)), service.getPatchItems(existingItems, null));
    }

    @Test
    public void testGetPatchItems_ThrowsOptimisticLockingException_WhenExistingItemVersionIsDifferentFromUpdateVersion() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L)
        );
        existingItems.get(0).setVersion(1);
        existingItems.get(1).setVersion(1);

        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L)
        );

        itemUpdates.get(0).setVersion(1);
        itemUpdates.get(1).setVersion(2);
        
        assertThrows(OptimisticLockException.class, () -> service.getPatchItems(existingItems, itemUpdates), "Cannot update entity with Id: 2 of version: 1 with payload of version: 2");
    }

    @Test
    public void testAddCollection_ReturnsCollectionOfBaseItems_WhenInputIsNotNull() {
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );

        List<InvoiceItem> items = service.getAddItems(itemUpdates);
        assertEquals(2, items.size());        
        Iterator<InvoiceItem> it = items.iterator();

        InvoiceItem item1 = it.next();
        assertEquals(null, item1.getId());
        assertEquals("Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 100"), item1.getPrice());
        assertEquals(new Tax(), item1.getTax());
        assertEquals(new Material(10L), item1.getMaterial());
        assertEquals(null, item1.getVersion());

        InvoiceItem item2 = it.next();
        assertEquals(null, item2.getId());
        assertEquals("Description_2", item2.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), item2.getQuantity());
        assertEquals(Money.parse("CAD 200"), item2.getPrice());
        assertEquals(new Tax(), item2.getTax());
        assertEquals(new Material(20L), item2.getMaterial());
        assertEquals(null, item2.getVersion());
    }
}
