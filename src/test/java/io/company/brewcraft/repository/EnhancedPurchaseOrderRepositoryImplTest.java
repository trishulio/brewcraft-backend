package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public class EnhancedPurchaseOrderRepositoryImplTest {

    private EnhancedPurchaseOrderRepository repo;

    private AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedPurchaseOrderRepositoryImpl(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<PurchaseOrderAccessor> accessors = List.of(mock(PurchaseOrderAccessor.class), mock(PurchaseOrderAccessor.class));
        
        repo.refreshAccessors(accessors);
        
        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
