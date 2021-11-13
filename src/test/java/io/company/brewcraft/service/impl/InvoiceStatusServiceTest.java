package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceStatus;
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
        doReturn(Optional.of(new InvoiceStatus(99L))).when(mRepo).findById(99L);

        InvoiceStatus invoiceStatus = service.getStatus(99L);
        assertEquals(new InvoiceStatus(99L), invoiceStatus);
    }

    @Test
    public void testGetInvoiceStatus_ReturnsNull_WhenEntityDoesNotExists() {
        doReturn(new ArrayList<>()).when(mRepo).findAllById(Set.of(99L));

        InvoiceStatus invoiceStatus = service.getStatus(99L);
        assertNull(invoiceStatus);
    }
}
