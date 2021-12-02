package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.FinishedGoodMixturePortionRefresher;
import io.company.brewcraft.repository.MixtureRefresher;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;

public class FinishedGoodMixturePortionRefresherTest {
    private FinishedGoodMixturePortionRefresher finishedGoodMixturePortionefresher;

    private MixtureRefresher mixtureRefresher;
    private AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRefresher = mock(MixtureRefresher.class);
        mRefresher = mock(AccessorRefresher.class);

        finishedGoodMixturePortionefresher = new FinishedGoodMixturePortionRefresher(mixtureRefresher, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<FinishedGoodMixturePortion> materialPortions = List.of(new FinishedGoodMixturePortion(1L));

        finishedGoodMixturePortionefresher.refresh(materialPortions);

        verify(mixtureRefresher, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<FinishedGoodMixturePortionAccessor> accessors = List.of(mock(FinishedGoodMixturePortionAccessor.class), mock(FinishedGoodMixturePortionAccessor.class));

        finishedGoodMixturePortionefresher.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
