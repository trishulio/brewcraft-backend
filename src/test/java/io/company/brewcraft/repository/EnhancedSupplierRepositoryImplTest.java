package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.SupplierAccessor;

public class EnhancedSupplierRepositoryImplTest {

    private EnhancedSupplierRepository repo;

    private AccessorRefresher<Long, SupplierAccessor, Supplier> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedSupplierRepositoryImpl(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<SupplierAccessor> accessors = List.of(mock(SupplierAccessor.class), mock(SupplierAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_DoesNothing() {
        repo.refresh(null);
    }
}
