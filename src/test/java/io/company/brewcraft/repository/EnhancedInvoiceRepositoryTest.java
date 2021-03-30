package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.pojo.Material;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedInvoiceRepositoryTest {

    private EnhancedInvoiceRepository repo;

    private InvoiceRepository mInvoiceRepo;
    private InvoiceStatusRepository mStatusRepo;
    private PurchaseOrderRepository mPoRepo;
    private MaterialRepository mMaterialRepo;

    @BeforeEach
    public void init() {
        mInvoiceRepo = mock(InvoiceRepository.class);
        mStatusRepo = mock(InvoiceStatusRepository.class);
        mPoRepo = mock(PurchaseOrderRepository.class);
        mMaterialRepo = mock(MaterialRepository.class);

        repo = new EnhancedInvoiceRepositoryImpl(mInvoiceRepo, mStatusRepo, mPoRepo, mMaterialRepo);
    }

    @Test
    public void testRefreshAndAdd_ReturnsInvoice_WithRefreshedChildEntities() {
        doAnswer(i -> i.getArgument(0, Invoice.class)).when(mInvoiceRepo).saveAndFlush(any(Invoice.class));

        doReturn(Optional.of(new PurchaseOrder(1L))).when(mPoRepo).findById(1L);
        doReturn(List.of(new InvoiceStatus(2L, "FINAL"))).when(mStatusRepo).findByNames(Set.of("FINAL"));

        InvoiceItem item1 = new InvoiceItem(11L);
        InvoiceItem item2 = new InvoiceItem(12L);
        InvoiceItem item3 = new InvoiceItem(13L);
        item1.setMaterial(new Material(3L));
        item2.setMaterial(new Material(4L));
        item3.setMaterial(new Material(5L));
        List<InvoiceItem> items = List.of(item1, item2, item3);

        Invoice invoice = new Invoice();
        invoice.setItems(items);
        invoice.setStatus(new InvoiceStatus(null, "FINAL"));

        Invoice ret = repo.save(1L, invoice);

        verify(mInvoiceRepo, times(1)).saveAndFlush(invoice);

        assertEquals(new PurchaseOrder(1L), ret.getPurchaseOrder());
        assertEquals(new InvoiceStatus(2L, "FINAL"), ret.getStatus());
        Iterator<InvoiceItem> it = ret.getItems().iterator();
        assertEquals(new Material(3L), it.next().getMaterial());
        assertEquals(new Material(4L), it.next().getMaterial());
        assertEquals(new Material(5L), it.next().getMaterial());
    }

    @Test
    public void testRefreshAndAdd_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
        doReturn(Optional.empty()).when(mPoRepo).findById(1L);

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, new Invoice()), "PurchaseOrder not found with id: 1");
    }

    @Test
    public void testRefreshAndAdd_ThrowsEntityNotFoundException_WhenInvoiceStatusNameIsNonExistent() {
        doReturn(Optional.of(new PurchaseOrder(1L))).when(mPoRepo).findById(1L);
        doReturn(new ArrayList<>()).when(mStatusRepo).findByNames(Set.of("PENDING"));

        Invoice invoice = new Invoice();
        invoice.setStatus(new InvoiceStatus(2L, "PENDING"));

        assertThrows(EntityNotFoundException.class, () -> repo.save(1L, invoice), "InvoiceStatus not found with name: PENDING");
    }
}
