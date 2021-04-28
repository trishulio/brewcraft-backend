package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.service.InvoiceItemAccessor;

public class EnhancedInvoiceItemRepositoryImplTest {
    private EnhancedInvoiceItemRepository repo;

    private MaterialRepository mMaterialRepo;
    private AccessorRefresher<Long, InvoiceItemAccessor, InvoiceItem> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mMaterialRepo = mock(MaterialRepository.class);
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedInvoiceItemRepositoryImpl(mMaterialRepo, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<InvoiceItem> items = List.of(new InvoiceItem(1L));

        repo.refresh(items);

        verify(mMaterialRepo, times(1)).refreshAccessors(items);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<InvoiceItemAccessor> accessors = List.of(mock(InvoiceItemAccessor.class), mock(InvoiceItemAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
