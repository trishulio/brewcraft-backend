package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementAccessor;
import io.company.brewcraft.model.procurement.ProcurementId;

public class ProcurementRefresherTest {
    private ProcurementRefresher procurementRefresher;

    private AccessorRefresher<ProcurementId, ProcurementAccessor, Procurement> mRefresher;

    private InvoiceRefresher mInvoiceRefresher;
    private ShipmentRefresher mShipmentRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);
        mInvoiceRefresher = mock(InvoiceRefresher.class);
        mShipmentRefresher = mock(ShipmentRefresher.class);

        procurementRefresher = new ProcurementRefresher(mRefresher, mInvoiceRefresher, mShipmentRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<ProcurementAccessor> accessors = List.of(mock(ProcurementAccessor.class), mock(ProcurementAccessor.class));

        procurementRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_RefreshesInvoices() {
        procurementRefresher.refresh(List.of(new Procurement(new ProcurementId(1L, 10L)), new Procurement(new ProcurementId(2L, 20L))));

        verify(mInvoiceRefresher, times(1)).refreshAccessors(List.of(new Procurement(new ProcurementId(1L, 10L)), new Procurement(new ProcurementId(2L, 20L))));
        verify(mShipmentRefresher, times(1)).refreshAccessors(List.of(new Procurement(new ProcurementId(1L, 10L)), new Procurement(new ProcurementId(2L, 20L))));
    }
}
