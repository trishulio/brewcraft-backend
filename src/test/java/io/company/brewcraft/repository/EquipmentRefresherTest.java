package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.service.EquipmentAccessor;

public class EquipmentRefresherTest {
    private EquipmentRefresher equipmentRefresher;

    private AccessorRefresher<Long, EquipmentAccessor, Equipment> mRefresher;
    private FacilityRefresher mFacilityRefresher;
    private EquipmentTypeRefresher mTypeRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mRefresher = mock(AccessorRefresher.class);
        mFacilityRefresher = mock(FacilityRefresher.class);
        mTypeRefresher = mock(EquipmentTypeRefresher.class);

        equipmentRefresher = new EquipmentRefresher(mRefresher, mFacilityRefresher, mTypeRefresher);
    }

    @Test
    public void testRefreshAccessors_CallsRefreshAccessor() {
        List<EquipmentAccessor> accessors = List.of(mock(EquipmentAccessor.class), mock(EquipmentAccessor.class));

        equipmentRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }

    @Test
    public void testRefresh_RefreshesFacilitys() {
        equipmentRefresher.refresh(List.of(new Equipment(1L), new Equipment(2L)));

        verify(mFacilityRefresher, times(1)).refreshAccessors(List.of(new Equipment(1L), new Equipment(2L)));
        verify(mTypeRefresher, times(1)).refreshAccessors(List.of(new Equipment(1L), new Equipment(2L)));
    }
}
