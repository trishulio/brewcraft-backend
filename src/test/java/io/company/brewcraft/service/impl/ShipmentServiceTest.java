package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ShipmentServiceTest {

    private ShipmentService service;
    
    private ShipmentRepository mRepo;
    private MaterialLotService mLotService;

    @BeforeEach
    public void init() {
        mRepo = mock(ShipmentRepository.class);
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mRepo).saveAndFlush(any(Shipment.class));
        doAnswer(i -> {
            Long invoiceId = i.getArgument(0, Long.class);
            Shipment shipment = i.getArgument(1, Shipment.class);
            shipment.setInvoice(new Invoice(invoiceId));
            return null;
        }).when(mRepo).refresh(anyLong(), any(Shipment.class));
        
        mLotService = mock(MaterialLotService.class);

        service = new ShipmentService(mRepo, mLotService);
    }
    
    @Test
    public void testGetShipment_ReturnsShipmentInstance_WhenItExistsInRepository() {
        doReturn(Optional.of(new Shipment(1L))).when(mRepo).findById(1L);
        
        Shipment shipment = service.getShipment(1L);
        
        assertEquals(new Shipment(1L), shipment);
    }

    @Test
    public void testGetShipment_ReturnsNull_WhenRepositoryDoesNotHaveIt() {
        doReturn(Optional.empty()).when(mRepo).findById(1L);
        Shipment shipment = service.getShipment(1L);
        
        assertNull(shipment);
    }
    
    @Test
    @Disabled("TODO: Find a good strategy to test get method with long list of specifications")
    public void testGetShipments() {
        fail("Not tested");
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(mRepo).existsByIds(Set.of(1L, 2L, 3L));
        
        boolean exists = service.existsByIds(Set.of(1L, 2L, 3L));
        
        assertTrue(exists);
    }
    
    @Test
    public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(mRepo).existsByIds(Set.of(1L, 2L, 3L));
        
        boolean exists = service.existsByIds(Set.of(1L, 2L, 3L));
        
        assertFalse(exists);
    }
    
    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        service.delete(Set.of(1L, 2L, 3L));
        
        verify(mRepo, times(1)).deleteByIds(Set.of(1L, 2L, 3L));
    }
    
    @Test
    public void testAdd_RetainsBaseValuesAndAddsShipmentToRepository_WhenObjectIsNotNull() {
        List<MaterialLot> additionLots = new ArrayList<>();
        additionLots.add(new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment addition = new Shipment(
            1L,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            additionLots,
            1
        );
        
        doReturn(List.of(
            new MaterialLot(null, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), null, null, null)
        )).when(mLotService).getAddLots(additionLots);

        Shipment shipment = service.add(2L, addition);
        
        assertEquals(null, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(new Invoice(2L), shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(null, shipment.getCreatedAt());
        assertEquals(null, shipment.getLastUpdated());
        assertEquals(1, shipment.getLots().size());
        assertEquals(null, shipment.getVersion());
        MaterialLot lot = shipment.getLots().iterator().next();
        assertEquals(null, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(null, lot.getCreatedAt());
        assertEquals(null, lot.getLastUpdated());
        assertEquals(null, lot.getVersion());
        
        verify(mRepo, times(1)).saveAndFlush(shipment);
        verify(mRepo, times(1)).refresh(eq(2L), any(Shipment.class));
        verify(mLotService, times(1)).getAddLots(anyList());
    }

    @Test
    public void testPut_OverridesAnExistingShipmentEntity_WhenInputIsNotNull() {
        List<MaterialLot> existingLots = new ArrayList<>();
        existingLots.add(new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment existing = new Shipment(
            1L,
            "SHIPMENT_0",
            "LOT_0",
            "DESCRIPTION_0",
            new ShipmentStatus("IN-TRANSIT"),
            null,
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            existingLots,
            1
        );
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        List<MaterialLot> updateLots = List.of( 
            new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        Shipment update = new Shipment(
            null,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            updateLots,
            1
        );
        
        doReturn(List.of(
            new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        )).when(mLotService).getPutLots(existingLots, updateLots);

        Shipment shipment = service.put(2L, 1L, update);
        
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(new Invoice(2L), shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 12, 31, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 12, 31, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getLots().size());
        assertEquals(1, shipment.getVersion());
        MaterialLot lot = shipment.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(2, lot.getVersion());
        
        verify(mRepo, times(1)).saveAndFlush(shipment);
        verify(mRepo, times(1)).refresh(eq(2L), any(Shipment.class));
        verify(mLotService, times(1)).getPutLots(anyList(), anyList());
    }

    @Test
    public void testPut_AddsANewEntity_WhenExistingEntityDoesNotExists() {
        doReturn(Optional.empty()).when(mRepo).findById(1L);

        List<MaterialLot> updateLots = List.of(
            new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        Shipment update = new Shipment(
            1L,
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            updateLots,
            1
        );
        
        doReturn(List.of(
            new MaterialLot(null, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), null, null, null)
        )).when(mLotService).getPutLots(null, updateLots);

        Shipment shipment = service.put(2L, 1L, update);
        
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(new Invoice(2L), shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(null, shipment.getCreatedAt());
        assertEquals(null, shipment.getLastUpdated());
        assertEquals(1, shipment.getLots().size());
        assertEquals(null, shipment.getVersion());
        MaterialLot lot = shipment.getLots().iterator().next();
        assertEquals(null, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(null, lot.getCreatedAt());
        assertEquals(null, lot.getLastUpdated());
        assertEquals(null, lot.getVersion());
        
        verify(mRepo, times(1)).saveAndFlush(shipment);
        verify(mRepo, times(1)).refresh(eq(2L), any(Shipment.class));
        verify(mLotService, times(1)).getPutLots(isNull(), anyList());
    }

    @Test
    public void testPut_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
        Shipment existing = new Shipment(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        Shipment update = new Shipment(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> service.put(2L, 1L, update));
    }

    @Test
    public void testPatch_PatchesExistingEntity_WhenExistingIsNotNull() {
        List<MaterialLot> existingLots = List.of(new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment existing = new Shipment(
            1L,
            "SHIPMENT_0",
            "LOT_0",
            "DESCRIPTION_0",
            new ShipmentStatus("IN-TRANSIT"),
            null,
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            existingLots,
            1
        );
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        List<MaterialLot> updateLots = List.of(
            new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );
        Shipment update = new Shipment(
            null,
            null,
            null,
            null,
            new ShipmentStatus("RECEIVED"),
            null,
            null,
            null,
            null,
            null,
            updateLots,
            1
        );

        doReturn(List.of(
            new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        )).when(mLotService).getPatchLots(existingLots, updateLots);

        Shipment shipment = service.patch(2L, 1L, update);
        
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_0", shipment.getShipmentNumber());
        assertEquals("LOT_0", shipment.getLotNumber());
        assertEquals("DESCRIPTION_0", shipment.getDescription());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(new Invoice(2L), shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 12, 31, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 12, 31, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 12, 31, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 12, 31, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getLots().size());
        assertEquals(1, shipment.getVersion());
        MaterialLot lot = shipment.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(1, lot.getVersion());
        
        verify(mRepo, times(1)).saveAndFlush(shipment);
        verify(mRepo, times(1)).refresh(eq(2L), any(Shipment.class));
        verify(mLotService, times(1)).getPatchLots(anyList(), anyList());
    }

    @Test
    public void testPatch_SavesWithExistingInvoiceId_WhenInvoiceIdArgIsNull() {
        Shipment existing = new Shipment(1L);
        existing.setInvoice(new Invoice(2L));
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        Shipment update = new Shipment(1L);
        Shipment shipment = service.patch(null, 1L, update);        
        verify(mRepo, times(1)).saveAndFlush(shipment);
        verify(mRepo, times(1)).refresh(eq(2L), any(Shipment.class));    }

    @Test
    public void testPatch_SavesWithNullInvoiceId_WhenInvoiceIdArgIsNullAndExistingInvoiceIsNull() {
        Shipment existing = new Shipment(1L);
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        Shipment update = new Shipment(1L);
        Shipment shipment = service.patch(null, 1L, update);
        verify(mRepo, times(1)).saveAndFlush(shipment);
        verify(mRepo, times(1)).refresh(isNull(), any(Shipment.class));    }

    @Test
    public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
        Shipment existing = new Shipment(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        Shipment update = new Shipment(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> service.patch(2L, 1L, update));
    }
}
