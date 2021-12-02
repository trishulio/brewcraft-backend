package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.FinishedGoodMaterialPortionRefresher;
import io.company.brewcraft.repository.MaterialLotRefresher;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;

public class FinishedGoodMaterialPortionRefresherTest {
    private FinishedGoodMaterialPortionRefresher finishedGoodMaterialPortionRefresher;

    private MaterialLotRefresher materialLotRefresher;
    private AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        materialLotRefresher = mock(MaterialLotRefresher.class);
        mRefresher = mock(AccessorRefresher.class);

        finishedGoodMaterialPortionRefresher = new FinishedGoodMaterialPortionRefresher(materialLotRefresher, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<FinishedGoodMaterialPortion> materialPortions = List.of(new FinishedGoodMaterialPortion(1L));

        finishedGoodMaterialPortionRefresher.refresh(materialPortions);

        verify(materialLotRefresher, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<FinishedGoodMaterialPortionAccessor> accessors = List.of(mock(FinishedGoodMaterialPortionAccessor.class), mock(FinishedGoodMaterialPortionAccessor.class));

        finishedGoodMaterialPortionRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
