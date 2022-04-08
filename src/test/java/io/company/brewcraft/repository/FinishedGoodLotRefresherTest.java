package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.service.FinishedGoodLotAccessor;

public class FinishedGoodLotRefresherTest {
    private FinishedGoodLotRefresher finishedGoodLotRefresher;

    private AccessorRefresher<Long, FinishedGoodLotAccessor, FinishedGoodLot> mRefresher;

    private FinishedGoodLotMixturePortionRefresher fgLotMixturePortionRefresher;

    private FinishedGoodLotMaterialPortionRefresher fgLotMaterialPortionRefresher;

    private FinishedGoodLotFinishedGoodLotPortionRefresher fgLotFgLotPortionRefresher;

    private SkuRefresher skuRefresher;

    @BeforeEach
    public void init() {
        this.fgLotMaterialPortionRefresher = mock(FinishedGoodLotMaterialPortionRefresher.class);
        this.skuRefresher = mock(SkuRefresher.class);
        this.fgLotMixturePortionRefresher = mock(FinishedGoodLotMixturePortionRefresher.class);
        this.mRefresher = mock(AccessorRefresher.class);
        this.fgLotFgLotPortionRefresher = mock(FinishedGoodLotFinishedGoodLotPortionRefresher.class);

        this.finishedGoodLotRefresher = new FinishedGoodLotRefresher(mRefresher, skuRefresher, fgLotMixturePortionRefresher, fgLotMaterialPortionRefresher, fgLotFgLotPortionRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        final List<FinishedGoodLot> finishedGoods = List.of(
            new FinishedGoodLot(1L),
            new FinishedGoodLot(2L)
        );

        final List<FinishedGoodLotMixturePortion> mixturePortions = List.of(
            new FinishedGoodLotMixturePortion(10L),
            new FinishedGoodLotMixturePortion(100L),
            new FinishedGoodLotMixturePortion(20L),
            new FinishedGoodLotMixturePortion(200L)
        );

        final List<FinishedGoodLotMaterialPortion> materialPortions = List.of(
            new FinishedGoodLotMaterialPortion(30L),
            new FinishedGoodLotMaterialPortion(300L),
            new FinishedGoodLotMaterialPortion(40L),
            new FinishedGoodLotMaterialPortion(400L)
        );

        final List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions = List.of(
                new FinishedGoodLotFinishedGoodLotPortion(5L),
                new FinishedGoodLotFinishedGoodLotPortion(6L),
                new FinishedGoodLotFinishedGoodLotPortion(7L),
                new FinishedGoodLotFinishedGoodLotPortion(8L)
        );

        finishedGoods.get(0).setMixturePortions(List.of(mixturePortions.get(0), mixturePortions.get(1)));
        finishedGoods.get(0).setMaterialPortions(List.of(materialPortions.get(0), materialPortions.get(1)));
        finishedGoods.get(0).setFinishedGoodLotPortions(List.of(finishedGoodLotPortions.get(0), finishedGoodLotPortions.get(1)));

        finishedGoods.get(1).setMixturePortions(List.of(mixturePortions.get(2), mixturePortions.get(3)));
        finishedGoods.get(1).setMaterialPortions(List.of(materialPortions.get(2), materialPortions.get(3)));
        finishedGoods.get(1).setFinishedGoodLotPortions(List.of(finishedGoodLotPortions.get(2), finishedGoodLotPortions.get(3)));

        this.finishedGoodLotRefresher.refresh(finishedGoods);

        verify(this.skuRefresher, times(1)).refreshAccessors(finishedGoods);
        verify(this.fgLotMaterialPortionRefresher, times(1)).refresh(materialPortions);
        verify(this.fgLotMixturePortionRefresher, times(1)).refresh(mixturePortions);
        verify(this.fgLotFgLotPortionRefresher, times(1)).refresh(finishedGoodLotPortions);
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends FinishedGoodLotAccessor> accessors = List.of(mock(FinishedGoodLotAccessor.class), mock(FinishedGoodLotAccessor.class));

        this.finishedGoodLotRefresher.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }
}
