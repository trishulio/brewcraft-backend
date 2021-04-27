package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;

public class EnhancedInvoiceRepositoryImplTest {

    private EnhancedInvoiceRepository repo;

    private InvoiceItemRepository mItemRepo;
    private InvoiceStatusRepository mStatusRepo;
    private PurchaseOrderRepository mPoRepo;

    @BeforeEach
    public void init() {
        mStatusRepo = mock(InvoiceStatusRepository.class);
        mPoRepo = mock(PurchaseOrderRepository.class);
        mItemRepo = mock(InvoiceItemRepository.class);

        repo = new EnhancedInvoiceRepositoryImpl(mItemRepo, mStatusRepo, mPoRepo);
    }
    
    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Invoice> invoices = List.of(
            new Invoice(1L),
            new Invoice(2L)
        );
        
        List<InvoiceItem> items = List.of(
            new InvoiceItem(10L),
            new InvoiceItem(100L),
            new InvoiceItem(20L),
            new InvoiceItem(200L)
        );
        
        invoices.get(0).setItems(List.of(items.get(0), items.get(1)));
        invoices.get(1).setItems(List.of(items.get(2), items.get(3)));

        repo.refresh(invoices);
        
        verify(mStatusRepo, times(1)).refreshAccessors(invoices);
        verify(mPoRepo, times(1)).refreshAccessors(invoices);
        
        verify(mItemRepo, times(1)).refresh(items);
    }
}
