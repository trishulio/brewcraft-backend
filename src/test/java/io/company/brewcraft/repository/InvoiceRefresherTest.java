package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceAccessor;
import io.company.brewcraft.model.InvoiceItem;

public class InvoiceRefresherTest {

    private InvoiceRefresher invoiceRefresher;

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

        this.invoiceRefresher = new InvoiceRefresher(this.mRefresher, this.mItemRepo, this.mStatusRepo, this.mPoRepo);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        final List<Invoice> invoices = List.of(
            new Invoice(1L),
            new Invoice(2L)
        );

        final List<InvoiceItem> invoiceItems = List.of(
            new InvoiceItem(10L),
            new InvoiceItem(100L),
            new InvoiceItem(20L),
            new InvoiceItem(200L)
        );

        invoices.get(0).setInvoiceItems(List.of(invoiceItems.get(0), invoiceItems.get(1)));
        invoices.get(1).setInvoiceItems(List.of(invoiceItems.get(2), invoiceItems.get(3)));

        this.invoiceRefresher.refresh(invoices);

        verify(this.mStatusRepo, times(1)).refreshAccessors(invoices);
        verify(this.mPoRepo, times(1)).refreshAccessors(invoices);

        verify(this.mItemRepo, times(1)).refresh(invoiceItems);
    }

    @Test
    public void testRefresh_SkipsNullInvoices() {
        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(1L));
        invoices.add(null);
        invoices.add(new Invoice(2L));

        final List<InvoiceItem> invoiceItems = List.of(new InvoiceItem(10L), new InvoiceItem(20L));

        invoices.get(0).setInvoiceItems(List.of(invoiceItems.get(0)));
        invoices.get(2).setInvoiceItems(List.of(invoiceItems.get(1)));

        this.invoiceRefresher.refresh(invoices);

        verify(this.mStatusRepo, times(1)).refreshAccessors(invoices);
        verify(this.mItemRepo, times(1)).refresh(invoiceItems);
    }

    @Test
    public void testRefresh_DoesNotRefreshInvoices_WhenListIsNull() {
        this.invoiceRefresher.refresh(null);

        verify(this.mStatusRepo, times(1)).refreshAccessors(null);
        verify(this.mItemRepo, times(1)).refresh(null);
    }

    @Test
    public void testRefresh_DoesNotRefreshItems_WhenItemsAreNull() {
        final List<Invoice> invoices = List.of(
            new Invoice(1L),
            new Invoice(2L),
            new Invoice(3L)
        );

        this.invoiceRefresher.refresh(invoices);

        verify(this.mStatusRepo, times(1)).refreshAccessors(invoices);
        verify(this.mItemRepo, times(1)).refresh(List.of());
    }

    @Test
    public void testRefresh_DoesNotRefreshItems_WhenItemsSizeIs0() {
        final List<Invoice> invoices = List.of(
            new Invoice(1L),
            new Invoice(2L),
            new Invoice(3L)
        );
        invoices.get(0).setInvoiceItems(List.of());
        invoices.get(1).setInvoiceItems(List.of());
        invoices.get(2).setInvoiceItems(List.of());

        this.invoiceRefresher.refresh(invoices);

        verify(this.mStatusRepo, times(1)).refreshAccessors(invoices);
        verify(this.mItemRepo, times(1)).refresh(List.of());
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends InvoiceAccessor> accessors = List.of(mock(InvoiceAccessor.class), mock(InvoiceAccessor.class));

        this.invoiceRefresher.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }
}
