package io.company.brewcraft.model;

import io.company.brewcraft.service.FinishedGoodLotAccessor;

public interface BaseFinishedGoodLotMixturePortion<T extends BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<T>, ? extends BaseFinishedGoodLotMaterialPortion<T>>> extends BaseMixturePortion, FinishedGoodLotAccessor {
}