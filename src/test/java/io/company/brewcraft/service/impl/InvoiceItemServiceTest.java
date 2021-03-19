package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

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
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), 11),
            new InvoiceItem(2L, "New_Description_2", Quantities.getQuantity(new BigDecimal("21.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 201"), null, new Material(21L), 21)
        );

        List<InvoiceItem> updatedItems = service.mergePut(new Validator(), existingItems, itemUpdates);
        
        assertEquals(1L, updatedItems.get(0).getId());
        assertEquals("New_Description_1", updatedItems.get(0).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11.00"), SupportedUnits.KILOGRAM), updatedItems.get(0).getQuantity());
        assertEquals(Money.parse("CAD 101"), updatedItems.get(0).getPrice());
        assertEquals(null, updatedItems.get(0).getTax());
        assertEquals(new Material(11L), updatedItems.get(0).getMaterial());
        assertEquals(11, updatedItems.get(0).getVersion());

        assertEquals(2L, updatedItems.get(1).getId());
        assertEquals("New_Description_2", updatedItems.get(1).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("21.00"), SupportedUnits.KILOGRAM), updatedItems.get(1).getQuantity());
        assertEquals(Money.parse("CAD 201"), updatedItems.get(1).getPrice());
        assertEquals(null, updatedItems.get(1).getTax());
        assertEquals(new Material(21L), updatedItems.get(1).getMaterial());
        assertEquals(21, updatedItems.get(1).getVersion());
    }

    @Test
    public void testMergePut_ReturnsNewItemsWithExistingItemsUpdatedAndNewItemsAdded_WhenPayloadObjectsDoNotHaveIds() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 101"), null, new Material(11L), 11),
            new InvoiceItem(null, "Description_2", Quantities.getQuantity(new BigDecimal("20.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );

        List<InvoiceItem> updatedItems = service.mergePut(new Validator(), existingItems, itemUpdates);

        assertEquals(1L, updatedItems.get(0).getId());
        assertEquals("New_Description_1", updatedItems.get(0).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11.00"), SupportedUnits.KILOGRAM), updatedItems.get(0).getQuantity());
        assertEquals(Money.parse("CAD 101"), updatedItems.get(0).getPrice());
        assertEquals(null, updatedItems.get(0).getTax());
        assertEquals(new Material(11L), updatedItems.get(0).getMaterial());
        assertEquals(11, updatedItems.get(0).getVersion());

        assertEquals(null, updatedItems.get(1).getId());
        assertEquals("Description_2", updatedItems.get(1).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), SupportedUnits.KILOGRAM), updatedItems.get(1).getQuantity());
        assertEquals(Money.parse("CAD 200"), updatedItems.get(1).getPrice());
        assertEquals(new Tax(), updatedItems.get(1).getTax());
        assertEquals(new Material(20L), updatedItems.get(1).getMaterial());
        assertEquals(null, updatedItems.get(1).getVersion());
    }
    
    @Test
    public void testMergePut_ReturnsNewItemsWithExistingItemRemoved_WhenExistingItemDoesNotExistInPayloadObjects() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        List<UpdateInvoiceItem> itemUpdates = List.of();

        List<InvoiceItem> updatedItems = service.mergePut(new Validator(), existingItems, itemUpdates);

        assertEquals(0, updatedItems.size());
    }
    
    @Test
    public void testMergePut_AddsValidatorException_WhenPayloadObjectIdsDoNotExistInExistingItems() {
        Validator validator = new Validator();
        
        List<InvoiceItem> existingItems = List.of();
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L),
            new InvoiceItem(2L),
            new InvoiceItem()
        );
        
        List<InvoiceItem> updatedItems = service.mergePut(validator, existingItems, itemUpdates);
        
        assertEquals(1, updatedItems.size());
        
        assertThrows(ValidationException.class, () -> validator.raiseErrors(), "1. No existing invoice item found with Id: 1. To add a new item to the invoice, don't include the version and id in the payload.\n2. No existing invoice item found with Id: 2. To add a new item to the invoice, don't include the version and id in the payload.\n 3. No existing invoice item found with Id: 3. To add a new item to the invoice, don't include the version and id in the payload.");
    }
    
    @Test
    public void testMergePatch_ReturnsNewItemsListWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "New_Description_1", Quantities.getQuantity(new BigDecimal("11.00"), SupportedUnits.KILOGRAM), null, new Tax(Money.parse("CAD 100")), null, 11)
        );

        List<InvoiceItem> updatedItems = service.mergePatch(new Validator(), existingItems, itemUpdates);
        
        assertEquals(1L, updatedItems.get(0).getId());
        assertEquals("New_Description_1", updatedItems.get(0).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("11.00"), SupportedUnits.KILOGRAM), updatedItems.get(0).getQuantity());
        assertEquals(Money.parse("CAD 100"), updatedItems.get(0).getPrice());
        assertEquals(new Tax(Money.parse("CAD 100")), updatedItems.get(0).getTax());
        assertEquals(new Material(10L), updatedItems.get(0).getMaterial());
        assertEquals(11, updatedItems.get(0).getVersion());
    }
    
    @Test
    public void testMergePatch_AddsValidationException_WhenPayloadObjectsIdDoNotExistInExistingItems() {
        List<InvoiceItem> existingItems = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1)
        );
        
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(2L),
            new InvoiceItem(3L)
        );

        Validator validator = new Validator();
        List<InvoiceItem> updatedItems = service.mergePatch(validator, existingItems, itemUpdates);

        assertEquals(0, updatedItems.size());
        assertThrows(ValidationException.class, () -> validator.raiseErrors(), "1. No existing invoice item found with Id: 2.\n2. No existing invoice item found with Id: 3.");
    }
    
    @Test
    public void testAddList_ReturnsListOfBaseItems_WhenInputIsNotNull() {
        List<UpdateInvoiceItem> itemUpdates = List.of(
            new InvoiceItem(1L, "Description_1", Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 100"), new Tax(), new Material(10L), 1),
            new InvoiceItem(2L, "Description_2", Quantities.getQuantity(new BigDecimal("20.00"), SupportedUnits.KILOGRAM), Money.parse("CAD 200"), new Tax(), new Material(20L), 2)
        );

        List<InvoiceItem> items = service.addList(new Validator(), itemUpdates);
        
        assertEquals(2, items.size());

        assertEquals(null, items.get(0).getId());
        assertEquals("Description_1", items.get(0).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), items.get(0).getQuantity());
        assertEquals(Money.parse("CAD 100"), items.get(0).getPrice());
        assertEquals(new Tax(), items.get(0).getTax());
        assertEquals(new Material(10L), items.get(0).getMaterial());
        assertEquals(null, items.get(0).getVersion());

        assertEquals(null, items.get(1).getId());
        assertEquals("Description_2", items.get(1).getDescription());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), SupportedUnits.KILOGRAM), items.get(1).getQuantity());
        assertEquals(Money.parse("CAD 200"), items.get(1).getPrice());
        assertEquals(new Tax(), items.get(1).getTax());
        assertEquals(new Material(20L), items.get(1).getMaterial());
        assertEquals(null, items.get(1).getVersion());
    }
}
