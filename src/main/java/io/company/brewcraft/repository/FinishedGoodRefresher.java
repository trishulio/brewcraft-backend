package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.service.ChildFinishedGoodsAccessor;
import io.company.brewcraft.service.FinishedGoodAccessor;
import io.company.brewcraft.service.FinishedGoodMaterialPortionAccessor;
import io.company.brewcraft.service.FinishedGoodMixturePortionAccessor;

public class FinishedGoodRefresher implements Refresher<FinishedGood, FinishedGoodAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodRefresher.class);

    private final AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> refresher;

    private final Refresher<Sku, SkuAccessor> skuRefresher;

    private final Refresher<FinishedGoodMixturePortion, FinishedGoodMixturePortionAccessor> fgMixturePortionRefresher;

    private final Refresher<FinishedGoodMaterialPortion, FinishedGoodMaterialPortionAccessor> fgMaterialPortionRefresher;

    private final CollectionAccessorRefresher<Long, ChildFinishedGoodsAccessor, FinishedGood> childFinishedGoodsRefresher;

    public FinishedGoodRefresher(AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> refresher, Refresher<Sku, SkuAccessor> skuRefresher, Refresher<FinishedGoodMixturePortion, FinishedGoodMixturePortionAccessor> fgMixturePortionRefresher, Refresher<FinishedGoodMaterialPortion, FinishedGoodMaterialPortionAccessor> fgMaterialPortionRefresher, CollectionAccessorRefresher<Long, ChildFinishedGoodsAccessor, FinishedGood> childFinishedGoodsRefresher) {
        this.refresher = refresher;
        this.skuRefresher = skuRefresher;
        this.fgMixturePortionRefresher = fgMixturePortionRefresher;
        this.fgMaterialPortionRefresher = fgMaterialPortionRefresher;
        this.childFinishedGoodsRefresher = childFinishedGoodsRefresher;
    }

    @Override
    public void refresh(Collection<FinishedGood> finishedGoods) {
        this.skuRefresher.refreshAccessors(finishedGoods);

        final List<FinishedGoodMixturePortion> mixturePortions = finishedGoods.stream().filter(i -> i.getMixturePortions() != null).flatMap(i -> i.getMixturePortions().stream()).collect(Collectors.toList());
        this.fgMixturePortionRefresher.refresh(mixturePortions);

        final List<FinishedGoodMaterialPortion> materialPortions = finishedGoods.stream().filter(i -> i.getMaterialPortions() != null).flatMap(i -> i.getMaterialPortions().stream()).collect(Collectors.toList());
        this.fgMaterialPortionRefresher.refresh(materialPortions);

        this.childFinishedGoodsRefresher.refreshAccessors(finishedGoods);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

}
