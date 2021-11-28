package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.InvoiceItemRefresher;
import io.company.brewcraft.repository.MaterialRefresher;
import io.company.brewcraft.service.InvoiceItemAccessor;

public class nvoiceItemRefresherTest {
    private InvoiceItemRefresher invoiceItemRefresher;

    private MaterialRefresher mMaterialRefresher;
    private AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mMaterialRefresher = mock(MaterialRefresher.class);
        mRefresher = mock(AccessorRefresher.class);

        invoiceItemRefresher = new InvoiceItemRefresher(mMaterialRefresher, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<InvoiceItem> invoiceItems = List.of(new InvoiceItem(1L));

        invoiceItemRefresher.refresh(invoiceItems);

        verify(mMaterialRefresher, times(1)).refreshAccessors(invoiceItems);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<InvoiceItemAccessor> accessors = List.of(mock(InvoiceItemAccessor.class), mock(InvoiceItemAccessor.class));

        invoiceItemRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
