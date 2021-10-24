package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;

public class EnhancedFinishedGoodMixturePortionRepositoryImplTest {
    private EnhancedFinishedGoodMixturePortionRepository repo;

    private MixtureRepository mixtureRepository;
    private AccessorRefresher<Long, FinishedGoodMixturePortionAccessor, FinishedGoodMixturePortion> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        mixtureRepository = mock(MixtureRepository.class);
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedFinishedGoodMixturePortionRepositoryImpl(mixtureRepository, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<FinishedGoodMixturePortion> materialPortions = List.of(new FinishedGoodMixturePortion(1L));

        repo.refresh(materialPortions);

        verify(mixtureRepository, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<FinishedGoodMixturePortionAccessor> accessors = List.of(mock(FinishedGoodMixturePortionAccessor.class), mock(FinishedGoodMixturePortionAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
