package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.ShipmentStatusAccessor;

public class ShipmentStatusRefresherTest {
    private ShipmentStatusRefresher shipmentStatusRefresher;

    private AccessorRefresher<Long, ShipmentStatusAccessor, ShipmentStatus> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        shipmentStatusRefresher = new ShipmentStatusRefresher(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<ShipmentStatusAccessor> accessors = List.of(mock(ShipmentStatusAccessor.class), mock(ShipmentStatusAccessor.class));

        shipmentStatusRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_DoesNothing() {
        List<ShipmentStatus> entities = List.of(new ShipmentStatus(1L), new ShipmentStatus(2L));

        shipmentStatusRefresher.refresh(entities);

        List<ShipmentStatus> expected = List.of(new ShipmentStatus(1L), new ShipmentStatus(2L));
        assertEquals(expected, entities);
    }
}
