package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.service.InvoiceStatusService;

public class InvoiceStatusServiceTest {
    private InvoiceStatusService service;

    private InvoiceStatusRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(InvoiceStatusRepository.class);
        service = new InvoiceStatusService(mRepo);
    }

    @Test
    public void testGetInvoiceStatus_ReturnsPojo_WhenEntityExists() {
        InvoiceStatus mEntity = new InvoiceStatus(1L, "FINAL");
        doReturn(Optional.of(mEntity)).when(mRepo).findByName("FINAL");

        InvoiceStatus status = service.getInvoiceStatus("FINAL");
        assertEquals(new InvoiceStatus(1L, "FINAL"), status);
    }

    @Test
    public void testGetInvoiceStatus_ReturnsNull_WhenEntityDoesNotExists() {
        doReturn(Optional.empty()).when(mRepo).findByName("FINAL");

        InvoiceStatus status = service.getInvoiceStatus("FINAL");
        assertNull(status);
    }
}
