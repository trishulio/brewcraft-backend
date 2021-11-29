package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.BaseProcurementItem;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.model.procurement.UpdateProcurementItem;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.TransactionService;
import io.company.brewcraft.service.impl.procurement.ProcurementFactory;
import io.company.brewcraft.service.impl.procurement.ProcurementItemFactory;
import io.company.brewcraft.service.impl.procurement.ProcurementService;

public class ProcurementServiceTest {
    private ProcurementService service;

    private InvoiceService mInvoiceService;
    private PurchaseOrderService mPoService;
    private ShipmentService mShipmentService;
    private TransactionService mTransactionService;
    private ProcurementFactory mProcurementFactory;
    private ProcurementItemFactory mProcurementItemFactory;

    @BeforeEach
    public void init() {
        mInvoiceService = mock(InvoiceService.class);
        mPoService = mock(PurchaseOrderService.class);
        mShipmentService = mock(ShipmentService.class);
        mTransactionService = mock(TransactionService.class);
        mProcurementFactory = mock(ProcurementFactory.class);
        mProcurementItemFactory = mock(ProcurementItemFactory.class);

        service = new ProcurementService(mInvoiceService, mPoService, mShipmentService, mTransactionService, mProcurementFactory, mProcurementItemFactory);
    }

    @Test
    public void testExists_ReturnsTrue_WhenShipmentInvoiceAndPurchaseOrderReturnsTrue() {
        doReturn(true).when(mShipmentService).exists(Set.of(1L));
        doReturn(true).when(mInvoiceService).exists(Set.of(10L));
        doReturn(true).when(mPoService).exists(Set.of(100L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L, 100L)));

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenShipmentIsFalse() {
        doReturn(false).when(mShipmentService).exists(Set.of(1L));
        doReturn(true).when(mInvoiceService).exists(Set.of(10L));
        doReturn(true).when(mPoService).exists(Set.of(100L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L, 100L)));

