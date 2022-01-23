package io.company.brewcraft.model;

import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateFinishedGoodLotMaterialPortion<T extends UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<T>,? extends UpdateFinishedGoodLotMaterialPortion<T>>> extends BaseFinishedGoodLotMaterialPortion<T>, UpdatableEntity<Long> {
}
