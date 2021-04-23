package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
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
        InvoiceStatus mEntity = new InvoiceStatus("FINAL");
        doReturn(List.of(mEntity)).when(mRepo).findByNames(Set.of("FINAL"));

        InvoiceStatus status = service.getStatus("FINAL");
        assertEquals(new InvoiceStatus("FINAL"), status);
    }

    @Test
    public void testGetInvoiceStatus_ReturnsNull_WhenEntityDoesNotExists() {
        doReturn(new ArrayList<>()).when(mRepo).findByNames(Set.of("FINAL"));

        InvoiceStatus status = service.getStatus("FINAL");
        assertNull(status);
    }

    @Test
    public void testGetInvoiceStatus_ThrowsNPE_WhenNameIsNull() {
        assertThrows(NullPointerException.class, () -> service.getStatus(null));
    }

    @Test
    public void testGetStatuses_ReturnTheListOfStatuses_WhenArgIsNotNull() {
        InvoiceStatus mEntity = new InvoiceStatus("FINAL");
        doReturn(List.of(mEntity)).when(mRepo).findByNames(Set.of("FINAL"));

        List<InvoiceStatus> statuses = service.getStatuses(Set.of("FINAL"));
        assertEquals(1, statuses.size());
        assertEquals(new InvoiceStatus("FINAL"), statuses.get(0));
    }

    @Test
    public void testGetStatuses_ReturnsNull_WhenArgIsEmptySet() {
        List<InvoiceStatus> statuses = service.getStatuses(Set.of());
        assertNull(statuses);
    }

    @Test
    public void testGetStatuses_ThrowsNullPointerExceptioN_WhenArgIsNull() {
        assertThrows(NullPointerException.class, () -> service.getStatus(null));
    }
}
