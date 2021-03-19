package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.Tax;
import io.company.brewcraft.pojo.UpdateInvoiceItem;
import io.company.brewcraft.service.InvoiceItemService;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class InvoiceItemServiceTest {

    private InvoiceItemService service;
    
    @BeforeEach
    public void init() {
        service = new InvoiceItemService();
    }
    
    @Test
    public void testMergePut_ReturnsNewItemsWithExistingItemsUpdated_WhenPayloadObjectsHaveIds() {
        Collection<InvoiceItem> existingItems = Set.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );
        
        Collection<UpdateInvoiceItem> itemUpdates = Set.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11.00"), Units.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), 11),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21.00"), Units.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), 21)
        );

        Collection<InvoiceItem> updatedItems = service.getPutCollection(new Validator(), existingItems, itemUpdates);
        Iterator<InvoiceItem> it = updatedItems.iterator();
        
        InvoiceItem item1 = it.next();
        assertEquals(1L, item1.getId());
        assertEquals("New_Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11.00"), Units.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 101"), item1.getPrice());
        assertEquals(null, item1.getTax());
        assertEquals(new Material(11L), item1.getMaterial());
        assertEquals(11, item1.getVersion());

        InvoiceItem item2 = it.next();
        assertEquals(2L, item2.getId());
        assertEquals("New_Description_2", item2.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("21.00"), Units.KILOGRAM), item2.getQuantity());
        assertEquals(Money.parse("CAD 201"), item2.getPrice());
        assertEquals(null, item2.getTax());
        assertEquals(new Material(21L), item2.getMaterial());
        assertEquals(21, item2.getVersion());
    }

    @Test
    public void testMergePut_ReturnsNewItemsWithExistingItemsUpdatedAndNewItemsAdded_WhenPayloadObjectsDoNotHaveIds() {
        Collection<InvoiceItem> existingItems = Set.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        Collection<UpdateInvoiceItem> itemUpdates = Set.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11.00"), Units.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), 11),
            new InvoiceItem(null, "Description_2", Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );

        Collection<InvoiceItem> updatedItems = service.getPutCollection(new Validator(), existingItems, itemUpdates);
        Iterator<InvoiceItem> it = updatedItems.iterator();
        
        InvoiceItem item1 = it.next();
        assertEquals(1L, item1.getId());
        assertEquals("New_Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11.00"), Units.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 101"), item1.getPrice());
        assertEquals(null, item1.getTax());
        assertEquals(new Material(11L), item1.getMaterial());
        assertEquals(11, item1.getVersion());

        InvoiceItem item2 = it.next();
        assertEquals(null, item2.getId());
        assertEquals("Description_2", item2.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), item2.getQuantity());
        assertEquals(Money.parse("CAD 200"), item2.getPrice());
        assertEquals(new Tax(), item2.getTax());
        assertEquals(new Material(20L), item2.getMaterial());
        assertEquals(null, item2.getVersion());
    }
    
    @Test
    public void testMergePut_ReturnsNewItemsWithExistingItemRemoved_WhenExistingItemDoesNotExistInPayloadObjects() {
        Collection<InvoiceItem> existingItems = Set.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        Collection<UpdateInvoiceItem> itemUpdates = Set.of();

        Collection<InvoiceItem> updatedItems = service.getPutCollection(new Validator(), existingItems, itemUpdates);

        assertEquals(0, updatedItems.size());
    }
    
    @Test
    public void testMergePut_AddsValidatorException_WhenPayloadObjectIdsDoNotExistInExistingItems() {
        Validator validator = new Validator();
        
        Collection<InvoiceItem> existingItems = Set.of();
        
        Collection<UpdateInvoiceItem> itemUpdates = Set.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L),
            new InvoiceItem()
        );
        
        Collection<InvoiceItem> updatedItems = service.getPutCollection(validator, existingItems, itemUpdates);
        
        assertEquals(1, updatedItems.size());
        
        assertThrows(ValidationException.class, () -> validator.raiseErrors(), "1. No existing invoice item found with Id: 1. To add a new item to the invoice, don't include the version and id in the payload.\n2. No existing invoice item found with Id: 2. To add a new item to the invoice, don't include the version and id in the payload.\n 3. No existing invoice item found with Id: 3. To add a new item to the invoice, don't include the version and id in the payload.");
    }
    
    @Test
    public void testMergePatch_ReturnsNewItemsCollectionWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        Collection<InvoiceItem> existingItems = Set.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        Collection<UpdateInvoiceItem> itemUpdates = Set.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11.00"), Units.KILOGRAM), null, new Tax(Money.parse("CAD 100")), null, 11)
        );

        Collection<InvoiceItem> updatedItems = service.getPatchCollection(new Validator(), existingItems, itemUpdates);
        Iterator<InvoiceItem> it = updatedItems.iterator();
        
        InvoiceItem item1 = it.next();
        assertEquals(1L, item1.getId());
        assertEquals("New_Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11.00"), Units.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 100"), item1.getPrice());
        assertEquals(new Tax(Money.parse("CAD 100")), item1.getTax());
        assertEquals(new Material(10L), item1.getMaterial());
        assertEquals(11, item1.getVersion());
    }
    
    @Test
    public void testMergePut_ReturnsNull_WhenUpdateItemsAreNull() {
        fail("Not implemented yet");
    }
    
    @Test
    public void testMergePatch_AddsValidationException_WhenPayloadObjectsIdDoNotExistInExistingItems() {
        Collection<InvoiceItem> existingItems = Set.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        Collection<UpdateInvoiceItem> itemUpdates = Set.of(
            new InvoiceItem(2L),
            new InvoiceItem(3L)
        );

        Validator validator = new Validator();
        Collection<InvoiceItem> updatedItems = service.getPatchCollection(validator, existingItems, itemUpdates);

        assertEquals(0, updatedItems.size());
        assertThrows(ValidationException.class, () -> validator.raiseErrors(), "1. No existing invoice item found with Id: 2.\n2. No existing invoice item found with Id: 3.");
    }
    
    @Test
    public void testAddCollection_ReturnsCollectionOfBaseItems_WhenInputIsNotNull() {
        Collection<UpdateInvoiceItem> itemUpdates = Set.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );

        Collection<InvoiceItem> items = service.getAddCollection(new Validator(), itemUpdates);
        assertEquals(2, items.size());        
        Iterator<InvoiceItem> it = items.iterator();

        InvoiceItem item1 = it.next();
        assertEquals(null, item1.getId());
        assertEquals("Description_1", item1.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), item1.getQuantity());
        assertEquals(Money.parse("CAD 100"), item1.getPrice());
        assertEquals(new Tax(), item1.getTax());
        assertEquals(new Material(10L), item1.getMaterial());
        assertEquals(null, item1.getVersion());

        InvoiceItem item2 = it.next();
        assertEquals(null, item2.getId());
        assertEquals("Description_2", item2.getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), item2.getQuantity());
        assertEquals(Money.parse("CAD 200"), item2.getPrice());
        assertEquals(new Tax(), item2.getTax());
        assertEquals(new Material(20L), item2.getMaterial());
        assertEquals(null, item2.getVersion());
    }
}
