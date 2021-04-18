package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
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
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class EnhancedShipmentRepositoryImplTest {

    private EnhancedShipmentRepository repo;

    private ShipmentStatusRepository mStatusRepo;
    private MaterialRepository mMaterialRepo;
    private InvoiceRepository mInvoiceRepo;

    @BeforeEach
    public void init() {
        mStatusRepo = mock(ShipmentStatusRepository.class);
        mInvoiceRepo = mock(InvoiceRepository.class);
        mMaterialRepo = mock(MaterialRepository.class);

        repo = new EnhancedShipmentRepositoryImpl(mStatusRepo, mInvoiceRepo, mMaterialRepo);
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithShipmentEntity_WhenAllValuesAreProvided() {
        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Optional.of(new ShipmentStatus(1L, "IN-TRANSIT"))).when(mStatusRepo).findByName("IN-TRANSIT");
        doReturn(List.of(
            new Material(1L, "Material_A", null, null, null, null, null, null, null),
            new Material(2L, "Material_B", null, null, null, null, null, null, null),
            new Material(3L, "Material_C", null, null, null, null, null, null, null)
        )).when(mMaterialRepo).findAllById(Set.of(1L, 2L, 3L));

        List<MaterialLot> lots = List.of(
            new MaterialLot(1L, Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), null, new Material(1L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new MaterialLot(2L, Quantities.getQuantity(new BigDecimal("2"), SupportedUnits.KILOGRAM), null, new Material(2L), LocalDateTime.of(1999, 1, 2, 12, 0, 0), LocalDateTime.of(2000, 1, 2, 12, 0, 0), 2),
            new MaterialLot(3L, Quantities.getQuantity(new BigDecimal("3"), SupportedUnits.KILOGRAM), null, new Material(3L), LocalDateTime.of(1999, 1, 3, 12, 0, 0), LocalDateTime.of(2000, 1, 3, 12, 0, 0), 3)
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
            lots,
            1
        );

        repo.refresh(1L, shipment);

        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("LOT_1", shipment.getLotNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus(1L, "IN-TRANSIT"), shipment.getStatus());
        assertEquals(new Invoice(1L), shipment.getInvoice());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getVersion());

        assertEquals(3, shipment.getLots().size());
        Iterator<MaterialLot> it = shipment.getLots().iterator();

        MaterialLot lot = it.next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L, "Material_A", null, null, null, null, null, null, null), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(1, lot.getVersion());

        lot = it.next();
        assertEquals(2L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("2"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(2L, "Material_B", null, null, null, null, null, null, null), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 2, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 2, 12, 0, 0), lot.getLastUpdated());
        assertEquals(2, lot.getVersion());

        lot = it.next();
        assertEquals(3L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("3"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(3L, "Material_C", null, null, null, null, null, null, null), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 3, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 3, 12, 0, 0), lot.getLastUpdated());
        assertEquals(3, lot.getVersion());

    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithNullInvoice_WhenAInvoiceIdIsNull() {
        doReturn(Optional.of(new ShipmentStatus(1L, "IN-TRANSIT"))).when(mStatusRepo).findByName("IN-TRANSIT");

        Shipment shipment = new Shipment(1L);
        shipment.setStatus(new ShipmentStatus("IN-TRANSIT"));

        repo.refresh(null, shipment);

        assertEquals(null, shipment.getInvoice());
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenInvoiceDoesNotExistForId() {
        doReturn(Optional.empty()).when(mInvoiceRepo).findById(1L);
        doReturn(Optional.of(new ShipmentStatus(1L, "DELIVERED"))).when(mStatusRepo).findByName("DELIVERED");

        Shipment shipment = new Shipment(1L);

        assertThrows(EntityNotFoundException.class, () -> repo.refresh(1L, shipment), "Invoice not found with id: 1");
    }

    @Test
    public void testSave_CallsShipmentRepositorySaveWithDefaultShipmentStatus_WhenShipmentStatusIsNull() {
        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Optional.of(new ShipmentStatus(1L, ShipmentStatus.DEFAULT_STATUS))).when(mStatusRepo).findByName(ShipmentStatus.DEFAULT_STATUS);

        List<MaterialLot> lots = new ArrayList<>();
        Shipment shipment = new Shipment(1L, "SHIPMENT_1", "LOT_1", "DESCRIPTION_1", null, null, LocalDateTime.of(1999, 1, 1, 12, 0), LocalDateTime.of(2000, 1, 1, 12, 0), LocalDateTime.of(2001, 1, 1, 12, 0), LocalDateTime.of(2002, 1, 1, 12, 0), lots, 1);

        repo.refresh(null, shipment);

        assertEquals(new ShipmentStatus(1L, ShipmentStatus.DEFAULT_STATUS), shipment.getStatus());
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenShipmentStatusDoesNotExistForName() {
        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of()).when(mStatusRepo).findByNames(Set.of("NO-STATUS-NAME"));
        doReturn(true).when(mMaterialRepo).existsByIds(Set.of(1L));

        Shipment shipment = new Shipment(1L);
        shipment.setStatus(new ShipmentStatus("NO-STATUS-NAME"));

        assertThrows(EntityNotFoundException.class, () -> repo.refresh(1L, shipment), "ShipmentStatus not found with name: NO-STATUS-NAME");
    }

    @Test
    public void testSave_ThrowsEntityNotFoundException_WhenMaterialsDoesNotExistForIds() {
        doReturn(Optional.of(new Invoice(1L))).when(mInvoiceRepo).findById(1L);
        doReturn(Set.of(new ShipmentStatus(1L, "DELIVERED"))).when(mStatusRepo).findByNames(Set.of("DELIVERED"));
        doReturn(false).when(mMaterialRepo).existsByIds(Set.of(1L));

        List<MaterialLot> lots = new ArrayList<>();
        lots.add(new MaterialLot(1L));
        lots.iterator().next().setMaterial(new Material(1L));
        Shipment shipment = new Shipment(1L);
        shipment.setLots(lots);

        assertThrows(EntityNotFoundException.class, () -> repo.refresh(1L, shipment), "Materials not found with ids: (1)");
    }
}
