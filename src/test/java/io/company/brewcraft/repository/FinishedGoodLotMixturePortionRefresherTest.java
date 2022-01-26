package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionAccessor;

public class FinishedGoodLotMixturePortionRefresherTest {
    private FinishedGoodLotMixturePortionRefresher finishedGoodLotMixturePortionefresher;

    private MixtureRefresher mixtureRefresher;
    private AccessorRefresher<Long, FinishedGoodLotMixturePortionAccessor, FinishedGoodLotMixturePortion> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRefresher = mock(MixtureRefresher.class);
        mRefresher = mock(AccessorRefresher.class);

        finishedGoodLotMixturePortionefresher = new FinishedGoodLotMixturePortionRefresher(mixtureRefresher, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<FinishedGoodLotMixturePortion> materialPortions = List.of(new FinishedGoodLotMixturePortion(1L));

        finishedGoodLotMixturePortionefresher.refresh(materialPortions);

        verify(mixtureRefresher, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<FinishedGoodLotMixturePortionAccessor> accessors = List.of(mock(FinishedGoodLotMixturePortionAccessor.class), mock(FinishedGoodLotMixturePortionAccessor.class));

        finishedGoodLotMixturePortionefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
