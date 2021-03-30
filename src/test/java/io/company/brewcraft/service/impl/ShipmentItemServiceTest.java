package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseShipmentItem;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.UpdateShipmentItem;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ShipmentItemServiceTest {

    private ShipmentItemService service;

    private UtilityProvider mUtilProvider;
    
    @BeforeEach
    public void init() {
        mUtilProvider = mock(UtilityProvider.class);
        doReturn(new Validator()).when(mUtilProvider).getValidator();
        
        service = new ShipmentItemService(mUtilProvider);
    }
    
    @Test
    public void testAddList_ReturnsListOfShipmentItemsWithBaseShipmentValues_WhenItemsAreNotNull() {
        List<BaseShipmentItem> additionItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
     
        List<ShipmentItem> items = service.getAddItems(additionItems);
        
        assertEquals(1, items.size()); // Excludes the null item
        ShipmentItem item = items.iterator().next();
        assertEquals(null, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(null, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(null, item.getCreatedAt());
        assertEquals(null, item.getLastUpdated());
        assertEquals(null, item.getVersion());
    }
    
    @Test
    public void testAddList_ReturnsNull_WhenItemsAreNull() {
        assertNull(service.getAddItems(null));
    }    

    @Test
    public void testPutList_ReturnsUpdatedList_WhenUpdateIsNotNull() {
        List<ShipmentItem> existingItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        List<UpdateShipmentItem> updateItems = List.of(
            new ShipmentItem(null, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, new Material(2L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );

        List<ShipmentItem> items = service.getPutItems(existingItems, updateItems);

        assertEquals(2, items.size());
        Iterator<ShipmentItem> it = items.iterator();

        ShipmentItem item1 = it.next();
        assertEquals(null, item1.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), item1.getQuantity());
        assertEquals(null, item1.getShipment());
        assertEquals(new Material(1L), item1.getMaterial());
        assertEquals(null, item1.getCreatedAt());
        assertEquals(null, item1.getLastUpdated());
        assertEquals(null, item1.getVersion());

        ShipmentItem item2 = it.next();
        assertEquals(1L, item2.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), item2.getQuantity());
        assertEquals(null, item2.getShipment());
        assertEquals(new Material(2L), item2.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item2.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item2.getLastUpdated());
        assertEquals(2, item2.getVersion());
    }
    
    @Test
    public void testPutList_ReturnsEmptyList_WhenUpdateListIsEmpty() {
        List<ShipmentItem> existingItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        assertEquals(Collections.emptyList(), service.getPutItems(existingItems, List.of()));
    }
    
    @Test
    public void testPutList_ReturnsNull_WhenUpdateListIsNull() {
        List<ShipmentItem> existingItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        assertNull(service.getPutItems(existingItems, null));
    }
    
    @Test
    public void testPutList_ThrowsError_WhenUpdateItemsDontHaveExistingId() {
        List<ShipmentItem> existingItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        List<UpdateShipmentItem> updateItems = List.of(
            new ShipmentItem(2L, Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, new Material(2L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        
        assertThrows(ValidationException.class, () -> service.getPutItems(existingItems, updateItems), "1. No existing item found with Id: 2\n");
    }
    
    @Test
    public void testPatchList_ReturnsPatchedList_WhenItemsAreNotNull() {
        List<ShipmentItem> existingItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        List<UpdateShipmentItem> updateItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), null)
        );

        List<ShipmentItem> items = service.getPatchItems(existingItems, updateItems);

        assertEquals(1, items.size());
        Iterator<ShipmentItem> it = items.iterator();
        
        ShipmentItem item = it.next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(null, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(1, item.getVersion());
    }

    @Test
    public void testPatchList_ThrowsValidationException_WhenUpdateItemsDontHaveExistingId() {
        List<ShipmentItem> existingItems = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        List<UpdateShipmentItem> updateItems = List.of(
            new ShipmentItem(2L, Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), null)
        );

        assertThrows(ValidationException.class, () -> service.getPatchItems(existingItems, updateItems), "1. No existing item found with Id: 2\\n");
    }

    @Test
    public void testPutList_ReturnsNull_WhenUpdateItemsAreNull() {
        assertNull(service.getPutItems(List.of(), null));
    }
}
