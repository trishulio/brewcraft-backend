package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.BasePurchaseOrder;
import io.company.brewcraft.model.PurchaseOrder;
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
        final ArgumentCaptor<Specification<PurchaseOrder>> captor = ArgumentCaptor.forClass(Specification.class);
        final Page<PurchaseOrder> mPage = new PageImpl<>(List.of(new PurchaseOrder(1L)));
        doReturn(mPage).when(this.mRepoService).getAll(captor.capture(), eq(new TreeSet<>(List.of("id"))), eq(true), eq(10), eq(20));

        final Page<PurchaseOrder> page = this.service.getPurchaseOrders(Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("ORDER_1"), // orderNumbers
            Set.of(3L), // supplierIds
            new TreeSet<>(List.of("id")), // sortBy
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
}