        assertFalse(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenInvoiceIsFalse() {
        doReturn(true).when(mShipmentService).exists(Set.of(1L));
        doReturn(false).when(mInvoiceService).exists(Set.of(10L));
        doReturn(true).when(mPoService).exists(Set.of(100L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L, 100L)));

        assertFalse(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenPurchaseOrderIsFalse() {
        doReturn(true).when(mShipmentService).exists(Set.of(1L));
        doReturn(true).when(mInvoiceService).exists(Set.of(10L));
        doReturn(false).when(mPoService).exists(Set.of(100L));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L, 100L)));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsTrue_WhenShipmentInvoiceAndPurchaseOrderIsTrue() {
        doReturn(true).when(mShipmentService).exist(1L);
        doReturn(true).when(mInvoiceService).exist(10L);
        doReturn(true).when(mPoService).exist(100L);

        boolean exists = service.exist(new ProcurementId(1L, 10L, 100L));

        assertTrue(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenShipmentIsFalse() {
        doReturn(false).when(mShipmentService).exist(1L);
        doReturn(true).when(mInvoiceService).exist(10L);
        doReturn(true).when(mPoService).exist(100L);

        boolean exists = service.exist(new ProcurementId(1L, 10L, 100L));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenInvoiceIsFall() {
        doReturn(true).when(mShipmentService).exist(1L);
        doReturn(false).when(mInvoiceService).exist(10L);
        doReturn(true).when(mPoService).exist(100L);

        boolean exists = service.exist(new ProcurementId(1L, 10L, 100L));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenPurchaseOrderIsFalse() {
        doReturn(true).when(mShipmentService).exist(1L);
        doReturn(true).when(mInvoiceService).exist(10L);
        doReturn(false).when(mPoService).exist(100L);

        boolean exists = service.exist(new ProcurementId(1L, 10L, 100L));

        assertFalse(exists);
    }

    @Test
    public void testDelete_Set_ReturnsCountOfItemsDeleted_WhenShipmentAndInvoiceDeleteIsSame() {
        doReturn(10).when(mShipmentService).delete(Set.of(1L));
        doReturn(10).when(mInvoiceService).delete(Set.of(10L));

        int count = service.delete(Set.of(new ProcurementId(1L, 10L, 100L)));

        verify(mTransactionService, times(0)).setRollbackOnly();
        assertEquals(10, count);
    }

    @Test
    public void testDelete_Set_Returns0AndRollback_WhenShipmentAndInvoiceDeleteIsDifferent() {
        doReturn(1).when(mShipmentService).delete(Set.of(1L));
        doReturn(2).when(mInvoiceService).delete(Set.of(10L));

        int count = service.delete(Set.of(new ProcurementId(1L, 10L, 100L)));

        verify(mTransactionService, times(1)).setRollbackOnly();
        assertEquals(0, count);
    }

    @Test
    public void testDelete_ReturnsCountOfItemsDeleted_WhenShipmentAndInvoiceDeleteIsSame() {
        doReturn(1).when(mShipmentService).delete(1L);
        doReturn(1).when(mInvoiceService).delete(10L);

        int count = service.delete(new ProcurementId(1L, 10L, 100L));

        verify(mTransactionService, times(0)).setRollbackOnly();
        assertEquals(1, count);
    }

    @Test
    public void testDelete_Returns0AndRollback_WhenShipmentAndInvoiceDeleteIsDifferent() {
        doReturn(1).when(mShipmentService).delete(1L);
        doReturn(2).when(mInvoiceService).delete(10L);

        int count = service.delete(new ProcurementId(1L, 10L, 100L));

        verify(mTransactionService, times(1)).setRollbackOnly();
        assertEquals(0, count);
    }

    @Test
    public void testGetAll_ReturnsPageOfProcurementsBuiltFromShipments() {
        doReturn(new PageImpl<>(List.of(new Shipment()))).when(mShipmentService).getShipments(
            // shipment filters
            Set.of(1L), // ids,
            Set.of(2L), // excludeIds,
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers,
            Set.of("S_DESCRIPTION"), // descriptions,
            Set.of(3L), // statusIds,
            LocalDateTime.of(2000, 1, 1, 0, 1), // deliveryDueDateFrom,
            LocalDateTime.of(2000, 1, 1, 0, 2), // deliveryDueDateTo,
            LocalDateTime.of(2000, 1, 1, 0, 3), // deliveredDateFrom,
            LocalDateTime.of(2000, 1, 1, 0, 4), // deliveredDateTo,
            // invoice filters
            Set.of(10L), // invoiceIds,
            Set.of(20L), // invoiceExcludeIds,
            Set.of("INVOICE_NUMBER"), // invoiceNumbers,
            Set.of("I_DESCRIPTION"), // invoiceDescriptions,
            Set.of("II_DESCRIPTION"), // invoiceItemDescriptions,
            LocalDateTime.of(2000, 1, 1, 0, 5), // generatedOnFrom,
            LocalDateTime.of(2000, 1, 1, 0, 6), // generatedOnTo,
            LocalDateTime.of(2000, 1, 1, 0, 7), // receivedOnFrom,
            LocalDateTime.of(2000, 1, 1, 0, 8), // receivedOnTo,
            LocalDateTime.of(2000, 1, 1, 0, 9), // paymentDueDateFrom,
            LocalDateTime.of(2000, 1, 1, 0, 10), // paymentDueDateTo,
            Set.of(100L), // purchaseOrderIds,
            Set.of(1000L), // materialIds,
            new BigDecimal("1"), // amtFrom,
            new BigDecimal("2"), // amtTo,
            new BigDecimal("3"), // freightAmtFrom,
            new BigDecimal("4"), // freightAmtTo,
            Set.of(30L), // invoiceStatusIds,
            Set.of(200L), // supplierIds,
            // misc
            new TreeSet<>(Set.of("col_1", "col_2")), // sort,
            true, // orderAscending,
            1, // page,
            10 //size
        );
        doReturn(new Procurement()).when(mProcurementFactory).buildFromShipment(new Shipment());

        Page<Procurement> page = service.getAll(
            // shipment filters
            Set.of(1L), // shipmentIds,
            Set.of(2L), // shipmentExcludeIds,
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers,
            Set.of("S_DESCRIPTION"), // descriptions,
            Set.of(3L), // shipmentStatusIds,
            LocalDateTime.of(2000, 1, 1, 0, 1), // deliveryDueDateFrom,
            LocalDateTime.of(2000, 1, 1, 0, 2), // deliveryDueDateTo,
            LocalDateTime.of(2000, 1, 1, 0, 3), // deliveredDateFrom,
            LocalDateTime.of(2000, 1, 1, 0, 4), // deliveredDateTo,
            // invoice filters
            Set.of(10L), // invoiceIds,
            Set.of(20L), // invoiceExcludeIds,
            Set.of("INVOICE_NUMBER"), // invoiceNumbers,
            Set.of("I_DESCRIPTION"), // invoiceDescriptions,
            Set.of("II_DESCRIPTION"), // invoiceItemDescriptions,
            LocalDateTime.of(2000, 1, 1, 0, 5), // generatedOnFrom,
            LocalDateTime.of(2000, 1, 1, 0, 6), // generatedOnTo,
            LocalDateTime.of(2000, 1, 1, 0, 7), // receivedOnFrom,
            LocalDateTime.of(2000, 1, 1, 0, 8), // receivedOnTo,
            LocalDateTime.of(2000, 1, 1, 0, 9), // paymentDueDateFrom,
            LocalDateTime.of(2000, 1, 1, 0, 10), // paymentDueDateTo,
            Set.of(100L), // purchaseOrderIds,
            Set.of(1000L), // materialIds,
            new BigDecimal("1"), // amtFrom,
            new BigDecimal("2"), // amtTo,
            new BigDecimal("3"), // freightAmtFrom,
            new BigDecimal("4"), // freightAmtTo,
            Set.of(30L), // invoiceStatusIds,
            Set.of(200L), // supplierIds,
            // misc
            new TreeSet<>(Set.of("col_1", "col_2")), // sort,
            true, // orderAscending,
            1, // page,
            10 //size
        );

        Page<Procurement> expected = new PageImpl<>(List.of(new Procurement()));
        assertEquals(expected, page);
    }

    @Test
    public void testGet_ReturnsProcurement_WhenShipmentProcurementIdMatchesInvoiceShipmentAndPurchaseOrder() {
        doReturn(new Shipment(1L)).when(mShipmentService).get(1L);
        doReturn(new Procurement()).when(mProcurementFactory).buildFromShipmentIfIdMatches(new ProcurementId(1L, 10L, 100L), new Shipment(1L));

        assertEquals(new Procurement(), service.get(new ProcurementId(1L, 10L, 100L)));
    }

    @Test
    public void testGetByIds_ReturnsProcurementsFromShipments() {
        ArgumentCaptor<Collection<? extends Identified<Long>>> captor = ArgumentCaptor.forClass(Collection.class);

        List<Shipment> shipments = List.of(new Shipment(1L), new Shipment(2L));
        doReturn(shipments).when(mShipmentService).getByIds(captor.capture());

        doReturn(new Procurement(new ProcurementId(1L, 10L, 100L))).when(mProcurementFactory).buildFromShipmentIfIdMatches(new ProcurementId(1L, 10L, 100L), new Shipment(1L));
        doReturn(new Procurement(new ProcurementId(2L, 20L, 200L))).when(mProcurementFactory).buildFromShipmentIfIdMatches(new ProcurementId(2L, 20L, 200L), new Shipment(2L));

        List<Procurement> expected = List.of(
            new Procurement(new ProcurementId(1L, 10L, 100L)),
            new Procurement(new ProcurementId(2L, 20L, 200L))
        );
        assertEquals(expected, service.getByIds(List.of(
            () -> new ProcurementId(1L, 10L, 100L),
            () -> new ProcurementId(2L, 20L, 200L)
        )));

        Iterator<? extends Identified<Long>> ids = captor.getValue().iterator();
        assertEquals(1L, ids.next().getId());
        assertEquals(2L, ids.next().getId());
    }

    @Test
    public void testGetByAccessorIds_DelegatesRequestToGetByIds() {
        service = spy(service);

        ArgumentCaptor<Collection<? extends Identified<ProcurementId>>> captor = ArgumentCaptor.forClass(Collection.class);
        doReturn(List.of(new Procurement())).when(service).getByIds(captor.capture());

        List<? extends ProcurementAccessor> accessors = List.of(new ProcurementAccessor() {
            @Override
            public void setProcurement(Procurement procurement) {
            }
            @Override
            public Procurement getProcurement() {
                return new Procurement(new ProcurementId(1L, 10L, 100L));
            }
        });

        List<Procurement> procurements = service.getByAccessorIds(accessors);
        assertEquals(List.of(new Procurement()), procurements);
    }

    @Test
    public void testAdd_ReturnsProcurementsAfterAddingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(3L);
            return pOs;
        }).when(mPoService).putBySupplierAndOrderNumber(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            return invoices;
        }).when(mInvoiceService).add(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            return shipments;
        }).when(mShipmentService).add(anyList());

