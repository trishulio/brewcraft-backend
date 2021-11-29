package io.company.brewcraft.model;

import io.company.brewcraft.service.FinishedGoodAccessor;

public interface BaseFinishedGoodMixturePortion<T extends BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<T>, ? extends BaseFinishedGoodMaterialPortion<T>>> extends BaseMixturePortion, FinishedGoodAccessor {

}