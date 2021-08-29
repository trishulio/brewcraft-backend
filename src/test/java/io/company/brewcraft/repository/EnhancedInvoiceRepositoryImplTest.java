package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;

public class EnhancedInvoiceRepositoryImplTest {

    private EnhancedInvoiceRepository repo;

    private AccessorRefresher<Long, InvoiceAccessor, Invoice> mRefresher;

    private InvoiceItemRepository mItemRepo;
    private InvoiceStatusRepository mStatusRepo;
    private PurchaseOrderRepository mPoRepo;

    @BeforeEach
    public void init() {
        this.mStatusRepo = mock(InvoiceStatusRepository.class);
        this.mPoRepo = mock(PurchaseOrderRepository.class);
        this.mItemRepo = mock(InvoiceItemRepository.class);
        this.mRefresher = mock(AccessorRefresher.class);

        this.repo = new EnhancedInvoiceRepositoryImpl(this.mRefresher, this.mItemRepo, this.mStatusRepo, this.mPoRepo);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        final List<Invoice> invoices = List.of(
            new Invoice(1L),
            new Invoice(2L)
        );

        final List<InvoiceItem> items = List.of(
            new InvoiceItem(10L),
            new InvoiceItem(100L),
            new InvoiceItem(20L),
            new InvoiceItem(200L)
        );

        invoices.get(0).setItems(List.of(items.get(0), items.get(1)));
        invoices.get(1).setItems(List.of(items.get(2), items.get(3)));

        this.repo.refresh(invoices);

        verify(this.mStatusRepo, times(1)).refreshAccessors(invoices);
        verify(this.mPoRepo, times(1)).refreshAccessors(invoices);

        verify(this.mItemRepo, times(1)).refresh(items);
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends InvoiceAccessor> accessors = List.of(mock(InvoiceAccessor.class), mock(InvoiceAccessor.class));

        this.repo.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }
}
