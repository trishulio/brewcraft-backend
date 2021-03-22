package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

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
}
