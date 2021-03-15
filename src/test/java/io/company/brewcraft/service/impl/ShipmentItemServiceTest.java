package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.pojo.BaseShipmentItem;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.pojo.ShipmentItem;
import io.company.brewcraft.pojo.UpdateShipmentItem;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

public class ShipmentItemServiceTest {

    private ShipmentItemService service;

    private Validator validator;
    
    @BeforeEach
    public void init() {
        service = new ShipmentItemService();
        validator = new Validator();
    }
    
    @Test
    public void testAddList_ReturnsListOfShipmentItemsWithBaseShipmentValues_WhenItemsAreNotNull() {
        Collection<BaseShipmentItem> additionItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            null
        );
     
        Collection<ShipmentItem> items = service.getAddItems(validator, additionItems);
        
        assertEquals(1, items.size()); // Excludes the null item
        ShipmentItem item = items.iterator().next();
        assertEquals(null, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), item.getQuantity());
        assertEquals(null, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(null, item.getCreatedAt());
        assertEquals(null, item.getLastUpdated());
        assertEquals(null, item.getVersion());
    }
    
    @Test
    public void testAddList_ReturnsNull_WhenItemsAreNull() {
        assertNull(service.getAddItems(validator, null));
    }    

    @Test
    public void testPutList_ReturnsUpdatedList_WhenUpdateIsNotNull() {
        Collection<ShipmentItem> existingItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        Collection<UpdateShipmentItem> updateItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), null, new Material(2L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2),
            new ShipmentItem(null, Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            null
        );
        
        Collection<ShipmentItem> items = service.getPutList(validator, existingItems, updateItems);
        

        assertEquals(2, items.size());
        Iterator<ShipmentItem> it = items.iterator();
        
        ShipmentItem item1 = it.next();
        assertEquals(2L, item1.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), item1.getQuantity());
        assertEquals(null, item1.getShipment());
        assertEquals(new Material(2L), item1.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item1.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item1.getLastUpdated());
        assertEquals(2, item1.getVersion());
        
        ShipmentItem item2 = it.next();
        assertEquals(null, item2.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), item2.getQuantity());
        assertEquals(null, item2.getShipment());
        assertEquals(new Material(1L), item2.getMaterial());
        assertEquals(null, item2.getCreatedAt());
        assertEquals(null, item2.getLastUpdated());
        assertEquals(null, item2.getVersion());
    }
    
    @Test
    public void testPutList_ThrowsError_WhenUpdateItemsDontHaveExistingId() {
        Collection<ShipmentItem> existingItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        Collection<UpdateShipmentItem> updateItems = Set.of(
            new ShipmentItem(2L, Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), null, new Material(2L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        
        assertThrows(ValidationException.class, () -> service.getPutList(validator, existingItems, updateItems), "1. No existing item found with Id: 2\n");
    }
    
    @Test
    public void testPatchList_ReturnsPatchedList_WhenItemsAreNotNull() {
        Collection<ShipmentItem> existingItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        Collection<UpdateShipmentItem> updateItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), null, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), null),
            null
        );

        Collection<ShipmentItem> items = service.getPatchList(validator, existingItems, updateItems);

        assertEquals(2, items.size());
        Iterator<ShipmentItem> it = items.iterator();
        
        ShipmentItem item = it.next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), item.getQuantity());
        assertEquals(null, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testPatchList_ThrowsValidationException_WhenUpdateItemsDontHaveExistingId() {
        Collection<ShipmentItem> existingItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10.00"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        Collection<UpdateShipmentItem> updateItems = Set.of(
            new ShipmentItem(2L, Quantities.getQuantity(new BigDecimal("20.00"), Units.KILOGRAM), null, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), null),
            null
        );

        assertThrows(ValidationException.class, () -> service.getPatchList(validator, existingItems, updateItems), "1. No existing item found with Id: 2\\n");
    }

    @Test
    public void testPutList_ReturnsNull_WhenUpdateItemsAreNull() {
        assertNull(service.getPutList(validator, Set.of(), null));
    }
}
