package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.company.brewcraft.model.Freight;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.ProcurementItemId;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.TransactionService;
import io.company.brewcraft.service.impl.procurement.ProcurementService;

public class ProcurementServiceTest {
    private ProcurementService service;

    private InvoiceService mInvoiceService;
    private PurchaseOrderService mPoService;
    private ShipmentService mShipmentService;
    private TransactionService mTransactionService;

    @BeforeEach
    public void init() {
        mInvoiceService = mock(InvoiceService.class);
        mPoService = mock(PurchaseOrderService.class);
        mShipmentService = mock(ShipmentService.class);
        mTransactionService = mock(TransactionService.class);

        service = new ProcurementService(mInvoiceService, mPoService, mShipmentService, mTransactionService);
    }

    @Test
    public void testExists_ReturnsTrue_WhenShipmentAndInvoiceReturnsTrue() {
        doReturn(true).when(mShipmentService).exists(Set.of(1L));
        doReturn(true).when(mInvoiceService).exists(Set.of(10L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L)));

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenShipmentAndInvoiceReturnsFalse() {
        doReturn(false).when(mShipmentService).exists(Set.of(1L));
        doReturn(false).when(mInvoiceService).exists(Set.of(10L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L)));

        assertFalse(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenShipmentIsFalseAndInvoiceReturnsTrue() {
        doReturn(false).when(mShipmentService).exists(Set.of(1L));
        doReturn(true).when(mInvoiceService).exists(Set.of(10L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L)));

        assertFalse(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenShipmentIsTrueAndInvoiceReturnsFalse() {
        doReturn(true).when(mShipmentService).exists(Set.of(1L));
        doReturn(false).when(mInvoiceService).exists(Set.of(10L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L)));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsTrue_WhenShipmentAndInvoiceReturnsTrue() {
        doReturn(true).when(mShipmentService).exist(1L);
        doReturn(true).when(mInvoiceService).exist(10L);

        boolean exists = service.exist(new ProcurementId(1L, 10L));

        assertTrue(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenShipmentAndInvoiceReturnsFalse() {
        doReturn(false).when(mShipmentService).exist(1L);
        doReturn(false).when(mInvoiceService).exist(10L);

        boolean exists = service.exist(new ProcurementId(1L, 10L));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenShipmentIsFalseAndInvoiceReturnsTrue() {
        doReturn(false).when(mShipmentService).exist(1L);
        doReturn(true).when(mInvoiceService).exist(10L);

        boolean exists = service.exist(new ProcurementId(1L, 10L));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenShipmentIsTrueAndInvoiceReturnsFalse() {
        doReturn(true).when(mShipmentService).exist(1L);
        doReturn(false).when(mInvoiceService).exist(10L);

        boolean exists = service.exist(new ProcurementId(1L, 10L));

        assertFalse(exists);
    }

    @Test
    public void testDelete_Set_ReturnsCountOfItemsDeleted_WhenShipmentAndInvoiceDeleteIsSame() {
        doReturn(10).when(mShipmentService).delete(Set.of(1L));
        doReturn(10).when(mInvoiceService).delete(Set.of(10L));

        int count = service.delete(Set.of(new ProcurementId(1L, 10L)));

        verify(mTransactionService, times(0)).setRollbackOnly();
        assertEquals(10, count);
    }

    @Test
    public void testDelete_Set_Returns0AndRollback_WhenShipmentAndInvoiceDeleteIsDifferent() {
        doReturn(1).when(mShipmentService).delete(Set.of(1L));
        doReturn(2).when(mInvoiceService).delete(Set.of(10L));

        int count = service.delete(Set.of(new ProcurementId(1L, 10L)));

        verify(mTransactionService, times(1)).setRollbackOnly();
        assertEquals(0, count);
    }

    @Test
    public void testDelete_ReturnsCountOfItemsDeleted_WhenShipmentAndInvoiceDeleteIsSame() {
        doReturn(1).when(mShipmentService).delete(1L);
        doReturn(1).when(mInvoiceService).delete(10L);

        int count = service.delete(new ProcurementId(1L, 10L));

        verify(mTransactionService, times(0)).setRollbackOnly();
        assertEquals(1, count);
    }

    @Test
    public void testDelete_Returns0AndRollback_WhenShipmentAndInvoiceDeleteIsDifferent() {
        doReturn(1).when(mShipmentService).delete(1L);
        doReturn(2).when(mInvoiceService).delete(10L);

        int count = service.delete(new ProcurementId(1L, 10L));

        verify(mTransactionService, times(1)).setRollbackOnly();
        assertEquals(0, count);
    }

    @Test
    public void testGet_ReturnsProcurement_WhenShipmentAndInvoiceReturnsEntities() {
        doReturn(new Shipment(1L)).when(mShipmentService).get(1L);
        doReturn(new Invoice(10L)).when(mInvoiceService).get(10L);

        Procurement procurement = service.get(new ProcurementId(1L, 10L));

        Procurement expected = new Procurement(new Shipment(1L), new Invoice(10L));
        assertEquals(expected, procurement);
    }

    @Test
    public void testGet_ReturnsNull_WhenShipmentAndInvoiceReturnsNull() {
        doReturn(null).when(mShipmentService).get(1L);
        doReturn(null).when(mInvoiceService).get(10L);

        Procurement procurement = service.get(new ProcurementId(1L, 10L));

        assertNull(procurement);
    }

    @Test
    public void testGet_ReturnsNull_WhenShipmentReturnsNullButInvoiceReturnsEntity() {
        doReturn(null).when(mShipmentService).get(1L);
        doReturn(new Invoice(10L)).when(mInvoiceService).get(10L);

        Procurement procurement = service.get(new ProcurementId(1L, 10L));
        assertNull(procurement);
    }

    @Test
    public void testGet_ReturnsNull_WhenInvoiceReturnsNullButShipmentReturnsEntity() {
        doReturn(new Shipment(1L)).when(mShipmentService).get(1L);
        doReturn(null).when(mInvoiceService).get(10L);

        Procurement procurement = service.get(new ProcurementId(1L, 10L));
        assertNull(procurement);
    }

    @Test
    public void testGetByIds_ReturnsListOfProcurements_WhenShipmentAndInvoicesReturnEntities() {
        ArgumentCaptor<Collection<? extends Identified<Long>>> captor = ArgumentCaptor.forClass(Collection.class);

        doReturn(List.of(new Shipment(1L))).when(mShipmentService).getByIds(captor.capture());
        doReturn(List.of(new Invoice(10L))).when(mInvoiceService).getByIds(captor.capture());

        List<Procurement> procurements = service.getByIds(List.of(
            () -> new ProcurementId(1L, 10L),
            () -> new ProcurementId(2L, 20L)
        ));

        List<Procurement> expected = List.of(new Procurement(new Shipment(1L), new Invoice(10L)));
        assertEquals(expected, procurements);

        Iterator<? extends Identified<Long>> it1 = captor.getAllValues().get(1).iterator();
        assertEquals(1L, it1.next().getId());
        assertEquals(2L, it1.next().getId());
        Iterator<? extends Identified<Long>> it2 = captor.getAllValues().get(0).iterator();
        assertEquals(10L, it2.next().getId());
        assertEquals(20L, it2.next().getId());
    }

    @Test
    public void testGetByIds_ThrowsException_WhenInvoiceAndShipmentCountIsDifferent() {
        ArgumentCaptor<Collection<? extends Identified<Long>>> captor = ArgumentCaptor.forClass(Collection.class);

        doReturn(List.of()).when(mShipmentService).getByIds(captor.capture());
        doReturn(List.of(new Invoice(10L))).when(mInvoiceService).getByIds(captor.capture());

        assertThrows(IllegalStateException.class, () -> service.getByIds(List.of(
            () -> new ProcurementId(1L, 10L),
            () -> new ProcurementId(2L, 20L)
        )));

        Iterator<? extends Identified<Long>> it1 = captor.getAllValues().get(1).iterator();
        assertEquals(1L, it1.next().getId());
        assertEquals(2L, it1.next().getId());
        Iterator<? extends Identified<Long>> it2 = captor.getAllValues().get(0).iterator();
        assertEquals(10L, it2.next().getId());
        assertEquals(20L, it2.next().getId());
    }

    @Test
    public void testAdd_ReturnsProcurementsAfterAddingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(100L);
            return pOs;
        }).when(mPoService).add(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            invoices.get(0).getItems().get(0).setId(20L);
            return invoices;
        }).when(mInvoiceService).add(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            shipments.get(0).getLots().get(0).setId(10L);
            return shipments;
        }).when(mShipmentService).add(anyList());

        List<BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>> additions = List.of(new Procurement(
            null,
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(null)),
            100, // invoiceVersion
            1 // version
        ));

        List<Procurement> procurements = service.add(additions);

        List<Procurement> expected = List.of(new Procurement(
            new ProcurementId(1L, 2L),
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(100L),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(new ProcurementItemId(10L, 20L))),
            100, // invoiceVersion
            1 // version
        ));
        assertEquals(expected.toString(), procurements.toString());
    }

    @Test
    public void testAdd_IgnoresNullProcurementsAndPurchasesOrders() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(100L);
            return pOs;
        }).when(mPoService).add(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            invoices.get(1).setId(4L);
            return invoices;
        }).when(mInvoiceService).add(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            shipments.get(1).setId(3L);
            return shipments;
        }).when(mShipmentService).add(anyList());

        List<BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>> additions = new ArrayList<>() {
        private static final long serialVersionUID = 1L;
        {
            add(new Procurement());
            add(null);
            add(new Procurement());
        }};
        additions.get(2).setPurchaseOrder(new PurchaseOrder());

        List<Procurement> procurements = service.add(additions);

        List<Procurement> expected = List.of(
            new Procurement(new ProcurementId(1L, 2L)),
            new Procurement(new ProcurementId(3L, 4L))
        );
        expected.get(1).setPurchaseOrder(new PurchaseOrder(100L));
        assertEquals(expected.toString(), procurements.toString());
    }

    @Test
    public void testPut_ReturnsProcurementsAfterPutingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(100L);
            return pOs;
        }).when(mPoService).put(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            invoices.get(0).getItems().get(0).setId(20L);
            return invoices;
        }).when(mInvoiceService).put(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            shipments.get(0).getLots().get(0).setId(10L);
            return shipments;
        }).when(mShipmentService).put(anyList());

        List<UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>> updates = List.of(new Procurement(
            null,
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(null)),
            100, // invoiceVersion
            1 // version
        ));

        List<Procurement> procurements = service.put(updates);

        List<Procurement> expected = List.of(new Procurement(
            new ProcurementId(1L, 2L),
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(100L),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(new ProcurementItemId(10L, 20L))),
            100, // invoiceVersion
            1 // version
        ));
        assertEquals(expected.toString(), procurements.toString());
    }

    @Test
    public void testPut_IgnoresNullProcurementsAndPurchasesOrders() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(100L);
            return pOs;
        }).when(mPoService).put(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            invoices.get(1).setId(4L);
            return invoices;
        }).when(mInvoiceService).put(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            shipments.get(1).setId(3L);
            return shipments;
        }).when(mShipmentService).put(anyList());

        List<UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>> updates = new ArrayList<>() {
        private static final long serialVersionUID = 1L;
        {
            add(new Procurement());
            add(null);
            add(new Procurement());
        }};
        updates.get(2).setPurchaseOrder(new PurchaseOrder());

        List<Procurement> procurements = service.put(updates);

        List<Procurement> expected = List.of(
            new Procurement(new ProcurementId(1L, 2L)),
            new Procurement(new ProcurementId(3L, 4L))
        );
        expected.get(1).setPurchaseOrder(new PurchaseOrder(100L));
        assertEquals(expected.toString(), procurements.toString());
    }

    @Test
    public void testPatch_ReturnsProcurementsAfterPatchingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(100L);
            return pOs;
        }).when(mPoService).patch(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            invoices.get(0).getItems().get(0).setId(20L);
            return invoices;
        }).when(mInvoiceService).patch(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            shipments.get(0).getLots().get(0).setId(10L);
            return shipments;
        }).when(mShipmentService).patch(anyList());

        List<UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>> updates = List.of(new Procurement(
            null,
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(null)),
            100, // invoiceVersion
            1 // version
        ));

        List<Procurement> procurements = service.patch(updates);

        List<Procurement> expected = List.of(new Procurement(
            new ProcurementId(1L, 2L),
            "INVOICE_NO_1",
            "SHIPMENT_NO_1",
            "DESCRIPTION",
            new PurchaseOrder(100L),
            LocalDateTime.of(1999, 1, 1, 0, 0), // generatedOn
            LocalDateTime.of(1999, 2, 1, 0, 0), // receivedOn
            LocalDateTime.of(1999, 3, 1, 0, 0), // paymentDueDate
            LocalDateTime.of(1999, 4, 1, 0, 0), // deliveryDueDate
            LocalDateTime.of(1999, 5, 1, 0, 0), // deliveredDate
            new Freight(),
            LocalDateTime.of(1999, 6, 1, 0, 0), // createdAt
            LocalDateTime.of(1999, 7, 1, 0, 0), // lastUpdated
            new InvoiceStatus(4L),
            new ShipmentStatus(5L),
            List.of(new ProcurementItem(new ProcurementItemId(10L, 20L))),
            100, // invoiceVersion
            1 // version
        ));
        assertEquals(expected.toString(), procurements.toString());
    }

    @Test
    public void testPatch_IgnoresNullProcurementsAndPurchasesOrders() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(100L);
            return pOs;
        }).when(mPoService).patch(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            invoices.get(1).setId(4L);
            return invoices;
        }).when(mInvoiceService).patch(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            shipments.get(1).setId(3L);
            return shipments;
        }).when(mShipmentService).patch(anyList());

        List<UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>> updates = new ArrayList<>() {
        private static final long serialVersionUID = 1L;
        {
            add(new Procurement());
            add(null);
            add(new Procurement());
        }};
        updates.get(2).setPurchaseOrder(new PurchaseOrder());

        List<Procurement> procurements = service.patch(updates);

        List<Procurement> expected = List.of(
            new Procurement(new ProcurementId(1L, 2L)),
            new Procurement(new ProcurementId(3L, 4L))
        );
        expected.get(1).setPurchaseOrder(new PurchaseOrder(100L));
        assertEquals(expected.toString(), procurements.toString());
    }
}