        Shipment savedShipment = new Shipment(1L);
        savedShipment.setInvoiceItemsFromInvoice(new Invoice(2L));
        doReturn(new Procurement()).when(mProcurementFactory).buildFromShipment(savedShipment);

        List<BaseProcurement<? extends BaseProcurementItem>> additions = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
        {
            add(null);
            add(new Procurement(new Shipment(), new Invoice(), new PurchaseOrder(), null));
        }};
        List<Procurement> procurements = service.add(additions);

        List<Procurement> expected = List.of(new Procurement());
        assertEquals(expected, procurements);
    }

    @Test
    public void testPut_ReturnsProcurementsAfterPutingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(3L);
            return pOs;
        }).when(mPoService).put(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            return invoices;
        }).when(mInvoiceService).put(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            return shipments;
        }).when(mShipmentService).put(anyList());

        Shipment savedShipment = new Shipment(1L);
        savedShipment.setInvoiceItemsFromInvoice(new Invoice(2L));
        doReturn(new Procurement()).when(mProcurementFactory).buildFromShipment(savedShipment);

        List<UpdateProcurement<? extends UpdateProcurementItem>> updates = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
        {
            add(null);
            add(new Procurement(new Shipment(), new Invoice(), new PurchaseOrder(), null));
        }};
        List<Procurement> procurements = service.put(updates);

        List<Procurement> expected = List.of(new Procurement());
        assertEquals(expected, procurements);
    }

    @Test
    public void testPatch_ReturnsProcurementsAfterPatchingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
        doAnswer(inv -> {
            List<PurchaseOrder> pOs = inv.getArgument(0, List.class);
            pOs.get(0).setId(3L);
            return pOs;
        }).when(mPoService).patch(anyList());
        doAnswer(inv -> {
            List<Invoice> invoices = inv.getArgument(0, List.class);
            invoices.get(0).setId(2L);
            return invoices;
        }).when(mInvoiceService).patch(anyList());
        doAnswer(inv -> {
            List<Shipment> shipments = inv.getArgument(0, List.class);
            shipments.get(0).setId(1L);
            return shipments;
        }).when(mShipmentService).patch(anyList());

        Shipment savedShipment = new Shipment(1L);
        savedShipment.setInvoiceItemsFromInvoice(new Invoice(2L));
        doReturn(new Procurement()).when(mProcurementFactory).buildFromShipment(savedShipment);

        List<UpdateProcurement<? extends UpdateProcurementItem>> updates = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
        {
            add(null);
            add(new Procurement(new Shipment(), new Invoice(), new PurchaseOrder(), null));
        }};
        List<Procurement> procurements = service.patch(updates);

        List<Procurement> expected = List.of(new Procurement());
        assertEquals(expected, procurements);
    }

}
