package io.company.brewcraft.model;

import io.company.brewcraft.service.FinishedGoodLotAccessor;

public interface BaseFinishedGoodLotMaterialPortion<T extends BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<T>, ? extends BaseFinishedGoodLotMaterialPortion<T>>> extends BaseMaterialPortion, FinishedGoodLotAccessor {

}