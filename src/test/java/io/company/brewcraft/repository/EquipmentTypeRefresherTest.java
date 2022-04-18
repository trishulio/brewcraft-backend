package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.service.EquipmentTypeAccessor;

public class EquipmentTypeRefresherTest {
    private EquipmentTypeRefresher equipmentTypeRefresher;

    private AccessorRefresher<Long, EquipmentTypeAccessor, EquipmentType> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);

        equipmentTypeRefresher = new EquipmentTypeRefresher(mRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<EquipmentTypeAccessor> accessors = List.of(mock(EquipmentTypeAccessor.class), mock(EquipmentTypeAccessor.class));

        equipmentTypeRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
