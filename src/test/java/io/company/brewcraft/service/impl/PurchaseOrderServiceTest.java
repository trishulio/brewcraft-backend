package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.UpdatePurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class PurchaseOrderServiceTest {

    private PurchaseOrderService service;
    private PurchaseOrderRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(PurchaseOrderRepository.class);
        doAnswer(inv -> inv.getArgument(0, PurchaseOrder.class)).when(mRepo).saveAndFlush(any(PurchaseOrder.class));

        service = new PurchaseOrderService(mRepo);
    }

    @Test
    public void testGetPurchaseOrder_ReturnsPojo_WhenEntityExists() {
        PurchaseOrder mEntity = new PurchaseOrder(1L);
        doReturn(Optional.of(mEntity)).when(mRepo).findById(1L);

        PurchaseOrder po = service.getPurchaseOrder(1L);
        assertEquals(new PurchaseOrder(1L), po);
    }

    @Test
    public void testGetPurchaseOrder_ReturnsNull_WhenEntityDoesNotExists() {
        doReturn(java.util.Optional.empty()).when(mRepo).findById(1L);

        PurchaseOrder po = service.getPurchaseOrder(1L);
        assertNull(po);
    }

    @Test
    public void testExistsById_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(mRepo).existsById(1L);

        assertTrue(service.exists(1L));
    }

    @Test
    public void testExistsById_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(mRepo).existsById(1L);

        assertFalse(service.exists(1L));
    }

    @Test
    public void testExistsByIds_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(mRepo).existsByIds(Set.of(1L, 2L));

        assertTrue(service.exists(Set.of(1L, 2L)));
    }

    @Test
    public void testExistsByIds_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(mRepo).existsByIds(Set.of(1L, 2L));

        assertFalse(service.exists(Set.of(1L, 2L)));
    }

    @Test
    public void testGetAllPurchaseOrders_GetsPageFromRepoWithSpecsAndPageRequestBuiltFromParams() {
        ArgumentCaptor<Specification<PurchaseOrder>> specCaptor = ArgumentCaptor.forClass(Specification.class);

        List<PurchaseOrder> mContent = List.of(new PurchaseOrder(1L));
        doReturn(new PageImpl<>(mContent)).when(mRepo).findAll(specCaptor.capture(), eq(PageRequest.of(10, 20, Direction.DESC, "col_1", "col_2")));

        Page<PurchaseOrder> page = service.getAllPurchaseOrders(
            Set.of(1L, 2L),
            Set.of(3L, 4L),
            Set.of("ORDER_1", "ORDER_2"),
            Set.of(5L, 6L),
            new TreeSet<>(List.of("col_1", "col_2")),
            false,
            10,
            20
        );

        Page<PurchaseOrder> expected = new PageImpl<PurchaseOrder>(List.of(new PurchaseOrder(1L)));

        assertEquals(expected, page);

        Specification<PurchaseOrder> spec = specCaptor.getValue();
        // TODO: Specifications are currently not being tested.
    }

    @Test
    public void testPut_SavesNewEntity_WhenNoExistingEntityExistsForTheGivenId() {
        doReturn(Optional.empty()).when(mRepo).findById(1L);

        UpdatePurchaseOrder update = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(1L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );

        PurchaseOrder po = service.put(1L, update);

        PurchaseOrder expected = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(1L),
            null,
            null,
            null
        );
        assertEquals(expected, po);

        verify(mRepo, times(1)).saveAndFlush(po);
    }

    @Test
    public void testPut_OverridesExistingPurchaseOrder_WhenEntityExistsForGivenId() {
        doReturn(Optional.of(new PurchaseOrder(1L, null, null, null, null, 1))).when(mRepo).findById(1L);

        UpdatePurchaseOrder update = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(1L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );

        PurchaseOrder po = service.put(1L, update);

        PurchaseOrder expected = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(1L),
            null,
            null,
            1
        );
        assertEquals(expected, po);

        verify(mRepo, times(1)).saveAndFlush(po);
    }

    @Test
    public void testPut_ThrowsOptimisticLockException_WhenInputVersionDoesntMatchExistingVersion() {
        doReturn(Optional.of(new PurchaseOrder(1L, null, null, null, null, 1))).when(mRepo).findById(1L);

        assertThrows(OptimisticLockException.class, () -> service.put(1L, new PurchaseOrder(1L, null, null, null, null, 2)));
    }

    @Test
    public void testPatch_OverridesPropertiesOfExistingPurchaseOrder_WhenInputPropertiesAreNotNull() {
        doReturn(Optional.of(new PurchaseOrder(1L, null, new Supplier(1L), LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2001, 1, 1, 0, 0), 1))).when(mRepo).findById(1L);

        UpdatePurchaseOrder update = new PurchaseOrder(
            1L,
            "ORDER_1",
            null,
            LocalDateTime.of(2011, 1, 1, 0, 0),
            LocalDateTime.of(2012, 1, 1, 0, 0),
            1
        );

        PurchaseOrder po = service.patch(1L, update);

        PurchaseOrder expected = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(1L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );
        assertEquals(expected, po);

        verify(mRepo, times(1)).saveAndFlush(po);
    }

    @Test
    public void testPatch_ThrowsEntityNotFoundException_WhenEntityDoesNotExist() {
        doReturn(Optional.empty()).when(mRepo).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> service.patch(1L, new PurchaseOrder(1L)));
    }

    @Test
    public void testPatch_ThrowsOptimisticLockException_WhenInputVersionDoesntMatchExistingVersion() {
        doReturn(Optional.of(new PurchaseOrder(1L, null, null, null, null, 1))).when(mRepo).findById(1L);

        assertThrows(OptimisticLockException.class, () -> service.patch(1L, new PurchaseOrder(1L, null, null, null, null, 2)));
    }

    @Test
    public void testDelete_CallsDeleteByIdsOnAllIds() {
        service.delete(Set.of(1L, 2L, 3L));

        verify(mRepo, times(1)).deleteByIds(Set.of(1L, 2L, 3L));
    }
}
