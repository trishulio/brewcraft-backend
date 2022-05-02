package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.BaseProcurementItem;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.model.procurement.UpdateProcurementItem;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.procurement.ProcurementService;

public class ProcurementServiceTest {
    private ProcurementService service;

    private InvoiceService mInvoiceService;
    private ShipmentService mShipmentService;
    private RepoService<ProcurementId, Procurement, ProcurementAccessor> mRepoService;

    @BeforeEach
    public void init() {
        mInvoiceService = mock(InvoiceService.class);
        mShipmentService = mock(ShipmentService.class);
        mRepoService = mock(RepoService.class);

        doAnswer(inv -> inv.getArgument(0, List.class)).when(mRepoService).saveAll(anyList());

        service = new ProcurementService(mInvoiceService, mShipmentService, mRepoService);
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(mRepoService).exists(Set.of(new ProcurementId(1L, 10L)));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L)));

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(false).when(mRepoService).exists(Set.of(new ProcurementId(1L, 10L)));

        boolean exists = service.exists(Set.of(new ProcurementId(1L, 10L)));

        assertFalse(exists);
    }

    @Test
    public void testExist_ReturnsTrue_WhenRepoServiceIsTrue() {
        doReturn(true).when(mRepoService).exists(new ProcurementId(1L, 10L));

        boolean exists = service.exist(new ProcurementId(1L, 10L));

        assertTrue(exists);
    }

    @Test
    public void testExist_ReturnsFalse_WhenRepoServiceIsFalse() {
        doReturn(false).when(mRepoService).exists(new ProcurementId(1L, 10L));

        boolean exists = service.exist(new ProcurementId(1L, 10L));

        assertFalse(exists);
    }

    @Test
    public void testDelete_Set_ReturnsRepoServiceDeleteCount() {
        doReturn(10L).when(mRepoService).delete(Set.of(new ProcurementId(1L, 10L)));

        long count = service.delete(Set.of(new ProcurementId(1L, 10L)));

        assertEquals(10L, count);
    }

    @Test
    public void testDelete_Id_ReturnsRepoServiceDeleteCount() {
        doReturn(10L).when(mRepoService).delete(new ProcurementId(1L, 10L));

        long count = service.delete(new ProcurementId(1L, 10L));

        assertEquals(10L, count);
    }

    @Test
    public void testGetAll_BuildsSpecAndReturnsPageOfProcurementsFromRepoService() {
        ArgumentCaptor<Specification<Procurement>> captor = ArgumentCaptor.forClass(Specification.class);
        doReturn(new PageImpl<>(List.of(new Procurement(new ProcurementId(1L, 10L))))).when(mRepoService).getAll(captor.capture(), eq(new TreeSet<>(Set.of("col_1", "col_2"))), eq(true), eq(1), eq(10));

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
            new BigDecimal("1"), // totalAmountFrom
            new BigDecimal("2"), // totalAmountTo
            new BigDecimal("3"), // subTotalAmountFrom
            new BigDecimal("4"), // subTotalAmountTo
            new BigDecimal("5"), // pstAmountFrom
            new BigDecimal("6"), // pstAmountTo
            new BigDecimal("7"), // gstAmountFrom
            new BigDecimal("8"), // gstAmountTo
            new BigDecimal("9"), // hstAmountFrom
            new BigDecimal("10"), // hstAmountTo
            new BigDecimal("11"), // totalTaxAmountFrom
            new BigDecimal("12"), // totalTaxAmountTo
            new BigDecimal("13"), // invoiceItemTotalAmountFrom
            new BigDecimal("14"), // invoiceItemTotalAmountTo
            new BigDecimal("15"), // invoiceItemSubTotalAmountFrom
            new BigDecimal("16"), // invoiceItemSubTotalAmountTo
            new BigDecimal("17"), // invoiceItemPstAmountFrom
            new BigDecimal("18"), // invoiceItemPstAmountTo
            new BigDecimal("19"), // invoiceItemGstAmountFrom
            new BigDecimal("20"), // invoiceItemGstAmountTo
            new BigDecimal("21"), // invoiceItemHstAmountFrom
            new BigDecimal("22"), // invoiceItemHstAmountTo
            new BigDecimal("23"), // invoiceItemTotalTaxAmountFrom
            new BigDecimal("24"), // invoiceItemTotalTaxAmountTo
            new BigDecimal("25"), // freightAmountFrom
            new BigDecimal("26"), // freightAmountTo
            Set.of(30L), // invoiceStatusIds,
            Set.of(200L), // supplierIds,
            // misc
            new TreeSet<>(Set.of("col_1", "col_2")), // sort,
            true, // orderAscending,
            1, // page,
            10 //size
        );

        Page<Procurement> expected = new PageImpl<>(List.of(new Procurement(new ProcurementId(1L,  10L))));
        assertEquals(expected, page);

        // TODO: Specs are not tested yet
        captor.getValue();
    }

    @Test
    public void testGet_ReturnsProcurementFromRepoService() {
        doReturn(new Procurement(new ProcurementId(1L, 10L))).when(mRepoService).get(new ProcurementId(1L, 10L));

        assertEquals(new Procurement(new ProcurementId(1L, 10L)), service.get(new ProcurementId(1L, 10L)));
    }

    @Test
    public void testGetByIds_ReturnsProcurementsFromShipments() {
        doReturn(List.of(new Procurement(new ProcurementId(1L, 10L)))).when(mRepoService).getByIds(Set.of(new Procurement()));

        List<Procurement> procurements = service.getByIds(Set.of(new Procurement()));

        assertEquals(List.of(new Procurement(new ProcurementId(1L, 10L))), procurements);
    }

    @Test
    public void testGetByAccessorIds_DelegatesRequestToGetByIds() {
        List<? extends ProcurementAccessor> accessors = List.of(new ProcurementAccessor() {
            @Override
            public void setProcurement(Procurement procurement) {
            }
            @Override
            public Procurement getProcurement() {
                return new Procurement(new ProcurementId(1L, 10L));
            }
        });

        ArgumentCaptor<Function<ProcurementAccessor, Procurement>> captor = ArgumentCaptor.forClass(Function.class);

        doReturn(List.of(new Procurement(new ProcurementId(1L, 10L)))).when(mRepoService).getByAccessorIds(eq(accessors), captor.capture());

        List<Procurement> procurements = service.getByAccessorIds(accessors);

        assertEquals(List.of(new Procurement(new ProcurementId(1L, 10L))), procurements);
        assertEquals(new Procurement(new ProcurementId(1L, 10L)), captor.getValue().apply(accessors.get(0)));
    }

    @Test
    public void testAdd_ReturnsProcurementsAfterAddingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
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

        List<BaseProcurement<? extends BaseProcurementItem>> additions = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
        {
            add(null);
            add(new Procurement(new Shipment(), new Invoice()));
        }};
        List<Procurement> procurements = service.add(additions);

        List<Procurement> expected = List.of(new Procurement(new Shipment(1L), new Invoice(2L)));
        assertEquals(expected, procurements);

        verify(mRepoService, times(1)).saveAll(procurements);
    }

    @Test
    public void testPut_ReturnsProcurementsAfterPutingInvoicesAndShipmentsProcurements_WhenEntitiesAreNotNull() {
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

        doReturn(List.of()).when(mRepoService).getByIds(anyList());

        List<UpdateProcurement<? extends UpdateProcurementItem>> updates = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
        {
            add(null);
            add(new Procurement(new Shipment(), new Invoice()));
        }};
        List<Procurement> procurements = service.put(updates);

        List<Procurement> expected = List.of(new Procurement(new Shipment(1L), new Invoice(2L)));
        assertEquals(expected, procurements);

        verify(mRepoService, times(1)).saveAll(procurements);
        verify(mRepoService, times(1)).getByIds(updates);
    }

    @Test
    public void testPut_ReturnsExistingPurchaseOrders_WhenTheyArePresent() {
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

        List<Procurement> existingProcurements = List.of(new Procurement(new ProcurementId(1L, 2L)));
        doReturn(existingProcurements).when(mRepoService).getByIds(anyList());

        List<UpdateProcurement<? extends UpdateProcurementItem>> updates = List.of(new Procurement(new Shipment(), new Invoice()));
        List<Procurement> procurements = service.put(updates);

        assertSame(existingProcurements.get(0), procurements.get(0));

        verify(mRepoService, times(1)).saveAll(procurements);
        verify(mRepoService, times(1)).getByIds(updates);
    }

    @Test
    public void testPatch_ReturnsProcurementsAfterPatchingPurchaseOrdersInvoicesAndShipments_WhenInvoicesAndPurchaseOrdersAreNotNull() {
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

        doReturn(List.of(new Procurement(new ProcurementId(1L, 2L)))).when(mRepoService).getByIds(anyList());

        List<UpdateProcurement<? extends UpdateProcurementItem>> updates = new ArrayList<>() {
            private static final long serialVersionUID = 1L;
        {
            add(null);
            add(new Procurement(new Shipment(), new Invoice()));
        }};
        List<Procurement> procurements = service.patch(updates);

        List<Procurement> expected = List.of(new Procurement(new Shipment(1L), new Invoice(2L)));
        assertEquals(expected, procurements);

        verify(mRepoService, times(1)).saveAll(procurements);
    }

    @Test
    public void testPatch_ThrowsEntityNotFoundException_WhenExistingProcurementDoesNotExist() {
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

        doReturn(List.of()).when(mRepoService).getByIds(anyList());

        List<UpdateProcurement<? extends UpdateProcurementItem>> updates = List.of(new Procurement(new Shipment(), new Invoice()));
        assertThrows(EntityNotFoundException.class, () -> service.patch(updates));
    }
}
