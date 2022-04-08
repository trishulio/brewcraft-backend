package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.service.InvoiceStatusAccessor;

public class InvoiceStatusRefresherTest {
    private InvoiceStatusRefresher invoiceStatusRefresher;

    private AccessorRefresher<Long, InvoiceStatusAccessor, InvoiceStatus> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        invoiceStatusRefresher = new InvoiceStatusRefresher(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<InvoiceStatusAccessor> accessors = List.of(mock(InvoiceStatusAccessor.class), mock(InvoiceStatusAccessor.class));

        invoiceStatusRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_DoesNothing() {
        List<InvoiceStatus> entities = List.of(new InvoiceStatus(1L), new InvoiceStatus(2L));

        invoiceStatusRefresher.refresh(entities);

        List<InvoiceStatus> expected = List.of(new InvoiceStatus(1L), new InvoiceStatus(2L));
        assertEquals(expected, entities);
    }
}
