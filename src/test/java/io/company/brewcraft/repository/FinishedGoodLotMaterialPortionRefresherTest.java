package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.FinishedGoodLotMaterialPortionRefresher;
import io.company.brewcraft.repository.MaterialLotRefresher;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionAccessor;

public class FinishedGoodLotMaterialPortionRefresherTest {
    private FinishedGoodLotMaterialPortionRefresher finishedGoodLotMaterialPortionRefresher;

    private MaterialLotRefresher materialLotRefresher;
    private AccessorRefresher<Long, FinishedGoodLotMaterialPortionAccessor, FinishedGoodLotMaterialPortion> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        materialLotRefresher = mock(MaterialLotRefresher.class);
        mRefresher = mock(AccessorRefresher.class);

        finishedGoodLotMaterialPortionRefresher = new FinishedGoodLotMaterialPortionRefresher(materialLotRefresher, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<FinishedGoodLotMaterialPortion> materialPortions = List.of(new FinishedGoodLotMaterialPortion(1L));

        finishedGoodLotMaterialPortionRefresher.refresh(materialPortions);

        verify(materialLotRefresher, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<FinishedGoodLotMaterialPortionAccessor> accessors = List.of(mock(FinishedGoodLotMaterialPortionAccessor.class), mock(FinishedGoodLotMaterialPortionAccessor.class));

        finishedGoodLotMaterialPortionRefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
