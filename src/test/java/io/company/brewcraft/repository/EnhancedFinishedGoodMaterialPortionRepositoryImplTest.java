package io.company.brewcraft.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;

public class EnhancedFinishedGoodMaterialPortionRepositoryImplTest {
    private EnhancedFinishedGoodMaterialPortionRepository repo;

    private MaterialLotRepository materialLotRepository;
    private AccessorRefresher<Long, FinishedGoodMaterialPortionAccessor, FinishedGoodMaterialPortion> mRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        materialLotRepository = mock(MaterialLotRepository.class);
        mRefresher = mock(AccessorRefresher.class);

        repo = new EnhancedFinishedGoodMaterialPortionRepositoryImpl(materialLotRepository, mRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<FinishedGoodMaterialPortion> materialPortions = List.of(new FinishedGoodMaterialPortion(1L));

        repo.refresh(materialPortions);

        verify(materialLotRepository, times(1)).refreshAccessors(materialPortions);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<FinishedGoodMaterialPortionAccessor> accessors = List.of(mock(FinishedGoodMaterialPortionAccessor.class), mock(FinishedGoodMaterialPortionAccessor.class));

        repo.refreshAccessors(accessors);

        verify(mRefresher, times(1)).refreshAccessors(accessors);
    }
}
