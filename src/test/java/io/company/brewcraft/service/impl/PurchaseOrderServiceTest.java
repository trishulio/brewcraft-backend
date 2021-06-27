package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.repository.PurchaseOrderRepository;
import io.company.brewcraft.service.PurchaseOrderService;

public class PurchaseOrderServiceTest {

    private PurchaseOrderService service;
    private PurchaseOrderRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(PurchaseOrderRepository.class);
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
}
