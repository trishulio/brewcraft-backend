package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.MaterialEntity;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentItem;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

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
        doReturn(Optional.of(new ShipmentStatus(1L, "IN-TRANSIT"))).when(mStatusRepo).findByName("IN-TRANSIT");
        doReturn(List.of(
            new MaterialEntity(1L, "Material_A", null, null, null, null, null, null, null),
            new MaterialEntity(2L, "Material_B", null, null, null, null, null, null, null),
            new MaterialEntity(3L, "Material_C", null, null, null, null, null, null, null)
        )).when(mMaterialRepo).findAllById(Set.of(1L, 2L, 3L));

        List<ShipmentItem> items = List.of(
            new ShipmentItem(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new ShipmentItem(2L, Quantities.getQuantity(new BigDecimal("2"), SupportedUnits.KILOGRAM), null, new Material(2L), LocalDateTime.of(1999, 1, 2, 12, 0, 0), LocalDateTime.of(2000, 1, 2, 12, 0, 0), 2),
            new ShipmentItem(3L, Quantities.getQuantity(new BigDecimal("3"), SupportedUnits.KILOGRAM), null, new Material(3L), LocalDateTime.of(1999, 1, 3, 12, 0, 0), LocalDateTime.of(2000, 1, 3, 12, 0, 0), 3)
        );
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
            1
        );

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
        assertEquals(1, ret.getVersion());

        assertEquals(3, ret.getItems().size());
        Iterator<ShipmentItem> it = ret.getItems().iterator();

        ShipmentItem item = it.next();
        assertEquals(1L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(ret, item.getShipment());
        assertEquals(new Material(1L, "Material_A", null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), item.getLastUpdated());
        assertEquals(1, item.getVersion());

        item = it.next();
        assertEquals(2L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("2"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(ret, item.getShipment());
        assertEquals(new Material(2L, "Material_B", null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 2, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 2, 12, 0, 0), item.getLastUpdated());
        assertEquals(2, item.getVersion());

        item = it.next();
        assertEquals(3L, item.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("3"), SupportedUnits.KILOGRAM), item.getQuantity());
        assertEquals(ret, item.getShipment());
        assertEquals(new Material(3L, "Material_C", null, null, null, null, null, null, null), item.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 3, 12, 0, 0), item.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 3, 12, 0, 0), item.getLastUpdated());
        assertEquals(3, item.getVersion());

        verify(mShipmentRepo, times(1)).save(ret);
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithNullInvoice_WhenAInvoiceIdIsNull() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));
        doReturn(Optional.of(new ShipmentStatus(1L, "IN-TRANSIT"))).when(mStatusRepo).findByName("IN-TRANSIT");

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
        doReturn(Optional.of(new ShipmentStatus(1L, "DELIVERED"))).when(mStatusRepo).findByName("DELIVERED");

        Shipment shipment = new Shipment(1L);

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, shipment), "Invoice not found with id: 1");

        verify(mShipmentRepo, times(0)).save(any(Shipment.class));
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithDefaultShipmentStatus_WhenShipmentStatusIsNull() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mShipmentRepo).save(any(Shipment.class));

        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Optional.of(new ShipmentStatus(1L, ShipmentStatus.DEFAULT_STATUS))).when(mStatusRepo).findByName(ShipmentStatus.DEFAULT_STATUS);

        List<ShipmentItem> items = new ArrayList<>();
        Shipment shipment = new Shipment(1L, "SHIPMENT_1", "LOT_1", "DESCRIPTION_1", null, null, LocalDateTime.of(1999, 1, 1, 12, 0), LocalDateTime.of(2000, 1, 1, 12, 0), LocalDateTime.of(2001, 1, 1, 12, 0), LocalDateTime.of(2002, 1, 1, 12, 0), items, 1);

        Shipment ret = repo.save(null, shipment);

        assertEquals(new ShipmentStatus(1L, ShipmentStatus.DEFAULT_STATUS), ret.getStatus());
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
        doReturn(Set.of(new ShipmentStatus(1L, "DELIVERED"))).when(mStatusRepo).findByNames(Set.of("DELIVERED"));
        doReturn(false).when(mMaterialRepo).existsByIds(Set.of(1L));

        List<ShipmentItem> items = new ArrayList<>();
        items.add(new ShipmentItem(1L));
        items.iterator().next().setMaterial(new Material(1L));
        Shipment shipment = new Shipment(1L);
        shipment.setItems(items);

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, shipment), "Materials not found with ids: (1)");

        verify(mShipmentRepo, times(0)).save(any(Shipment.class));
    }
}
