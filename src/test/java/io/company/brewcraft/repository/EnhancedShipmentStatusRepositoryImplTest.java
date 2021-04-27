package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.ShipmentStatusAccessor;

public class EnhancedShipmentStatusRepositoryImplTest {
    private EnhancedShipmentStatusRepository repo;

    private AccessorRefresher<String, ShipmentStatusAccessor, ShipmentStatus> mRefresher;

    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedShipmentStatusRepositoryImpl(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<ShipmentStatusAccessor> accessors = List.of(mock(ShipmentStatusAccessor.class), mock(ShipmentStatusAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
