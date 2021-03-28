package io.company.brewcraft.repository;

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

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import tec.uom.se.quantity.Quantities;
import io.company.brewcraft.utils.SupportedUnits;

public class EnhancedShipmentRepositoryImplTest {

    private EnhancedShipmentRepository repo;

    private ShipmentRepository mShipmentRepo;
    private ShipmentStatusRepository mStatusRepo;
    private MaterialRepository mMaterialRepo;
    private InvoiceRepository mInvoiceRepo;

    @BeforeEach
    public void init() {
        mShipmentRepo = mock(ShipmentRepository.class);
        mStatusRepo = mock(ShipmentStatusRepository.class);
        mInvoiceRepo = mock(InvoiceRepository.class);
        mMaterialRepo = mock(MaterialRepository.class);

        repo = new EnhancedShipmentRepositoryImpl(mShipmentRepo, mStatusRepo, mInvoiceRepo, mMaterialRepo);
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithShipmentEntity_WhenAllValuesAreProvided() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of(new ShipmentStatus(1L, "IN-TRANSIT"))).when(mStatusRepo).findByNames(Set.of("IN-TRANSIT"));
        doReturn(true).when(mMaterialRepo).existsByIds(Set.of(1L));

        Collection<ShipmentItem> items = new HashSet<>();
        items.add(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment shipment = new Shipment(1L,
                "SHIPMENT_1",
                "LOT_1",
                "DESCRIPTION_1",
                new ShipmentStatus("IN-TRANSIT"),
                null,
                LocalDateTime.of(1999, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2001, 1, 1, 12, 0),
                LocalDateTime.of(2002, 1, 1, 12, 0),
                items,
                1);

        Shipment ret = repo.save(1L, shipment);

        assertEquals(1L, ret.getId());
        assertEquals("SHIPMENT_1", ret.getShipmentNumber());
        assertEquals("LOT_1", ret.getLotNumber());
        assertEquals("DESCRIPTION_1", ret.getDescription());
        assertEquals(new ShipmentStatus(1L, "IN-TRANSIT"), ret.getStatus());
        assertEquals(new Invoice(1L), ret.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), ret.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), ret.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), ret.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), ret.getLastUpdated());
        assertEquals(1, ret.getItems().size());
        assertEquals(1, ret.getVersion());
        ShipmentItem item = ret.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(ret, item.getShipment());
        assertEquals(new Material(1L), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(1, item.getVersion());

        verify(mShipmentRepo, times(1)).save(ret);
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithNullInvoice_WhenAInvoiceIdIsNull() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Set.of(new ShipmentStatus(1L, "IN-TRANSIT"))).when(mStatusRepo).findByNames(Set.of("IN-TRANSIT"));
        doReturn(true).when(mMaterialRepo).existsByIds(Set.of(1L));

        Shipment shipment = new Shipment(1L);
        shipment.setStatus(new ShipmentStatus("IN-TRANSIT"));

        Shipment ret = repo.save(null, shipment);

        assertEquals(null, ret.getInvoice());

        verify(mShipmentRepo, times(1)).save(ret);
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenInvoiceDoesNotExistForId() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Optional.empty()).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of(new ShipmentStatus(1L, "RECEIVED"))).when(mStatusRepo).findByNames(Set.of("RECEIVED"));
        doReturn(true).when(mMaterialRepo).existsByIds(Set.of(1L));

        Shipment shipment = new Shipment(1L);

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, shipment), "Invoice not found with id: 1");

        verify(mShipmentRepo, times(0)).save(any(Shipment.class));
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithDefaultShipmentStatus_WhenShipmentStatusIsNull() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of(new ShipmentStatus(1L, "RECEIVED"))).when(mStatusRepo).findByNames(Set.of("RECEIVED"));
        doReturn(true).when(mMaterialRepo).existsByIds(Set.of(1L));

        Collection<ShipmentItem> items = new HashSet<>();
        items.add(new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1));
        Shipment shipment = new Shipment(1L, "SHIPMENT_1", "LOT_1", "DESCRIPTION_1", null, null, LocalDateTime.of(1999, 1, 1, 12, 0), LocalDateTime.of(2000, 1, 1, 12, 0), LocalDateTime.of(2001, 1, 1, 12, 0), LocalDateTime.of(2002, 1, 1, 12, 0), items, 1);

        Shipment ret = repo.save(null, shipment);

        assertEquals(new ShipmentStatus(1L, "RECEIVED"), ret.getStatus());
        verify(mShipmentRepo, times(1)).save(ret);
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenShipmentStatusDoesNotExistForName() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of()).when(mStatusRepo).findByNames(Set.of("NO-STATUS-NAME"));
        doReturn(true).when(mMaterialRepo).existsByIds(Set.of(1L));

        Shipment shipment = new Shipment(1L);
        shipment.setStatus(new ShipmentStatus("NO-STATUS-NAME"));

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, shipment), "ShipmentStatus not found with name: NO-STATUS-NAME");

        verify(mShipmentRepo, times(0)).save(any(Shipment.class));
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenMaterialsDoesNotExistForIds() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of(new ShipmentStatus(1L, "RECEIVED"))).when(mStatusRepo).findByNames(Set.of("RECEIVED"));
        doReturn(false).when(mMaterialRepo).existsByIds(Set.of(1L));

        Collection<ShipmentItem> items = new HashSet<>();
        items.add(new ShipmentItem(1L));
        items.iterator().next().setMaterial(new Material(1L));
        Shipment shipment = new Shipment(1L);
        shipment.setItems(items);

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, shipment), "Materials not found with ids: (1)");

        verify(mShipmentRepo, times(0)).save(any(Shipment.class));
    }
}
