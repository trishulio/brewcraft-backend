package io.company.brewcraft.model;

import io.company.brewcraft.dto.UpdateFinishedGood;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateFinishedGoodMaterialPortion<T extends UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<T>,? extends UpdateFinishedGoodMaterialPortion<T>>> extends BaseFinishedGoodMaterialPortion<T>, UpdatableEntity<Long> {
}
