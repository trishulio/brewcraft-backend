package io.company.brewcraft.model;

import io.company.brewcraft.dto.UpdateFinishedGood;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateFinishedGoodMixturePortion<T extends UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<T>, ? extends UpdateFinishedGoodMaterialPortion<T>>> extends BaseFinishedGoodMixturePortion<T>, UpdatableEntity<Long> {
}
