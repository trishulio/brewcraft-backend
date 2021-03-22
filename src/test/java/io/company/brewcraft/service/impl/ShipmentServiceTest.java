package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

public class ShipmentServiceTest {

    private ShipmentService service;
    
    private ShipmentRepository mRepo;
    private ShipmentItemService mItemService;
    private UtilityProvider mUtilProvider;
    
    private Validator validator;
    
    @BeforeEach
    public void init() {
        mRepo = mock(ShipmentRepository.class);
        mItemService = mock(ShipmentItemService.class);
        mUtilProvider = mock(UtilityProvider.class);

        doReturn(new Validator()).when(mUtilProvider).getValidator();

        service = new ShipmentService(mRepo, mItemService, mUtilProvider);
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
    public void testGetShipment_ThrowsValidationException_WhenIdIsNull() {
        assertThrows(ValidationException.class, () -> service.getShipment(null), "1. Non-null Id expected.");
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
    public void testExists_ThrowsValidationException_WhenIdCollectionIsNull() {
        assertThrows(ValidationException.class, () -> service.existsByIds(null));
    }
    
    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        service.delete(Set.of(1L, 2L, 3L));
        
        verify(mRepo, times(1)).deleteByIds(Set.of(1L, 2L, 3L));
    }
    
    @Test
    public void testDelete_ThrowsValidation_WhenIdsAreNull() {
        assertThrows(ValidationException.class, () -> service.delete(null), "Cannot retrieve Ids to delete from a null collection");
    }
    
    @Test
    public void testAdd_RetainsBaseValuesAndAddsShipmentToRepository_WhenObjectIsNotNull() {
        doAnswer(i -> i.getArgument(1, Shipment.class)).when(mRepo).save(anyLong(), any(Shipment.class));

        Collection<ShipmentItem> additionItems = new HashSet<>();
        additionItems.add(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment addition = new Shipment(
            1L,
            "SHIPMENT_1",
            "LOT_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            additionItems,
            1
        );
        
        doReturn(Set.of(
            new ShipmentItem(null, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), addition, new Material(1L), null, null, null)
        )).when(mItemService).getAddItems(
            Set.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), addition, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1))
        );

        Shipment shipment = service.add(2L, addition);
        
        assertEquals(null, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(null, shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(null, shipment.getCreatedAt());
        assertEquals(null, shipment.getLastUpdated());
        assertEquals(1, shipment.getItems().size());
        assertEquals(null, shipment.getVersion());
        ShipmentItem item = shipment.getItems().iterator().next();
        assertEquals(null, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), item.getQuantity());
        assertEquals(shipment, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(null, item.getCreatedAt());
        assertEquals(null, item.getLastUpdated());
        assertEquals(null, item.getVersion());
        
        verify(mRepo, times(1)).save(2L, shipment);
        verify(mItemService, times(1)).getAddItems(anyCollection());
    }
    
    @Test
    public void testAdd_ThrowsValidationError_WhenRequiredFieldsAreNull() {
        String err = "1. InvoiceId cannot be null\n"
                + "2. Shipment cannot be null#n";
        
        assertThrows(ValidationException.class, () -> service.add(null, null), err);
    }
    
    @Test
    public void testPut_OverridesAnExistingShipmentEntity_WhenInputIsNotNull() {
        doAnswer(i -> i.getArgument(1, Shipment.class)).when(mRepo).save(anyLong(), any(Shipment.class));

        Collection<ShipmentItem> existingitems = new HashSet<>();
        existingitems.add(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("0"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment existing = new Shipment(
            1L,
            "SHIPMENT_0",
            "LOT_0",
            new ShipmentStatus("IN-TRANSIT"),
            null,
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            existingitems,
            1
        );
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        Collection<ShipmentItem> updateItems = Set.of( 
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        Shipment update = new Shipment(
            null,
            "SHIPMENT_1",
            "LOT_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            updateItems,
            1
        );
        
        doReturn(Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), update, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        )).when(mItemService).getPutItems(
            Set.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("0"), Units.KILOGRAM), existing, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
            Set.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), update, new Material(1L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        ));

        Shipment shipment = service.put(2L, 1L, update);
        
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(null, shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 12, 31, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 12, 31, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getItems().size());
        assertEquals(1, shipment.getVersion());
        ShipmentItem item = shipment.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), item.getQuantity());
        assertEquals(shipment, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(2, item.getVersion());
        
        verify(mRepo, times(1)).save(2L, existing);
        verify(mItemService, times(1)).getPutItems(anyCollection(), anyCollection());
    }
    
    @Test
    public void testPut_AddsANewEntity_WhenExistingEntityDoesNotExists() {
        doAnswer(i -> i.getArgument(1, Shipment.class)).when(mRepo).save(anyLong(), any(Shipment.class));
        doReturn(Optional.empty()).when(mRepo).findById(1L);

        Collection<ShipmentItem> updateItems = new HashSet<>(); 
        updateItems.add(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2));
        Shipment update = new Shipment(
            1L,
            "SHIPMENT_1",
            "LOT_1",
            new ShipmentStatus("RECEIVED"),
            null,
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            updateItems,
            1
        );
        
        doReturn(Set.of(
            new ShipmentItem(null, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), update, new Material(1L), null, null, null)
        )).when(mItemService).getPutItems(
            null,
            Set.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), update, new Material(1L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2))
        );

        Shipment shipment = service.put(2L, 1L, update);
        
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(null, shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(null, shipment.getCreatedAt());
        assertEquals(null, shipment.getLastUpdated());
        assertEquals(1, shipment.getItems().size());
        assertEquals(null, shipment.getVersion());
        ShipmentItem item = shipment.getItems().iterator().next();
        assertEquals(null, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), item.getQuantity());
        assertEquals(shipment, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(null, item.getCreatedAt());
        assertEquals(null, item.getLastUpdated());
        assertEquals(null, item.getVersion());
        
        verify(mRepo, times(1)).save(2L, shipment);
        verify(mItemService, times(1)).getPutItems(isNull(), anyCollection());
    }
    
    @Test
    public void testPut_ThrowsValidationException_WhenRequiredInputsAreNull() {
        String err = "1. InvoiceId cannot be null\n"
                + "2. ShipmentId cannot be null\n"
                + "3. Shipment cannot be null\n";
        
        assertThrows(ValidationException.class, () -> service.put(null, null, null), err);
    }
    
    @Test
    public void testPatch_PatchesExistingEntity_WhenExistingIsNotNull() {
        Collection<ShipmentItem> existingItems = new HashSet<>();
        existingItems.add(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("0"), Units.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment existing = new Shipment(
            1L,
            "SHIPMENT_0",
            "LOT_0",
            new ShipmentStatus("IN-TRANSIT"),
            null,
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            existingItems,
            1
        );
        doReturn(Optional.of(existing)).when(mRepo).findById(1L);

        doAnswer(i -> i.getArgument(1, Shipment.class)).when(mRepo).save(anyLong(), any(Shipment.class));

        Collection<ShipmentItem> updateItems = Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), null, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );
        Shipment update = new Shipment(
            null,
            null,
            null,
            new ShipmentStatus("RECEIVED"),
            null,
            null,
            null,
            null,
            null,
            updateItems,
            1
        );

        doReturn(Set.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), update, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        )).when(mItemService).getPatchItems(
            Set.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("0"), Units.KILOGRAM), existing, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
            Set.of(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), update, null, LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1))
        );

        Shipment shipment = service.patch(2L, 1L, update);
        
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_0", shipment.getShipmentNumber());
        assertEquals("LOT_0", shipment.getLotNumber());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(null, shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 12, 31, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 12, 31, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 12, 31, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 12, 31, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getItems().size());
        assertEquals(1, shipment.getVersion());
        ShipmentItem item = shipment.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), Units.KILOGRAM), item.getQuantity());
        assertEquals(shipment, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(1, item.getVersion());
        
        verify(mRepo, times(1)).save(2L, shipment);
        verify(mItemService, times(1)).getPatchItems(anyCollection(), anyCollection());
    }
}
