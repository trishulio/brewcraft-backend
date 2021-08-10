package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.ShipmentRepository;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ShipmentServiceTest {

    private ShipmentService service;

    private ShipmentRepository mRepo;
    private MaterialLotService mLotService;

    @BeforeEach
    public void init() {
        this.mRepo = mock(ShipmentRepository.class);
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(this.mRepo).saveAndFlush(any(Shipment.class));
        doAnswer(i -> {
            final Collection<Shipment> coll = i.getArgument(0, Collection.class);
            coll.forEach(s -> s.setStatus(new ShipmentStatus(99L)));
            return null;
        }).when(this.mRepo).refresh(anyCollection());

        this.mLotService = mock(MaterialLotService.class);

        this.service = new ShipmentService(this.mRepo, this.mLotService);
    }

    @Test
    public void testGetShipment_ReturnsShipmentInstance_WhenItExistsInRepository() {
        doReturn(Optional.of(new Shipment(1L))).when(this.mRepo).findById(1L);

        final Shipment shipment = this.service.getShipment(1L);

        assertEquals(new Shipment(1L), shipment);
    }

    @Test
    public void testGetShipment_ReturnsNull_WhenRepositoryDoesNotHaveIt() {
        doReturn(Optional.empty()).when(this.mRepo).findById(1L);
        final Shipment shipment = this.service.getShipment(1L);

        assertNull(shipment);
    }

    @Test
    @Disabled("TODO: Find a good strategy to test get method with long list of specifications")
    public void testGetShipments() {
        fail("Not tested");
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(this.mRepo).existsByIds(Set.of(1L, 2L, 3L));

        final boolean exists = this.service.existsByIds(Set.of(1L, 2L, 3L));

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(this.mRepo).existsByIds(Set.of(1L, 2L, 3L));

        final boolean exists = this.service.existsByIds(Set.of(1L, 2L, 3L));

        assertFalse(exists);
    }

    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        this.service.delete(Set.of(1L, 2L, 3L));

        verify(this.mRepo, times(1)).deleteByIds(Set.of(1L, 2L, 3L));
    }

    @Test
    public void testAdd_RetainsBaseValuesAndAddsShipmentToRepository_WhenObjectIsNotNull() {
        final List<MaterialLot> additionLots = new ArrayList<>();
        additionLots.add(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        final Shipment addition = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(100L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            additionLots,
            1
        );

        doReturn(List.of(
            new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), null, null, null)
        )).when(this.mLotService).getAddLots(additionLots);

        final Shipment shipment = this.service.add(addition);

        final Shipment expected = new Shipment(
            null,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            null,
            null,
            List.of(new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), null, null, null)),
            null
        );

        assertEquals(expected, shipment);

        verify(this.mRepo, times(1)).saveAndFlush(shipment);
        verify(this.mRepo, times(1)).refresh(List.of(shipment));
        verify(this.mLotService, times(1)).getAddLots(anyList());
    }

    @Test
    public void testPut_OverridesAnExistingShipmentEntity_WhenInputIsNotNull() {
        final List<MaterialLot> existingLots = new ArrayList<>();
        existingLots.add(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        final Shipment existing = new Shipment(
            1L,
            "SHIPMENT_0",
            "DESCRIPTION_0",
            new ShipmentStatus(101L),
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            existingLots,
            1
        );
        doReturn(Optional.of(existing)).when(this.mRepo).findById(1L);

        final List<MaterialLot> updateLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        final Shipment update = new Shipment(
            null,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(100L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            updateLots,
            1
        );

        doReturn(List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        )).when(this.mLotService).getPutLots(existingLots, updateLots);

        final Shipment shipment = this.service.put(1L, update);

        final Shipment expected = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)),
            1
        );

        assertEquals(expected, shipment);

        verify(this.mRepo, times(1)).saveAndFlush(shipment);
        verify(this.mRepo, times(1)).refresh(anyList());
        verify(this.mLotService, times(1)).getPutLots(anyList(), anyList());
    }

    @Test
    public void testPut_AddsANewEntity_WhenExistingEntityDoesNotExists() {
        doReturn(Optional.empty()).when(this.mRepo).findById(1L);

        final List<MaterialLot> updateLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );
        final Shipment update = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(100L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            updateLots,
            1
        );

        doReturn(List.of(
            new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), null, null, null)
        )).when(this.mLotService).getPutLots(null, updateLots);

        final Shipment shipment = this.service.put(1L, update);

        final Shipment expected = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            null,
            null,
            List.of(new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), null, null, null)),
            null
        );

        assertEquals(expected, shipment);

        verify(this.mRepo, times(1)).saveAndFlush(shipment);
        verify(this.mRepo, times(1)).refresh(anyList());
        verify(this.mLotService, times(1)).getPutLots(isNull(), anyList());
    }

    @Test
    public void testPut_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
        final Shipment existing = new Shipment(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(this.mRepo).findById(1L);

        final Shipment update = new Shipment(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> this.service.put(1L, update));
    }

    @Test
    public void testPatch_PatchesExistingEntity_WhenExistingIsNotNull() {
        final List<MaterialLot> existingLots = List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        final Shipment existing = new Shipment(
            1L,
            "SHIPMENT_0",
            "DESCRIPTION_0",
            new ShipmentStatus(101L),
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            existingLots,
            1
        );
        doReturn(Optional.of(existing)).when(this.mRepo).findById(1L);

        final List<MaterialLot> updateLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(2L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );
        final Shipment update = new Shipment(
            null,
            null,
            null,
            new ShipmentStatus(100L),
            null,
            null,
            null,
            null,
            updateLots,
            1
        );

        doReturn(List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        )).when(this.mLotService).getPatchLots(existingLots, updateLots);

        final Shipment shipment = this.service.patch(1L, update);

        final Shipment expected = new Shipment(
            1L,
            "SHIPMENT_0",
            "DESCRIPTION_0",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 12, 31, 12, 0),
            LocalDateTime.of(2000, 12, 31, 12, 0),
            LocalDateTime.of(2001, 12, 31, 12, 0),
            LocalDateTime.of(2002, 12, 31, 12, 0),
            List.of(new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)),
            1
        );

        assertEquals(expected, shipment);

        verify(this.mRepo, times(1)).saveAndFlush(shipment);
        verify(this.mRepo, times(1)).refresh(anyList());
        verify(this.mLotService, times(1)).getPatchLots(anyList(), anyList());
    }

    @Test
    public void testPatch_SavesWithExistingInvoiceId_WhenInvoiceIdArgIsNull() {
        final Shipment existing = new Shipment(1L);
        doReturn(Optional.of(existing)).when(this.mRepo).findById(1L);

        final Shipment update = new Shipment(1L);
        final Shipment shipment = this.service.patch(1L, update);
        verify(this.mRepo, times(1)).saveAndFlush(shipment);
        verify(this.mRepo, times(1)).refresh(List.of(shipment));
    }

    @Test
    public void testPatch_SavesWithNullInvoiceId_WhenInvoiceIdArgIsNullAndExistingInvoiceIsNull() {
        final Shipment existing = new Shipment(1L);
        doReturn(Optional.of(existing)).when(this.mRepo).findById(1L);

        final Shipment update = new Shipment(1L);
        final Shipment shipment = this.service.patch(1L, update);
        verify(this.mRepo, times(1)).saveAndFlush(shipment);
        verify(this.mRepo, times(1)).refresh(List.of(shipment));
    }

    @Test
    public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
        final Shipment existing = new Shipment(1L);
        existing.setVersion(1);
        doReturn(Optional.of(existing)).when(this.mRepo).findById(1L);

        final Shipment update = new Shipment(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> this.service.patch(1L, update));
    }
}
