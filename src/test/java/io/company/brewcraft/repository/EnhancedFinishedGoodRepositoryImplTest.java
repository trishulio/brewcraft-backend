package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodAccessor;

public class EnhancedFinishedGoodRepositoryImplTest {

    private EnhancedFinishedGoodRepository repo;

    private AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> mRefresher;

    private FinishedGoodMixturePortionRepository fgMixturePortionRepository;
    private FinishedGoodMaterialPortionRepository fgMaterialPortionRepository;
    private SkuRepository skuRepository;

    @BeforeEach
    public void init() {
        this.fgMaterialPortionRepository = mock(FinishedGoodMaterialPortionRepository.class);
        this.skuRepository = mock(SkuRepository.class);
        this.fgMixturePortionRepository = mock(FinishedGoodMixturePortionRepository.class);
        this.mRefresher = mock(AccessorRefresher.class);

        this.repo = new EnhancedFinishedGoodRepositoryImpl(mRefresher, skuRepository, fgMixturePortionRepository, fgMaterialPortionRepository);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        final List<FinishedGood> finishedGoods = List.of(
            new FinishedGood(1L),
            new FinishedGood(2L)
        );

        final List<FinishedGoodMixturePortion> mixturePortions = List.of(
            new FinishedGoodMixturePortion(10L),
            new FinishedGoodMixturePortion(100L),
            new FinishedGoodMixturePortion(20L),
            new FinishedGoodMixturePortion(200L)
        );
        
        final List<FinishedGoodMaterialPortion> materialPortions = List.of(
            new FinishedGoodMaterialPortion(30L),
            new FinishedGoodMaterialPortion(300L),
            new FinishedGoodMaterialPortion(40L),
            new FinishedGoodMaterialPortion(400L)
        );

        finishedGoods.get(0).setMixturePortions(List.of(mixturePortions.get(0), mixturePortions.get(1)));
        finishedGoods.get(0).setMaterialPortions(List.of(materialPortions.get(0), materialPortions.get(1)));

        finishedGoods.get(1).setMixturePortions(List.of(mixturePortions.get(2), mixturePortions.get(3)));
        finishedGoods.get(1).setMaterialPortions(List.of(materialPortions.get(2), materialPortions.get(3)));

        this.repo.refresh(finishedGoods);

        verify(this.skuRepository, times(1)).refreshAccessors(finishedGoods);

        verify(this.fgMaterialPortionRepository, times(1)).refresh(materialPortions);
        verify(this.fgMixturePortionRepository, times(1)).refresh(mixturePortions);
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends FinishedGoodAccessor> accessors = List.of(mock(FinishedGoodAccessor.class), mock(FinishedGoodAccessor.class));

        this.repo.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }
}
