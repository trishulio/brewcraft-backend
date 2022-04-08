package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.PurchaseOrderRefresher;
import io.company.brewcraft.repository.SupplierRefresher;
import io.company.brewcraft.service.PurchaseOrderAccessor;

public class PurchaseOrderRefresherTest {
    private PurchaseOrderRefresher purchaseOrderRefresher;

    private AccessorRefresher<Long, PurchaseOrderAccessor, PurchaseOrder> mRefresher;
    private SupplierRefresher mSupplierRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);
        mSupplierRefresher = mock(SupplierRefresher.class);

        purchaseOrderRefresher = new PurchaseOrderRefresher(mRefresher, mSupplierRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<PurchaseOrderAccessor> accessors = List.of(mock(PurchaseOrderAccessor.class), mock(PurchaseOrderAccessor.class));

        purchaseOrderRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_RefreshesSuppliers() {
        purchaseOrderRefresher.refresh(List.of(new PurchaseOrder(1L), new PurchaseOrder(2L)));

        verify(mSupplierRefresher, times(1)).refreshAccessors(List.of(new PurchaseOrder(1L), new PurchaseOrder(2L)));
    }
}
