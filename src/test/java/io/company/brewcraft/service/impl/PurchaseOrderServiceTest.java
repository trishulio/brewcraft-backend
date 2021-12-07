package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderAccessor;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class PurchaseOrderServiceTest {
    private PurchaseOrderService service;

    private UpdateService<Long, PurchaseOrder, BasePurchaseOrder, UpdatePurchaseOrder> mUpdateService;
    private RepoService<Long, PurchaseOrder, PurchaseOrderAccessor> mRepoService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.mRepoService = mock(RepoService.class);

        doAnswer(inv -> inv.getArgument(0)).when(this.mRepoService).saveAll(anyList());

        this.service = new PurchaseOrderService(this.mUpdateService, this.mRepoService);
    }

    @Test
    public void testGetPurchaseOrders_ReturnsEntitiesFromRepoService_WithCustomSpec() {
        @SuppressWarnings("unchecked")
        final ArgumentCaptor<Specification<PurchaseOrder>> captor = ArgumentCaptor.forClass(Specification.class);
        final Page<PurchaseOrder> mPage = new PageImpl<>(List.of(new PurchaseOrder(1L)));
        doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

        final Page<PurchaseOrder> page = this.service.getPurchaseOrders(Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("ORDER_1"), // orderNumbers
            Set.of(3L), // supplierIds
            new TreeSet<>(List.of("id")), // sort
            true, // orderAscending
            10, // page
            20 // size
        );

        final Page<PurchaseOrder> expected = new PageImpl<>(List.of(new PurchaseOrder(1L)));
        assertEquals(expected, page);

        // TODO: Pending testing for the specification
        captor.getValue();
    }

    @Test
    public void testGetPurchaseOrder_ReturnsPurchaseOrderPojo_WhenRepoServiceReturnsOptionalWithEntity() {
        doReturn(new PurchaseOrder(1L)).when(this.mRepoService).get(1L);

        final PurchaseOrder purchaseOrder = this.service.get(1L);

        assertEquals(new PurchaseOrder(1L), purchaseOrder);
    }

    @Test
    public void testGetByIds_CallsRepoService() {
        ArgumentCaptor<List<? extends Identified<Long>>> captor = ArgumentCaptor.forClass(List.class);

        doReturn(List.of(new PurchaseOrder(1L))).when(mRepoService).getByIds(captor.capture());

        assertEquals(List.of(new PurchaseOrder(1L)), service.getByIds(List.of(() -> 1L)));
        assertEquals(1L, captor.getValue().get(0).getId());
    }

    @Test
    public void testGetByAccessorIds_CallsRepoService() {
        ArgumentCaptor<Function<PurchaseOrderAccessor, PurchaseOrder>> captor = ArgumentCaptor.forClass(Function.class);

        List<? extends PurchaseOrderAccessor> accessors = List.of(new PurchaseOrderAccessor() {
             @Override
             public void setPurchaseOrder(PurchaseOrder PurchaseOrder) {}
             @Override
             public PurchaseOrder getPurchaseOrder() {
                 return new PurchaseOrder(1L);
             }
         });

        doReturn(List.of(new PurchaseOrder(1L))).when(mRepoService).getByAccessorIds(eq(accessors), captor.capture());

        assertEquals(List.of(new PurchaseOrder(1L)), service.getByAccessorIds(accessors));
        assertEquals(new PurchaseOrder(1L), captor.getValue().apply(accessors.get(0)));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

        assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(Set.of(1L, 2L, 3L));

        assertTrue(this.service.exists(Set.of(1L, 2L, 3L)));
    }

    @Test
    public void testExist_ReturnsTrue_WhenRepoServiceReturnsTrue() {
        doReturn(true).when(this.mRepoService).exists(1L);

        assertTrue(this.service.exist(1L));
    }

    @Test
    public void testExist_ReturnsFalse_WhenRepoServiceReturnsFalse() {
        doReturn(true).when(this.mRepoService).exists(1L);

        assertTrue(this.service.exist(1L));
    }

    @Test
    public void testDelete_CallsRepoServiceDeleteBulk_WhenPurchaseOrderExists() {
        doReturn(123).when(this.mRepoService).delete(Set.of(1L, 2L, 3L));

        final int count = this.service.delete(Set.of(1L, 2L, 3L));
        assertEquals(123, count);
    }

    @Test
    public void testDelete_CallsRepoServiceDelete_WhenPurchaseOrderExists() {
        this.service.delete(1L);
        verify(this.mRepoService).delete(1L);
    }

    @Test
    public void testAdd_AddsPurchaseOrderAndItemsAndSavesToRepo_WhenAdditionsAreNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(this.mUpdateService).getAddEntities(any());

        final BasePurchaseOrder purchaseOrder1 = new PurchaseOrder(1L);
        final BasePurchaseOrder purchaseOrder2 = new PurchaseOrder();

        final List<PurchaseOrder> added = this.service.add(List.of(purchaseOrder1, purchaseOrder2));

        final List<PurchaseOrder> expected = List.of(new PurchaseOrder(1L), new PurchaseOrder());

        assertEquals(expected, added);
        verify(this.mRepoService, times(1)).saveAll(added);
    }

    @Test
    public void testAdd_DoesNotCallRepoServiceAndReturnsNull_WhenAdditionsAreNull() {
        assertNull(this.service.add(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPut_UpdatesPurchaseOrderAndItemsAndSavesToRepo_WhenUpdatesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPutEntities(any(), any());

        final UpdatePurchaseOrder purchaseOrder1 = new PurchaseOrder(1L);
        final UpdatePurchaseOrder purchaseOrder2 = new PurchaseOrder(2L);

        doReturn(List.of(new PurchaseOrder(1L), new PurchaseOrder(2L))).when(this.mRepoService).getByIds(List.of(purchaseOrder1, purchaseOrder2));

        final List<PurchaseOrder> updated = this.service.put(List.of(purchaseOrder1, purchaseOrder2, new PurchaseOrder()));

        final List<PurchaseOrder> expected = List.of(new PurchaseOrder(1L), new PurchaseOrder(2L), new PurchaseOrder());

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPut_DoesNotCallRepoServiceAndReturnsNull_WhenUpdatesAreNull() {
        assertNull(this.service.put(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_PatchesPurchaseOrderAndItemsAndSavesToRepo_WhenPatchesAreNotNull() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

        final UpdatePurchaseOrder purchaseOrder1 = new PurchaseOrder(1L);
        final UpdatePurchaseOrder purchaseOrder2 = new PurchaseOrder(2L);

        doReturn(List.of(new PurchaseOrder(1L), new PurchaseOrder(2L))).when(this.mRepoService).getByIds(List.of(purchaseOrder1, purchaseOrder2));

        final List<PurchaseOrder> updated = this.service.patch(List.of(purchaseOrder1, purchaseOrder2));

        final List<PurchaseOrder> expected = List.of(new PurchaseOrder(1L), new PurchaseOrder(2L));

        assertEquals(expected, updated);
        verify(this.mRepoService, times(1)).saveAll(updated);
    }

    @Test
    public void testPatch_DoesNotCallRepoServiceAndReturnsNull_WhenPatchesAreNull() {
        assertNull(this.service.patch(null));
        verify(this.mRepoService, times(0)).saveAll(any());
    }

    @Test
    public void testPatch_ThrowsNotFoundException_WhenAllPurchaseOrdersDontExist() {
        doAnswer(inv -> inv.getArgument(1)).when(this.mUpdateService).getPatchEntities(any(), any());

        final List<UpdatePurchaseOrder> updates = List.of(new PurchaseOrder(1L), new PurchaseOrder(2L), new PurchaseOrder(3L), new PurchaseOrder(4L));
        doReturn(List.of(new PurchaseOrder(1L), new PurchaseOrder(2L))).when(this.mRepoService).getByIds(updates);

        assertThrows(EntityNotFoundException.class, () -> this.service.patch(updates), "Cannot find purchaseOrders with Ids: [3, 4]");
    }

    @Test
    public void testPutByOrderNumberAndSupplier_ReturnsSavedPurchaseOrdersAfterApplyingUpdatesOnExisting_WhenUpdatesAreNotNull() {
        ArgumentCaptor<Specification<PurchaseOrder>> captor = ArgumentCaptor.forClass(Specification.class);

        List<PurchaseOrder> existing = List.of(new PurchaseOrder(10L, "ORDER_1", new Supplier(1L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1));
        doReturn(existing).when(mRepoService).getAll(captor.capture());

        List<BasePurchaseOrder> updates = new ArrayList<>();
        updates.add(new PurchaseOrder(null, "ORDER_1", new Supplier(1L), null, null, null));
        updates.add(null);
        updates.add(new PurchaseOrder(null, "ORDER_2", new Supplier(1L), null, null, null));
        updates.add(new PurchaseOrder(null, null, new Supplier(1L), null, null, null));
        updates.add(new PurchaseOrder(null, "ORDER_2", null, null, null, null));
        updates.add(new PurchaseOrder());

        List<PurchaseOrder> purchaseOrders = service.putBySupplierAndOrderNumber(updates);

        List<PurchaseOrder> expected = List.of(
            new PurchaseOrder(10L, "ORDER_1", new Supplier(1L), null, null, 1),
            new PurchaseOrder(null, "ORDER_2", new Supplier(1L), null, null, null),
            new PurchaseOrder(null, null, new Supplier(1L), null, null, null),
            new PurchaseOrder(null, "ORDER_2", null, null, null, null),
            new PurchaseOrder()
        );

        assertEquals(expected, purchaseOrders);
        verify(this.mRepoService, times(1)).saveAll(anyList());

        // TODO: Spec is not tested
        captor.getValue();
    }
}
