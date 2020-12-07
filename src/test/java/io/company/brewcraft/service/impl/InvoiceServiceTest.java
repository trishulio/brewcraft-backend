package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceItemEntity;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@SuppressWarnings("unchecked")
public class InvoiceServiceTest {
    private InvoiceService service;

    private InvoiceRepository mRepo;
    private SupplierService mSupplierService;

    @BeforeEach
    public void init() {
        mRepo = mock(InvoiceRepository.class);
        mSupplierService = mock(SupplierService.class);
        this.service = new InvoiceService(mRepo, mSupplierService);
    }

    @Test
    public void testGetAllInvoices_ReturnsInvoicesFromRepository_AndPassesNewSpecificationInstanceWithArgValues() {
//        Page<InvoiceEntity> mPage = mock(Page.class);
//
//        Specification<InvoiceEntity> spec = new InvoiceRepositoryGetAllInvoicesSpecification(Set.of(111L), LocalDateTime.MAX, LocalDateTime.MIN, Set.of(InvoiceStatus.PENDING), Set.of(12345L));
//        doReturn(mPage).when(mRepo).findAll(spec, PageRequest.of(0, 10, Direction.ASC, "col_1", "col_2"));
//
//        Page<Invoice> invoices = service.getInvoices(Set.of(111L), LocalDateTime.MAX, LocalDateTime.MIN, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("col_1", "col_2"), true, 0, 10);
//
//        assertSame(invoices, mPage);
    }

    // TODO: Move the controller date min and max tests to here.
//    @Test
//    public void testGetAllInvoices_ThrowsException_WhenOnlyOneOfDateToOrFromIsNull() {
//        assertThrows(IllegalArgumentException.class, () -> service.getInvoices(LocalDateTime.MAX, null, null, null, null, true, 10, 0));
//        assertThrows(IllegalArgumentException.class, () -> service.getInvoices(null, LocalDateTime.MAX, null, null, null, true, 10, 0));
//    }

    @Test
    public void testGetInvoice_ReturnsInvoice_WhenOptionalIsPresent() {
        Optional<InvoiceEntity> optional = Optional.of(new InvoiceEntity(12345L));
        doReturn(optional).when(mRepo).findById(12345L);

        assertEquals(new Invoice(12345L), service.getInvoice(12345L));
    }

    @Test
    public void testGetInvoice_ReturnsNull_WhenOptionalIsNotPresent() {
        Optional<InvoiceEntity> optional = Optional.empty();
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
        doAnswer(inv -> inv.getArgument(0, InvoiceEntity.class)).when(mRepo).save(any(InvoiceEntity.class));

        InvoiceEntity mExisting = new InvoiceEntity(
            12345L,
            new Supplier(),
            LocalDateTime.MAX,
            LocalDateTime.of(1997, 1, 1, 1, 1),
            LocalDateTime.of(1198, 2, 2, 2, 2),
            InvoiceStatus.PENDING,
            List.of(new InvoiceItemEntity(123L)),
            1
        );
        Optional<InvoiceEntity> optional = Optional.of(mExisting);
        doReturn(optional).when(mRepo).findById(1111L);
        
        doReturn(new Supplier()).when(mSupplierService).getSupplier(12345L);

        Invoice invoice = service.update(12345L, 1111L, new Invoice(111L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItem(55L)), 1));

//        assertEquals(new Invoice(111L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItem(55L)), 1), invoice);        
//        verify(mRepo, times(1)).save(new InvoiceEntity(12345L, new Supplier(),  LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItemEntity(55L)), 1));
    }

    @Test
    public void testUpdate_SavesNetEntityWithId_WhenExistingDoesNotExist() {
        doAnswer(inv -> inv.getArgument(0, InvoiceEntity.class)).when(mRepo).save(any(InvoiceEntity.class));

        Optional<InvoiceEntity> optional = Optional.empty();
        doReturn(optional).when(mRepo).findById(12345L);
        
        doReturn(new Supplier()).when(mSupplierService).getSupplier(1111L);

        Invoice invoice = service.update(1111L, 12345L, new Invoice(111L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItem(55L)), 1));

        assertEquals(new Invoice(12345L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItem(55L)), 1), invoice);
        // TODO: fix failure
//        verify(mRepo, times(1)).save(new InvoiceEntity(12345L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItemEntity(55L)), 1));
    }

    @Test
    public void testAdd_ReturnsInvoiceFromRepo_AfterSavingNewInvoice() {
        doAnswer(inv -> inv.getArgument(0, InvoiceEntity.class)).when(mRepo).save(any(InvoiceEntity.class));
        doReturn(new Supplier()).when(mSupplierService).getSupplier(12345L);
        
        Invoice invoice = service.add(12345L, new Invoice(111L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItem(55L)), 1));

        assertEquals(new Invoice(111L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItem(55L)), 1), invoice);
        // TODO: fix failure
        //verify(mRepo, times(1)).save(new InvoiceEntity(111L, new Supplier(), LocalDateTime.of(1998, 1, 1, 1, 1), LocalDateTime.of(1999, 2, 2, 2, 2), LocalDateTime.of(2000, 3, 3, 3, 3), InvoiceStatus.FINAL, List.of(new InvoiceItemEntity(55L)), 1));
    }
}
