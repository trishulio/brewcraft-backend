package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MaterialLotServiceTest {

    private MaterialLotService service;

    private UtilityProvider mUtilProvider;
    
    @BeforeEach
    public void init() {
        mUtilProvider = mock(UtilityProvider.class);
        doReturn(new Validator()).when(mUtilProvider).getValidator();
        
        service = new MaterialLotService(mUtilProvider);
    }

    @Test
    public void testGetAddLots_ReturnsListOfMaterialLotsWithBaseShipmentValues_WhenLotsAreNotNull() {
        List<BaseMaterialLot> additionLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L),LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
     
        List<MaterialLot> lots = service.getAddLots(additionLots);
        
        assertEquals(1, lots.size()); // Excludes the null lot
        MaterialLot lot = lots.iterator().next();
        assertEquals(null, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(null, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(null, lot.getCreatedAt());
        assertEquals(null, lot.getLastUpdated());
        assertEquals(null, lot.getVersion());
    }
    
    @Test
    public void testGetAddLots_ReturnsNull_WhenLotsAreNull() {
        assertNull(service.getAddLots(null));
    }    

    @Test
    public void testGetPutLots_ReturnsUpdatedList_WhenUpdateIsNotNull() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        List<UpdateMaterialLot> updateLots = List.of(
            new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2),
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new Material(2L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );

        List<MaterialLot> lots = service.getPutLots(existingLots, updateLots);

        assertEquals(2, lots.size());
        Iterator<MaterialLot> it = lots.iterator();

        MaterialLot lot1 = it.next();
        assertEquals(null, lot1.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), lot1.getQuantity());
        assertEquals(null, lot1.getShipment());
        assertEquals(new Material(1L), lot1.getMaterial());
        assertEquals(null, lot1.getCreatedAt());
        assertEquals(null, lot1.getLastUpdated());
        assertEquals(null, lot1.getVersion());

        MaterialLot lot2 = it.next();
        assertEquals(1L, lot2.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), lot2.getQuantity());
        assertEquals(null, lot2.getShipment());
        assertEquals(new Material(2L), lot2.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot2.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot2.getLastUpdated());
        assertEquals(1, lot2.getVersion());
    }
    
    @Test
    public void testGetPutLots_ReturnsEmptyList_WhenUpdateListIsEmpty() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        assertEquals(Collections.emptyList(), service.getPutLots(existingLots, List.of()));
    }
    
    @Test
    public void testGetPutLots_ReturnsNull_WhenUpdateListIsNull() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        assertNull(service.getPutLots(existingLots, null));
    }
    
    @Test
    public void testGetPutLots_ThrowsError_WhenUpdateLotsDontHaveExistingId() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );
        
        List<UpdateMaterialLot> updateLots = List.of(
            new MaterialLot(2L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new Material(2L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        
        assertThrows(ValidationException.class, () -> service.getPutLots(existingLots, updateLots), "1. No existing lot found with Id: 2\n");
    }

    @Test
    public void testGetPutLots_ThrowsOptimisticLockingException_WhenExistingLotVersionIsDifferentFromUpdateVersion() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );
        existingLots.get(0).setVersion(1);
        existingLots.get(1).setVersion(1);

        List<MaterialLot> lotUpdates = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        lotUpdates.get(0).setVersion(1);
        lotUpdates.get(1).setVersion(2);
        
        assertThrows(OptimisticLockException.class, () -> service.getPutLots(existingLots, lotUpdates), "Cannot update entity with Id: 2 of version: 1 with payload of version: 2");
    }

    @Test
    public void testGetPatchLots_ReturnsPatchedList_WhenLotsAreNotNull() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        List<UpdateMaterialLot> updateLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );

        List<MaterialLot> lots = service.getPatchLots(existingLots, updateLots);

        assertEquals(1, lots.size());
        Iterator<MaterialLot> it = lots.iterator();
        
        MaterialLot lot = it.next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(null, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(1, lot.getVersion());
    }

    @Test
    public void testGetPatchLots_ThrowsValidationException_WhenUpdateLotsDontHaveExistingId() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        List<UpdateMaterialLot> updateLots = List.of(
            new MaterialLot(2L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), null)
        );

        assertThrows(ValidationException.class, () -> service.getPatchLots(existingLots, updateLots), "1. No existing lot found with Id: 2\\n");
    }

    @Test
    public void testGetPatchLots_ReturnsExistingLots_WhenUpdateLotsAreNull() {
        List<MaterialLot> existingLots = List.of(new MaterialLot(1L));
        assertEquals(List.of(new MaterialLot(1l)), service.getPatchLots(existingLots, null));
    }
    
    @Test
    public void testGetPatchLots_ThrowsOptimisticLockingException_WhenExistingLotVersionIsDifferentFromUpdateVersion() {
        List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );
        existingLots.get(0).setVersion(1);
        existingLots.get(1).setVersion(1);

        List<MaterialLot> lotUpdates = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        lotUpdates.get(0).setVersion(1);
        lotUpdates.get(1).setVersion(2);
        
        assertThrows(OptimisticLockException.class, () -> service.getPatchLots(existingLots, lotUpdates), "Cannot update entity with Id: 2 of version: 1 with payload of version: 2");
    }
}
