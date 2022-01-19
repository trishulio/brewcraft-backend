package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.service.ChildFinishedGoodsAccessor;
import io.company.brewcraft.service.FinishedGoodAccessor;

public class FinishedGoodRefresherTest {

    private FinishedGoodRefresher finishedGoodRefresher;

    private AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> mRefresher;

    private FinishedGoodMixturePortionRefresher fgMixturePortionRefresher;

    private FinishedGoodMaterialPortionRefresher fgMaterialPortionRefresher;

    private SkuRefresher skuRefresher;

    private CollectionAccessorRefresher<Long, ChildFinishedGoodsAccessor, FinishedGood> childFinishedGoodsRefresher;


    @BeforeEach
    public void init() {
        this.fgMaterialPortionRefresher = mock(FinishedGoodMaterialPortionRefresher.class);
        this.skuRefresher = mock(SkuRefresher.class);
        this.fgMixturePortionRefresher = mock(FinishedGoodMixturePortionRefresher.class);
        this.mRefresher = mock(AccessorRefresher.class);
        this.childFinishedGoodsRefresher = mock(CollectionAccessorRefresher.class);

        this.finishedGoodRefresher = new FinishedGoodRefresher(mRefresher, skuRefresher, fgMixturePortionRefresher, fgMaterialPortionRefresher, childFinishedGoodsRefresher);
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

        final List<FinishedGood> childFinishedGoods = List.of(
                new FinishedGood(5L),
                new FinishedGood(6L),
                new FinishedGood(7L),
                new FinishedGood(8L)
        );

        finishedGoods.get(0).setMixturePortions(List.of(mixturePortions.get(0), mixturePortions.get(1)));
        finishedGoods.get(0).setMaterialPortions(List.of(materialPortions.get(0), materialPortions.get(1)));
        finishedGoods.get(0).setChildFinishedGoods(List.of(childFinishedGoods.get(0), childFinishedGoods.get(1)));

        finishedGoods.get(1).setMixturePortions(List.of(mixturePortions.get(2), mixturePortions.get(3)));
        finishedGoods.get(1).setMaterialPortions(List.of(materialPortions.get(2), materialPortions.get(3)));
        finishedGoods.get(1).setChildFinishedGoods(List.of(childFinishedGoods.get(2), childFinishedGoods.get(3)));

        this.finishedGoodRefresher.refresh(finishedGoods);

        verify(this.skuRefresher, times(1)).refreshAccessors(finishedGoods);
        verify(this.fgMaterialPortionRefresher, times(1)).refresh(materialPortions);
        verify(this.fgMixturePortionRefresher, times(1)).refresh(mixturePortions);
        verify(this.childFinishedGoodsRefresher, times(1)).refreshAccessors(finishedGoods);
    }

    @Test
    public void testRefreshAccessors_DelegatesTheCallToAccessorRefresher() {
        final Collection<? extends FinishedGoodAccessor> accessors = List.of(mock(FinishedGoodAccessor.class), mock(FinishedGoodAccessor.class));

        this.finishedGoodRefresher.refreshAccessors(accessors);

        verify(this.mRefresher, times(1)).refreshAccessors(accessors);
    }

}
