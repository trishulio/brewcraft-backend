package io.company.brewcraft.dto;

import io.company.brewcraft.model.BaseFinishedGoodLot;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateFinishedGoodLot<T extends UpdateFinishedGoodLotMixturePortion<? extends UpdateFinishedGoodLot<T, U>>, U extends UpdateFinishedGoodLotMaterialPortion<? extends UpdateFinishedGoodLot<T,U>>> extends BaseFinishedGoodLot<T,U>, UpdatableEntity<Long> {
}
