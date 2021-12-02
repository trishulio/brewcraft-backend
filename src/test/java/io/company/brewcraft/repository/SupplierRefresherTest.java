package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.SupplierRefresher;
import io.company.brewcraft.service.SupplierAccessor;

public class SupplierRefresherTest {

    private SupplierRefresher supplierRefresher;

    private AccessorRefresher<Long, SupplierAccessor, Supplier> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        supplierRefresher = new SupplierRefresher(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<SupplierAccessor> accessors = List.of(mock(SupplierAccessor.class), mock(SupplierAccessor.class));

        supplierRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_DoesNothing() {
        supplierRefresher.refresh(null);
    }
}
