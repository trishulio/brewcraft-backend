package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.service.FinishedGoodLotAccessor;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionAccessor;
import io.company.brewcraft.service.FinishedGoodLotMaterialPortionAccessor;
import io.company.brewcraft.service.FinishedGoodLotMixturePortionAccessor;

public class FinishedGoodLotRefresher implements Refresher<FinishedGoodLot, FinishedGoodLotAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotRefresher.class);

    private final AccessorRefresher<Long, FinishedGoodLotAccessor, FinishedGoodLot> refresher;

    private final Refresher<Sku, SkuAccessor> skuRefresher;

    private final Refresher<FinishedGoodLotMixturePortion, FinishedGoodLotMixturePortionAccessor> fgMixturePortionRefresher;

    private final Refresher<FinishedGoodLotMaterialPortion, FinishedGoodLotMaterialPortionAccessor> fgMaterialPortionRefresher;

    private final Refresher<FinishedGoodLotFinishedGoodLotPortion, FinishedGoodLotFinishedGoodLotPortionAccessor> fgLotFgLotPortionRefresher;

    public FinishedGoodLotRefresher(AccessorRefresher<Long, FinishedGoodLotAccessor, FinishedGoodLot> refresher, Refresher<Sku, SkuAccessor> skuRefresher, Refresher<FinishedGoodLotMixturePortion, FinishedGoodLotMixturePortionAccessor> fgMixturePortionRefresher, Refresher<FinishedGoodLotMaterialPortion, FinishedGoodLotMaterialPortionAccessor> fgMaterialPortionRefresher, Refresher<FinishedGoodLotFinishedGoodLotPortion, FinishedGoodLotFinishedGoodLotPortionAccessor> fgLotFgLotPortionRefresher) {
        this.refresher = refresher;
        this.skuRefresher = skuRefresher;
        this.fgMixturePortionRefresher = fgMixturePortionRefresher;
        this.fgMaterialPortionRefresher = fgMaterialPortionRefresher;
        this.fgLotFgLotPortionRefresher = fgLotFgLotPortionRefresher;
    }

    @Override
    public void refresh(Collection<FinishedGoodLot> finishedGoods) {
        this.skuRefresher.refreshAccessors(finishedGoods);

        final List<FinishedGoodLotMixturePortion> mixturePortions = finishedGoods.stream().filter(i -> i.getMixturePortions() != null).flatMap(i -> i.getMixturePortions().stream()).collect(Collectors.toList());
        this.fgMixturePortionRefresher.refresh(mixturePortions);

        final List<FinishedGoodLotMaterialPortion> materialPortions = finishedGoods.stream().filter(i -> i.getMaterialPortions() != null).flatMap(i -> i.getMaterialPortions().stream()).collect(Collectors.toList());
        this.fgMaterialPortionRefresher.refresh(materialPortions);

        final List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions = finishedGoods.stream().filter(i -> i.getFinishedGoodLotPortions() != null).flatMap(i -> i.getFinishedGoodLotPortions().stream()).collect(Collectors.toList());
        this.fgLotFgLotPortionRefresher.refresh(finishedGoodLotPortions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodLotAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
