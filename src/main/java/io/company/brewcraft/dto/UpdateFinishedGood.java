package io.company.brewcraft.dto;

import io.company.brewcraft.model.BaseFinishedGood;
import io.company.brewcraft.model.UpdateFinishedGoodMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodMixturePortion;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateFinishedGood<T extends UpdateFinishedGoodMixturePortion<? extends UpdateFinishedGood<T, U>>, U extends UpdateFinishedGoodMaterialPortion<? extends UpdateFinishedGood<T,U>>> extends BaseFinishedGood<T,U>, UpdatableEntity<Long> {
}
