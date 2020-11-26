package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceRepositoryGetAllInvoicesSpecification;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@SuppressWarnings("unchecked")
public class InvoiceServiceTest {
    private InvoiceService service;

    private InvoiceRepository mRepo;

    @BeforeEach
    public void init() {
        mRepo = mock(InvoiceRepository.class);
        this.service = new InvoiceService(mRepo);
    }

    @Test
    public void testGetAllInvoices_ReturnsInvoicesFromRepository_AndPassesNewSpecificationInstanceWithArgValues() {
        Page<Invoice> mPage = mock(Page.class);

        Specification<Invoice> spec = new InvoiceRepositoryGetAllInvoicesSpecification(new java.sql.Date(123), new java.sql.Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L));
        doReturn(mPage).when(mRepo).findAll(spec, PageRequest.of(0, 10, Direction.ASC, "col_1", "col_2"));

        Page<Invoice> invoices = service.getInvoices(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("col_1", "col_2"), true, 0, 10);

        assertSame(invoices, mPage);
    }

    @Test
    public void testGetAllInvoices_ThrowsException_WhenOnlyOneOfDateToOrFromIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.getInvoices(new Date(12345), null, null, null, null, true, 10, 0));
        assertThrows(IllegalArgumentException.class, () -> service.getInvoices(null, new Date(12345), null, null, null, true, 10, 0));
    }

    @Test
    public void testGetInvoice_ReturnsInvoice_WhenOptionalIsPresent() {
        Optional<Invoice> optional = Optional.of(new Invoice(12345L));
        doReturn(optional).when(mRepo).findById(12345L);

        assertEquals(new Invoice(12345L), service.getInvoice(12345L));
    }

    @Test
    public void testGetInvoice_ReturnsNull_WhenOptionalIsNotPresent() {
        Optional<Invoice> optional = Optional.empty();
        doReturn(optional).when(mRepo).findById(12345L);

        assertNull(service.getInvoice(12345L));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepoReturnsTrue() {
        doReturn(true).when(mRepo).existsById(12345L);

        assertTrue(service.exists(12345L));
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepoReturnsFalse() {
        doReturn(false).when(mRepo).existsById(12345L);

        assertFalse(service.exists(12345L));
    }

    @Test
    public void testDelete_CallsRepoDeleteById_WhenInvoiceExists() {
        doReturn(true).when(mRepo).existsById(12345L);
        service.delete(12345L);

        verify(mRepo, times(1)).deleteById(12345L);
    }

    @Test
    public void testDelete_ThrowsEntityNotFoundException_WhenInvoiceDoesNotExist() {
        doReturn(false).when(mRepo).existsById(12345L);

        assertThrows(EntityNotFoundException.class, () -> service.delete(12345L));
    }

    @Test
    public void testUpdate_SavesNewInvoiceAfterCopyingValuesFromExisting_WhenInvoiceExists() {
        doAnswer(inv -> inv.getArgument(0, Invoice.class)).when(mRepo).save(any(Invoice.class));

        Invoice mExisting = new Invoice(12345L, new Supplier(), new Date(123), InvoiceStatus.PENDING, List.of(new InvoiceItem(123L)), 1);
        Optional<Invoice> optional = Optional.of(mExisting);
        doReturn(optional).when(mRepo).findById(12345L);

        Invoice invoice = service.update(12345L, new Invoice(null, null, new Date(456), null, List.of(), 1));

        assertEquals(new Invoice(12345L, new Supplier(), new Date(456), InvoiceStatus.PENDING, List.of(), 1), invoice);
        verify(mRepo, times(1)).save(new Invoice(12345L, new Supplier(), new Date(456), InvoiceStatus.PENDING, List.of(), 1));
    }

    @Test
    public void testUpdate_SavesNetEntityWithId_WhenExistingDoesNotExist() {
        doAnswer(inv -> inv.getArgument(0, Invoice.class)).when(mRepo).save(any(Invoice.class));

        Optional<Invoice> optional = Optional.empty();
        doReturn(optional).when(mRepo).findById(12345L);

        Invoice invoice = service.update(12345L, new Invoice(null, null, new Date(456), null, List.of(), 1));

        assertEquals(new Invoice(12345L, null, new Date(456), null, List.of(), 1), invoice);
        verify(mRepo, times(1)).save(new Invoice(12345L, null, new Date(456), null, List.of(), 1));
    }

    @Test
    public void testAdd_ReturnsInvoiceFromRepo_AfterSavingNewInvoice() {
        doAnswer(inv -> inv.getArgument(0, Invoice.class)).when(mRepo).save(any(Invoice.class));

        Invoice invoice = service.add(new Invoice(null, new Supplier(), new Date(456), InvoiceStatus.PENDING, List.of(new InvoiceItem(456789L)), 1));

        assertEquals(new Invoice(null, new Supplier(), new Date(456), InvoiceStatus.PENDING, List.of(new InvoiceItem(456789L)), 1), invoice);
        verify(mRepo, times(1)).save(new Invoice(null, new Supplier(), new Date(456), InvoiceStatus.PENDING, List.of(new InvoiceItem(456789L)), 1));
    }
}
